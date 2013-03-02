/*     */ package javafx.scene.transform;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class ScaleBuilder<B extends ScaleBuilder<B>>
/*     */   implements Builder<Scale>
/*     */ {
/*     */   private int __set;
/*     */   private double pivotX;
/*     */   private double pivotY;
/*     */   private double pivotZ;
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */ 
/*     */   public static ScaleBuilder<?> create()
/*     */   {
/*  15 */     return new ScaleBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(Scale paramScale)
/*     */   {
/*  20 */     int i = this.__set;
/*  21 */     if ((i & 0x1) != 0) paramScale.setPivotX(this.pivotX);
/*  22 */     if ((i & 0x2) != 0) paramScale.setPivotY(this.pivotY);
/*  23 */     if ((i & 0x4) != 0) paramScale.setPivotZ(this.pivotZ);
/*  24 */     if ((i & 0x8) != 0) paramScale.setX(this.x);
/*  25 */     if ((i & 0x10) != 0) paramScale.setY(this.y);
/*  26 */     if ((i & 0x20) != 0) paramScale.setZ(this.z);
/*     */   }
/*     */ 
/*     */   public B pivotX(double paramDouble)
/*     */   {
/*  35 */     this.pivotX = paramDouble;
/*  36 */     this.__set |= 1;
/*  37 */     return this;
/*     */   }
/*     */ 
/*     */   public B pivotY(double paramDouble)
/*     */   {
/*  46 */     this.pivotY = paramDouble;
/*  47 */     this.__set |= 2;
/*  48 */     return this;
/*     */   }
/*     */ 
/*     */   public B pivotZ(double paramDouble)
/*     */   {
/*  57 */     this.pivotZ = paramDouble;
/*  58 */     this.__set |= 4;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public B x(double paramDouble)
/*     */   {
/*  68 */     this.x = paramDouble;
/*  69 */     this.__set |= 8;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public B y(double paramDouble)
/*     */   {
/*  79 */     this.y = paramDouble;
/*  80 */     this.__set |= 16;
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   public B z(double paramDouble)
/*     */   {
/*  90 */     this.z = paramDouble;
/*  91 */     this.__set |= 32;
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   public Scale build()
/*     */   {
/*  99 */     Scale localScale = new Scale();
/* 100 */     applyTo(localScale);
/* 101 */     return localScale;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.transform.ScaleBuilder
 * JD-Core Version:    0.6.2
 */