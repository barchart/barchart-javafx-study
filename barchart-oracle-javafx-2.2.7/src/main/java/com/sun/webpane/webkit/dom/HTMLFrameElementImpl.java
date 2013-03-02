/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.html.HTMLFrameElement;
/*     */ import org.w3c.dom.views.AbstractView;
/*     */ 
/*     */ public class HTMLFrameElementImpl extends HTMLElementImpl
/*     */   implements HTMLFrameElement
/*     */ {
/*     */   HTMLFrameElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  11 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLFrameElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  15 */     return (HTMLFrameElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getFrameBorder()
/*     */   {
/*  21 */     return getFrameBorderImpl(getPeer());
/*     */   }
/*     */   static native String getFrameBorderImpl(long paramLong);
/*     */ 
/*     */   public void setFrameBorder(String value) {
/*  26 */     setFrameBorderImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFrameBorderImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getLongDesc() {
/*  31 */     return getLongDescImpl(getPeer());
/*     */   }
/*     */   static native String getLongDescImpl(long paramLong);
/*     */ 
/*     */   public void setLongDesc(String value) {
/*  36 */     setLongDescImpl(getPeer(), value);
/*     */   }
/*     */   static native void setLongDescImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getMarginHeight() {
/*  41 */     return getMarginHeightImpl(getPeer());
/*     */   }
/*     */   static native String getMarginHeightImpl(long paramLong);
/*     */ 
/*     */   public void setMarginHeight(String value) {
/*  46 */     setMarginHeightImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMarginHeightImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getMarginWidth() {
/*  51 */     return getMarginWidthImpl(getPeer());
/*     */   }
/*     */   static native String getMarginWidthImpl(long paramLong);
/*     */ 
/*     */   public void setMarginWidth(String value) {
/*  56 */     setMarginWidthImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMarginWidthImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getName() {
/*  61 */     return getNameImpl(getPeer());
/*     */   }
/*     */   static native String getNameImpl(long paramLong);
/*     */ 
/*     */   public void setName(String value) {
/*  66 */     setNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getNoResize() {
/*  71 */     return getNoResizeImpl(getPeer());
/*     */   }
/*     */   static native boolean getNoResizeImpl(long paramLong);
/*     */ 
/*     */   public void setNoResize(boolean value) {
/*  76 */     setNoResizeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNoResizeImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getScrolling() {
/*  81 */     return getScrollingImpl(getPeer());
/*     */   }
/*     */   static native String getScrollingImpl(long paramLong);
/*     */ 
/*     */   public void setScrolling(String value) {
/*  86 */     setScrollingImpl(getPeer(), value);
/*     */   }
/*     */   static native void setScrollingImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getSrc() {
/*  91 */     return getSrcImpl(getPeer());
/*     */   }
/*     */   static native String getSrcImpl(long paramLong);
/*     */ 
/*     */   public void setSrc(String value) {
/*  96 */     setSrcImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSrcImpl(long paramLong, String paramString);
/*     */ 
/*     */   public Document getContentDocument() {
/* 101 */     return DocumentImpl.getImpl(getContentDocumentImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getContentDocumentImpl(long paramLong);
/*     */ 
/*     */   public AbstractView getContentWindow() {
/* 106 */     return DOMWindowImpl.getImpl(getContentWindowImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getContentWindowImpl(long paramLong);
/*     */ 
/*     */   public String getLocation() {
/* 111 */     return getLocationImpl(getPeer());
/*     */   }
/*     */   static native String getLocationImpl(long paramLong);
/*     */ 
/*     */   public void setLocation(String value) {
/* 116 */     setLocationImpl(getPeer(), value);
/*     */   }
/*     */   static native void setLocationImpl(long paramLong, String paramString);
/*     */ 
/*     */   public int getWidth() {
/* 121 */     return getWidthImpl(getPeer());
/*     */   }
/*     */   static native int getWidthImpl(long paramLong);
/*     */ 
/*     */   public int getHeight() {
/* 126 */     return getHeightImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native int getHeightImpl(long paramLong);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLFrameElementImpl
 * JD-Core Version:    0.6.2
 */