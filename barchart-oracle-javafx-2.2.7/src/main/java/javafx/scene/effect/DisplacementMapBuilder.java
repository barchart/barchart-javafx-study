/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class DisplacementMapBuilder<B extends DisplacementMapBuilder<B>>
/*     */   implements Builder<DisplacementMap>
/*     */ {
/*     */   private int __set;
/*     */   private Effect input;
/*     */   private FloatMap mapData;
/*     */   private double offsetX;
/*     */   private double offsetY;
/*     */   private double scaleX;
/*     */   private double scaleY;
/*     */   private boolean wrap;
/*     */ 
/*     */   public static DisplacementMapBuilder<?> create()
/*     */   {
/*  15 */     return new DisplacementMapBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(DisplacementMap paramDisplacementMap)
/*     */   {
/*  20 */     int i = this.__set;
/*  21 */     if ((i & 0x1) != 0) paramDisplacementMap.setInput(this.input);
/*  22 */     if ((i & 0x2) != 0) paramDisplacementMap.setMapData(this.mapData);
/*  23 */     if ((i & 0x4) != 0) paramDisplacementMap.setOffsetX(this.offsetX);
/*  24 */     if ((i & 0x8) != 0) paramDisplacementMap.setOffsetY(this.offsetY);
/*  25 */     if ((i & 0x10) != 0) paramDisplacementMap.setScaleX(this.scaleX);
/*  26 */     if ((i & 0x20) != 0) paramDisplacementMap.setScaleY(this.scaleY);
/*  27 */     if ((i & 0x40) != 0) paramDisplacementMap.setWrap(this.wrap);
/*     */   }
/*     */ 
/*     */   public B input(Effect paramEffect)
/*     */   {
/*  36 */     this.input = paramEffect;
/*  37 */     this.__set |= 1;
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   public B mapData(FloatMap paramFloatMap)
/*     */   {
/*  47 */     this.mapData = paramFloatMap;
/*  48 */     this.__set |= 2;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B offsetX(double paramDouble)
/*     */   {
/*  58 */     this.offsetX = paramDouble;
/*  59 */     this.__set |= 4;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B offsetY(double paramDouble)
/*     */   {
/*  69 */     this.offsetY = paramDouble;
/*  70 */     this.__set |= 8;
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B scaleX(double paramDouble)
/*     */   {
/*  80 */     this.scaleX = paramDouble;
/*  81 */     this.__set |= 16;
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B scaleY(double paramDouble)
/*     */   {
/*  91 */     this.scaleY = paramDouble;
/*  92 */     this.__set |= 32;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B wrap(boolean paramBoolean)
/*     */   {
/* 102 */     this.wrap = paramBoolean;
/* 103 */     this.__set |= 64;
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public DisplacementMap build()
/*     */   {
/* 111 */     DisplacementMap localDisplacementMap = new DisplacementMap();
/* 112 */     applyTo(localDisplacementMap);
/* 113 */     return localDisplacementMap;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.DisplacementMapBuilder
 * JD-Core Version:    0.6.2
 */