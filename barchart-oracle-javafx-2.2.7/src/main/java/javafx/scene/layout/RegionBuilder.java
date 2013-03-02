/*     */ package javafx.scene.layout;
/*     */ 
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.ParentBuilder;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class RegionBuilder<B extends RegionBuilder<B>> extends ParentBuilder<B>
/*     */   implements Builder<Region>
/*     */ {
/*     */   private int __set;
/*     */   private double maxHeight;
/*     */   private double maxWidth;
/*     */   private double minHeight;
/*     */   private double minWidth;
/*     */   private Insets padding;
/*     */   private double prefHeight;
/*     */   private double prefWidth;
/*     */   private boolean snapToPixel;
/*     */ 
/*     */   public static RegionBuilder<?> create()
/*     */   {
/*  15 */     return new RegionBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Region paramRegion) {
/*  23 */     super.applyTo(paramRegion);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramRegion.setMaxHeight(this.maxHeight); break;
/*     */       case 1:
/*  30 */         paramRegion.setMaxWidth(this.maxWidth); break;
/*     */       case 2:
/*  31 */         paramRegion.setMinHeight(this.minHeight); break;
/*     */       case 3:
/*  32 */         paramRegion.setMinWidth(this.minWidth); break;
/*     */       case 4:
/*  33 */         paramRegion.setPadding(this.padding); break;
/*     */       case 5:
/*  34 */         paramRegion.setPrefHeight(this.prefHeight); break;
/*     */       case 6:
/*  35 */         paramRegion.setPrefWidth(this.prefWidth); break;
/*     */       case 7:
/*  36 */         paramRegion.setSnapToPixel(this.snapToPixel);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B maxHeight(double paramDouble)
/*     */   {
/*  47 */     this.maxHeight = paramDouble;
/*  48 */     __set(0);
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B maxWidth(double paramDouble)
/*     */   {
/*  58 */     this.maxWidth = paramDouble;
/*  59 */     __set(1);
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B minHeight(double paramDouble)
/*     */   {
/*  69 */     this.minHeight = paramDouble;
/*  70 */     __set(2);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B minWidth(double paramDouble)
/*     */   {
/*  80 */     this.minWidth = paramDouble;
/*  81 */     __set(3);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B padding(Insets paramInsets)
/*     */   {
/*  91 */     this.padding = paramInsets;
/*  92 */     __set(4);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefHeight(double paramDouble)
/*     */   {
/* 102 */     this.prefHeight = paramDouble;
/* 103 */     __set(5);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefWidth(double paramDouble)
/*     */   {
/* 113 */     this.prefWidth = paramDouble;
/* 114 */     __set(6);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public B snapToPixel(boolean paramBoolean)
/*     */   {
/* 124 */     this.snapToPixel = paramBoolean;
/* 125 */     __set(7);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   public Region build()
/*     */   {
/* 133 */     Region localRegion = new Region();
/* 134 */     applyTo(localRegion);
/* 135 */     return localRegion;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.RegionBuilder
 * JD-Core Version:    0.6.2
 */