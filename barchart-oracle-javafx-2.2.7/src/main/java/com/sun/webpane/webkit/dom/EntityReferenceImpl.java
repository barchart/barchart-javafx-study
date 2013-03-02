/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.EntityReference;
/*    */ 
/*    */ public class EntityReferenceImpl extends NodeImpl
/*    */   implements EntityReference
/*    */ {
/*    */   EntityReferenceImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static EntityReference getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (EntityReference)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.EntityReferenceImpl
 * JD-Core Version:    0.6.2
 */