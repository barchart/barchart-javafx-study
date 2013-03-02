/*     */ package com.sun.prism.impl;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.ShortBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ 
/*     */ public class BufferUtil
/*     */ {
/*     */   public static final int SIZEOF_BYTE = 1;
/*     */   public static final int SIZEOF_SHORT = 2;
/*     */   public static final int SIZEOF_INT = 4;
/*     */   public static final int SIZEOF_FLOAT = 4;
/*     */   public static final int SIZEOF_DOUBLE = 8;
/*     */   private static boolean isCDCFP;
/*     */   private static Class byteOrderClass;
/*     */   private static Object nativeOrderObject;
/*     */   private static Method orderMethod;
/*     */ 
/*     */   public static void nativeOrder(ByteBuffer paramByteBuffer)
/*     */   {
/*  59 */     if (!isCDCFP) {
/*     */       try {
/*  61 */         if (byteOrderClass == null) {
/*  62 */           byteOrderClass = (Class)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */           {
/*     */             public Object run() throws ClassNotFoundException {
/*  65 */               return Class.forName("java.nio.ByteOrder", true, null);
/*     */             }
/*     */           });
/*  68 */           orderMethod = ByteBuffer.class.getMethod("order", new Class[] { byteOrderClass });
/*  69 */           Method localMethod = byteOrderClass.getMethod("nativeOrder", (Class[])null);
/*  70 */           nativeOrderObject = localMethod.invoke(null, (Object[])null);
/*     */         }
/*     */       }
/*     */       catch (Throwable localThrowable1) {
/*  74 */         isCDCFP = true;
/*     */       }
/*     */ 
/*  77 */       if (!isCDCFP)
/*     */         try {
/*  79 */           orderMethod.invoke(paramByteBuffer, new Object[] { nativeOrderObject });
/*     */         }
/*     */         catch (Throwable localThrowable2)
/*     */         {
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ByteBuffer newByteBuffer(int paramInt)
/*     */   {
/*  92 */     ByteBuffer localByteBuffer = ByteBuffer.allocateDirect(paramInt);
/*  93 */     nativeOrder(localByteBuffer);
/*  94 */     return localByteBuffer;
/*     */   }
/*     */ 
/*     */   public static DoubleBuffer newDoubleBuffer(int paramInt)
/*     */   {
/* 103 */     ByteBuffer localByteBuffer = newByteBuffer(paramInt * 8);
/* 104 */     return localByteBuffer.asDoubleBuffer();
/*     */   }
/*     */ 
/*     */   public static FloatBuffer newFloatBuffer(int paramInt)
/*     */   {
/* 113 */     ByteBuffer localByteBuffer = newByteBuffer(paramInt * 4);
/* 114 */     return localByteBuffer.asFloatBuffer();
/*     */   }
/*     */ 
/*     */   public static IntBuffer newIntBuffer(int paramInt)
/*     */   {
/* 123 */     ByteBuffer localByteBuffer = newByteBuffer(paramInt * 4);
/* 124 */     return localByteBuffer.asIntBuffer();
/*     */   }
/*     */ 
/*     */   public static ShortBuffer newShortBuffer(int paramInt)
/*     */   {
/* 133 */     ByteBuffer localByteBuffer = newByteBuffer(paramInt * 2);
/* 134 */     return localByteBuffer.asShortBuffer();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.BufferUtil
 * JD-Core Version:    0.6.2
 */