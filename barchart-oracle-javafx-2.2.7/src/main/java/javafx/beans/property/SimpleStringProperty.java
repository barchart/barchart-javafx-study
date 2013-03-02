/*     */ package javafx.beans.property;
/*     */ 
/*     */ public class SimpleStringProperty extends StringPropertyBase
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
/*     */   public SimpleStringProperty()
/*     */   {
/*  83 */     this(DEFAULT_BEAN, "");
/*     */   }
/*     */ 
/*     */   public SimpleStringProperty(String paramString)
/*     */   {
/*  93 */     this(DEFAULT_BEAN, "", paramString);
/*     */   }
/*     */ 
/*     */   public SimpleStringProperty(Object paramObject, String paramString)
/*     */   {
/* 105 */     this.bean = paramObject;
/* 106 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ 
/*     */   public SimpleStringProperty(Object paramObject, String paramString1, String paramString2)
/*     */   {
/* 120 */     super(paramString2);
/* 121 */     this.bean = paramObject;
/* 122 */     this.name = (paramString1 == null ? "" : paramString1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.SimpleStringProperty
 * JD-Core Version:    0.6.2
 */