/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.scene.control.FocusableTextField;
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.beans.property.SimpleIntegerProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.beans.value.WeakChangeListener;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class ComboBox<T> extends ComboBoxBase<T>
/*     */ {
/* 262 */   private ObjectProperty<ObservableList<T>> items = new SimpleObjectProperty(this, "items")
/*     */   {
/*     */     protected void invalidated()
/*     */     {
/* 266 */       if ((ComboBox.this.getSelectionModel() instanceof ComboBox.ComboBoxSelectionModel)) {
/* 267 */         ((ComboBox.ComboBoxSelectionModel)ComboBox.this.getSelectionModel()).updateItemsObserver(null, ComboBox.this.getItems());
/*     */       }
/* 269 */       if ((ComboBox.this.getSkin() instanceof ComboBoxListViewSkin)) {
/* 270 */         ComboBoxListViewSkin localComboBoxListViewSkin = (ComboBoxListViewSkin)ComboBox.this.getSkin();
/* 271 */         localComboBoxListViewSkin.updateListViewItems();
/*     */       }
/*     */     }
/* 262 */   };
/*     */ 
/* 287 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter", defaultStringConverter());
/*     */ 
/* 299 */   private ObjectProperty<Callback<ListView<T>, ListCell<T>>> cellFactory = new SimpleObjectProperty(this, "cellFactory");
/*     */ 
/* 315 */   private ObjectProperty<ListCell<T>> buttonCell = new SimpleObjectProperty(this, "buttonCell");
/*     */ 
/* 326 */   private ObjectProperty<SingleSelectionModel<T>> selectionModel = new SimpleObjectProperty(this, "selectionModel") {
/* 327 */     private SingleSelectionModel<T> oldSM = null;
/*     */ 
/* 329 */     protected void invalidated() { if (this.oldSM != null) {
/* 330 */         this.oldSM.selectedItemProperty().removeListener(ComboBox.this.selectedItemListener);
/*     */       }
/* 332 */       SingleSelectionModel localSingleSelectionModel = (SingleSelectionModel)get();
/* 333 */       this.oldSM = localSingleSelectionModel;
/* 334 */       if (localSingleSelectionModel != null)
/* 335 */         localSingleSelectionModel.selectedItemProperty().addListener(ComboBox.this.selectedItemListener);
/*     */     }
/* 326 */   };
/*     */ 
/* 350 */   private IntegerProperty visibleRowCount = new SimpleIntegerProperty(this, "visibleRowCount", 10);
/*     */   private FocusableTextField textField;
/*     */   private ReadOnlyObjectWrapper<TextField> editor;
/* 387 */   private ChangeListener<T> selectedItemListener = new ChangeListener() {
/*     */     public void changed(ObservableValue<? extends T> paramAnonymousObservableValue, T paramAnonymousT1, T paramAnonymousT2) {
/* 389 */       if ((ComboBox.this.wasSetAllCalled) && (paramAnonymousT2 == null))
/*     */       {
/* 399 */         ComboBox.this.wasSetAllCalled = false;
/*     */       }
/* 401 */       else ComboBox.this.updateValue(paramAnonymousT2);
/*     */     }
/* 387 */   };
/*     */   private static final String DEFAULT_STYLE_CLASS = "combo-box";
/* 431 */   private boolean wasSetAllCalled = false;
/* 432 */   private int previousItemCount = -1;
/*     */ 
/*     */   private static <T> StringConverter<T> defaultStringConverter()
/*     */   {
/* 175 */     return new StringConverter() {
/*     */       public String toString(T paramAnonymousT) {
/* 177 */         return paramAnonymousT == null ? null : paramAnonymousT.toString();
/*     */       }
/*     */ 
/*     */       public T fromString(String paramAnonymousString) {
/* 181 */         return paramAnonymousString;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public ComboBox()
/*     */   {
/* 200 */     this(FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public ComboBox(ObservableList<T> paramObservableList)
/*     */   {
/* 208 */     getStyleClass().add("combo-box");
/* 209 */     setItems(paramObservableList);
/* 210 */     setSelectionModel(new ComboBoxSelectionModel(this));
/*     */ 
/* 215 */     valueProperty().addListener(new Object() {
/*     */       public void changed(ObservableValue<? extends T> paramAnonymousObservableValue, T paramAnonymousT1, T paramAnonymousT2) {
/* 217 */         if (ComboBox.this.getItems() == null) return;
/*     */ 
/* 219 */         SingleSelectionModel localSingleSelectionModel = ComboBox.this.getSelectionModel();
/* 220 */         int i = ComboBox.this.getItems().indexOf(paramAnonymousT2);
/*     */ 
/* 222 */         if (i == -1) {
/* 223 */           localSingleSelectionModel.setSelectedItem(paramAnonymousT2);
/*     */         }
/*     */         else
/*     */         {
/* 234 */           Object localObject = localSingleSelectionModel.getSelectedItem();
/* 235 */           if ((localObject == null) || (!localObject.equals(ComboBox.this.getValue())))
/* 236 */             localSingleSelectionModel.clearAndSelect(i);
/*     */         }
/*     */       }
/*     */     });
/* 242 */     editableProperty().addListener(new InvalidationListener()
/*     */     {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 245 */         ComboBox.this.getSelectionModel().clearSelection();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public final void setItems(ObservableList<T> paramObservableList)
/*     */   {
/* 275 */     itemsProperty().set(paramObservableList); } 
/* 276 */   public final ObservableList<T> getItems() { return (ObservableList)this.items.get(); } 
/* 277 */   public ObjectProperty<ObservableList<T>> itemsProperty() { return this.items; }
/*     */ 
/*     */ 
/*     */   public ObjectProperty<StringConverter<T>> converterProperty()
/*     */   {
/* 286 */     return this.converter;
/*     */   }
/*     */   public final void setConverter(StringConverter<T> paramStringConverter) {
/* 289 */     converterProperty().set(paramStringConverter); } 
/* 290 */   public final StringConverter<T> getConverter() { return (StringConverter)converterProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final void setCellFactory(Callback<ListView<T>, ListCell<T>> paramCallback)
/*     */   {
/* 301 */     cellFactoryProperty().set(paramCallback); } 
/* 302 */   public final Callback<ListView<T>, ListCell<T>> getCellFactory() { return (Callback)cellFactoryProperty().get(); } 
/* 303 */   public ObjectProperty<Callback<ListView<T>, ListCell<T>>> cellFactoryProperty() { return this.cellFactory; }
/*     */ 
/*     */ 
/*     */   public ObjectProperty<ListCell<T>> buttonCellProperty()
/*     */   {
/* 314 */     return this.buttonCell;
/*     */   }
/*     */   public final void setButtonCell(ListCell<T> paramListCell) {
/* 317 */     buttonCellProperty().set(paramListCell); } 
/* 318 */   public final ListCell<T> getButtonCell() { return (ListCell)buttonCellProperty().get(); }
/*     */ 
/*     */ 
/*     */   public final void setSelectionModel(SingleSelectionModel<T> paramSingleSelectionModel)
/*     */   {
/* 339 */     this.selectionModel.set(paramSingleSelectionModel); } 
/* 340 */   public final SingleSelectionModel<T> getSelectionModel() { return (SingleSelectionModel)this.selectionModel.get(); } 
/* 341 */   public final ObjectProperty<SingleSelectionModel<T>> selectionModelProperty() { return this.selectionModel; }
/*     */ 
/*     */ 
/*     */   public final void setVisibleRowCount(int paramInt)
/*     */   {
/* 352 */     this.visibleRowCount.set(paramInt); } 
/* 353 */   public final int getVisibleRowCount() { return this.visibleRowCount.get(); } 
/* 354 */   public final IntegerProperty visibleRowCountProperty() { return this.visibleRowCount; }
/*     */ 
/*     */ 
/*     */   public final TextField getEditor()
/*     */   {
/* 366 */     return (TextField)editorProperty().get();
/*     */   }
/*     */   public final ReadOnlyObjectProperty<TextField> editorProperty() {
/* 369 */     if (this.editor == null) {
/* 370 */       this.editor = new ReadOnlyObjectWrapper(this, "editor");
/* 371 */       this.textField = new FocusableTextField();
/* 372 */       this.editor.set(this.textField);
/*     */     }
/* 374 */     return this.editor.getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private void updateValue(T paramT)
/*     */   {
/* 415 */     if (!valueProperty().isBound())
/* 416 */       setValue(paramT);
/*     */   }
/*     */ 
/*     */   static class ComboBoxSelectionModel<T> extends SingleSelectionModel<T>
/*     */   {
/*     */     private final ComboBox<T> comboBox;
/* 468 */     private final ListChangeListener<T> itemsContentObserver = new ListChangeListener()
/*     */     {
/*     */       public void onChanged(ListChangeListener.Change<? extends T> paramAnonymousChange)
/*     */       {
/*     */         int i;
/* 470 */         if ((ComboBox.this.getItems() == null) || (ComboBox.this.getItems().isEmpty())) {
/* 471 */           ComboBox.ComboBoxSelectionModel.this.setSelectedIndex(-1);
/* 472 */         } else if ((ComboBox.ComboBoxSelectionModel.this.getSelectedIndex() == -1) && (ComboBox.ComboBoxSelectionModel.this.getSelectedItem() != null)) {
/* 473 */           i = ComboBox.this.getItems().indexOf(ComboBox.ComboBoxSelectionModel.this.getSelectedItem());
/* 474 */           if (i != -1) {
/* 475 */             ComboBox.ComboBoxSelectionModel.this.setSelectedIndex(i);
/*     */           }
/*     */         }
/*     */ 
/* 479 */         while (paramAnonymousChange.next()) {
/* 480 */           ComboBox.this.wasSetAllCalled = (ComboBox.this.previousItemCount == paramAnonymousChange.getRemovedSize());
/*     */ 
/* 483 */           if ((paramAnonymousChange.getFrom() <= ComboBox.ComboBoxSelectionModel.this.getSelectedIndex()) && (ComboBox.ComboBoxSelectionModel.this.getSelectedIndex() != -1) && ((paramAnonymousChange.wasAdded()) || (paramAnonymousChange.wasRemoved()))) {
/* 484 */             i = paramAnonymousChange.wasAdded() ? paramAnonymousChange.getAddedSize() : -paramAnonymousChange.getRemovedSize();
/* 485 */             ComboBox.ComboBoxSelectionModel.this.clearAndSelect(ComboBox.ComboBoxSelectionModel.this.getSelectedIndex() + i);
/*     */           }
/*     */         }
/*     */ 
/* 489 */         ComboBox.this.previousItemCount = ComboBox.ComboBoxSelectionModel.this.getItemCount();
/*     */       }
/* 468 */     };
/*     */ 
/* 494 */     private final ChangeListener<ObservableList<T>> itemsObserver = new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends ObservableList<T>> paramAnonymousObservableValue, ObservableList<T> paramAnonymousObservableList1, ObservableList<T> paramAnonymousObservableList2)
/*     */       {
/* 498 */         ComboBox.ComboBoxSelectionModel.this.updateItemsObserver(paramAnonymousObservableList1, paramAnonymousObservableList2);
/*     */       }
/* 494 */     };
/*     */ 
/* 502 */     private WeakListChangeListener weakItemsContentObserver = new WeakListChangeListener(this.itemsContentObserver);
/*     */ 
/* 505 */     private WeakChangeListener weakItemsObserver = new WeakChangeListener(this.itemsObserver);
/*     */ 
/*     */     public ComboBoxSelectionModel(ComboBox<T> paramComboBox)
/*     */     {
/* 439 */       if (paramComboBox == null) {
/* 440 */         throw new NullPointerException("ComboBox can not be null");
/*     */       }
/* 442 */       this.comboBox = paramComboBox;
/*     */ 
/* 444 */       selectedIndexProperty().addListener(new InvalidationListener()
/*     */       {
/*     */         public void invalidated(Observable paramAnonymousObservable)
/*     */         {
/* 448 */           ComboBox.ComboBoxSelectionModel.this.setSelectedItem(ComboBox.ComboBoxSelectionModel.this.getModelItem(ComboBox.ComboBoxSelectionModel.this.getSelectedIndex()));
/*     */         }
/*     */       });
/* 461 */       this.comboBox.itemsProperty().addListener(this.weakItemsObserver);
/* 462 */       if (this.comboBox.getItems() != null)
/* 463 */         this.comboBox.getItems().addListener(this.weakItemsContentObserver);
/*     */     }
/*     */ 
/*     */     private void updateItemsObserver(ObservableList<T> paramObservableList1, ObservableList<T> paramObservableList2)
/*     */     {
/* 510 */       if (paramObservableList1 != null) {
/* 511 */         paramObservableList1.removeListener(this.weakItemsContentObserver);
/*     */       }
/* 513 */       if (paramObservableList2 != null) {
/* 514 */         paramObservableList2.addListener(this.weakItemsContentObserver);
/*     */       }
/*     */ 
/* 519 */       setSelectedIndex(-1);
/*     */     }
/*     */ 
/*     */     protected T getModelItem(int paramInt)
/*     */     {
/* 524 */       ObservableList localObservableList = this.comboBox.getItems();
/* 525 */       if (localObservableList == null) return null;
/* 526 */       if ((paramInt < 0) || (paramInt >= localObservableList.size())) return null;
/* 527 */       return localObservableList.get(paramInt);
/*     */     }
/*     */ 
/*     */     protected int getItemCount() {
/* 531 */       ObservableList localObservableList = this.comboBox.getItems();
/* 532 */       return localObservableList == null ? 0 : localObservableList.size();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ComboBox
 * JD-Core Version:    0.6.2
 */