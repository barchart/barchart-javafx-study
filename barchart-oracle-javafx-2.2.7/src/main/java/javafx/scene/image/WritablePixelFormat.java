/*     */ package javafx.scene.image;
/*     */ 
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public abstract class WritablePixelFormat<T extends Buffer> extends PixelFormat<T>
/*     */ {
/*     */   WritablePixelFormat(PixelFormat.Type paramType)
/*     */   {
/*  42 */     super(paramType);
/*     */   }
/*     */ 
/*     */   public boolean isWritable()
/*     */   {
/*  47 */     return true;
/*     */   }
/*     */ 
/*     */   public abstract void setArgb(T paramT, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   static class ByteBgraPre extends WritablePixelFormat<ByteBuffer>
/*     */   {
/* 164 */     static final ByteBgraPre INSTANCE = new ByteBgraPre();
/*     */ 
/*     */     private ByteBgraPre() {
/* 167 */       super();
/*     */     }
/*     */ 
/*     */     public boolean isPremultiplied()
/*     */     {
/* 172 */       return true;
/*     */     }
/*     */ 
/*     */     public int getArgb(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 177 */       int i = paramInt2 * paramInt3 + paramInt1 * 4;
/* 178 */       int j = paramByteBuffer.get(i) & 0xFF;
/* 179 */       int k = paramByteBuffer.get(i + 1) & 0xFF;
/* 180 */       int m = paramByteBuffer.get(i + 2) & 0xFF;
/* 181 */       int n = paramByteBuffer.get(i + 3) & 0xFF;
/* 182 */       if ((n > 0) && (n < 255)) {
/* 183 */         int i1 = n >> 1;
/* 184 */         m = m >= n ? 255 : (m * 255 + i1) / n;
/* 185 */         k = k >= n ? 255 : (k * 255 + i1) / n;
/* 186 */         j = j >= n ? 255 : (j * 255 + i1) / n;
/*     */       }
/* 188 */       return n << 24 | m << 16 | k << 8 | j;
/*     */     }
/*     */ 
/*     */     public void setArgb(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */     {
/* 195 */       int i = paramInt2 * paramInt3 + paramInt1 * 4;
/* 196 */       int j = paramInt4 >>> 24;
/*     */       int k;
/*     */       int m;
/*     */       int n;
/* 198 */       if (j > 0) {
/* 199 */         k = paramInt4 >> 16 & 0xFF;
/* 200 */         m = paramInt4 >> 8 & 0xFF;
/* 201 */         n = paramInt4 & 0xFF;
/* 202 */         if (j < 255) {
/* 203 */           k = (k * j + 127) / 255;
/* 204 */           m = (m * j + 127) / 255;
/* 205 */           n = (n * j + 127) / 255;
/*     */         }
/*     */       } else {
/* 208 */         j = k = m = n = 0;
/*     */       }
/* 210 */       paramByteBuffer.put(i, (byte)n);
/* 211 */       paramByteBuffer.put(i + 1, (byte)m);
/* 212 */       paramByteBuffer.put(i + 2, (byte)k);
/* 213 */       paramByteBuffer.put(i + 3, (byte)j);
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ByteBgra extends WritablePixelFormat<ByteBuffer>
/*     */   {
/* 130 */     static final ByteBgra INSTANCE = new ByteBgra();
/*     */ 
/*     */     private ByteBgra() {
/* 133 */       super();
/*     */     }
/*     */ 
/*     */     public boolean isPremultiplied()
/*     */     {
/* 138 */       return false;
/*     */     }
/*     */ 
/*     */     public int getArgb(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 143 */       int i = paramInt2 * paramInt3 + paramInt1 * 4;
/* 144 */       int j = paramByteBuffer.get(i) & 0xFF;
/* 145 */       int k = paramByteBuffer.get(i + 1) & 0xFF;
/* 146 */       int m = paramByteBuffer.get(i + 2) & 0xFF;
/* 147 */       int n = paramByteBuffer.get(i + 3) & 0xFF;
/* 148 */       return n << 24 | m << 16 | k << 8 | j;
/*     */     }
/*     */ 
/*     */     public void setArgb(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */     {
/* 155 */       int i = paramInt2 * paramInt3 + paramInt1 * 4;
/* 156 */       paramByteBuffer.put(i, (byte)paramInt4);
/* 157 */       paramByteBuffer.put(i + 1, (byte)(paramInt4 >> 8));
/* 158 */       paramByteBuffer.put(i + 2, (byte)(paramInt4 >> 16));
/* 159 */       paramByteBuffer.put(i + 3, (byte)(paramInt4 >> 24));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class IntArgbPre extends WritablePixelFormat<IntBuffer>
/*     */   {
/* 105 */     static final IntArgbPre INSTANCE = new IntArgbPre();
/*     */ 
/*     */     private IntArgbPre() {
/* 108 */       super();
/*     */     }
/*     */ 
/*     */     public boolean isPremultiplied()
/*     */     {
/* 113 */       return true;
/*     */     }
/*     */ 
/*     */     public int getArgb(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 118 */       return PretoNonPre(paramIntBuffer.get(paramInt2 * paramInt3 + paramInt1));
/*     */     }
/*     */ 
/*     */     public void setArgb(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */     {
/* 125 */       paramIntBuffer.put(paramInt2 * paramInt3 + paramInt1, NonPretoPre(paramInt4));
/*     */     }
/*     */   }
/*     */ 
/*     */   static class IntArgb extends WritablePixelFormat<IntBuffer>
/*     */   {
/*  80 */     static final IntArgb INSTANCE = new IntArgb();
/*     */ 
/*     */     private IntArgb() {
/*  83 */       super();
/*     */     }
/*     */ 
/*     */     public boolean isPremultiplied()
/*     */     {
/*  88 */       return false;
/*     */     }
/*     */ 
/*     */     public int getArgb(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/*  93 */       return paramIntBuffer.get(paramInt2 * paramInt3 + paramInt1);
/*     */     }
/*     */ 
/*     */     public void setArgb(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */     {
/* 100 */       paramIntBuffer.put(paramInt2 * paramInt3 + paramInt1, paramInt4);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.image.WritablePixelFormat
 * JD-Core Version:    0.6.2
 */