/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import java.security.AccessControlContext;
/*    */ import java.security.AccessController;
/*    */ import netscape.javascript.JSException;
/*    */ 
/*    */ class JSObject extends netscape.javascript.JSObject
/*    */ {
/* 10 */   public static final String UNDEFINED = new String("undefined");
/*    */   protected long jsPeer;
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */ 
/*    */   protected JSObject(long paramLong1, long paramLong2, long paramLong3)
/*    */   {
/* 18 */     this.jsPeer = paramLong1;
/* 19 */     this.contextPeer = paramLong2;
/* 20 */     this.rootPeer = paramLong3;
/*    */   }
/*    */ 
/*    */   protected long getJSPeer() {
/* 24 */     return this.jsPeer;
/*    */   }
/*    */ 
/*    */   public Object eval(String paramString) throws JSException
/*    */   {
/* 29 */     return evalImpl(getJSPeer(), this.contextPeer, this.rootPeer, paramString);
/*    */   }
/*    */ 
/*    */   public Object getMember(String paramString)
/*    */   {
/* 34 */     return getMemberImpl(getJSPeer(), this.contextPeer, this.rootPeer, paramString);
/*    */   }
/*    */ 
/*    */   public void setMember(String paramString, Object paramObject) throws JSException
/*    */   {
/* 39 */     setMemberImpl(getJSPeer(), this.contextPeer, this.rootPeer, paramString, paramObject, AccessController.getContext());
/*    */   }
/*    */ 
/*    */   public void removeMember(String paramString)
/*    */     throws JSException
/*    */   {
/* 45 */     removeMemberImpl(getJSPeer(), this.contextPeer, this.rootPeer, paramString);
/*    */   }
/*    */ 
/*    */   public Object getSlot(int paramInt) throws JSException
/*    */   {
/* 50 */     return getSlotImpl(getJSPeer(), this.contextPeer, this.rootPeer, paramInt);
/*    */   }
/*    */ 
/*    */   public void setSlot(int paramInt, Object paramObject) throws JSException
/*    */   {
/* 55 */     setSlotImpl(getJSPeer(), this.contextPeer, this.rootPeer, paramInt, paramObject, AccessController.getContext());
/*    */   }
/*    */ 
/*    */   public Object call(String paramString, Object[] paramArrayOfObject)
/*    */     throws JSException
/*    */   {
/* 61 */     return callImpl(getJSPeer(), this.contextPeer, this.rootPeer, paramString, paramArrayOfObject, AccessController.getContext());
/*    */   }
/*    */ 
/*    */   static native Object callImpl(long paramLong1, long paramLong2, long paramLong3, String paramString, Object[] paramArrayOfObject, AccessControlContext paramAccessControlContext);
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 69 */     return (paramObject == this) || ((paramObject != null) && (paramObject.getClass() == JSObject.class) && (getJSPeer() == ((JSObject)paramObject).getJSPeer()));
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 76 */     long l = getJSPeer();
/* 77 */     return (int)(l ^ l >> 17);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 82 */     return toStringImpl(getJSPeer(), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native Object evalImpl(long paramLong1, long paramLong2, long paramLong3, String paramString);
/*    */ 
/*    */   static native Object getMemberImpl(long paramLong1, long paramLong2, long paramLong3, String paramString);
/*    */ 
/*    */   static native void setMemberImpl(long paramLong1, long paramLong2, long paramLong3, String paramString, Object paramObject, AccessControlContext paramAccessControlContext);
/*    */ 
/*    */   static native void removeMemberImpl(long paramLong1, long paramLong2, long paramLong3, String paramString);
/*    */ 
/*    */   static native Object getSlotImpl(long paramLong1, long paramLong2, long paramLong3, int paramInt);
/*    */ 
/*    */   static native void setSlotImpl(long paramLong1, long paramLong2, long paramLong3, int paramInt, Object paramObject, AccessControlContext paramAccessControlContext);
/*    */ 
/*    */   static native String toStringImpl(long paramLong1, long paramLong2, long paramLong3);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.JSObject
 * JD-Core Version:    0.6.2
 */