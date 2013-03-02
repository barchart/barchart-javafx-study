/*     */ package javafx.collections;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import javafx.beans.WeakListener;
/*     */ 
/*     */ public final class WeakSetChangeListener<E>
/*     */   implements SetChangeListener<E>, WeakListener
/*     */ {
/*     */   private final WeakReference<SetChangeListener<E>> ref;
/*     */ 
/*     */   public WeakSetChangeListener(SetChangeListener<E> paramSetChangeListener)
/*     */   {
/*  85 */     if (paramSetChangeListener == null) {
/*  86 */       throw new NullPointerException("Listener must be specified.");
/*     */     }
/*  88 */     this.ref = new WeakReference(paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   public boolean wasGarbageCollected()
/*     */   {
/*  96 */     return this.ref.get() == null;
/*     */   }
/*     */ 
/*     */   public void onChanged(SetChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 104 */     SetChangeListener localSetChangeListener = (SetChangeListener)this.ref.get();
/* 105 */     if (localSetChangeListener != null) {
/* 106 */       localSetChangeListener.onChanged(paramChange);
/*     */     }
/*     */     else
/*     */     {
/* 111 */       paramChange.getSet().removeListener(this);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.collections.WeakSetChangeListener
 * JD-Core Version:    0.6.2
 */