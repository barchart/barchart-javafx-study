/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ProgressIndicatorBuilder<B extends ProgressIndicatorBuilder<B>> extends ControlBuilder<B>
/*    */   implements Builder<ProgressIndicator>
/*    */ {
/*    */   private boolean __set;
/*    */   private double progress;
/*    */ 
/*    */   public static ProgressIndicatorBuilder<?> create()
/*    */   {
/* 15 */     return new ProgressIndicatorBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ProgressIndicator paramProgressIndicator)
/*    */   {
/* 20 */     super.applyTo(paramProgressIndicator);
/* 21 */     if (this.__set) paramProgressIndicator.setProgress(this.progress);
/*    */   }
/*    */ 
/*    */   public B progress(double paramDouble)
/*    */   {
/* 30 */     this.progress = paramDouble;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public ProgressIndicator build()
/*    */   {
/* 39 */     ProgressIndicator localProgressIndicator = new ProgressIndicator();
/* 40 */     applyTo(localProgressIndicator);
/* 41 */     return localProgressIndicator;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ProgressIndicatorBuilder
 * JD-Core Version:    0.6.2
 */