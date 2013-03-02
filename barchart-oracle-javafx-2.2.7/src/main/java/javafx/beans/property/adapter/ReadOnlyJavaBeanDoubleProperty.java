/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor;
/*     */ import com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor.ReadOnlyListener;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javafx.beans.property.ReadOnlyDoublePropertyBase;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ public final class ReadOnlyJavaBeanDoubleProperty extends ReadOnlyDoublePropertyBase
/*     */   implements ReadOnlyJavaBeanProperty<Number>
/*     */ {
/*     */   private final ReadOnlyPropertyDescriptor descriptor;
/*     */   private final ReadOnlyPropertyDescriptor.ReadOnlyListener<Number> listener;
/*  81 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */   ReadOnlyJavaBeanDoubleProperty(ReadOnlyPropertyDescriptor paramReadOnlyPropertyDescriptor, Object paramObject) {
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
/*     */   public double get()
/*     */   {
/* 107 */     return ((Double)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Double run() {
/*     */         try {
/* 110 */           return Double.valueOf(((Number)MethodUtil.invoke(ReadOnlyJavaBeanDoubleProperty.this.descriptor.getGetter(), ReadOnlyJavaBeanDoubleProperty.this.getBean(), (Object[])null)).doubleValue());
/*     */         }
/*     */         catch (IllegalAccessException localIllegalAccessException) {
/* 113 */           throw new UndeclaredThrowableException(localIllegalAccessException);
/*     */         } catch (InvocationTargetException localInvocationTargetException) {
/* 115 */           throw new UndeclaredThrowableException(localInvocationTargetException);
/*     */         }
/*     */       }
/*     */     }
/*     */     , this.acc)).doubleValue();
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
 * Qualified Name:     javafx.beans.property.adapter.ReadOnlyJavaBeanDoubleProperty
 * JD-Core Version:    0.6.2
 */