/*     */ package com.sun.prism.paint;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ public final class Color extends Paint
/*     */ {
/*  17 */   public static final Color WHITE = new Color(1.0F, 1.0F, 1.0F, 1.0F);
/*  18 */   public static final Color BLACK = new Color(0.0F, 0.0F, 0.0F, 1.0F);
/*  19 */   public static final Color RED = new Color(1.0F, 0.0F, 0.0F, 1.0F);
/*  20 */   public static final Color GREEN = new Color(0.0F, 1.0F, 0.0F, 1.0F);
/*  21 */   public static final Color BLUE = new Color(0.0F, 0.0F, 1.0F, 1.0F);
/*  22 */   public static final Color TRANSPARENT = new Color(0.0F, 0.0F, 0.0F, 0.0F);
/*     */   private final int argb;
/*     */   private final float r;
/*     */   private final float g;
/*     */   private final float b;
/*     */   private final float a;
/*     */ 
/*     */   public Color(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  35 */     super(Paint.Type.COLOR, false);
/*  36 */     int i = (int)(255.0D * paramFloat4);
/*  37 */     int j = (int)(255.0D * paramFloat1 * paramFloat4);
/*  38 */     int k = (int)(255.0D * paramFloat2 * paramFloat4);
/*  39 */     int m = (int)(255.0D * paramFloat3 * paramFloat4);
/*  40 */     this.argb = (i << 24 | j << 16 | k << 8 | m << 0);
/*  41 */     this.r = paramFloat1;
/*  42 */     this.g = paramFloat2;
/*  43 */     this.b = paramFloat3;
/*  44 */     this.a = paramFloat4;
/*     */   }
/*     */ 
/*     */   public int getIntArgbPre()
/*     */   {
/*  52 */     return this.argb;
/*     */   }
/*     */ 
/*     */   public void putRgbaPreBytes(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/*  60 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(this.argb >> 16 & 0xFF));
/*  61 */     paramArrayOfByte[(paramInt + 1)] = ((byte)(this.argb >> 8 & 0xFF));
/*  62 */     paramArrayOfByte[(paramInt + 2)] = ((byte)(this.argb & 0xFF));
/*  63 */     paramArrayOfByte[(paramInt + 3)] = ((byte)(this.argb >> 24 & 0xFF));
/*     */   }
/*     */ 
/*     */   public void putBgraPreBytes(ByteBuffer paramByteBuffer)
/*     */   {
/*  71 */     paramByteBuffer.put((byte)(this.argb & 0xFF));
/*  72 */     paramByteBuffer.put((byte)(this.argb >> 8 & 0xFF));
/*  73 */     paramByteBuffer.put((byte)(this.argb >> 16 & 0xFF));
/*  74 */     paramByteBuffer.put((byte)(this.argb >> 24 & 0xFF));
/*     */   }
/*     */ 
/*     */   public float getRed()
/*     */   {
/*  82 */     return this.r;
/*     */   }
/*     */ 
/*     */   public float getRedPremult()
/*     */   {
/*  90 */     return this.r * this.a;
/*     */   }
/*     */ 
/*     */   public float getGreen()
/*     */   {
/*  98 */     return this.g;
/*     */   }
/*     */ 
/*     */   public float getGreenPremult()
/*     */   {
/* 106 */     return this.g * this.a;
/*     */   }
/*     */ 
/*     */   public float getBlue()
/*     */   {
/* 114 */     return this.b;
/*     */   }
/*     */ 
/*     */   public float getBluePremult()
/*     */   {
/* 122 */     return this.b * this.a;
/*     */   }
/*     */ 
/*     */   public float getAlpha()
/*     */   {
/* 129 */     return this.a;
/*     */   }
/*     */ 
/*     */   public boolean isOpaque()
/*     */   {
/* 134 */     return this.a >= 1.0F;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 139 */     if (!(paramObject instanceof Color)) {
/* 140 */       return false;
/*     */     }
/*     */ 
/* 143 */     Color localColor = (Color)paramObject;
/* 144 */     return (this.r == localColor.r) && (this.g == localColor.g) && (this.b == localColor.b) && (this.a == localColor.a);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 153 */     int i = 3;
/* 154 */     i = 53 * i + Float.floatToIntBits(this.r);
/* 155 */     i = 53 * i + Float.floatToIntBits(this.g);
/* 156 */     i = 53 * i + Float.floatToIntBits(this.b);
/* 157 */     i = 53 * i + Float.floatToIntBits(this.a);
/* 158 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 163 */     return "Color[r=" + this.r + ", g=" + this.g + ", b=" + this.b + ", a=" + this.a + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.paint.Color
 * JD-Core Version:    0.6.2
 */