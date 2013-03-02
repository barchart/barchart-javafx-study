/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor;
/*     */ import com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor.ReadOnlyListener;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javafx.beans.property.ReadOnlyStringPropertyBase;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ public final class ReadOnlyJavaBeanStringProperty extends ReadOnlyStringPropertyBase
/*     */   implements ReadOnlyJavaBeanProperty<String>
/*     */ {
/*     */   private final ReadOnlyPropertyDescriptor descriptor;
/*     */   private final ReadOnlyPropertyDescriptor.ReadOnlyListener<String> listener;
/*  81 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */   ReadOnlyJavaBeanStringProperty(ReadOnlyPropertyDescriptor paramReadOnlyPropertyDescriptor, Object paramObject) {
/*  84 */     this.descriptor = paramReadOnlyPropertyDescriptor;
/*     */     ReadOnlyPropertyDescriptor tmp22_21 = paramReadOnlyPropertyDescriptor; tmp22_21.getClass(); this.listener = new ReadOnlyPropertyDescriptor.ReadOnlyListener(tmp22_21, paramObject, this);
/*  86 */     paramReadOnlyPropertyDescriptor.addListener(this.listener);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  94 */     this.descriptor.removeListener(this.listener);
/*  95 */     super.finalize();
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 107 */     return (String)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public String run() {
/*     */         try {
/* 110 */           return (String)MethodUtil.invoke(ReadOnlyJavaBeanStringProperty.this.descriptor.getGetter(), ReadOnlyJavaBeanStringProperty.this.getBean(), (Object[])null);
/*     */         } catch (IllegalAccessException localIllegalAccessException) {
/* 112 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 114 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/*     */       }
/*     */     }
/*     */     , this.acc);
/*     */   }
/*     */ 
/*     */   public Object getBean()
/*     */   {
/* 125 */     return this.listener.getBean();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 133 */     return this.descriptor.getName();
/*     */   }
/*     */ 
/*     */   public void fireValueChangedEvent()
/*     */   {
/* 141 */     super.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 149 */     this.descriptor.removeListener(this.listener);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.ReadOnlyJavaBeanStringProperty
 * JD-Core Version:    0.6.2
 */