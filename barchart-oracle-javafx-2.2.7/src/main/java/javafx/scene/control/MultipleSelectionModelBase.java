/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.collections.MappingChange;
/*     */ import com.sun.javafx.collections.MappingChange.Map;
/*     */ import com.sun.javafx.collections.NonIterableChange.GenericAddRemoveChange;
/*     */ import com.sun.javafx.collections.NonIterableChange.SimpleAddChange;
/*     */ import com.sun.javafx.collections.NonIterableChange.SimplePermutationChange;
/*     */ import com.sun.javafx.scene.control.ReadOnlyUnbackedObservableList;
/*     */ import java.util.AbstractList;
/*     */ import java.util.BitSet;
/*     */ import java.util.Collections;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ abstract class MultipleSelectionModelBase<T> extends MultipleSelectionModel<T>
/*     */ {
/*     */   private final BitSet selectedIndices;
/*     */   private final ReadOnlyUnbackedObservableList<Integer> selectedIndicesSeq;
/*     */   private final ReadOnlyUnbackedObservableList selectedItemsSeq;
/* 184 */   boolean makeAtomic = false;
/*     */ 
/*     */   public MultipleSelectionModelBase()
/*     */   {
/*  59 */     selectedIndexProperty().addListener(new InvalidationListener()
/*     */     {
/*     */       public void invalidated(Observable paramAnonymousObservable)
/*     */       {
/*  65 */         MultipleSelectionModelBase.this.setSelectedItem(MultipleSelectionModelBase.this.getModelItem(MultipleSelectionModelBase.this.getSelectedIndex()));
/*     */       }
/*     */     });
/*  69 */     this.selectedIndices = new BitSet();
/*     */ 
/*  71 */     this.selectedIndicesSeq = new ReadOnlyUnbackedObservableList() {
/*     */       public Integer get(int paramAnonymousInt) {
/*  73 */         if ((paramAnonymousInt < 0) || (paramAnonymousInt >= MultipleSelectionModelBase.this.getItemCount())) return Integer.valueOf(-1);
/*     */ 
/*  75 */         int i = 0; for (int j = MultipleSelectionModelBase.this.selectedIndices.nextSetBit(0); 
/*  76 */           (j >= 0) || (i == paramAnonymousInt); 
/*  77 */           j = MultipleSelectionModelBase.this.selectedIndices.nextSetBit(j + 1)) {
/*  78 */           if (i == paramAnonymousInt) return Integer.valueOf(j);
/*  77 */           i++;
/*     */         }
/*     */ 
/*  81 */         return Integer.valueOf(-1);
/*     */       }
/*     */ 
/*     */       public int size() {
/*  85 */         return MultipleSelectionModelBase.this.selectedIndices.cardinality();
/*     */       }
/*     */ 
/*     */       public boolean contains(Object paramAnonymousObject) {
/*  89 */         if (((paramAnonymousObject instanceof Integer)) || ((paramAnonymousObject instanceof Integer))) {
/*  90 */           Number localNumber = (Number)paramAnonymousObject;
/*  91 */           int i = localNumber.intValue();
/*     */ 
/*  93 */           return (i > 0) && (i < MultipleSelectionModelBase.this.selectedIndices.length()) && (MultipleSelectionModelBase.this.selectedIndices.get(i));
/*     */         }
/*     */ 
/*  97 */         return false;
/*     */       }
/*     */     };
/* 101 */     final MappingChange.Map local3 = new MappingChange.Map() {
/*     */       public Object map(Integer paramAnonymousInteger) {
/* 103 */         return MultipleSelectionModelBase.this.getModelItem(paramAnonymousInteger.intValue());
/*     */       }
/*     */     };
/* 107 */     this.selectedIndicesSeq.addListener(new ListChangeListener()
/*     */     {
/*     */       public void onChanged(ListChangeListener.Change<? extends Integer> paramAnonymousChange)
/*     */       {
/* 111 */         MultipleSelectionModelBase.this.selectedItemsSeq.callObservers(new MappingChange(paramAnonymousChange, local3, MultipleSelectionModelBase.this.selectedItemsSeq));
/* 112 */         paramAnonymousChange.reset();
/*     */       }
/*     */     });
/* 117 */     this.selectedItemsSeq = new ReadOnlyUnbackedObservableList() {
/*     */       public T get(int paramAnonymousInt) {
/* 119 */         int i = ((Integer)MultipleSelectionModelBase.this.selectedIndicesSeq.get(paramAnonymousInt)).intValue();
/* 120 */         return MultipleSelectionModelBase.this.getModelItem(i);
/*     */       }
/*     */ 
/*     */       public int size() {
/* 124 */         return MultipleSelectionModelBase.this.selectedIndices.cardinality();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public ObservableList<Integer> getSelectedIndices()
/*     */   {
/* 163 */     return this.selectedIndicesSeq;
/*     */   }
/*     */ 
/*     */   public ObservableList<T> getSelectedItems()
/*     */   {
/* 172 */     return this.selectedItemsSeq;
/*     */   }
/*     */ 
/*     */   protected abstract int getItemCount();
/*     */ 
/*     */   protected abstract T getModelItem(int paramInt);
/*     */ 
/*     */   protected abstract void focus(int paramInt);
/*     */ 
/*     */   protected abstract int getFocusedIndex();
/*     */ 
/*     */   void shiftSelection(int paramInt1, int paramInt2)
/*     */   {
/* 218 */     if (paramInt1 < 0) return;
/* 219 */     if (paramInt2 == 0) return;
/*     */ 
/* 221 */     int i = this.selectedIndices.cardinality();
/* 222 */     if (i == 0) return;
/*     */ 
/* 224 */     int j = this.selectedIndices.size();
/*     */ 
/* 226 */     int[] arrayOfInt = new int[j];
/* 227 */     int k = 0;
/*     */     int m;
/*     */     boolean bool;
/* 229 */     if (paramInt2 > 0) {
/* 230 */       for (m = j - 1; (m >= paramInt1) && (m >= 0); m--) {
/* 231 */         bool = this.selectedIndices.get(m);
/* 232 */         this.selectedIndices.set(m + paramInt2, bool);
/*     */ 
/* 234 */         if (bool) {
/* 235 */           arrayOfInt[(k++)] = (m + 1);
/*     */         }
/*     */       }
/* 238 */       this.selectedIndices.clear(paramInt1);
/* 239 */     } else if (paramInt2 < 0) {
/* 240 */       for (m = paramInt1; m < j; m++) {
/* 241 */         if (m + paramInt2 >= 0) {
/* 242 */           bool = this.selectedIndices.get(m + 1);
/* 243 */           this.selectedIndices.set(m + 1 + paramInt2, bool);
/*     */ 
/* 245 */           if (bool) {
/* 246 */             arrayOfInt[(k++)] = m;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 252 */     if ((getFocusedIndex() >= paramInt1) && (getFocusedIndex() > -1) && (getFocusedIndex() + paramInt2 > -1)) {
/* 253 */       setSelectedIndex(getFocusedIndex() + paramInt2);
/*     */     }
/*     */ 
/* 256 */     this.selectedIndicesSeq.callObservers(new NonIterableChange.SimplePermutationChange(0, i, arrayOfInt, this.selectedIndicesSeq));
/*     */   }
/*     */ 
/*     */   public void clearAndSelect(int paramInt)
/*     */   {
/* 266 */     quietClearSelection();
/*     */ 
/* 269 */     select(paramInt);
/*     */   }
/*     */ 
/*     */   public void select(int paramInt) {
/* 273 */     if (paramInt == -1) {
/* 274 */       clearSelection();
/* 275 */       return;
/*     */     }
/* 277 */     if ((paramInt < 0) || (paramInt >= getItemCount())) {
/* 278 */       return;
/*     */     }
/*     */ 
/* 281 */     int i = paramInt == getSelectedIndex() ? 1 : 0;
/* 282 */     Object localObject1 = getSelectedItem();
/* 283 */     Object localObject2 = getModelItem(paramInt);
/* 284 */     int j = (localObject2 != null) && (localObject2.equals(localObject1)) ? 1 : 0;
/* 285 */     int k = (i != 0) && (j == 0) ? 1 : 0;
/*     */ 
/* 287 */     if (!this.selectedIndices.get(paramInt)) {
/* 288 */       if (getSelectionMode() == SelectionMode.SINGLE) {
/* 289 */         quietClearSelection();
/*     */       }
/* 291 */       this.selectedIndices.set(paramInt);
/*     */     }
/*     */ 
/* 294 */     setSelectedIndex(paramInt);
/* 295 */     focus(paramInt);
/*     */ 
/* 298 */     this.selectedIndicesSeq.callObservers(new NonIterableChange.SimpleAddChange(0, 1, this.selectedIndicesSeq));
/*     */ 
/* 300 */     if (k != 0)
/* 301 */       setSelectedItem(localObject2);
/*     */   }
/*     */ 
/*     */   public void select(T paramT)
/*     */   {
/* 311 */     Object localObject = null;
/* 312 */     for (int i = 0; i < getItemCount(); i++) {
/* 313 */       localObject = getModelItem(i);
/* 314 */       if (localObject != null)
/*     */       {
/* 316 */         if (localObject.equals(paramT)) {
/* 317 */           if (isSelected(i)) {
/* 318 */             return;
/*     */           }
/*     */ 
/* 321 */           if (getSelectionMode() == SelectionMode.SINGLE) {
/* 322 */             quietClearSelection();
/*     */           }
/*     */ 
/* 325 */           select(i);
/* 326 */           return;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 335 */     setSelectedItem(paramT);
/*     */   }
/*     */ 
/*     */   public void selectIndices(int paramInt, int[] paramArrayOfInt) {
/* 339 */     if (paramArrayOfInt == null) {
/* 340 */       select(paramInt);
/* 341 */       return;
/*     */     }
/*     */ 
/* 349 */     int i = getItemCount();
/*     */     int j;
/*     */     int k;
/* 351 */     if (getSelectionMode() == SelectionMode.SINGLE) {
/* 352 */       quietClearSelection();
/*     */ 
/* 354 */       for (j = paramArrayOfInt.length - 1; j >= 0; j--) {
/* 355 */         k = paramArrayOfInt[j];
/* 356 */         if ((k >= 0) && (k < i)) {
/* 357 */           this.selectedIndices.set(k);
/* 358 */           select(k);
/* 359 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 363 */       if ((this.selectedIndices.isEmpty()) && 
/* 364 */         (paramInt > 0) && (paramInt < i)) {
/* 365 */         this.selectedIndices.set(paramInt);
/* 366 */         select(paramInt);
/*     */       }
/*     */ 
/* 370 */       this.selectedIndicesSeq.callObservers(new NonIterableChange.SimpleAddChange(0, 1, this.selectedIndicesSeq));
/*     */     } else {
/* 372 */       j = -1;
/* 373 */       if ((paramInt >= 0) && (paramInt < i)) {
/* 374 */         j = paramInt;
/* 375 */         this.selectedIndices.set(paramInt);
/*     */       }
/*     */ 
/* 378 */       for (k = 0; k < paramArrayOfInt.length; k++) {
/* 379 */         int m = paramArrayOfInt[k];
/* 380 */         if ((m >= 0) && (m < i)) {
/* 381 */           j = m;
/* 382 */           this.selectedIndices.set(m);
/*     */         }
/*     */       }
/* 385 */       if (j != -1) {
/* 386 */         select(j);
/*     */       }
/*     */ 
/* 389 */       if (paramArrayOfInt.length == 0)
/*     */       {
/* 391 */         this.selectedIndicesSeq.callObservers(new NonIterableChange.SimpleAddChange(paramInt, paramInt, this.selectedIndicesSeq));
/*     */       }
/*     */       else
/* 394 */         this.selectedIndicesSeq.callObservers(new NonIterableChange.SimpleAddChange(paramInt, paramArrayOfInt[(paramArrayOfInt.length - 1)], this.selectedIndicesSeq));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void selectAll()
/*     */   {
/* 400 */     if (getSelectionMode() == SelectionMode.SINGLE) return;
/*     */ 
/* 402 */     quietClearSelection();
/* 403 */     if (getItemCount() <= 0) return;
/*     */ 
/* 405 */     int i = getItemCount();
/*     */ 
/* 408 */     quietClearSelection();
/* 409 */     this.selectedIndices.set(0, i, true);
/* 410 */     this.selectedIndicesSeq.callObservers(new NonIterableChange.SimpleAddChange(0, i, this.selectedIndicesSeq));
/*     */ 
/* 412 */     setSelectedIndex(i - 1);
/*     */ 
/* 414 */     focus(getSelectedIndex());
/*     */   }
/*     */ 
/*     */   public void selectFirst() {
/* 418 */     if (getSelectionMode() == SelectionMode.SINGLE) {
/* 419 */       quietClearSelection();
/*     */     }
/*     */ 
/* 422 */     if (getItemCount() > 0)
/* 423 */       select(0);
/*     */   }
/*     */ 
/*     */   public void selectLast()
/*     */   {
/* 428 */     if (getSelectionMode() == SelectionMode.SINGLE) {
/* 429 */       quietClearSelection();
/*     */     }
/*     */ 
/* 432 */     int i = getItemCount();
/* 433 */     if ((i > 0) && (getSelectedIndex() < i - 1))
/* 434 */       select(i - 1);
/*     */   }
/*     */ 
/*     */   public void clearSelection(int paramInt)
/*     */   {
/* 439 */     if (paramInt < 0) return;
/*     */ 
/* 443 */     boolean bool = this.selectedIndices.isEmpty();
/* 444 */     this.selectedIndices.clear(paramInt);
/*     */ 
/* 446 */     if ((!bool) && (this.selectedIndices.isEmpty())) {
/* 447 */       clearSelection();
/*     */     }
/*     */ 
/* 452 */     this.selectedIndicesSeq.callObservers(new NonIterableChange.GenericAddRemoveChange(0, 0, Collections.singletonList(Integer.valueOf(paramInt)), this.selectedIndicesSeq));
/*     */   }
/*     */ 
/*     */   public void clearSelection()
/*     */   {
/* 458 */     if (!this.makeAtomic) {
/* 459 */       setSelectedIndex(-1);
/* 460 */       focus(-1);
/*     */     }
/*     */ 
/* 463 */     if (!this.selectedIndices.isEmpty()) {
/* 464 */       AbstractList local6 = new AbstractList() {
/* 465 */         final BitSet clone = (BitSet)MultipleSelectionModelBase.this.selectedIndices.clone();
/*     */ 
/*     */         public Integer get(int paramAnonymousInt) {
/* 468 */           return Integer.valueOf(this.clone.nextSetBit(paramAnonymousInt));
/*     */         }
/*     */ 
/*     */         public int size() {
/* 472 */           return this.clone.cardinality();
/*     */         }
/*     */       };
/* 476 */       quietClearSelection();
/*     */ 
/* 478 */       this.selectedIndicesSeq.callObservers(new NonIterableChange.GenericAddRemoveChange(0, 0, local6, this.selectedIndicesSeq));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void quietClearSelection()
/*     */   {
/* 485 */     this.selectedIndices.clear();
/*     */   }
/*     */ 
/*     */   public boolean isSelected(int paramInt) {
/* 489 */     if ((paramInt >= 0) && (paramInt < getItemCount())) {
/* 490 */       return this.selectedIndices.get(paramInt);
/*     */     }
/*     */ 
/* 493 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/* 497 */     return this.selectedIndices.isEmpty();
/*     */   }
/*     */ 
/*     */   public void selectPrevious() {
/* 501 */     int i = getFocusedIndex();
/*     */ 
/* 503 */     if (getSelectionMode() == SelectionMode.SINGLE) {
/* 504 */       quietClearSelection();
/*     */     }
/*     */ 
/* 507 */     if (i == -1)
/* 508 */       select(getItemCount() - 1);
/* 509 */     else if (i > 0)
/* 510 */       select(i - 1);
/*     */   }
/*     */ 
/*     */   public void selectNext()
/*     */   {
/* 515 */     int i = getFocusedIndex();
/*     */ 
/* 517 */     if (getSelectionMode() == SelectionMode.SINGLE) {
/* 518 */       quietClearSelection();
/*     */     }
/*     */ 
/* 521 */     if (i == -1)
/* 522 */       select(0);
/* 523 */     else if (i != getItemCount() - 1)
/* 524 */       select(i + 1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.MultipleSelectionModelBase
 * JD-Core Version:    0.6.2
 */