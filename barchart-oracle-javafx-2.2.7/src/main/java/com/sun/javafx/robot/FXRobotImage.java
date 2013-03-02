/*     */ package com.sun.javafx.robot;
/*     */ 
/*     */ import java.nio.Buffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public class FXRobotImage
/*     */ {
/*     */   private final IntBuffer pixelBuffer;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final int scanlineStride;
/*     */ 
/*     */   public static FXRobotImage create(Buffer paramBuffer, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  62 */     return new FXRobotImage(paramBuffer, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   private FXRobotImage(Buffer paramBuffer, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  68 */     if (paramBuffer == null) {
/*  69 */       throw new IllegalArgumentException("Pixel buffer must be non-null");
/*     */     }
/*  71 */     if ((paramInt1 <= 0) || (paramInt2 <= 0)) {
/*  72 */       throw new IllegalArgumentException("Image dimensions must be > 0");
/*     */     }
/*  74 */     this.pixelBuffer = ((IntBuffer)paramBuffer);
/*  75 */     this.width = paramInt1;
/*  76 */     this.height = paramInt2;
/*  77 */     this.scanlineStride = paramInt3;
/*     */   }
/*     */ 
/*     */   public Buffer getPixelBuffer()
/*     */   {
/*  86 */     return this.pixelBuffer;
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/*  94 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 102 */     return this.height;
/*     */   }
/*     */ 
/*     */   public int getScanlineStride()
/*     */   {
/* 111 */     return this.scanlineStride;
/*     */   }
/*     */ 
/*     */   public int getPixelStride()
/*     */   {
/* 120 */     return 4;
/*     */   }
/*     */ 
/*     */   public int getArgbPre(int paramInt1, int paramInt2)
/*     */   {
/* 131 */     if ((paramInt1 < 0) || (paramInt1 >= this.width) || (paramInt2 < 0) || (paramInt2 >= this.height)) {
/* 132 */       throw new IllegalArgumentException("x,y must be >0, <width, height");
/*     */     }
/* 134 */     return this.pixelBuffer.get(paramInt1 + paramInt2 * this.scanlineStride / 4);
/*     */   }
/*     */ 
/*     */   public int getArgb(int paramInt1, int paramInt2)
/*     */   {
/* 145 */     if ((paramInt1 < 0) || (paramInt1 >= this.width) || (paramInt2 < 0) || (paramInt2 >= this.height)) {
/* 146 */       throw new IllegalArgumentException("x,y must be >0, <width, height");
/*     */     }
/* 148 */     int i = this.pixelBuffer.get(paramInt1 + paramInt2 * this.scanlineStride / 4);
/* 149 */     if (i >> 24 == -1) {
/* 150 */       return i;
/*     */     }
/* 152 */     int j = i >>> 24;
/* 153 */     int k = i >> 16 & 0xFF;
/* 154 */     int m = i >> 8 & 0xFF;
/* 155 */     int n = i & 0xFF;
/* 156 */     int i1 = j + (j >> 7);
/* 157 */     k = k * i1 >> 8;
/* 158 */     m = m * i1 >> 8;
/* 159 */     n = n * i1 >> 8;
/* 160 */     return j << 24 | k << 16 | m << 8 | n;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 165 */     return super.toString() + " [format=INT_ARGB_PRE width=" + this.width + " height=" + this.height + " scanlineStride=" + this.scanlineStride + " pixelStride=" + getPixelStride() + " pixelBuffer=" + this.pixelBuffer + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.robot.FXRobotImage
 * JD-Core Version:    0.6.2
 */