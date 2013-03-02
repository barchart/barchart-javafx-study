/*    */ package javafx.scene.control;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.event.EventHandler;
/*    */ 
/*    */ public class ContextMenuBuilder<B extends ContextMenuBuilder<B>> extends PopupControlBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private boolean impl_showRelativeToWindow;
/*    */   private Collection<? extends MenuItem> items;
/*    */   private EventHandler<ActionEvent> onAction;
/*    */ 
/*    */   public static ContextMenuBuilder<?> create()
/*    */   {
/* 15 */     return new ContextMenuBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ContextMenu paramContextMenu)
/*    */   {
/* 20 */     super.applyTo(paramContextMenu);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramContextMenu.setImpl_showRelativeToWindow(this.impl_showRelativeToWindow);
/* 23 */     if ((i & 0x2) != 0) paramContextMenu.getItems().addAll(this.items);
/* 24 */     if ((i & 0x4) != 0) paramContextMenu.setOnAction(this.onAction);
/*    */   }
/*    */ 
/*    */   @Deprecated
/*    */   public B impl_showRelativeToWindow(boolean paramBoolean)
/*    */   {
/* 35 */     this.impl_showRelativeToWindow = paramBoolean;
/* 36 */     this.__set |= 1;
/* 37 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(Collection<? extends MenuItem> paramCollection)
/*    */   {
/* 46 */     this.items = paramCollection;
/* 47 */     this.__set |= 2;
/* 48 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(MenuItem[] paramArrayOfMenuItem)
/*    */   {
/* 55 */     return items(Arrays.asList(paramArrayOfMenuItem));
/*    */   }
/*    */ 
/*    */   public B onAction(EventHandler<ActionEvent> paramEventHandler)
/*    */   {
/* 64 */     this.onAction = paramEventHandler;
/* 65 */     this.__set |= 4;
/* 66 */     return this;
/*    */   }
/*    */ 
/*    */   public ContextMenu build()
/*    */   {
/* 73 */     ContextMenu localContextMenu = new ContextMenu();
/* 74 */     applyTo(localContextMenu);
/* 75 */     return localContextMenu;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ContextMenuBuilder
 * JD-Core Version:    0.6.2
 */