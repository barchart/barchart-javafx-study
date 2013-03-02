/*     */ package javafx.beans.property;
/*     */ 
/*     */ public class SimpleBooleanProperty extends BooleanPropertyBase
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
/*     */   public SimpleBooleanProperty()
/*     */   {
/*  83 */     this(DEFAULT_BEAN, "");
/*     */   }
/*     */ 
/*     */   public SimpleBooleanProperty(boolean paramBoolean)
/*     */   {
/*  93 */     this(DEFAULT_BEAN, "", paramBoolean);
/*     */   }
/*     */ 
/*     */   public SimpleBooleanProperty(Object paramObject, String paramString)
/*     */   {
/* 105 */     this.bean = paramObject;
/* 106 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ 
/*     */   public SimpleBooleanProperty(Object paramObject, String paramString, boolean paramBoolean)
/*     */   {
/* 120 */     super(paramBoolean);
/* 121 */     this.bean = paramObject;
/* 122 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.SimpleBooleanProperty
 * JD-Core Version:    0.6.2
 */