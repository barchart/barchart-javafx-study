/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.control.ListCellBuilder;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class ComboBoxListCellBuilder<T, B extends ComboBoxListCellBuilder<T, B>> extends ListCellBuilder<T, B>
/*    */ {
/*    */   private int __set;
/*    */   private boolean comboBoxEditable;
/*    */   private StringConverter<T> converter;
/*    */   private Collection<? extends T> items;
/*    */ 
/*    */   public static <T> ComboBoxListCellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new ComboBoxListCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ComboBoxListCell<T> paramComboBoxListCell)
/*    */   {
/* 20 */     super.applyTo(paramComboBoxListCell);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramComboBoxListCell.setComboBoxEditable(this.comboBoxEditable);
/* 23 */     if ((i & 0x2) != 0) paramComboBoxListCell.setConverter(this.converter);
/* 24 */     if ((i & 0x4) != 0) paramComboBoxListCell.getItems().addAll(this.items);
/*    */   }
/*    */ 
/*    */   public B comboBoxEditable(boolean paramBoolean)
/*    */   {
/* 33 */     this.comboBoxEditable = paramBoolean;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B converter(StringConverter<T> paramStringConverter)
/*    */   {
/* 44 */     this.converter = paramStringConverter;
/* 45 */     this.__set |= 2;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(Collection<? extends T> paramCollection)
/*    */   {
/* 55 */     this.items = paramCollection;
/* 56 */     this.__set |= 4;
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(T[] paramArrayOfT)
/*    */   {
/* 64 */     return items(Arrays.asList(paramArrayOfT));
/*    */   }
/*    */ 
/*    */   public ComboBoxListCell<T> build()
/*    */   {
/* 71 */     ComboBoxListCell localComboBoxListCell = new ComboBoxListCell();
/* 72 */     applyTo(localComboBoxListCell);
/* 73 */     return localComboBoxListCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ComboBoxListCellBuilder
 * JD-Core Version:    0.6.2
 */