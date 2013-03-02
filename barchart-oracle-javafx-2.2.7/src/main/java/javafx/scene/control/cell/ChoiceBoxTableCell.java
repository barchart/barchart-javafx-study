/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.ChoiceBox;
/*     */ import javafx.scene.control.SingleSelectionModel;
/*     */ import javafx.scene.control.TableCell;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class ChoiceBoxTableCell<S, T> extends TableCell<S, T>
/*     */ {
/*     */   private final ObservableList<T> items;
/*     */   private ChoiceBox<T> choiceBox;
/* 255 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(T[] paramArrayOfT)
/*     */   {
/*  82 */     return forTableColumn(null, paramArrayOfT);
/*     */   }
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 108 */     return forTableColumn(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(ObservableList<T> paramObservableList)
/*     */   {
/* 131 */     return forTableColumn(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(StringConverter<T> paramStringConverter, final ObservableList<T> paramObservableList)
/*     */   {
/* 157 */     return new Callback() {
/*     */       public TableCell<S, T> call(TableColumn<S, T> paramAnonymousTableColumn) {
/* 159 */         return new ChoiceBoxTableCell(this.val$converter, paramObservableList);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public ChoiceBoxTableCell()
/*     */   {
/* 188 */     this(FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public ChoiceBoxTableCell(T[] paramArrayOfT)
/*     */   {
/* 199 */     this(FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ChoiceBoxTableCell(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 215 */     this(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ChoiceBoxTableCell(ObservableList<T> paramObservableList)
/*     */   {
/* 226 */     this(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public ChoiceBoxTableCell(StringConverter<T> paramStringConverter, ObservableList<T> paramObservableList)
/*     */   {
/* 242 */     getStyleClass().add("choice-box-table-cell");
/* 243 */     this.items = paramObservableList;
/* 244 */     setConverter(paramStringConverter != null ? paramStringConverter : CellUtils.defaultStringConverter());
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<StringConverter<T>> converterProperty()
/*     */   {
/* 262 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public final void setConverter(StringConverter<T> paramStringConverter)
/*     */   {
/* 269 */     converterProperty().set(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final StringConverter<T> getConverter()
/*     */   {
/* 276 */     return (StringConverter)converterProperty().get();
/*     */   }
/*     */ 
/*     */   public ObservableList<T> getItems()
/*     */   {
/* 291 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 296 */     if ((!isEditable()) || (!getTableView().isEditable()) || (!getTableColumn().isEditable())) {
/* 297 */       return;
/*     */     }
/*     */ 
/* 300 */     if (this.choiceBox == null) {
/* 301 */       this.choiceBox = CellUtils.createChoiceBox(this, this.items);
/*     */     }
/*     */ 
/* 304 */     this.choiceBox.getSelectionModel().select(getItem());
/*     */ 
/* 306 */     super.startEdit();
/* 307 */     setText(null);
/* 308 */     setGraphic(this.choiceBox);
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 313 */     super.cancelEdit();
/*     */ 
/* 315 */     setText(getConverter().toString(getItem()));
/* 316 */     setGraphic(null);
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 321 */     super.updateItem(paramT, paramBoolean);
/* 322 */     CellUtils.updateItem(this, this.choiceBox, getConverter());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ChoiceBoxTableCell
 * JD-Core Version:    0.6.2
 */