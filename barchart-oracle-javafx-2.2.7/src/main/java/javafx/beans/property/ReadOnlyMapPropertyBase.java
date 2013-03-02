/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.MapExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public abstract class ReadOnlyMapPropertyBase<K, V> extends ReadOnlyMapProperty<K, V>
/*     */ {
/*     */   private MapExpressionHelper<K, V> helper;
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  66 */     this.helper = MapExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  71 */     this.helper = MapExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */   {
/*  76 */     this.helper = MapExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */   {
/*  81 */     this.helper = MapExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */   {
/*  86 */     this.helper = MapExpressionHelper.addListener(this.helper, this, paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */   {
/*  91 */     this.helper = MapExpressionHelper.removeListener(this.helper, paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 106 */     MapExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */   {
/* 123 */     MapExpressionHelper.fireValueChangedEvent(this.helper, paramChange);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyMapPropertyBase
 * JD-Core Version:    0.6.2
 */