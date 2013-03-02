/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.html.HTMLCollection;
/*     */ import org.w3c.dom.html.HTMLElement;
/*     */ import org.w3c.dom.html.HTMLTableCaptionElement;
/*     */ import org.w3c.dom.html.HTMLTableElement;
/*     */ import org.w3c.dom.html.HTMLTableSectionElement;
/*     */ 
/*     */ public class HTMLTableElementImpl extends HTMLElementImpl
/*     */   implements HTMLTableElement
/*     */ {
/*     */   HTMLTableElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  14 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLTableElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  18 */     return (HTMLTableElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public HTMLTableCaptionElement getCaption()
/*     */   {
/*  24 */     return HTMLTableCaptionElementImpl.getImpl(getCaptionImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getCaptionImpl(long paramLong);
/*     */ 
/*     */   public void setCaption(HTMLTableCaptionElement value) throws DOMException {
/*  29 */     setCaptionImpl(getPeer(), HTMLTableCaptionElementImpl.getPeer(value));
/*     */   }
/*     */   static native void setCaptionImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public HTMLTableSectionElement getTHead() {
/*  34 */     return HTMLTableSectionElementImpl.getImpl(getTHeadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getTHeadImpl(long paramLong);
/*     */ 
/*     */   public void setTHead(HTMLTableSectionElement value) throws DOMException {
/*  39 */     setTHeadImpl(getPeer(), HTMLTableSectionElementImpl.getPeer(value));
/*     */   }
/*     */   static native void setTHeadImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public HTMLTableSectionElement getTFoot() {
/*  44 */     return HTMLTableSectionElementImpl.getImpl(getTFootImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getTFootImpl(long paramLong);
/*     */ 
/*     */   public void setTFoot(HTMLTableSectionElement value) throws DOMException {
/*  49 */     setTFootImpl(getPeer(), HTMLTableSectionElementImpl.getPeer(value));
/*     */   }
/*     */   static native void setTFootImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public HTMLCollection getRows() {
/*  54 */     return HTMLCollectionImpl.getImpl(getRowsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getRowsImpl(long paramLong);
/*     */ 
/*     */   public HTMLCollection getTBodies() {
/*  59 */     return HTMLCollectionImpl.getImpl(getTBodiesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getTBodiesImpl(long paramLong);
/*     */ 
/*     */   public String getAlign() {
/*  64 */     return getAlignImpl(getPeer());
/*     */   }
/*     */   static native String getAlignImpl(long paramLong);
/*     */ 
/*     */   public void setAlign(String value) {
/*  69 */     setAlignImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAlignImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getBgColor() {
/*  74 */     return getBgColorImpl(getPeer());
/*     */   }
/*     */   static native String getBgColorImpl(long paramLong);
/*     */ 
/*     */   public void setBgColor(String value) {
/*  79 */     setBgColorImpl(getPeer(), value);
/*     */   }
/*     */   static native void setBgColorImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getBorder() {
/*  84 */     return getBorderImpl(getPeer());
/*     */   }
/*     */   static native String getBorderImpl(long paramLong);
/*     */ 
/*     */   public void setBorder(String value) {
/*  89 */     setBorderImpl(getPeer(), value);
/*     */   }
/*     */   static native void setBorderImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getCellPadding() {
/*  94 */     return getCellPaddingImpl(getPeer());
/*     */   }
/*     */   static native String getCellPaddingImpl(long paramLong);
/*     */ 
/*     */   public void setCellPadding(String value) {
/*  99 */     setCellPaddingImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCellPaddingImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getCellSpacing() {
/* 104 */     return getCellSpacingImpl(getPeer());
/*     */   }
/*     */   static native String getCellSpacingImpl(long paramLong);
/*     */ 
/*     */   public void setCellSpacing(String value) {
/* 109 */     setCellSpacingImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCellSpacingImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getFrame() {
/* 114 */     return getFrameImpl(getPeer());
/*     */   }
/*     */   static native String getFrameImpl(long paramLong);
/*     */ 
/*     */   public void setFrame(String value) {
/* 119 */     setFrameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFrameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getRules() {
/* 124 */     return getRulesImpl(getPeer());
/*     */   }
/*     */   static native String getRulesImpl(long paramLong);
/*     */ 
/*     */   public void setRules(String value) {
/* 129 */     setRulesImpl(getPeer(), value);
/*     */   }
/*     */   static native void setRulesImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getSummary() {
/* 134 */     return getSummaryImpl(getPeer());
/*     */   }
/*     */   static native String getSummaryImpl(long paramLong);
/*     */ 
/*     */   public void setSummary(String value) {
/* 139 */     setSummaryImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSummaryImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getWidth() {
/* 144 */     return getWidthImpl(getPeer());
/*     */   }
/*     */   static native String getWidthImpl(long paramLong);
/*     */ 
/*     */   public void setWidth(String value) {
/* 149 */     setWidthImpl(getPeer(), value);
/*     */   }
/*     */ 
/*     */   static native void setWidthImpl(long paramLong, String paramString);
/*     */ 
/*     */   public HTMLElement createTHead()
/*     */   {
/* 157 */     return HTMLElementImpl.getImpl(createTHeadImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long createTHeadImpl(long paramLong);
/*     */ 
/*     */   public void deleteTHead()
/*     */   {
/* 164 */     deleteTHeadImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void deleteTHeadImpl(long paramLong);
/*     */ 
/*     */   public HTMLElement createTFoot()
/*     */   {
/* 171 */     return HTMLElementImpl.getImpl(createTFootImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long createTFootImpl(long paramLong);
/*     */ 
/*     */   public void deleteTFoot()
/*     */   {
/* 178 */     deleteTFootImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void deleteTFootImpl(long paramLong);
/*     */ 
/*     */   public HTMLElement createCaption()
/*     */   {
/* 185 */     return HTMLElementImpl.getImpl(createCaptionImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long createCaptionImpl(long paramLong);
/*     */ 
/*     */   public void deleteCaption()
/*     */   {
/* 192 */     deleteCaptionImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void deleteCaptionImpl(long paramLong);
/*     */ 
/*     */   public HTMLElement insertRow(int index) throws DOMException
/*     */   {
/* 199 */     return HTMLElementImpl.getImpl(insertRowImpl(getPeer(), index), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long insertRowImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public void deleteRow(int index)
/*     */     throws DOMException
/*     */   {
/* 208 */     deleteRowImpl(getPeer(), index);
/*     */   }
/*     */ 
/*     */   static native void deleteRowImpl(long paramLong, int paramInt);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLTableElementImpl
 * JD-Core Version:    0.6.2
 */