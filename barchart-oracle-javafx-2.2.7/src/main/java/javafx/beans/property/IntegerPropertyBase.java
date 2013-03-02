/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.binding.IntegerBinding;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableNumberValue;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ 
/*     */ public abstract class IntegerPropertyBase extends IntegerProperty
/*     */ {
/*     */   private int value;
/*  72 */   private ObservableNumberValue observable = null;
/*  73 */   private InvalidationListener listener = null;
/*  74 */   private boolean valid = true;
/*  75 */   private ExpressionHelper<Number> helper = null;
/*     */ 
/*     */   public IntegerPropertyBase()
/*     */   {
/*     */   }
/*     */ 
/*     */   public IntegerPropertyBase(int paramInt)
/*     */   {
/*  90 */     this.value = paramInt;
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
/*     */   public void addListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/* 105 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super Number> paramChangeListener)
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
/*     */   public int get()
/*     */   {
/* 149 */     this.valid = true;
/* 150 */     return this.observable == null ? this.value : this.observable.intValue();
/*     */   }
/*     */ 
/*     */   public void set(int paramInt)
/*     */   {
/* 158 */     if (isBound()) {
/* 159 */       throw new RuntimeException("A bound value cannot be set.");
/*     */     }
/* 161 */     if (this.value != paramInt) {
/* 162 */       this.value = paramInt;
/* 163 */       markInvalid();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 172 */     return this.observable != null;
/*     */   }
/*     */ 
/*     */   public void bind(final ObservableValue<? extends Number> paramObservableValue)
/*     */   {
/* 180 */     if (paramObservableValue == null) {
/* 181 */       throw new NullPointerException("Cannot bind to null");
/*     */     }
/*     */ 
/* 184 */     IntegerBinding local1 = (paramObservableValue instanceof ObservableNumberValue) ? (ObservableNumberValue)paramObservableValue : new IntegerBinding()
/*     */     {
/*     */       protected int computeValue()
/*     */       {
/* 192 */         return ((Number)paramObservableValue.getValue()).intValue();
/*     */       }
/*     */     };
/* 196 */     if (!local1.equals(this.observable)) {
/* 197 */       unbind();
/* 198 */       this.observable = local1;
/* 199 */       if (this.listener == null) {
/* 200 */         this.listener = new Listener(null);
/*     */       }
/* 202 */       this.observable.addListener(this.listener);
/* 203 */       markInvalid();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unbind()
/*     */   {
/* 212 */     if (this.observable != null) {
/* 213 */       this.value = this.observable.intValue();
/* 214 */       this.observable.removeListener(this.listener);
/* 215 */       this.observable = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 225 */     Object localObject = getBean();
/* 226 */     String str = getName();
/* 227 */     StringBuilder localStringBuilder = new StringBuilder("IntegerProperty [");
/* 228 */     if (localObject != null) {
/* 229 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 231 */     if ((str != null) && (!str.equals(""))) {
/* 232 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 234 */     if (isBound()) {
/* 235 */       localStringBuilder.append("bound, ");
/* 236 */       if (this.valid)
/* 237 */         localStringBuilder.append("value: ").append(get());
/*     */       else
/* 239 */         localStringBuilder.append("invalid");
/*     */     }
/*     */     else {
/* 242 */       localStringBuilder.append("value: ").append(get());
/*     */     }
/* 244 */     localStringBuilder.append("]");
/* 245 */     return localStringBuilder.toString();
/*     */   }
/*     */   private class Listener implements InvalidationListener {
/*     */     private Listener() {
/*     */     }
/*     */     public void invalidated(Observable paramObservable) {
/* 251 */       IntegerPropertyBase.this.markInvalid();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.IntegerPropertyBase
 * JD-Core Version:    0.6.2
 */