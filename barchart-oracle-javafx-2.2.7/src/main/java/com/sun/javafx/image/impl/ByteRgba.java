/*     */ package com.sun.javafx.image.impl;
/*     */ 
/*     */ import com.sun.javafx.image.AlphaType;
/*     */ import com.sun.javafx.image.BytePixelAccessor;
/*     */ import com.sun.javafx.image.BytePixelGetter;
/*     */ import com.sun.javafx.image.BytePixelSetter;
/*     */ import com.sun.javafx.image.ByteToBytePixelConverter;
/*     */ import com.sun.javafx.image.ByteToIntPixelConverter;
/*     */ import com.sun.javafx.image.PixelUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public class ByteRgba
/*     */ {
/*  39 */   public static final BytePixelGetter getter = Accessor.instance;
/*  40 */   public static final BytePixelSetter setter = Accessor.instance;
/*  41 */   public static final BytePixelAccessor accessor = Accessor.instance;
/*     */ 
/*  43 */   public static final ByteToBytePixelConverter ToByteRgbaConverter = BaseByteToByteConverter.create(accessor);
/*     */ 
/*  45 */   public static final ByteToBytePixelConverter ToByteBgraConverter = BaseByteToByteConverter.createReorderer(getter, ByteBgra.setter, 2, 1, 0, 3);
/*     */ 
/*     */   static class ToIntArgbPreConv extends BaseByteToIntConverter
/*     */   {
/* 244 */     public static final ByteToIntPixelConverter instance = new ToIntArgbPreConv();
/*     */ 
/*     */     private ToIntArgbPreConv()
/*     */     {
/* 248 */       super(IntArgbPre.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 256 */       paramInt2 -= paramInt5 * 4;
/* 257 */       paramInt4 -= paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 259 */         for (int i = 0; i < paramInt5; i++) {
/* 260 */           int j = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 261 */           int k = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 262 */           int m = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 263 */           int n = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 264 */           if (n < 255) {
/* 265 */             if (n == 0) {
/* 266 */               j = k = m = 0;
/*     */             } else {
/* 268 */               j = (j * n + 127) / 255;
/* 269 */               k = (k * n + 127) / 255;
/* 270 */               m = (m * n + 127) / 255;
/*     */             }
/*     */           }
/* 273 */           paramArrayOfInt[(paramInt3++)] = (n << 24 | m << 16 | k << 8 | j);
/*     */         }
/*     */ 
/* 276 */         paramInt3 += paramInt4;
/* 277 */         paramInt1 += paramInt2;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 286 */       paramInt2 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 288 */         for (int i = 0; i < paramInt5; i++) {
/* 289 */           int j = paramByteBuffer.get(paramInt1) & 0xFF;
/* 290 */           int k = paramByteBuffer.get(paramInt1 + 1) & 0xFF;
/* 291 */           int m = paramByteBuffer.get(paramInt1 + 2) & 0xFF;
/* 292 */           int n = paramByteBuffer.get(paramInt1 + 3) & 0xFF;
/* 293 */           paramInt1 += 4;
/* 294 */           if (n < 255) {
/* 295 */             if (n == 0) {
/* 296 */               j = k = m = 0;
/*     */             } else {
/* 298 */               j = (j * n + 127) / 255;
/* 299 */               k = (k * n + 127) / 255;
/* 300 */               m = (m * n + 127) / 255;
/*     */             }
/*     */           }
/* 303 */           paramIntBuffer.put(paramInt3 + i, n << 24 | m << 16 | k << 8 | j);
/*     */         }
/* 305 */         paramInt3 += paramInt4;
/* 306 */         paramInt1 += paramInt2;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ToIntArgbSameConv extends BaseByteToIntConverter
/*     */   {
/* 194 */     static final ByteToIntPixelConverter nonpremul = new ToIntArgbSameConv(false);
/* 195 */     static final ByteToIntPixelConverter premul = new ToIntArgbSameConv(true);
/*     */ 
/*     */     private ToIntArgbSameConv(boolean paramBoolean) {
/* 198 */       super(paramBoolean ? IntArgbPre.setter : IntArgb.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 207 */       paramInt2 -= paramInt5 * 4;
/* 208 */       paramInt4 -= paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 210 */         for (int i = 0; i < paramInt5; i++) {
/* 211 */           paramArrayOfInt[(paramInt3++)] = (paramArrayOfByte[(paramInt1++)] & 0xFF | (paramArrayOfByte[(paramInt1++)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt1++)] & 0xFF) << 16 | paramArrayOfByte[(paramInt1++)] << 24);
/*     */         }
/*     */ 
/* 217 */         paramInt1 += paramInt2;
/* 218 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 227 */       paramInt2 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 229 */         for (int i = 0; i < paramInt5; i++) {
/* 230 */           paramIntBuffer.put(paramInt3 + i, paramByteBuffer.get(paramInt1) & 0xFF | (paramByteBuffer.get(paramInt1 + 1) & 0xFF) << 8 | (paramByteBuffer.get(paramInt1 + 2) & 0xFF) << 16 | paramByteBuffer.get(paramInt1 + 3) << 24);
/*     */ 
/* 235 */           paramInt1 += 4;
/*     */         }
/* 237 */         paramInt1 += paramInt2;
/* 238 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ToByteBgraPreConv extends BaseByteToByteConverter
/*     */   {
/* 119 */     static final ByteToBytePixelConverter instance = new ToByteBgraPreConv();
/*     */ 
/*     */     private ToByteBgraPreConv()
/*     */     {
/* 123 */       super(ByteBgraPre.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 131 */       paramInt2 -= paramInt5 * 4;
/* 132 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 134 */         for (int i = 0; i < paramInt5; i++) {
/* 135 */           int j = paramArrayOfByte1[(paramInt1++)];
/* 136 */           int k = paramArrayOfByte1[(paramInt1++)];
/* 137 */           int m = paramArrayOfByte1[(paramInt1++)];
/* 138 */           int n = paramArrayOfByte1[(paramInt1++)] & 0xFF;
/* 139 */           if (n < 255) {
/* 140 */             if (n == 0) {
/* 141 */               m = k = j = 0;
/*     */             } else {
/* 143 */               m = (byte)(((m & 0xFF) * n + 127) / 255);
/* 144 */               k = (byte)(((k & 0xFF) * n + 127) / 255);
/* 145 */               j = (byte)(((j & 0xFF) * n + 127) / 255);
/*     */             }
/*     */           }
/* 148 */           paramArrayOfByte2[(paramInt3++)] = m;
/* 149 */           paramArrayOfByte2[(paramInt3++)] = k;
/* 150 */           paramArrayOfByte2[(paramInt3++)] = j;
/* 151 */           paramArrayOfByte2[(paramInt3++)] = ((byte)n);
/*     */         }
/* 153 */         paramInt1 += paramInt2;
/* 154 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 163 */       paramInt2 -= paramInt5 * 4;
/* 164 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 166 */         for (int i = 0; i < paramInt5; i++) {
/* 167 */           int j = paramByteBuffer1.get(paramInt1);
/* 168 */           int k = paramByteBuffer1.get(paramInt1 + 1);
/* 169 */           int m = paramByteBuffer1.get(paramInt1 + 2);
/* 170 */           int n = paramByteBuffer1.get(paramInt1 + 3) & 0xFF;
/* 171 */           paramInt1 += 4;
/* 172 */           if (n < 255) {
/* 173 */             if (n == 0) {
/* 174 */               m = k = j = 0;
/*     */             } else {
/* 176 */               m = (byte)(((m & 0xFF) * n + 127) / 255);
/* 177 */               k = (byte)(((k & 0xFF) * n + 127) / 255);
/* 178 */               j = (byte)(((j & 0xFF) * n + 127) / 255);
/*     */             }
/*     */           }
/* 181 */           paramByteBuffer2.put(paramInt3, m);
/* 182 */           paramByteBuffer2.put(paramInt3 + 1, k);
/* 183 */           paramByteBuffer2.put(paramInt3 + 2, j);
/* 184 */           paramByteBuffer2.put(paramInt3 + 3, (byte)n);
/* 185 */           paramInt3 += 4;
/*     */         }
/* 187 */         paramInt1 += paramInt2;
/* 188 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Accessor
/*     */     implements BytePixelAccessor
/*     */   {
/*  52 */     static final BytePixelAccessor instance = new Accessor();
/*     */ 
/*     */     public AlphaType getAlphaType()
/*     */     {
/*  57 */       return AlphaType.NONPREMULTIPLIED;
/*     */     }
/*     */ 
/*     */     public int getNumElements()
/*     */     {
/*  62 */       return 4;
/*     */     }
/*     */ 
/*     */     public int getArgb(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  67 */       return paramArrayOfByte[(paramInt + 2)] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[paramInt] & 0xFF) << 16 | paramArrayOfByte[(paramInt + 3)] << 24;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  75 */       return PixelUtils.NonPretoPre(getArgb(paramArrayOfByte, paramInt));
/*     */     }
/*     */ 
/*     */     public int getArgb(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  80 */       return paramByteBuffer.get(paramInt + 2) & 0xFF | (paramByteBuffer.get(paramInt + 1) & 0xFF) << 8 | (paramByteBuffer.get(paramInt) & 0xFF) << 16 | paramByteBuffer.get(paramInt + 3) << 24;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  88 */       return PixelUtils.NonPretoPre(getArgb(paramByteBuffer, paramInt));
/*     */     }
/*     */ 
/*     */     public void setArgb(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     {
/*  93 */       paramArrayOfByte[paramInt1] = ((byte)(paramInt2 >> 16));
/*  94 */       paramArrayOfByte[(paramInt1 + 1)] = ((byte)(paramInt2 >> 8));
/*  95 */       paramArrayOfByte[(paramInt1 + 2)] = ((byte)paramInt2);
/*  96 */       paramArrayOfByte[(paramInt1 + 3)] = ((byte)(paramInt2 >> 24));
/*     */     }
/*     */ 
/*     */     public void setArgbPre(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     {
/* 101 */       setArgb(paramArrayOfByte, paramInt1, PixelUtils.PretoNonPre(paramInt2));
/*     */     }
/*     */ 
/*     */     public void setArgb(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/*     */     {
/* 106 */       paramByteBuffer.put(paramInt1, (byte)(paramInt2 >> 16));
/* 107 */       paramByteBuffer.put(paramInt1 + 1, (byte)(paramInt2 >> 8));
/* 108 */       paramByteBuffer.put(paramInt1 + 2, (byte)paramInt2);
/* 109 */       paramByteBuffer.put(paramInt1 + 3, (byte)(paramInt2 >> 24));
/*     */     }
/*     */ 
/*     */     public void setArgbPre(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/*     */     {
/* 114 */       setArgb(paramByteBuffer, paramInt1, PixelUtils.PretoNonPre(paramInt2));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.ByteRgba
 * JD-Core Version:    0.6.2
 */