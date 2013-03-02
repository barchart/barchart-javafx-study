/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.html.HTMLElement;
/*     */ import org.w3c.dom.html.HTMLFormElement;
/*     */ import org.w3c.dom.html.HTMLSelectElement;
/*     */ 
/*     */ public class HTMLSelectElementImpl extends HTMLElementImpl
/*     */   implements HTMLSelectElement
/*     */ {
/*     */   HTMLSelectElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  14 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLSelectElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  18 */     return (HTMLSelectElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  24 */     return getTypeImpl(getPeer());
/*     */   }
/*     */   static native String getTypeImpl(long paramLong);
/*     */ 
/*     */   public int getSelectedIndex() {
/*  29 */     return getSelectedIndexImpl(getPeer());
/*     */   }
/*     */   static native int getSelectedIndexImpl(long paramLong);
/*     */ 
/*     */   public void setSelectedIndex(int value) {
/*  34 */     setSelectedIndexImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSelectedIndexImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getValue() {
/*  39 */     return getValueImpl(getPeer());
/*     */   }
/*     */   static native String getValueImpl(long paramLong);
/*     */ 
/*     */   public void setValue(String value) {
/*  44 */     setValueImpl(getPeer(), value);
/*     */   }
/*     */   static native void setValueImpl(long paramLong, String paramString);
/*     */ 
/*     */   public int getLength() {
/*  49 */     return getLengthImpl(getPeer());
/*     */   }
/*     */   static native int getLengthImpl(long paramLong);
/*     */ 
/*     */   public void setLength(int value) throws DOMException {
/*  54 */     setLengthImpl(getPeer(), value);
/*     */   }
/*     */   static native void setLengthImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public HTMLFormElement getForm() {
/*  59 */     return HTMLFormElementImpl.getImpl(getFormImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getFormImpl(long paramLong);
/*     */ 
/*     */   public boolean getWillValidate() {
/*  64 */     return getWillValidateImpl(getPeer());
/*     */   }
/*     */   static native boolean getWillValidateImpl(long paramLong);
/*     */ 
/*     */   public String getValidationMessage() {
/*  69 */     return getValidationMessageImpl(getPeer());
/*     */   }
/*     */   static native String getValidationMessageImpl(long paramLong);
/*     */ 
/*     */   public HTMLOptionsCollectionImpl getOptions() {
/*  74 */     return HTMLOptionsCollectionImpl.getImpl(getOptionsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOptionsImpl(long paramLong);
/*     */ 
/*     */   public boolean getDisabled() {
/*  79 */     return getDisabledImpl(getPeer());
/*     */   }
/*     */   static native boolean getDisabledImpl(long paramLong);
/*     */ 
/*     */   public void setDisabled(boolean value) {
/*  84 */     setDisabledImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDisabledImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean getAutofocus() {
/*  89 */     return getAutofocusImpl(getPeer());
/*     */   }
/*     */   static native boolean getAutofocusImpl(long paramLong);
/*     */ 
/*     */   public void setAutofocus(boolean value) {
/*  94 */     setAutofocusImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAutofocusImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean getMultiple() {
/*  99 */     return getMultipleImpl(getPeer());
/*     */   }
/*     */   static native boolean getMultipleImpl(long paramLong);
/*     */ 
/*     */   public void setMultiple(boolean value) {
/* 104 */     setMultipleImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMultipleImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getName() {
/* 109 */     return getNameImpl(getPeer());
/*     */   }
/*     */   static native String getNameImpl(long paramLong);
/*     */ 
/*     */   public void setName(String value) {
/* 114 */     setNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getRequired() {
/* 119 */     return getRequiredImpl(getPeer());
/*     */   }
/*     */   static native boolean getRequiredImpl(long paramLong);
/*     */ 
/*     */   public void setRequired(boolean value) {
/* 124 */     setRequiredImpl(getPeer(), value);
/*     */   }
/*     */   static native void setRequiredImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public int getSize() {
/* 129 */     return getSizeImpl(getPeer());
/*     */   }
/*     */   static native int getSizeImpl(long paramLong);
/*     */ 
/*     */   public void setSize(int value) {
/* 134 */     setSizeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSizeImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public NodeList getLabels() {
/* 139 */     return NodeListImpl.getImpl(getLabelsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getLabelsImpl(long paramLong);
/*     */ 
/*     */   public boolean checkValidity()
/*     */   {
/* 147 */     return checkValidityImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native boolean checkValidityImpl(long paramLong);
/*     */ 
/*     */   public void setCustomValidity(String error)
/*     */   {
/* 154 */     setCustomValidityImpl(getPeer(), error);
/*     */   }
/*     */ 
/*     */   static native void setCustomValidityImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void add(HTMLElement element, HTMLElement before)
/*     */     throws DOMException
/*     */   {
/* 164 */     addImpl(getPeer(), HTMLElementImpl.getPeer(element), HTMLElementImpl.getPeer(before));
/*     */   }
/*     */ 
/*     */   static native void addImpl(long paramLong1, long paramLong2, long paramLong3);
/*     */ 
/*     */   public void remove(int index)
/*     */   {
/* 175 */     removeImpl(getPeer(), index);
/*     */   }
/*     */ 
/*     */   static native void removeImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public Node item(int index)
/*     */   {
/* 184 */     return NodeImpl.getImpl(itemImpl(getPeer(), index), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long itemImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public Node namedItem(String name)
/*     */   {
/* 193 */     return NodeImpl.getImpl(namedItemImpl(getPeer(), name), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long namedItemImpl(long paramLong, String paramString);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLSelectElementImpl
 * JD-Core Version:    0.6.2
 */