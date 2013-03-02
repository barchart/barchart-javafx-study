/*    */ package com.sun.webpane.sg.prism;
/*    */ 
/*    */ import com.sun.webpane.platform.Utilities;
/*    */ import com.sun.webpane.platform.graphics.WCImage;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class UtilitiesImpl extends Utilities
/*    */ {
/* 11 */   private static final Logger log = Logger.getLogger("com.sun.webpane.sg.prism.UtilitiesImpl");
/*    */ 
/*    */   public WCImage getIconImage(String paramString)
/*    */   {
/* 16 */     log.fine("NOT IMPLEMENTED");
/* 17 */     return null;
/*    */   }
/*    */ 
/*    */   public Object toPlatformImage(WCImage paramWCImage)
/*    */   {
/* 22 */     return ((WCImageImpl)paramWCImage).getImage();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.UtilitiesImpl
 * JD-Core Version:    0.6.2
 */