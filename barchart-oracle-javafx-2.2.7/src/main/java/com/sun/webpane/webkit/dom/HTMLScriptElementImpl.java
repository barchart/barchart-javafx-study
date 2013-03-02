/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLScriptElement;
/*    */ 
/*    */ public class HTMLScriptElementImpl extends HTMLElementImpl
/*    */   implements HTMLScriptElement
/*    */ {
/*    */   HTMLScriptElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLScriptElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLScriptElement)create(peer, contextPeer, rootPeer);
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
/*    */   static native void setTextImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getHtmlFor() {
/* 29 */     return getHtmlForImpl(getPeer());
/*    */   }
/*    */   static native String getHtmlForImpl(long paramLong);
/*    */ 
/*    */   public void setHtmlFor(String value) {
/* 34 */     setHtmlForImpl(getPeer(), value);
/*    */   }
/*    */   static native void setHtmlForImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getEvent() {
/* 39 */     return getEventImpl(getPeer());
/*    */   }
/*    */   static native String getEventImpl(long paramLong);
/*    */ 
/*    */   public void setEvent(String value) {
/* 44 */     setEventImpl(getPeer(), value);
/*    */   }
/*    */   static native void setEventImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getCharset() {
/* 49 */     return getCharsetImpl(getPeer());
/*    */   }
/*    */   static native String getCharsetImpl(long paramLong);
/*    */ 
/*    */   public void setCharset(String value) {
/* 54 */     setCharsetImpl(getPeer(), value);
/*    */   }
/*    */   static native void setCharsetImpl(long paramLong, String paramString);
/*    */ 
/*    */   public boolean getAsync() {
/* 59 */     return getAsyncImpl(getPeer());
/*    */   }
/*    */   static native boolean getAsyncImpl(long paramLong);
/*    */ 
/*    */   public void setAsync(boolean value) {
/* 64 */     setAsyncImpl(getPeer(), value);
/*    */   }
/*    */   static native void setAsyncImpl(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   public boolean getDefer() {
/* 69 */     return getDeferImpl(getPeer());
/*    */   }
/*    */   static native boolean getDeferImpl(long paramLong);
/*    */ 
/*    */   public void setDefer(boolean value) {
/* 74 */     setDeferImpl(getPeer(), value);
/*    */   }
/*    */   static native void setDeferImpl(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   public String getSrc() {
/* 79 */     return getSrcImpl(getPeer());
/*    */   }
/*    */   static native String getSrcImpl(long paramLong);
/*    */ 
/*    */   public void setSrc(String value) {
/* 84 */     setSrcImpl(getPeer(), value);
/*    */   }
/*    */   static native void setSrcImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getType() {
/* 89 */     return getTypeImpl(getPeer());
/*    */   }
/*    */   static native String getTypeImpl(long paramLong);
/*    */ 
/*    */   public void setType(String value) {
/* 94 */     setTypeImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setTypeImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLScriptElementImpl
 * JD-Core Version:    0.6.2
 */