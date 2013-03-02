/*     */ package com.sun.javafx.image.impl;
/*     */ 
/*     */ import com.sun.javafx.image.BytePixelGetter;
/*     */ import com.sun.javafx.image.ByteToIntPixelConverter;
/*     */ import com.sun.javafx.image.IntPixelSetter;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public abstract class BaseByteToIntConverter
/*     */   implements ByteToIntPixelConverter
/*     */ {
/*     */   protected final BytePixelGetter getter;
/*     */   protected final IntPixelSetter setter;
/*     */   protected final int nSrcElems;
/*     */ 
/*     */   BaseByteToIntConverter(BytePixelGetter paramBytePixelGetter, IntPixelSetter paramIntPixelSetter)
/*     */   {
/*  42 */     this.getter = paramBytePixelGetter;
/*  43 */     this.setter = paramIntPixelSetter;
/*  44 */     this.nSrcElems = paramBytePixelGetter.getNumElements();
/*     */   }
/*     */ 
/*     */   public final BytePixelGetter getGetter()
/*     */   {
/*  49 */     return this.getter;
/*     */   }
/*     */ 
/*     */   public final IntPixelSetter getSetter()
/*     */   {
/*  54 */     return this.setter;
/*     */   }
/*     */ 
/*     */   abstract void doConvert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   abstract void doConvert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   public final void convert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/*  70 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/*  71 */     if ((paramInt2 == paramInt5 * this.nSrcElems) && (paramInt4 == paramInt5))
/*     */     {
/*  74 */       paramInt5 *= paramInt6;
/*  75 */       paramInt6 = 1;
/*     */     }
/*  77 */     doConvert(paramArrayOfByte, paramInt1, paramInt2, paramArrayOfInt, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */   }
/*     */ 
/*     */   public final void convert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/*  87 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/*  88 */     if ((paramInt2 == paramInt5 * this.nSrcElems) && (paramInt4 == paramInt5))
/*     */     {
/*  91 */       paramInt5 *= paramInt6;
/*  92 */       paramInt6 = 1;
/*     */     }
/*  94 */     if ((paramByteBuffer.hasArray()) && (paramIntBuffer.hasArray())) {
/*  95 */       doConvert(paramByteBuffer.array(), paramInt1, paramInt2, paramIntBuffer.array(), paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/*  99 */       doConvert(paramByteBuffer, paramInt1, paramInt2, paramIntBuffer, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void convert(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 110 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/* 111 */     if ((paramInt2 == paramInt5 * this.nSrcElems) && (paramInt4 == paramInt5))
/*     */     {
/* 114 */       paramInt5 *= paramInt6;
/* 115 */       paramInt6 = 1;
/*     */     }
/* 117 */     if (paramByteBuffer.hasArray()) {
/* 118 */       doConvert(paramByteBuffer.array(), paramInt1, paramInt2, paramArrayOfInt, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/* 122 */       IntBuffer localIntBuffer = IntBuffer.wrap(paramArrayOfInt);
/* 123 */       doConvert(paramByteBuffer, paramInt1, paramInt2, localIntBuffer, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void convert(byte[] paramArrayOfByte, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 134 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/* 135 */     if ((paramInt2 == paramInt5 * this.nSrcElems) && (paramInt4 == paramInt5))
/*     */     {
/* 138 */       paramInt5 *= paramInt6;
/* 139 */       paramInt6 = 1;
/*     */     }
/* 141 */     if (paramIntBuffer.hasArray()) {
/* 142 */       doConvert(paramArrayOfByte, paramInt1, paramInt2, paramIntBuffer.array(), paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/* 146 */       ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
/* 147 */       doConvert(localByteBuffer, paramInt1, paramInt2, paramIntBuffer, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.BaseByteToIntConverter
 * JD-Core Version:    0.6.2
 */