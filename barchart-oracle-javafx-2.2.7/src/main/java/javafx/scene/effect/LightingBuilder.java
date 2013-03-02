/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class LightingBuilder<B extends LightingBuilder<B>>
/*     */   implements Builder<Lighting>
/*     */ {
/*     */   private int __set;
/*     */   private Effect bumpInput;
/*     */   private Effect contentInput;
/*     */   private double diffuseConstant;
/*     */   private Light light;
/*     */   private double specularConstant;
/*     */   private double specularExponent;
/*     */   private double surfaceScale;
/*     */ 
/*     */   public static LightingBuilder<?> create()
/*     */   {
/*  15 */     return new LightingBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(Lighting paramLighting)
/*     */   {
/*  20 */     int i = this.__set;
/*  21 */     if ((i & 0x1) != 0) paramLighting.setBumpInput(this.bumpInput);
/*  22 */     if ((i & 0x2) != 0) paramLighting.setContentInput(this.contentInput);
/*  23 */     if ((i & 0x4) != 0) paramLighting.setDiffuseConstant(this.diffuseConstant);
/*  24 */     if ((i & 0x8) != 0) paramLighting.setLight(this.light);
/*  25 */     if ((i & 0x10) != 0) paramLighting.setSpecularConstant(this.specularConstant);
/*  26 */     if ((i & 0x20) != 0) paramLighting.setSpecularExponent(this.specularExponent);
/*  27 */     if ((i & 0x40) != 0) paramLighting.setSurfaceScale(this.surfaceScale);
/*     */   }
/*     */ 
/*     */   public B bumpInput(Effect paramEffect)
/*     */   {
/*  36 */     this.bumpInput = paramEffect;
/*  37 */     this.__set |= 1;
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   public B contentInput(Effect paramEffect)
/*     */   {
/*  47 */     this.contentInput = paramEffect;
/*  48 */     this.__set |= 2;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B diffuseConstant(double paramDouble)
/*     */   {
/*  58 */     this.diffuseConstant = paramDouble;
/*  59 */     this.__set |= 4;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B light(Light paramLight)
/*     */   {
/*  69 */     this.light = paramLight;
/*  70 */     this.__set |= 8;
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B specularConstant(double paramDouble)
/*     */   {
/*  80 */     this.specularConstant = paramDouble;
/*  81 */     this.__set |= 16;
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B specularExponent(double paramDouble)
/*     */   {
/*  91 */     this.specularExponent = paramDouble;
/*  92 */     this.__set |= 32;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B surfaceScale(double paramDouble)
/*     */   {
/* 102 */     this.surfaceScale = paramDouble;
/* 103 */     this.__set |= 64;
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public Lighting build()
/*     */   {
/* 111 */     Lighting localLighting = new Lighting();
/* 112 */     applyTo(localLighting);
/* 113 */     return localLighting;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.LightingBuilder
 * JD-Core Version:    0.6.2
 */