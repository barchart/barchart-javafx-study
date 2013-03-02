/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLMetaElement;
/*    */ 
/*    */ public class HTMLMetaElementImpl extends HTMLElementImpl
/*    */   implements HTMLMetaElement
/*    */ {
/*    */   HTMLMetaElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLMetaElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLMetaElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getContent()
/*    */   {
/* 19 */     return getContentImpl(getPeer());
/*    */   }
/*    */   static native String getContentImpl(long paramLong);
/*    */ 
/*    */   public void setContent(String value) {
/* 24 */     setContentImpl(getPeer(), value);
/*    */   }
/*    */   static native void setContentImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getHttpEquiv() {
/* 29 */     return getHttpEquivImpl(getPeer());
/*    */   }
/*    */   static native String getHttpEquivImpl(long paramLong);
/*    */ 
/*    */   public void setHttpEquiv(String value) {
/* 34 */     setHttpEquivImpl(getPeer(), value);
/*    */   }
/*    */   static native void setHttpEquivImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getName() {
/* 39 */     return getNameImpl(getPeer());
/*    */   }
/*    */   static native String getNameImpl(long paramLong);
/*    */ 
/*    */   public void setName(String value) {
/* 44 */     setNameImpl(getPeer(), value);
/*    */   }
/*    */   static native void setNameImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getScheme() {
/* 49 */     return getSchemeImpl(getPeer());
/*    */   }
/*    */   static native String getSchemeImpl(long paramLong);
/*    */ 
/*    */   public void setScheme(String value) {
/* 54 */     setSchemeImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setSchemeImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLMetaElementImpl
 * JD-Core Version:    0.6.2
 */