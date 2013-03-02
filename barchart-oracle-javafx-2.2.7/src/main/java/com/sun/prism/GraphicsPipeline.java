/*     */ package com.sun.prism;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.javafx.font.FontFactory;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.t2k.T2KFontFactory;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class GraphicsPipeline
/*     */ {
/*     */   private FontFactory fontFactory;
/* 107 */   protected Map deviceDetails = null;
/*     */   private static GraphicsPipeline installedPipeline;
/*     */ 
/*     */   public abstract boolean init();
/*     */ 
/*     */   public void dispose()
/*     */   {
/*  43 */     installedPipeline = null;
/*     */   }
/*     */ 
/*     */   public abstract ResourceFactory getResourceFactory(Screen paramScreen);
/*     */ 
/*     */   public abstract ResourceFactory getDefaultResourceFactory(List<Screen> paramList);
/*     */ 
/*     */   public abstract boolean is3DSupported();
/*     */ 
/*     */   public abstract boolean isVsyncSupported();
/*     */ 
/*     */   public abstract boolean supportsShaderType(ShaderType paramShaderType);
/*     */ 
/*     */   public abstract boolean supportsShaderModel(ShaderModel paramShaderModel);
/*     */ 
/*     */   public boolean supportsShader(ShaderType paramShaderType, ShaderModel paramShaderModel)
/*     */   {
/*  92 */     return (supportsShaderType(paramShaderType)) && (supportsShaderModel(paramShaderModel));
/*     */   }
/*     */ 
/*     */   public static ResourceFactory getDefaultResourceFactory() {
/*  96 */     List localList = Screen.getScreens();
/*  97 */     return getPipeline().getDefaultResourceFactory(localList);
/*     */   }
/*     */ 
/*     */   public FontFactory getFontFactory() {
/* 101 */     if (this.fontFactory == null) {
/* 102 */       this.fontFactory = T2KFontFactory.getFontFactory();
/*     */     }
/* 104 */     return this.fontFactory;
/*     */   }
/*     */ 
/*     */   public Map getDeviceDetails()
/*     */   {
/* 113 */     return this.deviceDetails;
/*     */   }
/*     */ 
/*     */   protected void setDeviceDetails(Map paramMap)
/*     */   {
/* 121 */     this.deviceDetails = paramMap;
/*     */   }
/*     */ 
/*     */   public static GraphicsPipeline createPipeline()
/*     */   {
/* 127 */     if (PrismSettings.tryOrder.length == 0)
/*     */     {
/* 129 */       if (PrismSettings.verbose) {
/* 130 */         System.out.println("No Prism pipelines specified");
/*     */       }
/* 132 */       return null;
/*     */     }
/*     */ 
/* 135 */     if (installedPipeline != null) {
/* 136 */       throw new IllegalStateException("pipeline already created:" + installedPipeline);
/*     */     }
/*     */ 
/* 139 */     for (Object localObject2 : PrismSettings.tryOrder) {
/* 140 */       if ((PrismSettings.verbose) && ("j2d".equals(localObject2))) {
/* 141 */         System.err.println("*** Fallback to Prism SW pipeline");
/*     */       }
/*     */ 
/* 144 */       String str = "com.sun.prism." + localObject2 + "." + localObject2.toUpperCase() + "Pipeline";
/*     */       try
/*     */       {
/* 147 */         if (PrismSettings.verbose) {
/* 148 */           System.out.println("Prism pipeline name = " + str);
/*     */         }
/* 150 */         Class localClass = Class.forName(str);
/* 151 */         if (PrismSettings.verbose) {
/* 152 */           System.out.println("(X) Got class = " + localClass);
/*     */         }
/* 154 */         Method localMethod = localClass.getMethod("getInstance", (Class[])null);
/* 155 */         GraphicsPipeline localGraphicsPipeline = (GraphicsPipeline)localMethod.invoke(null, (Object[])null);
/*     */ 
/* 157 */         if ((localGraphicsPipeline != null) && (localGraphicsPipeline.init())) {
/* 158 */           if (PrismSettings.verbose) {
/* 159 */             System.out.println("Initialized prism pipeline: " + localClass.getName());
/*     */           }
/*     */ 
/* 162 */           installedPipeline = localGraphicsPipeline;
/* 163 */           return installedPipeline;
/*     */         }
/* 165 */         if (localGraphicsPipeline != null) {
/* 166 */           localGraphicsPipeline.dispose();
/* 167 */           localGraphicsPipeline = null;
/*     */         }
/* 169 */         if (PrismSettings.verbose)
/* 170 */           System.err.println("GraphicsPipeline.createPipeline: error initializing pipeline " + str);
/*     */       }
/*     */       catch (Throwable localThrowable)
/*     */       {
/* 174 */         if (PrismSettings.verbose) {
/* 175 */           System.err.println("GraphicsPipeline.createPipeline failed for " + str);
/*     */ 
/* 177 */           localThrowable.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 181 */     ??? = new StringBuffer("Graphics Device initialization failed for :  ");
/* 182 */     for (??? = 0; ??? < PrismSettings.tryOrder.length; ???++) {
/* 183 */       ((StringBuffer)???).append(PrismSettings.tryOrder[???]);
/* 184 */       if (??? < PrismSettings.tryOrder.length - 1) {
/* 185 */         ((StringBuffer)???).append(", ");
/*     */       }
/*     */     }
/*     */ 
/* 189 */     System.err.println(???);
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */   public static GraphicsPipeline getPipeline() {
/* 194 */     return installedPipeline;
/*     */   }
/*     */ 
/*     */   public boolean isEffectSupported() {
/* 198 */     return true;
/*     */   }
/*     */ 
/*     */   public static enum ShaderModel
/*     */   {
/*  37 */     SM3;
/*     */   }
/*     */ 
/*     */   public static enum ShaderType
/*     */   {
/*  25 */     HLSL, 
/*     */ 
/*  29 */     GLSL;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.GraphicsPipeline
 * JD-Core Version:    0.6.2
 */