/*    */ package com.sun.scenario;
/*    */ 
/*    */ import com.sun.scenario.animation.AbstractMasterTimer;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class ToolkitAccessor
/*    */ {
/*    */   private static ToolkitAccessor instance;
/*    */ 
/*    */   public static void setInstance(ToolkitAccessor paramToolkitAccessor)
/*    */   {
/* 39 */     instance = paramToolkitAccessor;
/*    */   }
/*    */ 
/*    */   private static ToolkitAccessor getInstance() {
/* 43 */     if (instance == null)
/*    */       try
/*    */       {
/* 46 */         Class localClass = Class.forName("com.sun.scenario.StandaloneAccessor");
/*    */ 
/* 48 */         setInstance((ToolkitAccessor)localClass.newInstance());
/*    */       }
/*    */       catch (Throwable localThrowable)
/*    */       {
/*    */       }
/* 53 */     return instance;
/*    */   }
/*    */ 
/*    */   public static Map<Object, Object> getContextMap() {
/* 57 */     return getInstance().getContextMapImpl();
/*    */   }
/*    */ 
/*    */   public static AbstractMasterTimer getMasterTimer() {
/* 61 */     return getInstance().getMasterTimerImpl();
/*    */   }
/*    */ 
/*    */   public abstract Map<Object, Object> getContextMapImpl();
/*    */ 
/*    */   public abstract AbstractMasterTimer getMasterTimerImpl();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.ToolkitAccessor
 * JD-Core Version:    0.6.2
 */