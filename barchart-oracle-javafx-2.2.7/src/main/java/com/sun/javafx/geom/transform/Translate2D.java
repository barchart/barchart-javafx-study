/*     */ package com.sun.javafx.geom.transform;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.Vec3d;
/*     */ 
/*     */ public class Translate2D extends BaseTransform
/*     */ {
/*     */   private double mxt;
/*     */   private double myt;
/* 527 */   private static final long BASE_HASH = l;
/*     */ 
/*     */   public static BaseTransform getInstance(double paramDouble1, double paramDouble2)
/*     */   {
/*  27 */     if ((paramDouble1 == 0.0D) && (paramDouble2 == 0.0D)) {
/*  28 */       return IDENTITY_TRANSFORM;
/*     */     }
/*  30 */     return new Translate2D(paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */   public Translate2D(double paramDouble1, double paramDouble2)
/*     */   {
/*  35 */     this.mxt = paramDouble1;
/*  36 */     this.myt = paramDouble2;
/*     */   }
/*     */ 
/*     */   public Translate2D(BaseTransform paramBaseTransform) {
/*  40 */     if (!paramBaseTransform.isTranslateOrIdentity()) {
/*  41 */       degreeError(BaseTransform.Degree.TRANSLATE_2D);
/*     */     }
/*  43 */     this.mxt = paramBaseTransform.getMxt();
/*  44 */     this.myt = paramBaseTransform.getMyt();
/*     */   }
/*     */ 
/*     */   public BaseTransform.Degree getDegree()
/*     */   {
/*  49 */     return BaseTransform.Degree.TRANSLATE_2D;
/*     */   }
/*     */ 
/*     */   public double getDeterminant()
/*     */   {
/*  54 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   public double getMxt()
/*     */   {
/*  59 */     return this.mxt;
/*     */   }
/*     */ 
/*     */   public double getMyt()
/*     */   {
/*  64 */     return this.myt;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/*  69 */     return (this.mxt == 0.0D) && (this.myt == 0.0D) ? 0 : 1;
/*     */   }
/*     */ 
/*     */   public boolean isIdentity()
/*     */   {
/*  74 */     return (this.mxt == 0.0D) && (this.myt == 0.0D);
/*     */   }
/*     */ 
/*     */   public boolean isTranslateOrIdentity()
/*     */   {
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean is2D()
/*     */   {
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/*  89 */     if (paramPoint2D2 == null) paramPoint2D2 = makePoint(paramPoint2D1, paramPoint2D2);
/*  90 */     paramPoint2D2.setLocation((float)(paramPoint2D1.x + this.mxt), (float)(paramPoint2D1.y + this.myt));
/*     */ 
/*  93 */     return paramPoint2D2;
/*     */   }
/*     */ 
/*     */   public Point2D inverseTransform(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/*  98 */     if (paramPoint2D2 == null) paramPoint2D2 = makePoint(paramPoint2D1, paramPoint2D2);
/*  99 */     paramPoint2D2.setLocation((float)(paramPoint2D1.x - this.mxt), (float)(paramPoint2D1.y - this.myt));
/*     */ 
/* 102 */     return paramPoint2D2;
/*     */   }
/*     */ 
/*     */   public Vec3d transform(Vec3d paramVec3d1, Vec3d paramVec3d2)
/*     */   {
/* 107 */     if (paramVec3d2 == null) {
/* 108 */       paramVec3d2 = new Vec3d();
/*     */     }
/* 110 */     paramVec3d1.x += this.mxt;
/* 111 */     paramVec3d1.y += this.myt;
/* 112 */     paramVec3d2.z = paramVec3d1.z;
/* 113 */     return paramVec3d2;
/*     */   }
/*     */ 
/*     */   public void transform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*     */   {
/* 121 */     float f1 = (float)this.mxt;
/* 122 */     float f2 = (float)this.myt;
/* 123 */     if (paramArrayOfFloat2 == paramArrayOfFloat1) {
/* 124 */       if ((paramInt2 > paramInt1) && (paramInt2 < paramInt1 + paramInt3 * 2))
/*     */       {
/* 133 */         System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*     */ 
/* 135 */         paramInt1 = paramInt2;
/*     */       }
/* 137 */       if ((paramInt2 == paramInt1) && (f1 == 0.0F) && (f2 == 0.0F)) {
/* 138 */         return;
/*     */       }
/*     */     }
/* 141 */     for (int i = 0; i < paramInt3; i++) {
/* 142 */       paramArrayOfFloat1[(paramInt1++)] += f1;
/* 143 */       paramArrayOfFloat1[(paramInt1++)] += f2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void transform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3)
/*     */   {
/* 152 */     double d1 = this.mxt;
/* 153 */     double d2 = this.myt;
/* 154 */     if (paramArrayOfDouble2 == paramArrayOfDouble1) {
/* 155 */       if ((paramInt2 > paramInt1) && (paramInt2 < paramInt1 + paramInt3 * 2))
/*     */       {
/* 164 */         System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3 * 2);
/*     */ 
/* 166 */         paramInt1 = paramInt2;
/*     */       }
/* 168 */       if ((paramInt2 == paramInt1) && (d1 == 0.0D) && (d2 == 0.0D)) {
/* 169 */         return;
/*     */       }
/*     */     }
/* 172 */     for (int i = 0; i < paramInt3; i++) {
/* 173 */       paramArrayOfDouble1[(paramInt1++)] += d1;
/* 174 */       paramArrayOfDouble1[(paramInt1++)] += d2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void transform(float[] paramArrayOfFloat, int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3)
/*     */   {
/* 183 */     double d1 = this.mxt;
/* 184 */     double d2 = this.myt;
/* 185 */     for (int i = 0; i < paramInt3; i++) {
/* 186 */       paramArrayOfDouble[(paramInt2++)] = (paramArrayOfFloat[(paramInt1++)] + d1);
/* 187 */       paramArrayOfDouble[(paramInt2++)] = (paramArrayOfFloat[(paramInt1++)] + d2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void transform(double[] paramArrayOfDouble, int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3)
/*     */   {
/* 196 */     double d1 = this.mxt;
/* 197 */     double d2 = this.myt;
/* 198 */     for (int i = 0; i < paramInt3; i++) {
/* 199 */       paramArrayOfFloat[(paramInt2++)] = ((float)(paramArrayOfDouble[(paramInt1++)] + d1));
/* 200 */       paramArrayOfFloat[(paramInt2++)] = ((float)(paramArrayOfDouble[(paramInt1++)] + d2));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deltaTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*     */   {
/* 209 */     if ((paramArrayOfFloat1 != paramArrayOfFloat2) || (paramInt1 != paramInt2))
/* 210 */       System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*     */   }
/*     */ 
/*     */   public void deltaTransform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3)
/*     */   {
/* 219 */     if ((paramArrayOfDouble1 != paramArrayOfDouble2) || (paramInt1 != paramInt2))
/* 220 */       System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3 * 2);
/*     */   }
/*     */ 
/*     */   public void inverseTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*     */   {
/* 229 */     float f1 = (float)this.mxt;
/* 230 */     float f2 = (float)this.myt;
/* 231 */     if (paramArrayOfFloat2 == paramArrayOfFloat1) {
/* 232 */       if ((paramInt2 > paramInt1) && (paramInt2 < paramInt1 + paramInt3 * 2))
/*     */       {
/* 241 */         System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*     */ 
/* 243 */         paramInt1 = paramInt2;
/*     */       }
/* 245 */       if ((paramInt2 == paramInt1) && (f1 == 0.0F) && (f2 == 0.0F)) {
/* 246 */         return;
/*     */       }
/*     */     }
/* 249 */     for (int i = 0; i < paramInt3; i++) {
/* 250 */       paramArrayOfFloat1[(paramInt1++)] -= f1;
/* 251 */       paramArrayOfFloat1[(paramInt1++)] -= f2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void inverseDeltaTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*     */   {
/* 260 */     if ((paramArrayOfFloat1 != paramArrayOfFloat2) || (paramInt1 != paramInt2))
/* 261 */       System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*     */   }
/*     */ 
/*     */   public void inverseTransform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3)
/*     */   {
/* 270 */     double d1 = this.mxt;
/* 271 */     double d2 = this.myt;
/* 272 */     if (paramArrayOfDouble2 == paramArrayOfDouble1) {
/* 273 */       if ((paramInt2 > paramInt1) && (paramInt2 < paramInt1 + paramInt3 * 2))
/*     */       {
/* 282 */         System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3 * 2);
/*     */ 
/* 284 */         paramInt1 = paramInt2;
/*     */       }
/* 286 */       if ((paramInt2 == paramInt1) && (d1 == 0.0D) && (d2 == 0.0D)) {
/* 287 */         return;
/*     */       }
/*     */     }
/* 290 */     for (int i = 0; i < paramInt3; i++) {
/* 291 */       paramArrayOfDouble1[(paramInt1++)] -= d1;
/* 292 */       paramArrayOfDouble1[(paramInt1++)] -= d2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public BaseBounds transform(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*     */   {
/* 298 */     float f1 = (float)(paramBaseBounds1.getMinX() + this.mxt);
/* 299 */     float f2 = (float)(paramBaseBounds1.getMinY() + this.myt);
/* 300 */     float f3 = paramBaseBounds1.getMinZ();
/* 301 */     float f4 = (float)(paramBaseBounds1.getMaxX() + this.mxt);
/* 302 */     float f5 = (float)(paramBaseBounds1.getMaxY() + this.myt);
/* 303 */     float f6 = paramBaseBounds1.getMaxZ();
/* 304 */     return paramBaseBounds2.deriveWithNewBounds(f1, f2, f3, f4, f5, f6);
/*     */   }
/*     */ 
/*     */   public void transform(Rectangle paramRectangle1, Rectangle paramRectangle2)
/*     */   {
/* 309 */     transform(paramRectangle1, paramRectangle2, this.mxt, this.myt);
/*     */   }
/*     */ 
/*     */   public BaseBounds inverseTransform(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*     */   {
/* 314 */     float f1 = (float)(paramBaseBounds1.getMinX() - this.mxt);
/* 315 */     float f2 = (float)(paramBaseBounds1.getMinY() - this.myt);
/* 316 */     float f3 = paramBaseBounds1.getMinZ();
/* 317 */     float f4 = (float)(paramBaseBounds1.getMaxX() - this.mxt);
/* 318 */     float f5 = (float)(paramBaseBounds1.getMaxY() - this.myt);
/* 319 */     float f6 = paramBaseBounds1.getMaxZ();
/* 320 */     return paramBaseBounds2.deriveWithNewBounds(f1, f2, f3, f4, f5, f6);
/*     */   }
/*     */ 
/*     */   public void inverseTransform(Rectangle paramRectangle1, Rectangle paramRectangle2)
/*     */   {
/* 325 */     transform(paramRectangle1, paramRectangle2, -this.mxt, -this.myt);
/*     */   }
/*     */ 
/*     */   static void transform(Rectangle paramRectangle1, Rectangle paramRectangle2, double paramDouble1, double paramDouble2)
/*     */   {
/* 331 */     int i = (int)paramDouble1;
/* 332 */     int j = (int)paramDouble2;
/* 333 */     if ((i == paramDouble1) && (j == paramDouble2)) {
/* 334 */       paramRectangle2.setBounds(paramRectangle1);
/* 335 */       paramRectangle2.translate(i, j);
/*     */     } else {
/* 337 */       double d1 = paramRectangle1.x + paramDouble1;
/* 338 */       double d2 = paramRectangle1.y + paramDouble2;
/* 339 */       double d3 = Math.ceil(d1 + paramRectangle1.width);
/* 340 */       double d4 = Math.ceil(d2 + paramRectangle1.height);
/* 341 */       d1 = Math.floor(d1);
/* 342 */       d2 = Math.floor(d2);
/* 343 */       paramRectangle2.setBounds((int)d1, (int)d2, (int)(d3 - d1), (int)(d4 - d2));
/*     */     }
/*     */   }
/*     */ 
/*     */   public Shape createTransformedShape(Shape paramShape)
/*     */   {
/* 349 */     return new Path2D(paramShape, this);
/*     */   }
/*     */ 
/*     */   public void setToIdentity()
/*     */   {
/* 354 */     this.mxt = (this.myt = 0.0D);
/*     */   }
/*     */ 
/*     */   public void setTransform(BaseTransform paramBaseTransform)
/*     */   {
/* 359 */     if (!paramBaseTransform.isTranslateOrIdentity()) {
/* 360 */       degreeError(BaseTransform.Degree.TRANSLATE_2D);
/*     */     }
/* 362 */     this.mxt = paramBaseTransform.getMxt();
/* 363 */     this.myt = paramBaseTransform.getMyt();
/*     */   }
/*     */ 
/*     */   public void invert()
/*     */   {
/* 368 */     this.mxt = (-this.mxt);
/* 369 */     this.myt = (-this.myt);
/*     */   }
/*     */ 
/*     */   public void restoreTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 377 */     if ((paramDouble1 != 1.0D) || (paramDouble2 != 0.0D) || (paramDouble3 != 0.0D) || (paramDouble4 != 1.0D))
/*     */     {
/* 380 */       degreeError(BaseTransform.Degree.TRANSLATE_2D);
/*     */     }
/* 382 */     this.mxt = paramDouble5;
/* 383 */     this.myt = paramDouble6;
/*     */   }
/*     */ 
/*     */   public void restoreTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*     */   {
/* 391 */     if ((paramDouble1 != 1.0D) || (paramDouble2 != 0.0D) || (paramDouble3 != 0.0D) || (paramDouble5 != 0.0D) || (paramDouble6 != 1.0D) || (paramDouble7 != 0.0D) || (paramDouble9 != 0.0D) || (paramDouble10 != 0.0D) || (paramDouble11 != 1.0D) || (paramDouble12 != 0.0D))
/*     */     {
/* 395 */       degreeError(BaseTransform.Degree.TRANSLATE_2D);
/*     */     }
/* 397 */     this.mxt = paramDouble4;
/* 398 */     this.myt = paramDouble8;
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithTranslation(double paramDouble1, double paramDouble2)
/*     */   {
/* 403 */     this.mxt += paramDouble1;
/* 404 */     this.myt += paramDouble2;
/* 405 */     return this;
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithPreTranslation(double paramDouble1, double paramDouble2)
/*     */   {
/* 410 */     this.mxt += paramDouble1;
/* 411 */     this.myt += paramDouble2;
/* 412 */     return this;
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithConcatenation(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 420 */     if ((paramDouble1 == 1.0D) && (paramDouble2 == 0.0D) && (paramDouble3 == 0.0D) && (paramDouble4 == 1.0D)) {
/* 421 */       this.mxt += paramDouble5;
/* 422 */       this.myt += paramDouble6;
/* 423 */       return this;
/*     */     }
/* 425 */     return new Affine2D(paramDouble1, paramDouble2, paramDouble3, paramDouble4, this.mxt + paramDouble5, this.myt + paramDouble6);
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithConcatenation(BaseTransform paramBaseTransform)
/*     */   {
/* 433 */     if (paramBaseTransform.isTranslateOrIdentity()) {
/* 434 */       this.mxt += paramBaseTransform.getMxt();
/* 435 */       this.myt += paramBaseTransform.getMyt();
/* 436 */       return this;
/* 437 */     }if (paramBaseTransform.is2D()) {
/* 438 */       return getInstance(paramBaseTransform.getMxx(), paramBaseTransform.getMyx(), paramBaseTransform.getMxy(), paramBaseTransform.getMyy(), this.mxt + paramBaseTransform.getMxt(), this.myt + paramBaseTransform.getMyt());
/*     */     }
/*     */ 
/* 442 */     Affine3D localAffine3D = new Affine3D(paramBaseTransform);
/* 443 */     localAffine3D.preTranslate(this.mxt, this.myt, 0.0D);
/* 444 */     return localAffine3D;
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithPreConcatenation(BaseTransform paramBaseTransform)
/*     */   {
/* 450 */     if (paramBaseTransform.isTranslateOrIdentity()) {
/* 451 */       this.mxt += paramBaseTransform.getMxt();
/* 452 */       this.myt += paramBaseTransform.getMyt();
/* 453 */       return this;
/* 454 */     }if (paramBaseTransform.is2D()) {
/* 455 */       localObject = new Affine2D(paramBaseTransform);
/* 456 */       ((Affine2D)localObject).translate(this.mxt, this.myt);
/* 457 */       return localObject;
/*     */     }
/* 459 */     Object localObject = new Affine3D(paramBaseTransform);
/* 460 */     ((Affine3D)localObject).translate(this.mxt, this.myt, 0.0D);
/* 461 */     return localObject;
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithNewTransform(BaseTransform paramBaseTransform)
/*     */   {
/* 467 */     if (paramBaseTransform.isTranslateOrIdentity()) {
/* 468 */       this.mxt = paramBaseTransform.getMxt();
/* 469 */       this.myt = paramBaseTransform.getMyt();
/* 470 */       return this;
/*     */     }
/* 472 */     return getInstance(paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public BaseTransform createInverse()
/*     */   {
/* 478 */     if (isIdentity()) {
/* 479 */       return IDENTITY_TRANSFORM;
/*     */     }
/* 481 */     return new Translate2D(-this.mxt, -this.myt);
/*     */   }
/*     */ 
/*     */   private static double _matround(double paramDouble)
/*     */   {
/* 488 */     return Math.rint(paramDouble * 1000000000000000.0D) / 1000000000000000.0D;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 493 */     return "Translate2D[" + _matround(this.mxt) + ", " + _matround(this.myt) + "]";
/*     */   }
/*     */ 
/*     */   public BaseTransform copy()
/*     */   {
/* 500 */     return new Translate2D(this.mxt, this.myt);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 505 */     if ((paramObject instanceof BaseTransform)) {
/* 506 */       BaseTransform localBaseTransform = (BaseTransform)paramObject;
/* 507 */       return (localBaseTransform.isTranslateOrIdentity()) && (localBaseTransform.getMxt() == this.mxt) && (localBaseTransform.getMyt() == this.myt);
/*     */     }
/*     */ 
/* 511 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 532 */     if (isIdentity()) return 0;
/* 533 */     long l = BASE_HASH;
/* 534 */     l = l * 31L + Double.doubleToLongBits(getMyt());
/* 535 */     l = l * 31L + Double.doubleToLongBits(getMxt());
/* 536 */     return (int)l ^ (int)(l >> 32);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 516 */     long l = 0L;
/* 517 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMzz());
/* 518 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMzy());
/* 519 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMzx());
/* 520 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMyz());
/* 521 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMxz());
/* 522 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMyy());
/* 523 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMyx());
/* 524 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMxy());
/* 525 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMxx());
/* 526 */     l = l * 31L + Double.doubleToLongBits(IDENTITY_TRANSFORM.getMzt());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.transform.Translate2D
 * JD-Core Version:    0.6.2
 */