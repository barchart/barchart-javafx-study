/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.events.MutationEvent;
/*    */ 
/*    */ public class MutationEventImpl extends EventImpl
/*    */   implements MutationEvent
/*    */ {
/*    */   public static final int MODIFICATION = 1;
/*    */   public static final int ADDITION = 2;
/*    */   public static final int REMOVAL = 3;
/*    */ 
/*    */   MutationEventImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static MutationEvent getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (MutationEvent)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public Node getRelatedNode()
/*    */   {
/* 25 */     return NodeImpl.getImpl(getRelatedNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getRelatedNodeImpl(long paramLong);
/*    */ 
/*    */   public String getPrevValue() {
/* 30 */     return getPrevValueImpl(getPeer());
/*    */   }
/*    */   static native String getPrevValueImpl(long paramLong);
/*    */ 
/*    */   public String getNewValue() {
/* 35 */     return getNewValueImpl(getPeer());
/*    */   }
/*    */   static native String getNewValueImpl(long paramLong);
/*    */ 
/*    */   public String getAttrName() {
/* 40 */     return getAttrNameImpl(getPeer());
/*    */   }
/*    */   static native String getAttrNameImpl(long paramLong);
/*    */ 
/*    */   public short getAttrChange() {
/* 45 */     return getAttrChangeImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native short getAttrChangeImpl(long paramLong);
/*    */ 
/*    */   public void initMutationEvent(String type, boolean canBubble, boolean cancelable, Node relatedNode, String prevValue, String newValue, String attrName, short attrChange)
/*    */   {
/* 60 */     initMutationEventImpl(getPeer(), type, canBubble, cancelable, NodeImpl.getPeer(relatedNode), prevValue, newValue, attrName, attrChange);
/*    */   }
/*    */ 
/*    */   static native void initMutationEventImpl(long paramLong1, String paramString1, boolean paramBoolean1, boolean paramBoolean2, long paramLong2, String paramString2, String paramString3, String paramString4, short paramShort);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.MutationEventImpl
 * JD-Core Version:    0.6.2
 */