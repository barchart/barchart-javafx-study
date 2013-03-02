/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLDirectoryElement;
/*    */ 
/*    */ public class HTMLDirectoryElementImpl extends HTMLElementImpl
/*    */   implements HTMLDirectoryElement
/*    */ {
/*    */   HTMLDirectoryElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLDirectoryElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLDirectoryElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public boolean getCompact()
/*    */   {
/* 19 */     return getCompactImpl(getPeer());
/*    */   }
/*    */   static native boolean getCompactImpl(long paramLong);
/*    */ 
/*    */   public void setCompact(boolean value) {
/* 24 */     setCompactImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setCompactImpl(long paramLong, boolean paramBoolean);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLDirectoryElementImpl
 * JD-Core Version:    0.6.2
 */