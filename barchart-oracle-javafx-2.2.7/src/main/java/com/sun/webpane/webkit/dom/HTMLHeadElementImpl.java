/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLHeadElement;
/*    */ 
/*    */ public class HTMLHeadElementImpl extends HTMLElementImpl
/*    */   implements HTMLHeadElement
/*    */ {
/*    */   HTMLHeadElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLHeadElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLHeadElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getProfile()
/*    */   {
/* 19 */     return getProfileImpl(getPeer());
/*    */   }
/*    */   static native String getProfileImpl(long paramLong);
/*    */ 
/*    */   public void setProfile(String value) {
/* 24 */     setProfileImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setProfileImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLHeadElementImpl
 * JD-Core Version:    0.6.2
 */