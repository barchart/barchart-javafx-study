/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLOptGroupElement;
/*    */ 
/*    */ public class HTMLOptGroupElementImpl extends HTMLElementImpl
/*    */   implements HTMLOptGroupElement
/*    */ {
/*    */   HTMLOptGroupElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLOptGroupElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLOptGroupElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public boolean getDisabled()
/*    */   {
/* 19 */     return getDisabledImpl(getPeer());
/*    */   }
/*    */   static native boolean getDisabledImpl(long paramLong);
/*    */ 
/*    */   public void setDisabled(boolean value) {
/* 24 */     setDisabledImpl(getPeer(), value);
/*    */   }
/*    */   static native void setDisabledImpl(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   public String getLabel() {
/* 29 */     return getLabelImpl(getPeer());
/*    */   }
/*    */   static native String getLabelImpl(long paramLong);
/*    */ 
/*    */   public void setLabel(String value) {
/* 34 */     setLabelImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setLabelImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLOptGroupElementImpl
 * JD-Core Version:    0.6.2
 */