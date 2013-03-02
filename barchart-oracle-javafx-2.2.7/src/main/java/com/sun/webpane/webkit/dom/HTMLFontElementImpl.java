/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLFontElement;
/*    */ 
/*    */ public class HTMLFontElementImpl extends HTMLElementImpl
/*    */   implements HTMLFontElement
/*    */ {
/*    */   HTMLFontElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLFontElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLFontElement)create(peer, contextPeer, rootPeer);
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
/* 39 */     return getSizeImpl(getPeer());
/*    */   }
/*    */   static native String getSizeImpl(long paramLong);
/*    */ 
/*    */   public void setSize(String value) {
/* 44 */     setSizeImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setSizeImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLFontElementImpl
 * JD-Core Version:    0.6.2
 */