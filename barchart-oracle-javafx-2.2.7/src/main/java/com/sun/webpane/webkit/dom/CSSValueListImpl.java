/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.css.CSSValue;
/*    */ import org.w3c.dom.css.CSSValueList;
/*    */ 
/*    */ public class CSSValueListImpl extends CSSValueImpl
/*    */   implements CSSValueList
/*    */ {
/*    */   CSSValueListImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static CSSValueList getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (CSSValueList)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public int getLength()
/*    */   {
/* 20 */     return getLengthImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native int getLengthImpl(long paramLong);
/*    */ 
/*    */   public CSSValue item(int index)
/*    */   {
/* 28 */     return CSSValueImpl.getImpl(itemImpl(getPeer(), index), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long itemImpl(long paramLong, int paramInt);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSValueListImpl
 * JD-Core Version:    0.6.2
 */