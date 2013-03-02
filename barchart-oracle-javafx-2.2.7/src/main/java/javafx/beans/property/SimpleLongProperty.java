/*     */ package javafx.beans.property;
/*     */ 
/*     */ public class SimpleLongProperty extends LongPropertyBase
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
/*     */   public SimpleLongProperty()
/*     */   {
/*  83 */     this(DEFAULT_BEAN, "");
/*     */   }
/*     */ 
/*     */   public SimpleLongProperty(long paramLong)
/*     */   {
/*  93 */     this(DEFAULT_BEAN, "", paramLong);
/*     */   }
/*     */ 
/*     */   public SimpleLongProperty(Object paramObject, String paramString)
/*     */   {
/* 105 */     this.bean = paramObject;
/* 106 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ 
/*     */   public SimpleLongProperty(Object paramObject, String paramString, long paramLong)
/*     */   {
/* 120 */     super(paramLong);
/* 121 */     this.bean = paramObject;
/* 122 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.SimpleLongProperty
 * JD-Core Version:    0.6.2
 */