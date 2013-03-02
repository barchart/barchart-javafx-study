/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.CharacterData;
/*    */ import org.w3c.dom.DOMException;
/*    */ 
/*    */ public class CharacterDataImpl extends NodeImpl
/*    */   implements CharacterData
/*    */ {
/*    */   CharacterDataImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 10 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static CharacterData getImpl(long peer, long contextPeer, long rootPeer) {
/* 14 */     return (CharacterData)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getData()
/*    */   {
/* 20 */     return getDataImpl(getPeer());
/*    */   }
/*    */   static native String getDataImpl(long paramLong);
/*    */ 
/*    */   public void setData(String value) throws DOMException {
/* 25 */     setDataImpl(getPeer(), value);
/*    */   }
/*    */   static native void setDataImpl(long paramLong, String paramString);
/*    */ 
/*    */   public int getLength() {
/* 30 */     return getLengthImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native int getLengthImpl(long paramLong);
/*    */ 
/*    */   public String substringData(int offset, int length)
/*    */     throws DOMException
/*    */   {
/* 39 */     return substringDataImpl(getPeer(), offset, length);
/*    */   }
/*    */ 
/*    */   static native String substringDataImpl(long paramLong, int paramInt1, int paramInt2);
/*    */ 
/*    */   public void appendData(String data)
/*    */     throws DOMException
/*    */   {
/* 50 */     appendDataImpl(getPeer(), data);
/*    */   }
/*    */ 
/*    */   static native void appendDataImpl(long paramLong, String paramString);
/*    */ 
/*    */   public void insertData(int offset, String data)
/*    */     throws DOMException
/*    */   {
/* 60 */     insertDataImpl(getPeer(), offset, data);
/*    */   }
/*    */ 
/*    */   static native void insertDataImpl(long paramLong, int paramInt, String paramString);
/*    */ 
/*    */   public void deleteData(int offset, int length)
/*    */     throws DOMException
/*    */   {
/* 72 */     deleteDataImpl(getPeer(), offset, length);
/*    */   }
/*    */ 
/*    */   static native void deleteDataImpl(long paramLong, int paramInt1, int paramInt2);
/*    */ 
/*    */   public void replaceData(int offset, int length, String data)
/*    */     throws DOMException
/*    */   {
/* 85 */     replaceDataImpl(getPeer(), offset, length, data);
/*    */   }
/*    */ 
/*    */   static native void replaceDataImpl(long paramLong, int paramInt1, int paramInt2, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CharacterDataImpl
 * JD-Core Version:    0.6.2
 */