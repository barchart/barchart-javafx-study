/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.html.HTMLAreaElement;
/*     */ 
/*     */ public class HTMLAreaElementImpl extends HTMLElementImpl
/*     */   implements HTMLAreaElement
/*     */ {
/*     */   HTMLAreaElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*   9 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLAreaElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  13 */     return (HTMLAreaElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getAlt()
/*     */   {
/*  19 */     return getAltImpl(getPeer());
/*     */   }
/*     */   static native String getAltImpl(long paramLong);
/*     */ 
/*     */   public void setAlt(String value) {
/*  24 */     setAltImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAltImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getCoords() {
/*  29 */     return getCoordsImpl(getPeer());
/*     */   }
/*     */   static native String getCoordsImpl(long paramLong);
/*     */ 
/*     */   public void setCoords(String value) {
/*  34 */     setCoordsImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCoordsImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHref() {
/*  39 */     return getHrefImpl(getPeer());
/*     */   }
/*     */   static native String getHrefImpl(long paramLong);
/*     */ 
/*     */   public void setHref(String value) {
/*  44 */     setHrefImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHrefImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getNoHref() {
/*  49 */     return getNoHrefImpl(getPeer());
/*     */   }
/*     */   static native boolean getNoHrefImpl(long paramLong);
/*     */ 
/*     */   public void setNoHref(boolean value) {
/*  54 */     setNoHrefImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNoHrefImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getPing() {
/*  59 */     return getPingImpl(getPeer());
/*     */   }
/*     */   static native String getPingImpl(long paramLong);
/*     */ 
/*     */   public void setPing(String value) {
/*  64 */     setPingImpl(getPeer(), value);
/*     */   }
/*     */   static native void setPingImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getShape() {
/*  69 */     return getShapeImpl(getPeer());
/*     */   }
/*     */   static native String getShapeImpl(long paramLong);
/*     */ 
/*     */   public void setShape(String value) {
/*  74 */     setShapeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setShapeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getTarget() {
/*  79 */     return getTargetImpl(getPeer());
/*     */   }
/*     */   static native String getTargetImpl(long paramLong);
/*     */ 
/*     */   public void setTarget(String value) {
/*  84 */     setTargetImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTargetImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHash() {
/*  89 */     return getHashImpl(getPeer());
/*     */   }
/*     */   static native String getHashImpl(long paramLong);
/*     */ 
/*     */   public String getHost() {
/*  94 */     return getHostImpl(getPeer());
/*     */   }
/*     */   static native String getHostImpl(long paramLong);
/*     */ 
/*     */   public String getHostname() {
/*  99 */     return getHostnameImpl(getPeer());
/*     */   }
/*     */   static native String getHostnameImpl(long paramLong);
/*     */ 
/*     */   public String getPathname() {
/* 104 */     return getPathnameImpl(getPeer());
/*     */   }
/*     */   static native String getPathnameImpl(long paramLong);
/*     */ 
/*     */   public String getPort() {
/* 109 */     return getPortImpl(getPeer());
/*     */   }
/*     */   static native String getPortImpl(long paramLong);
/*     */ 
/*     */   public String getProtocol() {
/* 114 */     return getProtocolImpl(getPeer());
/*     */   }
/*     */   static native String getProtocolImpl(long paramLong);
/*     */ 
/*     */   public String getSearch() {
/* 119 */     return getSearchImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native String getSearchImpl(long paramLong);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLAreaElementImpl
 * JD-Core Version:    0.6.2
 */