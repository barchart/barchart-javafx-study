/*     */ package com.sun.prism.es2.gl;
/*     */ 
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.LongBuffer;
/*     */ import java.nio.ShortBuffer;
/*     */ 
/*     */ public class BufferFactory
/*     */ {
/*     */   public static final int SIZEOF_BYTE = 1;
/*     */   public static final int SIZEOF_SHORT = 2;
/*     */   public static final int SIZEOF_CHAR = 2;
/*     */   public static final int SIZEOF_INT = 4;
/*     */   public static final int SIZEOF_FLOAT = 4;
/*     */   public static final int SIZEOF_LONG = 8;
/*     */   public static final int SIZEOF_DOUBLE = 8;
/*  21 */   private static final boolean isLittleEndian = 3085 == localShortBuffer.get(0);
/*     */ 
/*     */   public static boolean isLittleEndian()
/*     */   {
/*  25 */     return isLittleEndian;
/*     */   }
/*     */ 
/*     */   public static ByteBuffer newDirectByteBuffer(int paramInt)
/*     */   {
/*  30 */     return nativeOrder(ByteBuffer.allocateDirect(paramInt));
/*     */   }
/*     */ 
/*     */   public static ByteBuffer nativeOrder(ByteBuffer paramByteBuffer)
/*     */   {
/*  37 */     return paramByteBuffer.order(ByteOrder.nativeOrder());
/*     */   }
/*     */ 
/*     */   public static boolean isDirect(Object paramObject)
/*     */   {
/*  44 */     if (paramObject == null) {
/*  45 */       return true;
/*     */     }
/*  47 */     if ((paramObject instanceof ByteBuffer))
/*  48 */       return ((ByteBuffer)paramObject).isDirect();
/*  49 */     if ((paramObject instanceof FloatBuffer))
/*  50 */       return ((FloatBuffer)paramObject).isDirect();
/*  51 */     if ((paramObject instanceof DoubleBuffer))
/*  52 */       return ((DoubleBuffer)paramObject).isDirect();
/*  53 */     if ((paramObject instanceof CharBuffer))
/*  54 */       return ((CharBuffer)paramObject).isDirect();
/*  55 */     if ((paramObject instanceof ShortBuffer))
/*  56 */       return ((ShortBuffer)paramObject).isDirect();
/*  57 */     if ((paramObject instanceof IntBuffer))
/*  58 */       return ((IntBuffer)paramObject).isDirect();
/*  59 */     if ((paramObject instanceof LongBuffer)) {
/*  60 */       return ((LongBuffer)paramObject).isDirect();
/*     */     }
/*  62 */     throw new RuntimeException("Unexpected buffer type " + paramObject.getClass().getName());
/*     */   }
/*     */ 
/*     */   public static int getDirectBufferByteOffset(Object paramObject)
/*     */   {
/*  70 */     if (paramObject == null) {
/*  71 */       return 0;
/*     */     }
/*  73 */     if ((paramObject instanceof Buffer)) {
/*  74 */       int i = ((Buffer)paramObject).position();
/*  75 */       if ((paramObject instanceof ByteBuffer))
/*  76 */         return i;
/*  77 */       if ((paramObject instanceof FloatBuffer))
/*  78 */         return i * 4;
/*  79 */       if ((paramObject instanceof IntBuffer))
/*  80 */         return i * 4;
/*  81 */       if ((paramObject instanceof ShortBuffer))
/*  82 */         return i * 2;
/*  83 */       if ((paramObject instanceof DoubleBuffer))
/*  84 */         return i * 8;
/*  85 */       if ((paramObject instanceof LongBuffer))
/*  86 */         return i * 8;
/*  87 */       if ((paramObject instanceof CharBuffer)) {
/*  88 */         return i * 2;
/*     */       }
/*     */     }
/*     */ 
/*  92 */     throw new RuntimeException("Disallowed array backing store type in buffer " + paramObject.getClass().getName());
/*     */   }
/*     */ 
/*     */   public static Object getArray(Object paramObject)
/*     */   {
/* 100 */     if (paramObject == null) {
/* 101 */       return null;
/*     */     }
/* 103 */     if ((paramObject instanceof ByteBuffer))
/* 104 */       return ((ByteBuffer)paramObject).array();
/* 105 */     if ((paramObject instanceof FloatBuffer))
/* 106 */       return ((FloatBuffer)paramObject).array();
/* 107 */     if ((paramObject instanceof IntBuffer))
/* 108 */       return ((IntBuffer)paramObject).array();
/* 109 */     if ((paramObject instanceof ShortBuffer))
/* 110 */       return ((ShortBuffer)paramObject).array();
/* 111 */     if ((paramObject instanceof DoubleBuffer))
/* 112 */       return ((DoubleBuffer)paramObject).array();
/* 113 */     if ((paramObject instanceof LongBuffer))
/* 114 */       return ((LongBuffer)paramObject).array();
/* 115 */     if ((paramObject instanceof CharBuffer)) {
/* 116 */       return ((CharBuffer)paramObject).array();
/*     */     }
/* 118 */     throw new RuntimeException("Disallowed array backing store type in buffer " + paramObject.getClass().getName());
/*     */   }
/*     */ 
/*     */   public static int getIndirectBufferByteOffset(Object paramObject)
/*     */   {
/* 128 */     if (paramObject == null) {
/* 129 */       return 0;
/*     */     }
/* 131 */     if ((paramObject instanceof Buffer)) {
/* 132 */       int i = ((Buffer)paramObject).position();
/* 133 */       if ((paramObject instanceof ByteBuffer))
/* 134 */         return ((ByteBuffer)paramObject).arrayOffset() + i;
/* 135 */       if ((paramObject instanceof FloatBuffer))
/* 136 */         return 4 * (((FloatBuffer)paramObject).arrayOffset() + i);
/* 137 */       if ((paramObject instanceof IntBuffer))
/* 138 */         return 4 * (((IntBuffer)paramObject).arrayOffset() + i);
/* 139 */       if ((paramObject instanceof ShortBuffer))
/* 140 */         return 2 * (((ShortBuffer)paramObject).arrayOffset() + i);
/* 141 */       if ((paramObject instanceof DoubleBuffer))
/* 142 */         return 8 * (((DoubleBuffer)paramObject).arrayOffset() + i);
/* 143 */       if ((paramObject instanceof LongBuffer))
/* 144 */         return 8 * (((LongBuffer)paramObject).arrayOffset() + i);
/* 145 */       if ((paramObject instanceof CharBuffer)) {
/* 146 */         return 2 * (((CharBuffer)paramObject).arrayOffset() + i);
/*     */       }
/*     */     }
/* 149 */     throw new RuntimeException("Unknown buffer type " + paramObject.getClass().getName());
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  17 */     ByteBuffer localByteBuffer = newDirectByteBuffer(4);
/*  18 */     IntBuffer localIntBuffer = localByteBuffer.asIntBuffer();
/*  19 */     ShortBuffer localShortBuffer = localByteBuffer.asShortBuffer();
/*  20 */     localIntBuffer.put(0, 168496141);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.gl.BufferFactory
 * JD-Core Version:    0.6.2
 */