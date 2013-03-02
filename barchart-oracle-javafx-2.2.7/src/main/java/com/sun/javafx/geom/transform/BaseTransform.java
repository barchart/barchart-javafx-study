/*     */ package com.sun.javafx.geom.transform;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.Vec3d;
/*     */ 
/*     */ public abstract class BaseTransform
/*     */   implements CanTransformVec3d
/*     */ {
/*  39 */   public static final BaseTransform IDENTITY_TRANSFORM = new Identity();
/*     */   protected static final int TYPE_UNKNOWN = -1;
/*     */   public static final int TYPE_IDENTITY = 0;
/*     */   public static final int TYPE_TRANSLATION = 1;
/*     */   public static final int TYPE_UNIFORM_SCALE = 2;
/*     */   public static final int TYPE_GENERAL_SCALE = 4;
/*     */   public static final int TYPE_MASK_SCALE = 6;
/*     */   public static final int TYPE_FLIP = 64;
/*     */   public static final int TYPE_QUADRANT_ROTATION = 8;
/*     */   public static final int TYPE_GENERAL_ROTATION = 16;
/*     */   public static final int TYPE_MASK_ROTATION = 24;
/*     */   public static final int TYPE_GENERAL_TRANSFORM = 32;
/*     */   public static final int TYPE_AFFINE2D_MASK = 127;
/*     */   public static final int TYPE_AFFINE_3D = 128;
/*     */ 
/*     */   static void degreeError(Degree paramDegree)
/*     */   {
/* 260 */     throw new InternalError("does not support higher than " + paramDegree + " operations");
/*     */   }
/*     */ 
/*     */   public static BaseTransform getInstance(BaseTransform paramBaseTransform)
/*     */   {
/* 265 */     if (paramBaseTransform.isIdentity())
/* 266 */       return IDENTITY_TRANSFORM;
/* 267 */     if (paramBaseTransform.isTranslateOrIdentity())
/* 268 */       return new Translate2D(paramBaseTransform);
/* 269 */     if (paramBaseTransform.is2D()) {
/* 270 */       return new Affine2D(paramBaseTransform);
/*     */     }
/* 272 */     return new Affine3D(paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public static BaseTransform getInstance(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*     */   {
/* 279 */     if ((paramDouble3 == 0.0D) && (paramDouble7 == 0.0D) && (paramDouble9 == 0.0D) && (paramDouble10 == 0.0D) && (paramDouble11 == 1.0D) && (paramDouble12 == 0.0D))
/*     */     {
/* 282 */       return getInstance(paramDouble1, paramDouble5, paramDouble2, paramDouble6, paramDouble4, paramDouble8);
/*     */     }
/* 284 */     return new Affine3D(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramDouble11, paramDouble12);
/*     */   }
/*     */ 
/*     */   public static BaseTransform getInstance(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 294 */     if ((paramDouble1 == 1.0D) && (paramDouble2 == 0.0D) && (paramDouble3 == 0.0D) && (paramDouble4 == 1.0D)) {
/* 295 */       return getTranslateInstance(paramDouble5, paramDouble6);
/*     */     }
/* 297 */     return new Affine2D(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*     */   }
/*     */ 
/*     */   public static BaseTransform getTranslateInstance(double paramDouble1, double paramDouble2)
/*     */   {
/* 302 */     if ((paramDouble1 == 0.0D) && (paramDouble2 == 0.0D)) {
/* 303 */       return IDENTITY_TRANSFORM;
/*     */     }
/* 305 */     return new Translate2D(paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */   public static BaseTransform getScaleInstance(double paramDouble1, double paramDouble2)
/*     */   {
/* 310 */     return getInstance(paramDouble1, 0.0D, 0.0D, paramDouble2, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   public static BaseTransform getRotateInstance(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 314 */     Affine2D localAffine2D = new Affine2D();
/* 315 */     localAffine2D.setToRotation(paramDouble1, paramDouble2, paramDouble3);
/* 316 */     return localAffine2D;
/*     */   }
/*     */ 
/*     */   public abstract Degree getDegree();
/*     */ 
/*     */   public abstract int getType();
/*     */ 
/*     */   public abstract boolean isIdentity();
/*     */ 
/*     */   public abstract boolean isTranslateOrIdentity();
/*     */ 
/*     */   public abstract boolean is2D();
/*     */ 
/*     */   public abstract double getDeterminant();
/*     */ 
/*     */   public double getMxx()
/*     */   {
/* 353 */     return 1.0D; } 
/* 354 */   public double getMxy() { return 0.0D; } 
/* 355 */   public double getMxz() { return 0.0D; } 
/* 356 */   public double getMxt() { return 0.0D; } 
/* 357 */   public double getMyx() { return 0.0D; } 
/* 358 */   public double getMyy() { return 1.0D; } 
/* 359 */   public double getMyz() { return 0.0D; } 
/* 360 */   public double getMyt() { return 0.0D; } 
/* 361 */   public double getMzx() { return 0.0D; } 
/* 362 */   public double getMzy() { return 0.0D; } 
/* 363 */   public double getMzz() { return 1.0D; } 
/* 364 */   public double getMzt() { return 0.0D; }
/*     */ 
/*     */ 
/*     */   public abstract Point2D transform(Point2D paramPoint2D1, Point2D paramPoint2D2);
/*     */ 
/*     */   public abstract Point2D inverseTransform(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */     throws NoninvertibleTransformException;
/*     */ 
/*     */   public abstract Vec3d transform(Vec3d paramVec3d1, Vec3d paramVec3d2);
/*     */ 
/*     */   public abstract void transform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3);
/*     */ 
/*     */   public abstract void transform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3);
/*     */ 
/*     */   public abstract void transform(float[] paramArrayOfFloat, int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3);
/*     */ 
/*     */   public abstract void transform(double[] paramArrayOfDouble, int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3);
/*     */ 
/*     */   public abstract void deltaTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3);
/*     */ 
/*     */   public abstract void deltaTransform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3);
/*     */ 
/*     */   public abstract void inverseTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*     */     throws NoninvertibleTransformException;
/*     */ 
/*     */   public abstract void inverseDeltaTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*     */     throws NoninvertibleTransformException;
/*     */ 
/*     */   public abstract void inverseTransform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3)
/*     */     throws NoninvertibleTransformException;
/*     */ 
/*     */   public abstract BaseBounds transform(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2);
/*     */ 
/*     */   public abstract void transform(Rectangle paramRectangle1, Rectangle paramRectangle2);
/*     */ 
/*     */   public abstract BaseBounds inverseTransform(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*     */     throws NoninvertibleTransformException;
/*     */ 
/*     */   public abstract void inverseTransform(Rectangle paramRectangle1, Rectangle paramRectangle2)
/*     */     throws NoninvertibleTransformException;
/*     */ 
/*     */   public abstract Shape createTransformedShape(Shape paramShape);
/*     */ 
/*     */   public abstract void setToIdentity();
/*     */ 
/*     */   public abstract void setTransform(BaseTransform paramBaseTransform);
/*     */ 
/*     */   public abstract void invert()
/*     */     throws NoninvertibleTransformException;
/*     */ 
/*     */   public abstract void restoreTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */ 
/*     */   public abstract void restoreTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12);
/*     */ 
/*     */   public abstract BaseTransform deriveWithTranslation(double paramDouble1, double paramDouble2);
/*     */ 
/*     */   public abstract BaseTransform deriveWithPreTranslation(double paramDouble1, double paramDouble2);
/*     */ 
/*     */   public abstract BaseTransform deriveWithConcatenation(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */ 
/*     */   public abstract BaseTransform deriveWithPreConcatenation(BaseTransform paramBaseTransform);
/*     */ 
/*     */   public abstract BaseTransform deriveWithConcatenation(BaseTransform paramBaseTransform);
/*     */ 
/*     */   public abstract BaseTransform deriveWithNewTransform(BaseTransform paramBaseTransform);
/*     */ 
/*     */   public abstract BaseTransform createInverse()
/*     */     throws NoninvertibleTransformException;
/*     */ 
/*     */   public abstract BaseTransform copy();
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 473 */     if (isIdentity()) return 0;
/* 474 */     long l = 0L;
/* 475 */     l = l * 31L + Double.doubleToLongBits(getMzz());
/* 476 */     l = l * 31L + Double.doubleToLongBits(getMzy());
/* 477 */     l = l * 31L + Double.doubleToLongBits(getMzx());
/* 478 */     l = l * 31L + Double.doubleToLongBits(getMyz());
/* 479 */     l = l * 31L + Double.doubleToLongBits(getMxz());
/* 480 */     l = l * 31L + Double.doubleToLongBits(getMyy());
/* 481 */     l = l * 31L + Double.doubleToLongBits(getMyx());
/* 482 */     l = l * 31L + Double.doubleToLongBits(getMxy());
/* 483 */     l = l * 31L + Double.doubleToLongBits(getMxx());
/* 484 */     l = l * 31L + Double.doubleToLongBits(getMzt());
/* 485 */     l = l * 31L + Double.doubleToLongBits(getMyt());
/* 486 */     l = l * 31L + Double.doubleToLongBits(getMxt());
/* 487 */     return (int)l ^ (int)(l >> 32);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 502 */     if (!(paramObject instanceof BaseTransform)) {
/* 503 */       return false;
/*     */     }
/*     */ 
/* 506 */     BaseTransform localBaseTransform = (BaseTransform)paramObject;
/*     */ 
/* 508 */     return (getMxx() == localBaseTransform.getMxx()) && (getMxy() == localBaseTransform.getMxy()) && (getMxz() == localBaseTransform.getMxz()) && (getMxt() == localBaseTransform.getMxt()) && (getMyx() == localBaseTransform.getMyx()) && (getMyy() == localBaseTransform.getMyy()) && (getMyz() == localBaseTransform.getMyz()) && (getMyt() == localBaseTransform.getMyt()) && (getMzx() == localBaseTransform.getMzx()) && (getMzy() == localBaseTransform.getMzy()) && (getMzz() == localBaseTransform.getMzz()) && (getMzt() == localBaseTransform.getMzt());
/*     */   }
/*     */ 
/*     */   static Point2D makePoint(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/* 523 */     if (paramPoint2D2 == null) {
/* 524 */       paramPoint2D2 = new Point2D();
/*     */     }
/* 526 */     return paramPoint2D2;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 535 */     return "Matrix: degree " + getDegree() + "\n" + getMxx() + ", " + getMxy() + ", " + getMxz() + ", " + getMxt() + "\n" + getMyx() + ", " + getMyy() + ", " + getMyz() + ", " + getMyt() + "\n" + getMzx() + ", " + getMzy() + ", " + getMzz() + ", " + getMzt() + "\n";
/*     */   }
/*     */ 
/*     */   public static enum Degree
/*     */   {
/*  42 */     IDENTITY, 
/*  43 */     TRANSLATE_2D, 
/*  44 */     AFFINE_2D, 
/*  45 */     TRANSLATE_3D, 
/*  46 */     AFFINE_3D;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.transform.BaseTransform
 * JD-Core Version:    0.6.2
 */