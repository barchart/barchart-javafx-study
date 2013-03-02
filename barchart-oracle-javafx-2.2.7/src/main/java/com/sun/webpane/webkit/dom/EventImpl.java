/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.events.Event;
/*     */ import org.w3c.dom.events.EventTarget;
/*     */ 
/*     */ public class EventImpl
/*     */   implements Event
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */   private static final int TYPE_MouseEvent = 1;
/*     */   private static final int TYPE_KeyboardEvent = 2;
/*     */   private static final int TYPE_WheelEvent = 3;
/*     */   private static final int TYPE_TouchEvent = 4;
/*     */   private static final int TYPE_UIEvent = 5;
/*     */   private static final int TYPE_MutationEvent = 6;
/*     */   public static final int CAPTURING_PHASE = 1;
/*     */   public static final int AT_TARGET = 2;
/*     */   public static final int BUBBLING_PHASE = 3;
/*     */   public static final int MOUSEDOWN = 1;
/*     */   public static final int MOUSEUP = 2;
/*     */   public static final int MOUSEOVER = 4;
/*     */   public static final int MOUSEOUT = 8;
/*     */   public static final int MOUSEMOVE = 16;
/*     */   public static final int MOUSEDRAG = 32;
/*     */   public static final int CLICK = 64;
/*     */   public static final int DBLCLICK = 128;
/*     */   public static final int KEYDOWN = 256;
/*     */   public static final int KEYUP = 512;
/*     */   public static final int KEYPRESS = 1024;
/*     */   public static final int DRAGDROP = 2048;
/*     */   public static final int FOCUS = 4096;
/*     */   public static final int BLUR = 8192;
/*     */   public static final int SELECT = 16384;
/*     */   public static final int CHANGE = 32768;
/*     */ 
/*     */   EventImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  22 */     this.peer = peer;
/*  23 */     this.contextPeer = contextPeer;
/*  24 */     this.rootPeer = rootPeer;
/*  25 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static Event create(long peer, long contextPeer, long rootPeer) {
/*  29 */     if (peer == 0L) return null;
/*  30 */     switch (getCPPTypeImpl(peer)) { case 1:
/*  31 */       return new MouseEventImpl(peer, contextPeer, rootPeer);
/*     */     case 2:
/*  32 */       return new KeyboardEventImpl(peer, contextPeer, rootPeer);
/*     */     case 3:
/*  33 */       return new WheelEventImpl(peer, contextPeer, rootPeer);
/*     */     case 4:
/*  34 */       return new TouchEventImpl(peer, contextPeer, rootPeer);
/*     */     case 5:
/*  35 */       return new UIEventImpl(peer, contextPeer, rootPeer);
/*     */     case 6:
/*  36 */       return new MutationEventImpl(peer, contextPeer, rootPeer);
/*     */     }
/*  38 */     return new EventImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  46 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(Event arg) {
/*  50 */     return arg == null ? 0L : ((EventImpl)arg).getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  54 */     return ((that instanceof EventImpl)) && (this.peer == ((EventImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  58 */     long p = this.peer;
/*  59 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   private static native int getCPPTypeImpl(long paramLong);
/*     */ 
/*     */   static Event getImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  73 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 100 */     return getTypeImpl(getPeer());
/*     */   }
/*     */   static native String getTypeImpl(long paramLong);
/*     */ 
/*     */   public EventTarget getTarget() {
/* 105 */     return (EventTarget)NodeImpl.getImpl(getTargetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getTargetImpl(long paramLong);
/*     */ 
/*     */   public EventTarget getCurrentTarget() {
/* 110 */     return (EventTarget)NodeImpl.getImpl(getCurrentTargetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getCurrentTargetImpl(long paramLong);
/*     */ 
/*     */   public short getEventPhase() {
/* 115 */     return getEventPhaseImpl(getPeer());
/*     */   }
/*     */   static native short getEventPhaseImpl(long paramLong);
/*     */ 
/*     */   public boolean getBubbles() {
/* 120 */     return getBubblesImpl(getPeer());
/*     */   }
/*     */   static native boolean getBubblesImpl(long paramLong);
/*     */ 
/*     */   public boolean getCancelable() {
/* 125 */     return getCancelableImpl(getPeer());
/*     */   }
/*     */   static native boolean getCancelableImpl(long paramLong);
/*     */ 
/*     */   public long getTimeStamp() {
/* 130 */     return getTimeStampImpl(getPeer());
/*     */   }
/*     */   static native long getTimeStampImpl(long paramLong);
/*     */ 
/*     */   public boolean getDefaultPrevented() {
/* 135 */     return getDefaultPreventedImpl(getPeer());
/*     */   }
/*     */   static native boolean getDefaultPreventedImpl(long paramLong);
/*     */ 
/*     */   public EventTarget getSrcElement() {
/* 140 */     return (EventTarget)NodeImpl.getImpl(getSrcElementImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getSrcElementImpl(long paramLong);
/*     */ 
/*     */   public boolean getReturnValue() {
/* 145 */     return getReturnValueImpl(getPeer());
/*     */   }
/*     */   static native boolean getReturnValueImpl(long paramLong);
/*     */ 
/*     */   public void setReturnValue(boolean value) {
/* 150 */     setReturnValueImpl(getPeer(), value);
/*     */   }
/*     */   static native void setReturnValueImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean getCancelBubble() {
/* 155 */     return getCancelBubbleImpl(getPeer());
/*     */   }
/*     */   static native boolean getCancelBubbleImpl(long paramLong);
/*     */ 
/*     */   public void setCancelBubble(boolean value) {
/* 160 */     setCancelBubbleImpl(getPeer(), value);
/*     */   }
/*     */ 
/*     */   static native void setCancelBubbleImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public void stopPropagation()
/*     */   {
/* 168 */     stopPropagationImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void stopPropagationImpl(long paramLong);
/*     */ 
/*     */   public void preventDefault()
/*     */   {
/* 175 */     preventDefaultImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void preventDefaultImpl(long paramLong);
/*     */ 
/*     */   public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg)
/*     */   {
/* 184 */     initEventImpl(getPeer(), eventTypeArg, canBubbleArg, cancelableArg);
/*     */   }
/*     */ 
/*     */   static native void initEventImpl(long paramLong, String paramString, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */   public void stopImmediatePropagation()
/*     */   {
/* 197 */     stopImmediatePropagationImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void stopImmediatePropagationImpl(long paramLong);
/*     */ 
/*     */   static class SelfDisposer
/*     */     implements DisposerRecord
/*     */   {
/*     */     private final long peer;
/*     */ 
/*     */     SelfDisposer(long peer)
/*     */     {
/*  14 */       this.peer = peer;
/*     */     }
/*     */     public void dispose() {
/*  17 */       EventImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.EventImpl
 * JD-Core Version:    0.6.2
 */