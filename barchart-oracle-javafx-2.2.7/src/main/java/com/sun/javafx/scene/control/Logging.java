/*    */ package com.sun.javafx.scene.control;
/*    */ 
/*    */ import com.sun.javafx.logging.PlatformLogger;
/*    */ import com.sun.javafx.tk.Toolkit;
/*    */ 
/*    */ public class Logging
/*    */ {
/* 40 */   private static PlatformLogger controlsLogger = null;
/*    */ 
/*    */   public static final PlatformLogger getControlsLogger()
/*    */   {
/* 46 */     if (controlsLogger == null) {
/* 47 */       controlsLogger = Toolkit.getToolkit().getLogger("controls");
/*    */     }
/* 49 */     return controlsLogger;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.Logging
 * JD-Core Version:    0.6.2
 */