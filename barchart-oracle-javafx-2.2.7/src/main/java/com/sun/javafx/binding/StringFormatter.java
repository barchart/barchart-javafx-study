/*     */ package com.sun.javafx.binding;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.binding.StringBinding;
/*     */ import javafx.beans.binding.StringExpression;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class StringFormatter extends StringBinding
/*     */ {
/*     */   private static Object extractValue(Object paramObject)
/*     */   {
/*  63 */     return (paramObject instanceof ObservableValue) ? ((ObservableValue)paramObject).getValue() : paramObject;
/*     */   }
/*     */ 
/*     */   private static Object[] extractValues(Object[] paramArrayOfObject)
/*     */   {
/*  68 */     int i = paramArrayOfObject.length;
/*  69 */     Object[] arrayOfObject = new Object[i];
/*  70 */     for (int j = 0; j < i; j++) {
/*  71 */       arrayOfObject[j] = extractValue(paramArrayOfObject[j]);
/*     */     }
/*  73 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */   private static ObservableValue<?>[] extractDependencies(Object[] paramArrayOfObject) {
/*  77 */     ArrayList localArrayList = new ArrayList();
/*  78 */     for (Object localObject : paramArrayOfObject) {
/*  79 */       if ((localObject instanceof ObservableValue)) {
/*  80 */         localArrayList.add((ObservableValue)localObject);
/*     */       }
/*     */     }
/*  83 */     return (ObservableValue[])localArrayList.toArray(new ObservableValue[localArrayList.size()]);
/*     */   }
/*     */ 
/*     */   public static StringExpression convert(ObservableValue<?> paramObservableValue)
/*     */   {
/*  88 */     if (paramObservableValue == null) {
/*  89 */       throw new NullPointerException("ObservableValue must be specified");
/*     */     }
/*  91 */     if ((paramObservableValue instanceof StringExpression)) {
/*  92 */       return (StringExpression)paramObservableValue;
/*     */     }
/*  94 */     return new StringBinding()
/*     */     {
/*     */       public void dispose()
/*     */       {
/* 101 */         super.unbind(new Observable[] { this.val$observableValue });
/*     */       }
/*     */ 
/*     */       protected String computeValue()
/*     */       {
/* 106 */         Object localObject = this.val$observableValue.getValue();
/* 107 */         return localObject == null ? "null" : localObject.toString();
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableValue<?>> getDependencies()
/*     */       {
/* 113 */         return FXCollections.singletonObservableList(this.val$observableValue);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static StringExpression concat(Object[] paramArrayOfObject)
/*     */   {
/* 121 */     if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0))
/* 122 */       return StringConstant.valueOf("");
/*     */     Object localObject1;
/* 124 */     if (paramArrayOfObject.length == 1) {
/* 125 */       localObject1 = paramArrayOfObject[0];
/* 126 */       return (localObject1 instanceof ObservableValue) ? convert((ObservableValue)localObject1) : StringConstant.valueOf(((Object)localObject1).toString());
/*     */     }
/*     */ 
/* 129 */     if (extractDependencies(paramArrayOfObject).length == 0) {
/* 130 */       localObject1 = new StringBuilder();
/* 131 */       for (Object localObject2 : paramArrayOfObject) {
/* 132 */         ((StringBuilder)localObject1).append(localObject2);
/*     */       }
/* 134 */       return StringConstant.valueOf(((StringBuilder)localObject1).toString());
/*     */     }
/* 136 */     return new StringFormatter()
/*     */     {
/*     */       public void dispose()
/*     */       {
/* 143 */         super.unbind(StringFormatter.extractDependencies(this.val$args));
/*     */       }
/*     */ 
/*     */       protected String computeValue()
/*     */       {
/* 148 */         StringBuilder localStringBuilder = new StringBuilder();
/* 149 */         for (Object localObject : this.val$args) {
/* 150 */           localStringBuilder.append(StringFormatter.extractValue(localObject));
/*     */         }
/* 152 */         return localStringBuilder.toString();
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableValue<?>> getDependencies()
/*     */       {
/* 158 */         return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(StringFormatter.extractDependencies(this.val$args)));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static StringExpression format(final Locale paramLocale, final String paramString, Object[] paramArrayOfObject)
/*     */   {
/* 166 */     if (extractDependencies(paramArrayOfObject).length == 0) {
/* 167 */       return StringConstant.valueOf(String.format(paramLocale, paramString, paramArrayOfObject));
/*     */     }
/* 169 */     StringFormatter local3 = new StringFormatter()
/*     */     {
/*     */       public void dispose()
/*     */       {
/* 176 */         super.unbind(StringFormatter.extractDependencies(this.val$args));
/*     */       }
/*     */ 
/*     */       protected String computeValue()
/*     */       {
/* 181 */         Object[] arrayOfObject = StringFormatter.extractValues(this.val$args);
/* 182 */         return String.format(paramLocale, paramString, arrayOfObject);
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableValue<?>> getDependencies()
/*     */       {
/* 188 */         return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(StringFormatter.extractDependencies(this.val$args)));
/*     */       }
/*     */     };
/* 193 */     local3.get();
/* 194 */     return local3;
/*     */   }
/*     */ 
/*     */   public static StringExpression format(final String paramString, Object[] paramArrayOfObject)
/*     */   {
/* 199 */     if (extractDependencies(paramArrayOfObject).length == 0) {
/* 200 */       return StringConstant.valueOf(String.format(paramString, paramArrayOfObject));
/*     */     }
/* 202 */     StringFormatter local4 = new StringFormatter()
/*     */     {
/*     */       public void dispose()
/*     */       {
/* 209 */         super.unbind(StringFormatter.extractDependencies(this.val$args));
/*     */       }
/*     */ 
/*     */       protected String computeValue()
/*     */       {
/* 214 */         Object[] arrayOfObject = StringFormatter.extractValues(this.val$args);
/* 215 */         return String.format(paramString, arrayOfObject);
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableValue<?>> getDependencies()
/*     */       {
/* 221 */         return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(StringFormatter.extractDependencies(this.val$args)));
/*     */       }
/*     */     };
/* 226 */     local4.get();
/* 227 */     return local4;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.StringFormatter
 * JD-Core Version:    0.6.2
 */