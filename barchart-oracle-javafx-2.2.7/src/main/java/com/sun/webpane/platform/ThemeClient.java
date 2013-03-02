/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ import com.sun.webpane.platform.graphics.RenderTheme;
/*    */ import com.sun.webpane.platform.graphics.ScrollBarTheme;
/*    */ 
/*    */ public abstract class ThemeClient
/*    */ {
/*    */   private static RenderTheme defaultRenderTheme;
/*    */ 
/*    */   public static void setDefaultRenderTheme(RenderTheme paramRenderTheme)
/*    */   {
/* 14 */     defaultRenderTheme = paramRenderTheme;
/*    */   }
/*    */ 
/*    */   public static RenderTheme getDefaultRenderTheme() {
/* 18 */     return defaultRenderTheme;
/*    */   }
/*    */ 
/*    */   public abstract RenderTheme createRenderTheme();
/*    */ 
/*    */   public abstract ScrollBarTheme createScrollBarTheme();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.ThemeClient
 * JD-Core Version:    0.6.2
 */