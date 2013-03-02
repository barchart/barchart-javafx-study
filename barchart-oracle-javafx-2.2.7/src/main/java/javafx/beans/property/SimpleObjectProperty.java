/*     */ package javafx.beans.property;
/*     */ 
/*     */ public class SimpleObjectProperty<T> extends ObjectPropertyBase<T>
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
/*     */   public SimpleObjectProperty()
/*     */   {
/*  86 */     this(DEFAULT_BEAN, "");
/*     */   }
/*     */ 
/*     */   public SimpleObjectProperty(T paramT)
/*     */   {
/*  96 */     this(DEFAULT_BEAN, "", paramT);
/*     */   }
/*     */ 
/*     */   public SimpleObjectProperty(Object paramObject, String paramString)
/*     */   {
/* 108 */     this.bean = paramObject;
/* 109 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ 
/*     */   public SimpleObjectProperty(Object paramObject, String paramString, T paramT)
/*     */   {
/* 123 */     super(paramT);
/* 124 */     this.bean = paramObject;
/* 125 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.SimpleObjectProperty
 * JD-Core Version:    0.6.2
 */