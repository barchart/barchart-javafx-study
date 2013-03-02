/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor;
/*     */ import com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor.ReadOnlyListener;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javafx.beans.property.ReadOnlyObjectPropertyBase;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ public final class ReadOnlyJavaBeanObjectProperty<T> extends ReadOnlyObjectPropertyBase<T>
/*     */   implements ReadOnlyJavaBeanProperty<T>
/*     */ {
/*     */   private final ReadOnlyPropertyDescriptor descriptor;
/*     */   private final ReadOnlyPropertyDescriptor.ReadOnlyListener<T> listener;
/*  83 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */   ReadOnlyJavaBeanObjectProperty(ReadOnlyPropertyDescriptor paramReadOnlyPropertyDescriptor, Object paramObject) {
/*  86 */     this.descriptor = paramReadOnlyPropertyDescriptor;
/*     */     ReadOnlyPropertyDescriptor tmp22_21 = paramReadOnlyPropertyDescriptor; tmp22_21.getClass(); this.listener = new ReadOnlyPropertyDescriptor.ReadOnlyListener(tmp22_21, paramObject, this);
/*  88 */     paramReadOnlyPropertyDescriptor.addListener(this.listener);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  96 */     this.descriptor.removeListener(this.listener);
/*  97 */     super.finalize();
/*     */   }
/*     */ 
/*     */   public T get()
/*     */   {
/* 109 */     return AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public T run() {
/*     */         try {
/* 112 */           return MethodUtil.invoke(ReadOnlyJavaBeanObjectProperty.this.descriptor.getGetter(), ReadOnlyJavaBeanObjectProperty.this.getBean(), (Object[])null);
/*     */         } catch (IllegalAccessException localIllegalAccessException) {
/* 114 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 116 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/*     */       }
/*     */     }
/*     */     , this.acc);
/*     */   }
/*     */ 
/*     */   public Object getBean()
/*     */   {
/* 127 */     return this.listener.getBean();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 135 */     return this.descriptor.getName();
/*     */   }
/*     */ 
/*     */   public void fireValueChangedEvent()
/*     */   {
/* 143 */     super.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 151 */     this.descriptor.removeListener(this.listener);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.ReadOnlyJavaBeanObjectProperty
 * JD-Core Version:    0.6.2
 */