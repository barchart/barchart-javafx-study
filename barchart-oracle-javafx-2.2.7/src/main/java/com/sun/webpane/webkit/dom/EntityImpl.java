/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.Entity;
/*    */ 
/*    */ public class EntityImpl extends NodeImpl
/*    */   implements Entity
/*    */ {
/*    */   EntityImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static Entity getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (Entity)create(peer, contextPeer, rootPeer);
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
/*    */   static native String getSystemIdImpl(long paramLong);
/*    */ 
/*    */   public String getNotationName() {
/* 29 */     return getNotationNameImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native String getNotationNameImpl(long paramLong);
/*    */ 
/*    */   public String getInputEncoding()
/*    */   {
/* 36 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */   public String getXmlVersion() {
/* 39 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */   public String getXmlEncoding() {
/* 42 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.EntityImpl
 * JD-Core Version:    0.6.2
 */