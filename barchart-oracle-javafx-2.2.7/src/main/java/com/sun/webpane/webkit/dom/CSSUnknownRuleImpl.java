/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.css.CSSUnknownRule;
/*    */ 
/*    */ public class CSSUnknownRuleImpl extends CSSRuleImpl
/*    */   implements CSSUnknownRule
/*    */ {
/*    */   CSSUnknownRuleImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static CSSUnknownRule getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (CSSUnknownRule)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSUnknownRuleImpl
 * JD-Core Version:    0.6.2
 */