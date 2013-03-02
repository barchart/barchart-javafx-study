/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLTableCaptionElement;
/*    */ 
/*    */ public class HTMLTableCaptionElementImpl extends HTMLElementImpl
/*    */   implements HTMLTableCaptionElement
/*    */ {
/*    */   HTMLTableCaptionElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLTableCaptionElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLTableCaptionElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getAlign()
/*    */   {
/* 19 */     return getAlignImpl(getPeer());
/*    */   }
/*    */   static native String getAlignImpl(long paramLong);
/*    */ 
/*    */   public void setAlign(String value) {
/* 24 */     setAlignImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setAlignImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLTableCaptionElementImpl
 * JD-Core Version:    0.6.2
 */