/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.css.CSSImportRule;
/*    */ import org.w3c.dom.css.CSSStyleSheet;
/*    */ import org.w3c.dom.stylesheets.MediaList;
/*    */ 
/*    */ public class CSSImportRuleImpl extends CSSRuleImpl
/*    */   implements CSSImportRule
/*    */ {
/*    */   CSSImportRuleImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 11 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static CSSImportRule getImpl(long peer, long contextPeer, long rootPeer) {
/* 15 */     return (CSSImportRule)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getHref()
/*    */   {
/* 21 */     return getHrefImpl(getPeer());
/*    */   }
/*    */   static native String getHrefImpl(long paramLong);
/*    */ 
/*    */   public MediaList getMedia() {
/* 26 */     return MediaListImpl.getImpl(getMediaImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getMediaImpl(long paramLong);
/*    */ 
/*    */   public CSSStyleSheet getStyleSheet() {
/* 31 */     return CSSStyleSheetImpl.getImpl(getStyleSheetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long getStyleSheetImpl(long paramLong);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSImportRuleImpl
 * JD-Core Version:    0.6.2
 */