/*     */ package javafx.collections;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import javafx.beans.WeakListener;
/*     */ 
/*     */ public final class WeakListChangeListener<E>
/*     */   implements ListChangeListener<E>, WeakListener
/*     */ {
/*     */   private final WeakReference<ListChangeListener<E>> ref;
/*     */ 
/*     */   public WeakListChangeListener(ListChangeListener<E> paramListChangeListener)
/*     */   {
/*  85 */     if (paramListChangeListener == null) {
/*  86 */       throw new NullPointerException("Listener must be specified.");
/*     */     }
/*  88 */     this.ref = new WeakReference(paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public boolean wasGarbageCollected()
/*     */   {
/*  96 */     return this.ref.get() == null;
/*     */   }
/*     */ 
/*     */   public void onChanged(ListChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 104 */     ListChangeListener localListChangeListener = (ListChangeListener)this.ref.get();
/* 105 */     if (localListChangeListener != null) {
/* 106 */       localListChangeListener.onChanged(paramChange);
/*     */     }
/*     */     else
/*     */     {
/* 111 */       paramChange.getList().removeListener(this);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.collections.WeakListChangeListener
 * JD-Core Version:    0.6.2
 */