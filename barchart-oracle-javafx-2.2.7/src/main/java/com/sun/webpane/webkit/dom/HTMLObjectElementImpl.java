/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.html.HTMLFormElement;
/*     */ import org.w3c.dom.html.HTMLObjectElement;
/*     */ 
/*     */ public class HTMLObjectElementImpl extends HTMLElementImpl
/*     */   implements HTMLObjectElement
/*     */ {
/*     */   HTMLObjectElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  11 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLObjectElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  15 */     return (HTMLObjectElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public HTMLFormElement getForm()
/*     */   {
/*  21 */     return HTMLFormElementImpl.getImpl(getFormImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getFormImpl(long paramLong);
/*     */ 
/*     */   public String getCode() {
/*  26 */     return getCodeImpl(getPeer());
/*     */   }
/*     */   static native String getCodeImpl(long paramLong);
/*     */ 
/*     */   public void setCode(String value) {
/*  31 */     setCodeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCodeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAlign() {
/*  36 */     return getAlignImpl(getPeer());
/*     */   }
/*     */   static native String getAlignImpl(long paramLong);
/*     */ 
/*     */   public void setAlign(String value) {
/*  41 */     setAlignImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAlignImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getArchive() {
/*  46 */     return getArchiveImpl(getPeer());
/*     */   }
/*     */   static native String getArchiveImpl(long paramLong);
/*     */ 
/*     */   public void setArchive(String value) {
/*  51 */     setArchiveImpl(getPeer(), value);
/*     */   }
/*     */   static native void setArchiveImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getBorder() {
/*  56 */     return getBorderImpl(getPeer());
/*     */   }
/*     */   static native String getBorderImpl(long paramLong);
/*     */ 
/*     */   public void setBorder(String value) {
/*  61 */     setBorderImpl(getPeer(), value);
/*     */   }
/*     */   static native void setBorderImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getCodeBase() {
/*  66 */     return getCodeBaseImpl(getPeer());
/*     */   }
/*     */   static native String getCodeBaseImpl(long paramLong);
/*     */ 
/*     */   public void setCodeBase(String value) {
/*  71 */     setCodeBaseImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCodeBaseImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getCodeType() {
/*  76 */     return getCodeTypeImpl(getPeer());
/*     */   }
/*     */   static native String getCodeTypeImpl(long paramLong);
/*     */ 
/*     */   public void setCodeType(String value) {
/*  81 */     setCodeTypeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCodeTypeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getData() {
/*  86 */     return getDataImpl(getPeer());
/*     */   }
/*     */   static native String getDataImpl(long paramLong);
/*     */ 
/*     */   public void setData(String value) {
/*  91 */     setDataImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDataImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getDeclare() {
/*  96 */     return getDeclareImpl(getPeer());
/*     */   }
/*     */   static native boolean getDeclareImpl(long paramLong);
/*     */ 
/*     */   public void setDeclare(boolean value) {
/* 101 */     setDeclareImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDeclareImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getHeight() {
/* 106 */     return getHeightImpl(getPeer());
/*     */   }
/*     */   static native String getHeightImpl(long paramLong);
/*     */ 
/*     */   public void setHeight(String value) {
/* 111 */     setHeightImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHeightImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getHspace() {
/* 116 */     return getHspaceImpl(getPeer()) + "";
/*     */   }
/*     */   static native int getHspaceImpl(long paramLong);
/*     */ 
/*     */   public void setHspace(String value) {
/* 121 */     setHspaceImpl(getPeer(), Integer.parseInt(value));
/*     */   }
/*     */   static native void setHspaceImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getName() {
/* 126 */     return getNameImpl(getPeer());
/*     */   }
/*     */   static native String getNameImpl(long paramLong);
/*     */ 
/*     */   public void setName(String value) {
/* 131 */     setNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getStandby() {
/* 136 */     return getStandbyImpl(getPeer());
/*     */   }
/*     */   static native String getStandbyImpl(long paramLong);
/*     */ 
/*     */   public void setStandby(String value) {
/* 141 */     setStandbyImpl(getPeer(), value);
/*     */   }
/*     */   static native void setStandbyImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getType() {
/* 146 */     return getTypeImpl(getPeer());
/*     */   }
/*     */   static native String getTypeImpl(long paramLong);
/*     */ 
/*     */   public void setType(String value) {
/* 151 */     setTypeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTypeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getUseMap() {
/* 156 */     return getUseMapImpl(getPeer());
/*     */   }
/*     */   static native String getUseMapImpl(long paramLong);
/*     */ 
/*     */   public void setUseMap(String value) {
/* 161 */     setUseMapImpl(getPeer(), value);
/*     */   }
/*     */   static native void setUseMapImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getVspace() {
/* 166 */     return getVspaceImpl(getPeer()) + "";
/*     */   }
/*     */   static native int getVspaceImpl(long paramLong);
/*     */ 
/*     */   public void setVspace(String value) {
/* 171 */     setVspaceImpl(getPeer(), Integer.parseInt(value));
/*     */   }
/*     */   static native void setVspaceImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getWidth() {
/* 176 */     return getWidthImpl(getPeer());
/*     */   }
/*     */   static native String getWidthImpl(long paramLong);
/*     */ 
/*     */   public void setWidth(String value) {
/* 181 */     setWidthImpl(getPeer(), value);
/*     */   }
/*     */   static native void setWidthImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getWillValidate() {
/* 186 */     return getWillValidateImpl(getPeer());
/*     */   }
/*     */   static native boolean getWillValidateImpl(long paramLong);
/*     */ 
/*     */   public String getValidationMessage() {
/* 191 */     return getValidationMessageImpl(getPeer());
/*     */   }
/*     */   static native String getValidationMessageImpl(long paramLong);
/*     */ 
/*     */   public Document getContentDocument() {
/* 196 */     return DocumentImpl.getImpl(getContentDocumentImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getContentDocumentImpl(long paramLong);
/*     */ 
/*     */   public boolean checkValidity()
/*     */   {
/* 204 */     return checkValidityImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native boolean checkValidityImpl(long paramLong);
/*     */ 
/*     */   public void setCustomValidity(String error)
/*     */   {
/* 211 */     setCustomValidityImpl(getPeer(), error);
/*     */   }
/*     */ 
/*     */   static native void setCustomValidityImpl(long paramLong, String paramString);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLObjectElementImpl
 * JD-Core Version:    0.6.2
 */