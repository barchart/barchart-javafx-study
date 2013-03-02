/*    */ package com.sun.glass.ui.win;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ final class WinPixels extends Pixels
/*    */ {
/* 40 */   private static final int nativeFormat = _initIDs();
/*    */ 
/*    */   protected WinPixels(int width, int height, ByteBuffer data)
/*    */   {
/* 22 */     super(width, height, data);
/*    */   }
/*    */ 
/*    */   protected WinPixels(int width, int height, IntBuffer data) {
/* 26 */     super(width, height, data);
/*    */   }
/*    */ 
/*    */   private static native int _initIDs();
/*    */ 
/*    */   static int getNativeFormat_impl()
/*    */   {
/* 44 */     return nativeFormat;
/*    */   }
/*    */ 
/*    */   protected native void _fillDirectByteBuffer(ByteBuffer paramByteBuffer);
/*    */ 
/*    */   protected native void _attachInt(long paramLong, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int[] paramArrayOfInt, int paramInt3);
/*    */ 
/*    */   protected native void _attachByte(long paramLong, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, byte[] paramArrayOfByte, int paramInt3);
/*    */ 
/*    */   static
/*    */   {
/* 33 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 36 */         Application.loadNativeLibrary();
/* 37 */         return null;
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinPixels
 * JD-Core Version:    0.6.2
 */