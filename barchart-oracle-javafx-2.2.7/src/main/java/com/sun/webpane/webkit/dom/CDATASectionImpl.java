/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.CDATASection;
/*    */ 
/*    */ public class CDATASectionImpl extends TextImpl
/*    */   implements CDATASection
/*    */ {
/*    */   CDATASectionImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static CDATASection getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (CDATASection)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CDATASectionImpl
 * JD-Core Version:    0.6.2
 */