/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ import com.sun.prism.render.CompletionListener;
/*    */ import com.sun.prism.render.RenderJob;
/*    */ 
/*    */ public class PaintRenderJob extends RenderJob
/*    */ {
/*    */   private GlassScene scene;
/*    */ 
/*    */   public PaintRenderJob(GlassScene paramGlassScene, CompletionListener paramCompletionListener, Runnable paramRunnable)
/*    */   {
/* 15 */     super(paramRunnable, paramCompletionListener);
/*    */ 
/* 17 */     this.scene = paramGlassScene;
/*    */   }
/*    */ 
/*    */   public GlassScene getScene() {
/* 21 */     return this.scene;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.PaintRenderJob
 * JD-Core Version:    0.6.2
 */