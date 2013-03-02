/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.html.HTMLCollection;
/*    */ import org.w3c.dom.html.HTMLElement;
/*    */ import org.w3c.dom.html.HTMLTableSectionElement;
/*    */ 
/*    */ public class HTMLTableSectionElementImpl extends HTMLElementImpl
/*    */   implements HTMLTableSectionElement
/*    */ {
/*    */   HTMLTableSectionElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 12 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLTableSectionElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 16 */     return (HTMLTableSectionElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getAlign()
/*    */   {
/* 22 */     return getAlignImpl(getPeer());
/*    */   }
/*    */   static native String getAlignImpl(long paramLong);
/*    */ 
/*    */   public void setAlign(String value) {
/* 27 */     setAlignImpl(getPeer(), value);
/*    */   }
/*    */   static native void setAlignImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getCh() {
/* 32 */     return getChImpl(getPeer());
/*    */   }
/*    */   static native String getChImpl(long paramLong);
/*    */ 
/*    */   public void setCh(String value) {
/* 37 */     setChImpl(getPeer(), value);
/*    */   }
/*    */   static native void setChImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getChOff() {
/* 42 */     return getChOffImpl(getPeer());
/*    */   }
/*    */   static native String getChOffImpl(long paramLong);
/*    */ 
/*    */   public void setChOff(String value) {
/* 47 */     setChOffImpl(getPeer(), value);
/*    */   }
/*    */   static native void setChOffImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getVAlign() {
/* 52 */     return getVAlignImpl(getPeer());
/*    */   }
/*    */   static native String getVAlignImpl(long paramLong);
/*    */ 
/*    */   public void setVAlign(String value) {
/* 57 */     setVAlignImpl(getPeer(), value);
/*    */   }
/*    */   static native void setVAlignImpl(long paramLong, String paramString);
/*    */ 
/*    */   public HTMLCollection getRows() {
/* 62 */     return HTMLCollectionImpl.getImpl(getRowsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long getRowsImpl(long paramLong);
/*    */ 
/*    */   public HTMLElement insertRow(int index)
/*    */     throws DOMException
/*    */   {
/* 70 */     return HTMLElementImpl.getImpl(insertRowImpl(getPeer(), index), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long insertRowImpl(long paramLong, int paramInt);
/*    */ 
/*    */   public void deleteRow(int index)
/*    */     throws DOMException
/*    */   {
/* 79 */     deleteRowImpl(getPeer(), index);
/*    */   }
/*    */ 
/*    */   static native void deleteRowImpl(long paramLong, int paramInt);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLTableSectionElementImpl
 * JD-Core Version:    0.6.2
 */