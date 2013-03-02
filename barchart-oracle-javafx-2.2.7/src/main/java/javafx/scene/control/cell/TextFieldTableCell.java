/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.TableCell;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ import javafx.util.converter.DefaultStringConverter;
/*     */ 
/*     */ public class TextFieldTableCell<S, T> extends TableCell<S, T>
/*     */ {
/*     */   private TextField textField;
/* 148 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/*     */   public static <S> Callback<TableColumn<S, String>, TableCell<S, String>> forTableColumn()
/*     */   {
/*  65 */     return forTableColumn(new DefaultStringConverter());
/*     */   }
/*     */ 
/*     */   public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(StringConverter<T> paramStringConverter)
/*     */   {
/*  86 */     return new Callback() {
/*     */       public TableCell<S, T> call(TableColumn<S, T> paramAnonymousTableColumn) {
/*  88 */         return new TextFieldTableCell(this.val$converter);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public TextFieldTableCell()
/*     */   {
/* 118 */     this(null);
/*     */   }
/*     */ 
/*     */   public TextFieldTableCell(StringConverter<T> paramStringConverter)
/*     */   {
/* 135 */     getStyleClass().add("text-field-table-cell");
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
/* 182 */     if ((!isEditable()) || (!getTableView().isEditable()) || (!getTableColumn().isEditable()))
/*     */     {
/* 185 */       return;
/*     */     }
/* 187 */     super.startEdit();
/* 188 */     CellUtils.startEdit(this, this.textField, getConverter());
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 193 */     super.cancelEdit();
/* 194 */     CellUtils.cancelEdit(this, getConverter());
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 199 */     super.updateItem(paramT, paramBoolean);
/* 200 */     CellUtils.updateItem(this, this.textField, getConverter());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.TextFieldTableCell
 * JD-Core Version:    0.6.2
 */