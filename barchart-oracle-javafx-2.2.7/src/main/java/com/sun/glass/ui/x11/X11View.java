/*     */ package com.sun.glass.ui.x11;
/*     */ 
/*     */ import com.sun.glass.ui.Pen;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.glass.ui.Window;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class X11View extends View
/*     */ {
/*  33 */   private static long multiClickTime = 300L;
/*  34 */   private static int multiClickMaxX = 2;
/*  35 */   private static int multiClickMaxY = 2;
/*     */   private int x;
/*     */   private int y;
/*     */   private long nativePtr;
/*     */ 
/*     */   protected X11View(Pen pen)
/*     */   {
/*  29 */     super(pen);
/*     */   }
/*     */ 
/*     */   private static native long _getMultiClickTime_impl();
/*     */ 
/*     */   private static native int _getMultiClickMaxX_impl();
/*     */ 
/*     */   private static native int _getMultiClickMaxY_impl();
/*     */ 
/*     */   static long getMultiClickTime_impl()
/*     */   {
/*  47 */     if (multiClickTime == -1L)
/*  48 */       multiClickTime = _getMultiClickTime_impl();
/*  49 */     return multiClickTime;
/*     */   }
/*     */ 
/*     */   static int getMultiClickMaxX_impl() {
/*  53 */     if (multiClickMaxX == -1)
/*  54 */       multiClickMaxX = _getMultiClickMaxX_impl();
/*  55 */     return multiClickMaxX;
/*     */   }
/*     */ 
/*     */   static int getMultiClickMaxY_impl() {
/*  59 */     if (multiClickMaxY == -1)
/*  60 */       multiClickMaxY = _getMultiClickMaxY_impl();
/*  61 */     return multiClickMaxY;
/*     */   }
/*     */ 
/*     */   protected void _enableInputMethodEvents(long ptr, boolean enable)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected native long _create(Map paramMap);
/*     */ 
/*     */   protected long _getNativeView(long ptr)
/*     */   {
/*  73 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   protected int _getX(long ptr)
/*     */   {
/*  78 */     return this.x;
/*     */   }
/*     */ 
/*     */   protected int _getY(long ptr)
/*     */   {
/*  83 */     return this.y;
/*     */   }
/*     */ 
/*     */   protected native void _setParent(long paramLong1, long paramLong2);
/*     */ 
/*     */   protected native boolean _close(long paramLong);
/*     */ 
/*     */   protected void _repaint(long ptr)
/*     */   {
/*  95 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   protected native boolean _begin(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   protected native void _end(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */   protected void _uploadPixels(long ptr, Pixels pixels)
/*     */   {
/* 105 */     if (pixels != null) {
/* 106 */       X11Pixels xpixels = (X11Pixels)pixels;
/* 107 */       long windowPtr = getWindow().getNativeWindow();
/* 108 */       Buffer data = xpixels.getPixels();
/* 109 */       int width = xpixels.getWidth();
/* 110 */       int height = xpixels.getHeight();
/* 111 */       if (data.isDirect() == true) {
/* 112 */         System.out.println("DIRECT");
/* 113 */         xpixels._attachIntDirect(windowPtr, width, height, data);
/* 114 */       } else if (data.hasArray() == true) {
/* 115 */         if (xpixels.getBytesPerComponent() == 1) {
/* 116 */           ByteBuffer bytes = (ByteBuffer)data;
/* 117 */           xpixels._attachByte(windowPtr, width, height, bytes, bytes.array(), bytes.arrayOffset());
/*     */         } else {
/* 119 */           IntBuffer ints = (IntBuffer)data;
/* 120 */           int[] intArray = ints.array();
/* 121 */           xpixels._attachInt(windowPtr, width, height, ints, intArray, ints.arrayOffset());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void _notifyMove(int x, int y) {
/* 128 */     this.x = x;
/* 129 */     this.y = y;
/*     */   }
/*     */ 
/*     */   protected native boolean _enterFullscreen(long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
/*     */ 
/*     */   protected native void _exitFullscreen(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   static
/*     */   {
/*  25 */     X11Application.initLibrary();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.x11.X11View
 * JD-Core Version:    0.6.2
 */