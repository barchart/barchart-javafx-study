/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.html.HTMLCollection;
/*     */ import org.w3c.dom.html.HTMLDocument;
/*     */ 
/*     */ public class HTMLDocumentImpl extends DocumentImpl
/*     */   implements HTMLDocument
/*     */ {
/*     */   HTMLDocumentImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  11 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLDocument getImpl(long peer, long contextPeer, long rootPeer) {
/*  15 */     return (HTMLDocument)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public HTMLCollection getEmbeds()
/*     */   {
/*  21 */     return HTMLCollectionImpl.getImpl(getEmbedsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getEmbedsImpl(long paramLong);
/*     */ 
/*     */   public HTMLCollection getPlugins() {
/*  26 */     return HTMLCollectionImpl.getImpl(getPluginsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getPluginsImpl(long paramLong);
/*     */ 
/*     */   public HTMLCollection getScripts() {
/*  31 */     return HTMLCollectionImpl.getImpl(getScriptsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getScriptsImpl(long paramLong);
/*     */ 
/*     */   public String getDir() {
/*  36 */     return getDirImpl(getPeer());
/*     */   }
/*     */   static native String getDirImpl(long paramLong);
/*     */ 
/*     */   public void setDir(String value) {
/*  41 */     setDirImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDirImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getDesignMode() {
/*  46 */     return getDesignModeImpl(getPeer());
/*     */   }
/*     */   static native String getDesignModeImpl(long paramLong);
/*     */ 
/*     */   public void setDesignMode(String value) {
/*  51 */     setDesignModeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDesignModeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getCompatMode() {
/*  56 */     return getCompatModeImpl(getPeer());
/*     */   }
/*     */   static native String getCompatModeImpl(long paramLong);
/*     */ 
/*     */   public Element getActiveElement() {
/*  61 */     return ElementImpl.getImpl(getActiveElementImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getActiveElementImpl(long paramLong);
/*     */ 
/*     */   public String getBgColor() {
/*  66 */     return getBgColorImpl(getPeer());
/*     */   }
/*     */   static native String getBgColorImpl(long paramLong);
/*     */ 
/*     */   public void setBgColor(String value) {
/*  71 */     setBgColorImpl(getPeer(), value);
/*     */   }
/*     */   static native void setBgColorImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getFgColor() {
/*  76 */     return getFgColorImpl(getPeer());
/*     */   }
/*     */   static native String getFgColorImpl(long paramLong);
/*     */ 
/*     */   public void setFgColor(String value) {
/*  81 */     setFgColorImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFgColorImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAlinkColor() {
/*  86 */     return getAlinkColorImpl(getPeer());
/*     */   }
/*     */   static native String getAlinkColorImpl(long paramLong);
/*     */ 
/*     */   public void setAlinkColor(String value) {
/*  91 */     setAlinkColorImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAlinkColorImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getLinkColor() {
/*  96 */     return getLinkColorImpl(getPeer());
/*     */   }
/*     */   static native String getLinkColorImpl(long paramLong);
/*     */ 
/*     */   public void setLinkColor(String value) {
/* 101 */     setLinkColorImpl(getPeer(), value);
/*     */   }
/*     */   static native void setLinkColorImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getVlinkColor() {
/* 106 */     return getVlinkColorImpl(getPeer());
/*     */   }
/*     */   static native String getVlinkColorImpl(long paramLong);
/*     */ 
/*     */   public void setVlinkColor(String value) {
/* 111 */     setVlinkColorImpl(getPeer(), value);
/*     */   }
/*     */ 
/*     */   static native void setVlinkColorImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void open()
/*     */   {
/* 119 */     openImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void openImpl(long paramLong);
/*     */ 
/*     */   public void close()
/*     */   {
/* 126 */     closeImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void closeImpl(long paramLong);
/*     */ 
/*     */   public void write(String text)
/*     */   {
/* 133 */     writeImpl(getPeer(), text);
/*     */   }
/*     */ 
/*     */   static native void writeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void writeln(String text)
/*     */   {
/* 142 */     writelnImpl(getPeer(), text);
/*     */   }
/*     */ 
/*     */   static native void writelnImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void clear()
/*     */   {
/* 151 */     clearImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void clearImpl(long paramLong);
/*     */ 
/*     */   public void captureEvents()
/*     */   {
/* 158 */     captureEventsImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void captureEventsImpl(long paramLong);
/*     */ 
/*     */   public void releaseEvents()
/*     */   {
/* 165 */     releaseEventsImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void releaseEventsImpl(long paramLong);
/*     */ 
/*     */   public boolean hasFocus()
/*     */   {
/* 172 */     return hasFocusImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native boolean hasFocusImpl(long paramLong);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLDocumentImpl
 * JD-Core Version:    0.6.2
 */