/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLElement;
/*    */ import org.w3c.dom.html.HTMLFormElement;
/*    */ import org.w3c.dom.html.HTMLLabelElement;
/*    */ 
/*    */ public class HTMLLabelElementImpl extends HTMLElementImpl
/*    */   implements HTMLLabelElement
/*    */ {
/*    */   HTMLLabelElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 11 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLLabelElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 15 */     return (HTMLLabelElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public HTMLFormElement getForm()
/*    */   {
/* 21 */     return HTMLFormElementImpl.getImpl(getFormImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getFormImpl(long paramLong);
/*    */ 
/*    */   public String getHtmlFor() {
/* 26 */     return getHtmlForImpl(getPeer());
/*    */   }
/*    */   static native String getHtmlForImpl(long paramLong);
/*    */ 
/*    */   public void setHtmlFor(String value) {
/* 31 */     setHtmlForImpl(getPeer(), value);
/*    */   }
/*    */   static native void setHtmlForImpl(long paramLong, String paramString);
/*    */ 
/*    */   public HTMLElement getControl() {
/* 36 */     return HTMLElementImpl.getImpl(getControlImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long getControlImpl(long paramLong);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLLabelElementImpl
 * JD-Core Version:    0.6.2
 */