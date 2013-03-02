/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class ProgressBarBuilder<B extends ProgressBarBuilder<B>> extends ProgressIndicatorBuilder<B>
/*    */ {
/*    */   public static ProgressBarBuilder<?> create()
/*    */   {
/* 15 */     return new ProgressBarBuilder();
/*    */   }
/*    */ 
/*    */   public ProgressBar build()
/*    */   {
/* 22 */     ProgressBar localProgressBar = new ProgressBar();
/* 23 */     applyTo(localProgressBar);
/* 24 */     return localProgressBar;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ProgressBarBuilder
 * JD-Core Version:    0.6.2
 */