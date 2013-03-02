/*    */ package com.sun.prism.impl.shape;
/*    */ 
/*    */ import com.sun.javafx.geom.RoundRectangle2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.prism.Graphics;
/*    */ 
/*    */ public class BasicRoundRectRep extends BasicShapeRep
/*    */ {
/* 17 */   private static final float[] TMP_ARR = new float[4];
/*    */ 
/*    */   public void fill(Graphics paramGraphics, Shape paramShape)
/*    */   {
/* 24 */     fillRoundRect(paramGraphics, (RoundRectangle2D)paramShape);
/*    */   }
/*    */ 
/*    */   public static void fillRoundRect(Graphics paramGraphics, RoundRectangle2D paramRoundRectangle2D)
/*    */   {
/* 30 */     float f1 = paramRoundRectangle2D.arcWidth;
/* 31 */     float f2 = paramRoundRectangle2D.arcHeight;
/* 32 */     if ((f1 > 0.0F) && (f2 > 0.0F)) {
/* 33 */       paramGraphics.fillRoundRect(paramRoundRectangle2D.x, paramRoundRectangle2D.y, paramRoundRectangle2D.width, paramRoundRectangle2D.height, f1, f2);
/*    */     }
/* 35 */     else if (isAARequiredForFill(paramGraphics, paramRoundRectangle2D))
/* 36 */       paramGraphics.fillRect(paramRoundRectangle2D.x, paramRoundRectangle2D.y, paramRoundRectangle2D.width, paramRoundRectangle2D.height);
/*    */     else
/* 38 */       paramGraphics.fillQuad(paramRoundRectangle2D.x, paramRoundRectangle2D.y, paramRoundRectangle2D.x + paramRoundRectangle2D.width, paramRoundRectangle2D.y + paramRoundRectangle2D.height);
/*    */   }
/*    */ 
/*    */   public void draw(Graphics paramGraphics, Shape paramShape)
/*    */   {
/* 45 */     drawRoundRect(paramGraphics, (RoundRectangle2D)paramShape);
/*    */   }
/*    */ 
/*    */   public static void drawRoundRect(Graphics paramGraphics, RoundRectangle2D paramRoundRectangle2D)
/*    */   {
/* 51 */     float f1 = paramRoundRectangle2D.arcWidth;
/* 52 */     float f2 = paramRoundRectangle2D.arcHeight;
/* 53 */     if ((f1 > 0.0F) && (f2 > 0.0F))
/* 54 */       paramGraphics.drawRoundRect(paramRoundRectangle2D.x, paramRoundRectangle2D.y, paramRoundRectangle2D.width, paramRoundRectangle2D.height, f1, f2);
/*    */     else
/* 56 */       paramGraphics.drawRect(paramRoundRectangle2D.x, paramRoundRectangle2D.y, paramRoundRectangle2D.width, paramRoundRectangle2D.height);
/*    */   }
/*    */ 
/*    */   private static boolean notIntEnough(float paramFloat)
/*    */   {
/* 64 */     return Math.abs(paramFloat - Math.round(paramFloat)) > 0.06D;
/*    */   }
/*    */ 
/*    */   private static boolean notOnIntGrid(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 68 */     return (notIntEnough(paramFloat1)) || (notIntEnough(paramFloat2)) || (notIntEnough(paramFloat3)) || (notIntEnough(paramFloat4));
/*    */   }
/*    */ 
/*    */   protected static boolean isAARequiredForFill(Graphics paramGraphics, RoundRectangle2D paramRoundRectangle2D)
/*    */   {
/* 76 */     BaseTransform localBaseTransform = paramGraphics.getTransformNoClone();
/* 77 */     long l = localBaseTransform.getType();
/*    */ 
/* 79 */     int i = (l & 0xFFFFFFF0) != 0L ? 1 : 0;
/*    */ 
/* 83 */     if (i != 0) {
/* 84 */       return true;
/*    */     }
/* 86 */     if ((localBaseTransform == null) || (localBaseTransform.isIdentity())) {
/* 87 */       return notOnIntGrid(paramRoundRectangle2D.x, paramRoundRectangle2D.y, paramRoundRectangle2D.x + paramRoundRectangle2D.width, paramRoundRectangle2D.y + paramRoundRectangle2D.height);
/*    */     }
/*    */ 
/* 91 */     TMP_ARR[0] = paramRoundRectangle2D.x;
/* 92 */     TMP_ARR[1] = paramRoundRectangle2D.y;
/* 93 */     TMP_ARR[2] = (paramRoundRectangle2D.x + paramRoundRectangle2D.width);
/* 94 */     TMP_ARR[3] = (paramRoundRectangle2D.y + paramRoundRectangle2D.height);
/* 95 */     localBaseTransform.transform(TMP_ARR, 0, TMP_ARR, 0, 2);
/*    */ 
/* 97 */     return notOnIntGrid(TMP_ARR[0], TMP_ARR[1], TMP_ARR[2], TMP_ARR[3]);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.shape.BasicRoundRectRep
 * JD-Core Version:    0.6.2
 */