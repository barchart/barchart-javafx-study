/*     */ package com.sun.webpane.platform.graphics;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public abstract class WCStroke<P, S>
/*     */ {
/*     */   public static final int NO_STROKE = 0;
/*     */   public static final int SOLID_STROKE = 1;
/*     */   public static final int DOTTED_STROKE = 2;
/*     */   public static final int DASHED_STROKE = 3;
/*     */   public static final int BUTT_CAP = 0;
/*     */   public static final int ROUND_CAP = 1;
/*     */   public static final int SQUARE_CAP = 2;
/*     */   public static final int MITER_JOIN = 0;
/*     */   public static final int ROUND_JOIN = 1;
/*     */   public static final int BEVEL_JOIN = 2;
/*  32 */   private int style = 1;
/*  33 */   private int lineCap = 0;
/*  34 */   private int lineJoin = 0;
/*  35 */   private float miterLimit = 10.0F;
/*  36 */   private float thickness = 1.0F;
/*     */   private float offset;
/*     */   private float[] sizes;
/*     */   private P paint;
/*     */ 
/*     */   protected abstract void invalidate();
/*     */ 
/*     */   public abstract S getPlatformStroke();
/*     */ 
/*     */   public void copyFrom(WCStroke<P, S> paramWCStroke)
/*     */   {
/*  46 */     this.style = paramWCStroke.style;
/*  47 */     this.lineCap = paramWCStroke.lineCap;
/*  48 */     this.lineJoin = paramWCStroke.lineJoin;
/*  49 */     this.miterLimit = paramWCStroke.miterLimit;
/*  50 */     this.thickness = paramWCStroke.thickness;
/*  51 */     this.offset = paramWCStroke.offset;
/*  52 */     this.sizes = paramWCStroke.sizes;
/*  53 */     this.paint = paramWCStroke.paint;
/*     */   }
/*     */ 
/*     */   public void setStyle(int paramInt) {
/*  57 */     if ((paramInt != 1) && (paramInt != 2) && (paramInt != 3)) {
/*  58 */       paramInt = 0;
/*     */     }
/*  60 */     if (this.style != paramInt) {
/*  61 */       this.style = paramInt;
/*  62 */       invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setLineCap(int paramInt) {
/*  67 */     if ((paramInt != 1) && (paramInt != 2)) {
/*  68 */       paramInt = 0;
/*     */     }
/*  70 */     if (this.lineCap != paramInt) {
/*  71 */       this.lineCap = paramInt;
/*  72 */       invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setLineJoin(int paramInt) {
/*  77 */     if ((paramInt != 1) && (paramInt != 2)) {
/*  78 */       paramInt = 0;
/*     */     }
/*  80 */     if (this.lineJoin != paramInt) {
/*  81 */       this.lineJoin = paramInt;
/*  82 */       invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setMiterLimit(float paramFloat) {
/*  87 */     if (paramFloat < 1.0F) {
/*  88 */       paramFloat = 1.0F;
/*     */     }
/*  90 */     if (this.miterLimit != paramFloat) {
/*  91 */       this.miterLimit = paramFloat;
/*  92 */       invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setThickness(float paramFloat) {
/*  97 */     if (paramFloat < 0.0F) {
/*  98 */       paramFloat = 1.0F;
/*     */     }
/* 100 */     if (this.thickness != paramFloat) {
/* 101 */       this.thickness = paramFloat;
/* 102 */       invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDashOffset(float paramFloat) {
/* 107 */     if (this.offset != paramFloat) {
/* 108 */       this.offset = paramFloat;
/* 109 */       invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDashSizes(float[] paramArrayOfFloat) {
/* 114 */     if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length == 0)) {
/* 115 */       if (this.sizes != null) {
/* 116 */         this.sizes = null;
/* 117 */         invalidate();
/*     */       }
/*     */     }
/* 120 */     else if (!Arrays.equals(this.sizes, paramArrayOfFloat)) {
/* 121 */       this.sizes = ((float[])paramArrayOfFloat.clone());
/* 122 */       invalidate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setPaint(P paramP) {
/* 127 */     this.paint = paramP;
/*     */   }
/*     */ 
/*     */   public int getStyle() {
/* 131 */     return this.style;
/*     */   }
/*     */ 
/*     */   public int getLineCap() {
/* 135 */     return this.lineCap;
/*     */   }
/*     */ 
/*     */   public int getLineJoin() {
/* 139 */     return this.lineJoin;
/*     */   }
/*     */ 
/*     */   public float getMiterLimit() {
/* 143 */     return this.miterLimit;
/*     */   }
/*     */ 
/*     */   public float getThickness() {
/* 147 */     return this.thickness;
/*     */   }
/*     */ 
/*     */   public float getDashOffset() {
/* 151 */     return this.offset;
/*     */   }
/*     */ 
/*     */   public float[] getDashSizes() {
/* 155 */     return this.sizes != null ? (float[])this.sizes.clone() : null;
/*     */   }
/*     */ 
/*     */   public P getPaint()
/*     */   {
/* 161 */     return this.paint;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 166 */     StringBuilder localStringBuilder = new StringBuilder(getClass().getSimpleName());
/* 167 */     localStringBuilder.append("[style=").append(this.style);
/* 168 */     localStringBuilder.append(", lineCap=").append(this.lineCap);
/* 169 */     localStringBuilder.append(", lineJoin=").append(this.lineJoin);
/* 170 */     localStringBuilder.append(", miterLimit=").append(this.miterLimit);
/* 171 */     localStringBuilder.append(", thickness=").append(this.thickness);
/* 172 */     localStringBuilder.append(", offset=").append(this.offset);
/* 173 */     localStringBuilder.append(", sizes=").append(Arrays.toString(this.sizes));
/* 174 */     localStringBuilder.append(", paint=").append(this.paint);
/* 175 */     return "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCStroke
 * JD-Core Version:    0.6.2
 */