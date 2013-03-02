/*    */ package com.sun.glass.ui.mac;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import java.nio.Buffer;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ final class MacPixels extends Pixels
/*    */ {
/* 32 */   private static final int nativeFormat = _initIDs();
/*    */ 
/*    */   private static native int _initIDs();
/*    */ 
/* 36 */   static int getNativeFormat_impl() { return nativeFormat; }
/*    */ 
/*    */   protected MacPixels(int width, int height, ByteBuffer data)
/*    */   {
/* 40 */     super(width, height, data);
/*    */   }
/*    */ 
/*    */   protected MacPixels(int width, int height, IntBuffer data) {
/* 44 */     super(width, height, data);
/*    */   }
/*    */ 
/*    */   protected void _fillDirectByteBuffer(ByteBuffer bb)
/*    */   {
/* 49 */     if (this.bytes != null) {
/* 50 */       this.bytes.rewind();
/* 51 */       if (this.bytes.isDirect() == true)
/* 52 */         _copyPixels(bb, this.bytes, getWidth() * getHeight());
/*    */       else {
/* 54 */         bb.put(this.bytes);
/*    */       }
/* 56 */       this.bytes.rewind();
/*    */     } else {
/* 58 */       this.ints.rewind();
/* 59 */       if (this.ints.isDirect() == true)
/* 60 */         _copyPixels(bb, this.ints, getWidth() * getHeight());
/*    */       else {
/* 62 */         for (int i = 0; i < this.ints.capacity(); i++) {
/* 63 */           int data = this.ints.get();
/* 64 */           bb.put((byte)(data >> 0 & 0xFF));
/* 65 */           bb.put((byte)(data >> 8 & 0xFF));
/* 66 */           bb.put((byte)(data >> 16 & 0xFF));
/* 67 */           bb.put((byte)(data >> 24 & 0xFF));
/*    */         }
/*    */       }
/* 70 */       this.ints.rewind();
/*    */     }
/*    */   }
/*    */   protected native void _copyPixels(Buffer paramBuffer1, Buffer paramBuffer2, int paramInt);
/*    */ 
/*    */   protected native void _attachInt(long paramLong, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int[] paramArrayOfInt, int paramInt3);
/*    */ 
/*    */   protected native void _attachByte(long paramLong, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, byte[] paramArrayOfByte, int paramInt3);
/*    */ 
/* 79 */   public String toString() { return "MacPixels [" + getWidth() + "x" + getHeight() + "]: " + super.toString(); }
/*    */ 
/*    */ 
/*    */   static
/*    */   {
/* 25 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 28 */         Application.loadNativeLibrary();
/* 29 */         return null;
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacPixels
 * JD-Core Version:    0.6.2
 */