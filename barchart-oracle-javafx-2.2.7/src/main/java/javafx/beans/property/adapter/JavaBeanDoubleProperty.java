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
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ public final class JavaBeanDoubleProperty extends DoubleProperty
/*     */   implements JavaBeanProperty<Number>
/*     */ {
/*     */   private final PropertyDescriptor descriptor;
/*     */   private final PropertyDescriptor.Listener<Number> listener;
/*  89 */   private ObservableValue<? extends Number> observable = null;
/*  90 */   private ExpressionHelper<Number> helper = null;
/*     */ 
/*  92 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */   JavaBeanDoubleProperty(PropertyDescriptor paramPropertyDescriptor, Object paramObject) {
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
/*     */   public double get()
/*     */   {
/* 118 */     return ((Double)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Double run() {
/*     */         try {
/* 121 */           return Double.valueOf(((Number)MethodUtil.invoke(JavaBeanDoubleProperty.this.descriptor.getGetter(), JavaBeanDoubleProperty.this.getBean(), (Object[])null)).doubleValue());
/*     */         }
/*     */         catch (IllegalAccessException localIllegalAccessException) {
/* 124 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 126 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/*     */       }
/*     */     }
/*     */     , this.acc)).doubleValue();
/*     */   }
/*     */ 
/*     */   public void set(final double paramDouble)
/*     */   {
/* 141 */     if (isBound()) {
/* 142 */       throw new RuntimeException("A bound value cannot be set.");
/*     */     }
/*     */ 
/* 145 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/*     */         try {
/* 148 */           MethodUtil.invoke(JavaBeanDoubleProperty.this.descriptor.getSetter(), JavaBeanDoubleProperty.this.getBean(), new Object[] { Double.valueOf(paramDouble) });
/* 149 */           ExpressionHelper.fireValueChangedEvent(JavaBeanDoubleProperty.this.helper);
/*     */         } catch (IllegalAccessException localIllegalAccessException) {
/* 151 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 153 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/* 155 */         return null;
/*     */       }
/*     */     }
/*     */     , this.acc);
/*     */   }
/*     */ 
/*     */   public void bind(ObservableValue<? extends Number> paramObservableValue)
/*     */   {
/* 165 */     if (paramObservableValue == null) {
/* 166 */       throw new NullPointerException("Cannot bind to null");
/*     */     }
/*     */ 
/* 169 */     if (!paramObservableValue.equals(this.observable)) {
/* 170 */       unbind();
/* 171 */       set(((Number)paramObservableValue.getValue()).doubleValue());
/* 172 */       this.observable = paramObservableValue;
/* 173 */       this.observable.addListener(this.listener);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unbind()
/*     */   {
/* 182 */     if (this.observable != null) {
/* 183 */       this.observable.removeListener(this.listener);
/* 184 */       this.observable = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 193 */     return this.observable != null;
/*     */   }
/*     */ 
/*     */   public Object getBean()
/*     */   {
/* 201 */     return this.listener.getBean();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 209 */     return this.descriptor.getName();
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/* 217 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/* 225 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 233 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 241 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void fireValueChangedEvent()
/*     */   {
/* 249 */     ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 257 */     this.descriptor.removeListener(this.listener);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 267 */     Object localObject = getBean();
/* 268 */     String str = getName();
/* 269 */     StringBuilder localStringBuilder = new StringBuilder("DoubleProperty [");
/* 270 */     if (localObject != null) {
/* 271 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 273 */     if ((str != null) && (!str.equals(""))) {
/* 274 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 276 */     if (isBound()) {
/* 277 */       localStringBuilder.append("bound, ");
/*     */     }
/* 279 */     localStringBuilder.append("value: ").append(get());
/* 280 */     localStringBuilder.append("]");
/* 281 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.JavaBeanDoubleProperty
 * JD-Core Version:    0.6.2
 */