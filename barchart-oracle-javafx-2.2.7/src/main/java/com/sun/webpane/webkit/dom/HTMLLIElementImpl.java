/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLLIElement;
/*    */ 
/*    */ public class HTMLLIElementImpl extends HTMLElementImpl
/*    */   implements HTMLLIElement
/*    */ {
/*    */   HTMLLIElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLLIElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLLIElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 19 */     return getTypeImpl(getPeer());
/*    */   }
/*    */   static native String getTypeImpl(long paramLong);
/*    */ 
/*    */   public void setType(String value) {
/* 24 */     setTypeImpl(getPeer(), value);
/*    */   }
/*    */   static native void setTypeImpl(long paramLong, String paramString);
/*    */ 
/*    */   public int getValue() {
/* 29 */     return getValueImpl(getPeer());
/*    */   }
/*    */   static native int getValueImpl(long paramLong);
/*    */ 
/*    */   public void setValue(int value) {
/* 34 */     setValueImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setValueImpl(long paramLong, int paramInt);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLLIElementImpl
 * JD-Core Version:    0.6.2
 */