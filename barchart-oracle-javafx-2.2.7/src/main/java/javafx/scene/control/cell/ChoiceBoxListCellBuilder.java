/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.control.ListCellBuilder;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class ChoiceBoxListCellBuilder<T, B extends ChoiceBoxListCellBuilder<T, B>> extends ListCellBuilder<T, B>
/*    */ {
/*    */   private int __set;
/*    */   private StringConverter<T> converter;
/*    */   private Collection<? extends T> items;
/*    */ 
/*    */   public static <T> ChoiceBoxListCellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new ChoiceBoxListCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ChoiceBoxListCell<T> paramChoiceBoxListCell)
/*    */   {
/* 20 */     super.applyTo(paramChoiceBoxListCell);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramChoiceBoxListCell.setConverter(this.converter);
/* 23 */     if ((i & 0x2) != 0) paramChoiceBoxListCell.getItems().addAll(this.items);
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
/*    */   public ChoiceBoxListCell<T> build()
/*    */   {
/* 59 */     ChoiceBoxListCell localChoiceBoxListCell = new ChoiceBoxListCell();
/* 60 */     applyTo(localChoiceBoxListCell);
/* 61 */     return localChoiceBoxListCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ChoiceBoxListCellBuilder
 * JD-Core Version:    0.6.2
 */