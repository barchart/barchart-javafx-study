/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.css.CSSValue;
/*    */ 
/*    */ public class CSSValueImpl
/*    */   implements CSSValue
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */   public static final int CSS_INHERIT = 0;
/*    */   public static final int CSS_PRIMITIVE_VALUE = 1;
/*    */   public static final int CSS_VALUE_LIST = 2;
/*    */   public static final int CSS_CUSTOM = 3;
/*    */ 
/*    */   CSSValueImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 22 */     this.peer = peer;
/* 23 */     this.contextPeer = contextPeer;
/* 24 */     this.rootPeer = rootPeer;
/* 25 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static CSSValue create(long peer, long contextPeer, long rootPeer) {
/* 29 */     if (peer == 0L) return null;
/* 30 */     switch (getCssValueTypeImpl(peer)) { case 1:
/* 31 */       return new CSSPrimitiveValueImpl(peer, contextPeer, rootPeer);
/*    */     case 2:
/* 32 */       return new CSSValueListImpl(peer, contextPeer, rootPeer);
/*    */     }
/* 34 */     return new CSSValueImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 42 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(CSSValue arg) {
/* 46 */     return arg == null ? 0L : ((CSSValueImpl)arg).getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 50 */     return ((that instanceof CSSValueImpl)) && (this.peer == ((CSSValueImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 54 */     long p = this.peer;
/* 55 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   static CSSValue getImpl(long peer, long contextPeer, long rootPeer) {
/* 61 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getCssText()
/*    */   {
/* 73 */     return getCssTextImpl(getPeer());
/*    */   }
/*    */   static native String getCssTextImpl(long paramLong);
/*    */ 
/*    */   public void setCssText(String value) throws DOMException {
/* 78 */     setCssTextImpl(getPeer(), value);
/*    */   }
/*    */   static native void setCssTextImpl(long paramLong, String paramString);
/*    */ 
/*    */   public short getCssValueType() {
/* 83 */     return getCssValueTypeImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native short getCssValueTypeImpl(long paramLong);
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
/* 17 */       CSSValueImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSValueImpl
 * JD-Core Version:    0.6.2
 */