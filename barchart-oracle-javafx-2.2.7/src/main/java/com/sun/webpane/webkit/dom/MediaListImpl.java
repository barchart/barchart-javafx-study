/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.stylesheets.MediaList;
/*    */ 
/*    */ public class MediaListImpl
/*    */   implements MediaList
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */ 
/*    */   MediaListImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 22 */     this.peer = peer;
/* 23 */     this.contextPeer = contextPeer;
/* 24 */     this.rootPeer = rootPeer;
/* 25 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static MediaList create(long peer, long contextPeer, long rootPeer) {
/* 29 */     if (peer == 0L) return null;
/* 30 */     return new MediaListImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 38 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(MediaList arg) {
/* 42 */     return arg == null ? 0L : ((MediaListImpl)arg).getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 46 */     return ((that instanceof MediaListImpl)) && (this.peer == ((MediaListImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 50 */     long p = this.peer;
/* 51 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   static MediaList getImpl(long peer, long contextPeer, long rootPeer) {
/* 57 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getMediaText()
/*    */   {
/* 63 */     return getMediaTextImpl(getPeer());
/*    */   }
/*    */   static native String getMediaTextImpl(long paramLong);
/*    */ 
/*    */   public void setMediaText(String value) throws DOMException {
/* 68 */     setMediaTextImpl(getPeer(), value);
/*    */   }
/*    */   static native void setMediaTextImpl(long paramLong, String paramString);
/*    */ 
/*    */   public int getLength() {
/* 73 */     return getLengthImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native int getLengthImpl(long paramLong);
/*    */ 
/*    */   public String item(int index)
/*    */   {
/* 81 */     return itemImpl(getPeer(), index);
/*    */   }
/*    */ 
/*    */   static native String itemImpl(long paramLong, int paramInt);
/*    */ 
/*    */   public void deleteMedium(String oldMedium)
/*    */     throws DOMException
/*    */   {
/* 90 */     deleteMediumImpl(getPeer(), oldMedium);
/*    */   }
/*    */ 
/*    */   static native void deleteMediumImpl(long paramLong, String paramString);
/*    */ 
/*    */   public void appendMedium(String newMedium)
/*    */     throws DOMException
/*    */   {
/* 99 */     appendMediumImpl(getPeer(), newMedium);
/*    */   }
/*    */ 
/*    */   static native void appendMediumImpl(long paramLong, String paramString);
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
/* 17 */       MediaListImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.MediaListImpl
 * JD-Core Version:    0.6.2
 */