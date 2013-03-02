/*     */ package com.sun.javafx.scene.layout.region;
/*     */ 
/*     */ import javafx.geometry.Insets;
/*     */ 
/*     */ public abstract class Border
/*     */ {
/*     */   private final double leftWidth;
/*     */   private final double topWidth;
/*     */   private final double rightWidth;
/*     */   private final double bottomWidth;
/*     */   private final boolean proportionalWidth;
/*     */   private final Insets offsets;
/*     */ 
/*     */   public double getLeftWidth()
/*     */   {
/*  59 */     return this.leftWidth;
/*     */   }
/*     */ 
/*     */   public double getTopWidth()
/*     */   {
/*  69 */     return this.topWidth;
/*     */   }
/*     */ 
/*     */   public double getRightWidth()
/*     */   {
/*  79 */     return this.rightWidth;
/*     */   }
/*     */ 
/*     */   public double getBottomWidth()
/*     */   {
/*  89 */     return this.bottomWidth;
/*     */   }
/*     */ 
/*     */   public boolean isProportionalWidth()
/*     */   {
/*  99 */     return this.proportionalWidth;
/*     */   }
/*     */ 
/*     */   public Insets getOffsets()
/*     */   {
/* 107 */     return this.offsets;
/*     */   }
/*     */ 
/*     */   protected Border(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean, Insets paramInsets)
/*     */   {
/* 113 */     this.topWidth = paramDouble1;
/* 114 */     this.rightWidth = paramDouble2;
/* 115 */     this.bottomWidth = paramDouble3;
/* 116 */     this.leftWidth = paramDouble4;
/* 117 */     this.proportionalWidth = paramBoolean;
/* 118 */     this.offsets = paramInsets;
/*     */   }
/*     */ 
/*     */   protected static class Builder
/*     */   {
/*  36 */     protected double leftWidth = 1.0D;
/*  37 */     protected double topWidth = 1.0D;
/*  38 */     protected double rightWidth = 1.0D;
/*  39 */     protected double bottomWidth = 1.0D;
/*  40 */     protected boolean proportionalWidth = false;
/*  41 */     protected Insets offsets = Insets.EMPTY;
/*     */ 
/*  43 */     public Builder setLeftWidth(double paramDouble) { this.leftWidth = paramDouble; return this; } 
/*  44 */     public Builder setTopWidth(double paramDouble) { this.topWidth = paramDouble; return this; } 
/*  45 */     public Builder setRightWidth(double paramDouble) { this.rightWidth = paramDouble; return this; } 
/*  46 */     public Builder setBottomWidth(double paramDouble) { this.bottomWidth = paramDouble; return this; } 
/*  47 */     public Builder setProportionalWidth(boolean paramBoolean) { this.proportionalWidth = paramBoolean; return this; } 
/*  48 */     public Builder setOffsets(Insets paramInsets) { this.offsets = paramInsets; return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.layout.region.Border
 * JD-Core Version:    0.6.2
 */