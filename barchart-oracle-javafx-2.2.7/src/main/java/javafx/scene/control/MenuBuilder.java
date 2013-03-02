/*    */ package javafx.scene.control;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventHandler;
/*    */ 
/*    */ public class MenuBuilder<B extends MenuBuilder<B>> extends MenuItemBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private Collection<? extends MenuItem> items;
/*    */   private EventHandler<Event> onHidden;
/*    */   private EventHandler<Event> onHiding;
/*    */   private EventHandler<Event> onShowing;
/*    */   private EventHandler<Event> onShown;
/*    */ 
/*    */   public static MenuBuilder<?> create()
/*    */   {
/* 15 */     return new MenuBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Menu paramMenu)
/*    */   {
/* 20 */     super.applyTo(paramMenu);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramMenu.getItems().addAll(this.items);
/* 23 */     if ((i & 0x2) != 0) paramMenu.setOnHidden(this.onHidden);
/* 24 */     if ((i & 0x4) != 0) paramMenu.setOnHiding(this.onHiding);
/* 25 */     if ((i & 0x8) != 0) paramMenu.setOnShowing(this.onShowing);
/* 26 */     if ((i & 0x10) != 0) paramMenu.setOnShown(this.onShown);
/*    */   }
/*    */ 
/*    */   public B items(Collection<? extends MenuItem> paramCollection)
/*    */   {
/* 35 */     this.items = paramCollection;
/* 36 */     this.__set |= 1;
/* 37 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(MenuItem[] paramArrayOfMenuItem)
/*    */   {
/* 44 */     return items(Arrays.asList(paramArrayOfMenuItem));
/*    */   }
/*    */ 
/*    */   public B onHidden(EventHandler<Event> paramEventHandler)
/*    */   {
/* 53 */     this.onHidden = paramEventHandler;
/* 54 */     this.__set |= 2;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public B onHiding(EventHandler<Event> paramEventHandler)
/*    */   {
/* 64 */     this.onHiding = paramEventHandler;
/* 65 */     this.__set |= 4;
/* 66 */     return this;
/*    */   }
/*    */ 
/*    */   public B onShowing(EventHandler<Event> paramEventHandler)
/*    */   {
/* 75 */     this.onShowing = paramEventHandler;
/* 76 */     this.__set |= 8;
/* 77 */     return this;
/*    */   }
/*    */ 
/*    */   public B onShown(EventHandler<Event> paramEventHandler)
/*    */   {
/* 86 */     this.onShown = paramEventHandler;
/* 87 */     this.__set |= 16;
/* 88 */     return this;
/*    */   }
/*    */ 
/*    */   public Menu build()
/*    */   {
/* 95 */     Menu localMenu = new Menu();
/* 96 */     applyTo(localMenu);
/* 97 */     return localMenu;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.MenuBuilder
 * JD-Core Version:    0.6.2
 */