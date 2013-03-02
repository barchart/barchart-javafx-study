/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ public class HTMLOptionsCollectionImpl extends HTMLCollectionImpl
/*    */ {
/*    */   HTMLOptionsCollectionImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  8 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLOptionsCollectionImpl getImpl(long peer, long contextPeer, long rootPeer) {
/* 12 */     return (HTMLOptionsCollectionImpl)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public int getSelectedIndex()
/*    */   {
/* 18 */     return getSelectedIndexImpl(getPeer());
/*    */   }
/*    */   static native int getSelectedIndexImpl(long paramLong);
/*    */ 
/*    */   public void setSelectedIndex(int value) {
/* 23 */     setSelectedIndexImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setSelectedIndexImpl(long paramLong, int paramInt);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLOptionsCollectionImpl
 * JD-Core Version:    0.6.2
 */