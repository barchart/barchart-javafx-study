/*     */ package com.sun.javafx.iio.png;
/*     */ 
/*     */ import com.sun.javafx.iio.ImageFrame;
/*     */ import com.sun.javafx.iio.ImageMetadata;
/*     */ import com.sun.javafx.iio.ImageStorage;
/*     */ import com.sun.javafx.iio.ImageStorage.ImageType;
/*     */ import com.sun.javafx.iio.common.ImageLoaderImpl;
/*     */ import com.sun.javafx.iio.common.ImageTools;
/*     */ import com.sun.javafx.iio.common.PushbroomScaler;
/*     */ import com.sun.javafx.iio.common.ScalerFactory;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.zip.Inflater;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ 
/*     */ public final class PNGImageLoader2 extends ImageLoaderImpl
/*     */ {
/*  24 */   static final byte[] FILE_SIG = { -119, 80, 78, 71, 13, 10, 26, 10 };
/*     */   static final int IHDR_TYPE = 1229472850;
/*     */   static final int PLTE_TYPE = 1347179589;
/*     */   static final int IDAT_TYPE = 1229209940;
/*     */   static final int IEND_TYPE = 1229278788;
/*     */   static final int tRNS_TYPE = 1951551059;
/*     */   static final int PNG_COLOR_GRAY = 0;
/*     */   static final int PNG_COLOR_RGB = 2;
/*     */   static final int PNG_COLOR_PALETTE = 3;
/*     */   static final int PNG_COLOR_GRAY_ALPHA = 4;
/*     */   static final int PNG_COLOR_RGB_ALPHA = 6;
/*  40 */   static final int[] numBandsPerColorType = { 1, -1, 3, 1, 2, -1, 4 };
/*     */   static final int PNG_FILTER_NONE = 0;
/*     */   static final int PNG_FILTER_SUB = 1;
/*     */   static final int PNG_FILTER_UP = 2;
/*     */   static final int PNG_FILTER_AVERAGE = 3;
/*     */   static final int PNG_FILTER_PAETH = 4;
/*     */   private final DataInputStream stream;
/*     */   private int width;
/*     */   private int height;
/*     */   private int bitDepth;
/*     */   private int colorType;
/*     */   private boolean isInterlaced;
/*  52 */   private boolean tRNS_present = false;
/*  53 */   private boolean tRNS_GRAY_RGB = false;
/*     */   private int trnsR;
/*     */   private int trnsG;
/*     */   private int trnsB;
/*     */   private byte[][] palette;
/* 507 */   private static final int[] starting_y = { 0, 0, 4, 0, 2, 0, 1, 0 };
/* 508 */   private static final int[] starting_x = { 0, 4, 0, 2, 0, 1, 0, 0 };
/* 509 */   private static final int[] increment_y = { 8, 8, 8, 4, 4, 2, 2, 1 };
/* 510 */   private static final int[] increment_x = { 8, 8, 4, 4, 2, 2, 1, 1 };
/*     */ 
/*     */   public PNGImageLoader2(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/*  59 */     super(PNGDescriptor.getInstance());
/*  60 */     this.stream = new DataInputStream(paramInputStream);
/*     */ 
/*  62 */     byte[] arrayOfByte = readBytes(new byte[8]);
/*     */ 
/*  64 */     if (!Arrays.equals(FILE_SIG, arrayOfByte)) {
/*  65 */       throw new IOException("Bad PNG signature!");
/*     */     }
/*     */ 
/*  68 */     readHeader();
/*     */   }
/*     */ 
/*     */   private void readHeader() throws IOException {
/*  72 */     int[] arrayOfInt = readChunk();
/*     */ 
/*  74 */     if ((arrayOfInt[1] != 1229472850) && (arrayOfInt[0] != 13)) {
/*  75 */       throw new IOException("Bad PNG header!");
/*     */     }
/*     */ 
/*  78 */     this.width = this.stream.readInt();
/*  79 */     this.height = this.stream.readInt();
/*     */ 
/*  81 */     if ((this.width == 0) || (this.height == 0)) {
/*  82 */       throw new IOException("Bad PNG image size!");
/*     */     }
/*     */ 
/*  85 */     this.bitDepth = this.stream.readByte();
/*  86 */     if ((this.bitDepth != 1) && (this.bitDepth != 2) && (this.bitDepth != 4) && (this.bitDepth != 8) && (this.bitDepth != 16))
/*     */     {
/*  88 */       throw new IOException("Bad PNG bit depth");
/*     */     }
/*     */ 
/*  91 */     this.colorType = this.stream.readByte();
/*     */ 
/*  93 */     if ((this.colorType > 6) || (this.colorType == 1) || (this.colorType == 5)) {
/*  94 */       throw new IOException("Bad PNG color type");
/*     */     }
/*     */ 
/*  99 */     if (((this.colorType != 3) && (this.colorType != 0) && (this.bitDepth < 8)) || ((this.colorType == 3) && (this.bitDepth == 16)))
/*     */     {
/* 101 */       throw new IOException("Bad color type/bit depth combination!");
/*     */     }
/*     */ 
/* 104 */     int i = this.stream.readByte();
/* 105 */     if (i != 0) {
/* 106 */       throw new IOException("Bad PNG comression!");
/*     */     }
/*     */ 
/* 109 */     int j = this.stream.readByte();
/* 110 */     if (j != 0) {
/* 111 */       throw new IOException("Bad PNG filter method!");
/*     */     }
/*     */ 
/* 114 */     int k = this.stream.readByte();
/*     */ 
/* 116 */     if ((k != 0) && (k != 1)) {
/* 117 */       throw new IOException("Unknown interlace method (not 0 or 1)!");
/*     */     }
/*     */ 
/* 120 */     int m = this.stream.readInt();
/*     */ 
/* 122 */     this.isInterlaced = (k == 1);
/*     */   }
/*     */ 
/*     */   private int[] readChunk() throws IOException {
/* 126 */     return new int[] { this.stream.readInt(), this.stream.readInt() };
/*     */   }
/*     */ 
/*     */   private byte[] readBytes(byte[] paramArrayOfByte) throws IOException {
/* 130 */     return readBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */ 
/*     */   private byte[] readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
/* 134 */     if (this.stream.read(paramArrayOfByte, paramInt1, paramInt2) != paramInt2) {
/* 135 */       throw new EOFException();
/*     */     }
/* 137 */     return paramArrayOfByte;
/*     */   }
/*     */ 
/*     */   private void skip(int paramInt) throws IOException {
/* 141 */     if (paramInt != this.stream.skipBytes(paramInt))
/* 142 */       throw new EOFException();
/*     */   }
/*     */ 
/*     */   private void readPaletteChunk(int paramInt) throws IOException
/*     */   {
/* 147 */     int i = paramInt / 3;
/* 148 */     int j = 1 << this.bitDepth;
/* 149 */     if (i > j) {
/* 150 */       emitWarning("PLTE chunk contains too many entries for bit depth, ignoring extras.");
/* 151 */       i = j;
/*     */     }
/*     */ 
/* 154 */     this.palette = new byte[3][j];
/*     */ 
/* 156 */     byte[] arrayOfByte = readBytes(new byte[paramInt]);
/*     */ 
/* 158 */     int k = 0; for (int m = 0; k != i; k++)
/* 159 */       for (int n = 0; n != 3; n++)
/* 160 */         this.palette[n][k] = arrayOfByte[(m++)];
/*     */   }
/*     */ 
/*     */   private void parsePaletteChink(int paramInt)
/*     */     throws IOException
/*     */   {
/* 166 */     if (this.palette != null) {
/* 167 */       emitWarning("A PNG image may not contain more than one PLTE chunk.\nThe chunk wil be ignored.");
/*     */ 
/* 170 */       skip(paramInt);
/* 171 */       return;
/*     */     }
/*     */ 
/* 174 */     switch (this.colorType) {
/*     */     case 3:
/* 176 */       readPaletteChunk(paramInt);
/* 177 */       return;
/*     */     case 0:
/*     */     case 4:
/* 180 */       emitWarning("A PNG gray or gray alpha image cannot have a PLTE chunk.\nThe chunk wil be ignored.");
/*     */     case 1:
/*     */     case 2:
/*     */     }
/* 184 */     skip(paramInt);
/*     */   }
/*     */ 
/*     */   private boolean readPaletteTransparency(int paramInt) throws IOException
/*     */   {
/* 189 */     if (this.palette == null) {
/* 190 */       emitWarning("tRNS chunk without prior PLTE chunk, ignoring it.");
/* 191 */       skip(paramInt);
/* 192 */       return false;
/*     */     }
/*     */ 
/* 195 */     byte[][] arrayOfByte = new byte[4][];
/*     */ 
/* 197 */     System.arraycopy(this.palette, 0, arrayOfByte, 0, 3);
/*     */ 
/* 199 */     int i = this.palette[0].length;
/* 200 */     arrayOfByte[3] = new byte[i];
/*     */ 
/* 202 */     int j = paramInt < i ? paramInt : i;
/* 203 */     readBytes(arrayOfByte[3], 0, j);
/*     */ 
/* 205 */     for (int k = j; k < i; k++) {
/* 206 */       arrayOfByte[3][k] = -1;
/*     */     }
/*     */ 
/* 209 */     if (j < paramInt) {
/* 210 */       skip(paramInt - j);
/*     */     }
/*     */ 
/* 213 */     this.palette = arrayOfByte;
/*     */ 
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean readGrayTransparency(int paramInt) throws IOException {
/* 219 */     if (paramInt == 2) {
/* 220 */       this.trnsG = this.stream.readShort();
/* 221 */       return true;
/*     */     }
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean readRgbTransparency(int paramInt) throws IOException {
/* 227 */     if (paramInt == 6) {
/* 228 */       this.trnsR = this.stream.readShort();
/* 229 */       this.trnsG = this.stream.readShort();
/* 230 */       this.trnsB = this.stream.readShort();
/* 231 */       return true;
/*     */     }
/* 233 */     return false;
/*     */   }
/*     */ 
/*     */   private void parseTransparencyChunk(int paramInt) throws IOException {
/* 237 */     switch (this.colorType) {
/*     */     case 3:
/* 239 */       this.tRNS_present = readPaletteTransparency(paramInt);
/* 240 */       break;
/*     */     case 0:
/* 242 */       this.tRNS_GRAY_RGB = (this.tRNS_present = readGrayTransparency(paramInt));
/* 243 */       break;
/*     */     case 2:
/* 245 */       this.tRNS_GRAY_RGB = (this.tRNS_present = readRgbTransparency(paramInt));
/* 246 */       break;
/*     */     case 1:
/*     */     default:
/* 248 */       emitWarning("TransparencyChunk may not present when alpha explicitly defined");
/* 249 */       skip(paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   private int parsePngMeta() throws IOException
/*     */   {
/*     */     while (true) {
/* 256 */       int[] arrayOfInt = readChunk();
/*     */ 
/* 258 */       switch (arrayOfInt[1]) {
/*     */       case 1229209940:
/* 260 */         return arrayOfInt[0];
/*     */       case 1229278788:
/* 262 */         return 0;
/*     */       case 1347179589:
/* 264 */         parsePaletteChink(arrayOfInt[0]);
/* 265 */         break;
/*     */       case 1951551059:
/* 267 */         parseTransparencyChunk(arrayOfInt[0]);
/* 268 */         break;
/*     */       default:
/* 270 */         skip(arrayOfInt[0]);
/*     */       }
/* 272 */       int i = this.stream.readInt();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose() {
/*     */   }
/*     */ 
/*     */   private ImageMetadata updateMetadata() {
/* 280 */     ImageMetadata localImageMetadata = new ImageMetadata(null, Boolean.valueOf(true), null, null, null, null, Integer.valueOf(this.width), Integer.valueOf(this.height), null, null, null);
/*     */ 
/* 282 */     updateImageMetadata(localImageMetadata);
/* 283 */     return localImageMetadata;
/*     */   }
/*     */ 
/*     */   private ImageStorage.ImageType getType() {
/* 287 */     switch (this.colorType) {
/*     */     case 0:
/* 289 */       return this.tRNS_present ? ImageStorage.ImageType.GRAY_ALPHA : ImageStorage.ImageType.GRAY;
/*     */     case 2:
/* 293 */       return this.tRNS_present ? ImageStorage.ImageType.RGBA : ImageStorage.ImageType.RGB;
/*     */     case 3:
/* 297 */       return ImageStorage.ImageType.PALETTE;
/*     */     case 4:
/* 299 */       return ImageStorage.ImageType.GRAY_ALPHA;
/*     */     case 6:
/* 301 */       return ImageStorage.ImageType.RGBA;
/*     */     case 1:
/* 303 */     case 5: } throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   private void doSubFilter(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 308 */     int i = paramArrayOfByte.length;
/* 309 */     for (int j = paramInt; j != i; j++)
/* 310 */       paramArrayOfByte[j] = ((byte)(paramArrayOfByte[j] + paramArrayOfByte[(j - paramInt)]));
/*     */   }
/*     */ 
/*     */   private void doUpFilter(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*     */   {
/* 315 */     int i = paramArrayOfByte1.length;
/* 316 */     for (int j = 0; j != i; j++)
/* 317 */       paramArrayOfByte1[j] = ((byte)(paramArrayOfByte1[j] + paramArrayOfByte2[j]));
/*     */   }
/*     */ 
/*     */   private void doAvrgFilter(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
/*     */   {
/* 322 */     int i = paramArrayOfByte1.length;
/* 323 */     for (int j = 0; j != paramInt; j++) {
/* 324 */       paramArrayOfByte1[j] = ((byte)(paramArrayOfByte1[j] + (paramArrayOfByte2[j] & 0xFF) / 2));
/*     */     }
/* 326 */     for (j = paramInt; j != i; j++)
/* 327 */       paramArrayOfByte1[j] = ((byte)(paramArrayOfByte1[j] + ((paramArrayOfByte1[(j - paramInt)] & 0xFF) + (paramArrayOfByte2[j] & 0xFF)) / 2));
/*     */   }
/*     */ 
/*     */   private static int paethPr(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 334 */     int i = Math.abs(paramInt2 - paramInt3);
/* 335 */     int j = Math.abs(paramInt1 - paramInt3);
/* 336 */     int k = Math.abs(paramInt2 - paramInt3 + paramInt1 - paramInt3);
/* 337 */     return j <= k ? paramInt2 : (i <= j) && (i <= k) ? paramInt1 : paramInt3;
/*     */   }
/*     */ 
/*     */   private void doPaethFilter(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt) {
/* 341 */     int i = paramArrayOfByte1.length;
/* 342 */     for (int j = 0; j != paramInt; j++) {
/* 343 */       paramArrayOfByte1[j] = ((byte)(paramArrayOfByte1[j] + paramArrayOfByte2[j]));
/*     */     }
/* 345 */     for (j = paramInt; j != i; j++)
/* 346 */       paramArrayOfByte1[j] = ((byte)(paramArrayOfByte1[j] + paethPr(paramArrayOfByte1[(j - paramInt)] & 0xFF, paramArrayOfByte2[j] & 0xFF, paramArrayOfByte2[(j - paramInt)] & 0xFF)));
/*     */   }
/*     */ 
/*     */   private void doFilter(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
/*     */   {
/* 352 */     switch (paramInt1) {
/*     */     case 1:
/* 354 */       doSubFilter(paramArrayOfByte1, paramInt2);
/* 355 */       break;
/*     */     case 2:
/* 357 */       doUpFilter(paramArrayOfByte1, paramArrayOfByte2);
/* 358 */       break;
/*     */     case 3:
/* 360 */       doAvrgFilter(paramArrayOfByte1, paramArrayOfByte2, paramInt2);
/* 361 */       break;
/*     */     case 4:
/* 363 */       doPaethFilter(paramArrayOfByte1, paramArrayOfByte2, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void downsample16to8trns_gray(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
/*     */   {
/* 369 */     int i = paramArrayOfByte1.length / 2;
/* 370 */     int j = 0; for (int k = paramInt1; j < i; j++) {
/* 371 */       int m = (short)((paramArrayOfByte1[(j * 2)] & 0xFF) * 256 + (paramArrayOfByte1[(j * 2 + 1)] & 0xFF));
/* 372 */       paramArrayOfByte2[(k + 0)] = paramArrayOfByte1[(j * 2)];
/* 373 */       paramArrayOfByte2[(k + 1)] = (m == this.trnsG ? 0 : -1);
/*     */ 
/* 370 */       k += paramInt2 * 2;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void downsample16to8trns_rgb(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
/*     */   {
/* 378 */     int i = paramArrayOfByte1.length / 2 / 3;
/* 379 */     int j = 0; for (int k = paramInt1; j < i; j++) {
/* 380 */       int m = j * 6;
/* 381 */       int n = (short)((paramArrayOfByte1[(m + 0)] & 0xFF) * 256 + (paramArrayOfByte1[(m + 1)] & 0xFF));
/* 382 */       int i1 = (short)((paramArrayOfByte1[(m + 2)] & 0xFF) * 256 + (paramArrayOfByte1[(m + 3)] & 0xFF));
/* 383 */       int i2 = (short)((paramArrayOfByte1[(m + 4)] & 0xFF) * 256 + (paramArrayOfByte1[(m + 5)] & 0xFF));
/* 384 */       paramArrayOfByte2[(k + 0)] = paramArrayOfByte1[(m + 0)];
/* 385 */       paramArrayOfByte2[(k + 1)] = paramArrayOfByte1[(m + 2)];
/* 386 */       paramArrayOfByte2[(k + 2)] = paramArrayOfByte1[(m + 4)];
/* 387 */       paramArrayOfByte2[(k + 3)] = ((n == this.trnsR) && (i1 == this.trnsG) && (i2 == this.trnsB) ? 0 : -1);
/*     */ 
/* 379 */       k += paramInt2 * 4;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void downsample16to8_plain(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 393 */     int i = paramArrayOfByte1.length / 2 / paramInt3 * paramInt3; int j = paramInt2 * paramInt3;
/* 394 */     int k = 0; for (int m = paramInt1; k != i; k += paramInt3) {
/* 395 */       for (int n = 0; n != paramInt3; n++)
/* 396 */         paramArrayOfByte2[(m + n)] = paramArrayOfByte1[((k + n) * 2)];
/* 394 */       m += j;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void downsample16to8(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 402 */     if (!this.tRNS_GRAY_RGB)
/* 403 */       downsample16to8_plain(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramInt2, paramInt3);
/* 404 */     else if (this.colorType == 0)
/* 405 */       downsample16to8trns_gray(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramInt2);
/* 406 */     else if (this.colorType == 2)
/* 407 */       downsample16to8trns_rgb(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   private void copyTrns_gray(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
/*     */   {
/* 412 */     int i = (byte)this.trnsG;
/* 413 */     int j = 0; int k = paramInt1; for (int m = paramArrayOfByte1.length; j < m; j++) {
/* 414 */       int n = paramArrayOfByte1[j];
/* 415 */       paramArrayOfByte2[k] = n;
/* 416 */       paramArrayOfByte2[(k + 1)] = (n == i ? 0 : -1);
/*     */ 
/* 413 */       k += 2 * paramInt2;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void copyTrns_rgb(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
/*     */   {
/* 421 */     int i = (byte)this.trnsR; int j = (byte)this.trnsG; int k = (byte)this.trnsB;
/* 422 */     int m = paramArrayOfByte1.length / 3;
/* 423 */     int n = 0; for (int i1 = paramInt1; n < m; n++) {
/* 424 */       int i2 = paramArrayOfByte1[(n * 3)]; int i3 = paramArrayOfByte1[(n * 3 + 1)]; int i4 = paramArrayOfByte1[(n * 3 + 2)];
/* 425 */       paramArrayOfByte2[(i1 + 0)] = i2;
/* 426 */       paramArrayOfByte2[(i1 + 1)] = i3;
/* 427 */       paramArrayOfByte2[(i1 + 2)] = i4;
/* 428 */       paramArrayOfByte2[(i1 + 3)] = ((i2 == i) && (i3 == j) && (i4 == k) ? 0 : -1);
/*     */ 
/* 423 */       i1 += paramInt2 * 4;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void copy_plain(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 433 */     int i = paramArrayOfByte1.length; int j = paramInt2 * paramInt3;
/* 434 */     int k = 0; for (int m = paramInt1; k != i; k += paramInt3) {
/* 435 */       for (int n = 0; n != paramInt3; n++)
/* 436 */         paramArrayOfByte2[(m + n)] = paramArrayOfByte1[(k + n)];
/* 434 */       m += j;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void copy(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 442 */     if (!this.tRNS_GRAY_RGB) {
/* 443 */       if (paramInt2 == 1)
/* 444 */         System.arraycopy(paramArrayOfByte1, 0, paramArrayOfByte2, paramInt1, paramArrayOfByte1.length);
/*     */       else
/* 446 */         copy_plain(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramInt2, paramInt3);
/*     */     }
/* 448 */     else if (this.colorType == 0)
/* 449 */       copyTrns_gray(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramInt2);
/* 450 */     else if (this.colorType == 2)
/* 451 */       copyTrns_rgb(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   private void upsampleTo8Palette(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 456 */     int i = 8 / this.bitDepth;
/* 457 */     int j = (1 << this.bitDepth) - 1;
/* 458 */     int k = 0; for (int m = 0; k < paramInt2; k += i) {
/* 459 */       int n = paramInt2 - k < i ? paramInt2 - k : i;
/* 460 */       int i1 = paramArrayOfByte1[m] >> (i - n) * this.bitDepth;
/* 461 */       for (int i2 = n - 1; i2 >= 0; i2--) {
/* 462 */         paramArrayOfByte2[(paramInt1 + (k + i2) * paramInt3)] = ((byte)(i1 & j));
/* 463 */         i1 >>= this.bitDepth;
/*     */       }
/* 458 */       m++;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void upsampleTo8Gray(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 469 */     int i = 8 / this.bitDepth;
/* 470 */     int j = (1 << this.bitDepth) - 1; int k = j / 2;
/* 471 */     int m = 0; for (int n = 0; m < paramInt2; m += i) {
/* 472 */       int i1 = paramInt2 - m < i ? paramInt2 - m : i;
/* 473 */       int i2 = paramArrayOfByte1[n] >> (i - i1) * this.bitDepth;
/* 474 */       for (int i3 = i1 - 1; i3 >= 0; i3--) {
/* 475 */         paramArrayOfByte2[(paramInt1 + (m + i3) * paramInt3)] = ((byte)(((i2 & j) * 255 + k) / j));
/* 476 */         i2 >>= this.bitDepth;
/*     */       }
/* 471 */       n++;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void upsampleTo8GrayTrns(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 482 */     int i = 8 / this.bitDepth;
/* 483 */     int j = (1 << this.bitDepth) - 1; int k = j / 2;
/* 484 */     int m = 0; for (int n = 0; m < paramInt2; m += i) {
/* 485 */       int i1 = paramInt2 - m < i ? paramInt2 - m : i;
/* 486 */       int i2 = paramArrayOfByte1[n] >> (i - i1) * this.bitDepth;
/* 487 */       for (int i3 = i1 - 1; i3 >= 0; i3--) {
/* 488 */         int i4 = paramInt1 + (m + i3) * paramInt3 * 2;
/* 489 */         int i5 = i2 & j;
/* 490 */         paramArrayOfByte2[i4] = ((byte)((i5 * 255 + k) / j));
/* 491 */         paramArrayOfByte2[(i4 + 1)] = (i5 == this.trnsG ? 0 : -1);
/* 492 */         i2 >>= this.bitDepth;
/*     */       }
/* 484 */       n++;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void upsampleTo8(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 498 */     if (this.colorType == 3)
/* 499 */       upsampleTo8Palette(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramInt2, paramInt3);
/* 500 */     else if (paramInt4 == 1)
/* 501 */       upsampleTo8Gray(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramInt2, paramInt3);
/* 502 */     else if ((this.tRNS_GRAY_RGB) && (paramInt4 == 2))
/* 503 */       upsampleTo8GrayTrns(paramArrayOfByte1, paramArrayOfByte2, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   private static int mipSize(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 513 */     return (paramInt1 - paramArrayOfInt1[paramInt2] + paramArrayOfInt2[paramInt2] - 1) / paramArrayOfInt2[paramInt2];
/*     */   }
/*     */ 
/*     */   private static int mipPos(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2) {
/* 517 */     return paramArrayOfInt1[paramInt2] + paramInt1 * paramArrayOfInt2[paramInt2];
/*     */   }
/*     */ 
/*     */   private void loadMip(byte[] paramArrayOfByte, InputStream paramInputStream, int paramInt) throws IOException
/*     */   {
/* 522 */     int i = mipSize(this.width, paramInt, starting_x, increment_x);
/* 523 */     int j = mipSize(this.height, paramInt, starting_y, increment_y);
/*     */ 
/* 525 */     int k = (i * this.bitDepth * numBandsPerColorType[this.colorType] + 7) / 8;
/* 526 */     Object localObject1 = new byte[k];
/* 527 */     Object localObject2 = new byte[k];
/*     */ 
/* 531 */     int m = bpp(); int n = numBandsPerColorType[this.colorType] * bytesPerColor();
/*     */ 
/* 533 */     for (int i1 = 0; i1 != j; i1++) {
/* 534 */       int i2 = paramInputStream.read();
/* 535 */       if (i2 == -1) {
/* 536 */         throw new EOFException();
/*     */       }
/*     */ 
/* 539 */       if (paramInputStream.read((byte[])localObject1) != k) {
/* 540 */         throw new EOFException();
/*     */       }
/*     */ 
/* 543 */       doFilter((byte[])localObject1, (byte[])localObject2, i2, n);
/*     */ 
/* 545 */       int i3 = (mipPos(i1, paramInt, starting_y, increment_y) * this.width + starting_x[paramInt]) * m;
/* 546 */       int i4 = increment_x[paramInt];
/*     */ 
/* 548 */       if (this.bitDepth == 16)
/* 549 */         downsample16to8((byte[])localObject1, paramArrayOfByte, i3, i4, m);
/* 550 */       else if (this.bitDepth < 8)
/* 551 */         upsampleTo8((byte[])localObject1, paramArrayOfByte, i3, i, i4, m);
/*     */       else {
/* 553 */         copy((byte[])localObject1, paramArrayOfByte, i3, i4, m);
/*     */       }
/*     */ 
/* 556 */       Object localObject3 = localObject1;
/* 557 */       localObject1 = localObject2;
/* 558 */       localObject2 = localObject3;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void load(byte[] paramArrayOfByte, InputStream paramInputStream) throws IOException {
/* 563 */     if (this.isInterlaced) {
/* 564 */       for (int i = 0; i != 7; i++) {
/* 565 */         if ((this.width > starting_x[i]) && (this.height > starting_y[i]))
/* 566 */           loadMip(paramArrayOfByte, paramInputStream, i);
/*     */       }
/*     */     }
/*     */     else
/* 570 */       loadMip(paramArrayOfByte, paramInputStream, 7);
/*     */   }
/*     */ 
/*     */   private ImageFrame decodePalette(byte[] paramArrayOfByte, ImageMetadata paramImageMetadata)
/*     */   {
/* 575 */     int i = this.tRNS_present ? 4 : 3;
/* 576 */     byte[] arrayOfByte = new byte[this.width * this.height * i];
/* 577 */     int j = this.width * this.height;
/*     */     int k;
/*     */     int m;
/*     */     int n;
/* 579 */     if (this.tRNS_present) {
/* 580 */       k = 0; for (m = 0; k != j; k++) {
/* 581 */         n = 0xFF & paramArrayOfByte[k];
/* 582 */         arrayOfByte[(m + 0)] = this.palette[0][n];
/* 583 */         arrayOfByte[(m + 1)] = this.palette[1][n];
/* 584 */         arrayOfByte[(m + 2)] = this.palette[2][n];
/* 585 */         arrayOfByte[(m + 3)] = this.palette[3][n];
/*     */ 
/* 580 */         m += 4;
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 588 */       k = 0; for (m = 0; k != j; k++) {
/* 589 */         n = 0xFF & paramArrayOfByte[k];
/* 590 */         arrayOfByte[(m + 0)] = this.palette[0][n];
/* 591 */         arrayOfByte[(m + 1)] = this.palette[1][n];
/* 592 */         arrayOfByte[(m + 2)] = this.palette[2][n];
/*     */ 
/* 588 */         m += 3;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 596 */     ImageStorage.ImageType localImageType = this.tRNS_present ? ImageStorage.ImageType.RGBA : ImageStorage.ImageType.RGB;
/*     */ 
/* 600 */     return new ImageFrame(localImageType, ByteBuffer.wrap(arrayOfByte), this.width, this.height, this.width * i, (byte[][])null, paramImageMetadata);
/*     */   }
/*     */ 
/*     */   private int bpp()
/*     */   {
/* 610 */     return numBandsPerColorType[this.colorType] + (this.tRNS_GRAY_RGB ? 1 : 0);
/*     */   }
/*     */ 
/*     */   private int bytesPerColor() {
/* 614 */     return this.bitDepth == 16 ? 2 : 1;
/*     */   }
/*     */ 
/*     */   public ImageFrame load(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
/*     */     throws IOException
/*     */   {
/* 620 */     if (paramInt1 != 0) {
/* 621 */       return null;
/*     */     }
/*     */ 
/* 624 */     int i = parsePngMeta();
/*     */ 
/* 626 */     if (i == 0) {
/* 627 */       emitWarning("No image data in PNG");
/* 628 */       return null;
/*     */     }
/*     */ 
/* 631 */     int j = bpp();
/* 632 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(j * this.width * this.height);
/* 633 */     ImageMetadata localImageMetadata = updateMetadata();
/*     */ 
/* 635 */     PNGIDATChunkInputStream localPNGIDATChunkInputStream = new PNGIDATChunkInputStream(this.stream, i);
/* 636 */     Inflater localInflater = new Inflater();
/* 637 */     BufferedInputStream localBufferedInputStream = new BufferedInputStream(new InflaterInputStream(localPNGIDATChunkInputStream, localInflater));
/*     */     try
/*     */     {
/* 640 */       load(localByteBuffer.array(), localBufferedInputStream);
/*     */     } catch (IOException localIOException) {
/* 642 */       throw localIOException;
/*     */     } finally {
/* 644 */       if (localInflater != null) {
/* 645 */         localInflater.end();
/*     */       }
/*     */     }
/*     */ 
/* 649 */     ImageFrame localImageFrame = this.colorType == 3 ? decodePalette(localByteBuffer.array(), localImageMetadata) : new ImageFrame(getType(), localByteBuffer, this.width, this.height, j * this.width, this.palette, localImageMetadata);
/*     */ 
/* 654 */     int[] arrayOfInt = ImageTools.computeDimensions(this.width, this.height, paramInt2, paramInt3, paramBoolean1);
/*     */ 
/* 656 */     if ((this.width != arrayOfInt[0]) || (this.height != arrayOfInt[1])) {
/* 657 */       localImageFrame = scaleImage(localImageFrame, arrayOfInt[0], arrayOfInt[1], paramBoolean2);
/*     */     }
/*     */ 
/* 660 */     return localImageFrame;
/*     */   }
/*     */ 
/*     */   private ImageFrame scaleImage(ImageFrame paramImageFrame, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 664 */     byte[] arrayOfByte = ((ByteBuffer)paramImageFrame.getImageData()).array();
/* 665 */     int i = ImageStorage.getNumBands(paramImageFrame.getImageType());
/*     */ 
/* 667 */     PushbroomScaler localPushbroomScaler = ScalerFactory.createScaler(this.width, this.height, i, paramInt1, paramInt2, paramBoolean);
/*     */ 
/* 670 */     for (int j = 0; j != this.height; j++) {
/* 671 */       localPushbroomScaler.putSourceScanline(arrayOfByte, j * this.width * i);
/*     */     }
/*     */ 
/* 674 */     return new ImageFrame(paramImageFrame.getImageType(), localPushbroomScaler.getDestination(), paramInt1, paramInt2, paramInt1 * i, (byte[][])null, paramImageFrame.getMetadata());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.png.PNGImageLoader2
 * JD-Core Version:    0.6.2
 */