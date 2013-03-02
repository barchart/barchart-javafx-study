/*    */ package com.sun.javafx.scene.control;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import javafx.collections.ListChangeListener;
/*    */ import javafx.collections.ListChangeListener.Change;
/*    */ import javafx.collections.ObservableList;
/*    */ 
/*    */ public class WeakListChangeListener<T>
/*    */   implements ListChangeListener<T>
/*    */ {
/*    */   private final WeakReference<ListChangeListener<T>> ref;
/*    */ 
/*    */   public WeakListChangeListener(ListChangeListener<T> paramListChangeListener)
/*    */   {
/* 62 */     if (paramListChangeListener == null) {
/* 63 */       throw new NullPointerException("Listener must be specified.");
/*    */     }
/* 65 */     this.ref = new WeakReference(paramListChangeListener);
/*    */   }
/*    */ 
/*    */   public void onChanged(ListChangeListener.Change<? extends T> paramChange) {
/* 69 */     ListChangeListener localListChangeListener = (ListChangeListener)this.ref.get();
/* 70 */     if (localListChangeListener != null) {
/* 71 */       localListChangeListener.onChanged(paramChange);
/*    */     }
/*    */     else
/*    */     {
/* 76 */       paramChange.getList().removeListener(this);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.WeakListChangeListener
 * JD-Core Version:    0.6.2
 */