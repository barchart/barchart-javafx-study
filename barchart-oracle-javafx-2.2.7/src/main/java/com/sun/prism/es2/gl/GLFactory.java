/*     */ package com.sun.prism.es2.gl;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public abstract class GLFactory
/*     */ {
/*  64 */   private static final GLFactory platformFactory = (GLFactory)AccessController.doPrivileged(new PrivilegedAction()
/*     */   {
/*     */     public GLFactory run() {
/*  67 */       GLFactory localGLFactory = null;
/*     */       try {
/*  69 */         if (null != this.val$factoryClassName)
/*  70 */           localGLFactory = (GLFactory)Class.forName(this.val$factoryClassName).newInstance();
/*     */       }
/*     */       catch (Throwable localThrowable) {
/*  73 */         System.err.println("GLFactory.static - Platform: " + System.getProperty("os.name") + " - not available: " + this.val$factoryClassName);
/*     */ 
/*  77 */         localThrowable.printStackTrace();
/*     */       }
/*  79 */       return localGLFactory;
/*     */     }
/*     */   });
/*     */   protected long nativeCtxInfo;
/*  30 */   protected boolean gl2 = false;
/*  31 */   private GLContext shareCtx = null;
/*     */ 
/*     */   private static native boolean nIsGLExtensionSupported(long paramLong, String paramString);
/*     */ 
/*     */   private static native String nGetGLVendor(long paramLong);
/*     */ 
/*     */   private static native String nGetGLRenderer(long paramLong);
/*     */ 
/*     */   private static native String nGetGLVersion(long paramLong);
/*     */ 
/*     */   public static GLFactory getFactory()
/*     */     throws RuntimeException
/*     */   {
/*  88 */     if (null != platformFactory) {
/*  89 */       return platformFactory;
/*     */     }
/*  91 */     throw new RuntimeException("No native platform GLFactory available.");
/*     */   }
/*     */ 
/*     */   protected abstract GLGPUInfo[] getPreQualificationFilter();
/*     */ 
/*     */   protected abstract GLGPUInfo[] getBlackList();
/*     */ 
/*     */   private static GLGPUInfo readGPUInfo(long paramLong)
/*     */   {
/* 102 */     String str1 = nGetGLVendor(paramLong);
/* 103 */     String str2 = nGetGLRenderer(paramLong);
/* 104 */     return new GLGPUInfo(str1.toLowerCase(), str2.toLowerCase());
/*     */   }
/*     */ 
/*     */   private static boolean matches(GLGPUInfo paramGLGPUInfo, GLGPUInfo[] paramArrayOfGLGPUInfo)
/*     */   {
/* 109 */     if (paramArrayOfGLGPUInfo != null) {
/* 110 */       for (int i = 0; i < paramArrayOfGLGPUInfo.length; i++) {
/* 111 */         if (paramGLGPUInfo.matches(paramArrayOfGLGPUInfo[i])) {
/* 112 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean inPreQualificationFilter(GLGPUInfo paramGLGPUInfo) {
/* 120 */     GLGPUInfo[] arrayOfGLGPUInfo = getPreQualificationFilter();
/* 121 */     if (arrayOfGLGPUInfo == null)
/*     */     {
/* 123 */       return true;
/*     */     }
/* 125 */     return matches(paramGLGPUInfo, arrayOfGLGPUInfo);
/*     */   }
/*     */ 
/*     */   private boolean inBlackList(GLGPUInfo paramGLGPUInfo) {
/* 129 */     return matches(paramGLGPUInfo, getBlackList());
/*     */   }
/*     */ 
/*     */   public boolean isQualified(long paramLong)
/*     */   {
/* 136 */     GLGPUInfo localGLGPUInfo = readGPUInfo(paramLong);
/*     */ 
/* 138 */     if ((localGLGPUInfo.vendor == null) || (localGLGPUInfo.model == null) || (localGLGPUInfo.vendor.contains("unknown")) || (localGLGPUInfo.model.contains("unknown")))
/*     */     {
/* 143 */       return false;
/*     */     }
/*     */ 
/* 146 */     return (inPreQualificationFilter(localGLGPUInfo)) && (!inBlackList(localGLGPUInfo));
/*     */   }
/*     */ 
/*     */   public abstract GLContext createGLContext(long paramLong);
/*     */ 
/*     */   public abstract GLContext createGLContext(GLDrawable paramGLDrawable, GLPixelFormat paramGLPixelFormat, GLContext paramGLContext, boolean paramBoolean);
/*     */ 
/*     */   public abstract GLDrawable createGLDrawable(long paramLong, GLPixelFormat paramGLPixelFormat);
/*     */ 
/*     */   public abstract GLDrawable createDummyGLDrawable(GLPixelFormat paramGLPixelFormat);
/*     */ 
/*     */   public abstract GLPixelFormat createGLPixelFormat(long paramLong, GLPixelFormat.Attributes paramAttributes);
/*     */ 
/*     */   public boolean isGLGPUQualify()
/*     */   {
/* 161 */     return isQualified(this.nativeCtxInfo);
/*     */   }
/*     */ 
/*     */   public abstract boolean initialize(Class paramClass, GLPixelFormat.Attributes paramAttributes);
/*     */ 
/*     */   public GLContext getShareContext() {
/* 167 */     if (this.shareCtx == null) {
/* 168 */       this.shareCtx = createGLContext(this.nativeCtxInfo);
/*     */     }
/* 170 */     return this.shareCtx;
/*     */   }
/*     */ 
/*     */   public boolean isGL2()
/*     */   {
/* 175 */     return this.gl2;
/*     */   }
/*     */ 
/*     */   public boolean isGLExtensionSupported(String paramString) {
/* 179 */     return nIsGLExtensionSupported(this.nativeCtxInfo, paramString);
/*     */   }
/*     */ 
/*     */   public abstract int getAdapterCount();
/*     */ 
/*     */   public abstract int getAdapterOrdinal(long paramLong);
/*     */ 
/*     */   public abstract void updateDeviceDetails(HashMap paramHashMap);
/*     */ 
/*     */   public void updateDeviceDetails(HashMap paramHashMap, GLContext paramGLContext)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void printDriverInformation(int paramInt)
/*     */   {
/* 198 */     System.out.println("Graphics Vendor: " + nGetGLVendor(this.nativeCtxInfo));
/* 199 */     System.out.println("       Renderer: " + nGetGLRenderer(this.nativeCtxInfo));
/* 200 */     System.out.println("        Version: " + nGetGLVersion(this.nativeCtxInfo));
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     String str;
/*  46 */     if (PlatformUtil.isUnix()) {
/*  47 */       if ("eglx11".equals(PlatformUtil.getEmbeddedType()))
/*  48 */         str = "com.sun.prism.es2.gl.eglx11.EGLX11GLFactory";
/*  49 */       else if ("eglfb".equals(PlatformUtil.getEmbeddedType()))
/*  50 */         str = "com.sun.prism.es2.gl.eglfb.EGLFBGLFactory";
/*     */       else
/*  52 */         str = "com.sun.prism.es2.gl.x11.X11GLFactory";
/*  53 */     } else if (PlatformUtil.isWindows()) {
/*  54 */       str = "com.sun.prism.es2.gl.win.WinGLFactory";
/*  55 */     } else if (PlatformUtil.isMac()) {
/*  56 */       str = "com.sun.prism.es2.gl.mac.MacGLFactory";
/*     */     } else {
/*  58 */       str = null;
/*  59 */       System.err.println("GLFactory.static - No Platform Factory for: " + System.getProperty("os.name"));
/*     */     }
/*  61 */     if (PrismSettings.verbose)
/*  62 */       System.out.println("GLFactory using " + str);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.gl.GLFactory
 * JD-Core Version:    0.6.2
 */