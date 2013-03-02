/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.ProcessingInstruction;
/*    */ import org.w3c.dom.stylesheets.StyleSheet;
/*    */ 
/*    */ public class ProcessingInstructionImpl extends NodeImpl
/*    */   implements ProcessingInstruction
/*    */ {
/*    */   ProcessingInstructionImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 11 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static ProcessingInstruction getImpl(long peer, long contextPeer, long rootPeer) {
/* 15 */     return (ProcessingInstruction)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getTarget()
/*    */   {
/* 21 */     return getTargetImpl(getPeer());
/*    */   }
/*    */   static native String getTargetImpl(long paramLong);
/*    */ 
/*    */   public String getData() {
/* 26 */     return getDataImpl(getPeer());
/*    */   }
/*    */   static native String getDataImpl(long paramLong);
/*    */ 
/*    */   public void setData(String value) throws DOMException {
/* 31 */     setDataImpl(getPeer(), value);
/*    */   }
/*    */   static native void setDataImpl(long paramLong, String paramString);
/*    */ 
/*    */   public StyleSheet getSheet() {
/* 36 */     return StyleSheetImpl.getImpl(getSheetImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long getSheetImpl(long paramLong);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.ProcessingInstructionImpl
 * JD-Core Version:    0.6.2
 */