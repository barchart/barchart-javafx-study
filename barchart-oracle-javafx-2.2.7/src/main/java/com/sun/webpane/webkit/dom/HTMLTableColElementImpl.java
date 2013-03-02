/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLTableColElement;
/*    */ 
/*    */ public class HTMLTableColElementImpl extends HTMLElementImpl
/*    */   implements HTMLTableColElement
/*    */ {
/*    */   HTMLTableColElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLTableColElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLTableColElement)create(peer, contextPeer, rootPeer);
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
/*    */   static native void setAlignImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getCh() {
/* 29 */     return getChImpl(getPeer());
/*    */   }
/*    */   static native String getChImpl(long paramLong);
/*    */ 
/*    */   public void setCh(String value) {
/* 34 */     setChImpl(getPeer(), value);
/*    */   }
/*    */   static native void setChImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getChOff() {
/* 39 */     return getChOffImpl(getPeer());
/*    */   }
/*    */   static native String getChOffImpl(long paramLong);
/*    */ 
/*    */   public void setChOff(String value) {
/* 44 */     setChOffImpl(getPeer(), value);
/*    */   }
/*    */   static native void setChOffImpl(long paramLong, String paramString);
/*    */ 
/*    */   public int getSpan() {
/* 49 */     return getSpanImpl(getPeer());
/*    */   }
/*    */   static native int getSpanImpl(long paramLong);
/*    */ 
/*    */   public void setSpan(int value) {
/* 54 */     setSpanImpl(getPeer(), value);
/*    */   }
/*    */   static native void setSpanImpl(long paramLong, int paramInt);
/*    */ 
/*    */   public String getVAlign() {
/* 59 */     return getVAlignImpl(getPeer());
/*    */   }
/*    */   static native String getVAlignImpl(long paramLong);
/*    */ 
/*    */   public void setVAlign(String value) {
/* 64 */     setVAlignImpl(getPeer(), value);
/*    */   }
/*    */   static native void setVAlignImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getWidth() {
/* 69 */     return getWidthImpl(getPeer());
/*    */   }
/*    */   static native String getWidthImpl(long paramLong);
/*    */ 
/*    */   public void setWidth(String value) {
/* 74 */     setWidthImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setWidthImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLTableColElementImpl
 * JD-Core Version:    0.6.2
 */