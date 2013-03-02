/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerWrapper;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ 
/*     */ public abstract class SelectionModel<T>
/*     */ {
/*  66 */   private ReadOnlyIntegerWrapper selectedIndex = new ReadOnlyIntegerWrapper(this, "selectedIndex", -1);
/*     */ 
/* 100 */   private ReadOnlyObjectWrapper<T> selectedItem = new ReadOnlyObjectWrapper(this, "selectedItem");
/*     */ 
/*     */   public final ReadOnlyIntegerProperty selectedIndexProperty()
/*     */   {
/*  65 */     return this.selectedIndex.getReadOnlyProperty();
/*     */   }
/*  67 */   protected final void setSelectedIndex(int paramInt) { this.selectedIndex.set(paramInt); }
/*     */ 
/*     */ 
/*     */   public final int getSelectedIndex()
/*     */   {
/*  84 */     return selectedIndexProperty().get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<T> selectedItemProperty()
/*     */   {
/*  99 */     return this.selectedItem.getReadOnlyProperty();
/*     */   }
/* 101 */   protected final void setSelectedItem(T paramT) { this.selectedItem.set(paramT); }
/*     */ 
/*     */ 
/*     */   public final T getSelectedItem()
/*     */   {
/* 119 */     return selectedItemProperty().get();
/*     */   }
/*     */ 
/*     */   public abstract void clearAndSelect(int paramInt);
/*     */ 
/*     */   public abstract void select(int paramInt);
/*     */ 
/*     */   public abstract void select(T paramT);
/*     */ 
/*     */   public abstract void clearSelection(int paramInt);
/*     */ 
/*     */   public abstract void clearSelection();
/*     */ 
/*     */   public abstract boolean isSelected(int paramInt);
/*     */ 
/*     */   public abstract boolean isEmpty();
/*     */ 
/*     */   public abstract void selectPrevious();
/*     */ 
/*     */   public abstract void selectNext();
/*     */ 
/*     */   public abstract void selectFirst();
/*     */ 
/*     */   public abstract void selectLast();
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.SelectionModel
 * JD-Core Version:    0.6.2
 */