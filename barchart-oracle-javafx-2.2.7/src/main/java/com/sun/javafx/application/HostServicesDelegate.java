/*    */ package com.sun.javafx.application;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedActionException;
/*    */ import java.security.PrivilegedExceptionAction;
/*    */ import javafx.application.Application;
/*    */ import netscape.javascript.JSObject;
/*    */ 
/*    */ public abstract class HostServicesDelegate
/*    */ {
/* 39 */   private static Method getInstanceMeth = null;
/*    */ 
/*    */   public static HostServicesDelegate getInstance(Application paramApplication)
/*    */   {
/* 43 */     HostServicesDelegate localHostServicesDelegate = null;
/*    */     try {
/* 45 */       localHostServicesDelegate = (HostServicesDelegate)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*    */       {
/*    */         public HostServicesDelegate run()
/*    */           throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ExceptionInInitializerError, InstantiationException
/*    */         {
/* 52 */           if (HostServicesDelegate.getInstanceMeth == null)
/*    */           {
/*    */             try
/*    */             {
/* 57 */               Class localClass = Class.forName("com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory", true, HostServicesDelegate.class.getClassLoader());
/*    */ 
/* 60 */               HostServicesDelegate.access$002(localClass.getMethod("getInstance", new Class[] { Application.class }));
/*    */             }
/*    */             catch (Exception localException) {
/* 63 */               localException.printStackTrace();
/* 64 */               return null;
/*    */             }
/*    */           }
/* 67 */           return (HostServicesDelegate)HostServicesDelegate.getInstanceMeth.invoke(null, new Object[] { this.val$app });
/*    */         }
/*    */       });
/*    */     }
/*    */     catch (PrivilegedActionException localPrivilegedActionException) {
/* 72 */       System.err.println(localPrivilegedActionException.getException().toString());
/* 73 */       return null;
/*    */     }
/*    */ 
/* 76 */     return localHostServicesDelegate;
/*    */   }
/*    */ 
/*    */   public abstract String getCodeBase();
/*    */ 
/*    */   public abstract String getDocumentBase();
/*    */ 
/*    */   public abstract void showDocument(String paramString);
/*    */ 
/*    */   public abstract JSObject getWebContext();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.application.HostServicesDelegate
 * JD-Core Version:    0.6.2
 */