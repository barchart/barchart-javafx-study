/*     */ package javafx.collections;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import javafx.beans.WeakListener;
/*     */ 
/*     */ public final class WeakMapChangeListener<K, V>
/*     */   implements MapChangeListener<K, V>, WeakListener
/*     */ {
/*     */   private final WeakReference<MapChangeListener<K, V>> ref;
/*     */ 
/*     */   public WeakMapChangeListener(MapChangeListener<K, V> paramMapChangeListener)
/*     */   {
/*  85 */     if (paramMapChangeListener == null) {
/*  86 */       throw new NullPointerException("Listener must be specified.");
/*     */     }
/*  88 */     this.ref = new WeakReference(paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public boolean wasGarbageCollected()
/*     */   {
/*  96 */     return this.ref.get() == null;
/*     */   }
/*     */ 
/*     */   public void onChanged(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */   {
/* 104 */     MapChangeListener localMapChangeListener = (MapChangeListener)this.ref.get();
/* 105 */     if (localMapChangeListener != null) {
/* 106 */       localMapChangeListener.onChanged(paramChange);
/*     */     }
/*     */     else
/*     */     {
/* 111 */       paramChange.getMap().removeListener(this);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.collections.WeakMapChangeListener
 * JD-Core Version:    0.6.2
 */