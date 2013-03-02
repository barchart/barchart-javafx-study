/*    */ package com.sun.prism.es2.gl;
/*    */ 
/*    */ public abstract class GLDrawable
/*    */ {
/*    */   private final long nativeWindow;
/*    */   private final GLPixelFormat pixelFormat;
/*    */   protected long nativeDrawableInfo;
/*    */ 
/*    */   protected GLDrawable(long paramLong, GLPixelFormat paramGLPixelFormat)
/*    */   {
/* 18 */     this.nativeWindow = paramLong;
/* 19 */     this.pixelFormat = paramGLPixelFormat;
/*    */   }
/*    */ 
/*    */   public GLPixelFormat getPixelFormat() {
/* 23 */     return this.pixelFormat;
/*    */   }
/*    */ 
/*    */   public long getNativeWindow() {
/* 27 */     return this.nativeWindow;
/*    */   }
/*    */ 
/*    */   protected void setNativeDrawableInfo(long paramLong) {
/* 31 */     this.nativeDrawableInfo = paramLong;
/*    */   }
/*    */ 
/*    */   public long getNativeDrawableInfo() {
/* 35 */     return this.nativeDrawableInfo;
/*    */   }
/*    */ 
/*    */   public abstract boolean swapBuffers(GLContext paramGLContext);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.gl.GLDrawable
 * JD-Core Version:    0.6.2
 */