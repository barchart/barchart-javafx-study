/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.ComboBox;
/*     */ import javafx.scene.control.SingleSelectionModel;
/*     */ import javafx.scene.control.TreeCell;
/*     */ import javafx.scene.control.TreeItem;
/*     */ import javafx.scene.control.TreeView;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class ComboBoxTreeCell<T> extends TreeCell<T>
/*     */ {
/*     */   private final ObservableList<T> items;
/*     */   private ComboBox<T> comboBox;
/* 247 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/* 273 */   private BooleanProperty comboBoxEditable = new SimpleBooleanProperty(this, "comboBoxEditable");
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(T[] paramArrayOfT)
/*     */   {
/*  79 */     return forTreeView(FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(ObservableList<T> paramObservableList)
/*     */   {
/* 102 */     return forTreeView(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 125 */     return forTreeView(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(StringConverter<T> paramStringConverter, final ObservableList<T> paramObservableList)
/*     */   {
/* 149 */     return new Callback() {
/*     */       public TreeCell<T> call(TreeView<T> paramAnonymousTreeView) {
/* 151 */         return new ComboBoxTreeCell(this.val$converter, paramObservableList);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public ComboBoxTreeCell()
/*     */   {
/* 179 */     this(FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public ComboBoxTreeCell(T[] paramArrayOfT)
/*     */   {
/* 190 */     this(FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ComboBoxTreeCell(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 206 */     this(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ComboBoxTreeCell(ObservableList<T> paramObservableList)
/*     */   {
/* 217 */     this(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public ComboBoxTreeCell(StringConverter<T> paramStringConverter, ObservableList<T> paramObservableList)
/*     */   {
/* 233 */     getStyleClass().add("combo-box-tree-cell");
/* 234 */     this.items = paramObservableList;
/* 235 */     setConverter(paramStringConverter != null ? paramStringConverter : CellUtils.defaultStringConverter());
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<StringConverter<T>> converterProperty()
/*     */   {
/* 254 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public final void setConverter(StringConverter<T> paramStringConverter)
/*     */   {
/* 261 */     converterProperty().set(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final StringConverter<T> getConverter()
/*     */   {
/* 268 */     return (StringConverter)converterProperty().get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty comboBoxEditableProperty()
/*     */   {
/* 281 */     return this.comboBoxEditable;
/*     */   }
/*     */ 
/*     */   public final void setComboBoxEditable(boolean paramBoolean)
/*     */   {
/* 289 */     comboBoxEditableProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isComboBoxEditable()
/*     */   {
/* 296 */     return comboBoxEditableProperty().get();
/*     */   }
/*     */ 
/*     */   public ObservableList<T> getItems()
/*     */   {
/* 311 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 316 */     if ((!isEditable()) || (!getTreeView().isEditable())) {
/* 317 */       return;
/*     */     }
/* 319 */     if (this.comboBox == null) {
/* 320 */       this.comboBox = CellUtils.createComboBox(this, this.items);
/* 321 */       this.comboBox.editableProperty().bind(comboBoxEditableProperty());
/*     */     }
/*     */ 
/* 324 */     this.comboBox.getSelectionModel().select(getTreeItem().getValue());
/*     */ 
/* 326 */     super.startEdit();
/* 327 */     setText(null);
/* 328 */     setGraphic(this.comboBox);
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 333 */     super.cancelEdit();
/*     */ 
/* 335 */     setText(getConverter().toString(getItem()));
/* 336 */     setGraphic(null);
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 341 */     super.updateItem(paramT, paramBoolean);
/* 342 */     CellUtils.updateItem(this, this.comboBox, getConverter());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ComboBoxTreeCell
 * JD-Core Version:    0.6.2
 */