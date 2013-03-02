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
/*     */ public class ByteBgra
/*     */ {
/*  39 */   public static final BytePixelGetter getter = Accessor.instance;
/*  40 */   public static final BytePixelSetter setter = Accessor.instance;
/*  41 */   public static final BytePixelAccessor accessor = Accessor.instance;
/*     */ 
/*  43 */   public static final ByteToBytePixelConverter ToByteBgraConverter = BaseByteToByteConverter.create(accessor);
/*     */ 
/*  45 */   public static final ByteToBytePixelConverter ToByteBgraPreConverter = ToByteBgraPreConv.instance;
/*     */ 
/*  47 */   public static final ByteToIntPixelConverter ToIntArgbConverter = ToIntArgbSameConv.nonpremul;
/*     */ 
/*  49 */   public static final ByteToIntPixelConverter ToIntArgbPreConverter = ToIntArgbPreConv.instance;
/*     */ 
/*     */   static class ToIntArgbPreConv extends BaseByteToIntConverter
/*     */   {
/* 245 */     public static final ByteToIntPixelConverter instance = new ToIntArgbPreConv();
/*     */ 
/*     */     private ToIntArgbPreConv()
/*     */     {
/* 249 */       super(IntArgbPre.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 257 */       paramInt2 -= paramInt5 * 4;
/* 258 */       paramInt4 -= paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 260 */         for (int i = 0; i < paramInt5; i++) {
/* 261 */           int j = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 262 */           int k = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 263 */           int m = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 264 */           int n = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 265 */           if (n < 255) {
/* 266 */             if (n == 0) {
/* 267 */               j = k = m = 0;
/*     */             } else {
/* 269 */               j = (j * n + 127) / 255;
/* 270 */               k = (k * n + 127) / 255;
/* 271 */               m = (m * n + 127) / 255;
/*     */             }
/*     */           }
/* 274 */           paramArrayOfInt[(paramInt3++)] = (n << 24 | m << 16 | k << 8 | j);
/*     */         }
/*     */ 
/* 277 */         paramInt3 += paramInt4;
/* 278 */         paramInt1 += paramInt2;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 287 */       paramInt2 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 289 */         for (int i = 0; i < paramInt5; i++) {
/* 290 */           int j = paramByteBuffer.get(paramInt1) & 0xFF;
/* 291 */           int k = paramByteBuffer.get(paramInt1 + 1) & 0xFF;
/* 292 */           int m = paramByteBuffer.get(paramInt1 + 2) & 0xFF;
/* 293 */           int n = paramByteBuffer.get(paramInt1 + 3) & 0xFF;
/* 294 */           paramInt1 += 4;
/* 295 */           if (n < 255) {
/* 296 */             if (n == 0) {
/* 297 */               j = k = m = 0;
/*     */             } else {
/* 299 */               j = (j * n + 127) / 255;
/* 300 */               k = (k * n + 127) / 255;
/* 301 */               m = (m * n + 127) / 255;
/*     */             }
/*     */           }
/* 304 */           paramIntBuffer.put(paramInt3 + i, n << 24 | m << 16 | k << 8 | j);
/*     */         }
/* 306 */         paramInt3 += paramInt4;
/* 307 */         paramInt1 += paramInt2;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ToIntArgbSameConv extends BaseByteToIntConverter
/*     */   {
/* 195 */     static final ByteToIntPixelConverter nonpremul = new ToIntArgbSameConv(false);
/* 196 */     static final ByteToIntPixelConverter premul = new ToIntArgbSameConv(true);
/*     */ 
/*     */     private ToIntArgbSameConv(boolean paramBoolean) {
/* 199 */       super(paramBoolean ? IntArgbPre.setter : IntArgb.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 208 */       paramInt2 -= paramInt5 * 4;
/* 209 */       paramInt4 -= paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 211 */         for (int i = 0; i < paramInt5; i++) {
/* 212 */           paramArrayOfInt[(paramInt3++)] = (paramArrayOfByte[(paramInt1++)] & 0xFF | (paramArrayOfByte[(paramInt1++)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt1++)] & 0xFF) << 16 | paramArrayOfByte[(paramInt1++)] << 24);
/*     */         }
/*     */ 
/* 218 */         paramInt1 += paramInt2;
/* 219 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 228 */       paramInt2 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 230 */         for (int i = 0; i < paramInt5; i++) {
/* 231 */           paramIntBuffer.put(paramInt3 + i, paramByteBuffer.get(paramInt1) & 0xFF | (paramByteBuffer.get(paramInt1 + 1) & 0xFF) << 8 | (paramByteBuffer.get(paramInt1 + 2) & 0xFF) << 16 | paramByteBuffer.get(paramInt1 + 3) << 24);
/*     */ 
/* 236 */           paramInt1 += 4;
/*     */         }
/* 238 */         paramInt1 += paramInt2;
/* 239 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ToByteBgraPreConv extends BaseByteToByteConverter
/*     */   {
/* 120 */     static final ByteToBytePixelConverter instance = new ToByteBgraPreConv();
/*     */ 
/*     */     private ToByteBgraPreConv()
/*     */     {
/* 124 */       super(ByteBgraPre.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 132 */       paramInt2 -= paramInt5 * 4;
/* 133 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 135 */         for (int i = 0; i < paramInt5; i++) {
/* 136 */           int j = paramArrayOfByte1[(paramInt1++)];
/* 137 */           int k = paramArrayOfByte1[(paramInt1++)];
/* 138 */           int m = paramArrayOfByte1[(paramInt1++)];
/* 139 */           int n = paramArrayOfByte1[(paramInt1++)] & 0xFF;
/* 140 */           if (n < 255) {
/* 141 */             if (n == 0) {
/* 142 */               j = k = m = 0;
/*     */             } else {
/* 144 */               j = (byte)(((j & 0xFF) * n + 127) / 255);
/* 145 */               k = (byte)(((k & 0xFF) * n + 127) / 255);
/* 146 */               m = (byte)(((m & 0xFF) * n + 127) / 255);
/*     */             }
/*     */           }
/* 149 */           paramArrayOfByte2[(paramInt3++)] = j;
/* 150 */           paramArrayOfByte2[(paramInt3++)] = k;
/* 151 */           paramArrayOfByte2[(paramInt3++)] = m;
/* 152 */           paramArrayOfByte2[(paramInt3++)] = ((byte)n);
/*     */         }
/* 154 */         paramInt1 += paramInt2;
/* 155 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 164 */       paramInt2 -= paramInt5 * 4;
/* 165 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 167 */         for (int i = 0; i < paramInt5; i++) {
/* 168 */           int j = paramByteBuffer1.get(paramInt1);
/* 169 */           int k = paramByteBuffer1.get(paramInt1 + 1);
/* 170 */           int m = paramByteBuffer1.get(paramInt1 + 2);
/* 171 */           int n = paramByteBuffer1.get(paramInt1 + 3) & 0xFF;
/* 172 */           paramInt1 += 4;
/* 173 */           if (n < 255) {
/* 174 */             if (n == 0) {
/* 175 */               j = k = m = 0;
/*     */             } else {
/* 177 */               j = (byte)(((j & 0xFF) * n + 127) / 255);
/* 178 */               k = (byte)(((k & 0xFF) * n + 127) / 255);
/* 179 */               m = (byte)(((m & 0xFF) * n + 127) / 255);
/*     */             }
/*     */           }
/* 182 */           paramByteBuffer2.put(paramInt3, j);
/* 183 */           paramByteBuffer2.put(paramInt3 + 1, k);
/* 184 */           paramByteBuffer2.put(paramInt3 + 2, m);
/* 185 */           paramByteBuffer2.put(paramInt3 + 3, (byte)n);
/* 186 */           paramInt3 += 4;
/*     */         }
/* 188 */         paramInt1 += paramInt2;
/* 189 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Accessor
/*     */     implements BytePixelAccessor
/*     */   {
/*  53 */     static final BytePixelAccessor instance = new Accessor();
/*     */ 
/*     */     public AlphaType getAlphaType()
/*     */     {
/*  58 */       return AlphaType.NONPREMULTIPLIED;
/*     */     }
/*     */ 
/*     */     public int getNumElements()
/*     */     {
/*  63 */       return 4;
/*     */     }
/*     */ 
/*     */     public int getArgb(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  68 */       return paramArrayOfByte[paramInt] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | paramArrayOfByte[(paramInt + 3)] << 24;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  76 */       return PixelUtils.NonPretoPre(getArgb(paramArrayOfByte, paramInt));
/*     */     }
/*     */ 
/*     */     public int getArgb(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  81 */       return paramByteBuffer.get(paramInt) & 0xFF | (paramByteBuffer.get(paramInt + 1) & 0xFF) << 8 | (paramByteBuffer.get(paramInt + 2) & 0xFF) << 16 | paramByteBuffer.get(paramInt + 3) << 24;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  89 */       return PixelUtils.NonPretoPre(getArgb(paramByteBuffer, paramInt));
/*     */     }
/*     */ 
/*     */     public void setArgb(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     {
/*  94 */       paramArrayOfByte[paramInt1] = ((byte)paramInt2);
/*  95 */       paramArrayOfByte[(paramInt1 + 1)] = ((byte)(paramInt2 >> 8));
/*  96 */       paramArrayOfByte[(paramInt1 + 2)] = ((byte)(paramInt2 >> 16));
/*  97 */       paramArrayOfByte[(paramInt1 + 3)] = ((byte)(paramInt2 >> 24));
/*     */     }
/*     */ 
/*     */     public void setArgbPre(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     {
/* 102 */       setArgb(paramArrayOfByte, paramInt1, PixelUtils.PretoNonPre(paramInt2));
/*     */     }
/*     */ 
/*     */     public void setArgb(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/*     */     {
/* 107 */       paramByteBuffer.put(paramInt1, (byte)paramInt2);
/* 108 */       paramByteBuffer.put(paramInt1 + 1, (byte)(paramInt2 >> 8));
/* 109 */       paramByteBuffer.put(paramInt1 + 2, (byte)(paramInt2 >> 16));
/* 110 */       paramByteBuffer.put(paramInt1 + 3, (byte)(paramInt2 >> 24));
/*     */     }
/*     */ 
/*     */     public void setArgbPre(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/*     */     {
/* 115 */       setArgb(paramByteBuffer, paramInt1, PixelUtils.PretoNonPre(paramInt2));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.ByteBgra
 * JD-Core Version:    0.6.2
 */