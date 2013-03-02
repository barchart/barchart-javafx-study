/*     */ package javafx.beans.property;
/*     */ 
/*     */ public class SimpleIntegerProperty extends IntegerPropertyBase
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
/*     */   public SimpleIntegerProperty()
/*     */   {
/*  83 */     this(DEFAULT_BEAN, "");
/*     */   }
/*     */ 
/*     */   public SimpleIntegerProperty(int paramInt)
/*     */   {
/*  93 */     this(DEFAULT_BEAN, "", paramInt);
/*     */   }
/*     */ 
/*     */   public SimpleIntegerProperty(Object paramObject, String paramString)
/*     */   {
/* 105 */     this.bean = paramObject;
/* 106 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ 
/*     */   public SimpleIntegerProperty(Object paramObject, String paramString, int paramInt)
/*     */   {
/* 120 */     super(paramInt);
/* 121 */     this.bean = paramObject;
/* 122 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.SimpleIntegerProperty
 * JD-Core Version:    0.6.2
 */