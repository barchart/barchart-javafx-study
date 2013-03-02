/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import java.nio.Buffer;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ 
/*    */ class GtkPixels extends Pixels
/*    */ {
/*    */   public GtkPixels(int width, int height, ByteBuffer data)
/*    */   {
/* 19 */     super(width, height, data);
/*    */   }
/*    */ 
/*    */   public GtkPixels(int width, int height, IntBuffer data) {
/* 23 */     super(width, height, data);
/*    */   }
/*    */ 
/*    */   protected void _fillDirectByteBuffer(ByteBuffer bb)
/*    */   {
/* 29 */     if (this.bytes != null) {
/* 30 */       this.bytes.rewind();
/* 31 */       if (this.bytes.isDirect() == true)
/* 32 */         _copyPixels(bb, this.bytes, getWidth() * getHeight());
/*    */       else {
/* 34 */         bb.put(this.bytes);
/*    */       }
/* 36 */       this.bytes.rewind();
/*    */     } else {
/* 38 */       this.ints.rewind();
/* 39 */       if (this.ints.isDirect() == true)
/* 40 */         _copyPixels(bb, this.ints, getWidth() * getHeight());
/*    */       else {
/* 42 */         for (int i = 0; i < this.ints.capacity(); i++) {
/* 43 */           int data = this.ints.get();
/* 44 */           bb.put((byte)(data >> 0 & 0xFF));
/* 45 */           bb.put((byte)(data >> 8 & 0xFF));
/* 46 */           bb.put((byte)(data >> 16 & 0xFF));
/* 47 */           bb.put((byte)(data >> 24 & 0xFF));
/*    */         }
/*    */       }
/* 50 */       this.ints.rewind();
/*    */     }
/*    */   }
/*    */ 
/*    */   protected native void _copyPixels(Buffer paramBuffer1, Buffer paramBuffer2, int paramInt);
/*    */ 
/*    */   protected native void _attachInt(long paramLong, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int[] paramArrayOfInt, int paramInt3);
/*    */ 
/*    */   protected native void _attachByte(long paramLong, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, byte[] paramArrayOfByte, int paramInt3);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkPixels
 * JD-Core Version:    0.6.2
 */