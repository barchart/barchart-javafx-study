/*     */ package com.sun.javafx.image.impl;
/*     */ 
/*     */ import com.sun.javafx.image.AlphaType;
/*     */ import com.sun.javafx.image.BytePixelGetter;
/*     */ import com.sun.javafx.image.BytePixelSetter;
/*     */ import com.sun.javafx.image.ByteToBytePixelConverter;
/*     */ import com.sun.javafx.image.ByteToIntPixelConverter;
/*     */ import com.sun.javafx.image.IntPixelGetter;
/*     */ import com.sun.javafx.image.IntPixelSetter;
/*     */ import com.sun.javafx.image.IntToBytePixelConverter;
/*     */ import com.sun.javafx.image.IntToIntPixelConverter;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public class General
/*     */ {
/*     */   public static ByteToBytePixelConverter create(BytePixelGetter paramBytePixelGetter, BytePixelSetter paramBytePixelSetter)
/*     */   {
/*  44 */     return new ByteToByteGeneralConverter(paramBytePixelGetter, paramBytePixelSetter);
/*     */   }
/*     */ 
/*     */   public static ByteToIntPixelConverter create(BytePixelGetter paramBytePixelGetter, IntPixelSetter paramIntPixelSetter)
/*     */   {
/*  50 */     return new ByteToIntGeneralConverter(paramBytePixelGetter, paramIntPixelSetter);
/*     */   }
/*     */ 
/*     */   public static IntToBytePixelConverter create(IntPixelGetter paramIntPixelGetter, BytePixelSetter paramBytePixelSetter)
/*     */   {
/*  56 */     return new IntToByteGeneralConverter(paramIntPixelGetter, paramBytePixelSetter);
/*     */   }
/*     */ 
/*     */   public static IntToIntPixelConverter create(IntPixelGetter paramIntPixelGetter, IntPixelSetter paramIntPixelSetter)
/*     */   {
/*  62 */     return new IntToIntGeneralConverter(paramIntPixelGetter, paramIntPixelSetter);
/*     */   }
/*     */ 
/*     */   static class IntToIntGeneralConverter extends BaseIntToIntConverter
/*     */   {
/*     */     boolean usePremult;
/*     */ 
/*     */     public IntToIntGeneralConverter(IntPixelGetter paramIntPixelGetter, IntPixelSetter paramIntPixelSetter)
/*     */     {
/* 229 */       super(paramIntPixelSetter);
/* 230 */       this.usePremult = ((paramIntPixelGetter.getAlphaType() != AlphaType.NONPREMULTIPLIED) && (paramIntPixelSetter.getAlphaType() != AlphaType.NONPREMULTIPLIED));
/*     */     }
/*     */ 
/*     */     void doConvert(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 239 */       paramInt2 -= paramInt5;
/* 240 */       paramInt4 -= paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 242 */         for (int i = 0; i < paramInt5; i++) {
/* 243 */           if (this.usePremult)
/* 244 */             this.setter.setArgbPre(paramArrayOfInt2, paramInt3, this.getter.getArgbPre(paramArrayOfInt1, paramInt1));
/*     */           else {
/* 246 */             this.setter.setArgb(paramArrayOfInt2, paramInt3, this.getter.getArgb(paramArrayOfInt1, paramInt1));
/*     */           }
/* 248 */           paramInt1++;
/* 249 */           paramInt3++;
/*     */         }
/* 251 */         paramInt1 += paramInt2;
/* 252 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(IntBuffer paramIntBuffer1, int paramInt1, int paramInt2, IntBuffer paramIntBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 261 */       paramInt2 -= paramInt5;
/* 262 */       paramInt4 -= paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 264 */         for (int i = 0; i < paramInt5; i++) {
/* 265 */           if (this.usePremult)
/* 266 */             this.setter.setArgbPre(paramIntBuffer2, paramInt3, this.getter.getArgbPre(paramIntBuffer1, paramInt1));
/*     */           else {
/* 268 */             this.setter.setArgb(paramIntBuffer2, paramInt3, this.getter.getArgb(paramIntBuffer1, paramInt1));
/*     */           }
/* 270 */           paramInt1++;
/* 271 */           paramInt3++;
/*     */         }
/* 273 */         paramInt1 += paramInt2;
/* 274 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class IntToByteGeneralConverter extends BaseIntToByteConverter
/*     */   {
/*     */     boolean usePremult;
/*     */ 
/*     */     public IntToByteGeneralConverter(IntPixelGetter paramIntPixelGetter, BytePixelSetter paramBytePixelSetter)
/*     */     {
/* 176 */       super(paramBytePixelSetter);
/* 177 */       this.usePremult = ((paramIntPixelGetter.getAlphaType() != AlphaType.NONPREMULTIPLIED) && (paramBytePixelSetter.getAlphaType() != AlphaType.NONPREMULTIPLIED));
/*     */     }
/*     */ 
/*     */     void doConvert(int[] paramArrayOfInt, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 186 */       paramInt2 -= paramInt5;
/* 187 */       paramInt4 -= this.nDstElems * paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 189 */         for (int i = 0; i < paramInt5; i++) {
/* 190 */           if (this.usePremult)
/* 191 */             this.setter.setArgbPre(paramArrayOfByte, paramInt3, this.getter.getArgbPre(paramArrayOfInt, paramInt1));
/*     */           else {
/* 193 */             this.setter.setArgb(paramArrayOfByte, paramInt3, this.getter.getArgb(paramArrayOfInt, paramInt1));
/*     */           }
/* 195 */           paramInt1++;
/* 196 */           paramInt3 += this.nDstElems;
/*     */         }
/* 198 */         paramInt1 += paramInt2;
/* 199 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 208 */       paramInt2 -= paramInt5;
/* 209 */       paramInt4 -= this.nDstElems * paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 211 */         for (int i = 0; i < paramInt5; i++) {
/* 212 */           if (this.usePremult)
/* 213 */             this.setter.setArgbPre(paramByteBuffer, paramInt3, this.getter.getArgbPre(paramIntBuffer, paramInt1));
/*     */           else {
/* 215 */             this.setter.setArgb(paramByteBuffer, paramInt3, this.getter.getArgb(paramIntBuffer, paramInt1));
/*     */           }
/* 217 */           paramInt1++;
/* 218 */           paramInt3 += this.nDstElems;
/*     */         }
/* 220 */         paramInt1 += paramInt2;
/* 221 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ByteToIntGeneralConverter extends BaseByteToIntConverter
/*     */   {
/*     */     boolean usePremult;
/*     */ 
/*     */     ByteToIntGeneralConverter(BytePixelGetter paramBytePixelGetter, IntPixelSetter paramIntPixelSetter)
/*     */     {
/* 123 */       super(paramIntPixelSetter);
/* 124 */       this.usePremult = ((paramBytePixelGetter.getAlphaType() != AlphaType.NONPREMULTIPLIED) && (paramIntPixelSetter.getAlphaType() != AlphaType.NONPREMULTIPLIED));
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 133 */       paramInt2 -= this.nSrcElems * paramInt5;
/* 134 */       paramInt4 -= paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 136 */         for (int i = 0; i < paramInt5; i++) {
/* 137 */           if (this.usePremult)
/* 138 */             this.setter.setArgbPre(paramArrayOfInt, paramInt3, this.getter.getArgbPre(paramArrayOfByte, paramInt1));
/*     */           else {
/* 140 */             this.setter.setArgb(paramArrayOfInt, paramInt3, this.getter.getArgb(paramArrayOfByte, paramInt1));
/*     */           }
/* 142 */           paramInt1 += this.nSrcElems;
/* 143 */           paramInt3++;
/*     */         }
/* 145 */         paramInt1 += paramInt2;
/* 146 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 155 */       paramInt2 -= this.nSrcElems * paramInt5;
/* 156 */       paramInt4 -= paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 158 */         for (int i = 0; i < paramInt5; i++) {
/* 159 */           if (this.usePremult)
/* 160 */             this.setter.setArgbPre(paramIntBuffer, paramInt3, this.getter.getArgbPre(paramByteBuffer, paramInt1));
/*     */           else {
/* 162 */             this.setter.setArgb(paramIntBuffer, paramInt3, this.getter.getArgb(paramByteBuffer, paramInt1));
/*     */           }
/* 164 */           paramInt1 += this.nSrcElems;
/* 165 */           paramInt3++;
/*     */         }
/* 167 */         paramInt1 += paramInt2;
/* 168 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ByteToByteGeneralConverter extends BaseByteToByteConverter
/*     */   {
/*     */     boolean usePremult;
/*     */ 
/*     */     ByteToByteGeneralConverter(BytePixelGetter paramBytePixelGetter, BytePixelSetter paramBytePixelSetter)
/*     */     {
/*  69 */       super(paramBytePixelSetter);
/*  70 */       this.usePremult = ((paramBytePixelGetter.getAlphaType() != AlphaType.NONPREMULTIPLIED) && (paramBytePixelSetter.getAlphaType() != AlphaType.NONPREMULTIPLIED));
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/*  79 */       paramInt2 -= this.nSrcElems * paramInt5;
/*  80 */       paramInt4 -= this.nDstElems * paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/*  82 */         for (int i = 0; i < paramInt5; i++) {
/*  83 */           if (this.usePremult)
/*  84 */             this.setter.setArgbPre(paramArrayOfByte2, paramInt3, this.getter.getArgbPre(paramArrayOfByte1, paramInt1));
/*     */           else {
/*  86 */             this.setter.setArgb(paramArrayOfByte2, paramInt3, this.getter.getArgb(paramArrayOfByte1, paramInt1));
/*     */           }
/*  88 */           paramInt1 += this.nSrcElems;
/*  89 */           paramInt3 += this.nDstElems;
/*     */         }
/*  91 */         paramInt1 += paramInt2;
/*  92 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 101 */       paramInt2 -= this.nSrcElems * paramInt5;
/* 102 */       paramInt4 -= this.nDstElems * paramInt5;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 104 */         for (int i = 0; i < paramInt5; i++) {
/* 105 */           if (this.usePremult)
/* 106 */             this.setter.setArgbPre(paramByteBuffer2, paramInt3, this.getter.getArgbPre(paramByteBuffer1, paramInt1));
/*     */           else {
/* 108 */             this.setter.setArgb(paramByteBuffer2, paramInt3, this.getter.getArgb(paramByteBuffer1, paramInt1));
/*     */           }
/* 110 */           paramInt1 += this.nSrcElems;
/* 111 */           paramInt3 += this.nDstElems;
/*     */         }
/* 113 */         paramInt1 += paramInt2;
/* 114 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.General
 * JD-Core Version:    0.6.2
 */