/*    */ package com.sun.prism.j2d;
/*    */ 
/*    */ import com.sun.glass.ui.Screen;
/*    */ import com.sun.javafx.font.FontFactory;
/*    */ import com.sun.prism.GraphicsPipeline;
/*    */ import com.sun.prism.GraphicsPipeline.ShaderModel;
/*    */ import com.sun.prism.GraphicsPipeline.ShaderType;
/*    */ import com.sun.prism.ResourceFactory;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ 
/*    */ public class J2DPipeline extends GraphicsPipeline
/*    */ {
/*    */   private static J2DPipeline theInstance;
/* 37 */   private final HashMap<Integer, J2DResourceFactory> factories = new HashMap(1);
/*    */   private FontFactory j2DFontFactory;
/*    */ 
/*    */   public boolean init()
/*    */   {
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   public static J2DPipeline getInstance()
/*    */   {
/* 31 */     if (theInstance == null) {
/* 32 */       theInstance = new J2DPipeline();
/*    */     }
/* 34 */     return theInstance;
/*    */   }
/*    */ 
/*    */   public ResourceFactory getResourceFactory(Screen paramScreen)
/*    */   {
/* 42 */     List localList = Screen.getScreens();
/* 43 */     Integer localInteger = new Integer(localList.indexOf(paramScreen));
/* 44 */     J2DResourceFactory localJ2DResourceFactory = (J2DResourceFactory)this.factories.get(localInteger);
/* 45 */     if (localJ2DResourceFactory == null) {
/* 46 */       localJ2DResourceFactory = new J2DResourceFactory(paramScreen);
/* 47 */       this.factories.put(localInteger, localJ2DResourceFactory);
/*    */     }
/* 49 */     return localJ2DResourceFactory;
/*    */   }
/*    */ 
/*    */   public ResourceFactory getDefaultResourceFactory(List<Screen> paramList)
/*    */   {
/* 54 */     return getResourceFactory(Screen.getMainScreen());
/*    */   }
/*    */ 
/*    */   public boolean is3DSupported()
/*    */   {
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean isVsyncSupported()
/*    */   {
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean supportsShaderType(GraphicsPipeline.ShaderType paramShaderType)
/*    */   {
/* 69 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean supportsShaderModel(GraphicsPipeline.ShaderModel paramShaderModel)
/*    */   {
/* 74 */     return false;
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/* 80 */     super.dispose();
/*    */   }
/*    */ 
/*    */   public FontFactory getFontFactory()
/*    */   {
/* 91 */     if (this.j2DFontFactory == null) {
/* 92 */       FontFactory localFontFactory = super.getFontFactory();
/* 93 */       this.j2DFontFactory = new J2DFontFactory(localFontFactory);
/*    */     }
/* 95 */     return this.j2DFontFactory;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.j2d.J2DPipeline
 * JD-Core Version:    0.6.2
 */