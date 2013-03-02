/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class MultipleSelectionModel<T> extends SelectionModel<T>
/*     */ {
/*     */   private ObjectProperty<SelectionMode> selectionMode;
/*     */ 
/*     */   public final void setSelectionMode(SelectionMode paramSelectionMode)
/*     */   {
/*  58 */     selectionModeProperty().set(paramSelectionMode);
/*     */   }
/*     */ 
/*     */   public final SelectionMode getSelectionMode() {
/*  62 */     return this.selectionMode == null ? SelectionMode.SINGLE : (SelectionMode)this.selectionMode.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<SelectionMode> selectionModeProperty() {
/*  66 */     if (this.selectionMode == null) {
/*  67 */       this.selectionMode = new ObjectPropertyBase(SelectionMode.SINGLE) {
/*     */         protected void invalidated() {
/*  69 */           if (MultipleSelectionModel.this.getSelectionMode() == SelectionMode.SINGLE)
/*     */           {
/*  72 */             if (!MultipleSelectionModel.this.isEmpty()) {
/*  73 */               int i = MultipleSelectionModel.this.getSelectedIndex();
/*  74 */               MultipleSelectionModel.this.clearSelection();
/*  75 */               MultipleSelectionModel.this.select(i);
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/*  82 */           return MultipleSelectionModel.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/*  87 */           return "selectionMode";
/*     */         }
/*     */       };
/*     */     }
/*  91 */     return this.selectionMode;
/*     */   }
/*     */ 
/*     */   public abstract ObservableList<Integer> getSelectedIndices();
/*     */ 
/*     */   public abstract ObservableList<T> getSelectedItems();
/*     */ 
/*     */   public abstract void selectIndices(int paramInt, int[] paramArrayOfInt);
/*     */ 
/*     */   public void selectRange(int paramInt1, int paramInt2)
/*     */   {
/* 161 */     if (paramInt1 == paramInt2) return;
/*     */ 
/* 163 */     int i = paramInt1 < paramInt2 ? 1 : 0;
/* 164 */     int j = i != 0 ? paramInt1 : paramInt2;
/* 165 */     int k = i != 0 ? paramInt2 : paramInt1;
/* 166 */     int m = k - j - 1;
/*     */ 
/* 168 */     int[] arrayOfInt = new int[m];
/*     */ 
/* 170 */     int n = i != 0 ? j : k;
/* 171 */     int i1 = i != 0 ? n++ : n--;
/* 172 */     for (int i2 = 0; i2 < m; i2++) {
/* 173 */       arrayOfInt[i2] = (i != 0 ? n++ : n--);
/*     */     }
/* 175 */     selectIndices(i1, arrayOfInt);
/*     */   }
/*     */ 
/*     */   public abstract void selectAll();
/*     */ 
/*     */   public abstract void selectFirst();
/*     */ 
/*     */   public abstract void selectLast();
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.MultipleSelectionModel
 * JD-Core Version:    0.6.2
 */