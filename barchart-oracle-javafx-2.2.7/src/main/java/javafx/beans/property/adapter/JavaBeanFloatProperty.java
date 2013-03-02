/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import com.sun.javafx.property.adapter.PropertyDescriptor;
/*     */ import com.sun.javafx.property.adapter.PropertyDescriptor.Listener;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.property.FloatProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ public final class JavaBeanFloatProperty extends FloatProperty
/*     */   implements JavaBeanProperty<Number>
/*     */ {
/*     */   private final PropertyDescriptor descriptor;
/*     */   private final PropertyDescriptor.Listener<Number> listener;
/*  89 */   private ObservableValue<? extends Number> observable = null;
/*  90 */   private ExpressionHelper<Number> helper = null;
/*     */ 
/*  92 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */   JavaBeanFloatProperty(PropertyDescriptor paramPropertyDescriptor, Object paramObject) {
/*  95 */     this.descriptor = paramPropertyDescriptor;
/*     */     PropertyDescriptor tmp32_31 = paramPropertyDescriptor; tmp32_31.getClass(); this.listener = new PropertyDescriptor.Listener(tmp32_31, paramObject, this);
/*  97 */     paramPropertyDescriptor.addListener(this.listener);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 105 */     this.descriptor.removeListener(this.listener);
/* 106 */     super.finalize();
/*     */   }
/*     */ 
/*     */   public float get()
/*     */   {
/* 118 */     return ((Float)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Float run() {
/*     */         try {
/* 121 */           return Float.valueOf(((Number)MethodUtil.invoke(JavaBeanFloatProperty.this.descriptor.getGetter(), JavaBeanFloatProperty.this.getBean(), (Object[])null)).floatValue());
/*     */         }
/*     */         catch (IllegalAccessException localIllegalAccessException) {
/* 124 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 126 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/*     */       }
/*     */     }
/*     */     , this.acc)).floatValue();
/*     */   }
/*     */ 
/*     */   public void set(final float paramFloat)
/*     */   {
/* 141 */     if (isBound()) {
/* 142 */       throw new RuntimeException("A bound value cannot be set.");
/*     */     }
/* 144 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/*     */         try {
/* 147 */           MethodUtil.invoke(JavaBeanFloatProperty.this.descriptor.getSetter(), JavaBeanFloatProperty.this.getBean(), new Object[] { Float.valueOf(paramFloat) });
/* 148 */           ExpressionHelper.fireValueChangedEvent(JavaBeanFloatProperty.this.helper);
/*     */         } catch (IllegalAccessException localIllegalAccessException) {
/* 150 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 152 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/* 154 */         return null;
/*     */       }
/*     */     }
/*     */     , this.acc);
/*     */   }
/*     */ 
/*     */   public void bind(ObservableValue<? extends Number> paramObservableValue)
/*     */   {
/* 164 */     if (paramObservableValue == null) {
/* 165 */       throw new NullPointerException("Cannot bind to null");
/*     */     }
/*     */ 
/* 168 */     if (!paramObservableValue.equals(this.observable)) {
/* 169 */       unbind();
/* 170 */       set(((Number)paramObservableValue.getValue()).floatValue());
/* 171 */       this.observable = paramObservableValue;
/* 172 */       this.observable.addListener(this.listener);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unbind()
/*     */   {
/* 181 */     if (this.observable != null) {
/* 182 */       this.observable.removeListener(this.listener);
/* 183 */       this.observable = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 192 */     return this.observable != null;
/*     */   }
/*     */ 
/*     */   public Object getBean()
/*     */   {
/* 200 */     return this.listener.getBean();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 208 */     return this.descriptor.getName();
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/* 216 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/* 224 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 232 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 240 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void fireValueChangedEvent()
/*     */   {
/* 248 */     ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 256 */     this.descriptor.removeListener(this.listener);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 266 */     Object localObject = getBean();
/* 267 */     String str = getName();
/* 268 */     StringBuilder localStringBuilder = new StringBuilder("FloatProperty [");
/* 269 */     if (localObject != null) {
/* 270 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 272 */     if ((str != null) && (!str.equals(""))) {
/* 273 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 275 */     if (isBound()) {
/* 276 */       localStringBuilder.append("bound, ");
/*     */     }
/* 278 */     localStringBuilder.append("value: ").append(get());
/* 279 */     localStringBuilder.append("]");
/* 280 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.JavaBeanFloatProperty
 * JD-Core Version:    0.6.2
 */