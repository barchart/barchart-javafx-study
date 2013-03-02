/*     */ package com.sun.t2k;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ 
/*     */ public class FontFileWriter
/*     */   implements FontConstants
/*     */ {
/*     */   byte[] header;
/*     */   int pos;
/*     */   int headerPos;
/*     */   int writtenBytes;
/*     */   FontTracker tracker;
/*     */   File file;
/*     */   RandomAccessFile raFile;
/*     */ 
/*     */   public FontFileWriter()
/*     */   {
/*  30 */     if (!hasTempPermission())
/*  31 */       this.tracker = FontTracker.getTracker();
/*     */   }
/*     */ 
/*     */   protected void setLength(int paramInt) throws IOException
/*     */   {
/*  36 */     if (this.raFile == null) {
/*  37 */       throw new IOException("File not open");
/*     */     }
/*  39 */     checkTracker(paramInt);
/*  40 */     this.raFile.setLength(paramInt);
/*     */   }
/*     */ 
/*     */   public void seek(int paramInt) throws IOException {
/*  44 */     if (this.raFile == null) {
/*  45 */       throw new IOException("File not open");
/*     */     }
/*  47 */     if (paramInt != this.pos) {
/*  48 */       this.raFile.seek(paramInt);
/*  49 */       this.pos = paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */   public File getFile() {
/*  54 */     return this.file;
/*     */   }
/*     */ 
/*     */   public File openFile() throws PrivilegedActionException {
/*  58 */     this.pos = 0;
/*  59 */     this.writtenBytes = 0;
/*  60 */     this.file = ((File)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */     {
/*     */       public File run() throws IOException {
/*  63 */         return File.createTempFile("+JXF", ".tmp", null);
/*     */       }
/*     */     }));
/*  67 */     this.raFile = ((RandomAccessFile)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */     {
/*     */       public RandomAccessFile run() throws IOException {
/*  70 */         return new RandomAccessFile(FontFileWriter.this.file, "rw");
/*     */       }
/*     */     }));
/*  74 */     if (T2KFontFactory.debugFonts) {
/*  75 */       System.err.println("Temp file created: " + this.file.getPath());
/*     */     }
/*  77 */     return this.file;
/*     */   }
/*     */ 
/*     */   public void closeFile() throws IOException {
/*  81 */     if (this.header != null) {
/*  82 */       this.raFile.seek(0L);
/*  83 */       this.raFile.write(this.header);
/*  84 */       this.header = null;
/*     */     }
/*  86 */     if (this.raFile != null) {
/*  87 */       this.raFile.close();
/*  88 */       this.raFile = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteFile() {
/*  93 */     if (this.file != null) {
/*  94 */       if (this.tracker != null)
/*  95 */         this.tracker.subBytes(this.writtenBytes);
/*     */       try
/*     */       {
/*  98 */         closeFile();
/*     */       } catch (Exception localException1) {
/*     */       }
/*     */       try {
/* 102 */         AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */         {
/*     */           public Void run() {
/* 105 */             FontFileWriter.this.file.delete();
/* 106 */             return null;
/*     */           }
/*     */         });
/* 110 */         if (T2KFontFactory.debugFonts)
/* 111 */           System.err.println("Temp file delete: " + this.file.getPath());
/*     */       }
/*     */       catch (Exception localException2) {
/*     */       }
/* 115 */       this.file = null;
/* 116 */       this.raFile = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isTracking() {
/* 121 */     return this.tracker != null;
/*     */   }
/*     */ 
/*     */   private void checkTracker(int paramInt) throws IOException {
/* 125 */     if (this.tracker != null) {
/* 126 */       if (this.pos + paramInt > 33554432) {
/* 127 */         throw new IOException("File too big.");
/*     */       }
/* 129 */       if (this.pos + this.tracker.getNumBytes() > 335544320)
/* 130 */         throw new IOException("Total files too big.");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkSize(int paramInt) throws IOException
/*     */   {
/* 136 */     if (this.tracker != null) {
/* 137 */       checkTracker(paramInt);
/* 138 */       this.tracker.addBytes(paramInt);
/* 139 */       this.writtenBytes += paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setHeaderPos(int paramInt) {
/* 144 */     this.headerPos = paramInt;
/*     */   }
/*     */ 
/*     */   public void writeHeader(int paramInt, short paramShort)
/*     */     throws IOException
/*     */   {
/* 151 */     int i = 12 + 16 * paramShort;
/* 152 */     checkSize(i);
/* 153 */     this.header = new byte[i];
/*     */ 
/* 160 */     int j = paramShort;
/* 161 */     j = (short)(j | j >> 1);
/* 162 */     j = (short)(j | j >> 2);
/* 163 */     j = (short)(j | j >> 4);
/* 164 */     j = (short)(j | j >> 8);
/*     */ 
/* 167 */     j = (short)(j & (j >> 1 ^ 0xFFFFFFFF));
/* 168 */     int k = (short)(j * 16);
/* 169 */     short s1 = 0;
/* 170 */     while (j > 1) {
/* 171 */       s1 = (short)(s1 + 1);
/* 172 */       j = (short)(j >> 1);
/*     */     }
/* 174 */     short s2 = (short)(paramShort * 16 - k);
/*     */ 
/* 176 */     setHeaderPos(0);
/* 177 */     writeInt(paramInt);
/* 178 */     writeShort(paramShort);
/* 179 */     writeShort(k);
/* 180 */     writeShort(s1);
/* 181 */     writeShort(s2);
/*     */   }
/*     */ 
/*     */   public void writeDirectoryEntry(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */     throws IOException
/*     */   {
/* 187 */     setHeaderPos(12 + 16 * paramInt1);
/* 188 */     writeInt(paramInt2);
/* 189 */     writeInt(paramInt3);
/* 190 */     writeInt(paramInt4);
/* 191 */     writeInt(paramInt5);
/*     */   }
/*     */ 
/*     */   private void writeInt(int paramInt) throws IOException {
/* 195 */     this.header[(this.headerPos++)] = ((byte)((paramInt & 0xFF000000) >> 24));
/* 196 */     this.header[(this.headerPos++)] = ((byte)((paramInt & 0xFF0000) >> 16));
/* 197 */     this.header[(this.headerPos++)] = ((byte)((paramInt & 0xFF00) >> 8));
/* 198 */     this.header[(this.headerPos++)] = ((byte)(paramInt & 0xFF));
/*     */   }
/*     */ 
/*     */   private void writeShort(short paramShort) throws IOException {
/* 202 */     this.header[(this.headerPos++)] = ((byte)((paramShort & 0xFF00) >> 8));
/* 203 */     this.header[(this.headerPos++)] = ((byte)(paramShort & 0xFF));
/*     */   }
/*     */ 
/*     */   public void writeBytes(byte[] paramArrayOfByte) throws IOException {
/* 207 */     writeBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */ 
/*     */   public void writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 213 */     checkSize(paramInt2);
/* 214 */     this.raFile.write(paramArrayOfByte, paramInt1, paramInt2);
/* 215 */     this.pos += paramInt2;
/*     */   }
/*     */ 
/*     */   private static boolean hasTempPermission()
/*     */   {
/* 224 */     if (System.getSecurityManager() == null) {
/* 225 */       return true;
/*     */     }
/* 227 */     File localFile = null;
/* 228 */     boolean bool = false;
/*     */     try {
/* 230 */       localFile = File.createTempFile("+JXF", ".tmp", null);
/* 231 */       localFile.delete();
/* 232 */       localFile = null;
/* 233 */       bool = true;
/*     */     }
/*     */     catch (Throwable localThrowable) {
/*     */     }
/* 237 */     return bool;
/*     */   }
/*     */ 
/*     */   static class FontTracker
/*     */   {
/*     */     public static final int MAX_FILE_SIZE = 33554432;
/*     */     public static final int MAX_TOTAL_BYTES = 335544320;
/*     */     static int numBytes;
/*     */     static FontTracker tracker;
/*     */ 
/*     */     public static synchronized FontTracker getTracker()
/*     */     {
/* 252 */       if (tracker == null) {
/* 253 */         tracker = new FontTracker();
/*     */       }
/* 255 */       return tracker;
/*     */     }
/*     */ 
/*     */     public synchronized int getNumBytes() {
/* 259 */       return numBytes;
/*     */     }
/*     */ 
/*     */     public synchronized void addBytes(int paramInt) {
/* 263 */       numBytes += paramInt;
/*     */     }
/*     */ 
/*     */     public synchronized void subBytes(int paramInt) {
/* 267 */       numBytes -= paramInt;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.FontFileWriter
 * JD-Core Version:    0.6.2
 */