/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.html.HTMLLinkElement;
/*     */ import org.w3c.dom.stylesheets.StyleSheet;
/*     */ 
/*     */ public class HTMLLinkElementImpl extends HTMLElementImpl
/*     */   implements HTMLLinkElement
/*     */ {
/*     */   HTMLLinkElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  10 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLLinkElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  14 */     return (HTMLLinkElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public boolean getDisabled()
/*     */   {
/*  20 */     return getDisabledImpl(getPeer());
/*     */   }
/*     */   static native boolean getDisabledImpl(long paramLong);
/*     */ 
/*     */   public void setDisabled(boolean value) {
/*  25 */     setDisabledImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDisabledImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getCharset() {
/*  30 */     return getCharsetImpl(getPeer());
/*     */   }
/*     */   static native String getCharsetImpl(long paramLong);
/*     */ 
/*     */   public void setCharset(String value) {
/*  35 */     setCharsetImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCharsetImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHref() {
/*  40 */     return getHrefImpl(getPeer());
/*     */   }
/*     */   static native String getHrefImpl(long paramLong);
/*     */ 
/*     */   public void setHref(String value) {
/*  45 */     setHrefImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHrefImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHreflang() {
/*  50 */     return getHreflangImpl(getPeer());
/*     */   }
/*     */   static native String getHreflangImpl(long paramLong);
/*     */ 
/*     */   public void setHreflang(String value) {
/*  55 */     setHreflangImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHreflangImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getMedia() {
/*  60 */     return getMediaImpl(getPeer());
/*     */   }
/*     */   static native String getMediaImpl(long paramLong);
/*     */ 
/*     */   public void setMedia(String value) {
/*  65 */     setMediaImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMediaImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getRel() {
/*  70 */     return getRelImpl(getPeer());
/*     */   }
/*     */   static native String getRelImpl(long paramLong);
/*     */ 
/*     */   public void setRel(String value) {
/*  75 */     setRelImpl(getPeer(), value);
/*     */   }
/*     */   static native void setRelImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getRev() {
/*  80 */     return getRevImpl(getPeer());
/*     */   }
/*     */   static native String getRevImpl(long paramLong);
/*     */ 
/*     */   public void setRev(String value) {
/*  85 */     setRevImpl(getPeer(), value);
/*     */   }
/*     */   static native void setRevImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getTarget() {
/*  90 */     return getTargetImpl(getPeer());
/*     */   }
/*     */   static native String getTargetImpl(long paramLong);
/*     */ 
/*     */   public void setTarget(String value) {
/*  95 */     setTargetImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTargetImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getType() {
/* 100 */     return getTypeImpl(getPeer());
/*     */   }
/*     */   static native String getTypeImpl(long paramLong);
/*     */ 
/*     */   public void setType(String value) {
/* 105 */     setTypeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTypeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public StyleSheet getSheet() {
/* 110 */     return StyleSheetImpl.getImpl(getSheetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getSheetImpl(long paramLong);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLLinkElementImpl
 * JD-Core Version:    0.6.2
 */