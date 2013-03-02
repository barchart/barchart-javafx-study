/*     */ package com.sun.javafx.image.impl;
/*     */ 
/*     */ import com.sun.javafx.image.BytePixelAccessor;
/*     */ import com.sun.javafx.image.BytePixelGetter;
/*     */ import com.sun.javafx.image.BytePixelSetter;
/*     */ import com.sun.javafx.image.ByteToBytePixelConverter;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ abstract class BaseByteToByteConverter
/*     */   implements ByteToBytePixelConverter
/*     */ {
/*     */   protected final BytePixelGetter getter;
/*     */   protected final BytePixelSetter setter;
/*     */   protected final int nSrcElems;
/*     */   protected final int nDstElems;
/*     */ 
/*     */   BaseByteToByteConverter(BytePixelGetter paramBytePixelGetter, BytePixelSetter paramBytePixelSetter)
/*     */   {
/*  43 */     this.getter = paramBytePixelGetter;
/*  44 */     this.setter = paramBytePixelSetter;
/*  45 */     this.nSrcElems = paramBytePixelGetter.getNumElements();
/*  46 */     this.nDstElems = paramBytePixelSetter.getNumElements();
/*     */   }
/*     */ 
/*     */   public final BytePixelGetter getGetter()
/*     */   {
/*  51 */     return this.getter;
/*     */   }
/*     */ 
/*     */   public final BytePixelSetter getSetter()
/*     */   {
/*  56 */     return this.setter;
/*     */   }
/*     */ 
/*     */   abstract void doConvert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   abstract void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   public final void convert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/*  72 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/*  73 */     if ((paramInt2 == paramInt5 * this.nSrcElems) && (paramInt4 == paramInt5 * this.nDstElems))
/*     */     {
/*  76 */       paramInt5 *= paramInt6;
/*  77 */       paramInt6 = 1;
/*     */     }
/*  79 */     doConvert(paramArrayOfByte1, paramInt1, paramInt2, paramArrayOfByte2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */   }
/*     */ 
/*     */   public final void convert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/*  89 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/*  90 */     if ((paramInt2 == paramInt5 * this.nSrcElems) && (paramInt4 == paramInt5 * this.nDstElems))
/*     */     {
/*  93 */       paramInt5 *= paramInt6;
/*  94 */       paramInt6 = 1;
/*     */     }
/*  96 */     if ((paramByteBuffer1.hasArray()) && (paramByteBuffer2.hasArray())) {
/*  97 */       doConvert(paramByteBuffer1.array(), paramInt1, paramInt2, paramByteBuffer2.array(), paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/* 101 */       doConvert(paramByteBuffer1, paramInt1, paramInt2, paramByteBuffer2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void convert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 112 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/* 113 */     if ((paramInt2 == paramInt5 * this.nSrcElems) && (paramInt4 == paramInt5 * this.nDstElems))
/*     */     {
/* 116 */       paramInt5 *= paramInt6;
/* 117 */       paramInt6 = 1;
/*     */     }
/* 119 */     if (paramByteBuffer.hasArray()) {
/* 120 */       doConvert(paramByteBuffer.array(), paramInt1, paramInt2, paramArrayOfByte, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/* 124 */       ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
/* 125 */       doConvert(paramByteBuffer, paramInt1, paramInt2, localByteBuffer, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void convert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 136 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/* 137 */     if ((paramInt2 == paramInt5 * this.nSrcElems) && (paramInt4 == paramInt5 * this.nDstElems))
/*     */     {
/* 140 */       paramInt5 *= paramInt6;
/* 141 */       paramInt6 = 1;
/*     */     }
/* 143 */     if (paramByteBuffer.hasArray()) {
/* 144 */       doConvert(paramArrayOfByte, paramInt1, paramInt2, paramByteBuffer.array(), paramByteBuffer.arrayOffset(), paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/* 148 */       ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
/* 149 */       doConvert(localByteBuffer, paramInt1, paramInt2, paramByteBuffer, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ 
/*     */   static ByteToBytePixelConverter create(BytePixelAccessor paramBytePixelAccessor)
/*     */   {
/* 156 */     return new ByteAnyToSameConverter(paramBytePixelAccessor);
/*     */   }
/*     */ 
/*     */   public static ByteToBytePixelConverter createReorderer(BytePixelGetter paramBytePixelGetter, BytePixelSetter paramBytePixelSetter, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 209 */     return new FourByteReorderer(paramBytePixelGetter, paramBytePixelSetter, paramInt1, paramInt2, paramInt3, paramInt4); } 
/*     */   static class FourByteReorderer extends BaseByteToByteConverter { private final int c0;
/*     */     private final int c1;
/*     */     private final int c2;
/*     */     private final int c3;
/*     */ 
/* 218 */     FourByteReorderer(BytePixelGetter paramBytePixelGetter, BytePixelSetter paramBytePixelSetter, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { super(paramBytePixelSetter);
/* 219 */       this.c0 = paramInt1;
/* 220 */       this.c1 = paramInt2;
/* 221 */       this.c2 = paramInt3;
/* 222 */       this.c3 = paramInt4;
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 230 */       paramInt2 -= paramInt5 * 4;
/* 231 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 233 */         for (int i = 0; i < paramInt5; i++)
/*     */         {
/* 236 */           int j = paramArrayOfByte1[(paramInt1 + this.c0)];
/* 237 */           int k = paramArrayOfByte1[(paramInt1 + this.c1)];
/* 238 */           int m = paramArrayOfByte1[(paramInt1 + this.c2)];
/* 239 */           int n = paramArrayOfByte1[(paramInt1 + this.c3)];
/* 240 */           paramArrayOfByte2[(paramInt3++)] = j;
/* 241 */           paramArrayOfByte2[(paramInt3++)] = k;
/* 242 */           paramArrayOfByte2[(paramInt3++)] = m;
/* 243 */           paramArrayOfByte2[(paramInt3++)] = n;
/* 244 */           paramInt1 += 4;
/*     */         }
/* 246 */         paramInt1 += paramInt2;
/* 247 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 256 */       paramInt2 -= paramInt5 * 4;
/* 257 */       paramInt4 -= paramInt5 * 4;
/*     */       while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 259 */         for (int i = 0; i < paramInt5; i++)
/*     */         {
/* 262 */           byte b1 = paramByteBuffer1.get(paramInt1 + this.c0);
/* 263 */           byte b2 = paramByteBuffer1.get(paramInt1 + this.c1);
/* 264 */           byte b3 = paramByteBuffer1.get(paramInt1 + this.c2);
/* 265 */           byte b4 = paramByteBuffer1.get(paramInt1 + this.c3);
/* 266 */           paramByteBuffer2.put(paramInt3, b1);
/* 267 */           paramByteBuffer2.put(paramInt3 + 1, b2);
/* 268 */           paramByteBuffer2.put(paramInt3 + 2, b3);
/* 269 */           paramByteBuffer2.put(paramInt3 + 3, b4);
/* 270 */           paramInt1 += 4;
/* 271 */           paramInt3 += 4;
/*     */         }
/* 273 */         paramInt1 += paramInt2;
/* 274 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ByteAnyToSameConverter extends BaseByteToByteConverter
/*     */   {
/*     */     ByteAnyToSameConverter(BytePixelAccessor paramBytePixelAccessor)
/*     */     {
/* 161 */       super(paramBytePixelAccessor);
/*     */     }
/*     */ 
/*     */     void doConvert(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/*     */       while (true)
/*     */       {
/* 169 */         paramInt6--; if (paramInt6 < 0) break;
/* 170 */         System.arraycopy(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt3, paramInt5 * this.nSrcElems);
/* 171 */         paramInt1 += paramInt2;
/* 172 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(ByteBuffer paramByteBuffer1, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 181 */       int i = paramByteBuffer1.limit();
/* 182 */       int j = paramByteBuffer1.position();
/* 183 */       int k = paramByteBuffer2.position();
/*     */       try {
/*     */         while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 186 */           int m = paramInt1 + paramInt5 * this.nSrcElems;
/* 187 */           if (m > i) {
/* 188 */             throw new IndexOutOfBoundsException("" + i);
/*     */           }
/* 190 */           paramByteBuffer1.limit(m);
/* 191 */           paramByteBuffer1.position(paramInt1);
/* 192 */           paramByteBuffer2.position(paramInt3);
/* 193 */           paramByteBuffer2.put(paramByteBuffer1);
/* 194 */           paramInt1 += paramInt2;
/* 195 */           paramInt3 += paramInt4; }
/*     */       }
/*     */       finally {
/* 198 */         paramByteBuffer1.limit(i);
/* 199 */         paramByteBuffer1.position(j);
/* 200 */         paramByteBuffer2.position(k);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.BaseByteToByteConverter
 * JD-Core Version:    0.6.2
 */