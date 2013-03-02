/*    */ package com.sun.javafx.binding;
/*    */ 
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.value.ChangeListener;
/*    */ import javafx.beans.value.ObservableObjectValue;
/*    */ 
/*    */ public class ObjectConstant<T>
/*    */   implements ObservableObjectValue<T>
/*    */ {
/*    */   private final T value;
/*    */ 
/*    */   private ObjectConstant(T paramT)
/*    */   {
/* 57 */     this.value = paramT;
/*    */   }
/*    */ 
/*    */   public static <T> ObjectConstant<T> valueOf(T paramT) {
/* 61 */     return new ObjectConstant(paramT);
/*    */   }
/*    */ 
/*    */   public T get()
/*    */   {
/* 66 */     return this.value;
/*    */   }
/*    */ 
/*    */   public T getValue()
/*    */   {
/* 71 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void addListener(InvalidationListener paramInvalidationListener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void addListener(ChangeListener<? super T> paramChangeListener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void removeListener(InvalidationListener paramInvalidationListener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void removeListener(ChangeListener<? super T> paramChangeListener)
/*    */   {
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.ObjectConstant
 * JD-Core Version:    0.6.2
 */