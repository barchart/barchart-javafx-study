/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.html.HTMLAppletElement;
/*     */ 
/*     */ public class HTMLAppletElementImpl extends HTMLElementImpl
/*     */   implements HTMLAppletElement
/*     */ {
/*     */   HTMLAppletElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*   9 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLAppletElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  13 */     return (HTMLAppletElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getAlign()
/*     */   {
/*  19 */     return getAlignImpl(getPeer());
/*     */   }
/*     */   static native String getAlignImpl(long paramLong);
/*     */ 
/*     */   public void setAlign(String value) {
/*  24 */     setAlignImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAlignImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAlt() {
/*  29 */     return getAltImpl(getPeer());
/*     */   }
/*     */   static native String getAltImpl(long paramLong);
/*     */ 
/*     */   public void setAlt(String value) {
/*  34 */     setAltImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAltImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getArchive() {
/*  39 */     return getArchiveImpl(getPeer());
/*     */   }
/*     */   static native String getArchiveImpl(long paramLong);
/*     */ 
/*     */   public void setArchive(String value) {
/*  44 */     setArchiveImpl(getPeer(), value);
/*     */   }
/*     */   static native void setArchiveImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getCode() {
/*  49 */     return getCodeImpl(getPeer());
/*     */   }
/*     */   static native String getCodeImpl(long paramLong);
/*     */ 
/*     */   public void setCode(String value) {
/*  54 */     setCodeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCodeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getCodeBase() {
/*  59 */     return getCodeBaseImpl(getPeer());
/*     */   }
/*     */   static native String getCodeBaseImpl(long paramLong);
/*     */ 
/*     */   public void setCodeBase(String value) {
/*  64 */     setCodeBaseImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCodeBaseImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHeight() {
/*  69 */     return getHeightImpl(getPeer());
/*     */   }
/*     */   static native String getHeightImpl(long paramLong);
/*     */ 
/*     */   public void setHeight(String value) {
/*  74 */     setHeightImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHeightImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHspace() {
/*  79 */     return getHspaceImpl(getPeer()) + "";
/*     */   }
/*     */   static native int getHspaceImpl(long paramLong);
/*     */ 
/*     */   public void setHspace(String value) {
/*  84 */     setHspaceImpl(getPeer(), Integer.parseInt(value));
/*     */   }
/*     */   static native void setHspaceImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getName() {
/*  89 */     return getNameImpl(getPeer());
/*     */   }
/*     */   static native String getNameImpl(long paramLong);
/*     */ 
/*     */   public void setName(String value) {
/*  94 */     setNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getObject() {
/*  99 */     return getObjectImpl(getPeer());
/*     */   }
/*     */   static native String getObjectImpl(long paramLong);
/*     */ 
/*     */   public void setObject(String value) {
/* 104 */     setObjectImpl(getPeer(), value);
/*     */   }
/*     */   static native void setObjectImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getVspace() {
/* 109 */     return getVspaceImpl(getPeer()) + "";
/*     */   }
/*     */   static native int getVspaceImpl(long paramLong);
/*     */ 
/*     */   public void setVspace(String value) {
/* 114 */     setVspaceImpl(getPeer(), Integer.parseInt(value));
/*     */   }
/*     */   static native void setVspaceImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getWidth() {
/* 119 */     return getWidthImpl(getPeer());
/*     */   }
/*     */   static native String getWidthImpl(long paramLong);
/*     */ 
/*     */   public void setWidth(String value) {
/* 124 */     setWidthImpl(getPeer(), value);
/*     */   }
/*     */ 
/*     */   static native void setWidthImpl(long paramLong, String paramString);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLAppletElementImpl
 * JD-Core Version:    0.6.2
 */