/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLPreElement;
/*    */ 
/*    */ public class HTMLPreElementImpl extends HTMLElementImpl
/*    */   implements HTMLPreElement
/*    */ {
/*    */   HTMLPreElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLPreElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLPreElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public int getWidth()
/*    */   {
/* 19 */     return getWidthImpl(getPeer());
/*    */   }
/*    */   static native int getWidthImpl(long paramLong);
/*    */ 
/*    */   public void setWidth(int value) {
/* 24 */     setWidthImpl(getPeer(), value);
/*    */   }
/*    */   static native void setWidthImpl(long paramLong, int paramInt);
/*    */ 
/*    */   public boolean getWrap() {
/* 29 */     return getWrapImpl(getPeer());
/*    */   }
/*    */   static native boolean getWrapImpl(long paramLong);
/*    */ 
/*    */   public void setWrap(boolean value) {
/* 34 */     setWrapImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setWrapImpl(long paramLong, boolean paramBoolean);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLPreElementImpl
 * JD-Core Version:    0.6.2
 */