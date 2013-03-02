/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.scene.control.CheckBox;
/*     */ import javafx.scene.control.ContentDisplay;
/*     */ import javafx.scene.control.ListCell;
/*     */ import javafx.scene.control.ListView;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class CheckBoxListCell<T> extends ListCell<T>
/*     */ {
/*     */   private final CheckBox checkBox;
/*     */   private ObservableValue<Boolean> booleanProperty;
/* 190 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/* 216 */   private ObjectProperty<Callback<T, ObservableValue<Boolean>>> selectedStateCallback = new SimpleObjectProperty(this, "selectedStateCallback");
/*     */ 
/*     */   public static <T> Callback<ListView<T>, ListCell<T>> forListView(Callback<T, ObservableValue<Boolean>> paramCallback)
/*     */   {
/*  90 */     return forListView(paramCallback, null);
/*     */   }
/*     */ 
/*     */   public static <T> Callback<ListView<T>, ListCell<T>> forListView(Callback<T, ObservableValue<Boolean>> paramCallback, final StringConverter<T> paramStringConverter)
/*     */   {
/* 117 */     return new Callback() {
/*     */       public ListCell<T> call(ListView<T> paramAnonymousListView) {
/* 119 */         return new CheckBoxListCell(this.val$getSelectedProperty, paramStringConverter);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public CheckBoxListCell()
/*     */   {
/* 146 */     this(null);
/*     */   }
/*     */ 
/*     */   public CheckBoxListCell(Callback<T, ObservableValue<Boolean>> paramCallback)
/*     */   {
/* 157 */     this(paramCallback, CellUtils.defaultStringConverter());
/*     */   }
/*     */ 
/*     */   public CheckBoxListCell(Callback<T, ObservableValue<Boolean>> paramCallback, StringConverter<T> paramStringConverter)
/*     */   {
/* 171 */     getStyleClass().add("choice-box-list-cell");
/* 172 */     setSelectedStateCallback(paramCallback);
/* 173 */     setConverter(paramStringConverter);
/*     */ 
/* 175 */     this.checkBox = new CheckBox();
/*     */ 
/* 177 */     setAlignment(Pos.CENTER_LEFT);
/* 178 */     setContentDisplay(ContentDisplay.LEFT);
/* 179 */     setGraphic(this.checkBox);
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<StringConverter<T>> converterProperty()
/*     */   {
/* 197 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public final void setConverter(StringConverter<T> paramStringConverter)
/*     */   {
/* 204 */     converterProperty().set(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final StringConverter<T> getConverter()
/*     */   {
/* 211 */     return (StringConverter)converterProperty().get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Callback<T, ObservableValue<Boolean>>> selectedStateCallbackProperty()
/*     */   {
/* 226 */     return this.selectedStateCallback;
/*     */   }
/*     */ 
/*     */   public final void setSelectedStateCallback(Callback<T, ObservableValue<Boolean>> paramCallback)
/*     */   {
/* 233 */     selectedStateCallbackProperty().set(paramCallback);
/*     */   }
/*     */ 
/*     */   public final Callback<T, ObservableValue<Boolean>> getSelectedStateCallback()
/*     */   {
/* 240 */     return (Callback)selectedStateCallbackProperty().get();
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 253 */     super.updateItem(paramT, paramBoolean);
/*     */ 
/* 255 */     if (!paramBoolean) {
/* 256 */       StringConverter localStringConverter = getConverter();
/* 257 */       Callback localCallback = getSelectedStateCallback();
/*     */ 
/* 259 */       setGraphic(this.checkBox);
/* 260 */       setText(paramT == null ? "" : localStringConverter != null ? localStringConverter.toString(paramT) : paramT.toString());
/*     */ 
/* 262 */       if (this.booleanProperty != null) {
/* 263 */         this.checkBox.selectedProperty().unbindBidirectional((BooleanProperty)this.booleanProperty);
/*     */       }
/* 265 */       this.booleanProperty = ((ObservableValue)localCallback.call(paramT));
/* 266 */       if (this.booleanProperty != null)
/* 267 */         this.checkBox.selectedProperty().bindBidirectional((BooleanProperty)this.booleanProperty);
/*     */     }
/*     */     else {
/* 270 */       setGraphic(null);
/* 271 */       setText(null);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.CheckBoxListCell
 * JD-Core Version:    0.6.2
 */