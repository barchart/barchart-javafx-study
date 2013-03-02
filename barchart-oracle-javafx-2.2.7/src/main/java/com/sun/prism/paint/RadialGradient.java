/*    */ package com.sun.prism.paint;
/*    */ 
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import java.util.List;
/*    */ 
/*    */ public final class RadialGradient extends Gradient
/*    */ {
/*    */   private final float centerX;
/*    */   private final float centerY;
/*    */   private final float focusAngle;
/*    */   private final float focusDistance;
/*    */   private final float radius;
/*    */ 
/*    */   public RadialGradient(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, BaseTransform paramBaseTransform, boolean paramBoolean, int paramInt, List<Stop> paramList)
/*    */   {
/* 28 */     super(Paint.Type.RADIAL_GRADIENT, paramBaseTransform, paramBoolean, paramInt, paramList);
/* 29 */     this.centerX = paramFloat1;
/* 30 */     this.centerY = paramFloat2;
/* 31 */     this.focusAngle = paramFloat3;
/* 32 */     this.focusDistance = paramFloat4;
/* 33 */     this.radius = paramFloat5;
/*    */   }
/*    */ 
/*    */   public float getCenterX() {
/* 37 */     return this.centerX;
/*    */   }
/*    */ 
/*    */   public float getCenterY() {
/* 41 */     return this.centerY;
/*    */   }
/*    */ 
/*    */   public float getFocusAngle() {
/* 45 */     return this.focusAngle;
/*    */   }
/*    */ 
/*    */   public float getFocusDistance() {
/* 49 */     return this.focusDistance;
/*    */   }
/*    */ 
/*    */   public float getRadius() {
/* 53 */     return this.radius;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 59 */     return "RadialGradient: FocusAngle: " + this.focusAngle + " FocusDistance: " + this.focusDistance + " CenterX: " + this.centerX + " CenterY " + this.centerY + " Radius: " + this.radius + "stops:" + getStops();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.paint.RadialGradient
 * JD-Core Version:    0.6.2
 */