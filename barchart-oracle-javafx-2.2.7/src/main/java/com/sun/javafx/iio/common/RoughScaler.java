/*     */ package com.sun.javafx.iio.common;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public class RoughScaler
/*     */   implements PushbroomScaler
/*     */ {
/*     */   protected int numBands;
/*     */   protected int destWidth;
/*     */   protected int destHeight;
/*     */   protected double scaleY;
/*     */   protected ByteBuffer destBuf;
/*     */   protected int[] colPositions;
/*     */   protected int sourceLine;
/*     */   protected int nextSourceLine;
/*     */   protected int destLine;
/*     */ 
/*     */   public RoughScaler(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  40 */     if ((paramInt1 <= 0) || (paramInt2 <= 0) || (paramInt3 <= 0) || (paramInt4 <= 0) || (paramInt5 <= 0))
/*     */     {
/*  42 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*  46 */     this.numBands = paramInt3;
/*  47 */     this.destWidth = paramInt4;
/*  48 */     this.destHeight = paramInt5;
/*     */ 
/*  51 */     this.destBuf = ByteBuffer.wrap(new byte[paramInt5 * paramInt4 * paramInt3]);
/*     */ 
/*  54 */     double d = paramInt1 / paramInt4;
/*  55 */     this.scaleY = (paramInt2 / paramInt5);
/*     */ 
/*  57 */     this.colPositions = new int[paramInt4];
/*  58 */     int i = paramInt1 - 1;
/*  59 */     for (int j = 0; j < paramInt4; j++) {
/*  60 */       int k = (int)(j * d);
/*  61 */       if (k < 0)
/*  62 */         k = 0;
/*  63 */       else if (k > i) {
/*  64 */         k = i;
/*     */       }
/*  66 */       this.colPositions[j] = k;
/*     */     }
/*     */ 
/*  70 */     this.sourceLine = 0;
/*  71 */     this.destLine = 0;
/*  72 */     this.nextSourceLine = 0;
/*     */   }
/*     */ 
/*     */   public ByteBuffer getDestination()
/*     */   {
/*  81 */     return this.destBuf;
/*     */   }
/*     */ 
/*     */   public boolean putSourceScanline(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/*  94 */     if (paramInt < 0) {
/*  95 */       throw new IllegalArgumentException("off < 0!");
/*     */     }
/*     */ 
/*  98 */     if (this.destLine < this.destHeight) {
/*  99 */       if (this.sourceLine == this.nextSourceLine) {
/* 100 */         assert (this.destBuf.hasArray()) : "destBuf.hasArray() == false => destBuf is direct";
/* 101 */         byte[] arrayOfByte = this.destBuf.array();
/*     */ 
/* 103 */         int i = this.destLine * this.destWidth * this.numBands;
/*     */ 
/* 105 */         int j = i;
/* 106 */         for (int k = 0; k < this.destWidth; k++) {
/* 107 */           int m = paramInt + this.colPositions[k] * this.numBands;
/* 108 */           for (int n = 0; n < this.numBands; n++) {
/* 109 */             arrayOfByte[(j++)] = paramArrayOfByte[(m + n)];
/*     */           }
/*     */         }
/*     */ 
/* 113 */         while ((int)(++this.destLine * this.scaleY) == this.sourceLine) {
/* 114 */           System.arraycopy(arrayOfByte, i, arrayOfByte, j, this.destWidth * this.numBands);
/* 115 */           j += this.destWidth * this.numBands;
/*     */         }
/*     */ 
/* 118 */         this.nextSourceLine = ((int)(this.destLine * this.scaleY));
/*     */       }
/*     */ 
/* 121 */       this.sourceLine += 1;
/*     */     }
/*     */ 
/* 124 */     return this.destLine == this.destHeight;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.common.RoughScaler
 * JD-Core Version:    0.6.2
 */