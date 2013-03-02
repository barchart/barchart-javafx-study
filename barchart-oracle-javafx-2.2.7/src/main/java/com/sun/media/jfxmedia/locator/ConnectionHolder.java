/*     */ package com.sun.media.jfxmedia.locator;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.JarURLConnection;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class ConnectionHolder
/*     */ {
/*  30 */   private static int DEFAULT_BUFFER_SIZE = 4096;
/*     */   ReadableByteChannel channel;
/*     */   ByteBuffer buffer;
/*     */ 
/*     */   public ConnectionHolder()
/*     */   {
/*  33 */     this.buffer = ByteBuffer.allocateDirect(DEFAULT_BUFFER_SIZE);
/*     */   }
/*     */   static ConnectionHolder createMemoryConnectionHolder(ByteBuffer buffer) {
/*  36 */     return new MemoryConnectionHolder(buffer);
/*     */   }
/*     */ 
/*     */   static ConnectionHolder createURIConnectionHolder(URI uri, Map<String, Object> connectionProperties) throws IOException {
/*  40 */     return new URIConnectionHolder(uri, connectionProperties);
/*     */   }
/*     */ 
/*     */   static ConnectionHolder createFileConnectionHolder(URI uri) throws IOException {
/*  44 */     return new FileConnectionHolder(uri);
/*     */   }
/*     */ 
/*     */   static ConnectionHolder createHLSConnectionHolder(URI uri) throws IOException {
/*  48 */     return new HLSConnectionHolder(uri);
/*     */   }
/*     */ 
/*     */   public int readNextBlock()
/*     */     throws IOException
/*     */   {
/*  61 */     this.buffer.rewind();
/*  62 */     if (this.buffer.limit() < this.buffer.capacity()) {
/*  63 */       this.buffer.limit(this.buffer.capacity());
/*     */     }
/*     */ 
/*  66 */     if (null == this.channel) {
/*  67 */       throw new ClosedChannelException();
/*     */     }
/*  69 */     return this.channel.read(this.buffer);
/*     */   }
/*     */ 
/*     */   public ByteBuffer getBuffer() {
/*  73 */     return this.buffer;
/*     */   }
/*     */ 
/*     */   abstract int readBlock(long paramLong, int paramInt)
/*     */     throws IOException;
/*     */ 
/*     */   abstract boolean needBuffer();
/*     */ 
/*     */   abstract boolean isSeekable();
/*     */ 
/*     */   abstract boolean isRandomAccess();
/*     */ 
/*     */   public abstract long seek(long paramLong);
/*     */ 
/*     */   public void closeConnection()
/*     */   {
/*     */     try
/*     */     {
/* 122 */       if (this.channel != null)
/* 123 */         this.channel.close();
/*     */     } catch (IOException ioex) {
/*     */     }
/*     */     finally {
/* 127 */       this.channel = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   int property(int prop, int value)
/*     */   {
/* 139 */     return 0;
/*     */   }
/*     */ 
/*     */   int getStreamSize()
/*     */   {
/* 150 */     return -1;
/*     */   }
/*     */ 
/*     */   private static class MemoryConnectionHolder extends ConnectionHolder
/*     */   {
/*     */     private final ByteBuffer backingBuffer;
/*     */ 
/*     */     public MemoryConnectionHolder(ByteBuffer buf)
/*     */     {
/* 342 */       if (null == buf) {
/* 343 */         throw new IllegalArgumentException("Can't connect to null buffer...");
/*     */       }
/*     */ 
/* 346 */       if (buf.isDirect())
/*     */       {
/* 348 */         this.backingBuffer = buf.duplicate();
/*     */       }
/*     */       else {
/* 351 */         this.backingBuffer = ByteBuffer.allocateDirect(buf.capacity());
/* 352 */         this.backingBuffer.put(buf);
/*     */       }
/*     */ 
/* 356 */       this.backingBuffer.rewind();
/*     */ 
/* 360 */       this.channel = new ReadableByteChannel() {
/*     */         public int read(ByteBuffer bb) throws IOException {
/* 362 */           if (ConnectionHolder.MemoryConnectionHolder.this.backingBuffer.remaining() <= 0)
/* 363 */             return -1;
/*     */           int actual;
/* 367 */           if (bb.equals(ConnectionHolder.MemoryConnectionHolder.this.buffer))
/*     */           {
/* 370 */             int actual = Math.min(ConnectionHolder.DEFAULT_BUFFER_SIZE, ConnectionHolder.MemoryConnectionHolder.this.backingBuffer.remaining());
/* 371 */             if (actual > 0) {
/* 372 */               ConnectionHolder.MemoryConnectionHolder.this.buffer = ConnectionHolder.MemoryConnectionHolder.this.backingBuffer.slice();
/* 373 */               ConnectionHolder.MemoryConnectionHolder.this.buffer.limit(actual);
/*     */             }
/*     */           } else {
/* 376 */             actual = Math.min(bb.remaining(), ConnectionHolder.MemoryConnectionHolder.this.backingBuffer.remaining());
/* 377 */             if (actual > 0) {
/* 378 */               ConnectionHolder.MemoryConnectionHolder.this.backingBuffer.limit(ConnectionHolder.MemoryConnectionHolder.this.backingBuffer.position() + actual);
/* 379 */               bb.put(ConnectionHolder.MemoryConnectionHolder.this.backingBuffer);
/* 380 */               ConnectionHolder.MemoryConnectionHolder.this.backingBuffer.limit(ConnectionHolder.MemoryConnectionHolder.this.backingBuffer.capacity());
/*     */             }
/*     */           }
/* 383 */           return actual;
/*     */         }
/*     */ 
/*     */         public boolean isOpen() {
/* 387 */           return true;
/*     */         }
/*     */ 
/*     */         public void close()
/*     */           throws IOException
/*     */         {
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     int readBlock(long position, int size) throws IOException
/*     */     {
/* 399 */       if (null == this.channel) {
/* 400 */         throw new ClosedChannelException();
/*     */       }
/*     */ 
/* 403 */       if ((int)position > this.backingBuffer.capacity()) {
/* 404 */         return -1;
/*     */       }
/* 406 */       this.backingBuffer.position((int)position);
/*     */ 
/* 408 */       this.buffer = this.backingBuffer.slice();
/*     */ 
/* 410 */       int actual = Math.min(this.backingBuffer.remaining(), size);
/* 411 */       this.buffer.limit(actual);
/* 412 */       this.backingBuffer.position(this.backingBuffer.position() + actual);
/*     */ 
/* 414 */       return actual;
/*     */     }
/*     */ 
/*     */     boolean needBuffer()
/*     */     {
/* 419 */       return false;
/*     */     }
/*     */ 
/*     */     boolean isSeekable()
/*     */     {
/* 424 */       return true;
/*     */     }
/*     */ 
/*     */     boolean isRandomAccess()
/*     */     {
/* 429 */       return true;
/*     */     }
/*     */ 
/*     */     public long seek(long position)
/*     */     {
/* 434 */       if ((int)position < this.backingBuffer.capacity()) {
/* 435 */         this.backingBuffer.limit(this.backingBuffer.capacity());
/* 436 */         this.backingBuffer.position((int)position);
/* 437 */         return position;
/*     */       }
/* 439 */       return -1L;
/*     */     }
/*     */ 
/*     */     public void closeConnection()
/*     */     {
/* 445 */       this.channel = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class URIConnectionHolder extends ConnectionHolder
/*     */   {
/*     */     private URI uri;
/*     */     private URLConnection urlConnection;
/*     */ 
/*     */     URIConnectionHolder(URI uri, Map<String, Object> connectionProperties)
/*     */       throws IOException
/*     */     {
/* 215 */       this.uri = uri;
/* 216 */       this.urlConnection = uri.toURL().openConnection();
/* 217 */       if (connectionProperties != null) {
/* 218 */         for (String key : connectionProperties.keySet()) {
/* 219 */           Object value = connectionProperties.get(key);
/* 220 */           if ((value instanceof String)) {
/* 221 */             this.urlConnection.setRequestProperty(key, (String)value);
/*     */           }
/*     */         }
/*     */       }
/* 225 */       this.channel = openChannel(null);
/*     */     }
/*     */ 
/*     */     boolean needBuffer() {
/* 229 */       String scheme = this.uri.getScheme().toLowerCase();
/* 230 */       return "http".equals(scheme);
/*     */     }
/*     */ 
/*     */     boolean isSeekable() {
/* 234 */       return ((this.urlConnection instanceof HttpURLConnection)) || ((this.urlConnection instanceof JarURLConnection));
/*     */     }
/*     */ 
/*     */     boolean isRandomAccess() {
/* 238 */       return false;
/*     */     }
/*     */ 
/*     */     int readBlock(long position, int size) throws IOException {
/* 242 */       throw new IOException();
/*     */     }
/*     */ 
/*     */     public long seek(long position) {
/* 246 */       if ((this.urlConnection instanceof HttpURLConnection)) {
/* 247 */         closeConnection();
/*     */         try {
/* 249 */           InputStream inputStream = null;
/*     */ 
/* 251 */           this.urlConnection = this.uri.toURL().openConnection();
/*     */ 
/* 253 */           HttpURLConnection httpConnection = (HttpURLConnection)this.urlConnection;
/* 254 */           httpConnection.setRequestMethod("GET");
/* 255 */           httpConnection.setUseCaches(false);
/* 256 */           httpConnection.setRequestProperty("Range", "bytes=" + position + "-");
/*     */ 
/* 258 */           if (httpConnection.getResponseCode() != 206)
/*     */           {
/* 261 */             if (httpConnection.getResponseCode() != 200) {
/* 262 */               httpConnection.disconnect();
/* 263 */               this.urlConnection = this.uri.toURL().openConnection();
/*     */             }
/*     */ 
/* 266 */             long skip_left = position;
/* 267 */             inputStream = this.urlConnection.getInputStream();
/*     */             do {
/* 269 */               long skip = inputStream.skip(skip_left);
/* 270 */               skip_left -= skip;
/* 271 */             }while (skip_left > 0L);
/*     */           }
/*     */ 
/* 274 */           this.channel = openChannel(inputStream);
/* 275 */           return position;
/*     */         } catch (IOException ioex) {
/* 277 */           return -1L;
/*     */         }
/*     */       }
/* 280 */       if ((this.urlConnection instanceof JarURLConnection)) {
/*     */         try {
/* 282 */           closeConnection();
/*     */ 
/* 284 */           this.urlConnection = this.uri.toURL().openConnection();
/*     */ 
/* 287 */           long skip_left = position;
/* 288 */           InputStream inputStream = this.urlConnection.getInputStream();
/*     */           do {
/* 290 */             long skip = inputStream.skip(skip_left);
/* 291 */             skip_left -= skip;
/* 292 */           }while (skip_left > 0L);
/*     */ 
/* 294 */           this.channel = openChannel(inputStream);
/*     */ 
/* 296 */           return position;
/*     */         } catch (IOException ioex) {
/* 298 */           return -1L;
/*     */         }
/*     */       }
/*     */ 
/* 302 */       return -1L;
/*     */     }
/*     */ 
/*     */     public void closeConnection()
/*     */     {
/* 307 */       super.closeConnection();
/*     */ 
/* 309 */       if ((this.urlConnection instanceof HttpURLConnection)) {
/* 310 */         ((HttpURLConnection)this.urlConnection).disconnect();
/*     */       }
/* 312 */       this.urlConnection = null;
/*     */     }
/*     */ 
/*     */     private ReadableByteChannel openChannel(final InputStream inputStream) throws IOException {
/* 316 */       ReadableByteChannel ch = null;
/*     */       try
/*     */       {
/* 319 */         ch = (ReadableByteChannel)AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*     */           public ReadableByteChannel run() throws IOException {
/* 321 */             if (inputStream == null) {
/* 322 */               return Channels.newChannel(ConnectionHolder.URIConnectionHolder.this.urlConnection.getInputStream());
/*     */             }
/* 324 */             return Channels.newChannel(inputStream);
/*     */           }
/*     */         });
/*     */       }
/*     */       catch (PrivilegedActionException e) {
/* 329 */         throw ((IOException)e.getException());
/*     */       }
/*     */ 
/* 332 */       return ch;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class FileConnectionHolder extends ConnectionHolder
/*     */   {
/*     */     FileConnectionHolder(URI uri)
/*     */       throws IOException
/*     */     {
/* 155 */       this.channel = openFile(uri);
/*     */     }
/*     */ 
/*     */     boolean needBuffer() {
/* 159 */       return false;
/*     */     }
/*     */ 
/*     */     boolean isRandomAccess() {
/* 163 */       return true;
/*     */     }
/*     */ 
/*     */     boolean isSeekable() {
/* 167 */       return true;
/*     */     }
/*     */ 
/*     */     public long seek(long position) {
/*     */       try {
/* 172 */         ((FileChannel)this.channel).position(position);
/* 173 */         return position; } catch (IOException ioex) {
/*     */       }
/* 175 */       return -1L;
/*     */     }
/*     */ 
/*     */     int readBlock(long position, int size) throws IOException
/*     */     {
/* 180 */       if (null == this.channel) {
/* 181 */         throw new ClosedChannelException();
/*     */       }
/*     */ 
/* 184 */       if (this.buffer.capacity() < size) {
/* 185 */         this.buffer = ByteBuffer.allocateDirect(size);
/*     */       }
/* 187 */       this.buffer.rewind().limit(size);
/* 188 */       return ((FileChannel)this.channel).read(this.buffer, position);
/*     */     }
/*     */ 
/*     */     private ReadableByteChannel openFile(final URI uri) throws IOException {
/* 192 */       ReadableByteChannel result = null;
/*     */       try
/*     */       {
/* 195 */         result = (ReadableByteChannel)AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*     */           public ReadableByteChannel run() throws IOException {
/* 197 */             RandomAccessFile file = new RandomAccessFile(new File(uri), "r");
/* 198 */             return file.getChannel();
/*     */           }
/*     */         });
/*     */       }
/*     */       catch (PrivilegedActionException e) {
/* 203 */         throw ((IOException)e.getException());
/*     */       }
/*     */ 
/* 206 */       return result;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.locator.ConnectionHolder
 * JD-Core Version:    0.6.2
 */