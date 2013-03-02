/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.css.CSSMediaRule;
/*    */ import org.w3c.dom.css.CSSRuleList;
/*    */ import org.w3c.dom.stylesheets.MediaList;
/*    */ 
/*    */ public class CSSMediaRuleImpl extends CSSRuleImpl
/*    */   implements CSSMediaRule
/*    */ {
/*    */   CSSMediaRuleImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 12 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static CSSMediaRule getImpl(long peer, long contextPeer, long rootPeer) {
/* 16 */     return (CSSMediaRule)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public MediaList getMedia()
/*    */   {
/* 22 */     return MediaListImpl.getImpl(getMediaImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getMediaImpl(long paramLong);
/*    */ 
/*    */   public CSSRuleList getCssRules() {
/* 27 */     return CSSRuleListImpl.getImpl(getCssRulesImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long getCssRulesImpl(long paramLong);
/*    */ 
/*    */   public int insertRule(String rule, int index)
/*    */     throws DOMException
/*    */   {
/* 36 */     return insertRuleImpl(getPeer(), rule, index);
/*    */   }
/*    */ 
/*    */   static native int insertRuleImpl(long paramLong, String paramString, int paramInt);
/*    */ 
/*    */   public void deleteRule(int index)
/*    */     throws DOMException
/*    */   {
/* 47 */     deleteRuleImpl(getPeer(), index);
/*    */   }
/*    */ 
/*    */   static native void deleteRuleImpl(long paramLong, int paramInt);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSMediaRuleImpl
 * JD-Core Version:    0.6.2
 */