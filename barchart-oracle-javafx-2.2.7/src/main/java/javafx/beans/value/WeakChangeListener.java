/*     */ package javafx.beans.value;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import javafx.beans.WeakListener;
/*     */ 
/*     */ public final class WeakChangeListener<T>
/*     */   implements ChangeListener<T>, WeakListener
/*     */ {
/*     */   private final WeakReference<ChangeListener<T>> ref;
/*     */ 
/*     */   public WeakChangeListener(ChangeListener<T> paramChangeListener)
/*     */   {
/*  85 */     if (paramChangeListener == null) {
/*  86 */       throw new NullPointerException("Listener must be specified.");
/*     */     }
/*  88 */     this.ref = new WeakReference(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public boolean wasGarbageCollected()
/*     */   {
/*  96 */     return this.ref.get() == null;
/*     */   }
/*     */ 
/*     */   public void changed(ObservableValue<? extends T> paramObservableValue, T paramT1, T paramT2)
/*     */   {
/* 105 */     ChangeListener localChangeListener = (ChangeListener)this.ref.get();
/* 106 */     if (localChangeListener != null) {
/* 107 */       localChangeListener.changed(paramObservableValue, paramT1, paramT2);
/*     */     }
/*     */     else
/*     */     {
/* 112 */       paramObservableValue.removeListener(this);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.value.WeakChangeListener
 * JD-Core Version:    0.6.2
 */