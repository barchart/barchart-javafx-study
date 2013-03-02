/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.html.HTMLTableCellElement;
/*     */ 
/*     */ public class HTMLTableCellElementImpl extends HTMLElementImpl
/*     */   implements HTMLTableCellElement
/*     */ {
/*     */   HTMLTableCellElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*   9 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLTableCellElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  13 */     return (HTMLTableCellElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public int getCellIndex()
/*     */   {
/*  19 */     return getCellIndexImpl(getPeer());
/*     */   }
/*     */   static native int getCellIndexImpl(long paramLong);
/*     */ 
/*     */   public String getAbbr() {
/*  24 */     return getAbbrImpl(getPeer());
/*     */   }
/*     */   static native String getAbbrImpl(long paramLong);
/*     */ 
/*     */   public void setAbbr(String value) {
/*  29 */     setAbbrImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAbbrImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAlign() {
/*  34 */     return getAlignImpl(getPeer());
/*     */   }
/*     */   static native String getAlignImpl(long paramLong);
/*     */ 
/*     */   public void setAlign(String value) {
/*  39 */     setAlignImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAlignImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAxis() {
/*  44 */     return getAxisImpl(getPeer());
/*     */   }
/*     */   static native String getAxisImpl(long paramLong);
/*     */ 
/*     */   public void setAxis(String value) {
/*  49 */     setAxisImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAxisImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getBgColor() {
/*  54 */     return getBgColorImpl(getPeer());
/*     */   }
/*     */   static native String getBgColorImpl(long paramLong);
/*     */ 
/*     */   public void setBgColor(String value) {
/*  59 */     setBgColorImpl(getPeer(), value);
/*     */   }
/*     */   static native void setBgColorImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getCh() {
/*  64 */     return getChImpl(getPeer());
/*     */   }
/*     */   static native String getChImpl(long paramLong);
/*     */ 
/*     */   public void setCh(String value) {
/*  69 */     setChImpl(getPeer(), value);
/*     */   }
/*     */   static native void setChImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getChOff() {
/*  74 */     return getChOffImpl(getPeer());
/*     */   }
/*     */   static native String getChOffImpl(long paramLong);
/*     */ 
/*     */   public void setChOff(String value) {
/*  79 */     setChOffImpl(getPeer(), value);
/*     */   }
/*     */   static native void setChOffImpl(long paramLong, String paramString);
/*     */ 
/*     */   public int getColSpan() {
/*  84 */     return getColSpanImpl(getPeer());
/*     */   }
/*     */   static native int getColSpanImpl(long paramLong);
/*     */ 
/*     */   public void setColSpan(int value) {
/*  89 */     setColSpanImpl(getPeer(), value);
/*     */   }
/*     */   static native void setColSpanImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getHeaders() {
/*  94 */     return getHeadersImpl(getPeer());
/*     */   }
/*     */   static native String getHeadersImpl(long paramLong);
/*     */ 
/*     */   public void setHeaders(String value) {
/*  99 */     setHeadersImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHeadersImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHeight() {
/* 104 */     return getHeightImpl(getPeer());
/*     */   }
/*     */   static native String getHeightImpl(long paramLong);
/*     */ 
/*     */   public void setHeight(String value) {
/* 109 */     setHeightImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHeightImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getNoWrap() {
/* 114 */     return getNoWrapImpl(getPeer());
/*     */   }
/*     */   static native boolean getNoWrapImpl(long paramLong);
/*     */ 
/*     */   public void setNoWrap(boolean value) {
/* 119 */     setNoWrapImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNoWrapImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public int getRowSpan() {
/* 124 */     return getRowSpanImpl(getPeer());
/*     */   }
/*     */   static native int getRowSpanImpl(long paramLong);
/*     */ 
/*     */   public void setRowSpan(int value) {
/* 129 */     setRowSpanImpl(getPeer(), value);
/*     */   }
/*     */   static native void setRowSpanImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getScope() {
/* 134 */     return getScopeImpl(getPeer());
/*     */   }
/*     */   static native String getScopeImpl(long paramLong);
/*     */ 
/*     */   public void setScope(String value) {
/* 139 */     setScopeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setScopeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getVAlign() {
/* 144 */     return getVAlignImpl(getPeer());
/*     */   }
/*     */   static native String getVAlignImpl(long paramLong);
/*     */ 
/*     */   public void setVAlign(String value) {
/* 149 */     setVAlignImpl(getPeer(), value);
/*     */   }
/*     */   static native void setVAlignImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getWidth() {
/* 154 */     return getWidthImpl(getPeer());
/*     */   }
/*     */   static native String getWidthImpl(long paramLong);
/*     */ 
/*     */   public void setWidth(String value) {
/* 159 */     setWidthImpl(getPeer(), value);
/*     */   }
/*     */ 
/*     */   static native void setWidthImpl(long paramLong, String paramString);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLTableCellElementImpl
 * JD-Core Version:    0.6.2
 */