/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLTitleElement;
/*    */ 
/*    */ public class HTMLTitleElementImpl extends HTMLElementImpl
/*    */   implements HTMLTitleElement
/*    */ {
/*    */   HTMLTitleElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLTitleElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLTitleElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getText()
/*    */   {
/* 19 */     return getTextImpl(getPeer());
/*    */   }
/*    */   static native String getTextImpl(long paramLong);
/*    */ 
/*    */   public void setText(String value) {
/* 24 */     setTextImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setTextImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLTitleElementImpl
 * JD-Core Version:    0.6.2
 */