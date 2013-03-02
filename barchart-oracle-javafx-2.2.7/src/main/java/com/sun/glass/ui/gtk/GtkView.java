/*     */ package com.sun.glass.ui.gtk;
/*     */ 
/*     */ import com.sun.glass.ui.Pen;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.View;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Map;
/*     */ 
/*     */ class GtkView extends View
/*     */ {
/*  21 */   private boolean imEnabled = false;
/*  22 */   private StringBuilder preedit = new StringBuilder();
/*     */   private int lastCaret;
/*     */ 
/*     */   public GtkView(Pen pen)
/*     */   {
/*  26 */     super(pen);
/*     */   }
/*     */ 
/*     */   protected void _enableInputMethodEvents(long ptr, boolean enable)
/*     */   {
/*  31 */     enableInputMethodEventsImpl(ptr, enable);
/*  32 */     if (this.imEnabled) {
/*  33 */       this.preedit.setLength(0);
/*     */     }
/*  35 */     this.imEnabled = enable;
/*     */   }
/*     */ 
/*     */   protected native long _create(Map paramMap);
/*     */ 
/*     */   protected native long _getNativeView(long paramLong);
/*     */ 
/*     */   protected native int _getX(long paramLong);
/*     */ 
/*     */   protected native int _getY(long paramLong);
/*     */ 
/*     */   protected native void _setParent(long paramLong1, long paramLong2);
/*     */ 
/*     */   protected native boolean _close(long paramLong);
/*     */ 
/*     */   protected native void _repaint(long paramLong);
/*     */ 
/*     */   protected native boolean _begin(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   protected native void _end(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */   protected void _uploadPixels(long ptr, Pixels pixels)
/*     */   {
/*  67 */     Buffer data = pixels.getPixels();
/*  68 */     if (data.isDirect() == true)
/*  69 */       _uploadPixelsDirect(ptr, data, pixels.getWidth(), pixels.getHeight());
/*  70 */     else if (data.hasArray() == true) {
/*  71 */       if (pixels.getBytesPerComponent() == 1) {
/*  72 */         ByteBuffer bytes = (ByteBuffer)data;
/*  73 */         _uploadPixelsByteArray(ptr, bytes.array(), bytes.arrayOffset(), pixels.getWidth(), pixels.getHeight());
/*     */       } else {
/*  75 */         IntBuffer ints = (IntBuffer)data;
/*  76 */         _uploadPixelsIntArray(ptr, ints.array(), ints.arrayOffset(), pixels.getWidth(), pixels.getHeight());
/*     */       }
/*     */     }
/*     */     else
/*  80 */       _uploadPixelsDirect(ptr, pixels.asByteBuffer(), pixels.getWidth(), pixels.getHeight());
/*     */   }
/*     */ 
/*     */   native void _uploadPixelsDirect(long paramLong, Buffer paramBuffer, int paramInt1, int paramInt2);
/*     */ 
/*     */   native void _uploadPixelsByteArray(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   native void _uploadPixelsIntArray(long paramLong, int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   protected native boolean _enterFullscreen(long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
/*     */ 
/*     */   protected native void _exitFullscreen(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   protected void notifyInputMethodDraw(String text, int first, int length, int caret)
/*     */   {
/*  95 */     if (text != null) {
/*  96 */       this.preedit.replace(first, first + length, text);
/*  97 */       notifyInputMethod(this.preedit.toString(), null, null, null, 0, caret, 0);
/*  98 */       this.lastCaret = caret;
/*     */     } else {
/* 100 */       this.preedit.setLength(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void notifyInputMethodCaret(int pos, int direction, int style) {
/* 105 */     switch (direction) {
/*     */     case 0:
/* 107 */       this.lastCaret += pos;
/* 108 */       break;
/*     */     case 1:
/* 110 */       this.lastCaret -= pos;
/* 111 */       break;
/*     */     case 10:
/* 113 */       this.lastCaret = pos;
/* 114 */       break;
/*     */     }
/*     */ 
/* 120 */     notifyInputMethod(this.preedit.toString(), null, null, null, 0, this.lastCaret, 0);
/*     */   }
/*     */ 
/*     */   protected native void enableInputMethodEventsImpl(long paramLong, boolean paramBoolean);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkView
 * JD-Core Version:    0.6.2
 */