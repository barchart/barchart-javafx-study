/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class RadioMenuItemBuilder<B extends RadioMenuItemBuilder<B>> extends MenuItemBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private boolean selected;
/*    */   private String text;
/*    */   private ToggleGroup toggleGroup;
/*    */ 
/*    */   public static RadioMenuItemBuilder<?> create()
/*    */   {
/* 15 */     return new RadioMenuItemBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(RadioMenuItem paramRadioMenuItem)
/*    */   {
/* 20 */     super.applyTo(paramRadioMenuItem);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramRadioMenuItem.setSelected(this.selected);
/* 23 */     if ((i & 0x2) != 0) paramRadioMenuItem.setToggleGroup(this.toggleGroup);
/*    */   }
/*    */ 
/*    */   public B selected(boolean paramBoolean)
/*    */   {
/* 32 */     this.selected = paramBoolean;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B text(String paramString)
/*    */   {
/* 43 */     this.text = paramString;
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   public B toggleGroup(ToggleGroup paramToggleGroup)
/*    */   {
/* 53 */     this.toggleGroup = paramToggleGroup;
/* 54 */     this.__set |= 2;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public RadioMenuItem build()
/*    */   {
/* 62 */     RadioMenuItem localRadioMenuItem = new RadioMenuItem(this.text);
/* 63 */     applyTo(localRadioMenuItem);
/* 64 */     return localRadioMenuItem;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.RadioMenuItemBuilder
 * JD-Core Version:    0.6.2
 */