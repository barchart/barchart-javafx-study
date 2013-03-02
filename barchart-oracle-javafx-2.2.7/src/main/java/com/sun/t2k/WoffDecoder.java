/*     */ package com.sun.t2k;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.zip.Inflater;
/*     */ 
/*     */ public class WoffDecoder extends FontFileWriter
/*     */ {
/*     */   WoffHeader woffHeader;
/*     */   WoffDirectoryEntry[] woffTableDirectory;
/*     */ 
/*     */   public void decode(FontFileReader paramFontFileReader)
/*     */     throws Exception
/*     */   {
/*  21 */     paramFontFileReader.reset();
/*  22 */     initWoffTables(paramFontFileReader);
/*  23 */     if ((this.woffHeader == null) || (this.woffTableDirectory == null)) {
/*  24 */       throw new Exception("WoffDecoder: failure reading header");
/*     */     }
/*  26 */     int i = this.woffHeader.flavor;
/*  27 */     if ((i != 65536) && (i != 1953658213) && (i != 1330926671)) {
/*  28 */       throw new Exception("WoffDecoder: invalid flavor");
/*     */     }
/*     */ 
/*  32 */     short s = this.woffHeader.numTables;
/*  33 */     setLength(this.woffHeader.totalSfntSize);
/*  34 */     writeHeader(i, s);
/*     */ 
/*  37 */     Arrays.sort(this.woffTableDirectory, new Comparator()
/*     */     {
/*     */       public int compare(WoffDecoder.WoffDirectoryEntry paramAnonymousWoffDirectoryEntry1, WoffDecoder.WoffDirectoryEntry paramAnonymousWoffDirectoryEntry2) {
/*  40 */         return paramAnonymousWoffDirectoryEntry1.offset - paramAnonymousWoffDirectoryEntry2.offset;
/*     */       }
/*     */     });
/*  44 */     Inflater localInflater = new Inflater();
/*  45 */     int j = 12 + s * 16;
/*  46 */     for (int k = 0; k < this.woffTableDirectory.length; k++) {
/*  47 */       WoffDirectoryEntry localWoffDirectoryEntry = this.woffTableDirectory[k];
/*     */ 
/*  52 */       writeDirectoryEntry(localWoffDirectoryEntry.index, localWoffDirectoryEntry.tag, localWoffDirectoryEntry.origChecksum, j, localWoffDirectoryEntry.origLength);
/*     */ 
/*  56 */       FontFileReader.Buffer localBuffer = paramFontFileReader.readBlock(localWoffDirectoryEntry.offset, localWoffDirectoryEntry.comLength);
/*  57 */       Object localObject = new byte[localWoffDirectoryEntry.comLength];
/*  58 */       localBuffer.get(0, (byte[])localObject, 0, localWoffDirectoryEntry.comLength);
/*  59 */       if (localWoffDirectoryEntry.comLength != localWoffDirectoryEntry.origLength) {
/*  60 */         localInflater.setInput((byte[])localObject);
/*  61 */         byte[] arrayOfByte = new byte[localWoffDirectoryEntry.origLength];
/*  62 */         int m = localInflater.inflate(arrayOfByte);
/*  63 */         if (m != localWoffDirectoryEntry.origLength) {
/*  64 */           throw new Exception("WoffDecoder: failure expanding table");
/*     */         }
/*  66 */         localInflater.reset();
/*  67 */         localObject = arrayOfByte;
/*     */       }
/*  69 */       seek(j);
/*  70 */       writeBytes((byte[])localObject);
/*     */ 
/*  72 */       j += (localWoffDirectoryEntry.origLength + 3 & 0xFFFFFFFC);
/*     */     }
/*  74 */     localInflater.end();
/*     */   }
/*     */ 
/*     */   void initWoffTables(FontFileReader paramFontFileReader) throws Exception {
/*  78 */     long l = paramFontFileReader.getLength();
/*  79 */     if (l < 44L) {
/*  80 */       throw new Exception("WoffDecoder: invalid filesize");
/*     */     }
/*  82 */     FontFileReader.Buffer localBuffer = paramFontFileReader.readBlock(0, 44);
/*  83 */     WoffHeader localWoffHeader = new WoffHeader(localBuffer);
/*  84 */     int i = localWoffHeader.numTables;
/*  85 */     if (localWoffHeader.signature != 2001684038) {
/*  86 */       throw new Exception("WoffDecoder: invalid signature");
/*     */     }
/*  88 */     if (localWoffHeader.reserved != 0) {
/*  89 */       throw new Exception("WoffDecoder: invalid reserved != 0");
/*     */     }
/*  91 */     if (l < 44 + i * 20) {
/*  92 */       throw new Exception("WoffDecoder: invalid filesize");
/*     */     }
/*     */ 
/*  95 */     WoffDirectoryEntry[] arrayOfWoffDirectoryEntry = new WoffDirectoryEntry[i];
/*  96 */     int j = 44 + i * 20;
/*  97 */     int k = 12 + i * 16;
/*  98 */     localBuffer = paramFontFileReader.readBlock(44, i * 20);
/*  99 */     int m = 0;
/* 100 */     for (int n = 0; n < i; n++)
/*     */     {
/*     */       void tmp180_177 = new WoffDirectoryEntry(localBuffer, n); WoffDirectoryEntry localWoffDirectoryEntry = tmp180_177; arrayOfWoffDirectoryEntry[n] = tmp180_177;
/* 102 */       if (localWoffDirectoryEntry.tag <= m) {
/* 103 */         throw new Exception("WoffDecoder: table directory not ordered by tag");
/*     */       }
/*     */ 
/* 106 */       int i1 = localWoffDirectoryEntry.offset;
/* 107 */       int i2 = localWoffDirectoryEntry.offset + localWoffDirectoryEntry.comLength;
/* 108 */       if ((j > i1) || (i1 > l)) {
/* 109 */         throw new Exception("WoffDecoder: invalid table offset");
/*     */       }
/* 111 */       if ((i1 > i2) || (i2 > l)) {
/* 112 */         throw new Exception("WoffDecoder: invalid table offset");
/*     */       }
/* 114 */       if (localWoffDirectoryEntry.comLength > localWoffDirectoryEntry.origLength) {
/* 115 */         throw new Exception("WoffDecoder: invalid compressed length");
/*     */       }
/* 117 */       k += (localWoffDirectoryEntry.origLength + 3 & 0xFFFFFFFC);
/* 118 */       if (k > localWoffHeader.totalSfntSize) {
/* 119 */         throw new Exception("WoffDecoder: invalid totalSfntSize");
/*     */       }
/*     */     }
/* 122 */     if (k != localWoffHeader.totalSfntSize) {
/* 123 */       throw new Exception("WoffDecoder: invalid totalSfntSize");
/*     */     }
/* 125 */     this.woffHeader = localWoffHeader;
/* 126 */     this.woffTableDirectory = arrayOfWoffDirectoryEntry;
/*     */   }
/*     */ 
/*     */   static class WoffDirectoryEntry
/*     */   {
/*     */     int tag;
/*     */     int offset;
/*     */     int comLength;
/*     */     int origLength;
/*     */     int origChecksum;
/*     */     int index;
/*     */ 
/*     */     WoffDirectoryEntry(FontFileReader.Buffer paramBuffer, int paramInt)
/*     */     {
/* 168 */       this.tag = paramBuffer.getInt();
/* 169 */       this.offset = paramBuffer.getInt();
/* 170 */       this.comLength = paramBuffer.getInt();
/* 171 */       this.origLength = paramBuffer.getInt();
/* 172 */       this.origChecksum = paramBuffer.getInt();
/* 173 */       this.index = paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */   static class WoffHeader
/*     */   {
/*     */     int signature;
/*     */     int flavor;
/*     */     int length;
/*     */     short numTables;
/*     */     short reserved;
/*     */     int totalSfntSize;
/*     */     short majorVersion;
/*     */     short minorVersion;
/*     */     int metaOffset;
/*     */     int metaLength;
/*     */     int metaOrigLength;
/*     */     int privateOffset;
/*     */     int privateLength;
/*     */ 
/*     */     WoffHeader(FontFileReader.Buffer paramBuffer)
/*     */     {
/* 144 */       this.signature = paramBuffer.getInt();
/* 145 */       this.flavor = paramBuffer.getInt();
/* 146 */       this.length = paramBuffer.getInt();
/* 147 */       this.numTables = paramBuffer.getShort();
/* 148 */       this.reserved = paramBuffer.getShort();
/* 149 */       this.totalSfntSize = paramBuffer.getInt();
/* 150 */       this.majorVersion = paramBuffer.getShort();
/* 151 */       this.minorVersion = paramBuffer.getShort();
/* 152 */       this.metaOffset = paramBuffer.getInt();
/* 153 */       this.metaLength = paramBuffer.getInt();
/* 154 */       this.metaOrigLength = paramBuffer.getInt();
/* 155 */       this.privateOffset = paramBuffer.getInt();
/* 156 */       this.privateLength = paramBuffer.getInt();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.WoffDecoder
 * JD-Core Version:    0.6.2
 */