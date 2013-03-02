/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class SplitMenuButtonBuilder<B extends SplitMenuButtonBuilder<B>> extends MenuButtonBuilder<B>
/*    */ {
/*    */   public static SplitMenuButtonBuilder<?> create()
/*    */   {
/* 15 */     return new SplitMenuButtonBuilder();
/*    */   }
/*    */ 
/*    */   public SplitMenuButton build()
/*    */   {
/* 22 */     SplitMenuButton localSplitMenuButton = new SplitMenuButton();
/* 23 */     applyTo(localSplitMenuButton);
/* 24 */     return localSplitMenuButton;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.SplitMenuButtonBuilder
 * JD-Core Version:    0.6.2
 */