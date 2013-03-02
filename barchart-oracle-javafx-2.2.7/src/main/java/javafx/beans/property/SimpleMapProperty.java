/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public class SimpleMapProperty<K, V> extends MapPropertyBase<K, V>
/*     */ {
/*  62 */   private static final Object DEFAULT_BEAN = null;
/*     */   private static final String DEFAULT_NAME = "";
/*     */   private final Object bean;
/*     */   private final String name;
/*     */ 
/*     */   public Object getBean()
/*     */   {
/*  73 */     return this.bean;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  81 */     return this.name;
/*     */   }
/*     */ 
/*     */   public SimpleMapProperty()
/*     */   {
/*  88 */     this(DEFAULT_BEAN, "");
/*     */   }
/*     */ 
/*     */   public SimpleMapProperty(ObservableMap<K, V> paramObservableMap)
/*     */   {
/*  98 */     this(DEFAULT_BEAN, "", paramObservableMap);
/*     */   }
/*     */ 
/*     */   public SimpleMapProperty(Object paramObject, String paramString)
/*     */   {
/* 110 */     this.bean = paramObject;
/* 111 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ 
/*     */   public SimpleMapProperty(Object paramObject, String paramString, ObservableMap<K, V> paramObservableMap)
/*     */   {
/* 125 */     super(paramObservableMap);
/* 126 */     this.bean = paramObject;
/* 127 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.SimpleMapProperty
 * JD-Core Version:    0.6.2
 */