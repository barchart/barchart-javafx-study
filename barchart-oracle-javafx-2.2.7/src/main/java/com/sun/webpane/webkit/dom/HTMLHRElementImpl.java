/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLHRElement;
/*    */ 
/*    */ public class HTMLHRElementImpl extends HTMLElementImpl
/*    */   implements HTMLHRElement
/*    */ {
/*    */   HTMLHRElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLHRElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLHRElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getAlign()
/*    */   {
/* 19 */     return getAlignImpl(getPeer());
/*    */   }
/*    */   static native String getAlignImpl(long paramLong);
/*    */ 
/*    */   public void setAlign(String value) {
/* 24 */     setAlignImpl(getPeer(), value);
/*    */   }
/*    */   static native void setAlignImpl(long paramLong, String paramString);
/*    */ 
/*    */   public boolean getNoShade() {
/* 29 */     return getNoShadeImpl(getPeer());
/*    */   }
/*    */   static native boolean getNoShadeImpl(long paramLong);
/*    */ 
/*    */   public void setNoShade(boolean value) {
/* 34 */     setNoShadeImpl(getPeer(), value);
/*    */   }
/*    */   static native void setNoShadeImpl(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   public String getSize() {
/* 39 */     return getSizeImpl(getPeer());
/*    */   }
/*    */   static native String getSizeImpl(long paramLong);
/*    */ 
/*    */   public void setSize(String value) {
/* 44 */     setSizeImpl(getPeer(), value);
/*    */   }
/*    */   static native void setSizeImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getWidth() {
/* 49 */     return getWidthImpl(getPeer());
/*    */   }
/*    */   static native String getWidthImpl(long paramLong);
/*    */ 
/*    */   public void setWidth(String value) {
/* 54 */     setWidthImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setWidthImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLHRElementImpl
 * JD-Core Version:    0.6.2
 */