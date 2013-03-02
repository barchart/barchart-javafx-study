/*     */ package com.sun.prism.util.tess.impl.tess;
/*     */ 
/*     */ class Render
/*     */ {
/*     */   private static final boolean USE_OPTIMIZED_CODE_PATH = false;
/*  63 */   private static final RenderFan renderFan = new RenderFan(null);
/*  64 */   private static final RenderStrip renderStrip = new RenderStrip(null);
/*  65 */   private static final RenderTriangle renderTriangle = new RenderTriangle(null);
/*     */   private static final int SIGN_INCONSISTENT = 2;
/*     */ 
/*     */   public static void __gl_renderMesh(TessellatorImpl paramTessellatorImpl, GLUmesh paramGLUmesh)
/*     */   {
/* 103 */     paramTessellatorImpl.lonelyTriList = null;
/*     */ 
/* 105 */     for (GLUface localGLUface = paramGLUmesh.fHead.next; localGLUface != paramGLUmesh.fHead; localGLUface = localGLUface.next) {
/* 106 */       localGLUface.marked = false;
/*     */     }
/* 108 */     for (localGLUface = paramGLUmesh.fHead.next; localGLUface != paramGLUmesh.fHead; localGLUface = localGLUface.next)
/*     */     {
/* 114 */       if ((localGLUface.inside) && (!localGLUface.marked)) {
/* 115 */         RenderMaximumFaceGroup(paramTessellatorImpl, localGLUface);
/* 116 */         assert (localGLUface.marked);
/*     */       }
/*     */     }
/* 119 */     if (paramTessellatorImpl.lonelyTriList != null) {
/* 120 */       RenderLonelyTriangles(paramTessellatorImpl, paramTessellatorImpl.lonelyTriList);
/* 121 */       paramTessellatorImpl.lonelyTriList = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   static void RenderMaximumFaceGroup(TessellatorImpl paramTessellatorImpl, GLUface paramGLUface)
/*     */   {
/* 134 */     GLUhalfEdge localGLUhalfEdge = paramGLUface.anEdge;
/* 135 */     Object localObject = new FaceCount();
/* 136 */     FaceCount localFaceCount = new FaceCount();
/*     */ 
/* 138 */     ((FaceCount)localObject).size = 1L;
/* 139 */     ((FaceCount)localObject).eStart = localGLUhalfEdge;
/* 140 */     ((FaceCount)localObject).render = renderTriangle;
/*     */ 
/* 142 */     if (!paramTessellatorImpl.flagBoundary) {
/* 143 */       localFaceCount = MaximumFan(localGLUhalfEdge);
/* 144 */       if (localFaceCount.size > ((FaceCount)localObject).size) {
/* 145 */         localObject = localFaceCount;
/*     */       }
/* 147 */       localFaceCount = MaximumFan(localGLUhalfEdge.Lnext);
/* 148 */       if (localFaceCount.size > ((FaceCount)localObject).size) {
/* 149 */         localObject = localFaceCount;
/*     */       }
/* 151 */       localFaceCount = MaximumFan(localGLUhalfEdge.Onext.Sym);
/* 152 */       if (localFaceCount.size > ((FaceCount)localObject).size) {
/* 153 */         localObject = localFaceCount;
/*     */       }
/*     */ 
/* 156 */       localFaceCount = MaximumStrip(localGLUhalfEdge);
/* 157 */       if (localFaceCount.size > ((FaceCount)localObject).size) {
/* 158 */         localObject = localFaceCount;
/*     */       }
/* 160 */       localFaceCount = MaximumStrip(localGLUhalfEdge.Lnext);
/* 161 */       if (localFaceCount.size > ((FaceCount)localObject).size) {
/* 162 */         localObject = localFaceCount;
/*     */       }
/* 164 */       localFaceCount = MaximumStrip(localGLUhalfEdge.Onext.Sym);
/* 165 */       if (localFaceCount.size > ((FaceCount)localObject).size) {
/* 166 */         localObject = localFaceCount;
/*     */       }
/*     */     }
/* 169 */     ((FaceCount)localObject).render.render(paramTessellatorImpl, ((FaceCount)localObject).eStart, ((FaceCount)localObject).size);
/*     */   }
/*     */ 
/*     */   private static boolean Marked(GLUface paramGLUface)
/*     */   {
/* 181 */     return (!paramGLUface.inside) || (paramGLUface.marked);
/*     */   }
/*     */ 
/*     */   private static GLUface AddToTrail(GLUface paramGLUface1, GLUface paramGLUface2) {
/* 185 */     paramGLUface1.trail = paramGLUface2;
/* 186 */     paramGLUface1.marked = true;
/* 187 */     return paramGLUface1;
/*     */   }
/*     */ 
/*     */   private static void FreeTrail(GLUface paramGLUface)
/*     */   {
/* 192 */     while (paramGLUface != null) {
/* 193 */       paramGLUface.marked = false;
/* 194 */       paramGLUface = paramGLUface.trail;
/*     */     }
/*     */   }
/*     */ 
/*     */   static FaceCount MaximumFan(GLUhalfEdge paramGLUhalfEdge)
/*     */   {
/* 206 */     FaceCount localFaceCount = new FaceCount(0L, null, renderFan);
/* 207 */     GLUface localGLUface = null;
/*     */ 
/* 210 */     for (GLUhalfEdge localGLUhalfEdge = paramGLUhalfEdge; !Marked(localGLUhalfEdge.Lface); localGLUhalfEdge = localGLUhalfEdge.Onext) {
/* 211 */       localGLUface = AddToTrail(localGLUhalfEdge.Lface, localGLUface);
/* 212 */       localFaceCount.size += 1L;
/*     */     }
/* 214 */     for (localGLUhalfEdge = paramGLUhalfEdge; !Marked(localGLUhalfEdge.Sym.Lface); localGLUhalfEdge = localGLUhalfEdge.Sym.Lnext) {
/* 215 */       localGLUface = AddToTrail(localGLUhalfEdge.Sym.Lface, localGLUface);
/* 216 */       localFaceCount.size += 1L;
/*     */     }
/* 218 */     localFaceCount.eStart = localGLUhalfEdge;
/*     */ 
/* 220 */     FreeTrail(localGLUface);
/* 221 */     return localFaceCount;
/*     */   }
/*     */ 
/*     */   private static boolean IsEven(long paramLong)
/*     */   {
/* 226 */     return (paramLong & 1L) == 0L;
/*     */   }
/*     */ 
/*     */   static FaceCount MaximumStrip(GLUhalfEdge paramGLUhalfEdge)
/*     */   {
/* 240 */     FaceCount localFaceCount = new FaceCount(0L, null, renderStrip);
/* 241 */     long l1 = 0L; long l2 = 0L;
/* 242 */     GLUface localGLUface = null;
/*     */ 
/* 245 */     for (GLUhalfEdge localGLUhalfEdge1 = paramGLUhalfEdge; !Marked(localGLUhalfEdge1.Lface); localGLUhalfEdge1 = localGLUhalfEdge1.Onext) {
/* 246 */       localGLUface = AddToTrail(localGLUhalfEdge1.Lface, localGLUface);
/* 247 */       l2 += 1L;
/* 248 */       localGLUhalfEdge1 = localGLUhalfEdge1.Lnext.Sym;
/* 249 */       if (Marked(localGLUhalfEdge1.Lface)) break;
/* 250 */       localGLUface = AddToTrail(localGLUhalfEdge1.Lface, localGLUface);
/*     */ 
/* 245 */       l2 += 1L;
/*     */     }
/*     */ 
/* 252 */     GLUhalfEdge localGLUhalfEdge2 = localGLUhalfEdge1;
/*     */ 
/* 254 */     for (localGLUhalfEdge1 = paramGLUhalfEdge; !Marked(localGLUhalfEdge1.Sym.Lface); localGLUhalfEdge1 = localGLUhalfEdge1.Sym.Onext.Sym) {
/* 255 */       localGLUface = AddToTrail(localGLUhalfEdge1.Sym.Lface, localGLUface);
/* 256 */       l1 += 1L;
/* 257 */       localGLUhalfEdge1 = localGLUhalfEdge1.Sym.Lnext;
/* 258 */       if (Marked(localGLUhalfEdge1.Sym.Lface)) break;
/* 259 */       localGLUface = AddToTrail(localGLUhalfEdge1.Sym.Lface, localGLUface);
/*     */ 
/* 254 */       l1 += 1L;
/*     */     }
/*     */ 
/* 261 */     GLUhalfEdge localGLUhalfEdge3 = localGLUhalfEdge1;
/*     */ 
/* 263 */     localFaceCount.size = (l2 + l1);
/* 264 */     if (IsEven(l2)) {
/* 265 */       localFaceCount.eStart = localGLUhalfEdge2.Sym;
/* 266 */     } else if (IsEven(l1)) {
/* 267 */       localFaceCount.eStart = localGLUhalfEdge3;
/*     */     }
/*     */     else
/*     */     {
/* 272 */       localFaceCount.size -= 1L;
/* 273 */       localFaceCount.eStart = localGLUhalfEdge3.Onext;
/*     */     }
/*     */ 
/* 276 */     FreeTrail(localGLUface);
/* 277 */     return localFaceCount;
/*     */   }
/*     */ 
/*     */   static void RenderLonelyTriangles(TessellatorImpl paramTessellatorImpl, GLUface paramGLUface)
/*     */   {
/* 297 */     int j = -1;
/*     */ 
/* 299 */     paramTessellatorImpl.callBeginOrBeginData(4);
/*     */ 
/* 301 */     for (; paramGLUface != null; paramGLUface = paramGLUface.trail)
/*     */     {
/* 304 */       GLUhalfEdge localGLUhalfEdge = paramGLUface.anEdge;
/*     */       do {
/* 306 */         if (paramTessellatorImpl.flagBoundary)
/*     */         {
/* 310 */           int i = !localGLUhalfEdge.Sym.Lface.inside ? 1 : 0;
/* 311 */           if (j != i) {
/* 312 */             j = i;
/* 313 */             paramTessellatorImpl.callEdgeFlagOrEdgeFlagData(j != 0);
/*     */           }
/*     */         }
/* 316 */         paramTessellatorImpl.callVertexOrVertexData(localGLUhalfEdge.Org.data);
/*     */ 
/* 318 */         localGLUhalfEdge = localGLUhalfEdge.Lnext;
/* 319 */       }while (localGLUhalfEdge != paramGLUface.anEdge);
/*     */     }
/* 321 */     paramTessellatorImpl.callEndOrEndData();
/*     */   }
/*     */ 
/*     */   public static void __gl_renderBoundary(TessellatorImpl paramTessellatorImpl, GLUmesh paramGLUmesh)
/*     */   {
/* 384 */     for (GLUface localGLUface = paramGLUmesh.fHead.next; localGLUface != paramGLUmesh.fHead; localGLUface = localGLUface.next)
/* 385 */       if (localGLUface.inside) {
/* 386 */         paramTessellatorImpl.callBeginOrBeginData(2);
/* 387 */         GLUhalfEdge localGLUhalfEdge = localGLUface.anEdge;
/*     */         do {
/* 389 */           paramTessellatorImpl.callVertexOrVertexData(localGLUhalfEdge.Org.data);
/* 390 */           localGLUhalfEdge = localGLUhalfEdge.Lnext;
/* 391 */         }while (localGLUhalfEdge != localGLUface.anEdge);
/* 392 */         paramTessellatorImpl.callEndOrEndData();
/*     */       }
/*     */   }
/*     */ 
/*     */   static int ComputeNormal(TessellatorImpl paramTessellatorImpl, double[] paramArrayOfDouble, boolean paramBoolean)
/*     */   {
/* 411 */     CachedVertex[] arrayOfCachedVertex = paramTessellatorImpl.cache;
/*     */ 
/* 413 */     int i = paramTessellatorImpl.cacheCount;
/*     */ 
/* 417 */     double[] arrayOfDouble = new double[3];
/* 418 */     int k = 0;
/*     */ 
/* 433 */     if (!paramBoolean)
/*     */     {
/*     */       double tmp32_31 = (paramArrayOfDouble[2] = 0.0D); paramArrayOfDouble[1] = tmp32_31; paramArrayOfDouble[0] = tmp32_31;
/*     */     }
/*     */ 
/* 437 */     int j = 1;
/* 438 */     double d2 = arrayOfCachedVertex[j].coords[0] - arrayOfCachedVertex[0].coords[0];
/* 439 */     double d3 = arrayOfCachedVertex[j].coords[1] - arrayOfCachedVertex[0].coords[1];
/* 440 */     double d4 = arrayOfCachedVertex[j].coords[2] - arrayOfCachedVertex[0].coords[2];
/*     */     while (true) { j++; if (j >= i) break;
/* 442 */       double d5 = d2;
/* 443 */       double d6 = d3;
/* 444 */       double d7 = d4;
/* 445 */       d2 = arrayOfCachedVertex[j].coords[0] - arrayOfCachedVertex[0].coords[0];
/* 446 */       d3 = arrayOfCachedVertex[j].coords[1] - arrayOfCachedVertex[0].coords[1];
/* 447 */       d4 = arrayOfCachedVertex[j].coords[2] - arrayOfCachedVertex[0].coords[2];
/*     */ 
/* 450 */       arrayOfDouble[0] = (d6 * d4 - d7 * d3);
/* 451 */       arrayOfDouble[1] = (d7 * d2 - d5 * d4);
/* 452 */       arrayOfDouble[2] = (d5 * d3 - d6 * d2);
/*     */ 
/* 454 */       double d1 = arrayOfDouble[0] * paramArrayOfDouble[0] + arrayOfDouble[1] * paramArrayOfDouble[1] + arrayOfDouble[2] * paramArrayOfDouble[2];
/* 455 */       if (!paramBoolean)
/*     */       {
/* 459 */         if (d1 >= 0.0D) {
/* 460 */           paramArrayOfDouble[0] += arrayOfDouble[0];
/* 461 */           paramArrayOfDouble[1] += arrayOfDouble[1];
/* 462 */           paramArrayOfDouble[2] += arrayOfDouble[2];
/*     */         } else {
/* 464 */           paramArrayOfDouble[0] -= arrayOfDouble[0];
/* 465 */           paramArrayOfDouble[1] -= arrayOfDouble[1];
/* 466 */           paramArrayOfDouble[2] -= arrayOfDouble[2];
/*     */         }
/* 468 */       } else if (d1 != 0.0D)
/*     */       {
/* 470 */         if (d1 > 0.0D) {
/* 471 */           if (k < 0) return 2;
/* 472 */           k = 1;
/*     */         } else {
/* 474 */           if (k > 0) return 2;
/* 475 */           k = -1;
/*     */         }
/*     */       }
/*     */     }
/* 479 */     return k;
/*     */   }
/*     */ 
/*     */   public static boolean __gl_renderCache(TessellatorImpl paramTessellatorImpl)
/*     */   {
/* 490 */     CachedVertex[] arrayOfCachedVertex = paramTessellatorImpl.cache;
/*     */ 
/* 492 */     int i = paramTessellatorImpl.cacheCount;
/*     */ 
/* 495 */     double[] arrayOfDouble = new double[3];
/*     */ 
/* 498 */     if (paramTessellatorImpl.cacheCount < 3)
/*     */     {
/* 500 */       return true;
/*     */     }
/*     */ 
/* 503 */     arrayOfDouble[0] = paramTessellatorImpl.normal[0];
/* 504 */     arrayOfDouble[1] = paramTessellatorImpl.normal[1];
/* 505 */     arrayOfDouble[2] = paramTessellatorImpl.normal[2];
/* 506 */     if ((arrayOfDouble[0] == 0.0D) && (arrayOfDouble[1] == 0.0D) && (arrayOfDouble[2] == 0.0D)) {
/* 507 */       ComputeNormal(paramTessellatorImpl, arrayOfDouble, false);
/*     */     }
/*     */ 
/* 510 */     int j = ComputeNormal(paramTessellatorImpl, arrayOfDouble, true);
/* 511 */     if (j == 2)
/*     */     {
/* 513 */       return false;
/*     */     }
/* 515 */     if (j == 0)
/*     */     {
/* 517 */       return true;
/*     */     }
/*     */ 
/* 521 */     return false;
/*     */   }
/*     */ 
/*     */   private static class RenderStrip
/*     */     implements Render.renderCallBack
/*     */   {
/*     */     public void render(TessellatorImpl paramTessellatorImpl, GLUhalfEdge paramGLUhalfEdge, long paramLong)
/*     */     {
/* 352 */       paramTessellatorImpl.callBeginOrBeginData(5);
/* 353 */       paramTessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Org.data);
/* 354 */       paramTessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Sym.Org.data);
/*     */ 
/* 356 */       while (!Render.Marked(paramGLUhalfEdge.Lface)) {
/* 357 */         paramGLUhalfEdge.Lface.marked = true;
/* 358 */         paramLong -= 1L;
/* 359 */         paramGLUhalfEdge = paramGLUhalfEdge.Lnext.Sym;
/* 360 */         paramTessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Org.data);
/* 361 */         if (Render.Marked(paramGLUhalfEdge.Lface))
/*     */           break;
/* 363 */         paramGLUhalfEdge.Lface.marked = true;
/* 364 */         paramLong -= 1L;
/* 365 */         paramGLUhalfEdge = paramGLUhalfEdge.Onext;
/* 366 */         paramTessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Sym.Org.data);
/*     */       }
/*     */ 
/* 369 */       assert (paramLong == 0L);
/* 370 */       paramTessellatorImpl.callEndOrEndData();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class RenderFan
/*     */     implements Render.renderCallBack
/*     */   {
/*     */     public void render(TessellatorImpl paramTessellatorImpl, GLUhalfEdge paramGLUhalfEdge, long paramLong)
/*     */     {
/* 330 */       paramTessellatorImpl.callBeginOrBeginData(6);
/* 331 */       paramTessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Org.data);
/* 332 */       paramTessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Sym.Org.data);
/*     */ 
/* 334 */       while (!Render.Marked(paramGLUhalfEdge.Lface)) {
/* 335 */         paramGLUhalfEdge.Lface.marked = true;
/* 336 */         paramLong -= 1L;
/* 337 */         paramGLUhalfEdge = paramGLUhalfEdge.Onext;
/* 338 */         paramTessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Sym.Org.data);
/*     */       }
/*     */ 
/* 341 */       assert (paramLong == 0L);
/* 342 */       paramTessellatorImpl.callEndOrEndData();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class RenderTriangle
/*     */     implements Render.renderCallBack
/*     */   {
/*     */     public void render(TessellatorImpl paramTessellatorImpl, GLUhalfEdge paramGLUhalfEdge, long paramLong)
/*     */     {
/* 285 */       assert (paramLong == 1L);
/* 286 */       paramTessellatorImpl.lonelyTriList = Render.AddToTrail(paramGLUhalfEdge.Lface, paramTessellatorImpl.lonelyTriList);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract interface renderCallBack
/*     */   {
/*     */     public abstract void render(TessellatorImpl paramTessellatorImpl, GLUhalfEdge paramGLUhalfEdge, long paramLong);
/*     */   }
/*     */ 
/*     */   private static class FaceCount
/*     */   {
/*     */     long size;
/*     */     GLUhalfEdge eStart;
/*     */     Render.renderCallBack render;
/*     */ 
/*     */     public FaceCount()
/*     */     {
/*     */     }
/*     */ 
/*     */     public FaceCount(long paramLong, GLUhalfEdge paramGLUhalfEdge, Render.renderCallBack paramrenderCallBack)
/*     */     {
/*  76 */       this.size = paramLong;
/*  77 */       this.eStart = paramGLUhalfEdge;
/*  78 */       this.render = paramrenderCallBack;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.Render
 * JD-Core Version:    0.6.2
 */