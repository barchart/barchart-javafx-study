/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.views.AbstractView;
/*     */ 
/*     */ public class WheelEventImpl extends UIEventImpl
/*     */ {
/*     */   WheelEventImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*   9 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static WheelEventImpl getImpl(long peer, long contextPeer, long rootPeer) {
/*  13 */     return (WheelEventImpl)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public int getScreenX()
/*     */   {
/*  19 */     return getScreenXImpl(getPeer());
/*     */   }
/*     */   static native int getScreenXImpl(long paramLong);
/*     */ 
/*     */   public int getScreenY() {
/*  24 */     return getScreenYImpl(getPeer());
/*     */   }
/*     */   static native int getScreenYImpl(long paramLong);
/*     */ 
/*     */   public int getClientX() {
/*  29 */     return getClientXImpl(getPeer());
/*     */   }
/*     */   static native int getClientXImpl(long paramLong);
/*     */ 
/*     */   public int getClientY() {
/*  34 */     return getClientYImpl(getPeer());
/*     */   }
/*     */   static native int getClientYImpl(long paramLong);
/*     */ 
/*     */   public boolean getCtrlKey() {
/*  39 */     return getCtrlKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getCtrlKeyImpl(long paramLong);
/*     */ 
/*     */   public boolean getShiftKey() {
/*  44 */     return getShiftKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getShiftKeyImpl(long paramLong);
/*     */ 
/*     */   public boolean getAltKey() {
/*  49 */     return getAltKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getAltKeyImpl(long paramLong);
/*     */ 
/*     */   public boolean getMetaKey() {
/*  54 */     return getMetaKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getMetaKeyImpl(long paramLong);
/*     */ 
/*     */   public int getWheelDelta() {
/*  59 */     return getWheelDeltaImpl(getPeer());
/*     */   }
/*     */   static native int getWheelDeltaImpl(long paramLong);
/*     */ 
/*     */   public int getWheelDeltaX() {
/*  64 */     return getWheelDeltaXImpl(getPeer());
/*     */   }
/*     */   static native int getWheelDeltaXImpl(long paramLong);
/*     */ 
/*     */   public int getWheelDeltaY() {
/*  69 */     return getWheelDeltaYImpl(getPeer());
/*     */   }
/*     */   static native int getWheelDeltaYImpl(long paramLong);
/*     */ 
/*     */   public int getOffsetX() {
/*  74 */     return getOffsetXImpl(getPeer());
/*     */   }
/*     */   static native int getOffsetXImpl(long paramLong);
/*     */ 
/*     */   public int getOffsetY() {
/*  79 */     return getOffsetYImpl(getPeer());
/*     */   }
/*     */   static native int getOffsetYImpl(long paramLong);
/*     */ 
/*     */   public int getX() {
/*  84 */     return getXImpl(getPeer());
/*     */   }
/*     */   static native int getXImpl(long paramLong);
/*     */ 
/*     */   public int getY() {
/*  89 */     return getYImpl(getPeer());
/*     */   }
/*     */   static native int getYImpl(long paramLong);
/*     */ 
/*     */   public boolean getWebkitDirectionInvertedFromDevice() {
/*  94 */     return getWebkitDirectionInvertedFromDeviceImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native boolean getWebkitDirectionInvertedFromDeviceImpl(long paramLong);
/*     */ 
/*     */   public void initWheelEvent(int wheelDeltaX, int wheelDeltaY, AbstractView view, int screenX, int screenY, int clientX, int clientY, boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey)
/*     */   {
/* 112 */     initWheelEventImpl(getPeer(), wheelDeltaX, wheelDeltaY, DOMWindowImpl.getPeer(view), screenX, screenY, clientX, clientY, ctrlKey, altKey, shiftKey, metaKey);
/*     */   }
/*     */ 
/*     */   static native void initWheelEventImpl(long paramLong1, int paramInt1, int paramInt2, long paramLong2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.WheelEventImpl
 * JD-Core Version:    0.6.2
 */