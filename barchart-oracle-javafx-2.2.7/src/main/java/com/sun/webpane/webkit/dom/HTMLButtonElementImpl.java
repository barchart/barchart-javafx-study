/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.html.HTMLButtonElement;
/*     */ import org.w3c.dom.html.HTMLFormElement;
/*     */ 
/*     */ public class HTMLButtonElementImpl extends HTMLElementImpl
/*     */   implements HTMLButtonElement
/*     */ {
/*     */   HTMLButtonElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  11 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLButtonElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  15 */     return (HTMLButtonElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public HTMLFormElement getForm()
/*     */   {
/*  21 */     return HTMLFormElementImpl.getImpl(getFormImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getFormImpl(long paramLong);
/*     */ 
/*     */   public String getFormAction() {
/*  26 */     return getFormActionImpl(getPeer());
/*     */   }
/*     */   static native String getFormActionImpl(long paramLong);
/*     */ 
/*     */   public void setFormAction(String value) {
/*  31 */     setFormActionImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFormActionImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getFormEnctype() {
/*  36 */     return getFormEnctypeImpl(getPeer());
/*     */   }
/*     */   static native String getFormEnctypeImpl(long paramLong);
/*     */ 
/*     */   public void setFormEnctype(String value) {
/*  41 */     setFormEnctypeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFormEnctypeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getFormMethod() {
/*  46 */     return getFormMethodImpl(getPeer());
/*     */   }
/*     */   static native String getFormMethodImpl(long paramLong);
/*     */ 
/*     */   public void setFormMethod(String value) {
/*  51 */     setFormMethodImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFormMethodImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getFormNoValidate() {
/*  56 */     return getFormNoValidateImpl(getPeer());
/*     */   }
/*     */   static native boolean getFormNoValidateImpl(long paramLong);
/*     */ 
/*     */   public void setFormNoValidate(boolean value) {
/*  61 */     setFormNoValidateImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFormNoValidateImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getFormTarget() {
/*  66 */     return getFormTargetImpl(getPeer());
/*     */   }
/*     */   static native String getFormTargetImpl(long paramLong);
/*     */ 
/*     */   public void setFormTarget(String value) {
/*  71 */     setFormTargetImpl(getPeer(), value);
/*     */   }
/*     */   static native void setFormTargetImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getDisabled() {
/*  76 */     return getDisabledImpl(getPeer());
/*     */   }
/*     */   static native boolean getDisabledImpl(long paramLong);
/*     */ 
/*     */   public void setDisabled(boolean value) {
/*  81 */     setDisabledImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDisabledImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean getAutofocus() {
/*  86 */     return getAutofocusImpl(getPeer());
/*     */   }
/*     */   static native boolean getAutofocusImpl(long paramLong);
/*     */ 
/*     */   public void setAutofocus(boolean value) {
/*  91 */     setAutofocusImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAutofocusImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getName() {
/*  96 */     return getNameImpl(getPeer());
/*     */   }
/*     */   static native String getNameImpl(long paramLong);
/*     */ 
/*     */   public void setName(String value) {
/* 101 */     setNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getType() {
/* 106 */     return getTypeImpl(getPeer());
/*     */   }
/*     */   static native String getTypeImpl(long paramLong);
/*     */ 
/*     */   public String getValue() {
/* 111 */     return getValueImpl(getPeer());
/*     */   }
/*     */   static native String getValueImpl(long paramLong);
/*     */ 
/*     */   public void setValue(String value) {
/* 116 */     setValueImpl(getPeer(), value);
/*     */   }
/*     */   static native void setValueImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getWillValidate() {
/* 121 */     return getWillValidateImpl(getPeer());
/*     */   }
/*     */   static native boolean getWillValidateImpl(long paramLong);
/*     */ 
/*     */   public String getValidationMessage() {
/* 126 */     return getValidationMessageImpl(getPeer());
/*     */   }
/*     */   static native String getValidationMessageImpl(long paramLong);
/*     */ 
/*     */   public NodeList getLabels() {
/* 131 */     return NodeListImpl.getImpl(getLabelsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getLabelsImpl(long paramLong);
/*     */ 
/*     */   public boolean checkValidity()
/*     */   {
/* 139 */     return checkValidityImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native boolean checkValidityImpl(long paramLong);
/*     */ 
/*     */   public void setCustomValidity(String error)
/*     */   {
/* 146 */     setCustomValidityImpl(getPeer(), error);
/*     */   }
/*     */ 
/*     */   static native void setCustomValidityImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void click()
/*     */   {
/* 155 */     clickImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void clickImpl(long paramLong);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLButtonElementImpl
 * JD-Core Version:    0.6.2
 */