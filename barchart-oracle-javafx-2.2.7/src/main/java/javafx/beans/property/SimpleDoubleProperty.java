/*     */ package javafx.beans.property;
/*     */ 
/*     */ public class SimpleDoubleProperty extends DoublePropertyBase
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
/*     */   public SimpleDoubleProperty()
/*     */   {
/*  83 */     this(DEFAULT_BEAN, "");
/*     */   }
/*     */ 
/*     */   public SimpleDoubleProperty(double paramDouble)
/*     */   {
/*  93 */     this(DEFAULT_BEAN, "", paramDouble);
/*     */   }
/*     */ 
/*     */   public SimpleDoubleProperty(Object paramObject, String paramString)
/*     */   {
/* 105 */     this.bean = paramObject;
/* 106 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ 
/*     */   public SimpleDoubleProperty(Object paramObject, String paramString, double paramDouble)
/*     */   {
/* 120 */     super(paramDouble);
/* 121 */     this.bean = paramObject;
/* 122 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.SimpleDoubleProperty
 * JD-Core Version:    0.6.2
 */