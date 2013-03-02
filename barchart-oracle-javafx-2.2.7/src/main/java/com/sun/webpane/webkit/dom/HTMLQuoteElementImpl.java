/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLQuoteElement;
/*    */ 
/*    */ public class HTMLQuoteElementImpl extends HTMLElementImpl
/*    */   implements HTMLQuoteElement
/*    */ {
/*    */   HTMLQuoteElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLQuoteElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLQuoteElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getCite()
/*    */   {
/* 19 */     return getCiteImpl(getPeer());
/*    */   }
/*    */   static native String getCiteImpl(long paramLong);
/*    */ 
/*    */   public void setCite(String value) {
/* 24 */     setCiteImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setCiteImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLQuoteElementImpl
 * JD-Core Version:    0.6.2
 */