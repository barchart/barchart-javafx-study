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
/*     */ import javafx.scene.control.TableCell;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class ComboBoxTableCell<S, T> extends TableCell<S, T>
/*     */ {
/*     */   private final ObservableList<T> items;
/*     */   private ComboBox<T> comboBox;
/* 259 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/* 285 */   private BooleanProperty comboBoxEditable = new SimpleBooleanProperty(this, "comboBoxEditable");
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(T[] paramArrayOfT)
/*     */   {
/*  85 */     return forTableColumn(null, paramArrayOfT);
/*     */   }
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 111 */     return forTableColumn(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(ObservableList<T> paramObservableList)
/*     */   {
/* 134 */     return forTableColumn(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(StringConverter<T> paramStringConverter, final ObservableList<T> paramObservableList)
/*     */   {
/* 160 */     return new Callback() {
/*     */       public TableCell<S, T> call(TableColumn<S, T> paramAnonymousTableColumn) {
/* 162 */         return new ComboBoxTableCell(this.val$converter, paramObservableList);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public ComboBoxTableCell()
/*     */   {
/* 191 */     this(FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public ComboBoxTableCell(T[] paramArrayOfT)
/*     */   {
/* 202 */     this(FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ComboBoxTableCell(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 218 */     this(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ComboBoxTableCell(ObservableList<T> paramObservableList)
/*     */   {
/* 229 */     this(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public ComboBoxTableCell(StringConverter<T> paramStringConverter, ObservableList<T> paramObservableList)
/*     */   {
/* 245 */     getStyleClass().add("combo-box-table-cell");
/* 246 */     this.items = paramObservableList;
/* 247 */     setConverter(paramStringConverter != null ? paramStringConverter : CellUtils.defaultStringConverter());
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<StringConverter<T>> converterProperty()
/*     */   {
/* 266 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public final void setConverter(StringConverter<T> paramStringConverter)
/*     */   {
/* 273 */     converterProperty().set(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final StringConverter<T> getConverter()
/*     */   {
/* 280 */     return (StringConverter)converterProperty().get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty comboBoxEditableProperty()
/*     */   {
/* 293 */     return this.comboBoxEditable;
/*     */   }
/*     */ 
/*     */   public final void setComboBoxEditable(boolean paramBoolean)
/*     */   {
/* 301 */     comboBoxEditableProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isComboBoxEditable()
/*     */   {
/* 308 */     return comboBoxEditableProperty().get();
/*     */   }
/*     */ 
/*     */   public ObservableList<T> getItems()
/*     */   {
/* 323 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 328 */     if ((!isEditable()) || (!getTableView().isEditable()) || (!getTableColumn().isEditable())) {
/* 329 */       return;
/*     */     }
/*     */ 
/* 332 */     if (this.comboBox == null) {
/* 333 */       this.comboBox = CellUtils.createComboBox(this, this.items);
/* 334 */       this.comboBox.editableProperty().bind(comboBoxEditableProperty());
/*     */     }
/*     */ 
/* 337 */     this.comboBox.getSelectionModel().select(getItem());
/*     */ 
/* 339 */     super.startEdit();
/* 340 */     setText(null);
/* 341 */     setGraphic(this.comboBox);
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 346 */     super.cancelEdit();
/*     */ 
/* 348 */     setText(getConverter().toString(getItem()));
/* 349 */     setGraphic(null);
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 354 */     super.updateItem(paramT, paramBoolean);
/* 355 */     CellUtils.updateItem(this, this.comboBox, getConverter());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ComboBoxTableCell
 * JD-Core Version:    0.6.2
 */