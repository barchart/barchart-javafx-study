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
/*     */ public abstract class BooleanBinding extends BooleanExpression
/*     */   implements Binding<Boolean>
/*     */ {
/*     */   private boolean value;
/*  63 */   private boolean valid = false;
/*     */   private BindingHelperObserver observer;
/*  65 */   private ExpressionHelper<Boolean> helper = null;
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  69 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  74 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super Boolean> paramChangeListener)
/*     */   {
/*  79 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super Boolean> paramChangeListener)
/*     */   {
/*  84 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   protected final void bind(Observable[] paramArrayOfObservable)
/*     */   {
/*  95 */     if ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0)) {
/*  96 */       if (this.observer == null) {
/*  97 */         this.observer = new BindingHelperObserver(this);
/*     */       }
/*  99 */       for (Observable localObservable : paramArrayOfObservable)
/* 100 */         localObservable.addListener(this.observer);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected final void unbind(Observable[] paramArrayOfObservable)
/*     */   {
/* 112 */     if (this.observer != null) {
/* 113 */       for (Observable localObservable : paramArrayOfObservable) {
/* 114 */         localObservable.removeListener(this.observer);
/*     */       }
/* 116 */       this.observer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ObservableList<?> getDependencies()
/*     */   {
/* 136 */     return FXCollections.emptyObservableList();
/*     */   }
/*     */ 
/*     */   public final boolean get()
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
/*     */   protected abstract boolean computeValue();
/*     */ 
/*     */   public String toString()
/*     */   {
/* 194 */     return this.valid ? "BooleanBinding [value: " + get() + "]" : "BooleanBinding [invalid]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.BooleanBinding
 * JD-Core Version:    0.6.2
 */