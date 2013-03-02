/*     */ package com.sun.t2k;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ 
/*     */ public class FontFileReader
/*     */   implements FontConstants
/*     */ {
/*     */   String filename;
/*     */   long filesize;
/*     */   RandomAccessFile raFile;
/*     */   private static final int READBUFFERSIZE = 1024;
/*     */   private byte[] readBuffer;
/*     */   private int readBufferLen;
/*     */   private int readBufferStart;
/*     */ 
/*     */   public FontFileReader(String paramString)
/*     */   {
/*  23 */     this.filename = paramString;
/*     */   }
/*     */ 
/*     */   public String getFilename() {
/*  27 */     return this.filename;
/*     */   }
/*     */ 
/*     */   public synchronized boolean openFile()
/*     */     throws PrivilegedActionException
/*     */   {
/*  37 */     if (this.raFile != null) {
/*  38 */       return false;
/*     */     }
/*  40 */     this.raFile = ((RandomAccessFile)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public RandomAccessFile run() {
/*     */         try {
/*  44 */           return new RandomAccessFile(FontFileReader.this.filename, "r"); } catch (FileNotFoundException localFileNotFoundException) {
/*     */         }
/*  46 */         return null;
/*     */       }
/*     */     }));
/*  50 */     if (this.raFile != null)
/*     */       try {
/*  52 */         this.filesize = this.raFile.length();
/*  53 */         return true;
/*     */       }
/*     */       catch (IOException localIOException) {
/*     */       }
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */   public synchronized void closeFile() throws IOException {
/*  61 */     if (this.raFile != null) {
/*  62 */       this.raFile.close();
/*  63 */       this.raFile = null;
/*  64 */       this.readBuffer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized long getLength() {
/*  69 */     return this.filesize;
/*     */   }
/*     */ 
/*     */   public synchronized void reset() throws IOException {
/*  73 */     if (this.raFile != null)
/*  74 */       this.raFile.seek(0L);
/*     */   }
/*     */ 
/*     */   private synchronized int readFromFile(byte[] paramArrayOfByte, long paramLong, int paramInt)
/*     */   {
/*     */     try
/*     */     {
/* 184 */       this.raFile.seek(paramLong);
/*     */ 
/* 186 */       return this.raFile.read(paramArrayOfByte, 0, paramInt);
/*     */     }
/*     */     catch (IOException localIOException) {
/* 189 */       if (T2KFontFactory.debugFonts)
/* 190 */         localIOException.printStackTrace();
/*     */     }
/* 192 */     return 0;
/*     */   }
/*     */ 
/*     */   public synchronized Buffer readBlock(int paramInt1, int paramInt2)
/*     */   {
/* 213 */     if (this.readBuffer == null) {
/* 214 */       this.readBuffer = new byte[1024];
/* 215 */       this.readBufferLen = 0;
/*     */     }
/*     */ 
/* 218 */     if (paramInt2 <= 1024) {
/* 219 */       if ((this.readBufferStart <= paramInt1) && (this.readBufferStart + this.readBufferLen >= paramInt1 + paramInt2))
/*     */       {
/* 222 */         return new Buffer(this.readBuffer, paramInt1 - this.readBufferStart);
/*     */       }
/* 224 */       this.readBufferStart = paramInt1;
/* 225 */       this.readBufferLen = (paramInt1 + 1024 > this.filesize ? (int)this.filesize - paramInt1 : 1024);
/*     */ 
/* 227 */       readFromFile(this.readBuffer, this.readBufferStart, this.readBufferLen);
/* 228 */       return new Buffer(this.readBuffer, 0);
/*     */     }
/*     */ 
/* 231 */     byte[] arrayOfByte = new byte[paramInt2];
/* 232 */     readFromFile(arrayOfByte, paramInt1, paramInt2);
/* 233 */     return new Buffer(arrayOfByte, 0);
/*     */   }
/*     */ 
/*     */   static class Buffer
/*     */   {
/*     */     byte[] data;
/*     */     int pos;
/*     */     int orig;
/*     */ 
/*     */     Buffer(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  91 */       this.orig = (this.pos = paramInt);
/*  92 */       this.data = paramArrayOfByte;
/*     */     }
/*     */ 
/*     */     int getInt(int paramInt) {
/*  96 */       paramInt += this.orig;
/*  97 */       int i = this.data[(paramInt++)] & 0xFF;
/*  98 */       i <<= 8;
/*  99 */       i |= this.data[(paramInt++)] & 0xFF;
/* 100 */       i <<= 8;
/* 101 */       i |= this.data[(paramInt++)] & 0xFF;
/* 102 */       i <<= 8;
/* 103 */       i |= this.data[(paramInt++)] & 0xFF;
/* 104 */       return i;
/*     */     }
/*     */ 
/*     */     int getInt() {
/* 108 */       int i = this.data[(this.pos++)] & 0xFF;
/* 109 */       i <<= 8;
/* 110 */       i |= this.data[(this.pos++)] & 0xFF;
/* 111 */       i <<= 8;
/* 112 */       i |= this.data[(this.pos++)] & 0xFF;
/* 113 */       i <<= 8;
/* 114 */       i |= this.data[(this.pos++)] & 0xFF;
/* 115 */       return i;
/*     */     }
/*     */ 
/*     */     short getShort(int paramInt) {
/* 119 */       paramInt += this.orig;
/* 120 */       int i = this.data[(paramInt++)] & 0xFF;
/* 121 */       i <<= 8;
/* 122 */       i |= this.data[(paramInt++)] & 0xFF;
/* 123 */       return (short)i;
/*     */     }
/*     */ 
/*     */     short getShort() {
/* 127 */       int i = this.data[(this.pos++)] & 0xFF;
/* 128 */       i <<= 8;
/* 129 */       i |= this.data[(this.pos++)] & 0xFF;
/* 130 */       return (short)i;
/*     */     }
/*     */ 
/*     */     char getChar(int paramInt) {
/* 134 */       paramInt += this.orig;
/* 135 */       int i = this.data[(paramInt++)] & 0xFF;
/* 136 */       i <<= 8;
/* 137 */       i |= this.data[(paramInt++)] & 0xFF;
/* 138 */       return (char)i;
/*     */     }
/*     */ 
/*     */     char getChar() {
/* 142 */       int i = this.data[(this.pos++)] & 0xFF;
/* 143 */       i <<= 8;
/* 144 */       i |= this.data[(this.pos++)] & 0xFF;
/* 145 */       return (char)i;
/*     */     }
/*     */ 
/*     */     void position(int paramInt) {
/* 149 */       this.pos = (this.orig + paramInt);
/*     */     }
/*     */ 
/*     */     int capacity() {
/* 153 */       return this.data.length - this.orig;
/*     */     }
/*     */ 
/*     */     byte get() {
/* 157 */       return this.data[(this.pos++)];
/*     */     }
/*     */ 
/*     */     byte get(int paramInt) {
/* 161 */       paramInt += this.orig;
/* 162 */       return this.data[paramInt];
/*     */     }
/*     */ 
/*     */     void skip(int paramInt) {
/* 166 */       this.pos += paramInt;
/*     */     }
/*     */ 
/*     */     void get(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3) {
/* 170 */       System.arraycopy(this.data, this.orig + paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.FontFileReader
 * JD-Core Version:    0.6.2
 */