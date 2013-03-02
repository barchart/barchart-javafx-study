/*    */ package com.sun.javafx.fxml.expression;
/*    */ 
/*    */ public abstract class UnaryExpression extends Expression
/*    */ {
/*    */   private Expression operand;
/*    */ 
/*    */   public UnaryExpression(Expression paramExpression)
/*    */   {
/* 15 */     if (paramExpression == null) {
/* 16 */       throw new NullPointerException();
/*    */     }
/*    */ 
/* 19 */     this.operand = paramExpression;
/*    */ 
/* 21 */     paramExpression.setParent(this);
/*    */   }
/*    */ 
/*    */   public Expression getOperand() {
/* 25 */     return this.operand;
/*    */   }
/*    */ 
/*    */   public abstract String getOperator();
/*    */ 
/*    */   public boolean isDefined()
/*    */   {
/* 32 */     return this.operand.isDefined();
/*    */   }
/*    */ 
/*    */   protected void registerChangeListeners()
/*    */   {
/* 37 */     this.operand.registerChangeListeners();
/*    */   }
/*    */ 
/*    */   protected void unregisterChangeListeners()
/*    */   {
/* 42 */     this.operand.unregisterChangeListeners();
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 47 */     return "(" + getOperator() + this.operand + ")";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.expression.UnaryExpression
 * JD-Core Version:    0.6.2
 */