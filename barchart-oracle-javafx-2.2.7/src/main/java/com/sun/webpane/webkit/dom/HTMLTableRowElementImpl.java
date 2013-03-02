/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.html.HTMLCollection;
/*    */ import org.w3c.dom.html.HTMLElement;
/*    */ import org.w3c.dom.html.HTMLTableRowElement;
/*    */ 
/*    */ public class HTMLTableRowElementImpl extends HTMLElementImpl
/*    */   implements HTMLTableRowElement
/*    */ {
/*    */   HTMLTableRowElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 12 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLTableRowElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 16 */     return (HTMLTableRowElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public int getRowIndex()
/*    */   {
/* 22 */     return getRowIndexImpl(getPeer());
/*    */   }
/*    */   static native int getRowIndexImpl(long paramLong);
/*    */ 
/*    */   public int getSectionRowIndex() {
/* 27 */     return getSectionRowIndexImpl(getPeer());
/*    */   }
/*    */   static native int getSectionRowIndexImpl(long paramLong);
/*    */ 
/*    */   public HTMLCollection getCells() {
/* 32 */     return HTMLCollectionImpl.getImpl(getCellsImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getCellsImpl(long paramLong);
/*    */ 
/*    */   public String getAlign() {
/* 37 */     return getAlignImpl(getPeer());
/*    */   }
/*    */   static native String getAlignImpl(long paramLong);
/*    */ 
/*    */   public void setAlign(String value) {
/* 42 */     setAlignImpl(getPeer(), value);
/*    */   }
/*    */   static native void setAlignImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getBgColor() {
/* 47 */     return getBgColorImpl(getPeer());
/*    */   }
/*    */   static native String getBgColorImpl(long paramLong);
/*    */ 
/*    */   public void setBgColor(String value) {
/* 52 */     setBgColorImpl(getPeer(), value);
/*    */   }
/*    */   static native void setBgColorImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getCh() {
/* 57 */     return getChImpl(getPeer());
/*    */   }
/*    */   static native String getChImpl(long paramLong);
/*    */ 
/*    */   public void setCh(String value) {
/* 62 */     setChImpl(getPeer(), value);
/*    */   }
/*    */   static native void setChImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getChOff() {
/* 67 */     return getChOffImpl(getPeer());
/*    */   }
/*    */   static native String getChOffImpl(long paramLong);
/*    */ 
/*    */   public void setChOff(String value) {
/* 72 */     setChOffImpl(getPeer(), value);
/*    */   }
/*    */   static native void setChOffImpl(long paramLong, String paramString);
/*    */ 
/*    */   public String getVAlign() {
/* 77 */     return getVAlignImpl(getPeer());
/*    */   }
/*    */   static native String getVAlignImpl(long paramLong);
/*    */ 
/*    */   public void setVAlign(String value) {
/* 82 */     setVAlignImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setVAlignImpl(long paramLong, String paramString);
/*    */ 
/*    */   public HTMLElement insertCell(int index)
/*    */     throws DOMException
/*    */   {
/* 90 */     return HTMLElementImpl.getImpl(insertCellImpl(getPeer(), index), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long insertCellImpl(long paramLong, int paramInt);
/*    */ 
/*    */   public void deleteCell(int index)
/*    */     throws DOMException
/*    */   {
/* 99 */     deleteCellImpl(getPeer(), index);
/*    */   }
/*    */ 
/*    */   static native void deleteCellImpl(long paramLong, int paramInt);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLTableRowElementImpl
 * JD-Core Version:    0.6.2
 */