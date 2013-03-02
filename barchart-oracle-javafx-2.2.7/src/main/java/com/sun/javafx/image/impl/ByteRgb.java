/*     */ package com.sun.javafx.image.impl;
/*     */ 
/*     */ import com.sun.javafx.image.AlphaType;
/*     */ import com.sun.javafx.image.BytePixelGetter;
/*     */ import com.sun.javafx.image.BytePixelSetter;
/*     */ import com.sun.javafx.image.ByteToBytePixelConverter;
/*     */ import com.sun.javafx.image.ByteToIntPixelConverter;
/*     */ import com.sun.javafx.image.IntPixelSetter;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public class ByteRgb
/*     */ {
/*  38 */   public static final BytePixelGetter getter = Getter.instance;
/*     */ 
/*  40 */   public static final ByteToBytePixelConverter ToByteBgraConverter = ToByteBgrfConv.nonpremult;
/*     */ 
/*  42 */   public static final ByteToBytePixelConverter ToByteBgraPreConverter = ToByteBgrfConv.premult;
/*     */ 
/*  44 */   public static final ByteToIntPixelConverter ToIntArgbConverter = ToIntFrgbConv.nonpremult;
/*     */ 
/*  46 */   public static final ByteToIntPixelConverter ToIntArgbPreConverter = ToIntFrgbConv.premult;
/*     */ 
/*  48 */   public static final ByteToBytePixelConverter ToByteArgbConverter = ToByteFrgbConv.nonpremult;
/*     */ 
/*     */   static class ToByteFrgbConv extends BaseByteToByteConverter
/*     */   {
/* 207 */     static final ByteToBytePixelConverter nonpremult = new ToByteFrgbConv(ByteArgb.setter);
/*     */ 
/*     */     private ToByteFrgbConv(BytePixelSetter paramBytePixelSetter)
/*     */     {
/* 215 */       super(paramBytePixelSetter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 223 */       paramInt2 -= paramInt5 * 3;
/* 224 */       paramInt2 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 226 */         for (int i = 0; i < paramInt5; i++) {
/* 227 */           paramArrayOfByte2[(paramInt3++)] = -1;
/* 228 */           paramArrayOfByte2[(paramInt3++)] = paramArrayOfByte1[(paramInt1++)];
/* 229 */           paramArrayOfByte2[(paramInt3++)] = paramArrayOfByte1[(paramInt1++)];
/* 230 */           paramArrayOfByte2[(paramInt3++)] = paramArrayOfByte1[(paramInt1++)];
/*     */         }
/* 232 */         paramInt1 += paramInt2;
/* 233 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 242 */       paramInt2 -= paramInt5 * 3;
/* 243 */       paramInt2 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 245 */         for (int i = 0; i < paramInt5; i++) {
/* 246 */           paramByteBuffer2.put(paramInt3++, (byte)-1);
/* 247 */           paramByteBuffer2.put(paramInt3++, paramByteBuffer1.get(paramInt1++));
/* 248 */           paramByteBuffer2.put(paramInt3++, paramByteBuffer1.get(paramInt1++));
/* 249 */           paramByteBuffer2.put(paramInt3++, paramByteBuffer1.get(paramInt1++));
/*     */         }
/* 251 */         paramInt1 += paramInt2;
/* 252 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ToIntFrgbConv extends BaseByteToIntConverter
/*     */   {
/* 155 */     public static final ByteToIntPixelConverter nonpremult = new ToIntFrgbConv(IntArgb.setter);
/*     */ 
/* 157 */     public static final ByteToIntPixelConverter premult = new ToIntFrgbConv(IntArgbPre.setter);
/*     */ 
/*     */     private ToIntFrgbConv(IntPixelSetter paramIntPixelSetter)
/*     */     {
/* 165 */       super(paramIntPixelSetter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 173 */       paramInt2 -= paramInt5 * 3;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 175 */         for (int i = 0; i < paramInt5; i++) {
/* 176 */           int j = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 177 */           int k = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 178 */           int m = paramArrayOfByte[(paramInt1++)] & 0xFF;
/* 179 */           paramArrayOfInt[(paramInt3 + i)] = (0xFF000000 | j << 16 | k << 8 | m);
/*     */         }
/* 181 */         paramInt1 += paramInt2;
/* 182 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 191 */       paramInt2 -= paramInt5 * 3;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 193 */         for (int i = 0; i < paramInt5; i++) {
/* 194 */           int j = paramByteBuffer.get(paramInt1) & 0xFF;
/* 195 */           int k = paramByteBuffer.get(paramInt1 + 1) & 0xFF;
/* 196 */           int m = paramByteBuffer.get(paramInt1 + 2) & 0xFF;
/* 197 */           paramInt1 += 3;
/* 198 */           paramIntBuffer.put(paramInt3 + i, 0xFF000000 | j << 16 | k << 8 | m);
/*     */         }
/* 200 */         paramInt1 += paramInt2;
/* 201 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ToByteBgrfConv extends BaseByteToByteConverter
/*     */   {
/*  99 */     public static final ByteToBytePixelConverter nonpremult = new ToByteBgrfConv(ByteBgra.setter);
/*     */ 
/* 101 */     public static final ByteToBytePixelConverter premult = new ToByteBgrfConv(ByteBgraPre.setter);
/*     */ 
/*     */     private ToByteBgrfConv(BytePixelSetter paramBytePixelSetter)
/*     */     {
/* 109 */       super(paramBytePixelSetter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 117 */       paramInt2 -= paramInt5 * 3;
/* 118 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 120 */         for (int i = 0; i < paramInt5; i++) {
/* 121 */           paramArrayOfByte2[(paramInt3++)] = paramArrayOfByte1[(paramInt1 + 2)];
/* 122 */           paramArrayOfByte2[(paramInt3++)] = paramArrayOfByte1[(paramInt1 + 1)];
/* 123 */           paramArrayOfByte2[(paramInt3++)] = paramArrayOfByte1[paramInt1];
/* 124 */           paramArrayOfByte2[(paramInt3++)] = -1;
/* 125 */           paramInt1 += 3;
/*     */         }
/* 127 */         paramInt1 += paramInt2;
/* 128 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 137 */       paramInt2 -= paramInt5 * 3;
/* 138 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 140 */         for (int i = 0; i < paramInt5; i++) {
/* 141 */           paramByteBuffer2.put(paramInt3, paramByteBuffer1.get(paramInt1 + 2));
/* 142 */           paramByteBuffer2.put(paramInt3 + 1, paramByteBuffer1.get(paramInt1 + 1));
/* 143 */           paramByteBuffer2.put(paramInt3 + 2, paramByteBuffer1.get(paramInt1));
/* 144 */           paramByteBuffer2.put(paramInt3 + 3, (byte)-1);
/* 145 */           paramInt1 += 3;
/* 146 */           paramInt3 += 4;
/*     */         }
/* 148 */         paramInt1 += paramInt2;
/* 149 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Getter
/*     */     implements BytePixelGetter
/*     */   {
/*  52 */     static final BytePixelGetter instance = new Getter();
/*     */ 
/*     */     public AlphaType getAlphaType()
/*     */     {
/*  57 */       return AlphaType.OPAQUE;
/*     */     }
/*     */ 
/*     */     public int getNumElements()
/*     */     {
/*  62 */       return 3;
/*     */     }
/*     */ 
/*     */     public int getArgb(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  67 */       return paramArrayOfByte[(paramInt + 2)] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[paramInt] & 0xFF) << 16 | 0xFF000000;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  75 */       return paramArrayOfByte[(paramInt + 2)] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[paramInt] & 0xFF) << 16 | 0xFF000000;
/*     */     }
/*     */ 
/*     */     public int getArgb(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  83 */       return paramByteBuffer.get(paramInt + 2) & 0xFF | (paramByteBuffer.get(paramInt + 1) & 0xFF) << 8 | (paramByteBuffer.get(paramInt) & 0xFF) << 16 | 0xFF000000;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  91 */       return paramByteBuffer.get(paramInt + 2) & 0xFF | (paramByteBuffer.get(paramInt + 1) & 0xFF) << 8 | (paramByteBuffer.get(paramInt) & 0xFF) << 16 | 0xFF000000;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.ByteRgb
 * JD-Core Version:    0.6.2
 */