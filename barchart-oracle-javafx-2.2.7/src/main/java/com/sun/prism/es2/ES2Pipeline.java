/*     */ package com.sun.prism.es2;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.runtime.NativeLibLoader;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.GraphicsPipeline.ShaderModel;
/*     */ import com.sun.prism.GraphicsPipeline.ShaderType;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.es2.gl.GLFactory;
/*     */ import com.sun.prism.es2.gl.GLPixelFormat.Attributes;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ES2Pipeline extends GraphicsPipeline
/*     */ {
/*  30 */   public static final boolean isEmbededDevice = PrismSettings.isEmbededDevice;
/*     */   public static final GLFactory glFactory;
/*  33 */   public static final GLPixelFormat.Attributes pixelFormatAttributes = new GLPixelFormat.Attributes();
/*     */   private static boolean es2Enabled;
/*     */   private static Thread creator;
/*     */   private static final ES2Pipeline theInstance;
/*     */   private static ES2ResourceFactory[] factories;
/*     */   ES2ResourceFactory _default;
/*     */ 
/*     */   public static ES2Pipeline getInstance()
/*     */   {
/*  87 */     return theInstance;
/*     */   }
/*     */ 
/*     */   public boolean init()
/*     */   {
/*  92 */     if (es2Enabled) {
/*  93 */       HashMap localHashMap = new HashMap();
/*  94 */       glFactory.updateDeviceDetails(localHashMap);
/*  95 */       setDeviceDetails(localHashMap);
/*  96 */       if (!PrismSettings.forceGPU) {
/*  97 */         es2Enabled = glFactory.isGLGPUQualify();
/*  98 */         if ((PrismSettings.verbose) && 
/*  99 */           (!es2Enabled)) {
/* 100 */           System.err.println("Failed Graphics Hardware Qualifier check.\nSystem GPU doesn't meet the es2 pipe requirement");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/* 105 */     else if (PrismSettings.verbose) {
/* 106 */       System.err.println("Failed to initialize ES2 backend: ");
/*     */     }
/* 108 */     return es2Enabled;
/*     */   }
/*     */ 
/*     */   private static ES2ResourceFactory getES2ResourceFactory(int paramInt, Screen paramScreen)
/*     */   {
/* 113 */     ES2ResourceFactory localES2ResourceFactory = factories[paramInt];
/* 114 */     if ((localES2ResourceFactory == null) && (paramScreen != null)) {
/* 115 */       localES2ResourceFactory = new ES2ResourceFactory(paramScreen);
/* 116 */       factories[paramInt] = localES2ResourceFactory;
/*     */     }
/* 118 */     return localES2ResourceFactory;
/*     */   }
/*     */ 
/*     */   private static Screen getScreenForAdapter(List<Screen> paramList, int paramInt)
/*     */   {
/* 125 */     for (Screen localScreen : paramList) {
/* 126 */       if (glFactory.getAdapterOrdinal(localScreen.getNativeScreen()) == paramInt) {
/* 127 */         return localScreen;
/*     */       }
/*     */     }
/* 130 */     return Screen.getMainScreen();
/*     */   }
/*     */ 
/*     */   private static ES2ResourceFactory findDefaultResourceFactory(List<Screen> paramList) {
/* 134 */     int i = 0; for (int j = glFactory.getAdapterCount(); i != j; i++) {
/* 135 */       ES2ResourceFactory localES2ResourceFactory = getES2ResourceFactory(i, getScreenForAdapter(paramList, i));
/*     */ 
/* 138 */       if (localES2ResourceFactory != null) {
/* 139 */         if (PrismSettings.verbose) {
/* 140 */           glFactory.printDriverInformation(i);
/*     */         }
/* 142 */         return localES2ResourceFactory;
/*     */       }
/* 144 */       if (!PrismSettings.disableBadDriverWarning) {
/* 145 */         System.err.println("disableBadDriverWarning is unsupported on prism-es2");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   public ResourceFactory getDefaultResourceFactory(List<Screen> paramList)
/*     */   {
/* 156 */     if (this._default == null) {
/* 157 */       this._default = findDefaultResourceFactory(paramList);
/*     */     }
/* 159 */     return this._default;
/*     */   }
/*     */ 
/*     */   public ResourceFactory getResourceFactory(Screen paramScreen)
/*     */   {
/* 164 */     return getES2ResourceFactory(glFactory.getAdapterOrdinal(paramScreen.getNativeScreen()), paramScreen);
/*     */   }
/*     */ 
/*     */   public boolean is3DSupported()
/*     */   {
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isVsyncSupported()
/*     */   {
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsShaderType(GraphicsPipeline.ShaderType paramShaderType)
/*     */   {
/* 180 */     switch (paramShaderType) {
/*     */     case GLSL:
/* 182 */       return true;
/*     */     }
/* 184 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean supportsShaderModel(GraphicsPipeline.ShaderModel paramShaderModel)
/*     */   {
/* 190 */     switch (paramShaderModel) {
/*     */     case SM3:
/* 192 */       return true;
/*     */     }
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  37 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  40 */         String str1 = "prism-es2";
/*     */ 
/*  42 */         String str2 = PlatformUtil.getEmbeddedType();
/*  43 */         if ("eglfb".equals(str2))
/*  44 */           str1 = "prism-es2-eglfb";
/*  45 */         else if ("eglx11".equals(str2)) {
/*  46 */           str1 = "prism-es2-eglx11";
/*     */         }
/*  48 */         if (PrismSettings.verbose) {
/*  49 */           System.out.println("Loading ES2 native library ... " + str1);
/*     */         }
/*  51 */         NativeLibLoader.loadLibrary(str1);
/*  52 */         if (PrismSettings.verbose) {
/*  53 */           System.out.println("\tsucceeded.");
/*     */         }
/*  55 */         return null;
/*     */       }
/*     */     });
/*  60 */     glFactory = GLFactory.getFactory();
/*     */ 
/*  62 */     creator = Thread.currentThread();
/*     */ 
/*  64 */     if (glFactory != null) {
/*  65 */       es2Enabled = glFactory.initialize(PrismSettings.class, pixelFormatAttributes);
/*     */     }
/*     */     else {
/*  68 */       es2Enabled = false;
/*     */     }
/*     */ 
/*  71 */     if (es2Enabled) {
/*  72 */       theInstance = new ES2Pipeline();
/*  73 */       factories = new ES2ResourceFactory[glFactory.getAdapterCount()];
/*     */     } else {
/*  75 */       theInstance = null;
/*     */     }
/*     */ 
/*  78 */     if (isEmbededDevice)
/*  79 */       System.out.println("ES2Pipeline: OpenGL ES 2.0 embedded device detected");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.ES2Pipeline
 * JD-Core Version:    0.6.2
 */