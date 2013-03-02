/*     */ package com.sun.javafx.geom.transform;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.Vec3d;
/*     */ 
/*     */ public final class Identity extends BaseTransform
/*     */ {
/*     */   public BaseTransform.Degree getDegree()
/*     */   {
/*  42 */     return BaseTransform.Degree.IDENTITY;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/*  47 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean isIdentity()
/*     */   {
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isTranslateOrIdentity()
/*     */   {
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean is2D()
/*     */   {
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */   public double getDeterminant()
/*     */   {
/*  67 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/*  72 */     if (paramPoint2D2 == null) paramPoint2D2 = makePoint(paramPoint2D1, paramPoint2D2);
/*  73 */     paramPoint2D2.setLocation(paramPoint2D1);
/*  74 */     return paramPoint2D2;
/*     */   }
/*     */ 
/*     */   public Point2D inverseTransform(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/*  79 */     if (paramPoint2D2 == null) paramPoint2D2 = makePoint(paramPoint2D1, paramPoint2D2);
/*  80 */     paramPoint2D2.setLocation(paramPoint2D1);
/*  81 */     return paramPoint2D2;
/*     */   }
/*     */ 
/*     */   public Vec3d transform(Vec3d paramVec3d1, Vec3d paramVec3d2)
/*     */   {
/*  86 */     if (paramVec3d2 == null) return new Vec3d(paramVec3d1);
/*  87 */     paramVec3d2.set(paramVec3d1);
/*  88 */     return paramVec3d2;
/*     */   }
/*     */ 
/*     */   public void transform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*     */   {
/*  95 */     if ((paramArrayOfFloat1 != paramArrayOfFloat2) || (paramInt1 != paramInt2))
/*  96 */       System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*     */   }
/*     */ 
/*     */   public void transform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3)
/*     */   {
/* 104 */     if ((paramArrayOfDouble1 != paramArrayOfDouble2) || (paramInt1 != paramInt2))
/* 105 */       System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3 * 2);
/*     */   }
/*     */ 
/*     */   public void transform(float[] paramArrayOfFloat, int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3)
/*     */   {
/* 113 */     for (int i = 0; i < paramInt3; i++) {
/* 114 */       paramArrayOfDouble[(paramInt2++)] = paramArrayOfFloat[(paramInt1++)];
/* 115 */       paramArrayOfDouble[(paramInt2++)] = paramArrayOfFloat[(paramInt1++)];
/*     */     }
/*     */   }
/*     */ 
/*     */   public void transform(double[] paramArrayOfDouble, int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3)
/*     */   {
/* 123 */     for (int i = 0; i < paramInt3; i++) {
/* 124 */       paramArrayOfFloat[(paramInt2++)] = ((float)paramArrayOfDouble[(paramInt1++)]);
/* 125 */       paramArrayOfFloat[(paramInt2++)] = ((float)paramArrayOfDouble[(paramInt1++)]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deltaTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*     */   {
/* 134 */     if ((paramArrayOfFloat1 != paramArrayOfFloat2) || (paramInt1 != paramInt2))
/* 135 */       System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*     */   }
/*     */ 
/*     */   public void deltaTransform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3)
/*     */   {
/* 144 */     if ((paramArrayOfDouble1 != paramArrayOfDouble2) || (paramInt1 != paramInt2))
/* 145 */       System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3 * 2);
/*     */   }
/*     */ 
/*     */   public void inverseTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*     */   {
/* 153 */     if ((paramArrayOfFloat1 != paramArrayOfFloat2) || (paramInt1 != paramInt2))
/* 154 */       System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*     */   }
/*     */ 
/*     */   public void inverseDeltaTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*     */   {
/* 162 */     if ((paramArrayOfFloat1 != paramArrayOfFloat2) || (paramInt1 != paramInt2))
/* 163 */       System.arraycopy(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3 * 2);
/*     */   }
/*     */ 
/*     */   public void inverseTransform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3)
/*     */   {
/* 171 */     if ((paramArrayOfDouble1 != paramArrayOfDouble2) || (paramInt1 != paramInt2))
/* 172 */       System.arraycopy(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3 * 2);
/*     */   }
/*     */ 
/*     */   public BaseBounds transform(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*     */   {
/* 178 */     if (paramBaseBounds2 != paramBaseBounds1) {
/* 179 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBounds(paramBaseBounds1);
/*     */     }
/* 181 */     return paramBaseBounds2;
/*     */   }
/*     */ 
/*     */   public void transform(Rectangle paramRectangle1, Rectangle paramRectangle2)
/*     */   {
/* 186 */     if (paramRectangle2 != paramRectangle1)
/* 187 */       paramRectangle2.setBounds(paramRectangle1);
/*     */   }
/*     */ 
/*     */   public BaseBounds inverseTransform(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*     */   {
/* 193 */     if (paramBaseBounds2 != paramBaseBounds1) {
/* 194 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBounds(paramBaseBounds1);
/*     */     }
/* 196 */     return paramBaseBounds2;
/*     */   }
/*     */ 
/*     */   public void inverseTransform(Rectangle paramRectangle1, Rectangle paramRectangle2)
/*     */   {
/* 201 */     if (paramRectangle2 != paramRectangle1)
/* 202 */       paramRectangle2.setBounds(paramRectangle1);
/*     */   }
/*     */ 
/*     */   public Shape createTransformedShape(Shape paramShape)
/*     */   {
/* 209 */     return new Path2D(paramShape);
/*     */   }
/*     */ 
/*     */   public void setToIdentity()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setTransform(BaseTransform paramBaseTransform)
/*     */   {
/* 218 */     if (!paramBaseTransform.isIdentity())
/* 219 */       degreeError(BaseTransform.Degree.IDENTITY);
/*     */   }
/*     */ 
/*     */   public void invert()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void restoreTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 232 */     if ((paramDouble1 != 1.0D) || (paramDouble2 != 0.0D) || (paramDouble3 != 0.0D) || (paramDouble4 != 1.0D) || (paramDouble5 != 0.0D) || (paramDouble6 != 0.0D))
/*     */     {
/* 236 */       degreeError(BaseTransform.Degree.IDENTITY);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void restoreTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*     */   {
/* 245 */     if ((paramDouble1 != 1.0D) || (paramDouble2 != 0.0D) || (paramDouble3 != 0.0D) || (paramDouble4 != 0.0D) || (paramDouble5 != 0.0D) || (paramDouble6 != 1.0D) || (paramDouble7 != 0.0D) || (paramDouble8 != 0.0D) || (paramDouble9 != 0.0D) || (paramDouble10 != 0.0D) || (paramDouble11 != 1.0D) || (paramDouble12 != 0.0D))
/*     */     {
/* 249 */       degreeError(BaseTransform.Degree.IDENTITY);
/*     */     }
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithTranslation(double paramDouble1, double paramDouble2)
/*     */   {
/* 255 */     return Translate2D.getInstance(paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithPreTranslation(double paramDouble1, double paramDouble2)
/*     */   {
/* 260 */     return Translate2D.getInstance(paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithConcatenation(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 268 */     return getInstance(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithConcatenation(BaseTransform paramBaseTransform)
/*     */   {
/* 275 */     return getInstance(paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithPreConcatenation(BaseTransform paramBaseTransform)
/*     */   {
/* 280 */     return getInstance(paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public BaseTransform deriveWithNewTransform(BaseTransform paramBaseTransform)
/*     */   {
/* 285 */     return getInstance(paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public BaseTransform createInverse()
/*     */   {
/* 290 */     return this;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 295 */     return "Identity[]";
/*     */   }
/*     */ 
/*     */   public BaseTransform copy()
/*     */   {
/* 300 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 305 */     return ((paramObject instanceof BaseTransform)) && (((BaseTransform)paramObject).isIdentity());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 311 */     return 0;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.transform.Identity
 * JD-Core Version:    0.6.2
 */