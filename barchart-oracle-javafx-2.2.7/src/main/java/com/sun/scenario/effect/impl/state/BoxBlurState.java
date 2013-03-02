/*     */ package com.sun.scenario.effect.impl.state;
/*     */ 
/*     */ import com.sun.scenario.effect.Effect.AccelType;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.impl.BufferUtil;
/*     */ import com.sun.scenario.effect.impl.EffectPeer;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import java.nio.FloatBuffer;
/*     */ 
/*     */ public class BoxBlurState extends HVSeparableKernel
/*     */ {
/*     */   private int hsize;
/*     */   private int vsize;
/*     */   private int blurPasses;
/*     */   private FloatBuffer weights;
/*     */ 
/*     */   public int getHsize()
/*     */   {
/*  44 */     return this.hsize;
/*     */   }
/*     */ 
/*     */   public void setHsize(int paramInt) {
/*  48 */     if ((paramInt < 0) || (paramInt > 255)) {
/*  49 */       throw new IllegalArgumentException("Blur size must be in the range [0,255]");
/*     */     }
/*  51 */     this.hsize = paramInt;
/*     */   }
/*     */ 
/*     */   public int getVsize() {
/*  55 */     return this.vsize;
/*     */   }
/*     */ 
/*     */   public void setVsize(int paramInt) {
/*  59 */     if ((paramInt < 0) || (paramInt > 255)) {
/*  60 */       throw new IllegalArgumentException("Blur size must be in the range [0,255]");
/*     */     }
/*  62 */     this.vsize = paramInt;
/*     */   }
/*     */ 
/*     */   public int getBlurPasses() {
/*  66 */     return this.blurPasses;
/*     */   }
/*     */ 
/*     */   public void setBlurPasses(int paramInt) {
/*  70 */     if ((paramInt < 0) || (paramInt > 3)) {
/*  71 */       throw new IllegalArgumentException("Number of passes must be in the range [0,3]");
/*     */     }
/*  73 */     this.blurPasses = paramInt;
/*     */   }
/*     */ 
/*     */   public float getSpread() {
/*  77 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public boolean isNop()
/*     */   {
/*  82 */     return (this.blurPasses == 0) || ((this.hsize <= 1) && (this.vsize <= 1));
/*     */   }
/*     */ 
/*     */   public boolean isNop(int paramInt)
/*     */   {
/*  87 */     if (this.blurPasses != 0);
/*  87 */     return (paramInt == 0 ? this.hsize : this.vsize) <= 1;
/*     */   }
/*     */ 
/*     */   public int getKernelSize(int paramInt) {
/*  91 */     int i = paramInt == 0 ? this.hsize : this.vsize;
/*  92 */     if (i < 1) i = 1;
/*  93 */     i = (i - 1) * this.blurPasses + 1;
/*  94 */     i |= 1;
/*  95 */     return i;
/*     */   }
/*     */ 
/*     */   private int getScaleVal(int paramInt, boolean paramBoolean) {
/*  99 */     int i = getKernelSize(paramInt);
/* 100 */     int j = 0;
/* 101 */     while (i > 128) {
/* 102 */       i = (i + 1) / 2 | 0x1;
/* 103 */       j--;
/*     */     }
/* 105 */     return paramBoolean ? j : i;
/*     */   }
/*     */ 
/*     */   public int getPow2Scale(int paramInt) {
/* 109 */     return getScaleVal(paramInt, true);
/*     */   }
/*     */ 
/*     */   public int getScaledKernelSize(int paramInt)
/*     */   {
/* 114 */     return getScaleVal(paramInt, false);
/*     */   }
/*     */ 
/*     */   public int getPow2ScaleX()
/*     */   {
/* 119 */     return getPow2Scale(0);
/*     */   }
/*     */ 
/*     */   public int getPow2ScaleY()
/*     */   {
/* 124 */     return getPow2Scale(1);
/*     */   }
/*     */ 
/*     */   public FloatBuffer getWeights(int paramInt) {
/* 128 */     int i = paramInt == 0 ? this.hsize : this.vsize;
/* 129 */     if ((i < 1) || (this.blurPasses == 0)) i = 1;
/* 130 */     Object localObject = new long[i];
/* 131 */     for (int j = 0; j < i; j++)
/* 132 */       localObject[j] = 1L;
/*     */     int n;
/* 134 */     for (j = 1; j < this.blurPasses; j++) {
/* 135 */       long[] arrayOfLong2 = new long[localObject.length + i - 1];
/* 136 */       for (n = 0; n < localObject.length; n++) {
/* 137 */         for (int i1 = 0; i1 < i; i1++) {
/* 138 */           arrayOfLong2[(n + i1)] += localObject[n];
/*     */         }
/*     */       }
/* 141 */       localObject = arrayOfLong2;
/*     */     }
/*     */     int m;
/* 143 */     if ((localObject.length & 0x1) == 0)
/*     */     {
/* 150 */       long[] arrayOfLong1 = new long[localObject.length + 1];
/* 151 */       for (m = 0; m < localObject.length; m++) {
/* 152 */         arrayOfLong1[m] += localObject[m];
/* 153 */         arrayOfLong1[(m + 1)] += localObject[m];
/*     */       }
/* 155 */       localObject = arrayOfLong1;
/*     */     }
/* 157 */     int k = getPow2Scale(paramInt);
/* 158 */     while (k < 0) {
/* 159 */       m = (localObject.length + 1) / 2 | 0x1;
/* 160 */       n = (m * 2 - localObject.length) / 2;
/* 161 */       long[] arrayOfLong3 = new long[m];
/* 162 */       for (int i3 = 0; i3 < localObject.length; i3++) {
/* 163 */         arrayOfLong3[(n / 2)] += localObject[i3];
/* 164 */         n++;
/* 165 */         arrayOfLong3[(n / 2)] += localObject[i3];
/*     */       }
/* 167 */       localObject = arrayOfLong3;
/* 168 */       k++;
/*     */     }
/* 170 */     double d = 0.0D;
/* 171 */     for (int i2 = 0; i2 < localObject.length; i2++) {
/* 172 */       d += localObject[i2];
/*     */     }
/*     */ 
/* 177 */     i2 = this.vsize > 1 ? 1 : 0;
/* 178 */     float f = paramInt == i2 ? getSpread() : 0.0F;
/* 179 */     d += (localObject[0] - d) * f;
/*     */ 
/* 181 */     if (this.weights == null)
/*     */     {
/* 183 */       i4 = 128;
/* 184 */       i4 = LinearConvolveKernel.getPeerSize(i4);
/* 185 */       i4 = i4 + 3 & 0xFFFFFFFC;
/* 186 */       this.weights = BufferUtil.newFloatBuffer(i4);
/*     */     }
/* 188 */     this.weights.clear();
/* 189 */     for (int i4 = 0; i4 < localObject.length; i4++) {
/* 190 */       this.weights.put((float)(localObject[i4] / d));
/*     */     }
/* 192 */     i4 = getPeerSize(localObject.length);
/* 193 */     while (this.weights.position() < i4) {
/* 194 */       this.weights.put(0.0F);
/*     */     }
/* 196 */     this.weights.limit(i4);
/* 197 */     this.weights.rewind();
/* 198 */     return this.weights;
/*     */   }
/*     */ 
/*     */   public EffectPeer getPeer(Renderer paramRenderer, FilterContext paramFilterContext, int paramInt)
/*     */   {
/* 203 */     int i = getScaledKernelSize(paramInt);
/* 204 */     if (i <= 1)
/*     */     {
/* 208 */       return null;
/*     */     }
/* 210 */     int j = getPeerSize(i);
/* 211 */     Effect.AccelType localAccelType = paramRenderer.getAccelType();
/*     */     String str;
/* 213 */     switch (1.$SwitchMap$com$sun$scenario$effect$Effect$AccelType[localAccelType.ordinal()]) {
/*     */     case 1:
/*     */     case 2:
/* 216 */       str = "BoxBlur";
/* 217 */       break;
/*     */     default:
/* 219 */       str = "LinearConvolve";
/*     */     }
/*     */ 
/* 222 */     EffectPeer localEffectPeer = paramRenderer.getPeerInstance(paramFilterContext, str, j);
/* 223 */     return localEffectPeer;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.state.BoxBlurState
 * JD-Core Version:    0.6.2
 */