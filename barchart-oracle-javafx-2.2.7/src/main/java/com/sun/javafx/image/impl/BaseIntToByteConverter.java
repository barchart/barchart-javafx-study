/*     */ package com.sun.javafx.image.impl;
/*     */ 
/*     */ import com.sun.javafx.image.BytePixelSetter;
/*     */ import com.sun.javafx.image.IntPixelGetter;
/*     */ import com.sun.javafx.image.IntToBytePixelConverter;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public abstract class BaseIntToByteConverter
/*     */   implements IntToBytePixelConverter
/*     */ {
/*     */   protected final IntPixelGetter getter;
/*     */   protected final BytePixelSetter setter;
/*     */   protected final int nDstElems;
/*     */ 
/*     */   BaseIntToByteConverter(IntPixelGetter paramIntPixelGetter, BytePixelSetter paramBytePixelSetter)
/*     */   {
/*  42 */     this.getter = paramIntPixelGetter;
/*  43 */     this.setter = paramBytePixelSetter;
/*  44 */     this.nDstElems = paramBytePixelSetter.getNumElements();
/*     */   }
/*     */ 
/*     */   public final IntPixelGetter getGetter()
/*     */   {
/*  49 */     return this.getter;
/*     */   }
/*     */ 
/*     */   public final BytePixelSetter getSetter()
/*     */   {
/*  54 */     return this.setter;
/*     */   }
/*     */ 
/*     */   abstract void doConvert(int[] paramArrayOfInt, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   abstract void doConvert(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   public final void convert(int[] paramArrayOfInt, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/*  70 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/*  71 */     if ((paramInt2 == paramInt5) && (paramInt4 == paramInt5 * this.nDstElems))
/*     */     {
/*  74 */       paramInt5 *= paramInt6;
/*  75 */       paramInt6 = 1;
/*     */     }
/*  77 */     doConvert(paramArrayOfInt, paramInt1, paramInt2, paramArrayOfByte, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */   }
/*     */ 
/*     */   public final void convert(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/*  87 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/*  88 */     if ((paramInt2 == paramInt5) && (paramInt4 == paramInt5 * this.nDstElems))
/*     */     {
/*  91 */       paramInt5 *= paramInt6;
/*  92 */       paramInt6 = 1;
/*     */     }
/*  94 */     if ((paramIntBuffer.hasArray()) && (paramByteBuffer.hasArray())) {
/*  95 */       doConvert(paramIntBuffer.array(), paramInt1, paramInt2, paramByteBuffer.array(), paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/*  99 */       doConvert(paramIntBuffer, paramInt1, paramInt2, paramByteBuffer, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void convert(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 110 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/* 111 */     if ((paramInt2 == paramInt5) && (paramInt4 == paramInt5 * this.nDstElems))
/*     */     {
/* 114 */       paramInt5 *= paramInt6;
/* 115 */       paramInt6 = 1;
/*     */     }
/* 117 */     if (paramIntBuffer.hasArray()) {
/* 118 */       doConvert(paramIntBuffer.array(), paramInt1, paramInt2, paramArrayOfByte, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/* 122 */       ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
/* 123 */       doConvert(paramIntBuffer, paramInt1, paramInt2, localByteBuffer, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void convert(int[] paramArrayOfInt, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 134 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/* 135 */     if ((paramInt2 == paramInt5) && (paramInt4 == paramInt5 * this.nDstElems))
/*     */     {
/* 138 */       paramInt5 *= paramInt6;
/* 139 */       paramInt6 = 1;
/*     */     }
/* 141 */     if (paramByteBuffer.hasArray()) {
/* 142 */       doConvert(paramArrayOfInt, paramInt1, paramInt2, paramByteBuffer.array(), paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/* 146 */       IntBuffer localIntBuffer = IntBuffer.wrap(paramArrayOfInt);
/* 147 */       doConvert(localIntBuffer, paramInt1, paramInt2, paramByteBuffer, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.BaseIntToByteConverter
 * JD-Core Version:    0.6.2
 */