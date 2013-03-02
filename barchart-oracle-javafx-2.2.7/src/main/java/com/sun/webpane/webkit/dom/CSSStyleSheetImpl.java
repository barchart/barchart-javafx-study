/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.css.CSSRule;
/*    */ import org.w3c.dom.css.CSSRuleList;
/*    */ import org.w3c.dom.css.CSSStyleSheet;
/*    */ 
/*    */ public class CSSStyleSheetImpl extends StyleSheetImpl
/*    */   implements CSSStyleSheet
/*    */ {
/*    */   CSSStyleSheetImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 12 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static CSSStyleSheet getImpl(long peer, long contextPeer, long rootPeer) {
/* 16 */     return (CSSStyleSheet)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public CSSRule getOwnerRule()
/*    */   {
/* 22 */     return CSSRuleImpl.getImpl(getOwnerRuleImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getOwnerRuleImpl(long paramLong);
/*    */ 
/*    */   public CSSRuleList getCssRules() {
/* 27 */     return CSSRuleListImpl.getImpl(getCssRulesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getCssRulesImpl(long paramLong);
/*    */ 
/*    */   public CSSRuleList getRules() {
/* 32 */     return CSSRuleListImpl.getImpl(getRulesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long getRulesImpl(long paramLong);
/*    */ 
/*    */   public int insertRule(String rule, int index)
/*    */     throws DOMException
/*    */   {
/* 41 */     return insertRuleImpl(getPeer(), rule, index);
/*    */   }
/*    */ 
/*    */   static native int insertRuleImpl(long paramLong, String paramString, int paramInt);
/*    */ 
/*    */   public void deleteRule(int index)
/*    */     throws DOMException
/*    */   {
/* 52 */     deleteRuleImpl(getPeer(), index);
/*    */   }
/*    */ 
/*    */   static native void deleteRuleImpl(long paramLong, int paramInt);
/*    */ 
/*    */   public int addRule(String selector, String style, int index)
/*    */     throws DOMException
/*    */   {
/* 63 */     return addRuleImpl(getPeer(), selector, style, index);
/*    */   }
/*    */ 
/*    */   static native int addRuleImpl(long paramLong, String paramString1, String paramString2, int paramInt);
/*    */ 
/*    */   public void removeRule(int index)
/*    */     throws DOMException
/*    */   {
/* 76 */     removeRuleImpl(getPeer(), index);
/*    */   }
/*    */ 
/*    */   static native void removeRuleImpl(long paramLong, int paramInt);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSStyleSheetImpl
 * JD-Core Version:    0.6.2
 */