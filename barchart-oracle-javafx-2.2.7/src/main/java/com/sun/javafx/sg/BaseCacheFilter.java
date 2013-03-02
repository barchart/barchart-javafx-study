/*     */ package com.sun.javafx.sg;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.Affine2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ 
/*     */ public abstract class BaseCacheFilter
/*     */ {
/*     */   protected ImageData cachedImageData;
/*  38 */   private Affine2D cachedXform = new Affine2D();
/*     */   private double cachedScaleX;
/*     */   private double cachedScaleY;
/*     */   private double cachedRotate;
/*     */   protected double cachedX;
/*     */   protected double cachedY;
/*     */   protected BaseNode node;
/*  50 */   protected Affine2D screenXform = new Affine2D();
/*     */   private boolean scaleHint;
/*     */   private boolean rotateHint;
/*     */   private PGNode.CacheHint cacheHint;
/*  60 */   private boolean wasUnsupported = false;
/*     */   private static final double EPSILON = 1.0E-07D;
/*     */ 
/*     */   protected BaseCacheFilter(BaseNode paramBaseNode, PGNode.CacheHint paramCacheHint)
/*     */   {
/*  66 */     this.node = paramBaseNode;
/*  67 */     setHint(paramCacheHint);
/*     */   }
/*     */ 
/*     */   public void setHint(PGNode.CacheHint paramCacheHint) {
/*  71 */     this.cacheHint = paramCacheHint;
/*  72 */     this.scaleHint = ((paramCacheHint == PGNode.CacheHint.SCALE) || (paramCacheHint == PGNode.CacheHint.SCALE_AND_ROTATE));
/*     */ 
/*  74 */     this.rotateHint = ((paramCacheHint == PGNode.CacheHint.ROTATE) || (paramCacheHint == PGNode.CacheHint.SCALE_AND_ROTATE));
/*     */   }
/*     */ 
/*     */   boolean matchesHint(PGNode.CacheHint paramCacheHint)
/*     */   {
/*  83 */     return this.cacheHint == paramCacheHint;
/*     */   }
/*     */ 
/*     */   protected abstract ImageData impl_createImageData(FilterContext paramFilterContext, BaseTransform paramBaseTransform);
/*     */ 
/*     */   protected abstract void impl_renderNodeToScreen(Object paramObject, BaseTransform paramBaseTransform);
/*     */ 
/*     */   protected abstract void impl_renderCacheToScreen(Object paramObject, Filterable paramFilterable, double paramDouble1, double paramDouble2);
/*     */ 
/*     */   public void render(Object paramObject, BaseTransform paramBaseTransform, FilterContext paramFilterContext)
/*     */   {
/* 116 */     double d1 = paramBaseTransform.getMxx();
/* 117 */     double d2 = paramBaseTransform.getMyx();
/* 118 */     double d3 = paramBaseTransform.getMxy();
/* 119 */     double d4 = paramBaseTransform.getMyy();
/* 120 */     double d5 = paramBaseTransform.getMxt();
/* 121 */     double d6 = paramBaseTransform.getMyt();
/*     */ 
/* 123 */     double[] arrayOfDouble = unmatrix(paramBaseTransform);
/* 124 */     boolean bool = unsupported(arrayOfDouble);
/*     */ 
/* 126 */     if (needToRenderCache(paramFilterContext, paramBaseTransform, arrayOfDouble)) {
/* 127 */       invalidate();
/*     */ 
/* 129 */       this.cachedXform.setTransform(d1, d2, d3, d4, 0.0D, 0.0D);
/* 130 */       this.cachedScaleX = arrayOfDouble[0];
/* 131 */       this.cachedScaleY = arrayOfDouble[1];
/* 132 */       this.cachedRotate = arrayOfDouble[2];
/*     */ 
/* 134 */       this.cachedImageData = impl_createImageData(paramFilterContext, this.cachedXform);
/*     */ 
/* 137 */       localObject = this.cachedImageData.getUntransformedBounds();
/*     */ 
/* 142 */       this.cachedX = ((Rectangle)localObject).x;
/* 143 */       this.cachedY = ((Rectangle)localObject).y;
/*     */ 
/* 147 */       this.screenXform.setTransform(BaseTransform.IDENTITY_TRANSFORM);
/*     */     }
/* 150 */     else if (bool)
/*     */     {
/* 157 */       this.screenXform.setTransform(BaseTransform.IDENTITY_TRANSFORM);
/*     */     } else {
/* 159 */       updateScreenXform(arrayOfDouble);
/*     */     }
/*     */ 
/* 164 */     this.wasUnsupported = bool;
/*     */ 
/* 166 */     Object localObject = this.cachedImageData.getUntransformedImage();
/* 167 */     if (localObject == null)
/* 168 */       impl_renderNodeToScreen(paramObject, paramBaseTransform);
/*     */     else
/* 170 */       impl_renderCacheToScreen(paramObject, (Filterable)localObject, d5, d6);
/*     */   }
/*     */ 
/*     */   boolean unsupported(double[] paramArrayOfDouble)
/*     */   {
/* 180 */     double d1 = paramArrayOfDouble[0];
/* 181 */     double d2 = paramArrayOfDouble[1];
/* 182 */     double d3 = paramArrayOfDouble[2];
/*     */ 
/* 185 */     if ((d3 > 1.0E-07D) || (d3 < -1.0E-07D))
/*     */     {
/* 188 */       if ((d1 > d2 + 1.0E-07D) || (d2 > d1 + 1.0E-07D) || (d1 < d2 - 1.0E-07D) || (d2 < d1 - 1.0E-07D) || (this.cachedScaleX > this.cachedScaleY + 1.0E-07D) || (this.cachedScaleY > this.cachedScaleX + 1.0E-07D) || (this.cachedScaleX < this.cachedScaleY - 1.0E-07D) || (this.cachedScaleY < this.cachedScaleX - 1.0E-07D))
/*     */       {
/* 194 */         return true;
/*     */       }
/*     */     }
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */   boolean needToRenderCache(FilterContext paramFilterContext, BaseTransform paramBaseTransform, double[] paramArrayOfDouble)
/*     */   {
/* 205 */     if ((this.cachedImageData == null) || (!this.cachedImageData.validate(paramFilterContext))) {
/* 206 */       return true;
/*     */     }
/*     */ 
/* 209 */     if ((this.cachedXform.getMxx() == paramBaseTransform.getMxx()) && (this.cachedXform.getMyy() == paramBaseTransform.getMyy()) && (this.cachedXform.getMxy() == paramBaseTransform.getMxy()) && (this.cachedXform.getMyx() == paramBaseTransform.getMyx()))
/*     */     {
/* 214 */       return false;
/*     */     }
/*     */ 
/* 217 */     if ((this.wasUnsupported) || (unsupported(paramArrayOfDouble))) {
/* 218 */       return true;
/*     */     }
/*     */ 
/* 221 */     double d1 = paramArrayOfDouble[0];
/* 222 */     double d2 = paramArrayOfDouble[1];
/* 223 */     double d3 = paramArrayOfDouble[2];
/* 224 */     if (this.scaleHint) {
/* 225 */       if (this.rotateHint) {
/* 226 */         return false;
/*     */       }
/*     */ 
/* 229 */       if ((this.cachedRotate - 1.0E-07D < d3) && (d3 < this.cachedRotate + 1.0E-07D)) {
/* 230 */         return false;
/*     */       }
/* 232 */       return true;
/*     */     }
/*     */ 
/* 236 */     if (this.rotateHint)
/*     */     {
/* 238 */       if ((this.cachedScaleX - 1.0E-07D < d1) && (d1 < this.cachedScaleX + 1.0E-07D) && (this.cachedScaleY - 1.0E-07D < d2) && (d2 < this.cachedScaleY + 1.0E-07D))
/*     */       {
/* 240 */         return false;
/*     */       }
/* 242 */       return true;
/*     */     }
/*     */ 
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */   void updateScreenXform(double[] paramArrayOfDouble)
/*     */   {
/*     */     double d1;
/* 259 */     if (this.scaleHint)
/*     */     {
/*     */       double d2;
/* 260 */       if (this.rotateHint) {
/* 261 */         d1 = paramArrayOfDouble[0] / this.cachedScaleX;
/* 262 */         d2 = paramArrayOfDouble[1] / this.cachedScaleY;
/* 263 */         double d3 = paramArrayOfDouble[2] - this.cachedRotate;
/*     */ 
/* 265 */         this.screenXform.setToScale(d1, d2);
/* 266 */         this.screenXform.rotate(d3);
/*     */       } else {
/* 268 */         d1 = paramArrayOfDouble[0] / this.cachedScaleX;
/* 269 */         d2 = paramArrayOfDouble[1] / this.cachedScaleY;
/* 270 */         this.screenXform.setToScale(d1, d2);
/*     */       }
/*     */     }
/* 273 */     else if (this.rotateHint) {
/* 274 */       d1 = paramArrayOfDouble[2] - this.cachedRotate;
/* 275 */       this.screenXform.setToRotation(d1, 0.0D, 0.0D);
/*     */     }
/*     */     else {
/* 278 */       this.screenXform.setTransform(BaseTransform.IDENTITY_TRANSFORM);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/* 284 */     if (this.cachedImageData != null) {
/* 285 */       this.cachedImageData.unref();
/* 286 */       this.cachedImageData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose() {
/* 291 */     invalidate();
/* 292 */     this.node = null;
/*     */   }
/*     */ 
/*     */   double[] unmatrix(BaseTransform paramBaseTransform)
/*     */   {
/* 320 */     double[] arrayOfDouble = new double[3];
/*     */ 
/* 322 */     double[][] arrayOfDouble1 = { { paramBaseTransform.getMxx(), paramBaseTransform.getMxy() }, { paramBaseTransform.getMyx(), paramBaseTransform.getMyy() } };
/*     */ 
/* 329 */     double d1 = v2length(arrayOfDouble1[0]);
/* 330 */     v2scale(arrayOfDouble1[0], 1.0D);
/*     */ 
/* 337 */     double d2 = v2dot(arrayOfDouble1[0], arrayOfDouble1[1]);
/*     */ 
/* 340 */     v2combine(arrayOfDouble1[1], arrayOfDouble1[0], arrayOfDouble1[1], 1.0D, -d2);
/*     */ 
/* 347 */     double d3 = v2length(arrayOfDouble1[1]);
/* 348 */     v2scale(arrayOfDouble1[1], 1.0D);
/* 349 */     d2 /= d3;
/*     */ 
/* 370 */     if (arrayOfDouble1[0][0] * arrayOfDouble1[1][1] - arrayOfDouble1[0][1] * arrayOfDouble1[1][0] < 0.0D)
/*     */     {
/* 373 */       d1 *= -1.0D;
/* 374 */       d3 *= -1.0D;
/* 375 */       arrayOfDouble1[0][0] *= -1.0D;
/* 376 */       arrayOfDouble1[0][1] *= -1.0D;
/* 377 */       arrayOfDouble1[1][0] *= -1.0D;
/* 378 */       arrayOfDouble1[1][1] *= -1.0D;
/*     */     }
/*     */ 
/* 389 */     double d4 = arrayOfDouble1[1][0];
/* 390 */     double d5 = arrayOfDouble1[0][0];
/* 391 */     double d6 = 0.0D;
/*     */ 
/* 396 */     if (d4 >= 0.0D)
/*     */     {
/* 398 */       d6 = Math.acos(d5);
/*     */     }
/* 400 */     else if (d5 > 0.0D)
/*     */     {
/* 403 */       d6 = 6.283185307179586D + Math.asin(d4);
/*     */     }
/*     */     else
/*     */     {
/* 408 */       d6 = 3.141592653589793D + Math.acos(-d5);
/*     */     }
/*     */ 
/* 412 */     arrayOfDouble[0] = d1;
/* 413 */     arrayOfDouble[1] = d3;
/* 414 */     arrayOfDouble[2] = d6;
/*     */ 
/* 416 */     return arrayOfDouble;
/*     */   }
/*     */ 
/*     */   void v2combine(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3, double paramDouble1, double paramDouble2)
/*     */   {
/* 440 */     paramArrayOfDouble3[0] = (paramDouble1 * paramArrayOfDouble1[0] + paramDouble2 * paramArrayOfDouble2[0]);
/* 441 */     paramArrayOfDouble3[1] = (paramDouble1 * paramArrayOfDouble1[1] + paramDouble2 * paramArrayOfDouble2[1]);
/*     */   }
/*     */ 
/*     */   double v2dot(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
/*     */   {
/* 449 */     return paramArrayOfDouble1[0] * paramArrayOfDouble2[0] + paramArrayOfDouble1[1] * paramArrayOfDouble2[1];
/*     */   }
/*     */ 
/*     */   double[] v2scale(double[] paramArrayOfDouble, double paramDouble)
/*     */   {
/* 458 */     double d = v2length(paramArrayOfDouble);
/* 459 */     double[] arrayOfDouble = paramArrayOfDouble;
/* 460 */     if (d != 0.0D) {
/* 461 */       paramArrayOfDouble[0] *= paramDouble / d;
/* 462 */       paramArrayOfDouble[1] *= paramDouble / d;
/*     */     }
/* 464 */     return arrayOfDouble;
/*     */   }
/*     */ 
/*     */   double v2length(double[] paramArrayOfDouble)
/*     */   {
/* 473 */     return Math.sqrt(paramArrayOfDouble[0] * paramArrayOfDouble[0] + paramArrayOfDouble[1] * paramArrayOfDouble[1]);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.BaseCacheFilter
 * JD-Core Version:    0.6.2
 */