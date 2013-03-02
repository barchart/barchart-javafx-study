/*    */ package com.sun.prism.impl.paint;
/*    */ 
/*    */ import com.sun.javafx.geom.transform.Affine2D;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.prism.paint.Color;
/*    */ import com.sun.prism.paint.Gradient;
/*    */ import com.sun.prism.paint.LinearGradient;
/*    */ import com.sun.prism.paint.Paint.Type;
/*    */ import com.sun.prism.paint.RadialGradient;
/*    */ import com.sun.prism.paint.Stop;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PaintUtil
/*    */ {
/* 20 */   private static final Affine2D gradXform = new Affine2D();
/*    */ 
/*    */   public static void fillImageWithGradient(int[] paramArrayOfInt, Gradient paramGradient, BaseTransform paramBaseTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*    */   {
/* 29 */     Gradient localGradient = paramGradient;
/* 30 */     int i = localGradient.getNumStops();
/* 31 */     float[] arrayOfFloat = new float[i];
/* 32 */     Color[] arrayOfColor = new Color[i];
/*    */     Object localObject2;
/* 33 */     for (int j = 0; j < i; j++) {
/* 34 */       localObject2 = (Stop)localGradient.getStops().get(j);
/* 35 */       arrayOfFloat[j] = ((Stop)localObject2).getOffset();
/* 36 */       arrayOfColor[j] = ((Stop)localObject2).getColor();
/*    */     }
/*    */     float f1;
/*    */     float f2;
/*    */     float f3;
/*    */     Object localObject1;
/* 40 */     if (paramGradient.getType() == Paint.Type.LINEAR_GRADIENT) {
/* 41 */       localObject2 = (LinearGradient)paramGradient;
/*    */       float f4;
/* 43 */       if (((LinearGradient)localObject2).isProportional()) {
/* 44 */         f1 = ((LinearGradient)localObject2).getX1() * paramFloat3 + paramFloat1;
/* 45 */         f2 = ((LinearGradient)localObject2).getY1() * paramFloat4 + paramFloat2;
/* 46 */         f3 = ((LinearGradient)localObject2).getX2() * paramFloat3 + paramFloat1;
/* 47 */         f4 = ((LinearGradient)localObject2).getY2() * paramFloat4 + paramFloat2;
/*    */       } else {
/* 49 */         f1 = ((LinearGradient)localObject2).getX1();
/* 50 */         f2 = ((LinearGradient)localObject2).getY1();
/* 51 */         f3 = ((LinearGradient)localObject2).getX2();
/* 52 */         f4 = ((LinearGradient)localObject2).getY2();
/*    */       }
/* 54 */       if ((f1 == f3) && (f2 == f4))
/*    */       {
/* 56 */         f1 -= 1.0E-06F;
/* 57 */         f3 += 1.0E-06F;
/*    */       }
/* 59 */       localObject1 = new LinearGradientContext((LinearGradient)localObject2, paramBaseTransform, f1, f2, f3, f4, arrayOfFloat, arrayOfColor, ((LinearGradient)localObject2).getSpreadMethod());
/*    */     }
/*    */     else
/*    */     {
/* 64 */       localObject2 = (RadialGradient)paramGradient;
/* 65 */       gradXform.setTransform(paramBaseTransform);
/* 66 */       f1 = ((RadialGradient)localObject2).getRadius();
/* 67 */       f2 = ((RadialGradient)localObject2).getCenterX();
/* 68 */       f3 = ((RadialGradient)localObject2).getCenterY();
/* 69 */       double d = Math.toRadians(((RadialGradient)localObject2).getFocusAngle());
/* 70 */       float f5 = ((RadialGradient)localObject2).getFocusDistance();
/* 71 */       if (((RadialGradient)localObject2).isProportional()) {
/* 72 */         f6 = paramFloat1 + paramFloat3 / 2.0F;
/* 73 */         f7 = paramFloat2 + paramFloat4 / 2.0F;
/* 74 */         float f8 = Math.min(paramFloat3, paramFloat4);
/* 75 */         f2 = (f2 - 0.5F) * f8 + f6;
/* 76 */         f3 = (f3 - 0.5F) * f8 + f7;
/* 77 */         if ((paramFloat3 != paramFloat4) && (paramFloat3 != 0.0F) && (paramFloat4 != 0.0F)) {
/* 78 */           gradXform.translate(f6, f7);
/* 79 */           gradXform.scale(paramFloat3 / f8, paramFloat4 / f8);
/* 80 */           gradXform.translate(-f6, -f7);
/*    */         }
/* 82 */         f1 *= f8;
/*    */       }
/* 84 */       if (f1 <= 0.0F) {
/* 85 */         f1 = 0.001F;
/*    */       }
/* 87 */       f5 *= f1;
/* 88 */       float f6 = (float)(f2 + f5 * Math.cos(d));
/* 89 */       float f7 = (float)(f3 + f5 * Math.sin(d));
/* 90 */       localObject1 = new RadialGradientContext((RadialGradient)localObject2, gradXform, f2, f3, f1, f6, f7, arrayOfFloat, arrayOfColor, ((RadialGradient)localObject2).getSpreadMethod());
/*    */     }
/*    */ 
/* 96 */     ((MultipleGradientContext)localObject1).fillRaster(paramArrayOfInt, 0, 0, paramInt1, paramInt2, paramInt3, paramInt4);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.paint.PaintUtil
 * JD-Core Version:    0.6.2
 */