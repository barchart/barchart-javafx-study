/*     */ package com.sun.openpisces;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ 
/*     */ final class Curve
/*     */ {
/*     */   float ax;
/*     */   float ay;
/*     */   float bx;
/*     */   float by;
/*     */   float cx;
/*     */   float cy;
/*     */   float dx;
/*     */   float dy;
/*     */   float dax;
/*     */   float day;
/*     */   float dbx;
/*     */   float dby;
/*     */ 
/*     */   void set(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/*  39 */     switch (paramInt) {
/*     */     case 8:
/*  41 */       set(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2], paramArrayOfFloat[3], paramArrayOfFloat[4], paramArrayOfFloat[5], paramArrayOfFloat[6], paramArrayOfFloat[7]);
/*     */ 
/*  45 */       break;
/*     */     case 6:
/*  47 */       set(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2], paramArrayOfFloat[3], paramArrayOfFloat[4], paramArrayOfFloat[5]);
/*     */ 
/*  50 */       break;
/*     */     default:
/*  52 */       throw new InternalError("Curves can only be cubic or quadratic");
/*     */     }
/*     */   }
/*     */ 
/*     */   void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/*  61 */     this.ax = (3.0F * (paramFloat3 - paramFloat5) + paramFloat7 - paramFloat1);
/*  62 */     this.ay = (3.0F * (paramFloat4 - paramFloat6) + paramFloat8 - paramFloat2);
/*  63 */     this.bx = (3.0F * (paramFloat1 - 2.0F * paramFloat3 + paramFloat5));
/*  64 */     this.by = (3.0F * (paramFloat2 - 2.0F * paramFloat4 + paramFloat6));
/*  65 */     this.cx = (3.0F * (paramFloat3 - paramFloat1));
/*  66 */     this.cy = (3.0F * (paramFloat4 - paramFloat2));
/*  67 */     this.dx = paramFloat1;
/*  68 */     this.dy = paramFloat2;
/*  69 */     this.dax = (3.0F * this.ax); this.day = (3.0F * this.ay);
/*  70 */     this.dbx = (2.0F * this.bx); this.dby = (2.0F * this.by);
/*     */   }
/*     */ 
/*     */   void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/*  77 */     this.ax = (this.ay = 0.0F);
/*     */ 
/*  79 */     this.bx = (paramFloat1 - 2.0F * paramFloat3 + paramFloat5);
/*  80 */     this.by = (paramFloat2 - 2.0F * paramFloat4 + paramFloat6);
/*  81 */     this.cx = (2.0F * (paramFloat3 - paramFloat1));
/*  82 */     this.cy = (2.0F * (paramFloat4 - paramFloat2));
/*  83 */     this.dx = paramFloat1;
/*  84 */     this.dy = paramFloat2;
/*  85 */     this.dax = 0.0F; this.day = 0.0F;
/*  86 */     this.dbx = (2.0F * this.bx); this.dby = (2.0F * this.by);
/*     */   }
/*     */ 
/*     */   float xat(float paramFloat) {
/*  90 */     return paramFloat * (paramFloat * (paramFloat * this.ax + this.bx) + this.cx) + this.dx;
/*     */   }
/*     */   float yat(float paramFloat) {
/*  93 */     return paramFloat * (paramFloat * (paramFloat * this.ay + this.by) + this.cy) + this.dy;
/*     */   }
/*     */ 
/*     */   float dxat(float paramFloat) {
/*  97 */     return paramFloat * (paramFloat * this.dax + this.dbx) + this.cx;
/*     */   }
/*     */ 
/*     */   float dyat(float paramFloat) {
/* 101 */     return paramFloat * (paramFloat * this.day + this.dby) + this.cy;
/*     */   }
/*     */ 
/*     */   int dxRoots(float[] paramArrayOfFloat, int paramInt) {
/* 105 */     return Helpers.quadraticRoots(this.dax, this.dbx, this.cx, paramArrayOfFloat, paramInt);
/*     */   }
/*     */ 
/*     */   int dyRoots(float[] paramArrayOfFloat, int paramInt) {
/* 109 */     return Helpers.quadraticRoots(this.day, this.dby, this.cy, paramArrayOfFloat, paramInt);
/*     */   }
/*     */ 
/*     */   int infPoints(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/* 116 */     float f1 = this.dax * this.dby - this.dbx * this.day;
/* 117 */     float f2 = 2.0F * (this.cy * this.dax - this.day * this.cx);
/* 118 */     float f3 = this.cy * this.dbx - this.cx * this.dby;
/*     */ 
/* 120 */     return Helpers.quadraticRoots(f1, f2, f3, paramArrayOfFloat, paramInt);
/*     */   }
/*     */ 
/*     */   private int perpendiculardfddf(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/* 127 */     assert (paramArrayOfFloat.length >= paramInt + 4);
/*     */ 
/* 132 */     float f1 = 2.0F * (this.dax * this.dax + this.day * this.day);
/* 133 */     float f2 = 3.0F * (this.dax * this.dbx + this.day * this.dby);
/* 134 */     float f3 = 2.0F * (this.dax * this.cx + this.day * this.cy) + this.dbx * this.dbx + this.dby * this.dby;
/* 135 */     float f4 = this.dbx * this.cx + this.dby * this.cy;
/* 136 */     return Helpers.cubicRootsInAB(f1, f2, f3, f4, paramArrayOfFloat, paramInt, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   int rootsOfROCMinusW(float[] paramArrayOfFloat, int paramInt, float paramFloat1, float paramFloat2)
/*     */   {
/* 154 */     assert ((paramInt <= 6) && (paramArrayOfFloat.length >= 10));
/* 155 */     int i = paramInt;
/* 156 */     int j = perpendiculardfddf(paramArrayOfFloat, paramInt);
/* 157 */     float f1 = 0.0F; float f2 = ROCsq(f1) - paramFloat1 * paramFloat1;
/* 158 */     paramArrayOfFloat[(paramInt + j)] = 1.0F;
/* 159 */     j++;
/* 160 */     for (int k = paramInt; k < paramInt + j; k++) {
/* 161 */       float f3 = paramArrayOfFloat[k]; float f4 = ROCsq(f3) - paramFloat1 * paramFloat1;
/* 162 */       if (f2 == 0.0F)
/* 163 */         paramArrayOfFloat[(i++)] = f1;
/* 164 */       else if (f4 * f2 < 0.0F)
/*     */       {
/* 167 */         paramArrayOfFloat[(i++)] = falsePositionROCsqMinusX(f1, f3, paramFloat1 * paramFloat1, paramFloat2);
/*     */       }
/* 169 */       f1 = f3;
/* 170 */       f2 = f4;
/*     */     }
/*     */ 
/* 173 */     return i - paramInt;
/*     */   }
/*     */ 
/*     */   private static float eliminateInf(float paramFloat) {
/* 177 */     return paramFloat == (1.0F / -1.0F) ? 1.4E-45F : paramFloat == (1.0F / 1.0F) ? 3.4028235E+38F : paramFloat;
/*     */   }
/*     */ 
/*     */   private float falsePositionROCsqMinusX(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 192 */     int i = 0;
/* 193 */     float f1 = paramFloat2; float f2 = eliminateInf(ROCsq(f1) - paramFloat3);
/* 194 */     float f3 = paramFloat1; float f4 = eliminateInf(ROCsq(f3) - paramFloat3);
/* 195 */     float f5 = f3;
/* 196 */     for (int j = 0; (j < 100) && (Math.abs(f1 - f3) > paramFloat4 * Math.abs(f1 + f3)); j++) {
/* 197 */       f5 = (f4 * f1 - f2 * f3) / (f4 - f2);
/* 198 */       float f6 = ROCsq(f5) - paramFloat3;
/* 199 */       if (sameSign(f6, f2)) {
/* 200 */         f2 = f6; f1 = f5;
/* 201 */         if (i < 0) {
/* 202 */           f4 /= (1 << -i);
/* 203 */           i--;
/*     */         } else {
/* 205 */           i = -1;
/*     */         }
/*     */       } else { if (f6 * f4 <= 0.0F) break;
/* 208 */         f4 = f6; f3 = f5;
/* 209 */         if (i > 0) {
/* 210 */           f2 /= (1 << i);
/* 211 */           i++;
/*     */         } else {
/* 213 */           i = 1;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 219 */     return f5;
/*     */   }
/*     */ 
/*     */   private static boolean sameSign(double paramDouble1, double paramDouble2)
/*     */   {
/* 224 */     return ((paramDouble1 < 0.0D) && (paramDouble2 < 0.0D)) || ((paramDouble1 > 0.0D) && (paramDouble2 > 0.0D));
/*     */   }
/*     */ 
/*     */   private float ROCsq(float paramFloat)
/*     */   {
/* 231 */     float f1 = paramFloat * (paramFloat * this.dax + this.dbx) + this.cx;
/* 232 */     float f2 = paramFloat * (paramFloat * this.day + this.dby) + this.cy;
/* 233 */     float f3 = 2.0F * this.dax * paramFloat + this.dbx;
/* 234 */     float f4 = 2.0F * this.day * paramFloat + this.dby;
/* 235 */     float f5 = f1 * f1 + f2 * f2;
/* 236 */     float f6 = f3 * f3 + f4 * f4;
/* 237 */     float f7 = f3 * f1 + f4 * f2;
/* 238 */     return f5 * (f5 * f5 / (f5 * f6 - f7 * f7));
/*     */   }
/*     */ 
/*     */   static Iterator<Integer> breakPtsAtTs(final float[] paramArrayOfFloat1, int paramInt1, final float[] paramArrayOfFloat2, final int paramInt2)
/*     */   {
/* 252 */     assert ((paramArrayOfFloat1.length >= 2 * paramInt1) && (paramInt2 <= paramArrayOfFloat2.length));
/* 253 */     return new Iterator()
/*     */     {
/* 257 */       final Integer i0 = Integer.valueOf(0);
/* 258 */       final Integer itype = Integer.valueOf(this.val$type);
/* 259 */       int nextCurveIdx = 0;
/* 260 */       Integer curCurveOff = this.i0;
/* 261 */       float prevT = 0.0F;
/*     */ 
/*     */       public boolean hasNext() {
/* 264 */         return this.nextCurveIdx < paramInt2 + 1;
/*     */       }
/*     */ 
/*     */       public Integer next()
/*     */       {
/*     */         Integer localInteger;
/* 269 */         if (this.nextCurveIdx < paramInt2) {
/* 270 */           float f1 = paramArrayOfFloat2[this.nextCurveIdx];
/* 271 */           float f2 = (f1 - this.prevT) / (1.0F - this.prevT);
/* 272 */           Helpers.subdivideAt(f2, paramArrayOfFloat1, this.curCurveOff.intValue(), paramArrayOfFloat1, 0, paramArrayOfFloat1, this.val$type, this.val$type);
/*     */ 
/* 276 */           this.prevT = f1;
/* 277 */           localInteger = this.i0;
/* 278 */           this.curCurveOff = this.itype;
/*     */         } else {
/* 280 */           localInteger = this.curCurveOff;
/*     */         }
/* 282 */         this.nextCurveIdx += 1;
/* 283 */         return localInteger;
/*     */       }
/*     */ 
/*     */       public void remove()
/*     */       {
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.openpisces.Curve
 * JD-Core Version:    0.6.2
 */