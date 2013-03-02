/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public abstract class Pixels
/*     */ {
/*     */   protected final int width;
/*     */   protected final int height;
/*     */   protected final int bytesPerComponent;
/*     */   protected final ByteBuffer bytes;
/*     */   protected final IntBuffer ints;
/*     */ 
/*     */   public static int getNativeFormat()
/*     */   {
/*  36 */     return Application.GetApplication().staticPixels_getNativeFormat();
/*     */   }
/*     */ 
/*     */   protected Pixels(int width, int height, ByteBuffer pixels)
/*     */   {
/*  59 */     this.width = width;
/*  60 */     this.height = height;
/*  61 */     this.bytesPerComponent = 1;
/*  62 */     this.bytes = pixels.slice();
/*  63 */     if ((this.width <= 0) || (this.height <= 0) || (this.width * this.height * 4 > this.bytes.capacity())) {
/*  64 */       throw new IllegalArgumentException("Too small byte buffer size " + this.width + "x" + this.height + " [" + this.width * this.height * 4 + "] > " + this.bytes.capacity());
/*     */     }
/*     */ 
/*  67 */     this.ints = null;
/*     */   }
/*     */ 
/*     */   protected Pixels(int width, int height, IntBuffer pixels) {
/*  71 */     this.width = width;
/*  72 */     this.height = height;
/*  73 */     this.bytesPerComponent = 4;
/*  74 */     this.ints = pixels.slice();
/*  75 */     if ((this.width <= 0) || (this.height <= 0) || (this.width * this.height > this.ints.capacity())) {
/*  76 */       throw new IllegalArgumentException("Too small int buffer size " + this.width + "x" + this.height + " [" + this.width * this.height + "] > " + this.ints.capacity());
/*     */     }
/*     */ 
/*  79 */     this.bytes = null;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/*  83 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/*  87 */     return this.height;
/*     */   }
/*     */ 
/*     */   public int getBytesPerComponent() {
/*  91 */     return this.bytesPerComponent;
/*     */   }
/*     */ 
/*     */   public Buffer getPixels()
/*     */   {
/*  98 */     if (this.bytes != null) {
/*  99 */       this.bytes.rewind();
/* 100 */       return this.bytes;
/* 101 */     }if (this.ints != null) {
/* 102 */       this.ints.rewind();
/* 103 */       return this.ints;
/*     */     }
/* 105 */     throw new RuntimeException("Unexpected Pixels state.");
/*     */   }
/*     */ 
/*     */   public ByteBuffer asByteBuffer()
/*     */   {
/* 113 */     ByteBuffer bb = ByteBuffer.allocateDirect(getWidth() * getHeight() * 4);
/* 114 */     bb.order(ByteOrder.nativeOrder());
/* 115 */     bb.rewind();
/* 116 */     asByteBuffer(bb);
/* 117 */     return bb;
/*     */   }
/*     */ 
/*     */   public void asByteBuffer(ByteBuffer bb)
/*     */   {
/* 125 */     if (!bb.isDirect())
/* 126 */       throw new RuntimeException("Expected direct buffer.");
/* 127 */     if (bb.remaining() < getWidth() * getHeight() * 4) {
/* 128 */       throw new RuntimeException("Too small buffer.");
/*     */     }
/* 130 */     _fillDirectByteBuffer(bb);
/*     */   }
/*     */ 
/*     */   private void attachData(long ptr)
/*     */   {
/* 135 */     if (this.ints != null) {
/* 136 */       int[] array = !this.ints.isDirect() ? this.ints.array() : null;
/* 137 */       _attachInt(ptr, this.width, this.height, this.ints, array, array != null ? this.ints.arrayOffset() : 0);
/*     */     }
/* 139 */     if (this.bytes != null) {
/* 140 */       byte[] array = !this.bytes.isDirect() ? this.bytes.array() : null;
/* 141 */       _attachByte(ptr, this.width, this.height, this.bytes, array, array != null ? this.bytes.arrayOffset() : 0);
/*     */     }
/*     */   }
/*     */   protected abstract void _fillDirectByteBuffer(ByteBuffer paramByteBuffer);
/*     */ 
/*     */   protected abstract void _attachInt(long paramLong, int paramInt1, int paramInt2, IntBuffer paramIntBuffer, int[] paramArrayOfInt, int paramInt3);
/*     */ 
/*     */   protected abstract void _attachByte(long paramLong, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, byte[] paramArrayOfByte, int paramInt3);
/*     */ 
/* 150 */   public boolean equals(Object object) { boolean equals = (object != null) && (getClass().equals(object.getClass()) == true);
/* 151 */     if (equals == true) {
/* 152 */       Pixels pixels = (Pixels)object;
/* 153 */       equals = (getWidth() == pixels.getWidth()) && (getHeight() == pixels.getHeight());
/* 154 */       if (equals == true) {
/* 155 */         ByteBuffer b1 = asByteBuffer();
/* 156 */         ByteBuffer b2 = pixels.asByteBuffer();
/* 157 */         equals = b1.compareTo(b2) == 0;
/*     */       }
/*     */     }
/* 160 */     return equals; }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 164 */     int val = getWidth();
/* 165 */     val = 31 * val + getHeight();
/* 166 */     val = 17 * val + asByteBuffer().hashCode();
/* 167 */     return val;
/*     */   }
/*     */ 
/*     */   public static class Format
/*     */   {
/*     */     public static final int BYTE_BGRA_PRE = 1;
/*     */     public static final int BYTE_ARGB = 2;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Pixels
 * JD-Core Version:    0.6.2
 */