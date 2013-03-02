/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class SeparatorMenuItemBuilder<B extends SeparatorMenuItemBuilder<B>> extends CustomMenuItemBuilder<B>
/*    */ {
/*    */   public static SeparatorMenuItemBuilder<?> create()
/*    */   {
/* 15 */     return new SeparatorMenuItemBuilder();
/*    */   }
/*    */ 
/*    */   public SeparatorMenuItem build()
/*    */   {
/* 22 */     SeparatorMenuItem localSeparatorMenuItem = new SeparatorMenuItem();
/* 23 */     applyTo(localSeparatorMenuItem);
/* 24 */     return localSeparatorMenuItem;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.SeparatorMenuItemBuilder
 * JD-Core Version:    0.6.2
 */