/*     */ package com.sun.javafx.iio.gif;
/*     */ 
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
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
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ public class GIFImageLoader extends ImageLoaderImpl
/*     */ {
/*  35 */   InputStream stream = null;
/*     */ 
/*  38 */   boolean gotHeader = false;
/*  39 */   boolean skipTransparency = false;
/*     */ 
/*  41 */   GIFStreamMetadata streamMetadata = null;
/*     */ 
/*  43 */   int currIndex = -1;
/*     */ 
/*  45 */   GIFImageMetadata imageMetadata = null;
/*     */ 
/*  49 */   List imageStartPosition = new ArrayList();
/*     */ 
/*  54 */   int numImages = -1;
/*     */ 
/*  56 */   byte[] block = new byte['ÿ'];
/*  57 */   int blockLength = 0;
/*  58 */   int bitPos = 0;
/*  59 */   int nextByte = 0;
/*     */   int initCodeSize;
/*     */   int clearCode;
/*     */   int eofCode;
/*  64 */   int next32Bits = 0;
/*     */ 
/*  67 */   boolean lastBlockFound = false;
/*     */   ImageStorage.ImageType sourceType;
/*  71 */   ImageFrame theImage = null;
/*  72 */   boolean firstImageflag = true;
/*     */ 
/*  74 */   byte[] theTile = null;
/*     */ 
/*  76 */   int width = -1; int height = -1;
/*  77 */   int fullImageWidth = -1;
/*  78 */   int fullImageHeight = -1;
/*  79 */   int fullBytesPerRow = -1;
/*     */ 
/*  81 */   int streamX = -1; int streamY = -1;
/*     */ 
/*  83 */   int rowsDone = 0;
/*     */ 
/*  85 */   int interlacePass = 0;
/*     */   int destWidth;
/*     */   int destHeight;
/*     */   boolean isScaling;
/*     */   PushbroomScaler scaler;
/*  95 */   static final int[] interlaceIncrement = { 8, 8, 4, 2, -1 };
/*  96 */   static final int[] interlaceOffset = { 0, 4, 2, 1, -1 };
/*     */   Rectangle sourceRegion;
/*     */   int sourceXSubsampling;
/*     */   int sourceYSubsampling;
/*     */   int sourceMinProgressivePass;
/*     */   int sourceMaxProgressivePass;
/*     */   Point2D destinationOffset;
/*     */   Rectangle destinationRegion;
/*     */   int updateMinY;
/*     */   int updateYStep;
/* 253 */   boolean decodeThisRow = true;
/* 254 */   int destY = 0;
/*     */   byte[] rowBuf;
/*     */   byte[] destBuf;
/* 257 */   boolean doneScaling = false;
/*     */ 
/*     */   static short readUnsignedShort(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/*  99 */     return (short)(paramInputStream.read() | paramInputStream.read() << 8);
/*     */   }
/*     */ 
/*     */   public GIFImageLoader(InputStream paramInputStream) throws IOException {
/* 103 */     super(GIFDescriptor.getInstance());
/* 104 */     if (paramInputStream == null) {
/* 105 */       throw new IllegalArgumentException("input == null!");
/*     */     }
/* 107 */     this.stream = paramInputStream;
/*     */   }
/*     */ 
/*     */   public byte[][] getPalette()
/*     */   {
/*     */     byte[] arrayOfByte1;
/* 113 */     if (this.imageMetadata.localColorTable != null)
/* 114 */       arrayOfByte1 = this.imageMetadata.localColorTable;
/*     */     else {
/* 116 */       arrayOfByte1 = this.streamMetadata.globalColorTable;
/*     */     }
/*     */ 
/* 120 */     int i = arrayOfByte1.length / 3;
/*     */     int j;
/* 122 */     if (i == 2)
/* 123 */       j = 1;
/* 124 */     else if (i == 4)
/* 125 */       j = 2;
/* 126 */     else if ((i == 8) || (i == 16))
/*     */     {
/* 128 */       j = 4;
/*     */     }
/*     */     else {
/* 131 */       j = 8;
/*     */     }
/* 133 */     int k = 1 << j;
/* 134 */     byte[] arrayOfByte2 = new byte[k];
/* 135 */     byte[] arrayOfByte3 = new byte[k];
/* 136 */     byte[] arrayOfByte4 = new byte[k];
/*     */ 
/* 139 */     int m = 0;
/* 140 */     for (int n = 0; n < i; n++) {
/* 141 */       arrayOfByte2[n] = arrayOfByte1[(m++)];
/* 142 */       arrayOfByte3[n] = arrayOfByte1[(m++)];
/* 143 */       arrayOfByte4[n] = arrayOfByte1[(m++)];
/*     */     }
/*     */ 
/* 146 */     byte[] arrayOfByte5 = null;
/* 147 */     if (this.imageMetadata.transparentColorFlag) {
/* 148 */       arrayOfByte5 = new byte[k];
/* 149 */       Arrays.fill(arrayOfByte5, (byte)-1);
/*     */ 
/* 153 */       int i1 = Math.min(this.imageMetadata.transparentColorIndex, k - 1);
/*     */ 
/* 155 */       arrayOfByte5[i1] = 0;
/*     */     }
/*     */ 
/* 158 */     int[] arrayOfInt = new int[1];
/* 159 */     arrayOfInt[0] = j;
/* 160 */     byte[][] arrayOfByte = new byte[arrayOfByte5 != null ? 4 : 3][];
/* 161 */     arrayOfByte[0] = arrayOfByte2;
/* 162 */     arrayOfByte[1] = arrayOfByte3;
/* 163 */     arrayOfByte[2] = arrayOfByte4;
/* 164 */     if (arrayOfByte5 != null) {
/* 165 */       arrayOfByte[3] = arrayOfByte5;
/*     */     }
/*     */ 
/* 168 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   private void initNext32Bits()
/*     */   {
/* 173 */     this.next32Bits = (this.block[0] & 0xFF);
/* 174 */     this.next32Bits |= (this.block[1] & 0xFF) << 8;
/* 175 */     this.next32Bits |= (this.block[2] & 0xFF) << 16;
/* 176 */     this.next32Bits |= this.block[3] << 24;
/* 177 */     this.nextByte = 4; } 
/*     */   private int getCode(int paramInt1, int paramInt2) throws IOException { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 16	com/sun/javafx/iio/gif/GIFImageLoader:bitPos	I
/*     */     //   4: iload_1
/*     */     //   5: iadd
/*     */     //   6: bipush 32
/*     */     //   8: if_icmple +8 -> 16
/*     */     //   11: aload_0
/*     */     //   12: getfield 45	com/sun/javafx/iio/gif/GIFImageLoader:eofCode	I
/*     */     //   15: ireturn
/*     */     //   16: aload_0
/*     */     //   17: getfield 18	com/sun/javafx/iio/gif/GIFImageLoader:next32Bits	I
/*     */     //   20: aload_0
/*     */     //   21: getfield 16	com/sun/javafx/iio/gif/GIFImageLoader:bitPos	I
/*     */     //   24: ishr
/*     */     //   25: iload_2
/*     */     //   26: iand
/*     */     //   27: istore_3
/*     */     //   28: aload_0
/*     */     //   29: dup
/*     */     //   30: getfield 16	com/sun/javafx/iio/gif/GIFImageLoader:bitPos	I
/*     */     //   33: iload_1
/*     */     //   34: iadd
/*     */     //   35: putfield 16	com/sun/javafx/iio/gif/GIFImageLoader:bitPos	I
/*     */     //   38: aload_0
/*     */     //   39: getfield 16	com/sun/javafx/iio/gif/GIFImageLoader:bitPos	I
/*     */     //   42: bipush 8
/*     */     //   44: if_icmplt +152 -> 196
/*     */     //   47: aload_0
/*     */     //   48: getfield 19	com/sun/javafx/iio/gif/GIFImageLoader:lastBlockFound	Z
/*     */     //   51: ifne +145 -> 196
/*     */     //   54: aload_0
/*     */     //   55: dup
/*     */     //   56: getfield 18	com/sun/javafx/iio/gif/GIFImageLoader:next32Bits	I
/*     */     //   59: bipush 8
/*     */     //   61: iushr
/*     */     //   62: putfield 18	com/sun/javafx/iio/gif/GIFImageLoader:next32Bits	I
/*     */     //   65: aload_0
/*     */     //   66: dup
/*     */     //   67: getfield 16	com/sun/javafx/iio/gif/GIFImageLoader:bitPos	I
/*     */     //   70: bipush 8
/*     */     //   72: isub
/*     */     //   73: putfield 16	com/sun/javafx/iio/gif/GIFImageLoader:bitPos	I
/*     */     //   76: aload_0
/*     */     //   77: getfield 17	com/sun/javafx/iio/gif/GIFImageLoader:nextByte	I
/*     */     //   80: aload_0
/*     */     //   81: getfield 15	com/sun/javafx/iio/gif/GIFImageLoader:blockLength	I
/*     */     //   84: if_icmplt +81 -> 165
/*     */     //   87: aload_0
/*     */     //   88: aload_0
/*     */     //   89: getfield 4	com/sun/javafx/iio/gif/GIFImageLoader:stream	Ljava/io/InputStream;
/*     */     //   92: invokevirtual 1	java/io/InputStream:read	()I
/*     */     //   95: putfield 15	com/sun/javafx/iio/gif/GIFImageLoader:blockLength	I
/*     */     //   98: aload_0
/*     */     //   99: getfield 15	com/sun/javafx/iio/gif/GIFImageLoader:blockLength	I
/*     */     //   102: ifne +10 -> 112
/*     */     //   105: aload_0
/*     */     //   106: iconst_1
/*     */     //   107: putfield 19	com/sun/javafx/iio/gif/GIFImageLoader:lastBlockFound	Z
/*     */     //   110: iload_3
/*     */     //   111: ireturn
/*     */     //   112: aload_0
/*     */     //   113: getfield 15	com/sun/javafx/iio/gif/GIFImageLoader:blockLength	I
/*     */     //   116: istore 4
/*     */     //   118: iconst_0
/*     */     //   119: istore 5
/*     */     //   121: iload 4
/*     */     //   123: ifle +37 -> 160
/*     */     //   126: aload_0
/*     */     //   127: getfield 4	com/sun/javafx/iio/gif/GIFImageLoader:stream	Ljava/io/InputStream;
/*     */     //   130: aload_0
/*     */     //   131: getfield 14	com/sun/javafx/iio/gif/GIFImageLoader:block	[B
/*     */     //   134: iload 5
/*     */     //   136: iload 4
/*     */     //   138: invokestatic 46	com/sun/javafx/iio/common/ImageTools:readFully	(Ljava/io/InputStream;[BII)I
/*     */     //   141: istore 6
/*     */     //   143: iload 5
/*     */     //   145: iload 6
/*     */     //   147: iadd
/*     */     //   148: istore 5
/*     */     //   150: iload 4
/*     */     //   152: iload 6
/*     */     //   154: isub
/*     */     //   155: istore 4
/*     */     //   157: goto -36 -> 121
/*     */     //   160: aload_0
/*     */     //   161: iconst_0
/*     */     //   162: putfield 17	com/sun/javafx/iio/gif/GIFImageLoader:nextByte	I
/*     */     //   165: aload_0
/*     */     //   166: dup
/*     */     //   167: getfield 18	com/sun/javafx/iio/gif/GIFImageLoader:next32Bits	I
/*     */     //   170: aload_0
/*     */     //   171: getfield 14	com/sun/javafx/iio/gif/GIFImageLoader:block	[B
/*     */     //   174: aload_0
/*     */     //   175: dup
/*     */     //   176: getfield 17	com/sun/javafx/iio/gif/GIFImageLoader:nextByte	I
/*     */     //   179: dup_x1
/*     */     //   180: iconst_1
/*     */     //   181: iadd
/*     */     //   182: putfield 17	com/sun/javafx/iio/gif/GIFImageLoader:nextByte	I
/*     */     //   185: baload
/*     */     //   186: bipush 24
/*     */     //   188: ishl
/*     */     //   189: ior
/*     */     //   190: putfield 18	com/sun/javafx/iio/gif/GIFImageLoader:next32Bits	I
/*     */     //   193: goto -155 -> 38
/*     */     //   196: iload_3
/*     */     //   197: ireturn } 
/* 228 */   public void initializeStringTable(int[] paramArrayOfInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt2) { int i = 1 << this.initCodeSize;
/* 229 */     for (int j = 0; j < i; j++) {
/* 230 */       paramArrayOfInt1[j] = -1;
/* 231 */       paramArrayOfByte1[j] = ((byte)j);
/* 232 */       paramArrayOfByte2[j] = ((byte)j);
/* 233 */       paramArrayOfInt2[j] = 1;
/*     */     }
/*     */ 
/* 238 */     for (j = i; j < 4096; j++) {
/* 239 */       paramArrayOfInt1[j] = -1;
/* 240 */       paramArrayOfInt2[j] = 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void outputRow()
/*     */   {
/* 261 */     int i = Math.min(this.sourceRegion.width, this.destinationRegion.width * this.sourceXSubsampling);
/*     */ 
/* 263 */     int j = this.destinationRegion.x * (this.theImage.getStride() / this.theImage.getWidth());
/*     */ 
/* 266 */     System.arraycopy(this.theTile, this.destY * this.theImage.getStride() + j, this.destBuf, 0, this.destBuf.length);
/*     */ 
/* 268 */     if ((!this.imageMetadata.interlaceFlag) || (!this.isScaling))
/*     */     {
/* 270 */       ImageTools.convert(i, 1, this.sourceType, this.rowBuf, 0, this.rowBuf.length, this.destBuf, 0, this.destBuf.length, getPalette(), this.imageMetadata.transparentColorIndex, this.skipTransparency);
/*     */     }
/*     */ 
/* 277 */     if (this.sourceXSubsampling == 1) {
/* 278 */       if (!this.imageMetadata.interlaceFlag) {
/* 279 */         if (this.isScaling) {
/* 280 */           if (!this.doneScaling)
/* 281 */             this.doneScaling = this.scaler.putSourceScanline(this.destBuf, 0);
/*     */         }
/*     */         else
/* 284 */           System.arraycopy(this.destBuf, 0, this.theTile, this.destY * this.theImage.getStride() + j, this.destBuf.length);
/*     */       }
/*     */       else
/*     */       {
/* 288 */         byte[] arrayOfByte = this.isScaling ? this.rowBuf : this.destBuf;
/* 289 */         System.arraycopy(arrayOfByte, 0, this.theTile, this.destY * this.theImage.getStride() + j, arrayOfByte.length);
/*     */       }
/*     */     }
/*     */     else {
/* 293 */       if (!$assertionsDisabled) throw new AssertionError();
/* 294 */       for (int k = 0; k < i; j++) {
/* 295 */         this.theTile[(this.destY * this.theImage.getStride() + j)] = this.rowBuf[k];
/*     */ 
/* 294 */         k += this.sourceXSubsampling;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void computeDecodeThisRow()
/*     */   {
/* 302 */     this.decodeThisRow = ((this.destY < this.destinationRegion.y + this.destinationRegion.height) && (this.streamY >= this.sourceRegion.y) && (this.streamY < this.sourceRegion.y + this.sourceRegion.height) && ((this.streamY - this.sourceRegion.y) % this.sourceYSubsampling == 0));
/*     */   }
/*     */ 
/*     */   private void outputPixels(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 310 */     if ((this.interlacePass < this.sourceMinProgressivePass) || (this.interlacePass > this.sourceMaxProgressivePass))
/*     */     {
/* 312 */       return;
/*     */     }
/*     */ 
/* 315 */     for (int i = 0; i < paramInt; i++) {
/* 316 */       if (this.streamX >= this.sourceRegion.x) {
/* 317 */         this.rowBuf[(this.streamX - this.sourceRegion.x)] = paramArrayOfByte[i];
/*     */       }
/*     */ 
/* 321 */       this.streamX += 1;
/* 322 */       if (this.streamX == this.width) {
/* 323 */         this.rowsDone += 1;
/* 324 */         updateImageProgress(100.0F * this.rowsDone / this.height);
/*     */ 
/* 326 */         if (this.decodeThisRow) {
/* 327 */           outputRow();
/*     */         }
/*     */ 
/* 330 */         this.streamX = 0;
/* 331 */         if (this.imageMetadata.interlaceFlag) {
/* 332 */           this.streamY += interlaceIncrement[this.interlacePass];
/* 333 */           if (this.streamY >= this.height) {
/* 334 */             this.interlacePass += 1;
/* 335 */             if (this.interlacePass > this.sourceMaxProgressivePass) {
/* 336 */               return;
/*     */             }
/* 338 */             this.streamY = interlaceOffset[this.interlacePass];
/* 339 */             startPass();
/*     */           }
/*     */         } else {
/* 342 */           this.streamY += 1;
/*     */         }
/*     */ 
/* 347 */         this.destY = (this.destinationRegion.y + (this.streamY - this.sourceRegion.y) / this.sourceYSubsampling);
/*     */ 
/* 349 */         computeDecodeThisRow();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readHeader() throws IOException
/*     */   {
/* 356 */     if (this.gotHeader) {
/* 357 */       return;
/*     */     }
/* 359 */     if (this.stream == null) {
/* 360 */       throw new IllegalStateException("Input not set!");
/*     */     }
/*     */ 
/* 364 */     this.streamMetadata = new GIFStreamMetadata();
/*     */     try
/*     */     {
/* 367 */       byte[] arrayOfByte = new byte[6];
/* 368 */       this.stream.read(arrayOfByte);
/*     */ 
/* 370 */       localObject = new StringBuffer(3);
/* 371 */       ((StringBuffer)localObject).append((char)arrayOfByte[3]);
/* 372 */       ((StringBuffer)localObject).append((char)arrayOfByte[4]);
/* 373 */       ((StringBuffer)localObject).append((char)arrayOfByte[5]);
/* 374 */       this.streamMetadata.version = ((StringBuffer)localObject).toString();
/*     */ 
/* 376 */       this.streamMetadata.logicalScreenWidth = readUnsignedShort(this.stream);
/* 377 */       this.streamMetadata.logicalScreenHeight = readUnsignedShort(this.stream);
/*     */ 
/* 379 */       int i = this.stream.read();
/* 380 */       int j = (i & 0x80) != 0 ? 1 : 0;
/* 381 */       this.streamMetadata.colorResolution = ((i >> 4 & 0x7) + 1);
/* 382 */       this.streamMetadata.sortFlag = ((i & 0x8) != 0);
/* 383 */       int k = 1 << (i & 0x7) + 1;
/*     */ 
/* 385 */       this.streamMetadata.backgroundColorIndex = this.stream.read();
/* 386 */       this.streamMetadata.pixelAspectRatio = this.stream.read();
/*     */ 
/* 388 */       if (j != 0) {
/* 389 */         this.streamMetadata.globalColorTable = new byte[3 * k];
/* 390 */         ImageTools.readFully(this.stream, this.streamMetadata.globalColorTable);
/*     */       } else {
/* 392 */         this.streamMetadata.globalColorTable = null;
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 395 */       Object localObject = new IOException("I/O error reading header!");
/* 396 */       ((IOException)localObject).initCause(localIOException);
/* 397 */       throw ((Throwable)localObject);
/*     */     }
/*     */ 
/* 400 */     this.gotHeader = true;
/*     */   }
/*     */ 
/*     */   private byte[] concatenateBlocks() throws IOException
/*     */   {
/* 405 */     Object localObject = new byte[0];
/*     */     while (true) {
/* 407 */       int i = this.stream.read();
/* 408 */       if (i == 0) {
/*     */         break;
/*     */       }
/* 411 */       byte[] arrayOfByte = new byte[localObject.length + i];
/* 412 */       System.arraycopy(localObject, 0, arrayOfByte, 0, localObject.length);
/* 413 */       ImageTools.readFully(this.stream, arrayOfByte, localObject.length, i);
/* 414 */       localObject = arrayOfByte;
/*     */     }
/*     */ 
/* 417 */     return localObject;
/*     */   }
/*     */ 
/*     */   private boolean readMetadata()
/*     */     throws IOException
/*     */   {
/* 423 */     if (this.stream == null) {
/* 424 */       throw new IllegalStateException("Input not set!");
/*     */     }
/*     */ 
/* 427 */     int i = 0;
/*     */     try
/*     */     {
/* 431 */       this.imageMetadata = new GIFImageMetadata();
/* 432 */       while (i == 0) {
/* 433 */         int j = this.stream.read();
/*     */         int k;
/*     */         int m;
/*     */         int i1;
/* 434 */         if (j == 44) {
/* 435 */           this.imageMetadata.imageLeftPosition = readUnsignedShort(this.stream);
/*     */ 
/* 437 */           this.imageMetadata.imageTopPosition = readUnsignedShort(this.stream);
/*     */ 
/* 439 */           this.imageMetadata.imageWidth = readUnsignedShort(this.stream);
/* 440 */           this.imageMetadata.imageHeight = readUnsignedShort(this.stream);
/*     */ 
/* 442 */           k = this.stream.read();
/* 443 */           m = (k & 0x80) != 0 ? 1 : 0;
/*     */ 
/* 445 */           this.imageMetadata.interlaceFlag = ((k & 0x40) != 0);
/* 446 */           this.imageMetadata.sortFlag = ((k & 0x20) != 0);
/* 447 */           i1 = 1 << (k & 0x7) + 1;
/*     */ 
/* 449 */           if (m != 0)
/*     */           {
/* 451 */             this.imageMetadata.localColorTable = new byte[3 * i1];
/*     */ 
/* 453 */             ImageTools.readFully(this.stream, this.imageMetadata.localColorTable);
/*     */           } else {
/* 455 */             this.imageMetadata.localColorTable = null;
/*     */           }
/*     */ 
/* 459 */           return true;
/* 460 */         }if (j == 33) {
/* 461 */           k = this.stream.read();
/*     */ 
/* 463 */           if (k == 249) {
/* 464 */             m = this.stream.read();
/* 465 */             i1 = this.stream.read();
/* 466 */             this.imageMetadata.disposalMethod = (i1 >> 2 & 0x3);
/*     */ 
/* 468 */             this.imageMetadata.userInputFlag = ((i1 & 0x2) != 0);
/*     */ 
/* 470 */             this.imageMetadata.transparentColorFlag = ((i1 & 0x1) != 0);
/*     */ 
/* 473 */             this.imageMetadata.delayTime = readUnsignedShort(this.stream);
/* 474 */             this.imageMetadata.transparentColorIndex = this.stream.read();
/*     */ 
/* 476 */             int i2 = this.stream.read();
/* 477 */           } else if (k == 1) {
/* 478 */             m = this.stream.read();
/* 479 */             this.imageMetadata.hasPlainTextExtension = true;
/* 480 */             this.imageMetadata.textGridLeft = readUnsignedShort(this.stream);
/*     */ 
/* 482 */             this.imageMetadata.textGridTop = readUnsignedShort(this.stream);
/*     */ 
/* 484 */             this.imageMetadata.textGridWidth = readUnsignedShort(this.stream);
/*     */ 
/* 486 */             this.imageMetadata.textGridHeight = readUnsignedShort(this.stream);
/*     */ 
/* 488 */             this.imageMetadata.characterCellWidth = this.stream.read();
/*     */ 
/* 490 */             this.imageMetadata.characterCellHeight = this.stream.read();
/*     */ 
/* 492 */             this.imageMetadata.textForegroundColor = this.stream.read();
/*     */ 
/* 494 */             this.imageMetadata.textBackgroundColor = this.stream.read();
/*     */ 
/* 496 */             this.imageMetadata.text = concatenateBlocks();
/* 497 */           } else if (k == 254) {
/* 498 */             byte[] arrayOfByte1 = concatenateBlocks();
/* 499 */             if (this.imageMetadata.comments == null) {
/* 500 */               this.imageMetadata.comments = new ArrayList();
/*     */             }
/* 502 */             this.imageMetadata.comments.add(arrayOfByte1);
/*     */           }
/*     */           else
/*     */           {
/*     */             int n;
/* 503 */             if (k == 255) {
/* 504 */               n = this.stream.read();
/* 505 */               byte[] arrayOfByte2 = new byte[8];
/* 506 */               byte[] arrayOfByte3 = new byte[3];
/*     */ 
/* 509 */               byte[] arrayOfByte4 = new byte[n];
/* 510 */               this.stream.read(arrayOfByte4);
/*     */ 
/* 512 */               int i3 = copyData(arrayOfByte4, 0, arrayOfByte2);
/* 513 */               i3 = copyData(arrayOfByte4, i3, arrayOfByte3);
/*     */ 
/* 515 */               Object localObject = concatenateBlocks();
/*     */ 
/* 517 */               if (i3 < n) {
/* 518 */                 int i4 = n - i3;
/* 519 */                 byte[] arrayOfByte5 = new byte[i4 + localObject.length];
/*     */ 
/* 522 */                 System.arraycopy(arrayOfByte4, i3, arrayOfByte5, 0, i4);
/* 523 */                 System.arraycopy(localObject, 0, arrayOfByte5, i4, localObject.length);
/*     */ 
/* 526 */                 localObject = arrayOfByte5;
/*     */               }
/*     */ 
/* 530 */               if (this.imageMetadata.applicationIDs == null) {
/* 531 */                 this.imageMetadata.applicationIDs = new ArrayList();
/* 532 */                 this.imageMetadata.authenticationCodes = new ArrayList();
/*     */ 
/* 534 */                 this.imageMetadata.applicationData = new ArrayList();
/*     */               }
/* 536 */               this.imageMetadata.applicationIDs.add(arrayOfByte2);
/* 537 */               this.imageMetadata.authenticationCodes.add(arrayOfByte3);
/* 538 */               this.imageMetadata.applicationData.add(localObject);
/*     */             }
/*     */             else {
/* 541 */               n = 0;
/*     */               do {
/* 543 */                 n = this.stream.read();
/* 544 */                 this.stream.skip(n);
/* 545 */               }while (n > 0);
/*     */             }
/*     */           } } else if (j == 59) {
/* 548 */           i = 1;
/*     */         } else {
/* 550 */           throw new IOException("Unexpected block type " + j + "!");
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (EOFException localEOFException) {
/* 555 */       i = 1;
/*     */     } catch (IOException localIOException1) {
/* 557 */       IOException localIOException2 = new IOException("I/O error reading image metadata!");
/* 558 */       localIOException2.initCause(localIOException1);
/* 559 */       throw localIOException2;
/*     */     }
/*     */ 
/* 562 */     return i == 0;
/*     */   }
/*     */ 
/*     */   private int copyData(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2) {
/* 566 */     int i = paramArrayOfByte2.length;
/* 567 */     int j = paramArrayOfByte1.length - paramInt;
/* 568 */     if (i > j) {
/* 569 */       i = j;
/*     */     }
/* 571 */     System.arraycopy(paramArrayOfByte1, paramInt, paramArrayOfByte2, 0, i);
/* 572 */     return paramInt + i;
/*     */   }
/*     */ 
/*     */   private void startPass() {
/* 576 */     int i = 0;
/* 577 */     int j = 1;
/* 578 */     if (this.imageMetadata.interlaceFlag) {
/* 579 */       i = interlaceOffset[this.interlacePass];
/* 580 */       j = interlaceIncrement[this.interlacePass];
/*     */     }
/*     */ 
/* 583 */     int[] arrayOfInt = ImageTools.computeUpdatedPixels(this.sourceRegion, this.destinationOffset, this.destinationRegion.x, this.destinationRegion.y, this.destinationRegion.x + this.destinationRegion.width - 1, this.destinationRegion.y + this.destinationRegion.height - 1, this.sourceXSubsampling, this.sourceYSubsampling, 0, i, this.destinationRegion.width, (this.destinationRegion.height + j - 1) / j, 1, j);
/*     */ 
/* 601 */     this.updateMinY = arrayOfInt[1];
/* 602 */     this.updateYStep = arrayOfInt[5];
/*     */   }
/*     */ 
/*     */   public ImageFrame read(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
/*     */     throws IOException
/*     */   {
/* 608 */     if (this.stream == null) {
/* 609 */       throw new IllegalStateException("Input not set!");
/*     */     }
/*     */ 
/* 613 */     readHeader();
/*     */ 
/* 616 */     if (!readMetadata()) {
/* 617 */       return null;
/*     */     }
/*     */ 
/* 622 */     this.width = this.imageMetadata.imageWidth;
/* 623 */     this.height = this.imageMetadata.imageHeight;
/* 624 */     this.sourceType = (this.imageMetadata.transparentColorFlag ? ImageStorage.ImageType.PALETTE_TRANS : ImageStorage.ImageType.PALETTE);
/*     */ 
/* 627 */     int[] arrayOfInt1 = ImageTools.computeDimensions(this.width, this.height, paramInt2, paramInt3, paramBoolean1);
/* 628 */     this.destWidth = (paramInt2 = arrayOfInt1[0]);
/* 629 */     this.destHeight = (paramInt3 = arrayOfInt1[1]);
/*     */ 
/* 632 */     this.isScaling = ((paramInt2 != this.width) || (paramInt3 != this.height));
/*     */ 
/* 635 */     Integer localInteger1 = null;
/* 636 */     if (this.streamMetadata.globalColorTable != null) {
/* 637 */       localInteger1 = Integer.valueOf(this.streamMetadata.backgroundColorIndex);
/*     */     }
/* 639 */     Integer localInteger2 = null;
/* 640 */     if (this.imageMetadata.transparentColorFlag) {
/* 641 */       localInteger2 = Integer.valueOf(this.imageMetadata.transparentColorIndex);
/*     */     }
/*     */ 
/* 644 */     ImageMetadata localImageMetadata1 = new ImageMetadata(null, Boolean.valueOf(true), localInteger1, null, localInteger2, Integer.valueOf(10 * this.imageMetadata.delayTime), Integer.valueOf(this.imageMetadata.imageWidth), Integer.valueOf(this.imageMetadata.imageHeight), Integer.valueOf(this.imageMetadata.imageLeftPosition), Integer.valueOf(this.imageMetadata.imageTopPosition), Integer.valueOf(this.imageMetadata.disposalMethod));
/*     */ 
/* 650 */     updateImageMetadata(localImageMetadata1);
/*     */ 
/* 655 */     ImageStorage.ImageType localImageType1 = ImageTools.getConvertedType(this.sourceType);
/* 656 */     int i = ImageStorage.getNumBands(localImageType1);
/*     */ 
/* 662 */     byte[][] arrayOfByte = (byte[][])null;
/* 663 */     ImageMetadata localImageMetadata2 = localImageMetadata1;
/*     */     ImageStorage.ImageType localImageType2;
/*     */     int k;
/*     */     int m;
/*     */     int j;
/*     */     ByteBuffer localByteBuffer1;
/* 664 */     if (this.isScaling) {
/* 665 */       this.scaler = ScalerFactory.createScaler(this.width, this.height, i, paramInt2, paramInt3, paramBoolean2);
/*     */ 
/* 667 */       if (this.imageMetadata.interlaceFlag) {
/* 668 */         localImageType2 = this.sourceType;
/* 669 */         if (!this.firstImageflag) {
/* 670 */           k = this.fullImageWidth;
/* 671 */           m = this.fullImageHeight;
/* 672 */           j = this.fullBytesPerRow;
/*     */         } else {
/* 674 */           k = this.width;
/* 675 */           m = this.height;
/* 676 */           j = 4 * ((this.width + 3) / 4);
/*     */         }
/*     */ 
/* 679 */         arrayOfByte = getPalette();
/* 680 */         localImageMetadata2 = localImageMetadata1;
/* 681 */         localByteBuffer1 = ByteBuffer.wrap(new byte[this.height * j]);
/*     */       } else {
/* 683 */         localImageType2 = localImageType1;
/* 684 */         k = paramInt2;
/* 685 */         m = paramInt3;
/* 686 */         j = paramInt2 * i;
/* 687 */         localByteBuffer1 = this.scaler.getDestination();
/*     */       }
/*     */     } else {
/* 690 */       localImageType2 = localImageType1;
/* 691 */       if (!this.firstImageflag) {
/* 692 */         k = this.fullImageWidth;
/* 693 */         m = this.fullImageHeight;
/* 694 */         j = this.fullBytesPerRow;
/*     */       } else {
/* 696 */         k = paramInt2;
/* 697 */         m = paramInt3;
/* 698 */         j = paramInt2 * i;
/*     */       }
/* 700 */       localByteBuffer1 = ByteBuffer.wrap(new byte[m * j]);
/*     */     }
/*     */ 
/* 706 */     if (this.firstImageflag) {
/* 707 */       this.theImage = new ImageFrame(localImageType2, localByteBuffer1, k, m, j, arrayOfByte, localImageMetadata2);
/* 708 */       this.fullImageHeight = m;
/* 709 */       this.fullImageWidth = k;
/* 710 */       this.fullBytesPerRow = j;
/* 711 */       this.sourceRegion = new Rectangle(this.width, this.height);
/* 712 */       this.destinationOffset = new Point2D();
/* 713 */       this.destinationRegion = new Rectangle(this.width, this.height);
/* 714 */       this.firstImageflag = false;
/*     */     }
/*     */     else {
/* 717 */       System.arraycopy((byte[])this.theImage.getImageData().array(), 0, localByteBuffer1.array(), 0, localByteBuffer1.array().length);
/* 718 */       this.theImage = new ImageFrame(localImageType2, localByteBuffer1, k, m, j, arrayOfByte, localImageMetadata2);
/* 719 */       this.sourceRegion = new Rectangle(this.width, this.height);
/* 720 */       this.destinationOffset = new Point2D(localImageMetadata2.imageLeftPosition.intValue(), localImageMetadata2.imageTopPosition.intValue());
/* 721 */       this.destinationRegion = new Rectangle(localImageMetadata2.imageLeftPosition.intValue(), localImageMetadata2.imageTopPosition.intValue(), this.width, this.height);
/* 722 */       this.skipTransparency = true;
/*     */     }
/* 724 */     this.sourceXSubsampling = 1;
/* 725 */     this.sourceYSubsampling = 1;
/* 726 */     this.sourceMinProgressivePass = 0;
/* 727 */     this.sourceMaxProgressivePass = 3;
/*     */ 
/* 729 */     this.theTile = ((ByteBuffer)this.theImage.getImageData()).array();
/* 730 */     this.streamX = 0;
/* 731 */     this.streamY = 0;
/* 732 */     this.rowsDone = 0;
/* 733 */     this.interlacePass = 0;
/*     */ 
/* 735 */     this.destY = (this.destinationRegion.y + (this.streamY - this.sourceRegion.y) / this.sourceYSubsampling);
/*     */ 
/* 737 */     computeDecodeThisRow();
/*     */ 
/* 740 */     updateImageProgress(0.0F);
/* 741 */     startPass();
/*     */ 
/* 743 */     this.rowBuf = new byte[this.width];
/* 744 */     this.destBuf = new byte[this.width * i];
/*     */     try
/*     */     {
/* 748 */       this.initCodeSize = this.stream.read();
/*     */ 
/* 751 */       this.blockLength = this.stream.read();
/* 752 */       int n = this.blockLength;
/* 753 */       int i1 = 0;
/*     */       ByteBuffer localByteBuffer2;
/* 754 */       while (n > 0) {
/* 755 */         localByteBuffer2 = ImageTools.readFully(this.stream, this.block, i1, n);
/* 756 */         n -= localByteBuffer2;
/* 757 */         i1 += localByteBuffer2;
/*     */       }
/*     */ 
/* 760 */       this.bitPos = 0;
/* 761 */       this.nextByte = 0;
/* 762 */       this.lastBlockFound = false;
/* 763 */       this.interlacePass = 0;
/*     */ 
/* 766 */       initNext32Bits();
/*     */ 
/* 768 */       this.clearCode = (1 << this.initCodeSize);
/* 769 */       this.eofCode = (this.clearCode + 1);
/*     */ 
/* 771 */       ByteBuffer localByteBuffer3 = 0;
/*     */ 
/* 773 */       int[] arrayOfInt2 = new int[4096];
/* 774 */       byte[] arrayOfByte1 = new byte[4096];
/* 775 */       byte[] arrayOfByte2 = new byte[4096];
/* 776 */       int[] arrayOfInt3 = new int[4096];
/* 777 */       byte[] arrayOfByte3 = new byte[4096];
/*     */ 
/* 779 */       initializeStringTable(arrayOfInt2, arrayOfByte1, arrayOfByte2, arrayOfInt3);
/* 780 */       byte[] arrayOfByte4 = (1 << this.initCodeSize) + 2;
/* 781 */       int i2 = this.initCodeSize + 1;
/* 782 */       int i3 = (1 << i2) - 1;
/*     */       while (true)
/*     */       {
/* 785 */         localByteBuffer2 = getCode(i2, i3);
/*     */ 
/* 787 */         if (localByteBuffer2 == this.clearCode) {
/* 788 */           initializeStringTable(arrayOfInt2, arrayOfByte1, arrayOfByte2, arrayOfInt3);
/* 789 */           arrayOfByte4 = (1 << this.initCodeSize) + 2;
/* 790 */           i2 = this.initCodeSize + 1;
/* 791 */           i3 = (1 << i2) - 1;
/*     */ 
/* 793 */           localByteBuffer2 = getCode(i2, i3);
/* 794 */           if (localByteBuffer2 == this.eofCode)
/*     */           {
/* 796 */             updateImageProgress(100.0F);
/* 797 */             return this.theImage;
/*     */           }
/*     */         } else { if (localByteBuffer2 == this.eofCode)
/*     */           {
/* 801 */             if ((this.imageMetadata.interlaceFlag) && (this.isScaling))
/*     */             {
/* 803 */               localByteBuffer4 = (ByteBuffer)this.theImage.getImageData();
/* 804 */               arrayOfByte5 = localByteBuffer4.array();
/* 805 */               i6 = this.theImage.getStride();
/* 806 */               int i7 = 0;
/* 807 */               byte[] arrayOfByte6 = new byte[this.width * i];
/* 808 */               for (int i8 = 0; i8 < this.height; i8++) {
/* 809 */                 ImageTools.convert(this.width, 1, this.sourceType, arrayOfByte5, i7, i6, arrayOfByte6, 0, 0, arrayOfByte, localInteger2.intValue(), false);
/*     */ 
/* 812 */                 if (this.scaler.putSourceScanline(arrayOfByte6, 0)) {
/*     */                   break;
/*     */                 }
/* 815 */                 i7 += i6;
/*     */               }
/* 817 */               this.theImage = new ImageFrame(localImageType1, this.scaler.getDestination(), paramInt2, paramInt3, paramInt2 * i, (byte[][])null, localImageMetadata2);
/*     */             }
/*     */ 
/* 822 */             updateImageProgress(100.0F);
/* 823 */             return this.theImage;
/*     */           }
/*     */ 
/* 826 */           if (localByteBuffer2 < arrayOfByte4) {
/* 827 */             localByteBuffer4 = localByteBuffer2;
/*     */           } else {
/* 829 */             localByteBuffer4 = localByteBuffer3;
/* 830 */             if (localByteBuffer2 != arrayOfByte4)
/*     */             {
/* 833 */               emitWarning("Out-of-sequence code!");
/*     */             }
/*     */           }
/*     */ 
/* 837 */           byte[] arrayOfByte5 = arrayOfByte4;
/* 838 */           i6 = localByteBuffer3;
/*     */ 
/* 840 */           arrayOfInt2[arrayOfByte5] = i6;
/* 841 */           arrayOfByte1[arrayOfByte5] = arrayOfByte2[localByteBuffer4];
/* 842 */           arrayOfByte2[arrayOfByte5] = arrayOfByte2[i6];
/* 843 */           arrayOfInt3[i6] += 1;
/*     */ 
/* 845 */           arrayOfByte4++;
/* 846 */           if ((arrayOfByte4 == 1 << i2) && (arrayOfByte4 < 4096))
/*     */           {
/* 848 */             i2++;
/* 849 */             i3 = (1 << i2) - 1;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 854 */         ByteBuffer localByteBuffer4 = localByteBuffer2;
/* 855 */         int i5 = arrayOfInt3[localByteBuffer4];
/* 856 */         for (int i6 = i5 - 1; i6 >= 0; i6--) {
/* 857 */           arrayOfByte3[i6] = arrayOfByte1[localByteBuffer4];
/* 858 */           int i4 = arrayOfInt2[localByteBuffer4];
/*     */         }
/*     */ 
/* 861 */         outputPixels(arrayOfByte3, i5);
/* 862 */         localByteBuffer3 = localByteBuffer2;
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException localIOException1)
/*     */     {
/* 868 */       IOException localIOException2 = new IOException("Error reading GIF image data");
/* 869 */       localIOException2.initCause(localIOException1);
/* 870 */       throw localIOException2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 880 */     resetStreamSettings();
/*     */   }
/*     */ 
/*     */   private void resetStreamSettings()
/*     */   {
/* 887 */     this.gotHeader = false;
/* 888 */     this.streamMetadata = null;
/* 889 */     this.currIndex = -1;
/* 890 */     this.imageMetadata = null;
/* 891 */     this.imageStartPosition = new ArrayList();
/* 892 */     this.numImages = -1;
/*     */ 
/* 895 */     this.blockLength = 0;
/* 896 */     this.bitPos = 0;
/* 897 */     this.nextByte = 0;
/*     */ 
/* 899 */     this.next32Bits = 0;
/* 900 */     this.lastBlockFound = false;
/*     */ 
/* 902 */     this.theImage = null;
/* 903 */     this.theTile = null;
/* 904 */     this.width = -1;
/* 905 */     this.height = -1;
/* 906 */     this.streamX = -1;
/* 907 */     this.streamY = -1;
/* 908 */     this.rowsDone = 0;
/* 909 */     this.interlacePass = 0;
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ImageFrame load(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
/* 917 */     return read(paramInt1, paramInt2, paramInt3, paramBoolean1, paramBoolean2);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.gif.GIFImageLoader
 * JD-Core Version:    0.6.2
 */