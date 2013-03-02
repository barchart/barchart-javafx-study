/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.events.EventTarget;
/*     */ import org.w3c.dom.events.MouseEvent;
/*     */ import org.w3c.dom.views.AbstractView;
/*     */ 
/*     */ public class MouseEventImpl extends UIEventImpl
/*     */   implements MouseEvent
/*     */ {
/*     */   MouseEventImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  12 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static MouseEvent getImpl(long peer, long contextPeer, long rootPeer) {
/*  16 */     return (MouseEvent)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public int getScreenX()
/*     */   {
/*  22 */     return getScreenXImpl(getPeer());
/*     */   }
/*     */   static native int getScreenXImpl(long paramLong);
/*     */ 
/*     */   public int getScreenY() {
/*  27 */     return getScreenYImpl(getPeer());
/*     */   }
/*     */   static native int getScreenYImpl(long paramLong);
/*     */ 
/*     */   public int getClientX() {
/*  32 */     return getClientXImpl(getPeer());
/*     */   }
/*     */   static native int getClientXImpl(long paramLong);
/*     */ 
/*     */   public int getClientY() {
/*  37 */     return getClientYImpl(getPeer());
/*     */   }
/*     */   static native int getClientYImpl(long paramLong);
/*     */ 
/*     */   public boolean getCtrlKey() {
/*  42 */     return getCtrlKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getCtrlKeyImpl(long paramLong);
/*     */ 
/*     */   public boolean getShiftKey() {
/*  47 */     return getShiftKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getShiftKeyImpl(long paramLong);
/*     */ 
/*     */   public boolean getAltKey() {
/*  52 */     return getAltKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getAltKeyImpl(long paramLong);
/*     */ 
/*     */   public boolean getMetaKey() {
/*  57 */     return getMetaKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getMetaKeyImpl(long paramLong);
/*     */ 
/*     */   public short getButton() {
/*  62 */     return getButtonImpl(getPeer());
/*     */   }
/*     */   static native short getButtonImpl(long paramLong);
/*     */ 
/*     */   public EventTarget getRelatedTarget() {
/*  67 */     return (EventTarget)NodeImpl.getImpl(getRelatedTargetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getRelatedTargetImpl(long paramLong);
/*     */ 
/*     */   public int getOffsetX() {
/*  72 */     return getOffsetXImpl(getPeer());
/*     */   }
/*     */   static native int getOffsetXImpl(long paramLong);
/*     */ 
/*     */   public int getOffsetY() {
/*  77 */     return getOffsetYImpl(getPeer());
/*     */   }
/*     */   static native int getOffsetYImpl(long paramLong);
/*     */ 
/*     */   public int getX() {
/*  82 */     return getXImpl(getPeer());
/*     */   }
/*     */   static native int getXImpl(long paramLong);
/*     */ 
/*     */   public int getY() {
/*  87 */     return getYImpl(getPeer());
/*     */   }
/*     */   static native int getYImpl(long paramLong);
/*     */ 
/*     */   public Node getFromElement() {
/*  92 */     return NodeImpl.getImpl(getFromElementImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getFromElementImpl(long paramLong);
/*     */ 
/*     */   public Node getToElement() {
/*  97 */     return NodeImpl.getImpl(getToElementImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getToElementImpl(long paramLong);
/*     */ 
/*     */   public void initMouseEvent(String type, boolean canBubble, boolean cancelable, AbstractView view, int detail, int screenX, int screenY, int clientX, int clientY, boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey, short button, EventTarget relatedTarget)
/*     */   {
/* 119 */     initMouseEventImpl(getPeer(), type, canBubble, cancelable, DOMWindowImpl.getPeer(view), detail, screenX, screenY, clientX, clientY, ctrlKey, altKey, shiftKey, metaKey, button, NodeImpl.getPeer((NodeImpl)relatedTarget));
/*     */   }
/*     */ 
/*     */   static native void initMouseEventImpl(long paramLong1, String paramString, boolean paramBoolean1, boolean paramBoolean2, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, short paramShort, long paramLong3);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.MouseEventImpl
 * JD-Core Version:    0.6.2
 */