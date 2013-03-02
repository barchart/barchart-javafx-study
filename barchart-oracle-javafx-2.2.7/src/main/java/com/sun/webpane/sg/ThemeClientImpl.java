/*    */ package com.sun.webpane.sg;
/*    */ 
/*    */ import com.sun.webpane.platform.ThemeClient;
/*    */ import com.sun.webpane.platform.graphics.RenderTheme;
/*    */ import com.sun.webpane.platform.graphics.ScrollBarTheme;
/*    */ import com.sun.webpane.sg.theme.RenderThemeImpl;
/*    */ import com.sun.webpane.sg.theme.ScrollBarThemeImpl;
/*    */ 
/*    */ public class ThemeClientImpl extends ThemeClient
/*    */ {
/*    */   private Accessor accessor;
/*    */ 
/*    */   public ThemeClientImpl(Accessor paramAccessor)
/*    */   {
/* 16 */     this.accessor = paramAccessor;
/*    */   }
/*    */ 
/*    */   public RenderTheme createRenderTheme() {
/* 20 */     return new RenderThemeImpl(this.accessor);
/*    */   }
/*    */ 
/*    */   public ScrollBarTheme createScrollBarTheme() {
/* 24 */     return new ScrollBarThemeImpl(this.accessor);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.ThemeClientImpl
 * JD-Core Version:    0.6.2
 */