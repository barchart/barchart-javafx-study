/*     */ package com.sun.glass.ui.win;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ class HTMLCodec extends InputStream
/*     */ {
/*     */   public static final String ENCODING = "UTF-8";
/*     */   public static final String VERSION = "Version:";
/*     */   public static final String START_HTML = "StartHTML:";
/*     */   public static final String END_HTML = "EndHTML:";
/*     */   public static final String START_FRAGMENT = "StartFragment:";
/*     */   public static final String END_FRAGMENT = "EndFragment:";
/*     */   public static final String START_SELECTION = "StartSelection:";
/*     */   public static final String END_SELECTION = "EndSelection:";
/*     */   public static final String START_FRAGMENT_CMT = "<!--StartFragment-->";
/*     */   public static final String END_FRAGMENT_CMT = "<!--EndFragment-->";
/*     */   public static final String SOURCE_URL = "SourceURL:";
/*     */   public static final String DEF_SOURCE_URL = "about:blank";
/*     */   public static final String EOLN = "\r\n";
/*     */   private static final String VERSION_NUM = "1.0";
/*     */   private static final int PADDED_WIDTH = 10;
/*     */   private final BufferedInputStream bufferedStream;
/* 229 */   private boolean descriptionParsed = false;
/* 230 */   private boolean closed = false;
/*     */   public static final int BYTE_BUFFER_LEN = 8192;
/*     */   public static final int CHAR_BUFFER_LEN = 2730;
/*     */   private static final String FAILURE_MSG = "Unable to parse HTML description: ";
/*     */   private static final String INVALID_MSG = " invalid";
/*     */   private long iHTMLStart;
/*     */   private long iHTMLEnd;
/*     */   private long iFragStart;
/*     */   private long iFragEnd;
/*     */   private long iSelStart;
/*     */   private long iSelEnd;
/*     */   private String stBaseURL;
/*     */   private String stVersion;
/*     */   private long iStartOffset;
/*     */   private long iEndOffset;
/*     */   private long iReadCount;
/*     */   private EHTMLReadMode readMode;
/*     */ 
/*     */   private static String toPaddedString(int n, int width)
/*     */   {
/*  97 */     String string = "" + n;
/*  98 */     int len = string.length();
/*  99 */     if ((n >= 0) && (len < width)) {
/* 100 */       char[] array = new char[width - len];
/* 101 */       Arrays.fill(array, '0');
/* 102 */       StringBuffer buffer = new StringBuffer(width);
/* 103 */       buffer.append(array);
/* 104 */       buffer.append(string);
/* 105 */       string = buffer.toString();
/*     */     }
/* 107 */     return string;
/*     */   }
/*     */ 
/*     */   public static byte[] convertToHTMLFormat(byte[] bytes)
/*     */   {
/* 139 */     String htmlPrefix = "";
/* 140 */     String htmlSuffix = "";
/*     */ 
/* 144 */     String stContext = new String(bytes);
/* 145 */     String stUpContext = stContext.toUpperCase();
/* 146 */     if (-1 == stUpContext.indexOf("<HTML")) {
/* 147 */       htmlPrefix = "<HTML>";
/* 148 */       htmlSuffix = "</HTML>";
/* 149 */       if (-1 == stUpContext.indexOf("<BODY")) {
/* 150 */         htmlPrefix = htmlPrefix + "<BODY>";
/* 151 */         htmlSuffix = "</BODY>" + htmlSuffix;
/*     */       }
/*     */     }
/* 154 */     htmlPrefix = htmlPrefix + "<!--StartFragment-->";
/* 155 */     htmlSuffix = "<!--EndFragment-->" + htmlSuffix;
/*     */ 
/* 158 */     String stBaseUrl = "about:blank";
/* 159 */     int nStartHTML = "Version:".length() + "1.0".length() + "\r\n".length() + "StartHTML:".length() + 10 + "\r\n".length() + "EndHTML:".length() + 10 + "\r\n".length() + "StartFragment:".length() + 10 + "\r\n".length() + "EndFragment:".length() + 10 + "\r\n".length() + "SourceURL:".length() + stBaseUrl.length() + "\r\n".length();
/*     */ 
/* 167 */     int nStartFragment = nStartHTML + htmlPrefix.length();
/* 168 */     int nEndFragment = nStartFragment + bytes.length - 1;
/* 169 */     int nEndHTML = nEndFragment + htmlSuffix.length();
/*     */ 
/* 171 */     StringBuilder header = new StringBuilder(nStartFragment + "<!--StartFragment-->".length());
/*     */ 
/* 176 */     header.append("Version:");
/* 177 */     header.append("1.0");
/* 178 */     header.append("\r\n");
/*     */ 
/* 180 */     header.append("StartHTML:");
/* 181 */     header.append(toPaddedString(nStartHTML, 10));
/* 182 */     header.append("\r\n");
/*     */ 
/* 184 */     header.append("EndHTML:");
/* 185 */     header.append(toPaddedString(nEndHTML, 10));
/* 186 */     header.append("\r\n");
/*     */ 
/* 188 */     header.append("StartFragment:");
/* 189 */     header.append(toPaddedString(nStartFragment, 10));
/* 190 */     header.append("\r\n");
/*     */ 
/* 192 */     header.append("EndFragment:");
/* 193 */     header.append(toPaddedString(nEndFragment, 10));
/* 194 */     header.append("\r\n");
/*     */ 
/* 196 */     header.append("SourceURL:");
/* 197 */     header.append(stBaseUrl);
/* 198 */     header.append("\r\n");
/*     */ 
/* 201 */     header.append(htmlPrefix);
/*     */ 
/* 203 */     byte[] headerBytes = null; byte[] trailerBytes = null;
/*     */     try
/*     */     {
/* 206 */       headerBytes = header.toString().getBytes("UTF-8");
/* 207 */       trailerBytes = htmlSuffix.getBytes("UTF-8");
/*     */     }
/*     */     catch (UnsupportedEncodingException cannotHappen) {
/*     */     }
/* 211 */     byte[] retval = new byte[headerBytes.length + bytes.length + trailerBytes.length];
/*     */ 
/* 214 */     System.arraycopy(headerBytes, 0, retval, 0, headerBytes.length);
/* 215 */     System.arraycopy(bytes, 0, retval, headerBytes.length, bytes.length - 1);
/*     */ 
/* 217 */     System.arraycopy(trailerBytes, 0, retval, headerBytes.length + bytes.length - 1, trailerBytes.length);
/*     */ 
/* 220 */     retval[(retval.length - 1)] = 0;
/*     */ 
/* 222 */     return retval;
/*     */   }
/*     */ 
/*     */   public HTMLCodec(InputStream _bytestream, EHTMLReadMode _readMode)
/*     */     throws IOException
/*     */   {
/* 265 */     this.bufferedStream = new BufferedInputStream(_bytestream, 8192);
/* 266 */     this.readMode = _readMode;
/*     */   }
/*     */ 
/*     */   public synchronized String getBaseURL() throws IOException
/*     */   {
/* 271 */     if (!this.descriptionParsed) {
/* 272 */       parseDescription();
/*     */     }
/* 274 */     return this.stBaseURL;
/*     */   }
/*     */ 
/*     */   public synchronized String getVersion() throws IOException {
/* 278 */     if (!this.descriptionParsed) {
/* 279 */       parseDescription();
/*     */     }
/* 281 */     return this.stVersion;
/*     */   }
/*     */ 
/*     */   private void parseDescription()
/*     */     throws IOException
/*     */   {
/* 290 */     this.stBaseURL = null;
/* 291 */     this.stVersion = null;
/*     */ 
/* 295 */     this.iHTMLEnd = (this.iHTMLStart = this.iFragEnd = this.iFragStart = this.iSelEnd = this.iSelStart = -1L);
/*     */ 
/* 302 */     this.bufferedStream.mark(8192);
/* 303 */     String[] astEntries = { "Version:", "StartHTML:", "EndHTML:", "StartFragment:", "EndFragment:", "StartSelection:", "EndSelection:", "SourceURL:" };
/*     */ 
/* 315 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.bufferedStream, "UTF-8"), 2730);
/*     */ 
/* 322 */     long iHeadSize = 0L;
/* 323 */     long iCRSize = "\r\n".length();
/* 324 */     int iEntCount = astEntries.length;
/* 325 */     boolean bContinue = true;
/*     */ 
/* 327 */     for (int iEntry = 0; iEntry < iEntCount; iEntry++) {
/* 328 */       String stLine = bufferedReader.readLine();
/* 329 */       if (null == stLine)
/*     */       {
/*     */         break;
/*     */       }
/* 333 */       for (; iEntry < iEntCount; iEntry++) {
/* 334 */         if (stLine.startsWith(astEntries[iEntry]))
/*     */         {
/* 337 */           iHeadSize += stLine.length() + iCRSize;
/* 338 */           String stValue = stLine.substring(astEntries[iEntry].length()).trim();
/* 339 */           if (null == stValue) break;
/*     */           try {
/* 341 */             switch (iEntry) {
/*     */             case 0:
/* 343 */               this.stVersion = stValue;
/* 344 */               break;
/*     */             case 1:
/* 346 */               this.iHTMLStart = Integer.parseInt(stValue);
/* 347 */               break;
/*     */             case 2:
/* 349 */               this.iHTMLEnd = Integer.parseInt(stValue);
/* 350 */               break;
/*     */             case 3:
/* 352 */               this.iFragStart = Integer.parseInt(stValue);
/* 353 */               break;
/*     */             case 4:
/* 355 */               this.iFragEnd = Integer.parseInt(stValue);
/* 356 */               break;
/*     */             case 5:
/* 358 */               this.iSelStart = Integer.parseInt(stValue);
/* 359 */               break;
/*     */             case 6:
/* 361 */               this.iSelEnd = Integer.parseInt(stValue);
/* 362 */               break;
/*     */             case 7:
/* 364 */               this.stBaseURL = stValue;
/*     */             }
/*     */           }
/*     */           catch (NumberFormatException e) {
/* 368 */             throw new IOException("Unable to parse HTML description: " + astEntries[iEntry] + " value " + e + " invalid");
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 376 */     if (-1L == this.iHTMLStart)
/* 377 */       this.iHTMLStart = iHeadSize;
/* 378 */     if (-1L == this.iFragStart)
/* 379 */       this.iFragStart = this.iHTMLStart;
/* 380 */     if (-1L == this.iFragEnd)
/* 381 */       this.iFragEnd = this.iHTMLEnd;
/* 382 */     if (-1L == this.iSelStart)
/* 383 */       this.iSelStart = this.iFragStart;
/* 384 */     if (-1L == this.iSelEnd) {
/* 385 */       this.iSelEnd = this.iFragEnd;
/*     */     }
/*     */ 
/* 388 */     switch (1.$SwitchMap$com$sun$glass$ui$win$EHTMLReadMode[this.readMode.ordinal()]) {
/*     */     case 1:
/* 390 */       this.iStartOffset = this.iHTMLStart;
/* 391 */       this.iEndOffset = this.iHTMLEnd;
/* 392 */       break;
/*     */     case 2:
/* 394 */       this.iStartOffset = this.iFragStart;
/* 395 */       this.iEndOffset = this.iFragEnd;
/* 396 */       break;
/*     */     case 3:
/*     */     default:
/* 399 */       this.iStartOffset = this.iSelStart;
/* 400 */       this.iEndOffset = this.iSelEnd;
/*     */     }
/*     */ 
/* 404 */     this.bufferedStream.reset();
/* 405 */     if (-1L == this.iStartOffset) {
/* 406 */       throw new IOException("Unable to parse HTML description: invalid HTML format.");
/*     */     }
/*     */ 
/* 409 */     int curOffset = 0;
/* 410 */     while (curOffset < this.iStartOffset) {
/* 411 */       curOffset = (int)(curOffset + this.bufferedStream.skip(this.iStartOffset - curOffset));
/*     */     }
/*     */ 
/* 414 */     this.iReadCount = curOffset;
/*     */ 
/* 416 */     if (this.iStartOffset != this.iReadCount) {
/* 417 */       throw new IOException("Unable to parse HTML description: Byte stream ends in description.");
/*     */     }
/* 419 */     this.descriptionParsed = true;
/*     */   }
/*     */ 
/*     */   public synchronized int read() throws IOException {
/* 423 */     if (this.closed) {
/* 424 */       throw new IOException("Stream closed");
/*     */     }
/*     */ 
/* 427 */     if (!this.descriptionParsed) {
/* 428 */       parseDescription();
/*     */     }
/* 430 */     if ((-1L != this.iEndOffset) && (this.iReadCount >= this.iEndOffset)) {
/* 431 */       return -1;
/*     */     }
/*     */ 
/* 434 */     int retval = this.bufferedStream.read();
/* 435 */     if (retval == -1) {
/* 436 */       return -1;
/*     */     }
/* 438 */     this.iReadCount += 1L;
/* 439 */     return retval;
/*     */   }
/*     */ 
/*     */   public synchronized void close() throws IOException {
/* 443 */     if (!this.closed) {
/* 444 */       this.closed = true;
/* 445 */       this.bufferedStream.close();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.HTMLCodec
 * JD-Core Version:    0.6.2
 */