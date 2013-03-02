/*    */ package com.sun.javafx.beans.event;
/*    */ 
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.WeakInvalidationListener;
/*    */ 
/*    */ public abstract class AbstractNotifyListener
/*    */   implements InvalidationListener
/*    */ {
/* 60 */   private final WeakInvalidationListener weakListener = new WeakInvalidationListener(this);
/*    */ 
/*    */   public InvalidationListener getWeakListener() {
/* 63 */     return this.weakListener;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.beans.event.AbstractNotifyListener
 * JD-Core Version:    0.6.2
 */