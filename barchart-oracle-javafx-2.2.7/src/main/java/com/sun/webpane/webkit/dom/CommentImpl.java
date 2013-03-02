/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.Comment;
/*    */ 
/*    */ public class CommentImpl extends CharacterDataImpl
/*    */   implements Comment
/*    */ {
/*    */   CommentImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static Comment getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (Comment)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CommentImpl
 * JD-Core Version:    0.6.2
 */