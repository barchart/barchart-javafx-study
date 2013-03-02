/*     */ package com.sun.t2k;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ abstract class CMap
/*     */ {
/*     */   static final char noSuchChar = '�';
/*     */   static final int SHORTMASK = 65535;
/*     */   static final int INTMASK = -1;
/* 570 */   public static final NullCMapClass theNullCmap = new NullCMapClass();
/*     */ 
/*     */   static CMap initialize(T2KFontFile paramT2KFontFile)
/*     */   {
/*  28 */     CMap localCMap = null;
/*     */ 
/*  30 */     int k = -1;
/*     */ 
/*  32 */     int m = 0; int n = 0; int i1 = 0; int i2 = 0;
/*  33 */     int i3 = 0; int i4 = 0;
/*     */ 
/*  35 */     FontFileReader.Buffer localBuffer = paramT2KFontFile.readTable(1668112752);
/*  36 */     int i5 = localBuffer.getShort(2);
/*     */ 
/*  47 */     for (int i6 = 0; i6 < i5; i6++) {
/*  48 */       localBuffer.position(i6 * 8 + 4);
/*  49 */       int j = localBuffer.getShort();
/*     */ 
/*  51 */       if (j == 0) {
/*  52 */         i3 = 1;
/*  53 */         k = localBuffer.getShort();
/*  54 */         i2 = localBuffer.getInt();
/*     */       }
/*  56 */       else if (j == 3) {
/*  57 */         i4 = 1;
/*  58 */         k = localBuffer.getShort();
/*  59 */         int i = localBuffer.getInt();
/*  60 */         switch (k) { case 0:
/*  61 */           m = i; break;
/*     */         case 1:
/*  62 */           n = i; break;
/*     */         case 10:
/*  63 */           i1 = i;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  69 */     if (i4 != 0) {
/*  70 */       if (i1 != 0) {
/*  71 */         localCMap = createCMap(localBuffer, i1);
/*     */       }
/*  73 */       else if (m != 0) {
/*  74 */         localCMap = createCMap(localBuffer, m);
/*     */       }
/*  76 */       else if (n != 0)
/*  77 */         localCMap = createCMap(localBuffer, n);
/*     */     }
/*  79 */     else if ((i3 != 0) && (i2 != 0)) {
/*  80 */       localCMap = createCMap(localBuffer, i2);
/*     */     }
/*     */     else
/*     */     {
/*  93 */       localCMap = createCMap(localBuffer, localBuffer.getInt(8));
/*     */     }
/*  95 */     return localCMap;
/*     */   }
/*     */ 
/*     */   static CMap createCMap(FontFileReader.Buffer paramBuffer, int paramInt)
/*     */   {
/* 102 */     int i = paramBuffer.getChar(paramInt);
/*     */ 
/* 104 */     switch (i) { case 0:
/* 105 */       return new CMapFormat0(paramBuffer, paramInt);
/*     */     case 2:
/* 106 */       return new CMapFormat2(paramBuffer, paramInt);
/*     */     case 4:
/* 107 */       return new CMapFormat4(paramBuffer, paramInt);
/*     */     case 6:
/* 108 */       return new CMapFormat6(paramBuffer, paramInt);
/*     */     case 8:
/* 109 */       return new CMapFormat8(paramBuffer, paramInt);
/*     */     case 10:
/* 110 */       return new CMapFormat10(paramBuffer, paramInt);
/*     */     case 12:
/* 111 */       return new CMapFormat12(paramBuffer, paramInt);
/*     */     case 1:
/*     */     case 3:
/*     */     case 5:
/*     */     case 7:
/*     */     case 9:
/* 112 */     case 11: } throw new RuntimeException("Cmap format unimplemented: " + paramBuffer.getChar(paramInt));
/*     */   }
/*     */ 
/*     */   abstract char getGlyph(int paramInt);
/*     */ 
/*     */   final int getControlCodeGlyph(int paramInt, boolean paramBoolean)
/*     */   {
/* 573 */     if (paramInt < 16) {
/* 574 */       switch (paramInt) { case 9:
/*     */       case 10:
/*     */       case 13:
/* 577 */         return 65535;
/*     */       case 11:
/* 579 */       case 12: }  } else if (paramInt >= 8204) {
/* 580 */       if ((paramInt <= 8207) || ((paramInt >= 8232) && (paramInt <= 8238)) || ((paramInt >= 8298) && (paramInt <= 8303)))
/*     */       {
/* 583 */         return 65535;
/* 584 */       }if ((paramBoolean) && (paramInt >= 65535)) {
/* 585 */         return 0;
/*     */       }
/*     */     }
/* 588 */     return -1;
/*     */   }
/*     */ 
/*     */   static class NullCMapClass extends CMap
/*     */   {
/*     */     char getGlyph(int paramInt)
/*     */     {
/* 566 */       return '\000';
/*     */     }
/*     */   }
/*     */ 
/*     */   static class CMapFormat12 extends CMap
/*     */   {
/*     */     int numGroups;
/* 477 */     int highBit = 0;
/*     */     int power;
/*     */     int extra;
/*     */     long[] startCharCode;
/*     */     long[] endCharCode;
/*     */     int[] startGlyphID;
/*     */ 
/*     */     CMapFormat12(FontFileReader.Buffer paramBuffer, int paramInt)
/*     */     {
/* 486 */       this.numGroups = paramBuffer.getInt(paramInt + 12);
/* 487 */       this.startCharCode = new long[this.numGroups];
/* 488 */       this.endCharCode = new long[this.numGroups];
/* 489 */       this.startGlyphID = new int[this.numGroups];
/* 490 */       paramBuffer.position(paramInt + 16);
/*     */ 
/* 493 */       for (int i = 0; i < this.numGroups; i++) {
/* 494 */         this.startCharCode[i] = (paramBuffer.getInt() & 0xFFFFFFFF);
/* 495 */         this.endCharCode[i] = (paramBuffer.getInt() & 0xFFFFFFFF);
/* 496 */         this.startGlyphID[i] = (paramBuffer.getInt() & 0xFFFFFFFF);
/*     */       }
/*     */ 
/* 500 */       i = this.numGroups;
/*     */ 
/* 502 */       if (i >= 65536) {
/* 503 */         i >>= 16;
/* 504 */         this.highBit += 16;
/*     */       }
/*     */ 
/* 507 */       if (i >= 256) {
/* 508 */         i >>= 8;
/* 509 */         this.highBit += 8;
/*     */       }
/*     */ 
/* 512 */       if (i >= 16) {
/* 513 */         i >>= 4;
/* 514 */         this.highBit += 4;
/*     */       }
/*     */ 
/* 517 */       if (i >= 4) {
/* 518 */         i >>= 2;
/* 519 */         this.highBit += 2;
/*     */       }
/*     */ 
/* 522 */       if (i >= 2) {
/* 523 */         i >>= 1;
/* 524 */         this.highBit += 1;
/*     */       }
/*     */ 
/* 527 */       this.power = (1 << this.highBit);
/* 528 */       this.extra = (this.numGroups - this.power);
/*     */     }
/*     */ 
/*     */     char getGlyph(int paramInt) {
/* 532 */       int i = getControlCodeGlyph(paramInt, false);
/* 533 */       if (i >= 0) {
/* 534 */         return (char)i;
/*     */       }
/* 536 */       int j = this.power;
/* 537 */       int k = 0;
/*     */ 
/* 539 */       if (this.startCharCode[this.extra] <= paramInt) {
/* 540 */         k = this.extra;
/*     */       }
/*     */ 
/* 543 */       while (j > 1) {
/* 544 */         j >>= 1;
/*     */ 
/* 546 */         if (this.startCharCode[(k + j)] <= paramInt) {
/* 547 */           k += j;
/*     */         }
/*     */       }
/*     */ 
/* 551 */       if ((this.startCharCode[k] <= paramInt) && (this.endCharCode[k] >= paramInt))
/*     */       {
/* 553 */         return (char)(int)(this.startGlyphID[k] + (paramInt - this.startCharCode[k]));
/*     */       }
/*     */ 
/* 557 */       return '\000';
/*     */     }
/*     */   }
/*     */ 
/*     */   static class CMapFormat10 extends CMap
/*     */   {
/*     */     long startCharCode;
/*     */     int numChars;
/*     */     char[] glyphIdArray;
/*     */ 
/*     */     CMapFormat10(FontFileReader.Buffer paramBuffer, int paramInt)
/*     */     {
/* 452 */       paramBuffer.position(paramInt + 12);
/* 453 */       this.startCharCode = (paramBuffer.getInt() & 0xFFFFFFFF);
/* 454 */       this.numChars = (paramBuffer.getInt() & 0xFFFFFFFF);
/* 455 */       this.glyphIdArray = new char[this.numChars];
/* 456 */       for (int i = 0; i < this.numChars; i++)
/* 457 */         this.glyphIdArray[i] = paramBuffer.getChar();
/*     */     }
/*     */ 
/*     */     char getGlyph(int paramInt)
/*     */     {
/* 463 */       int i = (int)(paramInt - this.startCharCode);
/* 464 */       if ((i < 0) || (i >= this.numChars)) {
/* 465 */         return '\000';
/*     */       }
/* 467 */       return this.glyphIdArray[i];
/*     */     }
/*     */   }
/*     */ 
/*     */   static class CMapFormat8 extends CMap
/*     */   {
/*     */     CMapFormat8(FontFileReader.Buffer paramBuffer, int paramInt)
/*     */     {
/* 431 */       System.err.println("WARNING: CMapFormat8 is untested.");
/*     */     }
/*     */ 
/*     */     char getGlyph(int paramInt) {
/* 435 */       return '\000';
/*     */     }
/*     */   }
/*     */ 
/*     */   static class CMapFormat6 extends CMap
/*     */   {
/*     */     char firstCode;
/*     */     char entryCount;
/*     */     char[] glyphIdArray;
/*     */ 
/*     */     CMapFormat6(FontFileReader.Buffer paramBuffer, int paramInt)
/*     */     {
/* 399 */       System.err.println("WARNING: CMapFormat6 is untested.");
/* 400 */       paramBuffer.position(paramInt + 6);
/* 401 */       this.firstCode = paramBuffer.getChar();
/* 402 */       this.entryCount = paramBuffer.getChar();
/* 403 */       this.glyphIdArray = new char[this.entryCount];
/* 404 */       for (int i = 0; i < this.entryCount; i++)
/* 405 */         this.glyphIdArray[i] = paramBuffer.getChar();
/*     */     }
/*     */ 
/*     */     char getGlyph(int paramInt)
/*     */     {
/* 410 */       int i = getControlCodeGlyph(paramInt, true);
/* 411 */       if (i >= 0) {
/* 412 */         return (char)i;
/*     */       }
/*     */ 
/* 415 */       paramInt -= this.firstCode;
/* 416 */       if ((paramInt < 0) || (paramInt >= this.entryCount)) {
/* 417 */         return '\000';
/*     */       }
/* 419 */       return this.glyphIdArray[paramInt];
/*     */     }
/*     */   }
/*     */ 
/*     */   static class CMapFormat2 extends CMap
/*     */   {
/* 287 */     char[] subHeaderKey = new char[256];
/*     */     char[] firstCodeArray;
/*     */     char[] entryCountArray;
/*     */     short[] idDeltaArray;
/*     */     char[] idRangeOffSetArray;
/*     */     char[] glyphIndexArray;
/*     */ 
/*     */     CMapFormat2(FontFileReader.Buffer paramBuffer, int paramInt)
/*     */     {
/* 305 */       int i = paramBuffer.getChar(paramInt + 2);
/* 306 */       paramBuffer.position(paramInt + 6);
/* 307 */       int j = 0;
/* 308 */       for (int k = 0; k < 256; k++) {
/* 309 */         this.subHeaderKey[k] = paramBuffer.getChar();
/* 310 */         if (this.subHeaderKey[k] > j) {
/* 311 */           j = this.subHeaderKey[k];
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 318 */       k = (j >> 3) + 1;
/* 319 */       this.firstCodeArray = new char[k];
/* 320 */       this.entryCountArray = new char[k];
/* 321 */       this.idDeltaArray = new short[k];
/* 322 */       this.idRangeOffSetArray = new char[k];
/* 323 */       for (int m = 0; m < k; m++) {
/* 324 */         this.firstCodeArray[m] = paramBuffer.getChar();
/* 325 */         this.entryCountArray[m] = paramBuffer.getChar();
/* 326 */         this.idDeltaArray[m] = ((short)paramBuffer.getChar());
/* 327 */         this.idRangeOffSetArray[m] = paramBuffer.getChar();
/*     */       }
/*     */ 
/* 330 */       m = (i - 518 - k * 8) / 2;
/* 331 */       this.glyphIndexArray = new char[m];
/* 332 */       for (int n = 0; n < m; n++)
/* 333 */         this.glyphIndexArray[n] = paramBuffer.getChar();
/*     */     }
/*     */ 
/*     */     char getGlyph(int paramInt)
/*     */     {
/* 338 */       int i = getControlCodeGlyph(paramInt, true);
/* 339 */       if (i >= 0) {
/* 340 */         return (char)i;
/*     */       }
/*     */ 
/* 343 */       int j = (char)(paramInt >> 8);
/* 344 */       int k = (char)(paramInt & 0xFF);
/* 345 */       int m = this.subHeaderKey[j] >> '\003';
/*     */ 
/* 348 */       if (m != 0) {
/* 349 */         n = k;
/*     */       } else {
/* 351 */         n = j;
/* 352 */         if (n == 0) {
/* 353 */           n = k;
/*     */         }
/*     */       }
/* 356 */       int i1 = this.firstCodeArray[m];
/* 357 */       if (n < i1) {
/* 358 */         return '\000';
/*     */       }
/* 360 */       int n = (char)(n - i1);
/*     */ 
/* 363 */       if (n < this.entryCountArray[m])
/*     */       {
/* 377 */         int i2 = (this.idRangeOffSetArray.length - m) * 8 - 6;
/* 378 */         int i3 = (this.idRangeOffSetArray[m] - i2) / 2;
/*     */ 
/* 380 */         int i4 = this.glyphIndexArray[(i3 + n)];
/* 381 */         if (i4 != 0) {
/* 382 */           i4 = (char)(i4 + this.idDeltaArray[m]);
/* 383 */           return i4;
/*     */         }
/*     */       }
/* 386 */       return '\000';
/*     */     }
/*     */   }
/*     */ 
/*     */   static class CMapFormat0 extends CMap
/*     */   {
/*     */     byte[] cmap;
/*     */ 
/*     */     CMapFormat0(FontFileReader.Buffer paramBuffer, int paramInt)
/*     */     {
/* 263 */       int i = paramBuffer.getChar(paramInt + 2);
/* 264 */       this.cmap = new byte[i - 6];
/* 265 */       paramBuffer.get(paramInt + 6, this.cmap, 0, i - 6);
/*     */     }
/*     */ 
/*     */     char getGlyph(int paramInt) {
/* 269 */       if (paramInt < 256) {
/* 270 */         if (paramInt < 16)
/* 271 */           switch (paramInt) { case 9:
/*     */           case 10:
/*     */           case 13:
/* 274 */             return 65535;
/*     */           case 11:
/*     */           case 12: }
/* 277 */         return (char)(0xFF & this.cmap[paramInt]);
/*     */       }
/* 279 */       return '\000';
/*     */     }
/*     */   }
/*     */ 
/*     */   static class CMapFormat4 extends CMap
/*     */   {
/*     */     int segCount;
/*     */     int entrySelector;
/*     */     int rangeShift;
/*     */     char[] endCount;
/*     */     char[] startCount;
/*     */     short[] idDelta;
/*     */     char[] idRangeOffset;
/*     */     char[] glyphIds;
/*     */ 
/*     */     CMapFormat4(FontFileReader.Buffer paramBuffer, int paramInt)
/*     */     {
/* 146 */       paramBuffer.position(paramInt);
/* 147 */       paramBuffer.getChar();
/* 148 */       int i = paramBuffer.getChar();
/*     */ 
/* 157 */       if (paramInt + i > paramBuffer.capacity()) {
/* 158 */         i = paramBuffer.capacity() - paramInt;
/*     */       }
/* 160 */       paramBuffer.getChar();
/* 161 */       this.segCount = (paramBuffer.getChar() / '\002');
/* 162 */       paramBuffer.getChar();
/* 163 */       this.entrySelector = paramBuffer.getChar();
/* 164 */       this.rangeShift = (paramBuffer.getChar() / '\002');
/* 165 */       this.startCount = new char[this.segCount];
/* 166 */       this.endCount = new char[this.segCount];
/* 167 */       this.idDelta = new short[this.segCount];
/* 168 */       this.idRangeOffset = new char[this.segCount];
/*     */ 
/* 170 */       for (int j = 0; j < this.segCount; j++) {
/* 171 */         this.endCount[j] = paramBuffer.getChar();
/*     */       }
/* 173 */       paramBuffer.getChar();
/* 174 */       for (j = 0; j < this.segCount; j++) {
/* 175 */         this.startCount[j] = paramBuffer.getChar();
/*     */       }
/*     */ 
/* 178 */       for (j = 0; j < this.segCount; j++) {
/* 179 */         this.idDelta[j] = ((short)paramBuffer.getChar());
/*     */       }
/*     */ 
/* 182 */       for (j = 0; j < this.segCount; j++) {
/* 183 */         k = paramBuffer.getChar();
/* 184 */         this.idRangeOffset[j] = ((char)(k >> 1 & 0xFFFF));
/*     */       }
/*     */ 
/* 189 */       j = (this.segCount * 8 + 16) / 2;
/* 190 */       paramBuffer.position(j * 2 + paramInt);
/* 191 */       int k = i / 2 - j;
/* 192 */       this.glyphIds = new char[k];
/* 193 */       for (int m = 0; m < k; m++)
/* 194 */         this.glyphIds[m] = paramBuffer.getChar();
/*     */     }
/*     */ 
/*     */     char getGlyph(int paramInt)
/*     */     {
/* 200 */       int i = 0;
/* 201 */       int j = 0;
/*     */ 
/* 203 */       int k = getControlCodeGlyph(paramInt, true);
/* 204 */       if (k >= 0) {
/* 205 */         return (char)k;
/*     */       }
/*     */ 
/* 226 */       int m = 0; int n = this.startCount.length;
/* 227 */       i = this.startCount.length >> 1;
/* 228 */       while (m < n) {
/* 229 */         if (this.endCount[i] < paramInt)
/* 230 */           m = i + 1;
/*     */         else {
/* 232 */           n = i;
/*     */         }
/* 234 */         i = m + n >> 1;
/*     */       }
/*     */ 
/* 237 */       if ((paramInt >= this.startCount[i]) && (paramInt <= this.endCount[i])) {
/* 238 */         int i1 = this.idRangeOffset[i];
/*     */ 
/* 240 */         if (i1 == 0) {
/* 241 */           j = (char)(paramInt + this.idDelta[i]);
/*     */         }
/*     */         else {
/* 244 */           int i2 = i1 - this.segCount + i + (paramInt - this.startCount[i]);
/*     */ 
/* 246 */           j = this.glyphIds[i2];
/* 247 */           if (j != 0) {
/* 248 */             j = (char)(j + this.idDelta[i]);
/*     */           }
/*     */         }
/*     */       }
/* 252 */       return j;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.CMap
 * JD-Core Version:    0.6.2
 */