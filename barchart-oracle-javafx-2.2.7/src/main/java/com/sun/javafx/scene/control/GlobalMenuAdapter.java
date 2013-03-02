/*     */ package com.sun.javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import com.sun.javafx.menu.CheckMenuItemBase;
/*     */ import com.sun.javafx.menu.CustomMenuItemBase;
/*     */ import com.sun.javafx.menu.MenuBase;
/*     */ import com.sun.javafx.menu.MenuItemBase;
/*     */ import com.sun.javafx.menu.RadioMenuItemBase;
/*     */ import com.sun.javafx.menu.SeparatorMenuItemBase;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.control.CheckMenuItem;
/*     */ import javafx.scene.control.CustomMenuItem;
/*     */ import javafx.scene.control.Menu;
/*     */ import javafx.scene.control.MenuItem;
/*     */ import javafx.scene.control.RadioMenuItem;
/*     */ import javafx.scene.control.SeparatorMenuItem;
/*     */ 
/*     */ public class GlobalMenuAdapter extends Menu
/*     */   implements MenuBase
/*     */ {
/*     */   private Menu menu;
/*  62 */   private final ObservableList<MenuItemBase> items = new TrackableObservableList() {
/*  62 */     protected void onChanged(ListChangeListener.Change<MenuItemBase> paramAnonymousChange) {  }  } ;
/*     */ 
/*     */   public static MenuBase adapt(Menu paramMenu)
/*     */   {
/*  59 */     return new GlobalMenuAdapter(paramMenu);
/*     */   }
/*     */ 
/*     */   private GlobalMenuAdapter(final Menu paramMenu)
/*     */   {
/*  68 */     super(paramMenu.getText());
/*     */ 
/*  70 */     this.menu = paramMenu;
/*     */ 
/*  72 */     bindMenuItemProperties(this, paramMenu);
/*     */ 
/*  74 */     paramMenu.showingProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/*  76 */         if ((paramMenu.isShowing()) && (!GlobalMenuAdapter.this.isShowing()))
/*  77 */           GlobalMenuAdapter.this.show();
/*  78 */         else if ((!paramMenu.isShowing()) && (GlobalMenuAdapter.this.isShowing()))
/*  79 */           GlobalMenuAdapter.this.hide();
/*     */       }
/*     */     });
/*  83 */     showingProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/*  85 */         if ((GlobalMenuAdapter.this.isShowing()) && (!paramMenu.isShowing()))
/*  86 */           paramMenu.show();
/*  87 */         else if ((!GlobalMenuAdapter.this.isShowing()) && (paramMenu.isShowing()))
/*  88 */           paramMenu.hide();
/*     */       }
/*     */     });
/*  93 */     EventHandler local4 = new EventHandler() {
/*     */       public void handle(Event paramAnonymousEvent) {
/*  95 */         Event.fireEvent(paramMenu, new Event(paramAnonymousEvent.getEventType()));
/*     */       }
/*     */     };
/*  99 */     paramMenu.getItems().addListener(new ListChangeListener() {
/*     */       public void onChanged(ListChangeListener.Change<? extends MenuItem> paramAnonymousChange) {
/* 101 */         while (paramAnonymousChange.next()) {
/* 102 */           int i = paramAnonymousChange.getFrom();
/* 103 */           int j = paramAnonymousChange.getTo();
/* 104 */           List localList = paramAnonymousChange.getRemoved();
/* 105 */           for (int k = i + localList.size() - 1; k >= i; k--) {
/* 106 */             GlobalMenuAdapter.this.items.remove(k);
/* 107 */             GlobalMenuAdapter.this.getItems().remove(k);
/*     */           }
/* 109 */           for (k = i; k < j; k++) {
/* 110 */             MenuItem localMenuItem = (MenuItem)paramAnonymousChange.getList().get(k);
/* 111 */             GlobalMenuAdapter.this.insertItem(localMenuItem, k);
/*     */           }
/*     */         }
/*     */       }
/*     */     });
/* 117 */     for (MenuItem localMenuItem : paramMenu.getItems())
/* 118 */       insertItem(localMenuItem, this.items.size());
/*     */   }
/*     */ 
/*     */   private void insertItem(MenuItem paramMenuItem, int paramInt)
/*     */   {
/*     */     Object localObject;
/* 125 */     if ((paramMenuItem instanceof Menu))
/* 126 */       localObject = new GlobalMenuAdapter((Menu)paramMenuItem);
/* 127 */     else if ((paramMenuItem instanceof CheckMenuItem))
/* 128 */       localObject = new CheckMenuItemAdapter((CheckMenuItem)paramMenuItem, null);
/* 129 */     else if ((paramMenuItem instanceof RadioMenuItem))
/* 130 */       localObject = new RadioMenuItemAdapter((RadioMenuItem)paramMenuItem, null);
/* 131 */     else if ((paramMenuItem instanceof SeparatorMenuItem))
/* 132 */       localObject = new SeparatorMenuItemAdapter((SeparatorMenuItem)paramMenuItem, null);
/* 133 */     else if ((paramMenuItem instanceof CustomMenuItem))
/* 134 */       localObject = new CustomMenuItemAdapter((CustomMenuItem)paramMenuItem, null);
/*     */     else {
/* 136 */       localObject = new MenuItemAdapter(paramMenuItem, null);
/*     */     }
/*     */ 
/* 139 */     this.items.add(paramInt, localObject);
/* 140 */     getItems().add(paramInt, (MenuItem)localObject);
/*     */   }
/*     */ 
/*     */   public final ObservableList<MenuItemBase> getItemsBase() {
/* 144 */     return this.items;
/*     */   }
/*     */ 
/*     */   private static void bindMenuItemProperties(MenuItem paramMenuItem1, MenuItem paramMenuItem2)
/*     */   {
/* 149 */     paramMenuItem1.idProperty().bind(paramMenuItem2.idProperty());
/* 150 */     paramMenuItem1.textProperty().bind(paramMenuItem2.textProperty());
/* 151 */     paramMenuItem1.graphicProperty().bind(paramMenuItem2.graphicProperty());
/* 152 */     paramMenuItem1.disableProperty().bind(paramMenuItem2.disableProperty());
/* 153 */     paramMenuItem1.visibleProperty().bind(paramMenuItem2.visibleProperty());
/* 154 */     paramMenuItem1.acceleratorProperty().bind(paramMenuItem2.acceleratorProperty());
/* 155 */     paramMenuItem1.mnemonicParsingProperty().bind(paramMenuItem2.mnemonicParsingProperty());
/*     */ 
/* 157 */     paramMenuItem1.setOnAction(new EventHandler() {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent) {
/* 159 */         this.val$menuItem.fire();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void fireValidation()
/*     */   {
/* 166 */     if (this.menu.getOnMenuValidation() != null) {
/* 167 */       Event.fireEvent(this.menu, new Event(this.MENU_VALIDATION_EVENT));
/*     */     }
/* 169 */     Menu localMenu = this.menu.getParentMenu();
/* 170 */     if ((localMenu != null) && (localMenu.getOnMenuValidation() != null))
/* 171 */       Event.fireEvent(localMenu, new Event(localMenu.MENU_VALIDATION_EVENT));
/*     */   }
/*     */ 
/*     */   private class CustomMenuItemAdapter extends CustomMenuItem
/*     */     implements CustomMenuItemBase
/*     */   {
/*     */     private CustomMenuItem menuItem;
/*     */ 
/*     */     private CustomMenuItemAdapter(CustomMenuItem arg2)
/*     */     {
/*     */       MenuItem localMenuItem;
/* 273 */       this.menuItem = localMenuItem;
/*     */ 
/* 275 */       GlobalMenuAdapter.bindMenuItemProperties(this, localMenuItem);
/*     */     }
/*     */ 
/*     */     public void fireValidation()
/*     */     {
/* 280 */       if (getOnMenuValidation() != null) {
/* 281 */         Event.fireEvent(this.menuItem, new Event(this.MENU_VALIDATION_EVENT));
/*     */       }
/* 283 */       Menu localMenu = this.menuItem.getParentMenu();
/* 284 */       if (localMenu.getOnMenuValidation() != null)
/* 285 */         Event.fireEvent(localMenu, new Event(localMenu.MENU_VALIDATION_EVENT));
/*     */     }
/*     */   }
/*     */ 
/*     */   private class SeparatorMenuItemAdapter extends SeparatorMenuItem
/*     */     implements SeparatorMenuItemBase
/*     */   {
/*     */     private SeparatorMenuItem menuItem;
/*     */ 
/*     */     private SeparatorMenuItemAdapter(SeparatorMenuItem arg2)
/*     */     {
/*     */       MenuItem localMenuItem;
/* 252 */       this.menuItem = localMenuItem;
/*     */ 
/* 254 */       GlobalMenuAdapter.bindMenuItemProperties(this, localMenuItem);
/*     */     }
/*     */ 
/*     */     public void fireValidation()
/*     */     {
/* 259 */       if (getOnMenuValidation() != null) {
/* 260 */         Event.fireEvent(this.menuItem, new Event(this.MENU_VALIDATION_EVENT));
/*     */       }
/* 262 */       Menu localMenu = this.menuItem.getParentMenu();
/* 263 */       if (localMenu.getOnMenuValidation() != null)
/* 264 */         Event.fireEvent(localMenu, new Event(localMenu.MENU_VALIDATION_EVENT));
/*     */     }
/*     */   }
/*     */ 
/*     */   private class RadioMenuItemAdapter extends RadioMenuItem
/*     */     implements RadioMenuItemBase
/*     */   {
/*     */     private RadioMenuItem menuItem;
/*     */ 
/*     */     private RadioMenuItemAdapter(RadioMenuItem arg2)
/*     */     {
/* 227 */       super();
/*     */ 
/* 229 */       this.menuItem = localMenuItem;
/*     */ 
/* 231 */       GlobalMenuAdapter.bindMenuItemProperties(this, localMenuItem);
/*     */ 
/* 233 */       selectedProperty().bindBidirectional(localMenuItem.selectedProperty());
/*     */     }
/*     */ 
/*     */     public void fireValidation()
/*     */     {
/* 238 */       if (getOnMenuValidation() != null) {
/* 239 */         Event.fireEvent(this.menuItem, new Event(this.MENU_VALIDATION_EVENT));
/*     */       }
/* 241 */       Menu localMenu = this.menuItem.getParentMenu();
/* 242 */       if (localMenu.getOnMenuValidation() != null)
/* 243 */         Event.fireEvent(localMenu, new Event(localMenu.MENU_VALIDATION_EVENT));
/*     */     }
/*     */   }
/*     */ 
/*     */   private class CheckMenuItemAdapter extends CheckMenuItem
/*     */     implements CheckMenuItemBase
/*     */   {
/*     */     private CheckMenuItem menuItem;
/*     */ 
/*     */     private CheckMenuItemAdapter(CheckMenuItem arg2)
/*     */     {
/* 203 */       super();
/* 204 */       this.menuItem = localMenuItem;
/*     */ 
/* 206 */       GlobalMenuAdapter.bindMenuItemProperties(this, localMenuItem);
/*     */ 
/* 208 */       selectedProperty().bindBidirectional(localMenuItem.selectedProperty());
/*     */     }
/*     */ 
/*     */     public void fireValidation()
/*     */     {
/* 213 */       if (getOnMenuValidation() != null) {
/* 214 */         Event.fireEvent(this.menuItem, new Event(this.MENU_VALIDATION_EVENT));
/*     */       }
/* 216 */       Menu localMenu = this.menuItem.getParentMenu();
/* 217 */       if (localMenu.getOnMenuValidation() != null)
/* 218 */         Event.fireEvent(localMenu, new Event(localMenu.MENU_VALIDATION_EVENT));
/*     */     }
/*     */   }
/*     */ 
/*     */   private class MenuItemAdapter extends MenuItem
/*     */     implements MenuItemBase
/*     */   {
/*     */     private MenuItem menuItem;
/*     */ 
/*     */     private MenuItemAdapter(MenuItem arg2)
/*     */     {
/* 180 */       super();
/*     */ 
/* 182 */       this.menuItem = localMenuItem;
/*     */ 
/* 184 */       GlobalMenuAdapter.bindMenuItemProperties(this, localMenuItem);
/*     */     }
/*     */ 
/*     */     public void fireValidation()
/*     */     {
/* 189 */       if (this.menuItem.getOnMenuValidation() != null) {
/* 190 */         Event.fireEvent(this.menuItem, new Event(this.menuItem.MENU_VALIDATION_EVENT));
/*     */       }
/* 192 */       Menu localMenu = this.menuItem.getParentMenu();
/* 193 */       if (localMenu.getOnMenuValidation() != null)
/* 194 */         Event.fireEvent(localMenu, new Event(localMenu.MENU_VALIDATION_EVENT));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.GlobalMenuAdapter
 * JD-Core Version:    0.6.2
 */