/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ import org.w3c.dom.DOMStringList;
/*    */ 
/*    */ public class DOMStringListImpl
/*    */   implements DOMStringList
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */ 
/*    */   DOMStringListImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 21 */     this.peer = peer;
/* 22 */     this.contextPeer = contextPeer;
/* 23 */     this.rootPeer = rootPeer;
/* 24 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static DOMStringList create(long peer, long contextPeer, long rootPeer) {
/* 28 */     if (peer == 0L) return null;
/* 29 */     return new DOMStringListImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 37 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(DOMStringList arg) {
/* 41 */     return arg == null ? 0L : ((DOMStringListImpl)arg).getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 45 */     return ((that instanceof DOMStringListImpl)) && (this.peer == ((DOMStringListImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 49 */     long p = this.peer;
/* 50 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   static DOMStringList getImpl(long peer, long contextPeer, long rootPeer) {
/* 56 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public int getLength()
/*    */   {
/* 62 */     return getLengthImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native int getLengthImpl(long paramLong);
/*    */ 
/*    */   public String item(int index)
/*    */   {
/* 70 */     return itemImpl(getPeer(), index);
/*    */   }
/*    */ 
/*    */   static native String itemImpl(long paramLong, int paramInt);
/*    */ 
/*    */   public boolean contains(String string)
/*    */   {
/* 79 */     return containsImpl(getPeer(), string);
/*    */   }
/*    */ 
/*    */   static native boolean containsImpl(long paramLong, String paramString);
/*    */ 
/*    */   static class SelfDisposer
/*    */     implements DisposerRecord
/*    */   {
/*    */     private final long peer;
/*    */ 
/*    */     SelfDisposer(long peer)
/*    */     {
/* 13 */       this.peer = peer;
/*    */     }
/*    */     public void dispose() {
/* 16 */       DOMStringListImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.DOMStringListImpl
 * JD-Core Version:    0.6.2
 */