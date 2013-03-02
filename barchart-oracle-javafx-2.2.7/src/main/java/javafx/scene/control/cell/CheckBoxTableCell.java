/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.binding.Bindings;
/*     */ import javafx.beans.binding.BooleanBinding;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.scene.control.CheckBox;
/*     */ import javafx.scene.control.TableCell;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class CheckBoxTableCell<S, T> extends TableCell<S, T>
/*     */ {
/*     */   private final CheckBox checkBox;
/*     */   private final boolean showLabel;
/*     */   private ObservableValue<Boolean> booleanProperty;
/* 271 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/* 298 */   private ObjectProperty<Callback<Integer, ObservableValue<Boolean>>> selectedStateCallback = new SimpleObjectProperty(this, "selectedStateCallback");
/*     */ 
/*     */   public static <S> Callback<TableColumn<S, Boolean>, TableCell<S, Boolean>> forTableColumn(TableColumn<S, Boolean> paramTableColumn)
/*     */   {
/*  87 */     return forTableColumn(null, null);
/*     */   }
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(Callback<Integer, ObservableValue<Boolean>> paramCallback)
/*     */   {
/* 113 */     return forTableColumn(paramCallback, null);
/*     */   }
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(Callback<Integer, ObservableValue<Boolean>> paramCallback, boolean paramBoolean)
/*     */   {
/* 148 */     StringConverter localStringConverter = !paramBoolean ? null : CellUtils.defaultStringConverter();
/*     */ 
/* 150 */     return forTableColumn(paramCallback, localStringConverter);
/*     */   }
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(Callback<Integer, ObservableValue<Boolean>> paramCallback, final StringConverter<T> paramStringConverter)
/*     */   {
/* 183 */     return new Callback() {
/*     */       public TableCell<S, T> call(TableColumn<S, T> paramAnonymousTableColumn) {
/* 185 */         return new CheckBoxTableCell(this.val$getSelectedProperty, paramStringConverter);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public CheckBoxTableCell()
/*     */   {
/* 215 */     this(null, null);
/*     */   }
/*     */ 
/*     */   public CheckBoxTableCell(Callback<Integer, ObservableValue<Boolean>> paramCallback)
/*     */   {
/* 227 */     this(paramCallback, null);
/*     */   }
/*     */ 
/*     */   public CheckBoxTableCell(Callback<Integer, ObservableValue<Boolean>> paramCallback, StringConverter<T> paramStringConverter)
/*     */   {
/* 243 */     getStyleClass().add("choice-box-table-cell");
/* 244 */     setSelectedStateCallback(paramCallback);
/* 245 */     setConverter(paramStringConverter);
/* 246 */     this.showLabel = (paramStringConverter != null);
/*     */ 
/* 248 */     this.checkBox = new CheckBox();
/* 249 */     setGraphic(this.checkBox);
/*     */ 
/* 258 */     if (this.showLabel)
/* 259 */       this.checkBox.setAlignment(Pos.CENTER_LEFT);
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<StringConverter<T>> converterProperty()
/*     */   {
/* 278 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public final void setConverter(StringConverter<T> paramStringConverter)
/*     */   {
/* 285 */     converterProperty().set(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final StringConverter<T> getConverter()
/*     */   {
/* 292 */     return (StringConverter)converterProperty().get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Callback<Integer, ObservableValue<Boolean>>> selectedStateCallbackProperty()
/*     */   {
/* 308 */     return this.selectedStateCallback;
/*     */   }
/*     */ 
/*     */   public final void setSelectedStateCallback(Callback<Integer, ObservableValue<Boolean>> paramCallback)
/*     */   {
/* 315 */     selectedStateCallbackProperty().set(paramCallback);
/*     */   }
/*     */ 
/*     */   public final Callback<Integer, ObservableValue<Boolean>> getSelectedStateCallback()
/*     */   {
/* 322 */     return (Callback)selectedStateCallbackProperty().get();
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 335 */     super.updateItem(paramT, paramBoolean);
/*     */ 
/* 337 */     if (paramBoolean) {
/* 338 */       setText(null);
/* 339 */       setGraphic(null);
/*     */     } else {
/* 341 */       StringConverter localStringConverter = getConverter();
/*     */ 
/* 343 */       if (this.showLabel) {
/* 344 */         setText(localStringConverter.toString(paramT));
/*     */       }
/* 346 */       setGraphic(this.checkBox);
/*     */ 
/* 348 */       if ((this.booleanProperty instanceof BooleanProperty)) {
/* 349 */         this.checkBox.selectedProperty().unbindBidirectional((BooleanProperty)this.booleanProperty);
/*     */       }
/* 351 */       ObservableValue localObservableValue = getSelectedProperty();
/* 352 */       if ((localObservableValue instanceof BooleanProperty)) {
/* 353 */         this.booleanProperty = localObservableValue;
/* 354 */         this.checkBox.selectedProperty().bindBidirectional((BooleanProperty)this.booleanProperty);
/*     */       }
/*     */ 
/* 357 */       this.checkBox.disableProperty().bind(Bindings.not(getTableView().editableProperty().and(getTableColumn().editableProperty()).and(editableProperty())));
/*     */     }
/*     */   }
/*     */ 
/*     */   private ObservableValue getSelectedProperty()
/*     */   {
/* 374 */     return getSelectedStateCallback() != null ? (ObservableValue)getSelectedStateCallback().call(Integer.valueOf(getIndex())) : getTableColumn().getCellObservableValue(getIndex());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.CheckBoxTableCell
 * JD-Core Version:    0.6.2
 */