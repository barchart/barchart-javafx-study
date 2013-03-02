/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.control.TreeCellBuilder;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class ChoiceBoxTreeCellBuilder<T, B extends ChoiceBoxTreeCellBuilder<T, B>> extends TreeCellBuilder<T, B>
/*    */ {
/*    */   private int __set;
/*    */   private StringConverter<T> converter;
/*    */   private Collection<? extends T> items;
/*    */ 
/*    */   public static <T> ChoiceBoxTreeCellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new ChoiceBoxTreeCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ChoiceBoxTreeCell<T> paramChoiceBoxTreeCell)
/*    */   {
/* 20 */     super.applyTo(paramChoiceBoxTreeCell);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramChoiceBoxTreeCell.setConverter(this.converter);
/* 23 */     if ((i & 0x2) != 0) paramChoiceBoxTreeCell.getItems().addAll(this.items);
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
/*    */   public ChoiceBoxTreeCell<T> build()
/*    */   {
/* 59 */     ChoiceBoxTreeCell localChoiceBoxTreeCell = new ChoiceBoxTreeCell();
/* 60 */     applyTo(localChoiceBoxTreeCell);
/* 61 */     return localChoiceBoxTreeCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.ChoiceBoxTreeCellBuilder
 * JD-Core Version:    0.6.2
 */