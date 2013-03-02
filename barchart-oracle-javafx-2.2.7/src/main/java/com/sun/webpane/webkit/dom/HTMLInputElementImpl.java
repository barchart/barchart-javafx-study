/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.html.HTMLElement;
/*     */ import org.w3c.dom.html.HTMLFormElement;
/*     */ import org.w3c.dom.html.HTMLInputElement;
/*     */ import org.w3c.dom.html.HTMLOptionElement;
/*     */ 
/*     */ public class HTMLInputElementImpl extends HTMLElementImpl
/*     */   implements HTMLInputElement
/*     */ {
/*     */   HTMLInputElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  14 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLInputElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  18 */     return (HTMLInputElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getDefaultValue()
/*     */   {
/*  24 */     return getDefaultValueImpl(getPeer());
/*     */   }
/*     */   static native String getDefaultValueImpl(long paramLong);
/*     */ 
/*     */   public void setDefaultValue(String value) {
/*  29 */     setDefaultValueImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDefaultValueImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getDefaultChecked() {
/*  34 */     return getDefaultCheckedImpl(getPeer());
/*     */   }
/*     */   static native boolean getDefaultCheckedImpl(long paramLong);
/*     */ 
/*     */   public void setDefaultChecked(boolean value) {
/*  39 */     setDefaultCheckedImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDefaultCheckedImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getDirName() {
/*  44 */     return getDirNameImpl(getPeer());
/*     */   }
/*     */   static native String getDirNameImpl(long paramLong);
/*     */ 
/*     */   public void setDirName(String value) {
/*  49 */     setDirNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDirNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public HTMLFormElement getForm() {
/*  54 */     return HTMLFormElementImpl.getImpl(getFormImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getFormImpl(long paramLong);
/*     */ 
/*     */   public String getFormAction() {
/*  59 */     return getFormActionImpl(getPeer());
/*     */   }
/*     */   static native String getFormActionImpl(long paramLong);
/*     */ 
/*     */   public void setFormAction(String value) {
/*  64 */     setFormActionImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFormActionImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getFormEnctype() {
/*  69 */     return getFormEnctypeImpl(getPeer());
/*     */   }
/*     */   static native String getFormEnctypeImpl(long paramLong);
/*     */ 
/*     */   public void setFormEnctype(String value) {
/*  74 */     setFormEnctypeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFormEnctypeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getFormMethod() {
/*  79 */     return getFormMethodImpl(getPeer());
/*     */   }
/*     */   static native String getFormMethodImpl(long paramLong);
/*     */ 
/*     */   public void setFormMethod(String value) {
/*  84 */     setFormMethodImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFormMethodImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getFormNoValidate() {
/*  89 */     return getFormNoValidateImpl(getPeer());
/*     */   }
/*     */   static native boolean getFormNoValidateImpl(long paramLong);
/*     */ 
/*     */   public void setFormNoValidate(boolean value) {
/*  94 */     setFormNoValidateImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFormNoValidateImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getFormTarget() {
/*  99 */     return getFormTargetImpl(getPeer());
/*     */   }
/*     */   static native String getFormTargetImpl(long paramLong);
/*     */ 
/*     */   public void setFormTarget(String value) {
/* 104 */     setFormTargetImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFormTargetImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAccept() {
/* 109 */     return getAcceptImpl(getPeer());
/*     */   }
/*     */   static native String getAcceptImpl(long paramLong);
/*     */ 
/*     */   public void setAccept(String value) {
/* 114 */     setAcceptImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAcceptImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAlign() {
/* 119 */     return getAlignImpl(getPeer());
/*     */   }
/*     */   static native String getAlignImpl(long paramLong);
/*     */ 
/*     */   public void setAlign(String value) {
/* 124 */     setAlignImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAlignImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAlt() {
/* 129 */     return getAltImpl(getPeer());
/*     */   }
/*     */   static native String getAltImpl(long paramLong);
/*     */ 
/*     */   public void setAlt(String value) {
/* 134 */     setAltImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAltImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getChecked() {
/* 139 */     return getCheckedImpl(getPeer());
/*     */   }
/*     */   static native boolean getCheckedImpl(long paramLong);
/*     */ 
/*     */   public void setChecked(boolean value) {
/* 144 */     setCheckedImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCheckedImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean getDisabled() {
/* 149 */     return getDisabledImpl(getPeer());
/*     */   }
/*     */   static native boolean getDisabledImpl(long paramLong);
/*     */ 
/*     */   public void setDisabled(boolean value) {
/* 154 */     setDisabledImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDisabledImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean getAutofocus() {
/* 159 */     return getAutofocusImpl(getPeer());
/*     */   }
/*     */   static native boolean getAutofocusImpl(long paramLong);
/*     */ 
/*     */   public void setAutofocus(boolean value) {
/* 164 */     setAutofocusImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAutofocusImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getAutocomplete() {
/* 169 */     return getAutocompleteImpl(getPeer());
/*     */   }
/*     */   static native String getAutocompleteImpl(long paramLong);
/*     */ 
/*     */   public void setAutocomplete(String value) {
/* 174 */     setAutocompleteImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAutocompleteImpl(long paramLong, String paramString);
/*     */ 
/*     */   public HTMLElement getList() {
/* 179 */     return HTMLElementImpl.getImpl(getListImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getListImpl(long paramLong);
/*     */ 
/*     */   public String getMax() {
/* 184 */     return getMaxImpl(getPeer());
/*     */   }
/*     */   static native String getMaxImpl(long paramLong);
/*     */ 
/*     */   public void setMax(String value) {
/* 189 */     setMaxImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMaxImpl(long paramLong, String paramString);
/*     */ 
/*     */   public int getMaxLength() {
/* 194 */     return getMaxLengthImpl(getPeer());
/*     */   }
/*     */   static native int getMaxLengthImpl(long paramLong);
/*     */ 
/*     */   public void setMaxLength(int value) throws DOMException {
/* 199 */     setMaxLengthImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMaxLengthImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getMin() {
/* 204 */     return getMinImpl(getPeer());
/*     */   }
/*     */   static native String getMinImpl(long paramLong);
/*     */ 
/*     */   public void setMin(String value) {
/* 209 */     setMinImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMinImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getMultiple() {
/* 214 */     return getMultipleImpl(getPeer());
/*     */   }
/*     */   static native boolean getMultipleImpl(long paramLong);
/*     */ 
/*     */   public void setMultiple(boolean value) {
/* 219 */     setMultipleImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMultipleImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getName() {
/* 224 */     return getNameImpl(getPeer());
/*     */   }
/*     */   static native String getNameImpl(long paramLong);
/*     */ 
/*     */   public void setName(String value) {
/* 229 */     setNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getPattern() {
/* 234 */     return getPatternImpl(getPeer());
/*     */   }
/*     */   static native String getPatternImpl(long paramLong);
/*     */ 
/*     */   public void setPattern(String value) {
/* 239 */     setPatternImpl(getPeer(), value);
/*     */   }
/*     */   static native void setPatternImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getPlaceholder() {
/* 244 */     return getPlaceholderImpl(getPeer());
/*     */   }
/*     */   static native String getPlaceholderImpl(long paramLong);
/*     */ 
/*     */   public void setPlaceholder(String value) {
/* 249 */     setPlaceholderImpl(getPeer(), value);
/*     */   }
/*     */   static native void setPlaceholderImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getReadOnly() {
/* 254 */     return getReadOnlyImpl(getPeer());
/*     */   }
/*     */   static native boolean getReadOnlyImpl(long paramLong);
/*     */ 
/*     */   public void setReadOnly(boolean value) {
/* 259 */     setReadOnlyImpl(getPeer(), value);
/*     */   }
/*     */   static native void setReadOnlyImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean getRequired() {
/* 264 */     return getRequiredImpl(getPeer());
/*     */   }
/*     */   static native boolean getRequiredImpl(long paramLong);
/*     */ 
/*     */   public void setRequired(boolean value) {
/* 269 */     setRequiredImpl(getPeer(), value);
/*     */   }
/*     */   static native void setRequiredImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getSize() {
/* 274 */     return getSizeImpl(getPeer()) + "";
/*     */   }
/*     */   static native int getSizeImpl(long paramLong);
/*     */ 
/*     */   public void setSize(String value) {
/* 279 */     setSizeImpl(getPeer(), Integer.parseInt(value));
/*     */   }
/*     */   static native void setSizeImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public String getSrc() {
/* 284 */     return getSrcImpl(getPeer());
/*     */   }
/*     */   static native String getSrcImpl(long paramLong);
/*     */ 
/*     */   public void setSrc(String value) {
/* 289 */     setSrcImpl(getPeer(), value);
/*     */   }
/*     */   static native void setSrcImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getStep() {
/* 294 */     return getStepImpl(getPeer());
/*     */   }
/*     */   static native String getStepImpl(long paramLong);
/*     */ 
/*     */   public void setStep(String value) {
/* 299 */     setStepImpl(getPeer(), value);
/*     */   }
/*     */   static native void setStepImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getType() {
/* 304 */     return getTypeImpl(getPeer());
/*     */   }
/*     */   static native String getTypeImpl(long paramLong);
/*     */ 
/*     */   public void setType(String value) {
/* 309 */     setTypeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTypeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getUseMap() {
/* 314 */     return getUseMapImpl(getPeer());
/*     */   }
/*     */   static native String getUseMapImpl(long paramLong);
/*     */ 
/*     */   public void setUseMap(String value) {
/* 319 */     setUseMapImpl(getPeer(), value);
/*     */   }
/*     */   static native void setUseMapImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getValue() {
/* 324 */     return getValueImpl(getPeer());
/*     */   }
/*     */   static native String getValueImpl(long paramLong);
/*     */ 
/*     */   public void setValue(String value) {
/* 329 */     setValueImpl(getPeer(), value);
/*     */   }
/*     */   static native void setValueImpl(long paramLong, String paramString);
/*     */ 
/*     */   public long getValueAsDate() {
/* 334 */     return getValueAsDateImpl(getPeer());
/*     */   }
/*     */   static native long getValueAsDateImpl(long paramLong);
/*     */ 
/*     */   public void setValueAsDate(long value) throws DOMException {
/* 339 */     setValueAsDateImpl(getPeer(), value);
/*     */   }
/*     */   static native void setValueAsDateImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public double getValueAsNumber() {
/* 344 */     return getValueAsNumberImpl(getPeer());
/*     */   }
/*     */   static native double getValueAsNumberImpl(long paramLong);
/*     */ 
/*     */   public void setValueAsNumber(double value) throws DOMException {
/* 349 */     setValueAsNumberImpl(getPeer(), value);
/*     */   }
/*     */   static native void setValueAsNumberImpl(long paramLong, double paramDouble);
/*     */ 
/*     */   public HTMLOptionElement getSelectedOption() {
/* 354 */     return HTMLOptionElementImpl.getImpl(getSelectedOptionImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getSelectedOptionImpl(long paramLong);
/*     */ 
/*     */   public boolean getIncremental() {
/* 359 */     return getIncrementalImpl(getPeer());
/*     */   }
/*     */   static native boolean getIncrementalImpl(long paramLong);
/*     */ 
/*     */   public void setIncremental(boolean value) {
/* 364 */     setIncrementalImpl(getPeer(), value);
/*     */   }
/*     */   static native void setIncrementalImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean getWillValidate() {
/* 369 */     return getWillValidateImpl(getPeer());
/*     */   }
/*     */   static native boolean getWillValidateImpl(long paramLong);
/*     */ 
/*     */   public String getValidationMessage() {
/* 374 */     return getValidationMessageImpl(getPeer());
/*     */   }
/*     */   static native String getValidationMessageImpl(long paramLong);
/*     */ 
/*     */   public boolean getIndeterminate() {
/* 379 */     return getIndeterminateImpl(getPeer());
/*     */   }
/*     */   static native boolean getIndeterminateImpl(long paramLong);
/*     */ 
/*     */   public void setIndeterminate(boolean value) {
/* 384 */     setIndeterminateImpl(getPeer(), value);
/*     */   }
/*     */   static native void setIndeterminateImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public NodeList getLabels() {
/* 389 */     return NodeListImpl.getImpl(getLabelsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getLabelsImpl(long paramLong);
/*     */ 
/*     */   public void stepUp(int n)
/*     */     throws DOMException
/*     */   {
/* 397 */     stepUpImpl(getPeer(), n);
/*     */   }
/*     */ 
/*     */   static native void stepUpImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public void stepDown(int n)
/*     */     throws DOMException
/*     */   {
/* 406 */     stepDownImpl(getPeer(), n);
/*     */   }
/*     */ 
/*     */   static native void stepDownImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public boolean checkValidity()
/*     */   {
/* 415 */     return checkValidityImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native boolean checkValidityImpl(long paramLong);
/*     */ 
/*     */   public void setCustomValidity(String error)
/*     */   {
/* 422 */     setCustomValidityImpl(getPeer(), error);
/*     */   }
/*     */ 
/*     */   static native void setCustomValidityImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void select()
/*     */   {
/* 431 */     selectImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void selectImpl(long paramLong);
/*     */ 
/*     */   public void click()
/*     */   {
/* 438 */     clickImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void clickImpl(long paramLong);
/*     */ 
/*     */   public void setValueForUser(String value)
/*     */   {
/* 445 */     setValueForUserImpl(getPeer(), value);
/*     */   }
/*     */ 
/*     */   static native void setValueForUserImpl(long paramLong, String paramString);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLInputElementImpl
 * JD-Core Version:    0.6.2
 */