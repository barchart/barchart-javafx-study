/*     */ package com.sun.media.jfxmediaimpl.platform.java;
/*     */ 
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmedia.logging.Logger;
/*     */ import com.sun.media.jfxmediaimpl.MetadataParserImpl;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class FLVMetadataParser extends MetadataParserImpl
/*     */ {
/*  68 */   private int dataSize = 0;
/*     */ 
/*     */   public FLVMetadataParser(Locator locator) {
/*  71 */     super(locator);
/*     */   }
/*     */ 
/*     */   protected void parse()
/*     */   {
/*     */     try {
/*  77 */       if ((getNextByte() == 70) && (getNextByte() == 76) && (getNextByte() == 86)) {
/*  78 */         skipBytes(2);
/*  79 */         int dataOffset = getInteger();
/*  80 */         skipBytes(dataOffset - 9);
/*     */ 
/*  82 */         int tagCount = 0;
/*  83 */         for (tagCount = 0; tagCount < 10; tagCount++) {
/*  84 */           skipBytes(4);
/*     */ 
/*  86 */           byte tagType = getNextByte();
/*  87 */           this.dataSize = getU24();
/*  88 */           skipBytes(7);
/*     */ 
/*  90 */           if (tagType == 18) {
/*  91 */             int expectedEndPosition = getStreamPosition() + this.dataSize;
/*     */ 
/*  93 */             if (parseDataTag())
/*     */             {
/*     */               break;
/*     */             }
/*     */ 
/*  98 */             if (getStreamPosition() < expectedEndPosition)
/*  99 */               skipBytes(expectedEndPosition - getStreamPosition());
/*     */           }
/*     */           else {
/* 102 */             skipBytes(this.dataSize);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean parseDataTag() throws IOException {
/* 112 */     if (this.dataSize < 14) {
/* 113 */       return false;
/*     */     }
/*     */ 
/* 117 */     byte[] header = new byte[14];
/* 118 */     for (int ii = 0; ii < 14; ii++) {
/* 119 */       header[ii] = getNextByte();
/*     */     }
/*     */ 
/* 122 */     if (header[0] != 2) {
/* 123 */       return false;
/*     */     }
/*     */ 
/* 126 */     int nameSize = (header[1] & 0xFF) << 8 | header[2] & 0xFF;
/* 127 */     if (nameSize != 10) {
/* 128 */       return false;
/*     */     }
/* 130 */     String methodName = new String(header, 3, nameSize);
/* 131 */     if (!methodName.equals("onMetaData")) {
/* 132 */       return false;
/*     */     }
/*     */ 
/* 136 */     if (header[13] != 8) {
/* 137 */       if (Logger.canLog(3)) {
/* 138 */         Logger.logMsg(3, "FLV metadata must be in an ECMA array");
/*     */       }
/* 140 */       return false;
/*     */     }
/*     */ 
/* 145 */     startRawMetadata(this.dataSize);
/* 146 */     if (null == this.rawMetaBlob) {
/* 147 */       if (Logger.canLog(1)) {
/* 148 */         Logger.logMsg(1, "Unable to allocate buffer for FLV metadata");
/*     */       }
/* 150 */       return false;
/*     */     }
/*     */ 
/* 154 */     stuffRawMetadata(header, 0, 14);
/* 155 */     readRawMetadata(this.dataSize - 14);
/*     */ 
/* 158 */     setParseRawMetadata(true);
/* 159 */     skipBytes(14);
/*     */     try
/*     */     {
/* 164 */       int arrayCount = getInteger();
/* 165 */       int parseCount = 0;
/* 166 */       boolean done = false;
/* 167 */       boolean warnMalformed = false;
/*     */       do
/*     */       {
/* 170 */         String attribute = getString(getShort());
/* 171 */         FlvDataValue flvValue = readDataValue(false);
/* 172 */         parseCount++;
/* 173 */         String tag = convertTag(attribute);
/* 174 */         if ((Logger.canLog(1)) && (!attribute.equals(""))) {
/* 175 */           Logger.logMsg(1, parseCount + ": \"" + attribute + "\" -> " + (null == tag ? "(unsupported)" : new StringBuilder().append("\"").append(tag).append("\"").toString()));
/*     */         }
/*     */ 
/* 178 */         if (tag != null) {
/* 179 */           Object value = convertValue(attribute, flvValue.obj);
/* 180 */           if (value != null) {
/* 181 */             addMetadataItem(tag, value);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 192 */         if (parseCount >= arrayCount)
/*     */         {
/* 194 */           if (getStreamPosition() < this.dataSize) {
/* 195 */             if ((!warnMalformed) && (Logger.canLog(3))) {
/* 196 */               Logger.logMsg(3, "FLV Source has malformed metadata, invalid ECMA element count");
/* 197 */               warnMalformed = true;
/*     */             }
/*     */           }
/* 200 */           else done = true;
/*     */         }
/*     */       }
/* 203 */       while (!done);
/*     */     }
/*     */     catch (Exception e) {
/* 206 */       if (Logger.canLog(3))
/* 207 */         Logger.logMsg(3, "Exception while processing FLV metadata: " + e);
/*     */     }
/*     */     finally {
/* 210 */       if (null != this.rawMetaBlob) {
/* 211 */         setParseRawMetadata(false);
/* 212 */         addRawMetadata("FLV");
/* 213 */         disposeRawMetadata();
/*     */       }
/* 215 */       done();
/*     */     }
/*     */ 
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   private FlvDataValue readDataValue(boolean hasName) throws IOException {
/* 222 */     FlvDataValue sdv = new FlvDataValue(null);
/*     */ 
/* 224 */     if (hasName) {
/* 225 */       skipBytes(getShort());
/*     */     }
/*     */ 
/* 228 */     sdv.type = getNextByte();
/* 229 */     switch (sdv.type) {
/*     */     case 0:
/* 231 */       sdv.obj = Double.valueOf(getDouble());
/* 232 */       break;
/*     */     case 1:
/* 234 */       boolean b = getNextByte() != 0;
/* 235 */       sdv.obj = Boolean.valueOf(b);
/* 236 */       break;
/*     */     case 2:
/* 238 */       sdv.obj = getString(getShort());
/* 239 */       break;
/*     */     case 3:
/* 241 */       skipObject();
/* 242 */       break;
/*     */     case 4:
/* 244 */       getString(getShort());
/* 245 */       break;
/*     */     case 5:
/* 247 */       break;
/*     */     case 6:
/* 249 */       break;
/*     */     case 7:
/* 251 */       skipBytes(2);
/* 252 */       break;
/*     */     case 8:
/* 254 */       skipArray();
/* 255 */       break;
/*     */     case 9:
/* 257 */       sdv.scriptDataObjectEnd = true;
/* 258 */       break;
/*     */     case 10:
/* 260 */       skipStrictArray();
/* 261 */       break;
/*     */     case 11:
/* 263 */       sdv.obj = Double.valueOf(getDouble());
/* 264 */       skipBytes(2);
/* 265 */       break;
/*     */     case 12:
/* 267 */       sdv.obj = getString(getInteger());
/* 268 */       break;
/*     */     }
/*     */ 
/* 273 */     return sdv;
/*     */   }
/*     */ 
/*     */   private void skipObject() throws IOException
/*     */   {
/*     */     FlvDataValue value;
/*     */     do
/* 280 */       value = readDataValue(true);
/* 281 */     while (!value.scriptDataObjectEnd);
/*     */   }
/*     */ 
/*     */   private void skipArray()
/*     */     throws IOException
/*     */   {
/* 287 */     int arrayCount = getInteger();
/* 288 */     int parseCount = 0;
/*     */     do {
/* 290 */       readDataValue(true);
/*     */     }
/* 292 */     while (parseCount < arrayCount);
/*     */   }
/*     */ 
/*     */   private void skipStrictArray() throws IOException {
/* 296 */     long arrayLen = getInteger();
/*     */ 
/* 298 */     for (int i = 0; i < arrayLen; i++)
/* 299 */       readDataValue(false);
/*     */   }
/*     */ 
/*     */   private String convertTag(String tag)
/*     */   {
/* 304 */     if (tag.equals("duration"))
/* 305 */       return "duration";
/* 306 */     if (tag.equals("width"))
/* 307 */       return "width";
/* 308 */     if (tag.equals("height"))
/* 309 */       return "height";
/* 310 */     if (tag.equals("framerate"))
/* 311 */       return "framerate";
/* 312 */     if (tag.equals("videocodecid"))
/* 313 */       return "video codec";
/* 314 */     if (tag.equals("audiocodecid"))
/* 315 */       return "audio codec";
/* 316 */     if (tag.equals("creationdate")) {
/* 317 */       return "creationdate";
/*     */     }
/*     */ 
/* 320 */     return null;
/*     */   }
/*     */ 
/*     */   private class FlvDataValue
/*     */   {
/*     */     static final byte NUMBER = 0;
/*     */     static final byte BOOLEAN = 1;
/*     */     static final byte STRING = 2;
/*     */     static final byte OBJECT = 3;
/*     */     static final byte MOVIE_CLIP = 4;
/*     */     static final byte NULL = 5;
/*     */     static final byte UNDEFINED = 6;
/*     */     static final byte REFERENCE = 7;
/*     */     static final byte ECMA_ARRAY = 8;
/*     */     static final byte END_OF_DATA = 9;
/*     */     static final byte STRICT_ARRAY = 10;
/*     */     static final byte DATE = 11;
/*     */     static final byte LONG_STRING = 12;
/* 337 */     boolean scriptDataObjectEnd = false;
/*     */     Object obj;
/*     */     byte type;
/*     */ 
/*     */     private FlvDataValue()
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.java.FLVMetadataParser
 * JD-Core Version:    0.6.2
 */