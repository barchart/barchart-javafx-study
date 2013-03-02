/*     */ package com.sun.scenario.effect.impl;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ class RendererFactory
/*     */ {
/*  43 */   private static String rootPkg = "com.sun.scenario.effect";
/*  44 */   private static boolean tryRSL = true;
/*  45 */   private static boolean trySIMD = true;
/*     */ 
/*  47 */   private static boolean tryJOGL = PlatformUtil.isMac();
/*  48 */   private static boolean tryPrism = true;
/*     */ 
/*     */   private static boolean isRSLFriendly(Class paramClass)
/*     */   {
/*  73 */     if (paramClass.getName().equals("sun.java2d.pipe.hw.AccelGraphicsConfig")) {
/*  74 */       return true;
/*     */     }
/*  76 */     boolean bool = false;
/*  77 */     for (Class localClass : paramClass.getInterfaces()) {
/*  78 */       if (isRSLFriendly(localClass)) {
/*  79 */         bool = true;
/*  80 */         break;
/*     */       }
/*     */     }
/*  83 */     return bool;
/*     */   }
/*     */ 
/*     */   private static boolean isRSLAvailable(FilterContext paramFilterContext) {
/*  87 */     return isRSLFriendly(paramFilterContext.getReferent().getClass());
/*     */   }
/*     */ 
/*     */   private static Renderer createRSLRenderer(FilterContext paramFilterContext) {
/*     */     try {
/*  92 */       Class localClass = Class.forName(rootPkg + ".impl.j2d.rsl.RSLRenderer");
/*  93 */       Method localMethod = localClass.getMethod("createRenderer", new Class[] { FilterContext.class });
/*     */ 
/*  95 */       return (Renderer)localMethod.invoke(null, new Object[] { paramFilterContext });
/*     */     } catch (Throwable localThrowable) {
/*     */     }
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */   private static Renderer createJOGLRenderer(FilterContext paramFilterContext) {
/* 102 */     if (tryJOGL)
/*     */       try {
/* 104 */         Class localClass = Class.forName(rootPkg + ".impl.j2d.jogl.JOGLRenderer");
/* 105 */         Method localMethod = localClass.getMethod("createRenderer", new Class[] { FilterContext.class });
/*     */ 
/* 107 */         return (Renderer)localMethod.invoke(null, new Object[] { paramFilterContext });
/*     */       }
/*     */       catch (Throwable localThrowable) {
/*     */       }
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */   private static Renderer createPrismRenderer(FilterContext paramFilterContext) {
/* 115 */     if (tryPrism) {
/*     */       try {
/* 117 */         Class localClass = Class.forName(rootPkg + ".impl.prism.PrRenderer");
/* 118 */         Method localMethod = localClass.getMethod("createRenderer", new Class[] { FilterContext.class });
/*     */ 
/* 120 */         return (Renderer)localMethod.invoke(null, new Object[] { paramFilterContext });
/*     */       } catch (Throwable localThrowable) {
/* 122 */         localThrowable.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */   private static Renderer getSSERenderer() {
/* 130 */     if (trySIMD) {
/*     */       try {
/* 132 */         Class localClass = Class.forName(rootPkg + ".impl.j2d.J2DSWRenderer");
/* 133 */         Method localMethod = localClass.getMethod("getSSEInstance", (Class[])null);
/* 134 */         Renderer localRenderer = (Renderer)localMethod.invoke(null, (Object[])null);
/* 135 */         if (localRenderer != null)
/* 136 */           return localRenderer;
/*     */       }
/*     */       catch (Throwable localThrowable) {
/*     */       }
/* 140 */       trySIMD = false;
/*     */     }
/* 142 */     return null;
/*     */   }
/*     */ 
/*     */   private static Renderer getJavaRenderer() {
/*     */     try {
/* 147 */       Class localClass = Class.forName(rootPkg + ".impl.j2d.J2DSWRenderer");
/* 148 */       Method localMethod = localClass.getMethod("getJSWInstance", (Class[])null);
/* 149 */       Renderer localRenderer = (Renderer)localMethod.invoke(null, (Object[])null);
/* 150 */       if (localRenderer != null)
/* 151 */         return localRenderer;
/*     */     } catch (Throwable localThrowable) {
/*     */     }
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */   static Renderer getSoftwareRenderer() {
/* 158 */     Renderer localRenderer = getSSERenderer();
/* 159 */     if (localRenderer == null) {
/* 160 */       localRenderer = getJavaRenderer();
/*     */     }
/* 162 */     return localRenderer;
/*     */   }
/*     */ 
/*     */   static Renderer createRenderer(FilterContext paramFilterContext) {
/* 166 */     return (Renderer)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Renderer run() {
/* 168 */         Renderer localRenderer = null;
/*     */ 
/* 170 */         String str1 = this.val$fctx.getClass().getName();
/* 171 */         String str2 = str1.substring(str1.lastIndexOf(".") + 1);
/*     */ 
/* 173 */         if ((str2.equals("PrFilterContext")) && (RendererFactory.tryPrism)) {
/* 174 */           localRenderer = RendererFactory.createPrismRenderer(this.val$fctx);
/*     */         }
/*     */ 
/* 179 */         if ((localRenderer == null) && (RendererFactory.tryRSL) && (RendererFactory.isRSLAvailable(this.val$fctx)))
/*     */         {
/* 184 */           localRenderer = RendererFactory.createRSLRenderer(this.val$fctx);
/*     */         }
/* 186 */         if ((localRenderer == null) && (RendererFactory.tryJOGL))
/*     */         {
/* 188 */           localRenderer = RendererFactory.createJOGLRenderer(this.val$fctx);
/*     */         }
/* 190 */         if ((localRenderer == null) && (RendererFactory.trySIMD))
/*     */         {
/* 192 */           localRenderer = RendererFactory.access$800();
/*     */         }
/* 194 */         if (localRenderer == null)
/*     */         {
/* 196 */           localRenderer = RendererFactory.access$900();
/*     */         }
/* 198 */         return localRenderer;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  52 */       if ("false".equals(System.getProperty("decora.rsl"))) {
/*  53 */         tryRSL = false;
/*     */       }
/*  55 */       if ("false".equals(System.getProperty("decora.simd"))) {
/*  56 */         trySIMD = false;
/*     */       }
/*  58 */       String str = System.getProperty("decora.jogl");
/*  59 */       if (str != null) {
/*  60 */         tryJOGL = Boolean.parseBoolean(str);
/*     */       }
/*  62 */       if ("false".equals(System.getProperty("decora.prism")))
/*  63 */         tryPrism = false;
/*     */     }
/*     */     catch (SecurityException localSecurityException)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.RendererFactory
 * JD-Core Version:    0.6.2
 */