/*    */ package com.sun.prism.es2.gl.x11;
/*    */ 
/*    */ import com.sun.prism.es2.gl.GLContext;
/*    */ import com.sun.prism.es2.gl.GLDrawable;
/*    */ import com.sun.prism.es2.gl.GLPixelFormat;
/*    */ 
/*    */ public class X11GLDrawable extends GLDrawable
/*    */ {
/*    */   private static native long nCreateDrawable(long paramLong1, long paramLong2);
/*    */ 
/*    */   private static native long nGetDummyDrawable(long paramLong);
/*    */ 
/*    */   private static native boolean nSwapBuffers(long paramLong);
/*    */ 
/*    */   X11GLDrawable(GLPixelFormat paramGLPixelFormat)
/*    */   {
/* 23 */     super(0L, paramGLPixelFormat);
/* 24 */     long l = nGetDummyDrawable(paramGLPixelFormat.getNativePFInfo());
/* 25 */     setNativeDrawableInfo(l);
/*    */   }
/*    */ 
/*    */   X11GLDrawable(long paramLong, GLPixelFormat paramGLPixelFormat) {
/* 29 */     super(paramLong, paramGLPixelFormat);
/* 30 */     long l = nCreateDrawable(paramLong, paramGLPixelFormat.getNativePFInfo());
/* 31 */     setNativeDrawableInfo(l);
/*    */   }
/*    */ 
/*    */   public boolean swapBuffers(GLContext paramGLContext)
/*    */   {
/* 36 */     return nSwapBuffers(getNativeDrawableInfo());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.gl.x11.X11GLDrawable
 * JD-Core Version:    0.6.2
 */