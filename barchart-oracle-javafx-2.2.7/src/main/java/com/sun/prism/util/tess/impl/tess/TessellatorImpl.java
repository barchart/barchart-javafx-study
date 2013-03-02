/*     */ package com.sun.prism.util.tess.impl.tess;
/*     */ 
/*     */ import com.sun.prism.util.tess.Tessellator;
/*     */ import com.sun.prism.util.tess.TessellatorCallback;
/*     */ import com.sun.prism.util.tess.TessellatorCallbackAdapter;
/*     */ 
/*     */ public class TessellatorImpl
/*     */   implements Tessellator
/*     */ {
/*     */   public static final int TESS_MAX_CACHE = 100;
/*     */   private int state;
/*     */   private GLUhalfEdge lastEdge;
/*     */   GLUmesh mesh;
/*  68 */   double[] normal = new double[3];
/*  69 */   double[] sUnit = new double[3];
/*  70 */   double[] tUnit = new double[3];
/*     */   private double relTolerance;
/*     */   int windingRule;
/*     */   boolean fatalError;
/*     */   Dict dict;
/*     */   PriorityQ pq;
/*     */   GLUvertex event;
/*     */   boolean flagBoundary;
/*     */   boolean boundaryOnly;
/*     */   boolean avoidDegenerateTris;
/*     */   GLUface lonelyTriList;
/*     */   private boolean flushCacheOnNextVertex;
/*     */   int cacheCount;
/*  99 */   CachedVertex[] cache = new CachedVertex[100];
/*     */   private Object polygonData;
/*     */   private TessellatorCallback callBegin;
/*     */   private TessellatorCallback callEdgeFlag;
/*     */   private TessellatorCallback callVertex;
/*     */   private TessellatorCallback callEnd;
/*     */   private TessellatorCallback callError;
/*     */   private TessellatorCallback callCombine;
/*     */   private TessellatorCallback callBeginData;
/*     */   private TessellatorCallback callEdgeFlagData;
/*     */   private TessellatorCallback callVertexData;
/*     */   private TessellatorCallback callEndData;
/*     */   private TessellatorCallback callErrorData;
/*     */   private TessellatorCallback callCombineData;
/*     */   private static final double GLU_TESS_DEFAULT_TOLERANCE = 0.0D;
/* 122 */   private static TessellatorCallback NULL_CB = new TessellatorCallbackAdapter();
/*     */ 
/*     */   private TessellatorImpl()
/*     */   {
/* 128 */     this.state = 0;
/*     */ 
/* 130 */     this.normal[0] = 0.0D;
/* 131 */     this.normal[1] = 0.0D;
/* 132 */     this.normal[2] = 0.0D;
/*     */ 
/* 134 */     this.relTolerance = 0.0D;
/* 135 */     this.windingRule = 100130;
/* 136 */     this.flagBoundary = false;
/* 137 */     this.boundaryOnly = false;
/*     */ 
/* 139 */     this.callBegin = NULL_CB;
/* 140 */     this.callEdgeFlag = NULL_CB;
/* 141 */     this.callVertex = NULL_CB;
/* 142 */     this.callEnd = NULL_CB;
/* 143 */     this.callError = NULL_CB;
/* 144 */     this.callCombine = NULL_CB;
/*     */ 
/* 147 */     this.callBeginData = NULL_CB;
/* 148 */     this.callEdgeFlagData = NULL_CB;
/* 149 */     this.callVertexData = NULL_CB;
/* 150 */     this.callEndData = NULL_CB;
/* 151 */     this.callErrorData = NULL_CB;
/* 152 */     this.callCombineData = NULL_CB;
/*     */ 
/* 154 */     this.polygonData = null;
/*     */ 
/* 156 */     for (int i = 0; i < this.cache.length; i++)
/* 157 */       this.cache[i] = new CachedVertex();
/*     */   }
/*     */ 
/*     */   public static Tessellator gluNewTess()
/*     */   {
/* 163 */     return new TessellatorImpl();
/*     */   }
/*     */ 
/*     */   private void makeDormant()
/*     */   {
/* 170 */     if (this.mesh != null) {
/* 171 */       Mesh.__gl_meshDeleteMesh(this.mesh);
/*     */     }
/* 173 */     this.state = 0;
/* 174 */     this.lastEdge = null;
/* 175 */     this.mesh = null;
/*     */   }
/*     */ 
/*     */   private void requireState(int paramInt) {
/* 179 */     if (this.state != paramInt) gotoState(paramInt); 
/*     */   }
/*     */ 
/*     */   private void gotoState(int paramInt)
/*     */   {
/* 183 */     while (this.state != paramInt)
/*     */     {
/* 187 */       if (this.state < paramInt) {
/* 188 */         if (this.state == 0) {
/* 189 */           callErrorOrErrorData(100151);
/* 190 */           gluTessBeginPolygon(null);
/* 191 */         } else if (this.state == 1) {
/* 192 */           callErrorOrErrorData(100152);
/* 193 */           gluTessBeginContour();
/*     */         }
/*     */       }
/* 196 */       else if (this.state == 2) {
/* 197 */         callErrorOrErrorData(100154);
/* 198 */         gluTessEndContour();
/* 199 */       } else if (this.state == 1) {
/* 200 */         callErrorOrErrorData(100153);
/*     */ 
/* 202 */         makeDormant();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void gluDeleteTess()
/*     */   {
/* 209 */     requireState(0);
/*     */   }
/*     */ 
/*     */   public void gluTessProperty(int paramInt, double paramDouble) {
/* 213 */     switch (paramInt) {
/*     */     case 100142:
/* 215 */       if ((paramDouble >= 0.0D) && (paramDouble <= 1.0D)) { this.relTolerance = paramDouble;
/*     */         return; }
/*     */       break;
/*     */     case 100140:
/* 220 */       int i = (int)paramDouble;
/* 221 */       if (i == paramDouble)
/*     */       {
/* 223 */         switch (i) {
/*     */         case 100130:
/*     */         case 100131:
/*     */         case 100132:
/*     */         case 100133:
/*     */         case 100134:
/* 229 */           this.windingRule = i;
/*     */           return;
/*     */         }
/*     */       }
/*     */       break;
/*     */     case 100141:
/* 236 */       this.boundaryOnly = (paramDouble != 0.0D);
/* 237 */       return;
/*     */     case 100149:
/* 240 */       this.avoidDegenerateTris = (paramDouble != 0.0D);
/* 241 */       return;
/*     */     case 100143:
/*     */     case 100144:
/*     */     case 100145:
/*     */     case 100146:
/*     */     case 100147:
/*     */     case 100148:
/*     */     default:
/* 244 */       callErrorOrErrorData(100900);
/* 245 */       return;
/*     */     }
/* 247 */     callErrorOrErrorData(100901);
/*     */   }
/*     */ 
/*     */   public void gluGetTessProperty(int paramInt1, double[] paramArrayOfDouble, int paramInt2)
/*     */   {
/* 252 */     switch (paramInt1)
/*     */     {
/*     */     case 100142:
/* 255 */       assert ((0.0D <= this.relTolerance) && (this.relTolerance <= 1.0D));
/* 256 */       paramArrayOfDouble[paramInt2] = this.relTolerance;
/* 257 */       break;
/*     */     case 100140:
/* 259 */       assert ((this.windingRule == 100130) || (this.windingRule == 100131) || (this.windingRule == 100132) || (this.windingRule == 100133) || (this.windingRule == 100134));
/*     */ 
/* 264 */       paramArrayOfDouble[paramInt2] = this.windingRule;
/* 265 */       break;
/*     */     case 100141:
/* 267 */       assert ((this.boundaryOnly == true) || (!this.boundaryOnly));
/* 268 */       paramArrayOfDouble[paramInt2] = (this.boundaryOnly ? 1.0D : 0.0D);
/* 269 */       break;
/*     */     case 100149:
/* 271 */       paramArrayOfDouble[paramInt2] = (this.avoidDegenerateTris ? 1.0D : 0.0D);
/* 272 */       break;
/*     */     case 100143:
/*     */     case 100144:
/*     */     case 100145:
/*     */     case 100146:
/*     */     case 100147:
/*     */     case 100148:
/*     */     default:
/* 274 */       paramArrayOfDouble[paramInt2] = 0.0D;
/* 275 */       callErrorOrErrorData(100900);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void gluTessNormal(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 281 */     this.normal[0] = paramDouble1;
/* 282 */     this.normal[1] = paramDouble2;
/* 283 */     this.normal[2] = paramDouble3;
/*     */   }
/*     */ 
/*     */   public void gluTessCallback(int paramInt, TessellatorCallback paramTessellatorCallback) {
/* 287 */     switch (paramInt) {
/*     */     case 100100:
/* 289 */       this.callBegin = (paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/* 290 */       return;
/*     */     case 100106:
/* 292 */       this.callBeginData = (paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/* 293 */       return;
/*     */     case 100104:
/* 295 */       this.callEdgeFlag = (paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/*     */ 
/* 299 */       this.flagBoundary = (paramTessellatorCallback != null);
/* 300 */       return;
/*     */     case 100110:
/* 302 */       this.callEdgeFlagData = (this.callBegin = paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/*     */ 
/* 306 */       this.flagBoundary = (paramTessellatorCallback != null);
/* 307 */       return;
/*     */     case 100101:
/* 309 */       this.callVertex = (paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/* 310 */       return;
/*     */     case 100107:
/* 312 */       this.callVertexData = (paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/* 313 */       return;
/*     */     case 100102:
/* 315 */       this.callEnd = (paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/* 316 */       return;
/*     */     case 100108:
/* 318 */       this.callEndData = (paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/* 319 */       return;
/*     */     case 100103:
/* 321 */       this.callError = (paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/* 322 */       return;
/*     */     case 100109:
/* 324 */       this.callErrorData = (paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/* 325 */       return;
/*     */     case 100105:
/* 327 */       this.callCombine = (paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/* 328 */       return;
/*     */     case 100111:
/* 330 */       this.callCombineData = (paramTessellatorCallback == null ? NULL_CB : paramTessellatorCallback);
/* 331 */       return;
/*     */     }
/*     */ 
/* 336 */     callErrorOrErrorData(100900);
/*     */   }
/*     */ 
/*     */   private boolean addVertex(double[] paramArrayOfDouble, Object paramObject)
/*     */   {
/* 344 */     GLUhalfEdge localGLUhalfEdge = this.lastEdge;
/* 345 */     if (localGLUhalfEdge == null)
/*     */     {
/* 348 */       localGLUhalfEdge = Mesh.__gl_meshMakeEdge(this.mesh);
/* 349 */       if (localGLUhalfEdge == null) return false;
/* 350 */       if (!Mesh.__gl_meshSplice(localGLUhalfEdge, localGLUhalfEdge.Sym)) return false;
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 355 */       if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge) == null) return false;
/* 356 */       localGLUhalfEdge = localGLUhalfEdge.Lnext;
/*     */     }
/*     */ 
/* 360 */     localGLUhalfEdge.Org.data = paramObject;
/* 361 */     localGLUhalfEdge.Org.coords[0] = paramArrayOfDouble[0];
/* 362 */     localGLUhalfEdge.Org.coords[1] = paramArrayOfDouble[1];
/* 363 */     localGLUhalfEdge.Org.coords[2] = paramArrayOfDouble[2];
/*     */ 
/* 370 */     localGLUhalfEdge.winding = 1;
/* 371 */     localGLUhalfEdge.Sym.winding = -1;
/*     */ 
/* 373 */     this.lastEdge = localGLUhalfEdge;
/*     */ 
/* 375 */     return true;
/*     */   }
/*     */ 
/*     */   private void cacheVertex(double[] paramArrayOfDouble, Object paramObject) {
/* 379 */     if (this.cache[this.cacheCount] == null) {
/* 380 */       this.cache[this.cacheCount] = new CachedVertex();
/*     */     }
/*     */ 
/* 383 */     CachedVertex localCachedVertex = this.cache[this.cacheCount];
/*     */ 
/* 385 */     localCachedVertex.data = paramObject;
/* 386 */     localCachedVertex.coords[0] = paramArrayOfDouble[0];
/* 387 */     localCachedVertex.coords[1] = paramArrayOfDouble[1];
/* 388 */     localCachedVertex.coords[2] = paramArrayOfDouble[2];
/* 389 */     this.cacheCount += 1;
/*     */   }
/*     */ 
/*     */   private boolean flushCache()
/*     */   {
/* 394 */     CachedVertex[] arrayOfCachedVertex = this.cache;
/*     */ 
/* 396 */     this.mesh = Mesh.__gl_meshNewMesh();
/* 397 */     if (this.mesh == null) return false;
/*     */ 
/* 399 */     for (int i = 0; i < this.cacheCount; i++) {
/* 400 */       CachedVertex localCachedVertex = arrayOfCachedVertex[i];
/* 401 */       if (!addVertex(localCachedVertex.coords, localCachedVertex.data)) return false;
/*     */     }
/* 403 */     this.cacheCount = 0;
/* 404 */     this.flushCacheOnNextVertex = false;
/*     */ 
/* 406 */     return true;
/*     */   }
/*     */ 
/*     */   public void gluTessVertex(double[] paramArrayOfDouble, int paramInt, Object paramObject)
/*     */   {
/* 411 */     int j = 0;
/*     */ 
/* 413 */     double[] arrayOfDouble = new double[3];
/*     */ 
/* 415 */     requireState(2);
/*     */ 
/* 417 */     if (this.flushCacheOnNextVertex) {
/* 418 */       if (!flushCache()) {
/* 419 */         callErrorOrErrorData(100902);
/* 420 */         return;
/*     */       }
/* 422 */       this.lastEdge = null;
/*     */     }
/* 424 */     for (int i = 0; i < 3; i++) {
/* 425 */       double d = paramArrayOfDouble[(i + paramInt)];
/* 426 */       if (d < -1.0E+150D) {
/* 427 */         d = -1.0E+150D;
/* 428 */         j = 1;
/*     */       }
/* 430 */       if (d > 1.0E+150D) {
/* 431 */         d = 1.0E+150D;
/* 432 */         j = 1;
/*     */       }
/* 434 */       arrayOfDouble[i] = d;
/*     */     }
/* 436 */     if (j != 0) {
/* 437 */       callErrorOrErrorData(100155);
/*     */     }
/*     */ 
/* 440 */     if (this.mesh == null) {
/* 441 */       if (this.cacheCount < 100) {
/* 442 */         cacheVertex(arrayOfDouble, paramObject);
/* 443 */         return;
/*     */       }
/* 445 */       if (!flushCache()) {
/* 446 */         callErrorOrErrorData(100902);
/* 447 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 451 */     if (!addVertex(arrayOfDouble, paramObject))
/* 452 */       callErrorOrErrorData(100902);
/*     */   }
/*     */ 
/*     */   public void gluTessBeginPolygon(Object paramObject)
/*     */   {
/* 458 */     requireState(0);
/*     */ 
/* 460 */     this.state = 1;
/* 461 */     this.cacheCount = 0;
/* 462 */     this.flushCacheOnNextVertex = false;
/* 463 */     this.mesh = null;
/*     */ 
/* 465 */     this.polygonData = paramObject;
/*     */   }
/*     */ 
/*     */   public void gluTessBeginContour()
/*     */   {
/* 470 */     requireState(1);
/*     */ 
/* 472 */     this.state = 2;
/* 473 */     this.lastEdge = null;
/* 474 */     if (this.cacheCount > 0)
/*     */     {
/* 479 */       this.flushCacheOnNextVertex = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void gluTessEndContour()
/*     */   {
/* 485 */     requireState(2);
/* 486 */     this.state = 1;
/*     */   }
/*     */ 
/*     */   public void gluTessEndPolygon()
/*     */   {
/*     */     try
/*     */     {
/* 493 */       requireState(1);
/* 494 */       this.state = 0;
/*     */ 
/* 496 */       if (this.mesh == null) {
/* 497 */         if (!this.flagBoundary)
/*     */         {
/* 504 */           if (Render.__gl_renderCache(this)) {
/* 505 */             this.polygonData = null;
/* 506 */             return;
/*     */           }
/*     */         }
/* 509 */         if (!flushCache()) throw new RuntimeException();
/*     */ 
/*     */       }
/*     */ 
/* 515 */       Normal.__gl_projectPolygon(this);
/*     */ 
/* 523 */       if (!Sweep.__gl_computeInterior(this)) {
/* 524 */         throw new RuntimeException();
/*     */       }
/*     */ 
/* 527 */       GLUmesh localGLUmesh = this.mesh;
/* 528 */       if (!this.fatalError) {
/* 529 */         boolean bool = true;
/*     */ 
/* 535 */         if (this.boundaryOnly)
/* 536 */           bool = TessMono.__gl_meshSetWindingNumber(localGLUmesh, 1, true);
/*     */         else {
/* 538 */           bool = TessMono.__gl_meshTessellateInterior(localGLUmesh, this.avoidDegenerateTris);
/*     */         }
/* 540 */         if (!bool) throw new RuntimeException();
/*     */ 
/* 542 */         Mesh.__gl_meshCheckMesh(localGLUmesh);
/*     */ 
/* 544 */         if ((this.callBegin != NULL_CB) || (this.callEnd != NULL_CB) || (this.callVertex != NULL_CB) || (this.callEdgeFlag != NULL_CB) || (this.callBeginData != NULL_CB) || (this.callEndData != NULL_CB) || (this.callVertexData != NULL_CB) || (this.callEdgeFlagData != NULL_CB))
/*     */         {
/* 550 */           if (this.boundaryOnly)
/* 551 */             Render.__gl_renderBoundary(this, localGLUmesh);
/*     */           else {
/* 553 */             Render.__gl_renderMesh(this, localGLUmesh);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 571 */       Mesh.__gl_meshDeleteMesh(localGLUmesh);
/* 572 */       this.polygonData = null;
/* 573 */       localGLUmesh = null;
/*     */     } catch (Exception localException) {
/* 575 */       localException.printStackTrace();
/* 576 */       callErrorOrErrorData(100902);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void gluBeginPolygon()
/*     */   {
/* 585 */     gluTessBeginPolygon(null);
/* 586 */     gluTessBeginContour();
/*     */   }
/*     */ 
/*     */   public void gluNextContour(int paramInt)
/*     */   {
/* 592 */     gluTessEndContour();
/* 593 */     gluTessBeginContour();
/*     */   }
/*     */ 
/*     */   public void gluEndPolygon()
/*     */   {
/* 598 */     gluTessEndContour();
/* 599 */     gluTessEndPolygon();
/*     */   }
/*     */ 
/*     */   void callBeginOrBeginData(int paramInt) {
/* 603 */     if (this.callBeginData != NULL_CB)
/* 604 */       this.callBeginData.beginData(paramInt, this.polygonData);
/*     */     else
/* 606 */       this.callBegin.begin(paramInt);
/*     */   }
/*     */ 
/*     */   void callVertexOrVertexData(Object paramObject) {
/* 610 */     if (this.callVertexData != NULL_CB)
/* 611 */       this.callVertexData.vertexData(paramObject, this.polygonData);
/*     */     else
/* 613 */       this.callVertex.vertex(paramObject);
/*     */   }
/*     */ 
/*     */   void callEdgeFlagOrEdgeFlagData(boolean paramBoolean) {
/* 617 */     if (this.callEdgeFlagData != NULL_CB)
/* 618 */       this.callEdgeFlagData.edgeFlagData(paramBoolean, this.polygonData);
/*     */     else
/* 620 */       this.callEdgeFlag.edgeFlag(paramBoolean);
/*     */   }
/*     */ 
/*     */   void callEndOrEndData() {
/* 624 */     if (this.callEndData != NULL_CB)
/* 625 */       this.callEndData.endData(this.polygonData);
/*     */     else
/* 627 */       this.callEnd.end();
/*     */   }
/*     */ 
/*     */   void callCombineOrCombineData(double[] paramArrayOfDouble, Object[] paramArrayOfObject1, float[] paramArrayOfFloat, Object[] paramArrayOfObject2) {
/* 631 */     if (this.callCombineData != NULL_CB)
/* 632 */       this.callCombineData.combineData(paramArrayOfDouble, paramArrayOfObject1, paramArrayOfFloat, paramArrayOfObject2, this.polygonData);
/*     */     else
/* 634 */       this.callCombine.combine(paramArrayOfDouble, paramArrayOfObject1, paramArrayOfFloat, paramArrayOfObject2);
/*     */   }
/*     */ 
/*     */   void callErrorOrErrorData(int paramInt) {
/* 638 */     if (this.callErrorData != NULL_CB)
/* 639 */       this.callErrorData.errorData(paramInt, this.polygonData);
/*     */     else
/* 641 */       this.callError.error(paramInt);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.TessellatorImpl
 * JD-Core Version:    0.6.2
 */