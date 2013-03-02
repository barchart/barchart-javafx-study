/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import com.sun.glass.utils.Disposer;
/*     */ import com.sun.glass.utils.DisposerRecord;
/*     */ import java.nio.IntBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.AllPermission;
/*     */ import java.security.Permission;
/*     */ 
/*     */ public abstract class Robot
/*     */ {
/*  17 */   private static final Permission allPermission = new AllPermission();
/*     */   private long ptr;
/*     */   private final RobotDisposerRecord disposerRecord;
/*     */ 
/*     */   protected abstract long _create();
/*     */ 
/*     */   protected Robot()
/*     */   {
/*  30 */     if (System.getSecurityManager() != null) {
/*  31 */       AccessController.checkPermission(allPermission);
/*     */     }
/*  33 */     this.ptr = _create();
/*  34 */     if (this.ptr == 0L) {
/*  35 */       throw new RuntimeException("could not create platform robot");
/*     */     }
/*  37 */     Disposer.addRecord(this, this.disposerRecord = new RobotDisposerRecord(this.ptr));
/*     */   }
/*     */ 
/*     */   protected abstract void _destroy(long paramLong);
/*     */ 
/*     */   public void destroy()
/*     */   {
/*  59 */     _destroy(this.ptr);
/*  60 */     this.ptr = 0L;
/*  61 */     this.disposerRecord.ptr = 0L;
/*     */   }
/*     */ 
/*     */   protected abstract void _keyPress(long paramLong, int paramInt);
/*     */ 
/*     */   public void keyPress(int code)
/*     */   {
/*  70 */     _keyPress(this.ptr, code);
/*     */   }
/*     */ 
/*     */   protected abstract void _keyRelease(long paramLong, int paramInt);
/*     */ 
/*     */   public void keyRelease(int code)
/*     */   {
/*  80 */     _keyRelease(this.ptr, code);
/*     */   }
/*     */ 
/*     */   protected abstract void _mouseMove(long paramLong, int paramInt1, int paramInt2);
/*     */ 
/*     */   public void mouseMove(int x, int y)
/*     */   {
/*  91 */     _mouseMove(this.ptr, x, y);
/*     */   }
/*     */ 
/*     */   protected abstract void _mousePress(long paramLong, int paramInt);
/*     */ 
/*     */   public void mousePress(int buttons)
/*     */   {
/* 105 */     _mousePress(this.ptr, buttons);
/*     */   }
/*     */ 
/*     */   protected abstract void _mouseRelease(long paramLong, int paramInt);
/*     */ 
/*     */   public void mouseRelease(int buttons)
/*     */   {
/* 115 */     _mouseRelease(this.ptr, buttons);
/*     */   }
/*     */ 
/*     */   protected abstract void _mouseWheel(long paramLong, int paramInt);
/*     */ 
/*     */   public void mouseWheel(int wheelAmt)
/*     */   {
/* 125 */     _mouseWheel(this.ptr, wheelAmt);
/*     */   }
/*     */   protected abstract int _getMouseX(long paramLong);
/*     */ 
/*     */   public int getMouseX() {
/* 130 */     return _getMouseX(this.ptr);
/*     */   }
/*     */   protected abstract int _getMouseY(long paramLong);
/*     */ 
/*     */   public int getMouseY() {
/* 135 */     return _getMouseY(this.ptr);
/*     */   }
/*     */ 
/*     */   protected abstract int _getPixelColor(long paramLong, int paramInt1, int paramInt2);
/*     */ 
/*     */   public int getPixelColor(int x, int y)
/*     */   {
/* 143 */     return _getPixelColor(this.ptr, x, y);
/*     */   }
/*     */ 
/*     */   protected abstract void _getScreenCapture(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt);
/*     */ 
/*     */   public Pixels getScreenCapture(int x, int y, int width, int height)
/*     */   {
/* 151 */     int[] data = new int[width * height];
/*     */ 
/* 153 */     _getScreenCapture(this.ptr, x, y, width, height, data);
/*     */ 
/* 155 */     return Application.GetApplication().createPixels(width, height, IntBuffer.wrap(data));
/*     */   }
/*     */ 
/*     */   private static class RobotDisposerRecord
/*     */     implements DisposerRecord
/*     */   {
/*     */     public volatile long ptr;
/*     */ 
/*     */     public RobotDisposerRecord(long ptr)
/*     */     {
/*  45 */       this.ptr = ptr;
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/*  50 */       if (this.ptr != 0L);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Robot
 * JD-Core Version:    0.6.2
 */