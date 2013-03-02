/*     */ package javafx.scene.image;
/*     */ 
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public abstract class PixelFormat<T extends Buffer>
/*     */ {
/*     */   private Type type;
/*     */ 
/*     */   PixelFormat(Type paramType)
/*     */   {
/*  81 */     this.type = paramType;
/*     */   }
/*     */ 
/*     */   public static WritablePixelFormat<IntBuffer> getIntArgbInstance() {
/*  85 */     return WritablePixelFormat.IntArgb.INSTANCE;
/*     */   }
/*     */ 
/*     */   public static WritablePixelFormat<IntBuffer> getIntArgbPreInstance() {
/*  89 */     return WritablePixelFormat.IntArgbPre.INSTANCE;
/*     */   }
/*     */ 
/*     */   public static WritablePixelFormat<ByteBuffer> getByteBgraInstance() {
/*  93 */     return WritablePixelFormat.ByteBgra.INSTANCE;
/*     */   }
/*     */ 
/*     */   public static WritablePixelFormat<ByteBuffer> getByteBgraPreInstance() {
/*  97 */     return WritablePixelFormat.ByteBgraPre.INSTANCE;
/*     */   }
/*     */ 
/*     */   public static PixelFormat<ByteBuffer> getByteRgbInstance() {
/* 101 */     return ByteRgb.instance;
/*     */   }
/*     */ 
/*     */   public static PixelFormat<ByteBuffer> createByteIndexedPremultipliedInstance(int[] paramArrayOfInt)
/*     */   {
/* 107 */     return IndexedPixelFormat.createByte(paramArrayOfInt, true);
/*     */   }
/*     */ 
/*     */   public static PixelFormat<ByteBuffer> createByteIndexedInstance(int[] paramArrayOfInt)
/*     */   {
/* 113 */     return IndexedPixelFormat.createByte(paramArrayOfInt, false);
/*     */   }
/*     */ 
/*     */   public Type getType() {
/* 117 */     return this.type;
/*     */   }
/*     */ 
/*     */   public abstract boolean isWritable();
/*     */ 
/*     */   public abstract boolean isPremultiplied();
/*     */ 
/*     */   static int NonPretoPre(int paramInt) {
/* 125 */     int i = paramInt >>> 24;
/* 126 */     if (i == 255) return paramInt;
/* 127 */     if (i == 0) return 0;
/* 128 */     int j = paramInt >> 16 & 0xFF;
/* 129 */     int k = paramInt >> 8 & 0xFF;
/* 130 */     int m = paramInt & 0xFF;
/* 131 */     j = (j * i + 127) / 255;
/* 132 */     k = (k * i + 127) / 255;
/* 133 */     m = (m * i + 127) / 255;
/* 134 */     return i << 24 | j << 16 | k << 8 | m;
/*     */   }
/*     */ 
/*     */   static int PretoNonPre(int paramInt) {
/* 138 */     int i = paramInt >>> 24;
/* 139 */     if ((i == 255) || (i == 0)) return paramInt;
/* 140 */     int j = paramInt >> 16 & 0xFF;
/* 141 */     int k = paramInt >> 8 & 0xFF;
/* 142 */     int m = paramInt & 0xFF;
/* 143 */     int n = i >> 1;
/* 144 */     j = j >= i ? 255 : (j * 255 + n) / i;
/* 145 */     k = k >= i ? 255 : (k * 255 + n) / i;
/* 146 */     m = m >= i ? 255 : (m * 255 + n) / i;
/* 147 */     return i << 24 | j << 16 | k << 8 | m;
/*     */   }
/*     */ 
/*     */   public abstract int getArgb(T paramT, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   static class IndexedPixelFormat extends PixelFormat<ByteBuffer>
/*     */   {
/*     */     int[] precolors;
/*     */     int[] nonprecolors;
/*     */     boolean premult;
/*     */ 
/*     */     static PixelFormat createByte(int[] paramArrayOfInt, boolean paramBoolean)
/*     */     {
/* 211 */       return new IndexedPixelFormat(PixelFormat.Type.BYTE_INDEXED, paramBoolean, Arrays.copyOf(paramArrayOfInt, 256));
/*     */     }
/*     */ 
/*     */     private IndexedPixelFormat(PixelFormat.Type paramType, boolean paramBoolean, int[] paramArrayOfInt)
/*     */     {
/* 216 */       super();
/* 217 */       if (paramBoolean)
/* 218 */         this.precolors = paramArrayOfInt;
/*     */       else {
/* 220 */         this.nonprecolors = paramArrayOfInt;
/*     */       }
/* 222 */       this.premult = paramBoolean;
/*     */     }
/*     */ 
/*     */     public boolean isWritable()
/*     */     {
/* 227 */       return false;
/*     */     }
/*     */ 
/*     */     public boolean isPremultiplied()
/*     */     {
/* 232 */       return this.premult;
/*     */     }
/*     */ 
/*     */     private int[] getPreColors() {
/* 236 */       if (this.precolors == null) {
/* 237 */         int[] arrayOfInt = new int[this.nonprecolors.length];
/* 238 */         for (int i = 0; i < arrayOfInt.length; i++) {
/* 239 */           arrayOfInt[i] = NonPretoPre(this.nonprecolors[i]);
/*     */         }
/* 241 */         this.precolors = arrayOfInt;
/*     */       }
/* 243 */       return this.precolors;
/*     */     }
/*     */ 
/*     */     private int[] getNonPreColors() {
/* 247 */       if (this.nonprecolors == null) {
/* 248 */         int[] arrayOfInt = new int[this.precolors.length];
/* 249 */         for (int i = 0; i < arrayOfInt.length; i++) {
/* 250 */           arrayOfInt[i] = PretoNonPre(this.precolors[i]);
/*     */         }
/* 252 */         this.nonprecolors = arrayOfInt;
/*     */       }
/* 254 */       return this.nonprecolors;
/*     */     }
/*     */ 
/*     */     public int getArgb(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 259 */       return getNonPreColors()[(paramByteBuffer.get(paramInt2 * paramInt3 + paramInt1) & 0xFF)];
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ByteRgb extends PixelFormat<ByteBuffer>
/*     */   {
/* 179 */     static final ByteRgb instance = new ByteRgb();
/*     */ 
/*     */     private ByteRgb() {
/* 182 */       super();
/*     */     }
/*     */ 
/*     */     public boolean isWritable()
/*     */     {
/* 187 */       return true;
/*     */     }
/*     */ 
/*     */     public boolean isPremultiplied()
/*     */     {
/* 192 */       return false;
/*     */     }
/*     */ 
/*     */     public int getArgb(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 197 */       int i = paramInt2 * paramInt3 + paramInt1 * 3;
/* 198 */       int j = paramByteBuffer.get(i) & 0xFF;
/* 199 */       int k = paramByteBuffer.get(i + 1) & 0xFF;
/* 200 */       int m = paramByteBuffer.get(i + 2) & 0xFF;
/* 201 */       return 0xFF000000 | j << 16 | k << 8 | m;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum Type
/*     */   {
/*  45 */     INT_ARGB_PRE, 
/*     */ 
/*  51 */     INT_ARGB, 
/*     */ 
/*  57 */     BYTE_BGRA_PRE, 
/*     */ 
/*  63 */     BYTE_BGRA, 
/*     */ 
/*  69 */     BYTE_RGB, 
/*     */ 
/*  75 */     BYTE_INDEXED;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.image.PixelFormat
 * JD-Core Version:    0.6.2
 */