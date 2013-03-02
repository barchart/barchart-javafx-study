/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class PerspectiveTransformBuilder<B extends PerspectiveTransformBuilder<B>>
/*     */   implements Builder<PerspectiveTransform>
/*     */ {
/*     */   private int __set;
/*     */   private Effect input;
/*     */   private double llx;
/*     */   private double lly;
/*     */   private double lrx;
/*     */   private double lry;
/*     */   private double ulx;
/*     */   private double uly;
/*     */   private double urx;
/*     */   private double ury;
/*     */ 
/*     */   public static PerspectiveTransformBuilder<?> create()
/*     */   {
/*  15 */     return new PerspectiveTransformBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(PerspectiveTransform paramPerspectiveTransform) {
/*  23 */     int i = this.__set;
/*  24 */     while (i != 0) {
/*  25 */       int j = Integer.numberOfTrailingZeros(i);
/*  26 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  27 */       switch (j) { case 0:
/*  28 */         paramPerspectiveTransform.setInput(this.input); break;
/*     */       case 1:
/*  29 */         paramPerspectiveTransform.setLlx(this.llx); break;
/*     */       case 2:
/*  30 */         paramPerspectiveTransform.setLly(this.lly); break;
/*     */       case 3:
/*  31 */         paramPerspectiveTransform.setLrx(this.lrx); break;
/*     */       case 4:
/*  32 */         paramPerspectiveTransform.setLry(this.lry); break;
/*     */       case 5:
/*  33 */         paramPerspectiveTransform.setUlx(this.ulx); break;
/*     */       case 6:
/*  34 */         paramPerspectiveTransform.setUly(this.uly); break;
/*     */       case 7:
/*  35 */         paramPerspectiveTransform.setUrx(this.urx); break;
/*     */       case 8:
/*  36 */         paramPerspectiveTransform.setUry(this.ury);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B input(Effect paramEffect)
/*     */   {
/*  47 */     this.input = paramEffect;
/*  48 */     __set(0);
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B llx(double paramDouble)
/*     */   {
/*  58 */     this.llx = paramDouble;
/*  59 */     __set(1);
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B lly(double paramDouble)
/*     */   {
/*  69 */     this.lly = paramDouble;
/*  70 */     __set(2);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B lrx(double paramDouble)
/*     */   {
/*  80 */     this.lrx = paramDouble;
/*  81 */     __set(3);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B lry(double paramDouble)
/*     */   {
/*  91 */     this.lry = paramDouble;
/*  92 */     __set(4);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B ulx(double paramDouble)
/*     */   {
/* 102 */     this.ulx = paramDouble;
/* 103 */     __set(5);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public B uly(double paramDouble)
/*     */   {
/* 113 */     this.uly = paramDouble;
/* 114 */     __set(6);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public B urx(double paramDouble)
/*     */   {
/* 124 */     this.urx = paramDouble;
/* 125 */     __set(7);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   public B ury(double paramDouble)
/*     */   {
/* 135 */     this.ury = paramDouble;
/* 136 */     __set(8);
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   public PerspectiveTransform build()
/*     */   {
/* 144 */     PerspectiveTransform localPerspectiveTransform = new PerspectiveTransform();
/* 145 */     applyTo(localPerspectiveTransform);
/* 146 */     return localPerspectiveTransform;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.PerspectiveTransformBuilder
 * JD-Core Version:    0.6.2
 */