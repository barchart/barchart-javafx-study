/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.html.HTMLImageElement;
/*     */ 
/*     */ public class HTMLImageElementImpl extends HTMLElementImpl
/*     */   implements HTMLImageElement
/*     */ {
/*     */   HTMLImageElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*   9 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLImageElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  13 */     return (HTMLImageElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  19 */     return getNameImpl(getPeer());
/*     */   }
/*     */   static native String getNameImpl(long paramLong);
/*     */ 
/*     */   public void setName(String value) {
/*  24 */     setNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAlign() {
/*  29 */     return getAlignImpl(getPeer());
/*     */   }
/*     */   static native String getAlignImpl(long paramLong);
/*     */ 
/*     */   public void setAlign(String value) {
/*  34 */     setAlignImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAlignImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAlt() {
/*  39 */     return getAltImpl(getPeer());
/*     */   }
/*     */   static native String getAltImpl(long paramLong);
/*     */ 
/*     */   public void setAlt(String value) {
/*  44 */     setAltImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAltImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getBorder() {
/*  49 */     return getBorderImpl(getPeer());
/*     */   }
/*     */   static native String getBorderImpl(long paramLong);
/*     */ 
/*     */   public void setBorder(String value) {
/*  54 */     setBorderImpl(getPeer(), value);
/*     */   }
/*     */   static native void setBorderImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getCrossOrigin() {
/*  59 */     return getCrossOriginImpl(getPeer());
/*     */   }
/*     */   static native String getCrossOriginImpl(long paramLong);
/*     */ 
/*     */   public void setCrossOrigin(String value) {
/*  64 */     setCrossOriginImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCrossOriginImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHeight() {
/*  69 */     return getHeightImpl(getPeer()) + "";
/*     */   }
/*     */   static native int getHeightImpl(long paramLong);
/*     */ 
/*     */   public void setHeight(String value) {
/*  74 */     setHeightImpl(getPeer(), Integer.parseInt(value));
/*     */   }
/*     */   static native void setHeightImpl(long paramLong, int paramInt);
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
/*     */   public boolean getIsMap() {
/*  89 */     return getIsMapImpl(getPeer());
/*     */   }
/*     */   static native boolean getIsMapImpl(long paramLong);
/*     */ 
/*     */   public void setIsMap(boolean value) {
/*  94 */     setIsMapImpl(getPeer(), value);
/*     */   }
/*     */   static native void setIsMapImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getLongDesc() {
/*  99 */     return getLongDescImpl(getPeer());
/*     */   }
/*     */   static native String getLongDescImpl(long paramLong);
/*     */ 
/*     */   public void setLongDesc(String value) {
/* 104 */     setLongDescImpl(getPeer(), value);
/*     */   }
/*     */   static native void setLongDescImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getSrc() {
/* 109 */     return getSrcImpl(getPeer());
/*     */   }
/*     */   static native String getSrcImpl(long paramLong);
/*     */ 
/*     */   public void setSrc(String value) {
/* 114 */     setSrcImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSrcImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getUseMap() {
/* 119 */     return getUseMapImpl(getPeer());
/*     */   }
/*     */   static native String getUseMapImpl(long paramLong);
/*     */ 
/*     */   public void setUseMap(String value) {
/* 124 */     setUseMapImpl(getPeer(), value);
/*     */   }
/*     */   static native void setUseMapImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getVspace() {
/* 129 */     return getVspaceImpl(getPeer()) + "";
/*     */   }
/*     */   static native int getVspaceImpl(long paramLong);
/*     */ 
/*     */   public void setVspace(String value) {
/* 134 */     setVspaceImpl(getPeer(), Integer.parseInt(value));
/*     */   }
/*     */   static native void setVspaceImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getWidth() {
/* 139 */     return getWidthImpl(getPeer()) + "";
/*     */   }
/*     */   static native int getWidthImpl(long paramLong);
/*     */ 
/*     */   public void setWidth(String value) {
/* 144 */     setWidthImpl(getPeer(), Integer.parseInt(value));
/*     */   }
/*     */   static native void setWidthImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public boolean getComplete() {
/* 149 */     return getCompleteImpl(getPeer());
/*     */   }
/*     */   static native boolean getCompleteImpl(long paramLong);
/*     */ 
/*     */   public String getLowsrc() {
/* 154 */     return getLowsrcImpl(getPeer());
/*     */   }
/*     */   static native String getLowsrcImpl(long paramLong);
/*     */ 
/*     */   public void setLowsrc(String value) {
/* 159 */     setLowsrcImpl(getPeer(), value);
/*     */   }
/*     */   static native void setLowsrcImpl(long paramLong, String paramString);
/*     */ 
/*     */   public int getNaturalHeight() {
/* 164 */     return getNaturalHeightImpl(getPeer());
/*     */   }
/*     */   static native int getNaturalHeightImpl(long paramLong);
/*     */ 
/*     */   public int getNaturalWidth() {
/* 169 */     return getNaturalWidthImpl(getPeer());
/*     */   }
/*     */   static native int getNaturalWidthImpl(long paramLong);
/*     */ 
/*     */   public int getX() {
/* 174 */     return getXImpl(getPeer());
/*     */   }
/*     */   static native int getXImpl(long paramLong);
/*     */ 
/*     */   public int getY() {
/* 179 */     return getYImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native int getYImpl(long paramLong);
/*     */ 
/*     */   public void setLowSrc(String lowSrc)
/*     */   {
/* 186 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   public String getLowSrc() {
/* 189 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLImageElementImpl
 * JD-Core Version:    0.6.2
 */