/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLModElement;
/*    */ 
/*    */ public class HTMLModElementImpl extends HTMLElementImpl
/*    */   implements HTMLModElement
/*    */ {
/*    */   HTMLModElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLModElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLModElement)create(peer, contextPeer, rootPeer);
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
/*    */   static native void setCiteImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getDateTime() {
/* 29 */     return getDateTimeImpl(getPeer());
/*    */   }
/*    */   static native String getDateTimeImpl(long paramLong);
/*    */ 
/*    */   public void setDateTime(String value) {
/* 34 */     setDateTimeImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setDateTimeImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLModElementImpl
 * JD-Core Version:    0.6.2
 */