/*     */ package javafx.scene.control;
/*     */ 
/*     */ public abstract class SingleSelectionModel<T> extends SelectionModel<T>
/*     */ {
/*     */   public void clearSelection()
/*     */   {
/*  67 */     updateSelectedIndex(-1);
/*     */   }
/*     */ 
/*     */   public void clearSelection(int paramInt)
/*     */   {
/*  74 */     if (getSelectedIndex() == paramInt)
/*  75 */       clearSelection();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  81 */     return (getItemCount() == 0) || (getSelectedIndex() == -1);
/*     */   }
/*     */ 
/*     */   public boolean isSelected(int paramInt)
/*     */   {
/*  93 */     return getSelectedIndex() == paramInt;
/*     */   }
/*     */ 
/*     */   public void clearAndSelect(int paramInt)
/*     */   {
/* 102 */     select(paramInt);
/*     */   }
/*     */ 
/*     */   public void select(T paramT)
/*     */   {
/* 112 */     int i = getItemCount();
/*     */ 
/* 114 */     for (int j = 0; j < i; j++) {
/* 115 */       Object localObject = getModelItem(j);
/* 116 */       if ((localObject != null) && (localObject.equals(paramT))) {
/* 117 */         select(j);
/* 118 */         return;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 127 */     setSelectedItem(paramT);
/*     */   }
/*     */ 
/*     */   public void select(int paramInt)
/*     */   {
/* 136 */     if (paramInt == -1) {
/* 137 */       clearSelection();
/* 138 */       return;
/*     */     }
/* 140 */     int i = getItemCount();
/* 141 */     if ((i == 0) || (paramInt < 0) || (paramInt >= i)) return;
/* 142 */     updateSelectedIndex(paramInt);
/*     */   }
/*     */ 
/*     */   public void selectPrevious()
/*     */   {
/* 151 */     if (getSelectedIndex() == 0) return;
/* 152 */     select(getSelectedIndex() - 1);
/*     */   }
/*     */ 
/*     */   public void selectNext()
/*     */   {
/* 161 */     select(getSelectedIndex() + 1);
/*     */   }
/*     */ 
/*     */   public void selectFirst()
/*     */   {
/* 170 */     if (getItemCount() > 0)
/* 171 */       select(0);
/*     */   }
/*     */ 
/*     */   public void selectLast()
/*     */   {
/* 181 */     int i = getItemCount();
/* 182 */     if ((i > 0) && (getSelectedIndex() < i - 1))
/* 183 */       select(i - 1);
/*     */   }
/*     */ 
/*     */   protected abstract T getModelItem(int paramInt);
/*     */ 
/*     */   protected abstract int getItemCount();
/*     */ 
/*     */   private void updateSelectedIndex(int paramInt)
/*     */   {
/* 205 */     int i = getSelectedIndex();
/* 206 */     Object localObject = getSelectedItem();
/*     */ 
/* 208 */     setSelectedIndex(paramInt);
/*     */ 
/* 210 */     if ((i != -1) || (localObject == null) || (paramInt != -1))
/*     */     {
/* 214 */       setSelectedItem(getModelItem(paramInt));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.SingleSelectionModel
 * JD-Core Version:    0.6.2
 */