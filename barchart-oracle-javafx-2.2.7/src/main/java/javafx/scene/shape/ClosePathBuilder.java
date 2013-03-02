/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ClosePathBuilder<B extends ClosePathBuilder<B>> extends PathElementBuilder<B>
/*    */   implements Builder<ClosePath>
/*    */ {
/*    */   public static ClosePathBuilder<?> create()
/*    */   {
/* 15 */     return new ClosePathBuilder();
/*    */   }
/*    */ 
/*    */   public ClosePath build()
/*    */   {
/* 22 */     ClosePath localClosePath = new ClosePath();
/* 23 */     applyTo(localClosePath);
/* 24 */     return localClosePath;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.ClosePathBuilder
 * JD-Core Version:    0.6.2
 */