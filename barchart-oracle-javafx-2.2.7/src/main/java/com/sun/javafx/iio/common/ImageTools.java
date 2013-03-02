/*     */ package com.sun.javafx.iio.common;
/*     */ 
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.iio.ImageFrame;
/*     */ import com.sun.javafx.iio.ImageMetadata;
/*     */ import com.sun.javafx.iio.ImageStorage.ImageType;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public class ImageTools
/*     */ {
/*     */   public static final int PROGRESS_INTERVAL = 5;
/*     */ 
/*     */   public static int readFully(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/*  55 */     if (paramInt2 < 0) {
/*  56 */       throw new IndexOutOfBoundsException();
/*     */     }
/*  58 */     int i = paramInt2;
/*     */ 
/*  60 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length) || (paramInt1 + paramInt2 < 0)) {
/*  61 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > b.length!");
/*     */     }
/*     */ 
/*  64 */     while (paramInt2 > 0) {
/*  65 */       int j = paramInputStream.read(paramArrayOfByte, paramInt1, paramInt2);
/*  66 */       if (j == -1) {
/*  67 */         throw new EOFException();
/*     */       }
/*  69 */       paramInt1 += j;
/*  70 */       paramInt2 -= j;
/*     */     }
/*     */ 
/*  73 */     return i;
/*     */   }
/*     */ 
/*     */   public static int readFully(InputStream paramInputStream, byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/*  91 */     return readFully(paramInputStream, paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */ 
/*     */   public static ImageStorage.ImageType getConvertedType(ImageStorage.ImageType paramImageType)
/*     */   {
/* 131 */     ImageStorage.ImageType localImageType = paramImageType;
/* 132 */     switch (1.$SwitchMap$com$sun$javafx$iio$ImageStorage$ImageType[paramImageType.ordinal()]) {
/*     */     case 1:
/* 134 */       localImageType = ImageStorage.ImageType.GRAY;
/* 135 */       break;
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/*     */     case 6:
/*     */     case 7:
/* 142 */       localImageType = ImageStorage.ImageType.RGBA_PRE;
/* 143 */       break;
/*     */     case 8:
/*     */     case 9:
/* 146 */       localImageType = ImageStorage.ImageType.RGB;
/* 147 */       break;
/*     */     case 10:
/* 149 */       localImageType = ImageStorage.ImageType.RGBA_PRE;
/* 150 */       break;
/*     */     default:
/* 152 */       throw new IllegalArgumentException("Unsupported ImageType " + paramImageType);
/*     */     }
/* 154 */     return localImageType;
/*     */   }
/*     */ 
/*     */   public static byte[] createImageArray(ImageStorage.ImageType paramImageType, int paramInt1, int paramInt2) {
/* 158 */     int i = 0;
/* 159 */     switch (1.$SwitchMap$com$sun$javafx$iio$ImageStorage$ImageType[paramImageType.ordinal()]) {
/*     */     case 1:
/*     */     case 4:
/*     */     case 5:
/*     */     case 8:
/* 164 */       i = 1;
/* 165 */       break;
/*     */     case 2:
/*     */     case 3:
/* 168 */       i = 2;
/* 169 */       break;
/*     */     case 9:
/* 171 */       i = 3;
/* 172 */       break;
/*     */     case 7:
/*     */     case 10:
/* 175 */       i = 4;
/* 176 */       break;
/*     */     case 6:
/*     */     default:
/* 178 */       throw new IllegalArgumentException("Unsupported ImageType " + paramImageType);
/*     */     }
/* 180 */     return new byte[paramInt1 * paramInt2 * i];
/*     */   }
/*     */ 
/*     */   public static ImageFrame convertImageFrame(ImageFrame paramImageFrame)
/*     */   {
/* 185 */     ImageStorage.ImageType localImageType1 = paramImageFrame.getImageType();
/* 186 */     ImageStorage.ImageType localImageType2 = getConvertedType(localImageType1);
/*     */     ImageFrame localImageFrame;
/* 187 */     if (localImageType2 == localImageType1) {
/* 188 */       localImageFrame = paramImageFrame;
/*     */     } else {
/* 190 */       byte[] arrayOfByte1 = null;
/* 191 */       Buffer localBuffer = paramImageFrame.getImageData();
/* 192 */       if (!(localBuffer instanceof ByteBuffer)) {
/* 193 */         throw new IllegalArgumentException("!(frame.getImageData() instanceof ByteBuffer)");
/*     */       }
/* 195 */       ByteBuffer localByteBuffer1 = (ByteBuffer)localBuffer;
/* 196 */       if (localByteBuffer1.hasArray()) {
/* 197 */         arrayOfByte1 = localByteBuffer1.array();
/*     */       } else {
/* 199 */         arrayOfByte1 = new byte[localByteBuffer1.capacity()];
/* 200 */         localByteBuffer1.get(arrayOfByte1);
/*     */       }
/* 202 */       int i = paramImageFrame.getWidth();
/* 203 */       int j = paramImageFrame.getHeight();
/* 204 */       int k = paramImageFrame.getStride();
/* 205 */       byte[] arrayOfByte2 = createImageArray(localImageType2, i, j);
/* 206 */       ByteBuffer localByteBuffer2 = ByteBuffer.wrap(arrayOfByte2);
/* 207 */       int m = arrayOfByte2.length / j;
/* 208 */       byte[][] arrayOfByte = paramImageFrame.getPalette();
/* 209 */       ImageMetadata localImageMetadata1 = paramImageFrame.getMetadata();
/* 210 */       int n = localImageMetadata1.transparentIndex != null ? localImageMetadata1.transparentIndex.intValue() : 0;
/* 211 */       convert(i, j, localImageType1, arrayOfByte1, 0, k, arrayOfByte2, 0, m, arrayOfByte, n, false);
/*     */ 
/* 214 */       ImageMetadata localImageMetadata2 = new ImageMetadata(localImageMetadata1.gamma, localImageMetadata1.blackIsZero, null, localImageMetadata1.backgroundColor, null, localImageMetadata1.delayTime, localImageMetadata1.imageWidth, localImageMetadata1.imageHeight, localImageMetadata1.imageLeftPosition, localImageMetadata1.imageTopPosition, localImageMetadata1.disposalMethod);
/*     */ 
/* 220 */       localImageFrame = new ImageFrame(localImageType2, localByteBuffer2, i, j, m, (byte[][])null, localImageMetadata2);
/*     */     }
/*     */ 
/* 223 */     return localImageFrame;
/*     */   }
/*     */ 
/*     */   public static byte[] convert(int paramInt1, int paramInt2, ImageStorage.ImageType paramImageType, byte[] paramArrayOfByte1, int paramInt3, int paramInt4, byte[] paramArrayOfByte2, int paramInt5, int paramInt6, byte[][] paramArrayOfByte, int paramInt7, boolean paramBoolean)
/*     */   {
/*     */     int i;
/*     */     int j;
/*     */     int k;
/*     */     int n;
/* 233 */     if ((paramImageType == ImageStorage.ImageType.GRAY) || (paramImageType == ImageStorage.ImageType.RGB) || (paramImageType == ImageStorage.ImageType.RGBA_PRE))
/*     */     {
/* 236 */       if (paramArrayOfByte1 != paramArrayOfByte2) {
/* 237 */         i = paramInt1;
/* 238 */         if (paramImageType == ImageStorage.ImageType.RGB)
/* 239 */           i *= 3;
/* 240 */         else if (paramImageType == ImageStorage.ImageType.RGBA_PRE) {
/* 241 */           i *= 4;
/*     */         }
/* 243 */         if (paramInt2 == 1) {
/* 244 */           System.arraycopy(paramArrayOfByte1, paramInt3, paramArrayOfByte2, paramInt5, i);
/*     */         } else {
/* 246 */           j = paramInt3;
/* 247 */           k = paramInt5;
/* 248 */           for (n = 0; n < paramInt2; n++) {
/* 249 */             System.arraycopy(paramArrayOfByte1, j, paramArrayOfByte2, k, i);
/* 250 */             j += paramInt4;
/* 251 */             k += paramInt6;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*     */       int i4;
/*     */       int i6;
/*     */       int i8;
/* 255 */       if ((paramImageType == ImageStorage.ImageType.GRAY_ALPHA) || (paramImageType == ImageStorage.ImageType.GRAY_ALPHA_PRE)) {
/* 256 */         i = paramInt3;
/* 257 */         j = paramInt5;
/*     */         int i2;
/* 258 */         if (paramImageType == ImageStorage.ImageType.GRAY_ALPHA)
/* 259 */           for (k = 0; k < paramInt2; k++) {
/* 260 */             n = i;
/* 261 */             i2 = j;
/* 262 */             for (i4 = 0; i4 < paramInt1; i4++)
/*     */             {
/* 264 */               i6 = paramArrayOfByte1[(n++)];
/* 265 */               i8 = paramArrayOfByte1[(n++)] & 0xFF;
/* 266 */               float f1 = i8 / 255.0F;
/* 267 */               i6 = (byte)(int)(f1 * (i6 & 0xFF));
/* 268 */               paramArrayOfByte2[(i2++)] = i6;
/* 269 */               paramArrayOfByte2[(i2++)] = i6;
/* 270 */               paramArrayOfByte2[(i2++)] = i6;
/* 271 */               paramArrayOfByte2[(i2++)] = ((byte)i8);
/*     */             }
/* 273 */             i += paramInt4;
/* 274 */             j += paramInt6;
/*     */           }
/*     */         else
/* 277 */           for (k = 0; k < paramInt2; k++) {
/* 278 */             n = i;
/* 279 */             i2 = j;
/* 280 */             for (i4 = 0; i4 < paramInt1; i4++)
/*     */             {
/* 282 */               i6 = paramArrayOfByte1[(n++)];
/* 283 */               paramArrayOfByte2[(i2++)] = i6;
/* 284 */               paramArrayOfByte2[(i2++)] = i6;
/* 285 */               paramArrayOfByte2[(i2++)] = i6;
/* 286 */               paramArrayOfByte2[(i2++)] = paramArrayOfByte1[(n++)];
/*     */             }
/* 288 */             i += paramInt4;
/* 289 */             j += paramInt6;
/*     */           }
/*     */       }
/*     */       else
/*     */       {
/*     */         byte[] arrayOfByte1;
/*     */         byte[] arrayOfByte2;
/*     */         byte[] arrayOfByte3;
/*     */         int i10;
/* 292 */         if (paramImageType == ImageStorage.ImageType.PALETTE) {
/* 293 */           i = paramInt3;
/* 294 */           j = paramInt5;
/* 295 */           arrayOfByte1 = paramArrayOfByte[0];
/* 296 */           arrayOfByte2 = paramArrayOfByte[1];
/* 297 */           arrayOfByte3 = paramArrayOfByte[2];
/* 298 */           i4 = i;
/* 299 */           i6 = j;
/*     */ 
/* 302 */           for (i8 = 0; i8 < paramInt1; i8++) {
/* 303 */             i10 = paramArrayOfByte1[(i4++)] & 0xFF;
/*     */ 
/* 305 */             paramArrayOfByte2[(i6++)] = arrayOfByte1[i10];
/* 306 */             paramArrayOfByte2[(i6++)] = arrayOfByte2[i10];
/* 307 */             paramArrayOfByte2[(i6++)] = arrayOfByte3[i10];
/*     */ 
/* 309 */             j += paramInt6;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/*     */           byte[] arrayOfByte4;
/*     */           int i11;
/*     */           int i12;
/* 311 */           if (paramImageType == ImageStorage.ImageType.PALETTE_ALPHA) {
/* 312 */             i = paramInt3;
/* 313 */             j = paramInt5;
/* 314 */             arrayOfByte1 = paramArrayOfByte[0];
/* 315 */             arrayOfByte2 = paramArrayOfByte[1];
/* 316 */             arrayOfByte3 = paramArrayOfByte[2];
/* 317 */             arrayOfByte4 = paramArrayOfByte[3];
/* 318 */             i6 = i;
/* 319 */             i8 = j;
/* 320 */             for (i10 = 0; i10 < paramInt1; i10++) {
/* 321 */               i11 = paramArrayOfByte1[(i6++)] & 0xFF;
/* 322 */               i12 = arrayOfByte1[i11];
/* 323 */               int i13 = arrayOfByte2[i11];
/* 324 */               int i14 = arrayOfByte3[i11];
/* 325 */               int i15 = arrayOfByte4[i11] & 0xFF;
/* 326 */               float f3 = i15 / 255.0F;
/* 327 */               paramArrayOfByte2[(i8++)] = ((byte)(int)(f3 * (i12 & 0xFF)));
/* 328 */               paramArrayOfByte2[(i8++)] = ((byte)(int)(f3 * (i13 & 0xFF)));
/* 329 */               paramArrayOfByte2[(i8++)] = ((byte)(int)(f3 * (i14 & 0xFF)));
/* 330 */               paramArrayOfByte2[(i8++)] = ((byte)i15);
/*     */             }
/* 332 */             i += paramInt4;
/* 333 */             j += paramInt6;
/* 334 */           } else if (paramImageType == ImageStorage.ImageType.PALETTE_ALPHA_PRE) {
/* 335 */             i = paramInt3;
/* 336 */             j = paramInt5;
/* 337 */             arrayOfByte1 = paramArrayOfByte[0];
/* 338 */             arrayOfByte2 = paramArrayOfByte[1];
/* 339 */             arrayOfByte3 = paramArrayOfByte[2];
/* 340 */             arrayOfByte4 = paramArrayOfByte[3];
/* 341 */             for (i6 = 0; i6 < paramInt2; i6++) {
/* 342 */               i8 = i;
/* 343 */               i10 = j;
/* 344 */               for (i11 = 0; i11 < paramInt1; i11++) {
/* 345 */                 i12 = paramArrayOfByte1[(i8++)] & 0xFF;
/* 346 */                 paramArrayOfByte2[(i10++)] = arrayOfByte1[i12];
/* 347 */                 paramArrayOfByte2[(i10++)] = arrayOfByte2[i12];
/* 348 */                 paramArrayOfByte2[(i10++)] = arrayOfByte3[i12];
/* 349 */                 paramArrayOfByte2[(i10++)] = arrayOfByte4[i12];
/*     */               }
/* 351 */               i += paramInt4;
/* 352 */               j += paramInt6;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/*     */             int m;
/*     */             int i1;
/*     */             int i3;
/* 354 */             if (paramImageType == ImageStorage.ImageType.PALETTE_TRANS) {
/* 355 */               i = paramInt3;
/* 356 */               j = paramInt5;
/* 357 */               for (m = 0; m < paramInt2; m++) {
/* 358 */                 i1 = i;
/* 359 */                 i3 = j;
/* 360 */                 arrayOfByte4 = paramArrayOfByte[0];
/* 361 */                 byte[] arrayOfByte5 = paramArrayOfByte[1];
/* 362 */                 byte[] arrayOfByte6 = paramArrayOfByte[2];
/* 363 */                 for (i10 = 0; i10 < paramInt1; i10++) {
/* 364 */                   i11 = paramArrayOfByte1[(i1++)] & 0xFF;
/* 365 */                   if (i11 == paramInt7) {
/* 366 */                     if (paramBoolean) {
/* 367 */                       i3 += 4;
/*     */                     } else {
/* 369 */                       paramArrayOfByte2[(i3++)] = 0;
/* 370 */                       paramArrayOfByte2[(i3++)] = 0;
/* 371 */                       paramArrayOfByte2[(i3++)] = 0;
/* 372 */                       paramArrayOfByte2[(i3++)] = 0;
/*     */                     }
/*     */                   } else {
/* 375 */                     paramArrayOfByte2[(i3++)] = arrayOfByte4[i11];
/* 376 */                     paramArrayOfByte2[(i3++)] = arrayOfByte5[i11];
/* 377 */                     paramArrayOfByte2[(i3++)] = arrayOfByte6[i11];
/* 378 */                     paramArrayOfByte2[(i3++)] = -1;
/*     */                   }
/*     */                 }
/* 381 */                 i += paramInt4;
/* 382 */                 j += paramInt6;
/*     */               }
/* 384 */             } else if (paramImageType == ImageStorage.ImageType.RGBA) {
/* 385 */               i = paramInt3;
/* 386 */               j = paramInt5;
/* 387 */               for (m = 0; m < paramInt2; m++) {
/* 388 */                 i1 = i;
/* 389 */                 i3 = j;
/* 390 */                 for (int i5 = 0; i5 < paramInt1; i5++)
/*     */                 {
/* 392 */                   int i7 = paramArrayOfByte1[(i1++)];
/* 393 */                   int i9 = paramArrayOfByte1[(i1++)];
/* 394 */                   i10 = paramArrayOfByte1[(i1++)];
/* 395 */                   i11 = paramArrayOfByte1[(i1++)] & 0xFF;
/* 396 */                   float f2 = i11 / 255.0F;
/* 397 */                   paramArrayOfByte2[(i3++)] = ((byte)(int)(f2 * (i7 & 0xFF)));
/* 398 */                   paramArrayOfByte2[(i3++)] = ((byte)(int)(f2 * (i9 & 0xFF)));
/* 399 */                   paramArrayOfByte2[(i3++)] = ((byte)(int)(f2 * (i10 & 0xFF)));
/* 400 */                   paramArrayOfByte2[(i3++)] = ((byte)i11);
/*     */                 }
/*     */ 
/* 403 */                 i += paramInt4;
/* 404 */                 j += paramInt6;
/*     */               }
/*     */             } else {
/* 407 */               throw new UnsupportedOperationException("Unsupported ImageType " + paramImageType);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 411 */     return paramArrayOfByte2;
/*     */   }
/*     */ 
/*     */   public static InputStream createInputStream(String paramString) throws IOException {
/* 415 */     Object localObject = null;
/*     */     try
/*     */     {
/* 420 */       File localFile = new File(paramString);
/* 421 */       if (localFile.exists())
/* 422 */         localObject = new FileInputStream(localFile);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/* 427 */     if (localObject == null) {
/* 428 */       URL localURL = new URL(paramString);
/* 429 */       localObject = localURL.openStream();
/*     */     }
/* 431 */     return localObject;
/*     */   }
/*     */ 
/*     */   private static void computeUpdatedPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int[] paramArrayOfInt, int paramInt10)
/*     */   {
/* 475 */     int i = 0;
/* 476 */     int j = -1;
/* 477 */     int k = -1;
/* 478 */     int m = -1;
/*     */ 
/* 480 */     for (int n = 0; n < paramInt8; n++) {
/* 481 */       int i1 = paramInt7 + n * paramInt9;
/* 482 */       if (i1 >= paramInt1)
/*     */       {
/* 485 */         if ((i1 - paramInt1) % paramInt6 == 0)
/*     */         {
/* 488 */           if (i1 >= paramInt1 + paramInt2)
/*     */           {
/*     */             break;
/*     */           }
/* 492 */           int i2 = paramInt3 + (i1 - paramInt1) / paramInt6;
/*     */ 
/* 494 */           if (i2 >= paramInt4)
/*     */           {
/* 497 */             if (i2 > paramInt5)
/*     */             {
/*     */               break;
/*     */             }
/* 501 */             if (i == 0) {
/* 502 */               j = i2;
/* 503 */               i = 1;
/* 504 */             } else if (k == -1) {
/* 505 */               k = i2;
/*     */             }
/* 507 */             m = i2;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 510 */     paramArrayOfInt[paramInt10] = j;
/*     */ 
/* 513 */     if (i == 0)
/* 514 */       paramArrayOfInt[(paramInt10 + 2)] = 0;
/*     */     else {
/* 516 */       paramArrayOfInt[(paramInt10 + 2)] = (m - j + 1);
/*     */     }
/*     */ 
/* 520 */     paramArrayOfInt[(paramInt10 + 4)] = Math.max(k - j, 1);
/*     */   }
/*     */ 
/*     */   public static int[] computeUpdatedPixels(Rectangle paramRectangle, Point2D paramPoint2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12)
/*     */   {
/* 580 */     int[] arrayOfInt = new int[6];
/* 581 */     computeUpdatedPixels(paramRectangle.x, paramRectangle.width, (int)(paramPoint2D.x + 0.5F), paramInt1, paramInt3, paramInt5, paramInt7, paramInt9, paramInt11, arrayOfInt, 0);
/*     */ 
/* 586 */     computeUpdatedPixels(paramRectangle.y, paramRectangle.height, (int)(paramPoint2D.y + 0.5F), paramInt2, paramInt4, paramInt6, paramInt8, paramInt10, paramInt12, arrayOfInt, 1);
/*     */ 
/* 591 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   public static int[] computeDimensions(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
/*     */   {
/* 597 */     int i = paramInt3 < 0 ? 0 : paramInt3;
/* 598 */     int j = paramInt4 < 0 ? 0 : paramInt4;
/*     */ 
/* 600 */     if ((i == 0) && (j == 0))
/*     */     {
/* 602 */       i = paramInt1;
/* 603 */       j = paramInt2;
/* 604 */     } else if ((i != paramInt1) || (j != paramInt2)) {
/* 605 */       if (paramBoolean)
/*     */       {
/* 607 */         if (i == 0) {
/* 608 */           i = (int)(paramInt1 * j / paramInt2);
/* 609 */         } else if (j == 0) {
/* 610 */           j = (int)(paramInt2 * i / paramInt1);
/*     */         } else {
/* 612 */           float f = Math.min(i / paramInt1, j / paramInt2);
/* 613 */           i = (int)(paramInt1 * f);
/* 614 */           j = (int)(paramInt2 * f);
/*     */         }
/*     */       }
/*     */       else {
/* 618 */         if (j == 0) {
/* 619 */           j = paramInt2;
/*     */         }
/* 621 */         if (i == 0) {
/* 622 */           i = paramInt1;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 628 */       if (i == 0) {
/* 629 */         i = 1;
/*     */       }
/* 631 */       if (j == 0) {
/* 632 */         j = 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 637 */     return new int[] { i, j };
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.common.ImageTools
 * JD-Core Version:    0.6.2
 */