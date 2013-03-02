/*     */ package com.sun.javafx.iio;
/*     */ 
/*     */ public class ImageMetadata
/*     */ {
/*     */   public final Float gamma;
/*     */   public final Boolean blackIsZero;
/*     */   public final Integer backgroundIndex;
/*     */   public final Integer backgroundColor;
/*     */   public final Integer delayTime;
/*     */   public final Integer transparentIndex;
/*     */   public final Integer imageWidth;
/*     */   public final Integer imageHeight;
/*     */   public final Integer imageLeftPosition;
/*     */   public final Integer imageTopPosition;
/*     */   public final Integer disposalMethod;
/*     */ 
/*     */   public ImageMetadata(Float paramFloat, Boolean paramBoolean, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5, Integer paramInteger6, Integer paramInteger7, Integer paramInteger8, Integer paramInteger9)
/*     */   {
/*  95 */     this.gamma = paramFloat;
/*  96 */     this.blackIsZero = paramBoolean;
/*  97 */     this.backgroundIndex = paramInteger1;
/*  98 */     this.backgroundColor = paramInteger2;
/*  99 */     this.transparentIndex = paramInteger3;
/* 100 */     this.delayTime = paramInteger4;
/* 101 */     this.imageWidth = paramInteger5;
/* 102 */     this.imageHeight = paramInteger6;
/* 103 */     this.imageLeftPosition = paramInteger7;
/* 104 */     this.imageTopPosition = paramInteger8;
/* 105 */     this.disposalMethod = paramInteger9;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 110 */     StringBuffer localStringBuffer = new StringBuffer("[" + getClass().getName());
/* 111 */     if (this.gamma != null) {
/* 112 */       localStringBuffer.append(" gamma: " + this.gamma);
/*     */     }
/* 114 */     if (this.blackIsZero != null) {
/* 115 */       localStringBuffer.append(" blackIsZero: " + this.blackIsZero);
/*     */     }
/* 117 */     if (this.backgroundIndex != null) {
/* 118 */       localStringBuffer.append(" backgroundIndex: " + this.backgroundIndex);
/*     */     }
/* 120 */     if (this.backgroundColor != null) {
/* 121 */       localStringBuffer.append(" backgroundColor: " + this.backgroundColor);
/*     */     }
/* 123 */     if (this.delayTime != null) {
/* 124 */       localStringBuffer.append(" delayTime: " + this.delayTime);
/*     */     }
/* 126 */     if (this.transparentIndex != null) {
/* 127 */       localStringBuffer.append(" transparentIndex: " + this.transparentIndex);
/*     */     }
/* 129 */     if (this.imageWidth != null) {
/* 130 */       localStringBuffer.append(" imageWidth: " + this.imageWidth);
/*     */     }
/* 132 */     if (this.imageHeight != null) {
/* 133 */       localStringBuffer.append(" imageHeight: " + this.imageHeight);
/*     */     }
/* 135 */     if (this.imageLeftPosition != null) {
/* 136 */       localStringBuffer.append(" imageLeftPosition: " + this.imageLeftPosition);
/*     */     }
/* 138 */     if (this.imageTopPosition != null) {
/* 139 */       localStringBuffer.append(" imageTopPosition: " + this.imageTopPosition);
/*     */     }
/* 141 */     if (this.disposalMethod != null) {
/* 142 */       localStringBuffer.append(" disposalMethod: " + this.disposalMethod);
/*     */     }
/* 144 */     localStringBuffer.append("]");
/* 145 */     return localStringBuffer.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.ImageMetadata
 * JD-Core Version:    0.6.2
 */