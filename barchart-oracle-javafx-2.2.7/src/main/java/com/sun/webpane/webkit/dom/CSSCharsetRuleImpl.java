/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.css.CSSCharsetRule;
/*    */ 
/*    */ public class CSSCharsetRuleImpl extends CSSRuleImpl
/*    */   implements CSSCharsetRule
/*    */ {
/*    */   CSSCharsetRuleImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static CSSCharsetRule getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (CSSCharsetRule)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getEncoding()
/*    */   {
/* 20 */     return getEncodingImpl(getPeer());
/*    */   }
/*    */   static native String getEncodingImpl(long paramLong);
/*    */ 
/*    */   public void setEncoding(String value) throws DOMException {
/* 25 */     setEncodingImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setEncodingImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CSSCharsetRuleImpl
 * JD-Core Version:    0.6.2
 */