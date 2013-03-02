/*     */ package com.sun.browser.plugin;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.TreeMap;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class PluginManager
/*     */ {
/*  18 */   private static final Logger log = Logger.getLogger("com.sun.browser.plugin.PluginManager");
/*     */ 
/*  21 */   static ServiceLoader<PluginHandler> pHandlers = ServiceLoader.load(PluginHandler.class);
/*     */ 
/*  24 */   static TreeMap<String, PluginHandler> hndMap = new TreeMap();
/*     */   static PluginHandler[] hndArray;
/*  29 */   static HashSet<String> disabledPluginHandlers = new HashSet();
/*     */ 
/*     */   static void updatePluginHandlers()
/*     */   {
/*  34 */     log.fine("Update plugin handlers");
/*     */ 
/*  36 */     hndMap.clear();
/*     */ 
/*  38 */     Iterator localIterator = pHandlers.iterator();
/*  39 */     while (localIterator.hasNext()) {
/*  40 */       localObject = (PluginHandler)localIterator.next();
/*  41 */       if ((((PluginHandler)localObject).isSupportedPlatform()) && (!isDisabledPlugin((PluginHandler)localObject)))
/*     */       {
/*  43 */         String[] arrayOfString1 = ((PluginHandler)localObject).supportedMIMETypes();
/*  44 */         for (String str : arrayOfString1) {
/*  45 */           hndMap.put(str, localObject);
/*  46 */           log.fine(str);
/*     */         }
/*     */       }
/*     */     }
/*  50 */     Object localObject = hndMap.values();
/*  51 */     hndArray = (PluginHandler[])((Collection)localObject).toArray(new PluginHandler[((Collection)localObject).size()]);
/*     */   }
/*     */ 
/*     */   public static Plugin createPlugin(URL paramURL, String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2)
/*     */   {
/*     */     try
/*     */     {
/*  70 */       PluginHandler localPluginHandler = (PluginHandler)hndMap.get(paramString);
/*  71 */       if (localPluginHandler == null) {
/*  72 */         return new DefaultPlugin(paramURL, paramString, paramArrayOfString1, paramArrayOfString2);
/*     */       }
/*  74 */       Plugin localPlugin = localPluginHandler.createPlugin(paramURL, paramString, paramArrayOfString1, paramArrayOfString2);
/*  75 */       if (localPlugin == null) {
/*  76 */         return new DefaultPlugin(paramURL, paramString, paramArrayOfString1, paramArrayOfString2);
/*     */       }
/*  78 */       return localPlugin;
/*     */     }
/*     */     catch (Throwable localThrowable)
/*     */     {
/*  82 */       log.log(Level.FINE, "Cannot create plugin", localThrowable);
/*  83 */     }return new DefaultPlugin(paramURL, paramString, paramArrayOfString1, paramArrayOfString2);
/*     */   }
/*     */ 
/*     */   public static List<PluginHandler> getAvailablePlugins()
/*     */   {
/*  89 */     Vector localVector = new Vector();
/*  90 */     Iterator localIterator = pHandlers.iterator();
/*  91 */     while (localIterator.hasNext()) {
/*  92 */       PluginHandler localPluginHandler = (PluginHandler)localIterator.next();
/*  93 */       if (localPluginHandler.isSupportedPlatform()) {
/*  94 */         localVector.add(localPluginHandler);
/*     */       }
/*     */     }
/*  97 */     return localVector;
/*     */   }
/*     */ 
/*     */   public static PluginHandler getEnabledPlugin(int paramInt) {
/* 101 */     if ((paramInt < 0) || (paramInt >= hndArray.length)) return null;
/* 102 */     return hndArray[paramInt];
/*     */   }
/*     */ 
/*     */   public static int getEnabledPluginCount() {
/* 106 */     return hndArray.length;
/*     */   }
/*     */ 
/*     */   public static void disablePlugin(PluginHandler paramPluginHandler) {
/* 110 */     disabledPluginHandlers.add(paramPluginHandler.getClass().getCanonicalName());
/* 111 */     updatePluginHandlers();
/*     */   }
/*     */ 
/*     */   public static void enablePlugin(PluginHandler paramPluginHandler) {
/* 115 */     disabledPluginHandlers.remove(paramPluginHandler.getClass().getCanonicalName());
/* 116 */     updatePluginHandlers();
/*     */   }
/*     */ 
/*     */   public static boolean isDisabledPlugin(PluginHandler paramPluginHandler) {
/* 120 */     return disabledPluginHandlers.contains(paramPluginHandler.getClass().getCanonicalName());
/*     */   }
/*     */ 
/*     */   public static boolean supportsMIMEType(String paramString)
/*     */   {
/* 125 */     return hndMap.containsKey(paramString);
/*     */   }
/*     */ 
/*     */   public static String getPluginNameForMIMEType(String paramString) {
/* 129 */     PluginHandler localPluginHandler = (PluginHandler)hndMap.get(paramString);
/* 130 */     if (localPluginHandler != null) return localPluginHandler.getName();
/* 131 */     return "";
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  55 */     if ("false".equalsIgnoreCase(System.getProperty("com.sun.browser.plugin")))
/*     */     {
/*  58 */       for (PluginHandler localPluginHandler : getAvailablePlugins()) {
/*  59 */         disabledPluginHandlers.add(localPluginHandler.getClass().getCanonicalName());
/*     */       }
/*     */     }
/*     */ 
/*  63 */     updatePluginHandlers();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.browser.plugin.PluginManager
 * JD-Core Version:    0.6.2
 */