/*     */ package com.sun.media.jfxmedia.logging;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class Logger
/*     */ {
/*     */   public static final int OFF = 2147483647;
/*     */   public static final int ERROR = 4;
/*     */   public static final int WARNING = 3;
/*     */   public static final int INFO = 2;
/*     */   public static final int DEBUG = 1;
/* 127 */   private static int currentLevel = 2147483647;
/* 128 */   private static long startTime = 0L;
/* 129 */   private static final Object lock = new Object();
/*     */ 
/*     */   private static void startLogger()
/*     */   {
/*     */     try
/*     */     {
/* 142 */       String level = System.getProperty("jfxmedia.loglevel", "off").toLowerCase();
/*     */       Integer logLevel;
/*     */       Integer logLevel;
/* 144 */       if (level.equals("debug")) {
/* 145 */         logLevel = Integer.valueOf(1);
/*     */       }
/*     */       else
/*     */       {
/*     */         Integer logLevel;
/* 146 */         if (level.equals("warning")) {
/* 147 */           logLevel = Integer.valueOf(3);
/*     */         }
/*     */         else
/*     */         {
/*     */           Integer logLevel;
/* 148 */           if (level.equals("error")) {
/* 149 */             logLevel = Integer.valueOf(4);
/*     */           }
/*     */           else
/*     */           {
/*     */             Integer logLevel;
/* 150 */             if (level.equals("info"))
/* 151 */               logLevel = Integer.valueOf(2);
/*     */             else
/* 153 */               logLevel = Integer.valueOf(2147483647); 
/*     */           }
/*     */         }
/*     */       }
/* 156 */       setLevel(logLevel.intValue());
/*     */ 
/* 158 */       startTime = System.currentTimeMillis();
/*     */     } catch (Exception e) {
/*     */     }
/* 161 */     if (canLog(1))
/* 162 */       logMsg(1, "Logger initialized");
/*     */   }
/*     */ 
/*     */   public static void initNative()
/*     */   {
/* 174 */     nativeInit();
/* 175 */     nativeSetNativeLevel(currentLevel);
/*     */   }
/*     */ 
/*     */   private static native void nativeInit();
/*     */ 
/*     */   public static void setLevel(int level)
/*     */   {
/* 188 */     currentLevel = level;
/*     */     try
/*     */     {
/* 191 */       nativeSetNativeLevel(level);
/*     */     }
/*     */     catch (UnsatisfiedLinkError e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private static native void nativeSetNativeLevel(int paramInt);
/*     */ 
/*     */   public static boolean canLog(int level)
/*     */   {
/* 204 */     if (level < currentLevel) {
/* 205 */       return false;
/*     */     }
/* 207 */     return true;
/*     */   }
/*     */ 
/*     */   public static void logMsg(int level, String msg)
/*     */   {
/* 218 */     synchronized (lock) {
/* 219 */       if (level < currentLevel) {
/* 220 */         return;
/*     */       }
/*     */ 
/* 223 */       if (level == 4)
/* 224 */         System.err.println("Error (" + getTimestamp() + "): " + msg);
/* 225 */       else if (level == 3)
/* 226 */         System.err.println("Warning (" + getTimestamp() + "): " + msg);
/* 227 */       else if (level == 2)
/* 228 */         System.out.println("Info (" + getTimestamp() + "): " + msg);
/* 229 */       else if (level == 1)
/* 230 */         System.out.println("Debug (" + getTimestamp() + "): " + msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void logMsg(int level, String sourceClass, String sourceMethod, String msg)
/*     */   {
/* 244 */     synchronized (lock) {
/* 245 */       if (level < currentLevel) {
/* 246 */         return;
/*     */       }
/*     */ 
/* 249 */       logMsg(level, sourceClass + ":" + sourceMethod + "() " + msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getTimestamp()
/*     */   {
/* 259 */     long elapsed = System.currentTimeMillis() - startTime;
/* 260 */     long elapsedHours = elapsed / 3600000L;
/* 261 */     long elapsedMinutes = (elapsed - elapsedHours * 60L * 60L * 1000L) / 60000L;
/* 262 */     long elapsedSeconds = (elapsed - elapsedHours * 60L * 60L * 1000L - elapsedMinutes * 60L * 1000L) / 1000L;
/* 263 */     long elapsedMillis = elapsed - elapsedHours * 60L * 60L * 1000L - elapsedMinutes * 60L * 1000L - elapsedSeconds * 1000L;
/*     */ 
/* 265 */     return String.format("%d:%02d:%02d:%03d", new Object[] { Long.valueOf(elapsedHours), Long.valueOf(elapsedMinutes), Long.valueOf(elapsedSeconds), Long.valueOf(elapsedMillis) });
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 132 */     startLogger();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.logging.Logger
 * JD-Core Version:    0.6.2
 */