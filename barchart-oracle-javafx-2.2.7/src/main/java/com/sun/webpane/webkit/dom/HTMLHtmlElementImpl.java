/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLHtmlElement;
/*    */ 
/*    */ public class HTMLHtmlElementImpl extends HTMLElementImpl
/*    */   implements HTMLHtmlElement
/*    */ {
/*    */   HTMLHtmlElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLHtmlElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLHtmlElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getVersion()
/*    */   {
/* 19 */     return getVersionImpl(getPeer());
/*    */   }
/*    */   static native String getVersionImpl(long paramLong);
/*    */ 
/*    */   public void setVersion(String value) {
/* 24 */     setVersionImpl(getPeer(), value);
/*    */   }
/*    */   static native void setVersionImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getManifest() {
/* 29 */     return getManifestImpl(getPeer());
/*    */   }
/*    */   static native String getManifestImpl(long paramLong);
/*    */ 
/*    */   public void setManifest(String value) {
/* 34 */     setManifestImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setManifestImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLHtmlElementImpl
 * JD-Core Version:    0.6.2
 */