/*     */ package com.sun.javafx.image.impl;
/*     */ 
/*     */ import com.sun.javafx.image.IntPixelAccessor;
/*     */ import com.sun.javafx.image.IntPixelGetter;
/*     */ import com.sun.javafx.image.IntPixelSetter;
/*     */ import com.sun.javafx.image.IntToIntPixelConverter;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public abstract class BaseIntToIntConverter
/*     */   implements IntToIntPixelConverter
/*     */ {
/*     */   protected final IntPixelGetter getter;
/*     */   protected final IntPixelSetter setter;
/*     */ 
/*     */   public BaseIntToIntConverter(IntPixelGetter paramIntPixelGetter, IntPixelSetter paramIntPixelSetter)
/*     */   {
/*  41 */     this.getter = paramIntPixelGetter;
/*  42 */     this.setter = paramIntPixelSetter;
/*     */   }
/*     */ 
/*     */   public final IntPixelGetter getGetter()
/*     */   {
/*  47 */     return this.getter;
/*     */   }
/*     */ 
/*     */   public final IntPixelSetter getSetter()
/*     */   {
/*  52 */     return this.setter;
/*     */   }
/*     */ 
/*     */   abstract void doConvert(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   abstract void doConvert(IntBuffer paramIntBuffer1, int paramInt1, int paramInt2, IntBuffer paramIntBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   public final void convert(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/*  68 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/*  69 */     if ((paramInt2 == paramInt5) && (paramInt4 == paramInt5))
/*     */     {
/*  72 */       paramInt5 *= paramInt6;
/*  73 */       paramInt6 = 1;
/*     */     }
/*  75 */     doConvert(paramArrayOfInt1, paramInt1, paramInt2, paramArrayOfInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */   }
/*     */ 
/*     */   public final void convert(IntBuffer paramIntBuffer1, int paramInt1, int paramInt2, IntBuffer paramIntBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/*  85 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/*  86 */     if ((paramInt2 == paramInt5) && (paramInt4 == paramInt5))
/*     */     {
/*  89 */       paramInt5 *= paramInt6;
/*  90 */       paramInt6 = 1;
/*     */     }
/*  92 */     if ((paramIntBuffer1.hasArray()) && (paramIntBuffer2.hasArray())) {
/*  93 */       doConvert(paramIntBuffer1.array(), paramInt1, paramInt2, paramIntBuffer2.array(), paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/*  97 */       doConvert(paramIntBuffer1, paramInt1, paramInt2, paramIntBuffer2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void convert(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 108 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/* 109 */     if ((paramInt2 == paramInt5) && (paramInt4 == paramInt5))
/*     */     {
/* 112 */       paramInt5 *= paramInt6;
/* 113 */       paramInt6 = 1;
/*     */     }
/* 115 */     if (paramIntBuffer.hasArray()) {
/* 116 */       doConvert(paramIntBuffer.array(), paramInt1, paramInt2, paramArrayOfInt, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/* 120 */       IntBuffer localIntBuffer = IntBuffer.wrap(paramArrayOfInt);
/* 121 */       doConvert(paramIntBuffer, paramInt1, paramInt2, localIntBuffer, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void convert(int[] paramArrayOfInt, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 132 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) return;
/* 133 */     if ((paramInt2 == paramInt5) && (paramInt4 == paramInt5))
/*     */     {
/* 136 */       paramInt5 *= paramInt6;
/* 137 */       paramInt6 = 1;
/*     */     }
/* 139 */     if (paramIntBuffer.hasArray()) {
/* 140 */       doConvert(paramArrayOfInt, paramInt1, paramInt2, paramIntBuffer.array(), paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     else
/*     */     {
/* 144 */       IntBuffer localIntBuffer = IntBuffer.wrap(paramArrayOfInt);
/* 145 */       doConvert(localIntBuffer, paramInt1, paramInt2, paramIntBuffer, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */   }
/*     */ 
/*     */   static IntToIntPixelConverter create(IntPixelAccessor paramIntPixelAccessor)
/*     */   {
/* 152 */     return new IntAnyToSameConverter(paramIntPixelAccessor);
/*     */   }
/*     */ 
/*     */   static class IntAnyToSameConverter extends BaseIntToIntConverter {
/*     */     IntAnyToSameConverter(IntPixelAccessor paramIntPixelAccessor) {
/* 157 */       super(paramIntPixelAccessor);
/*     */     }
/*     */ 
/*     */     void doConvert(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int[] paramArrayOfInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/*     */       while (true)
/*     */       {
/* 165 */         paramInt6--; if (paramInt6 < 0) break;
/* 166 */         System.arraycopy(paramArrayOfInt1, paramInt1, paramArrayOfInt2, paramInt3, paramInt5);
/* 167 */         paramInt1 += paramInt2;
/* 168 */         paramInt3 += paramInt4;
/*     */       }
/*     */     }
/*     */ 
/*     */     void doConvert(IntBuffer paramIntBuffer1, int paramInt1, int paramInt2, IntBuffer paramIntBuffer2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */     {
/* 177 */       int i = paramIntBuffer1.limit();
/* 178 */       int j = paramIntBuffer1.position();
/* 179 */       int k = paramIntBuffer2.position();
/*     */       try {
/*     */         while (true) { paramInt6--; if (paramInt6 < 0) break;
/* 182 */           int m = paramInt1 + paramInt5;
/* 183 */           if (m > i) {
/* 184 */             throw new IndexOutOfBoundsException("" + i);
/*     */           }
/* 186 */           paramIntBuffer1.limit(m);
/* 187 */           paramIntBuffer1.position(paramInt1);
/* 188 */           paramIntBuffer2.position(paramInt3);
/* 189 */           paramIntBuffer2.put(paramIntBuffer1);
/* 190 */           paramInt1 += paramInt2;
/* 191 */           paramInt3 += paramInt4; }
/*     */       }
/*     */       finally {
/* 194 */         paramIntBuffer1.limit(i);
/* 195 */         paramIntBuffer1.position(j);
/* 196 */         paramIntBuffer2.position(k);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.BaseIntToIntConverter
 * JD-Core Version:    0.6.2
 */