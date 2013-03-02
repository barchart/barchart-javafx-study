/*    */ package com.sun.glass.ui;
/*    */ 
/*    */ public abstract class ApplicationBase
/*    */ {
/*    */   protected String platform;
/*    */   protected String[] args;
/*    */   protected String name;
/*    */   protected Launchable launchable;
/*    */ 
/*    */   public String[] getArguments()
/*    */   {
/* 13 */     return this.args;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 17 */     return this.name;
/*    */   }
/*    */ 
/*    */   public String getPlatform() {
/* 21 */     return this.platform;
/*    */   }
/*    */ 
/*    */   public Launchable getLaunchable() {
/* 25 */     return this.launchable;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.ApplicationBase
 * JD-Core Version:    0.6.2
 */