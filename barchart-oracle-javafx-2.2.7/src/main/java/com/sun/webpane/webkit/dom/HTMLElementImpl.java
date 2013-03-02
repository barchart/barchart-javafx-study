/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.html.HTMLCollection;
/*     */ import org.w3c.dom.html.HTMLElement;
/*     */ 
/*     */ public class HTMLElementImpl extends ElementImpl
/*     */   implements HTMLElement
/*     */ {
/*     */   HTMLElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  12 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  16 */     return (HTMLElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  22 */     return getIdImpl(getPeer());
/*     */   }
/*     */   static native String getIdImpl(long paramLong);
/*     */ 
/*     */   public void setId(String value) {
/*  27 */     setIdImpl(getPeer(), value);
/*     */   }
/*     */   static native void setIdImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getTitle() {
/*  32 */     return getTitleImpl(getPeer());
/*     */   }
/*     */   static native String getTitleImpl(long paramLong);
/*     */ 
/*     */   public void setTitle(String value) {
/*  37 */     setTitleImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTitleImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getLang() {
/*  42 */     return getLangImpl(getPeer());
/*     */   }
/*     */   static native String getLangImpl(long paramLong);
/*     */ 
/*     */   public void setLang(String value) {
/*  47 */     setLangImpl(getPeer(), value);
/*     */   }
/*     */   static native void setLangImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getDir() {
/*  52 */     return getDirImpl(getPeer());
/*     */   }
/*     */   static native String getDirImpl(long paramLong);
/*     */ 
/*     */   public void setDir(String value) {
/*  57 */     setDirImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDirImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getClassName() {
/*  62 */     return getClassNameImpl(getPeer());
/*     */   }
/*     */   static native String getClassNameImpl(long paramLong);
/*     */ 
/*     */   public void setClassName(String value) {
/*  67 */     setClassNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setClassNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public int getTabIndex() {
/*  72 */     return getTabIndexImpl(getPeer());
/*     */   }
/*     */   static native int getTabIndexImpl(long paramLong);
/*     */ 
/*     */   public void setTabIndex(int value) {
/*  77 */     setTabIndexImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTabIndexImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public boolean getDraggable() {
/*  82 */     return getDraggableImpl(getPeer());
/*     */   }
/*     */   static native boolean getDraggableImpl(long paramLong);
/*     */ 
/*     */   public void setDraggable(boolean value) {
/*  87 */     setDraggableImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDraggableImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getWebkitdropzone() {
/*  92 */     return getWebkitdropzoneImpl(getPeer());
/*     */   }
/*     */   static native String getWebkitdropzoneImpl(long paramLong);
/*     */ 
/*     */   public void setWebkitdropzone(String value) {
/*  97 */     setWebkitdropzoneImpl(getPeer(), value);
/*     */   }
/*     */   static native void setWebkitdropzoneImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getHidden() {
/* 102 */     return getHiddenImpl(getPeer());
/*     */   }
/*     */   static native boolean getHiddenImpl(long paramLong);
/*     */ 
/*     */   public void setHidden(boolean value) {
/* 107 */     setHiddenImpl(getPeer(), value);
/*     */   }
/*     */   static native void setHiddenImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getAccessKey() {
/* 112 */     return getAccessKeyImpl(getPeer());
/*     */   }
/*     */   static native String getAccessKeyImpl(long paramLong);
/*     */ 
/*     */   public void setAccessKey(String value) {
/* 117 */     setAccessKeyImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAccessKeyImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getInnerHTML() {
/* 122 */     return getInnerHTMLImpl(getPeer());
/*     */   }
/*     */   static native String getInnerHTMLImpl(long paramLong);
/*     */ 
/*     */   public void setInnerHTML(String value) throws DOMException {
/* 127 */     setInnerHTMLImpl(getPeer(), value);
/*     */   }
/*     */   static native void setInnerHTMLImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getInnerText() {
/* 132 */     return getInnerTextImpl(getPeer());
/*     */   }
/*     */   static native String getInnerTextImpl(long paramLong);
/*     */ 
/*     */   public void setInnerText(String value) throws DOMException {
/* 137 */     setInnerTextImpl(getPeer(), value);
/*     */   }
/*     */   static native void setInnerTextImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getOuterHTML() {
/* 142 */     return getOuterHTMLImpl(getPeer());
/*     */   }
/*     */   static native String getOuterHTMLImpl(long paramLong);
/*     */ 
/*     */   public void setOuterHTML(String value) throws DOMException {
/* 147 */     setOuterHTMLImpl(getPeer(), value);
/*     */   }
/*     */   static native void setOuterHTMLImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getOuterText() {
/* 152 */     return getOuterTextImpl(getPeer());
/*     */   }
/*     */   static native String getOuterTextImpl(long paramLong);
/*     */ 
/*     */   public void setOuterText(String value) throws DOMException {
/* 157 */     setOuterTextImpl(getPeer(), value);
/*     */   }
/*     */   static native void setOuterTextImpl(long paramLong, String paramString);
/*     */ 
/*     */   public HTMLCollection getChildren() {
/* 162 */     return HTMLCollectionImpl.getImpl(getChildrenImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getChildrenImpl(long paramLong);
/*     */ 
/*     */   public String getContentEditable() {
/* 167 */     return getContentEditableImpl(getPeer());
/*     */   }
/*     */   static native String getContentEditableImpl(long paramLong);
/*     */ 
/*     */   public void setContentEditable(String value) throws DOMException {
/* 172 */     setContentEditableImpl(getPeer(), value);
/*     */   }
/*     */   static native void setContentEditableImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getIsContentEditable() {
/* 177 */     return getIsContentEditableImpl(getPeer());
/*     */   }
/*     */   static native boolean getIsContentEditableImpl(long paramLong);
/*     */ 
/*     */   public boolean getSpellcheck() {
/* 182 */     return getSpellcheckImpl(getPeer());
/*     */   }
/*     */   static native boolean getSpellcheckImpl(long paramLong);
/*     */ 
/*     */   public void setSpellcheck(boolean value) {
/* 187 */     setSpellcheckImpl(getPeer(), value);
/*     */   }
/*     */ 
/*     */   static native void setSpellcheckImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public Element insertAdjacentElement(String where, Element element)
/*     */     throws DOMException
/*     */   {
/* 196 */     return ElementImpl.getImpl(insertAdjacentElementImpl(getPeer(), where, ElementImpl.getPeer(element)), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long insertAdjacentElementImpl(long paramLong1, String paramString, long paramLong2);
/*     */ 
/*     */   public void insertAdjacentHTML(String where, String html)
/*     */     throws DOMException
/*     */   {
/* 208 */     insertAdjacentHTMLImpl(getPeer(), where, html);
/*     */   }
/*     */ 
/*     */   static native void insertAdjacentHTMLImpl(long paramLong, String paramString1, String paramString2);
/*     */ 
/*     */   public void insertAdjacentText(String where, String text)
/*     */     throws DOMException
/*     */   {
/* 220 */     insertAdjacentTextImpl(getPeer(), where, text);
/*     */   }
/*     */ 
/*     */   static native void insertAdjacentTextImpl(long paramLong, String paramString1, String paramString2);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLElementImpl
 * JD-Core Version:    0.6.2
 */