/*    */ package com.sun.prism.paint;
/*    */ 
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import java.util.List;
/*    */ 
/*    */ public final class LinearGradient extends Gradient
/*    */ {
/*    */   private float x1;
/*    */   private float y1;
/*    */   private float x2;
/*    */   private float y2;
/*    */ 
/*    */   public LinearGradient(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, BaseTransform paramBaseTransform, boolean paramBoolean, int paramInt, List<Stop> paramList)
/*    */   {
/* 26 */     super(Paint.Type.LINEAR_GRADIENT, paramBaseTransform, paramBoolean, paramInt, paramList);
/* 27 */     this.x1 = paramFloat1;
/* 28 */     this.y1 = paramFloat2;
/* 29 */     this.x2 = paramFloat3;
/* 30 */     this.y2 = paramFloat4;
/*    */   }
/*    */   public float getX1() {
/* 33 */     return this.x1; } 
/* 34 */   public float getY1() { return this.y1; } 
/* 35 */   public float getX2() { return this.x2; } 
/* 36 */   public float getY2() { return this.y2; }
/*    */ 
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.paint.LinearGradient
 * JD-Core Version:    0.6.2
 */