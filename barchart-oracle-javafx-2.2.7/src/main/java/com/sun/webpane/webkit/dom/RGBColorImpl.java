/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ import org.w3c.dom.css.CSSPrimitiveValue;
/*    */ import org.w3c.dom.css.RGBColor;
/*    */ 
/*    */ public class RGBColorImpl
/*    */   implements RGBColor
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */ 
/*    */   RGBColorImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 22 */     this.peer = peer;
/* 23 */     this.contextPeer = contextPeer;
/* 24 */     this.rootPeer = rootPeer;
/* 25 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static RGBColor create(long peer, long contextPeer, long rootPeer) {
/* 29 */     if (peer == 0L) return null;
/* 30 */     return new RGBColorImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 38 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(RGBColor arg) {
/* 42 */     return arg == null ? 0L : ((RGBColorImpl)arg).getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 46 */     return ((that instanceof RGBColorImpl)) && (this.peer == ((RGBColorImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 50 */     long p = this.peer;
/* 51 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   static RGBColor getImpl(long peer, long contextPeer, long rootPeer) {
/* 57 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public CSSPrimitiveValue getRed()
/*    */   {
/* 63 */     return CSSPrimitiveValueImpl.getImpl(getRedImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getRedImpl(long paramLong);
/*    */ 
/*    */   public CSSPrimitiveValue getGreen() {
/* 68 */     return CSSPrimitiveValueImpl.getImpl(getGreenImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getGreenImpl(long paramLong);
/*    */ 
/*    */   public CSSPrimitiveValue getBlue() {
/* 73 */     return CSSPrimitiveValueImpl.getImpl(getBlueImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getBlueImpl(long paramLong);
/*    */ 
/*    */   public CSSPrimitiveValue getAlpha() {
/* 78 */     return CSSPrimitiveValueImpl.getImpl(getAlphaImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long getAlphaImpl(long paramLong);
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
/* 17 */       RGBColorImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.RGBColorImpl
 * JD-Core Version:    0.6.2
 */