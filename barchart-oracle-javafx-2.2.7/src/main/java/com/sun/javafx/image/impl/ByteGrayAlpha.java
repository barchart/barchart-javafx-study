/*     */ package com.sun.javafx.image.impl;
/*     */ 
/*     */ import com.sun.javafx.image.AlphaType;
/*     */ import com.sun.javafx.image.BytePixelAccessor;
/*     */ import com.sun.javafx.image.BytePixelGetter;
/*     */ import com.sun.javafx.image.BytePixelSetter;
/*     */ import com.sun.javafx.image.ByteToBytePixelConverter;
/*     */ import com.sun.javafx.image.PixelUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public class ByteGrayAlpha
/*     */ {
/*  37 */   public static final BytePixelGetter getter = Accessor.nonpremul;
/*  38 */   public static final BytePixelSetter setter = Accessor.nonpremul;
/*  39 */   public static final BytePixelAccessor accessor = Accessor.nonpremul;
/*     */ 
/*  41 */   public static final ByteToBytePixelConverter ToByteGrayAlphaPre = ToByteGrayAlphaPreConv.instance;
/*     */ 
/*  43 */   public static final ByteToBytePixelConverter ToByteBgra = ToByteBgraSameConv.nonpremul;
/*     */ 
/*     */   static class ToByteBgraSameConv extends BaseByteToByteConverter
/*     */   {
/* 196 */     static final ByteToBytePixelConverter nonpremul = new ToByteBgraSameConv(false);
/*     */ 
/* 198 */     static final ByteToBytePixelConverter premul = new ToByteBgraSameConv(true);
/*     */ 
/*     */     private ToByteBgraSameConv(boolean paramBoolean)
/*     */     {
/* 202 */       super(paramBoolean ? ByteBgraPre.setter : ByteBgra.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 211 */       paramInt2 -= paramInt5 * 2;
/* 212 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 214 */         for (int i = 0; i < paramInt5; i++) {
/* 215 */           int j = paramArrayOfByte1[(paramInt1++)];
/* 216 */           int k = paramArrayOfByte1[(paramInt1++)];
/* 217 */           paramArrayOfByte2[(paramInt3++)] = j;
/* 218 */           paramArrayOfByte2[(paramInt3++)] = j;
/* 219 */           paramArrayOfByte2[(paramInt3++)] = j;
/* 220 */           paramArrayOfByte2[(paramInt3++)] = k;
/*     */         }
/* 222 */         paramInt1 += paramInt2;
/* 223 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 232 */       paramInt2 -= paramInt5 * 2;
/* 233 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 235 */         for (int i = 0; i < paramInt5; i++) {
/* 236 */           byte b1 = paramByteBuffer1.get(paramInt1++);
/* 237 */           byte b2 = paramByteBuffer1.get(paramInt1++);
/* 238 */           paramByteBuffer2.put(paramInt3++, b1);
/* 239 */           paramByteBuffer2.put(paramInt3++, b1);
/* 240 */           paramByteBuffer2.put(paramInt3++, b1);
/* 241 */           paramByteBuffer2.put(paramInt3++, b2);
/*     */         }
/* 243 */         paramInt1 += paramInt2;
/* 244 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ToByteGrayAlphaPreConv extends BaseByteToByteConverter
/*     */   {
/* 135 */     static final ByteToBytePixelConverter instance = new ToByteGrayAlphaPreConv();
/*     */ 
/*     */     private ToByteGrayAlphaPreConv()
/*     */     {
/* 139 */       super(ByteGrayAlphaPre.setter);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 147 */       paramInt2 -= paramInt5 * 2;
/* 148 */       paramInt4 -= paramInt5 * 2;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 150 */         for (int i = 0; i < paramInt5; i++) {
/* 151 */           int j = paramArrayOfByte1[(paramInt1++)] & 0xFF;
/* 152 */           int k = paramArrayOfByte1[(paramInt1++)];
/* 153 */           if (k != -1) {
/* 154 */             if (k == 0)
/* 155 */               j = 0;
/*     */             else {
/* 157 */               j = (j * (k & 0xFF) + 127) / 255;
/*     */             }
/*     */           }
/* 160 */           paramArrayOfByte2[(paramInt3++)] = ((byte)j);
/* 161 */           paramArrayOfByte2[(paramInt3++)] = ((byte)k);
/*     */         }
/* 163 */         paramInt1 += paramInt2;
/* 164 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 173 */       paramInt2 -= paramInt5 * 2;
/* 174 */       paramInt4 -= paramInt5 * 2;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 176 */         for (int i = 0; i < paramInt5; i++) {
/* 177 */           int j = paramByteBuffer1.get(paramInt1++) & 0xFF;
/* 178 */           int k = paramByteBuffer1.get(paramInt1++);
/* 179 */           if (k != -1) {
/* 180 */             if (k == 0)
/* 181 */               j = 0;
/*     */             else {
/* 183 */               j = (j * (k & 0xFF) + 127) / 255;
/*     */             }
/*     */           }
/* 186 */           paramByteBuffer2.put(paramInt3++, (byte)j);
/* 187 */           paramByteBuffer2.put(paramInt3++, (byte)k);
/*     */         }
/* 189 */         paramInt1 += paramInt2;
/* 190 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Accessor
/*     */     implements BytePixelAccessor
/*     */   {
/*  47 */     static final BytePixelAccessor nonpremul = new Accessor(false);
/*  48 */     static final BytePixelAccessor premul = new Accessor(true);
/*     */     private boolean isPremult;
/*     */ 
/*     */     private Accessor(boolean paramBoolean)
/*     */     {
/*  52 */       this.isPremult = paramBoolean;
/*     */     }
/*     */ 
/*     */     public AlphaType getAlphaType()
/*     */     {
/*  57 */       return this.isPremult ? AlphaType.PREMULTIPLIED : AlphaType.NONPREMULTIPLIED;
/*     */     }
/*     */ 
/*     */     public int getNumElements()
/*     */     {
/*  62 */       return 2;
/*     */     }
/*     */ 
/*     */     public int getArgb(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  67 */       int i = paramArrayOfByte[paramInt] & 0xFF;
/*  68 */       int j = paramArrayOfByte[(paramInt + 1)] & 0xFF;
/*  69 */       if (this.isPremult) i = PixelUtils.PreToNonPre(i, j);
/*  70 */       return j << 24 | i << 16 | i << 8 | i;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  75 */       int i = paramArrayOfByte[paramInt] & 0xFF;
/*  76 */       int j = paramArrayOfByte[(paramInt + 1)] & 0xFF;
/*  77 */       if (!this.isPremult) i = PixelUtils.NonPretoPre(i, j);
/*  78 */       return j << 24 | i << 16 | i << 8 | i;
/*     */     }
/*     */ 
/*     */     public int getArgb(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  83 */       int i = paramByteBuffer.get(paramInt) & 0xFF;
/*  84 */       int j = paramByteBuffer.get(paramInt + 1) & 0xFF;
/*  85 */       if (this.isPremult) i = PixelUtils.PreToNonPre(i, j);
/*  86 */       return j << 24 | i << 16 | i << 8 | i;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  91 */       int i = paramByteBuffer.get(paramInt) & 0xFF;
/*  92 */       int j = paramByteBuffer.get(paramInt + 1) & 0xFF;
/*  93 */       if (!this.isPremult) i = PixelUtils.NonPretoPre(i, j);
/*  94 */       return j << 24 | i << 16 | i << 8 | i;
/*     */     }
/*     */ 
/*     */     public void setArgb(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     {
/*  99 */       int i = PixelUtils.RgbToGray(paramInt2);
/* 100 */       int j = paramInt2 >>> 24;
/* 101 */       if (this.isPremult) i = PixelUtils.NonPretoPre(i, j);
/* 102 */       paramArrayOfByte[paramInt1] = ((byte)i);
/* 103 */       paramArrayOfByte[(paramInt1 + 1)] = ((byte)j);
/*     */     }
/*     */ 
/*     */     public void setArgbPre(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     {
/* 108 */       int i = PixelUtils.RgbToGray(paramInt2);
/* 109 */       int j = paramInt2 >>> 24;
/* 110 */       if (!this.isPremult) i = PixelUtils.PreToNonPre(i, j);
/* 111 */       paramArrayOfByte[paramInt1] = ((byte)i);
/* 112 */       paramArrayOfByte[(paramInt1 + 1)] = ((byte)j);
/*     */     }
/*     */ 
/*     */     public void setArgb(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/*     */     {
/* 117 */       int i = PixelUtils.RgbToGray(paramInt2);
/* 118 */       int j = paramInt2 >>> 24;
/* 119 */       if (this.isPremult) i = PixelUtils.NonPretoPre(i, j);
/* 120 */       paramByteBuffer.put(paramInt1, (byte)i);
/* 121 */       paramByteBuffer.put(paramInt1 + 1, (byte)j);
/*     */     }
/*     */ 
/*     */     public void setArgbPre(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/*     */     {
/* 126 */       int i = PixelUtils.RgbToGray(paramInt2);
/* 127 */       int j = paramInt2 >>> 24;
/* 128 */       if (!this.isPremult) i = PixelUtils.PreToNonPre(i, j);
/* 129 */       paramByteBuffer.put(paramInt1, (byte)i);
/* 130 */       paramByteBuffer.put(paramInt1 + 1, (byte)j);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.ByteGrayAlpha
 * JD-Core Version:    0.6.2
 */