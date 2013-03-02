/*    */ package com.sun.javafx.scene.control.skin.resources;
/*    */ 
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public final class EmbeddedResources
/*    */ {
/*    */   private static ResourceBundle embeddedResourceBundle;
/*    */ 
/*    */   public static ResourceBundle getBundle()
/*    */   {
/* 34 */     if (embeddedResourceBundle == null) {
/* 35 */       embeddedResourceBundle = ResourceBundle.getBundle("com/sun/javafx/scene/control/skin/resources/embedded");
/*    */     }
/* 37 */     return embeddedResourceBundle;
/*    */   }
/*    */ 
/*    */   public static String getString(String paramString) {
/* 41 */     return getBundle().getString(paramString);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.resources.EmbeddedResources
 * JD-Core Version:    0.6.2
 */