/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.Attr;
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.TypeInfo;
/*    */ 
/*    */ public class AttrImpl extends NodeImpl
/*    */   implements Attr
/*    */ {
/*    */   AttrImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 12 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static Attr getImpl(long peer, long contextPeer, long rootPeer) {
/* 16 */     return (Attr)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 22 */     return getNameImpl(getPeer());
/*    */   }
/*    */   static native String getNameImpl(long paramLong);
/*    */ 
/*    */   public boolean getSpecified() {
/* 27 */     return getSpecifiedImpl(getPeer());
/*    */   }
/*    */   static native boolean getSpecifiedImpl(long paramLong);
/*    */ 
/*    */   public String getValue() {
/* 32 */     return getValueImpl(getPeer());
/*    */   }
/*    */   static native String getValueImpl(long paramLong);
/*    */ 
/*    */   public void setValue(String value) throws DOMException {
/* 37 */     setValueImpl(getPeer(), value);
/*    */   }
/*    */   static native void setValueImpl(long paramLong, String paramString);
/*    */ 
/*    */   public Element getOwnerElement() {
/* 42 */     return ElementImpl.getImpl(getOwnerElementImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getOwnerElementImpl(long paramLong);
/*    */ 
/*    */   public boolean isId() {
/* 47 */     return isIdImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native boolean isIdImpl(long paramLong);
/*    */ 
/*    */   public TypeInfo getSchemaTypeInfo()
/*    */   {
/* 54 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.AttrImpl
 * JD-Core Version:    0.6.2
 */