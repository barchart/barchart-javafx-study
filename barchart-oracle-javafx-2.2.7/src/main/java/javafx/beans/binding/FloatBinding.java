/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import com.sun.javafx.binding.BindingHelperObserver;
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class FloatBinding extends FloatExpression
/*     */   implements NumberBinding
/*     */ {
/*     */   private float value;
/*     */   private boolean valid;
/*     */   private BindingHelperObserver observer;
/*  67 */   private ExpressionHelper<Number> helper = null;
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  71 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  76 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/*  81 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/*  86 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   protected final void bind(Observable[] paramArrayOfObservable)
/*     */   {
/*  97 */     if ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0)) {
/*  98 */       if (this.observer == null) {
/*  99 */         this.observer = new BindingHelperObserver(this);
/*     */       }
/* 101 */       for (Observable localObservable : paramArrayOfObservable)
/* 102 */         localObservable.addListener(this.observer);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected final void unbind(Observable[] paramArrayOfObservable)
/*     */   {
/* 114 */     if (this.observer != null) {
/* 115 */       for (Observable localObservable : paramArrayOfObservable) {
/* 116 */         localObservable.removeListener(this.observer);
/*     */       }
/* 118 */       this.observer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ObservableList<?> getDependencies()
/*     */   {
/* 138 */     return FXCollections.emptyObservableList();
/*     */   }
/*     */ 
/*     */   public final float get()
/*     */   {
/* 149 */     if (!this.valid) {
/* 150 */       this.value = computeValue();
/* 151 */       this.valid = true;
/*     */     }
/* 153 */     return this.value;
/*     */   }
/*     */ 
/*     */   protected void onInvalidating()
/*     */   {
/*     */   }
/*     */ 
/*     */   public final void invalidate()
/*     */   {
/* 166 */     if (this.valid) {
/* 167 */       this.valid = false;
/* 168 */       onInvalidating();
/* 169 */       ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean isValid()
/*     */   {
/* 175 */     return this.valid;
/*     */   }
/*     */ 
/*     */   protected abstract float computeValue();
/*     */ 
/*     */   public String toString()
/*     */   {
/* 194 */     return this.valid ? "FloatBinding [value: " + get() + "]" : "FloatBinding [invalid]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.FloatBinding
 * JD-Core Version:    0.6.2
 */