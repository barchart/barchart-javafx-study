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
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ public final class JavaBeanStringProperty extends StringProperty
/*     */   implements JavaBeanProperty<String>
/*     */ {
/*     */   private final PropertyDescriptor descriptor;
/*     */   private final PropertyDescriptor.Listener<String> listener;
/*  89 */   private ObservableValue<? extends String> observable = null;
/*  90 */   private ExpressionHelper<String> helper = null;
/*     */ 
/*  92 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */   JavaBeanStringProperty(PropertyDescriptor paramPropertyDescriptor, Object paramObject) {
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
/*     */   public String get()
/*     */   {
/* 118 */     return (String)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public String run() {
/*     */         try {
/* 121 */           return (String)MethodUtil.invoke(JavaBeanStringProperty.this.descriptor.getGetter(), JavaBeanStringProperty.this.getBean(), (Object[])null);
/*     */         } catch (IllegalAccessException localIllegalAccessException) {
/* 123 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 125 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/*     */       }
/*     */     }
/*     */     , this.acc);
/*     */   }
/*     */ 
/*     */   public void set(final String paramString)
/*     */   {
/* 140 */     if (isBound()) {
/* 141 */       throw new RuntimeException("A bound value cannot be set.");
/*     */     }
/* 143 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/*     */         try {
/* 146 */           MethodUtil.invoke(JavaBeanStringProperty.this.descriptor.getSetter(), JavaBeanStringProperty.this.getBean(), new Object[] { paramString });
/* 147 */           ExpressionHelper.fireValueChangedEvent(JavaBeanStringProperty.this.helper);
/*     */         } catch (IllegalAccessException localIllegalAccessException) {
/* 149 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 151 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/* 153 */         return null;
/*     */       }
/*     */     }
/*     */     , this.acc);
/*     */   }
/*     */ 
/*     */   public void bind(ObservableValue<? extends String> paramObservableValue)
/*     */   {
/* 163 */     if (paramObservableValue == null) {
/* 164 */       throw new NullPointerException("Cannot bind to null");
/*     */     }
/*     */ 
/* 167 */     if (!paramObservableValue.equals(this.observable)) {
/* 168 */       unbind();
/* 169 */       set((String)paramObservableValue.getValue());
/* 170 */       this.observable = paramObservableValue;
/* 171 */       this.observable.addListener(this.listener);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unbind()
/*     */   {
/* 180 */     if (this.observable != null) {
/* 181 */       this.observable.removeListener(this.listener);
/* 182 */       this.observable = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 191 */     return this.observable != null;
/*     */   }
/*     */ 
/*     */   public Object getBean()
/*     */   {
/* 199 */     return this.listener.getBean();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 207 */     return this.descriptor.getName();
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super String> paramChangeListener)
/*     */   {
/* 215 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super String> paramChangeListener)
/*     */   {
/* 223 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 231 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 239 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void fireValueChangedEvent()
/*     */   {
/* 247 */     ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 255 */     this.descriptor.removeListener(this.listener);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.JavaBeanStringProperty
 * JD-Core Version:    0.6.2
 */