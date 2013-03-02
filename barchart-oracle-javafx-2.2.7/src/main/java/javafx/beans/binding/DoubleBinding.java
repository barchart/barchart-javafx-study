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
/*     */ public abstract class DoubleBinding extends DoubleExpression
/*     */   implements NumberBinding
/*     */ {
/*     */   private double value;
/*     */   private boolean valid;
/*     */   private BindingHelperObserver observer;
/* 117 */   private ExpressionHelper<Number> helper = null;
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 121 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 126 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/* 131 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/* 136 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   protected final void bind(Observable[] paramArrayOfObservable)
/*     */   {
/* 147 */     if ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0)) {
/* 148 */       if (this.observer == null) {
/* 149 */         this.observer = new BindingHelperObserver(this);
/*     */       }
/* 151 */       for (Observable localObservable : paramArrayOfObservable)
/* 152 */         localObservable.addListener(this.observer);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected final void unbind(Observable[] paramArrayOfObservable)
/*     */   {
/* 164 */     if (this.observer != null) {
/* 165 */       for (Observable localObservable : paramArrayOfObservable) {
/* 166 */         localObservable.removeListener(this.observer);
/*     */       }
/* 168 */       this.observer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ObservableList<?> getDependencies()
/*     */   {
/* 188 */     return FXCollections.emptyObservableList();
/*     */   }
/*     */ 
/*     */   public final double get()
/*     */   {
/* 201 */     if (!this.valid) {
/* 202 */       this.value = computeValue();
/* 203 */       this.valid = true;
/*     */     }
/* 205 */     return this.value;
/*     */   }
/*     */ 
/*     */   protected void onInvalidating()
/*     */   {
/*     */   }
/*     */ 
/*     */   public final void invalidate()
/*     */   {
/* 218 */     if (this.valid) {
/* 219 */       this.valid = false;
/* 220 */       onInvalidating();
/* 221 */       ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean isValid()
/*     */   {
/* 227 */     return this.valid;
/*     */   }
/*     */ 
/*     */   protected abstract double computeValue();
/*     */ 
/*     */   public String toString()
/*     */   {
/* 246 */     return this.valid ? "DoubleBinding [value: " + get() + "]" : "DoubleBinding [invalid]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.DoubleBinding
 * JD-Core Version:    0.6.2
 */