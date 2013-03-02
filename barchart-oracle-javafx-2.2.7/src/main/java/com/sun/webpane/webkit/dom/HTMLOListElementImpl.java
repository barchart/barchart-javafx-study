/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLOListElement;
/*    */ 
/*    */ public class HTMLOListElementImpl extends HTMLElementImpl
/*    */   implements HTMLOListElement
/*    */ {
/*    */   HTMLOListElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLOListElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLOListElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public boolean getCompact()
/*    */   {
/* 19 */     return getCompactImpl(getPeer());
/*    */   }
/*    */   static native boolean getCompactImpl(long paramLong);
/*    */ 
/*    */   public void setCompact(boolean value) {
/* 24 */     setCompactImpl(getPeer(), value);
/*    */   }
/*    */   static native void setCompactImpl(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   public int getStart() {
/* 29 */     return getStartImpl(getPeer());
/*    */   }
/*    */   static native int getStartImpl(long paramLong);
/*    */ 
/*    */   public void setStart(int value) {
/* 34 */     setStartImpl(getPeer(), value);
/*    */   }
/*    */   static native void setStartImpl(long paramLong, int paramInt);
/*    */ 
/*    */   public boolean getReversed() {
/* 39 */     return getReversedImpl(getPeer());
/*    */   }
/*    */   static native boolean getReversedImpl(long paramLong);
/*    */ 
/*    */   public void setReversed(boolean value) {
/* 44 */     setReversedImpl(getPeer(), value);
/*    */   }
/*    */   static native void setReversedImpl(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   public String getType() {
/* 49 */     return getTypeImpl(getPeer());
/*    */   }
/*    */   static native String getTypeImpl(long paramLong);
/*    */ 
/*    */   public void setType(String value) {
/* 54 */     setTypeImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setTypeImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLOListElementImpl
 * JD-Core Version:    0.6.2
 */