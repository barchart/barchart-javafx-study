/*    */ package com.sun.javafx.sg;
/*    */ 
/*    */ public abstract interface PGShape extends PGNode
/*    */ {
/*    */   public abstract void setMode(Mode paramMode);
/*    */ 
/*    */   public abstract void setAntialiased(boolean paramBoolean);
/*    */ 
/*    */   public abstract void setFillPaint(Object paramObject);
/*    */ 
/*    */   public abstract void setDrawPaint(Object paramObject);
/*    */ 
/*    */   public abstract void setDrawStroke(float paramFloat1, StrokeType paramStrokeType, StrokeLineCap paramStrokeLineCap, StrokeLineJoin paramStrokeLineJoin, float paramFloat2, float[] paramArrayOfFloat, float paramFloat3);
/*    */ 
/*    */   public static enum StrokeType
/*    */   {
/* 14 */     INSIDE, OUTSIDE, CENTERED;
/*    */   }
/*    */ 
/*    */   public static enum StrokeLineJoin
/*    */   {
/* 13 */     MITER, BEVEL, ROUND;
/*    */   }
/*    */ 
/*    */   public static enum StrokeLineCap
/*    */   {
/* 12 */     SQUARE, BUTT, ROUND;
/*    */   }
/*    */ 
/*    */   public static enum Mode
/*    */   {
/* 11 */     EMPTY, FILL, STROKE, STROKE_FILL;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.PGShape
 * JD-Core Version:    0.6.2
 */