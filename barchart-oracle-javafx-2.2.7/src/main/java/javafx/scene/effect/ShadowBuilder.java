/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class ShadowBuilder<B extends ShadowBuilder<B>>
/*     */   implements Builder<Shadow>
/*     */ {
/*     */   private int __set;
/*     */   private BlurType blurType;
/*     */   private Color color;
/*     */   private double height;
/*     */   private Effect input;
/*     */   private double radius;
/*     */   private double width;
/*     */ 
/*     */   public static ShadowBuilder<?> create()
/*     */   {
/*  15 */     return new ShadowBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(Shadow paramShadow)
/*     */   {
/*  20 */     int i = this.__set;
/*  21 */     if ((i & 0x1) != 0) paramShadow.setBlurType(this.blurType);
/*  22 */     if ((i & 0x2) != 0) paramShadow.setColor(this.color);
/*  23 */     if ((i & 0x4) != 0) paramShadow.setHeight(this.height);
/*  24 */     if ((i & 0x8) != 0) paramShadow.setInput(this.input);
/*  25 */     if ((i & 0x10) != 0) paramShadow.setRadius(this.radius);
/*  26 */     if ((i & 0x20) != 0) paramShadow.setWidth(this.width);
/*     */   }
/*     */ 
/*     */   public B blurType(BlurType paramBlurType)
/*     */   {
/*  35 */     this.blurType = paramBlurType;
/*  36 */     this.__set |= 1;
/*  37 */     return this;
/*     */   }
/*     */ 
/*     */   public B color(Color paramColor)
/*     */   {
/*  46 */     this.color = paramColor;
/*  47 */     this.__set |= 2;
/*  48 */     return this;
/*     */   }
/*     */ 
/*     */   public B height(double paramDouble)
/*     */   {
/*  57 */     this.height = paramDouble;
/*  58 */     this.__set |= 4;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public B input(Effect paramEffect)
/*     */   {
/*  68 */     this.input = paramEffect;
/*  69 */     this.__set |= 8;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public B radius(double paramDouble)
/*     */   {
/*  79 */     this.radius = paramDouble;
/*  80 */     this.__set |= 16;
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   public B width(double paramDouble)
/*     */   {
/*  90 */     this.width = paramDouble;
/*  91 */     this.__set |= 32;
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   public Shadow build()
/*     */   {
/*  99 */     Shadow localShadow = new Shadow();
/* 100 */     applyTo(localShadow);
/* 101 */     return localShadow;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.ShadowBuilder
 * JD-Core Version:    0.6.2
 */