/*     */ package com.sun.javafx.logging;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.List;
/*     */ 
/*     */ class LoggingSupport
/*     */ {
/*  47 */   private static final LoggingProxy proxy = (LoggingProxy)AccessController.doPrivileged(new PrivilegedAction()
/*     */   {
/*     */     public LoggingProxy run()
/*     */     {
/*     */       try
/*     */       {
/*  53 */         Class localClass = Class.forName("java.util.logging.LoggingProxyImpl", true, null);
/*  54 */         Field localField = localClass.getDeclaredField("INSTANCE");
/*  55 */         localField.setAccessible(true);
/*  56 */         return (LoggingProxy)localField.get(null);
/*     */       } catch (ClassNotFoundException localClassNotFoundException) {
/*  58 */         return null;
/*     */       } catch (NoSuchFieldException localNoSuchFieldException) {
/*  60 */         throw new AssertionError(localNoSuchFieldException);
/*     */       } catch (IllegalAccessException localIllegalAccessException) {
/*  62 */         throw new AssertionError(localIllegalAccessException);
/*     */       }
/*     */     }
/*     */   });
/*     */ 
/*     */   static boolean isAvailable()
/*     */   {
/*  70 */     return proxy != null;
/*     */   }
/*     */ 
/*     */   static void ensureAvailable() {
/*  74 */     if (proxy == null)
/*  75 */       throw new AssertionError("Should not here");
/*     */   }
/*     */ 
/*     */   static List<String> getLoggerNames() {
/*  79 */     ensureAvailable();
/*  80 */     return proxy.getLoggerNames();
/*     */   }
/*     */ 
/*     */   static String getLoggerLevel(String paramString) {
/*  84 */     ensureAvailable();
/*  85 */     return proxy.getLoggerLevel(paramString);
/*     */   }
/*     */ 
/*     */   static void setLoggerLevel(String paramString1, String paramString2) {
/*  89 */     ensureAvailable();
/*  90 */     proxy.setLoggerLevel(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   static String getParentLoggerName(String paramString) {
/*  94 */     ensureAvailable();
/*  95 */     return proxy.getParentLoggerName(paramString);
/*     */   }
/*     */ 
/*     */   static Object getLogger(String paramString) {
/*  99 */     ensureAvailable();
/* 100 */     return proxy.getLogger(paramString);
/*     */   }
/*     */ 
/*     */   static Object getLevel(Object paramObject) {
/* 104 */     ensureAvailable();
/* 105 */     return proxy.getLevel(paramObject);
/*     */   }
/*     */ 
/*     */   static void setLevel(Object paramObject1, Object paramObject2) {
/* 109 */     ensureAvailable();
/* 110 */     proxy.setLevel(paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */   static boolean isLoggable(Object paramObject1, Object paramObject2) {
/* 114 */     ensureAvailable();
/* 115 */     return proxy.isLoggable(paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */   static void log(Object paramObject1, Object paramObject2, String paramString) {
/* 119 */     ensureAvailable();
/* 120 */     proxy.log(paramObject1, paramObject2, paramString);
/*     */   }
/*     */ 
/*     */   static void log(Object paramObject1, Object paramObject2, String paramString, Throwable paramThrowable) {
/* 124 */     ensureAvailable();
/* 125 */     proxy.log(paramObject1, paramObject2, paramString, paramThrowable);
/*     */   }
/*     */ 
/*     */   static void log(Object paramObject1, Object paramObject2, String paramString, Object[] paramArrayOfObject) {
/* 129 */     ensureAvailable();
/* 130 */     proxy.log(paramObject1, paramObject2, paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */   static Object parseLevel(String paramString) {
/* 134 */     ensureAvailable();
/* 135 */     return proxy.parseLevel(paramString);
/*     */   }
/*     */ 
/*     */   static String getLevelName(Object paramObject) {
/* 139 */     ensureAvailable();
/* 140 */     return proxy.getLevelName(paramObject);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.logging.LoggingSupport
 * JD-Core Version:    0.6.2
 */