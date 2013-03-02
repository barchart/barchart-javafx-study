/*     */ package com.sun.media.jfxmediaimpl;
/*     */ 
/*     */ import com.sun.media.jfxmedia.MetadataParser;
/*     */ import com.sun.media.jfxmedia.events.MetadataListener;
/*     */ import com.sun.media.jfxmedia.locator.ConnectionHolder;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class MetadataParserImpl extends Thread
/*     */   implements MetadataParser
/*     */ {
/*  83 */   private String[] FLV_VIDEO_CODEC_NAME = { "Unsupported", "JPEG Video (Unsupported)", "Sorenson H.263 Video", "Flash Screen Video", "On2 VP6 Video", "On2 VP6-Alpha Video", "Unsupported", "H.264 Video", "Unsupported", "Unsupported", "Unsupported", "Unsupported", "Unsupported", "Unsupported", "Unsupported", "Unsupported" };
/*     */ 
/*  90 */   private final List<WeakReference<MetadataListener>> listeners = new ArrayList();
/*  91 */   private Map<String, Object> metadata = new HashMap();
/*  92 */   private Locator locator = null;
/*  93 */   private ConnectionHolder connectionHolder = null;
/*  94 */   private ByteBuffer buffer = null;
/*  95 */   private Map<String, ByteBuffer> rawMetaMap = null;
/*  96 */   protected ByteBuffer rawMetaBlob = null;
/*  97 */   private boolean parsingRawMetadata = false;
/*  98 */   private int length = 0;
/*  99 */   private int index = 0;
/* 100 */   private int streamPosition = 0;
/*     */ 
/*     */   public MetadataParserImpl(Locator locator) {
/* 103 */     this.locator = locator;
/*     */   }
/*     */ 
/*     */   public void addListener(MetadataListener listener) {
/* 107 */     synchronized (this.listeners) {
/* 108 */       if (listener != null)
/* 109 */         this.listeners.add(new WeakReference(listener));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeListener(MetadataListener listener)
/*     */   {
/*     */     ListIterator it;
/* 115 */     synchronized (this.listeners) {
/* 116 */       if (listener != null)
/* 117 */         for (it = this.listeners.listIterator(); it.hasNext(); ) {
/* 118 */           MetadataListener l = (MetadataListener)((WeakReference)it.next()).get();
/* 119 */           if ((l == null) || (l == listener))
/* 120 */             it.remove();
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startParser()
/*     */     throws IOException
/*     */   {
/* 128 */     start();
/*     */   }
/*     */ 
/*     */   public void stopParser() {
/* 132 */     if (this.connectionHolder != null)
/* 133 */       this.connectionHolder.closeConnection();
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/* 140 */       this.connectionHolder = this.locator.createConnectionHolder();
/* 141 */       parse();
/*     */     } catch (IOException e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract void parse();
/*     */ 
/*     */   protected void addMetadataItem(String tag, Object value) {
/* 149 */     this.metadata.put(tag, value);
/*     */   }
/*     */ 
/*     */   protected void done()
/*     */   {
/*     */     ListIterator it;
/* 153 */     synchronized (this.listeners) {
/* 154 */       if (!this.metadata.isEmpty())
/* 155 */         for (it = this.listeners.listIterator(); it.hasNext(); ) {
/* 156 */           MetadataListener l = (MetadataListener)((WeakReference)it.next()).get();
/* 157 */           if (l != null)
/* 158 */             l.onMetadata(this.metadata);
/*     */           else
/* 160 */             it.remove();
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int getStreamPosition()
/*     */   {
/* 168 */     if (this.parsingRawMetadata) {
/* 169 */       return this.rawMetaBlob.position();
/*     */     }
/* 171 */     return this.streamPosition;
/*     */   }
/*     */ 
/*     */   protected void startRawMetadata(int sizeHint) {
/* 175 */     this.rawMetaBlob = ByteBuffer.allocate(sizeHint);
/*     */   }
/*     */ 
/*     */   private void adjustRawMetadataSize(int addSize)
/*     */   {
/* 180 */     if (this.rawMetaBlob.remaining() < addSize) {
/* 181 */       int pos = this.rawMetaBlob.position();
/* 182 */       int newSize = pos + addSize;
/* 183 */       ByteBuffer newBuffer = ByteBuffer.allocate(newSize);
/* 184 */       this.rawMetaBlob.position(0);
/* 185 */       newBuffer.put(this.rawMetaBlob.array(), 0, pos);
/* 186 */       this.rawMetaBlob = newBuffer;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void readRawMetadata(int size) throws IOException
/*     */   {
/* 192 */     byte[] data = getBytes(size);
/* 193 */     adjustRawMetadataSize(size);
/* 194 */     if (null != data)
/* 195 */       this.rawMetaBlob.put(data);
/*     */   }
/*     */ 
/*     */   protected void stuffRawMetadata(byte[] data, int offset, int size)
/*     */   {
/* 201 */     if (null != this.rawMetaBlob) {
/* 202 */       adjustRawMetadataSize(size);
/* 203 */       this.rawMetaBlob.put(data, offset, size);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void disposeRawMetadata() {
/* 208 */     this.parsingRawMetadata = false;
/* 209 */     this.rawMetaBlob = null;
/*     */   }
/*     */ 
/*     */   protected void setParseRawMetadata(boolean state)
/*     */   {
/* 214 */     if (null == this.rawMetaBlob) {
/* 215 */       this.parsingRawMetadata = false;
/* 216 */       return;
/*     */     }
/*     */ 
/* 219 */     if (state) {
/* 220 */       this.rawMetaBlob.position(0);
/*     */     }
/* 222 */     this.parsingRawMetadata = state;
/*     */   }
/*     */ 
/*     */   protected void addRawMetadata(String type)
/*     */   {
/* 227 */     if (null == this.rawMetaBlob) {
/* 228 */       return;
/*     */     }
/*     */ 
/* 231 */     if (null == this.rawMetaMap) {
/* 232 */       this.rawMetaMap = new HashMap();
/*     */ 
/* 234 */       this.metadata.put("raw metadata", Collections.unmodifiableMap(this.rawMetaMap));
/*     */     }
/* 236 */     this.rawMetaMap.put(type, this.rawMetaBlob.asReadOnlyBuffer());
/*     */   }
/*     */ 
/*     */   protected void skipBytes(int num) throws IOException, EOFException {
/* 240 */     if (this.parsingRawMetadata) {
/* 241 */       this.rawMetaBlob.position(this.rawMetaBlob.position() + num);
/* 242 */       return;
/*     */     }
/*     */ 
/* 245 */     for (int i = 0; i < num; i++)
/* 246 */       getNextByte();
/*     */   }
/*     */ 
/*     */   protected byte getNextByte() throws IOException, EOFException
/*     */   {
/* 251 */     if (this.parsingRawMetadata)
/*     */     {
/* 253 */       return this.rawMetaBlob.get();
/*     */     }
/*     */ 
/* 256 */     if (this.buffer == null) {
/* 257 */       this.buffer = this.connectionHolder.getBuffer();
/* 258 */       this.length = this.connectionHolder.readNextBlock();
/*     */     }
/*     */ 
/* 261 */     if (this.index >= this.length) {
/* 262 */       this.length = this.connectionHolder.readNextBlock();
/* 263 */       if (this.length < 1) {
/* 264 */         throw new EOFException();
/*     */       }
/* 266 */       this.index = 0;
/*     */     }
/*     */ 
/* 269 */     byte b = this.buffer.get(this.index);
/* 270 */     this.index += 1;
/* 271 */     this.streamPosition += 1;
/* 272 */     return b;
/*     */   }
/*     */ 
/*     */   protected byte[] getBytes(int size) throws IOException, EOFException {
/* 276 */     byte[] bytes = new byte[size];
/*     */ 
/* 278 */     if (this.parsingRawMetadata) {
/* 279 */       this.rawMetaBlob.get(bytes);
/* 280 */       return bytes;
/*     */     }
/*     */ 
/* 283 */     for (int i = 0; i < size; i++) {
/* 284 */       bytes[i] = getNextByte();
/*     */     }
/*     */ 
/* 287 */     return bytes;
/*     */   }
/*     */ 
/*     */   protected long getLong() throws IOException, EOFException {
/* 291 */     if (this.parsingRawMetadata) {
/* 292 */       return this.rawMetaBlob.getLong();
/*     */     }
/*     */ 
/* 295 */     long value = 0L;
/*     */ 
/* 297 */     value |= getNextByte() & 0xFF;
/* 298 */     value <<= 8;
/* 299 */     value |= getNextByte() & 0xFF;
/* 300 */     value <<= 8;
/* 301 */     value |= getNextByte() & 0xFF;
/* 302 */     value <<= 8;
/* 303 */     value |= getNextByte() & 0xFF;
/* 304 */     value <<= 8;
/* 305 */     value |= getNextByte() & 0xFF;
/* 306 */     value <<= 8;
/* 307 */     value |= getNextByte() & 0xFF;
/* 308 */     value <<= 8;
/* 309 */     value |= getNextByte() & 0xFF;
/* 310 */     value <<= 8;
/* 311 */     value |= getNextByte() & 0xFF;
/*     */ 
/* 313 */     return value;
/*     */   }
/*     */ 
/*     */   protected int getInteger() throws IOException, EOFException {
/* 317 */     if (this.parsingRawMetadata) {
/* 318 */       return this.rawMetaBlob.getInt();
/*     */     }
/*     */ 
/* 321 */     int value = 0;
/*     */ 
/* 323 */     value |= getNextByte() & 0xFF;
/* 324 */     value <<= 8;
/* 325 */     value |= getNextByte() & 0xFF;
/* 326 */     value <<= 8;
/* 327 */     value |= getNextByte() & 0xFF;
/* 328 */     value <<= 8;
/* 329 */     value |= getNextByte() & 0xFF;
/*     */ 
/* 331 */     return value;
/*     */   }
/*     */ 
/*     */   protected short getShort() throws IOException, EOFException {
/* 335 */     if (this.parsingRawMetadata) {
/* 336 */       return this.rawMetaBlob.getShort();
/*     */     }
/*     */ 
/* 339 */     short value = 0;
/*     */ 
/* 341 */     value = (short)(value | getNextByte() & 0xFF);
/* 342 */     value = (short)(value << 8);
/* 343 */     value = (short)(value | getNextByte() & 0xFF);
/*     */ 
/* 345 */     return value;
/*     */   }
/*     */ 
/*     */   protected double getDouble() throws IOException, EOFException {
/* 349 */     if (this.parsingRawMetadata) {
/* 350 */       return this.rawMetaBlob.getDouble();
/*     */     }
/*     */ 
/* 353 */     double value = 0.0D;
/* 354 */     long bits = getLong();
/* 355 */     value = Double.longBitsToDouble(bits);
/* 356 */     return value;
/*     */   }
/*     */ 
/*     */   protected String getString(int length) throws IOException, EOFException {
/* 360 */     String value = "";
/* 361 */     byte[] bytes = getBytes(length);
/* 362 */     value = new String(bytes, 0, length);
/* 363 */     return value;
/*     */   }
/*     */ 
/*     */   protected int getU24() throws IOException, EOFException {
/* 367 */     int value = 0;
/*     */ 
/* 369 */     value |= getNextByte() & 0xFF;
/* 370 */     value <<= 8;
/* 371 */     value |= getNextByte() & 0xFF;
/* 372 */     value <<= 8;
/* 373 */     value |= getNextByte() & 0xFF;
/*     */ 
/* 375 */     return value;
/*     */   }
/*     */ 
/*     */   protected Object convertValue(String tag, Object value)
/*     */   {
/* 380 */     if ((tag.equals("duration")) && ((value instanceof Double))) {
/* 381 */       Double v = Double.valueOf(((Double)value).doubleValue() * 1000.0D);
/* 382 */       return Long.valueOf(v.longValue());
/* 383 */     }if ((tag.equals("duration")) && ((value instanceof String))) {
/* 384 */       String v = (String)value;
/* 385 */       return Long.valueOf(v.trim());
/* 386 */     }if ((tag.equals("width")) || (tag.equals("height"))) {
/* 387 */       Double v = (Double)value;
/* 388 */       return Integer.valueOf(v.intValue());
/* 389 */     }if (tag.equals("framerate"))
/* 390 */       return value;
/* 391 */     if (tag.equals("videocodecid"))
/*     */     {
/* 393 */       int codecid = ((Double)value).intValue();
/* 394 */       if (codecid < this.FLV_VIDEO_CODEC_NAME.length) {
/* 395 */         return this.FLV_VIDEO_CODEC_NAME[codecid];
/*     */       }
/* 397 */       return null;
/*     */     }
/* 399 */     if (tag.equals("audiocodecid"))
/*     */     {
/* 401 */       return "MPEG 1 Audio";
/* 402 */     }if (tag.equals("creationdate"))
/* 403 */       return ((String)value).trim();
/* 404 */     if ((tag.equals("track number")) || (tag.equals("disc number"))) {
/* 405 */       String[] v = ((String)value).split("/");
/* 406 */       if (v.length == 2)
/* 407 */         return Integer.valueOf(v[0].trim());
/*     */     }
/* 409 */     else if ((tag.equals("track count")) || (tag.equals("disc count"))) {
/* 410 */       String[] tc = ((String)value).split("/");
/* 411 */       if (tc.length == 2)
/* 412 */         return Integer.valueOf(tc[1].trim());
/*     */     } else {
/* 414 */       if (tag.equals("album"))
/* 415 */         return value;
/* 416 */       if (tag.equals("artist"))
/* 417 */         return value;
/* 418 */       if (tag.equals("genre"))
/* 419 */         return value;
/* 420 */       if (tag.equals("title"))
/* 421 */         return value;
/* 422 */       if (tag.equals("album artist"))
/* 423 */         return value;
/* 424 */       if (tag.equals("comment"))
/* 425 */         return value;
/* 426 */       if (tag.equals("composer"))
/* 427 */         return value;
/* 428 */       if (tag.equals("year")) {
/* 429 */         String v = (String)value;
/* 430 */         return Integer.valueOf(v.trim());
/*     */       }
/*     */     }
/* 433 */     return null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.MetadataParserImpl
 * JD-Core Version:    0.6.2
 */