/*     */ package javafx.beans.property;
/*     */ 
/*     */ public class SimpleFloatProperty extends FloatPropertyBase
/*     */ {
/*  57 */   private static final Object DEFAULT_BEAN = null;
/*     */   private static final String DEFAULT_NAME = "";
/*     */   private final Object bean;
/*     */   private final String name;
/*     */ 
/*     */   public Object getBean()
/*     */   {
/*  68 */     return this.bean;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  76 */     return this.name;
/*     */   }
/*     */ 
/*     */   public SimpleFloatProperty()
/*     */   {
/*  83 */     this(DEFAULT_BEAN, "");
/*     */   }
/*     */ 
/*     */   public SimpleFloatProperty(float paramFloat)
/*     */   {
/*  93 */     this(DEFAULT_BEAN, "", paramFloat);
/*     */   }
/*     */ 
/*     */   public SimpleFloatProperty(Object paramObject, String paramString)
/*     */   {
/* 105 */     this.bean = paramObject;
/* 106 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ 
/*     */   public SimpleFloatProperty(Object paramObject, String paramString, float paramFloat)
/*     */   {
/* 120 */     super(paramFloat);
/* 121 */     this.bean = paramObject;
/* 122 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.SimpleFloatProperty
 * JD-Core Version:    0.6.2
 */