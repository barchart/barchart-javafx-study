/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLFormElement;
/*    */ import org.w3c.dom.html.HTMLLegendElement;
/*    */ 
/*    */ public class HTMLLegendElementImpl extends HTMLElementImpl
/*    */   implements HTMLLegendElement
/*    */ {
/*    */   HTMLLegendElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLLegendElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (HTMLLegendElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public HTMLFormElement getForm()
/*    */   {
/* 20 */     return HTMLFormElementImpl.getImpl(getFormImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getFormImpl(long paramLong);
/*    */ 
/*    */   public String getAlign() {
/* 25 */     return getAlignImpl(getPeer());
/*    */   }
/*    */   static native String getAlignImpl(long paramLong);
/*    */ 
/*    */   public void setAlign(String value) {
/* 30 */     setAlignImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setAlignImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLLegendElementImpl
 * JD-Core Version:    0.6.2
 */