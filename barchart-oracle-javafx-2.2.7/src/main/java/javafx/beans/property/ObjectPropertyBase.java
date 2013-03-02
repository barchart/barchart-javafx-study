/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ 
/*     */ public abstract class ObjectPropertyBase<T> extends ObjectProperty<T>
/*     */ {
/*     */   private T value;
/*  72 */   private ObservableValue<? extends T> observable = null;
/*  73 */   private InvalidationListener listener = null;
/*  74 */   private boolean valid = true;
/*  75 */   private ExpressionHelper<T> helper = null;
/*     */ 
/*     */   public ObjectPropertyBase()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ObjectPropertyBase(T paramT)
/*     */   {
/*  90 */     this.value = paramT;
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  95 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 100 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super T> paramChangeListener)
/*     */   {
/* 105 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super T> paramChangeListener)
/*     */   {
/* 110 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 123 */     ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   private void markInvalid() {
/* 127 */     if (this.valid) {
/* 128 */       this.valid = false;
/* 129 */       invalidated();
/* 130 */       fireValueChangedEvent();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void invalidated()
/*     */   {
/*     */   }
/*     */ 
/*     */   public T get()
/*     */   {
/* 149 */     this.valid = true;
/* 150 */     return this.observable == null ? this.value : this.observable.getValue();
/*     */   }
/*     */ 
/*     */   public void set(T paramT)
/*     */   {
/* 158 */     if (isBound()) {
/* 159 */       throw new RuntimeException("A bound value cannot be set.");
/*     */     }
/* 161 */     if (this.value != paramT) {
/* 162 */       this.value = paramT;
/* 163 */       markInvalid();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 172 */     return this.observable != null;
/*     */   }
/*     */ 
/*     */   public void bind(ObservableValue<? extends T> paramObservableValue)
/*     */   {
/* 180 */     if (paramObservableValue == null) {
/* 181 */       throw new NullPointerException("Cannot bind to null");
/*     */     }
/*     */ 
/* 184 */     if (!paramObservableValue.equals(this.observable)) {
/* 185 */       unbind();
/* 186 */       this.observable = paramObservableValue;
/* 187 */       if (this.listener == null) {
/* 188 */         this.listener = new Listener(null);
/*     */       }
/* 190 */       this.observable.addListener(this.listener);
/* 191 */       markInvalid();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unbind()
/*     */   {
/* 200 */     if (this.observable != null) {
/* 201 */       this.value = this.observable.getValue();
/* 202 */       this.observable.removeListener(this.listener);
/* 203 */       this.observable = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 213 */     Object localObject = getBean();
/* 214 */     String str = getName();
/* 215 */     StringBuilder localStringBuilder = new StringBuilder("ObjectProperty [");
/* 216 */     if (localObject != null) {
/* 217 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 219 */     if ((str != null) && (!str.equals(""))) {
/* 220 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 222 */     if (isBound()) {
/* 223 */       localStringBuilder.append("bound, ");
/* 224 */       if (this.valid)
/* 225 */         localStringBuilder.append("value: ").append(get());
/*     */       else
/* 227 */         localStringBuilder.append("invalid");
/*     */     }
/*     */     else {
/* 230 */       localStringBuilder.append("value: ").append(get());
/*     */     }
/* 232 */     localStringBuilder.append("]");
/* 233 */     return localStringBuilder.toString();
/*     */   }
/*     */   private class Listener implements InvalidationListener {
/*     */     private Listener() {
/*     */     }
/*     */     public void invalidated(Observable paramObservable) {
/* 239 */       ObjectPropertyBase.this.markInvalid();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ObjectPropertyBase
 * JD-Core Version:    0.6.2
 */