/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLStyleElement;
/*    */ import org.w3c.dom.stylesheets.StyleSheet;
/*    */ 
/*    */ public class HTMLStyleElementImpl extends HTMLElementImpl
/*    */   implements HTMLStyleElement
/*    */ {
/*    */   HTMLStyleElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLStyleElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (HTMLStyleElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public boolean getDisabled()
/*    */   {
/* 20 */     return getDisabledImpl(getPeer());
/*    */   }
/*    */   static native boolean getDisabledImpl(long paramLong);
/*    */ 
/*    */   public void setDisabled(boolean value) {
/* 25 */     setDisabledImpl(getPeer(), value);
/*    */   }
/*    */   static native void setDisabledImpl(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   public String getMedia() {
/* 30 */     return getMediaImpl(getPeer());
/*    */   }
/*    */   static native String getMediaImpl(long paramLong);
/*    */ 
/*    */   public void setMedia(String value) {
/* 35 */     setMediaImpl(getPeer(), value);
/*    */   }
/*    */   static native void setMediaImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getType() {
/* 40 */     return getTypeImpl(getPeer());
/*    */   }
/*    */   static native String getTypeImpl(long paramLong);
/*    */ 
/*    */   public void setType(String value) {
/* 45 */     setTypeImpl(getPeer(), value);
/*    */   }
/*    */   static native void setTypeImpl(long paramLong, String paramString);
/*    */ 
/*    */   public StyleSheet getSheet() {
/* 50 */     return StyleSheetImpl.getImpl(getSheetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long getSheetImpl(long paramLong);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLStyleElementImpl
 * JD-Core Version:    0.6.2
 */