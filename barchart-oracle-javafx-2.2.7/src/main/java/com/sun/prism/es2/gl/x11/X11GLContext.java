/*    */ package com.sun.prism.es2.gl.x11;
/*    */ 
/*    */ import com.sun.prism.es2.gl.GLContext;
/*    */ import com.sun.prism.es2.gl.GLDrawable;
/*    */ import com.sun.prism.es2.gl.GLPixelFormat;
/*    */ import com.sun.prism.es2.gl.GLPixelFormat.Attributes;
/*    */ 
/*    */ public class X11GLContext extends GLContext
/*    */ {
/*    */   private static native long nInitialize(long paramLong1, long paramLong2, boolean paramBoolean);
/*    */ 
/*    */   private static native long nGetNativeHandle(long paramLong);
/*    */ 
/*    */   private static native void nMakeCurrent(long paramLong1, long paramLong2);
/*    */ 
/*    */   public X11GLContext(long paramLong)
/*    */   {
/* 25 */     this.nativeCtxInfo = paramLong;
/*    */   }
/*    */ 
/*    */   public X11GLContext(GLDrawable paramGLDrawable, GLPixelFormat paramGLPixelFormat, boolean paramBoolean)
/*    */   {
/* 32 */     int[] arrayOfInt = new int[7];
/*    */ 
/* 34 */     GLPixelFormat.Attributes localAttributes = paramGLPixelFormat.getAttributes();
/*    */ 
/* 36 */     arrayOfInt[0] = localAttributes.getRedSize();
/* 37 */     arrayOfInt[1] = localAttributes.getGreenSize();
/* 38 */     arrayOfInt[2] = localAttributes.getBlueSize();
/* 39 */     arrayOfInt[3] = localAttributes.getAlphaSize();
/* 40 */     arrayOfInt[4] = localAttributes.getDepthSize();
/* 41 */     arrayOfInt[5] = (localAttributes.isDoubleBuffer() ? 1 : 0);
/* 42 */     arrayOfInt[6] = (localAttributes.isOnScreen() ? 1 : 0);
/*    */ 
/* 45 */     this.nativeCtxInfo = nInitialize(paramGLDrawable.getNativeDrawableInfo(), paramGLPixelFormat.getNativePFInfo(), paramBoolean);
/*    */   }
/*    */ 
/*    */   public long getNativeHandle()
/*    */   {
/* 51 */     return nGetNativeHandle(this.nativeCtxInfo);
/*    */   }
/*    */ 
/*    */   public void makeCurrent(GLDrawable paramGLDrawable)
/*    */   {
/* 56 */     nMakeCurrent(this.nativeCtxInfo, paramGLDrawable.getNativeDrawableInfo());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.gl.x11.X11GLContext
 * JD-Core Version:    0.6.2
 */