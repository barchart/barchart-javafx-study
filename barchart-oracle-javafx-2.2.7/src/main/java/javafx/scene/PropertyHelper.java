/*    */ package javafx.scene;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ class PropertyHelper
/*    */ {
/*    */   static boolean getBooleanProperty(String paramString)
/*    */   {
/*    */     try
/*    */     {
/* 36 */       return ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*    */       {
/*    */         public Boolean run()
/*    */         {
/* 40 */           String str = System.getProperty(this.val$propName);
/* 41 */           return Boolean.valueOf("true".equals(str.toLowerCase()));
/*    */         }
/*    */       })).booleanValue();
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/*    */ 
/* 47 */     return false;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.PropertyHelper
 * JD-Core Version:    0.6.2
 */