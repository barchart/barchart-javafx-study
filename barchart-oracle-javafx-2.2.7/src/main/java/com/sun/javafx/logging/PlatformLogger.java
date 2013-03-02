/*     */ package com.sun.javafx.logging;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class PlatformLogger
/*     */ {
/*     */   public static final int OFF = 2147483647;
/*     */   public static final int SEVERE = 1000;
/*     */   public static final int WARNING = 900;
/*     */   public static final int INFO = 800;
/*     */   public static final int CONFIG = 700;
/*     */   public static final int FINE = 500;
/*     */   public static final int FINER = 400;
/*     */   public static final int FINEST = 300;
/*     */   public static final int ALL = -2147483648;
/*     */   private static final int defaultLevel = 800;
/* 105 */   private static boolean loggingEnabled = false;
/*     */ 
/* 120 */   private static Map<String, WeakReference<PlatformLogger>> loggers = new HashMap();
/*     */   private volatile LoggerProxy logger;
/*     */ 
/*     */   public static boolean isLoggingEnabled()
/*     */   {
/* 116 */     return loggingEnabled;
/*     */   }
/*     */ 
/*     */   public static synchronized PlatformLogger getLogger(String paramString)
/*     */   {
/* 127 */     PlatformLogger localPlatformLogger = null;
/* 128 */     WeakReference localWeakReference = (WeakReference)loggers.get(paramString);
/* 129 */     if (localWeakReference != null) {
/* 130 */       localPlatformLogger = (PlatformLogger)localWeakReference.get();
/*     */     }
/* 132 */     if (localPlatformLogger == null) {
/* 133 */       localPlatformLogger = new PlatformLogger(paramString);
/* 134 */       loggers.put(paramString, new WeakReference(localPlatformLogger));
/*     */     }
/* 136 */     return localPlatformLogger;
/*     */   }
/*     */ 
/*     */   public static synchronized void redirectPlatformLoggers()
/*     */   {
/* 144 */     if ((loggingEnabled) || (!LoggingSupport.isAvailable())) return;
/*     */ 
/* 146 */     loggingEnabled = true;
/* 147 */     for (Map.Entry localEntry : loggers.entrySet()) {
/* 148 */       WeakReference localWeakReference = (WeakReference)localEntry.getValue();
/* 149 */       PlatformLogger localPlatformLogger = (PlatformLogger)localWeakReference.get();
/* 150 */       if (localPlatformLogger != null)
/* 151 */         localPlatformLogger.newJavaLogger();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void newJavaLogger()
/*     */   {
/* 160 */     this.logger = new JavaLogger(this.logger.name, this.logger.effectiveLevel);
/*     */   }
/*     */ 
/*     */   private PlatformLogger(String paramString)
/*     */   {
/* 168 */     if (loggingEnabled)
/* 169 */       this.logger = new JavaLogger(paramString);
/*     */     else
/* 171 */       this.logger = new LoggerProxy(paramString);
/*     */   }
/*     */ 
/*     */   public boolean isEnabled()
/*     */   {
/* 180 */     return this.logger.isEnabled();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 187 */     return this.logger.name;
/*     */   }
/*     */ 
/*     */   public boolean isLoggable(int paramInt)
/*     */   {
/* 195 */     return this.logger.isLoggable(paramInt);
/*     */   }
/*     */ 
/*     */   public int getLevel()
/*     */   {
/* 203 */     return this.logger.getLevel();
/*     */   }
/*     */ 
/*     */   public void setLevel(int paramInt)
/*     */   {
/* 210 */     this.logger.setLevel(paramInt);
/*     */   }
/*     */ 
/*     */   public void severe(String paramString)
/*     */   {
/* 217 */     this.logger.doLog(1000, paramString);
/*     */   }
/*     */ 
/*     */   public void severe(String paramString, Throwable paramThrowable) {
/* 221 */     this.logger.doLog(1000, paramString, paramThrowable);
/*     */   }
/*     */ 
/*     */   public void severe(String paramString, Object[] paramArrayOfObject) {
/* 225 */     this.logger.doLog(1000, paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */   public void warning(String paramString)
/*     */   {
/* 232 */     this.logger.doLog(900, paramString);
/*     */   }
/*     */ 
/*     */   public void warning(String paramString, Throwable paramThrowable) {
/* 236 */     this.logger.doLog(900, paramString, paramThrowable);
/*     */   }
/*     */ 
/*     */   public void warning(String paramString, Object[] paramArrayOfObject) {
/* 240 */     this.logger.doLog(900, paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */   public void info(String paramString)
/*     */   {
/* 247 */     this.logger.doLog(800, paramString);
/*     */   }
/*     */ 
/*     */   public void info(String paramString, Throwable paramThrowable) {
/* 251 */     this.logger.doLog(800, paramString, paramThrowable);
/*     */   }
/*     */ 
/*     */   public void info(String paramString, Object[] paramArrayOfObject) {
/* 255 */     this.logger.doLog(800, paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */   public void config(String paramString)
/*     */   {
/* 262 */     this.logger.doLog(700, paramString);
/*     */   }
/*     */ 
/*     */   public void config(String paramString, Throwable paramThrowable) {
/* 266 */     this.logger.doLog(700, paramString, paramThrowable);
/*     */   }
/*     */ 
/*     */   public void config(String paramString, Object[] paramArrayOfObject) {
/* 270 */     this.logger.doLog(700, paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */   public void fine(String paramString)
/*     */   {
/* 277 */     this.logger.doLog(500, paramString);
/*     */   }
/*     */ 
/*     */   public void fine(String paramString, Throwable paramThrowable) {
/* 281 */     this.logger.doLog(500, paramString, paramThrowable);
/*     */   }
/*     */ 
/*     */   public void fine(String paramString, Object[] paramArrayOfObject) {
/* 285 */     this.logger.doLog(500, paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */   public void finer(String paramString)
/*     */   {
/* 292 */     this.logger.doLog(400, paramString);
/*     */   }
/*     */ 
/*     */   public void finer(String paramString, Throwable paramThrowable) {
/* 296 */     this.logger.doLog(400, paramString, paramThrowable);
/*     */   }
/*     */ 
/*     */   public void finer(String paramString, Object[] paramArrayOfObject) {
/* 300 */     this.logger.doLog(400, paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */   public void finest(String paramString)
/*     */   {
/* 307 */     this.logger.doLog(300, paramString);
/*     */   }
/*     */ 
/*     */   public void finest(String paramString, Throwable paramThrowable) {
/* 311 */     this.logger.doLog(300, paramString, paramThrowable);
/*     */   }
/*     */ 
/*     */   public void finest(String paramString, Object[] paramArrayOfObject) {
/* 315 */     this.logger.doLog(300, paramString, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */   private static String getLevelName(int paramInt)
/*     */   {
/* 578 */     switch (paramInt) { case 2147483647:
/* 579 */       return "OFF";
/*     */     case 1000:
/* 580 */       return "SEVERE";
/*     */     case 900:
/* 581 */       return "WARNING";
/*     */     case 800:
/* 582 */       return "INFO";
/*     */     case 700:
/* 583 */       return "CONFIG";
/*     */     case 500:
/* 584 */       return "FINE";
/*     */     case 400:
/* 585 */       return "FINER";
/*     */     case 300:
/* 586 */       return "FINEST";
/*     */     case -2147483648:
/* 587 */       return "ALL"; }
/* 588 */     return "UNKNOWN";
/*     */   }
/*     */ 
/*     */   static class JavaLogger extends PlatformLogger.LoggerProxy
/*     */   {
/* 495 */     private static final Map<Integer, Object> levelObjects = new HashMap();
/*     */     private final Object javaLogger;
/*     */ 
/*     */     private static void getLevelObjects()
/*     */     {
/* 507 */       int[] arrayOfInt1 = { 2147483647, 1000, 900, 800, 700, 500, 400, 300, -2147483648 };
/* 508 */       for (int k : arrayOfInt1) {
/* 509 */         Object localObject = LoggingSupport.parseLevel(PlatformLogger.getLevelName(k));
/* 510 */         levelObjects.put(Integer.valueOf(k), localObject);
/*     */       }
/*     */     }
/*     */ 
/*     */     JavaLogger(String paramString)
/*     */     {
/* 516 */       this(paramString, 0);
/*     */     }
/*     */ 
/*     */     JavaLogger(String paramString, int paramInt) {
/* 520 */       super(paramInt);
/* 521 */       this.javaLogger = LoggingSupport.getLogger(paramString);
/* 522 */       if (paramInt != 0)
/*     */       {
/* 524 */         LoggingSupport.setLevel(this.javaLogger, levelObjects.get(Integer.valueOf(paramInt)));
/*     */       }
/*     */     }
/*     */ 
/*     */     void doLog(int paramInt, String paramString)
/*     */     {
/* 535 */       LoggingSupport.log(this.javaLogger, levelObjects.get(Integer.valueOf(paramInt)), paramString);
/*     */     }
/*     */ 
/*     */     void doLog(int paramInt, String paramString, Throwable paramThrowable) {
/* 539 */       LoggingSupport.log(this.javaLogger, levelObjects.get(Integer.valueOf(paramInt)), paramString, paramThrowable);
/*     */     }
/*     */ 
/*     */     void doLog(int paramInt, String paramString, Object[] paramArrayOfObject) {
/* 543 */       int i = paramArrayOfObject != null ? paramArrayOfObject.length : 0;
/* 544 */       for (int j = 0; j < i; j++) {
/* 545 */         paramArrayOfObject[j] = String.valueOf(paramArrayOfObject[j]);
/*     */       }
/* 547 */       LoggingSupport.log(this.javaLogger, levelObjects.get(Integer.valueOf(paramInt)), paramString, paramArrayOfObject);
/*     */     }
/*     */ 
/*     */     boolean isEnabled() {
/* 551 */       Object localObject = LoggingSupport.getLevel(this.javaLogger);
/* 552 */       return (localObject == null) || (!localObject.equals(levelObjects.get(Integer.valueOf(2147483647))));
/*     */     }
/*     */ 
/*     */     int getLevel() {
/* 556 */       Object localObject = LoggingSupport.getLevel(this.javaLogger);
/* 557 */       if (localObject != null) {
/* 558 */         for (Map.Entry localEntry : levelObjects.entrySet()) {
/* 559 */           if (localObject == localEntry.getValue()) {
/* 560 */             return ((Integer)localEntry.getKey()).intValue();
/*     */           }
/*     */         }
/*     */       }
/* 564 */       return 0;
/*     */     }
/*     */ 
/*     */     void setLevel(int paramInt) {
/* 568 */       this.levelValue = paramInt;
/* 569 */       LoggingSupport.setLevel(this.javaLogger, levelObjects.get(Integer.valueOf(paramInt)));
/*     */     }
/*     */ 
/*     */     public boolean isLoggable(int paramInt) {
/* 573 */       return LoggingSupport.isLoggable(this.javaLogger, levelObjects.get(Integer.valueOf(paramInt)));
/*     */     }
/*     */ 
/*     */     static
/*     */     {
/* 499 */       if (LoggingSupport.isAvailable())
/*     */       {
/* 501 */         getLevelObjects();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class LoggerProxy
/*     */   {
/* 323 */     private static final PrintStream defaultStream = System.err;
/* 324 */     private static final String lineSeparator = (String)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public String run() {
/* 327 */         return System.getProperty("line.separator"); }  } );
/*     */     final String name;
/*     */     volatile int levelValue;
/* 333 */     volatile int effectiveLevel = 0;
/*     */     private static final String format = "{0,date} {0,time}";
/* 388 */     private Object[] args = new Object[1];
/*     */     private MessageFormat formatter;
/*     */     private Date dat;
/*     */ 
/* 336 */     LoggerProxy(String paramString) { this(paramString, 800); }
/*     */ 
/*     */     LoggerProxy(String paramString, int paramInt)
/*     */     {
/* 340 */       this.name = paramString;
/* 341 */       this.levelValue = (paramInt == 0 ? 800 : paramInt);
/*     */     }
/*     */ 
/*     */     boolean isEnabled() {
/* 345 */       return this.levelValue != 2147483647;
/*     */     }
/*     */ 
/*     */     int getLevel() {
/* 349 */       return this.effectiveLevel;
/*     */     }
/*     */ 
/*     */     void setLevel(int paramInt) {
/* 353 */       this.levelValue = paramInt;
/* 354 */       this.effectiveLevel = paramInt;
/*     */     }
/*     */ 
/*     */     void doLog(int paramInt, String paramString) {
/* 358 */       if ((paramInt < this.levelValue) || (this.levelValue == 2147483647)) {
/* 359 */         return;
/*     */       }
/* 361 */       defaultStream.println(format(paramInt, paramString, null));
/*     */     }
/*     */ 
/*     */     void doLog(int paramInt, String paramString, Throwable paramThrowable) {
/* 365 */       if ((paramInt < this.levelValue) || (this.levelValue == 2147483647)) {
/* 366 */         return;
/*     */       }
/* 368 */       defaultStream.println(format(paramInt, paramString, paramThrowable));
/*     */     }
/*     */ 
/*     */     void doLog(int paramInt, String paramString, Object[] paramArrayOfObject) {
/* 372 */       if ((paramInt < this.levelValue) || (this.levelValue == 2147483647)) {
/* 373 */         return;
/*     */       }
/* 375 */       String str = formatMessage(paramString, paramArrayOfObject);
/* 376 */       defaultStream.println(format(paramInt, str, null));
/*     */     }
/*     */ 
/*     */     public boolean isLoggable(int paramInt) {
/* 380 */       if ((paramInt < this.levelValue) || (this.levelValue == 2147483647)) {
/* 381 */         return false;
/*     */       }
/* 383 */       return true;
/*     */     }
/*     */ 
/*     */     private String formatMessage(String paramString, Object[] paramArrayOfObject)
/*     */     {
/*     */       try
/*     */       {
/* 396 */         if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0))
/*     */         {
/* 398 */           return paramString;
/*     */         }
/*     */ 
/* 405 */         if ((paramString.indexOf("{0") >= 0) || (paramString.indexOf("{1") >= 0) || (paramString.indexOf("{2") >= 0) || (paramString.indexOf("{3") >= 0))
/*     */         {
/* 407 */           return MessageFormat.format(paramString, paramArrayOfObject);
/*     */         }
/* 409 */         return paramString;
/*     */       } catch (Exception localException) {
/*     */       }
/* 412 */       return paramString;
/*     */     }
/*     */ 
/*     */     private synchronized String format(int paramInt, String paramString, Throwable paramThrowable)
/*     */     {
/* 417 */       StringBuffer localStringBuffer = new StringBuffer();
/*     */ 
/* 431 */       localStringBuffer.append(PlatformLogger.getLevelName(paramInt));
/* 432 */       localStringBuffer.append(": ");
/* 433 */       localStringBuffer.append(getCallerInfo());
/* 434 */       localStringBuffer.append(" ");
/* 435 */       localStringBuffer.append(paramString);
/* 436 */       if (paramThrowable != null) {
/*     */         try {
/* 438 */           StringWriter localStringWriter = new StringWriter();
/* 439 */           PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
/* 440 */           paramThrowable.printStackTrace(localPrintWriter);
/* 441 */           localPrintWriter.close();
/* 442 */           localStringBuffer.append(lineSeparator);
/* 443 */           localStringBuffer.append(localStringWriter.toString());
/*     */         } catch (Exception localException) {
/* 445 */           throw new AssertionError(localException);
/*     */         }
/*     */       }
/*     */ 
/* 449 */       return localStringBuffer.toString();
/*     */     }
/*     */ 
/*     */     private String getCallerInfo()
/*     */     {
/* 455 */       Object localObject = null;
/* 456 */       String str1 = null;
/*     */ 
/* 458 */       StackTraceElement[] arrayOfStackTraceElement = new Throwable().getStackTrace();
/*     */ 
/* 461 */       int i = 1;
/* 462 */       for (int j = 0; j < arrayOfStackTraceElement.length; j++)
/*     */       {
/* 465 */         StackTraceElement localStackTraceElement = arrayOfStackTraceElement[j];
/* 466 */         String str2 = localStackTraceElement.getClassName();
/* 467 */         if (i != 0)
/*     */         {
/* 469 */           if (str2.equals("com.sun.javafx.logging.PlatformLogger")) {
/* 470 */             i = 0;
/*     */           }
/*     */         }
/* 473 */         else if (!str2.equals("com.sun.javafx.logging.PlatformLogger"))
/*     */         {
/* 475 */           localObject = str2;
/* 476 */           str1 = localStackTraceElement.getMethodName();
/* 477 */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 482 */       if (localObject != null) {
/* 483 */         return localObject + " " + str1;
/*     */       }
/* 485 */       return this.name;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.logging.PlatformLogger
 * JD-Core Version:    0.6.2
 */