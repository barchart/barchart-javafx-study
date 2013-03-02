/*    */ package com.sun.javafx.scene.control.skin.resources;
/*    */ 
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public final class ControlResources
/*    */ {
/*    */   private static ResourceBundle controlsResourceBundle;
/*    */ 
/*    */   public static ResourceBundle getBundle()
/*    */   {
/* 34 */     if (controlsResourceBundle == null) {
/* 35 */       controlsResourceBundle = ResourceBundle.getBundle("com/sun/javafx/scene/control/skin/resources/controls");
/*    */     }
/* 37 */     return controlsResourceBundle;
/*    */   }
/*    */ 
/*    */   public static String getString(String paramString) {
/* 41 */     return getBundle().getString(paramString);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.resources.ControlResources
 * JD-Core Version:    0.6.2
 */