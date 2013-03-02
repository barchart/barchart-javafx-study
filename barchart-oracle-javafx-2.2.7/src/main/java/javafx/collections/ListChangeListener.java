/*     */ package javafx.collections;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract interface ListChangeListener<E>
/*     */ {
/*     */   public abstract void onChanged(Change<? extends E> paramChange);
/*     */ 
/*     */   public static abstract class Change<E>
/*     */   {
/*     */     private final ObservableList<E> list;
/*     */ 
/*     */     public abstract boolean next();
/*     */ 
/*     */     public abstract void reset();
/*     */ 
/*     */     public Change(ObservableList<E> paramObservableList)
/*     */     {
/* 116 */       this.list = paramObservableList;
/*     */     }
/*     */ 
/*     */     public ObservableList<E> getList()
/*     */     {
/* 124 */       return this.list;
/*     */     }
/*     */ 
/*     */     public abstract int getFrom();
/*     */ 
/*     */     public abstract int getTo();
/*     */ 
/*     */     public abstract List<E> getRemoved();
/*     */ 
/*     */     public boolean wasPermutated()
/*     */     {
/* 156 */       return getPermutation().length != 0;
/*     */     }
/*     */ 
/*     */     public boolean wasAdded()
/*     */     {
/* 165 */       return (!wasPermutated()) && (getFrom() < getTo());
/*     */     }
/*     */ 
/*     */     public boolean wasRemoved()
/*     */     {
/* 174 */       return !getRemoved().isEmpty();
/*     */     }
/*     */ 
/*     */     public boolean wasReplaced()
/*     */     {
/* 183 */       return (wasAdded()) && (wasRemoved());
/*     */     }
/*     */ 
/*     */     public boolean wasUpdated()
/*     */     {
/* 195 */       return false;
/*     */     }
/*     */ 
/*     */     public List<E> getAddedSubList()
/*     */     {
/* 212 */       return wasPermutated() ? Collections.emptyList() : getList().subList(getFrom(), getTo());
/*     */     }
/*     */ 
/*     */     public int getRemovedSize()
/*     */     {
/* 221 */       return getRemoved().size();
/*     */     }
/*     */ 
/*     */     public int getAddedSize()
/*     */     {
/* 230 */       return wasPermutated() ? 0 : getTo() - getFrom();
/*     */     }
/*     */ 
/*     */     protected abstract int[] getPermutation();
/*     */ 
/*     */     public int getPermutation(int paramInt)
/*     */     {
/* 257 */       return getPermutation()[(paramInt - getFrom())];
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.collections.ListChangeListener
 * JD-Core Version:    0.6.2
 */