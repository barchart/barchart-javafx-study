/*    */ package com.sun.javafx.fxml.expression;
/*    */ 
/*    */ public abstract class BinaryExpression extends Expression
/*    */ {
/*    */   private Expression left;
/*    */   private Expression right;
/*    */ 
/*    */   public BinaryExpression(Expression paramExpression1, Expression paramExpression2)
/*    */   {
/* 16 */     if (paramExpression1 == null) {
/* 17 */       throw new NullPointerException();
/*    */     }
/*    */ 
/* 20 */     if (paramExpression2 == null) {
/* 21 */       throw new NullPointerException();
/*    */     }
/*    */ 
/* 24 */     this.left = paramExpression1;
/* 25 */     paramExpression1.setParent(this);
/*    */ 
/* 27 */     this.right = paramExpression2;
/* 28 */     paramExpression2.setParent(this);
/*    */   }
/*    */ 
/*    */   public Expression getLeft() {
/* 32 */     return this.left;
/*    */   }
/*    */ 
/*    */   public Expression getRight() {
/* 36 */     return this.right;
/*    */   }
/*    */ 
/*    */   public abstract String getOperator();
/*    */ 
/*    */   public boolean isDefined()
/*    */   {
/* 43 */     return (this.left.isDefined()) && (this.right.isDefined());
/*    */   }
/*    */ 
/*    */   protected void registerChangeListeners()
/*    */   {
/* 48 */     this.left.registerChangeListeners();
/* 49 */     this.right.registerChangeListeners();
/*    */   }
/*    */ 
/*    */   protected void unregisterChangeListeners()
/*    */   {
/* 54 */     this.left.unregisterChangeListeners();
/* 55 */     this.right.unregisterChangeListeners();
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 60 */     return "(" + this.left + " " + getOperator() + " " + this.right + ")";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.expression.BinaryExpression
 * JD-Core Version:    0.6.2
 */