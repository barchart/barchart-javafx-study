/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.views.AbstractView;
/*    */ 
/*    */ public class TouchEventImpl extends UIEventImpl
/*    */ {
/*    */   TouchEventImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static TouchEventImpl getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (TouchEventImpl)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public TouchListImpl getTouches()
/*    */   {
/* 19 */     return TouchListImpl.getImpl(getTouchesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getTouchesImpl(long paramLong);
/*    */ 
/*    */   public TouchListImpl getTargetTouches() {
/* 24 */     return TouchListImpl.getImpl(getTargetTouchesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getTargetTouchesImpl(long paramLong);
/*    */ 
/*    */   public TouchListImpl getChangedTouches() {
/* 29 */     return TouchListImpl.getImpl(getChangedTouchesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getChangedTouchesImpl(long paramLong);
/*    */ 
/*    */   public boolean getCtrlKey() {
/* 34 */     return getCtrlKeyImpl(getPeer());
/*    */   }
/*    */   static native boolean getCtrlKeyImpl(long paramLong);
/*    */ 
/*    */   public boolean getShiftKey() {
/* 39 */     return getShiftKeyImpl(getPeer());
/*    */   }
/*    */   static native boolean getShiftKeyImpl(long paramLong);
/*    */ 
/*    */   public boolean getAltKey() {
/* 44 */     return getAltKeyImpl(getPeer());
/*    */   }
/*    */   static native boolean getAltKeyImpl(long paramLong);
/*    */ 
/*    */   public boolean getMetaKey() {
/* 49 */     return getMetaKeyImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native boolean getMetaKeyImpl(long paramLong);
/*    */ 
/*    */   public void initTouchEvent(TouchListImpl touches, TouchListImpl targetTouches, TouchListImpl changedTouches, String type, AbstractView view, int screenX, int screenY, int clientX, int clientY, boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey)
/*    */   {
/* 69 */     initTouchEventImpl(getPeer(), TouchListImpl.getPeer(touches), TouchListImpl.getPeer(targetTouches), TouchListImpl.getPeer(changedTouches), type, DOMWindowImpl.getPeer(view), screenX, screenY, clientX, clientY, ctrlKey, altKey, shiftKey, metaKey);
/*    */   }
/*    */ 
/*    */   static native void initTouchEventImpl(long paramLong1, long paramLong2, long paramLong3, long paramLong4, String paramString, long paramLong5, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.TouchEventImpl
 * JD-Core Version:    0.6.2
 */