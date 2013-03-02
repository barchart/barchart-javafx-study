/*     */ package com.sun.javafx.image;
/*     */ 
/*     */ import com.sun.javafx.image.impl.ByteBgra;
/*     */ import com.sun.javafx.image.impl.ByteBgraPre;
/*     */ import com.sun.javafx.image.impl.ByteRgb;
/*     */ import com.sun.javafx.image.impl.General;
/*     */ import com.sun.javafx.image.impl.IntArgb;
/*     */ import com.sun.javafx.image.impl.IntArgbPre;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import javafx.scene.image.PixelFormat;
/*     */ import javafx.scene.image.WritablePixelFormat;
/*     */ 
/*     */ public class PixelUtils
/*     */ {
/*     */   public static int RgbToGray(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  44 */     return (int)(paramInt1 * 0.3D + paramInt2 * 0.59D + paramInt3 * 0.11D);
/*     */   }
/*     */ 
/*     */   public static int RgbToGray(int paramInt) {
/*  48 */     return RgbToGray(paramInt >> 16 & 0xFF, paramInt >> 8 & 0xFF, paramInt & 0xFF);
/*     */   }
/*     */ 
/*     */   public static int NonPretoPre(int paramInt1, int paramInt2)
/*     */   {
/*  54 */     if (paramInt2 == 255) return paramInt1;
/*  55 */     if (paramInt2 == 0) return 0;
/*  56 */     return (paramInt1 * paramInt2 + 127) / 255;
/*     */   }
/*     */ 
/*     */   public static int PreToNonPre(int paramInt1, int paramInt2) {
/*  60 */     if ((paramInt2 == 255) || (paramInt2 == 0)) return paramInt1;
/*  61 */     return paramInt1 >= paramInt2 ? 255 : (paramInt1 * 255 + (paramInt2 >> 1)) / paramInt2;
/*     */   }
/*     */ 
/*     */   public static int NonPretoPre(int paramInt) {
/*  65 */     int i = paramInt >>> 24;
/*  66 */     if (i == 255) return paramInt;
/*  67 */     if (i == 0) return 0;
/*  68 */     int j = paramInt >> 16 & 0xFF;
/*  69 */     int k = paramInt >> 8 & 0xFF;
/*  70 */     int m = paramInt & 0xFF;
/*  71 */     j = (j * i + 127) / 255;
/*  72 */     k = (k * i + 127) / 255;
/*  73 */     m = (m * i + 127) / 255;
/*  74 */     return i << 24 | j << 16 | k << 8 | m;
/*     */   }
/*     */ 
/*     */   public static int PretoNonPre(int paramInt) {
/*  78 */     int i = paramInt >>> 24;
/*  79 */     if ((i == 255) || (i == 0)) return paramInt;
/*  80 */     int j = paramInt >> 16 & 0xFF;
/*  81 */     int k = paramInt >> 8 & 0xFF;
/*  82 */     int m = paramInt & 0xFF;
/*  83 */     int n = i >> 1;
/*  84 */     j = j >= i ? 255 : (j * 255 + n) / i;
/*  85 */     k = k >= i ? 255 : (k * 255 + n) / i;
/*  86 */     m = m >= i ? 255 : (m * 255 + n) / i;
/*  87 */     return i << 24 | j << 16 | k << 8 | m;
/*     */   }
/*     */ 
/*     */   public static BytePixelGetter getByteGetter(PixelFormat<ByteBuffer> paramPixelFormat) {
/*  91 */     switch (1.$SwitchMap$javafx$scene$image$PixelFormat$Type[paramPixelFormat.getType().ordinal()]) {
/*     */     case 1:
/*  93 */       return ByteBgra.getter;
/*     */     case 2:
/*  95 */       return ByteBgraPre.getter;
/*     */     case 3:
/*  97 */       return ByteRgb.getter;
/*     */     case 4:
/*     */     case 5:
/*     */     }
/*     */ 
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */   public static IntPixelGetter getIntGetter(PixelFormat<IntBuffer> paramPixelFormat) {
/* 106 */     switch (1.$SwitchMap$javafx$scene$image$PixelFormat$Type[paramPixelFormat.getType().ordinal()]) {
/*     */     case 4:
/* 108 */       return IntArgb.getter;
/*     */     case 5:
/* 110 */       return IntArgbPre.getter;
/*     */     case 1:
/*     */     case 2:
/*     */     case 3:
/*     */     }
/*     */ 
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   public static <T extends Buffer> PixelGetter<T> getGetter(PixelFormat<T> paramPixelFormat) {
/* 120 */     switch (1.$SwitchMap$javafx$scene$image$PixelFormat$Type[paramPixelFormat.getType().ordinal()]) {
/*     */     case 1:
/* 122 */       return ByteBgra.getter;
/*     */     case 2:
/* 124 */       return ByteBgraPre.getter;
/*     */     case 4:
/* 126 */       return IntArgb.getter;
/*     */     case 5:
/* 128 */       return IntArgbPre.getter;
/*     */     case 3:
/* 130 */       return ByteRgb.getter;
/*     */     }
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */   public static BytePixelSetter getByteSetter(WritablePixelFormat<ByteBuffer> paramWritablePixelFormat) {
/* 136 */     switch (1.$SwitchMap$javafx$scene$image$PixelFormat$Type[paramWritablePixelFormat.getType().ordinal()]) {
/*     */     case 1:
/* 138 */       return ByteBgra.setter;
/*     */     case 2:
/* 140 */       return ByteBgraPre.setter;
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/*     */     }
/*     */ 
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */   public static IntPixelSetter getIntSetter(WritablePixelFormat<IntBuffer> paramWritablePixelFormat) {
/* 150 */     switch (1.$SwitchMap$javafx$scene$image$PixelFormat$Type[paramWritablePixelFormat.getType().ordinal()]) {
/*     */     case 4:
/* 152 */       return IntArgb.setter;
/*     */     case 5:
/* 154 */       return IntArgbPre.setter;
/*     */     case 1:
/*     */     case 2:
/*     */     case 3:
/*     */     }
/*     */ 
/* 160 */     return null;
/*     */   }
/*     */ 
/*     */   public static <T extends Buffer> PixelSetter<T> getSetter(WritablePixelFormat<T> paramWritablePixelFormat) {
/* 164 */     switch (1.$SwitchMap$javafx$scene$image$PixelFormat$Type[paramWritablePixelFormat.getType().ordinal()]) {
/*     */     case 1:
/* 166 */       return ByteBgra.setter;
/*     */     case 2:
/* 168 */       return ByteBgraPre.setter;
/*     */     case 4:
/* 170 */       return IntArgb.setter;
/*     */     case 5:
/* 172 */       return IntArgbPre.setter;
/*     */     case 3:
/*     */     }
/*     */ 
/* 176 */     return null;
/*     */   }
/*     */ 
/*     */   public static <T extends Buffer, U extends Buffer> PixelConverter<T, U> getConverter(PixelGetter<T> paramPixelGetter, PixelSetter<U> paramPixelSetter)
/*     */   {
/* 182 */     if ((paramPixelGetter instanceof BytePixelGetter)) {
/* 183 */       if ((paramPixelSetter instanceof BytePixelSetter)) {
/* 184 */         return getB2BConverter((BytePixelGetter)paramPixelGetter, (BytePixelSetter)paramPixelSetter);
/*     */       }
/*     */ 
/* 187 */       return getB2IConverter((BytePixelGetter)paramPixelGetter, (IntPixelSetter)paramPixelSetter);
/*     */     }
/*     */ 
/* 191 */     if ((paramPixelSetter instanceof BytePixelSetter)) {
/* 192 */       return getI2BConverter((IntPixelGetter)paramPixelGetter, (BytePixelSetter)paramPixelSetter);
/*     */     }
/*     */ 
/* 195 */     return getI2IConverter((IntPixelGetter)paramPixelGetter, (IntPixelSetter)paramPixelSetter);
/*     */   }
/*     */ 
/*     */   public static ByteToBytePixelConverter getB2BConverter(PixelGetter<ByteBuffer> paramPixelGetter, PixelSetter<ByteBuffer> paramPixelSetter)
/*     */   {
/* 204 */     if (paramPixelGetter == ByteBgra.getter) {
/* 205 */       if (paramPixelSetter == ByteBgra.setter)
/* 206 */         return ByteBgra.ToByteBgraConverter;
/* 207 */       if (paramPixelSetter == ByteBgraPre.setter)
/* 208 */         return ByteBgra.ToByteBgraPreConverter;
/*     */     }
/* 210 */     else if (paramPixelGetter == ByteBgraPre.getter) {
/* 211 */       if (paramPixelSetter == ByteBgra.setter)
/* 212 */         return ByteBgraPre.ToByteBgraConverter;
/* 213 */       if (paramPixelSetter == ByteBgraPre.setter)
/* 214 */         return ByteBgraPre.ToByteBgraPreConverter;
/*     */     }
/* 216 */     else if (paramPixelGetter == ByteRgb.getter) {
/* 217 */       if (paramPixelSetter == ByteBgra.setter)
/* 218 */         return ByteRgb.ToByteBgraConverter;
/* 219 */       if (paramPixelSetter == ByteBgraPre.setter) {
/* 220 */         return ByteRgb.ToByteBgraPreConverter;
/*     */       }
/*     */     }
/* 223 */     return General.create((BytePixelGetter)paramPixelGetter, (BytePixelSetter)paramPixelSetter);
/*     */   }
/*     */ 
/*     */   public static ByteToIntPixelConverter getB2IConverter(PixelGetter<ByteBuffer> paramPixelGetter, PixelSetter<IntBuffer> paramPixelSetter)
/*     */   {
/* 229 */     if (paramPixelGetter == ByteBgra.getter) {
/* 230 */       if (paramPixelSetter == IntArgb.setter)
/* 231 */         return ByteBgra.ToIntArgbConverter;
/* 232 */       if (paramPixelSetter == IntArgbPre.setter)
/* 233 */         return ByteBgra.ToIntArgbPreConverter;
/*     */     }
/* 235 */     else if (paramPixelGetter == ByteBgraPre.getter) {
/* 236 */       if (paramPixelSetter == IntArgb.setter)
/* 237 */         return ByteBgraPre.ToIntArgbConverter;
/* 238 */       if (paramPixelSetter == IntArgbPre.setter)
/* 239 */         return ByteBgraPre.ToIntArgbPreConverter;
/*     */     }
/* 241 */     else if (paramPixelGetter == ByteRgb.getter) {
/* 242 */       if (paramPixelSetter == IntArgb.setter)
/* 243 */         return ByteRgb.ToIntArgbConverter;
/* 244 */       if (paramPixelSetter == IntArgbPre.setter) {
/* 245 */         return ByteRgb.ToIntArgbPreConverter;
/*     */       }
/*     */     }
/* 248 */     return General.create((BytePixelGetter)paramPixelGetter, (IntPixelSetter)paramPixelSetter);
/*     */   }
/*     */ 
/*     */   public static IntToBytePixelConverter getI2BConverter(PixelGetter<IntBuffer> paramPixelGetter, PixelSetter<ByteBuffer> paramPixelSetter)
/*     */   {
/* 254 */     if (paramPixelGetter == IntArgb.getter) {
/* 255 */       if (paramPixelSetter == ByteBgra.setter)
/* 256 */         return IntArgb.ToByteBgraConverter;
/* 257 */       if (paramPixelSetter == ByteBgraPre.setter)
/* 258 */         return IntArgb.ToByteBgraPreConverter;
/*     */     }
/* 260 */     else if (paramPixelGetter == IntArgbPre.getter) {
/* 261 */       if (paramPixelSetter == ByteBgra.setter)
/* 262 */         return IntArgbPre.ToByteBgraConverter;
/* 263 */       if (paramPixelSetter == ByteBgraPre.setter) {
/* 264 */         return IntArgbPre.ToByteBgraPreConverter;
/*     */       }
/*     */     }
/* 267 */     return General.create((IntPixelGetter)paramPixelGetter, (BytePixelSetter)paramPixelSetter);
/*     */   }
/*     */ 
/*     */   public static IntToIntPixelConverter getI2IConverter(PixelGetter<IntBuffer> paramPixelGetter, PixelSetter<IntBuffer> paramPixelSetter)
/*     */   {
/* 273 */     if (paramPixelGetter == IntArgb.getter) {
/* 274 */       if (paramPixelSetter == IntArgb.setter)
/* 275 */         return IntArgb.ToIntArgbConverter;
/* 276 */       if (paramPixelSetter == IntArgbPre.setter)
/* 277 */         return IntArgb.ToIntArgbPreConverter;
/*     */     }
/* 279 */     else if (paramPixelGetter == IntArgbPre.getter) {
/* 280 */       if (paramPixelSetter == IntArgb.setter)
/* 281 */         return IntArgbPre.ToIntArgbConverter;
/* 282 */       if (paramPixelSetter == IntArgbPre.setter) {
/* 283 */         return IntArgbPre.ToIntArgbPreConverter;
/*     */       }
/*     */     }
/* 286 */     return General.create((IntPixelGetter)paramPixelGetter, (IntPixelSetter)paramPixelSetter);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.image.PixelUtils
 * JD-Core Version:    0.6.2
 */