/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import javafx.scene.control.TableCellBuilder;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class TextFieldTableCellBuilder<S, T, B extends TextFieldTableCellBuilder<S, T, B>> extends TableCellBuilder<S, T, B>
/*    */ {
/*    */   private boolean __set;
/*    */   private StringConverter<T> converter;
/*    */ 
/*    */   public static <S, T> TextFieldTableCellBuilder<S, T, ?> create()
/*    */   {
/* 15 */     return new TextFieldTableCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(TextFieldTableCell<S, T> paramTextFieldTableCell)
/*    */   {
/* 20 */     super.applyTo(paramTextFieldTableCell);
/* 21 */     if (this.__set) paramTextFieldTableCell.setConverter(this.converter);
/*    */   }
/*    */ 
/*    */   public B converter(StringConverter<T> paramStringConverter)
/*    */   {
/* 30 */     this.converter = paramStringConverter;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public TextFieldTableCell<S, T> build()
/*    */   {
/* 39 */     TextFieldTableCell localTextFieldTableCell = new TextFieldTableCell();
/* 40 */     applyTo(localTextFieldTableCell);
/* 41 */     return localTextFieldTableCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.TextFieldTableCellBuilder
 * JD-Core Version:    0.6.2
 */