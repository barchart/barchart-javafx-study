/*    */ package com.sun.javafx.fxml;
/*    */ 
/*    */ public class PropertyNotFoundException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */ 
/*    */   public PropertyNotFoundException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PropertyNotFoundException(String paramString)
/*    */   {
/* 21 */     super(paramString);
/*    */   }
/*    */ 
/*    */   public PropertyNotFoundException(Throwable paramThrowable) {
/* 25 */     super(paramThrowable);
/*    */   }
/*    */ 
/*    */   public PropertyNotFoundException(String paramString, Throwable paramThrowable) {
/* 29 */     super(paramString, paramThrowable);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.PropertyNotFoundException
 * JD-Core Version:    0.6.2
 */