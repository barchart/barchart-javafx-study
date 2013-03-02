/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLFieldSetElement;
/*    */ import org.w3c.dom.html.HTMLFormElement;
/*    */ 
/*    */ public class HTMLFieldSetElementImpl extends HTMLElementImpl
/*    */   implements HTMLFieldSetElement
/*    */ {
/*    */   HTMLFieldSetElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLFieldSetElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (HTMLFieldSetElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public HTMLFormElement getForm()
/*    */   {
/* 20 */     return HTMLFormElementImpl.getImpl(getFormImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getFormImpl(long paramLong);
/*    */ 
/*    */   public boolean getWillValidate() {
/* 25 */     return getWillValidateImpl(getPeer());
/*    */   }
/*    */   static native boolean getWillValidateImpl(long paramLong);
/*    */ 
/*    */   public String getValidationMessage() {
/* 30 */     return getValidationMessageImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native String getValidationMessageImpl(long paramLong);
/*    */ 
/*    */   public boolean checkValidity()
/*    */   {
/* 38 */     return checkValidityImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native boolean checkValidityImpl(long paramLong);
/*    */ 
/*    */   public void setCustomValidity(String error)
/*    */   {
/* 45 */     setCustomValidityImpl(getPeer(), error);
/*    */   }
/*    */ 
/*    */   static native void setCustomValidityImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLFieldSetElementImpl
 * JD-Core Version:    0.6.2
 */