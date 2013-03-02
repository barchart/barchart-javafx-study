/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import org.w3c.dom.views.AbstractView;
/*     */ 
/*     */ public class KeyboardEventImpl extends UIEventImpl
/*     */ {
/*     */   public static final int KEY_LOCATION_STANDARD = 0;
/*     */   public static final int KEY_LOCATION_LEFT = 1;
/*     */   public static final int KEY_LOCATION_RIGHT = 2;
/*     */   public static final int KEY_LOCATION_NUMPAD = 3;
/*     */ 
/*     */   KeyboardEventImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*   9 */     super(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   static KeyboardEventImpl getImpl(long peer, long contextPeer, long rootPeer) {
/*  13 */     return (KeyboardEventImpl)create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public String getKeyIdentifier()
/*     */   {
/*  25 */     return getKeyIdentifierImpl(getPeer());
/*     */   }
/*     */   static native String getKeyIdentifierImpl(long paramLong);
/*     */ 
/*     */   public int getKeyLocation() {
/*  30 */     return getKeyLocationImpl(getPeer());
/*     */   }
/*     */   static native int getKeyLocationImpl(long paramLong);
/*     */ 
/*     */   public boolean getCtrlKey() {
/*  35 */     return getCtrlKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getCtrlKeyImpl(long paramLong);
/*     */ 
/*     */   public boolean getShiftKey() {
/*  40 */     return getShiftKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getShiftKeyImpl(long paramLong);
/*     */ 
/*     */   public boolean getAltKey() {
/*  45 */     return getAltKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getAltKeyImpl(long paramLong);
/*     */ 
/*     */   public boolean getMetaKey() {
/*  50 */     return getMetaKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getMetaKeyImpl(long paramLong);
/*     */ 
/*     */   public boolean getAltGraphKey() {
/*  55 */     return getAltGraphKeyImpl(getPeer());
/*     */   }
/*     */   static native boolean getAltGraphKeyImpl(long paramLong);
/*     */ 
/*     */   public int getKeyCode() {
/*  60 */     return getKeyCodeImpl(getPeer());
/*     */   }
/*     */   static native int getKeyCodeImpl(long paramLong);
/*     */ 
/*     */   public int getCharCode() {
/*  65 */     return getCharCodeImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native int getCharCodeImpl(long paramLong);
/*     */ 
/*     */   public boolean getModifierState(String keyIdentifierArg)
/*     */   {
/*  73 */     return getModifierStateImpl(getPeer(), keyIdentifierArg);
/*     */   }
/*     */ 
/*     */   static native boolean getModifierStateImpl(long paramLong, String paramString);
/*     */ 
/*     */   public void initKeyboardEvent(String type, boolean canBubble, boolean cancelable, AbstractView view, String keyIdentifier, int keyLocation, boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey, boolean altGraphKey)
/*     */   {
/*  92 */     initKeyboardEventImpl(getPeer(), type, canBubble, cancelable, DOMWindowImpl.getPeer(view), keyIdentifier, keyLocation, ctrlKey, altKey, shiftKey, metaKey, altGraphKey);
/*     */   }
/*     */ 
/*     */   static native void initKeyboardEventImpl(long paramLong1, String paramString1, boolean paramBoolean1, boolean paramBoolean2, long paramLong2, String paramString2, int paramInt, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7);
/*     */ 
/*     */   public void initKeyboardEventEx(String type, boolean canBubble, boolean cancelable, AbstractView view, String keyIdentifier, int keyLocation, boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey)
/*     */   {
/* 130 */     initKeyboardEventExImpl(getPeer(), type, canBubble, cancelable, DOMWindowImpl.getPeer(view), keyIdentifier, keyLocation, ctrlKey, altKey, shiftKey, metaKey);
/*     */   }
/*     */ 
/*     */   static native void initKeyboardEventExImpl(long paramLong1, String paramString1, boolean paramBoolean1, boolean paramBoolean2, long paramLong2, String paramString2, int paramInt, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.KeyboardEventImpl
 * JD-Core Version:    0.6.2
 */