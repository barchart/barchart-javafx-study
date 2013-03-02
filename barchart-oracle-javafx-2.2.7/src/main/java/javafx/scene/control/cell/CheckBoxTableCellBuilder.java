/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import javafx.beans.value.ObservableValue;
/*    */ import javafx.scene.control.TableCellBuilder;
/*    */ import javafx.util.Callback;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class CheckBoxTableCellBuilder<S, T, B extends CheckBoxTableCellBuilder<S, T, B>> extends TableCellBuilder<S, T, B>
/*    */ {
/*    */   private int __set;
/*    */   private StringConverter<T> converter;
/*    */   private Callback<Integer, ObservableValue<Boolean>> selectedStateCallback;
/*    */ 
/*    */   public static <S, T> CheckBoxTableCellBuilder<S, T, ?> create()
/*    */   {
/* 15 */     return new CheckBoxTableCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(CheckBoxTableCell<S, T> paramCheckBoxTableCell)
/*    */   {
/* 20 */     super.applyTo(paramCheckBoxTableCell);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramCheckBoxTableCell.setConverter(this.converter);
/* 23 */     if ((i & 0x2) != 0) paramCheckBoxTableCell.setSelectedStateCallback(this.selectedStateCallback);
/*    */   }
/*    */ 
/*    */   public B converter(StringConverter<T> paramStringConverter)
/*    */   {
/* 32 */     this.converter = paramStringConverter;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B selectedStateCallback(Callback<Integer, ObservableValue<Boolean>> paramCallback)
/*    */   {
/* 43 */     this.selectedStateCallback = paramCallback;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public CheckBoxTableCell<S, T> build()
/*    */   {
/* 52 */     CheckBoxTableCell localCheckBoxTableCell = new CheckBoxTableCell();
/* 53 */     applyTo(localCheckBoxTableCell);
/* 54 */     return localCheckBoxTableCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.CheckBoxTableCellBuilder
 * JD-Core Version:    0.6.2
 */