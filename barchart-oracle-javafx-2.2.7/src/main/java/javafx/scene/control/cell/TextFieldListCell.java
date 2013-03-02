/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.ListCell;
/*     */ import javafx.scene.control.ListView;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ import javafx.util.converter.DefaultStringConverter;
/*     */ 
/*     */ public class TextFieldListCell<T> extends ListCell<T>
/*     */ {
/*     */   private TextField textField;
/* 148 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/*     */   public static Callback<ListView<String>, ListCell<String>> forListView()
/*     */   {
/*  67 */     return forListView(new DefaultStringConverter());
/*     */   }
/*     */ 
/*     */   public static <T> Callback<ListView<T>, ListCell<T>> forListView(StringConverter<T> paramStringConverter)
/*     */   {
/*  86 */     return new Callback() {
/*     */       public ListCell<T> call(ListView<T> paramAnonymousListView) {
/*  88 */         return new TextFieldListCell(this.val$converter);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public TextFieldListCell()
/*     */   {
/* 118 */     this(null);
/*     */   }
/*     */ 
/*     */   public TextFieldListCell(StringConverter<T> paramStringConverter)
/*     */   {
/* 135 */     getStyleClass().add("text-field-list-cell");
/* 136 */     setConverter(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<StringConverter<T>> converterProperty()
/*     */   {
/* 155 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public final void setConverter(StringConverter<T> paramStringConverter)
/*     */   {
/* 162 */     converterProperty().set(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final StringConverter<T> getConverter()
/*     */   {
/* 169 */     return (StringConverter)converterProperty().get();
/*     */   }
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 181 */     if ((!isEditable()) || (!getListView().isEditable())) {
/* 182 */       return;
/*     */     }
/* 184 */     super.startEdit();
/* 185 */     CellUtils.startEdit(this, this.textField, getConverter());
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 190 */     super.cancelEdit();
/* 191 */     CellUtils.cancelEdit(this, getConverter());
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 196 */     super.updateItem(paramT, paramBoolean);
/* 197 */     CellUtils.updateItem(this, this.textField, getConverter());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.TextFieldListCell
 * JD-Core Version:    0.6.2
 */