/*      */ package javafx.scene.control;
/*      */ 
/*      */ import com.sun.javafx.collections.MappingChange;
/*      */ import com.sun.javafx.collections.MappingChange.Map;
/*      */ import com.sun.javafx.collections.NonIterableChange.SimpleAddChange;
/*      */ import com.sun.javafx.collections.transformation.SortableList;
/*      */ import com.sun.javafx.collections.transformation.SortableList.SortMode;
/*      */ import com.sun.javafx.collections.transformation.TransformationList;
/*      */ import com.sun.javafx.css.StyleManager;
/*      */ import com.sun.javafx.scene.control.ReadOnlyUnbackedObservableList;
/*      */ import com.sun.javafx.scene.control.TableColumnComparator;
/*      */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*      */ import com.sun.javafx.scene.control.skin.TableViewSkin;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javafx.beans.DefaultProperty;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.WeakInvalidationListener;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.BooleanPropertyBase;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ObjectPropertyBase;
/*      */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*      */ import javafx.beans.property.SimpleBooleanProperty;
/*      */ import javafx.beans.property.SimpleObjectProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.beans.value.WeakChangeListener;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ListChangeListener;
/*      */ import javafx.collections.ListChangeListener.Change;
/*      */ import javafx.collections.MapChangeListener;
/*      */ import javafx.collections.MapChangeListener.Change;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.collections.ObservableMap;
/*      */ import javafx.scene.Node;
/*      */ import javafx.util.Callback;
/*      */ 
/*      */ @DefaultProperty("items")
/*      */ public class TableView<S> extends Control
/*      */ {
/*      */   private static final String SET_CONTENT_WIDTH = "TableView.contentWidth";
/*      */   private static final String REFRESH = "TableView.refresh";
/*  320 */   public static final Callback<ResizeFeatures, Boolean> UNCONSTRAINED_RESIZE_POLICY = new Callback() {
/*      */     public String toString() {
/*  322 */       return "unconstrained-resize";
/*      */     }
/*      */ 
/*      */     public Boolean call(TableView.ResizeFeatures paramAnonymousResizeFeatures) {
/*  326 */       double d = TableView.resize(paramAnonymousResizeFeatures.getColumn(), paramAnonymousResizeFeatures.getDelta().doubleValue());
/*  327 */       return Boolean.valueOf(Double.compare(d, 0.0D) == 0);
/*      */     }
/*  320 */   };
/*      */ 
/*  343 */   public static final Callback<ResizeFeatures, Boolean> CONSTRAINED_RESIZE_POLICY = new Callback()
/*      */   {
/*  345 */     private boolean isFirstRun = true;
/*      */ 
/*      */     public String toString() {
/*  348 */       return "constrained-resize";
/*      */     }
/*      */ 
/*      */     public Boolean call(TableView.ResizeFeatures paramAnonymousResizeFeatures) {
/*  352 */       TableView localTableView = paramAnonymousResizeFeatures.getTable();
/*  353 */       TableColumn localTableColumn1 = paramAnonymousResizeFeatures.getColumn();
/*  354 */       double d1 = paramAnonymousResizeFeatures.getDelta().doubleValue();
/*      */ 
/*  368 */       double d3 = 0.0D;
/*  369 */       double d4 = 0.0D;
/*      */ 
/*  371 */       double d5 = localTableView.contentWidth;
/*  372 */       if (d5 == 0.0D) return Boolean.valueOf(false);
/*      */ 
/*  379 */       double d6 = 0.0D;
/*  380 */       for (Iterator localIterator = localTableView.getVisibleLeafColumns().iterator(); localIterator.hasNext(); ) { localTableColumn3 = (TableColumn)localIterator.next();
/*  381 */         d6 += localTableColumn3.getWidth();
/*      */       }
/*      */       TableColumn localTableColumn3;
/*  384 */       if (Math.abs(d6 - d5) > 1.0D) {
/*  385 */         i = d6 > d5 ? 1 : 0;
/*  386 */         double d2 = d5;
/*      */ 
/*  388 */         if (this.isFirstRun)
/*      */         {
/*  391 */           for (localIterator = localTableView.getVisibleLeafColumns().iterator(); localIterator.hasNext(); ) { localTableColumn3 = (TableColumn)localIterator.next();
/*  392 */             d3 += localTableColumn3.getMinWidth();
/*  393 */             d4 += localTableColumn3.getMaxWidth();
/*      */           }
/*      */ 
/*  397 */           d4 = d4 == (-1.0D / 0.0D) ? 4.9E-324D : d4 == (1.0D / 0.0D) ? 1.7976931348623157E+308D : d4;
/*      */ 
/*  401 */           for (localIterator = localTableView.getVisibleLeafColumns().iterator(); localIterator.hasNext(); ) { localTableColumn3 = (TableColumn)localIterator.next();
/*  402 */             double d8 = localTableColumn3.getMinWidth();
/*  403 */             double d10 = localTableColumn3.getMaxWidth();
/*      */             double d11;
/*  409 */             if (d3 == d4) {
/*  410 */               d11 = d8;
/*      */             } else {
/*  412 */               d12 = (d2 - d3) / (d4 - d3);
/*  413 */               d11 = Math.round(d8 + d12 * (d10 - d8));
/*      */             }
/*      */ 
/*  416 */             double d12 = TableView.resize(localTableColumn3, d11 - localTableColumn3.getWidth());
/*      */ 
/*  418 */             d2 -= d11 + d12;
/*  419 */             d3 -= d8;
/*  420 */             d4 -= d10;
/*      */           }
/*      */ 
/*  423 */           this.isFirstRun = false;
/*      */         } else {
/*  425 */           double d7 = d5 - d6;
/*  426 */           ObservableList localObservableList = localTableView.getVisibleLeafColumns();
/*  427 */           TableView.resizeColumns(localObservableList, d7);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  439 */       if (localTableColumn1 == null) {
/*  440 */         return Boolean.valueOf(false);
/*      */       }
/*      */ 
/*  448 */       int i = d1 < 0.0D ? 1 : 0;
/*      */ 
/*  453 */       TableColumn localTableColumn2 = localTableColumn1;
/*  454 */       while (localTableColumn2.getColumns().size() > 0) {
/*  455 */         localTableColumn2 = (TableColumn)localTableColumn2.getColumns().get(0);
/*      */       }
/*      */ 
/*  458 */       int j = localTableView.getVisibleLeafColumns().indexOf(localTableColumn2);
/*  459 */       int k = localTableView.getVisibleLeafColumns().size() - 1;
/*      */ 
/*  473 */       double d9 = d1;
/*  474 */       while ((k > j) && (d9 != 0.0D)) {
/*  475 */         TableColumn localTableColumn4 = (TableColumn)localTableView.getVisibleLeafColumns().get(k);
/*  476 */         k--;
/*      */ 
/*  479 */         if (localTableColumn4.isResizable())
/*      */         {
/*  482 */           Object localObject1 = i != 0 ? localTableColumn2 : localTableColumn4;
/*  483 */           Object localObject2 = i == 0 ? localTableColumn2 : localTableColumn4;
/*      */ 
/*  487 */           if (((TableColumn)localObject2).getWidth() > ((TableColumn)localObject2).getPrefWidth())
/*      */           {
/*  490 */             List localList = localTableView.getVisibleLeafColumns().subList(j + 1, k + 1);
/*  491 */             for (int m = localList.size() - 1; m >= 0; m--) {
/*  492 */               TableColumn localTableColumn5 = (TableColumn)localList.get(m);
/*  493 */               if (localTableColumn5.getWidth() < localTableColumn5.getPrefWidth()) {
/*  494 */                 localObject2 = localTableColumn5;
/*  495 */                 break;
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  511 */           double d13 = Math.min(Math.abs(d9), localObject1.getWidth() - localObject1.getMinWidth());
/*      */ 
/*  516 */           double d14 = TableView.resize(localObject1, -d13);
/*  517 */           double d15 = TableView.resize((TableColumn)localObject2, d13);
/*  518 */           d9 += (i != 0 ? d13 : -d13);
/*      */         }
/*      */       }
/*  520 */       return Boolean.valueOf(d9 == 0.0D);
/*      */     }
/*  343 */   };
/*      */ 
/*  727 */   private final ObservableList<TableColumn<S, ?>> columns = FXCollections.observableArrayList();
/*      */ 
/*  731 */   private final ObservableList<TableColumn<S, ?>> visibleLeafColumns = FXCollections.observableArrayList();
/*  732 */   private final ObservableList<TableColumn<S, ?>> unmodifiableVisibleLeafColumns = FXCollections.unmodifiableObservableList(this.visibleLeafColumns);
/*      */ 
/*  738 */   private ObservableList<TableColumn<S, ?>> sortOrder = FXCollections.observableArrayList();
/*      */   private double contentWidth;
/*  746 */   private boolean isInited = false;
/*      */ 
/*  756 */   private final ListChangeListener columnsObserver = new ListChangeListener() {
/*      */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/*  758 */       TableView.this.updateVisibleLeafColumns();
/*      */ 
/*  762 */       while (paramAnonymousChange.next()) {
/*  763 */         TableView.this.removeColumnsListener(paramAnonymousChange.getRemoved(), TableView.this.weakColumnsObserver);
/*  764 */         TableView.this.addColumnsListener(paramAnonymousChange.getAddedSubList(), TableView.this.weakColumnsObserver);
/*      */ 
/*  766 */         if (paramAnonymousChange.wasRemoved())
/*  767 */           for (int i = 0; i < paramAnonymousChange.getRemovedSize(); i++)
/*  768 */             TableView.this.getSortOrder().remove(paramAnonymousChange.getRemoved().get(i));
/*      */       }
/*      */     }
/*  756 */   };
/*      */ 
/*  775 */   private final InvalidationListener columnVisibleObserver = new InvalidationListener() {
/*      */     public void invalidated(Observable paramAnonymousObservable) {
/*  777 */       TableView.this.updateVisibleLeafColumns();
/*      */     }
/*  775 */   };
/*      */ 
/*  781 */   private final InvalidationListener columnSortableObserver = new InvalidationListener() {
/*      */     public void invalidated(Observable paramAnonymousObservable) {
/*  783 */       TableColumn localTableColumn = (TableColumn)((BooleanProperty)paramAnonymousObservable).getBean();
/*  784 */       if (!TableView.this.getSortOrder().contains(localTableColumn)) return;
/*  785 */       TableView.this.sort();
/*      */     }
/*  781 */   };
/*      */ 
/*  789 */   private final InvalidationListener columnSortTypeObserver = new InvalidationListener() {
/*      */     public void invalidated(Observable paramAnonymousObservable) {
/*  791 */       TableColumn localTableColumn = (TableColumn)((ObjectProperty)paramAnonymousObservable).getBean();
/*  792 */       if (!TableView.this.getSortOrder().contains(localTableColumn)) return;
/*  793 */       TableView.this.sort();
/*      */     }
/*  789 */   };
/*      */ 
/*  798 */   private final WeakInvalidationListener weakColumnVisibleObserver = new WeakInvalidationListener(this.columnVisibleObserver);
/*      */ 
/*  801 */   private final WeakInvalidationListener weakColumnSortableObserver = new WeakInvalidationListener(this.columnSortableObserver);
/*      */ 
/*  804 */   private final WeakInvalidationListener weakColumnSortTypeObserver = new WeakInvalidationListener(this.columnSortTypeObserver);
/*      */ 
/*  807 */   private final WeakListChangeListener weakColumnsObserver = new WeakListChangeListener(this.columnsObserver);
/*      */ 
/*  823 */   private ObjectProperty<ObservableList<S>> items = new SimpleObjectProperty(this, "items")
/*      */   {
/*      */     WeakReference<ObservableList<S>> oldItemsRef;
/*      */ 
/*      */     protected void invalidated() {
/*  828 */       ObservableList localObservableList = this.oldItemsRef == null ? null : (ObservableList)this.oldItemsRef.get();
/*      */ 
/*  832 */       if ((TableView.this.getSelectionModel() instanceof TableView.TableViewArrayListSelectionModel)) {
/*  833 */         ((TableView.TableViewArrayListSelectionModel)TableView.this.getSelectionModel()).updateItemsObserver(localObservableList, TableView.this.getItems());
/*      */       }
/*  835 */       if ((TableView.this.getFocusModel() instanceof TableView.TableViewFocusModel)) {
/*  836 */         TableView.this.getFocusModel().updateItemsObserver(localObservableList, TableView.this.getItems());
/*      */       }
/*  838 */       if ((TableView.this.getSkin() instanceof TableViewSkin)) {
/*  839 */         TableViewSkin localTableViewSkin = (TableViewSkin)TableView.this.getSkin();
/*  840 */         localTableViewSkin.updateTableItems(localObservableList, TableView.this.getItems());
/*      */       }
/*      */ 
/*  843 */       this.oldItemsRef = new WeakReference(TableView.this.getItems());
/*      */     }
/*  823 */   };
/*      */   private BooleanProperty tableMenuButtonVisible;
/*      */   private ObjectProperty<Callback<ResizeFeatures, Boolean>> columnResizePolicy;
/*      */   private ObjectProperty<Callback<TableView<S>, TableRow<S>>> rowFactory;
/*      */   private ObjectProperty<Node> placeholder;
/*  978 */   private ObjectProperty<TableViewSelectionModel<S>> selectionModel = new SimpleObjectProperty(this, "selectionModel");
/*      */   private ObjectProperty<TableViewFocusModel<S>> focusModel;
/*      */   private BooleanProperty editable;
/*      */   private ReadOnlyObjectWrapper<TablePosition<S, ?>> editingCell;
/*      */   private static final String DEFAULT_STYLE_CLASS = "table-view";
/*      */   private static final String PSEUDO_CLASS_CELL_SELECTION = "cell-selection";
/*      */   private static final String PSEUDO_CLASS_ROW_SELECTION = "row-selection";
/* 1447 */   private static final long CELL_SELECTION_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("cell-selection");
/*      */ 
/* 1449 */   private static final long ROW_SELECTION_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("row-selection");
/*      */ 
/*      */   private static double resize(TableColumn<?, ?> paramTableColumn, double paramDouble)
/*      */   {
/*  528 */     if (paramDouble == 0.0D) return 0.0D;
/*  529 */     if (!paramTableColumn.isResizable()) return paramDouble;
/*      */ 
/*  531 */     boolean bool = paramDouble < 0.0D;
/*  532 */     List localList = getResizableChildren(paramTableColumn, bool);
/*      */ 
/*  534 */     if (localList.size() > 0) {
/*  535 */       return resizeColumns(localList, paramDouble);
/*      */     }
/*  537 */     double d = paramTableColumn.getWidth() + paramDouble;
/*      */ 
/*  539 */     if (d > paramTableColumn.getMaxWidth()) {
/*  540 */       paramTableColumn.impl_setWidth(paramTableColumn.getMaxWidth());
/*  541 */       return d - paramTableColumn.getMaxWidth();
/*  542 */     }if (d < paramTableColumn.getMinWidth()) {
/*  543 */       paramTableColumn.impl_setWidth(paramTableColumn.getMinWidth());
/*  544 */       return d - paramTableColumn.getMinWidth();
/*      */     }
/*  546 */     paramTableColumn.impl_setWidth(d);
/*  547 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   private static List<TableColumn<?, ?>> getResizableChildren(TableColumn<?, ?> paramTableColumn, boolean paramBoolean)
/*      */   {
/*  556 */     if ((paramTableColumn == null) || (paramTableColumn.getColumns().isEmpty())) {
/*  557 */       return Collections.emptyList();
/*      */     }
/*      */ 
/*  560 */     ArrayList localArrayList = new ArrayList();
/*  561 */     for (TableColumn localTableColumn : paramTableColumn.getColumns()) {
/*  562 */       if ((localTableColumn.isVisible()) && 
/*  563 */         (localTableColumn.isResizable()))
/*      */       {
/*  565 */         if ((paramBoolean) && (localTableColumn.getWidth() > localTableColumn.getMinWidth()))
/*  566 */           localArrayList.add(localTableColumn);
/*  567 */         else if ((!paramBoolean) && (localTableColumn.getWidth() < localTableColumn.getMaxWidth()))
/*  568 */           localArrayList.add(localTableColumn);
/*      */       }
/*      */     }
/*  571 */     return localArrayList;
/*      */   }
/*      */ 
/*      */   private static double resizeColumns(List<TableColumn<?, ?>> paramList, double paramDouble)
/*      */   {
/*  580 */     int i = paramList.size();
/*      */ 
/*  585 */     double d1 = paramDouble / i;
/*      */ 
/*  591 */     double d2 = paramDouble;
/*      */ 
/*  595 */     int j = 0;
/*      */ 
/*  600 */     int k = 1;
/*  601 */     for (TableColumn localTableColumn : paramList) {
/*  602 */       j++;
/*      */ 
/*  605 */       double d3 = resize(localTableColumn, d1);
/*      */ 
/*  609 */       d2 = d2 - d1 + d3;
/*      */ 
/*  613 */       if (d3 != 0.0D) {
/*  614 */         k = 0;
/*      */ 
/*  617 */         d1 = d2 / (i - j);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  622 */     return k != 0 ? 0.0D : d2;
/*      */   }
/*      */ 
/*      */   public TableView()
/*      */   {
/*  640 */     this(FXCollections.observableArrayList());
/*      */   }
/*      */ 
/*      */   public TableView(ObservableList<S> paramObservableList)
/*      */   {
/*  655 */     getStyleClass().setAll(new String[] { "table-view" });
/*      */ 
/*  658 */     setItems(paramObservableList);
/*      */ 
/*  662 */     setSelectionModel(new TableViewArrayListSelectionModel(this));
/*  663 */     setFocusModel(new TableViewFocusModel(this));
/*      */ 
/*  667 */     getColumns().addListener(this.weakColumnsObserver);
/*  668 */     getColumns().addListener(new ListChangeListener()
/*      */     {
/*      */       public void onChanged(ListChangeListener.Change<? extends TableColumn<S, ?>> paramAnonymousChange) {
/*  671 */         while (paramAnonymousChange.next())
/*      */         {
/*  673 */           for (Iterator localIterator = paramAnonymousChange.getRemoved().iterator(); localIterator.hasNext(); ) { localTableColumn = (TableColumn)localIterator.next();
/*  674 */             localTableColumn.setTableView(null);
/*      */           }
/*  676 */           TableColumn localTableColumn;
/*  676 */           for (localIterator = paramAnonymousChange.getAddedSubList().iterator(); localIterator.hasNext(); ) { localTableColumn = (TableColumn)localIterator.next();
/*  677 */             localTableColumn.setTableView(TableView.this);
/*      */           }
/*      */ 
/*  681 */           TableView.this.removeTableColumnListener(paramAnonymousChange.getRemoved());
/*  682 */           TableView.this.addTableColumnListener(paramAnonymousChange.getAddedSubList());
/*      */         }
/*      */ 
/*  687 */         TableView.this.updateVisibleLeafColumns();
/*      */       }
/*      */     });
/*  693 */     getSortOrder().addListener(new ListChangeListener() {
/*      */       public void onChanged(ListChangeListener.Change<? extends TableColumn<S, ?>> paramAnonymousChange) {
/*  695 */         TableView.this.sort();
/*      */       }
/*      */     });
/*  702 */     getProperties().addListener(new MapChangeListener()
/*      */     {
/*      */       public void onChanged(MapChangeListener.Change<? extends Object, ? extends Object> paramAnonymousChange) {
/*  705 */         if ((paramAnonymousChange.wasAdded()) && ("TableView.contentWidth".equals(paramAnonymousChange.getKey()))) {
/*  706 */           if ((paramAnonymousChange.getValueAdded() instanceof Number)) {
/*  707 */             TableView.this.setContentWidth(((Double)paramAnonymousChange.getValueAdded()).doubleValue());
/*      */           }
/*  709 */           TableView.this.getProperties().remove("TableView.contentWidth");
/*      */         }
/*      */       }
/*      */     });
/*  714 */     this.isInited = true;
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<ObservableList<S>> itemsProperty()
/*      */   {
/*  822 */     return this.items;
/*      */   }
/*      */ 
/*      */   public final void setItems(ObservableList<S> paramObservableList)
/*      */   {
/*  846 */     itemsProperty().set(paramObservableList); } 
/*  847 */   public final ObservableList<S> getItems() { return (ObservableList)this.items.get(); }
/*      */ 
/*      */ 
/*      */   public final BooleanProperty tableMenuButtonVisibleProperty()
/*      */   {
/*  859 */     if (this.tableMenuButtonVisible == null) {
/*  860 */       this.tableMenuButtonVisible = new SimpleBooleanProperty(this, "tableMenuButtonVisible");
/*      */     }
/*  862 */     return this.tableMenuButtonVisible;
/*      */   }
/*      */   public final void setTableMenuButtonVisible(boolean paramBoolean) {
/*  865 */     tableMenuButtonVisibleProperty().set(paramBoolean);
/*      */   }
/*      */   public final boolean isTableMenuButtonVisible() {
/*  868 */     return this.tableMenuButtonVisible == null ? false : this.tableMenuButtonVisible.get();
/*      */   }
/*      */ 
/*      */   public final void setColumnResizePolicy(Callback<ResizeFeatures, Boolean> paramCallback)
/*      */   {
/*  875 */     columnResizePolicyProperty().set(paramCallback);
/*      */   }
/*      */   public final Callback<ResizeFeatures, Boolean> getColumnResizePolicy() {
/*  878 */     return this.columnResizePolicy == null ? UNCONSTRAINED_RESIZE_POLICY : (Callback)this.columnResizePolicy.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Callback<ResizeFeatures, Boolean>> columnResizePolicyProperty()
/*      */   {
/*  888 */     if (this.columnResizePolicy == null) {
/*  889 */       this.columnResizePolicy = new ObjectPropertyBase(UNCONSTRAINED_RESIZE_POLICY) {
/*      */         private Callback<TableView.ResizeFeatures, Boolean> oldPolicy;
/*      */ 
/*      */         protected void invalidated() {
/*  893 */           if (TableView.this.isInited) {
/*  894 */             ((Callback)get()).call(new TableView.ResizeFeatures(TableView.this, null, Double.valueOf(0.0D)));
/*  895 */             TableView.this.refresh();
/*      */ 
/*  897 */             if (this.oldPolicy != null) {
/*  898 */               TableView.this.impl_pseudoClassStateChanged(this.oldPolicy.toString());
/*      */             }
/*  900 */             if (get() != null) {
/*  901 */               TableView.this.impl_pseudoClassStateChanged(((Callback)get()).toString());
/*      */             }
/*  903 */             this.oldPolicy = ((Callback)get());
/*      */           }
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  909 */           return TableView.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  914 */           return "columnResizePolicy";
/*      */         }
/*      */       };
/*      */     }
/*  918 */     return this.columnResizePolicy;
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Callback<TableView<S>, TableRow<S>>> rowFactoryProperty()
/*      */   {
/*  941 */     if (this.rowFactory == null) {
/*  942 */       this.rowFactory = new SimpleObjectProperty(this, "rowFactory");
/*      */     }
/*  944 */     return this.rowFactory;
/*      */   }
/*      */   public final void setRowFactory(Callback<TableView<S>, TableRow<S>> paramCallback) {
/*  947 */     rowFactoryProperty().set(paramCallback);
/*      */   }
/*      */   public final Callback<TableView<S>, TableRow<S>> getRowFactory() {
/*  950 */     return this.rowFactory == null ? null : (Callback)this.rowFactory.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Node> placeholderProperty()
/*      */   {
/*  964 */     if (this.placeholder == null) {
/*  965 */       this.placeholder = new SimpleObjectProperty(this, "placeholder");
/*      */     }
/*  967 */     return this.placeholder;
/*      */   }
/*      */   public final void setPlaceholder(Node paramNode) {
/*  970 */     placeholderProperty().set(paramNode);
/*      */   }
/*      */   public final Node getPlaceholder() {
/*  973 */     return this.placeholder == null ? null : (Node)this.placeholder.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<TableViewSelectionModel<S>> selectionModelProperty()
/*      */   {
/*  987 */     return this.selectionModel;
/*      */   }
/*      */   public final void setSelectionModel(TableViewSelectionModel<S> paramTableViewSelectionModel) {
/*  990 */     selectionModelProperty().set(paramTableViewSelectionModel);
/*      */   }
/*      */ 
/*      */   public final TableViewSelectionModel<S> getSelectionModel() {
/*  994 */     return (TableViewSelectionModel)this.selectionModel.get();
/*      */   }
/*      */ 
/*      */   public final void setFocusModel(TableViewFocusModel<S> paramTableViewFocusModel)
/*      */   {
/* 1001 */     focusModelProperty().set(paramTableViewFocusModel);
/*      */   }
/*      */   public final TableViewFocusModel<S> getFocusModel() {
/* 1004 */     return this.focusModel == null ? null : (TableViewFocusModel)this.focusModel.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<TableViewFocusModel<S>> focusModelProperty()
/*      */   {
/* 1012 */     if (this.focusModel == null) {
/* 1013 */       this.focusModel = new SimpleObjectProperty(this, "focusModel");
/*      */     }
/* 1015 */     return this.focusModel;
/*      */   }
/*      */ 
/*      */   public final void setEditable(boolean paramBoolean)
/*      */   {
/* 1022 */     editableProperty().set(paramBoolean);
/*      */   }
/*      */   public final boolean isEditable() {
/* 1025 */     return this.editable == null ? false : this.editable.get();
/*      */   }
/*      */ 
/*      */   public final BooleanProperty editableProperty()
/*      */   {
/* 1033 */     if (this.editable == null) {
/* 1034 */       this.editable = new SimpleBooleanProperty(this, "editable", false);
/*      */     }
/* 1036 */     return this.editable;
/*      */   }
/*      */ 
/*      */   private void setEditingCell(TablePosition<S, ?> paramTablePosition)
/*      */   {
/* 1043 */     editingCellPropertyImpl().set(paramTablePosition);
/*      */   }
/*      */   public final TablePosition<S, ?> getEditingCell() {
/* 1046 */     return this.editingCell == null ? null : (TablePosition)this.editingCell.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyObjectProperty<TablePosition<S, ?>> editingCellProperty()
/*      */   {
/* 1054 */     return editingCellPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyObjectWrapper<TablePosition<S, ?>> editingCellPropertyImpl() {
/* 1058 */     if (this.editingCell == null) {
/* 1059 */       this.editingCell = new ReadOnlyObjectWrapper(this, "editingCell");
/*      */     }
/* 1061 */     return this.editingCell;
/*      */   }
/*      */ 
/*      */   public final ObservableList<TableColumn<S, ?>> getColumns()
/*      */   {
/* 1185 */     return this.columns;
/*      */   }
/*      */ 
/*      */   public final ObservableList<TableColumn<S, ?>> getSortOrder()
/*      */   {
/* 1206 */     return this.sortOrder;
/*      */   }
/*      */ 
/*      */   public void scrollTo(int paramInt)
/*      */   {
/* 1214 */     getProperties().put("VirtualContainerBase.scrollToIndexCentered", Integer.valueOf(paramInt));
/*      */   }
/*      */ 
/*      */   public boolean resizeColumn(TableColumn<S, ?> paramTableColumn, double paramDouble)
/*      */   {
/* 1222 */     if ((paramTableColumn == null) || (Double.compare(paramDouble, 0.0D) == 0)) return false;
/*      */ 
/* 1224 */     boolean bool = ((Boolean)getColumnResizePolicy().call(new ResizeFeatures(this, paramTableColumn, Double.valueOf(paramDouble)))).booleanValue();
/* 1225 */     if (!bool) return false;
/*      */ 
/* 1233 */     refresh();
/* 1234 */     return true;
/*      */   }
/*      */ 
/*      */   public void edit(int paramInt, TableColumn<S, ?> paramTableColumn)
/*      */   {
/* 1243 */     if ((!isEditable()) || ((paramTableColumn != null) && (!paramTableColumn.isEditable()))) return;
/* 1244 */     setEditingCell(new TablePosition(this, paramInt, paramTableColumn));
/*      */   }
/*      */ 
/*      */   public ObservableList<TableColumn<S, ?>> getVisibleLeafColumns()
/*      */   {
/* 1252 */     return this.unmodifiableVisibleLeafColumns;
/*      */   }
/*      */ 
/*      */   public int getVisibleLeafIndex(TableColumn<S, ?> paramTableColumn)
/*      */   {
/* 1260 */     return getVisibleLeafColumns().indexOf(paramTableColumn);
/*      */   }
/*      */ 
/*      */   public TableColumn<S, ?> getVisibleLeafColumn(int paramInt)
/*      */   {
/* 1268 */     if ((paramInt < 0) || (paramInt >= this.visibleLeafColumns.size())) return null;
/* 1269 */     return (TableColumn)this.visibleLeafColumns.get(paramInt);
/*      */   }
/*      */ 
/*      */   private void refresh()
/*      */   {
/* 1292 */     getProperties().put("TableView.refresh", Boolean.TRUE);
/*      */   }
/*      */ 
/*      */   private void sort()
/*      */   {
/* 1304 */     TableColumnComparator localTableColumnComparator = new TableColumnComparator();
/* 1305 */     for (Object localObject1 = getSortOrder().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (TableColumn)((Iterator)localObject1).next();
/* 1306 */       localTableColumnComparator.getColumns().add(localObject2);
/*      */     }
/*      */     Object localObject2;
/* 1311 */     if ((getItems() instanceof TransformationList))
/*      */     {
/* 1315 */       localObject1 = getItems();
/* 1316 */       while ((localObject1 != null) && 
/* 1317 */         (!(localObject1 instanceof SortableList)))
/*      */       {
/* 1319 */         if (!(localObject1 instanceof TransformationList)) break;
/* 1320 */         localObject1 = ((TransformationList)localObject1).getDirectSource();
/*      */       }
/*      */ 
/* 1326 */       if ((localObject1 instanceof SortableList)) {
/* 1327 */         localObject2 = (SortableList)localObject1;
/*      */ 
/* 1330 */         ((SortableList)localObject2).setComparator(localTableColumnComparator);
/*      */ 
/* 1332 */         if (((SortableList)localObject2).getMode() == SortableList.SortMode.BATCH) {
/* 1333 */           ((SortableList)localObject2).sort();
/*      */         }
/*      */ 
/* 1336 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1342 */     FXCollections.sort(getItems(), localTableColumnComparator);
/*      */   }
/*      */ 
/*      */   private void setContentWidth(double paramDouble)
/*      */   {
/* 1348 */     this.contentWidth = paramDouble;
/* 1349 */     if (this.isInited)
/*      */     {
/* 1355 */       getColumnResizePolicy().call(new ResizeFeatures(this, null, Double.valueOf(0.0D)));
/* 1356 */       refresh();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateVisibleLeafColumns()
/*      */   {
/* 1365 */     ArrayList localArrayList = new ArrayList();
/* 1366 */     buildVisibleLeafColumns(getColumns(), localArrayList);
/* 1367 */     this.visibleLeafColumns.setAll(localArrayList);
/*      */ 
/* 1374 */     getColumnResizePolicy().call(new ResizeFeatures(this, null, Double.valueOf(0.0D)));
/* 1375 */     refresh();
/*      */   }
/*      */ 
/*      */   private void buildVisibleLeafColumns(List<TableColumn<S, ?>> paramList1, List<TableColumn<S, ?>> paramList2) {
/* 1379 */     for (TableColumn localTableColumn : paramList1)
/* 1380 */       if (localTableColumn != null)
/*      */       {
/* 1382 */         int i = !localTableColumn.getColumns().isEmpty() ? 1 : 0;
/*      */ 
/* 1384 */         if (i != 0)
/* 1385 */           buildVisibleLeafColumns(localTableColumn.getColumns(), paramList2);
/* 1386 */         else if (localTableColumn.isVisible())
/* 1387 */           paramList2.add(localTableColumn);
/*      */       }
/*      */   }
/*      */ 
/*      */   private void removeTableColumnListener(List<? extends TableColumn<S, ?>> paramList)
/*      */   {
/* 1393 */     if (paramList == null) return;
/*      */ 
/* 1395 */     for (TableColumn localTableColumn : paramList) {
/* 1396 */       localTableColumn.visibleProperty().removeListener(this.weakColumnVisibleObserver);
/* 1397 */       localTableColumn.sortableProperty().removeListener(this.weakColumnSortableObserver);
/* 1398 */       localTableColumn.sortTypeProperty().removeListener(this.weakColumnSortTypeObserver);
/*      */ 
/* 1400 */       removeTableColumnListener(localTableColumn.getColumns());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addTableColumnListener(List<? extends TableColumn<S, ?>> paramList) {
/* 1405 */     if (paramList == null) return;
/* 1406 */     for (TableColumn localTableColumn : paramList) {
/* 1407 */       localTableColumn.visibleProperty().addListener(this.weakColumnVisibleObserver);
/* 1408 */       localTableColumn.sortableProperty().addListener(this.weakColumnSortableObserver);
/* 1409 */       localTableColumn.sortTypeProperty().addListener(this.weakColumnSortTypeObserver);
/*      */ 
/* 1411 */       addTableColumnListener(localTableColumn.getColumns());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void removeColumnsListener(List<? extends TableColumn<S, ?>> paramList, ListChangeListener paramListChangeListener) {
/* 1416 */     if (paramList == null) return;
/*      */ 
/* 1418 */     for (TableColumn localTableColumn : paramList) {
/* 1419 */       localTableColumn.getColumns().removeListener(paramListChangeListener);
/* 1420 */       removeColumnsListener(localTableColumn.getColumns(), paramListChangeListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addColumnsListener(List<? extends TableColumn<S, ?>> paramList, ListChangeListener paramListChangeListener) {
/* 1425 */     if (paramList == null) return;
/*      */ 
/* 1427 */     for (TableColumn localTableColumn : paramList) {
/* 1428 */       localTableColumn.getColumns().addListener(paramListChangeListener);
/* 1429 */       addColumnsListener(localTableColumn.getColumns(), paramListChangeListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public long impl_getPseudoClassState()
/*      */   {
/* 1457 */     long l = super.impl_getPseudoClassState();
/* 1458 */     if (getSelectionModel() != null) {
/* 1459 */       l |= (getSelectionModel().isCellSelectionEnabled() ? CELL_SELECTION_PSEUDOCLASS_STATE : ROW_SELECTION_PSEUDOCLASS_STATE);
/*      */     }
/*      */ 
/* 1462 */     return l;
/*      */   }
/*      */ 
/*      */   public static class TableViewFocusModel<S> extends FocusModel<S>
/*      */   {
/*      */     private final TableView<S> tableView;
/*      */     private final TablePosition EMPTY_CELL;
/* 2405 */     private ChangeListener<ObservableList<S>> itemsPropertyListener = new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends ObservableList<S>> paramAnonymousObservableValue, ObservableList<S> paramAnonymousObservableList1, ObservableList<S> paramAnonymousObservableList2)
/*      */       {
/* 2409 */         TableView.TableViewFocusModel.this.updateItemsObserver(paramAnonymousObservableList1, paramAnonymousObservableList2);
/*      */       }
/* 2405 */     };
/*      */ 
/* 2413 */     private WeakChangeListener<ObservableList<S>> weakItemsPropertyListener = new WeakChangeListener(this.itemsPropertyListener);
/*      */ 
/* 2418 */     private final ListChangeListener<S> itemsContentListener = new ListChangeListener() {
/*      */       public void onChanged(ListChangeListener.Change<? extends S> paramAnonymousChange) {
/* 2420 */         paramAnonymousChange.next();
/* 2421 */         if (paramAnonymousChange.getFrom() > TableView.TableViewFocusModel.this.getFocusedIndex()) return;
/* 2422 */         paramAnonymousChange.reset();
/* 2423 */         boolean bool1 = false;
/* 2424 */         boolean bool2 = false;
/* 2425 */         int i = 0;
/* 2426 */         int j = 0;
/* 2427 */         while (paramAnonymousChange.next()) {
/* 2428 */           bool1 |= paramAnonymousChange.wasAdded();
/* 2429 */           bool2 |= paramAnonymousChange.wasRemoved();
/* 2430 */           i += paramAnonymousChange.getAddedSize();
/* 2431 */           j += paramAnonymousChange.getRemovedSize();
/*      */         }
/* 2433 */         if ((bool1) && (!bool2))
/* 2434 */           TableView.TableViewFocusModel.this.focus(TableView.TableViewFocusModel.this.getFocusedIndex() + i);
/* 2435 */         else if ((!bool1) && (bool2))
/* 2436 */           TableView.TableViewFocusModel.this.focus(TableView.TableViewFocusModel.this.getFocusedIndex() - j);
/*      */       }
/* 2418 */     };
/*      */ 
/* 2441 */     private WeakListChangeListener<S> weakItemsContentListener = new WeakListChangeListener(this.itemsContentListener);
/*      */     private ReadOnlyObjectWrapper<TablePosition> focusedCell;
/*      */ 
/*      */     public TableViewFocusModel(TableView<S> paramTableView)
/*      */     {
/* 2389 */       if (paramTableView == null) {
/* 2390 */         throw new NullPointerException("TableView can not be null");
/*      */       }
/*      */ 
/* 2393 */       this.tableView = paramTableView;
/*      */ 
/* 2395 */       this.tableView.itemsProperty().addListener(this.weakItemsPropertyListener);
/* 2396 */       if (paramTableView.getItems() != null) {
/* 2397 */         this.tableView.getItems().addListener(this.weakItemsContentListener);
/*      */       }
/*      */ 
/* 2400 */       TablePosition localTablePosition = new TablePosition(paramTableView, -1, null);
/* 2401 */       setFocusedCell(localTablePosition);
/* 2402 */       this.EMPTY_CELL = localTablePosition;
/*      */     }
/*      */ 
/*      */     private void updateItemsObserver(ObservableList<S> paramObservableList1, ObservableList<S> paramObservableList2)
/*      */     {
/* 2447 */       if (paramObservableList1 != null) paramObservableList1.removeListener(this.weakItemsContentListener);
/* 2448 */       if (paramObservableList2 != null) paramObservableList2.addListener(this.weakItemsContentListener);
/*      */     }
/*      */ 
/*      */     protected int getItemCount()
/*      */     {
/* 2453 */       if (this.tableView.getItems() == null) return -1;
/* 2454 */       return this.tableView.getItems().size();
/*      */     }
/*      */ 
/*      */     protected S getModelItem(int paramInt)
/*      */     {
/* 2459 */       if (this.tableView.getItems() == null) return null;
/*      */ 
/* 2461 */       if ((paramInt < 0) || (paramInt >= getItemCount())) return null;
/*      */ 
/* 2463 */       return this.tableView.getItems().get(paramInt);
/*      */     }
/*      */ 
/*      */     public final ReadOnlyObjectProperty<TablePosition> focusedCellProperty()
/*      */     {
/* 2471 */       return focusedCellPropertyImpl().getReadOnlyProperty();
/*      */     }
/* 2473 */     private void setFocusedCell(TablePosition paramTablePosition) { focusedCellPropertyImpl().set(paramTablePosition); } 
/* 2474 */     public final TablePosition getFocusedCell() { return this.focusedCell == null ? this.EMPTY_CELL : (TablePosition)this.focusedCell.get(); }
/*      */ 
/*      */     private ReadOnlyObjectWrapper<TablePosition> focusedCellPropertyImpl() {
/* 2477 */       if (this.focusedCell == null) {
/* 2478 */         this.focusedCell = new ReadOnlyObjectWrapper(this.EMPTY_CELL) {
/*      */           private TablePosition old;
/*      */ 
/* 2481 */           protected void invalidated() { if (get() == null) return;
/*      */ 
/* 2483 */             if ((this.old == null) || ((this.old != null) && (!this.old.equals(get())))) {
/* 2484 */               TableView.TableViewFocusModel.this.setFocusedIndex(((TablePosition)get()).getRow());
/* 2485 */               TableView.TableViewFocusModel.this.setFocusedItem(TableView.TableViewFocusModel.this.getModelItem(((TablePosition)getValue()).getRow()));
/*      */ 
/* 2487 */               this.old = ((TablePosition)get());
/*      */             }
/*      */           }
/*      */ 
/*      */           public Object getBean()
/*      */           {
/* 2493 */             return TableView.TableViewFocusModel.this;
/*      */           }
/*      */ 
/*      */           public String getName()
/*      */           {
/* 2498 */             return "focusedCell";
/*      */           }
/*      */         };
/*      */       }
/* 2502 */       return this.focusedCell;
/*      */     }
/*      */ 
/*      */     public void focus(int paramInt, TableColumn<S, ?> paramTableColumn)
/*      */     {
/* 2513 */       if ((paramInt < 0) || (paramInt >= getItemCount()))
/* 2514 */         setFocusedCell(this.EMPTY_CELL);
/*      */       else
/* 2516 */         setFocusedCell(new TablePosition(this.tableView, paramInt, paramTableColumn));
/*      */     }
/*      */ 
/*      */     public void focus(TablePosition paramTablePosition)
/*      */     {
/* 2527 */       if (paramTablePosition == null) return;
/* 2528 */       focus(paramTablePosition.getRow(), paramTablePosition.getTableColumn());
/*      */     }
/*      */ 
/*      */     public boolean isFocused(int paramInt, TableColumn<S, ?> paramTableColumn)
/*      */     {
/* 2543 */       if ((paramInt < 0) || (paramInt >= getItemCount())) return false;
/*      */ 
/* 2545 */       TablePosition localTablePosition = getFocusedCell();
/* 2546 */       int i = (paramTableColumn == null) || ((paramTableColumn != null) && (paramTableColumn.equals(localTablePosition.getTableColumn()))) ? 1 : 0;
/*      */ 
/* 2548 */       return (localTablePosition.getRow() == paramInt) && (i != 0);
/*      */     }
/*      */ 
/*      */     public void focus(int paramInt)
/*      */     {
/* 2560 */       if ((paramInt < 0) || (paramInt >= getItemCount()))
/* 2561 */         setFocusedCell(this.EMPTY_CELL);
/*      */       else
/* 2563 */         setFocusedCell(new TablePosition(this.tableView, paramInt, null));
/*      */     }
/*      */ 
/*      */     public void focusAboveCell()
/*      */     {
/* 2571 */       TablePosition localTablePosition = getFocusedCell();
/*      */ 
/* 2573 */       if (getFocusedIndex() == -1)
/* 2574 */         focus(getItemCount() - 1, localTablePosition.getTableColumn());
/* 2575 */       else if (getFocusedIndex() > 0)
/* 2576 */         focus(getFocusedIndex() - 1, localTablePosition.getTableColumn());
/*      */     }
/*      */ 
/*      */     public void focusBelowCell()
/*      */     {
/* 2584 */       TablePosition localTablePosition = getFocusedCell();
/* 2585 */       if (getFocusedIndex() == -1)
/* 2586 */         focus(0, localTablePosition.getTableColumn());
/* 2587 */       else if (getFocusedIndex() != getItemCount() - 1)
/* 2588 */         focus(getFocusedIndex() + 1, localTablePosition.getTableColumn());
/*      */     }
/*      */ 
/*      */     public void focusLeftCell()
/*      */     {
/* 2596 */       TablePosition localTablePosition = getFocusedCell();
/* 2597 */       if (localTablePosition.getColumn() <= 0) return;
/* 2598 */       focus(localTablePosition.getRow(), getTableColumn(localTablePosition.getTableColumn(), -1));
/*      */     }
/*      */ 
/*      */     public void focusRightCell()
/*      */     {
/* 2605 */       TablePosition localTablePosition = getFocusedCell();
/* 2606 */       if (localTablePosition.getColumn() == getColumnCount() - 1) return;
/* 2607 */       focus(localTablePosition.getRow(), getTableColumn(localTablePosition.getTableColumn(), 1));
/*      */     }
/*      */ 
/*      */     private int getColumnCount()
/*      */     {
/* 2619 */       return this.tableView.getVisibleLeafColumns().size();
/*      */     }
/*      */ 
/*      */     private TableColumn<S, ?> getTableColumn(TableColumn<S, ?> paramTableColumn, int paramInt)
/*      */     {
/* 2624 */       int i = this.tableView.getVisibleLeafIndex(paramTableColumn);
/* 2625 */       int j = i + paramInt;
/* 2626 */       return this.tableView.getVisibleLeafColumn(j);
/*      */     }
/*      */   }
/*      */ 
/*      */   static class TableViewArrayListSelectionModel<S> extends TableView.TableViewSelectionModel<S>
/*      */   {
/*      */     private final TableView<S> tableView;
/* 1781 */     private ChangeListener<ObservableList<S>> itemsPropertyListener = new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends ObservableList<S>> paramAnonymousObservableValue, ObservableList<S> paramAnonymousObservableList1, ObservableList<S> paramAnonymousObservableList2)
/*      */       {
/* 1785 */         TableView.TableViewArrayListSelectionModel.this.updateItemsObserver(paramAnonymousObservableList1, paramAnonymousObservableList2);
/*      */       }
/* 1781 */     };
/*      */ 
/* 1789 */     private WeakChangeListener<ObservableList<S>> weakItemsPropertyListener = new WeakChangeListener(this.itemsPropertyListener);
/*      */ 
/* 1792 */     final ListChangeListener<S> itemsContentListener = new ListChangeListener() {
/*      */       public void onChanged(ListChangeListener.Change<? extends S> paramAnonymousChange) {
/* 1794 */         if ((TableView.this.getItems() == null) || (TableView.this.getItems().isEmpty())) {
/* 1795 */           TableView.TableViewArrayListSelectionModel.this.setSelectedIndex(-1);
/* 1796 */         } else if ((TableView.TableViewArrayListSelectionModel.this.getSelectedIndex() == -1) && (TableView.TableViewArrayListSelectionModel.this.getSelectedItem() != null)) {
/* 1797 */           int i = TableView.this.getItems().indexOf(TableView.TableViewArrayListSelectionModel.this.getSelectedItem());
/* 1798 */           if (i != -1) {
/* 1799 */             TableView.TableViewArrayListSelectionModel.this.setSelectedIndex(i);
/*      */           }
/*      */         }
/* 1802 */         TableView.TableViewArrayListSelectionModel.this.updateSelection(paramAnonymousChange);
/*      */       }
/* 1792 */     };
/*      */ 
/* 1806 */     final WeakListChangeListener weakItemsContentListener = new WeakListChangeListener(this.itemsContentListener);
/*      */     private final ObservableList<TablePosition> selectedCells;
/*      */     private final ReadOnlyUnbackedObservableList<Integer> selectedIndices;
/*      */     private final ReadOnlyUnbackedObservableList<S> selectedItems;
/*      */     private final ReadOnlyUnbackedObservableList<TablePosition> selectedCellsSeq;
/* 1859 */     private int previousModelSize = 0;
/*      */ 
/*      */     public TableViewArrayListSelectionModel(TableView<S> paramTableView)
/*      */     {
/* 1690 */       super();
/* 1691 */       this.tableView = paramTableView;
/*      */ 
/* 1693 */       final MappingChange.Map local1 = new MappingChange.Map()
/*      */       {
/*      */         public S map(TablePosition paramAnonymousTablePosition)
/*      */         {
/* 1697 */           return TableView.TableViewArrayListSelectionModel.this.getModelItem(paramAnonymousTablePosition.getRow());
/*      */         }
/*      */       };
/* 1701 */       final MappingChange.Map local2 = new MappingChange.Map()
/*      */       {
/*      */         public Integer map(TablePosition paramAnonymousTablePosition)
/*      */         {
/* 1705 */           return Integer.valueOf(paramAnonymousTablePosition.getRow());
/*      */         }
/*      */       };
/* 1709 */       this.selectedCells = FXCollections.observableArrayList();
/* 1710 */       this.selectedCells.addListener(new ListChangeListener()
/*      */       {
/*      */         public void onChanged(ListChangeListener.Change<? extends TablePosition> paramAnonymousChange)
/*      */         {
/* 1719 */           TableView.TableViewArrayListSelectionModel.this.selectedItems.callObservers(new MappingChange(paramAnonymousChange, local1, TableView.TableViewArrayListSelectionModel.this.selectedItems));
/* 1720 */           paramAnonymousChange.reset();
/*      */ 
/* 1722 */           TableView.TableViewArrayListSelectionModel.this.selectedIndices.callObservers(new MappingChange(paramAnonymousChange, local2, TableView.TableViewArrayListSelectionModel.this.selectedIndices));
/* 1723 */           paramAnonymousChange.reset();
/*      */ 
/* 1725 */           TableView.TableViewArrayListSelectionModel.this.selectedCellsSeq.callObservers(new MappingChange(paramAnonymousChange, MappingChange.NOOP_MAP, TableView.TableViewArrayListSelectionModel.this.selectedCellsSeq));
/* 1726 */           paramAnonymousChange.reset();
/*      */         }
/*      */       });
/* 1730 */       this.selectedIndices = new ReadOnlyUnbackedObservableList() {
/*      */         public Integer get(int paramAnonymousInt) {
/* 1732 */           return Integer.valueOf(((TablePosition)TableView.TableViewArrayListSelectionModel.this.selectedCells.get(paramAnonymousInt)).getRow());
/*      */         }
/*      */ 
/*      */         public int size() {
/* 1736 */           return TableView.TableViewArrayListSelectionModel.this.selectedCells.size();
/*      */         }
/*      */       };
/* 1740 */       this.selectedItems = new ReadOnlyUnbackedObservableList() {
/*      */         public S get(int paramAnonymousInt) {
/* 1742 */           return TableView.TableViewArrayListSelectionModel.this.getModelItem(((Integer)TableView.TableViewArrayListSelectionModel.this.selectedIndices.get(paramAnonymousInt)).intValue());
/*      */         }
/*      */ 
/*      */         public int size() {
/* 1746 */           return TableView.TableViewArrayListSelectionModel.this.selectedIndices.size();
/*      */         }
/*      */       };
/* 1750 */       this.selectedCellsSeq = new ReadOnlyUnbackedObservableList() {
/*      */         public TablePosition get(int paramAnonymousInt) {
/* 1752 */           return (TablePosition)TableView.TableViewArrayListSelectionModel.this.selectedCells.get(paramAnonymousInt);
/*      */         }
/*      */ 
/*      */         public int size() {
/* 1756 */           return TableView.TableViewArrayListSelectionModel.this.selectedCells.size();
/*      */         }
/*      */       };
/* 1771 */       paramTableView.itemsProperty().addListener(this.weakItemsPropertyListener);
/*      */ 
/* 1774 */       if (paramTableView.getItems() != null)
/* 1775 */         paramTableView.getItems().addListener(this.weakItemsContentListener);
/*      */     }
/*      */ 
/*      */     private void updateItemsObserver(ObservableList<S> paramObservableList1, ObservableList<S> paramObservableList2)
/*      */     {
/* 1812 */       if (paramObservableList1 != null) {
/* 1813 */         paramObservableList1.removeListener(this.weakItemsContentListener);
/*      */       }
/* 1815 */       if (paramObservableList2 != null) {
/* 1816 */         paramObservableList2.addListener(this.weakItemsContentListener);
/*      */       }
/*      */ 
/* 1821 */       setSelectedIndex(-1);
/*      */     }
/*      */ 
/*      */     public ObservableList<Integer> getSelectedIndices()
/*      */     {
/* 1838 */       return this.selectedIndices;
/*      */     }
/*      */ 
/*      */     public ObservableList<S> getSelectedItems()
/*      */     {
/* 1844 */       return this.selectedItems;
/*      */     }
/*      */ 
/*      */     public ObservableList<TablePosition> getSelectedCells()
/*      */     {
/* 1849 */       return this.selectedCellsSeq;
/*      */     }
/*      */ 
/*      */     private void updateSelection(ListChangeListener.Change<? extends S> paramChange)
/*      */     {
/* 1865 */       while (paramChange.next())
/*      */       {
/*      */         int i;
/* 1866 */         if (paramChange.wasReplaced()) {
/* 1867 */           if (paramChange.getList().isEmpty())
/*      */           {
/* 1869 */             clearSelection();
/*      */           } else {
/* 1871 */             i = getSelectedIndex();
/*      */ 
/* 1873 */             if (this.previousModelSize == paramChange.getRemovedSize())
/*      */             {
/* 1875 */               clearSelection();
/* 1876 */             } else if ((i < getRowCount()) && (i >= 0))
/*      */             {
/* 1878 */               clearSelection(i);
/* 1879 */               select(i);
/*      */             }
/*      */             else {
/* 1882 */               clearSelection();
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/* 1887 */         else if ((paramChange.wasAdded()) || (paramChange.wasRemoved())) {
/* 1888 */           i = paramChange.getFrom();
/* 1889 */           int j = paramChange.wasAdded() ? paramChange.getAddedSize() : -paramChange.getRemovedSize();
/*      */ 
/* 1891 */           if (i < 0) return;
/*      */ 
/* 1893 */           ArrayList localArrayList1 = new ArrayList(this.selectedCells.size());
/*      */           TablePosition localTablePosition1;
/* 1895 */           for (int m = 0; m < this.selectedCells.size(); m++) {
/* 1896 */             localTablePosition1 = (TablePosition)this.selectedCells.get(m);
/* 1897 */             int i1 = localTablePosition1.getRow() < i ? localTablePosition1.getRow() : localTablePosition1.getRow() + j;
/* 1898 */             if (i1 >= 0) {
/* 1899 */               localArrayList1.add(new TablePosition(getTableView(), i1, localTablePosition1.getTableColumn()));
/*      */             }
/*      */           }
/* 1902 */           quietClearSelection();
/*      */ 
/* 1905 */           for (m = 0; m < localArrayList1.size(); m++) {
/* 1906 */             localTablePosition1 = (TablePosition)localArrayList1.get(m);
/* 1907 */             select(localTablePosition1.getRow(), localTablePosition1.getTableColumn());
/*      */           }
/* 1909 */         } else if (paramChange.wasPermutated())
/*      */         {
/* 1922 */           i = paramChange.getTo() - paramChange.getFrom();
/* 1923 */           HashMap localHashMap = new HashMap(i);
/* 1924 */           for (int k = paramChange.getFrom(); k < paramChange.getTo(); k++) {
/* 1925 */             localHashMap.put(Integer.valueOf(k), Integer.valueOf(paramChange.getPermutation(k)));
/*      */           }
/*      */ 
/* 1929 */           ArrayList localArrayList2 = new ArrayList(getSelectedCells());
/*      */ 
/* 1933 */           clearSelection();
/*      */ 
/* 1936 */           ArrayList localArrayList3 = new ArrayList(getSelectedIndices().size());
/*      */ 
/* 1939 */           for (int n = 0; n < localArrayList2.size(); n++) {
/* 1940 */             TablePosition localTablePosition2 = (TablePosition)localArrayList2.get(n);
/*      */ 
/* 1942 */             if (localHashMap.containsKey(Integer.valueOf(localTablePosition2.getRow()))) {
/* 1943 */               Integer localInteger = (Integer)localHashMap.get(Integer.valueOf(localTablePosition2.getRow()));
/* 1944 */               localArrayList3.add(new TablePosition(localTablePosition2.getTableView(), localInteger.intValue(), localTablePosition2.getTableColumn()));
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1949 */           quietClearSelection();
/* 1950 */           this.selectedCells.setAll(localArrayList3);
/* 1951 */           this.selectedCellsSeq.callObservers(new NonIterableChange.SimpleAddChange(0, localArrayList3.size(), this.selectedCellsSeq));
/*      */         }
/*      */       }
/*      */ 
/* 1955 */       this.previousModelSize = getRowCount();
/*      */     }
/*      */ 
/*      */     public void clearAndSelect(int paramInt)
/*      */     {
/* 1965 */       clearAndSelect(paramInt, null);
/*      */     }
/*      */ 
/*      */     public void clearAndSelect(int paramInt, TableColumn<S, ?> paramTableColumn) {
/* 1969 */       quietClearSelection();
/* 1970 */       select(paramInt, paramTableColumn);
/*      */     }
/*      */ 
/*      */     public void select(int paramInt) {
/* 1974 */       select(paramInt, null);
/*      */     }
/*      */ 
/*      */     public void select(int paramInt, TableColumn<S, ?> paramTableColumn)
/*      */     {
/* 1979 */       if ((paramInt < 0) || (paramInt >= getRowCount())) return;
/*      */ 
/* 1983 */       if ((isCellSelectionEnabled()) && (paramTableColumn == null)) return;
/*      */ 
/* 1989 */       TablePosition localTablePosition = new TablePosition(getTableView(), paramInt, paramTableColumn);
/*      */ 
/* 1991 */       if (getSelectionMode() == SelectionMode.SINGLE) {
/* 1992 */         quietClearSelection();
/*      */       }
/*      */ 
/* 1995 */       if (!this.selectedCells.contains(localTablePosition)) {
/* 1996 */         this.selectedCells.add(localTablePosition);
/*      */       }
/*      */ 
/* 1999 */       updateSelectedIndex(paramInt);
/* 2000 */       focus(paramInt, paramTableColumn);
/*      */     }
/*      */ 
/*      */     public void select(S paramS) {
/* 2004 */       if (getTableModel() == null) return;
/*      */ 
/* 2009 */       Object localObject = null;
/* 2010 */       for (int i = 0; i < getRowCount(); i++) {
/* 2011 */         localObject = getTableModel().get(i);
/* 2012 */         if (localObject != null)
/*      */         {
/* 2014 */           if (localObject.equals(paramS)) {
/* 2015 */             if (isSelected(i)) {
/* 2016 */               return;
/*      */             }
/*      */ 
/* 2019 */             if (getSelectionMode() == SelectionMode.SINGLE) {
/* 2020 */               quietClearSelection();
/*      */             }
/*      */ 
/* 2023 */             select(i);
/* 2024 */             return;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2033 */       setSelectedItem(paramS);
/*      */     }
/*      */ 
/*      */     public void selectIndices(int paramInt, int[] paramArrayOfInt) {
/* 2037 */       if (paramArrayOfInt == null) {
/* 2038 */         select(paramInt);
/* 2039 */         return;
/*      */       }
/*      */ 
/* 2046 */       int i = getRowCount();
/*      */       int j;
/* 2048 */       if (getSelectionMode() == SelectionMode.SINGLE) {
/* 2049 */         quietClearSelection();
/*      */ 
/* 2051 */         for (j = paramArrayOfInt.length - 1; j >= 0; j--) {
/* 2052 */           int k = paramArrayOfInt[j];
/* 2053 */           if ((k >= 0) && (k < i)) {
/* 2054 */             select(k);
/* 2055 */             break;
/*      */           }
/*      */         }
/*      */ 
/* 2059 */         if ((this.selectedCells.isEmpty()) && 
/* 2060 */           (paramInt > 0) && (paramInt < i))
/* 2061 */           select(paramInt);
/*      */       }
/*      */       else
/*      */       {
/* 2065 */         j = -1;
/* 2066 */         ArrayList localArrayList = new ArrayList();
/*      */ 
/* 2068 */         if ((paramInt >= 0) && (paramInt < i)) {
/* 2069 */           localArrayList.add(new TablePosition(getTableView(), paramInt, null));
/* 2070 */           j = paramInt;
/*      */         }
/*      */ 
/* 2073 */         for (int m = 0; m < paramArrayOfInt.length; m++) {
/* 2074 */           int n = paramArrayOfInt[m];
/* 2075 */           if ((n >= 0) && (n < i)) {
/* 2076 */             j = n;
/* 2077 */             TablePosition localTablePosition = new TablePosition(getTableView(), n, null);
/* 2078 */             if (!this.selectedCells.contains(localTablePosition))
/*      */             {
/* 2080 */               localArrayList.add(localTablePosition);
/*      */             }
/*      */           }
/*      */         }
/* 2083 */         this.selectedCells.addAll(localArrayList);
/*      */ 
/* 2085 */         if (j != -1)
/* 2086 */           select(j);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void selectAll()
/*      */     {
/* 2092 */       if (getSelectionMode() == SelectionMode.SINGLE) return;
/*      */ 
/* 2094 */       quietClearSelection();
/* 2095 */       if (getTableModel() == null)
/*      */         return;
/*      */       ArrayList localArrayList;
/* 2097 */       if (isCellSelectionEnabled()) {
/* 2098 */         localArrayList = new ArrayList();
/*      */ 
/* 2100 */         TablePosition localTablePosition = null;
/* 2101 */         for (int j = 0; j < getTableView().getVisibleLeafColumns().size(); j++) {
/* 2102 */           TableColumn localTableColumn = (TableColumn)getTableView().getVisibleLeafColumns().get(j);
/* 2103 */           for (int k = 0; k < getRowCount(); k++) {
/* 2104 */             localTablePosition = new TablePosition(getTableView(), k, localTableColumn);
/* 2105 */             localArrayList.add(localTablePosition);
/*      */           }
/*      */         }
/* 2108 */         this.selectedCells.setAll(localArrayList);
/*      */ 
/* 2110 */         if (localTablePosition != null) {
/* 2111 */           select(localTablePosition.getRow(), localTablePosition.getTableColumn());
/* 2112 */           focus(localTablePosition.getRow(), localTablePosition.getTableColumn());
/*      */         }
/*      */       } else {
/* 2115 */         localArrayList = new ArrayList();
/* 2116 */         for (int i = 0; i < getRowCount(); i++) {
/* 2117 */           localArrayList.add(new TablePosition(getTableView(), i, null));
/*      */         }
/* 2119 */         this.selectedCells.setAll(localArrayList);
/* 2120 */         select(getRowCount() - 1);
/* 2121 */         focus((TablePosition)localArrayList.get(localArrayList.size() - 1));
/*      */       }
/*      */     }
/*      */ 
/*      */     public void clearSelection(int paramInt) {
/* 2126 */       clearSelection(paramInt, null);
/*      */     }
/*      */ 
/*      */     public void clearSelection(int paramInt, TableColumn<S, ?> paramTableColumn)
/*      */     {
/* 2131 */       TablePosition localTablePosition1 = new TablePosition(getTableView(), paramInt, paramTableColumn);
/*      */ 
/* 2133 */       boolean bool = isCellSelectionEnabled();
/*      */ 
/* 2135 */       for (TablePosition localTablePosition2 : getSelectedCells())
/* 2136 */         if (((!bool) && (localTablePosition2.getRow() == paramInt)) || ((bool) && (localTablePosition2.equals(localTablePosition1)))) {
/* 2137 */           this.selectedCells.remove(localTablePosition2);
/*      */ 
/* 2140 */           focus(paramInt);
/*      */ 
/* 2142 */           return;
/*      */         }
/*      */     }
/*      */ 
/*      */     public void clearSelection()
/*      */     {
/* 2148 */       updateSelectedIndex(-1);
/* 2149 */       focus(-1);
/* 2150 */       quietClearSelection();
/*      */     }
/*      */ 
/*      */     private void quietClearSelection() {
/* 2154 */       this.selectedCells.clear();
/*      */     }
/*      */ 
/*      */     public boolean isSelected(int paramInt) {
/* 2158 */       return isSelected(paramInt, null);
/*      */     }
/*      */ 
/*      */     public boolean isSelected(int paramInt, TableColumn<S, ?> paramTableColumn)
/*      */     {
/* 2166 */       if ((isCellSelectionEnabled()) && (paramTableColumn == null)) return false;
/*      */ 
/* 2168 */       for (TablePosition localTablePosition : getSelectedCells()) {
/* 2169 */         int i = (!isCellSelectionEnabled()) || ((paramTableColumn == null) && (localTablePosition.getTableColumn() == null)) || ((paramTableColumn != null) && (paramTableColumn.equals(localTablePosition.getTableColumn()))) ? 1 : 0;
/*      */ 
/* 2173 */         if ((localTablePosition.getRow() == paramInt) && (i != 0)) {
/* 2174 */           return true;
/*      */         }
/*      */       }
/* 2177 */       return false;
/*      */     }
/*      */ 
/*      */     public boolean isEmpty() {
/* 2181 */       return this.selectedCells.isEmpty();
/*      */     }
/*      */ 
/*      */     public void selectPrevious() {
/* 2185 */       if (isCellSelectionEnabled())
/*      */       {
/* 2188 */         TablePosition localTablePosition = getFocusedCell();
/* 2189 */         if (localTablePosition.getColumn() - 1 >= 0)
/*      */         {
/* 2191 */           select(localTablePosition.getRow(), getTableColumn(localTablePosition.getTableColumn(), -1));
/* 2192 */         } else if (localTablePosition.getRow() < getRowCount() - 1)
/*      */         {
/* 2194 */           select(localTablePosition.getRow() - 1, getTableColumn(getTableView().getVisibleLeafColumns().size() - 1));
/*      */         }
/*      */       } else {
/* 2197 */         int i = getFocusedIndex();
/* 2198 */         if (i == -1)
/* 2199 */           select(getRowCount() - 1);
/* 2200 */         else if (i > 0)
/* 2201 */           select(i - 1);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void selectNext()
/*      */     {
/* 2207 */       if (isCellSelectionEnabled())
/*      */       {
/* 2210 */         TablePosition localTablePosition = getFocusedCell();
/* 2211 */         if (localTablePosition.getColumn() + 1 < getTableView().getVisibleLeafColumns().size())
/*      */         {
/* 2213 */           select(localTablePosition.getRow(), getTableColumn(localTablePosition.getTableColumn(), 1));
/* 2214 */         } else if (localTablePosition.getRow() < getRowCount() - 1)
/*      */         {
/* 2216 */           select(localTablePosition.getRow() + 1, getTableColumn(0));
/*      */         }
/*      */       } else {
/* 2219 */         int i = getFocusedIndex();
/* 2220 */         if (i == -1)
/* 2221 */           select(0);
/* 2222 */         else if (i < getRowCount() - 1)
/* 2223 */           select(i + 1);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void selectAboveCell()
/*      */     {
/* 2229 */       TablePosition localTablePosition = getFocusedCell();
/* 2230 */       if (localTablePosition.getRow() == -1)
/* 2231 */         select(getRowCount() - 1);
/* 2232 */       else if (localTablePosition.getRow() > 0)
/* 2233 */         select(localTablePosition.getRow() - 1, localTablePosition.getTableColumn());
/*      */     }
/*      */ 
/*      */     public void selectBelowCell()
/*      */     {
/* 2238 */       TablePosition localTablePosition = getFocusedCell();
/*      */ 
/* 2240 */       if (localTablePosition.getRow() == -1)
/* 2241 */         select(0);
/* 2242 */       else if (localTablePosition.getRow() < getRowCount() - 1)
/* 2243 */         select(localTablePosition.getRow() + 1, localTablePosition.getTableColumn());
/*      */     }
/*      */ 
/*      */     public void selectFirst()
/*      */     {
/* 2248 */       TablePosition localTablePosition = getFocusedCell();
/*      */ 
/* 2250 */       if (getSelectionMode() == SelectionMode.SINGLE) {
/* 2251 */         quietClearSelection();
/*      */       }
/*      */ 
/* 2254 */       if (getRowCount() > 0)
/* 2255 */         if (isCellSelectionEnabled())
/* 2256 */           select(0, localTablePosition.getTableColumn());
/*      */         else
/* 2258 */           select(0);
/*      */     }
/*      */ 
/*      */     public void selectLast()
/*      */     {
/* 2264 */       TablePosition localTablePosition = getFocusedCell();
/*      */ 
/* 2266 */       if (getSelectionMode() == SelectionMode.SINGLE) {
/* 2267 */         quietClearSelection();
/*      */       }
/*      */ 
/* 2270 */       int i = getRowCount();
/* 2271 */       if ((i > 0) && (getSelectedIndex() < i - 1))
/* 2272 */         if (isCellSelectionEnabled())
/* 2273 */           select(i - 1, localTablePosition.getTableColumn());
/*      */         else
/* 2275 */           select(i - 1);
/*      */     }
/*      */ 
/*      */     public void selectLeftCell()
/*      */     {
/* 2282 */       if (!isCellSelectionEnabled()) return;
/*      */ 
/* 2284 */       TablePosition localTablePosition = getFocusedCell();
/* 2285 */       if (localTablePosition.getColumn() - 1 >= 0)
/* 2286 */         select(localTablePosition.getRow(), getTableColumn(localTablePosition.getTableColumn(), -1));
/*      */     }
/*      */ 
/*      */     public void selectRightCell()
/*      */     {
/* 2292 */       if (!isCellSelectionEnabled()) return;
/*      */ 
/* 2294 */       TablePosition localTablePosition = getFocusedCell();
/* 2295 */       if (localTablePosition.getColumn() + 1 < getTableView().getVisibleLeafColumns().size())
/* 2296 */         select(localTablePosition.getRow(), getTableColumn(localTablePosition.getTableColumn(), 1));
/*      */     }
/*      */ 
/*      */     private TableColumn<S, ?> getTableColumn(int paramInt)
/*      */     {
/* 2309 */       return getTableView().getVisibleLeafColumn(paramInt);
/*      */     }
/*      */ 
/*      */     private TableColumn<S, ?> getTableColumn(TableColumn<S, ?> paramTableColumn, int paramInt)
/*      */     {
/* 2318 */       int i = getTableView().getVisibleLeafIndex(paramTableColumn);
/* 2319 */       int j = i + paramInt;
/* 2320 */       return getTableView().getVisibleLeafColumn(j);
/*      */     }
/*      */ 
/*      */     private void updateSelectedIndex(int paramInt) {
/* 2324 */       setSelectedIndex(paramInt);
/* 2325 */       setSelectedItem(getModelItem(paramInt));
/*      */     }
/*      */ 
/*      */     private void focus(int paramInt) {
/* 2329 */       focus(paramInt, null);
/*      */     }
/*      */ 
/*      */     private void focus(int paramInt, TableColumn<S, ?> paramTableColumn) {
/* 2333 */       focus(new TablePosition(getTableView(), paramInt, paramTableColumn));
/*      */     }
/*      */ 
/*      */     private void focus(TablePosition paramTablePosition) {
/* 2337 */       if (getTableView().getFocusModel() == null) return;
/*      */ 
/* 2339 */       getTableView().getFocusModel().focus(paramTablePosition.getRow(), paramTablePosition.getTableColumn());
/*      */     }
/*      */ 
/*      */     private int getFocusedIndex() {
/* 2343 */       return getFocusedCell().getRow();
/*      */     }
/*      */ 
/*      */     private TablePosition getFocusedCell() {
/* 2347 */       if (getTableView().getFocusModel() == null) {
/* 2348 */         return new TablePosition(getTableView(), -1, null);
/*      */       }
/* 2350 */       return getTableView().getFocusModel().getFocusedCell();
/*      */     }
/*      */ 
/*      */     private int getRowCount() {
/* 2354 */       return getTableModel() == null ? -1 : getTableModel().size();
/*      */     }
/*      */ 
/*      */     private S getModelItem(int paramInt) {
/* 2358 */       if (getTableModel() == null) return null;
/*      */ 
/* 2360 */       if ((paramInt < 0) || (paramInt >= getRowCount())) return null;
/*      */ 
/* 2362 */       return getTableModel().get(paramInt);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static abstract class TableViewSelectionModel<S> extends MultipleSelectionModel<S>
/*      */   {
/*      */     private final TableView<S> tableView;
/*      */     private BooleanProperty cellSelectionEnabled;
/*      */ 
/*      */     public TableViewSelectionModel(TableView<S> paramTableView)
/*      */     {
/* 1543 */       if (paramTableView == null) {
/* 1544 */         throw new NullPointerException("TableView can not be null");
/*      */       }
/*      */ 
/* 1547 */       this.tableView = paramTableView;
/*      */ 
/* 1549 */       selectedIndexProperty().addListener(new InvalidationListener()
/*      */       {
/*      */         public void invalidated(Observable paramAnonymousObservable)
/*      */         {
/* 1555 */           if (TableView.TableViewSelectionModel.this.getTableModel() == null) return;
/*      */ 
/* 1557 */           int i = TableView.TableViewSelectionModel.this.getSelectedIndex();
/* 1558 */           if ((i < 0) || (i >= TableView.TableViewSelectionModel.this.getTableModel().size()))
/* 1559 */             TableView.TableViewSelectionModel.this.setSelectedItem(null);
/*      */           else
/* 1561 */             TableView.TableViewSelectionModel.this.setSelectedItem(TableView.TableViewSelectionModel.this.getTableModel().get(i));
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     public abstract ObservableList<TablePosition> getSelectedCells();
/*      */ 
/*      */     public abstract boolean isSelected(int paramInt, TableColumn<S, ?> paramTableColumn);
/*      */ 
/*      */     public abstract void select(int paramInt, TableColumn<S, ?> paramTableColumn);
/*      */ 
/*      */     public abstract void clearAndSelect(int paramInt, TableColumn<S, ?> paramTableColumn);
/*      */ 
/*      */     public abstract void clearSelection(int paramInt, TableColumn<S, ?> paramTableColumn);
/*      */ 
/*      */     public abstract void selectLeftCell();
/*      */ 
/*      */     public abstract void selectRightCell();
/*      */ 
/*      */     public abstract void selectAboveCell();
/*      */ 
/*      */     public abstract void selectBelowCell();
/*      */ 
/*      */     public final BooleanProperty cellSelectionEnabledProperty()
/*      */     {
/* 1627 */       if (this.cellSelectionEnabled == null) {
/* 1628 */         this.cellSelectionEnabled = new BooleanPropertyBase() {
/*      */           protected void invalidated() {
/* 1630 */             get();
/* 1631 */             TableView.TableViewSelectionModel.this.clearSelection();
/* 1632 */             TableView.this.impl_pseudoClassStateChanged("cell-selection");
/* 1633 */             TableView.this.impl_pseudoClassStateChanged("row-selection");
/*      */           }
/*      */ 
/*      */           public Object getBean()
/*      */           {
/* 1638 */             return TableView.TableViewSelectionModel.this;
/*      */           }
/*      */ 
/*      */           public String getName()
/*      */           {
/* 1643 */             return "cellSelectionEnabled";
/*      */           }
/*      */         };
/*      */       }
/* 1647 */       return this.cellSelectionEnabled;
/*      */     }
/*      */     public final void setCellSelectionEnabled(boolean paramBoolean) {
/* 1650 */       cellSelectionEnabledProperty().set(paramBoolean);
/*      */     }
/*      */     public final boolean isCellSelectionEnabled() {
/* 1653 */       return this.cellSelectionEnabled == null ? false : this.cellSelectionEnabled.get();
/*      */     }
/*      */ 
/*      */     public TableView<S> getTableView()
/*      */     {
/* 1661 */       return this.tableView;
/*      */     }
/*      */ 
/*      */     protected List<S> getTableModel()
/*      */     {
/* 1670 */       return this.tableView.getItems();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ResizeFeatures<S>
/*      */   {
/*      */     private TableView<S> table;
/*      */     private TableColumn<S, ?> column;
/*      */     private Double delta;
/*      */ 
/*      */     public ResizeFeatures(TableView<S> paramTableView, TableColumn<S, ?> paramTableColumn, Double paramDouble)
/*      */     {
/* 1494 */       this.table = paramTableView;
/* 1495 */       this.column = paramTableColumn;
/* 1496 */       this.delta = paramDouble;
/*      */     }
/*      */ 
/*      */     public TableColumn<S, ?> getColumn()
/*      */     {
/* 1504 */       return this.column;
/*      */     }
/*      */ 
/*      */     public Double getDelta()
/*      */     {
/* 1510 */       return this.delta;
/*      */     }
/*      */ 
/*      */     public TableView<S> getTable()
/*      */     {
/* 1515 */       return this.table;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TableView
 * JD-Core Version:    0.6.2
 */