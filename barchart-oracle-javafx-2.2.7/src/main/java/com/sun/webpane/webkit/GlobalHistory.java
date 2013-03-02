/*    */ package com.sun.webpane.webkit;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class GlobalHistory
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(GlobalHistory.class.getName());
/*    */ 
/* 17 */   private static final Set<String> history = new HashSet();
/*    */ 
/*    */   static boolean historyContains(String paramString)
/*    */   {
/* 22 */     if (((paramString.startsWith("http://")) && (-1 == paramString.indexOf("/", 7))) || ((paramString.startsWith("https://")) && (-1 == paramString.indexOf("/", 8))))
/*    */     {
/* 25 */       paramString = paramString + "/";
/*    */     }
/*    */ 
/* 28 */     boolean bool = history.contains(paramString);
/* 29 */     log.fine(">" + paramString + "< visited = " + bool);
/*    */ 
/* 31 */     return bool;
/*    */   }
/*    */ 
/*    */   static void updateForStandardLoad(String paramString) {
/* 35 */     history.add(paramString);
/* 36 */     log.fine(">" + paramString + "< added");
/*    */   }
/*    */ 
/*    */   static void updateForReload(String paramString) {
/* 40 */     log.finer(paramString);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.GlobalHistory
 * JD-Core Version:    0.6.2
 */