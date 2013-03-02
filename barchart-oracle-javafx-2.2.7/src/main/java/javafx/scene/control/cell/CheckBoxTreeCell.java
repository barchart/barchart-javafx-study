/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.CheckBox;
/*     */ import javafx.scene.control.CheckBoxTreeItem;
/*     */ import javafx.scene.control.TreeCell;
/*     */ import javafx.scene.control.TreeItem;
/*     */ import javafx.scene.control.TreeView;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class CheckBoxTreeCell<T> extends TreeCell<T>
/*     */ {
/*     */   private final CheckBox checkBox;
/*     */   private ObservableValue<Boolean> booleanProperty;
/*     */   private BooleanProperty indeterminateProperty;
/* 431 */   private ObjectProperty<StringConverter<TreeItem<T>>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/* 458 */   private ObjectProperty<Callback<TreeItem<T>, ObservableValue<Boolean>>> selectedStateCallback = new SimpleObjectProperty(this, "selectedStateCallback");
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView()
/*     */   {
/* 127 */     Callback local1 = new Callback()
/*     */     {
/*     */       public ObservableValue<Boolean> call(TreeItem<T> paramAnonymousTreeItem) {
/* 130 */         if ((paramAnonymousTreeItem instanceof CheckBoxTreeItem)) {
/* 131 */           return ((CheckBoxTreeItem)paramAnonymousTreeItem).selectedProperty();
/*     */         }
/* 133 */         return null;
/*     */       }
/*     */     };
/* 136 */     Callback local2 = new Callback()
/*     */     {
/*     */       public ObservableValue<Boolean> call(TreeItem<T> paramAnonymousTreeItem) {
/* 139 */         if ((paramAnonymousTreeItem instanceof CheckBoxTreeItem)) {
/* 140 */           return ((CheckBoxTreeItem)paramAnonymousTreeItem).indeterminateProperty();
/*     */         }
/* 142 */         return null;
/*     */       }
/*     */     };
/* 145 */     return forTreeView(local1, CellUtils.defaultTreeItemStringConverter(), local2);
/*     */   }
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(Callback<TreeItem<T>, ObservableValue<Boolean>> paramCallback)
/*     */   {
/* 183 */     return forTreeView(paramCallback, CellUtils.defaultTreeItemStringConverter());
/*     */   }
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(Callback<TreeItem<T>, ObservableValue<Boolean>> paramCallback, StringConverter<TreeItem<T>> paramStringConverter)
/*     */   {
/* 224 */     return forTreeView(paramCallback, paramStringConverter, null);
/*     */   }
/*     */ 
/*     */   private static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(Callback<TreeItem<T>, ObservableValue<Boolean>> paramCallback1, final StringConverter<TreeItem<T>> paramStringConverter, Callback<TreeItem<T>, ObservableValue<Boolean>> paramCallback2)
/*     */   {
/* 291 */     return new Callback() {
/*     */       public TreeCell<T> call(TreeView<T> paramAnonymousTreeView) {
/* 293 */         return new CheckBoxTreeCell(this.val$getSelectedProperty, paramStringConverter);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeCell()
/*     */   {
/* 329 */     this(new Callback() {
/*     */       public ObservableValue<Boolean> call(TreeItem<T> paramAnonymousTreeItem) {
/* 331 */         if ((paramAnonymousTreeItem instanceof CheckBoxTreeItem)) {
/* 332 */           return ((CheckBoxTreeItem)paramAnonymousTreeItem).selectedProperty();
/*     */         }
/* 334 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeCell(Callback<TreeItem<T>, ObservableValue<Boolean>> paramCallback)
/*     */   {
/* 368 */     this(paramCallback, CellUtils.defaultTreeItemStringConverter(), null);
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeCell(Callback<TreeItem<T>, ObservableValue<Boolean>> paramCallback, StringConverter<TreeItem<T>> paramStringConverter)
/*     */   {
/* 403 */     this(paramCallback, paramStringConverter, null);
/*     */   }
/*     */ 
/*     */   private CheckBoxTreeCell(Callback<TreeItem<T>, ObservableValue<Boolean>> paramCallback1, StringConverter<TreeItem<T>> paramStringConverter, Callback<TreeItem<T>, ObservableValue<Boolean>> paramCallback2)
/*     */   {
/* 410 */     if (paramCallback1 == null) {
/* 411 */       throw new NullPointerException("getSelectedProperty can not be null");
/*     */     }
/* 413 */     getStyleClass().add("choice-box-tree-cell");
/* 414 */     setSelectedStateCallback(paramCallback1);
/* 415 */     setConverter(paramStringConverter);
/*     */ 
/* 417 */     this.checkBox = new CheckBox();
/* 418 */     this.checkBox.setAllowIndeterminate(false);
/* 419 */     setGraphic(this.checkBox);
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<StringConverter<TreeItem<T>>> converterProperty()
/*     */   {
/* 438 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public final void setConverter(StringConverter<TreeItem<T>> paramStringConverter)
/*     */   {
/* 445 */     converterProperty().set(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final StringConverter<TreeItem<T>> getConverter()
/*     */   {
/* 452 */     return (StringConverter)converterProperty().get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Callback<TreeItem<T>, ObservableValue<Boolean>>> selectedStateCallbackProperty()
/*     */   {
/* 468 */     return this.selectedStateCallback;
/*     */   }
/*     */ 
/*     */   public final void setSelectedStateCallback(Callback<TreeItem<T>, ObservableValue<Boolean>> paramCallback)
/*     */   {
/* 475 */     selectedStateCallbackProperty().set(paramCallback);
/*     */   }
/*     */ 
/*     */   public final Callback<TreeItem<T>, ObservableValue<Boolean>> getSelectedStateCallback()
/*     */   {
/* 482 */     return (Callback)selectedStateCallbackProperty().get();
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 495 */     super.updateItem(paramT, paramBoolean);
/*     */ 
/* 497 */     if (paramBoolean) {
/* 498 */       setText(null);
/* 499 */       setGraphic(null);
/*     */     } else {
/* 501 */       StringConverter localStringConverter = getConverter();
/* 502 */       Callback localCallback = getSelectedStateCallback();
/*     */ 
/* 505 */       setText(localStringConverter.toString(getTreeItem()));
/* 506 */       setGraphic(this.checkBox);
/*     */ 
/* 509 */       if (this.booleanProperty != null) {
/* 510 */         this.checkBox.selectedProperty().unbindBidirectional((BooleanProperty)this.booleanProperty);
/*     */       }
/* 512 */       if (this.indeterminateProperty != null) {
/* 513 */         this.checkBox.indeterminateProperty().unbindBidirectional(this.indeterminateProperty);
/*     */       }
/*     */ 
/* 518 */       if ((getTreeItem() instanceof CheckBoxTreeItem)) {
/* 519 */         CheckBoxTreeItem localCheckBoxTreeItem = (CheckBoxTreeItem)getTreeItem();
/* 520 */         this.booleanProperty = localCheckBoxTreeItem.selectedProperty();
/* 521 */         this.checkBox.selectedProperty().bindBidirectional((BooleanProperty)this.booleanProperty);
/*     */ 
/* 523 */         this.indeterminateProperty = localCheckBoxTreeItem.indeterminateProperty();
/* 524 */         this.checkBox.indeterminateProperty().bindBidirectional(this.indeterminateProperty);
/*     */       } else {
/* 526 */         this.booleanProperty = ((ObservableValue)localCallback.call(getTreeItem()));
/* 527 */         if (this.booleanProperty != null)
/* 528 */           this.checkBox.selectedProperty().bindBidirectional((BooleanProperty)this.booleanProperty);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.CheckBoxTreeCell
 * JD-Core Version:    0.6.2
 */