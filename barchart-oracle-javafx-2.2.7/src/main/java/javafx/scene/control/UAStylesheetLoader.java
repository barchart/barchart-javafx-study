/*    */ package javafx.scene.control;
/*    */ 
/*    */ import com.sun.javafx.PlatformUtil;
/*    */ import com.sun.javafx.Utils;
/*    */ import com.sun.javafx.css.StyleManager;
/*    */ import com.sun.javafx.scene.control.skin.SkinBase;
/*    */ import java.net.URL;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ class UAStylesheetLoader
/*    */ {
/* 45 */   private static boolean stylesheetLoaded = false;
/*    */ 
/*    */   static void doLoad()
/*    */   {
/* 51 */     Holder.stylesheetLoader.loadUAStylesheet();
/*    */   }
/*    */ 
/*    */   private void loadUAStylesheet()
/*    */   {
/* 57 */     if (!stylesheetLoaded)
/* 58 */       AccessController.doPrivileged(new PrivilegedAction()
/*    */       {
/*    */         public Object run() {
/* 61 */           URL localURL = SkinBase.class.getResource("caspian/caspian.css");
/* 62 */           StyleManager.getInstance().setDefaultUserAgentStylesheet(localURL.toExternalForm());
/*    */ 
/* 64 */           if (PlatformUtil.isEmbedded()) {
/* 65 */             localURL = SkinBase.class.getResource("caspian/embedded.css");
/* 66 */             StyleManager.getInstance().addUserAgentStylesheet(localURL.toExternalForm());
/*    */ 
/* 68 */             if (Utils.isQVGAScreen()) {
/* 69 */               localURL = SkinBase.class.getResource("caspian/embedded-qvga.css");
/* 70 */               StyleManager.getInstance().addUserAgentStylesheet(localURL.toExternalForm());
/*    */             }
/*    */           }
/*    */ 
/* 74 */           UAStylesheetLoader.access$202(true);
/* 75 */           return null;
/*    */         }
/*    */       });
/*    */   }
/*    */ 
/*    */   private static class Holder
/*    */   {
/* 42 */     private static UAStylesheetLoader stylesheetLoader = new UAStylesheetLoader(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.UAStylesheetLoader
 * JD-Core Version:    0.6.2
 */