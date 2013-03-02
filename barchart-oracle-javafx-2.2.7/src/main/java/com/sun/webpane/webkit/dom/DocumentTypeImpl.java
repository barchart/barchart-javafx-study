/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.DocumentType;
/*    */ import org.w3c.dom.NamedNodeMap;
/*    */ 
/*    */ public class DocumentTypeImpl extends NodeImpl
/*    */   implements DocumentType
/*    */ {
/*    */   DocumentTypeImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static DocumentType getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (DocumentType)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 20 */     return getNameImpl(getPeer());
/*    */   }
/*    */   static native String getNameImpl(long paramLong);
/*    */ 
/*    */   public NamedNodeMap getEntities() {
/* 25 */     return NamedNodeMapImpl.getImpl(getEntitiesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getEntitiesImpl(long paramLong);
/*    */ 
/*    */   public NamedNodeMap getNotations() {
/* 30 */     return NamedNodeMapImpl.getImpl(getNotationsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getNotationsImpl(long paramLong);
/*    */ 
/*    */   public String getPublicId() {
/* 35 */     return getPublicIdImpl(getPeer());
/*    */   }
/*    */   static native String getPublicIdImpl(long paramLong);
/*    */ 
/*    */   public String getSystemId() {
/* 40 */     return getSystemIdImpl(getPeer());
/*    */   }
/*    */   static native String getSystemIdImpl(long paramLong);
/*    */ 
/*    */   public String getInternalSubset() {
/* 45 */     return getInternalSubsetImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native String getInternalSubsetImpl(long paramLong);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.DocumentTypeImpl
 * JD-Core Version:    0.6.2
 */