/*    */ package com.sun.webpane.sg;
/*    */ 
/*    */ import com.sun.webpane.platform.Pasteboard;
/*    */ import com.sun.webpane.platform.Utilities;
/*    */ import com.sun.webpane.platform.graphics.WCImageFrame;
/*    */ import javafx.scene.image.Image;
/*    */ import javafx.scene.input.Clipboard;
/*    */ import javafx.scene.input.ClipboardContent;
/*    */ 
/*    */ class PasteboardImpl
/*    */   implements Pasteboard
/*    */ {
/* 17 */   private Clipboard clipboard = Clipboard.getSystemClipboard();
/*    */ 
/*    */   public String getPlainText()
/*    */   {
/* 23 */     return this.clipboard.getString();
/*    */   }
/*    */ 
/*    */   public String getHtml() {
/* 27 */     return this.clipboard.getHtml();
/*    */   }
/*    */ 
/*    */   public void writePlainText(String paramString) {
/* 31 */     ClipboardContent localClipboardContent = new ClipboardContent();
/* 32 */     localClipboardContent.putString(paramString);
/* 33 */     this.clipboard.setContent(localClipboardContent);
/*    */   }
/*    */ 
/*    */   public void writeSelection(boolean paramBoolean, String paramString1, String paramString2) {
/* 37 */     ClipboardContent localClipboardContent = new ClipboardContent();
/* 38 */     localClipboardContent.putString(paramString1);
/* 39 */     localClipboardContent.putHtml(paramString2);
/* 40 */     this.clipboard.setContent(localClipboardContent);
/*    */   }
/*    */ 
/*    */   public void writeImage(WCImageFrame paramWCImageFrame) {
/* 44 */     Object localObject = Utilities.getUtilities().toPlatformImage(paramWCImageFrame.getFrame());
/* 45 */     Image localImage = Image.impl_fromPlatformImage(localObject);
/* 46 */     ClipboardContent localClipboardContent = new ClipboardContent();
/* 47 */     localClipboardContent.putImage(localImage);
/* 48 */     this.clipboard.setContent(localClipboardContent);
/*    */   }
/*    */ 
/*    */   public void writeUrl(String paramString1, String paramString2) {
/* 52 */     ClipboardContent localClipboardContent = new ClipboardContent();
/* 53 */     localClipboardContent.putString(paramString1);
/* 54 */     localClipboardContent.putHtml(paramString2);
/* 55 */     localClipboardContent.putUrl(paramString1);
/* 56 */     this.clipboard.setContent(localClipboardContent);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.PasteboardImpl
 * JD-Core Version:    0.6.2
 */