/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.beans.IDProperty;
/*     */ import com.sun.javafx.css.Styleable;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import com.sun.javafx.event.EventHandlerManager;
/*     */ import com.sun.javafx.scene.control.skin.ContextMenuContent;
/*     */ import com.sun.javafx.scene.control.skin.ContextMenuContent.MenuItemContainer;
/*     */ import com.sun.javafx.scene.control.skin.ContextMenuSkin;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.property.SimpleStringProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventDispatchChain;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.input.KeyCombination;
/*     */ import javafx.scene.layout.VBox;
/*     */ 
/*     */ @IDProperty("id")
/*     */ public class MenuItem
/*     */   implements EventTarget
/*     */ {
/* 142 */   private final ObservableList<String> styleClass = FXCollections.observableArrayList();
/*     */ 
/* 144 */   final EventHandlerManager eventHandlerManager = new EventHandlerManager(this);
/*     */   private Object userData;
/*     */   private ObservableMap<Object, Object> properties;
/*     */   private StringProperty id;
/*     */   private StringProperty style;
/*     */   private ReadOnlyObjectWrapper<Menu> parentMenu;
/*     */   private ReadOnlyObjectWrapper<ContextMenu> parentPopup;
/*     */   private StringProperty text;
/*     */   private ObjectProperty<Node> graphic;
/*     */   private ObjectProperty<EventHandler<ActionEvent>> onAction;
/* 333 */   public final EventType<Event> MENU_VALIDATION_EVENT = new EventType();
/*     */   private ObjectProperty<EventHandler<Event>> onMenuValidation;
/*     */   private BooleanProperty disable;
/*     */   private BooleanProperty visible;
/*     */   private ObjectProperty<KeyCombination> accelerator;
/*     */   private BooleanProperty mnemonicParsing;
/*     */   private static final String DEFAULT_STYLE_CLASS = "menu-item";
/*     */ 
/*     */   @Deprecated
/*     */   protected Styleable styleable;
/*     */ 
/*     */   public MenuItem()
/*     */   {
/* 111 */     this(null, null);
/*     */   }
/*     */ 
/*     */   public MenuItem(String paramString)
/*     */   {
/* 119 */     this(paramString, null);
/*     */   }
/*     */ 
/*     */   public MenuItem(String paramString, Node paramNode)
/*     */   {
/* 129 */     setText(paramString);
/* 130 */     setGraphic(paramNode);
/* 131 */     this.styleClass.add("menu-item");
/*     */   }
/*     */ 
/*     */   public final void setId(String paramString)
/*     */   {
/* 161 */     idProperty().set(paramString); } 
/* 162 */   public final String getId() { return this.id == null ? null : (String)this.id.get(); } 
/*     */   public final StringProperty idProperty() {
/* 164 */     if (this.id == null) {
/* 165 */       this.id = new SimpleStringProperty(this, "id");
/*     */     }
/* 167 */     return this.id;
/*     */   }
/*     */ 
/*     */   public final void setStyle(String paramString)
/*     */   {
/* 177 */     styleProperty().set(paramString); } 
/* 178 */   public final String getStyle() { return this.style == null ? null : (String)this.style.get(); } 
/*     */   public final StringProperty styleProperty() {
/* 180 */     if (this.style == null) {
/* 181 */       this.style = new SimpleStringProperty(this, "style");
/*     */     }
/* 183 */     return this.style;
/*     */   }
/*     */ 
/*     */   protected final void setParentMenu(Menu paramMenu)
/*     */   {
/* 201 */     parentMenuPropertyImpl().set(paramMenu);
/*     */   }
/*     */ 
/*     */   public final Menu getParentMenu() {
/* 205 */     return this.parentMenu == null ? null : (Menu)this.parentMenu.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<Menu> parentMenuProperty() {
/* 209 */     return parentMenuPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyObjectWrapper<Menu> parentMenuPropertyImpl() {
/* 213 */     if (this.parentMenu == null) {
/* 214 */       this.parentMenu = new ReadOnlyObjectWrapper(this, "parentMenu");
/*     */     }
/* 216 */     return this.parentMenu;
/*     */   }
/*     */ 
/*     */   protected final void setParentPopup(ContextMenu paramContextMenu)
/*     */   {
/* 227 */     parentPopupPropertyImpl().set(paramContextMenu);
/*     */   }
/*     */ 
/*     */   public final ContextMenu getParentPopup() {
/* 231 */     return this.parentPopup == null ? null : (ContextMenu)this.parentPopup.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<ContextMenu> parentPopupProperty() {
/* 235 */     return parentPopupPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyObjectWrapper<ContextMenu> parentPopupPropertyImpl() {
/* 239 */     if (this.parentPopup == null) {
/* 240 */       this.parentPopup = new ReadOnlyObjectWrapper(this, "parentPopup");
/*     */     }
/* 242 */     return this.parentPopup;
/*     */   }
/*     */ 
/*     */   public final void setText(String paramString)
/*     */   {
/* 253 */     textProperty().set(paramString);
/*     */   }
/*     */ 
/*     */   public final String getText() {
/* 257 */     return this.text == null ? null : (String)this.text.get();
/*     */   }
/*     */ 
/*     */   public final StringProperty textProperty() {
/* 261 */     if (this.text == null) {
/* 262 */       this.text = new SimpleStringProperty(this, "text");
/*     */     }
/* 264 */     return this.text;
/*     */   }
/*     */ 
/*     */   public final void setGraphic(Node paramNode)
/*     */   {
/* 277 */     graphicProperty().set(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getGraphic() {
/* 281 */     return this.graphic == null ? null : (Node)this.graphic.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> graphicProperty() {
/* 285 */     if (this.graphic == null) {
/* 286 */       this.graphic = new SimpleObjectProperty(this, "graphic");
/*     */     }
/* 288 */     return this.graphic;
/*     */   }
/*     */ 
/*     */   public final void setOnAction(EventHandler<ActionEvent> paramEventHandler)
/*     */   {
/* 302 */     onActionProperty().set(paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final EventHandler<ActionEvent> getOnAction() {
/* 306 */     return this.onAction == null ? null : (EventHandler)this.onAction.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {
/* 310 */     if (this.onAction == null) {
/* 311 */       this.onAction = new ObjectPropertyBase() {
/*     */         protected void invalidated() {
/* 313 */           MenuItem.this.eventHandlerManager.setEventHandler(ActionEvent.ACTION, (EventHandler)get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 318 */           return MenuItem.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 323 */           return "onAction";
/*     */         }
/*     */       };
/*     */     }
/* 327 */     return this.onAction;
/*     */   }
/*     */ 
/*     */   public final void setOnMenuValidation(EventHandler<Event> paramEventHandler)
/*     */   {
/* 344 */     onMenuValidationProperty().set(paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final EventHandler<Event> getOnMenuValidation() {
/* 348 */     return this.onMenuValidation == null ? null : (EventHandler)this.onMenuValidation.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventHandler<Event>> onMenuValidationProperty() {
/* 352 */     if (this.onMenuValidation == null) {
/* 353 */       this.onMenuValidation = new ObjectPropertyBase() {
/*     */         protected void invalidated() {
/* 355 */           MenuItem.this.eventHandlerManager.setEventHandler(MenuItem.this.MENU_VALIDATION_EVENT, (EventHandler)get());
/*     */         }
/*     */         public Object getBean() {
/* 358 */           return MenuItem.this;
/*     */         }
/*     */         public String getName() {
/* 361 */           return "onMenuValidation";
/*     */         }
/*     */       };
/*     */     }
/* 365 */     return this.onMenuValidation;
/*     */   }
/*     */ 
/*     */   public final void setDisable(boolean paramBoolean)
/*     */   {
/* 374 */     disableProperty().set(paramBoolean); } 
/* 375 */   public final boolean isDisable() { return this.disable == null ? false : this.disable.get(); } 
/*     */   public final BooleanProperty disableProperty() {
/* 377 */     if (this.disable == null) {
/* 378 */       this.disable = new SimpleBooleanProperty(this, "disable");
/*     */     }
/* 380 */     return this.disable;
/*     */   }
/*     */ 
/*     */   public final void setVisible(boolean paramBoolean)
/*     */   {
/* 389 */     visibleProperty().set(paramBoolean); } 
/* 390 */   public final boolean isVisible() { return this.visible == null ? true : this.visible.get(); } 
/*     */   public final BooleanProperty visibleProperty() {
/* 392 */     if (this.visible == null) {
/* 393 */       this.visible = new SimpleBooleanProperty(this, "visible", true);
/*     */     }
/* 395 */     return this.visible;
/*     */   }
/*     */ 
/*     */   public final void setAccelerator(KeyCombination paramKeyCombination)
/*     */   {
/* 404 */     acceleratorProperty().set(paramKeyCombination);
/*     */   }
/*     */   public final KeyCombination getAccelerator() {
/* 407 */     return this.accelerator == null ? null : (KeyCombination)this.accelerator.get();
/*     */   }
/*     */   public final ObjectProperty<KeyCombination> acceleratorProperty() {
/* 410 */     if (this.accelerator == null) {
/* 411 */       this.accelerator = new SimpleObjectProperty(this, "accelerator");
/*     */     }
/* 413 */     return this.accelerator;
/*     */   }
/*     */ 
/*     */   public final void setMnemonicParsing(boolean paramBoolean)
/*     */   {
/* 430 */     mnemonicParsingProperty().set(paramBoolean);
/*     */   }
/*     */   public final boolean isMnemonicParsing() {
/* 433 */     return this.mnemonicParsing == null ? true : this.mnemonicParsing.get();
/*     */   }
/*     */   public final BooleanProperty mnemonicParsingProperty() {
/* 436 */     if (this.mnemonicParsing == null) {
/* 437 */       this.mnemonicParsing = new SimpleBooleanProperty(this, "mnemonicParsing", true);
/*     */     }
/* 439 */     return this.mnemonicParsing;
/*     */   }
/*     */ 
/*     */   public ObservableList<String> getStyleClass()
/*     */   {
/* 449 */     return this.styleClass;
/*     */   }
/*     */ 
/*     */   public void fire()
/*     */   {
/* 456 */     Event.fireEvent(this, new ActionEvent(this, this));
/*     */   }
/*     */ 
/*     */   public <E extends Event> void addEventHandler(EventType<E> paramEventType, EventHandler<E> paramEventHandler)
/*     */   {
/* 470 */     this.eventHandlerManager.addEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   public <E extends Event> void removeEventHandler(EventType<E> paramEventType, EventHandler<E> paramEventHandler)
/*     */   {
/* 485 */     this.eventHandlerManager.removeEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain)
/*     */   {
/* 491 */     if (getParentPopup() != null) {
/* 492 */       getParentPopup().buildEventDispatchChain(paramEventDispatchChain);
/*     */     }
/*     */ 
/* 495 */     if (getParentMenu() != null) {
/* 496 */       getParentMenu().buildEventDispatchChain(paramEventDispatchChain);
/*     */     }
/*     */ 
/* 499 */     return paramEventDispatchChain.prepend(this.eventHandlerManager);
/*     */   }
/*     */ 
/*     */   public Object getUserData()
/*     */   {
/* 510 */     return this.userData;
/*     */   }
/*     */ 
/*     */   public void setUserData(Object paramObject)
/*     */   {
/* 523 */     this.userData = paramObject;
/*     */   }
/*     */ 
/*     */   public ObservableMap<Object, Object> getProperties()
/*     */   {
/* 534 */     if (this.properties == null) {
/* 535 */       this.properties = FXCollections.observableMap(new HashMap());
/*     */     }
/* 537 */     return this.properties;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Styleable impl_getStyleable()
/*     */   {
/* 565 */     if (this.styleable == null) {
/* 566 */       this.styleable = new Styleable()
/*     */       {
/*     */         public String getId()
/*     */         {
/* 570 */           return MenuItem.this.getId();
/*     */         }
/*     */ 
/*     */         public List<String> getStyleClass()
/*     */         {
/* 575 */           return MenuItem.this.getStyleClass();
/*     */         }
/*     */ 
/*     */         public String getStyle()
/*     */         {
/* 580 */           return MenuItem.this.getStyle();
/*     */         }
/*     */ 
/*     */         public Styleable getStyleableParent()
/*     */         {
/* 585 */           Menu localMenu = MenuItem.this.getParentMenu();
/* 586 */           ContextMenu localContextMenu = MenuItem.this.getParentPopup();
/*     */ 
/* 588 */           if (localMenu == null) {
/* 589 */             return localContextMenu != null ? localContextMenu.impl_getStyleable() : null;
/*     */           }
/*     */ 
/* 593 */           return localMenu != null ? localMenu.impl_getStyleable() : null;
/*     */         }
/*     */ 
/*     */         public List<StyleableProperty> getStyleableProperties()
/*     */         {
/* 602 */           return Collections.EMPTY_LIST;
/*     */         }
/*     */ 
/*     */         public Node getNode()
/*     */         {
/* 609 */           ContextMenu localContextMenu = MenuItem.this.getParentPopup();
/* 610 */           if ((localContextMenu == null) || (!(localContextMenu.getSkin() instanceof ContextMenuSkin))) return null;
/*     */ 
/* 612 */           ContextMenuSkin localContextMenuSkin = (ContextMenuSkin)localContextMenu.getSkin();
/* 613 */           if (!(localContextMenuSkin.getNode() instanceof ContextMenuContent)) return null;
/*     */ 
/* 615 */           ContextMenuContent localContextMenuContent = (ContextMenuContent)localContextMenuSkin.getNode();
/* 616 */           VBox localVBox = localContextMenuContent.getItemsContainer();
/*     */ 
/* 618 */           MenuItem localMenuItem = MenuItem.this;
/* 619 */           ObservableList localObservableList = localVBox.getChildrenUnmodifiable();
/* 620 */           for (int i = 0; i < localObservableList.size(); i++) {
/* 621 */             if ((localObservableList.get(i) instanceof ContextMenuContent.MenuItemContainer))
/*     */             {
/* 623 */               ContextMenuContent.MenuItemContainer localMenuItemContainer = (ContextMenuContent.MenuItemContainer)localObservableList.get(i);
/*     */ 
/* 626 */               if (localMenuItem.equals(localMenuItemContainer.getItem())) {
/* 627 */                 return localMenuItemContainer;
/*     */               }
/*     */             }
/*     */           }
/* 631 */           return null;
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/* 636 */     return this.styleable;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.MenuItem
 * JD-Core Version:    0.6.2
 */