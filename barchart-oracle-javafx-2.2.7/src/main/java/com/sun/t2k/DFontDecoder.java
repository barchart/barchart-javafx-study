/*    */ package com.sun.t2k;
/*    */ 
/*    */ import [B;
/*    */ import com.sun.javafx.runtime.NativeLibLoader;
/*    */ import java.io.IOException;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ public class DFontDecoder extends FontFileWriter
/*    */ {
/*    */   private static native long createCTFont(String paramString);
/*    */ 
/*    */   private static native void releaseCTFont(long paramLong);
/*    */ 
/*    */   private static native int getCTFontFormat(long paramLong);
/*    */ 
/*    */   private static native int[] getCTFontTags(long paramLong);
/*    */ 
/*    */   private static native byte[] getCTFontTable(long paramLong, int paramInt);
/*    */ 
/*    */   public void decode(String paramString)
/*    */     throws IOException
/*    */   {
/* 31 */     if (paramString == null) {
/* 32 */       throw new IOException("Invalid font name");
/*    */     }
/* 34 */     long l = 0L;
/*    */     try {
/* 36 */       l = createCTFont(paramString);
/* 37 */       if (l == 0L) {
/* 38 */         throw new IOException("Failure creating CTFont");
/*    */       }
/* 40 */       int i = getCTFontFormat(l);
/* 41 */       if ((i != 1953658213) && (i != 65536) && (i != 1330926671)) {
/* 42 */         throw new IOException("Unsupported Dfont");
/*    */       }
/* 44 */       int[] arrayOfInt = getCTFontTags(l);
/* 45 */       int j = (short)arrayOfInt.length;
/* 46 */       int k = 12 + 16 * j;
/* 47 */       byte[][] arrayOfByte = new byte[j][];
/*    */       int i1;
/* 48 */       for (int m = 0; m < arrayOfInt.length; m++) {
/* 49 */         n = arrayOfInt[m];
/* 50 */         arrayOfByte[m] = getCTFontTable(l, n);
/* 51 */         i1 = arrayOfByte[m].length;
/* 52 */         k += (i1 + 3 & 0xFFFFFFFC);
/*    */       }
/* 54 */       releaseCTFont(l);
/* 55 */       l = 0L;
/*    */ 
/* 58 */       setLength(k);
/* 59 */       writeHeader(i, j);
/*    */ 
/* 61 */       m = 12 + 16 * j;
/* 62 */       for (int n = 0; n < j; n++) {
/* 63 */         i1 = arrayOfInt[n];
/* 64 */         [B local[B = arrayOfByte[n];
/*    */ 
/* 67 */         writeDirectoryEntry(n, i1, 0, m, local[B.length);
/*    */ 
/* 70 */         seek(m);
/* 71 */         writeBytes(local[B);
/*    */ 
/* 73 */         m += (local[B.length + 3 & 0xFFFFFFFC);
/*    */       }
/*    */ 
/* 77 */       if (l != 0L)
/* 78 */         releaseCTFont(l);
/*    */     }
/*    */     finally
/*    */     {
/* 77 */       if (l != 0L)
/* 78 */         releaseCTFont(l);
/*    */     }
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 13 */     AccessController.doPrivileged(new PrivilegedAction() {
/*    */       public Void run() {
/* 15 */         NativeLibLoader.loadLibrary("javafx-font");
/* 16 */         return null;
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.DFontDecoder
 * JD-Core Version:    0.6.2
 */