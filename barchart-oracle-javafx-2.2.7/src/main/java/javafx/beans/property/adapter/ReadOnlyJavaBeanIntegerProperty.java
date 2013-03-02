/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor;
/*     */ import com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor.ReadOnlyListener;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javafx.beans.property.ReadOnlyIntegerPropertyBase;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ public final class ReadOnlyJavaBeanIntegerProperty extends ReadOnlyIntegerPropertyBase
/*     */   implements ReadOnlyJavaBeanProperty<Number>
/*     */ {
/*     */   private final ReadOnlyPropertyDescriptor descriptor;
/*     */   private final ReadOnlyPropertyDescriptor.ReadOnlyListener<Number> listener;
/*  81 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */   ReadOnlyJavaBeanIntegerProperty(ReadOnlyPropertyDescriptor paramReadOnlyPropertyDescriptor, Object paramObject) {
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
/*     */   public int get()
/*     */   {
/* 107 */     return ((Integer)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Integer run() {
/*     */         try {
/* 110 */           return Integer.valueOf(((Number)MethodUtil.invoke(ReadOnlyJavaBeanIntegerProperty.this.descriptor.getGetter(), ReadOnlyJavaBeanIntegerProperty.this.getBean(), (Object[])null)).intValue());
/*     */         }
/*     */         catch (IllegalAccessException localIllegalAccessException) {
/* 113 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 115 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/*     */       }
/*     */     }
/*     */     , this.acc)).intValue();
/*     */   }
/*     */ 
/*     */   public Object getBean()
/*     */   {
/* 126 */     return this.listener.getBean();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 134 */     return this.descriptor.getName();
/*     */   }
/*     */ 
/*     */   public void fireValueChangedEvent()
/*     */   {
/* 142 */     super.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 150 */     this.descriptor.removeListener(this.listener);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.ReadOnlyJavaBeanIntegerProperty
 * JD-Core Version:    0.6.2
 */