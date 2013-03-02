/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import org.w3c.dom.events.Event;
/*     */ import org.w3c.dom.events.EventListener;
/*     */ 
/*     */ final class EventListenerImpl
/*     */   implements EventListener
/*     */ {
/*  19 */   static Map<EventListener, Long> EL2peer = new WeakHashMap();
/*     */ 
/*  21 */   static Map<Long, WeakReference<EventListener>> peer2EL = new HashMap();
/*     */   protected final EventListener eventListener;
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long jsPeer;
/*     */ 
/*     */   static long getPeer(EventListener eventListener, long contextPeer, long rootPeer)
/*     */   {
/*  41 */     if (eventListener == null) {
/*  42 */       return 0L;
/*     */     }
/*     */ 
/*  45 */     Long peer = (Long)EL2peer.get(eventListener);
/*  46 */     if (peer != null) {
/*  47 */       return peer.longValue();
/*     */     }
/*     */ 
/*  51 */     EventListenerImpl eli = new EventListenerImpl(eventListener, 0L, contextPeer, rootPeer);
/*  52 */     peer = Long.valueOf(eli.twkCreatePeer());
/*  53 */     EL2peer.put(eventListener, peer);
/*  54 */     peer2EL.put(peer, new WeakReference(eventListener));
/*     */ 
/*  56 */     return peer.longValue();
/*     */   }
/*     */ 
/*     */   private native long twkCreatePeer();
/*     */ 
/*     */   static EventListener getELfromPeer(long peer) {
/*  62 */     WeakReference wr = (WeakReference)peer2EL.get(Long.valueOf(peer));
/*  63 */     return wr == null ? null : (EventListener)wr.get();
/*     */   }
/*     */ 
/*     */   static EventListener getImpl(long peer, long contextPeer, long rootPeer) {
/*  67 */     if (peer == 0L) {
/*  68 */       return null;
/*     */     }
/*  70 */     EventListener ev = getELfromPeer(peer);
/*  71 */     if (ev != null)
/*     */     {
/*  73 */       twkDisposeJSPeer(peer);
/*  74 */       return ev;
/*     */     }
/*     */ 
/*  78 */     EventListener el = new EventListenerImpl(null, peer, contextPeer, rootPeer);
/*  79 */     EL2peer.put(el, Long.valueOf(peer));
/*  80 */     peer2EL.put(Long.valueOf(peer), new WeakReference(el));
/*  81 */     Disposer.addRecord(el, new SelfDisposer(peer));
/*     */ 
/*  83 */     return el;
/*     */   }
/*     */ 
/*     */   public void handleEvent(Event evt)
/*     */   {
/*  88 */     if ((this.jsPeer != 0L) && ((evt instanceof EventImpl)))
/*  89 */       twkDispatchEvent(this.jsPeer, ((EventImpl)evt).getPeer());
/*     */   }
/*     */ 
/*     */   private static native void twkDispatchEvent(long paramLong1, long paramLong2);
/*     */ 
/*     */   private EventListenerImpl(EventListener eventListener, long jsPeer, long contextPeer, long rootPeer) {
/*  95 */     this.eventListener = eventListener;
/*  96 */     this.contextPeer = contextPeer;
/*  97 */     this.rootPeer = rootPeer;
/*  98 */     this.jsPeer = jsPeer;
/*     */   }
/*     */ 
/*     */   private static void dispose(long peer)
/*     */   {
/* 103 */     EventListener ev = getELfromPeer(peer);
/* 104 */     if (ev != null)
/* 105 */       EL2peer.remove(ev);
/* 106 */     peer2EL.remove(Long.valueOf(peer));
/*     */   }
/*     */ 
/*     */   private static native void twkDisposeJSPeer(long paramLong);
/*     */ 
/*     */   private void fwkHandleEvent(long eventPeer) {
/* 112 */     this.eventListener.handleEvent(EventImpl.getImpl(eventPeer, this.contextPeer, this.rootPeer));
/*     */   }
/*     */ 
/*     */   static class SelfDisposer
/*     */     implements DisposerRecord
/*     */   {
/*     */     private final long peer;
/*     */ 
/*     */     SelfDisposer(long peer)
/*     */     {
/*  26 */       this.peer = peer;
/*     */     }
/*     */ 
/*     */     public void dispose() {
/*  30 */       EventListenerImpl.dispose(this.peer);
/*  31 */       EventListenerImpl.twkDisposeJSPeer(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.EventListenerImpl
 * JD-Core Version:    0.6.2
 */