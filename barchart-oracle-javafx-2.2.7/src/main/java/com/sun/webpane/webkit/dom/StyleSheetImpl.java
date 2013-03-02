/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.stylesheets.MediaList;
/*     */ import org.w3c.dom.stylesheets.StyleSheet;
/*     */ 
/*     */ public class StyleSheetImpl
/*     */   implements StyleSheet
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */   private static final int TYPE_CSSStyleSheet = 1;
/*     */ 
/*     */   StyleSheetImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  23 */     this.peer = peer;
/*  24 */     this.contextPeer = contextPeer;
/*  25 */     this.rootPeer = rootPeer;
/*  26 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static StyleSheet create(long peer, long contextPeer, long rootPeer) {
/*  30 */     if (peer == 0L) return null;
/*  31 */     switch (getCPPTypeImpl(peer)) { case 1:
/*  32 */       return new CSSStyleSheetImpl(peer, contextPeer, rootPeer);
/*     */     }
/*  34 */     return new StyleSheetImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  42 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(StyleSheet arg) {
/*  46 */     return arg == null ? 0L : ((StyleSheetImpl)arg).getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  50 */     return ((that instanceof StyleSheetImpl)) && (this.peer == ((StyleSheetImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  54 */     long p = this.peer;
/*  55 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   private static native int getCPPTypeImpl(long paramLong);
/*     */ 
/*     */   static StyleSheet getImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  64 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  70 */     return getTypeImpl(getPeer());
/*     */   }
/*     */   static native String getTypeImpl(long paramLong);
/*     */ 
/*     */   public boolean getDisabled() {
/*  75 */     return getDisabledImpl(getPeer());
/*     */   }
/*     */   static native boolean getDisabledImpl(long paramLong);
/*     */ 
/*     */   public void setDisabled(boolean value) {
/*  80 */     setDisabledImpl(getPeer(), value);
/*     */   }
/*     */   static native void setDisabledImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public Node getOwnerNode() {
/*  85 */     return NodeImpl.getImpl(getOwnerNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getOwnerNodeImpl(long paramLong);
/*     */ 
/*     */   public StyleSheet getParentStyleSheet() {
/*  90 */     return getImpl(getParentStyleSheetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getParentStyleSheetImpl(long paramLong);
/*     */ 
/*     */   public String getHref() {
/*  95 */     return getHrefImpl(getPeer());
/*     */   }
/*     */   static native String getHrefImpl(long paramLong);
/*     */ 
/*     */   public String getTitle() {
/* 100 */     return getTitleImpl(getPeer());
/*     */   }
/*     */   static native String getTitleImpl(long paramLong);
/*     */ 
/*     */   public MediaList getMedia() {
/* 105 */     return MediaListImpl.getImpl(getMediaImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getMediaImpl(long paramLong);
/*     */ 
/*     */   static class SelfDisposer
/*     */     implements DisposerRecord
/*     */   {
/*     */     private final long peer;
/*     */ 
/*     */     SelfDisposer(long peer)
/*     */     {
/*  15 */       this.peer = peer;
/*     */     }
/*     */     public void dispose() {
/*  18 */       StyleSheetImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.StyleSheetImpl
 * JD-Core Version:    0.6.2
 */