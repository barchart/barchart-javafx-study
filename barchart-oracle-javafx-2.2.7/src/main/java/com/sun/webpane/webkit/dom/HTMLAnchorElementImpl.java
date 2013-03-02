/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.html.HTMLAnchorElement;
/*     */ 
/*     */ public class HTMLAnchorElementImpl extends HTMLElementImpl
/*     */   implements HTMLAnchorElement
/*     */ {
/*     */   HTMLAnchorElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*   9 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLAnchorElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  13 */     return (HTMLAnchorElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getCharset()
/*     */   {
/*  19 */     return getCharsetImpl(getPeer());
/*     */   }
/*     */   static native String getCharsetImpl(long paramLong);
/*     */ 
/*     */   public void setCharset(String value) {
/*  24 */     setCharsetImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCharsetImpl(long paramLong, String paramString);
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
/*     */   public String getHreflang() {
/*  49 */     return getHreflangImpl(getPeer());
/*     */   }
/*     */   static native String getHreflangImpl(long paramLong);
/*     */ 
/*     */   public void setHreflang(String value) {
/*  54 */     setHreflangImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHreflangImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getName() {
/*  59 */     return getNameImpl(getPeer());
/*     */   }
/*     */   static native String getNameImpl(long paramLong);
/*     */ 
/*     */   public void setName(String value) {
/*  64 */     setNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getPing() {
/*  69 */     return getPingImpl(getPeer());
/*     */   }
/*     */   static native String getPingImpl(long paramLong);
/*     */ 
/*     */   public void setPing(String value) {
/*  74 */     setPingImpl(getPeer(), value);
/*     */   }
/*     */   static native void setPingImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getRel() {
/*  79 */     return getRelImpl(getPeer());
/*     */   }
/*     */   static native String getRelImpl(long paramLong);
/*     */ 
/*     */   public void setRel(String value) {
/*  84 */     setRelImpl(getPeer(), value);
/*     */   }
/*     */   static native void setRelImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getRev() {
/*  89 */     return getRevImpl(getPeer());
/*     */   }
/*     */   static native String getRevImpl(long paramLong);
/*     */ 
/*     */   public void setRev(String value) {
/*  94 */     setRevImpl(getPeer(), value);
/*     */   }
/*     */   static native void setRevImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getShape() {
/*  99 */     return getShapeImpl(getPeer());
/*     */   }
/*     */   static native String getShapeImpl(long paramLong);
/*     */ 
/*     */   public void setShape(String value) {
/* 104 */     setShapeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setShapeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getTarget() {
/* 109 */     return getTargetImpl(getPeer());
/*     */   }
/*     */   static native String getTargetImpl(long paramLong);
/*     */ 
/*     */   public void setTarget(String value) {
/* 114 */     setTargetImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTargetImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getType() {
/* 119 */     return getTypeImpl(getPeer());
/*     */   }
/*     */   static native String getTypeImpl(long paramLong);
/*     */ 
/*     */   public void setType(String value) {
/* 124 */     setTypeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTypeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHash() {
/* 129 */     return getHashImpl(getPeer());
/*     */   }
/*     */   static native String getHashImpl(long paramLong);
/*     */ 
/*     */   public void setHash(String value) {
/* 134 */     setHashImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHashImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHost() {
/* 139 */     return getHostImpl(getPeer());
/*     */   }
/*     */   static native String getHostImpl(long paramLong);
/*     */ 
/*     */   public void setHost(String value) {
/* 144 */     setHostImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHostImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHostname() {
/* 149 */     return getHostnameImpl(getPeer());
/*     */   }
/*     */   static native String getHostnameImpl(long paramLong);
/*     */ 
/*     */   public void setHostname(String value) {
/* 154 */     setHostnameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHostnameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getPathname() {
/* 159 */     return getPathnameImpl(getPeer());
/*     */   }
/*     */   static native String getPathnameImpl(long paramLong);
/*     */ 
/*     */   public void setPathname(String value) {
/* 164 */     setPathnameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setPathnameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getPort() {
/* 169 */     return getPortImpl(getPeer());
/*     */   }
/*     */   static native String getPortImpl(long paramLong);
/*     */ 
/*     */   public void setPort(String value) {
/* 174 */     setPortImpl(getPeer(), value);
/*     */   }
/*     */   static native void setPortImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getProtocol() {
/* 179 */     return getProtocolImpl(getPeer());
/*     */   }
/*     */   static native String getProtocolImpl(long paramLong);
/*     */ 
/*     */   public void setProtocol(String value) {
/* 184 */     setProtocolImpl(getPeer(), value);
/*     */   }
/*     */   static native void setProtocolImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getSearch() {
/* 189 */     return getSearchImpl(getPeer());
/*     */   }
/*     */   static native String getSearchImpl(long paramLong);
/*     */ 
/*     */   public void setSearch(String value) {
/* 194 */     setSearchImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSearchImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getOrigin() {
/* 199 */     return getOriginImpl(getPeer());
/*     */   }
/*     */   static native String getOriginImpl(long paramLong);
/*     */ 
/*     */   public String getText() {
/* 204 */     return getTextImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native String getTextImpl(long paramLong);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLAnchorElementImpl
 * JD-Core Version:    0.6.2
 */