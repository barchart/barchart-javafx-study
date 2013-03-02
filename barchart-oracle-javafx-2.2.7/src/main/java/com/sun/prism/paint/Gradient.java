/*    */ package com.sun.prism.paint;
/*    */ 
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class Gradient extends Paint
/*    */ {
/*    */   public static final int PAD = 0;
/*    */   public static final int REFLECT = 1;
/*    */   public static final int REPEAT = 2;
/*    */   private final int numStops;
/*    */   private final List<Stop> stops;
/*    */   private final BaseTransform gradientTransform;
/*    */   private final int spreadMethod;
/* 22 */   private long cacheOffset = -1L;
/*    */ 
/*    */   protected Gradient(Paint.Type paramType, BaseTransform paramBaseTransform, boolean paramBoolean, int paramInt, List<Stop> paramList)
/*    */   {
/* 30 */     super(paramType, paramBoolean);
/* 31 */     if (paramBaseTransform != null)
/* 32 */       this.gradientTransform = paramBaseTransform.copy();
/*    */     else {
/* 34 */       this.gradientTransform = BaseTransform.IDENTITY_TRANSFORM;
/*    */     }
/* 36 */     this.spreadMethod = paramInt;
/* 37 */     this.numStops = paramList.size();
/* 38 */     this.stops = paramList;
/*    */   }
/*    */ 
/*    */   public int getSpreadMethod()
/*    */   {
/* 43 */     return this.spreadMethod;
/*    */   }
/*    */ 
/*    */   public BaseTransform getGradientTransformNoClone() {
/* 47 */     return this.gradientTransform;
/*    */   }
/*    */ 
/*    */   public int getNumStops() {
/* 51 */     return this.numStops;
/*    */   }
/*    */ 
/*    */   public List<Stop> getStops() {
/* 55 */     return this.stops;
/*    */   }
/*    */ 
/*    */   public void setGradientOffset(long paramLong) {
/* 59 */     this.cacheOffset = paramLong;
/*    */   }
/*    */ 
/*    */   public long getGradientOffset() {
/* 63 */     return this.cacheOffset;
/*    */   }
/*    */ 
/*    */   public boolean isOpaque()
/*    */   {
/* 68 */     for (int i = 0; i < this.numStops; i++) {
/* 69 */       if (!((Stop)this.stops.get(i)).getColor().isOpaque()) {
/* 70 */         return false;
/*    */       }
/*    */     }
/* 73 */     return true;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.paint.Gradient
 * JD-Core Version:    0.6.2
 */