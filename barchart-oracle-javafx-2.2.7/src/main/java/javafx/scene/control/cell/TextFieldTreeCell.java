/*     */ package javafx.scene.control.cell;
/*     */ 
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.scene.control.TreeCell;
/*     */ import javafx.scene.control.TreeView;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ import javafx.util.converter.DefaultStringConverter;
/*     */ 
/*     */ public class TextFieldTreeCell<T> extends TreeCell<T>
/*     */ {
/*     */   private TextField textField;
/* 153 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter");
/*     */ 
/*     */   public static Callback<TreeView<String>, TreeCell<String>> forTreeView()
/*     */   {
/*  68 */     return forTreeView(new DefaultStringConverter());
/*     */   }
/*     */ 
/*     */   public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(StringConverter<T> paramStringConverter)
/*     */   {
/*  90 */     return new Callback() {
/*     */       public TreeCell<T> call(TreeView<T> paramAnonymousTreeView) {
/*  92 */         return new TextFieldTreeCell(this.val$converter);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public TextFieldTreeCell()
/*     */   {
/* 123 */     this(null);
/*     */   }
/*     */ 
/*     */   public TextFieldTreeCell(StringConverter<T> paramStringConverter)
/*     */   {
/* 140 */     getStyleClass().add("text-field-tree-cell");
/* 141 */     setConverter(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<StringConverter<T>> converterProperty()
/*     */   {
/* 160 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public final void setConverter(StringConverter<T> paramStringConverter)
/*     */   {
/* 167 */     converterProperty().set(paramStringConverter);
/*     */   }
/*     */ 
/*     */   public final StringConverter<T> getConverter()
/*     */   {
/* 174 */     return (StringConverter)converterProperty().get();
/*     */   }
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 187 */     if ((!isEditable()) || (!getTreeView().isEditable())) {
/* 188 */       return;
/*     */     }
/* 190 */     super.startEdit();
/* 191 */     CellUtils.startEdit(this, this.textField, getConverter());
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 196 */     super.cancelEdit();
/* 197 */     CellUtils.cancelEdit(this, getConverter());
/*     */   }
/*     */ 
/*     */   public void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 202 */     super.updateItem(paramT, paramBoolean);
/* 203 */     CellUtils.updateItem(this, this.textField, getConverter());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.TextFieldTreeCell
 * JD-Core Version:    0.6.2
 */