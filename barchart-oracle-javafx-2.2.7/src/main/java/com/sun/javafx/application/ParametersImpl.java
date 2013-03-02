/*     */ package com.sun.javafx.application;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javafx.application.Application;
/*     */ import javafx.application.Application.Parameters;
/*     */ 
/*     */ public class ParametersImpl extends Application.Parameters
/*     */ {
/*  45 */   private List<String> rawArgs = new ArrayList();
/*  46 */   private Map<String, String> namedParams = new HashMap();
/*  47 */   private List<String> unnamedParams = new ArrayList();
/*     */ 
/*  49 */   private List<String> readonlyRawArgs = null;
/*  50 */   private Map<String, String> readonlyNamedParams = null;
/*  51 */   private List<String> readonlyUnnamedParams = null;
/*     */ 
/*  54 */   private static Map<Application, Application.Parameters> params = new HashMap();
/*     */ 
/*     */   public ParametersImpl()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ParametersImpl(List<String> paramList)
/*     */   {
/*  71 */     if (paramList != null)
/*  72 */       init(paramList);
/*     */   }
/*     */ 
/*     */   public ParametersImpl(String[] paramArrayOfString)
/*     */   {
/*  83 */     if (paramArrayOfString != null)
/*  84 */       init(Arrays.asList(paramArrayOfString));
/*     */   }
/*     */ 
/*     */   public ParametersImpl(Map paramMap, String[] paramArrayOfString)
/*     */   {
/*  96 */     init(paramMap, paramArrayOfString);
/*     */   }
/*     */ 
/*     */   private void init(List<String> paramList)
/*     */   {
/* 106 */     for (String str : paramList) {
/* 107 */       if (str != null) {
/* 108 */         this.rawArgs.add(str);
/*     */       }
/*     */     }
/* 111 */     computeNamedParams();
/* 112 */     computeUnnamedParams();
/*     */   }
/*     */ 
/*     */   private void init(Map paramMap, String[] paramArrayOfString)
/*     */   {
/* 123 */     for (Object localObject1 = paramMap.entrySet().iterator(); ((Iterator)localObject1).hasNext(); ) { Object localObject2 = ((Iterator)localObject1).next();
/* 124 */       Object localObject3 = ((Map.Entry)localObject2).getKey();
/* 125 */       if (validKey(localObject3)) {
/* 126 */         localObject4 = paramMap.get(localObject3);
/* 127 */         if ((localObject4 instanceof String))
/* 128 */           this.namedParams.put((String)localObject3, (String)localObject4);
/*     */       }
/*     */     }
/*     */     Object localObject4;
/* 133 */     computeRawArgs();
/* 134 */     if (paramArrayOfString != null)
/* 135 */       for (localObject4 : paramArrayOfString) {
/* 136 */         this.unnamedParams.add(localObject4);
/* 137 */         this.rawArgs.add(localObject4);
/*     */       }
/*     */   }
/*     */ 
/*     */   private boolean validFirstChar(char paramChar)
/*     */   {
/* 151 */     return (Character.isLetter(paramChar)) || (paramChar == '_');
/*     */   }
/*     */ 
/*     */   private boolean validKey(Object paramObject)
/*     */   {
/* 163 */     if ((paramObject instanceof String)) {
/* 164 */       String str = (String)paramObject;
/* 165 */       if ((str.length() > 0) && (str.indexOf('=') < 0)) {
/* 166 */         return validFirstChar(str.charAt(0));
/*     */       }
/*     */     }
/*     */ 
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean isNamedParam(String paramString)
/*     */   {
/* 182 */     if (paramString.startsWith("--")) {
/* 183 */       return (paramString.indexOf('=') > 2) && (validFirstChar(paramString.charAt(2)));
/*     */     }
/* 185 */     return false;
/*     */   }
/*     */ 
/*     */   private void computeUnnamedParams()
/*     */   {
/* 194 */     for (String str : this.rawArgs)
/* 195 */       if (!isNamedParam(str))
/* 196 */         this.unnamedParams.add(str);
/*     */   }
/*     */ 
/*     */   private void computeNamedParams()
/*     */   {
/* 207 */     for (String str1 : this.rawArgs)
/* 208 */       if (isNamedParam(str1)) {
/* 209 */         int i = str1.indexOf('=');
/* 210 */         String str2 = str1.substring(2, i);
/* 211 */         String str3 = str1.substring(i + 1);
/* 212 */         this.namedParams.put(str2, str3);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void computeRawArgs()
/*     */   {
/* 224 */     ArrayList localArrayList = new ArrayList();
/* 225 */     localArrayList.addAll(this.namedParams.keySet());
/* 226 */     Collections.sort(localArrayList);
/* 227 */     for (String str : localArrayList)
/* 228 */       this.rawArgs.add("--" + str + "=" + (String)this.namedParams.get(str));
/*     */   }
/*     */ 
/*     */   public List<String> getRaw()
/*     */   {
/* 233 */     if (this.readonlyRawArgs == null) {
/* 234 */       this.readonlyRawArgs = Collections.unmodifiableList(this.rawArgs);
/*     */     }
/* 236 */     return this.readonlyRawArgs;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getNamed() {
/* 240 */     if (this.readonlyNamedParams == null) {
/* 241 */       this.readonlyNamedParams = Collections.unmodifiableMap(this.namedParams);
/*     */     }
/* 243 */     return this.readonlyNamedParams;
/*     */   }
/*     */ 
/*     */   public List<String> getUnnamed() {
/* 247 */     if (this.readonlyUnnamedParams == null) {
/* 248 */       this.readonlyUnnamedParams = Collections.unmodifiableList(this.unnamedParams);
/*     */     }
/* 250 */     return this.readonlyUnnamedParams;
/*     */   }
/*     */ 
/*     */   public static Application.Parameters getParameters(Application paramApplication)
/*     */   {
/* 256 */     Application.Parameters localParameters = (Application.Parameters)params.get(paramApplication);
/* 257 */     return localParameters;
/*     */   }
/*     */ 
/*     */   public static void registerParameters(Application paramApplication, Application.Parameters paramParameters) {
/* 261 */     params.put(paramApplication, paramParameters);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.application.ParametersImpl
 * JD-Core Version:    0.6.2
 */