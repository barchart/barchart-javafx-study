/*    */ package com.sun.prism.es2.gl.x11;
/*    */ 
/*    */ import com.sun.prism.es2.gl.GLPixelFormat;
/*    */ import com.sun.prism.es2.gl.GLPixelFormat.Attributes;
/*    */ 
/*    */ public class X11GLPixelFormat extends GLPixelFormat
/*    */ {
/*    */   private static native long nCreatePixelFormat(long paramLong, int[] paramArrayOfInt);
/*    */ 
/*    */   X11GLPixelFormat(long paramLong, GLPixelFormat.Attributes paramAttributes)
/*    */   {
/* 18 */     super(paramLong, paramAttributes);
/*    */ 
/* 21 */     int[] arrayOfInt = new int[7];
/*    */ 
/* 23 */     arrayOfInt[0] = paramAttributes.getRedSize();
/* 24 */     arrayOfInt[1] = paramAttributes.getGreenSize();
/* 25 */     arrayOfInt[2] = paramAttributes.getBlueSize();
/* 26 */     arrayOfInt[3] = paramAttributes.getAlphaSize();
/* 27 */     arrayOfInt[4] = paramAttributes.getDepthSize();
/* 28 */     arrayOfInt[5] = (paramAttributes.isDoubleBuffer() ? 1 : 0);
/* 29 */     arrayOfInt[6] = (paramAttributes.isOnScreen() ? 1 : 0);
/* 30 */     long l = nCreatePixelFormat(paramLong, arrayOfInt);
/* 31 */     setNativePFInfo(l);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.gl.x11.X11GLPixelFormat
 * JD-Core Version:    0.6.2
 */