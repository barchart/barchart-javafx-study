/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.css.CSSFontFaceRule;
/*    */ import org.w3c.dom.css.CSSStyleDeclaration;
/*    */ 
/*    */ public class CSSFontFaceRuleImpl extends CSSRuleImpl
/*    */   implements CSSFontFaceRule
/*    */ {
/*    */   CSSFontFaceRuleImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static CSSFontFaceRule getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (CSSFontFaceRule)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public CSSStyleDeclaration getStyle()
/*    */   {
/* 20 */     return CSSStyleDeclarationImpl.getImpl(getStyleImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long getStyleImpl(long paramLong);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSFontFaceRuleImpl
 * JD-Core Version:    0.6.2
 */