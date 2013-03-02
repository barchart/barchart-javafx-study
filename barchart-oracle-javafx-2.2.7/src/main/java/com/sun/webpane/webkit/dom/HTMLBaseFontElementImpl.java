/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLBaseFontElement;
/*    */ 
/*    */ public class HTMLBaseFontElementImpl extends HTMLElementImpl
/*    */   implements HTMLBaseFontElement
/*    */ {
/*    */   HTMLBaseFontElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLBaseFontElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLBaseFontElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getColor()
/*    */   {
/* 19 */     return getColorImpl(getPeer());
/*    */   }
/*    */   static native String getColorImpl(long paramLong);
/*    */ 
/*    */   public void setColor(String value) {
/* 24 */     setColorImpl(getPeer(), value);
/*    */   }
/*    */   static native void setColorImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getFace() {
/* 29 */     return getFaceImpl(getPeer());
/*    */   }
/*    */   static native String getFaceImpl(long paramLong);
/*    */ 
/*    */   public void setFace(String value) {
/* 34 */     setFaceImpl(getPeer(), value);
/*    */   }
/*    */   static native void setFaceImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getSize() {
/* 39 */     return getSizeImpl(getPeer()) + "";
/*    */   }
/*    */   static native int getSizeImpl(long paramLong);
/*    */ 
/*    */   public void setSize(String value) {
/* 44 */     setSizeImpl(getPeer(), Integer.parseInt(value));
/*    */   }
/*    */ 
/*    */   static native void setSizeImpl(long paramLong, int paramInt);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLBaseFontElementImpl
 * JD-Core Version:    0.6.2
 */