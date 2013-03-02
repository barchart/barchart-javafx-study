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
/*     */ public class ByteGray
/*     */ {
/*  38 */   public static final BytePixelGetter getter = Getter.instance;
/*     */ 
/*  40 */   public static final ByteToBytePixelConverter ToByteBgraConverter = ByteRgb.ToByteBgrfConv.nonpremult;
/*     */ 
/*  42 */   public static final ByteToBytePixelConverter ToByteBgraPreConverter = ByteRgb.ToByteBgrfConv.premult;
/*     */ 
/*  44 */   public static final ByteToIntPixelConverter ToIntArgbConverter = ByteRgb.ToIntFrgbConv.nonpremult;
/*     */ 
/*  46 */   public static final ByteToIntPixelConverter ToIntArgbPreConverter = ByteRgb.ToIntFrgbConv.premult;
/*     */ 
/*     */   static class ToIntFrgbConv extends BaseByteToIntConverter
/*     */   {
/* 139 */     public static final ByteToIntPixelConverter nonpremult = new ToIntFrgbConv(IntArgb.setter);
/*     */ 
/* 141 */     public static final ByteToIntPixelConverter premult = new ToIntFrgbConv(IntArgbPre.setter);
/*     */ 
/*     */     private ToIntFrgbConv(IntPixelSetter paramIntPixelSetter)
/*     */     {
/* 145 */       super(paramIntPixelSetter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/*     */       while (true)
/*     */       {
/* 153 */         paramInt6--; if (paramInt6 < 0) break;
/* 154 */         for (int i = 0; i < paramInt5; i++) {
/* 155 */           int j = paramArrayOfByte[(paramInt1 + i)] & 0xFF;
/* 156 */           paramArrayOfInt[(paramInt3 + i)] = (0xFF000000 | j << 16 | j << 8 | j);
/*     */         }
/* 158 */         paramInt1 += paramInt2;
/* 159 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/*     */       while (true)
/*     */       {
/* 168 */         paramInt6--; if (paramInt6 < 0) break;
/* 169 */         for (int i = 0; i < paramInt5; i++) {
/* 170 */           int j = paramByteBuffer.get(paramInt1 + i) & 0xFF;
/* 171 */           paramIntBuffer.put(paramInt3 + i, 0xFF000000 | j << 16 | j << 8 | j);
/*     */         }
/* 173 */         paramInt1 += paramInt2;
/* 174 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ToByteBgrfConv extends BaseByteToByteConverter
/*     */   {
/*  89 */     public static final ByteToBytePixelConverter nonpremult = new ToByteBgrfConv(ByteBgra.setter);
/*     */ 
/*  91 */     public static final ByteToBytePixelConverter premult = new ToByteBgrfConv(ByteBgraPre.setter);
/*     */ 
/*     */     ToByteBgrfConv(BytePixelSetter paramBytePixelSetter)
/*     */     {
/*  95 */       super(paramBytePixelSetter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 103 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 105 */         for (int i = 0; i < paramInt5; i++) {
/* 106 */           int j = paramArrayOfByte1[(paramInt1 + i)];
/* 107 */           paramArrayOfByte2[(paramInt3++)] = j;
/* 108 */           paramArrayOfByte2[(paramInt3++)] = j;
/* 109 */           paramArrayOfByte2[(paramInt3++)] = j;
/* 110 */           paramArrayOfByte2[(paramInt3++)] = -1;
/*     */         }
/* 112 */         paramInt1 += paramInt2;
/* 113 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 122 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 124 */         for (int i = 0; i < paramInt5; i++) {
/* 125 */           byte b = paramByteBuffer1.get(paramInt1 + i);
/* 126 */           paramByteBuffer2.put(paramInt3, b);
/* 127 */           paramByteBuffer2.put(paramInt3 + 1, b);
/* 128 */           paramByteBuffer2.put(paramInt3 + 2, b);
/* 129 */           paramByteBuffer2.put(paramInt3 + 3, (byte)-1);
/* 130 */           paramInt3 += 4;
/*     */         }
/* 132 */         paramInt1 += paramInt2;
/* 133 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Getter
/*     */     implements BytePixelGetter
/*     */   {
/*  50 */     static final BytePixelGetter instance = new Getter();
/*     */ 
/*     */     public AlphaType getAlphaType()
/*     */     {
/*  55 */       return AlphaType.OPAQUE;
/*     */     }
/*     */ 
/*     */     public int getNumElements()
/*     */     {
/*  60 */       return 1;
/*     */     }
/*     */ 
/*     */     public int getArgb(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  65 */       int i = paramArrayOfByte[paramInt] & 0xFF;
/*  66 */       return 0xFF000000 | i << 16 | i << 8 | i;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  71 */       int i = paramArrayOfByte[paramInt] & 0xFF;
/*  72 */       return 0xFF000000 | i << 16 | i << 8 | i;
/*     */     }
/*     */ 
/*     */     public int getArgb(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  77 */       int i = paramByteBuffer.get(paramInt) & 0xFF;
/*  78 */       return 0xFF000000 | i << 16 | i << 8 | i;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  83 */       int i = paramByteBuffer.get(paramInt) & 0xFF;
/*  84 */       return 0xFF000000 | i << 16 | i << 8 | i;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.ByteGray
 * JD-Core Version:    0.6.2
 */