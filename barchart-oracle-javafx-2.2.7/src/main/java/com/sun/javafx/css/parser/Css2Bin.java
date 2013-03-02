/*    */ package com.sun.javafx.css.parser;
/*    */ 
/*    */ import com.sun.javafx.css.StringStore;
/*    */ import com.sun.javafx.css.Stylesheet;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.net.URL;
/*    */ 
/*    */ public class Css2Bin
/*    */ {
/*    */   public static void main(String[] paramArrayOfString)
/*    */     throws Exception
/*    */   {
/* 44 */     if (paramArrayOfString.length < 1) throw new IllegalArgumentException("expected file name as argument");
/*    */     try
/*    */     {
/* 47 */       String str1 = paramArrayOfString[0];
/* 48 */       String str2 = paramArrayOfString.length > 1 ? paramArrayOfString[1] : str1.substring(0, str1.lastIndexOf('.') + 1).concat("bss");
/*    */ 
/* 51 */       convertToBinary(str1, str2);
/*    */     }
/*    */     catch (Exception localException) {
/* 54 */       System.err.println(localException.toString());
/* 55 */       localException.printStackTrace(System.err);
/* 56 */       System.exit(-1);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void convertToBinary(String paramString1, String paramString2) throws IOException {
/* 61 */     URL localURL = new URL("file", null, paramString1);
/*    */ 
/* 63 */     CSSParser.EXIT_ON_ERROR = true;
/* 64 */     Stylesheet localStylesheet = CSSParser.getInstance().parse(localURL);
/*    */ 
/* 67 */     StringStore localStringStore = new StringStore();
/* 68 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/* 69 */     DataOutputStream localDataOutputStream1 = new DataOutputStream(localByteArrayOutputStream);
/* 70 */     localStylesheet.writeBinary(localDataOutputStream1, localStringStore);
/* 71 */     localDataOutputStream1.flush();
/* 72 */     localDataOutputStream1.close();
/*    */ 
/* 74 */     File localFile = new File(paramString2);
/* 75 */     FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
/* 76 */     DataOutputStream localDataOutputStream2 = new DataOutputStream(localFileOutputStream);
/*    */ 
/* 78 */     localDataOutputStream2.writeShort(2);
/*    */ 
/* 80 */     localStringStore.writeBinary(localDataOutputStream2);
/*    */ 
/* 82 */     localDataOutputStream2.write(localByteArrayOutputStream.toByteArray());
/* 83 */     localDataOutputStream2.flush();
/* 84 */     localDataOutputStream2.close();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.parser.Css2Bin
 * JD-Core Version:    0.6.2
 */