/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerWrapper;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ 
/*     */ public abstract class FocusModel<T>
/*     */ {
/*  75 */   private ReadOnlyIntegerWrapper focusedIndex = new ReadOnlyIntegerWrapper(this, "focusedIndex", -1);
/*     */ 
/*  89 */   private ReadOnlyObjectWrapper<T> focusedItem = new ReadOnlyObjectWrapper(this, "focusedItem");
/*     */ 
/*     */   public FocusModel()
/*     */   {
/*  51 */     focusedIndexProperty().addListener(new InvalidationListener()
/*     */     {
/*     */       public void invalidated(Observable paramAnonymousObservable)
/*     */       {
/*  55 */         FocusModel.this.setFocusedItem(FocusModel.this.getModelItem(FocusModel.this.getFocusedIndex()));
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public final ReadOnlyIntegerProperty focusedIndexProperty()
/*     */   {
/*  76 */     return this.focusedIndex.getReadOnlyProperty(); } 
/*  77 */   public final int getFocusedIndex() { return this.focusedIndex.get(); } 
/*  78 */   final void setFocusedIndex(int paramInt) { this.focusedIndex.set(paramInt); }
/*     */ 
/*     */ 
/*     */   public final ReadOnlyObjectProperty<T> focusedItemProperty()
/*     */   {
/*  90 */     return this.focusedItem.getReadOnlyProperty(); } 
/*  91 */   public final T getFocusedItem() { return focusedItemProperty().get(); } 
/*  92 */   final void setFocusedItem(T paramT) { this.focusedItem.set(paramT); }
/*     */ 
/*     */ 
/*     */   protected abstract int getItemCount();
/*     */ 
/*     */   protected abstract T getModelItem(int paramInt);
/*     */ 
/*     */   public boolean isFocused(int paramInt)
/*     */   {
/* 132 */     if ((paramInt < 0) || (paramInt >= getItemCount())) return false;
/*     */ 
/* 134 */     return getFocusedIndex() == paramInt;
/*     */   }
/*     */ 
/*     */   public void focus(int paramInt)
/*     */   {
/* 146 */     if ((paramInt < 0) || (paramInt >= getItemCount()))
/* 147 */       setFocusedIndex(-1);
/*     */     else
/* 149 */       setFocusedIndex(paramInt);
/*     */   }
/*     */ 
/*     */   public void focusPrevious()
/*     */   {
/* 159 */     if (getFocusedIndex() == -1)
/* 160 */       focus(0);
/* 161 */     else if (getFocusedIndex() > 0)
/* 162 */       focus(getFocusedIndex() - 1);
/*     */   }
/*     */ 
/*     */   public void focusNext()
/*     */   {
/* 172 */     if (getFocusedIndex() == -1)
/* 173 */       focus(0);
/* 174 */     else if (getFocusedIndex() != getItemCount() - 1)
/* 175 */       focus(getFocusedIndex() + 1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.FocusModel
 * JD-Core Version:    0.6.2
 */