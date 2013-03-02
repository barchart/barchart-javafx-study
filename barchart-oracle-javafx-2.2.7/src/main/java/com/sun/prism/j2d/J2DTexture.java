/*     */ package com.sun.prism.j2d;
/*     */ 
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.MediaFrame;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.Texture.WrapMode;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public class J2DTexture
/*     */   implements Texture
/*     */ {
/*     */   protected BufferedImage bimg;
/*     */   private Updater updater;
/*  23 */   private Texture.WrapMode wrapmode = Texture.WrapMode.CLAMP_TO_ZERO;
/*  24 */   private boolean linearfiltering = true;
/*     */   private int lastImageSerial;
/*     */ 
/*     */   public J2DTexture(PixelFormat paramPixelFormat, int paramInt1, int paramInt2)
/*     */   {
/*     */     int i;
/*  29 */     switch (1.$SwitchMap$com$sun$prism$PixelFormat[paramPixelFormat.ordinal()]) {
/*     */     case 1:
/*  31 */       i = 5;
/*  32 */       this.updater = ThreeByteBgrUpdater.THREE_BYTE_BGR_INSTANCE;
/*  33 */       break;
/*     */     case 2:
/*  35 */       i = 10;
/*  36 */       this.updater = Updater.GENERAL_INSTANCE;
/*  37 */       break;
/*     */     case 3:
/*     */     case 4:
/*  40 */       i = 3;
/*  41 */       this.updater = IntArgbPreUpdater.INT_ARGB_PRE_INSTANCE;
/*  42 */       break;
/*     */     default:
/*  44 */       throw new InternalError("Unrecognized PixelFormat (" + paramPixelFormat + ")!");
/*     */     }
/*  46 */     this.bimg = new BufferedImage(paramInt1, paramInt2, i);
/*     */   }
/*     */ 
/*     */   public void dispose() {
/*  50 */     this.bimg.flush();
/*  51 */     this.bimg = null;
/*     */   }
/*     */ 
/*     */   public BufferedImage getBufferedImage() {
/*  55 */     return this.bimg;
/*     */   }
/*     */ 
/*     */   public PixelFormat getPixelFormat() {
/*  59 */     switch (this.bimg.getType()) {
/*     */     case 5:
/*  61 */       return PixelFormat.BYTE_RGB;
/*     */     case 10:
/*  63 */       return PixelFormat.BYTE_GRAY;
/*     */     case 3:
/*  65 */       return PixelFormat.INT_ARGB_PRE;
/*     */     }
/*  67 */     throw new InternalError("Unrecognized BufferedImage type (" + this.bimg.getType() + ")!");
/*     */   }
/*     */ 
/*     */   public int getPhysicalWidth()
/*     */   {
/*  72 */     return this.bimg.getWidth();
/*     */   }
/*     */ 
/*     */   public int getPhysicalHeight() {
/*  76 */     return this.bimg.getHeight();
/*     */   }
/*     */ 
/*     */   public int getContentX() {
/*  80 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getContentY() {
/*  84 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getContentWidth() {
/*  88 */     return this.bimg.getWidth();
/*     */   }
/*     */ 
/*     */   public int getContentHeight() {
/*  92 */     return this.bimg.getHeight();
/*     */   }
/*     */ 
/*     */   public Texture.WrapMode getWrapMode() {
/*  96 */     return this.wrapmode;
/*     */   }
/*     */ 
/*     */   public void setWrapMode(Texture.WrapMode paramWrapMode) {
/* 100 */     this.wrapmode = paramWrapMode;
/*     */   }
/*     */ 
/*     */   public boolean getLinearFiltering() {
/* 104 */     return this.linearfiltering;
/*     */   }
/*     */ 
/*     */   public void setLinearFiltering(boolean paramBoolean) {
/* 108 */     this.linearfiltering = paramBoolean;
/*     */   }
/*     */ 
/*     */   public long getNativeSourceHandle() {
/* 112 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public int getLastImageSerial() {
/* 116 */     return this.lastImageSerial;
/*     */   }
/*     */ 
/*     */   public void setLastImageSerial(int paramInt) {
/* 120 */     this.lastImageSerial = paramInt;
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage) {
/* 124 */     update(paramImage, 0, 0);
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2) {
/* 128 */     update(paramImage, paramInt1, paramInt2, paramImage.getWidth(), paramImage.getHeight());
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 132 */     update(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, false);
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
/*     */   {
/* 138 */     update(paramImage.getPixelBuffer(), paramImage.getPixelFormat(), paramInt1, paramInt2, paramImage.getMinX(), paramImage.getMinY(), paramInt3, paramInt4, paramImage.getScanlineStride(), paramBoolean);
/*     */   }
/*     */ 
/*     */   public void update(Buffer paramBuffer, PixelFormat paramPixelFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean)
/*     */   {
/* 150 */     switch (1.$SwitchMap$com$sun$prism$PixelFormat[paramPixelFormat.ordinal()]) {
/*     */     case 1:
/* 152 */       this.updater.updateFromByteBuffer(this.bimg, (ByteBuffer)paramBuffer, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, 0, 1, 2, -1, 3, true);
/*     */ 
/* 156 */       return;
/*     */     case 2:
/* 158 */       this.updater.updateFromByteBuffer(this.bimg, (ByteBuffer)paramBuffer, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, 0, 0, 0, -1, 1, true);
/*     */ 
/* 162 */       return;
/*     */     case 3:
/* 164 */       this.updater.updateFromIntBuffer(this.bimg, (IntBuffer)paramBuffer, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7 / 4, true, true);
/*     */ 
/* 168 */       return;
/*     */     case 4:
/* 170 */       this.updater.updateFromByteBuffer(this.bimg, (ByteBuffer)paramBuffer, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, 2, 1, 0, 3, 4, true);
/*     */ 
/* 174 */       return;
/*     */     }
/* 176 */     throw new UnsupportedOperationException("Pixel format " + paramPixelFormat + " not supported yet.");
/*     */   }
/*     */ 
/*     */   public void update(MediaFrame paramMediaFrame, boolean paramBoolean)
/*     */   {
/* 182 */     paramMediaFrame.holdFrame();
/*     */ 
/* 184 */     if (paramMediaFrame.getPixelFormat() != PixelFormat.INT_ARGB_PRE) {
/* 185 */       localObject = paramMediaFrame.convertToFormat(PixelFormat.INT_ARGB_PRE);
/* 186 */       paramMediaFrame.releaseFrame();
/* 187 */       paramMediaFrame = (MediaFrame)localObject;
/*     */ 
/* 189 */       if (null == paramMediaFrame)
/*     */       {
/* 191 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 195 */     Object localObject = paramMediaFrame.getBuffer();
/* 196 */     ((ByteBuffer)localObject).position(paramMediaFrame.offsetForPlane(0));
/* 197 */     this.updater.updateFromIntBuffer(this.bimg, ((ByteBuffer)localObject).asIntBuffer(), 0, 0, 0, 0, paramMediaFrame.getWidth(), paramMediaFrame.getHeight(), paramMediaFrame.strideForPlane(0) / 4, true, true);
/*     */ 
/* 201 */     paramMediaFrame.releaseFrame();
/*     */   }
/*     */ 
/*     */   static class ThreeByteBgrUpdater extends J2DTexture.Updater
/*     */   {
/* 497 */     static ThreeByteBgrUpdater THREE_BYTE_BGR_INSTANCE = new ThreeByteBgrUpdater();
/*     */ 
/*     */     private ThreeByteBgrUpdater() {
/* 500 */       super();
/*     */     }
/*     */ 
/*     */     void updateFromByteBuffer(BufferedImage paramBufferedImage, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, boolean paramBoolean)
/*     */     {
/* 510 */       byte[] arrayOfByte1 = ((DataBufferByte)paramBufferedImage.getRaster().getDataBuffer()).getData();
/*     */ 
/* 512 */       int i = paramBufferedImage.getWidth() * 3;
/* 513 */       int j = paramInt2 * i + paramInt1 * 3;
/*     */ 
/* 515 */       int k = paramInt4 * paramInt7 + paramInt3 * paramInt12;
/*     */       byte[] arrayOfByte2;
/* 516 */       if (paramByteBuffer.hasArray()) {
/* 517 */         arrayOfByte2 = paramByteBuffer.array();
/* 518 */         k += paramByteBuffer.arrayOffset();
/*     */       } else {
/* 520 */         arrayOfByte2 = new byte[paramInt5 * paramInt12];
/* 521 */         paramByteBuffer.position(paramByteBuffer.position() + k);
/* 522 */         k = 0;
/*     */       }
/* 524 */       paramInt7 -= paramInt12 * paramInt5;
/* 525 */       i -= 3 * paramInt5;
/* 526 */       for (int m = 0; m < paramInt6; m++) {
/* 527 */         if (!paramByteBuffer.hasArray()) {
/* 528 */           paramByteBuffer.get(arrayOfByte2);
/* 529 */           if (paramInt7 != 0) {
/* 530 */             paramByteBuffer.position(paramByteBuffer.position() + paramInt7);
/*     */           }
/* 532 */           k = 0;
/*     */         }
/* 534 */         for (int n = 0; n < paramInt5; n++) {
/* 535 */           arrayOfByte1[j] = arrayOfByte2[(k + paramInt10)];
/* 536 */           arrayOfByte1[(j + 1)] = arrayOfByte2[(k + paramInt9)];
/* 537 */           arrayOfByte1[(j + 2)] = arrayOfByte2[(k + paramInt8)];
/* 538 */           k += paramInt12;
/* 539 */           j += 3;
/*     */         }
/* 541 */         k += paramInt7;
/* 542 */         j += i;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class IntArgbPreUpdater extends J2DTexture.Updater
/*     */   {
/* 379 */     static IntArgbPreUpdater INT_ARGB_PRE_INSTANCE = new IntArgbPreUpdater();
/*     */ 
/*     */     private IntArgbPreUpdater() {
/* 382 */       super();
/*     */     }
/*     */ 
/*     */     void updateFromByteBuffer(BufferedImage paramBufferedImage, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, boolean paramBoolean)
/*     */     {
/* 392 */       int[] arrayOfInt = ((DataBufferInt)paramBufferedImage.getRaster().getDataBuffer()).getData();
/*     */ 
/* 394 */       int i = paramBufferedImage.getWidth();
/* 395 */       int j = paramInt2 * i + paramInt1;
/*     */ 
/* 397 */       int k = paramInt4 * paramInt7 + paramInt3 * paramInt12;
/* 398 */       int m = 255;
/*     */       byte[] arrayOfByte;
/* 399 */       if (paramByteBuffer.hasArray()) {
/* 400 */         arrayOfByte = paramByteBuffer.array();
/* 401 */         k += paramByteBuffer.arrayOffset();
/*     */       } else {
/* 403 */         arrayOfByte = new byte[paramInt5 * paramInt12];
/* 404 */         paramByteBuffer.position(paramByteBuffer.position() + k);
/* 405 */         k = 0;
/*     */       }
/* 407 */       paramInt7 -= paramInt12 * paramInt5;
/* 408 */       for (int n = 0; n < paramInt6; n++) {
/* 409 */         if (!paramByteBuffer.hasArray()) {
/* 410 */           paramByteBuffer.get(arrayOfByte);
/* 411 */           if (paramInt7 != 0) {
/* 412 */             paramByteBuffer.position(paramByteBuffer.position() + paramInt7);
/*     */           }
/* 414 */           k = 0;
/*     */         }
/* 416 */         for (int i1 = 0; i1 < paramInt5; i1++) {
/* 417 */           int i2 = arrayOfByte[(k + paramInt8)] & 0xFF;
/* 418 */           int i3 = arrayOfByte[(k + paramInt9)] & 0xFF;
/* 419 */           int i4 = arrayOfByte[(k + paramInt10)] & 0xFF;
/* 420 */           if (paramInt11 >= 0) {
/* 421 */             m = arrayOfByte[(k + paramInt11)] & 0xFF;
/* 422 */             if ((!paramBoolean) && (m != 255)) {
/* 423 */               if (m == 0) {
/* 424 */                 i2 = i3 = i4 = 0;
/*     */               } else {
/* 426 */                 i2 = i2 * m / 255;
/* 427 */                 i3 = i3 * m / 255;
/* 428 */                 i4 = i4 * m / 255;
/*     */               }
/*     */             }
/*     */           }
/* 432 */           int i5 = m << 24 | i2 << 16 | i3 << 8 | i4;
/* 433 */           arrayOfInt[(j + i1)] = i5;
/* 434 */           k += paramInt12;
/*     */         }
/* 436 */         k += paramInt7;
/* 437 */         j += i;
/*     */       }
/*     */     }
/*     */ 
/*     */     void updateFromIntBuffer(BufferedImage paramBufferedImage, IntBuffer paramIntBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean1, boolean paramBoolean2)
/*     */     {
/* 448 */       int[] arrayOfInt1 = ((DataBufferInt)paramBufferedImage.getRaster().getDataBuffer()).getData();
/*     */ 
/* 450 */       int i = paramBufferedImage.getWidth();
/* 451 */       int j = paramInt2 * i + paramInt1;
/*     */ 
/* 453 */       int k = paramInt4 * paramInt7 + paramInt3;
/*     */       int[] arrayOfInt2;
/* 454 */       if (paramIntBuffer.hasArray()) {
/* 455 */         arrayOfInt2 = (int[])paramIntBuffer.array();
/* 456 */         k += paramIntBuffer.arrayOffset();
/*     */       } else {
/* 458 */         arrayOfInt2 = new int[paramInt5];
/* 459 */         paramIntBuffer.position(paramIntBuffer.position() + k);
/* 460 */         k = 0;
/*     */       }
/* 462 */       for (int m = 0; m < paramInt6; m++) {
/* 463 */         if (!paramIntBuffer.hasArray()) {
/* 464 */           paramIntBuffer.get(arrayOfInt2);
/* 465 */           if (paramInt7 - paramInt5 != 0) {
/* 466 */             paramIntBuffer.position(paramIntBuffer.position() + (paramInt7 - paramInt5));
/*     */           }
/* 468 */           k = 0;
/*     */         }
/* 470 */         for (int n = 0; n < paramInt5; n++) {
/* 471 */           int i1 = arrayOfInt2[(k + n)];
/* 472 */           if (!paramBoolean1) {
/* 473 */             i1 |= -16777216;
/* 474 */           } else if (!paramBoolean2) {
/* 475 */             int i2 = i1 >>> 24;
/* 476 */             if (i2 == 0) {
/* 477 */               i1 = 0;
/* 478 */             } else if (i2 != 255) {
/* 479 */               int i3 = i1 >> 16 & 0xFF;
/* 480 */               int i4 = i1 >> 8 & 0xFF;
/* 481 */               int i5 = i1 & 0xFF;
/* 482 */               i3 = i3 * i2 / 255;
/* 483 */               i4 = i4 * i2 / 255;
/* 484 */               i5 = i5 * i2 / 255;
/* 485 */               i1 = i2 << 24 | i3 << 16 | i4 << 8 | i5;
/*     */             }
/*     */           }
/* 488 */           arrayOfInt1[(j + n)] = i1;
/*     */         }
/* 490 */         k += paramInt7;
/* 491 */         j += i;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Updater
/*     */   {
/* 205 */     static Updater GENERAL_INSTANCE = new Updater();
/*     */ 
/*     */     void updateFromByteBuffer(BufferedImage paramBufferedImage, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, boolean paramBoolean)
/*     */     {
/* 259 */       int i = paramInt4 * paramInt7 + paramInt3 * paramInt12;
/* 260 */       int j = 255;
/*     */       byte[] arrayOfByte;
/* 261 */       if (paramByteBuffer.hasArray()) {
/* 262 */         arrayOfByte = paramByteBuffer.array();
/* 263 */         i += paramByteBuffer.arrayOffset();
/*     */       } else {
/* 265 */         arrayOfByte = new byte[paramInt5 * paramInt12];
/* 266 */         paramByteBuffer.position(paramByteBuffer.position() + i);
/* 267 */         i = 0;
/*     */       }
/* 269 */       paramInt7 -= paramInt12 * paramInt5;
/* 270 */       for (int k = 0; k < paramInt6; k++) {
/* 271 */         if (!paramByteBuffer.hasArray()) {
/* 272 */           paramByteBuffer.get(arrayOfByte);
/* 273 */           if (paramInt7 != 0) {
/* 274 */             paramByteBuffer.position(paramByteBuffer.position() + paramInt7);
/*     */           }
/* 276 */           i = 0;
/*     */         }
/* 278 */         for (int m = 0; m < paramInt5; m++) {
/* 279 */           int n = arrayOfByte[(i + paramInt8)] & 0xFF;
/* 280 */           int i1 = arrayOfByte[(i + paramInt9)] & 0xFF;
/* 281 */           int i2 = arrayOfByte[(i + paramInt10)] & 0xFF;
/* 282 */           if (paramInt11 >= 0) {
/* 283 */             j = arrayOfByte[(i + paramInt11)] & 0xFF;
/* 284 */             if ((paramBoolean) && (j != 255) && (j != 0)) {
/* 285 */               n = n * 255 / j;
/* 286 */               i1 = i1 * 255 / j;
/* 287 */               i2 = i2 * 255 / j;
/*     */             }
/*     */           }
/* 290 */           int i3 = j << 24 | n << 16 | i1 << 8 | i2;
/* 291 */           paramBufferedImage.setRGB(paramInt1 + m, paramInt2 + k, i3);
/* 292 */           i += paramInt12;
/*     */         }
/* 294 */         i += paramInt7;
/*     */       }
/*     */     }
/*     */ 
/*     */     void updateFromIntBuffer(BufferedImage paramBufferedImage, IntBuffer paramIntBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean1, boolean paramBoolean2)
/*     */     {
/* 338 */       int i = paramInt4 * paramInt7 + paramInt3;
/*     */       int[] arrayOfInt;
/* 339 */       if (paramIntBuffer.hasArray()) {
/* 340 */         arrayOfInt = (int[])paramIntBuffer.array();
/* 341 */         i += paramIntBuffer.arrayOffset();
/*     */       } else {
/* 343 */         arrayOfInt = new int[paramInt5];
/* 344 */         paramIntBuffer.position(paramIntBuffer.position() + i);
/* 345 */         i = 0;
/*     */       }
/* 347 */       for (int j = 0; j < paramInt6; j++) {
/* 348 */         if (!paramIntBuffer.hasArray()) {
/* 349 */           paramIntBuffer.get(arrayOfInt);
/* 350 */           if (paramInt7 - paramInt5 != 0) {
/* 351 */             paramIntBuffer.position(paramIntBuffer.position() + (paramInt7 - paramInt5));
/*     */           }
/* 353 */           i = 0;
/*     */         }
/* 355 */         for (int k = 0; k < paramInt5; k++) {
/* 356 */           int m = arrayOfInt[(i + k)];
/* 357 */           if (!paramBoolean1) {
/* 358 */             m |= -16777216;
/* 359 */           } else if (paramBoolean2) {
/* 360 */             int n = m >>> 24;
/* 361 */             if ((n != 255) && (n != 0)) {
/* 362 */               int i1 = m >> 16 & 0xFF;
/* 363 */               int i2 = m >> 8 & 0xFF;
/* 364 */               int i3 = m & 0xFF;
/* 365 */               i1 = i1 * 255 / n;
/* 366 */               i2 = i2 * 255 / n;
/* 367 */               i3 = i3 * 255 / n;
/* 368 */               m = n << 24 | i1 << 16 | i2 << 8 | i3;
/*     */             }
/*     */           }
/* 371 */           paramBufferedImage.setRGB(paramInt1 + k, paramInt2 + j, m);
/*     */         }
/* 373 */         i += paramInt7;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.j2d.J2DTexture
 * JD-Core Version:    0.6.2
 */