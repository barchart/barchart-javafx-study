/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.control.TableCellBuilder;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class ChoiceBoxTableCellBuilder<S, T, B extends ChoiceBoxTableCellBuilder<S, T, B>> extends TableCellBuilder<S, T, B>
/*    */ {
/*    */   private int __set;
/*    */   private StringConverter<T> converter;
/*    */   private Collection<? extends T> items;
/*    */ 
/*    */   public static <S, T> ChoiceBoxTableCellBuilder<S, T, ?> create()
/*    */   {
/* 15 */     return new ChoiceBoxTableCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ChoiceBoxTableCell<S, T> paramChoiceBoxTableCell)
/*    */   {
/* 20 */     super.applyTo(paramChoiceBoxTableCell);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramChoiceBoxTableCell.setConverter(this.converter);
/* 23 */     if ((i & 0x2) != 0) paramChoiceBoxTableCell.getItems().addAll(this.items);
/*    */   }
/*    */ 
/*    */   public B converter(StringConverter<T> paramStringConverter)
/*    */   {
/* 32 */     this.converter = paramStringConverter;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(Collection<? extends T> paramCollection)
/*    */   {
/* 43 */     this.items = paramCollection;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(T[] paramArrayOfT)
/*    */   {
/* 52 */     return items(Arrays.asList(paramArrayOfT));
/*    */   }
/*    */ 
/*    */   public ChoiceBoxTableCell<S, T> build()
/*    */   {
/* 59 */     ChoiceBoxTableCell localChoiceBoxTableCell = new ChoiceBoxTableCell();
/* 60 */     applyTo(localChoiceBoxTableCell);
/* 61 */     return localChoiceBoxTableCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ChoiceBoxTableCellBuilder
 * JD-Core Version:    0.6.2
 */