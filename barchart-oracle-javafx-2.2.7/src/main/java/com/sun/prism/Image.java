/*     */ package com.sun.prism;
/*     */ 
/*     */ import com.sun.javafx.iio.ImageFrame;
/*     */ import com.sun.javafx.iio.ImageStorage.ImageType;
/*     */ import com.sun.javafx.image.BytePixelGetter;
/*     */ import com.sun.javafx.image.BytePixelSetter;
/*     */ import com.sun.javafx.image.ByteToBytePixelConverter;
/*     */ import com.sun.javafx.image.ByteToIntPixelConverter;
/*     */ import com.sun.javafx.image.IntPixelGetter;
/*     */ import com.sun.javafx.image.IntPixelSetter;
/*     */ import com.sun.javafx.image.IntToBytePixelConverter;
/*     */ import com.sun.javafx.image.IntToIntPixelConverter;
/*     */ import com.sun.javafx.image.PixelConverter;
/*     */ import com.sun.javafx.image.PixelGetter;
/*     */ import com.sun.javafx.image.PixelSetter;
/*     */ import com.sun.javafx.image.PixelUtils;
/*     */ import com.sun.javafx.image.impl.ByteBgra;
/*     */ import com.sun.javafx.image.impl.ByteBgraPre;
/*     */ import com.sun.javafx.image.impl.ByteGray;
/*     */ import com.sun.javafx.image.impl.ByteGrayAlpha;
/*     */ import com.sun.javafx.image.impl.ByteGrayAlphaPre;
/*     */ import com.sun.javafx.image.impl.ByteRgb;
/*     */ import com.sun.javafx.image.impl.ByteRgba;
/*     */ import com.sun.javafx.tk.PlatformImage;
/*     */ import com.sun.prism.impl.BufferUtil;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import javafx.scene.image.PixelReader;
/*     */ import javafx.scene.image.WritablePixelFormat;
/*     */ 
/*     */ public class Image
/*     */   implements PlatformImage
/*     */ {
/*  40 */   static final WritablePixelFormat FX_ByteBgraPre_FORMAT = javafx.scene.image.PixelFormat.getByteBgraPreInstance();
/*     */ 
/*  42 */   static final WritablePixelFormat FX_IntArgbPre_FORMAT = javafx.scene.image.PixelFormat.getIntArgbPreInstance();
/*     */ 
/*  44 */   static final javafx.scene.image.PixelFormat FX_ByteRgb_FORMAT = javafx.scene.image.PixelFormat.getByteRgbInstance();
/*     */   private final Buffer pixelBuffer;
/*     */   private final int minX;
/*     */   private final int minY;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final int scanlineStride;
/*     */   private final PixelFormat pixelFormat;
/*     */   int serial;
/*     */   private Accessor pixelaccessor;
/*     */ 
/*     */   public static Image fromIntArgbPreData(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*     */   {
/*  57 */     return new Image(PixelFormat.INT_ARGB_PRE, paramArrayOfInt, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromIntArgbPreData(IntBuffer paramIntBuffer, int paramInt1, int paramInt2) {
/*  61 */     return new Image(PixelFormat.INT_ARGB_PRE, paramIntBuffer, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromIntArgbPreData(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, int paramInt3) {
/*  65 */     return new Image(PixelFormat.INT_ARGB_PRE, paramIntBuffer, paramInt1, paramInt2, 0, 0, paramInt3);
/*     */   }
/*     */ 
/*     */   public static Image fromByteBgraPreData(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/*  69 */     return new Image(PixelFormat.BYTE_BGRA_PRE, paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromByteBgraPreData(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2) {
/*  73 */     return new Image(PixelFormat.BYTE_BGRA_PRE, paramByteBuffer, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromByteBgraPreData(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3) {
/*  77 */     return new Image(PixelFormat.BYTE_BGRA_PRE, paramByteBuffer, paramInt1, paramInt2, 0, 0, paramInt3);
/*     */   }
/*     */ 
/*     */   public static Image fromByteRgbData(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/*  81 */     return new Image(PixelFormat.BYTE_RGB, paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromByteRgbData(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2) {
/*  85 */     return new Image(PixelFormat.BYTE_RGB, paramByteBuffer, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromByteRgbData(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3) {
/*  89 */     return new Image(PixelFormat.BYTE_RGB, paramByteBuffer, paramInt1, paramInt2, 0, 0, paramInt3);
/*     */   }
/*     */ 
/*     */   public static Image fromByteGrayData(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/*  93 */     return new Image(PixelFormat.BYTE_GRAY, paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromByteGrayData(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2) {
/*  97 */     return new Image(PixelFormat.BYTE_GRAY, paramByteBuffer, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromByteGrayData(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3) {
/* 101 */     return new Image(PixelFormat.BYTE_GRAY, paramByteBuffer, paramInt1, paramInt2, 0, 0, paramInt3);
/*     */   }
/*     */ 
/*     */   public static Image fromByteAlphaData(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/* 105 */     return new Image(PixelFormat.BYTE_ALPHA, paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromByteAlphaData(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2) {
/* 109 */     return new Image(PixelFormat.BYTE_ALPHA, paramByteBuffer, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromByteAlphaData(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3) {
/* 113 */     return new Image(PixelFormat.BYTE_ALPHA, paramByteBuffer, paramInt1, paramInt2, 0, 0, paramInt3);
/*     */   }
/*     */ 
/*     */   public static Image fromByteApple422Data(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/* 117 */     return new Image(PixelFormat.BYTE_APPLE_422, paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromByteApple422Data(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2) {
/* 121 */     return new Image(PixelFormat.BYTE_APPLE_422, paramByteBuffer, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image fromByteApple422Data(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3) {
/* 125 */     return new Image(PixelFormat.BYTE_APPLE_422, paramByteBuffer, paramInt1, paramInt2, 0, 0, paramInt3);
/*     */   }
/*     */ 
/*     */   public static Image fromFloatMapData(FloatBuffer paramFloatBuffer, int paramInt1, int paramInt2) {
/* 129 */     return new Image(PixelFormat.FLOAT_XYZW, paramFloatBuffer, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Image convertImageFrame(ImageFrame paramImageFrame)
/*     */   {
/* 143 */     ByteBuffer localByteBuffer = (ByteBuffer)paramImageFrame.getImageData();
/* 144 */     ImageStorage.ImageType localImageType = paramImageFrame.getImageType();
/* 145 */     int i = paramImageFrame.getWidth(); int j = paramImageFrame.getHeight();
/* 146 */     int k = paramImageFrame.getStride();
/*     */ 
/* 148 */     switch (1.$SwitchMap$com$sun$javafx$iio$ImageStorage$ImageType[localImageType.ordinal()]) {
/*     */     case 1:
/* 150 */       return fromByteGrayData(localByteBuffer, i, j, k);
/*     */     case 2:
/* 153 */       return fromByteRgbData(localByteBuffer, i, j, k);
/*     */     case 3:
/* 157 */       ByteBgra.ToByteBgraPreConverter.convert(localByteBuffer, 0, k, localByteBuffer, 0, k, i, j);
/*     */     case 4:
/* 162 */       ByteRgba.ToByteBgraConverter.convert(localByteBuffer, 0, k, localByteBuffer, 0, k, i, j);
/*     */ 
/* 165 */       return fromByteBgraPreData(localByteBuffer, i, j, k);
/*     */     case 5:
/* 168 */       ByteGrayAlpha.ToByteGrayAlphaPre.convert(localByteBuffer, 0, k, localByteBuffer, 0, k, i, j);
/*     */     case 6:
/* 173 */       if (k != i * 2) {
/* 174 */         throw new AssertionError("Bad stride for GRAY_ALPHA");
/*     */       }
/* 176 */       byte[] arrayOfByte = new byte[i * j * 4];
/* 177 */       ByteGrayAlphaPre.ToByteBgraPre.convert(localByteBuffer, 0, k, arrayOfByte, 0, i * 4, i, j);
/*     */ 
/* 180 */       return fromByteBgraPreData(arrayOfByte, i, j);
/*     */     }
/* 182 */     throw new RuntimeException("Unknown image type: " + localImageType);
/*     */   }
/*     */ 
/*     */   private Image(PixelFormat paramPixelFormat, int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*     */   {
/* 189 */     this(paramPixelFormat, IntBuffer.wrap(paramArrayOfInt), paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   private Image(PixelFormat paramPixelFormat, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 195 */     this(paramPixelFormat, ByteBuffer.wrap(paramArrayOfByte), paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   private Image(PixelFormat paramPixelFormat, Buffer paramBuffer, int paramInt1, int paramInt2)
/*     */   {
/* 201 */     this(paramPixelFormat, paramBuffer, paramInt1, paramInt2, 0, 0, 0);
/*     */   }
/*     */ 
/*     */   private Image(PixelFormat paramPixelFormat, Buffer paramBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/* 207 */     if (paramPixelFormat == PixelFormat.MULTI_YCbCr_420) {
/* 208 */       throw new IllegalArgumentException("Format not supported " + paramPixelFormat.name());
/*     */     }
/* 210 */     if (paramInt5 == 0) {
/* 211 */       paramInt5 = paramInt1 * paramPixelFormat.getBytesPerPixelUnit();
/*     */     }
/*     */ 
/* 214 */     if (paramBuffer == null) {
/* 215 */       throw new IllegalArgumentException("Pixel buffer must be non-null");
/*     */     }
/* 217 */     if ((paramInt1 <= 0) || (paramInt2 <= 0)) {
/* 218 */       throw new IllegalArgumentException("Image dimensions must be > 0");
/*     */     }
/* 220 */     if ((paramInt3 < 0) || (paramInt4 < 0)) {
/* 221 */       throw new IllegalArgumentException("Image minX and minY must be >= 0");
/*     */     }
/* 223 */     if ((paramInt3 + paramInt1) * paramPixelFormat.getBytesPerPixelUnit() > paramInt5) {
/* 224 */       throw new IllegalArgumentException("Image scanlineStride is too small");
/*     */     }
/* 226 */     if (paramInt5 % paramPixelFormat.getBytesPerPixelUnit() != 0) {
/* 227 */       throw new IllegalArgumentException("Image scanlineStride must be a multiple of the pixel stride");
/*     */     }
/*     */ 
/* 230 */     this.pixelFormat = paramPixelFormat;
/* 231 */     this.pixelBuffer = paramBuffer;
/* 232 */     this.width = paramInt1;
/* 233 */     this.height = paramInt2;
/* 234 */     this.minX = paramInt3;
/* 235 */     this.minY = paramInt4;
/* 236 */     this.scanlineStride = paramInt5;
/*     */   }
/*     */ 
/*     */   public PixelFormat getPixelFormat() {
/* 240 */     return this.pixelFormat;
/*     */   }
/*     */ 
/*     */   public PixelFormat.DataType getDataType() {
/* 244 */     return this.pixelFormat.getDataType();
/*     */   }
/*     */ 
/*     */   public int getBytesPerPixelUnit() {
/* 248 */     return this.pixelFormat.getBytesPerPixelUnit();
/*     */   }
/*     */ 
/*     */   public Buffer getPixelBuffer() {
/* 252 */     return this.pixelBuffer;
/*     */   }
/*     */ 
/*     */   public int getMinX() {
/* 256 */     return this.minX;
/*     */   }
/*     */ 
/*     */   public int getMinY() {
/* 260 */     return this.minY;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/* 264 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 268 */     return this.height;
/*     */   }
/*     */ 
/*     */   public int getScanlineStride() {
/* 272 */     return this.scanlineStride;
/*     */   }
/*     */ 
/*     */   public int getRowLength()
/*     */   {
/* 278 */     return this.scanlineStride / this.pixelFormat.getBytesPerPixelUnit();
/*     */   }
/*     */ 
/*     */   public boolean isTightlyPacked() {
/* 282 */     return (this.minX == 0) && (this.minY == 0) && (this.width == getRowLength());
/*     */   }
/*     */ 
/*     */   public Image createSubImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 300 */     if ((paramInt3 <= 0) || (paramInt4 <= 0)) {
/* 301 */       throw new IllegalArgumentException("Subimage dimensions must be > 0");
/*     */     }
/* 303 */     if ((paramInt1 < 0) || (paramInt2 < 0)) {
/* 304 */       throw new IllegalArgumentException("Subimage minX and minY must be >= 0");
/*     */     }
/* 306 */     if (paramInt1 + paramInt3 > this.width) {
/* 307 */       throw new IllegalArgumentException("Subimage minX+width must be <= width of parent image");
/*     */     }
/*     */ 
/* 310 */     if (paramInt2 + paramInt4 > this.height) {
/* 311 */       throw new IllegalArgumentException("Subimage minY+height must be <= height of parent image");
/*     */     }
/*     */ 
/* 314 */     return new Image(this.pixelFormat, this.pixelBuffer, paramInt3, paramInt4, this.minX + paramInt1, this.minY + paramInt2, this.scanlineStride);
/*     */   }
/*     */ 
/*     */   public Image createPackedCopy()
/*     */   {
/* 329 */     int i = this.width * this.pixelFormat.getBytesPerPixelUnit();
/* 330 */     Buffer localBuffer = createPackedBuffer(this.pixelBuffer, this.pixelFormat, this.minX, this.minY, this.width, this.height, this.scanlineStride);
/*     */ 
/* 333 */     return new Image(this.pixelFormat, localBuffer, this.width, this.height, 0, 0, i);
/*     */   }
/*     */ 
/*     */   public Image createPackedCopyIfNeeded()
/*     */   {
/* 346 */     int i = this.width * this.pixelFormat.getBytesPerPixelUnit();
/*     */ 
/* 348 */     if ((i == this.scanlineStride) && (this.minX == 0) && (this.minY == 0)) {
/* 349 */       return this;
/*     */     }
/* 351 */     return createPackedCopy();
/*     */   }
/*     */ 
/*     */   public static Buffer createPackedBuffer(Buffer paramBuffer, PixelFormat paramPixelFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/* 375 */     if (paramInt5 % paramPixelFormat.getBytesPerPixelUnit() != 0) {
/* 376 */       throw new IllegalArgumentException("Image scanlineStride must be a multiple of the pixel stride");
/*     */     }
/*     */ 
/* 379 */     if (paramPixelFormat == PixelFormat.MULTI_YCbCr_420) {
/* 380 */       throw new IllegalArgumentException("Format unsupported " + paramPixelFormat);
/*     */     }
/*     */ 
/* 383 */     int i = paramPixelFormat.getElemsPerPixelUnit();
/* 384 */     int j = paramInt5 / paramPixelFormat.getBytesPerPixelUnit();
/* 385 */     int k = j * i;
/* 386 */     int m = paramInt3 * i;
/* 387 */     int n = m * paramInt4;
/* 388 */     int i1 = paramInt1 * i + paramInt2 * k;
/* 389 */     int i2 = 0;
/*     */     Object localObject;
/* 392 */     switch (1.$SwitchMap$com$sun$prism$PixelFormat$DataType[paramPixelFormat.getDataType().ordinal()]) {
/*     */     case 1:
/* 394 */       ByteBuffer localByteBuffer1 = (ByteBuffer)paramBuffer;
/* 395 */       ByteBuffer localByteBuffer2 = BufferUtil.newByteBuffer(n);
/* 396 */       for (int i3 = 0; i3 < paramInt4; i3++) {
/* 397 */         localByteBuffer1.limit(i1 + m);
/* 398 */         localByteBuffer1.position(i1);
/* 399 */         localByteBuffer2.limit(i2 + m);
/* 400 */         localByteBuffer2.position(i2);
/* 401 */         localByteBuffer2.put(localByteBuffer1);
/* 402 */         i1 += k;
/* 403 */         i2 += m;
/*     */       }
/* 405 */       localObject = localByteBuffer2;
/* 406 */       break;
/*     */     case 2:
/* 408 */       IntBuffer localIntBuffer1 = (IntBuffer)paramBuffer;
/* 409 */       IntBuffer localIntBuffer2 = BufferUtil.newIntBuffer(n);
/* 410 */       for (int i4 = 0; i4 < paramInt4; i4++) {
/* 411 */         localIntBuffer1.limit(i1 + m);
/* 412 */         localIntBuffer1.position(i1);
/* 413 */         localIntBuffer2.limit(i2 + m);
/* 414 */         localIntBuffer2.position(i2);
/* 415 */         localIntBuffer2.put(localIntBuffer1);
/* 416 */         i1 += k;
/* 417 */         i2 += m;
/*     */       }
/* 419 */       localObject = localIntBuffer2;
/* 420 */       break;
/*     */     case 3:
/* 422 */       FloatBuffer localFloatBuffer1 = (FloatBuffer)paramBuffer;
/* 423 */       FloatBuffer localFloatBuffer2 = BufferUtil.newFloatBuffer(n);
/* 424 */       for (int i5 = 0; i5 < paramInt4; i5++) {
/* 425 */         localFloatBuffer1.limit(i1 + m);
/* 426 */         localFloatBuffer1.position(i1);
/* 427 */         localFloatBuffer2.limit(i2 + m);
/* 428 */         localFloatBuffer2.position(i2);
/* 429 */         localFloatBuffer2.put(localFloatBuffer1);
/* 430 */         i1 += k;
/* 431 */         i2 += m;
/*     */       }
/* 433 */       localObject = localFloatBuffer2;
/* 434 */       break;
/*     */     default:
/* 436 */       throw new InternalError("Unknown data type");
/*     */     }
/*     */ 
/* 439 */     paramBuffer.limit(paramBuffer.capacity());
/* 440 */     paramBuffer.rewind();
/* 441 */     ((Buffer)localObject).limit(((Buffer)localObject).capacity());
/* 442 */     ((Buffer)localObject).rewind();
/*     */ 
/* 444 */     return localObject;
/*     */   }
/*     */ 
/*     */   public Image iconify(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/*     */   {
/* 453 */     if (this.pixelFormat == PixelFormat.MULTI_YCbCr_420) {
/* 454 */       throw new IllegalArgumentException("Format not supported " + this.pixelFormat);
/*     */     }
/*     */ 
/* 459 */     int i = getBytesPerPixelUnit();
/*     */ 
/* 462 */     int j = paramInt1 * i;
/*     */     ByteToIntPixelConverter localByteToIntPixelConverter;
/* 465 */     if (i == 1)
/* 466 */       localByteToIntPixelConverter = ByteGray.ToIntArgbPreConverter;
/* 467 */     else if (this.pixelFormat == PixelFormat.BYTE_BGRA_PRE)
/* 468 */       localByteToIntPixelConverter = ByteBgraPre.ToIntArgbPreConverter;
/*     */     else {
/* 470 */       localByteToIntPixelConverter = ByteRgb.ToIntArgbPreConverter;
/*     */     }
/*     */ 
/* 474 */     int[] arrayOfInt = new int[paramInt1 * paramInt2];
/* 475 */     localByteToIntPixelConverter.convert(paramByteBuffer, 0, j, arrayOfInt, 0, paramInt1, paramInt1, paramInt2);
/*     */ 
/* 480 */     return new Image(PixelFormat.INT_ARGB_PRE, arrayOfInt, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 485 */     return super.toString() + " [format=" + this.pixelFormat + " width=" + this.width + " height=" + this.height + " scanlineStride=" + this.scanlineStride + " minX=" + this.minX + " minY=" + this.minY + " pixelBuffer=" + this.pixelBuffer + " bpp=" + getBytesPerPixelUnit() + "]";
/*     */   }
/*     */ 
/*     */   public int getSerial()
/*     */   {
/* 494 */     return this.serial;
/*     */   }
/*     */ 
/*     */   public Image promoteByteRgbToByteBgra() {
/* 498 */     ByteBuffer localByteBuffer1 = (ByteBuffer)this.pixelBuffer;
/* 499 */     ByteBuffer localByteBuffer2 = ByteBuffer.allocate(this.width * this.height * 4);
/* 500 */     int i = this.minY * this.scanlineStride + this.minX * 3;
/* 501 */     ByteRgb.ToByteBgraPreConverter.convert(localByteBuffer1, i, this.scanlineStride, localByteBuffer2, 0, this.width * 4, this.width, this.height);
/*     */ 
/* 504 */     return new Image(PixelFormat.BYTE_BGRA_PRE, localByteBuffer2, this.width, this.height, 0, 0, this.width * 4);
/*     */   }
/*     */ 
/*     */   private Accessor getPixelAccessor()
/*     */   {
/* 510 */     if (this.pixelaccessor == null) {
/* 511 */       switch (1.$SwitchMap$com$sun$prism$PixelFormat[getPixelFormat().ordinal()]) {
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       default:
/* 518 */         this.pixelaccessor = new UnsupportedAccess(null);
/* 519 */         break;
/*     */       case 6:
/* 521 */         this.pixelaccessor = new ByteRgbAccess();
/* 522 */         break;
/*     */       case 7:
/* 524 */         this.pixelaccessor = new ByteAccess(FX_ByteBgraPre_FORMAT, (ByteBuffer)this.pixelBuffer, 4);
/*     */ 
/* 526 */         break;
/*     */       case 8:
/* 528 */         this.pixelaccessor = new IntAccess(FX_IntArgbPre_FORMAT, (IntBuffer)this.pixelBuffer);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 533 */     return this.pixelaccessor;
/*     */   }
/*     */ 
/*     */   public javafx.scene.image.PixelFormat getPlatformPixelFormat()
/*     */   {
/* 538 */     return getPixelAccessor().getPlatformPixelFormat();
/*     */   }
/*     */ 
/*     */   public boolean isWritable()
/*     */   {
/* 543 */     return getPixelAccessor().isWritable();
/*     */   }
/*     */ 
/*     */   public PlatformImage promoteToWritableImage()
/*     */   {
/* 548 */     return getPixelAccessor().promoteToWritableImage();
/*     */   }
/*     */ 
/*     */   public int getArgb(int paramInt1, int paramInt2)
/*     */   {
/* 553 */     return getPixelAccessor().getArgb(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void setArgb(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 558 */     getPixelAccessor().setArgb(paramInt1, paramInt2, paramInt3);
/* 559 */     this.serial += 1;
/*     */   }
/*     */ 
/*     */   public <T extends Buffer> void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<T> paramWritablePixelFormat, T paramT, int paramInt5)
/*     */   {
/* 568 */     getPixelAccessor().getPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramWritablePixelFormat, paramT, paramInt5);
/*     */   }
/*     */ 
/*     */   public void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<ByteBuffer> paramWritablePixelFormat, byte[] paramArrayOfByte, int paramInt5, int paramInt6)
/*     */   {
/* 577 */     getPixelAccessor().getPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramWritablePixelFormat, paramArrayOfByte, paramInt5, paramInt6);
/*     */   }
/*     */ 
/*     */   public void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<IntBuffer> paramWritablePixelFormat, int[] paramArrayOfInt, int paramInt5, int paramInt6)
/*     */   {
/* 586 */     getPixelAccessor().getPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramWritablePixelFormat, paramArrayOfInt, paramInt5, paramInt6);
/*     */   }
/*     */ 
/*     */   public <T extends Buffer> void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, javafx.scene.image.PixelFormat<T> paramPixelFormat, T paramT, int paramInt5)
/*     */   {
/* 596 */     getPixelAccessor().setPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramPixelFormat, paramT, paramInt5);
/*     */ 
/* 598 */     this.serial += 1;
/*     */   }
/*     */ 
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, javafx.scene.image.PixelFormat<ByteBuffer> paramPixelFormat, byte[] paramArrayOfByte, int paramInt5, int paramInt6)
/*     */   {
/* 606 */     getPixelAccessor().setPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramPixelFormat, paramArrayOfByte, paramInt5, paramInt6);
/*     */ 
/* 608 */     this.serial += 1;
/*     */   }
/*     */ 
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, javafx.scene.image.PixelFormat<IntBuffer> paramPixelFormat, int[] paramArrayOfInt, int paramInt5, int paramInt6)
/*     */   {
/* 616 */     getPixelAccessor().setPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramPixelFormat, paramArrayOfInt, paramInt5, paramInt6);
/*     */ 
/* 618 */     this.serial += 1;
/*     */   }
/*     */ 
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PixelReader paramPixelReader, int paramInt5, int paramInt6)
/*     */   {
/* 625 */     getPixelAccessor().setPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramPixelReader, paramInt5, paramInt6);
/* 626 */     this.serial += 1;
/*     */   }
/*     */ 
/*     */   class ByteRgbAccess extends Image.ByteAccess
/*     */   {
/*     */     public ByteRgbAccess()
/*     */     {
/* 877 */       super(Image.FX_ByteRgb_FORMAT, (ByteBuffer)Image.this.pixelBuffer, 3);
/*     */     }
/*     */ 
/*     */     public PlatformImage promoteToWritableImage()
/*     */     {
/* 882 */       return Image.this.promoteByteRgbToByteBgra();
/*     */     }
/*     */   }
/*     */ 
/*     */   class UnsupportedAccess extends Image.ByteAccess
/*     */   {
/*     */     private UnsupportedAccess()
/*     */     {
/* 871 */       super(null, null, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   class IntAccess extends Image.Accessor<IntBuffer>
/*     */   {
/*     */     IntAccess(IntBuffer arg2)
/*     */     {
/* 803 */       super(localPixelFormat, localBuffer, 1);
/*     */     }
/*     */ 
/*     */     public void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<ByteBuffer> paramWritablePixelFormat, byte[] paramArrayOfByte, int paramInt5, int paramInt6)
/*     */     {
/* 811 */       BytePixelSetter localBytePixelSetter = PixelUtils.getByteSetter(paramWritablePixelFormat);
/* 812 */       IntToBytePixelConverter localIntToBytePixelConverter = PixelUtils.getI2BConverter(getGetter(), localBytePixelSetter);
/*     */ 
/* 814 */       localIntToBytePixelConverter.convert((IntBuffer)getBuffer(), getIndex(paramInt1, paramInt2), this.scanlineElems, paramArrayOfByte, paramInt5, paramInt6, paramInt3, paramInt4);
/*     */     }
/*     */ 
/*     */     public void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<IntBuffer> paramWritablePixelFormat, int[] paramArrayOfInt, int paramInt5, int paramInt6)
/*     */     {
/* 824 */       IntPixelSetter localIntPixelSetter = PixelUtils.getIntSetter(paramWritablePixelFormat);
/* 825 */       IntToIntPixelConverter localIntToIntPixelConverter = PixelUtils.getI2IConverter(getGetter(), localIntPixelSetter);
/*     */ 
/* 827 */       localIntToIntPixelConverter.convert((IntBuffer)getBuffer(), getIndex(paramInt1, paramInt2), this.scanlineElems, paramArrayOfInt, paramInt5, paramInt6, paramInt3, paramInt4);
/*     */     }
/*     */ 
/*     */     public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, javafx.scene.image.PixelFormat<ByteBuffer> paramPixelFormat, byte[] paramArrayOfByte, int paramInt5, int paramInt6)
/*     */     {
/* 837 */       BytePixelGetter localBytePixelGetter = PixelUtils.getByteGetter(paramPixelFormat);
/* 838 */       ByteToIntPixelConverter localByteToIntPixelConverter = PixelUtils.getB2IConverter(localBytePixelGetter, getSetter());
/*     */ 
/* 840 */       localByteToIntPixelConverter.convert(paramArrayOfByte, paramInt5, paramInt6, (IntBuffer)getBuffer(), getIndex(paramInt1, paramInt2), this.scanlineElems, paramInt3, paramInt4);
/*     */     }
/*     */ 
/*     */     public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, javafx.scene.image.PixelFormat<IntBuffer> paramPixelFormat, int[] paramArrayOfInt, int paramInt5, int paramInt6)
/*     */     {
/* 850 */       IntPixelGetter localIntPixelGetter = PixelUtils.getIntGetter(paramPixelFormat);
/* 851 */       IntToIntPixelConverter localIntToIntPixelConverter = PixelUtils.getI2IConverter(localIntPixelGetter, getSetter());
/*     */ 
/* 853 */       localIntToIntPixelConverter.convert(paramArrayOfInt, paramInt5, paramInt6, (IntBuffer)getBuffer(), getIndex(paramInt1, paramInt2), this.scanlineElems, paramInt3, paramInt4);
/*     */     }
/*     */ 
/*     */     public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PixelReader paramPixelReader, int paramInt5, int paramInt6)
/*     */     {
/* 861 */       IntBuffer localIntBuffer = ((IntBuffer)this.theBuffer).duplicate();
/* 862 */       localIntBuffer.position(localIntBuffer.position() + getIndex(paramInt1, paramInt2));
/* 863 */       paramPixelReader.getPixels(paramInt5, paramInt6, paramInt3, paramInt4, (WritablePixelFormat)this.theFormat, localIntBuffer, this.scanlineElems);
/*     */     }
/*     */   }
/*     */ 
/*     */   class ByteAccess extends Image.Accessor<ByteBuffer>
/*     */   {
/*     */     ByteAccess(ByteBuffer paramInt, int arg3)
/*     */     {
/* 735 */       super(paramInt, localBuffer, i);
/*     */     }
/*     */ 
/*     */     public void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<ByteBuffer> paramWritablePixelFormat, byte[] paramArrayOfByte, int paramInt5, int paramInt6)
/*     */     {
/* 743 */       BytePixelSetter localBytePixelSetter = PixelUtils.getByteSetter(paramWritablePixelFormat);
/* 744 */       ByteToBytePixelConverter localByteToBytePixelConverter = PixelUtils.getB2BConverter(getGetter(), localBytePixelSetter);
/*     */ 
/* 746 */       localByteToBytePixelConverter.convert((ByteBuffer)getBuffer(), getIndex(paramInt1, paramInt2), this.scanlineElems, paramArrayOfByte, paramInt5, paramInt6, paramInt3, paramInt4);
/*     */     }
/*     */ 
/*     */     public void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<IntBuffer> paramWritablePixelFormat, int[] paramArrayOfInt, int paramInt5, int paramInt6)
/*     */     {
/* 756 */       IntPixelSetter localIntPixelSetter = PixelUtils.getIntSetter(paramWritablePixelFormat);
/* 757 */       ByteToIntPixelConverter localByteToIntPixelConverter = PixelUtils.getB2IConverter(getGetter(), localIntPixelSetter);
/*     */ 
/* 759 */       localByteToIntPixelConverter.convert((ByteBuffer)getBuffer(), getIndex(paramInt1, paramInt2), this.scanlineElems, paramArrayOfInt, paramInt5, paramInt6, paramInt3, paramInt4);
/*     */     }
/*     */ 
/*     */     public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, javafx.scene.image.PixelFormat<ByteBuffer> paramPixelFormat, byte[] paramArrayOfByte, int paramInt5, int paramInt6)
/*     */     {
/* 769 */       BytePixelGetter localBytePixelGetter = PixelUtils.getByteGetter(paramPixelFormat);
/* 770 */       ByteToBytePixelConverter localByteToBytePixelConverter = PixelUtils.getB2BConverter(localBytePixelGetter, getSetter());
/*     */ 
/* 772 */       localByteToBytePixelConverter.convert(paramArrayOfByte, paramInt5, paramInt6, (ByteBuffer)getBuffer(), getIndex(paramInt1, paramInt2), this.scanlineElems, paramInt3, paramInt4);
/*     */     }
/*     */ 
/*     */     public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, javafx.scene.image.PixelFormat<IntBuffer> paramPixelFormat, int[] paramArrayOfInt, int paramInt5, int paramInt6)
/*     */     {
/* 782 */       IntPixelGetter localIntPixelGetter = PixelUtils.getIntGetter(paramPixelFormat);
/* 783 */       IntToBytePixelConverter localIntToBytePixelConverter = PixelUtils.getI2BConverter(localIntPixelGetter, getSetter());
/*     */ 
/* 785 */       localIntToBytePixelConverter.convert(paramArrayOfInt, paramInt5, paramInt6, (ByteBuffer)getBuffer(), getIndex(paramInt1, paramInt2), this.scanlineElems, paramInt3, paramInt4);
/*     */     }
/*     */ 
/*     */     public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PixelReader paramPixelReader, int paramInt5, int paramInt6)
/*     */     {
/* 793 */       ByteBuffer localByteBuffer = ((ByteBuffer)this.theBuffer).duplicate();
/* 794 */       localByteBuffer.position(localByteBuffer.position() + getIndex(paramInt1, paramInt2));
/* 795 */       paramPixelReader.getPixels(paramInt5, paramInt6, paramInt3, paramInt4, (WritablePixelFormat)this.theFormat, localByteBuffer, this.scanlineElems);
/*     */     }
/*     */   }
/*     */ 
/*     */   abstract class Accessor<I extends Buffer>
/*     */     implements PlatformImage
/*     */   {
/*     */     javafx.scene.image.PixelFormat<I> theFormat;
/*     */     PixelGetter<I> theGetter;
/*     */     PixelSetter<I> theSetter;
/*     */     I theBuffer;
/*     */     int pixelElems;
/*     */     int scanlineElems;
/*     */     int offsetElems;
/*     */ 
/*     */     Accessor(I paramInt, int arg3)
/*     */     {
/* 639 */       this.theFormat = paramInt;
/* 640 */       this.theGetter = PixelUtils.getGetter(paramInt);
/* 641 */       if ((paramInt instanceof WritablePixelFormat))
/* 642 */         this.theSetter = PixelUtils.getSetter((WritablePixelFormat)paramInt);
/*     */       Object localObject;
/* 644 */       this.theBuffer = localObject;
/*     */       int i;
/* 645 */       this.pixelElems = i;
/* 646 */       this.scanlineElems = (Image.this.scanlineStride / Image.this.pixelFormat.getDataType().getSizeInBytes());
/* 647 */       this.offsetElems = (Image.this.minY * this.scanlineElems + Image.this.minX * i);
/*     */     }
/*     */ 
/*     */     public int getIndex(int paramInt1, int paramInt2) {
/* 651 */       if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= Image.this.width) || (paramInt2 >= Image.this.height)) {
/* 652 */         throw new IndexOutOfBoundsException(paramInt1 + ", " + paramInt2);
/*     */       }
/* 654 */       return this.offsetElems + paramInt2 * this.scanlineElems + paramInt1 * this.pixelElems;
/*     */     }
/*     */ 
/*     */     public I getBuffer() {
/* 658 */       return this.theBuffer;
/*     */     }
/*     */ 
/*     */     public PixelGetter<I> getGetter() {
/* 662 */       if (this.theGetter == null) {
/* 663 */         throw new UnsupportedOperationException("Unsupported Image type");
/*     */       }
/* 665 */       return this.theGetter;
/*     */     }
/*     */ 
/*     */     public PixelSetter<I> getSetter() {
/* 669 */       if (this.theSetter == null) {
/* 670 */         throw new UnsupportedOperationException("Unsupported Image type");
/*     */       }
/* 672 */       return this.theSetter;
/*     */     }
/*     */ 
/*     */     public javafx.scene.image.PixelFormat getPlatformPixelFormat()
/*     */     {
/* 677 */       return this.theFormat;
/*     */     }
/*     */ 
/*     */     public boolean isWritable()
/*     */     {
/* 682 */       return this.theSetter != null;
/*     */     }
/*     */ 
/*     */     public PlatformImage promoteToWritableImage()
/*     */     {
/* 687 */       return Image.this;
/*     */     }
/*     */ 
/*     */     public int getArgb(int paramInt1, int paramInt2)
/*     */     {
/* 692 */       return getGetter().getArgb(getBuffer(), getIndex(paramInt1, paramInt2));
/*     */     }
/*     */ 
/*     */     public void setArgb(int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 697 */       getSetter().setArgb(getBuffer(), getIndex(paramInt1, paramInt2), paramInt3);
/*     */     }
/*     */ 
/*     */     public <T extends Buffer> void getPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, WritablePixelFormat<T> paramWritablePixelFormat, T paramT, int paramInt5)
/*     */     {
/* 706 */       PixelSetter localPixelSetter = PixelUtils.getSetter(paramWritablePixelFormat);
/* 707 */       PixelConverter localPixelConverter = PixelUtils.getConverter(getGetter(), localPixelSetter);
/*     */ 
/* 709 */       int i = paramT.position();
/* 710 */       localPixelConverter.convert(getBuffer(), getIndex(paramInt1, paramInt2), this.scanlineElems, paramT, i, paramInt5, paramInt3, paramInt4);
/*     */     }
/*     */ 
/*     */     public <T extends Buffer> void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, javafx.scene.image.PixelFormat<T> paramPixelFormat, T paramT, int paramInt5)
/*     */     {
/* 721 */       PixelGetter localPixelGetter = PixelUtils.getGetter(paramPixelFormat);
/* 722 */       PixelConverter localPixelConverter = PixelUtils.getConverter(localPixelGetter, getSetter());
/*     */ 
/* 724 */       int i = paramT.position();
/* 725 */       localPixelConverter.convert(paramT, i, paramInt5, getBuffer(), getIndex(paramInt1, paramInt2), this.scanlineElems, paramInt3, paramInt4);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.Image
 * JD-Core Version:    0.6.2
 */