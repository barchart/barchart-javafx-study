/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class InnerShadowBuilder<B extends InnerShadowBuilder<B>>
/*     */   implements Builder<InnerShadow>
/*     */ {
/*     */   private int __set;
/*     */   private BlurType blurType;
/*     */   private double choke;
/*     */   private Color color;
/*     */   private double height;
/*     */   private Effect input;
/*     */   private double offsetX;
/*     */   private double offsetY;
/*     */   private double radius;
/*     */   private double width;
/*     */ 
/*     */   public static InnerShadowBuilder<?> create()
/*     */   {
/*  15 */     return new InnerShadowBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(InnerShadow paramInnerShadow) {
/*  23 */     int i = this.__set;
/*  24 */     while (i != 0) {
/*  25 */       int j = Integer.numberOfTrailingZeros(i);
/*  26 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  27 */       switch (j) { case 0:
/*  28 */         paramInnerShadow.setBlurType(this.blurType); break;
/*     */       case 1:
/*  29 */         paramInnerShadow.setChoke(this.choke); break;
/*     */       case 2:
/*  30 */         paramInnerShadow.setColor(this.color); break;
/*     */       case 3:
/*  31 */         paramInnerShadow.setHeight(this.height); break;
/*     */       case 4:
/*  32 */         paramInnerShadow.setInput(this.input); break;
/*     */       case 5:
/*  33 */         paramInnerShadow.setOffsetX(this.offsetX); break;
/*     */       case 6:
/*  34 */         paramInnerShadow.setOffsetY(this.offsetY); break;
/*     */       case 7:
/*  35 */         paramInnerShadow.setRadius(this.radius); break;
/*     */       case 8:
/*  36 */         paramInnerShadow.setWidth(this.width);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B blurType(BlurType paramBlurType)
/*     */   {
/*  47 */     this.blurType = paramBlurType;
/*  48 */     __set(0);
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B choke(double paramDouble)
/*     */   {
/*  58 */     this.choke = paramDouble;
/*  59 */     __set(1);
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B color(Color paramColor)
/*     */   {
/*  69 */     this.color = paramColor;
/*  70 */     __set(2);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B height(double paramDouble)
/*     */   {
/*  80 */     this.height = paramDouble;
/*  81 */     __set(3);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B input(Effect paramEffect)
/*     */   {
/*  91 */     this.input = paramEffect;
/*  92 */     __set(4);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B offsetX(double paramDouble)
/*     */   {
/* 102 */     this.offsetX = paramDouble;
/* 103 */     __set(5);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public B offsetY(double paramDouble)
/*     */   {
/* 113 */     this.offsetY = paramDouble;
/* 114 */     __set(6);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public B radius(double paramDouble)
/*     */   {
/* 124 */     this.radius = paramDouble;
/* 125 */     __set(7);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   public B width(double paramDouble)
/*     */   {
/* 135 */     this.width = paramDouble;
/* 136 */     __set(8);
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   public InnerShadow build()
/*     */   {
/* 144 */     InnerShadow localInnerShadow = new InnerShadow();
/* 145 */     applyTo(localInnerShadow);
/* 146 */     return localInnerShadow;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.InnerShadowBuilder
 * JD-Core Version:    0.6.2
 */