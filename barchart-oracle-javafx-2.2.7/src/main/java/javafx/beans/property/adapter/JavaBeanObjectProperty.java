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
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ public final class JavaBeanObjectProperty<T> extends ObjectProperty<T>
/*     */   implements JavaBeanProperty<T>
/*     */ {
/*     */   private final PropertyDescriptor descriptor;
/*     */   private final PropertyDescriptor.Listener<T> listener;
/*  91 */   private ObservableValue<? extends T> observable = null;
/*  92 */   private ExpressionHelper<T> helper = null;
/*     */ 
/*  94 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */   JavaBeanObjectProperty(PropertyDescriptor paramPropertyDescriptor, Object paramObject) {
/*  97 */     this.descriptor = paramPropertyDescriptor;
/*     */     PropertyDescriptor tmp32_31 = paramPropertyDescriptor; tmp32_31.getClass(); this.listener = new PropertyDescriptor.Listener(tmp32_31, paramObject, this);
/*  99 */     paramPropertyDescriptor.addListener(this.listener);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 107 */     this.descriptor.removeListener(this.listener);
/* 108 */     super.finalize();
/*     */   }
/*     */ 
/*     */   public T get()
/*     */   {
/* 121 */     return AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public T run() {
/*     */         try {
/* 124 */           return MethodUtil.invoke(JavaBeanObjectProperty.this.descriptor.getGetter(), JavaBeanObjectProperty.this.getBean(), (Object[])null);
/*     */         } catch (IllegalAccessException localIllegalAccessException) {
/* 126 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 128 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/*     */       }
/*     */     }
/*     */     , this.acc);
/*     */   }
/*     */ 
/*     */   public void set(final T paramT)
/*     */   {
/* 143 */     if (isBound()) {
/* 144 */       throw new RuntimeException("A bound value cannot be set.");
/*     */     }
/*     */ 
/* 147 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/*     */         try {
/* 150 */           MethodUtil.invoke(JavaBeanObjectProperty.this.descriptor.getSetter(), JavaBeanObjectProperty.this.getBean(), new Object[] { paramT });
/* 151 */           ExpressionHelper.fireValueChangedEvent(JavaBeanObjectProperty.this.helper);
/*     */         } catch (IllegalAccessException localIllegalAccessException) {
/* 153 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 155 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/* 157 */         return null;
/*     */       }
/*     */     }
/*     */     , this.acc);
/*     */   }
/*     */ 
/*     */   public void bind(ObservableValue<? extends T> paramObservableValue)
/*     */   {
/* 168 */     if (paramObservableValue == null) {
/* 169 */       throw new NullPointerException("Cannot bind to null");
/*     */     }
/*     */ 
/* 172 */     if (!paramObservableValue.equals(this.observable)) {
/* 173 */       unbind();
/* 174 */       set(paramObservableValue.getValue());
/* 175 */       this.observable = paramObservableValue;
/* 176 */       this.observable.addListener(this.listener);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unbind()
/*     */   {
/* 185 */     if (this.observable != null) {
/* 186 */       this.observable.removeListener(this.listener);
/* 187 */       this.observable = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 196 */     return this.observable != null;
/*     */   }
/*     */ 
/*     */   public Object getBean()
/*     */   {
/* 204 */     return this.listener.getBean();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 212 */     return this.descriptor.getName();
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super T> paramChangeListener)
/*     */   {
/* 220 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super T> paramChangeListener)
/*     */   {
/* 228 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 236 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 244 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void fireValueChangedEvent()
/*     */   {
/* 252 */     ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 260 */     this.descriptor.removeListener(this.listener);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 270 */     Object localObject = getBean();
/* 271 */     String str = getName();
/* 272 */     StringBuilder localStringBuilder = new StringBuilder("ObjectProperty [");
/* 273 */     if (localObject != null) {
/* 274 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 276 */     if ((str != null) && (!str.equals(""))) {
/* 277 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 279 */     if (isBound()) {
/* 280 */       localStringBuilder.append("bound, ");
/*     */     }
/* 282 */     localStringBuilder.append("value: ").append(get());
/* 283 */     localStringBuilder.append("]");
/* 284 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.JavaBeanObjectProperty
 * JD-Core Version:    0.6.2
 */