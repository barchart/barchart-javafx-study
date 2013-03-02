/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class ComboBoxBuilder<T, B extends ComboBoxBuilder<T, B>> extends ComboBoxBaseBuilder<T, B>
/*     */   implements Builder<ComboBox<T>>
/*     */ {
/*     */   private int __set;
/*     */   private ListCell<T> buttonCell;
/*     */   private Callback<ListView<T>, ListCell<T>> cellFactory;
/*     */   private StringConverter<T> converter;
/*     */   private ObservableList<T> items;
/*     */   private SingleSelectionModel<T> selectionModel;
/*     */   private int visibleRowCount;
/*     */ 
/*     */   public static <T> ComboBoxBuilder<T, ?> create()
/*     */   {
/*  15 */     return new ComboBoxBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(ComboBox<T> paramComboBox)
/*     */   {
/*  20 */     super.applyTo(paramComboBox);
/*  21 */     int i = this.__set;
/*  22 */     if ((i & 0x1) != 0) paramComboBox.setButtonCell(this.buttonCell);
/*  23 */     if ((i & 0x2) != 0) paramComboBox.setCellFactory(this.cellFactory);
/*  24 */     if ((i & 0x4) != 0) paramComboBox.setConverter(this.converter);
/*  25 */     if ((i & 0x8) != 0) paramComboBox.setItems(this.items);
/*  26 */     if ((i & 0x10) != 0) paramComboBox.setSelectionModel(this.selectionModel);
/*  27 */     if ((i & 0x20) != 0) paramComboBox.setVisibleRowCount(this.visibleRowCount);
/*     */   }
/*     */ 
/*     */   public B buttonCell(ListCell<T> paramListCell)
/*     */   {
/*  36 */     this.buttonCell = paramListCell;
/*  37 */     this.__set |= 1;
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   public B cellFactory(Callback<ListView<T>, ListCell<T>> paramCallback)
/*     */   {
/*  47 */     this.cellFactory = paramCallback;
/*  48 */     this.__set |= 2;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B converter(StringConverter<T> paramStringConverter)
/*     */   {
/*  58 */     this.converter = paramStringConverter;
/*  59 */     this.__set |= 4;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B items(ObservableList<T> paramObservableList)
/*     */   {
/*  69 */     this.items = paramObservableList;
/*  70 */     this.__set |= 8;
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B selectionModel(SingleSelectionModel<T> paramSingleSelectionModel)
/*     */   {
/*  80 */     this.selectionModel = paramSingleSelectionModel;
/*  81 */     this.__set |= 16;
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B visibleRowCount(int paramInt)
/*     */   {
/*  91 */     this.visibleRowCount = paramInt;
/*  92 */     this.__set |= 32;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public ComboBox<T> build()
/*     */   {
/* 100 */     ComboBox localComboBox = new ComboBox();
/* 101 */     applyTo(localComboBox);
/* 102 */     return localComboBox;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ComboBoxBuilder
 * JD-Core Version:    0.6.2
 */