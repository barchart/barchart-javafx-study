/*     */ package com.sun.javafx.image.impl;
/*     */ 
/*     */ import com.sun.javafx.image.AlphaType;
/*     */ import com.sun.javafx.image.IntPixelAccessor;
/*     */ import com.sun.javafx.image.IntPixelGetter;
/*     */ import com.sun.javafx.image.IntPixelSetter;
/*     */ import com.sun.javafx.image.IntToBytePixelConverter;
/*     */ import com.sun.javafx.image.IntToIntPixelConverter;
/*     */ import com.sun.javafx.image.PixelUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public class IntArgb
/*     */ {
/*  39 */   public static final IntPixelGetter getter = Accessor.instance;
/*  40 */   public static final IntPixelSetter setter = Accessor.instance;
/*  41 */   public static final IntPixelAccessor accessor = Accessor.instance;
/*     */ 
/*  43 */   public static final IntToBytePixelConverter ToByteBgraConverter = ToByteBgraSameConv.nonpremul;
/*     */ 
/*  45 */   public static final IntToBytePixelConverter ToByteBgraPreConverter = ToByteBgraPreConv.instance;
/*     */ 
/*  47 */   public static final IntToIntPixelConverter ToIntArgbConverter = BaseIntToIntConverter.create(accessor);
/*     */ 
/*  49 */   public static final IntToIntPixelConverter ToIntArgbPreConverter = ToIntArgbPreConv.instance;
/*     */ 
/*     */   static class ToByteBgraPreConv extends BaseIntToByteConverter
/*     */   {
/* 221 */     public static final IntToBytePixelConverter instance = new ToByteBgraPreConv();
/*     */ 
/*     */     private ToByteBgraPreConv()
/*     */     {
/* 225 */       super(ByteBgraPre.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(int[] paramArrayOfInt, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 233 */       paramInt2 -= paramInt5;
/* 234 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 236 */         for (int i = 0; i < paramInt5; i++) {
/* 237 */           int j = paramArrayOfInt[(paramInt1++)];
/* 238 */           int k = j >>> 24;
/* 239 */           int m = j >> 16;
/* 240 */           int n = j >> 8;
/* 241 */           int i1 = j;
/* 242 */           if (k < 255) {
/* 243 */             if (k == 0) {
/* 244 */               i1 = n = m = 0;
/*     */             } else {
/* 246 */               i1 = ((i1 & 0xFF) * k + 127) / 255;
/* 247 */               n = ((n & 0xFF) * k + 127) / 255;
/* 248 */               m = ((m & 0xFF) * k + 127) / 255;
/*     */             }
/*     */           }
/* 251 */           paramArrayOfByte[(paramInt3++)] = ((byte)i1);
/* 252 */           paramArrayOfByte[(paramInt3++)] = ((byte)n);
/* 253 */           paramArrayOfByte[(paramInt3++)] = ((byte)m);
/* 254 */           paramArrayOfByte[(paramInt3++)] = ((byte)k);
/*     */         }
/* 256 */         paramInt1 += paramInt2;
/* 257 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 266 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 268 */         for (int i = 0; i < paramInt5; i++) {
/* 269 */           int j = paramIntBuffer.get(paramInt1 + i);
/* 270 */           int k = j >>> 24;
/* 271 */           int m = j >> 16;
/* 272 */           int n = j >> 8;
/* 273 */           int i1 = j;
/* 274 */           if (k < 255) {
/* 275 */             if (k == 0) {
/* 276 */               i1 = n = m = 0;
/*     */             } else {
/* 278 */               i1 = ((i1 & 0xFF) * k + 127) / 255;
/* 279 */               n = ((n & 0xFF) * k + 127) / 255;
/* 280 */               m = ((m & 0xFF) * k + 127) / 255;
/*     */             }
/*     */           }
/* 283 */           paramByteBuffer.put(paramInt3, (byte)i1);
/* 284 */           paramByteBuffer.put(paramInt3 + 1, (byte)n);
/* 285 */           paramByteBuffer.put(paramInt3 + 2, (byte)m);
/* 286 */           paramByteBuffer.put(paramInt3 + 3, (byte)k);
/* 287 */           paramInt3 += 4;
/*     */         }
/* 289 */         paramInt1 += paramInt2;
/* 290 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ToByteBgraSameConv extends BaseIntToByteConverter
/*     */   {
/* 171 */     static final IntToBytePixelConverter nonpremul = new ToByteBgraSameConv(false);
/* 172 */     static final IntToBytePixelConverter premul = new ToByteBgraSameConv(true);
/*     */ 
/*     */     private ToByteBgraSameConv(boolean paramBoolean) {
/* 175 */       super(paramBoolean ? ByteBgraPre.setter : ByteBgra.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(int[] paramArrayOfInt, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 184 */       paramInt2 -= paramInt5;
/* 185 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 187 */         for (int i = 0; i < paramInt5; i++) {
/* 188 */           int j = paramArrayOfInt[(paramInt1++)];
/* 189 */           paramArrayOfByte[(paramInt3++)] = ((byte)j);
/* 190 */           paramArrayOfByte[(paramInt3++)] = ((byte)(j >> 8));
/* 191 */           paramArrayOfByte[(paramInt3++)] = ((byte)(j >> 16));
/* 192 */           paramArrayOfByte[(paramInt3++)] = ((byte)(j >> 24));
/*     */         }
/* 194 */         paramInt1 += paramInt2;
/* 195 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 204 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 206 */         for (int i = 0; i < paramInt5; i++) {
/* 207 */           int j = paramIntBuffer.get(paramInt1 + i);
/* 208 */           paramByteBuffer.put(paramInt3, (byte)j);
/* 209 */           paramByteBuffer.put(paramInt3 + 1, (byte)(j >> 8));
/* 210 */           paramByteBuffer.put(paramInt3 + 2, (byte)(j >> 16));
/* 211 */           paramByteBuffer.put(paramInt3 + 3, (byte)(j >> 24));
/* 212 */           paramInt3 += 4;
/*     */         }
/* 214 */         paramInt1 += paramInt2;
/* 215 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class ToIntArgbPreConv extends BaseIntToIntConverter
/*     */   {
/* 108 */     public static final IntToIntPixelConverter instance = new ToIntArgbPreConv();
/*     */ 
/*     */     private ToIntArgbPreConv()
/*     */     {
/* 112 */       super(IntArgbPre.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 120 */       paramInt2 -= paramInt5;
/* 121 */       paramInt4 -= paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 123 */         for (int i = 0; i < paramInt5; i++) {
/* 124 */           int j = paramArrayOfInt1[(paramInt1++)];
/* 125 */           int k = j >>> 24;
/* 126 */           if (k < 255) {
/* 127 */             if (k == 0) {
/* 128 */               j = 0;
/*     */             } else {
/* 130 */               int m = ((j >> 16 & 0xFF) * k + 127) / 255;
/* 131 */               int n = ((j >> 8 & 0xFF) * k + 127) / 255;
/* 132 */               int i1 = ((j & 0xFF) * k + 127) / 255;
/* 133 */               j = k << 24 | m << 16 | n << 8 | i1;
/*     */             }
/*     */           }
/* 136 */           paramArrayOfInt2[(paramInt3++)] = j;
/*     */         }
/* 138 */         paramInt1 += paramInt2;
/* 139 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(IntBuffer paramIntBuffer1, int paramInt1, int paramInt2, IntBuffer paramIntBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/*     */       while (true)
/*     */       {
/* 148 */         paramInt6--; if (paramInt6 < 0) break;
/* 149 */         for (int i = 0; i < paramInt5; i++) {
/* 150 */           int j = paramIntBuffer1.get(paramInt1 + i);
/* 151 */           int k = j >>> 24;
/* 152 */           if (k < 255) {
/* 153 */             if (k == 0) {
/* 154 */               j = 0;
/*     */             } else {
/* 156 */               int m = ((j >> 16 & 0xFF) * k + 127) / 255;
/* 157 */               int n = ((j >> 8 & 0xFF) * k + 127) / 255;
/* 158 */               int i1 = ((j & 0xFF) * k + 127) / 255;
/* 159 */               j = k << 24 | m << 16 | n << 8 | i1;
/*     */             }
/*     */           }
/* 162 */           paramIntBuffer2.put(paramInt3 + i, j);
/*     */         }
/* 164 */         paramInt1 += paramInt2;
/* 165 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Accessor
/*     */     implements IntPixelAccessor
/*     */   {
/*  53 */     static final IntPixelAccessor instance = new Accessor();
/*     */ 
/*     */     public AlphaType getAlphaType()
/*     */     {
/*  58 */       return AlphaType.NONPREMULTIPLIED;
/*     */     }
/*     */ 
/*     */     public int getNumElements()
/*     */     {
/*  63 */       return 1;
/*     */     }
/*     */ 
/*     */     public int getArgb(int[] paramArrayOfInt, int paramInt)
/*     */     {
/*  68 */       return paramArrayOfInt[paramInt];
/*     */     }
/*     */ 
/*     */     public int getArgbPre(int[] paramArrayOfInt, int paramInt)
/*     */     {
/*  73 */       return PixelUtils.NonPretoPre(paramArrayOfInt[paramInt]);
/*     */     }
/*     */ 
/*     */     public int getArgb(IntBuffer paramIntBuffer, int paramInt)
/*     */     {
/*  78 */       return paramIntBuffer.get(paramInt);
/*     */     }
/*     */ 
/*     */     public int getArgbPre(IntBuffer paramIntBuffer, int paramInt)
/*     */     {
/*  83 */       return PixelUtils.NonPretoPre(paramIntBuffer.get(paramInt));
/*     */     }
/*     */ 
/*     */     public void setArgb(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*     */     {
/*  88 */       paramArrayOfInt[paramInt1] = paramInt2;
/*     */     }
/*     */ 
/*     */     public void setArgbPre(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*     */     {
/*  93 */       paramArrayOfInt[paramInt1] = PixelUtils.PretoNonPre(paramInt2);
/*     */     }
/*     */ 
/*     */     public void setArgb(IntBuffer paramIntBuffer, int paramInt1, int paramInt2)
/*     */     {
/*  98 */       paramIntBuffer.put(paramInt1, paramInt2);
/*     */     }
/*     */ 
/*     */     public void setArgbPre(IntBuffer paramIntBuffer, int paramInt1, int paramInt2)
/*     */     {
/* 103 */       paramIntBuffer.put(paramInt1, PixelUtils.PretoNonPre(paramInt2));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.IntArgb
 * JD-Core Version:    0.6.2
 */