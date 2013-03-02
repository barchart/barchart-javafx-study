/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import javafx.beans.value.ObservableValue;
/*    */ import javafx.scene.control.ListCellBuilder;
/*    */ import javafx.util.Callback;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class CheckBoxListCellBuilder<T, B extends CheckBoxListCellBuilder<T, B>> extends ListCellBuilder<T, B>
/*    */ {
/*    */   private int __set;
/*    */   private StringConverter<T> converter;
/*    */   private Callback<T, ObservableValue<Boolean>> selectedStateCallback;
/*    */ 
/*    */   public static <T> CheckBoxListCellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new CheckBoxListCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(CheckBoxListCell<T> paramCheckBoxListCell)
/*    */   {
/* 20 */     super.applyTo(paramCheckBoxListCell);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramCheckBoxListCell.setConverter(this.converter);
/* 23 */     if ((i & 0x2) != 0) paramCheckBoxListCell.setSelectedStateCallback(this.selectedStateCallback);
/*    */   }
/*    */ 
/*    */   public B converter(StringConverter<T> paramStringConverter)
/*    */   {
/* 32 */     this.converter = paramStringConverter;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B selectedStateCallback(Callback<T, ObservableValue<Boolean>> paramCallback)
/*    */   {
/* 43 */     this.selectedStateCallback = paramCallback;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public CheckBoxListCell<T> build()
/*    */   {
/* 52 */     CheckBoxListCell localCheckBoxListCell = new CheckBoxListCell();
/* 53 */     applyTo(localCheckBoxListCell);
/* 54 */     return localCheckBoxListCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.CheckBoxListCellBuilder
 * JD-Core Version:    0.6.2
 */