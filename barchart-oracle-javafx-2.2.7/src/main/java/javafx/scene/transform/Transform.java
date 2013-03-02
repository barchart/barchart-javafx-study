/*     */ package javafx.scene.transform;
/*     */ 
/*     */ import com.sun.javafx.WeakReferenceQueue;
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import com.sun.javafx.scene.transform.TransformUtils;
/*     */ import java.util.Iterator;
/*     */ import javafx.geometry.Point3D;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public abstract class Transform
/*     */ {
/* 220 */   private WeakReferenceQueue impl_nodes = new WeakReferenceQueue();
/*     */ 
/*     */   public static Affine affine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/*  66 */     Affine localAffine = new Affine();
/*  67 */     localAffine.setMxx(paramDouble1);
/*  68 */     localAffine.setMxy(paramDouble3);
/*  69 */     localAffine.setTx(paramDouble5);
/*  70 */     localAffine.setMyx(paramDouble2);
/*  71 */     localAffine.setMyy(paramDouble4);
/*  72 */     localAffine.setTy(paramDouble6);
/*  73 */     return localAffine;
/*     */   }
/*     */ 
/*     */   public static Affine affine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*     */   {
/* 102 */     Affine localAffine = new Affine();
/* 103 */     localAffine.setMxx(paramDouble1);
/* 104 */     localAffine.setMxy(paramDouble2);
/* 105 */     localAffine.setMxz(paramDouble3);
/* 106 */     localAffine.setTx(paramDouble4);
/* 107 */     localAffine.setMyx(paramDouble5);
/* 108 */     localAffine.setMyy(paramDouble6);
/* 109 */     localAffine.setMyz(paramDouble7);
/* 110 */     localAffine.setTy(paramDouble8);
/* 111 */     localAffine.setMzx(paramDouble9);
/* 112 */     localAffine.setMzy(paramDouble10);
/* 113 */     localAffine.setMzz(paramDouble11);
/* 114 */     localAffine.setTz(paramDouble12);
/* 115 */     return localAffine;
/*     */   }
/*     */ 
/*     */   public static Translate translate(double paramDouble1, double paramDouble2)
/*     */   {
/* 128 */     Translate localTranslate = new Translate();
/* 129 */     localTranslate.setX(paramDouble1);
/* 130 */     localTranslate.setY(paramDouble2);
/* 131 */     return localTranslate;
/*     */   }
/*     */ 
/*     */   public static Rotate rotate(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 145 */     Rotate localRotate = new Rotate();
/* 146 */     localRotate.setAngle(paramDouble1);
/* 147 */     localRotate.setPivotX(paramDouble2);
/* 148 */     localRotate.setPivotY(paramDouble3);
/* 149 */     return localRotate;
/*     */   }
/*     */ 
/*     */   public static Scale scale(double paramDouble1, double paramDouble2)
/*     */   {
/* 162 */     Scale localScale = new Scale();
/* 163 */     localScale.setX(paramDouble1);
/* 164 */     localScale.setY(paramDouble2);
/* 165 */     return localScale;
/*     */   }
/*     */ 
/*     */   public static Scale scale(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 179 */     Scale localScale = new Scale();
/* 180 */     localScale.setX(paramDouble1);
/* 181 */     localScale.setY(paramDouble2);
/* 182 */     localScale.setPivotX(paramDouble3);
/* 183 */     localScale.setPivotY(paramDouble4);
/* 184 */     return localScale;
/*     */   }
/*     */ 
/*     */   public static Shear shear(double paramDouble1, double paramDouble2)
/*     */   {
/* 197 */     Shear localShear = new Shear();
/* 198 */     localShear.setX(paramDouble1);
/* 199 */     localShear.setY(paramDouble2);
/* 200 */     return localShear;
/*     */   }
/*     */ 
/*     */   public static Shear shear(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 212 */     Shear localShear = new Shear();
/* 213 */     localShear.setX(paramDouble1);
/* 214 */     localShear.setY(paramDouble2);
/* 215 */     localShear.setPivotX(paramDouble3);
/* 216 */     localShear.setPivotY(paramDouble4);
/* 217 */     return localShear;
/*     */   }
/*     */ 
/*     */   public double getMxx()
/*     */   {
/* 228 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   public double getMxy()
/*     */   {
/* 237 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double getMxz()
/*     */   {
/* 246 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double getTx()
/*     */   {
/* 255 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double getMyx()
/*     */   {
/* 264 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double getMyy()
/*     */   {
/* 273 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   public double getMyz()
/*     */   {
/* 282 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double getTy()
/*     */   {
/* 291 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double getMzx()
/*     */   {
/* 300 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double getMzy()
/*     */   {
/* 309 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double getMzz()
/*     */   {
/* 318 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   public double getTz()
/*     */   {
/* 327 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Transform impl_getConcatenation(Transform paramTransform)
/*     */   {
/* 342 */     double d1 = paramTransform.getMxx();
/* 343 */     double d2 = paramTransform.getMxy();
/* 344 */     double d3 = paramTransform.getMxz();
/* 345 */     double d4 = paramTransform.getTx();
/* 346 */     double d5 = paramTransform.getMyx();
/* 347 */     double d6 = paramTransform.getMyy();
/* 348 */     double d7 = paramTransform.getMyz();
/* 349 */     double d8 = paramTransform.getTy();
/* 350 */     double d9 = paramTransform.getMzx();
/* 351 */     double d10 = paramTransform.getMzy();
/* 352 */     double d11 = paramTransform.getMzz();
/* 353 */     double d12 = paramTransform.getTz();
/* 354 */     double d13 = getMxx() * d1 + getMxy() * d5 + getMxz() * d9;
/* 355 */     double d14 = getMxx() * d2 + getMxy() * d6 + getMxz() * d10;
/* 356 */     double d15 = getMxx() * d3 + getMxy() * d7 + getMxz() * d11;
/* 357 */     double d16 = getMxx() * d4 + getMxy() * d8 + getMxz() * d12 + getTx();
/* 358 */     double d17 = getMyx() * d1 + getMyy() * d5 + getMyz() * d9;
/* 359 */     double d18 = getMyx() * d2 + getMyy() * d6 + getMyz() * d10;
/* 360 */     double d19 = getMyx() * d3 + getMyy() * d7 + getMyz() * d11;
/* 361 */     double d20 = getMyx() * d4 + getMyy() * d8 + getMyz() * d12 + getTy();
/* 362 */     double d21 = getMzx() * d1 + getMzy() * d5 + getMzz() * d9;
/* 363 */     double d22 = getMzx() * d2 + getMzy() * d6 + getMzz() * d10;
/* 364 */     double d23 = getMzx() * d3 + getMzy() * d7 + getMzz() * d11;
/* 365 */     double d24 = getMzx() * d4 + getMzy() * d8 + getMzz() * d12 + getTz();
/* 366 */     return TransformUtils.immutableTransform(d13, d14, d15, d16, d17, d18, d19, d20, d21, d22, d23, d24);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Point3D impl_transform(Point3D paramPoint3D)
/*     */   {
/* 385 */     double d1 = paramPoint3D.getX();
/* 386 */     double d2 = paramPoint3D.getY();
/* 387 */     double d3 = paramPoint3D.getZ();
/* 388 */     return new Point3D(getMxx() * d1 + getMxy() * d2 + getMxz() * d3 + getTx(), getMyx() * d1 + getMyy() * d2 + getMyz() * d3 + getTy(), getMzx() * d1 + getMzy() * d2 + getMzz() * d3 + getTz());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public abstract void impl_apply(Affine3D paramAffine3D);
/*     */ 
/*     */   @Deprecated
/*     */   public abstract Transform impl_copy();
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_add(Node paramNode)
/*     */   {
/* 414 */     this.impl_nodes.add(paramNode);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_remove(Node paramNode)
/*     */   {
/* 423 */     this.impl_nodes.remove(paramNode);
/*     */   }
/*     */ 
/*     */   void transformChanged() {
/* 427 */     Iterator localIterator = this.impl_nodes.iterator();
/* 428 */     while (localIterator.hasNext())
/* 429 */       ((Node)localIterator.next()).impl_transformsChanged();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.transform.Transform
 * JD-Core Version:    0.6.2
 */