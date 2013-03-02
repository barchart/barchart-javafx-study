/*     */ package com.sun.prism.es2.gl.x11;
/*     */ 
/*     */ import com.sun.prism.es2.gl.GLContext;
/*     */ import com.sun.prism.es2.gl.GLDrawable;
/*     */ import com.sun.prism.es2.gl.GLFactory;
/*     */ import com.sun.prism.es2.gl.GLGPUInfo;
/*     */ import com.sun.prism.es2.gl.GLPixelFormat;
/*     */ import com.sun.prism.es2.gl.GLPixelFormat.Attributes;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class X11GLFactory extends GLFactory
/*     */ {
/*  30 */   private GLGPUInfo[] preQualificationFilter = { new GLGPUInfo("nvidia", null) };
/*  31 */   private GLGPUInfo[] blackList = null;
/*     */ 
/*     */   private static native long nInitialize(int[] paramArrayOfInt);
/*     */ 
/*     */   private static native int nGetAdapterOrdinal(long paramLong);
/*     */ 
/*     */   private static native int nGetAdapterCount();
/*     */ 
/*     */   private static native int nGetDefaultScreen(long paramLong);
/*     */ 
/*     */   private static native long nGetDisplay(long paramLong);
/*     */ 
/*     */   private static native long nGetVisualID(long paramLong);
/*     */ 
/*  35 */   protected GLGPUInfo[] getPreQualificationFilter() { return this.preQualificationFilter; }
/*     */ 
/*     */ 
/*     */   protected GLGPUInfo[] getBlackList()
/*     */   {
/*  40 */     return this.blackList;
/*     */   }
/*     */ 
/*     */   public GLContext createGLContext(long paramLong)
/*     */   {
/*  45 */     return new X11GLContext(paramLong);
/*     */   }
/*     */ 
/*     */   public GLContext createGLContext(GLDrawable paramGLDrawable, GLPixelFormat paramGLPixelFormat, GLContext paramGLContext, boolean paramBoolean)
/*     */   {
/*  52 */     return new X11GLContext(paramGLDrawable, paramGLPixelFormat, paramBoolean);
/*     */   }
/*     */ 
/*     */   public GLDrawable createDummyGLDrawable(GLPixelFormat paramGLPixelFormat)
/*     */   {
/*  57 */     return new X11GLDrawable(paramGLPixelFormat);
/*     */   }
/*     */ 
/*     */   public GLDrawable createGLDrawable(long paramLong, GLPixelFormat paramGLPixelFormat)
/*     */   {
/*  62 */     return new X11GLDrawable(paramLong, paramGLPixelFormat);
/*     */   }
/*     */ 
/*     */   public GLPixelFormat createGLPixelFormat(long paramLong, GLPixelFormat.Attributes paramAttributes)
/*     */   {
/*  67 */     return new X11GLPixelFormat(paramLong, paramAttributes);
/*     */   }
/*     */ 
/*     */   public boolean initialize(Class paramClass, GLPixelFormat.Attributes paramAttributes)
/*     */   {
/*  74 */     int[] arrayOfInt = new int[7];
/*     */ 
/*  76 */     arrayOfInt[0] = paramAttributes.getRedSize();
/*  77 */     arrayOfInt[1] = paramAttributes.getGreenSize();
/*  78 */     arrayOfInt[2] = paramAttributes.getBlueSize();
/*  79 */     arrayOfInt[3] = paramAttributes.getAlphaSize();
/*  80 */     arrayOfInt[4] = paramAttributes.getDepthSize();
/*  81 */     arrayOfInt[5] = (paramAttributes.isDoubleBuffer() ? 1 : 0);
/*  82 */     arrayOfInt[6] = (paramAttributes.isOnScreen() ? 1 : 0);
/*     */ 
/*  85 */     this.nativeCtxInfo = nInitialize(arrayOfInt);
/*     */ 
/*  87 */     if (this.nativeCtxInfo == 0L)
/*     */     {
/*  89 */       return false;
/*     */     }
/*  91 */     this.gl2 = true;
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */   public int getAdapterCount()
/*     */   {
/*  98 */     return nGetAdapterCount();
/*     */   }
/*     */ 
/*     */   public int getAdapterOrdinal(long paramLong)
/*     */   {
/* 103 */     return nGetAdapterOrdinal(paramLong);
/*     */   }
/*     */ 
/*     */   public void updateDeviceDetails(HashMap paramHashMap)
/*     */   {
/* 108 */     paramHashMap.put("XVisualID", new Long(nGetVisualID(this.nativeCtxInfo)));
/* 109 */     paramHashMap.put("XDisplay", new Long(nGetDisplay(this.nativeCtxInfo)));
/* 110 */     paramHashMap.put("XScreenID", new Integer(nGetDefaultScreen(this.nativeCtxInfo)));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.gl.x11.X11GLFactory
 * JD-Core Version:    0.6.2
 */