/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ import com.sun.webpane.platform.graphics.WCImageFrame;
/*    */ 
/*    */ class PasteboardSimple
/*    */   implements Pasteboard
/*    */ {
/*    */   private String text;
/*    */   private String url;
/*    */   private String html;
/*    */ 
/*    */   public synchronized String getPlainText()
/*    */   {
/* 20 */     if ((this.text != null) && (!this.text.isEmpty()))
/* 21 */       return this.text;
/* 22 */     if ((this.url != null) && (!this.url.isEmpty())) {
/* 23 */       return this.url;
/*    */     }
/* 25 */     return this.html;
/*    */   }
/*    */ 
/*    */   public synchronized String getHtml() {
/* 29 */     return this.html;
/*    */   }
/*    */ 
/*    */   public void writePlainText(String paramString) {
/* 33 */     this.text = paramString;
/*    */   }
/*    */ 
/*    */   public synchronized void writeSelection(boolean paramBoolean, String paramString1, String paramString2)
/*    */   {
/* 38 */     this.text = paramString1;
/* 39 */     this.html = paramString2;
/*    */   }
/*    */ 
/*    */   public synchronized void writeImage(WCImageFrame paramWCImageFrame) {
/*    */   }
/*    */ 
/*    */   public synchronized void writeUrl(String paramString1, String paramString2) {
/* 46 */     this.url = paramString1;
/* 47 */     writeSelection(false, paramString1, paramString2);
/*    */   }
/*    */ 
/*    */   public synchronized void clear() {
/* 51 */     this.text = null;
/* 52 */     this.url = null;
/* 53 */     this.html = null;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.PasteboardSimple
 * JD-Core Version:    0.6.2
 */