/*    */ package com.sun.webpane.webkit.unicode;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.CharBuffer;
/*    */ import java.nio.charset.Charset;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.SortedMap;
/*    */ 
/*    */ public class TextCodec
/*    */ {
/*    */   private final Charset charset;
/* 19 */   private static Map<String, String> reMap = new HashMap();
/*    */ 
/*    */   public TextCodec(String paramString)
/*    */   {
/* 29 */     this.charset = Charset.forName(paramString);
/*    */   }
/*    */ 
/*    */   public byte[] encode(char[] paramArrayOfChar) {
/* 33 */     ByteBuffer localByteBuffer = this.charset.encode(CharBuffer.wrap(paramArrayOfChar));
/* 34 */     byte[] arrayOfByte = new byte[localByteBuffer.remaining()];
/* 35 */     localByteBuffer.get(arrayOfByte);
/* 36 */     return arrayOfByte;
/*    */   }
/*    */ 
/*    */   public String decode(byte[] paramArrayOfByte) {
/* 40 */     CharBuffer localCharBuffer = this.charset.decode(ByteBuffer.wrap(paramArrayOfByte));
/* 41 */     char[] arrayOfChar = new char[localCharBuffer.remaining()];
/* 42 */     localCharBuffer.get(arrayOfChar);
/* 43 */     return new String(arrayOfChar);
/*    */   }
/*    */ 
/*    */   public static String[] getEncodings()
/*    */   {
/* 55 */     ArrayList localArrayList = new ArrayList();
/* 56 */     SortedMap localSortedMap = Charset.availableCharsets();
/* 57 */     for (Iterator localIterator1 = localSortedMap.keySet().iterator(); localIterator1.hasNext(); ) { str1 = (String)localIterator1.next();
/* 58 */       localArrayList.add(str1);
/* 59 */       localArrayList.add(str1);
/* 60 */       Charset localCharset = (Charset)localSortedMap.get(str1);
/* 61 */       for (String str2 : localCharset.aliases())
/*    */       {
/* 64 */         if (!str2.equals("8859_1"))
/*    */         {
/* 66 */           localArrayList.add(str2);
/* 67 */           String str3 = (String)reMap.get(str2);
/* 68 */           localArrayList.add(str3 == null ? str1 : str3);
/*    */         }
/*    */       }
/*    */     }
/*    */     String str1;
/* 71 */     return (String[])localArrayList.toArray(new String[0]);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 21 */     reMap.put("ISO-10646-UCS-2", "UTF-16");
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.unicode.TextCodec
 * JD-Core Version:    0.6.2
 */