/*    */ package com.sun.javafx.iio.bmp;
/*    */ 
/*    */ import java.io.EOFException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ final class LEInputStream
/*    */ {
/*    */   public final InputStream in;
/*    */ 
/*    */   LEInputStream(InputStream paramInputStream)
/*    */   {
/* 32 */     this.in = paramInputStream;
/*    */   }
/*    */ 
/*    */   public final short readShort() throws IOException {
/* 36 */     int i = this.in.read();
/* 37 */     int j = this.in.read();
/* 38 */     if ((i | j) < 0) {
/* 39 */       throw new EOFException();
/*    */     }
/* 41 */     return (short)((j << 8) + i);
/*    */   }
/*    */ 
/*    */   public final int readInt() throws IOException {
/* 45 */     int i = this.in.read();
/* 46 */     int j = this.in.read();
/* 47 */     int k = this.in.read();
/* 48 */     int m = this.in.read();
/* 49 */     if ((i | j | k | m) < 0) {
/* 50 */       throw new EOFException();
/*    */     }
/* 52 */     return (m << 24) + (k << 16) + (j << 8) + i;
/*    */   }
/*    */ 
/*    */   public final void skipBytes(int paramInt) throws IOException {
/* 56 */     int i = (int)this.in.skip(paramInt);
/* 57 */     if (i < paramInt)
/* 58 */       throw new EOFException();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.bmp.LEInputStream
 * JD-Core Version:    0.6.2
 */