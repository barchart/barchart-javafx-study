/*      */ package com.sun.javafx.geom.transform;
/*      */ 
/*      */ import com.sun.javafx.geom.BaseBounds;
/*      */ import com.sun.javafx.geom.Point2D;
/*      */ import com.sun.javafx.geom.Rectangle;
/*      */ import com.sun.javafx.geom.Vec3d;
/*      */ 
/*      */ public class Affine3D extends AffineBase
/*      */ {
/*      */   private double mxz;
/*      */   private double myz;
/*      */   private double mzx;
/*      */   private double mzy;
/*      */   private double mzz;
/*      */   private double mzt;
/*   28 */   private Vec3d tempV3d = new Vec3d();
/*      */   private static final double EPSILON_ABSOLUTE = 1.E-05D;
/*      */ 
/*      */   public Affine3D()
/*      */   {
/*   31 */     this.mxx = (this.myy = this.mzz = 1.0D);
/*      */   }
/*      */ 
/*      */   public Affine3D(BaseTransform paramBaseTransform)
/*      */   {
/*   39 */     setTransform(paramBaseTransform);
/*      */   }
/*      */ 
/*      */   public Affine3D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*      */   {
/*   46 */     this.mxx = paramDouble1;
/*   47 */     this.mxy = paramDouble2;
/*   48 */     this.mxz = paramDouble3;
/*   49 */     this.mxt = paramDouble4;
/*      */ 
/*   51 */     this.myx = paramDouble5;
/*   52 */     this.myy = paramDouble6;
/*   53 */     this.myz = paramDouble7;
/*   54 */     this.myt = paramDouble8;
/*      */ 
/*   56 */     this.mzx = paramDouble9;
/*   57 */     this.mzy = paramDouble10;
/*   58 */     this.mzz = paramDouble11;
/*   59 */     this.mzt = paramDouble12;
/*      */ 
/*   61 */     updateState();
/*      */   }
/*      */ 
/*      */   public Affine3D(Affine3D paramAffine3D) {
/*   65 */     this.mxx = paramAffine3D.mxx;
/*   66 */     this.mxy = paramAffine3D.mxy;
/*   67 */     this.mxz = paramAffine3D.mxz;
/*   68 */     this.mxt = paramAffine3D.mxt;
/*      */ 
/*   70 */     this.myx = paramAffine3D.myx;
/*   71 */     this.myy = paramAffine3D.myy;
/*   72 */     this.myz = paramAffine3D.myz;
/*   73 */     this.myt = paramAffine3D.myt;
/*      */ 
/*   75 */     this.mzx = paramAffine3D.mzx;
/*   76 */     this.mzy = paramAffine3D.mzy;
/*   77 */     this.mzz = paramAffine3D.mzz;
/*   78 */     this.mzt = paramAffine3D.mzt;
/*      */ 
/*   80 */     this.state = paramAffine3D.state;
/*   81 */     this.type = paramAffine3D.type;
/*      */   }
/*      */ 
/*      */   public BaseTransform copy()
/*      */   {
/*   86 */     return new Affine3D(this);
/*      */   }
/*      */ 
/*      */   public BaseTransform.Degree getDegree()
/*      */   {
/*   91 */     return BaseTransform.Degree.AFFINE_3D;
/*      */   }
/*      */ 
/*      */   protected void reset3Delements()
/*      */   {
/*   96 */     this.mxz = 0.0D;
/*   97 */     this.myz = 0.0D;
/*   98 */     this.mzx = 0.0D;
/*   99 */     this.mzy = 0.0D;
/*  100 */     this.mzz = 1.0D;
/*  101 */     this.mzt = 0.0D;
/*      */   }
/*      */ 
/*      */   protected void updateState()
/*      */   {
/*  106 */     super.updateState();
/*  107 */     if ((!almostZero(this.mxz)) || (!almostZero(this.myz)) || (!almostZero(this.mzx)) || (!almostZero(this.mzy)) || (!almostOne(this.mzz)) || (!almostZero(this.mzt)))
/*      */     {
/*  114 */       this.state |= 8;
/*  115 */       if (this.type != -1)
/*  116 */         this.type |= 128;
/*      */     }
/*      */   }
/*      */ 
/*      */   public double getMxz() {
/*  121 */     return this.mxz; } 
/*  122 */   public double getMyz() { return this.myz; } 
/*  123 */   public double getMzx() { return this.mzx; } 
/*  124 */   public double getMzy() { return this.mzy; } 
/*  125 */   public double getMzz() { return this.mzz; } 
/*  126 */   public double getMzt() { return this.mzt; }
/*      */ 
/*      */   public double getDeterminant()
/*      */   {
/*  130 */     if ((this.state & 0x8) == 0) {
/*  131 */       return super.getDeterminant();
/*      */     }
/*      */ 
/*  136 */     return this.mxx * (this.myy * this.mzz - this.mzy * this.myz) + this.mxy * (this.myz * this.mzx - this.mzz * this.myx) + this.mxz * (this.myx * this.mzy - this.mzx * this.myy);
/*      */   }
/*      */ 
/*      */   public void setTransform(BaseTransform paramBaseTransform)
/*      */   {
/*  142 */     this.mxx = paramBaseTransform.getMxx();
/*  143 */     this.mxy = paramBaseTransform.getMxy();
/*  144 */     this.mxz = paramBaseTransform.getMxz();
/*  145 */     this.mxt = paramBaseTransform.getMxt();
/*  146 */     this.myx = paramBaseTransform.getMyx();
/*  147 */     this.myy = paramBaseTransform.getMyy();
/*  148 */     this.myz = paramBaseTransform.getMyz();
/*  149 */     this.myt = paramBaseTransform.getMyt();
/*  150 */     this.mzx = paramBaseTransform.getMzx();
/*  151 */     this.mzy = paramBaseTransform.getMzy();
/*  152 */     this.mzz = paramBaseTransform.getMzz();
/*  153 */     this.mzt = paramBaseTransform.getMzt();
/*  154 */     updateState();
/*      */   }
/*      */ 
/*      */   public void setTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*      */   {
/*  161 */     this.mxx = paramDouble1;
/*  162 */     this.mxy = paramDouble2;
/*  163 */     this.mxz = paramDouble3;
/*  164 */     this.mxt = paramDouble4;
/*      */ 
/*  166 */     this.myx = paramDouble5;
/*  167 */     this.myy = paramDouble6;
/*  168 */     this.myz = paramDouble7;
/*  169 */     this.myt = paramDouble8;
/*      */ 
/*  171 */     this.mzx = paramDouble9;
/*  172 */     this.mzy = paramDouble10;
/*  173 */     this.mzz = paramDouble11;
/*  174 */     this.mzt = paramDouble12;
/*      */ 
/*  176 */     updateState();
/*      */   }
/*      */ 
/*      */   public void setToTranslation(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  180 */     this.mxx = 1.0D;
/*  181 */     this.mxy = 0.0D;
/*  182 */     this.mxz = 0.0D;
/*  183 */     this.mxt = paramDouble1;
/*      */ 
/*  185 */     this.myx = 0.0D;
/*  186 */     this.myy = 1.0D;
/*  187 */     this.myz = 0.0D;
/*  188 */     this.myt = paramDouble2;
/*      */ 
/*  190 */     this.mzx = 0.0D;
/*  191 */     this.mzy = 0.0D;
/*  192 */     this.mzz = 1.0D;
/*  193 */     this.mzt = paramDouble3;
/*      */ 
/*  195 */     if (paramDouble3 == 0.0D) {
/*  196 */       if ((paramDouble1 == 0.0D) && (paramDouble2 == 0.0D)) {
/*  197 */         this.state = 0;
/*  198 */         this.type = 0;
/*      */       } else {
/*  200 */         this.state = 1;
/*  201 */         this.type = 1;
/*      */       }
/*      */     }
/*  204 */     else if ((paramDouble1 == 0.0D) && (paramDouble2 == 0.0D)) {
/*  205 */       this.state = 8;
/*  206 */       this.type = 128;
/*      */     } else {
/*  208 */       this.state = 9;
/*  209 */       this.type = 129;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setToScale(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  215 */     this.mxx = paramDouble1;
/*  216 */     this.mxy = 0.0D;
/*  217 */     this.mxz = 0.0D;
/*  218 */     this.mxt = 0.0D;
/*      */ 
/*  220 */     this.myx = 0.0D;
/*  221 */     this.myy = paramDouble2;
/*  222 */     this.myz = 0.0D;
/*  223 */     this.myt = 0.0D;
/*      */ 
/*  225 */     this.mzx = 0.0D;
/*  226 */     this.mzy = 0.0D;
/*  227 */     this.mzz = paramDouble3;
/*  228 */     this.mzt = 0.0D;
/*      */ 
/*  230 */     if (paramDouble3 == 1.0D) {
/*  231 */       if ((paramDouble1 == 1.0D) && (paramDouble2 == 1.0D)) {
/*  232 */         this.state = 0;
/*  233 */         this.type = 0;
/*      */       } else {
/*  235 */         this.state = 2;
/*  236 */         this.type = -1;
/*      */       }
/*      */     }
/*  239 */     else if ((paramDouble1 == 1.0D) && (paramDouble2 == 1.0D)) {
/*  240 */       this.state = 8;
/*  241 */       this.type = 128;
/*      */     } else {
/*  243 */       this.state = 10;
/*  244 */       this.type = -1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setToRotation(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7)
/*      */   {
/*  253 */     setToRotation(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*  254 */     if ((paramDouble5 != 0.0D) || (paramDouble6 != 0.0D) || (paramDouble7 != 0.0D)) {
/*  255 */       preTranslate(paramDouble5, paramDouble6, paramDouble7);
/*  256 */       translate(-paramDouble5, -paramDouble6, -paramDouble7);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setToRotation(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  261 */     double d1 = Math.sqrt(paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3 + paramDouble4 * paramDouble4);
/*      */ 
/*  263 */     if (almostZero(d1)) {
/*  264 */       setToIdentity();
/*  265 */       return;
/*      */     }
/*  267 */     d1 = 1.0D / d1;
/*  268 */     double d2 = paramDouble2 * d1;
/*  269 */     double d3 = paramDouble3 * d1;
/*  270 */     double d4 = paramDouble4 * d1;
/*      */ 
/*  272 */     double d5 = Math.sin(paramDouble1);
/*  273 */     double d6 = Math.cos(paramDouble1);
/*  274 */     double d7 = 1.0D - d6;
/*      */ 
/*  276 */     double d8 = d2 * d4;
/*  277 */     double d9 = d2 * d3;
/*  278 */     double d10 = d3 * d4;
/*      */ 
/*  280 */     this.mxx = (d7 * d2 * d2 + d6);
/*  281 */     this.mxy = (d7 * d9 - d5 * d4);
/*  282 */     this.mxz = (d7 * d8 + d5 * d3);
/*  283 */     this.mxt = 0.0D;
/*      */ 
/*  285 */     this.myx = (d7 * d9 + d5 * d4);
/*  286 */     this.myy = (d7 * d3 * d3 + d6);
/*  287 */     this.myz = (d7 * d10 - d5 * d2);
/*  288 */     this.myt = 0.0D;
/*      */ 
/*  290 */     this.mzx = (d7 * d8 - d5 * d3);
/*  291 */     this.mzy = (d7 * d10 + d5 * d2);
/*  292 */     this.mzz = (d7 * d4 * d4 + d6);
/*  293 */     this.mzt = 0.0D;
/*      */ 
/*  295 */     updateState();
/*      */   }
/*      */ 
/*      */   public BaseBounds transform(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*      */   {
/*  300 */     if ((this.state & 0x8) == 0) {
/*  301 */       return paramBaseBounds2 = super.transform(paramBaseBounds1, paramBaseBounds2);
/*      */     }
/*      */ 
/*  304 */     switch (this.state)
/*      */     {
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*      */     default:
/*  315 */       paramBaseBounds2 = TransformHelper.general3dBoundsTransform(this, paramBaseBounds1, paramBaseBounds2, this.tempV3d);
/*  316 */       break;
/*      */     case 3:
/*  318 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBoundsAndSort((float)(paramBaseBounds1.getMinX() * this.mxx + this.mxt), (float)(paramBaseBounds1.getMinY() * this.myy + this.myt), (float)(paramBaseBounds1.getMinZ() * this.mzz + this.mzt), (float)(paramBaseBounds1.getMaxX() * this.mxx + this.mxt), (float)(paramBaseBounds1.getMaxY() * this.myy + this.myt), (float)(paramBaseBounds1.getMaxZ() * this.mzz + this.mzt));
/*      */ 
/*  325 */       break;
/*      */     case 2:
/*  327 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBoundsAndSort((float)(paramBaseBounds1.getMinX() * this.mxx), (float)(paramBaseBounds1.getMinY() * this.myy), (float)(paramBaseBounds1.getMinZ() * this.mzz), (float)(paramBaseBounds1.getMaxX() * this.mxx), (float)(paramBaseBounds1.getMaxY() * this.myy), (float)(paramBaseBounds1.getMaxZ() * this.mzz));
/*      */ 
/*  334 */       break;
/*      */     case 1:
/*  336 */       paramBaseBounds2 = paramBaseBounds2.deriveWithNewBounds((float)(paramBaseBounds1.getMinX() + this.mxt), (float)(paramBaseBounds1.getMinY() + this.myt), (float)(paramBaseBounds1.getMinZ() + this.mzt), (float)(paramBaseBounds1.getMaxX() + this.mxt), (float)(paramBaseBounds1.getMaxY() + this.myt), (float)(paramBaseBounds1.getMaxZ() + this.mzt));
/*      */ 
/*  343 */       break;
/*      */     case 0:
/*  345 */       if (paramBaseBounds1 != paramBaseBounds2) {
/*  346 */         paramBaseBounds2 = paramBaseBounds2.deriveWithNewBounds(paramBaseBounds1);
/*      */       }
/*      */       break;
/*      */     }
/*      */ 
/*  351 */     return paramBaseBounds2;
/*      */   }
/*      */ 
/*      */   public Vec3d transform(Vec3d paramVec3d1, Vec3d paramVec3d2)
/*      */   {
/*  357 */     if ((this.state & 0x8) == 0) {
/*  358 */       return super.transform(paramVec3d1, paramVec3d2);
/*      */     }
/*  360 */     if (paramVec3d2 == null) {
/*  361 */       paramVec3d2 = new Vec3d();
/*      */     }
/*  363 */     double d1 = paramVec3d1.x;
/*  364 */     double d2 = paramVec3d1.y;
/*  365 */     double d3 = paramVec3d1.z;
/*  366 */     paramVec3d2.x = (this.mxx * d1 + this.mxy * d2 + this.mxz * d3 + this.mxt);
/*  367 */     paramVec3d2.y = (this.myx * d1 + this.myy * d2 + this.myz * d3 + this.myt);
/*  368 */     paramVec3d2.z = (this.mzx * d1 + this.mzy * d2 + this.mzz * d3 + this.mzt);
/*  369 */     return paramVec3d2;
/*      */   }
/*      */ 
/*      */   public void inverseTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*      */     throws NoninvertibleTransformException
/*      */   {
/*  378 */     if ((this.state & 0x8) == 0) {
/*  379 */       super.inverseTransform(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3);
/*      */     }
/*      */     else
/*  382 */       createInverse().transform(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */   public void inverseDeltaTransform(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
/*      */     throws NoninvertibleTransformException
/*      */   {
/*  392 */     if ((this.state & 0x8) == 0) {
/*  393 */       super.inverseDeltaTransform(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3);
/*      */     }
/*      */     else
/*  396 */       createInverse().deltaTransform(paramArrayOfFloat1, paramInt1, paramArrayOfFloat2, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */   public void inverseTransform(double[] paramArrayOfDouble1, int paramInt1, double[] paramArrayOfDouble2, int paramInt2, int paramInt3)
/*      */     throws NoninvertibleTransformException
/*      */   {
/*  406 */     if ((this.state & 0x8) == 0) {
/*  407 */       super.inverseTransform(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3);
/*      */     }
/*      */     else
/*  410 */       createInverse().transform(paramArrayOfDouble1, paramInt1, paramArrayOfDouble2, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */   public Point2D inverseTransform(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*      */     throws NoninvertibleTransformException
/*      */   {
/*  418 */     if ((this.state & 0x8) == 0) {
/*  419 */       return super.inverseTransform(paramPoint2D1, paramPoint2D2);
/*      */     }
/*      */ 
/*  422 */     return createInverse().transform(paramPoint2D1, paramPoint2D2);
/*      */   }
/*      */ 
/*      */   public Vec3d inverseTransform(Vec3d paramVec3d1, Vec3d paramVec3d2)
/*      */     throws NoninvertibleTransformException
/*      */   {
/*  430 */     if ((this.state & 0x8) == 0) {
/*  431 */       return super.inverseTransform(paramVec3d1, paramVec3d2);
/*      */     }
/*      */ 
/*  434 */     return createInverse().transform(paramVec3d1, paramVec3d2);
/*      */   }
/*      */ 
/*      */   public BaseBounds inverseTransform(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*      */     throws NoninvertibleTransformException
/*      */   {
/*  442 */     if ((this.state & 0x8) == 0) {
/*  443 */       paramBaseBounds2 = super.inverseTransform(paramBaseBounds1, paramBaseBounds2);
/*      */     }
/*      */     else {
/*  446 */       paramBaseBounds2 = createInverse().transform(paramBaseBounds1, paramBaseBounds2);
/*      */     }
/*  448 */     return paramBaseBounds2;
/*      */   }
/*      */ 
/*      */   public void inverseTransform(Rectangle paramRectangle1, Rectangle paramRectangle2)
/*      */     throws NoninvertibleTransformException
/*      */   {
/*  455 */     if ((this.state & 0x8) == 0) {
/*  456 */       super.inverseTransform(paramRectangle1, paramRectangle2);
/*      */     }
/*      */     else
/*  459 */       createInverse().transform(paramRectangle1, paramRectangle2);
/*      */   }
/*      */ 
/*      */   public BaseTransform createInverse()
/*      */     throws NoninvertibleTransformException
/*      */   {
/*  467 */     BaseTransform localBaseTransform = copy();
/*  468 */     localBaseTransform.invert();
/*  469 */     return localBaseTransform;
/*      */   }
/*      */ 
/*      */   public void invert()
/*      */     throws NoninvertibleTransformException
/*      */   {
/*  476 */     if ((this.state & 0x8) == 0) {
/*  477 */       super.invert();
/*  478 */       return;
/*      */     }
/*      */ 
/*  489 */     double d1 = minor(0, 0);
/*  490 */     double d2 = -minor(0, 1);
/*  491 */     double d3 = minor(0, 2);
/*  492 */     double d4 = -minor(1, 0);
/*  493 */     double d5 = minor(1, 1);
/*  494 */     double d6 = -minor(1, 2);
/*  495 */     double d7 = minor(2, 0);
/*  496 */     double d8 = -minor(2, 1);
/*  497 */     double d9 = minor(2, 2);
/*  498 */     double d10 = -minor(3, 0);
/*  499 */     double d11 = minor(3, 1);
/*  500 */     double d12 = -minor(3, 2);
/*  501 */     double d13 = getDeterminant();
/*  502 */     this.mxx = (d1 / d13);
/*  503 */     this.mxy = (d4 / d13);
/*  504 */     this.mxz = (d7 / d13);
/*  505 */     this.mxt = (d10 / d13);
/*  506 */     this.myx = (d2 / d13);
/*  507 */     this.myy = (d5 / d13);
/*  508 */     this.myz = (d8 / d13);
/*  509 */     this.myt = (d11 / d13);
/*  510 */     this.mzx = (d3 / d13);
/*  511 */     this.mzy = (d6 / d13);
/*  512 */     this.mzz = (d9 / d13);
/*  513 */     this.mzt = (d12 / d13);
/*  514 */     updateState();
/*      */   }
/*      */ 
/*      */   private double minor(int paramInt1, int paramInt2) {
/*  518 */     double d1 = this.mxx; double d2 = this.mxy; double d3 = this.mxz;
/*  519 */     double d4 = this.myx; double d5 = this.myy; double d6 = this.myz;
/*  520 */     double d7 = this.mzx; double d8 = this.mzy; double d9 = this.mzz;
/*  521 */     switch (paramInt2) {
/*      */     case 0:
/*  523 */       d1 = d2;
/*  524 */       d4 = d5;
/*  525 */       d7 = d8;
/*      */     case 1:
/*  527 */       d2 = d3;
/*  528 */       d5 = d6;
/*  529 */       d8 = d9;
/*      */     case 2:
/*  531 */       d3 = this.mxt;
/*  532 */       d6 = this.myt;
/*  533 */       d9 = this.mzt;
/*      */     }
/*  535 */     switch (paramInt1) {
/*      */     case 0:
/*  537 */       d1 = d4;
/*  538 */       d2 = d5;
/*      */     case 1:
/*  541 */       d4 = d7;
/*  542 */       d5 = d8;
/*      */     case 2:
/*  548 */       break;
/*      */     case 3:
/*  551 */       return d1 * (d5 * d9 - d8 * d6) + d2 * (d6 * d7 - d9 * d4) + d3 * (d4 * d8 - d7 * d5);
/*      */     }
/*      */ 
/*  558 */     return d1 * d5 - d2 * d4;
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithNewTransform(BaseTransform paramBaseTransform)
/*      */   {
/*  563 */     setTransform(paramBaseTransform);
/*  564 */     return this;
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithTranslation(double paramDouble1, double paramDouble2)
/*      */   {
/*  569 */     translate(paramDouble1, paramDouble2, 0.0D);
/*  570 */     return this;
/*      */   }
/*      */ 
/*      */   public void translate(double paramDouble1, double paramDouble2)
/*      */   {
/*  575 */     if ((this.state & 0x8) == 0)
/*  576 */       super.translate(paramDouble1, paramDouble2);
/*      */     else
/*  578 */       translate(paramDouble1, paramDouble2, 0.0D);
/*      */   }
/*      */ 
/*      */   public void translate(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  583 */     if ((this.state & 0x8) == 0) {
/*  584 */       super.translate(paramDouble1, paramDouble2);
/*  585 */       if (paramDouble3 != 0.0D) {
/*  586 */         this.mzt = paramDouble3;
/*  587 */         this.state |= 8;
/*  588 */         if (this.type != -1) {
/*  589 */           this.type |= 128;
/*      */         }
/*      */       }
/*  592 */       return;
/*      */     }
/*  594 */     this.mxt = (paramDouble1 * this.mxx + paramDouble2 * this.mxy + paramDouble3 * this.mxz + this.mxt);
/*  595 */     this.myt = (paramDouble1 * this.myx + paramDouble2 * this.myy + paramDouble3 * this.myz + this.myt);
/*  596 */     this.mzt = (paramDouble1 * this.mzx + paramDouble2 * this.mzy + paramDouble3 * this.mzz + this.mzt);
/*  597 */     updateState();
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithPreTranslation(double paramDouble1, double paramDouble2)
/*      */   {
/*  602 */     preTranslate(paramDouble1, paramDouble2, 0.0D);
/*  603 */     return this;
/*      */   }
/*      */ 
/*      */   public void preTranslate(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  607 */     this.mxt += paramDouble1;
/*  608 */     this.myt += paramDouble2;
/*  609 */     this.mzt += paramDouble3;
/*  610 */     int i = 0;
/*  611 */     int j = 0;
/*  612 */     if (this.mzt == 0.0D) {
/*  613 */       if ((this.state & 0x8) != 0)
/*      */       {
/*  615 */         updateState();
/*      */       }
/*      */     }
/*      */     else {
/*  619 */       this.state |= 8;
/*  620 */       j = 128;
/*      */     }
/*  622 */     if ((this.mxt == 0.0D) && (this.myt == 0.0D)) {
/*  623 */       this.state &= -2;
/*  624 */       i = 1;
/*      */     } else {
/*  626 */       this.state |= 1;
/*  627 */       j |= 1;
/*      */     }
/*  629 */     if (this.type != -1)
/*  630 */       this.type = (this.type & (i ^ 0xFFFFFFFF) | j);
/*      */   }
/*      */ 
/*      */   public void scale(double paramDouble1, double paramDouble2)
/*      */   {
/*  636 */     if ((this.state & 0x8) == 0)
/*  637 */       super.scale(paramDouble1, paramDouble2);
/*      */     else
/*  639 */       scale(paramDouble1, paramDouble2, 1.0D);
/*      */   }
/*      */ 
/*      */   public void scale(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  644 */     if ((this.state & 0x8) == 0) {
/*  645 */       super.scale(paramDouble1, paramDouble2);
/*  646 */       if (paramDouble3 != 1.0D) {
/*  647 */         this.mzz = paramDouble3;
/*  648 */         this.state |= 8;
/*  649 */         if (this.type != -1) {
/*  650 */           this.type |= 128;
/*      */         }
/*      */       }
/*  653 */       return;
/*      */     }
/*  655 */     this.mxx *= paramDouble1;
/*  656 */     this.mxy *= paramDouble2;
/*  657 */     this.mxz *= paramDouble3;
/*      */ 
/*  659 */     this.myx *= paramDouble1;
/*  660 */     this.myy *= paramDouble2;
/*  661 */     this.myz *= paramDouble3;
/*      */ 
/*  663 */     this.mzx *= paramDouble1;
/*  664 */     this.mzy *= paramDouble2;
/*  665 */     this.mzz *= paramDouble3;
/*      */ 
/*  668 */     updateState();
/*      */   }
/*      */ 
/*      */   public void rotate(double paramDouble)
/*      */   {
/*  673 */     if ((this.state & 0x8) == 0)
/*  674 */       super.rotate(paramDouble);
/*      */     else
/*  676 */       rotate(paramDouble, 0.0D, 0.0D, 1.0D);
/*      */   }
/*      */ 
/*      */   public void rotate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/*  681 */     if (((this.state & 0x8) == 0) && (almostZero(paramDouble2)) && (almostZero(paramDouble3))) {
/*  682 */       if (paramDouble4 > 0.0D)
/*  683 */         super.rotate(paramDouble1);
/*  684 */       else if (paramDouble4 < 0.0D) {
/*  685 */         super.rotate(-paramDouble1);
/*      */       }
/*  687 */       return;
/*      */     }
/*  689 */     double d1 = Math.sqrt(paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3 + paramDouble4 * paramDouble4);
/*      */ 
/*  691 */     if (almostZero(d1)) {
/*  692 */       return;
/*      */     }
/*  694 */     d1 = 1.0D / d1;
/*  695 */     double d2 = paramDouble2 * d1;
/*  696 */     double d3 = paramDouble3 * d1;
/*  697 */     double d4 = paramDouble4 * d1;
/*      */ 
/*  699 */     double d5 = Math.sin(paramDouble1);
/*  700 */     double d6 = Math.cos(paramDouble1);
/*  701 */     double d7 = 1.0D - d6;
/*      */ 
/*  703 */     double d8 = d2 * d4;
/*  704 */     double d9 = d2 * d3;
/*  705 */     double d10 = d3 * d4;
/*      */ 
/*  707 */     double d11 = d7 * d2 * d2 + d6;
/*  708 */     double d12 = d7 * d9 - d5 * d4;
/*  709 */     double d13 = d7 * d8 + d5 * d3;
/*      */ 
/*  711 */     double d14 = d7 * d9 + d5 * d4;
/*  712 */     double d15 = d7 * d3 * d3 + d6;
/*  713 */     double d16 = d7 * d10 - d5 * d2;
/*      */ 
/*  715 */     double d17 = d7 * d8 - d5 * d3;
/*  716 */     double d18 = d7 * d10 + d5 * d2;
/*  717 */     double d19 = d7 * d4 * d4 + d6;
/*      */ 
/*  719 */     double d20 = this.mxx * d11 + this.mxy * d14 + this.mxz * d17;
/*  720 */     double d21 = this.mxx * d12 + this.mxy * d15 + this.mxz * d18;
/*  721 */     double d22 = this.mxx * d13 + this.mxy * d16 + this.mxz * d19;
/*  722 */     double d23 = this.myx * d11 + this.myy * d14 + this.myz * d17;
/*  723 */     double d24 = this.myx * d12 + this.myy * d15 + this.myz * d18;
/*  724 */     double d25 = this.myx * d13 + this.myy * d16 + this.myz * d19;
/*  725 */     double d26 = this.mzx * d11 + this.mzy * d14 + this.mzz * d17;
/*  726 */     double d27 = this.mzx * d12 + this.mzy * d15 + this.mzz * d18;
/*  727 */     double d28 = this.mzx * d13 + this.mzy * d16 + this.mzz * d19;
/*  728 */     this.mxx = d20;
/*  729 */     this.mxy = d21;
/*  730 */     this.mxz = d22;
/*  731 */     this.myx = d23;
/*  732 */     this.myy = d24;
/*  733 */     this.myz = d25;
/*  734 */     this.mzx = d26;
/*  735 */     this.mzy = d27;
/*  736 */     this.mzz = d28;
/*  737 */     updateState();
/*      */   }
/*      */ 
/*      */   public void shear(double paramDouble1, double paramDouble2)
/*      */   {
/*  742 */     if ((this.state & 0x8) == 0) {
/*  743 */       super.shear(paramDouble1, paramDouble2);
/*  744 */       return;
/*      */     }
/*  746 */     double d1 = this.mxx + this.mxy * paramDouble2;
/*  747 */     double d2 = this.mxy + this.mxx * paramDouble1;
/*  748 */     double d3 = this.myx + this.myy * paramDouble2;
/*  749 */     double d4 = this.myy + this.myx * paramDouble1;
/*  750 */     double d5 = this.mzx + this.mzy * paramDouble2;
/*  751 */     double d6 = this.mzy + this.mzx * paramDouble1;
/*  752 */     this.mxx = d1;
/*  753 */     this.mxy = d2;
/*  754 */     this.myx = d3;
/*  755 */     this.myy = d4;
/*  756 */     this.mzx = d5;
/*  757 */     this.mzy = d6;
/*  758 */     updateState();
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithConcatenation(BaseTransform paramBaseTransform)
/*      */   {
/*  763 */     concatenate(paramBaseTransform);
/*  764 */     return this;
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithPreConcatenation(BaseTransform paramBaseTransform)
/*      */   {
/*  769 */     preConcatenate(paramBaseTransform);
/*  770 */     return this;
/*      */   }
/*      */ 
/*      */   public void concatenate(BaseTransform paramBaseTransform)
/*      */   {
/*  775 */     switch (1.$SwitchMap$com$sun$javafx$geom$transform$BaseTransform$Degree[paramBaseTransform.getDegree().ordinal()]) {
/*      */     case 1:
/*  777 */       return;
/*      */     case 2:
/*  779 */       translate(paramBaseTransform.getMxt(), paramBaseTransform.getMyt());
/*  780 */       return;
/*      */     case 3:
/*  782 */       translate(paramBaseTransform.getMxt(), paramBaseTransform.getMyt(), paramBaseTransform.getMzt());
/*  783 */       return;
/*      */     case 4:
/*  785 */       if (!paramBaseTransform.is2D())
/*      */       {
/*      */         break;
/*      */       }
/*      */     case 5:
/*  790 */       if ((this.state & 0x8) == 0) { super.concatenate(paramBaseTransform);
/*      */         return;
/*      */       }
/*      */       break;
/*      */     }
/*  796 */     double d1 = paramBaseTransform.getMxx();
/*  797 */     double d2 = paramBaseTransform.getMxy();
/*  798 */     double d3 = paramBaseTransform.getMxz();
/*  799 */     double d4 = paramBaseTransform.getMxt();
/*  800 */     double d5 = paramBaseTransform.getMyx();
/*  801 */     double d6 = paramBaseTransform.getMyy();
/*  802 */     double d7 = paramBaseTransform.getMyz();
/*  803 */     double d8 = paramBaseTransform.getMyt();
/*  804 */     double d9 = paramBaseTransform.getMzx();
/*  805 */     double d10 = paramBaseTransform.getMzy();
/*  806 */     double d11 = paramBaseTransform.getMzz();
/*  807 */     double d12 = paramBaseTransform.getMzt();
/*  808 */     double d13 = this.mxx * d1 + this.mxy * d5 + this.mxz * d9;
/*  809 */     double d14 = this.mxx * d2 + this.mxy * d6 + this.mxz * d10;
/*  810 */     double d15 = this.mxx * d3 + this.mxy * d7 + this.mxz * d11;
/*  811 */     double d16 = this.mxx * d4 + this.mxy * d8 + this.mxz * d12 + this.mxt;
/*  812 */     double d17 = this.myx * d1 + this.myy * d5 + this.myz * d9;
/*  813 */     double d18 = this.myx * d2 + this.myy * d6 + this.myz * d10;
/*  814 */     double d19 = this.myx * d3 + this.myy * d7 + this.myz * d11;
/*  815 */     double d20 = this.myx * d4 + this.myy * d8 + this.myz * d12 + this.myt;
/*  816 */     double d21 = this.mzx * d1 + this.mzy * d5 + this.mzz * d9;
/*  817 */     double d22 = this.mzx * d2 + this.mzy * d6 + this.mzz * d10;
/*  818 */     double d23 = this.mzx * d3 + this.mzy * d7 + this.mzz * d11;
/*  819 */     double d24 = this.mzx * d4 + this.mzy * d8 + this.mzz * d12 + this.mzt;
/*  820 */     this.mxx = d13;
/*  821 */     this.mxy = d14;
/*  822 */     this.mxz = d15;
/*  823 */     this.mxt = d16;
/*  824 */     this.myx = d17;
/*  825 */     this.myy = d18;
/*  826 */     this.myz = d19;
/*  827 */     this.myt = d20;
/*  828 */     this.mzx = d21;
/*  829 */     this.mzy = d22;
/*  830 */     this.mzz = d23;
/*  831 */     this.mzt = d24;
/*  832 */     updateState();
/*      */   }
/*      */ 
/*      */   public void concatenate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*      */   {
/*  839 */     double d1 = this.mxx * paramDouble1 + this.mxy * paramDouble5 + this.mxz * paramDouble9;
/*  840 */     double d2 = this.mxx * paramDouble2 + this.mxy * paramDouble6 + this.mxz * paramDouble10;
/*  841 */     double d3 = this.mxx * paramDouble3 + this.mxy * paramDouble7 + this.mxz * paramDouble11;
/*  842 */     double d4 = this.mxx * paramDouble4 + this.mxy * paramDouble8 + this.mxz * paramDouble12 + this.mxt;
/*  843 */     double d5 = this.myx * paramDouble1 + this.myy * paramDouble5 + this.myz * paramDouble9;
/*  844 */     double d6 = this.myx * paramDouble2 + this.myy * paramDouble6 + this.myz * paramDouble10;
/*  845 */     double d7 = this.myx * paramDouble3 + this.myy * paramDouble7 + this.myz * paramDouble11;
/*  846 */     double d8 = this.myx * paramDouble4 + this.myy * paramDouble8 + this.myz * paramDouble12 + this.myt;
/*  847 */     double d9 = this.mzx * paramDouble1 + this.mzy * paramDouble5 + this.mzz * paramDouble9;
/*  848 */     double d10 = this.mzx * paramDouble2 + this.mzy * paramDouble6 + this.mzz * paramDouble10;
/*  849 */     double d11 = this.mzx * paramDouble3 + this.mzy * paramDouble7 + this.mzz * paramDouble11;
/*  850 */     double d12 = this.mzx * paramDouble4 + this.mzy * paramDouble8 + this.mzz * paramDouble12 + this.mzt;
/*  851 */     this.mxx = d1;
/*  852 */     this.mxy = d2;
/*  853 */     this.mxz = d3;
/*  854 */     this.mxt = d4;
/*  855 */     this.myx = d5;
/*  856 */     this.myy = d6;
/*  857 */     this.myz = d7;
/*  858 */     this.myt = d8;
/*  859 */     this.mzx = d9;
/*  860 */     this.mzy = d10;
/*  861 */     this.mzz = d11;
/*  862 */     this.mzt = d12;
/*  863 */     updateState();
/*      */   }
/*      */ 
/*      */   public BaseTransform deriveWithConcatenation(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/*  871 */     double d1 = this.mxx * paramDouble1 + this.mxy * paramDouble2;
/*  872 */     double d2 = this.mxx * paramDouble3 + this.mxy * paramDouble4;
/*      */ 
/*  874 */     double d3 = this.mxx * paramDouble5 + this.mxy * paramDouble6 + this.mxt;
/*  875 */     double d4 = this.myx * paramDouble1 + this.myy * paramDouble2;
/*  876 */     double d5 = this.myx * paramDouble3 + this.myy * paramDouble4;
/*      */ 
/*  878 */     double d6 = this.myx * paramDouble5 + this.myy * paramDouble6 + this.myt;
/*  879 */     double d7 = this.mzx * paramDouble1 + this.mzy * paramDouble2;
/*  880 */     double d8 = this.mzx * paramDouble3 + this.mzy * paramDouble4;
/*      */ 
/*  882 */     double d9 = this.mzx * paramDouble5 + this.mzy * paramDouble6 + this.mzt;
/*  883 */     this.mxx = d1;
/*  884 */     this.mxy = d2;
/*      */ 
/*  886 */     this.mxt = d3;
/*  887 */     this.myx = d4;
/*  888 */     this.myy = d5;
/*      */ 
/*  890 */     this.myt = d6;
/*  891 */     this.mzx = d7;
/*  892 */     this.mzy = d8;
/*      */ 
/*  894 */     this.mzt = d9;
/*  895 */     updateState();
/*  896 */     return this;
/*      */   }
/*      */ 
/*      */   public void preConcatenate(BaseTransform paramBaseTransform) {
/*  900 */     switch (1.$SwitchMap$com$sun$javafx$geom$transform$BaseTransform$Degree[paramBaseTransform.getDegree().ordinal()]) {
/*      */     case 1:
/*  902 */       return;
/*      */     case 2:
/*  904 */       preTranslate(paramBaseTransform.getMxt(), paramBaseTransform.getMyt(), 0.0D);
/*  905 */       return;
/*      */     case 3:
/*  907 */       preTranslate(paramBaseTransform.getMxt(), paramBaseTransform.getMyt(), paramBaseTransform.getMzt());
/*  908 */       return;
/*      */     }
/*  910 */     double d1 = paramBaseTransform.getMxx();
/*  911 */     double d2 = paramBaseTransform.getMxy();
/*  912 */     double d3 = paramBaseTransform.getMxz();
/*  913 */     double d4 = paramBaseTransform.getMxt();
/*  914 */     double d5 = paramBaseTransform.getMyx();
/*  915 */     double d6 = paramBaseTransform.getMyy();
/*  916 */     double d7 = paramBaseTransform.getMyz();
/*  917 */     double d8 = paramBaseTransform.getMyt();
/*  918 */     double d9 = paramBaseTransform.getMzx();
/*  919 */     double d10 = paramBaseTransform.getMzy();
/*  920 */     double d11 = paramBaseTransform.getMzz();
/*  921 */     double d12 = paramBaseTransform.getMzt();
/*  922 */     double d13 = d1 * this.mxx + d2 * this.myx + d3 * this.mzx;
/*  923 */     double d14 = d1 * this.mxy + d2 * this.myy + d3 * this.mzy;
/*  924 */     double d15 = d1 * this.mxz + d2 * this.myz + d3 * this.mzz;
/*  925 */     double d16 = d1 * this.mxt + d2 * this.myt + d3 * this.mzt + d4;
/*  926 */     double d17 = d5 * this.mxx + d6 * this.myx + d7 * this.mzx;
/*  927 */     double d18 = d5 * this.mxy + d6 * this.myy + d7 * this.mzy;
/*  928 */     double d19 = d5 * this.mxz + d6 * this.myz + d7 * this.mzz;
/*  929 */     double d20 = d5 * this.mxt + d6 * this.myt + d7 * this.mzt + d8;
/*  930 */     double d21 = d9 * this.mxx + d10 * this.myx + d11 * this.mzx;
/*  931 */     double d22 = d9 * this.mxy + d10 * this.myy + d11 * this.mzy;
/*  932 */     double d23 = d9 * this.mxz + d10 * this.myz + d11 * this.mzz;
/*  933 */     double d24 = d9 * this.mxt + d10 * this.myt + d11 * this.mzt + d12;
/*  934 */     this.mxx = d13;
/*  935 */     this.mxy = d14;
/*  936 */     this.mxz = d15;
/*  937 */     this.mxt = d16;
/*  938 */     this.myx = d17;
/*  939 */     this.myy = d18;
/*  940 */     this.myz = d19;
/*  941 */     this.myt = d20;
/*  942 */     this.mzx = d21;
/*  943 */     this.mzy = d22;
/*  944 */     this.mzz = d23;
/*  945 */     this.mzt = d24;
/*  946 */     updateState();
/*      */   }
/*      */ 
/*      */   public void restoreTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/*  954 */     throw new InternalError("must use Affine3D restore method to prevent loss of information");
/*      */   }
/*      */ 
/*      */   public void restoreTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*      */   {
/*  963 */     this.mxx = paramDouble1;
/*  964 */     this.mxy = paramDouble2;
/*  965 */     this.mxz = paramDouble3;
/*  966 */     this.mxt = paramDouble4;
/*      */ 
/*  968 */     this.myx = paramDouble5;
/*  969 */     this.myy = paramDouble6;
/*  970 */     this.myz = paramDouble7;
/*  971 */     this.myt = paramDouble8;
/*      */ 
/*  973 */     this.mzx = paramDouble9;
/*  974 */     this.mzy = paramDouble10;
/*  975 */     this.mzz = paramDouble11;
/*  976 */     this.mzt = paramDouble12;
/*      */ 
/*  978 */     updateState();
/*      */   }
/*      */ 
/*      */   public Affine3D lookAt(Vec3d paramVec3d1, Vec3d paramVec3d2, Vec3d paramVec3d3)
/*      */   {
/* 1000 */     double d1 = paramVec3d1.x - paramVec3d2.x;
/* 1001 */     double d2 = paramVec3d1.y - paramVec3d2.y;
/* 1002 */     double d3 = paramVec3d1.z - paramVec3d2.z;
/*      */ 
/* 1004 */     double d4 = 1.0D / Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/* 1005 */     d1 *= d4;
/* 1006 */     d2 *= d4;
/* 1007 */     d3 *= d4;
/*      */ 
/* 1009 */     d4 = 1.0D / Math.sqrt(paramVec3d3.x * paramVec3d3.x + paramVec3d3.y * paramVec3d3.y + paramVec3d3.z * paramVec3d3.z);
/* 1010 */     double d5 = paramVec3d3.x * d4;
/* 1011 */     double d6 = paramVec3d3.y * d4;
/* 1012 */     double d7 = paramVec3d3.z * d4;
/*      */ 
/* 1015 */     double d8 = d6 * d3 - d2 * d7;
/* 1016 */     double d9 = d7 * d1 - d5 * d3;
/* 1017 */     double d10 = d5 * d2 - d6 * d1;
/*      */ 
/* 1019 */     d4 = 1.0D / Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
/* 1020 */     d8 *= d4;
/* 1021 */     d9 *= d4;
/* 1022 */     d10 *= d4;
/*      */ 
/* 1025 */     d5 = d2 * d10 - d9 * d3;
/* 1026 */     d6 = d3 * d8 - d1 * d10;
/* 1027 */     d7 = d1 * d9 - d2 * d8;
/*      */ 
/* 1030 */     this.mxx = d8;
/* 1031 */     this.mxy = d9;
/* 1032 */     this.mxz = d10;
/*      */ 
/* 1034 */     this.myx = d5;
/* 1035 */     this.myy = d6;
/* 1036 */     this.myz = d7;
/*      */ 
/* 1038 */     this.mzx = d1;
/* 1039 */     this.mzy = d2;
/* 1040 */     this.mzz = d3;
/*      */ 
/* 1042 */     this.mxt = (-paramVec3d1.x * this.mxx + -paramVec3d1.y * this.mxy + -paramVec3d1.z * this.mxz);
/* 1043 */     this.myt = (-paramVec3d1.x * this.myx + -paramVec3d1.y * this.myy + -paramVec3d1.z * this.myz);
/* 1044 */     this.mzt = (-paramVec3d1.x * this.mzx + -paramVec3d1.y * this.mzy + -paramVec3d1.z * this.mzz);
/*      */ 
/* 1046 */     updateState();
/* 1047 */     return this;
/*      */   }
/*      */ 
/*      */   public static boolean almostZero(double paramDouble)
/*      */   {
/* 1053 */     return (paramDouble < 1.E-05D) && (paramDouble > -1.E-05D);
/*      */   }
/*      */ 
/*      */   static boolean almostOne(double paramDouble) {
/* 1057 */     return (paramDouble < 1.00001D) && (paramDouble > 0.9999900000000001D);
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.transform.Affine3D
 * JD-Core Version:    0.6.2
 */