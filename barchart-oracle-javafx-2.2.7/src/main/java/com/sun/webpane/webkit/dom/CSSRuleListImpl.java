/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ import org.w3c.dom.css.CSSRule;
/*    */ import org.w3c.dom.css.CSSRuleList;
/*    */ 
/*    */ public class CSSRuleListImpl
/*    */   implements CSSRuleList
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */ 
/*    */   CSSRuleListImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 22 */     this.peer = peer;
/* 23 */     this.contextPeer = contextPeer;
/* 24 */     this.rootPeer = rootPeer;
/* 25 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static CSSRuleList create(long peer, long contextPeer, long rootPeer) {
/* 29 */     if (peer == 0L) return null;
/* 30 */     return new CSSRuleListImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 38 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(CSSRuleList arg) {
/* 42 */     return arg == null ? 0L : ((CSSRuleListImpl)arg).getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 46 */     return ((that instanceof CSSRuleListImpl)) && (this.peer == ((CSSRuleListImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 50 */     long p = this.peer;
/* 51 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   static CSSRuleList getImpl(long peer, long contextPeer, long rootPeer) {
/* 57 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public int getLength()
/*    */   {
/* 63 */     return getLengthImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native int getLengthImpl(long paramLong);
/*    */ 
/*    */   public CSSRule item(int index)
/*    */   {
/* 71 */     return CSSRuleImpl.getImpl(itemImpl(getPeer(), index), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long itemImpl(long paramLong, int paramInt);
/*    */ 
/*    */   static class SelfDisposer
/*    */     implements DisposerRecord
/*    */   {
/*    */     private final long peer;
/*    */ 
/*    */     SelfDisposer(long peer)
/*    */     {
/* 14 */       this.peer = peer;
/*    */     }
/*    */     public void dispose() {
/* 17 */       CSSRuleListImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSRuleListImpl
 * JD-Core Version:    0.6.2
 */