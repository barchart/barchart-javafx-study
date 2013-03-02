/*     */ package com.sun.javafx.sg;
/*     */ 
/*     */ import java.nio.BufferOverflowException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class GrowableDataBuffer<T>
/*     */ {
/*     */   static final int MIN_BUF_GROW = 1024;
/*     */   static final int MIN_REF_GROW = 32;
/*     */   byte[] buf;
/*     */   T[] refs;
/*     */   int pos;
/*     */   int mark;
/*     */   int savepos;
/*     */   int refpos;
/*     */   int refmark;
/*     */   int refsavepos;
/*     */ 
/*     */   public GrowableDataBuffer(int paramInt)
/*     */   {
/*  47 */     this.buf = new byte[paramInt];
/*     */   }
/*     */ 
/*     */   public int position() {
/*  51 */     return this.pos;
/*     */   }
/*     */ 
/*     */   public void save() {
/*  55 */     this.savepos = this.pos;
/*  56 */     this.refsavepos = this.refpos;
/*     */   }
/*     */ 
/*     */   public void restore() {
/*  60 */     this.pos = this.savepos;
/*  61 */     this.refpos = this.refsavepos;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/*  65 */     return (this.pos >= this.mark) && (this.refpos >= this.refmark);
/*     */   }
/*     */ 
/*     */   public void switchToRead() {
/*  69 */     this.mark = this.pos;
/*  70 */     this.refmark = this.refpos;
/*  71 */     this.pos = 0;
/*  72 */     this.refpos = 0;
/*     */   }
/*     */ 
/*     */   public void resetForWrite() {
/*  76 */     this.pos = (this.mark = 0);
/*  77 */     if ((this.refpos > 0) || (this.refmark > 0)) {
/*  78 */       Arrays.fill(this.refs, 0, Math.max(this.refpos, this.refmark), null);
/*  79 */       this.refpos = (this.refmark = 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void ensureWriteCapacity(int paramInt) {
/*  84 */     if (this.pos + paramInt > this.buf.length) {
/*  85 */       if (paramInt < 1024) paramInt = 1024;
/*  86 */       this.buf = Arrays.copyOf(this.buf, this.pos + paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void ensureReadCapacity(int paramInt) {
/*  91 */     if (this.pos + paramInt > this.mark)
/*  92 */       throw new BufferOverflowException();
/*     */   }
/*     */ 
/*     */   public void putBoolean(boolean paramBoolean)
/*     */   {
/*  97 */     putByte((byte)(paramBoolean ? 1 : 0));
/*     */   }
/*     */ 
/*     */   public void putByte(byte paramByte) {
/* 101 */     ensureWriteCapacity(1);
/* 102 */     this.buf[(this.pos++)] = paramByte;
/*     */   }
/*     */ 
/*     */   public void putChar(char paramChar) {
/* 106 */     ensureWriteCapacity(2);
/* 107 */     this.buf[(this.pos++)] = ((byte)(paramChar >> '\b'));
/* 108 */     this.buf[(this.pos++)] = ((byte)paramChar);
/*     */   }
/*     */ 
/*     */   public void putShort(short paramShort) {
/* 112 */     ensureWriteCapacity(2);
/* 113 */     this.buf[(this.pos++)] = ((byte)(paramShort >> 8));
/* 114 */     this.buf[(this.pos++)] = ((byte)paramShort);
/*     */   }
/*     */ 
/*     */   public void putInt(int paramInt) {
/* 118 */     ensureWriteCapacity(4);
/* 119 */     this.buf[(this.pos++)] = ((byte)(paramInt >> 24));
/* 120 */     this.buf[(this.pos++)] = ((byte)(paramInt >> 16));
/* 121 */     this.buf[(this.pos++)] = ((byte)(paramInt >> 8));
/* 122 */     this.buf[(this.pos++)] = ((byte)paramInt);
/*     */   }
/*     */ 
/*     */   public void putLong(long paramLong) {
/* 126 */     ensureWriteCapacity(8);
/* 127 */     this.buf[(this.pos++)] = ((byte)(int)(paramLong >> 56));
/* 128 */     this.buf[(this.pos++)] = ((byte)(int)(paramLong >> 48));
/* 129 */     this.buf[(this.pos++)] = ((byte)(int)(paramLong >> 40));
/* 130 */     this.buf[(this.pos++)] = ((byte)(int)(paramLong >> 32));
/* 131 */     this.buf[(this.pos++)] = ((byte)(int)(paramLong >> 24));
/* 132 */     this.buf[(this.pos++)] = ((byte)(int)(paramLong >> 16));
/* 133 */     this.buf[(this.pos++)] = ((byte)(int)(paramLong >> 8));
/* 134 */     this.buf[(this.pos++)] = ((byte)(int)paramLong);
/*     */   }
/*     */ 
/*     */   public void putFloat(float paramFloat) {
/* 138 */     putInt(Float.floatToIntBits(paramFloat));
/*     */   }
/*     */ 
/*     */   public void putDouble(double paramDouble) {
/* 142 */     putLong(Double.doubleToLongBits(paramDouble));
/*     */   }
/*     */ 
/*     */   public void putObject(T paramT) {
/* 146 */     if (this.refs == null)
/* 147 */       this.refs = ((Object[])new Object[32]);
/* 148 */     else if (this.refpos >= this.refs.length) {
/* 149 */       this.refs = Arrays.copyOf(this.refs, this.refpos + 32);
/*     */     }
/* 151 */     this.refs[(this.refpos++)] = paramT;
/*     */   }
/*     */ 
/*     */   public boolean getBoolean() {
/* 155 */     ensureReadCapacity(1);
/* 156 */     return this.buf[(this.pos++)] != 0;
/*     */   }
/*     */ 
/*     */   public byte getByte() {
/* 160 */     ensureReadCapacity(1);
/* 161 */     return this.buf[(this.pos++)];
/*     */   }
/*     */ 
/*     */   public int getUByte() {
/* 165 */     ensureReadCapacity(1);
/* 166 */     return this.buf[(this.pos++)] & 0xFF;
/*     */   }
/*     */ 
/*     */   public char getChar() {
/* 170 */     ensureReadCapacity(2);
/* 171 */     int i = this.buf[(this.pos++)];
/* 172 */     i = i << 8 | this.buf[(this.pos++)] & 0xFF;
/* 173 */     return (char)i;
/*     */   }
/*     */ 
/*     */   public short getShort() {
/* 177 */     ensureReadCapacity(2);
/* 178 */     int i = this.buf[(this.pos++)];
/* 179 */     i = i << 8 | this.buf[(this.pos++)] & 0xFF;
/* 180 */     return (short)i;
/*     */   }
/*     */ 
/*     */   public int getInt() {
/* 184 */     ensureReadCapacity(4);
/* 185 */     int i = this.buf[(this.pos++)];
/* 186 */     i = i << 8 | this.buf[(this.pos++)] & 0xFF;
/* 187 */     i = i << 8 | this.buf[(this.pos++)] & 0xFF;
/* 188 */     i = i << 8 | this.buf[(this.pos++)] & 0xFF;
/* 189 */     return i;
/*     */   }
/*     */ 
/*     */   public long getLong() {
/* 193 */     ensureReadCapacity(8);
/* 194 */     long l = this.buf[(this.pos++)];
/* 195 */     l = l << 8 | this.buf[(this.pos++)] & 0xFF;
/* 196 */     l = l << 8 | this.buf[(this.pos++)] & 0xFF;
/* 197 */     l = l << 8 | this.buf[(this.pos++)] & 0xFF;
/* 198 */     l = l << 8 | this.buf[(this.pos++)] & 0xFF;
/* 199 */     l = l << 8 | this.buf[(this.pos++)] & 0xFF;
/* 200 */     l = l << 8 | this.buf[(this.pos++)] & 0xFF;
/* 201 */     l = l << 8 | this.buf[(this.pos++)] & 0xFF;
/* 202 */     return l;
/*     */   }
/*     */ 
/*     */   public float getFloat() {
/* 206 */     return Float.intBitsToFloat(getInt());
/*     */   }
/*     */ 
/*     */   public double getDouble() {
/* 210 */     return Double.longBitsToDouble(getLong());
/*     */   }
/*     */ 
/*     */   public T getObject() {
/* 214 */     if ((this.refs == null) || (this.refpos >= this.refs.length)) {
/* 215 */       throw new BufferOverflowException();
/*     */     }
/* 217 */     return this.refs[(this.refpos++)];
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.GrowableDataBuffer
 * JD-Core Version:    0.6.2
 */