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
/*     */ public class IntArgbPre
/*     */ {
/*  39 */   public static final IntPixelGetter getter = IntArgb.Accessor.instance;
/*  40 */   public static final IntPixelSetter setter = IntArgb.Accessor.instance;
/*  41 */   public static final IntPixelAccessor accessor = IntArgb.Accessor.instance;
/*     */ 
/*  43 */   public static final IntToBytePixelConverter ToByteBgraConverter = ToByteBgraConv.instance;
/*     */ 
/*  45 */   public static final IntToBytePixelConverter ToByteBgraPreConverter = IntArgb.ToByteBgraSameConv.premul;
/*     */ 
/*  47 */   public static final IntToIntPixelConverter ToIntArgbConverter = ToIntArgbConv.instance;
/*     */ 
/*  49 */   public static final IntToIntPixelConverter ToIntArgbPreConverter = BaseIntToIntConverter.create(accessor);
/*     */ 
/*     */   static class ToByteBgraConv extends BaseIntToByteConverter
/*     */   {
/* 165 */     public static final IntToBytePixelConverter instance = new ToByteBgraConv();
/*     */ 
/*     */     private ToByteBgraConv()
/*     */     {
/* 169 */       super(ByteBgra.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(int[] paramArrayOfInt, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 177 */       paramInt2 -= paramInt5;
/* 178 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 180 */         for (int i = 0; i < paramInt5; i++) {
/* 181 */           int j = paramArrayOfInt[(paramInt1++)];
/* 182 */           int k = j >>> 24;
/* 183 */           int m = j >> 16 & 0xFF;
/* 184 */           int n = j >> 8 & 0xFF;
/* 185 */           int i1 = j & 0xFF;
/* 186 */           if ((k > 0) && (k < 255)) {
/* 187 */             int i2 = k >> 1;
/* 188 */             m = (m * 255 + i2) / k;
/* 189 */             n = (n * 255 + i2) / k;
/* 190 */             i1 = (i1 * 255 + i2) / k;
/*     */           }
/* 192 */           paramArrayOfByte[(paramInt3++)] = ((byte)i1);
/* 193 */           paramArrayOfByte[(paramInt3++)] = ((byte)n);
/* 194 */           paramArrayOfByte[(paramInt3++)] = ((byte)m);
/* 195 */           paramArrayOfByte[(paramInt3++)] = ((byte)k);
/*     */         }
/* 197 */         paramInt1 += paramInt2;
/* 198 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 207 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 209 */         for (int i = 0; i < paramInt5; i++) {
/* 210 */           int j = paramIntBuffer.get(paramInt1 + i);
/* 211 */           int k = j >>> 24;
/* 212 */           int m = j >> 16 & 0xFF;
/* 213 */           int n = j >> 8 & 0xFF;
/* 214 */           int i1 = j & 0xFF;
/* 215 */           if ((k > 0) && (k < 255)) {
/* 216 */             m = (m * k + 127) / 255;
/* 217 */             n = (n * k + 127) / 255;
/* 218 */             i1 = (i1 * k + 127) / 255;
/*     */           }
/* 220 */           paramByteBuffer.put(paramInt3, (byte)i1);
/* 221 */           paramByteBuffer.put(paramInt3 + 1, (byte)n);
/* 222 */           paramByteBuffer.put(paramInt3 + 2, (byte)m);
/* 223 */           paramByteBuffer.put(paramInt3 + 3, (byte)k);
/* 224 */           paramInt3 += 4;
/*     */         }
/* 226 */         paramInt1 += paramInt2;
/* 227 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class ToIntArgbConv extends BaseIntToIntConverter
/*     */   {
/* 108 */     public static final IntToIntPixelConverter instance = new ToIntArgbConv();
/*     */ 
/*     */     private ToIntArgbConv()
/*     */     {
/* 112 */       super(IntArgb.setter);
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
/* 126 */           if ((k > 0) && (k < 255)) {
/* 127 */             int m = k >> 1;
/* 128 */             int n = ((j >> 16 & 0xFF) * 255 + m) / k;
/* 129 */             int i1 = ((j >> 8 & 0xFF) * 255 + m) / k;
/* 130 */             int i2 = ((j & 0xFF) * 255 + m) / k;
/* 131 */             j = k << 24 | n << 16 | i1 << 8 | i2;
/*     */           }
/* 133 */           paramArrayOfInt2[(paramInt3++)] = j;
/*     */         }
/* 135 */         paramInt1 += paramInt2;
/* 136 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(IntBuffer paramIntBuffer1, int paramInt1, int paramInt2, IntBuffer paramIntBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/*     */       while (true)
/*     */       {
/* 145 */         paramInt6--; if (paramInt6 < 0) break;
/* 146 */         for (int i = 0; i < paramInt5; i++) {
/* 147 */           int j = paramIntBuffer1.get(paramInt1 + i);
/* 148 */           int k = j >>> 24;
/* 149 */           if ((k > 0) && (k < 255)) {
/* 150 */             int m = k >> 1;
/* 151 */             int n = ((j >> 16 & 0xFF) * 255 + m) / k;
/* 152 */             int i1 = ((j >> 8 & 0xFF) * 255 + m) / k;
/* 153 */             int i2 = ((j & 0xFF) * 255 + m) / k;
/* 154 */             j = k << 24 | n << 16 | i1 << 8 | i2;
/*     */           }
/* 156 */           paramIntBuffer2.put(paramInt3 + i, j);
/*     */         }
/* 158 */         paramInt1 += paramInt2;
/* 159 */         paramInt3 += paramInt4;
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
/*  58 */       return AlphaType.PREMULTIPLIED;
/*     */     }
/*     */ 
/*     */     public int getNumElements()
/*     */     {
/*  63 */       return 1;
/*     */     }
/*     */ 
/*     */     public int getArgb(int[] paramArrayOfInt, int paramInt)
/*     */     {
/*  68 */       return PixelUtils.PretoNonPre(paramArrayOfInt[paramInt]);
/*     */     }
/*     */ 
/*     */     public int getArgbPre(int[] paramArrayOfInt, int paramInt)
/*     */     {
/*  73 */       return paramArrayOfInt[paramInt];
/*     */     }
/*     */ 
/*     */     public int getArgb(IntBuffer paramIntBuffer, int paramInt)
/*     */     {
/*  78 */       return PixelUtils.PretoNonPre(paramIntBuffer.get(paramInt));
/*     */     }
/*     */ 
/*     */     public int getArgbPre(IntBuffer paramIntBuffer, int paramInt)
/*     */     {
/*  83 */       return paramIntBuffer.get(paramInt);
/*     */     }
/*     */ 
/*     */     public void setArgb(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*     */     {
/*  88 */       paramArrayOfInt[paramInt1] = PixelUtils.NonPretoPre(paramInt2);
/*     */     }
/*     */ 
/*     */     public void setArgbPre(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*     */     {
/*  93 */       paramArrayOfInt[paramInt1] = paramInt2;
/*     */     }
/*     */ 
/*     */     public void setArgb(IntBuffer paramIntBuffer, int paramInt1, int paramInt2)
/*     */     {
/*  98 */       paramIntBuffer.put(paramInt1, PixelUtils.NonPretoPre(paramInt2));
/*     */     }
/*     */ 
/*     */     public void setArgbPre(IntBuffer paramIntBuffer, int paramInt1, int paramInt2)
/*     */     {
/* 103 */       paramIntBuffer.put(paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.IntArgbPre
 * JD-Core Version:    0.6.2
 */