/*    */ package javafx.scene.control;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ 
/*    */ public abstract class MultipleSelectionModelBuilder<T, B extends MultipleSelectionModelBuilder<T, B>>
/*    */ {
/*    */   private int __set;
/*    */   private Collection<? extends Integer> selectedIndices;
/*    */   private Collection<? extends T> selectedItems;
/*    */   private SelectionMode selectionMode;
/*    */ 
/*    */   public void applyTo(MultipleSelectionModel<T> paramMultipleSelectionModel)
/*    */   {
/* 15 */     int i = this.__set;
/* 16 */     if ((i & 0x1) != 0) paramMultipleSelectionModel.getSelectedIndices().addAll(this.selectedIndices);
/* 17 */     if ((i & 0x2) != 0) paramMultipleSelectionModel.getSelectedItems().addAll(this.selectedItems);
/* 18 */     if ((i & 0x4) != 0) paramMultipleSelectionModel.setSelectionMode(this.selectionMode);
/*    */   }
/*    */ 
/*    */   public B selectedIndices(Collection<? extends Integer> paramCollection)
/*    */   {
/* 27 */     this.selectedIndices = paramCollection;
/* 28 */     this.__set |= 1;
/* 29 */     return this;
/*    */   }
/*    */ 
/*    */   public B selectedIndices(Integer[] paramArrayOfInteger)
/*    */   {
/* 36 */     return selectedIndices(Arrays.asList(paramArrayOfInteger));
/*    */   }
/*    */ 
/*    */   public B selectedItems(Collection<? extends T> paramCollection)
/*    */   {
/* 45 */     this.selectedItems = paramCollection;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B selectedItems(T[] paramArrayOfT)
/*    */   {
/* 54 */     return selectedItems(Arrays.asList(paramArrayOfT));
/*    */   }
/*    */ 
/*    */   public B selectionMode(SelectionMode paramSelectionMode)
/*    */   {
/* 63 */     this.selectionMode = paramSelectionMode;
/* 64 */     this.__set |= 4;
/* 65 */     return this;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.MultipleSelectionModelBuilder
 * JD-Core Version:    0.6.2
 */