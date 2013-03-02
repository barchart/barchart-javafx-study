/*      */ package com.sun.t2k;
/*      */ 
/*      */ import com.sun.javafx.font.FontResource;
/*      */ import com.sun.javafx.font.FontStrike;
/*      */ import com.sun.javafx.font.PGFont;
/*      */ import com.sun.javafx.geom.Path2D;
/*      */ import com.sun.javafx.geom.RectBounds;
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ import com.sun.javafx.runtime.NativeLibLoader;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ 
/*      */ public class T2KFontFile
/*      */   implements FontResource, FontConstants
/*      */ {
/*      */   private static final int TRUETYPE_FONT = 1;
/*   46 */   Map<T2KFontStrikeDesc, WeakReference<T2KFontStrike>> strikeMap = new ConcurrentHashMap();
/*      */ 
/*   49 */   private long pScaler = 0L;
/*   50 */   private boolean scalerInited = false;
/*   51 */   private int fontInstallationType = -1;
/*      */   private String familyName;
/*      */   private String fullName;
/*      */   private String psName;
/*      */   private String localeFamilyName;
/*      */   private String localeFullName;
/*      */   private String styleName;
/*      */   private String localeStyleName;
/*      */   private String filename;
/*      */   private int filesize;
/*      */   private FontFileReader filereader;
/*   85 */   int numGlyphs = -1;
/*      */   private int fontIndex;
/*      */   private boolean isCFF;
/*   88 */   private boolean isCopy = false;
/*   89 */   private boolean isTracked = false;
/*   90 */   private boolean isDecoded = false;
/*   91 */   private boolean isRegistered = true;
/*      */ 
/*   94 */   private static HashMap<String, T2KFontFile> fileNameToT2KFontResourceMap = new HashMap();
/*      */   private Object peer;
/*  511 */   int directoryCount = 1;
/*      */   int numTables;
/*      */   DirectoryEntry[] tableDirectory;
/*      */   private static final int fsSelectionItalicBit = 1;
/*      */   private static final int fsSelectionBoldBit = 32;
/*      */   private static final int MACSTYLE_BOLD_BIT = 1;
/*      */   private static final int MACSTYLE_ITALIC_BIT = 2;
/*      */   private boolean isBold;
/*      */   private boolean isItalic;
/*      */   private float upem;
/*      */   private float ascent;
/*      */   private float descent;
/*      */   private float linegap;
/*      */   private float xHeight;
/*      */   private int numHMetrics;
/*      */   public static final int MAC_PLATFORM_ID = 1;
/*      */   public static final int MACROMAN_SPECIFIC_ID = 0;
/*      */   public static final int MACROMAN_ENGLISH_LANG = 0;
/*      */   public static final int MS_PLATFORM_ID = 3;
/*      */   public static final short MS_ENGLISH_LOCALE_ID = 1033;
/*      */   public static final int FAMILY_NAME_ID = 1;
/*      */   public static final int STYLE_NAME_ID = 2;
/*      */   public static final int FULL_NAME_ID = 4;
/*      */   public static final int PS_NAME_ID = 6;
/*      */   private static Map<String, Short> lcidMap;
/* 1154 */   static short nameLocaleID = getSystemLCID();
/*      */ 
/* 1164 */   private OpenTypeGlyphMapper mapper = null;
/*      */ 
/* 1213 */   char[] advanceWidths = null;
/*      */ 
/* 1290 */   int[][] bbArr = (int[][])null;
/* 1291 */   static final int[] EMPTY_BOUNDS = new int[4];
/*      */   private float[] styleMetrics;
/*      */ 
/*      */   static T2KFontFile createFontResource(String paramString, int paramInt)
/*      */   {
/*  100 */     return createFontResource(paramString, paramInt, true, false, false);
/*      */   }
/*      */ 
/*      */   static T2KFontFile createFontResource(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*      */   {
/*  106 */     String str = (paramString + paramInt).toLowerCase();
/*  107 */     T2KFontFile localT2KFontFile = (T2KFontFile)fileNameToT2KFontResourceMap.get(str);
/*  108 */     if (localT2KFontFile != null) {
/*  109 */       return localT2KFontFile;
/*      */     }
/*      */     try
/*      */     {
/*  113 */       localT2KFontFile = new T2KFontFile(null, paramString, paramInt, paramBoolean1, paramBoolean2, paramBoolean3);
/*  114 */       if (paramBoolean1) {
/*  115 */         T2KFontFactory.storeInMap(localT2KFontFile.getFullName(), localT2KFontFile);
/*  116 */         fileNameToT2KFontResourceMap.put(str, localT2KFontFile);
/*      */       }
/*  118 */       return localT2KFontFile;
/*      */     } catch (Exception localException) {
/*  120 */       if (T2KFontFactory.debugFonts)
/*  121 */         localException.printStackTrace();
/*      */     }
/*  123 */     return null;
/*      */   }
/*      */ 
/*      */   static T2KFontFile createFontResource(String paramString1, String paramString2)
/*      */   {
/*  128 */     return createFontResource(paramString1, paramString2, true, false, false);
/*      */   }
/*      */ 
/*      */   static T2KFontFile createFontResource(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*      */   {
/*  134 */     if (paramString2 == null) {
/*  135 */       return null;
/*      */     }
/*  137 */     String str1 = paramString2.toLowerCase();
/*  138 */     if (str1.endsWith(".ttc")) { int i = 0;
/*  140 */       Object localObject = null;
/*      */       T2KFontFile localT2KFontFile;
/*      */       label170: 
/*      */       do { String str2 = (paramString2 + i).toLowerCase();
/*      */         try {
/*  144 */           localT2KFontFile = (T2KFontFile)fileNameToT2KFontResourceMap.get(str2);
/*  145 */           if (localT2KFontFile != null) {
/*  146 */             if (paramString1.equals(localT2KFontFile.getFullName())) {
/*  147 */               return localT2KFontFile;
/*      */             }
/*      */ 
/*  151 */             break label170;
/*      */           }
/*      */ 
/*  154 */           localT2KFontFile = new T2KFontFile(paramString1, paramString2, i, paramBoolean1, paramBoolean2, paramBoolean3);
/*      */         }
/*      */         catch (Exception localException)
/*      */         {
/*  158 */           if (T2KFontFactory.debugFonts) {
/*  159 */             localException.printStackTrace();
/*      */           }
/*  161 */           return null;
/*      */         }
/*      */ 
/*  164 */         String str3 = localT2KFontFile.getFullName();
/*  165 */         if (paramBoolean1) {
/*  166 */           T2KFontFactory.storeInMap(str3, localT2KFontFile);
/*  167 */           fileNameToT2KFontResourceMap.put(str2, localT2KFontFile);
/*      */         }
/*  169 */         if ((i == 0) || (paramString1.equals(str3))) {
/*  170 */           localObject = localT2KFontFile;
/*      */         }
/*  172 */         i++; } while (i < localT2KFontFile.getFontCount());
/*  173 */       return localObject;
/*      */     }
/*  175 */     return createFontResource(paramString2, 0, paramBoolean1, paramBoolean2, paramBoolean3);
/*      */   }
/*      */ 
/*      */   private T2KFontFile(String paramString1, String paramString2, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*      */     throws Exception
/*      */   {
/*  183 */     this.filename = paramString2;
/*  184 */     this.isRegistered = paramBoolean1;
/*  185 */     this.isCopy = paramBoolean2;
/*  186 */     this.isTracked = paramBoolean3;
/*  187 */     init(paramString1, paramInt);
/*      */   }
/*      */ 
/*      */   private synchronized void initScaler() {
/*  191 */     if ((this.pScaler == 0L) && (!this.scalerInited)) {
/*  192 */       int i = 1;
/*  193 */       byte[] arrayOfByte = this.filename.getBytes();
/*  194 */       this.pScaler = initNativeScaler(i, this.fontIndex, false, this.filename, arrayOfByte, this.filesize, null);
/*      */ 
/*  199 */       this.scalerInited = true;
/*      */ 
/*  201 */       if (!isRegistered())
/*      */       {
/*  205 */         ScalerDisposer localScalerDisposer = new ScalerDisposer(this.pScaler);
/*  206 */         Disposer.addRecord(this, localScalerDisposer);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   WeakReference<T2KFontFile> createFileDisposer(T2KFontFactory paramT2KFontFactory) {
/*  212 */     FileDisposer localFileDisposer = new FileDisposer(this.filename, this.isTracked);
/*  213 */     WeakReference localWeakReference = Disposer.addRecord(this, localFileDisposer);
/*  214 */     localFileDisposer.setFactory(paramT2KFontFactory, localWeakReference);
/*  215 */     return localWeakReference;
/*      */   }
/*      */ 
/*      */   void setIsDecoded(boolean paramBoolean) {
/*  219 */     this.isDecoded = paramBoolean;
/*      */   }
/*      */ 
/*      */   synchronized void disposeOnShutdown()
/*      */   {
/*  225 */     if (this.pScaler != 0L)
/*      */     {
/*  229 */       disposeNativeScaler(this.pScaler);
/*  230 */       if (T2KFontFactory.debugFonts) {
/*  231 */         System.err.println("pScaler freed: " + this.pScaler);
/*      */       }
/*  233 */       this.pScaler = 0L;
/*      */     }
/*  235 */     if ((this.isCopy) || (this.isDecoded)) {
/*  236 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Void run() {
/*      */           try {
/*  240 */             new File(T2KFontFile.this.filename).delete();
/*      */ 
/*  244 */             T2KFontFile.this.isCopy = T2KFontFile.access$202(T2KFontFile.this, false);
/*      */           } catch (Exception localException) {
/*      */           }
/*  247 */           return null;
/*      */         }
/*      */       });
/*  251 */       if (T2KFontFactory.debugFonts)
/*  252 */         System.err.println("Temp file deleted: " + this.filename);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getDefaultAAMode()
/*      */   {
/*  258 */     return 0;
/*      */   }
/*      */ 
/*      */   boolean isInstalledFont() {
/*  262 */     if (this.familyName.startsWith("Amble"))
/*  263 */       return false;
/*  264 */     if (this.fontInstallationType == -1) {
/*  265 */       this.fontInstallationType = (T2KFontFactory.isInstalledFont(this.filename) ? 1 : 0);
/*      */     }
/*      */ 
/*  268 */     return this.fontInstallationType > 0;
/*      */   }
/*      */ 
/*      */   public String getFileName()
/*      */   {
/*  357 */     return this.filename;
/*      */   }
/*      */ 
/*      */   public String getFullName() {
/*  361 */     return this.fullName;
/*      */   }
/*      */ 
/*      */   public String getPSName() {
/*  365 */     if (this.psName == null) {
/*  366 */       this.psName = this.fullName;
/*      */     }
/*  368 */     return this.psName;
/*      */   }
/*      */ 
/*      */   public String getFamilyName() {
/*  372 */     return this.familyName;
/*      */   }
/*      */ 
/*      */   public String getStyleName() {
/*  376 */     return this.styleName;
/*      */   }
/*      */ 
/*      */   public String getLocaleFullName() {
/*  380 */     return this.localeFullName;
/*      */   }
/*      */ 
/*      */   public String getLocaleFamilyName() {
/*  384 */     return this.localeFamilyName;
/*      */   }
/*      */ 
/*      */   public String getLocaleStyleName() {
/*  388 */     return this.localeStyleName;
/*      */   }
/*      */ 
/*      */   public int getNumGlyphs() {
/*  392 */     if (this.numGlyphs == -1) {
/*  393 */       FontFileReader.Buffer localBuffer = readTable(1835104368);
/*  394 */       this.numGlyphs = localBuffer.getChar(4);
/*      */     }
/*  396 */     return this.numGlyphs;
/*      */   }
/*      */ 
/*      */   public Object getPeer()
/*      */   {
/*  401 */     return this.peer;
/*      */   }
/*      */ 
/*      */   public void setPeer(Object paramObject) {
/*  405 */     this.peer = paramObject;
/*      */   }
/*      */ 
/*      */   static synchronized native void freePointer(long paramLong);
/*      */ 
/*      */   private static native void initNativeIDs();
/*      */ 
/*      */   private synchronized native long initNativeScaler(int paramInt1, int paramInt2, boolean paramBoolean, String paramString, byte[] paramArrayOfByte, int paramInt3, int[] paramArrayOfInt);
/*      */ 
/*      */   private static native void disposeNativeScaler(long paramLong);
/*      */ 
/*      */   synchronized long createScalerContext(double[] paramArrayOfDouble, int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2)
/*      */   {
/*  433 */     initScaler();
/*  434 */     return createScalerContext(this.pScaler, paramArrayOfDouble, paramInt1, paramInt2, paramBoolean, paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */   private synchronized native long createScalerContext(long paramLong, double[] paramArrayOfDouble, int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2);
/*      */ 
/*      */   synchronized Path2D getGlyphOutline(long paramLong, int paramInt)
/*      */   {
/*  448 */     return getGlyphOutline(this.pScaler, paramLong, paramInt);
/*      */   }
/*      */ 
/*      */   private native Path2D getGlyphOutline(long paramLong1, long paramLong2, int paramInt);
/*      */ 
/*      */   synchronized RectBounds getGlyphBounds(long paramLong, int paramInt)
/*      */   {
/*  458 */     return getGlyphBounds(this.pScaler, paramLong, paramInt);
/*      */   }
/*      */ 
/*      */   private native RectBounds getGlyphBounds(long paramLong1, long paramLong2, int paramInt);
/*      */ 
/*      */   synchronized float[] getGlyphMetrics(long paramLong, int paramInt)
/*      */   {
/*  469 */     return getGlyphMetrics(this.pScaler, paramLong, paramInt);
/*      */   }
/*      */ 
/*      */   private native float[] getGlyphMetrics(long paramLong1, long paramLong2, int paramInt);
/*      */ 
/*      */   synchronized long getGlyphImage(long paramLong, int paramInt)
/*      */   {
/*  479 */     return getGlyphImage(this.pScaler, paramLong, paramInt);
/*      */   }
/*      */ 
/*      */   private native long getGlyphImage(long paramLong1, long paramLong2, int paramInt);
/*      */ 
/*      */   synchronized FontFileReader.Buffer readTable(int paramInt)
/*      */   {
/*  488 */     FontFileReader.Buffer localBuffer = null;
/*  489 */     boolean bool = false;
/*      */     try {
/*  491 */       bool = this.filereader.openFile();
/*  492 */       DirectoryEntry localDirectoryEntry = getDirectoryEntry(paramInt);
/*  493 */       if (localDirectoryEntry != null)
/*  494 */         localBuffer = this.filereader.readBlock(localDirectoryEntry.offset, localDirectoryEntry.length);
/*      */     }
/*      */     catch (Exception localException2) {
/*  497 */       if (T2KFontFactory.debugFonts)
/*  498 */         localException2.printStackTrace();
/*      */     }
/*      */     finally {
/*  501 */       if (bool)
/*      */         try {
/*  503 */           this.filereader.closeFile();
/*      */         }
/*      */         catch (Exception localException4) {
/*      */         }
/*      */     }
/*  508 */     return localBuffer;
/*      */   }
/*      */ 
/*      */   public int getFontCount()
/*      */   {
/*  517 */     return this.directoryCount;
/*      */   }
/*      */ 
/*      */   DirectoryEntry getDirectoryEntry(int paramInt)
/*      */   {
/*  529 */     for (int i = 0; i < this.numTables; i++) {
/*  530 */       if (this.tableDirectory[i].tag == paramInt) {
/*  531 */         return this.tableDirectory[i];
/*      */       }
/*      */     }
/*  534 */     return null;
/*      */   }
/*      */ 
/*      */   private final void init(String paramString, int paramInt)
/*      */     throws Exception
/*      */   {
/*  543 */     this.filereader = new FontFileReader(this.filename);
/*  544 */     WoffDecoder localWoffDecoder = null;
/*      */     try {
/*  546 */       if (!this.filereader.openFile()) {
/*  547 */         throw new FileNotFoundException("Unable to create FontResource for file " + this.filename);
/*      */       }
/*      */ 
/*  550 */       FontFileReader.Buffer localBuffer1 = this.filereader.readBlock(0, 12);
/*  551 */       int i = localBuffer1.getInt();
/*      */ 
/*  554 */       if (i == 2001684038) {
/*  555 */         localWoffDecoder = new WoffDecoder();
/*  556 */         File localFile = localWoffDecoder.openFile();
/*  557 */         localWoffDecoder.decode(this.filereader);
/*  558 */         localWoffDecoder.closeFile();
/*      */ 
/*  561 */         this.filereader.closeFile();
/*  562 */         this.filereader = new FontFileReader(localFile.getPath());
/*  563 */         if (!this.filereader.openFile()) {
/*  564 */           throw new FileNotFoundException("Unable to create FontResource for file " + this.filename);
/*      */         }
/*      */ 
/*  567 */         localBuffer1 = this.filereader.readBlock(0, 12);
/*  568 */         i = localBuffer1.getInt();
/*      */       }
/*      */ 
/*  571 */       this.filesize = ((int)this.filereader.getLength());
/*  572 */       int j = 0;
/*  573 */       switch (i) {
/*      */       case 1953784678:
/*  575 */         localBuffer1.getInt();
/*  576 */         this.directoryCount = localBuffer1.getInt();
/*  577 */         if (paramInt >= this.directoryCount) {
/*  578 */           throw new Exception("Bad collection index");
/*      */         }
/*  580 */         this.fontIndex = paramInt;
/*  581 */         localBuffer1 = this.filereader.readBlock(12 + 4 * paramInt, 4);
/*  582 */         j = localBuffer1.getInt();
/*  583 */         break;
/*      */       case 65536:
/*      */       case 1953658213:
/*  587 */         break;
/*      */       case 1330926671:
/*  590 */         this.isCFF = true;
/*  591 */         break;
/*      */       default:
/*  594 */         throw new Exception("Unsupported sfnt " + this.filename);
/*      */       }
/*      */ 
/*  603 */       localBuffer1 = this.filereader.readBlock(j + 4, 2);
/*  604 */       this.numTables = localBuffer1.getShort();
/*  605 */       int k = j + 12;
/*  606 */       FontFileReader.Buffer localBuffer2 = this.filereader.readBlock(k, this.numTables * 16);
/*      */ 
/*  609 */       this.tableDirectory = new DirectoryEntry[this.numTables];
/*  610 */       for (int m = 0; m < this.numTables; m++)
/*      */       {
/*      */         void tmp441_438 = new DirectoryEntry(); DirectoryEntry localDirectoryEntry1 = tmp441_438; this.tableDirectory[m] = tmp441_438;
/*  612 */         localDirectoryEntry1.tag = localBuffer2.getInt();
/*  613 */         localBuffer2.skip(4);
/*  614 */         localDirectoryEntry1.offset = localBuffer2.getInt();
/*  615 */         localDirectoryEntry1.length = localBuffer2.getInt();
/*  616 */         if (localDirectoryEntry1.offset + localDirectoryEntry1.length > this.filesize) {
/*  617 */           throw new Exception("bad table, tag=" + localDirectoryEntry1.tag);
/*      */         }
/*      */       }
/*      */ 
/*  621 */       DirectoryEntry localDirectoryEntry2 = getDirectoryEntry(1751474532);
/*  622 */       FontFileReader.Buffer localBuffer3 = this.filereader.readBlock(localDirectoryEntry2.offset, localDirectoryEntry2.length);
/*      */ 
/*  625 */       this.upem = (localBuffer3.getShort(18) & 0xFFFF);
/*      */ 
/*  629 */       FontFileReader.Buffer localBuffer4 = readTable(1751672161);
/*  630 */       if (localBuffer4 == null) {
/*  631 */         this.numHMetrics = -1;
/*      */       }
/*      */       else
/*      */       {
/*  635 */         this.ascent = (-localBuffer4.getShort(4));
/*  636 */         this.descent = (-localBuffer4.getShort(6));
/*  637 */         this.linegap = localBuffer4.getShort(8);
/*      */ 
/*  641 */         this.numHMetrics = (localBuffer4.getChar(34) & 0xFFFF);
/*      */       }
/*      */ 
/*  647 */       getNumGlyphs();
/*      */ 
/*  649 */       setStyle();
/*      */ 
/*  656 */       initNames();
/*      */ 
/*  658 */       if ((this.familyName == null) || (this.fullName == null)) {
/*  659 */         String str = paramString != null ? paramString : "";
/*  660 */         if (this.fullName == null) {
/*  661 */           this.fullName = (this.familyName != null ? this.familyName : str);
/*      */         }
/*  663 */         if (this.familyName == null) {
/*  664 */           this.familyName = (this.fullName != null ? this.fullName : str);
/*      */         }
/*  666 */         throw new Exception("Font name not found.");
/*      */       }
/*      */ 
/*  672 */       if (localWoffDecoder != null) {
/*  673 */         this.isDecoded = true;
/*  674 */         this.filename = this.filereader.getFilename();
/*  675 */         T2KFontFactory.getFontFactory().addDecodedFont(this);
/*      */       }
/*      */     } catch (Exception localException) {
/*  678 */       if (localWoffDecoder != null) {
/*  679 */         localWoffDecoder.deleteFile();
/*      */       }
/*  681 */       throw localException;
/*      */     } finally {
/*  683 */       this.filereader.closeFile();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setStyle()
/*      */   {
/*  709 */     DirectoryEntry localDirectoryEntry = getDirectoryEntry(1330851634);
/*      */     Object localObject;
/*  710 */     if (localDirectoryEntry != null)
/*      */     {
/*  750 */       localObject = this.filereader.readBlock(localDirectoryEntry.offset, localDirectoryEntry.length);
/*      */ 
/*  752 */       int i = ((FontFileReader.Buffer)localObject).getChar(62) & 0xFFFF;
/*  753 */       this.isItalic = ((i & 0x1) != 0);
/*  754 */       this.isBold = ((i & 0x20) != 0);
/*      */ 
/*  759 */       this.xHeight = 0.0F;
/*  760 */       if (localDirectoryEntry.length >= 88)
/*  761 */         this.xHeight = ((((FontFileReader.Buffer)localObject).getChar(86) & 0xFFFF) / this.upem);
/*      */     }
/*      */     else {
/*  764 */       localObject = getDirectoryEntry(1751474532);
/*  765 */       FontFileReader.Buffer localBuffer = this.filereader.readBlock(((DirectoryEntry)localObject).offset, ((DirectoryEntry)localObject).length);
/*      */ 
/*  767 */       int j = localBuffer.getShort(44);
/*  768 */       this.isItalic = ((j & 0x2) != 0);
/*  769 */       this.isBold = ((j & 0x1) != 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isBold() {
/*  774 */     return this.isBold;
/*      */   }
/*      */ 
/*      */   public boolean isItalic() {
/*  778 */     return this.isItalic;
/*      */   }
/*      */ 
/*      */   public boolean isDecoded() {
/*  782 */     return this.isDecoded;
/*      */   }
/*      */ 
/*      */   public boolean isRegistered() {
/*  786 */     return this.isRegistered;
/*      */   }
/*      */ 
/*      */   void initNames()
/*      */     throws Exception
/*      */   {
/*  803 */     byte[] arrayOfByte = new byte[256];
/*      */ 
/*  805 */     DirectoryEntry localDirectoryEntry = getDirectoryEntry(1851878757);
/*  806 */     FontFileReader.Buffer localBuffer = this.filereader.readBlock(localDirectoryEntry.offset, localDirectoryEntry.length);
/*      */ 
/*  808 */     localBuffer.skip(2);
/*  809 */     int i = localBuffer.getShort();
/*      */ 
/*  815 */     int j = localBuffer.getShort() & 0xFFFF;
/*      */ 
/*  821 */     for (int k = 0; k < i; k++) {
/*  822 */       int m = localBuffer.getShort();
/*  823 */       if ((m != 3) && (m != 1))
/*      */       {
/*  825 */         localBuffer.skip(10);
/*      */       }
/*      */       else {
/*  828 */         int n = localBuffer.getShort();
/*      */ 
/*  831 */         if (((m == 3) && (n > 1)) || ((m == 1) && (n != 0)))
/*      */         {
/*  834 */           localBuffer.skip(8);
/*      */         }
/*      */         else {
/*  837 */           int i1 = localBuffer.getShort();
/*  838 */           if ((m == 1) && (i1 != 0))
/*      */           {
/*  840 */             localBuffer.skip(6);
/*      */           }
/*      */           else {
/*  843 */             int i2 = localBuffer.getShort();
/*  844 */             int i3 = localBuffer.getShort() & 0xFFFF;
/*  845 */             int i4 = (localBuffer.getShort() & 0xFFFF) + j;
/*  846 */             String str1 = null;
/*      */             String str2;
/*  848 */             switch (i2)
/*      */             {
/*      */             case 1:
/*  852 */               if ((this.familyName == null) || (i1 == 1033) || (i1 == nameLocaleID))
/*      */               {
/*  855 */                 localBuffer.get(i4, arrayOfByte, 0, i3);
/*  856 */                 if (m == 1)
/*  857 */                   str2 = "US-ASCII";
/*      */                 else {
/*  859 */                   str2 = "UTF-16BE";
/*      */                 }
/*  861 */                 str1 = new String(arrayOfByte, 0, i3, str2);
/*      */ 
/*  863 */                 if ((this.familyName == null) || (i1 == 1033))
/*      */                 {
/*  865 */                   this.familyName = str1;
/*      */                 }
/*  867 */                 if (i1 == nameLocaleID)
/*  868 */                   this.localeFamilyName = str1;  } break;
/*      */             case 4:
/*  875 */               if ((this.fullName == null) || (i1 == 1033) || (i1 == nameLocaleID))
/*      */               {
/*  879 */                 localBuffer.get(i4, arrayOfByte, 0, i3);
/*  880 */                 if (m == 1)
/*  881 */                   str2 = "US-ASCII";
/*      */                 else {
/*  883 */                   str2 = "UTF-16BE";
/*      */                 }
/*  885 */                 str1 = new String(arrayOfByte, 0, i3, str2);
/*      */ 
/*  887 */                 if ((this.fullName == null) || (i1 == 1033))
/*      */                 {
/*  889 */                   this.fullName = str1;
/*      */                 }
/*  891 */                 if (i1 == nameLocaleID)
/*  892 */                   this.localeFullName = str1;  } break;
/*      */             case 6:
/*  899 */               if (this.psName == null) {
/*  900 */                 localBuffer.get(i4, arrayOfByte, 0, i3);
/*  901 */                 if (m == 1)
/*  902 */                   str2 = "US-ASCII";
/*      */                 else {
/*  904 */                   str2 = "UTF-16BE";
/*      */                 }
/*  906 */                 this.psName = new String(arrayOfByte, 0, i3, str2); } break;
/*      */             case 2:
/*  912 */               if ((this.styleName == null) || (i1 == 1033) || (i1 == nameLocaleID))
/*      */               {
/*  916 */                 localBuffer.get(i4, arrayOfByte, 0, i3);
/*  917 */                 if (m == 1)
/*  918 */                   str2 = "US-ASCII";
/*      */                 else {
/*  920 */                   str2 = "UTF-16BE";
/*      */                 }
/*  922 */                 str1 = new String(arrayOfByte, 0, i3, str2);
/*      */ 
/*  924 */                 if ((this.styleName == null) || (i1 == 1033))
/*      */                 {
/*  926 */                   this.styleName = str1;
/*      */                 }
/*  928 */                 if (i1 == nameLocaleID)
/*  929 */                   this.localeStyleName = str1;  } break;
/*      */             case 3:
/*      */             case 5:
/*      */             }
/*      */ 
/*  938 */             if (this.localeFamilyName == null) {
/*  939 */               this.localeFamilyName = this.familyName;
/*      */             }
/*  941 */             if (this.localeFullName == null) {
/*  942 */               this.localeFullName = this.fullName;
/*      */             }
/*  944 */             if (this.localeStyleName == null)
/*  945 */               this.localeStyleName = this.styleName;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void addLCIDMapEntry(Map<String, Short> paramMap, String paramString, short paramShort)
/*      */   {
/*  959 */     paramMap.put(paramString, Short.valueOf(paramShort));
/*      */   }
/*      */ 
/*      */   private static synchronized void createLCIDMap() {
/*  963 */     if (lcidMap != null) {
/*  964 */       return;
/*      */     }
/*      */ 
/*  967 */     HashMap localHashMap = new HashMap(200);
/*  968 */     addLCIDMapEntry(localHashMap, "ar", (short)1025);
/*  969 */     addLCIDMapEntry(localHashMap, "bg", (short)1026);
/*  970 */     addLCIDMapEntry(localHashMap, "ca", (short)1027);
/*  971 */     addLCIDMapEntry(localHashMap, "zh", (short)1028);
/*  972 */     addLCIDMapEntry(localHashMap, "cs", (short)1029);
/*  973 */     addLCIDMapEntry(localHashMap, "da", (short)1030);
/*  974 */     addLCIDMapEntry(localHashMap, "de", (short)1031);
/*  975 */     addLCIDMapEntry(localHashMap, "el", (short)1032);
/*  976 */     addLCIDMapEntry(localHashMap, "es", (short)1034);
/*  977 */     addLCIDMapEntry(localHashMap, "fi", (short)1035);
/*  978 */     addLCIDMapEntry(localHashMap, "fr", (short)1036);
/*  979 */     addLCIDMapEntry(localHashMap, "iw", (short)1037);
/*  980 */     addLCIDMapEntry(localHashMap, "hu", (short)1038);
/*  981 */     addLCIDMapEntry(localHashMap, "is", (short)1039);
/*  982 */     addLCIDMapEntry(localHashMap, "it", (short)1040);
/*  983 */     addLCIDMapEntry(localHashMap, "ja", (short)1041);
/*  984 */     addLCIDMapEntry(localHashMap, "ko", (short)1042);
/*  985 */     addLCIDMapEntry(localHashMap, "nl", (short)1043);
/*  986 */     addLCIDMapEntry(localHashMap, "no", (short)1044);
/*  987 */     addLCIDMapEntry(localHashMap, "pl", (short)1045);
/*  988 */     addLCIDMapEntry(localHashMap, "pt", (short)1046);
/*  989 */     addLCIDMapEntry(localHashMap, "rm", (short)1047);
/*  990 */     addLCIDMapEntry(localHashMap, "ro", (short)1048);
/*  991 */     addLCIDMapEntry(localHashMap, "ru", (short)1049);
/*  992 */     addLCIDMapEntry(localHashMap, "hr", (short)1050);
/*  993 */     addLCIDMapEntry(localHashMap, "sk", (short)1051);
/*  994 */     addLCIDMapEntry(localHashMap, "sq", (short)1052);
/*  995 */     addLCIDMapEntry(localHashMap, "sv", (short)1053);
/*  996 */     addLCIDMapEntry(localHashMap, "th", (short)1054);
/*  997 */     addLCIDMapEntry(localHashMap, "tr", (short)1055);
/*  998 */     addLCIDMapEntry(localHashMap, "ur", (short)1056);
/*  999 */     addLCIDMapEntry(localHashMap, "in", (short)1057);
/* 1000 */     addLCIDMapEntry(localHashMap, "uk", (short)1058);
/* 1001 */     addLCIDMapEntry(localHashMap, "be", (short)1059);
/* 1002 */     addLCIDMapEntry(localHashMap, "sl", (short)1060);
/* 1003 */     addLCIDMapEntry(localHashMap, "et", (short)1061);
/* 1004 */     addLCIDMapEntry(localHashMap, "lv", (short)1062);
/* 1005 */     addLCIDMapEntry(localHashMap, "lt", (short)1063);
/* 1006 */     addLCIDMapEntry(localHashMap, "fa", (short)1065);
/* 1007 */     addLCIDMapEntry(localHashMap, "vi", (short)1066);
/* 1008 */     addLCIDMapEntry(localHashMap, "hy", (short)1067);
/* 1009 */     addLCIDMapEntry(localHashMap, "eu", (short)1069);
/* 1010 */     addLCIDMapEntry(localHashMap, "mk", (short)1071);
/* 1011 */     addLCIDMapEntry(localHashMap, "tn", (short)1074);
/* 1012 */     addLCIDMapEntry(localHashMap, "xh", (short)1076);
/* 1013 */     addLCIDMapEntry(localHashMap, "zu", (short)1077);
/* 1014 */     addLCIDMapEntry(localHashMap, "af", (short)1078);
/* 1015 */     addLCIDMapEntry(localHashMap, "ka", (short)1079);
/* 1016 */     addLCIDMapEntry(localHashMap, "fo", (short)1080);
/* 1017 */     addLCIDMapEntry(localHashMap, "hi", (short)1081);
/* 1018 */     addLCIDMapEntry(localHashMap, "mt", (short)1082);
/* 1019 */     addLCIDMapEntry(localHashMap, "se", (short)1083);
/* 1020 */     addLCIDMapEntry(localHashMap, "gd", (short)1084);
/* 1021 */     addLCIDMapEntry(localHashMap, "ms", (short)1086);
/* 1022 */     addLCIDMapEntry(localHashMap, "kk", (short)1087);
/* 1023 */     addLCIDMapEntry(localHashMap, "ky", (short)1088);
/* 1024 */     addLCIDMapEntry(localHashMap, "sw", (short)1089);
/* 1025 */     addLCIDMapEntry(localHashMap, "tt", (short)1092);
/* 1026 */     addLCIDMapEntry(localHashMap, "bn", (short)1093);
/* 1027 */     addLCIDMapEntry(localHashMap, "pa", (short)1094);
/* 1028 */     addLCIDMapEntry(localHashMap, "gu", (short)1095);
/* 1029 */     addLCIDMapEntry(localHashMap, "ta", (short)1097);
/* 1030 */     addLCIDMapEntry(localHashMap, "te", (short)1098);
/* 1031 */     addLCIDMapEntry(localHashMap, "kn", (short)1099);
/* 1032 */     addLCIDMapEntry(localHashMap, "ml", (short)1100);
/* 1033 */     addLCIDMapEntry(localHashMap, "mr", (short)1102);
/* 1034 */     addLCIDMapEntry(localHashMap, "sa", (short)1103);
/* 1035 */     addLCIDMapEntry(localHashMap, "mn", (short)1104);
/* 1036 */     addLCIDMapEntry(localHashMap, "cy", (short)1106);
/* 1037 */     addLCIDMapEntry(localHashMap, "gl", (short)1110);
/* 1038 */     addLCIDMapEntry(localHashMap, "dv", (short)1125);
/* 1039 */     addLCIDMapEntry(localHashMap, "qu", (short)1131);
/* 1040 */     addLCIDMapEntry(localHashMap, "mi", (short)1153);
/* 1041 */     addLCIDMapEntry(localHashMap, "ar_IQ", (short)2049);
/* 1042 */     addLCIDMapEntry(localHashMap, "zh_CN", (short)2052);
/* 1043 */     addLCIDMapEntry(localHashMap, "de_CH", (short)2055);
/* 1044 */     addLCIDMapEntry(localHashMap, "en_GB", (short)2057);
/* 1045 */     addLCIDMapEntry(localHashMap, "es_MX", (short)2058);
/* 1046 */     addLCIDMapEntry(localHashMap, "fr_BE", (short)2060);
/* 1047 */     addLCIDMapEntry(localHashMap, "it_CH", (short)2064);
/* 1048 */     addLCIDMapEntry(localHashMap, "nl_BE", (short)2067);
/* 1049 */     addLCIDMapEntry(localHashMap, "no_NO_NY", (short)2068);
/* 1050 */     addLCIDMapEntry(localHashMap, "pt_PT", (short)2070);
/* 1051 */     addLCIDMapEntry(localHashMap, "ro_MD", (short)2072);
/* 1052 */     addLCIDMapEntry(localHashMap, "ru_MD", (short)2073);
/* 1053 */     addLCIDMapEntry(localHashMap, "sr_CS", (short)2074);
/* 1054 */     addLCIDMapEntry(localHashMap, "sv_FI", (short)2077);
/* 1055 */     addLCIDMapEntry(localHashMap, "az_AZ", (short)2092);
/* 1056 */     addLCIDMapEntry(localHashMap, "se_SE", (short)2107);
/* 1057 */     addLCIDMapEntry(localHashMap, "ga_IE", (short)2108);
/* 1058 */     addLCIDMapEntry(localHashMap, "ms_BN", (short)2110);
/* 1059 */     addLCIDMapEntry(localHashMap, "uz_UZ", (short)2115);
/* 1060 */     addLCIDMapEntry(localHashMap, "qu_EC", (short)2155);
/* 1061 */     addLCIDMapEntry(localHashMap, "ar_EG", (short)3073);
/* 1062 */     addLCIDMapEntry(localHashMap, "zh_HK", (short)3076);
/* 1063 */     addLCIDMapEntry(localHashMap, "de_AT", (short)3079);
/* 1064 */     addLCIDMapEntry(localHashMap, "en_AU", (short)3081);
/* 1065 */     addLCIDMapEntry(localHashMap, "fr_CA", (short)3084);
/* 1066 */     addLCIDMapEntry(localHashMap, "sr_CS", (short)3098);
/* 1067 */     addLCIDMapEntry(localHashMap, "se_FI", (short)3131);
/* 1068 */     addLCIDMapEntry(localHashMap, "qu_PE", (short)3179);
/* 1069 */     addLCIDMapEntry(localHashMap, "ar_LY", (short)4097);
/* 1070 */     addLCIDMapEntry(localHashMap, "zh_SG", (short)4100);
/* 1071 */     addLCIDMapEntry(localHashMap, "de_LU", (short)4103);
/* 1072 */     addLCIDMapEntry(localHashMap, "en_CA", (short)4105);
/* 1073 */     addLCIDMapEntry(localHashMap, "es_GT", (short)4106);
/* 1074 */     addLCIDMapEntry(localHashMap, "fr_CH", (short)4108);
/* 1075 */     addLCIDMapEntry(localHashMap, "hr_BA", (short)4122);
/* 1076 */     addLCIDMapEntry(localHashMap, "ar_DZ", (short)5121);
/* 1077 */     addLCIDMapEntry(localHashMap, "zh_MO", (short)5124);
/* 1078 */     addLCIDMapEntry(localHashMap, "de_LI", (short)5127);
/* 1079 */     addLCIDMapEntry(localHashMap, "en_NZ", (short)5129);
/* 1080 */     addLCIDMapEntry(localHashMap, "es_CR", (short)5130);
/* 1081 */     addLCIDMapEntry(localHashMap, "fr_LU", (short)5132);
/* 1082 */     addLCIDMapEntry(localHashMap, "bs_BA", (short)5146);
/* 1083 */     addLCIDMapEntry(localHashMap, "ar_MA", (short)6145);
/* 1084 */     addLCIDMapEntry(localHashMap, "en_IE", (short)6153);
/* 1085 */     addLCIDMapEntry(localHashMap, "es_PA", (short)6154);
/* 1086 */     addLCIDMapEntry(localHashMap, "fr_MC", (short)6156);
/* 1087 */     addLCIDMapEntry(localHashMap, "sr_BA", (short)6170);
/* 1088 */     addLCIDMapEntry(localHashMap, "ar_TN", (short)7169);
/* 1089 */     addLCIDMapEntry(localHashMap, "en_ZA", (short)7177);
/* 1090 */     addLCIDMapEntry(localHashMap, "es_DO", (short)7178);
/* 1091 */     addLCIDMapEntry(localHashMap, "sr_BA", (short)7194);
/* 1092 */     addLCIDMapEntry(localHashMap, "ar_OM", (short)8193);
/* 1093 */     addLCIDMapEntry(localHashMap, "en_JM", (short)8201);
/* 1094 */     addLCIDMapEntry(localHashMap, "es_VE", (short)8202);
/* 1095 */     addLCIDMapEntry(localHashMap, "ar_YE", (short)9217);
/* 1096 */     addLCIDMapEntry(localHashMap, "es_CO", (short)9226);
/* 1097 */     addLCIDMapEntry(localHashMap, "ar_SY", (short)10241);
/* 1098 */     addLCIDMapEntry(localHashMap, "en_BZ", (short)10249);
/* 1099 */     addLCIDMapEntry(localHashMap, "es_PE", (short)10250);
/* 1100 */     addLCIDMapEntry(localHashMap, "ar_JO", (short)11265);
/* 1101 */     addLCIDMapEntry(localHashMap, "en_TT", (short)11273);
/* 1102 */     addLCIDMapEntry(localHashMap, "es_AR", (short)11274);
/* 1103 */     addLCIDMapEntry(localHashMap, "ar_LB", (short)12289);
/* 1104 */     addLCIDMapEntry(localHashMap, "en_ZW", (short)12297);
/* 1105 */     addLCIDMapEntry(localHashMap, "es_EC", (short)12298);
/* 1106 */     addLCIDMapEntry(localHashMap, "ar_KW", (short)13313);
/* 1107 */     addLCIDMapEntry(localHashMap, "en_PH", (short)13321);
/* 1108 */     addLCIDMapEntry(localHashMap, "es_CL", (short)13322);
/* 1109 */     addLCIDMapEntry(localHashMap, "ar_AE", (short)14337);
/* 1110 */     addLCIDMapEntry(localHashMap, "es_UY", (short)14346);
/* 1111 */     addLCIDMapEntry(localHashMap, "ar_BH", (short)15361);
/* 1112 */     addLCIDMapEntry(localHashMap, "es_PY", (short)15370);
/* 1113 */     addLCIDMapEntry(localHashMap, "ar_QA", (short)16385);
/* 1114 */     addLCIDMapEntry(localHashMap, "es_BO", (short)16394);
/* 1115 */     addLCIDMapEntry(localHashMap, "es_SV", (short)17418);
/* 1116 */     addLCIDMapEntry(localHashMap, "es_HN", (short)18442);
/* 1117 */     addLCIDMapEntry(localHashMap, "es_NI", (short)19466);
/* 1118 */     addLCIDMapEntry(localHashMap, "es_PR", (short)20490);
/*      */ 
/* 1120 */     lcidMap = localHashMap;
/*      */   }
/*      */ 
/*      */   private static short getLCIDFromLocale(Locale paramLocale)
/*      */   {
/* 1125 */     if ((paramLocale.equals(Locale.US)) || (paramLocale.getLanguage().equals("en"))) {
/* 1126 */       return 1033;
/*      */     }
/*      */ 
/* 1129 */     if (lcidMap == null) {
/* 1130 */       createLCIDMap();
/*      */     }
/*      */ 
/* 1133 */     String str = paramLocale.toString();
/* 1134 */     while (!str.isEmpty()) {
/* 1135 */       Short localShort = (Short)lcidMap.get(str);
/* 1136 */       if (localShort != null) {
/* 1137 */         return localShort.shortValue();
/*      */       }
/* 1139 */       int i = str.lastIndexOf(95);
/* 1140 */       if (i < 1) {
/* 1141 */         return 1033;
/*      */       }
/* 1143 */       str = str.substring(0, i);
/*      */     }
/*      */ 
/* 1146 */     return 1033;
/*      */   }
/*      */ 
/*      */   private static short getSystemLCID()
/*      */   {
/* 1157 */     if (T2KFontFactory.isWindows) {
/* 1158 */       return T2KFontFactory.getSystemLCID();
/*      */     }
/* 1160 */     return getLCIDFromLocale(Locale.getDefault());
/*      */   }
/*      */ 
/*      */   public CharToGlyphMapper getGlyphMapper()
/*      */   {
/* 1167 */     if (this.mapper == null) {
/* 1168 */       this.mapper = new OpenTypeGlyphMapper(this);
/*      */     }
/* 1170 */     return this.mapper;
/*      */   }
/*      */ 
/*      */   public FontStrike getStrike(PGFont paramPGFont, BaseTransform paramBaseTransform) {
/* 1174 */     return getStrike(paramPGFont.getSize(), paramBaseTransform, getDefaultAAMode());
/*      */   }
/*      */ 
/*      */   public FontStrike getStrike(float paramFloat, BaseTransform paramBaseTransform) {
/* 1178 */     return getStrike(paramFloat, paramBaseTransform, getDefaultAAMode());
/*      */   }
/*      */ 
/*      */   public FontStrike getStrike(PGFont paramPGFont, BaseTransform paramBaseTransform, int paramInt)
/*      */   {
/* 1184 */     return getStrike(paramPGFont.getSize(), paramBaseTransform, paramInt);
/*      */   }
/*      */ 
/*      */   public FontStrike getStrike(float paramFloat, BaseTransform paramBaseTransform, int paramInt)
/*      */   {
/* 1190 */     T2KFontStrikeDesc localT2KFontStrikeDesc = new T2KFontStrikeDesc(paramFloat, paramBaseTransform, paramInt);
/*      */ 
/* 1192 */     WeakReference localWeakReference = (WeakReference)this.strikeMap.get(localT2KFontStrikeDesc);
/* 1193 */     T2KFontStrike localT2KFontStrike = null;
/* 1194 */     if (localWeakReference != null) {
/* 1195 */       localT2KFontStrike = (T2KFontStrike)localWeakReference.get();
/*      */     }
/* 1197 */     if (localT2KFontStrike == null) {
/* 1198 */       localT2KFontStrike = new T2KFontStrike(this, paramFloat, paramBaseTransform, localT2KFontStrikeDesc);
/* 1199 */       if (localT2KFontStrike.disposer != null)
/* 1200 */         localWeakReference = Disposer.addRecord(localT2KFontStrike, localT2KFontStrike.disposer);
/*      */       else {
/* 1202 */         localWeakReference = new WeakReference(localT2KFontStrike);
/*      */       }
/* 1204 */       this.strikeMap.put(localT2KFontStrikeDesc, localWeakReference);
/*      */     }
/* 1206 */     return localT2KFontStrike;
/*      */   }
/*      */ 
/*      */   public Map getStrikeMap() {
/* 1210 */     return this.strikeMap;
/*      */   }
/*      */ 
/*      */   public float getAdvance(int paramInt, float paramFloat)
/*      */   {
/* 1245 */     if ((this.advanceWidths == null) && (this.numHMetrics > 0)) {
/* 1246 */       synchronized (this) {
/* 1247 */         FontFileReader.Buffer localBuffer = readTable(1752003704);
/* 1248 */         if (localBuffer == null) {
/* 1249 */           this.numHMetrics = -1;
/* 1250 */           return 0.0F;
/*      */         }
/* 1252 */         char[] arrayOfChar = new char[this.numHMetrics];
/* 1253 */         for (int j = 0; j < this.numHMetrics; j++) {
/* 1254 */           arrayOfChar[j] = localBuffer.getChar(j * 4);
/*      */         }
/* 1256 */         this.advanceWidths = arrayOfChar;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1261 */     if (this.numHMetrics > 0)
/*      */     {
/*      */       int i;
/* 1263 */       if (paramInt < this.numHMetrics)
/* 1264 */         i = this.advanceWidths[paramInt];
/*      */       else {
/* 1266 */         i = this.advanceWidths[(this.numHMetrics - 1)];
/*      */       }
/* 1268 */       return (i & 0xFFFF) * paramFloat / this.upem;
/*      */     }
/* 1270 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   private synchronized native int[] getGlyphBoundingBoxNative(long paramLong, int paramInt);
/*      */ 
/*      */   public float[] getGlyphBoundingBox(int paramInt, float paramFloat, float[] paramArrayOfFloat)
/*      */   {
/* 1295 */     if (this.bbArr == null) {
/* 1296 */       this.bbArr = new int[this.numGlyphs][];
/*      */     }
/*      */ 
/* 1299 */     int[] arrayOfInt = paramInt < this.numGlyphs ? this.bbArr[paramInt] : EMPTY_BOUNDS;
/* 1300 */     if (arrayOfInt == null) {
/* 1301 */       if (this.pScaler == 0L) {
/* 1302 */         initScaler();
/*      */       }
/* 1304 */       if (this.isCFF)
/* 1305 */         arrayOfInt = getGlyphBoundingBoxCFF(paramInt, paramFloat);
/*      */       else {
/* 1307 */         arrayOfInt = getGlyphBoundingBoxNative(this.pScaler, paramInt);
/*      */       }
/* 1309 */       if (arrayOfInt == null) {
/* 1310 */         arrayOfInt = EMPTY_BOUNDS;
/*      */       }
/* 1312 */       this.bbArr[paramInt] = arrayOfInt;
/*      */     }
/* 1314 */     if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length < 4)) {
/* 1315 */       paramArrayOfFloat = new float[4];
/*      */     }
/* 1317 */     float f = paramFloat / this.upem;
/* 1318 */     paramArrayOfFloat[0] = (arrayOfInt[0] * f);
/* 1319 */     paramArrayOfFloat[1] = (arrayOfInt[1] * f);
/* 1320 */     paramArrayOfFloat[2] = (arrayOfInt[2] * f);
/* 1321 */     paramArrayOfFloat[3] = (arrayOfInt[3] * f);
/* 1322 */     return paramArrayOfFloat;
/*      */   }
/*      */ 
/*      */   private int[] getGlyphBoundingBoxCFF(int paramInt, float paramFloat) {
/* 1326 */     if ((paramFloat < 1.0F) || (paramFloat > 48.0F)) {
/* 1327 */       paramFloat = 12.0F;
/*      */     }
/* 1329 */     T2KFontStrike localT2KFontStrike = (T2KFontStrike)getStrike(paramFloat, BaseTransform.IDENTITY_TRANSFORM);
/*      */ 
/* 1332 */     long l = localT2KFontStrike.getScalerContext();
/* 1333 */     RectBounds localRectBounds = getGlyphBounds(this.pScaler, l, paramInt);
/* 1334 */     int[] arrayOfInt = new int[4];
/* 1335 */     float f = this.upem / paramFloat;
/* 1336 */     arrayOfInt[0] = Math.round(localRectBounds.getMinX() * f);
/* 1337 */     arrayOfInt[1] = Math.round(localRectBounds.getMinY() * f);
/* 1338 */     arrayOfInt[2] = Math.round(localRectBounds.getMaxX() * f);
/* 1339 */     arrayOfInt[3] = Math.round(localRectBounds.getMaxY() * f);
/* 1340 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */   T2KMetrics getFontMetrics(long paramLong, float paramFloat)
/*      */   {
/* 1345 */     if (this.xHeight == 0.0F) {
/* 1346 */       int i = getGlyphMapper().charToGlyph('x');
/* 1347 */       if (this.mapper.getMissingGlyphCode() != i)
/*      */       {
/* 1350 */         this.xHeight = (getGlyphBounds(paramLong, i).getHeight() / paramFloat);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1355 */     return new T2KMetrics(this.ascent * paramFloat / this.upem, this.descent * paramFloat / this.upem, this.linegap * paramFloat / this.upem, this.xHeight * paramFloat);
/*      */   }
/*      */ 
/*      */   float[] getStyleMetrics(float paramFloat)
/*      */   {
/* 1363 */     if (this.styleMetrics == null) {
/* 1364 */       arrayOfFloat = new float[4];
/*      */ 
/* 1366 */       FontFileReader.Buffer localBuffer1 = readTable(1330851634);
/* 1367 */       if ((localBuffer1 == null) || (localBuffer1.capacity() < 30) || (this.upem < 0.0F)) {
/* 1368 */         arrayOfFloat[0] = -0.4F;
/* 1369 */         arrayOfFloat[1] = 0.05F;
/*      */       } else {
/* 1371 */         arrayOfFloat[0] = (-localBuffer1.getShort(28) / this.upem);
/* 1372 */         arrayOfFloat[1] = (localBuffer1.getShort(26) / this.upem);
/*      */       }
/*      */ 
/* 1375 */       FontFileReader.Buffer localBuffer2 = readTable(1886352244);
/* 1376 */       if ((localBuffer2 == null) || (localBuffer2.capacity() < 12) || (this.upem < 0.0F)) {
/* 1377 */         arrayOfFloat[2] = 0.1F;
/* 1378 */         arrayOfFloat[3] = 0.05F;
/*      */       } else {
/* 1380 */         arrayOfFloat[2] = (-localBuffer2.getShort(8) / this.upem);
/* 1381 */         arrayOfFloat[3] = (localBuffer2.getShort(10) / this.upem);
/*      */       }
/* 1383 */       this.styleMetrics = arrayOfFloat;
/*      */     }
/*      */ 
/* 1386 */     float[] arrayOfFloat = new float[4];
/* 1387 */     for (int i = 0; i < 4; i++) {
/* 1388 */       arrayOfFloat[i] = (this.styleMetrics[i] * paramFloat);
/*      */     }
/*      */ 
/* 1391 */     return arrayOfFloat;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*   30 */     AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Void run() {
/*   32 */         NativeLibLoader.loadLibrary("javafx-font");
/*   33 */         return null;
/*      */       }
/*      */     });
/*   36 */     initNativeIDs();
/*      */   }
/*      */ 
/*      */   static class DirectoryEntry
/*      */   {
/*      */     int tag;
/*      */     int offset;
/*      */     int length;
/*      */   }
/*      */ 
/*      */   static class FileDisposer
/*      */     implements DisposerRecord
/*      */   {
/*      */     String fileName;
/*      */     boolean isTracked;
/*      */     T2KFontFactory factory;
/*      */     WeakReference<T2KFontFile> refKey;
/*      */ 
/*      */     public FileDisposer(String paramString, boolean paramBoolean)
/*      */     {
/*  306 */       this.fileName = paramString;
/*  307 */       this.isTracked = paramBoolean;
/*      */     }
/*      */ 
/*      */     public void setFactory(T2KFontFactory paramT2KFontFactory, WeakReference<T2KFontFile> paramWeakReference)
/*      */     {
/*  312 */       this.factory = paramT2KFontFactory;
/*  313 */       this.refKey = paramWeakReference;
/*      */     }
/*      */ 
/*      */     public synchronized void dispose() {
/*  317 */       if (this.fileName != null) {
/*  318 */         AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Void run() {
/*      */             try {
/*  322 */               File localFile = new File(T2KFontFile.FileDisposer.this.fileName);
/*  323 */               int i = (int)localFile.length();
/*  324 */               localFile.delete();
/*      */ 
/*  327 */               if (T2KFontFile.FileDisposer.this.isTracked) {
/*  328 */                 FontFileWriter.FontTracker.getTracker().subBytes(i);
/*      */               }
/*      */ 
/*  331 */               if ((T2KFontFile.FileDisposer.this.factory != null) && (T2KFontFile.FileDisposer.this.refKey != null)) {
/*  332 */                 Object localObject = T2KFontFile.FileDisposer.this.refKey.get();
/*  333 */                 if (localObject == null) {
/*  334 */                   T2KFontFile.FileDisposer.this.factory.removeTmpFont(T2KFontFile.FileDisposer.this.refKey);
/*  335 */                   T2KFontFile.FileDisposer.this.factory = null;
/*  336 */                   T2KFontFile.FileDisposer.this.refKey = null;
/*      */                 }
/*      */               }
/*  339 */               if (T2KFontFactory.debugFonts)
/*  340 */                 System.err.println("FileDisposer=" + T2KFontFile.FileDisposer.this.fileName);
/*      */             }
/*      */             catch (Exception localException) {
/*  343 */               if (T2KFontFactory.debugFonts) {
/*  344 */                 localException.printStackTrace();
/*      */               }
/*      */             }
/*  347 */             return null;
/*      */           }
/*      */         });
/*  351 */         this.fileName = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   static class ScalerDisposer
/*      */     implements DisposerRecord
/*      */   {
/*      */     long pScaler;
/*      */ 
/*      */     public ScalerDisposer(long paramLong)
/*      */     {
/*  284 */       this.pScaler = paramLong;
/*      */     }
/*      */ 
/*      */     public synchronized void dispose() {
/*  288 */       if (this.pScaler != 0L)
/*      */       {
/*  290 */         T2KFontFile.disposeNativeScaler(this.pScaler);
/*  291 */         if (T2KFontFactory.debugFonts) {
/*  292 */           System.err.println("ScalerDisposer=" + this.pScaler);
/*      */         }
/*  294 */         this.pScaler = 0L;
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.T2KFontFile
 * JD-Core Version:    0.6.2
 */