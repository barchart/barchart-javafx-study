/*      */ package com.sun.javafx.iio.png;
/*      */ 
/*      */ import com.sun.javafx.geom.Point2D;
/*      */ import com.sun.javafx.geom.Rectangle;
/*      */ import com.sun.javafx.iio.ImageFrame;
/*      */ import com.sun.javafx.iio.ImageMetadata;
/*      */ import com.sun.javafx.iio.ImageStorage;
/*      */ import com.sun.javafx.iio.ImageStorage.ImageType;
/*      */ import com.sun.javafx.iio.common.ImageLoaderImpl;
/*      */ import com.sun.javafx.iio.common.ImageTools;
/*      */ import com.sun.javafx.iio.common.PushbroomScaler;
/*      */ import com.sun.javafx.iio.common.ScalerFactory;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.nio.Buffer;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.Arrays;
/*      */ import java.util.zip.Inflater;
/*      */ import java.util.zip.InflaterInputStream;
/*      */ import java.util.zip.ZipException;
/*      */ 
/*      */ public class PNGImageLoader extends ImageLoaderImpl
/*      */ {
/*      */   static final int IHDR_TYPE = 1229472850;
/*      */   static final int PLTE_TYPE = 1347179589;
/*      */   static final int IDAT_TYPE = 1229209940;
/*      */   static final int IEND_TYPE = 1229278788;
/*      */   static final int bKGD_TYPE = 1649100612;
/*      */   static final int cHRM_TYPE = 1665684045;
/*      */   static final int gAMA_TYPE = 1732332865;
/*      */   static final int hIST_TYPE = 1749635924;
/*      */   static final int iCCP_TYPE = 1766015824;
/*      */   static final int iTXt_TYPE = 1767135348;
/*      */   static final int pHYs_TYPE = 1883789683;
/*      */   static final int sBIT_TYPE = 1933723988;
/*      */   static final int sPLT_TYPE = 1934642260;
/*      */   static final int sRGB_TYPE = 1934772034;
/*      */   static final int tEXt_TYPE = 1950701684;
/*      */   static final int tIME_TYPE = 1950960965;
/*      */   static final int tRNS_TYPE = 1951551059;
/*      */   static final int zTXt_TYPE = 2052348020;
/*      */   static final int PNG_COLOR_GRAY = 0;
/*      */   static final int PNG_COLOR_RGB = 2;
/*      */   static final int PNG_COLOR_PALETTE = 3;
/*      */   static final int PNG_COLOR_GRAY_ALPHA = 4;
/*      */   static final int PNG_COLOR_RGB_ALPHA = 6;
/*   93 */   static final int[] inputBandsForColorType = { 1, -1, 3, 1, 2, -1, 4 };
/*      */   static final int PNG_FILTER_NONE = 0;
/*      */   static final int PNG_FILTER_SUB = 1;
/*      */   static final int PNG_FILTER_UP = 2;
/*      */   static final int PNG_FILTER_AVERAGE = 3;
/*      */   static final int PNG_FILTER_PAETH = 4;
/*  107 */   static final int[] adam7XOffset = { 0, 4, 0, 2, 0, 1, 0 };
/*  108 */   static final int[] adam7YOffset = { 0, 0, 4, 0, 2, 0, 1 };
/*  109 */   static final int[] adam7XSubsampling = { 8, 8, 4, 4, 2, 2, 1, 1 };
/*  110 */   static final int[] adam7YSubsampling = { 8, 8, 8, 4, 4, 2, 2, 1 };
/*      */   private InputStream stream;
/*  135 */   private boolean gotHeader = false;
/*  136 */   private boolean gotMetadata = false;
/*      */   private PNGIDATChunkInputStream IDATStream;
/*  138 */   int sourceXSubsampling = -1;
/*  139 */   int sourceYSubsampling = -1;
/*  140 */   int sourceMinProgressivePass = 0;
/*  141 */   int sourceMaxProgressivePass = 6;
/*      */   private PNGImageMetadata metadata;
/*      */   private DataInputStream pixelStream;
/*      */   private ImageStorage.ImageType sourceType;
/*      */   private ImageStorage.ImageType destType;
/*      */   private int numDestBands;
/*      */   private ImageFrame theImage;
/*      */   byte[][] thePalette;
/*      */   private PushbroomScaler scaler;
/*      */   private boolean isInterlaced;
/*      */   private boolean isConverting;
/*      */   private boolean isScaling;
/*      */   private ROW_PROCESS rowProc;
/*  155 */   byte[] curr = null;
/*  156 */   byte[] prior = null;
/*  157 */   byte[] passRow = null;
/*  158 */   byte[] rescaledBuf = null;
/*  159 */   short[] shortData = null;
/*  160 */   byte[] convertedBuf = null;
/*      */   private int pixelsDone;
/*      */   private int totalPixels;
/*      */ 
/*      */   public PNGImageLoader(InputStream paramInputStream)
/*      */     throws IOException
/*      */   {
/*  165 */     super(PNGDescriptor.getInstance());
/*  166 */     if (paramInputStream == null) {
/*  167 */       throw new IllegalArgumentException("input == null!");
/*      */     }
/*  169 */     this.stream = paramInputStream;
/*  170 */     readHeader();
/*      */   }
/*      */ 
/*      */   public void abort()
/*      */   {
/*      */   }
/*      */ 
/*      */   public void dispose()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ImageFrame load(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
/*  182 */     if (paramInt1 != 0) {
/*  183 */       return null;
/*      */     }
/*      */ 
/*  186 */     readImage(paramInt2, paramInt3, paramBoolean1, paramBoolean2);
/*      */ 
/*  188 */     return this.theImage;
/*      */   }
/*      */ 
/*      */   private String readNullTerminatedString(String paramString, int paramInt) throws IOException {
/*  192 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/*      */ 
/*  194 */     int j = 0;
/*      */     int i;
/*  195 */     while ((paramInt > j++) && ((i = this.stream.read()) != 0)) {
/*  196 */       if (i == -1) {
/*  197 */         throw new EOFException();
/*      */       }
/*  199 */       localByteArrayOutputStream.write(i);
/*      */     }
/*  201 */     return new String(localByteArrayOutputStream.toByteArray(), paramString);
/*      */   }
/*      */ 
/*      */   private synchronized void readHeader() throws IOException {
/*  205 */     if (this.gotHeader) {
/*  206 */       return;
/*      */     }
/*  208 */     if (this.stream == null) {
/*  209 */       throw new IllegalStateException("Input source not set!");
/*      */     }
/*      */ 
/*  212 */     byte[] arrayOfByte = new byte[8];
/*  213 */     ImageTools.readFully(this.stream, arrayOfByte);
/*      */ 
/*  215 */     if ((arrayOfByte[0] != -119) || (arrayOfByte[1] != 80) || (arrayOfByte[2] != 78) || (arrayOfByte[3] != 71) || (arrayOfByte[4] != 13) || (arrayOfByte[5] != 10) || (arrayOfByte[6] != 26) || (arrayOfByte[7] != 10))
/*      */     {
/*  223 */       throw new IOException("Bad PNG signature!");
/*      */     }
/*      */ 
/*  226 */     int i = this.stream.read() << 24 | this.stream.read() << 16 | this.stream.read() << 8 | this.stream.read();
/*      */ 
/*  228 */     if (i != 13) {
/*  229 */       throw new IOException("Bad length for IHDR chunk!");
/*      */     }
/*  231 */     int j = this.stream.read() << 24 | this.stream.read() << 16 | this.stream.read() << 8 | this.stream.read();
/*      */ 
/*  233 */     if (j != 1229472850) {
/*  234 */       throw new IOException("Bad type for IHDR chunk!");
/*      */     }
/*      */ 
/*  237 */     this.metadata = new PNGImageMetadata();
/*      */ 
/*  239 */     int k = this.stream.read() << 24 | this.stream.read() << 16 | this.stream.read() << 8 | this.stream.read();
/*      */ 
/*  241 */     int m = this.stream.read() << 24 | this.stream.read() << 16 | this.stream.read() << 8 | this.stream.read();
/*      */ 
/*  246 */     ImageTools.readFully(this.stream, arrayOfByte, 0, 5);
/*  247 */     int n = arrayOfByte[0] & 0xFF;
/*  248 */     int i1 = arrayOfByte[1] & 0xFF;
/*  249 */     int i2 = arrayOfByte[2] & 0xFF;
/*  250 */     int i3 = arrayOfByte[3] & 0xFF;
/*  251 */     int i4 = arrayOfByte[4] & 0xFF;
/*      */ 
/*  254 */     this.stream.skip(4L);
/*      */ 
/*  258 */     if (k == 0) {
/*  259 */       throw new IOException("Image width == 0!");
/*      */     }
/*  261 */     if (m == 0) {
/*  262 */       throw new IOException("Image height == 0!");
/*      */     }
/*  264 */     if ((n != 1) && (n != 2) && (n != 4) && (n != 8) && (n != 16))
/*      */     {
/*  266 */       throw new IOException("Bit depth must be 1, 2, 4, 8, or 16!");
/*      */     }
/*  268 */     if ((i1 != 0) && (i1 != 2) && (i1 != 3) && (i1 != 4) && (i1 != 6))
/*      */     {
/*  270 */       throw new IOException("Color type must be 0, 2, 3, 4, or 6!");
/*      */     }
/*  272 */     if ((i1 == 3) && (n == 16)) {
/*  273 */       throw new IOException("Bad color type/bit depth combination!");
/*      */     }
/*  275 */     if (((i1 == 2) || (i1 == 6) || (i1 == 4)) && (n != 8) && (n != 16))
/*      */     {
/*  279 */       throw new IOException("Bad color type/bit depth combination!");
/*      */     }
/*  281 */     if (i2 != 0) {
/*  282 */       throw new IOException("Unknown compression method (not 0)!");
/*      */     }
/*  284 */     if (i3 != 0) {
/*  285 */       throw new IOException("Unknown filter method (not 0)!");
/*      */     }
/*  287 */     if ((i4 != 0) && (i4 != 1)) {
/*  288 */       throw new IOException("Unknown interlace method (not 0 or 1)!");
/*      */     }
/*      */ 
/*  291 */     switch (i1)
/*      */     {
/*      */     case 0:
/*  294 */       this.sourceType = ImageStorage.ImageType.GRAY;
/*  295 */       break;
/*      */     case 2:
/*  298 */       this.sourceType = ImageStorage.ImageType.RGB;
/*  299 */       break;
/*      */     case 3:
/*  302 */       this.sourceType = ImageStorage.ImageType.PALETTE;
/*  303 */       break;
/*      */     case 4:
/*  306 */       this.sourceType = ImageStorage.ImageType.GRAY_ALPHA;
/*  307 */       break;
/*      */     case 6:
/*  310 */       this.sourceType = ImageStorage.ImageType.RGBA;
/*  311 */       break;
/*      */     case 1:
/*      */     case 5:
/*      */     default:
/*  313 */       throw new UnsupportedOperationException("Unhandled color type");
/*      */     }
/*      */ 
/*  316 */     this.metadata.IHDR_present = true;
/*  317 */     this.metadata.IHDR_width = k;
/*  318 */     this.metadata.IHDR_height = m;
/*  319 */     this.metadata.IHDR_bitDepth = n;
/*  320 */     this.metadata.IHDR_colorType = i1;
/*  321 */     this.metadata.IHDR_compressionMethod = i2;
/*  322 */     this.metadata.IHDR_filterMethod = i3;
/*  323 */     this.metadata.IHDR_interlaceMethod = i4;
/*  324 */     this.isInterlaced = (this.metadata.IHDR_interlaceMethod != 0);
/*  325 */     this.gotHeader = true;
/*      */   }
/*      */ 
/*      */   private void parse_PLTE_chunk(int paramInt) throws IOException {
/*  329 */     if (this.metadata.PLTE_present) {
/*  330 */       emitWarning("A PNG image may not contain more than one PLTE chunk.\nThe chunk wil be ignored.");
/*      */ 
/*  333 */       return;
/*  334 */     }if ((this.metadata.IHDR_colorType == 0) || (this.metadata.IHDR_colorType == 4))
/*      */     {
/*  336 */       emitWarning("A PNG gray or gray alpha image cannot have a PLTE chunk.\nThe chunk wil be ignored.");
/*      */ 
/*  339 */       return;
/*      */     }
/*      */ 
/*  342 */     byte[] arrayOfByte = new byte[paramInt];
/*  343 */     ImageTools.readFully(this.stream, arrayOfByte);
/*      */ 
/*  345 */     int i = paramInt / 3;
/*      */     int j;
/*  346 */     if (this.metadata.IHDR_colorType == 3) {
/*  347 */       j = 1 << this.metadata.IHDR_bitDepth;
/*  348 */       if (i > j) {
/*  349 */         emitWarning("PLTE chunk contains too many entries for bit depth, ignoring extras.");
/*      */ 
/*  351 */         i = j;
/*      */       }
/*  353 */       i = Math.min(i, j);
/*      */     }
/*      */ 
/*  358 */     if (i > 16)
/*  359 */       j = 256;
/*  360 */     else if (i > 4)
/*  361 */       j = 16;
/*  362 */     else if (i > 2)
/*  363 */       j = 4;
/*      */     else {
/*  365 */       j = 2;
/*      */     }
/*      */ 
/*  368 */     this.metadata.PLTE_present = true;
/*  369 */     this.metadata.PLTE_red = new byte[j];
/*  370 */     this.metadata.PLTE_green = new byte[j];
/*  371 */     this.metadata.PLTE_blue = new byte[j];
/*      */ 
/*  373 */     int k = 0;
/*  374 */     for (int m = 0; m < i; m++) {
/*  375 */       this.metadata.PLTE_red[m] = arrayOfByte[(k++)];
/*  376 */       this.metadata.PLTE_green[m] = arrayOfByte[(k++)];
/*  377 */       this.metadata.PLTE_blue[m] = arrayOfByte[(k++)];
/*      */     }
/*      */   }
/*      */ 
/*      */   private void parse_bKGD_chunk()
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_cHRM_chunk()
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_gAMA_chunk()
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_hIST_chunk(int paramInt)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_iCCP_chunk(int paramInt)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_iTXt_chunk(int paramInt)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_pHYs_chunk()
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_sBIT_chunk()
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_sPLT_chunk(int paramInt)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_sRGB_chunk()
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_tEXt_chunk(int paramInt)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_tIME_chunk()
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private void parse_tRNS_chunk(int paramInt)
/*      */     throws IOException
/*      */   {
/*  578 */     int i = this.metadata.IHDR_colorType;
/*  579 */     if (i == 3) {
/*  580 */       if (!this.metadata.PLTE_present) {
/*  581 */         emitWarning("tRNS chunk without prior PLTE chunk, ignoring it.");
/*      */ 
/*  583 */         return;
/*      */       }
/*      */ 
/*  587 */       int j = this.metadata.PLTE_red.length;
/*  588 */       int k = paramInt;
/*  589 */       if (k > j) {
/*  590 */         emitWarning("tRNS chunk has more entries than prior PLTE chunk, ignoring extras.");
/*      */ 
/*  592 */         k = j;
/*      */       }
/*  594 */       this.metadata.tRNS_alpha = new byte[k];
/*  595 */       this.metadata.tRNS_colorType = 3;
/*  596 */       ImageTools.readFully(this.stream, this.metadata.tRNS_alpha, 0, k);
/*  597 */       this.stream.skip(paramInt - k);
/*  598 */     } else if (i == 0) {
/*  599 */       if (paramInt != 2) {
/*  600 */         emitWarning("tRNS chunk for gray image must have length 2, ignoring chunk.");
/*      */ 
/*  602 */         this.stream.skip(paramInt);
/*  603 */         return;
/*      */       }
/*  605 */       this.metadata.tRNS_gray = (this.stream.read() << 8 | this.stream.read());
/*  606 */       this.metadata.tRNS_colorType = 0;
/*  607 */     } else if (i == 2) {
/*  608 */       if (paramInt != 6) {
/*  609 */         emitWarning("tRNS chunk for RGB image must have length 6, ignoring chunk.");
/*      */ 
/*  611 */         this.stream.skip(paramInt);
/*  612 */         return;
/*      */       }
/*  614 */       this.metadata.tRNS_red = (this.stream.read() << 8 | this.stream.read());
/*  615 */       this.metadata.tRNS_green = (this.stream.read() << 8 | this.stream.read());
/*  616 */       this.metadata.tRNS_blue = (this.stream.read() << 8 | this.stream.read());
/*  617 */       this.metadata.tRNS_colorType = 2;
/*      */     } else {
/*  619 */       emitWarning("Gray+Alpha and RGBS images may not have a tRNS chunk, ignoring it.");
/*      */ 
/*  621 */       return;
/*      */     }
/*      */ 
/*  624 */     this.metadata.tRNS_present = true;
/*      */   }
/*      */ 
/*      */   private void parse_zTXt_chunk(int paramInt)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private synchronized void readMetadata()
/*      */     throws IOException
/*      */   {
/*  655 */     if (this.gotMetadata) {
/*  656 */       return;
/*      */     }
/*      */ 
/*  659 */     readHeader();
/*      */ 
/*  667 */     int i = this.metadata.IHDR_colorType;
/*      */ 
/*  669 */     if (i != 3) {
/*      */       try {
/*      */         while (true) {
/*  672 */           int j = this.stream.read() << 24 | this.stream.read() << 16 | this.stream.read() << 8 | this.stream.read();
/*      */ 
/*  674 */           int n = this.stream.read() << 24 | this.stream.read() << 16 | this.stream.read() << 8 | this.stream.read();
/*      */ 
/*  677 */           if (n == 1229209940) {
/*  678 */             this.IDATStream = new PNGIDATChunkInputStream(this.stream, j);
/*      */ 
/*  682 */             break;
/*      */           }
/*      */ 
/*  685 */           this.stream.skip(j + 4);
/*      */         }
/*      */       }
/*      */       catch (IOException localIOException1) {
/*  689 */         IOException localIOException3 = new IOException("Error skipping PNG metadata");
/*  690 */         localIOException3.initCause(localIOException1);
/*  691 */         throw localIOException3;
/*      */       }
/*      */ 
/*  694 */       this.gotMetadata = true;
/*  695 */       return;
/*      */     }
/*      */     try
/*      */     {
/*      */       while (true)
/*      */       {
/*  701 */         int k = this.stream.read() << 24 | this.stream.read() << 16 | this.stream.read() << 8 | this.stream.read();
/*      */ 
/*  703 */         int i1 = this.stream.read() << 24 | this.stream.read() << 16 | this.stream.read() << 8 | this.stream.read();
/*      */ 
/*  706 */         switch (i1) {
/*      */         case 1229209940:
/*  708 */           this.IDATStream = new PNGIDATChunkInputStream(this.stream, k);
/*      */ 
/*  712 */           break;
/*      */         case 1347179589:
/*  714 */           parse_PLTE_chunk(k);
/*  715 */           break;
/*      */         case 1951551059:
/*  753 */           parse_tRNS_chunk(k);
/*  754 */           break;
/*      */         default:
/*  778 */           this.stream.skip(k);
/*      */         }
/*      */ 
/*  782 */         int i2 = this.stream.read() << 24 | this.stream.read() << 16 | this.stream.read() << 8 | this.stream.read();
/*      */       }
/*      */     }
/*      */     catch (IOException localIOException2)
/*      */     {
/*  787 */       IOException localIOException4 = new IOException("Error reading PNG metadata");
/*  788 */       localIOException4.initCause(localIOException2);
/*  789 */       throw localIOException4;
/*      */     }
/*      */ 
/*  792 */     if (this.metadata.PLTE_present)
/*      */     {
/*  807 */       if (this.metadata.tRNS_present)
/*  808 */         this.thePalette = new byte[4][];
/*      */       else {
/*  810 */         this.thePalette = new byte[3][];
/*      */       }
/*  812 */       this.thePalette[0] = this.metadata.PLTE_red;
/*  813 */       this.thePalette[1] = this.metadata.PLTE_green;
/*  814 */       this.thePalette[2] = this.metadata.PLTE_blue;
/*      */ 
/*  816 */       int m = 1 << this.metadata.IHDR_bitDepth;
/*  817 */       if (this.metadata.PLTE_red.length < m) {
/*  818 */         this.thePalette[0] = new byte[m];
/*  819 */         System.arraycopy(this.metadata.PLTE_red, 0, this.thePalette[0], 0, this.metadata.PLTE_red.length);
/*      */ 
/*  821 */         Arrays.fill(this.thePalette[0], this.metadata.PLTE_red.length, m, this.metadata.PLTE_red[(this.metadata.PLTE_red.length - 1)]);
/*      */       }
/*      */ 
/*  824 */       if (this.metadata.PLTE_green.length < m) {
/*  825 */         this.thePalette[1] = new byte[m];
/*  826 */         System.arraycopy(this.metadata.PLTE_green, 0, this.thePalette[1], 0, this.metadata.PLTE_green.length);
/*      */ 
/*  828 */         Arrays.fill(this.thePalette[1], this.metadata.PLTE_green.length, m, this.metadata.PLTE_green[(this.metadata.PLTE_green.length - 1)]);
/*      */       }
/*      */ 
/*  831 */       if (this.metadata.PLTE_red.length < m) {
/*  832 */         this.thePalette[2] = new byte[m];
/*  833 */         System.arraycopy(this.metadata.PLTE_blue, 0, this.thePalette[2], 0, this.metadata.PLTE_blue.length);
/*      */ 
/*  835 */         Arrays.fill(this.thePalette[2], this.metadata.PLTE_blue.length, m, this.metadata.PLTE_blue[(this.metadata.PLTE_blue.length - 1)]);
/*      */       }
/*      */ 
/*  839 */       if ((this.metadata.tRNS_present) && (this.metadata.tRNS_alpha != null)) {
/*  840 */         this.sourceType = ImageStorage.ImageType.PALETTE_ALPHA;
/*  841 */         if (this.metadata.tRNS_alpha.length >= this.metadata.PLTE_red.length) {
/*  842 */           this.thePalette[3] = this.metadata.tRNS_alpha;
/*      */         } else {
/*  844 */           this.thePalette[3] = new byte[this.metadata.PLTE_red.length];
/*  845 */           System.arraycopy(this.metadata.tRNS_alpha, 0, this.thePalette[3], 0, this.metadata.tRNS_alpha.length);
/*      */ 
/*  847 */           Arrays.fill(this.thePalette[3], this.metadata.tRNS_alpha.length, this.thePalette[3].length, (byte)-1);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  854 */     this.gotMetadata = true;
/*      */   }
/*      */ 
/*      */   private static void decodeSubFilter(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  860 */     for (int i = paramInt3; i < paramInt2; i++)
/*      */     {
/*  863 */       int j = paramArrayOfByte[(i + paramInt1)] & 0xFF;
/*  864 */       j += (paramArrayOfByte[(i + paramInt1 - paramInt3)] & 0xFF);
/*      */ 
/*  866 */       paramArrayOfByte[(i + paramInt1)] = ((byte)j);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void decodeUpFilter(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
/*      */   {
/*  873 */     for (int i = 0; i < paramInt3; i++) {
/*  874 */       int j = paramArrayOfByte1[(i + paramInt1)] & 0xFF;
/*  875 */       int k = paramArrayOfByte2[(i + paramInt2)] & 0xFF;
/*      */ 
/*  877 */       paramArrayOfByte1[(i + paramInt1)] = ((byte)(j + k));
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void decodeAverageFilter(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*      */     int i;
/*      */     int k;
/*  886 */     for (int m = 0; m < paramInt4; m++) {
/*  887 */       i = paramArrayOfByte1[(m + paramInt1)] & 0xFF;
/*  888 */       k = paramArrayOfByte2[(m + paramInt2)] & 0xFF;
/*      */ 
/*  890 */       paramArrayOfByte1[(m + paramInt1)] = ((byte)(i + k / 2));
/*      */     }
/*      */ 
/*  893 */     for (m = paramInt4; m < paramInt3; m++) {
/*  894 */       i = paramArrayOfByte1[(m + paramInt1)] & 0xFF;
/*  895 */       int j = paramArrayOfByte1[(m + paramInt1 - paramInt4)] & 0xFF;
/*  896 */       k = paramArrayOfByte2[(m + paramInt2)] & 0xFF;
/*      */ 
/*  898 */       paramArrayOfByte1[(m + paramInt1)] = ((byte)(i + (j + k) / 2));
/*      */     }
/*      */   }
/*      */ 
/*      */   private static int paethPredictor(int paramInt1, int paramInt2, int paramInt3) {
/*  903 */     int i = paramInt1 + paramInt2 - paramInt3;
/*  904 */     int j = Math.abs(i - paramInt1);
/*  905 */     int k = Math.abs(i - paramInt2);
/*  906 */     int m = Math.abs(i - paramInt3);
/*      */ 
/*  908 */     if ((j <= k) && (j <= m))
/*  909 */       return paramInt1;
/*  910 */     if (k <= m) {
/*  911 */       return paramInt2;
/*      */     }
/*  913 */     return paramInt3;
/*      */   }
/*      */ 
/*      */   private static void decodePaethFilter(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*      */     int i;
/*      */     int k;
/*  922 */     for (int n = 0; n < paramInt4; n++) {
/*  923 */       i = paramArrayOfByte1[(n + paramInt1)] & 0xFF;
/*  924 */       k = paramArrayOfByte2[(n + paramInt2)] & 0xFF;
/*      */ 
/*  926 */       paramArrayOfByte1[(n + paramInt1)] = ((byte)(i + k));
/*      */     }
/*      */ 
/*  929 */     for (n = paramInt4; n < paramInt3; n++) {
/*  930 */       i = paramArrayOfByte1[(n + paramInt1)] & 0xFF;
/*  931 */       int j = paramArrayOfByte1[(n + paramInt1 - paramInt4)] & 0xFF;
/*  932 */       k = paramArrayOfByte2[(n + paramInt2)] & 0xFF;
/*  933 */       int m = paramArrayOfByte2[(n + paramInt2 - paramInt4)] & 0xFF;
/*      */ 
/*  935 */       paramArrayOfByte1[(n + paramInt1)] = ((byte)(i + paethPredictor(j, k, m)));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void skipPass(int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/*  983 */     if ((paramInt1 == 0) || (paramInt2 == 0)) {
/*  984 */       return;
/*      */     }
/*      */ 
/*  987 */     int i = inputBandsForColorType[this.metadata.IHDR_colorType];
/*  988 */     int j = (i * paramInt1 * this.metadata.IHDR_bitDepth + 7) / 8;
/*      */ 
/*  991 */     for (int k = 0; k < paramInt2; k++)
/*      */     {
/*  993 */       this.pixelStream.skipBytes(1 + j);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateImageProgress(int paramInt)
/*      */   {
/* 1004 */     this.pixelsDone += paramInt;
/* 1005 */     updateImageProgress(100.0F * this.pixelsDone / this.totalPixels);
/*      */   }
/*      */ 
/*      */   private void decodePass(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/*      */     throws IOException
/*      */   {
/* 1013 */     if ((paramInt6 == 0) || (paramInt7 == 0)) {
/* 1014 */       return;
/*      */     }
/*      */ 
/* 1018 */     byte[] arrayOfByte1 = null;
/* 1019 */     Buffer localBuffer = this.theImage.getImageData();
/* 1020 */     if ((localBuffer instanceof ByteBuffer))
/* 1021 */       arrayOfByte1 = ((ByteBuffer)localBuffer).array();
/*      */     else {
/* 1023 */       throw new UnsupportedOperationException("Only ByteBuffer is supported!");
/*      */     }
/*      */ 
/* 1026 */     int i = 0;
/* 1027 */     int j = this.metadata.IHDR_width - 1;
/* 1028 */     int k = 0;
/* 1029 */     int m = this.metadata.IHDR_height - 1;
/*      */ 
/* 1047 */     int[] arrayOfInt1 = ImageTools.computeUpdatedPixels(new Rectangle(this.metadata.IHDR_width, this.metadata.IHDR_height), new Point2D(), i, k, j, m, this.sourceXSubsampling, this.sourceYSubsampling, paramInt2, paramInt3, paramInt6, paramInt7, paramInt4, paramInt5);
/*      */ 
/* 1057 */     int n = arrayOfInt1[0];
/* 1058 */     int i1 = arrayOfInt1[1];
/* 1059 */     int i2 = arrayOfInt1[2];
/* 1060 */     int i3 = arrayOfInt1[4];
/* 1061 */     int i4 = arrayOfInt1[5];
/*      */ 
/* 1068 */     int i5 = this.metadata.IHDR_bitDepth;
/* 1069 */     int i6 = inputBandsForColorType[this.metadata.IHDR_colorType];
/* 1070 */     int i7 = i5 == 16 ? 2 : 1;
/* 1071 */     i7 *= i6;
/* 1072 */     int i8 = 8 / i5;
/* 1073 */     int i9 = 1;
/*      */ 
/* 1075 */     switch (i5) {
/*      */     case 8:
/* 1077 */       i9 = 255;
/* 1078 */       break;
/*      */     case 4:
/* 1080 */       i9 = 15;
/* 1081 */       break;
/*      */     case 2:
/* 1083 */       i9 = 3;
/* 1084 */       break;
/*      */     case 1:
/* 1086 */       i9 = 1;
/*      */     case 3:
/*      */     case 5:
/*      */     case 6:
/* 1090 */     case 7: } int i10 = (i6 * paramInt6 * i5 + 7) / 8;
/* 1091 */     int i11 = i5 == 16 ? i10 / 2 : i10;
/*      */ 
/* 1094 */     if (i2 == 0) {
/* 1095 */       for (i12 = 0; i12 < paramInt7; i12++)
/*      */       {
/* 1097 */         updateImageProgress(paramInt6);
/*      */ 
/* 1099 */         this.pixelStream.skipBytes(1 + i10);
/*      */       }
/* 1101 */       return;
/*      */     }
/*      */ 
/* 1108 */     int i12 = n * this.sourceXSubsampling;
/* 1109 */     int i13 = (i12 - paramInt2) / paramInt4;
/*      */ 
/* 1112 */     int i14 = i3 * this.sourceXSubsampling / paramInt4;
/*      */ 
/* 1114 */     if ((this.curr == null) || (this.curr.length < i10)) {
/* 1115 */       this.curr = new byte[i10];
/* 1116 */       this.prior = new byte[i10];
/*      */ 
/* 1122 */       this.passRow = new byte[i10];
/* 1123 */       if (i5 == 16)
/*      */       {
/* 1125 */         this.shortData = new short[i10 / 2];
/*      */       }
/*      */     }
/*      */ 
/* 1129 */     Object localObject1 = i5 != 16 ? this.passRow : null;
/* 1130 */     if (this.rowProc == ROW_PROCESS.CONVERT_DOWNSCALE) {
/* 1131 */       i15 = i2 * this.numDestBands;
/* 1132 */       if ((this.convertedBuf == null) || (this.convertedBuf.length < i15)) {
/* 1133 */         this.convertedBuf = new byte[i15];
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1179 */     int i15 = 0;
/*      */ 
/* 1181 */     int[] arrayOfInt2 = new int[i6];
/* 1182 */     for (int i16 = 0; i16 < i6; i16++) {
/* 1183 */       arrayOfInt2[i16] = 8;
/*      */     }
/* 1185 */     i16 = arrayOfInt2.length;
/* 1186 */     for (int i17 = 0; i17 < i16; i17++) {
/* 1187 */       if (arrayOfInt2[i17] != i5) {
/* 1188 */         i15 = 1;
/* 1189 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1195 */     Object localObject2 = (int[][])null;
/*      */     int i19;
/*      */     int i21;
/* 1196 */     if (i15 != 0) {
/* 1197 */       i18 = (1 << i5) - 1;
/* 1198 */       i19 = i18 / 2;
/* 1199 */       localObject2 = new int[i16][];
/* 1200 */       for (i20 = 0; i20 < i16; i20++) {
/* 1201 */         i21 = (1 << arrayOfInt2[i20]) - 1;
/* 1202 */         localObject2[i20] = new int[i18 + 1];
/* 1203 */         for (int i22 = 0; i22 <= i18; i22++) {
/* 1204 */           localObject2[i20][i22] = ((i22 * i21 + i19) / i18);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1223 */     int i18 = (i14 == 1) && (i3 == 1) && (i15 == 0) ? 1 : 0;
/*      */ 
/* 1228 */     if (i18 != 0) {
/* 1229 */       this.rescaledBuf = null;
/* 1230 */       i19 = 0;
/*      */     }
/* 1232 */     else if (this.rowProc == ROW_PROCESS.COPY) {
/* 1233 */       this.rescaledBuf = arrayOfByte1;
/* 1234 */       i19 = this.theImage.getStride();
/*      */     } else {
/* 1236 */       i20 = this.metadata.IHDR_width * i6;
/* 1237 */       if ((this.rescaledBuf == null) || (this.rescaledBuf.length < i20)) {
/* 1238 */         this.rescaledBuf = new byte[this.metadata.IHDR_width * i6];
/*      */       }
/* 1240 */       i19 = 0;
/*      */     }
/*      */ 
/* 1245 */     for (int i20 = 0; i20 < paramInt7; i20++)
/*      */     {
/* 1247 */       updateImageProgress(paramInt6);
/*      */ 
/* 1250 */       i21 = this.pixelStream.read();
/*      */       try
/*      */       {
/* 1253 */         byte[] arrayOfByte2 = this.prior;
/* 1254 */         this.prior = this.curr;
/* 1255 */         this.curr = arrayOfByte2;
/*      */ 
/* 1257 */         this.pixelStream.readFully(this.curr, 0, i10);
/*      */       } catch (ZipException localZipException) {
/* 1259 */         IOException localIOException = new IOException("Error deflating compressed PNG data!");
/* 1260 */         localIOException.initCause(localZipException);
/* 1261 */         throw localIOException;
/*      */       }
/*      */ 
/* 1264 */       switch (i21) {
/*      */       case 0:
/* 1266 */         break;
/*      */       case 1:
/* 1268 */         decodeSubFilter(this.curr, 0, i10, i7);
/* 1269 */         break;
/*      */       case 2:
/* 1271 */         decodeUpFilter(this.curr, 0, this.prior, 0, i10);
/* 1272 */         break;
/*      */       case 3:
/* 1274 */         decodeAverageFilter(this.curr, 0, this.prior, 0, i10, i7);
/*      */ 
/* 1276 */         break;
/*      */       case 4:
/* 1278 */         decodePaethFilter(this.curr, 0, this.prior, 0, i10, i7);
/*      */ 
/* 1280 */         break;
/*      */       default:
/* 1282 */         throw new IOException("Unknown row filter type (= " + i21 + ")!");
/*      */       }
/*      */       int i24;
/* 1287 */       if (i5 < 16) {
/* 1288 */         System.arraycopy(this.curr, 0, localObject1, 0, i10);
/*      */       } else {
/* 1290 */         i23 = 0;
/* 1291 */         for (i24 = 0; i24 < i11; i24++) {
/* 1292 */           this.shortData[i24] = ((short)(this.curr[i23] << 8 | this.curr[(i23 + 1)] & 0xFF));
/*      */ 
/* 1294 */           i23 += 2;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1299 */       int i23 = i20 * paramInt5 + paramInt3;
/* 1300 */       if ((i23 >= 0) && (i23 < this.metadata.IHDR_height))
/*      */       {
/* 1302 */         i24 = i23 / this.sourceYSubsampling;
/* 1303 */         if (i24 >= k)
/*      */         {
/* 1306 */           if (i24 > m)
/*      */           {
/*      */             break;
/*      */           }
/*      */ 
/* 1313 */           if (i18 != 0) {
/* 1314 */             switch (1.$SwitchMap$com$sun$javafx$iio$png$PNGImageLoader$ROW_PROCESS[this.rowProc.ordinal()]) {
/*      */             case 1:
/* 1316 */               System.arraycopy(this.passRow, 0, arrayOfByte1, i24 * this.theImage.getStride(), i2 * i16);
/*      */ 
/* 1319 */               break;
/*      */             case 2:
/* 1321 */               ImageTools.convert(i2, 1, this.sourceType, this.passRow, 0, 0, arrayOfByte1, i24 * this.theImage.getStride(), this.theImage.getStride(), this.thePalette, 0, false);
/*      */ 
/* 1325 */               break;
/*      */             case 3:
/* 1327 */               this.scaler.putSourceScanline(this.passRow, 0);
/* 1328 */               break;
/*      */             case 4:
/* 1330 */               assert (this.passRow.length <= paramInt6 * i16);
/* 1331 */               ImageTools.convert(i2, 1, this.sourceType, this.passRow, 0, 0, this.convertedBuf, 0, 0, this.thePalette, 0, false);
/*      */ 
/* 1335 */               this.scaler.putSourceScanline(this.convertedBuf, 0);
/* 1336 */               break;
/*      */             default:
/* 1338 */               if ($assertionsDisabled) continue; throw new AssertionError();
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1345 */             int i25 = i13;
/* 1346 */             int i26 = i24 * i19 + n * i16;
/* 1347 */             int i27 = (i3 - 1) * i16;
/*      */             int i28;
/*      */             int i29;
/*      */             int i30;
/* 1348 */             if (i15 != 0)
/*      */             {
/*      */               int i31;
/* 1349 */               if (i5 == 16) {
/* 1350 */                 i28 = n;
/*      */ 
/* 1352 */                 for (; i28 < n + i2; 
/* 1352 */                   i28 += i3) {
/* 1353 */                   i29 = 0; for (i30 = i25 * i6; i29 < i6; i29++) {
/* 1354 */                     i31 = this.shortData[(i30++)] & 0xFFFF;
/* 1355 */                     this.rescaledBuf[(i26++)] = ((byte)localObject2[i29][i31]);
/*      */                   }
/* 1357 */                   i26 += i27;
/* 1358 */                   i25 += i14;
/*      */                 }
/*      */               } else {
/* 1361 */                 i28 = n;
/*      */ 
/* 1363 */                 for (; i28 < n + i2; 
/* 1363 */                   i28 += i8 * i6) {
/* 1364 */                   i29 = 0; for (i30 = i25 * i6; i29 < i6; i29++) {
/* 1365 */                     i31 = localObject1[(i30++)];
/* 1366 */                     for (int i32 = 0; i32 < i8; i32++)
/*      */                     {
/* 1368 */                       int i33 = i31 >> (i8 - 1 - i32) * i5 & 0xFF;
/* 1369 */                       int i34 = i33 & i9;
/* 1370 */                       if (i26 < i2)
/* 1371 */                         this.rescaledBuf[(i26++)] = ((byte)(localObject2[i29][i34] & i9));
/*      */                     }
/*      */                   }
/* 1374 */                   i25 += i14;
/*      */                 }
/*      */               }
/* 1377 */             } else if (i15 == 0) {
/* 1378 */               if (i5 == 16) {
/* 1379 */                 i28 = n;
/*      */ 
/* 1381 */                 for (; i28 < n + i2; 
/* 1381 */                   i28 += i3) {
/* 1382 */                   i29 = 0; for (i30 = i25 * i6; i29 < i6; i29++) {
/* 1383 */                     this.rescaledBuf[(i26++)] = ((byte)((this.shortData[(i30++)] & 0xFFFF) >> 8));
/*      */                   }
/* 1385 */                   i26 += i27;
/* 1386 */                   i25 += i14;
/*      */                 }
/*      */               } else {
/* 1389 */                 i28 = n;
/*      */ 
/* 1391 */                 for (; i28 < n + i2; 
/* 1391 */                   i28 += i3) {
/* 1392 */                   i29 = 0; for (i30 = i25 * i6; i29 < i6; i29++) {
/* 1393 */                     this.rescaledBuf[(i26++)] = localObject1[(i30++)];
/*      */                   }
/* 1395 */                   i26 += i27;
/* 1396 */                   i25 += i14;
/*      */                 }
/*      */               }
/*      */             }
/*      */ 
/* 1401 */             if (!this.isInterlaced)
/* 1402 */               switch (1.$SwitchMap$com$sun$javafx$iio$png$PNGImageLoader$ROW_PROCESS[this.rowProc.ordinal()])
/*      */               {
/*      */               case 1:
/* 1405 */                 break;
/*      */               case 2:
/* 1407 */                 ImageTools.convert(i2, 1, this.sourceType, this.rescaledBuf, n * i16, i19, arrayOfByte1, i24 * this.theImage.getStride() + n * i16, this.theImage.getStride(), this.thePalette, 0, false);
/*      */ 
/* 1411 */                 break;
/*      */               case 3:
/* 1413 */                 this.scaler.putSourceScanline(this.rescaledBuf, 0);
/* 1414 */                 break;
/*      */               case 4:
/* 1416 */                 ImageTools.convert(i2, 1, this.sourceType, this.rescaledBuf, n * i16, i19, this.convertedBuf, n * i16, 0, this.thePalette, 0, false);
/*      */ 
/* 1420 */                 this.scaler.putSourceScanline(this.convertedBuf, 0);
/* 1421 */                 break;
/*      */               default:
/* 1423 */                 if (!$assertionsDisabled) throw new AssertionError();
/*      */                 break;
/*      */               }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void decodeImage(int paramInt1, int paramInt2, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1447 */     int i = this.metadata.IHDR_width;
/* 1448 */     int j = this.metadata.IHDR_height;
/*      */ 
/* 1450 */     this.pixelsDone = 0;
/* 1451 */     this.totalPixels = (i * j);
/*      */ 
/* 1456 */     updateImageProgress(0);
/*      */ 
/* 1458 */     if (this.metadata.IHDR_interlaceMethod == 0) {
/* 1459 */       decodePass(0, 0, 0, 1, 1, i, j);
/*      */     }
/*      */     else
/*      */     {
/*      */       int n;
/*      */       int i1;
/*      */       int i3;
/* 1461 */       for (int k = 0; k <= this.sourceMaxProgressivePass; k++) {
/* 1462 */         int m = adam7XOffset[k];
/* 1463 */         n = adam7YOffset[k];
/* 1464 */         i1 = adam7XSubsampling[k];
/* 1465 */         int i2 = adam7YSubsampling[k];
/* 1466 */         i3 = adam7XSubsampling[(k + 1)] - 1;
/* 1467 */         int i4 = adam7YSubsampling[(k + 1)] - 1;
/*      */ 
/* 1469 */         if (k >= this.sourceMinProgressivePass) {
/* 1470 */           decodePass(k, m, n, i1, i2, (i + i3) / i1, (j + i4) / i2);
/*      */         }
/*      */         else
/*      */         {
/* 1478 */           skipPass((i + i3) / i1, (j + i4) / i2);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1490 */       if (this.isScaling)
/*      */       {
/* 1492 */         ByteBuffer localByteBuffer = (ByteBuffer)this.theImage.getImageData();
/* 1493 */         byte[] arrayOfByte1 = localByteBuffer.array();
/* 1494 */         n = this.theImage.getStride();
/* 1495 */         i1 = 0;
/* 1496 */         byte[] arrayOfByte2 = new byte[i * this.numDestBands];
/* 1497 */         for (i3 = 0; i3 < j; i3++) {
/* 1498 */           ImageTools.convert(i, 1, this.sourceType, arrayOfByte1, i1, n, arrayOfByte2, 0, 0, this.thePalette, 0, false);
/*      */ 
/* 1501 */           if (this.scaler.putSourceScanline(arrayOfByte2, 0)) {
/*      */             break;
/*      */           }
/* 1504 */           i1 += n;
/*      */         }
/*      */ 
/*      */       }
/* 1509 */       else if (this.isConverting) {
/* 1510 */         this.theImage = ImageTools.convertImageFrame(this.theImage);
/*      */       }
/*      */     }
/*      */ 
/* 1514 */     if (this.isScaling)
/* 1515 */       this.theImage = new ImageFrame(this.destType, this.scaler.getDestination(), paramInt1, paramInt2, paramInt1 * this.numDestBands, (byte[][])null, null);
/*      */   }
/*      */ 
/*      */   private synchronized void readImage(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*      */     throws IOException
/*      */   {
/* 1523 */     if (this.theImage != null) {
/* 1524 */       return;
/*      */     }
/*      */ 
/* 1527 */     readMetadata();
/*      */ 
/* 1529 */     int i = this.metadata.IHDR_width;
/* 1530 */     int j = this.metadata.IHDR_height;
/*      */ 
/* 1533 */     int[] arrayOfInt = ImageTools.computeDimensions(i, j, paramInt1, paramInt2, paramBoolean1);
/* 1534 */     paramInt1 = arrayOfInt[0];
/* 1535 */     paramInt2 = arrayOfInt[1];
/*      */ 
/* 1538 */     this.destType = ImageTools.getConvertedType(this.sourceType);
/* 1539 */     this.numDestBands = ImageStorage.getNumBands(this.destType);
/*      */ 
/* 1542 */     this.isConverting = (this.destType != this.sourceType);
/* 1543 */     this.isScaling = ((paramInt1 != i) || (paramInt2 != j));
/*      */ 
/* 1546 */     if (((!this.isConverting) && (!this.isScaling)) || ((this.isInterlaced) && ((this.isConverting) || (this.isScaling))))
/*      */     {
/* 1553 */       this.rowProc = ROW_PROCESS.COPY;
/* 1554 */     } else if ((this.isConverting) && (!this.isScaling))
/*      */     {
/* 1556 */       this.rowProc = ROW_PROCESS.CONVERT;
/*      */     } else {
/* 1558 */       assert (!this.isInterlaced);
/* 1559 */       if ((!this.isConverting) && (this.isScaling))
/*      */       {
/* 1561 */         this.rowProc = ROW_PROCESS.DOWNSCALE;
/*      */       }
/*      */       else {
/* 1564 */         this.rowProc = ROW_PROCESS.CONVERT_DOWNSCALE;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1569 */     this.sourceXSubsampling = 1;
/* 1570 */     this.sourceYSubsampling = 1;
/* 1571 */     this.sourceMinProgressivePass = 0;
/* 1572 */     this.sourceMaxProgressivePass = 6;
/*      */ 
/* 1591 */     Inflater localInflater = null;
/*      */     try
/*      */     {
/* 1609 */       localInflater = new Inflater();
/* 1610 */       Object localObject1 = new InflaterInputStream(this.IDATStream, localInflater);
/* 1611 */       localObject1 = new BufferedInputStream((InputStream)localObject1);
/* 1612 */       this.pixelStream = new DataInputStream((InputStream)localObject1);
/*      */ 
/* 1614 */       this.scaler = ScalerFactory.createScaler(i, j, ImageStorage.getNumBands(this.destType), paramInt1, paramInt2, paramBoolean2);
/*      */ 
/* 1618 */       localObject2 = this.metadata.gAMA_present ? Float.valueOf(this.metadata.gAMA_gamma) : null;
/* 1619 */       Integer localInteger1 = null;
/* 1620 */       Integer localInteger2 = null;
/*      */ 
/* 1622 */       if (this.metadata.bKGD_present) {
/* 1623 */         if (this.metadata.IHDR_colorType == 3) {
/* 1624 */           localInteger1 = Integer.valueOf(this.metadata.bKGD_index);
/* 1625 */           this.metadata.bKGD_colorType = 3;
/* 1626 */         } else if ((this.metadata.IHDR_colorType == 0) || (this.metadata.IHDR_colorType == 4))
/*      */         {
/* 1628 */           int k = this.metadata.bKGD_gray & 0xFF;
/* 1629 */           localInteger2 = new Integer(k << 16 | k << 8 | k | 0xFF000000);
/*      */         }
/*      */         else {
/* 1632 */           localInteger2 = new Integer((this.metadata.bKGD_red & 0xFF) << 16 | (this.metadata.bKGD_green & 0xFF) << 8 | this.metadata.bKGD_blue & 0xFF | 0xFF000000);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1639 */       ImageMetadata localImageMetadata = new ImageMetadata((Float)localObject2, Boolean.valueOf(true), localInteger1, localInteger2, null, null, Integer.valueOf(this.metadata.IHDR_width), Integer.valueOf(this.metadata.IHDR_height), null, null, null);
/*      */ 
/* 1643 */       updateImageMetadata(localImageMetadata);
/*      */       int m;
/* 1645 */       if (this.rowProc == ROW_PROCESS.COPY) {
/* 1646 */         m = ImageStorage.getNumBands(this.sourceType);
/* 1647 */         int n = i * m;
/* 1648 */         this.theImage = new ImageFrame(this.sourceType, ByteBuffer.wrap(new byte[j * n]), i, j, n, this.thePalette, localImageMetadata);
/*      */       }
/*      */       else
/*      */       {
/* 1652 */         m = paramInt1 * this.numDestBands;
/* 1653 */         this.theImage = new ImageFrame(this.destType, ByteBuffer.wrap(new byte[paramInt2 * m]), paramInt1, paramInt2, m, this.thePalette, localImageMetadata);
/*      */       }
/*      */ 
/* 1675 */       decodeImage(paramInt1, paramInt2, paramBoolean2);
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/* 1682 */       Object localObject2 = new IOException("Error reading PNG image data");
/* 1683 */       ((IOException)localObject2).initCause(localIOException);
/* 1684 */       throw ((Throwable)localObject2);
/*      */     } finally {
/* 1686 */       if (localInflater != null)
/* 1687 */         localInflater.end();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static enum ROW_PROCESS
/*      */   {
/*  120 */     COPY, 
/*      */ 
/*  124 */     CONVERT, 
/*      */ 
/*  128 */     DOWNSCALE, 
/*      */ 
/*  132 */     CONVERT_DOWNSCALE;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.png.PNGImageLoader
 * JD-Core Version:    0.6.2
 */