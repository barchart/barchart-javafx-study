/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class CheckMenuItemBuilder<B extends CheckMenuItemBuilder<B>> extends MenuItemBuilder<B>
/*    */ {
/*    */   private boolean __set;
/*    */   private boolean selected;
/*    */ 
/*    */   public static CheckMenuItemBuilder<?> create()
/*    */   {
/* 15 */     return new CheckMenuItemBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(CheckMenuItem paramCheckMenuItem)
/*    */   {
/* 20 */     super.applyTo(paramCheckMenuItem);
/* 21 */     if (this.__set) paramCheckMenuItem.setSelected(this.selected);
/*    */   }
/*    */ 
/*    */   public B selected(boolean paramBoolean)
/*    */   {
/* 30 */     this.selected = paramBoolean;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public CheckMenuItem build()
/*    */   {
/* 39 */     CheckMenuItem localCheckMenuItem = new CheckMenuItem();
/* 40 */     applyTo(localCheckMenuItem);
/* 41 */     return localCheckMenuItem;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.CheckMenuItemBuilder
 * JD-Core Version:    0.6.2
 */