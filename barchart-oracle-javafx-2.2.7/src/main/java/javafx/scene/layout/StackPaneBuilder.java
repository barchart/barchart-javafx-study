/*    */ package javafx.scene.layout;
/*    */ 
/*    */ import javafx.geometry.Pos;
/*    */ 
/*    */ public class StackPaneBuilder<B extends StackPaneBuilder<B>> extends PaneBuilder<B>
/*    */ {
/*    */   private boolean __set;
/*    */   private Pos alignment;
/*    */ 
/*    */   public static StackPaneBuilder<?> create()
/*    */   {
/* 15 */     return new StackPaneBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(StackPane paramStackPane)
/*    */   {
/* 20 */     super.applyTo(paramStackPane);
/* 21 */     if (this.__set) paramStackPane.setAlignment(this.alignment);
/*    */   }
/*    */ 
/*    */   public B alignment(Pos paramPos)
/*    */   {
/* 30 */     this.alignment = paramPos;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public StackPane build()
/*    */   {
/* 39 */     StackPane localStackPane = new StackPane();
/* 40 */     applyTo(localStackPane);
/* 41 */     return localStackPane;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.StackPaneBuilder
 * JD-Core Version:    0.6.2
 */