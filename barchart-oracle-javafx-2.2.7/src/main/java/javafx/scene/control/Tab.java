/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.Styleable;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import com.sun.javafx.event.EventHandlerManager;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import javafx.beans.DefaultProperty;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.property.SimpleStringProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventDispatchChain;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ @DefaultProperty("content")
/*     */ public class Tab
/*     */   implements EventTarget
/*     */ {
/*     */   private StringProperty id;
/*     */   private StringProperty style;
/*     */   private ReadOnlyBooleanWrapper selected;
/*     */   private ReadOnlyObjectWrapper<TabPane> tabPane;
/*     */   private StringProperty text;
/*     */   private ObjectProperty<Node> graphic;
/*     */   private ObjectProperty<Node> content;
/*     */   private ObjectProperty<ContextMenu> contextMenu;
/*     */   private BooleanProperty closable;
/* 382 */   public static final EventType<Event> SELECTION_CHANGED_EVENT = new EventType();
/*     */   private ObjectProperty<EventHandler<Event>> onSelectionChanged;
/* 428 */   public static final EventType<Event> CLOSED_EVENT = new EventType();
/*     */   private ObjectProperty<EventHandler<Event>> onClosed;
/*     */   private ObjectProperty<Tooltip> tooltip;
/* 494 */   private final ObservableList<String> styleClass = FXCollections.observableArrayList();
/*     */   private BooleanProperty disable;
/*     */   private ReadOnlyBooleanWrapper disabled;
/* 595 */   private static final Object USER_DATA_KEY = new Object();
/*     */   private ObservableMap<Object, Object> properties;
/* 664 */   private final EventHandlerManager eventHandlerManager = new EventHandlerManager(this);
/*     */   private static final String DEFAULT_STYLE_CLASS = "tab";
/*     */ 
/*     */   /** @deprecated */
/*     */   protected Styleable styleable;
/*     */ 
/*     */   public Tab()
/*     */   {
/*  74 */     this(null);
/*     */   }
/*     */ 
/*     */   public Tab(String paramString)
/*     */   {
/*  83 */     setText(paramString);
/*  84 */     this.styleClass.addAll(new String[] { "tab" });
/*     */   }
/*     */ 
/*     */   public final void setId(String paramString)
/*     */   {
/* 100 */     idProperty().set(paramString);
/*     */   }
/*     */ 
/*     */   public final String getId()
/*     */   {
/* 107 */     return this.id == null ? null : (String)this.id.get();
/*     */   }
/*     */ 
/*     */   public final StringProperty idProperty()
/*     */   {
/* 113 */     if (this.id == null) {
/* 114 */       this.id = new SimpleStringProperty(this, "id");
/*     */     }
/* 116 */     return this.id;
/*     */   }
/*     */ 
/*     */   public final void setStyle(String paramString)
/*     */   {
/* 132 */     styleProperty().set(paramString);
/*     */   }
/*     */ 
/*     */   public final String getStyle()
/*     */   {
/* 139 */     return this.style == null ? null : (String)this.style.get();
/*     */   }
/*     */ 
/*     */   public final StringProperty styleProperty()
/*     */   {
/* 145 */     if (this.style == null) {
/* 146 */       this.style = new SimpleStringProperty(this, "style");
/*     */     }
/* 148 */     return this.style;
/*     */   }
/*     */ 
/*     */   final void setSelected(boolean paramBoolean)
/*     */   {
/* 154 */     selectedPropertyImpl().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isSelected()
/*     */   {
/* 163 */     return this.selected == null ? false : this.selected.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyBooleanProperty selectedProperty()
/*     */   {
/* 170 */     return selectedPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyBooleanWrapper selectedPropertyImpl() {
/* 174 */     if (this.selected == null) {
/* 175 */       this.selected = new ReadOnlyBooleanWrapper() {
/*     */         protected void invalidated() {
/* 177 */           if (Tab.this.getOnSelectionChanged() != null)
/* 178 */             Event.fireEvent(Tab.this, new Event(Tab.SELECTION_CHANGED_EVENT));
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 184 */           return Tab.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 189 */           return "selected";
/*     */         }
/*     */       };
/*     */     }
/* 193 */     return this.selected;
/*     */   }
/*     */ 
/*     */   final void setTabPane(TabPane paramTabPane)
/*     */   {
/* 199 */     tabPanePropertyImpl().set(paramTabPane);
/*     */   }
/*     */ 
/*     */   public final TabPane getTabPane()
/*     */   {
/* 206 */     return this.tabPane == null ? null : (TabPane)this.tabPane.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<TabPane> tabPaneProperty()
/*     */   {
/* 213 */     return tabPanePropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyObjectWrapper<TabPane> tabPanePropertyImpl() {
/* 217 */     if (this.tabPane == null) {
/* 218 */       this.tabPane = new ReadOnlyObjectWrapper(this, "tabPane");
/*     */     }
/* 220 */     return this.tabPane;
/*     */   }
/*     */ 
/*     */   public final void setText(String paramString)
/*     */   {
/* 231 */     textProperty().set(paramString);
/*     */   }
/*     */ 
/*     */   public final String getText()
/*     */   {
/* 240 */     return this.text == null ? null : (String)this.text.get();
/*     */   }
/*     */ 
/*     */   public final StringProperty textProperty()
/*     */   {
/* 247 */     if (this.text == null) {
/* 248 */       this.text = new SimpleStringProperty(this, "text");
/*     */     }
/* 250 */     return this.text;
/*     */   }
/*     */ 
/*     */   public final void setGraphic(Node paramNode)
/*     */   {
/* 262 */     graphicProperty().set(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getGraphic()
/*     */   {
/* 271 */     return this.graphic == null ? null : (Node)this.graphic.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> graphicProperty()
/*     */   {
/* 280 */     if (this.graphic == null) {
/* 281 */       this.graphic = new SimpleObjectProperty(this, "graphic");
/*     */     }
/* 283 */     return this.graphic;
/*     */   }
/*     */ 
/*     */   public final void setContent(Node paramNode)
/*     */   {
/* 294 */     contentProperty().set(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getContent()
/*     */   {
/* 303 */     return this.content == null ? null : (Node)this.content.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> contentProperty()
/*     */   {
/* 310 */     if (this.content == null) {
/* 311 */       this.content = new SimpleObjectProperty(this, "content");
/*     */     }
/* 313 */     return this.content;
/*     */   }
/*     */ 
/*     */   public final void setContextMenu(ContextMenu paramContextMenu)
/*     */   {
/* 324 */     contextMenuProperty().set(paramContextMenu);
/*     */   }
/*     */ 
/*     */   public final ContextMenu getContextMenu()
/*     */   {
/* 332 */     return this.contextMenu == null ? null : (ContextMenu)this.contextMenu.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<ContextMenu> contextMenuProperty()
/*     */   {
/* 339 */     if (this.contextMenu == null) {
/* 340 */       this.contextMenu = new SimpleObjectProperty(this, "contextMenu");
/*     */     }
/* 342 */     return this.contextMenu;
/*     */   }
/*     */ 
/*     */   public final void setClosable(boolean paramBoolean)
/*     */   {
/* 356 */     closableProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isClosable()
/*     */   {
/* 365 */     return this.closable == null ? true : this.closable.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty closableProperty()
/*     */   {
/* 372 */     if (this.closable == null) {
/* 373 */       this.closable = new SimpleBooleanProperty(this, "closable", true);
/*     */     }
/* 375 */     return this.closable;
/*     */   }
/*     */ 
/*     */   public final void setOnSelectionChanged(EventHandler<Event> paramEventHandler)
/*     */   {
/* 389 */     onSelectionChangedProperty().set(paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final EventHandler<Event> getOnSelectionChanged()
/*     */   {
/* 398 */     return this.onSelectionChanged == null ? null : (EventHandler)this.onSelectionChanged.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventHandler<Event>> onSelectionChangedProperty()
/*     */   {
/* 405 */     if (this.onSelectionChanged == null) {
/* 406 */       this.onSelectionChanged = new ObjectPropertyBase() {
/*     */         protected void invalidated() {
/* 408 */           Tab.this.setEventHandler(Tab.SELECTION_CHANGED_EVENT, (EventHandler)get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 413 */           return Tab.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 418 */           return "onSelectionChanged";
/*     */         }
/*     */       };
/*     */     }
/* 422 */     return this.onSelectionChanged;
/*     */   }
/*     */ 
/*     */   public final void setOnClosed(EventHandler<Event> paramEventHandler)
/*     */   {
/* 435 */     onClosedProperty().set(paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final EventHandler<Event> getOnClosed()
/*     */   {
/* 444 */     return this.onClosed == null ? null : (EventHandler)this.onClosed.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventHandler<Event>> onClosedProperty()
/*     */   {
/* 451 */     if (this.onClosed == null) {
/* 452 */       this.onClosed = new ObjectPropertyBase() {
/*     */         protected void invalidated() {
/* 454 */           Tab.this.setEventHandler(Tab.CLOSED_EVENT, (EventHandler)get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 459 */           return Tab.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 464 */           return "onClosed";
/*     */         }
/*     */       };
/*     */     }
/* 468 */     return this.onClosed;
/*     */   }
/*     */ 
/*     */   public final void setTooltip(Tooltip paramTooltip)
/*     */   {
/* 476 */     tooltipProperty().setValue(paramTooltip);
/*     */   }
/*     */ 
/*     */   public final Tooltip getTooltip()
/*     */   {
/* 482 */     return this.tooltip == null ? null : (Tooltip)this.tooltip.getValue();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Tooltip> tooltipProperty()
/*     */   {
/* 488 */     if (this.tooltip == null) {
/* 489 */       this.tooltip = new SimpleObjectProperty(this, "tooltip");
/*     */     }
/* 491 */     return this.tooltip;
/*     */   }
/*     */ 
/*     */   public final void setDisable(boolean paramBoolean)
/*     */   {
/* 507 */     disableProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isDisable()
/*     */   {
/* 514 */     return this.disable == null ? false : this.disable.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty disableProperty()
/*     */   {
/* 525 */     if (this.disable == null) {
/* 526 */       this.disable = new BooleanPropertyBase(false)
/*     */       {
/*     */         protected void invalidated() {
/* 529 */           Tab.this.updateDisabled();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 534 */           return Tab.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 539 */           return "disable";
/*     */         }
/*     */       };
/*     */     }
/* 543 */     return this.disable;
/*     */   }
/*     */ 
/*     */   private final void setDisabled(boolean paramBoolean)
/*     */   {
/* 549 */     disabledPropertyImpl().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isDisabled()
/*     */   {
/* 558 */     return this.disabled == null ? false : this.disabled.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyBooleanProperty disabledProperty()
/*     */   {
/* 570 */     return disabledPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyBooleanWrapper disabledPropertyImpl() {
/* 574 */     if (this.disabled == null) {
/* 575 */       this.disabled = new ReadOnlyBooleanWrapper()
/*     */       {
/*     */         public Object getBean() {
/* 578 */           return Tab.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 583 */           return "disabled";
/*     */         }
/*     */       };
/*     */     }
/* 587 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   private void updateDisabled() {
/* 591 */     setDisabled((isDisable()) || ((getTabPane() != null) && (getTabPane().isDisabled())));
/*     */   }
/*     */ 
/*     */   public final ObservableMap<Object, Object> getProperties()
/*     */   {
/* 609 */     if (this.properties == null) {
/* 610 */       this.properties = FXCollections.observableMap(new HashMap());
/*     */     }
/* 612 */     return this.properties;
/*     */   }
/*     */ 
/*     */   public boolean hasProperties()
/*     */   {
/* 621 */     return this.properties != null;
/*     */   }
/*     */ 
/*     */   public void setUserData(Object paramObject)
/*     */   {
/* 637 */     getProperties().put(USER_DATA_KEY, paramObject);
/*     */   }
/*     */ 
/*     */   public Object getUserData()
/*     */   {
/* 649 */     return getProperties().get(USER_DATA_KEY);
/*     */   }
/*     */ 
/*     */   public ObservableList<String> getStyleClass()
/*     */   {
/* 661 */     return this.styleClass;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain)
/*     */   {
/* 673 */     return paramEventDispatchChain.prepend(this.eventHandlerManager);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   protected <E extends Event> void setEventHandler(EventType<E> paramEventType, EventHandler<E> paramEventHandler)
/*     */   {
/* 681 */     this.eventHandlerManager.setEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Styleable impl_getStyleable()
/*     */   {
/* 708 */     if (this.styleable == null) {
/* 709 */       this.styleable = new Styleable()
/*     */       {
/*     */         public String getId()
/*     */         {
/* 713 */           return Tab.this.getId();
/*     */         }
/*     */ 
/*     */         public List<String> getStyleClass()
/*     */         {
/* 718 */           return Tab.this.getStyleClass();
/*     */         }
/*     */ 
/*     */         public String getStyle()
/*     */         {
/* 723 */           return Tab.this.getStyle();
/*     */         }
/*     */ 
/*     */         public Styleable getStyleableParent()
/*     */         {
/* 728 */           return Tab.this.getTabPane() != null ? Tab.this.getTabPane().impl_getStyleable() : null;
/*     */         }
/*     */ 
/*     */         public List<StyleableProperty> getStyleableProperties()
/*     */         {
/* 735 */           return Collections.EMPTY_LIST;
/*     */         }
/*     */ 
/*     */         public Node getNode()
/*     */         {
/* 740 */           return null;
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/* 745 */     return this.styleable;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.Tab
 * JD-Core Version:    0.6.2
 */