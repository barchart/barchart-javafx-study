/*     */ package com.sun.scenario;
/*     */ 
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class Settings
/*     */ {
/*  46 */   private final Map<String, String> settings = new HashMap(5);
/*  47 */   private final PropertyChangeSupport pcs = new PropertyChangeSupport(Settings.class);
/*     */ 
/*  51 */   private static final Object SETTINGS_KEY = new StringBuilder("SettingsKey");
/*     */ 
/*     */   private static synchronized Settings getInstance()
/*     */   {
/*  60 */     Map localMap = ToolkitAccessor.getContextMap();
/*  61 */     Settings localSettings = (Settings)localMap.get(SETTINGS_KEY);
/*  62 */     if (localSettings == null) {
/*  63 */       localSettings = new Settings();
/*  64 */       localMap.put(SETTINGS_KEY, localSettings);
/*     */     }
/*  66 */     return localSettings;
/*     */   }
/*     */ 
/*     */   public static void set(String paramString1, String paramString2)
/*     */   {
/*  81 */     getInstance().setImpl(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   private void setImpl(String paramString1, String paramString2) {
/*  85 */     checkKeyArg(paramString1);
/*  86 */     String str1 = getImpl(paramString1);
/*  87 */     this.settings.put(paramString1, paramString2);
/*  88 */     String str2 = paramString2;
/*  89 */     if (str2 == null) {
/*  90 */       str2 = getImpl(paramString1);
/*     */     }
/*  92 */     this.pcs.firePropertyChange(paramString1, str1, str2);
/*     */   }
/*     */ 
/*     */   public static String get(String paramString)
/*     */   {
/* 107 */     return getInstance().getImpl(paramString);
/*     */   }
/*     */ 
/*     */   private String getImpl(String paramString) {
/* 111 */     checkKeyArg(paramString);
/* 112 */     String str = (String)this.settings.get(paramString);
/* 113 */     if (str == null)
/*     */       try {
/* 115 */         str = System.getProperty(paramString);
/*     */       }
/*     */       catch (SecurityException localSecurityException) {
/*     */       }
/* 119 */     return str;
/*     */   }
/*     */ 
/*     */   public static boolean getBoolean(String paramString)
/*     */   {
/* 131 */     return getInstance().getBooleanImpl(paramString);
/*     */   }
/*     */ 
/*     */   private boolean getBooleanImpl(String paramString)
/*     */   {
/* 136 */     String str = getImpl(paramString);
/* 137 */     return "true".equals(str);
/*     */   }
/*     */ 
/*     */   public static boolean getBoolean(String paramString, boolean paramBoolean)
/*     */   {
/* 150 */     return getInstance().getBooleanImpl(paramString, paramBoolean);
/*     */   }
/*     */ 
/*     */   private boolean getBooleanImpl(String paramString, boolean paramBoolean)
/*     */   {
/* 155 */     String str = getImpl(paramString);
/* 156 */     boolean bool = paramBoolean;
/* 157 */     if (str != null) {
/* 158 */       if ("false".equals(str))
/* 159 */         bool = false;
/* 160 */       else if ("true".equals(str)) {
/* 161 */         bool = true;
/*     */       }
/*     */     }
/* 164 */     return bool;
/*     */   }
/*     */ 
/*     */   public static int getInt(String paramString, int paramInt)
/*     */   {
/* 176 */     return getInstance().getIntImpl(paramString, paramInt);
/*     */   }
/*     */ 
/*     */   private int getIntImpl(String paramString, int paramInt)
/*     */   {
/* 181 */     String str = getImpl(paramString);
/* 182 */     int i = paramInt;
/*     */     try {
/* 184 */       i = Integer.parseInt(str);
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException) {
/*     */     }
/* 188 */     return i;
/*     */   }
/*     */ 
/*     */   public static void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*     */   {
/* 203 */     getInstance().addPropertyChangeListenerImpl(paramString, paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */   private void addPropertyChangeListenerImpl(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*     */   {
/* 208 */     checkKeyArg(paramString);
/* 209 */     this.pcs.addPropertyChangeListener(paramString, paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */   public static void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*     */   {
/* 219 */     getInstance().removePropertyChangeListenerImpl(paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */   private void removePropertyChangeListenerImpl(PropertyChangeListener paramPropertyChangeListener) {
/* 223 */     this.pcs.removePropertyChangeListener(paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */   private void checkKeyArg(String paramString)
/*     */   {
/* 231 */     if ((null == paramString) || ("".equals(paramString)))
/* 232 */       throw new IllegalArgumentException("null key not allowed");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.Settings
 * JD-Core Version:    0.6.2
 */