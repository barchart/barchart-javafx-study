/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import javafx.scene.control.TreeCellBuilder;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class TextFieldTreeCellBuilder<T, B extends TextFieldTreeCellBuilder<T, B>> extends TreeCellBuilder<T, B>
/*    */ {
/*    */   private boolean __set;
/*    */   private StringConverter<T> converter;
/*    */ 
/*    */   public static <T> TextFieldTreeCellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new TextFieldTreeCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(TextFieldTreeCell<T> paramTextFieldTreeCell)
/*    */   {
/* 20 */     super.applyTo(paramTextFieldTreeCell);
/* 21 */     if (this.__set) paramTextFieldTreeCell.setConverter(this.converter);
/*    */   }
/*    */ 
/*    */   public B converter(StringConverter<T> paramStringConverter)
/*    */   {
/* 30 */     this.converter = paramStringConverter;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public TextFieldTreeCell<T> build()
/*    */   {
/* 39 */     TextFieldTreeCell localTextFieldTreeCell = new TextFieldTreeCell();
/* 40 */     applyTo(localTextFieldTreeCell);
/* 41 */     return localTextFieldTreeCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.TextFieldTreeCellBuilder
 * JD-Core Version:    0.6.2
 */