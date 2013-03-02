/*    */ package com.sun.glass.ui;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ public class Platform
/*    */ {
/*    */   public static final String MAC = "Mac";
/*    */   public static final String WINDOWS = "Win";
/*    */   public static final String X11 = "X11";
/*    */   public static final String GTK = "Gtk";
/*    */   public static final String UNKNOWN = "unknown";
/* 16 */   private static String type = null;
/*    */ 
/*    */   public static synchronized String DeterminePlatform() {
/* 19 */     if (type == null)
/*    */     {
/* 22 */       String userPlatform = (String)AccessController.doPrivileged(new PrivilegedAction()
/*    */       {
/*    */         public String run() {
/* 25 */           return System.getProperty("glass.platform");
/*    */         }
/*    */       });
/* 29 */       if (userPlatform != null) {
/* 30 */         if (userPlatform.equals("macosx"))
/* 31 */           type = "Mac";
/* 32 */         else if (userPlatform.equals("windows"))
/* 33 */           type = "Win";
/* 34 */         else if (userPlatform.equals("linux"))
/* 35 */           type = "Gtk";
/* 36 */         else if (userPlatform.equals("x11"))
/* 37 */           type = "X11";
/* 38 */         else if (userPlatform.equals("gtk"))
/* 39 */           type = "Gtk";
/*    */         else
/* 41 */           type = userPlatform;
/* 42 */         return type;
/*    */       }
/*    */ 
/* 45 */       String osName = System.getProperty("os.name");
/* 46 */       String osNameLowerCase = osName.toLowerCase();
/* 47 */       if ((osNameLowerCase.startsWith("mac")) || (osNameLowerCase.startsWith("darwin")))
/* 48 */         type = "Mac";
/* 49 */       else if (osNameLowerCase.startsWith("wind"))
/* 50 */         type = "Win";
/* 51 */       else if (osNameLowerCase.startsWith("linux")) {
/* 52 */         type = "Gtk";
/*    */       }
/*    */     }
/*    */ 
/* 56 */     return type;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Platform
 * JD-Core Version:    0.6.2
 */