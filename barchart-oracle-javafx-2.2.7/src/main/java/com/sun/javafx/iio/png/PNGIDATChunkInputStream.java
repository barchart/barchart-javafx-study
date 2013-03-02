/*     */ package com.sun.javafx.iio.png;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class PNGIDATChunkInputStream extends InputStream
/*     */ {
/*     */   static final int IDAT_TYPE = 1229209940;
/*     */   private InputStream source;
/*  35 */   private int numBytesAvailable = 0;
/*  36 */   private boolean foundAllIDATChunks = false;
/*  37 */   private int nextChunkLength = 0;
/*  38 */   private int nextChunkType = 0;
/*     */ 
/*     */   PNGIDATChunkInputStream(InputStream paramInputStream, int paramInt)
/*     */   {
/*  49 */     this.source = paramInputStream;
/*  50 */     this.numBytesAvailable = paramInt;
/*     */   }
/*     */ 
/*     */   private void nextChunk() throws IOException {
/*  54 */     if (!this.foundAllIDATChunks) {
/*  55 */       if (4L != this.source.skip(4L)) {
/*  56 */         throw new EOFException();
/*     */       }
/*  58 */       int i = this.source.read() << 24 | this.source.read() << 16 | this.source.read() << 8 | this.source.read();
/*     */ 
/*  60 */       int j = this.source.read() << 24 | this.source.read() << 16 | this.source.read() << 8 | this.source.read();
/*     */ 
/*  62 */       if (j == 1229209940) {
/*  63 */         this.numBytesAvailable += i;
/*     */       } else {
/*  65 */         this.foundAllIDATChunks = true;
/*  66 */         this.nextChunkLength = i;
/*  67 */         this.nextChunkType = j;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean isFoundAllIDATChunks() {
/*  73 */     return this.foundAllIDATChunks;
/*     */   }
/*     */ 
/*     */   int getNextChunkLength() {
/*  77 */     return this.nextChunkLength;
/*     */   }
/*     */ 
/*     */   int getNextChunkType() {
/*  81 */     return this.nextChunkType;
/*     */   }
/*     */ 
/*     */   public int read() throws IOException
/*     */   {
/*  86 */     if (this.numBytesAvailable == 0) {
/*  87 */       nextChunk();
/*     */     }
/*     */ 
/*  90 */     if (this.numBytesAvailable == 0) {
/*  91 */       return -1;
/*     */     }
/*  93 */     this.numBytesAvailable -= 1;
/*  94 */     return this.source.read();
/*     */   }
/*     */ 
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 100 */     if (this.numBytesAvailable == 0) {
/* 101 */       nextChunk();
/* 102 */       if (this.numBytesAvailable == 0) {
/* 103 */         return -1;
/*     */       }
/*     */     }
/*     */ 
/* 107 */     int i = 0;
/* 108 */     while ((this.numBytesAvailable > 0) && (paramInt2 > 0)) {
/* 109 */       int j = paramInt2 < this.numBytesAvailable ? paramInt2 : this.numBytesAvailable;
/*     */ 
/* 111 */       int k = this.source.read(paramArrayOfByte, paramInt1, j);
/* 112 */       if (k == -1) {
/* 113 */         throw new EOFException();
/*     */       }
/*     */ 
/* 116 */       this.numBytesAvailable -= k;
/* 117 */       paramInt1 += k;
/* 118 */       paramInt2 -= k;
/* 119 */       i += k;
/* 120 */       if ((this.numBytesAvailable == 0) && (paramInt2 > 0)) {
/* 121 */         nextChunk();
/*     */       }
/*     */     }
/*     */ 
/* 125 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.png.PNGIDATChunkInputStream
 * JD-Core Version:    0.6.2
 */