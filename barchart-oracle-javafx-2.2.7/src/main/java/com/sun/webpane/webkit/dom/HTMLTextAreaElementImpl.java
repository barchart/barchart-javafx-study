/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.html.HTMLFormElement;
/*     */ import org.w3c.dom.html.HTMLTextAreaElement;
/*     */ 
/*     */ public class HTMLTextAreaElementImpl extends HTMLElementImpl
/*     */   implements HTMLTextAreaElement
/*     */ {
/*     */   HTMLTextAreaElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  12 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLTextAreaElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  16 */     return (HTMLTextAreaElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getDefaultValue()
/*     */   {
/*  22 */     return getDefaultValueImpl(getPeer());
/*     */   }
/*     */   static native String getDefaultValueImpl(long paramLong);
/*     */ 
/*     */   public void setDefaultValue(String value) {
/*  27 */     setDefaultValueImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDefaultValueImpl(long paramLong, String paramString);
/*     */ 
/*     */   public HTMLFormElement getForm() {
/*  32 */     return HTMLFormElementImpl.getImpl(getFormImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getFormImpl(long paramLong);
/*     */ 
/*     */   public int getCols() {
/*  37 */     return getColsImpl(getPeer());
/*     */   }
/*     */   static native int getColsImpl(long paramLong);
/*     */ 
/*     */   public void setCols(int value) {
/*  42 */     setColsImpl(getPeer(), value);
/*     */   }
/*     */   static native void setColsImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getDirName() {
/*  47 */     return getDirNameImpl(getPeer());
/*     */   }
/*     */   static native String getDirNameImpl(long paramLong);
/*     */ 
/*     */   public void setDirName(String value) {
/*  52 */     setDirNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDirNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getDisabled() {
/*  57 */     return getDisabledImpl(getPeer());
/*     */   }
/*     */   static native boolean getDisabledImpl(long paramLong);
/*     */ 
/*     */   public void setDisabled(boolean value) {
/*  62 */     setDisabledImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDisabledImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean getAutofocus() {
/*  67 */     return getAutofocusImpl(getPeer());
/*     */   }
/*     */   static native boolean getAutofocusImpl(long paramLong);
/*     */ 
/*     */   public void setAutofocus(boolean value) {
/*  72 */     setAutofocusImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAutofocusImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public int getMaxLength() {
/*  77 */     return getMaxLengthImpl(getPeer());
/*     */   }
/*     */   static native int getMaxLengthImpl(long paramLong);
/*     */ 
/*     */   public void setMaxLength(int value) throws DOMException {
/*  82 */     setMaxLengthImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMaxLengthImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getName() {
/*  87 */     return getNameImpl(getPeer());
/*     */   }
/*     */   static native String getNameImpl(long paramLong);
/*     */ 
/*     */   public void setName(String value) {
/*  92 */     setNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getPlaceholder() {
/*  97 */     return getPlaceholderImpl(getPeer());
/*     */   }
/*     */   static native String getPlaceholderImpl(long paramLong);
/*     */ 
/*     */   public void setPlaceholder(String value) {
/* 102 */     setPlaceholderImpl(getPeer(), value);
/*     */   }
/*     */   static native void setPlaceholderImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getReadOnly() {
/* 107 */     return getReadOnlyImpl(getPeer());
/*     */   }
/*     */   static native boolean getReadOnlyImpl(long paramLong);
/*     */ 
/*     */   public void setReadOnly(boolean value) {
/* 112 */     setReadOnlyImpl(getPeer(), value);
/*     */   }
/*     */   static native void setReadOnlyImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean getRequired() {
/* 117 */     return getRequiredImpl(getPeer());
/*     */   }
/*     */   static native boolean getRequiredImpl(long paramLong);
/*     */ 
/*     */   public void setRequired(boolean value) {
/* 122 */     setRequiredImpl(getPeer(), value);
/*     */   }
/*     */   static native void setRequiredImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public int getRows() {
/* 127 */     return getRowsImpl(getPeer());
/*     */   }
/*     */   static native int getRowsImpl(long paramLong);
/*     */ 
/*     */   public void setRows(int value) {
/* 132 */     setRowsImpl(getPeer(), value);
/*     */   }
/*     */   static native void setRowsImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getWrap() {
/* 137 */     return getWrapImpl(getPeer());
/*     */   }
/*     */   static native String getWrapImpl(long paramLong);
/*     */ 
/*     */   public void setWrap(String value) {
/* 142 */     setWrapImpl(getPeer(), value);
/*     */   }
/*     */   static native void setWrapImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getType() {
/* 147 */     return getTypeImpl(getPeer());
/*     */   }
/*     */   static native String getTypeImpl(long paramLong);
/*     */ 
/*     */   public String getValue() {
/* 152 */     return getValueImpl(getPeer());
/*     */   }
/*     */   static native String getValueImpl(long paramLong);
/*     */ 
/*     */   public void setValue(String value) {
/* 157 */     setValueImpl(getPeer(), value);
/*     */   }
/*     */   static native void setValueImpl(long paramLong, String paramString);
/*     */ 
/*     */   public int getTextLength() {
/* 162 */     return getTextLengthImpl(getPeer());
/*     */   }
/*     */   static native int getTextLengthImpl(long paramLong);
/*     */ 
/*     */   public boolean getWillValidate() {
/* 167 */     return getWillValidateImpl(getPeer());
/*     */   }
/*     */   static native boolean getWillValidateImpl(long paramLong);
/*     */ 
/*     */   public String getValidationMessage() {
/* 172 */     return getValidationMessageImpl(getPeer());
/*     */   }
/*     */   static native String getValidationMessageImpl(long paramLong);
/*     */ 
/*     */   public int getSelectionStart() {
/* 177 */     return getSelectionStartImpl(getPeer());
/*     */   }
/*     */   static native int getSelectionStartImpl(long paramLong);
/*     */ 
/*     */   public void setSelectionStart(int value) {
/* 182 */     setSelectionStartImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSelectionStartImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public int getSelectionEnd() {
/* 187 */     return getSelectionEndImpl(getPeer());
/*     */   }
/*     */   static native int getSelectionEndImpl(long paramLong);
/*     */ 
/*     */   public void setSelectionEnd(int value) {
/* 192 */     setSelectionEndImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSelectionEndImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getSelectionDirection() {
/* 197 */     return getSelectionDirectionImpl(getPeer());
/*     */   }
/*     */   static native String getSelectionDirectionImpl(long paramLong);
/*     */ 
/*     */   public void setSelectionDirection(String value) {
/* 202 */     setSelectionDirectionImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSelectionDirectionImpl(long paramLong, String paramString);
/*     */ 
/*     */   public NodeList getLabels() {
/* 207 */     return NodeListImpl.getImpl(getLabelsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getLabelsImpl(long paramLong);
/*     */ 
/*     */   public void select()
/*     */   {
/* 215 */     selectImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void selectImpl(long paramLong);
/*     */ 
/*     */   public boolean checkValidity()
/*     */   {
/* 222 */     return checkValidityImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native boolean checkValidityImpl(long paramLong);
/*     */ 
/*     */   public void setCustomValidity(String error)
/*     */   {
/* 229 */     setCustomValidityImpl(getPeer(), error);
/*     */   }
/*     */ 
/*     */   static native void setCustomValidityImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void setSelectionRange(int start, int end, String direction)
/*     */   {
/* 240 */     setSelectionRangeImpl(getPeer(), start, end, direction);
/*     */   }
/*     */ 
/*     */   static native void setSelectionRangeImpl(long paramLong, int paramInt1, int paramInt2, String paramString);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLTextAreaElementImpl
 * JD-Core Version:    0.6.2
 */