/*     */ package com.sun.javafx.image.impl;
/*     */ 
/*     */ import com.sun.javafx.image.AlphaType;
/*     */ import com.sun.javafx.image.BytePixelAccessor;
/*     */ import com.sun.javafx.image.BytePixelGetter;
/*     */ import com.sun.javafx.image.BytePixelSetter;
/*     */ import com.sun.javafx.image.PixelUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public class ByteArgb
/*     */ {
/*  36 */   public static final BytePixelGetter getter = Accessor.instance;
/*  37 */   public static final BytePixelSetter setter = Accessor.instance;
/*  38 */   public static final BytePixelAccessor accessor = Accessor.instance;
/*     */ 
/*     */   static class Accessor implements BytePixelAccessor {
/*  41 */     static final BytePixelAccessor instance = new Accessor();
/*     */ 
/*     */     public AlphaType getAlphaType()
/*     */     {
/*  46 */       return AlphaType.NONPREMULTIPLIED;
/*     */     }
/*     */ 
/*     */     public int getNumElements()
/*     */     {
/*  51 */       return 4;
/*     */     }
/*     */ 
/*     */     public int getArgb(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  56 */       return paramArrayOfByte[paramInt] << 24 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 3)] & 0xFF;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/*  64 */       return PixelUtils.NonPretoPre(getArgb(paramArrayOfByte, paramInt));
/*     */     }
/*     */ 
/*     */     public int getArgb(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  69 */       return paramByteBuffer.get(paramInt) << 24 | (paramByteBuffer.get(paramInt + 1) & 0xFF) << 16 | (paramByteBuffer.get(paramInt + 2) & 0xFF) << 8 | paramByteBuffer.get(paramInt + 3) & 0xFF;
/*     */     }
/*     */ 
/*     */     public int getArgbPre(ByteBuffer paramByteBuffer, int paramInt)
/*     */     {
/*  77 */       return PixelUtils.NonPretoPre(getArgb(paramByteBuffer, paramInt));
/*     */     }
/*     */ 
/*     */     public void setArgb(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     {
/*  82 */       paramArrayOfByte[paramInt1] = ((byte)(paramInt2 >> 24));
/*  83 */       paramArrayOfByte[(paramInt1 + 1)] = ((byte)(paramInt2 >> 16));
/*  84 */       paramArrayOfByte[(paramInt1 + 2)] = ((byte)(paramInt2 >> 8));
/*  85 */       paramArrayOfByte[(paramInt1 + 3)] = ((byte)paramInt2);
/*     */     }
/*     */ 
/*     */     public void setArgbPre(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     {
/*  90 */       setArgb(paramArrayOfByte, paramInt1, PixelUtils.PretoNonPre(paramInt2));
/*     */     }
/*     */ 
/*     */     public void setArgb(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/*     */     {
/*  95 */       paramByteBuffer.put(paramInt1, (byte)(paramInt2 >> 24));
/*  96 */       paramByteBuffer.put(paramInt1 + 1, (byte)(paramInt2 >> 16));
/*  97 */       paramByteBuffer.put(paramInt1 + 2, (byte)(paramInt2 >> 8));
/*  98 */       paramByteBuffer.put(paramInt1 + 3, (byte)paramInt2);
/*     */     }
/*     */ 
/*     */     public void setArgbPre(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/*     */     {
/* 103 */       setArgb(paramByteBuffer, paramInt1, PixelUtils.PretoNonPre(paramInt2));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.impl.ByteArgb
 * JD-Core Version:    0.6.2
 */