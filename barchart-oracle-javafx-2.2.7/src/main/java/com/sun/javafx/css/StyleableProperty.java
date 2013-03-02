/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import com.sun.javafx.css.converters.FontConverter;
/*     */ import com.sun.javafx.css.converters.SizeConverter;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javafx.beans.value.WritableValue;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.text.Font;
/*     */ import javafx.scene.text.FontPosture;
/*     */ import javafx.scene.text.FontWeight;
/*     */ 
/*     */ public abstract class StyleableProperty<N extends Node, V>
/*     */ {
/* 148 */   private static Map<Class, List<StyleableProperty>> styleablesCache = null;
/*     */   private final String property;
/*     */   private final StyleConverter converter;
/*     */   private final V initialValue;
/*     */   private final List<StyleableProperty> subProperties;
/*     */   private final boolean inherits;
/*     */ 
/*     */   public void set(N paramN, V paramV, Stylesheet.Origin paramOrigin)
/*     */   {
/*  59 */     WritableValue localWritableValue = getWritableValue(paramN);
/*  60 */     assert ((localWritableValue instanceof Property));
/*  61 */     Property localProperty = (Property)localWritableValue;
/*  62 */     Stylesheet.Origin localOrigin = localProperty.getOrigin();
/*  63 */     Object localObject = localWritableValue.getValue();
/*     */ 
/*  65 */     if ((localOrigin != paramOrigin) || (localObject != null ? !localObject.equals(paramV) : paramV != null))
/*     */     {
/*  70 */       localProperty.applyStyle(paramOrigin, paramV);
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void set(N paramN, V paramV) {
/*  76 */     set(paramN, paramV, null);
/*     */   }
/*     */ 
/*     */   public static StyleableProperty getStyleableProperty(WritableValue paramWritableValue)
/*     */   {
/*  84 */     if ((paramWritableValue instanceof Property))
/*     */     {
/*  86 */       return ((Property)paramWritableValue).getStyleableProperty();
/*     */     }
/*     */ 
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */   public static Stylesheet.Origin getOrigin(WritableValue paramWritableValue)
/*     */   {
/*  97 */     if ((paramWritableValue instanceof Property))
/*     */     {
/*  99 */       return ((Property)paramWritableValue).getOrigin();
/*     */     }
/*     */ 
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */   public List<Style> getMatchingStyles(Node paramNode)
/*     */   {
/* 110 */     if (paramNode != null) {
/* 111 */       return getMatchingStyles(paramNode.impl_getStyleable());
/*     */     }
/* 113 */     return Collections.EMPTY_LIST;
/*     */   }
/*     */ 
/*     */   public List<Style> getMatchingStyles(Styleable paramStyleable) {
/* 117 */     if (paramStyleable != null) {
/* 118 */       Object localObject = paramStyleable.getNode() != null ? paramStyleable.getNode().impl_getStyleHelper() : null;
/*     */ 
/* 121 */       return localObject != null ? localObject.getMatchingStyles(paramStyleable, this) : Collections.EMPTY_LIST;
/*     */     }
/*     */ 
/* 125 */     return Collections.EMPTY_LIST;
/*     */   }
/*     */ 
/*     */   public abstract boolean isSettable(N paramN);
/*     */ 
/*     */   public abstract WritableValue<V> getWritableValue(N paramN);
/*     */ 
/*     */   private static Method getMethod_impl_CSS_STYLEABLES(Class paramClass)
/*     */   {
/* 152 */     return (Method)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Method run() {
/*     */         try {
/* 155 */           return this.val$nodeClass.getDeclaredMethod("impl_CSS_STYLEABLES", new Class[0]); } catch (NoSuchMethodException localNoSuchMethodException) {
/*     */         }
/* 157 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static List<StyleableProperty> getStyleables(Styleable paramStyleable)
/*     */   {
/* 171 */     return paramStyleable != null ? paramStyleable.getStyleableProperties() : Collections.EMPTY_LIST;
/*     */   }
/*     */ 
/*     */   public static List<StyleableProperty> getStyleables(Node paramNode)
/*     */   {
/* 183 */     return paramNode != null ? paramNode.impl_getStyleableProperties() : Collections.EMPTY_LIST;
/*     */   }
/*     */ 
/*     */   public final String getProperty()
/*     */   {
/* 331 */     return this.property;
/*     */   }
/*     */ 
/*     */   public final StyleConverter getConverter()
/*     */   {
/* 339 */     return this.converter;
/*     */   }
/*     */ 
/*     */   public V getInitialValue(N paramN)
/*     */   {
/* 358 */     return this.initialValue;
/*     */   }
/*     */ 
/*     */   public final List<StyleableProperty> getSubProperties()
/*     */   {
/* 367 */     return this.subProperties;
/*     */   }
/*     */ 
/*     */   public final boolean isInherits()
/*     */   {
/* 378 */     return this.inherits;
/*     */   }
/*     */ 
/*     */   protected StyleableProperty(String paramString, StyleConverter paramStyleConverter, V paramV, boolean paramBoolean, List<StyleableProperty> paramList)
/*     */   {
/* 398 */     this.property = paramString;
/* 399 */     this.converter = paramStyleConverter;
/* 400 */     this.initialValue = paramV;
/* 401 */     this.inherits = paramBoolean;
/* 402 */     this.subProperties = (paramList != null ? Collections.unmodifiableList(paramList) : null);
/*     */ 
/* 404 */     if ((this.property == null) || (this.converter == null))
/* 405 */       throw new IllegalArgumentException("neither property nor converter can be null");
/*     */   }
/*     */ 
/*     */   protected StyleableProperty(String paramString, StyleConverter paramStyleConverter, V paramV, boolean paramBoolean)
/*     */   {
/* 420 */     this(paramString, paramStyleConverter, paramV, paramBoolean, null);
/*     */   }
/*     */ 
/*     */   protected StyleableProperty(String paramString, StyleConverter paramStyleConverter, V paramV)
/*     */   {
/* 434 */     this(paramString, paramStyleConverter, paramV, false, null);
/*     */   }
/*     */ 
/*     */   protected StyleableProperty(String paramString, StyleConverter paramStyleConverter)
/*     */   {
/* 447 */     this(paramString, paramStyleConverter, null, false, null);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 452 */     if (paramObject == null) {
/* 453 */       return false;
/*     */     }
/* 455 */     if (getClass() != paramObject.getClass()) {
/* 456 */       return false;
/*     */     }
/* 458 */     StyleableProperty localStyleableProperty = (StyleableProperty)paramObject;
/* 459 */     if (this.property == null ? localStyleableProperty.property != null : !this.property.equals(localStyleableProperty.property)) {
/* 460 */       return false;
/*     */     }
/* 462 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 467 */     int i = 3;
/* 468 */     i = 19 * i + (this.property != null ? this.property.hashCode() : 0);
/* 469 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 474 */     return new StringBuilder("StyleableProperty {").append("property: ").append(this.property).append(", converter: ").append(this.converter.toString()).append(", initalValue: ").append(String.valueOf(this.initialValue)).append(", inherits: ").append(this.inherits).append(", subProperties: ").append(this.subProperties != null ? this.subProperties.toString() : "[]").append("}").toString();
/*     */   }
/*     */ 
/*     */   public static abstract class FONT<T extends Node> extends StyleableProperty<T, Font>
/*     */   {
/*     */     public FONT(String paramString, Font paramFont)
/*     */     {
/* 240 */       super(FontConverter.getInstance(), paramFont, true, createSubProperties(paramString, paramFont));
/*     */     }
/*     */ 
/*     */     private static List<StyleableProperty> createSubProperties(String paramString, Font paramFont)
/*     */     {
/* 245 */       Font localFont = paramFont != null ? paramFont : Font.getDefault();
/*     */ 
/* 247 */       StyleableProperty local1 = new StyleableProperty(paramString.concat("-size"), SizeConverter.getInstance(), new Size(localFont.getSize(), SizeUnits.PT), true)
/*     */       {
/*     */         public boolean isSettable(Node paramAnonymousNode)
/*     */         {
/* 255 */           return false;
/*     */         }
/*     */ 
/*     */         public WritableValue<Size> getWritableValue(Node paramAnonymousNode)
/*     */         {
/* 260 */           return null;
/*     */         }
/*     */       };
/* 265 */       StyleableProperty local2 = new StyleableProperty(paramString.concat("-weight"), SizeConverter.getInstance(), FontWeight.NORMAL, true)
/*     */       {
/*     */         public boolean isSettable(Node paramAnonymousNode)
/*     */         {
/* 273 */           return false;
/*     */         }
/*     */ 
/*     */         public WritableValue<FontWeight> getWritableValue(Node paramAnonymousNode)
/*     */         {
/* 278 */           return null;
/*     */         }
/*     */       };
/* 283 */       StyleableProperty local3 = new StyleableProperty(paramString.concat("-style"), SizeConverter.getInstance(), FontPosture.REGULAR, true)
/*     */       {
/*     */         public boolean isSettable(Node paramAnonymousNode)
/*     */         {
/* 291 */           return false;
/*     */         }
/*     */ 
/*     */         public WritableValue<FontPosture> getWritableValue(Node paramAnonymousNode)
/*     */         {
/* 296 */           return null;
/*     */         }
/*     */       };
/* 301 */       StyleableProperty local4 = new StyleableProperty(paramString.concat("-family"), SizeConverter.getInstance(), localFont.getFamily(), true)
/*     */       {
/*     */         public boolean isSettable(Node paramAnonymousNode)
/*     */         {
/* 309 */           return false;
/*     */         }
/*     */ 
/*     */         public WritableValue<String> getWritableValue(Node paramAnonymousNode)
/*     */         {
/* 314 */           return null;
/*     */         }
/*     */       };
/* 319 */       ArrayList localArrayList = new ArrayList();
/* 320 */       Collections.addAll(localArrayList, new StyleableProperty[] { local4, local1, local3, local2 });
/* 321 */       return Collections.unmodifiableList(localArrayList);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.StyleableProperty
 * JD-Core Version:    0.6.2
 */