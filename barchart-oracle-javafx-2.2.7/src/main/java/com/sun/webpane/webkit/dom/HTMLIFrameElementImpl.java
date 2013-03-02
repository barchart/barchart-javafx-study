/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.html.HTMLIFrameElement;
/*     */ import org.w3c.dom.views.AbstractView;
/*     */ 
/*     */ public class HTMLIFrameElementImpl extends HTMLElementImpl
/*     */   implements HTMLIFrameElement
/*     */ {
/*     */   HTMLIFrameElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  11 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLIFrameElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  15 */     return (HTMLIFrameElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getAlign()
/*     */   {
/*  21 */     return getAlignImpl(getPeer());
/*     */   }
/*     */   static native String getAlignImpl(long paramLong);
/*     */ 
/*     */   public void setAlign(String value) {
/*  26 */     setAlignImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAlignImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getFrameBorder() {
/*  31 */     return getFrameBorderImpl(getPeer());
/*     */   }
/*     */   static native String getFrameBorderImpl(long paramLong);
/*     */ 
/*     */   public void setFrameBorder(String value) {
/*  36 */     setFrameBorderImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFrameBorderImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHeight() {
/*  41 */     return getHeightImpl(getPeer());
/*     */   }
/*     */   static native String getHeightImpl(long paramLong);
/*     */ 
/*     */   public void setHeight(String value) {
/*  46 */     setHeightImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHeightImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getLongDesc() {
/*  51 */     return getLongDescImpl(getPeer());
/*     */   }
/*     */   static native String getLongDescImpl(long paramLong);
/*     */ 
/*     */   public void setLongDesc(String value) {
/*  56 */     setLongDescImpl(getPeer(), value);
/*     */   }
/*     */   static native void setLongDescImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getMarginHeight() {
/*  61 */     return getMarginHeightImpl(getPeer());
/*     */   }
/*     */   static native String getMarginHeightImpl(long paramLong);
/*     */ 
/*     */   public void setMarginHeight(String value) {
/*  66 */     setMarginHeightImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMarginHeightImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getMarginWidth() {
/*  71 */     return getMarginWidthImpl(getPeer());
/*     */   }
/*     */   static native String getMarginWidthImpl(long paramLong);
/*     */ 
/*     */   public void setMarginWidth(String value) {
/*  76 */     setMarginWidthImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMarginWidthImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getName() {
/*  81 */     return getNameImpl(getPeer());
/*     */   }
/*     */   static native String getNameImpl(long paramLong);
/*     */ 
/*     */   public void setName(String value) {
/*  86 */     setNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getSandbox() {
/*  91 */     return getSandboxImpl(getPeer());
/*     */   }
/*     */   static native String getSandboxImpl(long paramLong);
/*     */ 
/*     */   public void setSandbox(String value) {
/*  96 */     setSandboxImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSandboxImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getScrolling() {
/* 101 */     return getScrollingImpl(getPeer());
/*     */   }
/*     */   static native String getScrollingImpl(long paramLong);
/*     */ 
/*     */   public void setScrolling(String value) {
/* 106 */     setScrollingImpl(getPeer(), value);
/*     */   }
/*     */   static native void setScrollingImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getSrc() {
/* 111 */     return getSrcImpl(getPeer());
/*     */   }
/*     */   static native String getSrcImpl(long paramLong);
/*     */ 
/*     */   public void setSrc(String value) {
/* 116 */     setSrcImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSrcImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getWidth() {
/* 121 */     return getWidthImpl(getPeer());
/*     */   }
/*     */   static native String getWidthImpl(long paramLong);
/*     */ 
/*     */   public void setWidth(String value) {
/* 126 */     setWidthImpl(getPeer(), value);
/*     */   }
/*     */   static native void setWidthImpl(long paramLong, String paramString);
/*     */ 
/*     */   public Document getContentDocument() {
/* 131 */     return DocumentImpl.getImpl(getContentDocumentImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getContentDocumentImpl(long paramLong);
/*     */ 
/*     */   public AbstractView getContentWindow() {
/* 136 */     return DOMWindowImpl.getImpl(getContentWindowImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getContentWindowImpl(long paramLong);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLIFrameElementImpl
 * JD-Core Version:    0.6.2
 */