/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.scene.Node;
/*    */ 
/*    */ public class CustomMenuItemBuilder<B extends CustomMenuItemBuilder<B>> extends MenuItemBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private Node content;
/*    */   private boolean hideOnClick;
/*    */ 
/*    */   public static CustomMenuItemBuilder<?> create()
/*    */   {
/* 15 */     return new CustomMenuItemBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(CustomMenuItem paramCustomMenuItem)
/*    */   {
/* 20 */     super.applyTo(paramCustomMenuItem);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramCustomMenuItem.setContent(this.content);
/* 23 */     if ((i & 0x2) != 0) paramCustomMenuItem.setHideOnClick(this.hideOnClick);
/*    */   }
/*    */ 
/*    */   public B content(Node paramNode)
/*    */   {
/* 32 */     this.content = paramNode;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B hideOnClick(boolean paramBoolean)
/*    */   {
/* 43 */     this.hideOnClick = paramBoolean;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public CustomMenuItem build()
/*    */   {
/* 52 */     CustomMenuItem localCustomMenuItem = new CustomMenuItem();
/* 53 */     applyTo(localCustomMenuItem);
/* 54 */     return localCustomMenuItem;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.CustomMenuItemBuilder
 * JD-Core Version:    0.6.2
 */