/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ 
/*     */ public abstract class StringPropertyBase extends StringProperty
/*     */ {
/*     */   private String value;
/*  70 */   private ObservableValue<? extends String> observable = null;
/*  71 */   private InvalidationListener listener = null;
/*  72 */   private boolean valid = true;
/*  73 */   private ExpressionHelper<String> helper = null;
/*     */ 
/*     */   public StringPropertyBase()
/*     */   {
/*     */   }
/*     */ 
/*     */   public StringPropertyBase(String paramString)
/*     */   {
/*  88 */     this.value = paramString;
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
/*     */   public void addListener(ChangeListener<? super String> paramChangeListener)
/*     */   {
/* 103 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super String> paramChangeListener)
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
/*     */   public String get()
/*     */   {
/* 147 */     this.valid = true;
/* 148 */     return this.observable == null ? this.value : (String)this.observable.getValue();
/*     */   }
/*     */ 
/*     */   public void set(String paramString)
/*     */   {
/* 156 */     if (isBound()) {
/* 157 */       throw new RuntimeException("A bound value cannot be set.");
/*     */     }
/* 159 */     if (this.value == null ? paramString != null : !this.value.equals(paramString)) {
/* 160 */       this.value = paramString;
/* 161 */       markInvalid();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 170 */     return this.observable != null;
/*     */   }
/*     */ 
/*     */   public void bind(ObservableValue<? extends String> paramObservableValue)
/*     */   {
/* 178 */     if (paramObservableValue == null) {
/* 179 */       throw new NullPointerException("Cannot bind to null");
/*     */     }
/* 181 */     if (!paramObservableValue.equals(this.observable)) {
/* 182 */       unbind();
/* 183 */       this.observable = paramObservableValue;
/* 184 */       if (this.listener == null) {
/* 185 */         this.listener = new Listener(null);
/*     */       }
/* 187 */       this.observable.addListener(this.listener);
/* 188 */       markInvalid();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unbind()
/*     */   {
/* 197 */     if (this.observable != null) {
/* 198 */       this.value = ((String)this.observable.getValue());
/* 199 */       this.observable.removeListener(this.listener);
/* 200 */       this.observable = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 210 */     Object localObject = getBean();
/* 211 */     String str = getName();
/* 212 */     StringBuilder localStringBuilder = new StringBuilder("StringProperty [");
/* 213 */     if (localObject != null) {
/* 214 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 216 */     if ((str != null) && (!str.equals(""))) {
/* 217 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 219 */     if (isBound()) {
/* 220 */       localStringBuilder.append("bound, ");
/* 221 */       if (this.valid)
/* 222 */         localStringBuilder.append("value: ").append(get());
/*     */       else
/* 224 */         localStringBuilder.append("invalid");
/*     */     }
/*     */     else {
/* 227 */       localStringBuilder.append("value: ").append(get());
/*     */     }
/* 229 */     localStringBuilder.append("]");
/* 230 */     return localStringBuilder.toString();
/*     */   }
/*     */   private class Listener implements InvalidationListener {
/*     */     private Listener() {
/*     */     }
/*     */     public void invalidated(Observable paramObservable) {
/* 236 */       StringPropertyBase.this.markInvalid();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.StringPropertyBase
 * JD-Core Version:    0.6.2
 */