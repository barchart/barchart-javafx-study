/*    */ package com.sun.javafx.jmx;
/*    */ 
/*    */ import com.sun.javafx.Logging;
/*    */ import com.sun.javafx.logging.PlatformLogger;
/*    */ 
/*    */ public abstract class MXExtension
/*    */ {
/* 31 */   private static final String EXTENSION_CLASS_NAME = System.getProperty("javafx.debug.jmx.class", "com.oracle.javafx.jmx.MXExtensionImpl");
/*    */ 
/*    */   public abstract void intialize() throws Exception;
/*    */ 
/*    */   public static void initializeIfAvailable()
/*    */   {
/*    */     try
/*    */     {
/* 39 */       Class localClass = Class.forName(EXTENSION_CLASS_NAME);
/*    */ 
/* 42 */       MXExtension localMXExtension = (MXExtension)localClass.newInstance();
/* 43 */       localMXExtension.intialize();
/*    */     } catch (Exception localException) {
/* 45 */       Logging.getJavaFXLogger().info("Failed to initialize management extension", localException);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.jmx.MXExtension
 * JD-Core Version:    0.6.2
 */