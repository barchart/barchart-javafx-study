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
/*     */ public abstract class StringBinding extends StringExpression
/*     */   implements Binding<String>
/*     */ {
/*     */   private String value;
/*  64 */   private boolean valid = false;
/*     */   private BindingHelperObserver observer;
/*  66 */   private ExpressionHelper<String> helper = null;
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  70 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  75 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super String> paramChangeListener)
/*     */   {
/*  80 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super String> paramChangeListener)
/*     */   {
/*  85 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   protected final void bind(Observable[] paramArrayOfObservable)
/*     */   {
/*  96 */     if ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0)) {
/*  97 */       if (this.observer == null) {
/*  98 */         this.observer = new BindingHelperObserver(this);
/*     */       }
/* 100 */       for (Observable localObservable : paramArrayOfObservable)
/* 101 */         localObservable.addListener(this.observer);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected final void unbind(Observable[] paramArrayOfObservable)
/*     */   {
/* 113 */     if (this.observer != null) {
/* 114 */       for (Observable localObservable : paramArrayOfObservable) {
/* 115 */         localObservable.removeListener(this.observer);
/*     */       }
/* 117 */       this.observer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ObservableList<?> getDependencies()
/*     */   {
/* 137 */     return FXCollections.emptyObservableList();
/*     */   }
/*     */ 
/*     */   public final String get()
/*     */   {
/* 150 */     if (!this.valid) {
/* 151 */       this.value = computeValue();
/* 152 */       this.valid = true;
/*     */     }
/* 154 */     return this.value;
/*     */   }
/*     */ 
/*     */   protected void onInvalidating()
/*     */   {
/*     */   }
/*     */ 
/*     */   public final void invalidate()
/*     */   {
/* 167 */     if (this.valid) {
/* 168 */       this.valid = false;
/* 169 */       onInvalidating();
/* 170 */       ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean isValid()
/*     */   {
/* 176 */     return this.valid;
/*     */   }
/*     */ 
/*     */   protected abstract String computeValue();
/*     */ 
/*     */   public String toString()
/*     */   {
/* 195 */     return this.valid ? "StringBinding [value: " + get() + "]" : "StringBinding [invalid]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.StringBinding
 * JD-Core Version:    0.6.2
 */