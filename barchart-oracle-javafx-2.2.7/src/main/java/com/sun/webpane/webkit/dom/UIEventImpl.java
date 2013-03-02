/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.events.UIEvent;
/*    */ import org.w3c.dom.views.AbstractView;
/*    */ 
/*    */ public class UIEventImpl extends EventImpl
/*    */   implements UIEvent
/*    */ {
/*    */   UIEventImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static UIEvent getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (UIEvent)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public AbstractView getView()
/*    */   {
/* 20 */     return DOMWindowImpl.getImpl(getViewImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getViewImpl(long paramLong);
/*    */ 
/*    */   public int getDetail() {
/* 25 */     return getDetailImpl(getPeer());
/*    */   }
/*    */   static native int getDetailImpl(long paramLong);
/*    */ 
/*    */   public int getKeyCode() {
/* 30 */     return getKeyCodeImpl(getPeer());
/*    */   }
/*    */   static native int getKeyCodeImpl(long paramLong);
/*    */ 
/*    */   public int getCharCode() {
/* 35 */     return getCharCodeImpl(getPeer());
/*    */   }
/*    */   static native int getCharCodeImpl(long paramLong);
/*    */ 
/*    */   public int getLayerX() {
/* 40 */     return getLayerXImpl(getPeer());
/*    */   }
/*    */   static native int getLayerXImpl(long paramLong);
/*    */ 
/*    */   public int getLayerY() {
/* 45 */     return getLayerYImpl(getPeer());
/*    */   }
/*    */   static native int getLayerYImpl(long paramLong);
/*    */ 
/*    */   public int getPageX() {
/* 50 */     return getPageXImpl(getPeer());
/*    */   }
/*    */   static native int getPageXImpl(long paramLong);
/*    */ 
/*    */   public int getPageY() {
/* 55 */     return getPageYImpl(getPeer());
/*    */   }
/*    */   static native int getPageYImpl(long paramLong);
/*    */ 
/*    */   public int getWhich() {
/* 60 */     return getWhichImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native int getWhichImpl(long paramLong);
/*    */ 
/*    */   public void initUIEvent(String type, boolean canBubble, boolean cancelable, AbstractView view, int detail)
/*    */   {
/* 72 */     initUIEventImpl(getPeer(), type, canBubble, cancelable, DOMWindowImpl.getPeer(view), detail);
/*    */   }
/*    */ 
/*    */   static native void initUIEventImpl(long paramLong1, String paramString, boolean paramBoolean1, boolean paramBoolean2, long paramLong2, int paramInt);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.UIEventImpl
 * JD-Core Version:    0.6.2
 */