/*     */ package com.sun.openpisces;
/*     */ 
/*     */ import com.sun.javafx.geom.PathConsumer2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public abstract class TransformingPathConsumer2D
/*     */   implements PathConsumer2D
/*     */ {
/*     */   protected PathConsumer2D out;
/*     */ 
/*     */   public TransformingPathConsumer2D(PathConsumer2D paramPathConsumer2D)
/*     */   {
/*  35 */     this.out = paramPathConsumer2D;
/*     */   }
/*     */ 
/*     */   public void setConsumer(PathConsumer2D paramPathConsumer2D) {
/*  39 */     this.out = paramPathConsumer2D;
/*     */   }
/*     */ 
/*     */   static final class DeltaTransformFilter extends TransformingPathConsumer2D
/*     */   {
/*     */     private float Mxx;
/*     */     private float Mxy;
/*     */     private float Myx;
/*     */     private float Myy;
/*     */ 
/*     */     DeltaTransformFilter(PathConsumer2D paramPathConsumer2D, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 440 */       super();
/* 441 */       set(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */     }
/*     */ 
/*     */     public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 447 */       this.Mxx = paramFloat1;
/* 448 */       this.Mxy = paramFloat2;
/* 449 */       this.Myx = paramFloat3;
/* 450 */       this.Myy = paramFloat4;
/*     */     }
/*     */ 
/*     */     public void moveTo(float paramFloat1, float paramFloat2) {
/* 454 */       this.out.moveTo(paramFloat1 * this.Mxx + paramFloat2 * this.Mxy, paramFloat1 * this.Myx + paramFloat2 * this.Myy);
/*     */     }
/*     */ 
/*     */     public void lineTo(float paramFloat1, float paramFloat2)
/*     */     {
/* 459 */       this.out.lineTo(paramFloat1 * this.Mxx + paramFloat2 * this.Mxy, paramFloat1 * this.Myx + paramFloat2 * this.Myy);
/*     */     }
/*     */ 
/*     */     public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 466 */       this.out.quadTo(paramFloat1 * this.Mxx + paramFloat2 * this.Mxy, paramFloat1 * this.Myx + paramFloat2 * this.Myy, paramFloat3 * this.Mxx + paramFloat4 * this.Mxy, paramFloat3 * this.Myx + paramFloat4 * this.Myy);
/*     */     }
/*     */ 
/*     */     public void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */     {
/* 476 */       this.out.curveTo(paramFloat1 * this.Mxx + paramFloat2 * this.Mxy, paramFloat1 * this.Myx + paramFloat2 * this.Myy, paramFloat3 * this.Mxx + paramFloat4 * this.Mxy, paramFloat3 * this.Myx + paramFloat4 * this.Myy, paramFloat5 * this.Mxx + paramFloat6 * this.Mxy, paramFloat5 * this.Myx + paramFloat6 * this.Myy);
/*     */     }
/*     */ 
/*     */     public void closePath()
/*     */     {
/* 485 */       this.out.closePath();
/*     */     }
/*     */ 
/*     */     public void pathDone() {
/* 489 */       this.out.pathDone();
/*     */     }
/*     */ 
/*     */     public long getNativeConsumer() {
/* 493 */       return 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   static final class DeltaScaleFilter extends TransformingPathConsumer2D
/*     */   {
/*     */     private float sx;
/*     */     private float sy;
/*     */ 
/*     */     public DeltaScaleFilter(PathConsumer2D paramPathConsumer2D, float paramFloat1, float paramFloat2)
/*     */     {
/* 384 */       super();
/* 385 */       set(paramFloat1, paramFloat2);
/*     */     }
/*     */ 
/*     */     public void set(float paramFloat1, float paramFloat2) {
/* 389 */       this.sx = paramFloat1;
/* 390 */       this.sy = paramFloat2;
/*     */     }
/*     */ 
/*     */     public void moveTo(float paramFloat1, float paramFloat2) {
/* 394 */       this.out.moveTo(paramFloat1 * this.sx, paramFloat2 * this.sy);
/*     */     }
/*     */ 
/*     */     public void lineTo(float paramFloat1, float paramFloat2) {
/* 398 */       this.out.lineTo(paramFloat1 * this.sx, paramFloat2 * this.sy);
/*     */     }
/*     */ 
/*     */     public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 404 */       this.out.quadTo(paramFloat1 * this.sx, paramFloat2 * this.sy, paramFloat3 * this.sx, paramFloat4 * this.sy);
/*     */     }
/*     */ 
/*     */     public void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */     {
/* 412 */       this.out.curveTo(paramFloat1 * this.sx, paramFloat2 * this.sy, paramFloat3 * this.sx, paramFloat4 * this.sy, paramFloat5 * this.sx, paramFloat6 * this.sy);
/*     */     }
/*     */ 
/*     */     public void closePath()
/*     */     {
/* 418 */       this.out.closePath();
/*     */     }
/*     */ 
/*     */     public void pathDone() {
/* 422 */       this.out.pathDone();
/*     */     }
/*     */ 
/*     */     public long getNativeConsumer() {
/* 426 */       return 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   static final class TransformFilter extends TransformingPathConsumer2D
/*     */   {
/*     */     private float Mxx;
/*     */     private float Mxy;
/*     */     private float Mxt;
/*     */     private float Myx;
/*     */     private float Myy;
/*     */     private float Myt;
/*     */ 
/*     */     TransformFilter(PathConsumer2D paramPathConsumer2D, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */     {
/* 320 */       super();
/* 321 */       set(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*     */     }
/*     */ 
/*     */     public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */     {
/* 327 */       this.Mxx = paramFloat1;
/* 328 */       this.Mxy = paramFloat2;
/* 329 */       this.Mxt = paramFloat3;
/* 330 */       this.Myx = paramFloat4;
/* 331 */       this.Myy = paramFloat5;
/* 332 */       this.Myt = paramFloat6;
/*     */     }
/*     */ 
/*     */     public void moveTo(float paramFloat1, float paramFloat2) {
/* 336 */       this.out.moveTo(paramFloat1 * this.Mxx + paramFloat2 * this.Mxy + this.Mxt, paramFloat1 * this.Myx + paramFloat2 * this.Myy + this.Myt);
/*     */     }
/*     */ 
/*     */     public void lineTo(float paramFloat1, float paramFloat2)
/*     */     {
/* 341 */       this.out.lineTo(paramFloat1 * this.Mxx + paramFloat2 * this.Mxy + this.Mxt, paramFloat1 * this.Myx + paramFloat2 * this.Myy + this.Myt);
/*     */     }
/*     */ 
/*     */     public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 348 */       this.out.quadTo(paramFloat1 * this.Mxx + paramFloat2 * this.Mxy + this.Mxt, paramFloat1 * this.Myx + paramFloat2 * this.Myy + this.Myt, paramFloat3 * this.Mxx + paramFloat4 * this.Mxy + this.Mxt, paramFloat3 * this.Myx + paramFloat4 * this.Myy + this.Myt);
/*     */     }
/*     */ 
/*     */     public void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */     {
/* 358 */       this.out.curveTo(paramFloat1 * this.Mxx + paramFloat2 * this.Mxy + this.Mxt, paramFloat1 * this.Myx + paramFloat2 * this.Myy + this.Myt, paramFloat3 * this.Mxx + paramFloat4 * this.Mxy + this.Mxt, paramFloat3 * this.Myx + paramFloat4 * this.Myy + this.Myt, paramFloat5 * this.Mxx + paramFloat6 * this.Mxy + this.Mxt, paramFloat5 * this.Myx + paramFloat6 * this.Myy + this.Myt);
/*     */     }
/*     */ 
/*     */     public void closePath()
/*     */     {
/* 367 */       this.out.closePath();
/*     */     }
/*     */ 
/*     */     public void pathDone() {
/* 371 */       this.out.pathDone();
/*     */     }
/*     */ 
/*     */     public long getNativeConsumer() {
/* 375 */       return 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   static final class ScaleTranslateFilter extends TransformingPathConsumer2D
/*     */   {
/*     */     private float sx;
/*     */     private float sy;
/*     */     private float tx;
/*     */     private float ty;
/*     */ 
/*     */     ScaleTranslateFilter(PathConsumer2D paramPathConsumer2D, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 260 */       super();
/* 261 */       set(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */     }
/*     */ 
/*     */     public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 265 */       this.sx = paramFloat1;
/* 266 */       this.sy = paramFloat2;
/* 267 */       this.tx = paramFloat3;
/* 268 */       this.ty = paramFloat4;
/*     */     }
/*     */ 
/*     */     public void moveTo(float paramFloat1, float paramFloat2) {
/* 272 */       this.out.moveTo(paramFloat1 * this.sx + this.tx, paramFloat2 * this.sy + this.ty);
/*     */     }
/*     */ 
/*     */     public void lineTo(float paramFloat1, float paramFloat2) {
/* 276 */       this.out.lineTo(paramFloat1 * this.sx + this.tx, paramFloat2 * this.sy + this.ty);
/*     */     }
/*     */ 
/*     */     public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 282 */       this.out.quadTo(paramFloat1 * this.sx + this.tx, paramFloat2 * this.sy + this.ty, paramFloat3 * this.sx + this.tx, paramFloat4 * this.sy + this.ty);
/*     */     }
/*     */ 
/*     */     public void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */     {
/* 290 */       this.out.curveTo(paramFloat1 * this.sx + this.tx, paramFloat2 * this.sy + this.ty, paramFloat3 * this.sx + this.tx, paramFloat4 * this.sy + this.ty, paramFloat5 * this.sx + this.tx, paramFloat6 * this.sy + this.ty);
/*     */     }
/*     */ 
/*     */     public void closePath()
/*     */     {
/* 296 */       this.out.closePath();
/*     */     }
/*     */ 
/*     */     public void pathDone() {
/* 300 */       this.out.pathDone();
/*     */     }
/*     */ 
/*     */     public long getNativeConsumer() {
/* 304 */       return 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   static final class TranslateFilter extends TransformingPathConsumer2D
/*     */   {
/*     */     private float tx;
/*     */     private float ty;
/*     */ 
/*     */     TranslateFilter(PathConsumer2D paramPathConsumer2D, float paramFloat1, float paramFloat2)
/*     */     {
/* 205 */       super();
/* 206 */       set(paramFloat1, paramFloat2);
/*     */     }
/*     */ 
/*     */     public void set(float paramFloat1, float paramFloat2) {
/* 210 */       this.tx = paramFloat1;
/* 211 */       this.ty = paramFloat2;
/*     */     }
/*     */ 
/*     */     public void moveTo(float paramFloat1, float paramFloat2) {
/* 215 */       this.out.moveTo(paramFloat1 + this.tx, paramFloat2 + this.ty);
/*     */     }
/*     */ 
/*     */     public void lineTo(float paramFloat1, float paramFloat2) {
/* 219 */       this.out.lineTo(paramFloat1 + this.tx, paramFloat2 + this.ty);
/*     */     }
/*     */ 
/*     */     public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 225 */       this.out.quadTo(paramFloat1 + this.tx, paramFloat2 + this.ty, paramFloat3 + this.tx, paramFloat4 + this.ty);
/*     */     }
/*     */ 
/*     */     public void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */     {
/* 233 */       this.out.curveTo(paramFloat1 + this.tx, paramFloat2 + this.ty, paramFloat3 + this.tx, paramFloat4 + this.ty, paramFloat5 + this.tx, paramFloat6 + this.ty);
/*     */     }
/*     */ 
/*     */     public void closePath()
/*     */     {
/* 239 */       this.out.closePath();
/*     */     }
/*     */ 
/*     */     public void pathDone() {
/* 243 */       this.out.pathDone();
/*     */     }
/*     */ 
/*     */     public long getNativeConsumer() {
/* 247 */       return 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final class FilterSet
/*     */   {
/*     */     private TransformingPathConsumer2D.TranslateFilter translater;
/*     */     private TransformingPathConsumer2D.DeltaScaleFilter deltascaler;
/*     */     private TransformingPathConsumer2D.ScaleTranslateFilter scaletranslater;
/*     */     private TransformingPathConsumer2D.DeltaTransformFilter deltatransformer;
/*     */     private TransformingPathConsumer2D.TransformFilter transformer;
/*     */ 
/*     */     public PathConsumer2D getConsumer(PathConsumer2D paramPathConsumer2D, BaseTransform paramBaseTransform)
/*     */     {
/* 139 */       if (paramBaseTransform == null) {
/* 140 */         return paramPathConsumer2D;
/*     */       }
/* 142 */       float f1 = (float)paramBaseTransform.getMxx();
/* 143 */       float f2 = (float)paramBaseTransform.getMxy();
/* 144 */       float f3 = (float)paramBaseTransform.getMxt();
/* 145 */       float f4 = (float)paramBaseTransform.getMyx();
/* 146 */       float f5 = (float)paramBaseTransform.getMyy();
/* 147 */       float f6 = (float)paramBaseTransform.getMyt();
/* 148 */       if ((f2 == 0.0F) && (f4 == 0.0F)) {
/* 149 */         if ((f1 == 1.0F) && (f5 == 1.0F)) {
/* 150 */           if ((f3 == 0.0F) && (f6 == 0.0F)) {
/* 151 */             return paramPathConsumer2D;
/*     */           }
/* 153 */           if (this.translater == null)
/* 154 */             this.translater = new TransformingPathConsumer2D.TranslateFilter(paramPathConsumer2D, f3, f6);
/*     */           else {
/* 156 */             this.translater.set(f3, f6);
/*     */           }
/* 158 */           return this.translater;
/*     */         }
/*     */ 
/* 161 */         if ((f3 == 0.0F) && (f6 == 0.0F)) {
/* 162 */           if (this.deltascaler == null)
/* 163 */             this.deltascaler = new TransformingPathConsumer2D.DeltaScaleFilter(paramPathConsumer2D, f1, f5);
/*     */           else {
/* 165 */             this.deltascaler.set(f1, f5);
/*     */           }
/* 167 */           return this.deltascaler;
/*     */         }
/* 169 */         if (this.scaletranslater == null) {
/* 170 */           this.scaletranslater = new TransformingPathConsumer2D.ScaleTranslateFilter(paramPathConsumer2D, f1, f5, f3, f6);
/*     */         }
/*     */         else {
/* 173 */           this.scaletranslater.set(f1, f5, f3, f6);
/*     */         }
/* 175 */         return this.scaletranslater;
/*     */       }
/*     */ 
/* 178 */       if ((f3 == 0.0F) && (f6 == 0.0F)) {
/* 179 */         if (this.deltatransformer == null) {
/* 180 */           this.deltatransformer = new TransformingPathConsumer2D.DeltaTransformFilter(paramPathConsumer2D, f1, f2, f4, f5);
/*     */         }
/*     */         else {
/* 183 */           this.deltatransformer.set(f1, f2, f4, f5);
/*     */         }
/* 185 */         return this.deltatransformer;
/*     */       }
/* 187 */       if (this.transformer == null) {
/* 188 */         this.transformer = new TransformingPathConsumer2D.TransformFilter(paramPathConsumer2D, f1, f2, f3, f4, f5, f6);
/*     */       }
/*     */       else {
/* 191 */         this.transformer.set(f1, f2, f3, f4, f5, f6);
/*     */       }
/* 193 */       return this.transformer;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.openpisces.TransformingPathConsumer2D
 * JD-Core Version:    0.6.2
 */