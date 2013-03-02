/*    */ package com.sun.javafx.sg;
/*    */ 
/*    */ import com.sun.javafx.geom.Path2D;
/*    */ 
/*    */ public abstract interface PGPath extends PGShape
/*    */ {
/*    */   public abstract void reset();
/*    */ 
/*    */   public abstract void update();
/*    */ 
/*    */   public abstract void setFillRule(FillRule paramFillRule);
/*    */ 
/*    */   public abstract float getCurrentX();
/*    */ 
/*    */   public abstract float getCurrentY();
/*    */ 
/*    */   public abstract void addClosePath();
/*    */ 
/*    */   public abstract void addMoveTo(float paramFloat1, float paramFloat2);
/*    */ 
/*    */   public abstract void addLineTo(float paramFloat1, float paramFloat2);
/*    */ 
/*    */   public abstract void addQuadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*    */ 
/*    */   public abstract void addCubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
/*    */ 
/*    */   public abstract void addArcTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7);
/*    */ 
/*    */   public abstract boolean acceptsPath2dOnUpdate();
/*    */ 
/*    */   public abstract void updateWithPath2d(Path2D paramPath2D);
/*    */ 
/*    */   public static enum FillRule
/*    */   {
/* 13 */     EVEN_ODD, NON_ZERO;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.PGPath
 * JD-Core Version:    0.6.2
 */