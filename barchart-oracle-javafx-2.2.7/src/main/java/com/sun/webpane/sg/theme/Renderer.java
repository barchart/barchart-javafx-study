/*    */ package com.sun.webpane.sg.theme;
/*    */ 
/*    */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*    */ import javafx.scene.control.Control;
/*    */ 
/*    */ public abstract class Renderer
/*    */ {
/*    */   private static Renderer instance;
/*    */ 
/*    */   public static void setRenderer(Renderer paramRenderer)
/*    */   {
/* 15 */     instance = paramRenderer;
/*    */   }
/*    */ 
/*    */   public static Renderer getRenderer() {
/* 19 */     return instance;
/*    */   }
/*    */ 
/*    */   public abstract void render(Control paramControl, WCGraphicsContext paramWCGraphicsContext);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.theme.Renderer
 * JD-Core Version:    0.6.2
 */