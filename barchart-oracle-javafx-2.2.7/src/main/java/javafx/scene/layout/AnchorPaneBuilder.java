/*    */ package javafx.scene.layout;
/*    */ 
/*    */ public class AnchorPaneBuilder<B extends AnchorPaneBuilder<B>> extends PaneBuilder<B>
/*    */ {
/*    */   public static AnchorPaneBuilder<?> create()
/*    */   {
/* 15 */     return new AnchorPaneBuilder();
/*    */   }
/*    */ 
/*    */   public AnchorPane build()
/*    */   {
/* 22 */     AnchorPane localAnchorPane = new AnchorPane();
/* 23 */     applyTo(localAnchorPane);
/* 24 */     return localAnchorPane;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.AnchorPaneBuilder
 * JD-Core Version:    0.6.2
 */