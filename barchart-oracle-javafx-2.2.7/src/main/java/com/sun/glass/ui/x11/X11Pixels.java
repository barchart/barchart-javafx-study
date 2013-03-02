/*    */ package com.sun.glass.ui.x11;
/*    */ 
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import com.sun.glass.utils.Disposer;
/*    */ import com.sun.glass.utils.DisposerRecord;
/*    */ import java.nio.Buffer;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ 
/*    */ public class X11Pixels extends Pixels
/*    */ {
/* 35 */   private long ptr = 0L;
/*    */   private final PixelsDisposerRecord disposerRecord;
/*    */ 
/*    */   protected X11Pixels(int width, int height, ByteBuffer data)
/*    */   {
/* 22 */     super(width, height, data);
/* 23 */     Disposer.addRecord(this, this.disposerRecord = new PixelsDisposerRecord(this.ptr));
/*    */   }
/*    */ 
/*    */   protected X11Pixels(int width, int height, IntBuffer data) {
/* 27 */     super(width, height, data);
/* 28 */     Disposer.addRecord(this, this.disposerRecord = new PixelsDisposerRecord(this.ptr));
/*    */   }
/*    */ 
/*    */   static int getNativeFormat_impl() {
/* 32 */     return 1;
/*    */   }
/*    */ 
/*    */   private static native void _close(long paramLong);
/*    */ 
/*    */   protected native void _fillDirectByteBuffer(ByteBuffer paramByteBuffer);
/*    */ 
/*    */   protected native void _attachInt(long paramLong, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int[] paramArrayOfInt, int paramInt3);
/*    */ 
/*    */   protected native void _attachByte(long paramLong, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, byte[] paramArrayOfByte, int paramInt3);
/*    */ 
/*    */   protected native void _attachIntDirect(long paramLong, int paramInt1, int paramInt2, Buffer paramBuffer);
/*    */ 
/*    */   static
/*    */   {
/* 18 */     X11Application.initLibrary();
/*    */   }
/*    */ 
/*    */   private static class PixelsDisposerRecord
/*    */     implements DisposerRecord
/*    */   {
/*    */     public volatile long ptr;
/*    */ 
/*    */     public PixelsDisposerRecord(long ptr)
/*    */     {
/* 43 */       this.ptr = ptr;
/*    */     }
/*    */ 
/*    */     public void dispose() {
/* 47 */       if (this.ptr != 0L)
/* 48 */         X11Pixels._close(this.ptr);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.x11.X11Pixels
 * JD-Core Version:    0.6.2
 */