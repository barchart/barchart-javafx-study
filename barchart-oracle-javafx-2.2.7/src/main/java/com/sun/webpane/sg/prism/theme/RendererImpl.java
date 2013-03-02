/*    */ package com.sun.webpane.sg.prism.theme;
/*    */ 
/*    */ import com.sun.javafx.sg.prism.NGNode;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*    */ import com.sun.webpane.sg.theme.Renderer;
/*    */ import javafx.scene.Scene;
/*    */ import javafx.scene.control.Control;
/*    */ 
/*    */ public class RendererImpl extends Renderer
/*    */ {
/*    */   public void render(Control paramControl, WCGraphicsContext paramWCGraphicsContext)
/*    */   {
/* 19 */     Scene.impl_setAllowPGAccess(true);
/*    */ 
/* 21 */     NGNode localNGNode = (NGNode)paramControl.impl_getPGNode();
/* 22 */     Scene.impl_setAllowPGAccess(false);
/*    */ 
/* 24 */     localNGNode.render((Graphics)paramWCGraphicsContext.getPlatformGraphics());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.theme.RendererImpl
 * JD-Core Version:    0.6.2
 */