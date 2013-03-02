/*    */ package com.sun.scenario.effect.impl.state;
/*    */ 
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*    */ 
/*    */ public abstract class HVSeparableKernel extends LinearConvolveKernel
/*    */ {
/*    */   public final int getNumberOfPasses()
/*    */   {
/* 38 */     return 2;
/*    */   }
/*    */ 
/*    */   public LinearConvolveKernel.PassType getPassType(int paramInt)
/*    */   {
/* 43 */     return paramInt == 0 ? LinearConvolveKernel.PassType.HORIZONTAL_CENTERED : LinearConvolveKernel.PassType.VERTICAL_CENTERED;
/*    */   }
/*    */ 
/*    */   public final Rectangle getResultBounds(Rectangle paramRectangle, int paramInt)
/*    */   {
/* 50 */     int i = getKernelSize(paramInt);
/* 51 */     Rectangle localRectangle = new Rectangle(paramRectangle);
/* 52 */     if (paramInt == 0)
/* 53 */       localRectangle.grow(i / 2, 0);
/*    */     else {
/* 55 */       localRectangle.grow(0, i / 2);
/*    */     }
/* 57 */     return localRectangle;
/*    */   }
/*    */ 
/*    */   public final Rectangle getScaledResultBounds(Rectangle paramRectangle, int paramInt)
/*    */   {
/* 62 */     int i = getScaledKernelSize(paramInt);
/* 63 */     Rectangle localRectangle = new Rectangle(paramRectangle);
/* 64 */     if (paramInt == 0)
/* 65 */       localRectangle.grow(i / 2, 0);
/*    */     else {
/* 67 */       localRectangle.grow(0, i / 2);
/*    */     }
/* 69 */     return localRectangle;
/*    */   }
/*    */ 
/*    */   public final float[] getVector(Rectangle paramRectangle, BaseTransform paramBaseTransform, int paramInt)
/*    */   {
/* 76 */     float[] arrayOfFloat = new float[4];
/*    */     float f1;
/* 78 */     if (paramBaseTransform.isTranslateOrIdentity()) {
/* 79 */       f1 = 1.0F;
/*    */     } else {
/* 81 */       arrayOfFloat[0] = (paramInt == 0 ? 1.0F : 0.0F);
/* 82 */       arrayOfFloat[1] = (paramInt == 1 ? 1.0F : 0.0F);
/*    */       try {
/* 84 */         paramBaseTransform.inverseDeltaTransform(arrayOfFloat, 0, arrayOfFloat, 0, 1);
/* 85 */         f1 = (float)Math.hypot(arrayOfFloat[0], arrayOfFloat[1]);
/*    */       } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/* 87 */         f1 = 0.0F;
/*    */       }
/*    */     }
/* 90 */     float f2 = paramInt == 0 ? f1 / paramRectangle.width : 0.0F;
/* 91 */     float f3 = paramInt == 1 ? f1 / paramRectangle.height : 0.0F;
/* 92 */     int i = getScaledKernelSize(paramInt);
/* 93 */     int j = i / 2;
/* 94 */     arrayOfFloat[0] = f2;
/* 95 */     arrayOfFloat[1] = f3;
/* 96 */     arrayOfFloat[2] = (-j * f2);
/* 97 */     arrayOfFloat[3] = (-j * f3);
/* 98 */     return arrayOfFloat;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.state.HVSeparableKernel
 * JD-Core Version:    0.6.2
 */