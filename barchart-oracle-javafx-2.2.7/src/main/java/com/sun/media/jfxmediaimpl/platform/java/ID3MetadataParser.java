/*     */ package com.sun.media.jfxmediaimpl.platform.java;
/*     */ 
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmedia.logging.Logger;
/*     */ import com.sun.media.jfxmediaimpl.MetadataParserImpl;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class ID3MetadataParser extends MetadataParserImpl
/*     */ {
/*     */   private static final int ID3_VERSION_MIN = 2;
/*     */   private static final int ID3_VERSION_MAX = 4;
/*  73 */   private int COMMCount = 0;
/*  74 */   private int TXXXCount = 0;
/*  75 */   private int version = 3;
/*  76 */   private boolean unsynchronized = false;
/*     */ 
/*     */   public ID3MetadataParser(Locator locator) {
/*  79 */     super(locator);
/*     */   }
/*     */ 
/*     */   protected void parse()
/*     */   {
/*     */     try
/*     */     {
/*  97 */       byte[] buf = getBytes(10);
/*  98 */       this.version = (buf[3] & 0xFF);
/*  99 */       if ((buf[0] == 73) && (buf[1] == 68) && (buf[2] == 51) && (this.version >= 2) && (this.version <= 4))
/*     */       {
/* 101 */         int flags = buf[5] & 0xFF;
/* 102 */         if ((flags & 0x80) == 128) {
/* 103 */           this.unsynchronized = true;
/*     */         }
/*     */ 
/* 106 */         int tagSize = 0;
/* 107 */         int i = 6; for (int shift = 21; i < 10; i++) {
/* 108 */           tagSize += ((buf[i] & 0x7F) << shift);
/* 109 */           shift -= 7;
/*     */         }
/*     */ 
/* 112 */         startRawMetadata(tagSize + 10);
/* 113 */         stuffRawMetadata(buf, 0, 10);
/* 114 */         readRawMetadata(tagSize);
/* 115 */         setParseRawMetadata(true);
/* 116 */         skipBytes(10);
/*     */ 
/* 118 */         while (getStreamPosition() < tagSize) {
/* 119 */           String frameID = "";
/* 120 */           int frameSize = 0;
/*     */ 
/* 122 */           byte[] idBytes = null;
/* 123 */           if (2 == this.version)
/*     */           {
/* 125 */             idBytes = getBytes(3);
/* 126 */             frameSize = getU24();
/*     */           } else {
/* 128 */             idBytes = getBytes(4);
/* 129 */             frameSize = getFrameSize();
/* 130 */             skipBytes(2);
/*     */           }
/*     */ 
/* 133 */           if (0 == idBytes[0])
/*     */           {
/* 135 */             if (!Logger.canLog(1)) break;
/* 136 */             Logger.logMsg(1, "ID3MetadataParser", "parse", "ID3 parser: zero padding detected at " + getStreamPosition() + ", terminating"); break;
/*     */           }
/*     */ 
/* 142 */           frameID = new String(idBytes);
/*     */ 
/* 144 */           if (Logger.canLog(1)) {
/* 145 */             Logger.logMsg(1, "ID3MetadataParser", "parse", getStreamPosition() + "\\" + tagSize + ": frame ID " + frameID + ", size " + frameSize);
/*     */           }
/*     */ 
/* 149 */           if ((frameID.equals("APIC")) || (frameID.equals("PIC"))) {
/* 150 */             byte[] data = getBytes(frameSize);
/* 151 */             if (this.unsynchronized) {
/* 152 */               data = unsynchronizeBuffer(data);
/*     */             }
/* 154 */             byte[] image = null;
/* 155 */             if (frameID.equals("PIC"))
/* 156 */               image = getImageFromPIC(data);
/*     */             else {
/* 158 */               image = getImageFromAPIC(data);
/*     */             }
/* 160 */             if (image != null)
/* 161 */               addMetadataItem("image", image);
/*     */           }
/* 163 */           else if ((frameID.startsWith("T")) && (!frameID.equals("TXXX"))) {
/* 164 */             String encoding = getEncoding();
/* 165 */             byte[] data = getBytes(frameSize - 1);
/* 166 */             if (this.unsynchronized) {
/* 167 */               data = unsynchronizeBuffer(data);
/*     */             }
/* 169 */             String value = new String(data, encoding);
/* 170 */             String[] tag = getTagFromFrameID(frameID);
/* 171 */             if (tag != null) {
/* 172 */               for (int i = 0; i < tag.length; i++) {
/* 173 */                 Object tagValue = convertValue(tag[i], value);
/* 174 */                 if (tagValue != null)
/* 175 */                   addMetadataItem(tag[i], tagValue);
/*     */               }
/*     */             }
/*     */           }
/* 179 */           else if ((frameID.equals("COMM")) || (frameID.equals("COM"))) {
/* 180 */             String encoding = getEncoding();
/*     */ 
/* 182 */             byte[] data = getBytes(3);
/* 183 */             if (this.unsynchronized) {
/* 184 */               data = unsynchronizeBuffer(data);
/*     */             }
/* 186 */             String language = new String(data);
/*     */ 
/* 188 */             data = getBytes(frameSize - 4);
/* 189 */             if (this.unsynchronized) {
/* 190 */               data = unsynchronizeBuffer(data);
/*     */             }
/* 192 */             String value = new String(data, encoding);
/* 193 */             if (value != null) {
/* 194 */               int index = value.indexOf(0);
/* 195 */               String content = "";
/* 196 */               String comment = "";
/* 197 */               if (index == 0) {
/* 198 */                 if (encoding.equals("UTF-16"))
/* 199 */                   comment = value.substring(2);
/*     */                 else
/* 201 */                   comment = value.substring(1);
/*     */               }
/*     */               else {
/* 204 */                 content = value.substring(0, index);
/* 205 */                 if (encoding.equals("UTF-16"))
/* 206 */                   comment = value.substring(index + 2);
/*     */                 else {
/* 208 */                   comment = value.substring(index + 1);
/*     */                 }
/*     */               }
/* 211 */               String[] tag = getTagFromFrameID(frameID);
/* 212 */               if (tag != null)
/* 213 */                 for (int i = 0; i < tag.length; i++) {
/* 214 */                   addMetadataItem(tag[i] + "-" + this.COMMCount, content + "[" + language + "]=" + comment);
/* 215 */                   this.COMMCount += 1;
/*     */                 }
/*     */             }
/*     */           }
/* 219 */           else if ((frameID.equals("TXX")) || (frameID.equals("TXXX"))) {
/* 220 */             String encoding = getEncoding();
/* 221 */             byte[] data = getBytes(frameSize - 1);
/* 222 */             if (this.unsynchronized) {
/* 223 */               data = unsynchronizeBuffer(data);
/*     */             }
/* 225 */             String value = new String(data, encoding);
/* 226 */             if (null != value) {
/* 227 */               int index = value.indexOf(0);
/* 228 */               String description = "";
/* 229 */               String text = "";
/* 230 */               if (index != 0)
/*     */               {
/* 232 */                 description = value.substring(0, index);
/*     */               }
/* 234 */               if (encoding.equals("UTF-16"))
/* 235 */                 text = value.substring(index + 2);
/*     */               else {
/* 237 */                 text = value.substring(index + 1);
/*     */               }
/* 239 */               String[] tag = getTagFromFrameID(frameID);
/* 240 */               if (tag != null)
/* 241 */                 for (int i = 0; i < tag.length; i++) {
/* 242 */                   if (description.equals(""))
/* 243 */                     addMetadataItem(tag[i] + "-" + this.TXXXCount, text);
/*     */                   else {
/* 245 */                     addMetadataItem(tag[i] + "-" + this.TXXXCount, description + "=" + text);
/*     */                   }
/* 247 */                   this.TXXXCount += 1;
/*     */                 }
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 253 */             skipBytes(frameSize);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 260 */       if (Logger.canLog(3))
/* 261 */         Logger.logMsg(3, "ID3MetadataParser", "parse", "Exception while processing ID3v2 metadata: " + ex);
/*     */     }
/*     */     finally
/*     */     {
/* 265 */       if (null != this.rawMetaBlob) {
/* 266 */         setParseRawMetadata(false);
/* 267 */         addRawMetadata("ID3");
/* 268 */         disposeRawMetadata();
/*     */       }
/* 270 */       done();
/*     */     }
/*     */   }
/*     */ 
/*     */   private int getFrameSize() throws IOException {
/* 275 */     if (this.version == 4) {
/* 276 */       byte[] buf = getBytes(4);
/* 277 */       int size = 0;
/* 278 */       int i = 0; for (int shift = 21; i < 4; i++) {
/* 279 */         size += ((buf[i] & 0x7F) << shift);
/* 280 */         shift -= 7;
/*     */       }
/* 282 */       return size;
/*     */     }
/* 284 */     return getInteger();
/*     */   }
/*     */ 
/*     */   private String getEncoding() throws IOException
/*     */   {
/* 289 */     byte encodingType = getNextByte();
/* 290 */     if (encodingType == 0)
/* 291 */       return "ISO-8859-1";
/* 292 */     if (encodingType == 1)
/* 293 */       return "UTF-16";
/* 294 */     if (encodingType == 2)
/* 295 */       return "UTF-16BE";
/* 296 */     if (encodingType == 3) {
/* 297 */       return "UTF-8";
/*     */     }
/* 299 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   private String[] getTagFromFrameID(String frameID)
/*     */   {
/* 320 */     if ((frameID.equals("TPE2")) || (frameID.equals("TP2")))
/* 321 */       return new String[] { "album artist" };
/* 322 */     if ((frameID.equals("TALB")) || (frameID.equals("TAL")))
/* 323 */       return new String[] { "album" };
/* 324 */     if ((frameID.equals("TPE1")) || (frameID.equals("TP1")))
/* 325 */       return new String[] { "artist" };
/* 326 */     if ((frameID.equals("COMM")) || (frameID.equals("COM")))
/* 327 */       return new String[] { "comment" };
/* 328 */     if ((frameID.equals("TCOM")) || (frameID.equals("TCM")))
/* 329 */       return new String[] { "composer" };
/* 330 */     if ((frameID.equals("TLEN")) || (frameID.equals("TLE")))
/* 331 */       return new String[] { "duration" };
/* 332 */     if ((frameID.equals("TCON")) || (frameID.equals("TCO")))
/* 333 */       return new String[] { "genre" };
/* 334 */     if ((frameID.equals("TIT2")) || (frameID.equals("TT2")))
/* 335 */       return new String[] { "title" };
/* 336 */     if ((frameID.equals("TRCK")) || (frameID.equals("TRK")))
/* 337 */       return new String[] { "track number", "track count" };
/* 338 */     if ((frameID.equals("TPOS")) || (frameID.equals("TPA")))
/* 339 */       return new String[] { "disc number", "disc count" };
/* 340 */     if ((frameID.equals("TYER")) || (frameID.equals("TDRC")))
/* 341 */       return new String[] { "year" };
/* 342 */     if ((frameID.equals("TXX")) || (frameID.equals("TXXX"))) {
/* 343 */       return new String[] { "text" };
/*     */     }
/*     */ 
/* 346 */     return null;
/*     */   }
/*     */ 
/*     */   private byte[] getImageFromPIC(byte[] data)
/*     */   {
/* 361 */     int imgOffset = 5;
/* 362 */     while ((0 != data[imgOffset]) && (imgOffset < data.length)) {
/* 363 */       imgOffset++;
/*     */     }
/* 365 */     if (imgOffset == data.length)
/*     */     {
/* 367 */       return null;
/*     */     }
/*     */ 
/* 370 */     String type = new String(data, 1, 3);
/* 371 */     if (Logger.canLog(1)) {
/* 372 */       Logger.logMsg(1, "ID3MetadataParser", "getImageFromPIC", "PIC type: " + type);
/*     */     }
/*     */ 
/* 376 */     if ((type.equalsIgnoreCase("PNG")) || (type.equalsIgnoreCase("JPG")))
/*     */     {
/* 378 */       return Arrays.copyOfRange(data, imgOffset + 1, data.length);
/*     */     }
/* 380 */     if (Logger.canLog(3)) {
/* 381 */       Logger.logMsg(3, "ID3MetadataParser", "getImageFromPIC", "Unsupported picture type found \"" + type + "\"");
/*     */     }
/*     */ 
/* 384 */     return null;
/*     */   }
/*     */ 
/*     */   private byte[] getImageFromAPIC(byte[] data) {
/* 388 */     boolean isImageJPEG = false;
/* 389 */     boolean isImagePNG = false;
/*     */ 
/* 392 */     int maxIndex = data.length - 10;
/* 393 */     int offset = 0;
/* 394 */     for (int j = 0; j < maxIndex; j++) {
/* 395 */       if ((data[j] == 105) && (data[(j + 1)] == 109) && (data[(j + 2)] == 97) && (data[(j + 3)] == 103) && (data[(j + 4)] == 101) && (data[(j + 5)] == 47))
/*     */       {
/* 402 */         j += 6;
/*     */ 
/* 405 */         if ((data[j] == 106) && (data[(j + 1)] == 112) && (data[(j + 2)] == 101) && (data[(j + 3)] == 103))
/*     */         {
/* 410 */           isImageJPEG = true;
/* 411 */           offset = j + 4;
/* 412 */           break;
/*     */         }
/* 414 */         if ((data[j] == 112) && (data[(j + 1)] == 110) && (data[(j + 2)] == 103))
/*     */         {
/* 418 */           isImagePNG = true;
/* 419 */           offset = j + 3;
/* 420 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 425 */     if (isImageJPEG)
/*     */     {
/* 427 */       boolean isSignatureFound = false;
/* 428 */       int upperBound = data.length - 1;
/*     */ 
/* 430 */       for (int j = offset; j < upperBound; j++)
/*     */       {
/* 432 */         if ((-1 == data[j]) && (-40 == data[(j + 1)]))
/*     */         {
/* 434 */           isSignatureFound = true;
/* 435 */           offset = j;
/* 436 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 440 */       if (isSignatureFound)
/*     */       {
/* 442 */         return Arrays.copyOfRange(data, offset, data.length);
/*     */       }
/*     */     }
/*     */ 
/* 446 */     if (isImagePNG)
/*     */     {
/* 448 */       boolean isSignatureFound = false;
/* 449 */       int upperBound = data.length - 7;
/*     */ 
/* 451 */       for (int j = offset; j < upperBound; j++)
/*     */       {
/* 453 */         if ((-119 == data[j]) && (80 == data[(j + 1)]) && (78 == data[(j + 2)]) && (71 == data[(j + 3)]) && (13 == data[(j + 4)]) && (10 == data[(j + 5)]) && (26 == data[(j + 6)]) && (10 == data[(j + 7)]))
/*     */         {
/* 463 */           isSignatureFound = true;
/* 464 */           offset = j;
/* 465 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 469 */       if (isSignatureFound)
/*     */       {
/* 471 */         return Arrays.copyOfRange(data, offset, data.length);
/*     */       }
/*     */     }
/* 474 */     return null;
/*     */   }
/*     */ 
/*     */   private byte[] unsynchronizeBuffer(byte[] data) {
/* 478 */     byte[] udata = new byte[data.length];
/* 479 */     int udatalen = 0;
/*     */ 
/* 481 */     for (int i = 0; i < data.length; i++) {
/* 482 */       if ((((data[i] & 0xFF) == 255) && (data[(i + 1)] == 0) && (data[(i + 2)] == 0)) || (((data[i] & 0xFF) == 255) && (data[(i + 1)] == 0) && ((data[(i + 2)] & 0xE0) == 224)))
/*     */       {
/* 484 */         udata[udatalen] = data[i];
/* 485 */         udatalen++;
/* 486 */         udata[udatalen] = data[(i + 2)];
/* 487 */         udatalen++;
/* 488 */         i += 2;
/*     */       } else {
/* 490 */         udata[udatalen] = data[i];
/* 491 */         udatalen++;
/*     */       }
/*     */     }
/*     */ 
/* 495 */     return Arrays.copyOf(udata, udatalen);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.java.ID3MetadataParser
 * JD-Core Version:    0.6.2
 */