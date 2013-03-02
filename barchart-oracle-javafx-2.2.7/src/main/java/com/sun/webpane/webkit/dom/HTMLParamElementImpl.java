/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLParamElement;
/*    */ 
/*    */ public class HTMLParamElementImpl extends HTMLElementImpl
/*    */   implements HTMLParamElement
/*    */ {
/*    */   HTMLParamElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLParamElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLParamElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 19 */     return getNameImpl(getPeer());
/*    */   }
/*    */   static native String getNameImpl(long paramLong);
/*    */ 
/*    */   public void setName(String value) {
/* 24 */     setNameImpl(getPeer(), value);
/*    */   }
/*    */   static native void setNameImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getType() {
/* 29 */     return getTypeImpl(getPeer());
/*    */   }
/*    */   static native String getTypeImpl(long paramLong);
/*    */ 
/*    */   public void setType(String value) {
/* 34 */     setTypeImpl(getPeer(), value);
/*    */   }
/*    */   static native void setTypeImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getValue() {
/* 39 */     return getValueImpl(getPeer());
/*    */   }
/*    */   static native String getValueImpl(long paramLong);
/*    */ 
/*    */   public void setValue(String value) {
/* 44 */     setValueImpl(getPeer(), value);
/*    */   }
/*    */   static native void setValueImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getValueType() {
/* 49 */     return getValueTypeImpl(getPeer());
/*    */   }
/*    */   static native String getValueTypeImpl(long paramLong);
/*    */ 
/*    */   public void setValueType(String value) {
/* 54 */     setValueTypeImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setValueTypeImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLParamElementImpl
 * JD-Core Version:    0.6.2
 */