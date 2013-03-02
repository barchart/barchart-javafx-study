/*    */ package com.sun.deploy.uitoolkit.impl.fx;
/*    */ 
/*    */ import com.sun.deploy.uitoolkit.PluginWindowFactory;
/*    */ import com.sun.deploy.uitoolkit.Window;
/*    */ import java.util.concurrent.Callable;
/*    */ import sun.plugin2.main.client.ModalityInterface;
/*    */ import sun.plugin2.message.Pipe;
/*    */ 
/*    */ public class FXWindowFactory extends PluginWindowFactory
/*    */ {
/*    */   public Window createWindow()
/*    */   {
/* 25 */     return new FXWindow(0L, null);
/*    */   }
/*    */ 
/*    */   public Window createWindow(final long parent, String caRenderServer, boolean bln, ModalityInterface mi)
/*    */   {
/*    */     try
/*    */     {
/* 33 */       return (Window)FXPluginToolkit.callAndWait(new Callable() {
/*    */         public FXWindow call() throws Exception {
/* 35 */           return new FXWindow(parent, this.val$caRenderServer);
/*    */         } } );
/*    */     }
/*    */     catch (Exception e) {
/* 39 */       throw new RuntimeException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Window createWindow(long l, String string, boolean bln, ModalityInterface mi, Pipe pipe, int i)
/*    */   {
/* 45 */     return createWindow(l, string, bln, mi);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.FXWindowFactory
 * JD-Core Version:    0.6.2
 */