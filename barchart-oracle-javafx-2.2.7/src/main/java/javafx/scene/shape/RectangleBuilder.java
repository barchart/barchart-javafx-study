/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class RectangleBuilder<B extends RectangleBuilder<B>> extends ShapeBuilder<B>
/*     */   implements Builder<Rectangle>
/*     */ {
/*     */   private int __set;
/*     */   private double arcHeight;
/*     */   private double arcWidth;
/*     */   private double height;
/*     */   private double width;
/*     */   private double x;
/*     */   private double y;
/*     */ 
/*     */   public static RectangleBuilder<?> create()
/*     */   {
/*  15 */     return new RectangleBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(Rectangle paramRectangle)
/*     */   {
/*  20 */     super.applyTo(paramRectangle);
/*  21 */     int i = this.__set;
/*  22 */     if ((i & 0x1) != 0) paramRectangle.setArcHeight(this.arcHeight);
/*  23 */     if ((i & 0x2) != 0) paramRectangle.setArcWidth(this.arcWidth);
/*  24 */     if ((i & 0x4) != 0) paramRectangle.setHeight(this.height);
/*  25 */     if ((i & 0x8) != 0) paramRectangle.setWidth(this.width);
/*  26 */     if ((i & 0x10) != 0) paramRectangle.setX(this.x);
/*  27 */     if ((i & 0x20) != 0) paramRectangle.setY(this.y);
/*     */   }
/*     */ 
/*     */   public B arcHeight(double paramDouble)
/*     */   {
/*  36 */     this.arcHeight = paramDouble;
/*  37 */     this.__set |= 1;
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   public B arcWidth(double paramDouble)
/*     */   {
/*  47 */     this.arcWidth = paramDouble;
/*  48 */     this.__set |= 2;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B height(double paramDouble)
/*     */   {
/*  58 */     this.height = paramDouble;
/*  59 */     this.__set |= 4;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B width(double paramDouble)
/*     */   {
/*  69 */     this.width = paramDouble;
/*  70 */     this.__set |= 8;
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B x(double paramDouble)
/*     */   {
/*  80 */     this.x = paramDouble;
/*  81 */     this.__set |= 16;
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B y(double paramDouble)
/*     */   {
/*  91 */     this.y = paramDouble;
/*  92 */     this.__set |= 32;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public Rectangle build()
/*     */   {
/* 100 */     Rectangle localRectangle = new Rectangle();
/* 101 */     applyTo(localRectangle);
/* 102 */     return localRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.RectangleBuilder
 * JD-Core Version:    0.6.2
 */