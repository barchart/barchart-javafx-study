/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class HyperlinkBuilder<B extends HyperlinkBuilder<B>> extends ButtonBaseBuilder<B>
/*    */   implements Builder<Hyperlink>
/*    */ {
/*    */   private boolean __set;
/*    */   private boolean visited;
/*    */ 
/*    */   public static HyperlinkBuilder<?> create()
/*    */   {
/* 15 */     return new HyperlinkBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Hyperlink paramHyperlink)
/*    */   {
/* 20 */     super.applyTo(paramHyperlink);
/* 21 */     if (this.__set) paramHyperlink.setVisited(this.visited);
/*    */   }
/*    */ 
/*    */   public B visited(boolean paramBoolean)
/*    */   {
/* 30 */     this.visited = paramBoolean;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public Hyperlink build()
/*    */   {
/* 39 */     Hyperlink localHyperlink = new Hyperlink();
/* 40 */     applyTo(localHyperlink);
/* 41 */     return localHyperlink;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.HyperlinkBuilder
 * JD-Core Version:    0.6.2
 */