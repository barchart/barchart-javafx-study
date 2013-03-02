/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.collections.ObservableSet;
/*     */ 
/*     */ public class SimpleSetProperty<E> extends SetPropertyBase<E>
/*     */ {
/*  60 */   private static final Object DEFAULT_BEAN = null;
/*     */   private static final String DEFAULT_NAME = "";
/*     */   private final Object bean;
/*     */   private final String name;
/*     */ 
/*     */   public Object getBean()
/*     */   {
/*  71 */     return this.bean;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  79 */     return this.name;
/*     */   }
/*     */ 
/*     */   public SimpleSetProperty()
/*     */   {
/*  86 */     this(DEFAULT_BEAN, "");
/*     */   }
/*     */ 
/*     */   public SimpleSetProperty(ObservableSet<E> paramObservableSet)
/*     */   {
/*  96 */     this(DEFAULT_BEAN, "", paramObservableSet);
/*     */   }
/*     */ 
/*     */   public SimpleSetProperty(Object paramObject, String paramString)
/*     */   {
/* 108 */     this.bean = paramObject;
/* 109 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ 
/*     */   public SimpleSetProperty(Object paramObject, String paramString, ObservableSet<E> paramObservableSet)
/*     */   {
/* 123 */     super(paramObservableSet);
/* 124 */     this.bean = paramObject;
/* 125 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.SimpleSetProperty
 * JD-Core Version:    0.6.2
 */