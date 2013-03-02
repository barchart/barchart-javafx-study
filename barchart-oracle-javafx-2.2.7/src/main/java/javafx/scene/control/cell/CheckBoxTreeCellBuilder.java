/*    */ package javafx.scene.control.cell;
/*    */ 
/*    */ import javafx.beans.value.ObservableValue;
/*    */ import javafx.scene.control.TreeCellBuilder;
/*    */ import javafx.scene.control.TreeItem;
/*    */ import javafx.util.Callback;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class CheckBoxTreeCellBuilder<T, B extends CheckBoxTreeCellBuilder<T, B>> extends TreeCellBuilder<T, B>
/*    */ {
/*    */   private int __set;
/*    */   private StringConverter<TreeItem<T>> converter;
/*    */   private Callback<TreeItem<T>, ObservableValue<Boolean>> selectedStateCallback;
/*    */ 
/*    */   public static <T> CheckBoxTreeCellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new CheckBoxTreeCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(CheckBoxTreeCell<T> paramCheckBoxTreeCell)
/*    */   {
/* 20 */     super.applyTo(paramCheckBoxTreeCell);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramCheckBoxTreeCell.setConverter(this.converter);
/* 23 */     if ((i & 0x2) != 0) paramCheckBoxTreeCell.setSelectedStateCallback(this.selectedStateCallback);
/*    */   }
/*    */ 
/*    */   public B converter(StringConverter<TreeItem<T>> paramStringConverter)
/*    */   {
/* 32 */     this.converter = paramStringConverter;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B selectedStateCallback(Callback<TreeItem<T>, ObservableValue<Boolean>> paramCallback)
/*    */   {
/* 43 */     this.selectedStateCallback = paramCallback;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public CheckBoxTreeCell<T> build()
/*    */   {
/* 52 */     CheckBoxTreeCell localCheckBoxTreeCell = new CheckBoxTreeCell();
/* 53 */     applyTo(localCheckBoxTreeCell);
/* 54 */     return localCheckBoxTreeCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.cell.CheckBoxTreeCellBuilder
 * JD-Core Version:    0.6.2
 */