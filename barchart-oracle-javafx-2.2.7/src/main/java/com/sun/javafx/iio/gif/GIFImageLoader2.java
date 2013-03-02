/*     */ package com.sun.javafx.iio.gif;
/*     */ 
/*     */ import com.sun.javafx.iio.ImageFrame;
/*     */ import com.sun.javafx.iio.ImageMetadata;
/*     */ import com.sun.javafx.iio.ImageStorage;
/*     */ import com.sun.javafx.iio.ImageStorage.ImageType;
/*     */ import com.sun.javafx.iio.common.ImageLoaderImpl;
/*     */ import com.sun.javafx.iio.common.ImageTools;
/*     */ import com.sun.javafx.iio.common.PushbroomScaler;
/*     */ import com.sun.javafx.iio.common.ScalerFactory;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class GIFImageLoader2 extends ImageLoaderImpl
/*     */ {
/*  27 */   static final byte[] FILE_SIG87 = { 71, 73, 70, 56, 55, 97 };
/*  28 */   static final byte[] FILE_SIG89 = { 71, 73, 70, 56, 57, 97 };
/*     */   static final int DEFAULT_FPS = 25;
/*  31 */   InputStream stream = null;
/*     */   int screenW;
/*     */   int screenH;
/*     */   int bgColor;
/*     */   byte[][] globalPalette;
/*     */   byte[] image;
/*     */ 
/*     */   public GIFImageLoader2(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/*  37 */     super(GIFDescriptor.getInstance());
/*  38 */     this.stream = paramInputStream;
/*  39 */     readGlobalHeader();
/*     */   }
/*     */ 
/*     */   private void readGlobalHeader() throws IOException
/*     */   {
/*  44 */     byte[] arrayOfByte = readBytes(new byte[6]);
/*  45 */     if ((!Arrays.equals(FILE_SIG87, arrayOfByte)) && (!Arrays.equals(FILE_SIG89, arrayOfByte))) {
/*  46 */       throw new IOException("Bad GIF signature!");
/*     */     }
/*  48 */     this.screenW = readShort();
/*  49 */     this.screenH = readShort();
/*  50 */     int i = readByte();
/*  51 */     this.bgColor = readByte();
/*  52 */     int j = readByte();
/*     */ 
/*  54 */     if ((i & 0x80) != 0) {
/*  55 */       this.globalPalette = readPalete(2 << (i & 0x7), -1);
/*     */     }
/*  57 */     this.image = new byte[this.screenW * this.screenH * 4];
/*     */   }
/*     */ 
/*     */   private byte[][] readPalete(int paramInt1, int paramInt2) throws IOException
/*     */   {
/*  62 */     byte[][] arrayOfByte = new byte[4][paramInt1];
/*  63 */     byte[] arrayOfByte1 = readBytes(new byte[paramInt1 * 3]);
/*  64 */     int i = 0; for (int j = 0; i != paramInt1; i++) {
/*  65 */       for (int k = 0; k != 3; k++) {
/*  66 */         arrayOfByte[k][i] = arrayOfByte1[(j++)];
/*     */       }
/*  68 */       arrayOfByte[3][i] = (i == paramInt2 ? 0 : -1);
/*     */     }
/*  70 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   private void consumeAnExtension() throws IOException
/*     */   {
/*  75 */     for (int i = readByte(); i != 0; i = readByte())
/*  76 */       skipBytes(i);
/*     */   }
/*     */ 
/*     */   private int readControlCode()
/*     */     throws IOException
/*     */   {
/*  83 */     int i = readByte();
/*  84 */     int j = readByte();
/*  85 */     int k = readShort();
/*  86 */     int m = readByte();
/*     */ 
/*  88 */     if ((i != 4) || (readByte() != 0)) {
/*  89 */       throw new IOException("Bad GIF GraphicControlExtension");
/*     */     }
/*  91 */     return ((j & 0x1F) << 24) + (m << 16) + k;
/*     */   }
/*     */ 
/*     */   private int waitForImageFrame()
/*     */     throws IOException
/*     */   {
/*  98 */     int i = 0;
/*     */     while (true) {
/* 100 */       int j = this.stream.read();
/* 101 */       switch (j) {
/*     */       case 44:
/* 103 */         return i;
/*     */       case 33:
/* 105 */         switch (readByte()) {
/*     */         case 249:
/* 107 */           i = readControlCode();
/* 108 */           break;
/*     */         default:
/* 110 */           consumeAnExtension();
/*     */         }
/* 112 */         break;
/*     */       case -1:
/*     */       case 59:
/* 114 */         return -1;
/*     */       default:
/* 116 */         throw new IOException("Unexpected GIF control characher 0x" + String.format("%02X", new Object[] { Integer.valueOf(j) })); }  }  } 
/* 124 */   private void decodeImage(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt) throws IOException { LZWDecoder localLZWDecoder = new LZWDecoder();
/* 125 */     byte[] arrayOfByte = localLZWDecoder.getString();
/* 126 */     int i = 0; int j = 0; int k = paramInt1;
/*     */     int m;
/*     */     int n;
/*     */     while (true) { m = localLZWDecoder.readString();
/* 129 */       if (m == -1) {
/* 130 */         localLZWDecoder.waitForTerminator();
/* 131 */         return;
/*     */       }
/* 133 */       for (n = 0; n != m; ) {
/* 134 */         int i1 = k < m - n ? k : m - n;
/* 135 */         System.arraycopy(arrayOfByte, n, paramArrayOfByte, j, i1);
/* 136 */         j += i1;
/* 137 */         n += i1;
/* 138 */         if (k -= i1 == 0) {
/* 139 */           i++; if (i == paramInt2) {
/* 140 */             localLZWDecoder.waitForTerminator();
/* 141 */             return;
/*     */           }
/* 143 */           int i2 = paramArrayOfInt == null ? i : paramArrayOfInt[i];
/* 144 */           j = i2 * paramInt1;
/* 145 */           k = paramInt1;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private int[] computeInterlaceReIndex(int paramInt)
/*     */   {
/* 153 */     int[] arrayOfInt = new int[paramInt]; int i = 0;
/* 154 */     for (int j = 0; j < paramInt; j += 8) arrayOfInt[(i++)] = j;
/* 155 */     for (j = 4; j < paramInt; j += 8) arrayOfInt[(i++)] = j;
/* 156 */     for (j = 2; j < paramInt; j += 4) arrayOfInt[(i++)] = j;
/* 157 */     for (j = 1; j < paramInt; j += 2) arrayOfInt[(i++)] = j;
/* 158 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   public ImageFrame load(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) throws IOException
/*     */   {
/* 163 */     int i = waitForImageFrame();
/*     */ 
/* 165 */     if (i < 0) {
/* 166 */       return null;
/*     */     }
/*     */ 
/* 169 */     int j = readShort(); int k = readShort(); int m = readShort(); int n = readShort();
/*     */ 
/* 172 */     if ((j + m > this.screenW) || (k + n > this.screenH)) {
/* 173 */       throw new IOException("Wrong GIF image frame size");
/*     */     }
/*     */ 
/* 176 */     int i1 = readByte();
/*     */ 
/* 178 */     int i2 = (i >>> 24 & 0x1) == 1 ? 1 : 0;
/* 179 */     int i3 = i2 != 0 ? i >>> 16 & 0xFF : -1;
/* 180 */     int i4 = (i1 & 0x80) != 0 ? 1 : 0;
/* 181 */     int i5 = (i1 & 0x40) != 0 ? 1 : 0;
/*     */ 
/* 183 */     byte[][] arrayOfByte = i4 != 0 ? readPalete(2 << (i1 & 0x7), i3) : this.globalPalette;
/*     */ 
/* 185 */     ImageMetadata localImageMetadata = updateMetadata(this.screenW, this.screenH, i & 0xFFFF);
/*     */ 
/* 187 */     int i6 = i >>> 26 & 0x7;
/* 188 */     byte[] arrayOfByte1 = new byte[m * n];
/* 189 */     decodeImage(arrayOfByte1, m, n, i5 != 0 ? computeInterlaceReIndex(n) : null);
/*     */ 
/* 191 */     ImageFrame localImageFrame = decodePalette(arrayOfByte1, arrayOfByte, i3, j, k, m, n, i6, localImageMetadata);
/*     */ 
/* 195 */     int[] arrayOfInt = ImageTools.computeDimensions(this.screenW, this.screenH, paramInt2, paramInt3, paramBoolean1);
/*     */ 
/* 197 */     if ((this.screenW != arrayOfInt[0]) || (this.screenH != arrayOfInt[1])) {
/* 198 */       localImageFrame = scaleImage(localImageFrame, arrayOfInt[0], arrayOfInt[1], paramBoolean2);
/*     */     }
/*     */ 
/* 201 */     return localImageFrame;
/*     */   }
/*     */ 
/*     */   private int readByte() throws IOException
/*     */   {
/* 206 */     int i = this.stream.read();
/* 207 */     if (i < 0) {
/* 208 */       throw new EOFException();
/*     */     }
/* 210 */     return i;
/*     */   }
/*     */ 
/*     */   private int readShort() throws IOException {
/* 214 */     int i = readByte(); int j = readByte();
/* 215 */     return i + (j << 8);
/*     */   }
/*     */ 
/*     */   private byte[] readBytes(byte[] paramArrayOfByte) throws IOException {
/* 219 */     return readBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */ 
/*     */   private byte[] readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
/* 223 */     while (paramInt2 > 0) {
/* 224 */       int i = this.stream.read(paramArrayOfByte, paramInt1, paramInt2);
/* 225 */       if (i < 0) {
/* 226 */         throw new EOFException();
/*     */       }
/* 228 */       paramInt1 += i;
/* 229 */       paramInt2 -= i;
/*     */     }
/* 231 */     return paramArrayOfByte;
/*     */   }
/*     */ 
/*     */   private void skipBytes(int paramInt) throws IOException {
/* 235 */     while (paramInt > 0) {
/* 236 */       int i = (int)this.stream.skip(paramInt);
/* 237 */       if (i < 0) {
/* 238 */         throw new EOFException();
/*     */       }
/* 240 */       paramInt -= i;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose() {
/*     */   }
/*     */ 
/*     */   private void fillBackground(int paramInt, byte[] paramArrayOfByte) {
/* 248 */     int i = this.globalPalette[0][this.bgColor]; int j = this.globalPalette[1][this.bgColor];
/* 249 */     int k = this.globalPalette[2][this.bgColor]; int m = this.bgColor == paramInt ? 0 : -1;
/* 250 */     int n = 0; int i1 = 0; for (int i2 = this.screenW * this.screenH; n != i2; n++) {
/* 251 */       paramArrayOfByte[(i1 + 0)] = i;
/* 252 */       paramArrayOfByte[(i1 + 1)] = j;
/* 253 */       paramArrayOfByte[(i1 + 2)] = k;
/* 254 */       paramArrayOfByte[(i1 + 3)] = m;
/*     */ 
/* 250 */       i1 += 4;
/*     */     }
/*     */   }
/*     */ 
/*     */   private ImageFrame decodePalette(byte[] paramArrayOfByte, byte[][] paramArrayOfByte1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, ImageMetadata paramImageMetadata)
/*     */   {
/* 262 */     byte[] arrayOfByte = paramInt6 == 3 ? (byte[])this.image.clone() : this.image;
/*     */ 
/* 264 */     for (int i = 0; i != paramInt5; i++) {
/* 265 */       int j = ((paramInt3 + i) * this.screenW + paramInt2) * 4;
/* 266 */       int k = i * paramInt4;
/*     */       int m;
/*     */       int n;
/* 267 */       if (paramInt1 < 0) {
/* 268 */         for (m = 0; m != paramInt4; m++) {
/* 269 */           n = 0xFF & paramArrayOfByte[(k + m)];
/* 270 */           arrayOfByte[(j + 0)] = paramArrayOfByte1[0][n];
/* 271 */           arrayOfByte[(j + 1)] = paramArrayOfByte1[1][n];
/* 272 */           arrayOfByte[(j + 2)] = paramArrayOfByte1[2][n];
/* 273 */           arrayOfByte[(j + 3)] = paramArrayOfByte1[3][n];
/*     */ 
/* 268 */           j += 4;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 276 */         for (m = 0; m != paramInt4; m++) {
/* 277 */           n = 0xFF & paramArrayOfByte[(k + m)];
/* 278 */           if (n != paramInt1) {
/* 279 */             arrayOfByte[(j + 0)] = paramArrayOfByte1[0][n];
/* 280 */             arrayOfByte[(j + 1)] = paramArrayOfByte1[1][n];
/* 281 */             arrayOfByte[(j + 2)] = paramArrayOfByte1[2][n];
/* 282 */             arrayOfByte[(j + 3)] = paramArrayOfByte1[3][n];
/*     */           }
/* 276 */           j += 4;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 288 */     if (paramInt6 != 3) arrayOfByte = (byte[])arrayOfByte.clone();
/* 289 */     if (paramInt6 == 2) fillBackground(paramInt1, this.image);
/*     */ 
/* 291 */     return new ImageFrame(ImageStorage.ImageType.RGBA, ByteBuffer.wrap(arrayOfByte), this.screenW, this.screenH, this.screenW * 4, (byte[][])null, paramImageMetadata);
/*     */   }
/*     */ 
/*     */   private ImageFrame scaleImage(ImageFrame paramImageFrame, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/* 298 */     byte[] arrayOfByte = ((ByteBuffer)paramImageFrame.getImageData()).array();
/* 299 */     int i = ImageStorage.getNumBands(paramImageFrame.getImageType());
/*     */ 
/* 301 */     PushbroomScaler localPushbroomScaler = ScalerFactory.createScaler(this.screenW, this.screenH, i, paramInt1, paramInt2, paramBoolean);
/*     */ 
/* 304 */     for (int j = 0; j != this.screenH; j++) {
/* 305 */       localPushbroomScaler.putSourceScanline(arrayOfByte, j * this.screenW * i);
/*     */     }
/*     */ 
/* 308 */     return new ImageFrame(paramImageFrame.getImageType(), localPushbroomScaler.getDestination(), paramInt1, paramInt2, paramInt1 * i, (byte[][])null, paramImageFrame.getMetadata());
/*     */   }
/*     */ 
/*     */   private ImageMetadata updateMetadata(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 314 */     ImageMetadata localImageMetadata = new ImageMetadata(null, Boolean.valueOf(true), null, null, null, Integer.valueOf(paramInt3 != 0 ? paramInt3 * 10 : 40), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), null, null, null);
/*     */ 
/* 316 */     updateImageMetadata(localImageMetadata);
/* 317 */     return localImageMetadata;
/*     */   }
/*     */ 
/*     */   class LZWDecoder
/*     */   {
/*     */     private final int initCodeSize;
/*     */     private final int clearCode;
/*     */     private final int eofCode;
/*     */     private int codeSize;
/*     */     private int codeMask;
/*     */     private int tableIndex;
/*     */     private int oldCode;
/* 325 */     private int blockLength = 0; private int blockPos = 0;
/* 326 */     private byte[] block = new byte['ÿ'];
/* 327 */     private int inData = 0; private int inBits = 0;
/*     */ 
/* 330 */     private int[] prefix = new int[4096];
/* 331 */     private byte[] suffix = new byte[4096];
/* 332 */     private byte[] initial = new byte[4096];
/* 333 */     private int[] length = new int[4096];
/* 334 */     private byte[] string = new byte[4096];
/*     */ 
/*     */     public LZWDecoder() throws IOException {
/* 337 */       this.initCodeSize = GIFImageLoader2.this.readByte();
/* 338 */       this.clearCode = (1 << this.initCodeSize);
/* 339 */       this.eofCode = (this.clearCode + 1);
/* 340 */       initTable();
/*     */     }
/*     */ 
/*     */     public final int readString() throws IOException
/*     */     {
/* 345 */       int i = getCode();
/* 346 */       if (i == this.eofCode)
/* 347 */         return -1;
/* 348 */       if (i == this.clearCode) {
/* 349 */         initTable();
/* 350 */         i = getCode();
/* 351 */         if (i == this.eofCode)
/* 352 */           return -1;
/*     */       }
/*     */       else
/*     */       {
/* 356 */         k = this.tableIndex;
/* 357 */         if (i < k) {
/* 358 */           j = i;
/*     */         } else {
/* 360 */           j = this.oldCode;
/* 361 */           if (i != k) {
/* 362 */             throw new IOException("Bad GIF LZW: Out-of-sequence code!");
/*     */           }
/*     */         }
/*     */ 
/* 366 */         m = this.oldCode;
/*     */ 
/* 368 */         this.prefix[k] = m;
/* 369 */         this.suffix[k] = this.initial[j];
/* 370 */         this.initial[k] = this.initial[m];
/* 371 */         this.length[m] += 1;
/*     */ 
/* 373 */         this.tableIndex += 1;
/* 374 */         if ((this.tableIndex == 1 << this.codeSize) && (this.tableIndex < 4096)) {
/* 375 */           this.codeSize += 1;
/* 376 */           this.codeMask = ((1 << this.codeSize) - 1);
/*     */         }
/*     */       }
/*     */ 
/* 380 */       int j = i;
/* 381 */       int k = this.length[j];
/* 382 */       for (int m = k - 1; m >= 0; m--) {
/* 383 */         this.string[m] = this.suffix[j];
/* 384 */         j = this.prefix[j];
/*     */       }
/*     */ 
/* 387 */       this.oldCode = i;
/* 388 */       return k;
/*     */     }
/*     */ 
/*     */     public final byte[] getString() {
/* 392 */       return this.string;
/*     */     }
/*     */ 
/*     */     public final void waitForTerminator() throws IOException {
/* 396 */       GIFImageLoader2.this.consumeAnExtension();
/*     */     }
/*     */ 
/*     */     private void initTable()
/*     */     {
/* 401 */       int i = 1 << this.initCodeSize;
/* 402 */       for (int j = 0; j < i; j++) {
/* 403 */         this.prefix[j] = -1;
/* 404 */         this.suffix[j] = ((byte)j);
/* 405 */         this.initial[j] = ((byte)j);
/* 406 */         this.length[j] = 1;
/*     */       }
/*     */ 
/* 411 */       for (j = i; j < 4096; j++) {
/* 412 */         this.prefix[j] = -1;
/* 413 */         this.length[j] = 1;
/*     */       }
/*     */ 
/* 416 */       this.codeSize = (this.initCodeSize + 1);
/* 417 */       this.codeMask = ((1 << this.codeSize) - 1);
/* 418 */       this.tableIndex = (i + 2);
/* 419 */       this.oldCode = 0;
/*     */     }
/*     */ 
/*     */     private int getCode() throws IOException
/*     */     {
/* 424 */       while (this.inBits < this.codeSize) {
/* 425 */         this.inData |= nextByte() << this.inBits;
/* 426 */         this.inBits += 8;
/*     */       }
/* 428 */       int i = this.inData & this.codeMask;
/* 429 */       this.inBits -= this.codeSize;
/* 430 */       this.inData >>>= this.codeSize;
/* 431 */       return i;
/*     */     }
/*     */ 
/*     */     private int nextByte() throws IOException
/*     */     {
/* 436 */       if (this.blockPos == this.blockLength) {
/* 437 */         readData();
/*     */       }
/* 439 */       return this.block[(this.blockPos++)] & 0xFF;
/*     */     }
/*     */ 
/*     */     private void readData() throws IOException
/*     */     {
/* 444 */       this.blockPos = 0;
/* 445 */       this.blockLength = GIFImageLoader2.this.readByte();
/* 446 */       if (this.blockLength > 0)
/* 447 */         GIFImageLoader2.this.readBytes(this.block, 0, this.blockLength);
/*     */       else
/* 449 */         throw new EOFException();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.gif.GIFImageLoader2
 * JD-Core Version:    0.6.2
 */