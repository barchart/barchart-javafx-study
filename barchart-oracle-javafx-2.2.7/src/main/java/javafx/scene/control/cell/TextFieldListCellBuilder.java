/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import javafx.scene.control.ListCellBuilder;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class TextFieldListCellBuilder<T, B extends TextFieldListCellBuilder<T, B>> extends ListCellBuilder<T, B>
/*    */ {
/*    */   private boolean __set;
/*    */   private StringConverter<T> converter;
/*    */ 
/*    */   public static <T> TextFieldListCellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new TextFieldListCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(TextFieldListCell<T> paramTextFieldListCell)
/*    */   {
/* 20 */     super.applyTo(paramTextFieldListCell);
/* 21 */     if (this.__set) paramTextFieldListCell.setConverter(this.converter);
/*    */   }
/*    */ 
/*    */   public B converter(StringConverter<T> paramStringConverter)
/*    */   {
/* 30 */     this.converter = paramStringConverter;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public TextFieldListCell<T> build()
/*    */   {
/* 39 */     TextFieldListCell localTextFieldListCell = new TextFieldListCell();
/* 40 */     applyTo(localTextFieldListCell);
/* 41 */     return localTextFieldListCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.TextFieldListCellBuilder
 * JD-Core Version:    0.6.2
 */