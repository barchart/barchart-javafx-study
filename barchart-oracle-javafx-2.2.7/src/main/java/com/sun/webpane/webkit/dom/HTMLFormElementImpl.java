/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.html.HTMLCollection;
/*     */ import org.w3c.dom.html.HTMLFormElement;
/*     */ 
/*     */ public class HTMLFormElementImpl extends HTMLElementImpl
/*     */   implements HTMLFormElement
/*     */ {
/*     */   HTMLFormElementImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  10 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static HTMLFormElement getImpl(long peer, long contextPeer, long rootPeer) {
/*  14 */     return (HTMLFormElement)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public HTMLCollection getElements()
/*     */   {
/*  20 */     return HTMLCollectionImpl.getImpl(getElementsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getElementsImpl(long paramLong);
/*     */ 
/*     */   public int getLength() {
/*  25 */     return getLengthImpl(getPeer());
/*     */   }
/*     */   static native int getLengthImpl(long paramLong);
/*     */ 
/*     */   public String getName() {
/*  30 */     return getNameImpl(getPeer());
/*     */   }
/*     */   static native String getNameImpl(long paramLong);
/*     */ 
/*     */   public void setName(String value) {
/*  35 */     setNameImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNameImpl(long paramLong, String paramString);
/*     */ 
/*     */   public boolean getNoValidate() {
/*  40 */     return getNoValidateImpl(getPeer());
/*     */   }
/*     */   static native boolean getNoValidateImpl(long paramLong);
/*     */ 
/*     */   public void setNoValidate(boolean value) {
/*  45 */     setNoValidateImpl(getPeer(), value);
/*     */   }
/*     */   static native void setNoValidateImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public String getAcceptCharset() {
/*  50 */     return getAcceptCharsetImpl(getPeer());
/*     */   }
/*     */   static native String getAcceptCharsetImpl(long paramLong);
/*     */ 
/*     */   public void setAcceptCharset(String value) {
/*  55 */     setAcceptCharsetImpl(getPeer(), value);
/*     */   }
/*     */   static native void setAcceptCharsetImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAction() {
/*  60 */     return getActionImpl(getPeer());
/*     */   }
/*     */   static native String getActionImpl(long paramLong);
/*     */ 
/*     */   public void setAction(String value) {
/*  65 */     setActionImpl(getPeer(), value);
/*     */   }
/*     */   static native void setActionImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getEncoding() {
/*  70 */     return getEncodingImpl(getPeer());
/*     */   }
/*     */   static native String getEncodingImpl(long paramLong);
/*     */ 
/*     */   public void setEncoding(String value) {
/*  75 */     setEncodingImpl(getPeer(), value);
/*     */   }
/*     */   static native void setEncodingImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getEnctype() {
/*  80 */     return getEnctypeImpl(getPeer());
/*     */   }
/*     */   static native String getEnctypeImpl(long paramLong);
/*     */ 
/*     */   public void setEnctype(String value) {
/*  85 */     setEnctypeImpl(getPeer(), value);
/*     */   }
/*     */   static native void setEnctypeImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getMethod() {
/*  90 */     return getMethodImpl(getPeer());
/*     */   }
/*     */   static native String getMethodImpl(long paramLong);
/*     */ 
/*     */   public void setMethod(String value) {
/*  95 */     setMethodImpl(getPeer(), value);
/*     */   }
/*     */   static native void setMethodImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getTarget() {
/* 100 */     return getTargetImpl(getPeer());
/*     */   }
/*     */   static native String getTargetImpl(long paramLong);
/*     */ 
/*     */   public void setTarget(String value) {
/* 105 */     setTargetImpl(getPeer(), value);
/*     */   }
/*     */   static native void setTargetImpl(long paramLong, String paramString);
/*     */ 
/*     */   public String getAutocomplete() {
/* 110 */     return getAutocompleteImpl(getPeer());
/*     */   }
/*     */   static native String getAutocompleteImpl(long paramLong);
/*     */ 
/*     */   public void setAutocomplete(String value) {
/* 115 */     setAutocompleteImpl(getPeer(), value);
/*     */   }
/*     */ 
/*     */   static native void setAutocompleteImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void submit()
/*     */   {
/* 123 */     submitImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void submitImpl(long paramLong);
/*     */ 
/*     */   public void reset()
/*     */   {
/* 130 */     resetImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void resetImpl(long paramLong);
/*     */ 
/*     */   public boolean checkValidity()
/*     */   {
/* 137 */     return checkValidityImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native boolean checkValidityImpl(long paramLong);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLFormElementImpl
 * JD-Core Version:    0.6.2
 */