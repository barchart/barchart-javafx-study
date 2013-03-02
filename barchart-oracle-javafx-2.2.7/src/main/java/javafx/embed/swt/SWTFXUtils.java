/*     */ package javafx.embed.swt;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import javafx.scene.image.Image;
/*     */ import javafx.scene.image.PixelFormat;
/*     */ import javafx.scene.image.PixelReader;
/*     */ import javafx.scene.image.PixelWriter;
/*     */ import javafx.scene.image.WritableImage;
/*     */ import javafx.scene.image.WritablePixelFormat;
/*     */ import org.eclipse.swt.graphics.ImageData;
/*     */ import org.eclipse.swt.graphics.PaletteData;
/*     */ import org.eclipse.swt.graphics.RGB;
/*     */ 
/*     */ public class SWTFXUtils
/*     */ {
/*     */   private static int blitSrc;
/*     */   private static boolean blitSrcCache;
/*     */   private static int alphaOpaque;
/*     */   private static boolean alphaOpaqueCache;
/*     */   private static int msbFirst;
/*     */   private static boolean msbFirstCache;
/*     */   private static Method blitDirect;
/*     */   private static Method blitPalette;
/*     */   private static Method getByteOrderMethod;
/*     */ 
/*     */   public static WritableImage toFXImage(ImageData paramImageData, WritableImage paramWritableImage)
/*     */   {
/*  79 */     byte[] arrayOfByte = convertImage(paramImageData);
/*  80 */     if (arrayOfByte == null) return null;
/*  81 */     int i = paramImageData.width;
/*  82 */     int j = paramImageData.height;
/*  83 */     if (paramWritableImage != null) {
/*  84 */       int k = (int)paramWritableImage.getWidth();
/*  85 */       m = (int)paramWritableImage.getHeight();
/*  86 */       if ((k < i) || (m < j)) {
/*  87 */         paramWritableImage = null;
/*  88 */       } else if ((i < k) || (j < m)) {
/*  89 */         localObject = new int[k];
/*  90 */         PixelWriter localPixelWriter2 = paramWritableImage.getPixelWriter();
/*  91 */         WritablePixelFormat localWritablePixelFormat = PixelFormat.getIntArgbPreInstance();
/*  92 */         if (i < k) {
/*  93 */           localPixelWriter2.setPixels(i, 0, k - i, j, localWritablePixelFormat, (int[])localObject, 0, 0);
/*     */         }
/*  95 */         if (j < m) {
/*  96 */           localPixelWriter2.setPixels(0, j, k, m - j, localWritablePixelFormat, (int[])localObject, 0, 0);
/*     */         }
/*     */       }
/*     */     }
/* 100 */     if (paramWritableImage == null) {
/* 101 */       paramWritableImage = new WritableImage(i, j);
/*     */     }
/* 103 */     PixelWriter localPixelWriter1 = paramWritableImage.getPixelWriter();
/* 104 */     int m = i * 4;
/* 105 */     Object localObject = PixelFormat.getByteBgraInstance();
/* 106 */     localPixelWriter1.setPixels(0, 0, i, j, (PixelFormat)localObject, arrayOfByte, 0, m);
/* 107 */     return paramWritableImage;
/*     */   }
/*     */ 
/*     */   public static ImageData fromFXImage(Image paramImage, ImageData paramImageData)
/*     */   {
/* 138 */     PixelReader localPixelReader = paramImage.getPixelReader();
/* 139 */     if (localPixelReader == null) {
/* 140 */       return null;
/*     */     }
/* 142 */     int i = (int)paramImage.getWidth();
/* 143 */     int j = (int)paramImage.getHeight();
/* 144 */     int k = i * 4;
/* 145 */     int m = k * j;
/* 146 */     byte[] arrayOfByte1 = new byte[m];
/* 147 */     WritablePixelFormat localWritablePixelFormat = PixelFormat.getByteBgraInstance();
/* 148 */     localPixelReader.getPixels(0, 0, i, j, localWritablePixelFormat, arrayOfByte1, 0, k);
/* 149 */     byte[] arrayOfByte2 = new byte[i * j];
/* 150 */     int n = 0; int i1 = 0; for (int i2 = 0; n < j; n++) {
/* 151 */       for (int i3 = 0; i3 < i; i1 += 4) {
/* 152 */         int i4 = arrayOfByte1[(i1 + 3)];
/* 153 */         arrayOfByte1[(i1 + 3)] = 0;
/* 154 */         arrayOfByte2[(i2++)] = i4;
/*     */ 
/* 151 */         i3++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 157 */     PaletteData localPaletteData = new PaletteData(65280, 16711680, -16777216);
/* 158 */     paramImageData = new ImageData(i, j, 32, localPaletteData, 4, arrayOfByte1);
/* 159 */     paramImageData.alphaData = arrayOfByte2;
/* 160 */     return paramImageData;
/*     */   }
/*     */ 
/*     */   private static int BLIT_SRC()
/*     */     throws Exception
/*     */   {
/* 166 */     if (!blitSrcCache) {
/* 167 */       blitSrc = readValue("BLIT_SRC");
/* 168 */       blitSrcCache = true;
/*     */     }
/* 170 */     return blitSrc;
/*     */   }
/*     */ 
/*     */   private static int ALPHA_OPAQUE()
/*     */     throws Exception
/*     */   {
/* 176 */     if (!alphaOpaqueCache) {
/* 177 */       alphaOpaque = readValue("ALPHA_OPAQUE");
/* 178 */       alphaOpaqueCache = true;
/*     */     }
/* 180 */     return alphaOpaque;
/*     */   }
/*     */ 
/*     */   private static int MSB_FIRST()
/*     */     throws Exception
/*     */   {
/* 186 */     if (!msbFirstCache) {
/* 187 */       msbFirst = readValue("MSB_FIRST");
/* 188 */       msbFirstCache = true;
/*     */     }
/* 190 */     return msbFirst;
/*     */   }
/*     */ 
/*     */   private static int readValue(final String paramString) throws Exception {
/* 194 */     ImageData localImageData = ImageData.class;
/* 195 */     return ((Integer)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */     {
/*     */       public Integer run() throws Exception {
/* 198 */         Field localField = this.val$clazz.getDeclaredField(paramString);
/* 199 */         localField.setAccessible(true);
/* 200 */         return Integer.valueOf(localField.getInt(this.val$clazz));
/*     */       }
/*     */     })).intValue();
/*     */   }
/*     */ 
/*     */   private static void blit(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, byte[] paramArrayOfByte2, int paramInt13, int paramInt14, int paramInt15, byte[] paramArrayOfByte3, int paramInt16, int paramInt17, int paramInt18, int paramInt19, int paramInt20, int paramInt21, int paramInt22, int paramInt23, int paramInt24, int paramInt25, boolean paramBoolean1, boolean paramBoolean2)
/*     */     throws Exception
/*     */   {
/* 216 */     ImageData localImageData = ImageData.class;
/* 217 */     if (blitDirect == null) {
/* 218 */       Class localClass1 = Integer.TYPE; Class localClass2 = Boolean.TYPE; byte[] arrayOfByte = [B.class;
/* 219 */       final Class[] arrayOfClass = { localClass1, arrayOfByte, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, arrayOfByte, localClass1, localClass1, localClass1, arrayOfByte, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass2, localClass2 };
/*     */ 
/* 227 */       blitDirect = (Method)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public Method run() throws Exception {
/* 230 */           Method localMethod = this.val$clazz.getDeclaredMethod("blit", arrayOfClass);
/*     */ 
/* 232 */           localMethod.setAccessible(true);
/* 233 */           return localMethod;
/*     */         }
/*     */       });
/*     */     }
/* 237 */     if (blitDirect != null)
/* 238 */       blitDirect.invoke(localImageData, new Object[] { Integer.valueOf(paramInt1), paramArrayOfByte1, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Integer.valueOf(paramInt5), Integer.valueOf(paramInt6), Integer.valueOf(paramInt7), Integer.valueOf(paramInt8), Integer.valueOf(paramInt9), Integer.valueOf(paramInt10), Integer.valueOf(paramInt11), Integer.valueOf(paramInt12), paramArrayOfByte2, Integer.valueOf(paramInt13), Integer.valueOf(paramInt14), Integer.valueOf(paramInt15), paramArrayOfByte3, Integer.valueOf(paramInt16), Integer.valueOf(paramInt17), Integer.valueOf(paramInt18), Integer.valueOf(paramInt19), Integer.valueOf(paramInt20), Integer.valueOf(paramInt21), Integer.valueOf(paramInt22), Integer.valueOf(paramInt23), Integer.valueOf(paramInt24), Integer.valueOf(paramInt25), Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2) });
/*     */   }
/*     */ 
/*     */   private static void blit(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4, int paramInt9, byte[] paramArrayOfByte5, int paramInt10, int paramInt11, int paramInt12, byte[] paramArrayOfByte6, int paramInt13, int paramInt14, int paramInt15, int paramInt16, int paramInt17, int paramInt18, int paramInt19, int paramInt20, int paramInt21, int paramInt22, boolean paramBoolean1, boolean paramBoolean2)
/*     */     throws Exception
/*     */   {
/* 261 */     ImageData localImageData = ImageData.class;
/* 262 */     if (blitPalette == null) {
/* 263 */       Class localClass1 = Integer.TYPE; Class localClass2 = Boolean.TYPE; byte[] arrayOfByte = [B.class;
/* 264 */       final Class[] arrayOfClass = { localClass1, arrayOfByte, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, arrayOfByte, arrayOfByte, arrayOfByte, localClass1, arrayOfByte, localClass1, localClass1, localClass1, arrayOfByte, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass1, localClass2, localClass2 };
/*     */ 
/* 272 */       blitPalette = (Method)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public Method run() throws Exception {
/* 275 */           Method localMethod = this.val$clazz.getDeclaredMethod("blit", arrayOfClass);
/*     */ 
/* 277 */           localMethod.setAccessible(true);
/* 278 */           return localMethod;
/*     */         }
/*     */       });
/*     */     }
/* 282 */     if (blitPalette != null)
/* 283 */       blitPalette.invoke(localImageData, new Object[] { Integer.valueOf(paramInt1), paramArrayOfByte1, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Integer.valueOf(paramInt5), Integer.valueOf(paramInt6), Integer.valueOf(paramInt7), Integer.valueOf(paramInt8), paramArrayOfByte2, paramArrayOfByte3, paramArrayOfByte4, Integer.valueOf(paramInt9), paramArrayOfByte5, Integer.valueOf(paramInt10), Integer.valueOf(paramInt11), Integer.valueOf(paramInt12), paramArrayOfByte6, Integer.valueOf(paramInt13), Integer.valueOf(paramInt14), Integer.valueOf(paramInt15), Integer.valueOf(paramInt16), Integer.valueOf(paramInt17), Integer.valueOf(paramInt18), Integer.valueOf(paramInt19), Integer.valueOf(paramInt20), Integer.valueOf(paramInt21), Integer.valueOf(paramInt22), Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2) });
/*     */   }
/*     */ 
/*     */   private static int getByteOrder(ImageData paramImageData)
/*     */     throws Exception
/*     */   {
/* 297 */     ImageData localImageData = ImageData.class;
/* 298 */     if (getByteOrderMethod != null) {
/* 299 */       getByteOrderMethod = (Method)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public Method run() throws Exception {
/* 302 */           Method localMethod = this.val$clazz.getDeclaredMethod("getByteOrder", new Class[0]);
/* 303 */           localMethod.setAccessible(true);
/* 304 */           return localMethod;
/*     */         }
/*     */       });
/*     */     }
/* 308 */     if (getByteOrderMethod != null) {
/* 309 */       return ((Integer)getByteOrderMethod.invoke(paramImageData, new Object[0])).intValue();
/*     */     }
/* 311 */     return MSB_FIRST();
/*     */   }
/*     */ 
/*     */   private static byte[] convertImage(ImageData paramImageData) {
/* 315 */     byte[] arrayOfByte1 = null;
/*     */     try {
/* 317 */       PaletteData localPaletteData = paramImageData.palette;
/* 318 */       if (((paramImageData.depth != 1) && (paramImageData.depth != 2) && (paramImageData.depth != 4) && (paramImageData.depth != 8)) || ((localPaletteData.isDirect) && (paramImageData.depth != 8) && (((paramImageData.depth != 16) && (paramImageData.depth != 24) && (paramImageData.depth != 32)) || (!localPaletteData.isDirect))))
/*     */       {
/* 324 */         return null;
/*     */       }
/*     */ 
/* 327 */       int i = BLIT_SRC();
/* 328 */       int j = ALPHA_OPAQUE();
/* 329 */       int k = MSB_FIRST();
/*     */ 
/* 331 */       int m = paramImageData.width;
/* 332 */       int n = paramImageData.height;
/* 333 */       int i1 = getByteOrder(paramImageData);
/* 334 */       int i2 = 3;
/* 335 */       int i3 = 65280;
/* 336 */       int i4 = 16711680;
/* 337 */       int i5 = -16777216;
/* 338 */       int i6 = m * n * 4;
/* 339 */       int i7 = m * 4;
/* 340 */       arrayOfByte1 = new byte[i6];
/*     */       Object localObject;
/*     */       byte[] arrayOfByte3;
/*     */       int i14;
/* 342 */       if (localPaletteData.isDirect) {
/* 343 */         blit(i, paramImageData.data, paramImageData.depth, paramImageData.bytesPerLine, i1, 0, 0, m, n, localPaletteData.redMask, localPaletteData.greenMask, localPaletteData.blueMask, j, null, 0, 0, 0, arrayOfByte1, 32, i7, k, 0, 0, m, n, i3, i4, i5, false, false);
/*     */       }
/*     */       else
/*     */       {
/* 352 */         RGB[] arrayOfRGB = localPaletteData.getRGBs();
/* 353 */         i9 = arrayOfRGB.length;
/* 354 */         localObject = new byte[i9];
/* 355 */         arrayOfByte3 = new byte[i9];
/* 356 */         byte[] arrayOfByte4 = new byte[i9];
/* 357 */         for (i14 = 0; i14 < arrayOfRGB.length; i14++) {
/* 358 */           RGB localRGB = arrayOfRGB[i14];
/* 359 */           if (localRGB != null) {
/* 360 */             localObject[i14] = ((byte)localRGB.red);
/* 361 */             arrayOfByte3[i14] = ((byte)localRGB.green);
/* 362 */             arrayOfByte4[i14] = ((byte)localRGB.blue);
/*     */           }
/*     */         }
/* 364 */         blit(i, paramImageData.data, paramImageData.depth, paramImageData.bytesPerLine, i1, 0, 0, m, n, (byte[])localObject, arrayOfByte3, arrayOfByte4, j, null, 0, 0, 0, arrayOfByte1, 32, i7, k, 0, 0, m, n, i3, i4, i5, false, false);
/*     */       }
/*     */ 
/* 374 */       int i8 = paramImageData.getTransparencyType();
/* 375 */       int i9 = i8 != 0 ? 1 : 0;
/*     */       int i13;
/*     */       int i15;
/* 376 */       if ((i8 == 2) || (paramImageData.transparentPixel != -1))
/*     */       {
/* 378 */         localObject = paramImageData.getTransparencyMask();
/* 379 */         arrayOfByte3 = ((ImageData)localObject).data;
/* 380 */         i13 = ((ImageData)localObject).bytesPerLine;
/* 381 */         i14 = 0; i15 = 0;
/* 382 */         for (int i16 = 0; i16 < n; i16++) {
/* 383 */           for (int i17 = 0; i17 < m; i17++) {
/* 384 */             int i18 = arrayOfByte3[(i15 + (i17 >> 3))];
/* 385 */             int i19 = 1 << 7 - (i17 & 0x7);
/* 386 */             arrayOfByte1[(i14 + i2)] = ((i18 & i19) != 0 ? -1 : 0);
/* 387 */             i14 += 4;
/*     */           }
/* 389 */           i15 += i13;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*     */         int i12;
/* 392 */         if (paramImageData.alpha != -1) {
/* 393 */           i9 = 1;
/* 394 */           int i10 = paramImageData.alpha;
/* 395 */           i12 = (byte)i10;
/* 396 */           for (i13 = 0; i13 < arrayOfByte1.length; i13 += 4)
/* 397 */             arrayOfByte1[(i13 + i2)] = i12;
/*     */         }
/* 399 */         else if (paramImageData.alphaData != null) {
/* 400 */           i9 = 1;
/* 401 */           byte[] arrayOfByte2 = new byte[paramImageData.alphaData.length];
/* 402 */           System.arraycopy(paramImageData.alphaData, 0, arrayOfByte2, 0, arrayOfByte2.length);
/*     */ 
/* 404 */           i12 = 0; i13 = 0;
/* 405 */           for (i14 = 0; i14 < n; i14++) {
/* 406 */             for (i15 = 0; i15 < m; i15++) {
/* 407 */               arrayOfByte1[(i12 + i2)] = arrayOfByte2[i13];
/* 408 */               i12 += 4;
/* 409 */               i13++;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 414 */       if (i9 == 0)
/* 415 */         for (int i11 = 0; i11 < arrayOfByte1.length; i11 += 4)
/* 416 */           arrayOfByte1[(i11 + i2)] = -1;
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 420 */       return null;
/*     */     }
/* 422 */     return arrayOfByte1;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swt.SWTFXUtils
 * JD-Core Version:    0.6.2
 */