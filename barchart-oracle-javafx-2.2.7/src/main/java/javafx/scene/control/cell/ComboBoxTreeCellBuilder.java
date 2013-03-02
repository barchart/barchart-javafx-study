/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.control.TreeCellBuilder;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class ComboBoxTreeCellBuilder<T, B extends ComboBoxTreeCellBuilder<T, B>> extends TreeCellBuilder<T, B>
/*    */ {
/*    */   private int __set;
/*    */   private boolean comboBoxEditable;
/*    */   private StringConverter<T> converter;
/*    */   private Collection<? extends T> items;
/*    */ 
/*    */   public static <T> ComboBoxTreeCellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new ComboBoxTreeCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ComboBoxTreeCell<T> paramComboBoxTreeCell)
/*    */   {
/* 20 */     super.applyTo(paramComboBoxTreeCell);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramComboBoxTreeCell.setComboBoxEditable(this.comboBoxEditable);
/* 23 */     if ((i & 0x2) != 0) paramComboBoxTreeCell.setConverter(this.converter);
/* 24 */     if ((i & 0x4) != 0) paramComboBoxTreeCell.getItems().addAll(this.items);
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
/*    */   public ComboBoxTreeCell<T> build()
/*    */   {
/* 71 */     ComboBoxTreeCell localComboBoxTreeCell = new ComboBoxTreeCell();
/* 72 */     applyTo(localComboBoxTreeCell);
/* 73 */     return localComboBoxTreeCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ComboBoxTreeCellBuilder
 * JD-Core Version:    0.6.2
 */