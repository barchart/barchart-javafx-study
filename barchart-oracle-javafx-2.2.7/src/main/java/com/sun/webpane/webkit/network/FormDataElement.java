/*     */ package com.sun.webpane.webkit.network;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ abstract class FormDataElement
/*     */ {
/*     */   private InputStream inputStream;
/*     */ 
/*     */   void open()
/*     */     throws IOException
/*     */   {
/*  30 */     this.inputStream = createInputStream();
/*     */   }
/*     */ 
/*     */   long getSize()
/*     */   {
/*  37 */     if (this.inputStream == null) {
/*  38 */       throw new IllegalStateException();
/*     */     }
/*  40 */     return doGetSize();
/*     */   }
/*     */ 
/*     */   InputStream getInputStream()
/*     */   {
/*  48 */     if (this.inputStream == null) {
/*  49 */       throw new IllegalStateException();
/*     */     }
/*  51 */     return this.inputStream;
/*     */   }
/*     */ 
/*     */   void close()
/*     */     throws IOException
/*     */   {
/*  61 */     if (this.inputStream != null) {
/*  62 */       this.inputStream.close();
/*  63 */       this.inputStream = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract InputStream createInputStream()
/*     */     throws IOException;
/*     */ 
/*     */   protected abstract long doGetSize();
/*     */ 
/*     */   private static FormDataElement fwkCreateFromByteArray(byte[] paramArrayOfByte)
/*     */   {
/*  83 */     return new ByteArrayElement(paramArrayOfByte, null);
/*     */   }
/*     */ 
/*     */   private static FormDataElement fwkCreateFromFile(String paramString)
/*     */   {
/*  90 */     return new FileElement(paramString, null);
/*     */   }
/*     */ 
/*     */   private static class FileElement extends FormDataElement
/*     */   {
/*     */     private final File file;
/*     */ 
/*     */     private FileElement(String paramString)
/*     */     {
/* 126 */       this.file = new File(paramString);
/*     */     }
/*     */ 
/*     */     protected InputStream createInputStream()
/*     */       throws IOException
/*     */     {
/* 132 */       return new BufferedInputStream(new FileInputStream(this.file));
/*     */     }
/*     */ 
/*     */     protected long doGetSize()
/*     */     {
/* 137 */       return this.file.length();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class ByteArrayElement extends FormDataElement
/*     */   {
/*     */     private final byte[] byteArray;
/*     */ 
/*     */     private ByteArrayElement(byte[] paramArrayOfByte)
/*     */     {
/* 102 */       this.byteArray = paramArrayOfByte;
/*     */     }
/*     */ 
/*     */     protected InputStream createInputStream()
/*     */     {
/* 108 */       return new ByteArrayInputStream(this.byteArray);
/*     */     }
/*     */ 
/*     */     protected long doGetSize()
/*     */     {
/* 113 */       return this.byteArray.length;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.FormDataElement
 * JD-Core Version:    0.6.2
 */