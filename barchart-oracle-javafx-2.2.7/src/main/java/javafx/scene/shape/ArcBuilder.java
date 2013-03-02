/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class ArcBuilder<B extends ArcBuilder<B>> extends ShapeBuilder<B>
/*     */   implements Builder<Arc>
/*     */ {
/*     */   private int __set;
/*     */   private double centerX;
/*     */   private double centerY;
/*     */   private double length;
/*     */   private double radiusX;
/*     */   private double radiusY;
/*     */   private double startAngle;
/*     */   private ArcType type;
/*     */ 
/*     */   public static ArcBuilder<?> create()
/*     */   {
/*  15 */     return new ArcBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(Arc paramArc)
/*     */   {
/*  20 */     super.applyTo(paramArc);
/*  21 */     int i = this.__set;
/*  22 */     if ((i & 0x1) != 0) paramArc.setCenterX(this.centerX);
/*  23 */     if ((i & 0x2) != 0) paramArc.setCenterY(this.centerY);
/*  24 */     if ((i & 0x4) != 0) paramArc.setLength(this.length);
/*  25 */     if ((i & 0x8) != 0) paramArc.setRadiusX(this.radiusX);
/*  26 */     if ((i & 0x10) != 0) paramArc.setRadiusY(this.radiusY);
/*  27 */     if ((i & 0x20) != 0) paramArc.setStartAngle(this.startAngle);
/*  28 */     if ((i & 0x40) != 0) paramArc.setType(this.type);
/*     */   }
/*     */ 
/*     */   public B centerX(double paramDouble)
/*     */   {
/*  37 */     this.centerX = paramDouble;
/*  38 */     this.__set |= 1;
/*  39 */     return this;
/*     */   }
/*     */ 
/*     */   public B centerY(double paramDouble)
/*     */   {
/*  48 */     this.centerY = paramDouble;
/*  49 */     this.__set |= 2;
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   public B length(double paramDouble)
/*     */   {
/*  59 */     this.length = paramDouble;
/*  60 */     this.__set |= 4;
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   public B radiusX(double paramDouble)
/*     */   {
/*  70 */     this.radiusX = paramDouble;
/*  71 */     this.__set |= 8;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public B radiusY(double paramDouble)
/*     */   {
/*  81 */     this.radiusY = paramDouble;
/*  82 */     this.__set |= 16;
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public B startAngle(double paramDouble)
/*     */   {
/*  92 */     this.startAngle = paramDouble;
/*  93 */     this.__set |= 32;
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public B type(ArcType paramArcType)
/*     */   {
/* 103 */     this.type = paramArcType;
/* 104 */     this.__set |= 64;
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public Arc build()
/*     */   {
/* 112 */     Arc localArc = new Arc();
/* 113 */     applyTo(localArc);
/* 114 */     return localArc;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.ArcBuilder
 * JD-Core Version:    0.6.2
 */