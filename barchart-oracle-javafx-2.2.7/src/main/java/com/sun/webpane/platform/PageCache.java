/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ public final class PageCache
/*    */ {
/*    */   private PageCache()
/*    */   {
/* 15 */     throw new AssertionError();
/*    */   }
/*    */ 
/*    */   public static int getCapacity()
/*    */   {
/* 24 */     return twkGetCapacity();
/*    */   }
/*    */ 
/*    */   public static void setCapacity(int paramInt)
/*    */   {
/* 33 */     if (paramInt < 0) {
/* 34 */       throw new IllegalArgumentException("capacity is negative:" + paramInt);
/*    */     }
/*    */ 
/* 37 */     twkSetCapacity(paramInt);
/*    */   }
/*    */ 
/*    */   private static native int twkGetCapacity();
/*    */ 
/*    */   private static native void twkSetCapacity(int paramInt);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.PageCache
 * JD-Core Version:    0.6.2
 */