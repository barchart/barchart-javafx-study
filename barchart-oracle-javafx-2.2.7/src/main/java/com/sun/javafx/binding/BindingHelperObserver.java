/*    */ package com.sun.javafx.binding;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.Observable;
/*    */ import javafx.beans.binding.Binding;
/*    */ 
/*    */ public class BindingHelperObserver
/*    */   implements InvalidationListener
/*    */ {
/*    */   private final WeakReference<Binding<?>> ref;
/*    */ 
/*    */   public BindingHelperObserver(Binding<?> paramBinding)
/*    */   {
/* 59 */     if (paramBinding == null) {
/* 60 */       throw new NullPointerException("Binding has to be specified.");
/*    */     }
/* 62 */     this.ref = new WeakReference(paramBinding);
/*    */   }
/*    */ 
/*    */   public void invalidated(Observable paramObservable)
/*    */   {
/* 67 */     Binding localBinding = (Binding)this.ref.get();
/* 68 */     if (localBinding == null)
/* 69 */       paramObservable.removeListener(this);
/*    */     else
/* 71 */       localBinding.invalidate();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.BindingHelperObserver
 * JD-Core Version:    0.6.2
 */