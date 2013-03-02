/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLCollection;
/*    */ import org.w3c.dom.html.HTMLMapElement;
/*    */ 
/*    */ public class HTMLMapElementImpl extends HTMLElementImpl
/*    */   implements HTMLMapElement
/*    */ {
/*    */   HTMLMapElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLMapElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (HTMLMapElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public HTMLCollection getAreas()
/*    */   {
/* 20 */     return HTMLCollectionImpl.getImpl(getAreasImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getAreasImpl(long paramLong);
/*    */ 
/*    */   public String getName() {
/* 25 */     return getNameImpl(getPeer());
/*    */   }
/*    */   static native String getNameImpl(long paramLong);
/*    */ 
/*    */   public void setName(String value) {
/* 30 */     setNameImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setNameImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLMapElementImpl
 * JD-Core Version:    0.6.2
 */