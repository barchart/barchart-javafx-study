/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLBaseElement;
/*    */ 
/*    */ public class HTMLBaseElementImpl extends HTMLElementImpl
/*    */   implements HTMLBaseElement
/*    */ {
/*    */   HTMLBaseElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLBaseElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLBaseElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getHref()
/*    */   {
/* 19 */     return getHrefImpl(getPeer());
/*    */   }
/*    */   static native String getHrefImpl(long paramLong);
/*    */ 
/*    */   public void setHref(String value) {
/* 24 */     setHrefImpl(getPeer(), value);
/*    */   }
/*    */   static native void setHrefImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getTarget() {
/* 29 */     return getTargetImpl(getPeer());
/*    */   }
/*    */   static native String getTargetImpl(long paramLong);
/*    */ 
/*    */   public void setTarget(String value) {
/* 34 */     setTargetImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setTargetImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLBaseElementImpl
 * JD-Core Version:    0.6.2
 */