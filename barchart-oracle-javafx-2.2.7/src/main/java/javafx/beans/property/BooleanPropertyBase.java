/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.binding.BooleanBinding;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableBooleanValue;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ 
/*     */ public abstract class BooleanPropertyBase extends BooleanProperty
/*     */ {
/*     */   private boolean value;
/*  70 */   private ObservableBooleanValue observable = null;
/*  71 */   private InvalidationListener listener = null;
/*  72 */   private boolean valid = true;
/*  73 */   private ExpressionHelper<Boolean> helper = null;
/*     */ 
/*     */   public BooleanPropertyBase()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BooleanPropertyBase(boolean paramBoolean)
/*     */   {
/*  88 */     this.value = paramBoolean;
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  93 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  98 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super Boolean> paramChangeListener)
/*     */   {
/* 103 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super Boolean> paramChangeListener)
/*     */   {
/* 108 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 121 */     ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   private void markInvalid() {
/* 125 */     if (this.valid) {
/* 126 */       this.valid = false;
/* 127 */       invalidated();
/* 128 */       fireValueChangedEvent();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void invalidated()
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean get()
/*     */   {
/* 147 */     this.valid = true;
/* 148 */     return this.observable == null ? this.value : this.observable.get();
/*     */   }
/*     */ 
/*     */   public void set(boolean paramBoolean)
/*     */   {
/* 156 */     if (isBound()) {
/* 157 */       throw new RuntimeException("A bound value cannot be set.");
/*     */     }
/* 159 */     if (this.value != paramBoolean) {
/* 160 */       this.value = paramBoolean;
/* 161 */       markInvalid();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 170 */     return this.observable != null;
/*     */   }
/*     */ 
/*     */   public void bind(final ObservableValue<? extends Boolean> paramObservableValue)
/*     */   {
/* 178 */     if (paramObservableValue == null) {
/* 179 */       throw new NullPointerException("Cannot bind to null");
/*     */     }
/*     */ 
/* 182 */     BooleanBinding local1 = (paramObservableValue instanceof ObservableBooleanValue) ? (ObservableBooleanValue)paramObservableValue : new BooleanBinding()
/*     */     {
/*     */       protected boolean computeValue()
/*     */       {
/* 190 */         return ((Boolean)paramObservableValue.getValue()).booleanValue();
/*     */       }
/*     */     };
/* 194 */     if (!local1.equals(this.observable)) {
/* 195 */       unbind();
/* 196 */       this.observable = local1;
/* 197 */       if (this.listener == null) {
/* 198 */         this.listener = new Listener(null);
/*     */       }
/* 200 */       this.observable.addListener(this.listener);
/* 201 */       markInvalid();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unbind()
/*     */   {
/* 210 */     if (this.observable != null) {
/* 211 */       this.value = this.observable.get();
/* 212 */       this.observable.removeListener(this.listener);
/* 213 */       this.observable = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 223 */     Object localObject = getBean();
/* 224 */     String str = getName();
/* 225 */     StringBuilder localStringBuilder = new StringBuilder("BooleanProperty [");
/* 226 */     if (localObject != null) {
/* 227 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 229 */     if ((str != null) && (!str.equals(""))) {
/* 230 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 232 */     if (isBound()) {
/* 233 */       localStringBuilder.append("bound, ");
/* 234 */       if (this.valid)
/* 235 */         localStringBuilder.append("value: ").append(get());
/*     */       else
/* 237 */         localStringBuilder.append("invalid");
/*     */     }
/*     */     else {
/* 240 */       localStringBuilder.append("value: ").append(get());
/*     */     }
/* 242 */     localStringBuilder.append("]");
/* 243 */     return localStringBuilder.toString();
/*     */   }
/*     */   private class Listener implements InvalidationListener {
/*     */     private Listener() {
/*     */     }
/*     */     public void invalidated(Observable paramObservable) {
/* 249 */       BooleanPropertyBase.this.markInvalid();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.BooleanPropertyBase
 * JD-Core Version:    0.6.2
 */