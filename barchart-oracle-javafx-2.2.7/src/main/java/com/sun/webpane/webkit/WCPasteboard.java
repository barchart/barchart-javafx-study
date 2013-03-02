/*    */ package com.sun.webpane.webkit;
/*    */ 
/*    */ import com.sun.webpane.platform.Pasteboard;
/*    */ import com.sun.webpane.platform.ServiceProvider;
/*    */ import com.sun.webpane.platform.graphics.WCImageFrame;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public final class WCPasteboard
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(WCPasteboard.class.getName());
/*    */ 
/* 23 */   private static Pasteboard pasteboard = ServiceProvider.getInstance().createPasteboard();
/*    */ 
/*    */   private static native void initIDs();
/*    */ 
/*    */   public static String getPlainText()
/*    */   {
/* 30 */     log.fine("getPlainText()");
/* 31 */     return pasteboard.getPlainText();
/*    */   }
/*    */ 
/*    */   public static String getHtml() {
/* 35 */     log.fine("getHtml()");
/* 36 */     return pasteboard.getHtml();
/*    */   }
/*    */ 
/*    */   public static void writePlainText(String paramString) {
/* 40 */     log.log(Level.FINE, "writePlainText(): text = {0}", new Object[] { paramString });
/* 41 */     pasteboard.writePlainText(paramString);
/*    */   }
/*    */ 
/*    */   public static void writeSelection(boolean paramBoolean, String paramString1, String paramString2)
/*    */   {
/* 46 */     log.log(Level.FINE, "writeSelection(): canSmartCopyOrDelete = {0},\n text = \n{1}\n html=\n{2}", new Object[] { Boolean.valueOf(paramBoolean), paramString1, paramString2 });
/*    */ 
/* 48 */     pasteboard.writeSelection(paramBoolean, paramString1, paramString2);
/*    */   }
/*    */ 
/*    */   public static void writeImage(WCImageFrame paramWCImageFrame) {
/* 52 */     log.log(Level.FINE, "writeImage(): img = {0}", new Object[] { paramWCImageFrame });
/* 53 */     pasteboard.writeImage(paramWCImageFrame);
/*    */   }
/*    */ 
/*    */   public static void writeUrl(String paramString1, String paramString2) {
/* 57 */     log.log(Level.FINE, "writeUrl(): url = {0}, markup = {1}", new Object[] { paramString1, paramString2 });
/*    */ 
/* 59 */     pasteboard.writeUrl(paramString1, paramString2);
/*    */   }
/*    */ 
/*    */   public static void clear()
/*    */   {
/* 66 */     log.fine("installing dummy transferable");
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 22 */     initIDs();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.WCPasteboard
 * JD-Core Version:    0.6.2
 */