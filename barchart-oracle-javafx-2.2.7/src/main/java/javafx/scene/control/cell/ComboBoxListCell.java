/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.ComboBox;
/*     */ import javafx.scene.control.ListCell;
/*     */ import javafx.scene.control.ListView;
/*     */ import javafx.scene.control.SingleSelectionModel;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class ComboBoxListCell<T> extends ListCell<T>
/*     */ {
/*     */   private final ObservableList<T> items;
/*     */   private ComboBox<T> comboBox;
/* 244 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/* 270 */   private BooleanProperty comboBoxEditable = new SimpleBooleanProperty(this, "comboBoxEditable");
/*     */ 
/*     */   public static <T> Callback<ListView<T>, ListCell<T>> forListView(T[] paramArrayOfT)
/*     */   {
/*  79 */     return forListView(FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public static <T> Callback<ListView<T>, ListCell<T>> forListView(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 102 */     return forListView(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public static <T> Callback<ListView<T>, ListCell<T>> forListView(ObservableList<T> paramObservableList)
/*     */   {
/* 122 */     return forListView(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public static <T> Callback<ListView<T>, ListCell<T>> forListView(StringConverter<T> paramStringConverter, final ObservableList<T> paramObservableList)
/*     */   {
/* 145 */     return new Callback() {
/*     */       public ListCell<T> call(ListView<T> paramAnonymousListView) {
/* 147 */         return new ComboBoxListCell(this.val$converter, paramObservableList);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public ComboBoxListCell()
/*     */   {
/* 176 */     this(FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public ComboBoxListCell(T[] paramArrayOfT)
/*     */   {
/* 187 */     this(FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ComboBoxListCell(StringConverter<T> paramStringConverter, T[] paramArrayOfT)
/*     */   {
/* 203 */     this(paramStringConverter, FXCollections.observableArrayList(paramArrayOfT));
/*     */   }
/*     */ 
/*     */   public ComboBoxListCell(ObservableList<T> paramObservableList)
/*     */   {
/* 214 */     this(null, paramObservableList);
/*     */   }
/*     */ 
/*     */   public ComboBoxListCell(StringConverter<T> paramStringConverter, ObservableList<T> paramObservableList)
/*     */   {
/* 230 */     getStyleClass().add("combo-box-list-cell");
/* 231 */     this.items = paramObservableList;
/* 232 */     setConverter(paramStringConverter != null ? paramStringConverter : CellUtils.defaultStringConverter());
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<StringConverter<T>> converterProperty()
/*     */   {
/* 251 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public final void setConverter(StringConverter<T> paramStringConverter)
/*     */   {
/* 258 */     converterProperty().set(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final StringConverter<T> getConverter()
/*     */   {
/* 265 */     return (StringConverter)converterProperty().get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty comboBoxEditableProperty()
/*     */   {
/* 278 */     return this.comboBoxEditable;
/*     */   }
/*     */ 
/*     */   public final void setComboBoxEditable(boolean paramBoolean)
/*     */   {
/* 286 */     comboBoxEditableProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isComboBoxEditable()
/*     */   {
/* 293 */     return comboBoxEditableProperty().get();
/*     */   }
/*     */ 
/*     */   public ObservableList<T> getItems()
/*     */   {
/* 308 */     return this.items;
/*     */   }
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 313 */     if ((!isEditable()) || (!getListView().isEditable())) {
/* 314 */       return;
/*     */     }
/*     */ 
/* 317 */     if (this.comboBox == null) {
/* 318 */       this.comboBox = CellUtils.createComboBox(this, this.items);
/* 319 */       this.comboBox.editableProperty().bind(comboBoxEditableProperty());
/*     */     }
/*     */ 
/* 322 */     this.comboBox.getSelectionModel().select(getItem());
/*     */ 
/* 324 */     super.startEdit();
/* 325 */     setText(null);
/* 326 */     setGraphic(this.comboBox);
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 331 */     super.cancelEdit();
/*     */ 
/* 333 */     setText(getConverter().toString(getItem()));
/* 334 */     setGraphic(null);
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 339 */     super.updateItem(paramT, paramBoolean);
/* 340 */     CellUtils.updateItem(this, this.comboBox, getConverter());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ComboBoxListCell
 * JD-Core Version:    0.6.2
 */