/*      */ package javafx.scene.control;
/*      */ 
/*      */ import com.sun.javafx.css.Styleable;
/*      */ import com.sun.javafx.css.StyleableProperty;
/*      */ import com.sun.javafx.event.EventHandlerManager;
/*      */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*      */ import com.sun.javafx.scene.control.skin.NestedTableColumnHeader;
/*      */ import com.sun.javafx.scene.control.skin.TableColumnHeader;
/*      */ import com.sun.javafx.scene.control.skin.TableHeaderRow;
/*      */ import com.sun.javafx.scene.control.skin.TableViewSkin;
/*      */ import com.sun.javafx.scene.control.skin.Utils;
/*      */ import java.text.Collator;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.BooleanPropertyBase;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.property.DoublePropertyBase;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ObjectPropertyBase;
/*      */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*      */ import javafx.beans.property.ReadOnlyDoubleWrapper;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*      */ import javafx.beans.property.SimpleBooleanProperty;
/*      */ import javafx.beans.property.SimpleObjectProperty;
/*      */ import javafx.beans.property.SimpleStringProperty;
/*      */ import javafx.beans.property.StringProperty;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.beans.value.WritableValue;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ListChangeListener;
/*      */ import javafx.collections.ListChangeListener.Change;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.collections.ObservableMap;
/*      */ import javafx.event.Event;
/*      */ import javafx.event.EventDispatchChain;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.event.EventTarget;
/*      */ import javafx.event.EventType;
/*      */ import javafx.scene.Node;
/*      */ import javafx.util.Callback;
/*      */ 
/*      */ public class TableColumn<S, T>
/*      */   implements EventTarget
/*      */ {
/*      */   private static final double DEFAULT_WIDTH = 80.0D;
/*      */   private static final double DEFAULT_MIN_WIDTH = 10.0D;
/*      */   private static final double DEFAULT_MAX_WIDTH = 5000.0D;
/*  171 */   private static final EventType<?> EDIT_ANY_EVENT = new EventType(Event.ANY, "EDIT");
/*      */ 
/*  183 */   private static final EventType<?> EDIT_START_EVENT = new EventType(editAnyEvent(), "EDIT_START");
/*      */ 
/*  194 */   private static final EventType<?> EDIT_CANCEL_EVENT = new EventType(editAnyEvent(), "EDIT_CANCEL");
/*      */ 
/*  206 */   private static final EventType<?> EDIT_COMMIT_EVENT = new EventType(editAnyEvent(), "EDIT_COMMIT");
/*      */ 
/*  219 */   public static final Callback<TableColumn<?, ?>, TableCell<?, ?>> DEFAULT_CELL_FACTORY = new Callback() {
/*      */     public TableCell<?, ?> call(TableColumn<?, ?> paramAnonymousTableColumn) {
/*  221 */       return new TableCell() {
/*      */         protected void updateItem(Object paramAnonymous2Object, boolean paramAnonymous2Boolean) {
/*  223 */           if (paramAnonymous2Object == getItem()) return;
/*      */ 
/*  225 */           super.updateItem(paramAnonymous2Object, paramAnonymous2Boolean);
/*      */ 
/*  227 */           if (paramAnonymous2Object == null) {
/*  228 */             super.setText(null);
/*  229 */             super.setGraphic(null);
/*  230 */           } else if ((paramAnonymous2Object instanceof Node)) {
/*  231 */             super.setText(null);
/*  232 */             super.setGraphic((Node)paramAnonymous2Object);
/*      */           } else {
/*  234 */             super.setText(paramAnonymous2Object.toString());
/*  235 */             super.setGraphic(null);
/*      */           }
/*      */         }
/*      */       };
/*      */     }
/*  219 */   };
/*      */ 
/*  249 */   public static final Comparator DEFAULT_COMPARATOR = new Comparator() {
/*      */     public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2) {
/*  251 */       if ((paramAnonymousObject1 == null) && (paramAnonymousObject2 == null)) return 0;
/*  252 */       if (paramAnonymousObject1 == null) return -1;
/*  253 */       if (paramAnonymousObject2 == null) return 1;
/*      */ 
/*  255 */       if ((paramAnonymousObject1 instanceof Comparable)) {
/*  256 */         return ((Comparable)paramAnonymousObject1).compareTo(paramAnonymousObject2);
/*      */       }
/*      */ 
/*  259 */       return Collator.getInstance().compare(paramAnonymousObject1.toString(), paramAnonymousObject2.toString());
/*      */     }
/*  249 */   };
/*      */ 
/*  321 */   private EventHandler<CellEditEvent<S, T>> DEFAULT_EDIT_COMMIT_HANDLER = new Object() {
/*      */     public void handle(TableColumn.CellEditEvent<S, T> paramAnonymousCellEditEvent) {
/*  323 */       int i = paramAnonymousCellEditEvent.getTablePosition().getRow();
/*  324 */       ObservableList localObservableList = paramAnonymousCellEditEvent.getTableView().getItems();
/*  325 */       if ((localObservableList == null) || (i < 0) || (i >= localObservableList.size())) return;
/*  326 */       Object localObject = localObservableList.get(i);
/*  327 */       ObservableValue localObservableValue = TableColumn.this.getCellObservableValue(localObject);
/*      */ 
/*  329 */       if ((localObservableValue instanceof WritableValue))
/*  330 */         ((WritableValue)localObservableValue).setValue(paramAnonymousCellEditEvent.getNewValue());
/*      */     }
/*  321 */   };
/*      */ 
/*  335 */   private ListChangeListener columnsListener = new ListChangeListener() {
/*      */     public void onChanged(ListChangeListener.Change<? extends TableColumn<S, ?>> paramAnonymousChange) {
/*  337 */       while (paramAnonymousChange.next())
/*      */       {
/*  339 */         for (Iterator localIterator1 = paramAnonymousChange.getRemoved().iterator(); localIterator1.hasNext(); ) { localTableColumn1 = (TableColumn)localIterator1.next();
/*      */ 
/*  347 */           if (!TableColumn.this.getColumns().contains(localTableColumn1))
/*      */           {
/*  349 */             localTableColumn1.setTableView(null);
/*  350 */             localTableColumn1.setParentColumn(null);
/*      */           }
/*      */         }
/*  352 */         TableColumn localTableColumn1;
/*  352 */         for (localIterator1 = paramAnonymousChange.getAddedSubList().iterator(); localIterator1.hasNext(); ) { localTableColumn1 = (TableColumn)localIterator1.next();
/*  353 */           localTableColumn1.setTableView(TableColumn.this.getTableView());
/*  354 */           localTableColumn1.setVisible(TableColumn.this.isVisible());
/*      */         }
/*      */ 
/*  357 */         if (!TableColumn.this.getColumns().isEmpty())
/*      */         {
/*  361 */           double d1 = 0.0D;
/*  362 */           double d2 = 0.0D;
/*  363 */           double d3 = 0.0D;
/*      */ 
/*  365 */           for (TableColumn localTableColumn2 : TableColumn.this.getColumns()) {
/*  366 */             localTableColumn2.setParentColumn(TableColumn.this);
/*      */ 
/*  368 */             d1 += localTableColumn2.getMinWidth();
/*  369 */             d2 += localTableColumn2.getPrefWidth();
/*  370 */             d3 += localTableColumn2.getMaxWidth();
/*      */           }
/*      */ 
/*  373 */           TableColumn.this.setMinWidth(d1);
/*  374 */           TableColumn.this.setPrefWidth(d2);
/*  375 */           TableColumn.this.setMaxWidth(d3);
/*      */         }
/*      */       }
/*      */     }
/*  335 */   };
/*      */ 
/*  381 */   private WeakListChangeListener weakColumnsListener = new WeakListChangeListener(this.columnsListener);
/*      */ 
/*  392 */   private final ObservableList<TableColumn<S, ?>> columns = FXCollections.observableArrayList();
/*      */ 
/*  396 */   private final EventHandlerManager eventHandlerManager = new EventHandlerManager(this);
/*      */ 
/*  411 */   private ReadOnlyObjectWrapper<TableView<S>> tableView = new ReadOnlyObjectWrapper(this, "tableView");
/*      */ 
/*  423 */   private StringProperty text = new SimpleStringProperty(this, "text", "");
/*      */ 
/*  434 */   private BooleanProperty visible = new BooleanPropertyBase(true)
/*      */   {
/*      */     protected void invalidated()
/*      */     {
/*  448 */       for (TableColumn localTableColumn : TableColumn.this.getColumns())
/*  449 */         localTableColumn.setVisible(TableColumn.this.isVisible());
/*      */     }
/*      */ 
/*      */     public Object getBean()
/*      */     {
/*  455 */       return TableColumn.this;
/*      */     }
/*      */ 
/*      */     public String getName()
/*      */     {
/*  460 */       return "visible";
/*      */     }
/*  434 */   };
/*      */   private ReadOnlyObjectWrapper<TableColumn<S, ?>> parentColumn;
/*      */   private ObjectProperty<ContextMenu> contextMenu;
/*      */   private ObjectProperty<Callback<CellDataFeatures<S, T>, ObservableValue<T>>> cellValueFactory;
/*  570 */   private final ObjectProperty<Callback<TableColumn<S, T>, TableCell<S, T>>> cellFactory = new SimpleObjectProperty(this, "cellFactory", DEFAULT_CELL_FACTORY);
/*      */   private StringProperty id;
/*      */   private StringProperty style;
/*  652 */   private final ObservableList<String> styleClass = FXCollections.observableArrayList();
/*      */   private ObjectProperty<Node> graphic;
/*  704 */   private ObjectProperty<Node> sortNode = new SimpleObjectProperty(this, "sortNode");
/*      */ 
/*  744 */   private ReadOnlyDoubleWrapper width = new ReadOnlyDoubleWrapper(this, "width", 80.0D);
/*      */   private DoubleProperty minWidth;
/*  788 */   private final DoubleProperty prefWidth = new DoublePropertyBase(80.0D) {
/*      */     protected void invalidated() {
/*  790 */       TableColumn.this.impl_setWidth(TableColumn.this.getPrefWidth());
/*      */     }
/*      */ 
/*      */     public Object getBean()
/*      */     {
/*  795 */       return TableColumn.this;
/*      */     }
/*      */ 
/*      */     public String getName()
/*      */     {
/*  800 */       return "prefWidth";
/*      */     }
/*  788 */   };
/*      */ 
/*  814 */   private DoubleProperty maxWidth = new DoublePropertyBase(5000.0D) {
/*      */     protected void invalidated() {
/*  816 */       TableColumn.this.impl_setWidth(TableColumn.this.getWidth());
/*      */     }
/*      */ 
/*      */     public Object getBean()
/*      */     {
/*  821 */       return TableColumn.this;
/*      */     }
/*      */ 
/*      */     public String getName()
/*      */     {
/*  826 */       return "maxWidth";
/*      */     }
/*  814 */   };
/*      */   private BooleanProperty resizable;
/*      */   private ObjectProperty<SortType> sortType;
/*      */   private BooleanProperty sortable;
/*      */   private ObjectProperty<Comparator<T>> comparator;
/*      */   private BooleanProperty editable;
/*      */   private ObjectProperty<EventHandler<CellEditEvent<S, T>>> onEditStart;
/*  977 */   private ObjectProperty<EventHandler<CellEditEvent<S, T>>> onEditCommit = new ObjectPropertyBase()
/*      */   {
/*      */     protected void invalidated() {
/*  980 */       TableColumn.this.eventHandlerManager.setEventHandler(TableColumn.editCommitEvent(), (EventHandler)get());
/*      */     }
/*      */ 
/*      */     public Object getBean() {
/*  984 */       return TableColumn.this;
/*      */     }
/*      */ 
/*      */     public String getName() {
/*  988 */       return "onEditCommit";
/*      */     }
/*  977 */   };
/*      */   private ObjectProperty<EventHandler<CellEditEvent<S, T>>> onEditCancel;
/* 1040 */   private static final Object USER_DATA_KEY = new Object();
/*      */   private ObservableMap<Object, Object> properties;
/*      */   private static final String DEFAULT_STYLE_CLASS = "table-column";
/*      */ 
/*      */   @Deprecated
/*      */   protected Styleable styleable;
/*      */ 
/*      */   public static <S, T> EventType<CellEditEvent<S, T>> editAnyEvent()
/*      */   {
/*  169 */     return EDIT_ANY_EVENT;
/*      */   }
/*      */ 
/*      */   public static <S, T> EventType<CellEditEvent<S, T>> editStartEvent()
/*      */   {
/*  181 */     return EDIT_START_EVENT;
/*      */   }
/*      */ 
/*      */   public static <S, T> EventType<CellEditEvent<S, T>> editCancelEvent()
/*      */   {
/*  192 */     return EDIT_CANCEL_EVENT;
/*      */   }
/*      */ 
/*      */   public static <S, T> EventType<CellEditEvent<S, T>> editCommitEvent()
/*      */   {
/*  204 */     return EDIT_COMMIT_EVENT;
/*      */   }
/*      */ 
/*      */   public TableColumn()
/*      */   {
/*  276 */     getStyleClass().add("table-column");
/*      */ 
/*  278 */     setOnEditCommit(this.DEFAULT_EDIT_COMMIT_HANDLER);
/*      */ 
/*  283 */     getColumns().addListener(this.weakColumnsListener);
/*      */ 
/*  285 */     tableViewProperty().addListener(new InvalidationListener()
/*      */     {
/*      */       public void invalidated(Observable paramAnonymousObservable)
/*      */       {
/*  289 */         for (TableColumn localTableColumn : TableColumn.this.getColumns())
/*  290 */           localTableColumn.setTableView(TableColumn.this.getTableView());
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public TableColumn(String paramString)
/*      */   {
/*  309 */     this();
/*  310 */     setText(paramString);
/*      */   }
/*      */ 
/*      */   public final ReadOnlyObjectProperty<TableView<S>> tableViewProperty()
/*      */   {
/*  413 */     return this.tableView.getReadOnlyProperty();
/*      */   }
/*  415 */   final void setTableView(TableView<S> paramTableView) { this.tableView.set(paramTableView); } 
/*  416 */   public final TableView<S> getTableView() { return (TableView)this.tableView.get(); }
/*      */ 
/*      */ 
/*      */   public final StringProperty textProperty()
/*      */   {
/*  424 */     return this.text; } 
/*  425 */   public final void setText(String paramString) { this.text.set(paramString); } 
/*  426 */   public final String getText() { return (String)this.text.get(); }
/*      */ 
/*      */ 
/*      */   public final void setVisible(boolean paramBoolean)
/*      */   {
/*  463 */     visibleProperty().set(paramBoolean); } 
/*  464 */   public final boolean isVisible() { return this.visible.get(); } 
/*  465 */   public final BooleanProperty visibleProperty() { return this.visible; }
/*      */ 
/*      */ 
/*      */   private void setParentColumn(TableColumn<S, ?> paramTableColumn)
/*      */   {
/*  476 */     parentColumnPropertyImpl().set(paramTableColumn);
/*      */   }
/*  478 */   public final TableColumn<S, ?> getParentColumn() { return this.parentColumn == null ? null : (TableColumn)this.parentColumn.get(); }
/*      */ 
/*      */   public final ReadOnlyObjectProperty<TableColumn<S, ?>> parentColumnProperty()
/*      */   {
/*  482 */     return parentColumnPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyObjectWrapper<TableColumn<S, ?>> parentColumnPropertyImpl() {
/*  486 */     if (this.parentColumn == null) {
/*  487 */       this.parentColumn = new ReadOnlyObjectWrapper(this, "parentColumn");
/*      */     }
/*  489 */     return this.parentColumn;
/*      */   }
/*      */ 
/*      */   public final void setContextMenu(ContextMenu paramContextMenu)
/*      */   {
/*  499 */     contextMenuProperty().set(paramContextMenu); } 
/*  500 */   public final ContextMenu getContextMenu() { return this.contextMenu == null ? null : (ContextMenu)this.contextMenu.get(); } 
/*      */   public final ObjectProperty<ContextMenu> contextMenuProperty() {
/*  502 */     if (this.contextMenu == null) {
/*  503 */       this.contextMenu = new SimpleObjectProperty(this, "contextMenu");
/*      */     }
/*  505 */     return this.contextMenu;
/*      */   }
/*      */ 
/*      */   public final void setCellValueFactory(Callback<CellDataFeatures<S, T>, ObservableValue<T>> paramCallback)
/*      */   {
/*  544 */     cellValueFactoryProperty().set(paramCallback);
/*      */   }
/*      */   public final Callback<CellDataFeatures<S, T>, ObservableValue<T>> getCellValueFactory() {
/*  547 */     return this.cellValueFactory == null ? null : (Callback)this.cellValueFactory.get();
/*      */   }
/*      */   public final ObjectProperty<Callback<CellDataFeatures<S, T>, ObservableValue<T>>> cellValueFactoryProperty() {
/*  550 */     if (this.cellValueFactory == null) {
/*  551 */       this.cellValueFactory = new SimpleObjectProperty(this, "cellValueFactory");
/*      */     }
/*  553 */     return this.cellValueFactory;
/*      */   }
/*      */ 
/*      */   public final void setCellFactory(Callback<TableColumn<S, T>, TableCell<S, T>> paramCallback)
/*      */   {
/*  574 */     this.cellFactory.set(paramCallback);
/*      */   }
/*      */ 
/*      */   public final Callback<TableColumn<S, T>, TableCell<S, T>> getCellFactory() {
/*  578 */     return (Callback)this.cellFactory.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Callback<TableColumn<S, T>, TableCell<S, T>>> cellFactoryProperty() {
/*  582 */     return this.cellFactory;
/*      */   }
/*      */ 
/*      */   public final void setId(String paramString)
/*      */   {
/*  595 */     idProperty().set(paramString);
/*      */   }
/*      */ 
/*      */   public final String getId()
/*      */   {
/*  603 */     return this.id == null ? null : (String)this.id.get();
/*      */   }
/*      */ 
/*      */   public final StringProperty idProperty()
/*      */   {
/*  610 */     if (this.id == null) {
/*  611 */       this.id = new SimpleStringProperty(this, "id");
/*      */     }
/*  613 */     return this.id;
/*      */   }
/*      */ 
/*      */   public final void setStyle(String paramString)
/*      */   {
/*  629 */     styleProperty().set(paramString);
/*      */   }
/*      */ 
/*      */   public final String getStyle()
/*      */   {
/*  637 */     return this.style == null ? null : (String)this.style.get();
/*      */   }
/*      */ 
/*      */   public final StringProperty styleProperty()
/*      */   {
/*  644 */     if (this.style == null) {
/*  645 */       this.style = new SimpleStringProperty(this, "style");
/*      */     }
/*  647 */     return this.style;
/*      */   }
/*      */ 
/*      */   public ObservableList<String> getStyleClass()
/*      */   {
/*  663 */     return this.styleClass;
/*      */   }
/*      */ 
/*      */   public final void setGraphic(Node paramNode)
/*      */   {
/*  676 */     graphicProperty().set(paramNode);
/*      */   }
/*      */ 
/*      */   public final Node getGraphic()
/*      */   {
/*  686 */     return this.graphic == null ? null : (Node)this.graphic.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Node> graphicProperty()
/*      */   {
/*  696 */     if (this.graphic == null) {
/*  697 */       this.graphic = new SimpleObjectProperty(this, "graphic");
/*      */     }
/*  699 */     return this.graphic;
/*      */   }
/*      */ 
/*      */   public final void setSortNode(Node paramNode)
/*      */   {
/*  716 */     sortNodeProperty().set(paramNode);
/*      */   }
/*      */ 
/*      */   public final Node getSortNode()
/*      */   {
/*  722 */     return (Node)this.sortNode.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Node> sortNodeProperty()
/*      */   {
/*  730 */     return this.sortNode;
/*      */   }
/*      */ 
/*      */   public final ReadOnlyDoubleProperty widthProperty()
/*      */   {
/*  741 */     return this.width.getReadOnlyProperty(); } 
/*  742 */   public final double getWidth() { return this.width.get(); } 
/*  743 */   private void setWidth(double paramDouble) { this.width.set(paramDouble); }
/*      */ 
/*      */ 
/*      */   public final void setMinWidth(double paramDouble)
/*      */   {
/*  749 */     minWidthProperty().set(paramDouble); } 
/*  750 */   public final double getMinWidth() { return this.minWidth == null ? 10.0D : this.minWidth.get(); }
/*      */ 
/*      */ 
/*      */   public final DoubleProperty minWidthProperty()
/*      */   {
/*  756 */     if (this.minWidth == null) {
/*  757 */       this.minWidth = new DoublePropertyBase(10.0D) {
/*      */         protected void invalidated() {
/*  759 */           if (TableColumn.this.getMinWidth() < 0.0D) {
/*  760 */             TableColumn.this.setMinWidth(0.0D);
/*      */           }
/*      */ 
/*  763 */           TableColumn.this.impl_setWidth(TableColumn.this.getWidth());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  768 */           return TableColumn.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  773 */           return "minWidth";
/*      */         }
/*      */       };
/*      */     }
/*  777 */     return this.minWidth;
/*      */   }
/*      */ 
/*      */   public final DoubleProperty prefWidthProperty()
/*      */   {
/*  785 */     return this.prefWidth; } 
/*  786 */   public final void setPrefWidth(double paramDouble) { prefWidthProperty().set(paramDouble); } 
/*  787 */   public final double getPrefWidth() { return this.prefWidth.get(); }
/*      */ 
/*      */ 
/*      */   public final DoubleProperty maxWidthProperty()
/*      */   {
/*  811 */     return this.maxWidth; } 
/*  812 */   public final void setMaxWidth(double paramDouble) { maxWidthProperty().set(paramDouble); } 
/*  813 */   public final double getMaxWidth() { return this.maxWidth.get(); }
/*      */ 
/*      */ 
/*      */   public final BooleanProperty resizableProperty()
/*      */   {
/*  838 */     if (this.resizable == null) {
/*  839 */       this.resizable = new SimpleBooleanProperty(this, "resizable", true);
/*      */     }
/*  841 */     return this.resizable;
/*      */   }
/*      */   public final void setResizable(boolean paramBoolean) {
/*  844 */     resizableProperty().set(paramBoolean);
/*      */   }
/*      */   public final boolean isResizable() {
/*  847 */     return this.resizable == null ? true : this.resizable.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<SortType> sortTypeProperty()
/*      */   {
/*  862 */     if (this.sortType == null) {
/*  863 */       this.sortType = new SimpleObjectProperty(this, "sortType", SortType.ASCENDING);
/*      */     }
/*  865 */     return this.sortType;
/*      */   }
/*      */   public final void setSortType(SortType paramSortType) {
/*  868 */     sortTypeProperty().set(paramSortType);
/*      */   }
/*      */   public final SortType getSortType() {
/*  871 */     return this.sortType == null ? SortType.ASCENDING : (SortType)this.sortType.get();
/*      */   }
/*      */ 
/*      */   public final BooleanProperty sortableProperty()
/*      */   {
/*  888 */     if (this.sortable == null) {
/*  889 */       this.sortable = new SimpleBooleanProperty(this, "sortable", true);
/*      */     }
/*  891 */     return this.sortable;
/*      */   }
/*      */   public final void setSortable(boolean paramBoolean) {
/*  894 */     sortableProperty().set(paramBoolean);
/*      */   }
/*      */   public final boolean isSortable() {
/*  897 */     return this.sortable == null ? true : this.sortable.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Comparator<T>> comparatorProperty()
/*      */   {
/*  909 */     if (this.comparator == null) {
/*  910 */       this.comparator = new SimpleObjectProperty(this, "comparator", DEFAULT_COMPARATOR);
/*      */     }
/*  912 */     return this.comparator;
/*      */   }
/*      */   public final void setComparator(Comparator<T> paramComparator) {
/*  915 */     comparatorProperty().set(paramComparator);
/*      */   }
/*      */   public final Comparator<T> getComparator() {
/*  918 */     return this.comparator == null ? DEFAULT_COMPARATOR : (Comparator)this.comparator.get();
/*      */   }
/*      */ 
/*      */   public final void setEditable(boolean paramBoolean)
/*      */   {
/*  925 */     editableProperty().set(paramBoolean);
/*      */   }
/*      */   public final boolean isEditable() {
/*  928 */     return this.editable == null ? true : this.editable.get();
/*      */   }
/*      */ 
/*      */   public final BooleanProperty editableProperty()
/*      */   {
/*  935 */     if (this.editable == null) {
/*  936 */       this.editable = new SimpleBooleanProperty(this, "editable", true);
/*      */     }
/*  938 */     return this.editable;
/*      */   }
/*      */ 
/*      */   public final void setOnEditStart(EventHandler<CellEditEvent<S, T>> paramEventHandler)
/*      */   {
/*  945 */     onEditStartProperty().set(paramEventHandler);
/*      */   }
/*      */   public final EventHandler<CellEditEvent<S, T>> getOnEditStart() {
/*  948 */     return this.onEditStart == null ? null : (EventHandler)this.onEditStart.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<CellEditEvent<S, T>>> onEditStartProperty()
/*      */   {
/*  955 */     if (this.onEditStart == null) {
/*  956 */       this.onEditStart = new ObjectPropertyBase() {
/*      */         protected void invalidated() {
/*  958 */           TableColumn.this.eventHandlerManager.setEventHandler(TableColumn.editStartEvent(), (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  963 */           return TableColumn.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  968 */           return "onEditStart";
/*      */         }
/*      */       };
/*      */     }
/*  972 */     return this.onEditStart;
/*      */   }
/*      */ 
/*      */   public final void setOnEditCommit(EventHandler<CellEditEvent<S, T>> paramEventHandler)
/*      */   {
/*  992 */     this.onEditCommit.set(paramEventHandler);
/*      */   }
/*      */   public final EventHandler<CellEditEvent<S, T>> getOnEditCommit() {
/*  995 */     return (EventHandler)this.onEditCommit.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<CellEditEvent<S, T>>> onEditCommitProperty()
/*      */   {
/* 1002 */     return this.onEditCommit;
/*      */   }
/*      */ 
/*      */   public final void setOnEditCancel(EventHandler<CellEditEvent<S, T>> paramEventHandler)
/*      */   {
/* 1009 */     onEditCancelProperty().set(paramEventHandler);
/*      */   }
/*      */   public final EventHandler<CellEditEvent<S, T>> getOnEditCancel() {
/* 1012 */     return this.onEditCancel == null ? null : (EventHandler)this.onEditCancel.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<CellEditEvent<S, T>>> onEditCancelProperty()
/*      */   {
/* 1018 */     if (this.onEditCancel == null) {
/* 1019 */       this.onEditCancel = new ObjectPropertyBase() {
/*      */         protected void invalidated() {
/* 1021 */           TableColumn.this.eventHandlerManager.setEventHandler(TableColumn.editCancelEvent(), (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 1026 */           return TableColumn.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 1031 */           return "onEditCancel";
/*      */         }
/*      */       };
/*      */     }
/* 1035 */     return this.onEditCancel;
/*      */   }
/*      */ 
/*      */   public final ObservableMap<Object, Object> getProperties()
/*      */   {
/* 1054 */     if (this.properties == null) {
/* 1055 */       this.properties = FXCollections.observableMap(new HashMap());
/*      */     }
/* 1057 */     return this.properties;
/*      */   }
/*      */ 
/*      */   public boolean hasProperties()
/*      */   {
/* 1066 */     return this.properties != null;
/*      */   }
/*      */ 
/*      */   public void setUserData(Object paramObject)
/*      */   {
/* 1082 */     getProperties().put(USER_DATA_KEY, paramObject);
/*      */   }
/*      */ 
/*      */   public Object getUserData()
/*      */   {
/* 1094 */     return getProperties().get(USER_DATA_KEY);
/*      */   }
/*      */ 
/*      */   public final ObservableList<TableColumn<S, ?>> getColumns()
/*      */   {
/* 1121 */     return this.columns;
/*      */   }
/*      */ 
/*      */   public final T getCellData(int paramInt)
/*      */   {
/* 1133 */     ObservableValue localObservableValue = getCellObservableValue(paramInt);
/* 1134 */     return localObservableValue == null ? null : localObservableValue.getValue();
/*      */   }
/*      */ 
/*      */   public final T getCellData(S paramS)
/*      */   {
/* 1145 */     ObservableValue localObservableValue = getCellObservableValue(paramS);
/* 1146 */     return localObservableValue == null ? null : localObservableValue.getValue();
/*      */   }
/*      */ 
/*      */   public final ObservableValue<T> getCellObservableValue(int paramInt)
/*      */   {
/* 1168 */     if (paramInt < 0) return null;
/*      */ 
/* 1171 */     TableView localTableView = getTableView();
/* 1172 */     if ((localTableView == null) || (localTableView.getItems() == null)) return null;
/*      */ 
/* 1175 */     ObservableList localObservableList = localTableView.getItems();
/* 1176 */     if (paramInt >= localObservableList.size()) return null;
/*      */ 
/* 1178 */     Object localObject = localObservableList.get(paramInt);
/* 1179 */     return getCellObservableValue(localObject);
/*      */   }
/*      */ 
/*      */   public final ObservableValue<T> getCellObservableValue(S paramS)
/*      */   {
/* 1200 */     Callback localCallback = getCellValueFactory();
/* 1201 */     if (localCallback == null) return null;
/*      */ 
/* 1204 */     TableView localTableView = getTableView();
/* 1205 */     if (localTableView == null) return null;
/*      */ 
/* 1208 */     CellDataFeatures localCellDataFeatures = new CellDataFeatures(localTableView, this, paramS);
/* 1209 */     return (ObservableValue)localCallback.call(localCellDataFeatures);
/*      */   }
/*      */ 
/*      */   public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain)
/*      */   {
/* 1214 */     return paramEventDispatchChain.prepend(this.eventHandlerManager);
/*      */   }
/*      */ 
/*      */   public <E extends Event> void addEventHandler(EventType<E> paramEventType, EventHandler<E> paramEventHandler)
/*      */   {
/* 1228 */     this.eventHandlerManager.addEventHandler(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   public <E extends Event> void removeEventHandler(EventType<E> paramEventType, EventHandler<E> paramEventHandler)
/*      */   {
/* 1242 */     this.eventHandlerManager.removeEventHandler(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_setWidth(double paramDouble)
/*      */   {
/* 1259 */     setWidth(Utils.boundedSize(paramDouble, getMinWidth(), getMaxWidth()));
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public Styleable impl_getStyleable()
/*      */   {
/* 1290 */     if (this.styleable == null) {
/* 1291 */       this.styleable = new Styleable()
/*      */       {
/*      */         public String getId()
/*      */         {
/* 1295 */           return TableColumn.this.getId();
/*      */         }
/*      */ 
/*      */         public List<String> getStyleClass()
/*      */         {
/* 1300 */           return TableColumn.this.getStyleClass();
/*      */         }
/*      */ 
/*      */         public String getStyle()
/*      */         {
/* 1305 */           return TableColumn.this.getStyle();
/*      */         }
/*      */ 
/*      */         public Styleable getStyleableParent()
/*      */         {
/* 1310 */           return TableColumn.this.getTableView() == null ? null : TableColumn.this.getTableView().impl_getStyleable();
/*      */         }
/*      */ 
/*      */         public List<StyleableProperty> getStyleableProperties()
/*      */         {
/* 1316 */           return Collections.EMPTY_LIST;
/*      */         }
/*      */ 
/*      */         public Node getNode()
/*      */         {
/* 1321 */           if (!(TableColumn.this.getTableView().getSkin() instanceof TableViewSkin)) return null;
/* 1322 */           TableViewSkin localTableViewSkin = (TableViewSkin)TableColumn.this.getTableView().getSkin();
/*      */ 
/* 1324 */           TableHeaderRow localTableHeaderRow = localTableViewSkin.getTableHeaderRow();
/* 1325 */           NestedTableColumnHeader localNestedTableColumnHeader = localTableHeaderRow.getRootHeader();
/*      */ 
/* 1328 */           return scan(localNestedTableColumnHeader);
/*      */         }
/*      */ 
/*      */         private TableColumnHeader scan(TableColumnHeader paramAnonymousTableColumnHeader)
/*      */         {
/* 1333 */           if (TableColumn.this.equals(paramAnonymousTableColumnHeader.getTableColumn())) {
/* 1334 */             return paramAnonymousTableColumnHeader;
/*      */           }
/*      */ 
/* 1337 */           if ((paramAnonymousTableColumnHeader instanceof NestedTableColumnHeader)) {
/* 1338 */             NestedTableColumnHeader localNestedTableColumnHeader = (NestedTableColumnHeader)paramAnonymousTableColumnHeader;
/* 1339 */             for (int i = 0; i < localNestedTableColumnHeader.getColumnHeaders().size(); i++) {
/* 1340 */               TableColumnHeader localTableColumnHeader = scan((TableColumnHeader)localNestedTableColumnHeader.getColumnHeaders().get(i));
/* 1341 */               if (localTableColumnHeader != null) {
/* 1342 */                 return localTableColumnHeader;
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/* 1347 */           return null;
/*      */         }
/*      */       };
/*      */     }
/* 1351 */     return this.styleable;
/*      */   }
/*      */ 
/*      */   public static class CellEditEvent<S, T> extends Event
/*      */   {
/*      */     private final T newValue;
/*      */     private final transient TablePosition<S, T> pos;
/*      */ 
/*      */     public CellEditEvent(TableView<S> paramTableView, TablePosition<S, T> paramTablePosition, EventType<CellEditEvent> paramEventType, T paramT)
/*      */     {
/* 1456 */       super(Event.NULL_SOURCE_TARGET, paramEventType);
/*      */ 
/* 1458 */       if (paramTableView == null) {
/* 1459 */         throw new NullPointerException("TableView can not be null");
/*      */       }
/* 1461 */       if (paramTablePosition == null) {
/* 1462 */         throw new NullPointerException("TablePosition can not be null");
/*      */       }
/* 1464 */       this.pos = paramTablePosition;
/* 1465 */       this.newValue = paramT;
/*      */     }
/*      */ 
/*      */     public TableView<S> getTableView()
/*      */     {
/* 1473 */       return this.pos.getTableView();
/*      */     }
/*      */ 
/*      */     public TableColumn<S, T> getTableColumn()
/*      */     {
/* 1482 */       return this.pos.getTableColumn();
/*      */     }
/*      */ 
/*      */     public TablePosition<S, T> getTablePosition()
/*      */     {
/* 1490 */       return this.pos;
/*      */     }
/*      */ 
/*      */     public T getNewValue()
/*      */     {
/* 1502 */       return this.newValue;
/*      */     }
/*      */ 
/*      */     public T getOldValue()
/*      */     {
/* 1514 */       Object localObject = getRowValue();
/* 1515 */       if ((localObject == null) || (this.pos.getTableColumn() == null)) {
/* 1516 */         return null;
/*      */       }
/*      */ 
/* 1520 */       return this.pos.getTableColumn().getCellData(localObject);
/*      */     }
/*      */ 
/*      */     public S getRowValue()
/*      */     {
/* 1530 */       ObservableList localObservableList = getTableView().getItems();
/* 1531 */       if (localObservableList == null) return null;
/*      */ 
/* 1533 */       int i = this.pos.getRow();
/* 1534 */       if ((i < 0) || (i >= localObservableList.size())) return null;
/*      */ 
/* 1536 */       return localObservableList.get(i);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class CellDataFeatures<S, T>
/*      */   {
/*      */     private final TableView<S> tableView;
/*      */     private final TableColumn<S, T> tableColumn;
/*      */     private final S value;
/*      */ 
/*      */     public CellDataFeatures(TableView<S> paramTableView, TableColumn<S, T> paramTableColumn, S paramS)
/*      */     {
/* 1403 */       this.tableView = paramTableView;
/* 1404 */       this.tableColumn = paramTableColumn;
/* 1405 */       this.value = paramS;
/*      */     }
/*      */ 
/*      */     public S getValue()
/*      */     {
/* 1412 */       return this.value;
/*      */     }
/*      */ 
/*      */     public TableColumn<S, T> getTableColumn()
/*      */     {
/* 1419 */       return this.tableColumn;
/*      */     }
/*      */ 
/*      */     public TableView<S> getTableView()
/*      */     {
/* 1426 */       return this.tableView;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static enum SortType
/*      */   {
/* 1370 */     ASCENDING, 
/*      */ 
/* 1375 */     DESCENDING;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TableColumn
 * JD-Core Version:    0.6.2
 */