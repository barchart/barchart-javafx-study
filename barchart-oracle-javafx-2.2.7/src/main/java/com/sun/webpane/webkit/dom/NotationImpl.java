/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.Notation;
/*    */ 
/*    */ public class NotationImpl extends NodeImpl
/*    */   implements Notation
/*    */ {
/*    */   NotationImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static Notation getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (Notation)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getPublicId()
/*    */   {
/* 19 */     return getPublicIdImpl(getPeer());
/*    */   }
/*    */   static native String getPublicIdImpl(long paramLong);
/*    */ 
/*    */   public String getSystemId() {
/* 24 */     return getSystemIdImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native String getSystemIdImpl(long paramLong);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.NotationImpl
 * JD-Core Version:    0.6.2
 */