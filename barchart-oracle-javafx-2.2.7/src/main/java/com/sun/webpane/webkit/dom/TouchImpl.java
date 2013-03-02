/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.events.EventTarget;
/*     */ 
/*     */ public class TouchImpl
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */ 
/*     */   TouchImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  21 */     this.peer = peer;
/*  22 */     this.contextPeer = contextPeer;
/*  23 */     this.rootPeer = rootPeer;
/*  24 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static TouchImpl create(long peer, long contextPeer, long rootPeer) {
/*  28 */     if (peer == 0L) return null;
/*  29 */     return new TouchImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  37 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(TouchImpl arg) {
/*  41 */     return arg == null ? 0L : arg.getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  45 */     return ((that instanceof TouchImpl)) && (this.peer == ((TouchImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  49 */     long p = this.peer;
/*  50 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   static TouchImpl getImpl(long peer, long contextPeer, long rootPeer) {
/*  56 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public int getClientX()
/*     */   {
/*  62 */     return getClientXImpl(getPeer());
/*     */   }
/*     */   static native int getClientXImpl(long paramLong);
/*     */ 
/*     */   public int getClientY() {
/*  67 */     return getClientYImpl(getPeer());
/*     */   }
/*     */   static native int getClientYImpl(long paramLong);
/*     */ 
/*     */   public int getScreenX() {
/*  72 */     return getScreenXImpl(getPeer());
/*     */   }
/*     */   static native int getScreenXImpl(long paramLong);
/*     */ 
/*     */   public int getScreenY() {
/*  77 */     return getScreenYImpl(getPeer());
/*     */   }
/*     */   static native int getScreenYImpl(long paramLong);
/*     */ 
/*     */   public int getPageX() {
/*  82 */     return getPageXImpl(getPeer());
/*     */   }
/*     */   static native int getPageXImpl(long paramLong);
/*     */ 
/*     */   public int getPageY() {
/*  87 */     return getPageYImpl(getPeer());
/*     */   }
/*     */   static native int getPageYImpl(long paramLong);
/*     */ 
/*     */   public EventTarget getTarget() {
/*  92 */     return (EventTarget)NodeImpl.getImpl(getTargetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getTargetImpl(long paramLong);
/*     */ 
/*     */   public int getIdentifier() {
/*  97 */     return getIdentifierImpl(getPeer());
/*     */   }
/*     */   static native int getIdentifierImpl(long paramLong);
/*     */ 
/*     */   public int getWebkitRadiusX() {
/* 102 */     return getWebkitRadiusXImpl(getPeer());
/*     */   }
/*     */   static native int getWebkitRadiusXImpl(long paramLong);
/*     */ 
/*     */   public int getWebkitRadiusY() {
/* 107 */     return getWebkitRadiusYImpl(getPeer());
/*     */   }
/*     */   static native int getWebkitRadiusYImpl(long paramLong);
/*     */ 
/*     */   public float getWebkitRotationAngle() {
/* 112 */     return getWebkitRotationAngleImpl(getPeer());
/*     */   }
/*     */   static native float getWebkitRotationAngleImpl(long paramLong);
/*     */ 
/*     */   public float getWebkitForce() {
/* 117 */     return getWebkitForceImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native float getWebkitForceImpl(long paramLong);
/*     */ 
/*     */   static class SelfDisposer
/*     */     implements DisposerRecord
/*     */   {
/*     */     private final long peer;
/*     */ 
/*     */     SelfDisposer(long peer)
/*     */     {
/*  13 */       this.peer = peer;
/*     */     }
/*     */     public void dispose() {
/*  16 */       TouchImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.TouchImpl
 * JD-Core Version:    0.6.2
 */