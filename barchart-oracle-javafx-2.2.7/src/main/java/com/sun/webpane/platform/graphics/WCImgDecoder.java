/*    */ package com.sun.webpane.platform.graphics;
/*    */ 
/*    */ public abstract class WCImgDecoder
/*    */ {
/*  9 */   private long pObserver = 0L;
/* 10 */   private long pImage = 0L;
/*    */ 
/*    */   public abstract void addImageData(byte[] paramArrayOfByte);
/*    */ 
/*    */   public abstract void getImageSize(int[] paramArrayOfInt);
/*    */ 
/*    */   public abstract int getFrameCount();
/*    */ 
/*    */   public abstract WCImageFrame getFrame(int paramInt, int[] paramArrayOfInt);
/*    */ 
/*    */   public abstract void loadFromResource(String paramString);
/*    */ 
/*    */   public void destroy()
/*    */   {
/* 49 */     this.pObserver = 0L;
/* 50 */     this.pImage = 0L;
/*    */   }
/*    */ 
/*    */   private void fwkSetObserver(long paramLong1, long paramLong2) {
/* 54 */     this.pObserver = paramLong1;
/* 55 */     this.pImage = paramLong2;
/*    */   }
/*    */ 
/*    */   private native void twkNotifyObserver(long paramLong1, long paramLong2);
/*    */ 
/*    */   protected void notifyObserver() {
/* 61 */     twkNotifyObserver(this.pObserver, this.pImage);
/*    */   }
/*    */ 
/*    */   public abstract String getFilenameExtension();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCImgDecoder
 * JD-Core Version:    0.6.2
 */