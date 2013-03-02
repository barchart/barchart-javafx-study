/*    */ package com.sun.javafx.css;
/*    */ 
/*    */ import javafx.beans.property.ObjectProperty;
/*    */ import javafx.scene.paint.Paint;
/*    */ 
/*    */ public final class BorderPaint
/*    */ {
/*    */   private ObjectProperty<Paint> top;
/*    */   private ObjectProperty<Paint> right;
/*    */   private ObjectProperty<Paint> bottom;
/*    */   private ObjectProperty<Paint> left;
/*    */ 
/*    */   public final Paint getTop()
/*    */   {
/* 55 */     return this.top == null ? null : (Paint)this.top.get();
/*    */   }
/*    */ 
/*    */   public final Paint getRight()
/*    */   {
/* 60 */     return this.right == null ? null : (Paint)this.right.get();
/*    */   }
/*    */ 
/*    */   public final Paint getBottom()
/*    */   {
/* 65 */     return this.bottom == null ? null : (Paint)this.bottom.get();
/*    */   }
/*    */ 
/*    */   public final Paint getLeft()
/*    */   {
/* 70 */     return this.left == null ? null : (Paint)this.left.get();
/*    */   }
/*    */ 
/*    */   public BorderPaint(Paint paramPaint1, Paint paramPaint2, Paint paramPaint3, Paint paramPaint4)
/*    */   {
/* 75 */     this.top.set(paramPaint1);
/* 76 */     this.right.set(paramPaint2);
/* 77 */     this.bottom.set(paramPaint3);
/* 78 */     this.left.set(paramPaint4);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 82 */     StringBuilder localStringBuilder = new StringBuilder();
/* 83 */     localStringBuilder.append("top: ");
/* 84 */     localStringBuilder.append(getTop());
/* 85 */     localStringBuilder.append(", right: ");
/* 86 */     localStringBuilder.append(getRight());
/* 87 */     localStringBuilder.append(", bottom: ");
/* 88 */     localStringBuilder.append(getBottom());
/* 89 */     localStringBuilder.append(", left: ");
/* 90 */     localStringBuilder.append(getLeft());
/* 91 */     return localStringBuilder.toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.BorderPaint
 * JD-Core Version:    0.6.2
 */