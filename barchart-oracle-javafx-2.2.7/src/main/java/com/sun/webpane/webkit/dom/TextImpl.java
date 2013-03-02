/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.Text;
/*    */ 
/*    */ public class TextImpl extends CharacterDataImpl
/*    */   implements Text
/*    */ {
/*    */   TextImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static Text getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (Text)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getWholeText()
/*    */   {
/* 20 */     return getWholeTextImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native String getWholeTextImpl(long paramLong);
/*    */ 
/*    */   public Text splitText(int offset)
/*    */     throws DOMException
/*    */   {
/* 28 */     return getImpl(splitTextImpl(getPeer(), offset), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long splitTextImpl(long paramLong, int paramInt);
/*    */ 
/*    */   public Text replaceWholeText(String content)
/*    */     throws DOMException
/*    */   {
/* 37 */     return getImpl(replaceWholeTextImpl(getPeer(), content), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long replaceWholeTextImpl(long paramLong, String paramString);
/*    */ 
/*    */   public boolean isElementContentWhitespace()
/*    */   {
/* 47 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.TextImpl
 * JD-Core Version:    0.6.2
 */