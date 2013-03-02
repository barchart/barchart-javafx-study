/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.css.CSSRule;
/*     */ import org.w3c.dom.css.CSSStyleSheet;
/*     */ 
/*     */ public class CSSRuleImpl
/*     */   implements CSSRule
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */   public static final int UNKNOWN_RULE = 0;
/*     */   public static final int STYLE_RULE = 1;
/*     */   public static final int CHARSET_RULE = 2;
/*     */   public static final int IMPORT_RULE = 3;
/*     */   public static final int MEDIA_RULE = 4;
/*     */   public static final int FONT_FACE_RULE = 5;
/*     */   public static final int PAGE_RULE = 6;
/*     */   public static final int WEBKIT_KEYFRAMES_RULE = 8;
/*     */   public static final int WEBKIT_KEYFRAME_RULE = 9;
/*     */   public static final int WEBKIT_REGION_RULE = 10;
/*     */ 
/*     */   CSSRuleImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  23 */     this.peer = peer;
/*  24 */     this.contextPeer = contextPeer;
/*  25 */     this.rootPeer = rootPeer;
/*  26 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static CSSRule create(long peer, long contextPeer, long rootPeer) {
/*  30 */     if (peer == 0L) return null;
/*  31 */     switch (getTypeImpl(peer)) { case 1:
/*  32 */       return new CSSStyleRuleImpl(peer, contextPeer, rootPeer);
/*     */     case 2:
/*  33 */       return new CSSCharsetRuleImpl(peer, contextPeer, rootPeer);
/*     */     case 3:
/*  34 */       return new CSSImportRuleImpl(peer, contextPeer, rootPeer);
/*     */     case 4:
/*  35 */       return new CSSMediaRuleImpl(peer, contextPeer, rootPeer);
/*     */     case 5:
/*  36 */       return new CSSFontFaceRuleImpl(peer, contextPeer, rootPeer);
/*     */     case 6:
/*  37 */       return new CSSPageRuleImpl(peer, contextPeer, rootPeer);
/*     */     }
/*  39 */     return new CSSRuleImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  47 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(CSSRule arg) {
/*  51 */     return arg == null ? 0L : ((CSSRuleImpl)arg).getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  55 */     return ((that instanceof CSSRuleImpl)) && (this.peer == ((CSSRuleImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  59 */     long p = this.peer;
/*  60 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   static CSSRule getImpl(long peer, long contextPeer, long rootPeer) {
/*  66 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public short getType()
/*     */   {
/*  84 */     return getTypeImpl(getPeer());
/*     */   }
/*     */   static native short getTypeImpl(long paramLong);
/*     */ 
/*     */   public String getCssText() {
/*  89 */     return getCssTextImpl(getPeer());
/*     */   }
/*     */   static native String getCssTextImpl(long paramLong);
/*     */ 
/*     */   public void setCssText(String value) throws DOMException {
/*  94 */     setCssTextImpl(getPeer(), value);
/*     */   }
/*     */   static native void setCssTextImpl(long paramLong, String paramString);
/*     */ 
/*     */   public CSSStyleSheet getParentStyleSheet() {
/*  99 */     return CSSStyleSheetImpl.getImpl(getParentStyleSheetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getParentStyleSheetImpl(long paramLong);
/*     */ 
/*     */   public CSSRule getParentRule() {
/* 104 */     return getImpl(getParentRuleImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getParentRuleImpl(long paramLong);
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
/*  18 */       CSSRuleImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSRuleImpl
 * JD-Core Version:    0.6.2
 */