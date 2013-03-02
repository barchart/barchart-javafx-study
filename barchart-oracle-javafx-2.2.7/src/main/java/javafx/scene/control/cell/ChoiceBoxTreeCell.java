/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.ChoiceBox;
/*     */ import javafx.scene.control.SingleSelectionModel;
/*     */ import javafx.scene.control.TreeCell;
/*     */ import javafx.scene.control.TreeItem;
/*     */ import javafx.scene.control.TreeView;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class ChoiceBoxTreeCell<T> extends TreeCell<T>
/*     */ {
/*     */   private final ObservableList<T> items;
/*     */   private ChoiceBox<T> choiceBox;
/* 247 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(T[] paramArrayOfT)
/*     */   {
/*  77 */     return forTreeView(FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(ObservableList<T> paramObservableList)
/*     */   {
/* 101 */     return forTreeView(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 124 */     return forTreeView(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(StringConverter<T> paramStringConverter, final ObservableList<T> paramObservableList)
/*     */   {
/* 148 */     return new Callback() {
/*     */       public TreeCell<T> call(TreeView<T> paramAnonymousTreeView) {
/* 150 */         return new ChoiceBoxTreeCell(this.val$converter, paramObservableList);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public ChoiceBoxTreeCell()
/*     */   {
/* 179 */     this(FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public ChoiceBoxTreeCell(T[] paramArrayOfT)
/*     */   {
/* 190 */     this(FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ChoiceBoxTreeCell(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 206 */     this(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ChoiceBoxTreeCell(ObservableList<T> paramObservableList)
/*     */   {
/* 217 */     this(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public ChoiceBoxTreeCell(StringConverter<T> paramStringConverter, ObservableList<T> paramObservableList)
/*     */   {
/* 233 */     getStyleClass().add("choice-box-tree-cell");
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
/*     */   public ObservableList<T> getItems()
/*     */   {
/* 283 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 288 */     if ((!isEditable()) || (!getTreeView().isEditable())) {
/* 289 */       return;
/*     */     }
/* 291 */     if (this.choiceBox == null) {
/* 292 */       this.choiceBox = CellUtils.createChoiceBox(this, this.items);
/*     */     }
/*     */ 
/* 295 */     this.choiceBox.getSelectionModel().select(getTreeItem().getValue());
/*     */ 
/* 297 */     super.startEdit();
/* 298 */     setText(null);
/* 299 */     setGraphic(this.choiceBox);
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 304 */     super.cancelEdit();
/*     */ 
/* 306 */     setText(getConverter().toString(getItem()));
/* 307 */     setGraphic(null);
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 312 */     super.updateItem(paramT, paramBoolean);
/* 313 */     CellUtils.updateItem(this, this.choiceBox, getConverter());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ChoiceBoxTreeCell
 * JD-Core Version:    0.6.2
 */