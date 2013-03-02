/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.ChoiceBox;
/*     */ import javafx.scene.control.ListCell;
/*     */ import javafx.scene.control.ListView;
/*     */ import javafx.scene.control.SingleSelectionModel;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class ChoiceBoxListCell<T> extends ListCell<T>
/*     */ {
/*     */   private final ObservableList<T> items;
/*     */   private ChoiceBox<T> choiceBox;
/* 245 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/*     */   public static <T> Callback<ListView<T>, ListCell<T>> forListView(T[] paramArrayOfT)
/*     */   {
/*  80 */     return forListView(FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public static <T> Callback<ListView<T>, ListCell<T>> forListView(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 103 */     return forListView(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public static <T> Callback<ListView<T>, ListCell<T>> forListView(ObservableList<T> paramObservableList)
/*     */   {
/* 123 */     return forListView(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public static <T> Callback<ListView<T>, ListCell<T>> forListView(StringConverter<T> paramStringConverter, final ObservableList<T> paramObservableList)
/*     */   {
/* 146 */     return new Callback() {
/*     */       public ListCell<T> call(ListView<T> paramAnonymousListView) {
/* 148 */         return new ChoiceBoxListCell(this.val$converter, paramObservableList);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public ChoiceBoxListCell()
/*     */   {
/* 177 */     this(FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public ChoiceBoxListCell(T[] paramArrayOfT)
/*     */   {
/* 188 */     this(FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ChoiceBoxListCell(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 204 */     this(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ChoiceBoxListCell(ObservableList<T> paramObservableList)
/*     */   {
/* 215 */     this(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public ChoiceBoxListCell(StringConverter<T> paramStringConverter, ObservableList<T> paramObservableList)
/*     */   {
/* 231 */     getStyleClass().add("choice-box-list-cell");
/* 232 */     this.items = paramObservableList;
/* 233 */     setConverter(paramStringConverter != null ? paramStringConverter : CellUtils.defaultStringConverter());
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<StringConverter<T>> converterProperty()
/*     */   {
/* 252 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public final void setConverter(StringConverter<T> paramStringConverter)
/*     */   {
/* 259 */     converterProperty().set(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final StringConverter<T> getConverter()
/*     */   {
/* 266 */     return (StringConverter)converterProperty().get();
/*     */   }
/*     */ 
/*     */   public ObservableList<T> getItems()
/*     */   {
/* 281 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 286 */     if ((!isEditable()) || (!getListView().isEditable())) {
/* 287 */       return;
/*     */     }
/*     */ 
/* 290 */     if (this.choiceBox == null) {
/* 291 */       this.choiceBox = CellUtils.createChoiceBox(this, this.items);
/*     */     }
/*     */ 
/* 294 */     this.choiceBox.getSelectionModel().select(getItem());
/*     */ 
/* 296 */     super.startEdit();
/* 297 */     setText(null);
/* 298 */     setGraphic(this.choiceBox);
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 303 */     super.cancelEdit();
/*     */ 
/* 305 */     setText(getConverter().toString(getItem()));
/* 306 */     setGraphic(null);
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 311 */     super.updateItem(paramT, paramBoolean);
/* 312 */     CellUtils.updateItem(this, this.choiceBox, getConverter());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ChoiceBoxListCell
 * JD-Core Version:    0.6.2
 */