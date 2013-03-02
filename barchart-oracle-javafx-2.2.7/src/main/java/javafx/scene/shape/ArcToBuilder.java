/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class ArcToBuilder<B extends ArcToBuilder<B>> extends PathElementBuilder<B>
/*     */   implements Builder<ArcTo>
/*     */ {
/*     */   private int __set;
/*     */   private boolean largeArcFlag;
/*     */   private double radiusX;
/*     */   private double radiusY;
/*     */   private boolean sweepFlag;
/*     */   private double x;
/*     */   private double XAxisRotation;
/*     */   private double y;
/*     */ 
/*     */   public static ArcToBuilder<?> create()
/*     */   {
/*  15 */     return new ArcToBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(ArcTo paramArcTo)
/*     */   {
/*  20 */     super.applyTo(paramArcTo);
/*  21 */     int i = this.__set;
/*  22 */     if ((i & 0x1) != 0) paramArcTo.setLargeArcFlag(this.largeArcFlag);
/*  23 */     if ((i & 0x2) != 0) paramArcTo.setRadiusX(this.radiusX);
/*  24 */     if ((i & 0x4) != 0) paramArcTo.setRadiusY(this.radiusY);
/*  25 */     if ((i & 0x8) != 0) paramArcTo.setSweepFlag(this.sweepFlag);
/*  26 */     if ((i & 0x10) != 0) paramArcTo.setX(this.x);
/*  27 */     if ((i & 0x20) != 0) paramArcTo.setXAxisRotation(this.XAxisRotation);
/*  28 */     if ((i & 0x40) != 0) paramArcTo.setY(this.y);
/*     */   }
/*     */ 
/*     */   public B largeArcFlag(boolean paramBoolean)
/*     */   {
/*  37 */     this.largeArcFlag = paramBoolean;
/*  38 */     this.__set |= 1;
/*  39 */     return this;
/*     */   }
/*     */ 
/*     */   public B radiusX(double paramDouble)
/*     */   {
/*  48 */     this.radiusX = paramDouble;
/*  49 */     this.__set |= 2;
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   public B radiusY(double paramDouble)
/*     */   {
/*  59 */     this.radiusY = paramDouble;
/*  60 */     this.__set |= 4;
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   public B sweepFlag(boolean paramBoolean)
/*     */   {
/*  70 */     this.sweepFlag = paramBoolean;
/*  71 */     this.__set |= 8;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public B x(double paramDouble)
/*     */   {
/*  81 */     this.x = paramDouble;
/*  82 */     this.__set |= 16;
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public B XAxisRotation(double paramDouble)
/*     */   {
/*  92 */     this.XAxisRotation = paramDouble;
/*  93 */     this.__set |= 32;
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public B y(double paramDouble)
/*     */   {
/* 103 */     this.y = paramDouble;
/* 104 */     this.__set |= 64;
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public ArcTo build()
/*     */   {
/* 112 */     ArcTo localArcTo = new ArcTo();
/* 113 */     applyTo(localArcTo);
/* 114 */     return localArcTo;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.ArcToBuilder
 * JD-Core Version:    0.6.2
 */