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
/*     */ public class ByteBgraPre
/*     */ {
/*  39 */   public static final BytePixelGetter getter = Accessor.instance;
/*  40 */   public static final BytePixelSetter setter = Accessor.instance;
/*  41 */   public static final BytePixelAccessor accessor = Accessor.instance;
/*     */ 
/*  43 */   public static final ByteToBytePixelConverter ToByteBgraConverter = ToByteBgraConv.instance;
/*     */ 
/*  45 */   public static final ByteToBytePixelConverter ToByteBgraPreConverter = BaseByteToByteConverter.create(accessor);
/*     */ 
/*  47 */   public static final ByteToIntPixelConverter ToIntArgbConverter = ToIntArgbConv.instance;
/*     */ 
/*  49 */   public static final ByteToIntPixelConverter ToIntArgbPreConverter = ByteBgra.ToIntArgbSameConv.premul;
/*     */ 
/*     */   public static class ToIntArgbConv extends BaseByteToIntConverter
/*     */   {
/* 189 */     public static final ByteToIntPixelConverter instance = new ToIntArgbConv();
/*     */ 
/*     */     private ToIntArgbConv()
/*     */     {
/* 193 */       super(IntArgb.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 201 */       paramInt2 -= paramInt5 * 4;
/* 202 */       paramInt4 -= paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 204 */         for (int i = 0; i < paramInt5; i++) {
/* 205 */           int j = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 206 */           int k = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 207 */           int m = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 208 */           int n = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 209 */           if ((n > 0) && (n < 255)) {
/* 210 */             int i1 = n >> 1;
/* 211 */             m = (m * 255 + i1) / n;
/* 212 */             k = (k * 255 + i1) / n;
/* 213 */             j = (j * 255 + i1) / n;
/*     */           }
/* 215 */           paramArrayOfInt[(paramInt3++)] = (n << 24 | m << 16 | k << 8 | j);
/*     */         }
/*     */ 
/* 218 */         paramInt3 += paramInt4;
/* 219 */         paramInt1 += paramInt2;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 228 */       paramInt2 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 230 */         for (int i = 0; i < paramInt5; i++) {
/* 231 */           int j = paramByteBuffer.get(paramInt1) & 0xFF;
/* 232 */           int k = paramByteBuffer.get(paramInt1 + 1) & 0xFF;
/* 233 */           int m = paramByteBuffer.get(paramInt1 + 2) & 0xFF;
/* 234 */           int n = paramByteBuffer.get(paramInt1 + 3) & 0xFF;
/* 235 */           paramInt1 += 4;
/* 236 */           if ((n > 0) && (n < 255)) {
/* 237 */             int i1 = n >> 1;
/* 238 */             m = (m * 255 + i1) / n;
/* 239 */             k = (k * 255 + i1) / n;
/* 240 */             j = (j * 255 + i1) / n;
/*     */           }
/* 242 */           paramIntBuffer.put(paramInt3 + i, n << 24 | m << 16 | k << 8 | j);
/*     */         }
/* 244 */         paramInt3 += paramInt4;
/* 245 */         paramInt1 += paramInt2;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class ToByteBgraConv extends BaseByteToByteConverter
/*     */   {
/* 120 */     public static final ByteToBytePixelConverter instance = new ToByteBgraConv();
/*     */ 
/*     */     private ToByteBgraConv()
/*     */     {
/* 124 */       super(ByteBgra.setter);
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
/* 140 */           if ((n > 0) && (n < 255)) {
/* 141 */             int i1 = n >> 1;
/* 142 */             j = (byte)(((j & 0xFF) * 255 + i1) / n);
/* 143 */             k = (byte)(((k & 0xFF) * 255 + i1) / n);
/* 144 */             m = (byte)(((m & 0xFF) * 255 + i1) / n);
/*     */           }
/* 146 */           paramArrayOfByte2[(paramInt3++)] = j;
/* 147 */           paramArrayOfByte2[(paramInt3++)] = k;
/* 148 */           paramArrayOfByte2[(paramInt3++)] = m;
/* 149 */           paramArrayOfByte2[(paramInt3++)] = ((byte)n);
/*     */         }
/* 151 */         paramInt1 += paramInt2;
/* 152 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 161 */       paramInt2 -= paramInt5 * 4;
/* 162 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 164 */         for (int i = 0; i < paramInt5; i++) {
/* 165 */           int j = paramByteBuffer1.get(paramInt1);
/* 166 */           int k = paramByteBuffer1.get(paramInt1 + 1);
/* 167 */           int m = paramByteBuffer1.get(paramInt1 + 2);
/* 168 */           int n = paramByteBuffer1.get(paramInt1 + 3) & 0xFF;
/* 169 */           paramInt1 += 4;
/* 170 */           if ((n > 0) && (n < 255)) {
/* 171 */             int i1 = n >> 1;
/* 172 */             j = (byte)(((j & 0xFF) * 255 + i1) / n);
/* 173 */             k = (byte)(((k & 0xFF) * 255 + i1) / n);
/* 174 */             m = (byte)(((m & 0xFF) * 255 + i1) / n);
/*     */           }
/* 176 */           paramByteBuffer2.put(paramInt3, j);
/* 177 */           paramByteBuffer2.put(paramInt3 + 1, k);
/* 178 */           paramByteBuffer2.put(paramInt3 + 2, m);
/* 179 */           paramByteBuffer2.put(paramInt3 + 3, (byte)n);
/* 180 */           paramInt3 += 4;
/*     */         }
/* 182 */         paramInt1 += paramInt2;
/* 183 */         paramInt3 += paramInt4;
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
/*  58 */       return AlphaType.PREMULTIPLIED;
/*     */     }
/*     */ 
/*     */     public int getNumElements()
/*     */     {
/*  63 */       return 4;
/*     */     }
/*     */ 
/*     */     public int getArgb(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  68 */       return PixelUtils.PretoNonPre(getArgbPre(paramArrayOfByte, paramInt));
/*     */     }
/*     */ 
/*     */     public int getArgbPre(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  73 */       return paramArrayOfByte[paramInt] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | paramArrayOfByte[(paramInt + 3)] << 24;
/*     */     }
/*     */ 
/*     */     public int getArgb(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  81 */       return PixelUtils.PretoNonPre(getArgbPre(paramByteBuffer, paramInt));
/*     */     }
/*     */ 
/*     */     public int getArgbPre(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  86 */       return paramByteBuffer.get(paramInt) & 0xFF | (paramByteBuffer.get(paramInt + 1) & 0xFF) << 8 | (paramByteBuffer.get(paramInt + 2) & 0xFF) << 16 | paramByteBuffer.get(paramInt + 3) << 24;
/*     */     }
/*     */ 
/*     */     public void setArgb(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     {
/*  94 */       setArgbPre(paramArrayOfByte, paramInt1, PixelUtils.NonPretoPre(paramInt2));
/*     */     }
/*     */ 
/*     */     public void setArgbPre(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     {
/*  99 */       paramArrayOfByte[paramInt1] = ((byte)paramInt2);
/* 100 */       paramArrayOfByte[(paramInt1 + 1)] = ((byte)(paramInt2 >> 8));
/* 101 */       paramArrayOfByte[(paramInt1 + 2)] = ((byte)(paramInt2 >> 16));
/* 102 */       paramArrayOfByte[(paramInt1 + 3)] = ((byte)(paramInt2 >> 24));
/*     */     }
/*     */ 
/*     */     public void setArgb(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/*     */     {
/* 107 */       setArgbPre(paramByteBuffer, paramInt1, PixelUtils.NonPretoPre(paramInt2));
/*     */     }
/*     */ 
/*     */     public void setArgbPre(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/*     */     {
/* 112 */       paramByteBuffer.put(paramInt1, (byte)paramInt2);
/* 113 */       paramByteBuffer.put(paramInt1 + 1, (byte)(paramInt2 >> 8));
/* 114 */       paramByteBuffer.put(paramInt1 + 2, (byte)(paramInt2 >> 16));
/* 115 */       paramByteBuffer.put(paramInt1 + 3, (byte)(paramInt2 >> 24));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.ByteBgraPre
 * JD-Core Version:    0.6.2
 */