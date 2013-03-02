/*     */ package com.sun.prism.util.tess.impl.tess;
/*     */ 
/*     */ class Geom
/*     */ {
/*     */   static final double EPSILON = 1.E-05D;
/*     */   static final double ONE_MINUS_EPSILON = 0.9999900000000001D;
/*     */ 
/*     */   static double EdgeEval(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
/*     */   {
/*  72 */     assert ((VertLeq(paramGLUvertex1, paramGLUvertex2)) && (VertLeq(paramGLUvertex2, paramGLUvertex3)));
/*     */ 
/*  74 */     double d1 = paramGLUvertex2.s - paramGLUvertex1.s;
/*  75 */     double d2 = paramGLUvertex3.s - paramGLUvertex2.s;
/*     */ 
/*  77 */     if (d1 + d2 > 0.0D) {
/*  78 */       if (d1 < d2) {
/*  79 */         return paramGLUvertex2.t - paramGLUvertex1.t + (paramGLUvertex1.t - paramGLUvertex3.t) * (d1 / (d1 + d2));
/*     */       }
/*  81 */       return paramGLUvertex2.t - paramGLUvertex3.t + (paramGLUvertex3.t - paramGLUvertex1.t) * (d2 / (d1 + d2));
/*     */     }
/*     */ 
/*  85 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   static double EdgeSign(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
/*     */   {
/*  91 */     assert ((VertLeq(paramGLUvertex1, paramGLUvertex2)) && (VertLeq(paramGLUvertex2, paramGLUvertex3)));
/*     */ 
/*  93 */     double d1 = paramGLUvertex2.s - paramGLUvertex1.s;
/*  94 */     double d2 = paramGLUvertex3.s - paramGLUvertex2.s;
/*     */ 
/*  96 */     if (d1 + d2 > 0.0D) {
/*  97 */       return (paramGLUvertex2.t - paramGLUvertex3.t) * d1 + (paramGLUvertex2.t - paramGLUvertex1.t) * d2;
/*     */     }
/*     */ 
/* 100 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   static double TransEval(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
/*     */   {
/* 121 */     assert ((TransLeq(paramGLUvertex1, paramGLUvertex2)) && (TransLeq(paramGLUvertex2, paramGLUvertex3)));
/*     */ 
/* 123 */     double d1 = paramGLUvertex2.t - paramGLUvertex1.t;
/* 124 */     double d2 = paramGLUvertex3.t - paramGLUvertex2.t;
/*     */ 
/* 126 */     if (d1 + d2 > 0.0D) {
/* 127 */       if (d1 < d2) {
/* 128 */         return paramGLUvertex2.s - paramGLUvertex1.s + (paramGLUvertex1.s - paramGLUvertex3.s) * (d1 / (d1 + d2));
/*     */       }
/* 130 */       return paramGLUvertex2.s - paramGLUvertex3.s + (paramGLUvertex3.s - paramGLUvertex1.s) * (d2 / (d1 + d2));
/*     */     }
/*     */ 
/* 134 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   static double TransSign(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
/*     */   {
/* 144 */     assert ((TransLeq(paramGLUvertex1, paramGLUvertex2)) && (TransLeq(paramGLUvertex2, paramGLUvertex3)));
/*     */ 
/* 146 */     double d1 = paramGLUvertex2.t - paramGLUvertex1.t;
/* 147 */     double d2 = paramGLUvertex3.t - paramGLUvertex2.t;
/*     */ 
/* 149 */     if (d1 + d2 > 0.0D) {
/* 150 */       return (paramGLUvertex2.s - paramGLUvertex3.s) * d1 + (paramGLUvertex2.s - paramGLUvertex1.s) * d2;
/*     */     }
/*     */ 
/* 153 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   static boolean VertCCW(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
/*     */   {
/* 164 */     return paramGLUvertex1.s * (paramGLUvertex2.t - paramGLUvertex3.t) + paramGLUvertex2.s * (paramGLUvertex3.t - paramGLUvertex1.t) + paramGLUvertex3.s * (paramGLUvertex1.t - paramGLUvertex2.t) >= 0.0D;
/*     */   }
/*     */ 
/*     */   static double Interpolate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 176 */     paramDouble1 = paramDouble1 < 0.0D ? 0.0D : paramDouble1;
/* 177 */     paramDouble3 = paramDouble3 < 0.0D ? 0.0D : paramDouble3;
/* 178 */     if (paramDouble1 <= paramDouble3) {
/* 179 */       if (paramDouble3 == 0.0D) {
/* 180 */         return (paramDouble2 + paramDouble4) / 2.0D;
/*     */       }
/* 182 */       return paramDouble2 + (paramDouble4 - paramDouble2) * (paramDouble1 / (paramDouble1 + paramDouble3));
/*     */     }
/*     */ 
/* 185 */     return paramDouble4 + (paramDouble2 - paramDouble4) * (paramDouble3 / (paramDouble1 + paramDouble3));
/*     */   }
/*     */ 
/*     */   static void EdgeIntersect(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3, GLUvertex paramGLUvertex4, GLUvertex paramGLUvertex5)
/*     */   {
/*     */     GLUvertex localGLUvertex;
/* 206 */     if (!VertLeq(paramGLUvertex1, paramGLUvertex2)) {
/* 207 */       localGLUvertex = paramGLUvertex1;
/* 208 */       paramGLUvertex1 = paramGLUvertex2;
/* 209 */       paramGLUvertex2 = localGLUvertex;
/*     */     }
/* 211 */     if (!VertLeq(paramGLUvertex3, paramGLUvertex4)) {
/* 212 */       localGLUvertex = paramGLUvertex3;
/* 213 */       paramGLUvertex3 = paramGLUvertex4;
/* 214 */       paramGLUvertex4 = localGLUvertex;
/*     */     }
/* 216 */     if (!VertLeq(paramGLUvertex1, paramGLUvertex3)) {
/* 217 */       localGLUvertex = paramGLUvertex1;
/* 218 */       paramGLUvertex1 = paramGLUvertex3;
/* 219 */       paramGLUvertex3 = localGLUvertex;
/* 220 */       localGLUvertex = paramGLUvertex2;
/* 221 */       paramGLUvertex2 = paramGLUvertex4;
/* 222 */       paramGLUvertex4 = localGLUvertex;
/*     */     }
/*     */     double d1;
/*     */     double d2;
/* 225 */     if (!VertLeq(paramGLUvertex3, paramGLUvertex2))
/*     */     {
/* 227 */       paramGLUvertex5.s = ((paramGLUvertex3.s + paramGLUvertex2.s) / 2.0D);
/* 228 */     } else if (VertLeq(paramGLUvertex2, paramGLUvertex4))
/*     */     {
/* 230 */       d1 = EdgeEval(paramGLUvertex1, paramGLUvertex3, paramGLUvertex2);
/* 231 */       d2 = EdgeEval(paramGLUvertex3, paramGLUvertex2, paramGLUvertex4);
/* 232 */       if (d1 + d2 < 0.0D) {
/* 233 */         d1 = -d1;
/* 234 */         d2 = -d2;
/*     */       }
/* 236 */       paramGLUvertex5.s = Interpolate(d1, paramGLUvertex3.s, d2, paramGLUvertex2.s);
/*     */     }
/*     */     else {
/* 239 */       d1 = EdgeSign(paramGLUvertex1, paramGLUvertex3, paramGLUvertex2);
/* 240 */       d2 = -EdgeSign(paramGLUvertex1, paramGLUvertex4, paramGLUvertex2);
/* 241 */       if (d1 + d2 < 0.0D) {
/* 242 */         d1 = -d1;
/* 243 */         d2 = -d2;
/*     */       }
/* 245 */       paramGLUvertex5.s = Interpolate(d1, paramGLUvertex3.s, d2, paramGLUvertex4.s);
/*     */     }
/*     */ 
/* 250 */     if (!TransLeq(paramGLUvertex1, paramGLUvertex2)) {
/* 251 */       localGLUvertex = paramGLUvertex1;
/* 252 */       paramGLUvertex1 = paramGLUvertex2;
/* 253 */       paramGLUvertex2 = localGLUvertex;
/*     */     }
/* 255 */     if (!TransLeq(paramGLUvertex3, paramGLUvertex4)) {
/* 256 */       localGLUvertex = paramGLUvertex3;
/* 257 */       paramGLUvertex3 = paramGLUvertex4;
/* 258 */       paramGLUvertex4 = localGLUvertex;
/*     */     }
/* 260 */     if (!TransLeq(paramGLUvertex1, paramGLUvertex3)) {
/* 261 */       localGLUvertex = paramGLUvertex3;
/* 262 */       paramGLUvertex3 = paramGLUvertex1;
/* 263 */       paramGLUvertex1 = localGLUvertex;
/* 264 */       localGLUvertex = paramGLUvertex4;
/* 265 */       paramGLUvertex4 = paramGLUvertex2;
/* 266 */       paramGLUvertex2 = localGLUvertex;
/*     */     }
/*     */ 
/* 269 */     if (!TransLeq(paramGLUvertex3, paramGLUvertex2))
/*     */     {
/* 271 */       paramGLUvertex5.t = ((paramGLUvertex3.t + paramGLUvertex2.t) / 2.0D);
/* 272 */     } else if (TransLeq(paramGLUvertex2, paramGLUvertex4))
/*     */     {
/* 274 */       d1 = TransEval(paramGLUvertex1, paramGLUvertex3, paramGLUvertex2);
/* 275 */       d2 = TransEval(paramGLUvertex3, paramGLUvertex2, paramGLUvertex4);
/* 276 */       if (d1 + d2 < 0.0D) {
/* 277 */         d1 = -d1;
/* 278 */         d2 = -d2;
/*     */       }
/* 280 */       paramGLUvertex5.t = Interpolate(d1, paramGLUvertex3.t, d2, paramGLUvertex2.t);
/*     */     }
/*     */     else {
/* 283 */       d1 = TransSign(paramGLUvertex1, paramGLUvertex3, paramGLUvertex2);
/* 284 */       d2 = -TransSign(paramGLUvertex1, paramGLUvertex4, paramGLUvertex2);
/* 285 */       if (d1 + d2 < 0.0D) {
/* 286 */         d1 = -d1;
/* 287 */         d2 = -d2;
/*     */       }
/* 289 */       paramGLUvertex5.t = Interpolate(d1, paramGLUvertex3.t, d2, paramGLUvertex4.t);
/*     */     }
/*     */   }
/*     */ 
/*     */   static boolean VertEq(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2) {
/* 294 */     return (paramGLUvertex1.s == paramGLUvertex2.s) && (paramGLUvertex1.t == paramGLUvertex2.t);
/*     */   }
/*     */ 
/*     */   static boolean VertLeq(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2) {
/* 298 */     return (paramGLUvertex1.s < paramGLUvertex2.s) || ((paramGLUvertex1.s == paramGLUvertex2.s) && (paramGLUvertex1.t <= paramGLUvertex2.t));
/*     */   }
/*     */ 
/*     */   static boolean TransLeq(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2)
/*     */   {
/* 304 */     return (paramGLUvertex1.t < paramGLUvertex2.t) || ((paramGLUvertex1.t == paramGLUvertex2.t) && (paramGLUvertex1.s <= paramGLUvertex2.s));
/*     */   }
/*     */ 
/*     */   static boolean EdgeGoesLeft(GLUhalfEdge paramGLUhalfEdge) {
/* 308 */     return VertLeq(paramGLUhalfEdge.Sym.Org, paramGLUhalfEdge.Org);
/*     */   }
/*     */ 
/*     */   static boolean EdgeGoesRight(GLUhalfEdge paramGLUhalfEdge) {
/* 312 */     return VertLeq(paramGLUhalfEdge.Org, paramGLUhalfEdge.Sym.Org);
/*     */   }
/*     */ 
/*     */   static double VertL1dist(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2) {
/* 316 */     return Math.abs(paramGLUvertex1.s - paramGLUvertex2.s) + Math.abs(paramGLUvertex1.t - paramGLUvertex2.t);
/*     */   }
/*     */ 
/*     */   static double EdgeCos(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
/*     */   {
/* 324 */     double d1 = paramGLUvertex2.s - paramGLUvertex1.s;
/* 325 */     double d2 = paramGLUvertex2.t - paramGLUvertex1.t;
/* 326 */     double d3 = paramGLUvertex3.s - paramGLUvertex1.s;
/* 327 */     double d4 = paramGLUvertex3.t - paramGLUvertex1.t;
/* 328 */     double d5 = d1 * d3 + d2 * d4;
/* 329 */     double d6 = Math.sqrt(d1 * d1 + d2 * d2) * Math.sqrt(d3 * d3 + d4 * d4);
/* 330 */     if (d6 > 0.0D) {
/* 331 */       d5 /= d6;
/*     */     }
/* 333 */     return d5;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.Geom
 * JD-Core Version:    0.6.2
 */