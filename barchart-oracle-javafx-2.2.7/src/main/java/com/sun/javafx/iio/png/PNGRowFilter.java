/*     */ package com.sun.javafx.iio.png;
/*     */ 
/*     */ public class PNGRowFilter
/*     */ {
/*     */   private static final int abs(int paramInt)
/*     */   {
/*  18 */     return paramInt < 0 ? -paramInt : paramInt;
/*     */   }
/*     */ 
/*     */   protected static int subFilter(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
/*     */   {
/*  26 */     int i = 0;
/*  27 */     for (int j = paramInt1; j < paramInt2 + paramInt1; j++) {
/*  28 */       int k = paramArrayOfByte1[j] & 0xFF;
/*  29 */       int m = paramArrayOfByte1[(j - paramInt1)] & 0xFF;
/*  30 */       int n = k - m;
/*  31 */       paramArrayOfByte2[j] = ((byte)n);
/*     */ 
/*  33 */       i += abs(n);
/*     */     }
/*     */ 
/*  36 */     return i;
/*     */   }
/*     */ 
/*     */   protected static int upFilter(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt1, int paramInt2)
/*     */   {
/*  45 */     int i = 0;
/*  46 */     for (int j = paramInt1; j < paramInt2 + paramInt1; j++) {
/*  47 */       int k = paramArrayOfByte1[j] & 0xFF;
/*  48 */       int m = paramArrayOfByte2[j] & 0xFF;
/*  49 */       int n = k - m;
/*  50 */       paramArrayOfByte3[j] = ((byte)n);
/*     */ 
/*  52 */       i += abs(n);
/*     */     }
/*     */ 
/*  55 */     return i;
/*     */   }
/*     */ 
/*     */   protected final int paethPredictor(int paramInt1, int paramInt2, int paramInt3) {
/*  59 */     int i = paramInt1 + paramInt2 - paramInt3;
/*  60 */     int j = abs(i - paramInt1);
/*  61 */     int k = abs(i - paramInt2);
/*  62 */     int m = abs(i - paramInt3);
/*     */ 
/*  64 */     if ((j <= k) && (j <= m))
/*  65 */       return paramInt1;
/*  66 */     if (k <= m) {
/*  67 */       return paramInt2;
/*     */     }
/*  69 */     return paramInt3;
/*     */   }
/*     */ 
/*     */   public int filterRow(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[][] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/*  81 */     if (paramInt1 != 3) {
/*  82 */       System.arraycopy(paramArrayOfByte1, paramInt3, paramArrayOfByte[0], paramInt3, paramInt2);
/*     */ 
/*  85 */       return 0;
/*     */     }
/*     */ 
/*  88 */     int[] arrayOfInt = new int[5];
/*  89 */     for (int i = 0; i < 5; i++) {
/*  90 */       arrayOfInt[i] = 2147483647;
/*     */     }
/*     */ 
/*  94 */     i = 0;
/*     */ 
/*  96 */     for (int k = paramInt3; k < paramInt2 + paramInt3; k++) {
/*  97 */       m = paramArrayOfByte1[k] & 0xFF;
/*  98 */       i += m;
/*     */     }
/*     */ 
/* 101 */     arrayOfInt[0] = i;
/*     */ 
/* 105 */     byte[] arrayOfByte = paramArrayOfByte[1];
/* 106 */     k = subFilter(paramArrayOfByte1, arrayOfByte, paramInt3, paramInt2);
/*     */ 
/* 111 */     arrayOfInt[1] = k;
/*     */ 
/* 115 */     arrayOfByte = paramArrayOfByte[2];
/* 116 */     k = upFilter(paramArrayOfByte1, paramArrayOfByte2, arrayOfByte, paramInt3, paramInt2);
/*     */ 
/* 122 */     arrayOfInt[2] = k;
/*     */ 
/* 126 */     arrayOfByte = paramArrayOfByte[3];
/* 127 */     k = 0;
/*     */     int n;
/*     */     int i1;
/*     */     int i2;
/*     */     int i3;
/* 129 */     for (int m = paramInt3; m < paramInt2 + paramInt3; m++) {
/* 130 */       n = paramArrayOfByte1[m] & 0xFF;
/* 131 */       i1 = paramArrayOfByte1[(m - paramInt3)] & 0xFF;
/* 132 */       i2 = paramArrayOfByte2[m] & 0xFF;
/* 133 */       i3 = n - (i1 + i2) / 2;
/* 134 */       arrayOfByte[m] = ((byte)i3);
/*     */ 
/* 136 */       k += abs(i3);
/*     */     }
/*     */ 
/* 139 */     arrayOfInt[3] = k;
/*     */ 
/* 143 */     arrayOfByte = paramArrayOfByte[4];
/* 144 */     k = 0;
/*     */ 
/* 146 */     for (m = paramInt3; m < paramInt2 + paramInt3; m++) {
/* 147 */       n = paramArrayOfByte1[m] & 0xFF;
/* 148 */       i1 = paramArrayOfByte1[(m - paramInt3)] & 0xFF;
/* 149 */       i2 = paramArrayOfByte2[m] & 0xFF;
/* 150 */       i3 = paramArrayOfByte2[(m - paramInt3)] & 0xFF;
/* 151 */       int i4 = paethPredictor(i1, i2, i3);
/* 152 */       int i5 = n - i4;
/* 153 */       arrayOfByte[m] = ((byte)i5);
/*     */ 
/* 155 */       k += abs(i5);
/*     */     }
/*     */ 
/* 158 */     arrayOfInt[4] = k;
/*     */ 
/* 161 */     int j = arrayOfInt[0];
/* 162 */     k = 0;
/*     */ 
/* 164 */     for (m = 1; m < 5; m++) {
/* 165 */       if (arrayOfInt[m] < j) {
/* 166 */         j = arrayOfInt[m];
/* 167 */         k = m;
/*     */       }
/*     */     }
/*     */ 
/* 171 */     if (k == 0) {
/* 172 */       System.arraycopy(paramArrayOfByte1, paramInt3, paramArrayOfByte[0], paramInt3, paramInt2);
/*     */     }
/*     */ 
/* 177 */     return k;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.png.PNGRowFilter
 * JD-Core Version:    0.6.2
 */