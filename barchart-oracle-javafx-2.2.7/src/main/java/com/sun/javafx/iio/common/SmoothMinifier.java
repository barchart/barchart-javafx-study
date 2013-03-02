/*     */ package com.sun.javafx.iio.common;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class SmoothMinifier
/*     */   implements PushbroomScaler
/*     */ {
/*     */   protected int sourceWidth;
/*     */   protected int sourceHeight;
/*     */   protected int numBands;
/*     */   protected int destWidth;
/*     */   protected int destHeight;
/*     */   protected double scaleY;
/*     */   protected ByteBuffer destBuf;
/*     */   protected int boxHeight;
/*     */   protected byte[][] sourceData;
/*     */   protected int[] leftPoints;
/*     */   protected int[] rightPoints;
/*     */   protected int[] topPoints;
/*     */   protected int[] bottomPoints;
/*     */   protected int sourceLine;
/*     */   protected int sourceDataLine;
/*     */   protected int destLine;
/*     */   protected int[] tmpBuf;
/*     */ 
/*     */   SmoothMinifier(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  73 */     if ((paramInt1 <= 0) || (paramInt2 <= 0) || (paramInt3 <= 0) || (paramInt4 <= 0) || (paramInt5 <= 0) || (paramInt4 > paramInt1) || (paramInt5 > paramInt2))
/*     */     {
/*  76 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*  80 */     this.sourceWidth = paramInt1;
/*  81 */     this.sourceHeight = paramInt2;
/*  82 */     this.numBands = paramInt3;
/*  83 */     this.destWidth = paramInt4;
/*  84 */     this.destHeight = paramInt5;
/*     */ 
/*  87 */     this.destBuf = ByteBuffer.wrap(new byte[paramInt5 * paramInt4 * paramInt3]);
/*     */ 
/*  90 */     double d = paramInt1 / paramInt4;
/*  91 */     this.scaleY = (paramInt2 / paramInt5);
/*     */ 
/*  94 */     int i = (paramInt1 + paramInt4 - 1) / paramInt4;
/*  95 */     this.boxHeight = ((paramInt2 + paramInt5 - 1) / paramInt5);
/*     */ 
/*  99 */     int j = i / 2;
/* 100 */     int k = i - j - 1;
/* 101 */     int m = this.boxHeight / 2;
/* 102 */     int n = this.boxHeight - m - 1;
/*     */ 
/* 105 */     this.sourceData = new byte[this.boxHeight][paramInt4 * paramInt3];
/*     */ 
/* 110 */     this.leftPoints = new int[paramInt4];
/* 111 */     this.rightPoints = new int[paramInt4];
/*     */     int i2;
/* 112 */     for (int i1 = 0; i1 < paramInt4; i1++) {
/* 113 */       i2 = (int)(i1 * d);
/* 114 */       this.leftPoints[i1] = (i2 - j);
/* 115 */       this.rightPoints[i1] = (i2 + k);
/*     */     }
/*     */ 
/* 121 */     this.topPoints = new int[paramInt5];
/* 122 */     this.bottomPoints = new int[paramInt5];
/* 123 */     for (i1 = 0; i1 < paramInt5; i1++) {
/* 124 */       i2 = (int)(i1 * this.scaleY);
/* 125 */       this.topPoints[i1] = (i2 - m);
/* 126 */       this.bottomPoints[i1] = (i2 + n);
/*     */     }
/*     */ 
/* 130 */     this.sourceLine = 0;
/* 131 */     this.sourceDataLine = 0;
/* 132 */     this.destLine = 0;
/*     */ 
/* 134 */     this.tmpBuf = new int[paramInt4 * paramInt3];
/*     */   }
/*     */ 
/*     */   public ByteBuffer getDestination()
/*     */   {
/* 143 */     return this.destBuf;
/*     */   }
/*     */ 
/*     */   public boolean putSourceScanline(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 156 */     if (paramInt < 0)
/* 157 */       throw new IllegalArgumentException("off < 0!");
/*     */     int i;
/*     */     int j;
/*     */     int k;
/*     */     int m;
/*     */     int n;
/*     */     int i1;
/*     */     int i2;
/* 167 */     if (this.numBands == 1) {
/* 168 */       i = paramArrayOfByte[paramInt] & 0xFF;
/* 169 */       j = paramArrayOfByte[(paramInt + this.sourceWidth - 1)] & 0xFF;
/* 170 */       for (k = 0; k < this.destWidth; k++) {
/* 171 */         m = 0;
/* 172 */         n = this.rightPoints[k];
/* 173 */         for (i1 = this.leftPoints[k]; i1 <= n; i1++) {
/* 174 */           if (i1 < 0)
/* 175 */             m += i;
/* 176 */           else if (i1 >= this.sourceWidth)
/* 177 */             m += j;
/*     */           else {
/* 179 */             m += (paramArrayOfByte[(paramInt + i1)] & 0xFF);
/*     */           }
/*     */         }
/* 182 */         m /= (n - this.leftPoints[k] + 1);
/* 183 */         this.sourceData[this.sourceDataLine][k] = ((byte)m);
/*     */       }
/*     */     } else {
/* 186 */       i = paramInt + (this.sourceWidth - 1) * this.numBands;
/* 187 */       for (j = 0; j < this.destWidth; j++) {
/* 188 */         k = this.leftPoints[j];
/* 189 */         m = this.rightPoints[j];
/* 190 */         n = m - k + 1;
/* 191 */         i1 = j * this.numBands;
/* 192 */         for (i2 = 0; i2 < this.numBands; i2++)
/*     */         {
/* 197 */           int i3 = paramArrayOfByte[(paramInt + i2)] & 0xFF;
/* 198 */           int i4 = paramArrayOfByte[(i + i2)] & 0xFF;
/*     */ 
/* 200 */           int i5 = 0;
/* 201 */           for (int i6 = k; i6 <= m; i6++) {
/* 202 */             if (i6 < 0)
/* 203 */               i5 += i3;
/* 204 */             else if (i6 >= this.sourceWidth)
/* 205 */               i5 += i4;
/*     */             else {
/* 207 */               i5 += (paramArrayOfByte[(paramInt + i6 * this.numBands + i2)] & 0xFF);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 215 */           i5 /= n;
/* 216 */           this.sourceData[this.sourceDataLine][(i1 + i2)] = ((byte)i5);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 226 */     if ((this.sourceLine == this.bottomPoints[this.destLine]) || ((this.destLine == this.destHeight - 1) && (this.sourceLine == this.sourceHeight - 1)))
/*     */     {
/* 230 */       assert (this.destBuf.hasArray()) : "destBuf.hasArray() == false => destBuf is direct";
/* 231 */       byte[] arrayOfByte1 = this.destBuf.array();
/*     */ 
/* 233 */       j = this.destLine * this.destWidth * this.numBands;
/* 234 */       Arrays.fill(this.tmpBuf, 0);
/* 235 */       for (k = this.topPoints[this.destLine]; k <= this.bottomPoints[this.destLine]; k++) {
/* 236 */         m = 0;
/* 237 */         if (k < 0)
/* 238 */           m = 0 - this.sourceLine + this.sourceDataLine;
/* 239 */         else if (k >= this.sourceHeight)
/* 240 */           m = (this.sourceHeight - 1 - this.sourceLine + this.sourceDataLine) % this.boxHeight;
/*     */         else {
/* 242 */           m = (k - this.sourceLine + this.sourceDataLine) % this.boxHeight;
/*     */         }
/* 244 */         if (m < 0) {
/* 245 */           m += this.boxHeight;
/*     */         }
/* 247 */         byte[] arrayOfByte2 = this.sourceData[m];
/* 248 */         i1 = arrayOfByte2.length;
/* 249 */         for (i2 = 0; i2 < i1; i2++) {
/* 250 */           this.tmpBuf[i2] += (arrayOfByte2[i2] & 0xFF);
/*     */         }
/*     */       }
/* 253 */       k = this.tmpBuf.length;
/* 254 */       for (m = 0; m < k; m++) {
/* 255 */         arrayOfByte1[(j + m)] = ((byte)(this.tmpBuf[m] / this.boxHeight));
/*     */       }
/*     */ 
/* 258 */       if (this.destLine < this.destHeight - 1) {
/* 259 */         this.destLine += 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 264 */     if (++this.sourceLine != this.sourceHeight) {
/* 265 */       this.sourceDataLine = ((this.sourceDataLine + 1) % this.boxHeight);
/*     */     }
/*     */ 
/* 268 */     return this.destLine == this.destHeight;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.common.SmoothMinifier
 * JD-Core Version:    0.6.2
 */