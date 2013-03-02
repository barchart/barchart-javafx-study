/*     */ package javafx.scene.transform;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class AffineBuilder<B extends AffineBuilder<B>>
/*     */   implements Builder<Affine>
/*     */ {
/*     */   private int __set;
/*     */   private double mxx;
/*     */   private double mxy;
/*     */   private double mxz;
/*     */   private double myx;
/*     */   private double myy;
/*     */   private double myz;
/*     */   private double mzx;
/*     */   private double mzy;
/*     */   private double mzz;
/*     */   private double tx;
/*     */   private double ty;
/*     */   private double tz;
/*     */ 
/*     */   public static AffineBuilder<?> create()
/*     */   {
/*  15 */     return new AffineBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Affine paramAffine) {
/*  23 */     int i = this.__set;
/*  24 */     while (i != 0) {
/*  25 */       int j = Integer.numberOfTrailingZeros(i);
/*  26 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  27 */       switch (j) { case 0:
/*  28 */         paramAffine.setMxx(this.mxx); break;
/*     */       case 1:
/*  29 */         paramAffine.setMxy(this.mxy); break;
/*     */       case 2:
/*  30 */         paramAffine.setMxz(this.mxz); break;
/*     */       case 3:
/*  31 */         paramAffine.setMyx(this.myx); break;
/*     */       case 4:
/*  32 */         paramAffine.setMyy(this.myy); break;
/*     */       case 5:
/*  33 */         paramAffine.setMyz(this.myz); break;
/*     */       case 6:
/*  34 */         paramAffine.setMzx(this.mzx); break;
/*     */       case 7:
/*  35 */         paramAffine.setMzy(this.mzy); break;
/*     */       case 8:
/*  36 */         paramAffine.setMzz(this.mzz); break;
/*     */       case 9:
/*  37 */         paramAffine.setTx(this.tx); break;
/*     */       case 10:
/*  38 */         paramAffine.setTy(this.ty); break;
/*     */       case 11:
/*  39 */         paramAffine.setTz(this.tz);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B mxx(double paramDouble)
/*     */   {
/*  50 */     this.mxx = paramDouble;
/*  51 */     __set(0);
/*  52 */     return this;
/*     */   }
/*     */ 
/*     */   public B mxy(double paramDouble)
/*     */   {
/*  61 */     this.mxy = paramDouble;
/*  62 */     __set(1);
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   public B mxz(double paramDouble)
/*     */   {
/*  72 */     this.mxz = paramDouble;
/*  73 */     __set(2);
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   public B myx(double paramDouble)
/*     */   {
/*  83 */     this.myx = paramDouble;
/*  84 */     __set(3);
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public B myy(double paramDouble)
/*     */   {
/*  94 */     this.myy = paramDouble;
/*  95 */     __set(4);
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   public B myz(double paramDouble)
/*     */   {
/* 105 */     this.myz = paramDouble;
/* 106 */     __set(5);
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   public B mzx(double paramDouble)
/*     */   {
/* 116 */     this.mzx = paramDouble;
/* 117 */     __set(6);
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   public B mzy(double paramDouble)
/*     */   {
/* 127 */     this.mzy = paramDouble;
/* 128 */     __set(7);
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   public B mzz(double paramDouble)
/*     */   {
/* 138 */     this.mzz = paramDouble;
/* 139 */     __set(8);
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   public B tx(double paramDouble)
/*     */   {
/* 149 */     this.tx = paramDouble;
/* 150 */     __set(9);
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   public B ty(double paramDouble)
/*     */   {
/* 160 */     this.ty = paramDouble;
/* 161 */     __set(10);
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */   public B tz(double paramDouble)
/*     */   {
/* 171 */     this.tz = paramDouble;
/* 172 */     __set(11);
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */   public Affine build()
/*     */   {
/* 180 */     Affine localAffine = new Affine();
/* 181 */     applyTo(localAffine);
/* 182 */     return localAffine;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.transform.AffineBuilder
 * JD-Core Version:    0.6.2
 */