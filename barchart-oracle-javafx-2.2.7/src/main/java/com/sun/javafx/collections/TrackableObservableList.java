/*    */ package com.sun.javafx.collections;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javafx.collections.ListChangeListener.Change;
/*    */ import javafx.collections.ObservableList;
/*    */ 
/*    */ public abstract class TrackableObservableList<T> extends ObservableListWrapper<T>
/*    */   implements ObservableList<T>
/*    */ {
/*    */   public TrackableObservableList(List<T> paramList)
/*    */   {
/* 39 */     super(paramList);
/*    */   }
/*    */ 
/*    */   public TrackableObservableList() {
/* 43 */     super(new ArrayList());
/*    */   }
/*    */ 
/*    */   protected abstract void onChanged(ListChangeListener.Change<T> paramChange);
/*    */ 
/*    */   protected void callObservers(ListChangeListener.Change<T> paramChange)
/*    */   {
/* 50 */     onChanged(paramChange);
/* 51 */     super.callObservers(paramChange);
/*    */   }
/*    */ 
/*    */   protected boolean hasObserver()
/*    */   {
/* 56 */     return true;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.TrackableObservableList
 * JD-Core Version:    0.6.2
 */