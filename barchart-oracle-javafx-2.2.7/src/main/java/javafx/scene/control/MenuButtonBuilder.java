/*    */ package javafx.scene.control;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.geometry.Side;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class MenuButtonBuilder<B extends MenuButtonBuilder<B>> extends ButtonBaseBuilder<B>
/*    */   implements Builder<MenuButton>
/*    */ {
/*    */   private int __set;
/*    */   private Collection<? extends MenuItem> items;
/*    */   private Side popupSide;
/*    */ 
/*    */   public static MenuButtonBuilder<?> create()
/*    */   {
/* 15 */     return new MenuButtonBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(MenuButton paramMenuButton)
/*    */   {
/* 20 */     super.applyTo(paramMenuButton);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramMenuButton.getItems().addAll(this.items);
/* 23 */     if ((i & 0x2) != 0) paramMenuButton.setPopupSide(this.popupSide);
/*    */   }
/*    */ 
/*    */   public B items(Collection<? extends MenuItem> paramCollection)
/*    */   {
/* 32 */     this.items = paramCollection;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(MenuItem[] paramArrayOfMenuItem)
/*    */   {
/* 41 */     return items(Arrays.asList(paramArrayOfMenuItem));
/*    */   }
/*    */ 
/*    */   public B popupSide(Side paramSide)
/*    */   {
/* 50 */     this.popupSide = paramSide;
/* 51 */     this.__set |= 2;
/* 52 */     return this;
/*    */   }
/*    */ 
/*    */   public MenuButton build()
/*    */   {
/* 59 */     MenuButton localMenuButton = new MenuButton();
/* 60 */     applyTo(localMenuButton);
/* 61 */     return localMenuButton;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.MenuButtonBuilder
 * JD-Core Version:    0.6.2
 */