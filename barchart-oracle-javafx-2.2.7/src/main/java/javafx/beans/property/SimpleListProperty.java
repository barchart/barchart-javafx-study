/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class SimpleListProperty<E> extends ListPropertyBase<E>
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
/*     */   public SimpleListProperty()
/*     */   {
/*  86 */     this(DEFAULT_BEAN, "");
/*     */   }
/*     */ 
/*     */   public SimpleListProperty(ObservableList<E> paramObservableList)
/*     */   {
/*  96 */     this(DEFAULT_BEAN, "", paramObservableList);
/*     */   }
/*     */ 
/*     */   public SimpleListProperty(Object paramObject, String paramString)
/*     */   {
/* 108 */     this.bean = paramObject;
/* 109 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ 
/*     */   public SimpleListProperty(Object paramObject, String paramString, ObservableList<E> paramObservableList)
/*     */   {
/* 123 */     super(paramObservableList);
/* 124 */     this.bean = paramObject;
/* 125 */     this.name = (paramString == null ? "" : paramString);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.SimpleListProperty
 * JD-Core Version:    0.6.2
 */