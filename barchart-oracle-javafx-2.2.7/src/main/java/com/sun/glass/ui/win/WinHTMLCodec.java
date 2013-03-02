/*    */ package com.sun.glass.ui.win;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class WinHTMLCodec
/*    */ {
/*    */   public static final String defaultCharset = "UTF-8";
/*    */ 
/*    */   public static byte[] encode(byte[] html)
/*    */   {
/* 33 */     return HTMLCodec.convertToHTMLFormat(html);
/*    */   }
/*    */ 
/*    */   public static byte[] decode(byte[] data) {
/*    */     try {
/* 38 */       ByteArrayInputStream bais = new ByteArrayInputStream(data);
/* 39 */       InputStream is = new HTMLCodec(bais, EHTMLReadMode.HTML_READ_SELECTION);
/*    */ 
/* 42 */       ByteArrayOutputStream baos = new ByteArrayOutputStream(data.length);
/*    */       int b;
/* 47 */       while ((b = is.read()) != -1) {
/* 48 */         baos.write(b);
/*    */       }
/*    */ 
/* 51 */       return baos.toByteArray();
/*    */     } catch (IOException ex) {
/* 53 */       throw new RuntimeException("Unexpected IOException caught", ex);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinHTMLCodec
 * JD-Core Version:    0.6.2
 */