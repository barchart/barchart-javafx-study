/*    */ package com.sun.javafx.fxml.expression;
/*    */ 
/*    */ public class LiteralExpression extends Expression
/*    */ {
/*    */   private Object value;
/*    */ 
/*    */   public LiteralExpression(Object paramObject)
/*    */   {
/* 14 */     this.value = paramObject;
/*    */   }
/*    */ 
/*    */   protected Object evaluate()
/*    */   {
/* 19 */     return this.value;
/*    */   }
/*    */ 
/*    */   public boolean isDefined()
/*    */   {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   protected void registerChangeListeners()
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void unregisterChangeListeners()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 39 */     return this.value == null ? "null" : this.value.toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.expression.LiteralExpression
 * JD-Core Version:    0.6.2
 */