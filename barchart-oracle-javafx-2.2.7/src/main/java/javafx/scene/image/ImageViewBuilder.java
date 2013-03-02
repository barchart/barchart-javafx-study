/*     */ package javafx.scene.image;
/*     */ 
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.NodeBuilder;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class ImageViewBuilder<B extends ImageViewBuilder<B>> extends NodeBuilder<B>
/*     */   implements Builder<ImageView>
/*     */ {
/*     */   private int __set;
/*     */   private double fitHeight;
/*     */   private double fitWidth;
/*     */   private Image image;
/*     */   private boolean preserveRatio;
/*     */   private boolean smooth;
/*     */   private Rectangle2D viewport;
/*     */   private double x;
/*     */   private double y;
/*     */ 
/*     */   public static ImageViewBuilder<?> create()
/*     */   {
/*  15 */     return new ImageViewBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(ImageView paramImageView) {
/*  23 */     super.applyTo(paramImageView);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramImageView.setFitHeight(this.fitHeight); break;
/*     */       case 1:
/*  30 */         paramImageView.setFitWidth(this.fitWidth); break;
/*     */       case 2:
/*  31 */         paramImageView.setImage(this.image); break;
/*     */       case 3:
/*  32 */         paramImageView.setPreserveRatio(this.preserveRatio); break;
/*     */       case 4:
/*  33 */         paramImageView.setSmooth(this.smooth); break;
/*     */       case 5:
/*  34 */         paramImageView.setViewport(this.viewport); break;
/*     */       case 6:
/*  35 */         paramImageView.setX(this.x); break;
/*     */       case 7:
/*  36 */         paramImageView.setY(this.y);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B fitHeight(double paramDouble)
/*     */   {
/*  47 */     this.fitHeight = paramDouble;
/*  48 */     __set(0);
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B fitWidth(double paramDouble)
/*     */   {
/*  58 */     this.fitWidth = paramDouble;
/*  59 */     __set(1);
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B image(Image paramImage)
/*     */   {
/*  69 */     this.image = paramImage;
/*  70 */     __set(2);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B preserveRatio(boolean paramBoolean)
/*     */   {
/*  80 */     this.preserveRatio = paramBoolean;
/*  81 */     __set(3);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B smooth(boolean paramBoolean)
/*     */   {
/*  91 */     this.smooth = paramBoolean;
/*  92 */     __set(4);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B viewport(Rectangle2D paramRectangle2D)
/*     */   {
/* 102 */     this.viewport = paramRectangle2D;
/* 103 */     __set(5);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public B x(double paramDouble)
/*     */   {
/* 113 */     this.x = paramDouble;
/* 114 */     __set(6);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public B y(double paramDouble)
/*     */   {
/* 124 */     this.y = paramDouble;
/* 125 */     __set(7);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   public ImageView build()
/*     */   {
/* 133 */     ImageView localImageView = new ImageView();
/* 134 */     applyTo(localImageView);
/* 135 */     return localImageView;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.image.ImageViewBuilder
 * JD-Core Version:    0.6.2
 */