/*    */ package com.sun.javafx.binding;
/*    */ 
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.binding.StringExpression;
/*    */ import javafx.beans.value.ChangeListener;
/*    */ 
/*    */ public class StringConstant extends StringExpression
/*    */ {
/*    */   private final String value;
/*    */ 
/*    */   private StringConstant(String paramString)
/*    */   {
/* 57 */     this.value = paramString;
/*    */   }
/*    */ 
/*    */   public static StringConstant valueOf(String paramString) {
/* 61 */     return new StringConstant(paramString);
/*    */   }
/*    */ 
/*    */   public String get()
/*    */   {
/* 66 */     return this.value;
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 71 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void addListener(InvalidationListener paramInvalidationListener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void addListener(ChangeListener<? super String> paramChangeListener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void removeListener(InvalidationListener paramInvalidationListener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void removeListener(ChangeListener<? super String> paramChangeListener)
/*    */   {
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.StringConstant
 * JD-Core Version:    0.6.2
 */