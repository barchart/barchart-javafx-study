/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class DropShadowBuilder<B extends DropShadowBuilder<B>>
/*     */   implements Builder<DropShadow>
/*     */ {
/*     */   private int __set;
/*     */   private BlurType blurType;
/*     */   private Color color;
/*     */   private double height;
/*     */   private Effect input;
/*     */   private double offsetX;
/*     */   private double offsetY;
/*     */   private double radius;
/*     */   private double spread;
/*     */   private double width;
/*     */ 
/*     */   public static DropShadowBuilder<?> create()
/*     */   {
/*  15 */     return new DropShadowBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(DropShadow paramDropShadow) {
/*  23 */     int i = this.__set;
/*  24 */     while (i != 0) {
/*  25 */       int j = Integer.numberOfTrailingZeros(i);
/*  26 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  27 */       switch (j) { case 0:
/*  28 */         paramDropShadow.setBlurType(this.blurType); break;
/*     */       case 1:
/*  29 */         paramDropShadow.setColor(this.color); break;
/*     */       case 2:
/*  30 */         paramDropShadow.setHeight(this.height); break;
/*     */       case 3:
/*  31 */         paramDropShadow.setInput(this.input); break;
/*     */       case 4:
/*  32 */         paramDropShadow.setOffsetX(this.offsetX); break;
/*     */       case 5:
/*  33 */         paramDropShadow.setOffsetY(this.offsetY); break;
/*     */       case 6:
/*  34 */         paramDropShadow.setRadius(this.radius); break;
/*     */       case 7:
/*  35 */         paramDropShadow.setSpread(this.spread); break;
/*     */       case 8:
/*  36 */         paramDropShadow.setWidth(this.width);
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
/*     */   public B color(Color paramColor)
/*     */   {
/*  58 */     this.color = paramColor;
/*  59 */     __set(1);
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B height(double paramDouble)
/*     */   {
/*  69 */     this.height = paramDouble;
/*  70 */     __set(2);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B input(Effect paramEffect)
/*     */   {
/*  80 */     this.input = paramEffect;
/*  81 */     __set(3);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B offsetX(double paramDouble)
/*     */   {
/*  91 */     this.offsetX = paramDouble;
/*  92 */     __set(4);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B offsetY(double paramDouble)
/*     */   {
/* 102 */     this.offsetY = paramDouble;
/* 103 */     __set(5);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public B radius(double paramDouble)
/*     */   {
/* 113 */     this.radius = paramDouble;
/* 114 */     __set(6);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public B spread(double paramDouble)
/*     */   {
/* 124 */     this.spread = paramDouble;
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
/*     */   public DropShadow build()
/*     */   {
/* 144 */     DropShadow localDropShadow = new DropShadow();
/* 145 */     applyTo(localDropShadow);
/* 146 */     return localDropShadow;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.DropShadowBuilder
 * JD-Core Version:    0.6.2
 */