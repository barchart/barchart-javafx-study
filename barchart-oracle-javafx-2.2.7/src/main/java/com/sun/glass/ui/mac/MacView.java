/*     */ package com.sun.glass.ui.mac;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Pen;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.View;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Map;
/*     */ 
/*     */ final class MacView extends View
/*     */ {
/*  48 */   private static final long multiClickTime = _getMultiClickTime_impl();
/*  49 */   private static final int multiClickMaxX = _getMultiClickMaxX_impl();
/*  50 */   private static final int multiClickMaxY = _getMultiClickMaxY_impl();
/*     */ 
/*     */   protected MacView(Pen pen)
/*     */   {
/*  26 */     super(pen);
/*     */   }
/*     */ 
/*     */   private static native void _initIDs();
/*     */ 
/*     */   private static native long _getMultiClickTime_impl();
/*     */ 
/*     */   private static native int _getMultiClickMaxX_impl();
/*     */ 
/*     */   private static native int _getMultiClickMaxY_impl();
/*     */ 
/*     */   static long getMultiClickTime_impl()
/*     */   {
/*  54 */     return multiClickTime;
/*     */   }
/*     */ 
/*     */   static int getMultiClickMaxX_impl() {
/*  58 */     return multiClickMaxX;
/*     */   }
/*     */ 
/*     */   static int getMultiClickMaxY_impl() {
/*  62 */     return multiClickMaxY; } 
/*     */   protected native long _create(Map paramMap);
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
/*     */   protected native boolean _enterFullscreen(long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
/*     */ 
/*     */   protected native void _exitFullscreen(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   protected native void _enableInputMethodEvents(long paramLong, boolean paramBoolean);
/*     */ 
/*  78 */   protected void _uploadPixels(long ptr, Pixels pixels) { Buffer data = pixels.getPixels();
/*  79 */     if (data.isDirect() == true)
/*  80 */       _uploadPixelsDirect(ptr, data, pixels.getWidth(), pixels.getHeight());
/*  81 */     else if (data.hasArray() == true) {
/*  82 */       if (pixels.getBytesPerComponent() == 1) {
/*  83 */         ByteBuffer bytes = (ByteBuffer)data;
/*  84 */         _uploadPixelsByteArray(ptr, bytes.array(), bytes.arrayOffset(), pixels.getWidth(), pixels.getHeight());
/*     */       } else {
/*  86 */         IntBuffer ints = (IntBuffer)data;
/*  87 */         _uploadPixelsIntArray(ptr, ints.array(), ints.arrayOffset(), pixels.getWidth(), pixels.getHeight());
/*     */       }
/*     */     }
/*     */     else
/*  91 */       _uploadPixelsDirect(ptr, pixels.asByteBuffer(), pixels.getWidth(), pixels.getHeight());  } 
/*     */   native void _uploadPixelsDirect(long paramLong, Buffer paramBuffer, int paramInt1, int paramInt2);
/*     */ 
/*     */   native void _uploadPixelsByteArray(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   native void _uploadPixelsIntArray(long paramLong, int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   protected long _getNativeView(long ptr) {
/*  99 */     return ptr;
/*     */   }
/*     */   protected native long _getNativeLayer(long paramLong);
/*     */ 
/*     */   public long getNativeLayer() {
/* 104 */     return _getNativeLayer(getNativeView());
/*     */   }
/*     */ 
/*     */   protected native int _getNativeRemoteLayerId(long paramLong, String paramString);
/*     */ 
/*     */   public int getNativeRemoteLayerId(String serverName) {
/* 110 */     return _getNativeRemoteLayerId(getNativeLayer(), serverName);
/*     */   }
/*     */ 
/*     */   protected native void _hostRemoteLayerId(long paramLong, int paramInt);
/*     */ 
/*     */   public void hostRemoteLayerId(int nativeLayerId) {
/* 116 */     _hostRemoteLayerId(getNativeLayer(), nativeLayerId);
/*     */   }
/*     */ 
/*     */   protected void notifyInputMethodMac(String str, int attrib, int length, int cursor) {
/* 120 */     byte[] atts = new byte[1];
/* 121 */     atts[0] = ((byte)attrib);
/* 122 */     int[] attBounds = new int[2];
/* 123 */     attBounds[0] = 0;
/* 124 */     attBounds[1] = length;
/* 125 */     if (attrib == 4)
/*     */     {
/* 127 */       notifyInputMethod(str, null, attBounds, atts, length, cursor, 0);
/*     */     }
/*     */     else
/* 130 */       notifyInputMethod(str, null, attBounds, atts, 0, cursor, 0);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  39 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  42 */         Application.loadNativeLibrary();
/*  43 */         return null;
/*     */       }
/*     */     });
/*  46 */     _initIDs();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacView
 * JD-Core Version:    0.6.2
 */