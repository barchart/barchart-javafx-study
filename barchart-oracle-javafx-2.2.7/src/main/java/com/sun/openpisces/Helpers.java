/*     */ package com.sun.openpisces;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ final class Helpers
/*     */ {
/*     */   private Helpers()
/*     */   {
/*  38 */     throw new Error("This is a non instantiable class");
/*     */   }
/*     */ 
/*     */   static boolean within(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  42 */     float f = paramFloat2 - paramFloat1;
/*  43 */     return (f <= paramFloat3) && (f >= -paramFloat3);
/*     */   }
/*     */ 
/*     */   static boolean within(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  47 */     double d = paramDouble2 - paramDouble1;
/*  48 */     return (d <= paramDouble3) && (d >= -paramDouble3);
/*     */   }
/*     */ 
/*     */   static int quadraticRoots(float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOfFloat, int paramInt)
/*     */   {
/*  54 */     int i = paramInt;
/*     */     float f1;
/*  56 */     if (paramFloat1 != 0.0F) {
/*  57 */       float f2 = paramFloat2 * paramFloat2 - 4.0F * paramFloat1 * paramFloat3;
/*  58 */       if (f2 > 0.0F) {
/*  59 */         float f3 = (float)Math.sqrt(f2);
/*     */ 
/*  64 */         if (paramFloat2 >= 0.0F) {
/*  65 */           paramArrayOfFloat[(i++)] = (2.0F * paramFloat3 / (-paramFloat2 - f3));
/*  66 */           paramArrayOfFloat[(i++)] = ((-paramFloat2 - f3) / (2.0F * paramFloat1));
/*     */         } else {
/*  68 */           paramArrayOfFloat[(i++)] = ((-paramFloat2 + f3) / (2.0F * paramFloat1));
/*  69 */           paramArrayOfFloat[(i++)] = (2.0F * paramFloat3 / (-paramFloat2 + f3));
/*     */         }
/*  71 */       } else if (f2 == 0.0F) {
/*  72 */         f1 = -paramFloat2 / (2.0F * paramFloat1);
/*  73 */         paramArrayOfFloat[(i++)] = f1;
/*     */       }
/*     */     }
/*  76 */     else if (paramFloat2 != 0.0F) {
/*  77 */       f1 = -paramFloat3 / paramFloat2;
/*  78 */       paramArrayOfFloat[(i++)] = f1;
/*     */     }
/*     */ 
/*  81 */     return i - paramInt;
/*     */   }
/*     */ 
/*     */   static int cubicRootsInAB(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat, int paramInt, float paramFloat5, float paramFloat6)
/*     */   {
/*  89 */     if (paramFloat1 == 0.0F) {
/*  90 */       int i = quadraticRoots(paramFloat2, paramFloat3, paramFloat4, paramArrayOfFloat, paramInt);
/*  91 */       return filterOutNotInAB(paramArrayOfFloat, paramInt, i, paramFloat5, paramFloat6) - paramInt;
/*     */     }
/*     */ 
/* 100 */     paramFloat2 /= paramFloat1;
/* 101 */     paramFloat3 /= paramFloat1;
/* 102 */     paramFloat4 /= paramFloat1;
/*     */ 
/* 112 */     double d1 = paramFloat2 * paramFloat2;
/* 113 */     double d2 = 0.3333333333333333D * (-0.3333333333333333D * d1 + paramFloat3);
/* 114 */     double d3 = 0.5D * (0.0740740740740741D * paramFloat2 * d1 - 0.3333333333333333D * paramFloat2 * paramFloat3 + paramFloat4);
/*     */ 
/* 118 */     double d4 = d2 * d2 * d2;
/* 119 */     double d5 = d3 * d3 + d4;
/*     */     double d6;
/*     */     double d7;
/*     */     int j;
/* 122 */     if (d5 < 0.0D)
/*     */     {
/* 124 */       d6 = 0.3333333333333333D * Math.acos(-d3 / Math.sqrt(-d4));
/* 125 */       d7 = 2.0D * Math.sqrt(-d2);
/*     */ 
/* 127 */       paramArrayOfFloat[(paramInt + 0)] = ((float)(d7 * Math.cos(d6)));
/* 128 */       paramArrayOfFloat[(paramInt + 1)] = ((float)(-d7 * Math.cos(d6 + 1.047197551196598D)));
/* 129 */       paramArrayOfFloat[(paramInt + 2)] = ((float)(-d7 * Math.cos(d6 - 1.047197551196598D)));
/* 130 */       j = 3;
/*     */     } else {
/* 132 */       d6 = Math.sqrt(d5);
/* 133 */       d7 = Math.cbrt(d6 - d3);
/* 134 */       double d8 = -Math.cbrt(d6 + d3);
/*     */ 
/* 136 */       paramArrayOfFloat[paramInt] = ((float)(d7 + d8));
/* 137 */       j = 1;
/*     */ 
/* 139 */       if (within(d5, 0.0D, 1.0E-08D)) {
/* 140 */         paramArrayOfFloat[(paramInt + 1)] = (-(paramArrayOfFloat[paramInt] / 2.0F));
/* 141 */         j = 2;
/*     */       }
/*     */     }
/*     */ 
/* 145 */     float f = 0.3333333F * paramFloat2;
/*     */ 
/* 147 */     for (int k = 0; k < j; k++) {
/* 148 */       paramArrayOfFloat[(paramInt + k)] -= f;
/*     */     }
/*     */ 
/* 151 */     return filterOutNotInAB(paramArrayOfFloat, paramInt, j, paramFloat5, paramFloat6) - paramInt;
/*     */   }
/*     */ 
/*     */   static float[] widenArray(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*     */   {
/* 157 */     if (paramArrayOfFloat.length >= paramInt1 + paramInt2) {
/* 158 */       return paramArrayOfFloat;
/*     */     }
/* 160 */     return Arrays.copyOf(paramArrayOfFloat, 2 * (paramInt1 + paramInt2));
/*     */   }
/*     */ 
/*     */   static int[] widenArray(int[] paramArrayOfInt, int paramInt1, int paramInt2) {
/* 164 */     if (paramArrayOfInt.length >= paramInt1 + paramInt2) {
/* 165 */       return paramArrayOfInt;
/*     */     }
/* 167 */     return Arrays.copyOf(paramArrayOfInt, 2 * (paramInt1 + paramInt2));
/*     */   }
/*     */ 
/*     */   static float evalCubic(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*     */   {
/* 174 */     return paramFloat5 * (paramFloat5 * (paramFloat5 * paramFloat1 + paramFloat2) + paramFloat3) + paramFloat4;
/*     */   }
/*     */ 
/*     */   static float evalQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 180 */     return paramFloat4 * (paramFloat4 * paramFloat1 + paramFloat2) + paramFloat3;
/*     */   }
/*     */ 
/*     */   static int filterOutNotInAB(float[] paramArrayOfFloat, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
/*     */   {
/* 187 */     int i = paramInt1;
/* 188 */     for (int j = paramInt1; j < paramInt1 + paramInt2; j++) {
/* 189 */       if ((paramArrayOfFloat[j] >= paramFloat1) && (paramArrayOfFloat[j] < paramFloat2)) {
/* 190 */         paramArrayOfFloat[(i++)] = paramArrayOfFloat[j];
/*     */       }
/*     */     }
/* 193 */     return i;
/*     */   }
/*     */ 
/*     */   static float polyLineLength(float[] paramArrayOfFloat, int paramInt1, int paramInt2) {
/* 197 */     assert ((paramInt2 % 2 == 0) && (paramArrayOfFloat.length >= paramInt1 + paramInt2)) : "";
/* 198 */     float f = 0.0F;
/* 199 */     for (int i = paramInt1 + 2; i < paramInt1 + paramInt2; i += 2) {
/* 200 */       f += linelen(paramArrayOfFloat[i], paramArrayOfFloat[(i + 1)], paramArrayOfFloat[(i - 2)], paramArrayOfFloat[(i - 1)]);
/*     */     }
/* 202 */     return f;
/*     */   }
/*     */ 
/*     */   static float linelen(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 206 */     float f1 = paramFloat3 - paramFloat1;
/* 207 */     float f2 = paramFloat4 - paramFloat2;
/* 208 */     return (float)Math.sqrt(f1 * f1 + f2 * f2);
/*     */   }
/*     */ 
/*     */   static void subdivide(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, float[] paramArrayOfFloat3, int paramInt3, int paramInt4)
/*     */   {
/* 214 */     switch (paramInt4) {
/*     */     case 6:
/* 216 */       subdivideQuad(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramArrayOfFloat3, paramInt3);
/* 217 */       break;
/*     */     case 8:
/* 219 */       subdivideCubic(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramArrayOfFloat3, paramInt3);
/* 220 */       break;
/*     */     default:
/* 222 */       throw new InternalError("Unsupported curve type");
/*     */     }
/*     */   }
/*     */ 
/*     */   static void isort(float[] paramArrayOfFloat, int paramInt1, int paramInt2) {
/* 227 */     for (int i = paramInt1 + 1; i < paramInt1 + paramInt2; i++) {
/* 228 */       float f = paramArrayOfFloat[i];
/* 229 */       for (int j = i - 1; 
/* 230 */         (j >= paramInt1) && (paramArrayOfFloat[j] > f); j--) {
/* 231 */         paramArrayOfFloat[(j + 1)] = paramArrayOfFloat[j];
/*     */       }
/* 233 */       paramArrayOfFloat[(j + 1)] = f;
/*     */     }
/*     */   }
/*     */ 
/*     */   static void subdivideCubic(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, float[] paramArrayOfFloat3, int paramInt3)
/*     */   {
/* 272 */     float f1 = paramArrayOfFloat1[(paramInt1 + 0)];
/* 273 */     float f2 = paramArrayOfFloat1[(paramInt1 + 1)];
/* 274 */     float f3 = paramArrayOfFloat1[(paramInt1 + 2)];
/* 275 */     float f4 = paramArrayOfFloat1[(paramInt1 + 3)];
/* 276 */     float f5 = paramArrayOfFloat1[(paramInt1 + 4)];
/* 277 */     float f6 = paramArrayOfFloat1[(paramInt1 + 5)];
/* 278 */     float f7 = paramArrayOfFloat1[(paramInt1 + 6)];
/* 279 */     float f8 = paramArrayOfFloat1[(paramInt1 + 7)];
/* 280 */     if (paramArrayOfFloat2 != null) {
/* 281 */       paramArrayOfFloat2[(paramInt2 + 0)] = f1;
/* 282 */       paramArrayOfFloat2[(paramInt2 + 1)] = f2;
/*     */     }
/* 284 */     if (paramArrayOfFloat3 != null) {
/* 285 */       paramArrayOfFloat3[(paramInt3 + 6)] = f7;
/* 286 */       paramArrayOfFloat3[(paramInt3 + 7)] = f8;
/*     */     }
/* 288 */     f1 = (f1 + f3) / 2.0F;
/* 289 */     f2 = (f2 + f4) / 2.0F;
/* 290 */     f7 = (f7 + f5) / 2.0F;
/* 291 */     f8 = (f8 + f6) / 2.0F;
/* 292 */     float f9 = (f3 + f5) / 2.0F;
/* 293 */     float f10 = (f4 + f6) / 2.0F;
/* 294 */     f3 = (f1 + f9) / 2.0F;
/* 295 */     f4 = (f2 + f10) / 2.0F;
/* 296 */     f5 = (f7 + f9) / 2.0F;
/* 297 */     f6 = (f8 + f10) / 2.0F;
/* 298 */     f9 = (f3 + f5) / 2.0F;
/* 299 */     f10 = (f4 + f6) / 2.0F;
/* 300 */     if (paramArrayOfFloat2 != null) {
/* 301 */       paramArrayOfFloat2[(paramInt2 + 2)] = f1;
/* 302 */       paramArrayOfFloat2[(paramInt2 + 3)] = f2;
/* 303 */       paramArrayOfFloat2[(paramInt2 + 4)] = f3;
/* 304 */       paramArrayOfFloat2[(paramInt2 + 5)] = f4;
/* 305 */       paramArrayOfFloat2[(paramInt2 + 6)] = f9;
/* 306 */       paramArrayOfFloat2[(paramInt2 + 7)] = f10;
/*     */     }
/* 308 */     if (paramArrayOfFloat3 != null) {
/* 309 */       paramArrayOfFloat3[(paramInt3 + 0)] = f9;
/* 310 */       paramArrayOfFloat3[(paramInt3 + 1)] = f10;
/* 311 */       paramArrayOfFloat3[(paramInt3 + 2)] = f5;
/* 312 */       paramArrayOfFloat3[(paramInt3 + 3)] = f6;
/* 313 */       paramArrayOfFloat3[(paramInt3 + 4)] = f7;
/* 314 */       paramArrayOfFloat3[(paramInt3 + 5)] = f8;
/*     */     }
/*     */   }
/*     */ 
/*     */   static void subdivideCubicAt(float paramFloat, float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, float[] paramArrayOfFloat3, int paramInt3)
/*     */   {
/* 323 */     float f1 = paramArrayOfFloat1[(paramInt1 + 0)];
/* 324 */     float f2 = paramArrayOfFloat1[(paramInt1 + 1)];
/* 325 */     float f3 = paramArrayOfFloat1[(paramInt1 + 2)];
/* 326 */     float f4 = paramArrayOfFloat1[(paramInt1 + 3)];
/* 327 */     float f5 = paramArrayOfFloat1[(paramInt1 + 4)];
/* 328 */     float f6 = paramArrayOfFloat1[(paramInt1 + 5)];
/* 329 */     float f7 = paramArrayOfFloat1[(paramInt1 + 6)];
/* 330 */     float f8 = paramArrayOfFloat1[(paramInt1 + 7)];
/* 331 */     if (paramArrayOfFloat2 != null) {
/* 332 */       paramArrayOfFloat2[(paramInt2 + 0)] = f1;
/* 333 */       paramArrayOfFloat2[(paramInt2 + 1)] = f2;
/*     */     }
/* 335 */     if (paramArrayOfFloat3 != null) {
/* 336 */       paramArrayOfFloat3[(paramInt3 + 6)] = f7;
/* 337 */       paramArrayOfFloat3[(paramInt3 + 7)] = f8;
/*     */     }
/* 339 */     f1 += paramFloat * (f3 - f1);
/* 340 */     f2 += paramFloat * (f4 - f2);
/* 341 */     f7 = f5 + paramFloat * (f7 - f5);
/* 342 */     f8 = f6 + paramFloat * (f8 - f6);
/* 343 */     float f9 = f3 + paramFloat * (f5 - f3);
/* 344 */     float f10 = f4 + paramFloat * (f6 - f4);
/* 345 */     f3 = f1 + paramFloat * (f9 - f1);
/* 346 */     f4 = f2 + paramFloat * (f10 - f2);
/* 347 */     f5 = f9 + paramFloat * (f7 - f9);
/* 348 */     f6 = f10 + paramFloat * (f8 - f10);
/* 349 */     f9 = f3 + paramFloat * (f5 - f3);
/* 350 */     f10 = f4 + paramFloat * (f6 - f4);
/* 351 */     if (paramArrayOfFloat2 != null) {
/* 352 */       paramArrayOfFloat2[(paramInt2 + 2)] = f1;
/* 353 */       paramArrayOfFloat2[(paramInt2 + 3)] = f2;
/* 354 */       paramArrayOfFloat2[(paramInt2 + 4)] = f3;
/* 355 */       paramArrayOfFloat2[(paramInt2 + 5)] = f4;
/* 356 */       paramArrayOfFloat2[(paramInt2 + 6)] = f9;
/* 357 */       paramArrayOfFloat2[(paramInt2 + 7)] = f10;
/*     */     }
/* 359 */     if (paramArrayOfFloat3 != null) {
/* 360 */       paramArrayOfFloat3[(paramInt3 + 0)] = f9;
/* 361 */       paramArrayOfFloat3[(paramInt3 + 1)] = f10;
/* 362 */       paramArrayOfFloat3[(paramInt3 + 2)] = f5;
/* 363 */       paramArrayOfFloat3[(paramInt3 + 3)] = f6;
/* 364 */       paramArrayOfFloat3[(paramInt3 + 4)] = f7;
/* 365 */       paramArrayOfFloat3[(paramInt3 + 5)] = f8;
/*     */     }
/*     */   }
/*     */ 
/*     */   static void subdivideQuad(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, float[] paramArrayOfFloat3, int paramInt3)
/*     */   {
/* 373 */     float f1 = paramArrayOfFloat1[(paramInt1 + 0)];
/* 374 */     float f2 = paramArrayOfFloat1[(paramInt1 + 1)];
/* 375 */     float f3 = paramArrayOfFloat1[(paramInt1 + 2)];
/* 376 */     float f4 = paramArrayOfFloat1[(paramInt1 + 3)];
/* 377 */     float f5 = paramArrayOfFloat1[(paramInt1 + 4)];
/* 378 */     float f6 = paramArrayOfFloat1[(paramInt1 + 5)];
/* 379 */     if (paramArrayOfFloat2 != null) {
/* 380 */       paramArrayOfFloat2[(paramInt2 + 0)] = f1;
/* 381 */       paramArrayOfFloat2[(paramInt2 + 1)] = f2;
/*     */     }
/* 383 */     if (paramArrayOfFloat3 != null) {
/* 384 */       paramArrayOfFloat3[(paramInt3 + 4)] = f5;
/* 385 */       paramArrayOfFloat3[(paramInt3 + 5)] = f6;
/*     */     }
/* 387 */     f1 = (f1 + f3) / 2.0F;
/* 388 */     f2 = (f2 + f4) / 2.0F;
/* 389 */     f5 = (f5 + f3) / 2.0F;
/* 390 */     f6 = (f6 + f4) / 2.0F;
/* 391 */     f3 = (f1 + f5) / 2.0F;
/* 392 */     f4 = (f2 + f6) / 2.0F;
/* 393 */     if (paramArrayOfFloat2 != null) {
/* 394 */       paramArrayOfFloat2[(paramInt2 + 2)] = f1;
/* 395 */       paramArrayOfFloat2[(paramInt2 + 3)] = f2;
/* 396 */       paramArrayOfFloat2[(paramInt2 + 4)] = f3;
/* 397 */       paramArrayOfFloat2[(paramInt2 + 5)] = f4;
/*     */     }
/* 399 */     if (paramArrayOfFloat3 != null) {
/* 400 */       paramArrayOfFloat3[(paramInt3 + 0)] = f3;
/* 401 */       paramArrayOfFloat3[(paramInt3 + 1)] = f4;
/* 402 */       paramArrayOfFloat3[(paramInt3 + 2)] = f5;
/* 403 */       paramArrayOfFloat3[(paramInt3 + 3)] = f6;
/*     */     }
/*     */   }
/*     */ 
/*     */   static void subdivideQuadAt(float paramFloat, float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, float[] paramArrayOfFloat3, int paramInt3)
/*     */   {
/* 411 */     float f1 = paramArrayOfFloat1[(paramInt1 + 0)];
/* 412 */     float f2 = paramArrayOfFloat1[(paramInt1 + 1)];
/* 413 */     float f3 = paramArrayOfFloat1[(paramInt1 + 2)];
/* 414 */     float f4 = paramArrayOfFloat1[(paramInt1 + 3)];
/* 415 */     float f5 = paramArrayOfFloat1[(paramInt1 + 4)];
/* 416 */     float f6 = paramArrayOfFloat1[(paramInt1 + 5)];
/* 417 */     if (paramArrayOfFloat2 != null) {
/* 418 */       paramArrayOfFloat2[(paramInt2 + 0)] = f1;
/* 419 */       paramArrayOfFloat2[(paramInt2 + 1)] = f2;
/*     */     }
/* 421 */     if (paramArrayOfFloat3 != null) {
/* 422 */       paramArrayOfFloat3[(paramInt3 + 4)] = f5;
/* 423 */       paramArrayOfFloat3[(paramInt3 + 5)] = f6;
/*     */     }
/* 425 */     f1 += paramFloat * (f3 - f1);
/* 426 */     f2 += paramFloat * (f4 - f2);
/* 427 */     f5 = f3 + paramFloat * (f5 - f3);
/* 428 */     f6 = f4 + paramFloat * (f6 - f4);
/* 429 */     f3 = f1 + paramFloat * (f5 - f1);
/* 430 */     f4 = f2 + paramFloat * (f6 - f2);
/* 431 */     if (paramArrayOfFloat2 != null) {
/* 432 */       paramArrayOfFloat2[(paramInt2 + 2)] = f1;
/* 433 */       paramArrayOfFloat2[(paramInt2 + 3)] = f2;
/* 434 */       paramArrayOfFloat2[(paramInt2 + 4)] = f3;
/* 435 */       paramArrayOfFloat2[(paramInt2 + 5)] = f4;
/*     */     }
/* 437 */     if (paramArrayOfFloat3 != null) {
/* 438 */       paramArrayOfFloat3[(paramInt3 + 0)] = f3;
/* 439 */       paramArrayOfFloat3[(paramInt3 + 1)] = f4;
/* 440 */       paramArrayOfFloat3[(paramInt3 + 2)] = f5;
/* 441 */       paramArrayOfFloat3[(paramInt3 + 3)] = f6;
/*     */     }
/*     */   }
/*     */ 
/*     */   static void subdivideAt(float paramFloat, float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, float[] paramArrayOfFloat3, int paramInt3, int paramInt4)
/*     */   {
/* 449 */     switch (paramInt4) {
/*     */     case 8:
/* 451 */       subdivideCubicAt(paramFloat, paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramArrayOfFloat3, paramInt3);
/* 452 */       break;
/*     */     case 6:
/* 454 */       subdivideQuadAt(paramFloat, paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramArrayOfFloat3, paramInt3);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.openpisces.Helpers
 * JD-Core Version:    0.6.2
 */