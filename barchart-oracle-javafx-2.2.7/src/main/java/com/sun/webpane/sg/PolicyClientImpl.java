/*    */ package com.sun.webpane.sg;
/*    */ 
/*    */ import com.sun.webpane.platform.PolicyClient;
/*    */ import java.net.URL;
/*    */ 
/*    */ public class PolicyClientImpl
/*    */   implements PolicyClient
/*    */ {
/*    */   public boolean permitNavigateAction(long paramLong, URL paramURL)
/*    */   {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean permitRedirectAction(long paramLong, URL paramURL) {
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean permitAcceptResourceAction(long paramLong, URL paramURL) {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean permitSubmitDataAction(long paramLong, URL paramURL, String paramString) {
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean permitResubmitDataAction(long paramLong, URL paramURL, String paramString) {
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean permitEnableScriptsAction(long paramLong, URL paramURL) {
/* 48 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean permitNewPageAction(long paramLong, URL paramURL) {
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean permitClosePageAction(long paramLong) {
/* 56 */     return true;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.PolicyClientImpl
 * JD-Core Version:    0.6.2
 */