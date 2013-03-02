/*     */ package com.sun.prism.image;
/*     */ 
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ 
/*     */ public abstract class CompoundImage
/*     */ {
/*     */   public static final int BORDER_SIZE_DEFAULT = 1;
/*     */   protected final int[] uSubdivision;
/*     */   protected final int[] u0;
/*     */   protected final int[] u1;
/*     */   protected final int[] vSubdivision;
/*     */   protected final int[] v0;
/*     */   protected final int[] v1;
/*     */   protected final int uSections;
/*     */   protected final int vSections;
/*     */   protected final int uBorderSize;
/*     */   protected final int vBorderSize;
/*     */   protected Image[] tiles;
/*     */ 
/*     */   public CompoundImage(Image paramImage, int paramInt)
/*     */   {
/*  33 */     this(paramImage, paramInt, 1);
/*     */   }
/*     */ 
/*     */   public CompoundImage(Image paramImage, int paramInt1, int paramInt2)
/*     */   {
/*  38 */     if (4 * paramInt2 >= paramInt1) paramInt2 = paramInt1 / 4;
/*     */ 
/*  40 */     int i = paramImage.getWidth();
/*  41 */     int j = paramImage.getHeight();
/*     */ 
/*  43 */     this.uBorderSize = (i <= paramInt1 ? 0 : paramInt2);
/*  44 */     this.vBorderSize = (j <= paramInt1 ? 0 : paramInt2);
/*     */ 
/*  46 */     this.uSubdivision = subdivideUVs(i, paramInt1, this.uBorderSize);
/*  47 */     this.vSubdivision = subdivideUVs(j, paramInt1, this.vBorderSize);
/*     */ 
/*  49 */     this.uSections = (this.uSubdivision.length - 1);
/*  50 */     this.vSections = (this.vSubdivision.length - 1);
/*     */ 
/*  52 */     this.u0 = new int[this.uSections]; this.u1 = new int[this.uSections];
/*  53 */     this.v0 = new int[this.vSections]; this.v1 = new int[this.vSections];
/*     */ 
/*  56 */     this.tiles = new Image[this.uSections * this.vSections];
/*     */ 
/*  58 */     for (int k = 0; k != this.vSections; k++) {
/*  59 */       this.v0[k] = (this.vSubdivision[k] - uBorder(k));
/*  60 */       this.v1[k] = (this.vSubdivision[(k + 1)] + dBorder(k));
/*     */     }
/*     */ 
/*  63 */     for (k = 0; k != this.uSections; k++) {
/*  64 */       this.u0[k] = (this.uSubdivision[k] - lBorder(k));
/*  65 */       this.u1[k] = (this.uSubdivision[(k + 1)] + rBorder(k));
/*     */     }
/*     */ 
/*  68 */     for (k = 0; k != this.vSections; k++)
/*  69 */       for (int m = 0; m != this.uSections; m++)
/*     */       {
/*  71 */         this.tiles[(k * this.uSections + m)] = paramImage.createSubImage(this.u0[m], this.v0[k], this.u1[m] - this.u0[m], this.v1[k] - this.v0[k]);
/*     */       }
/*     */   }
/*     */ 
/*     */   private int lBorder(int paramInt)
/*     */   {
/*  77 */     return paramInt > 0 ? this.uBorderSize : 0; } 
/*  78 */   private int rBorder(int paramInt) { return paramInt < this.uSections - 1 ? this.uBorderSize : 0; } 
/*  79 */   private int uBorder(int paramInt) { return paramInt > 0 ? this.vBorderSize : 0; } 
/*  80 */   private int dBorder(int paramInt) { return paramInt < this.vSections - 1 ? this.vBorderSize : 0; }
/*     */ 
/*     */ 
/*     */   private static int[] subdivideUVs(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  86 */     int i = paramInt2 - paramInt3 * 2;
/*  87 */     int j = (paramInt1 - paramInt3 * 2 + i - 1) / i;
/*     */ 
/*  89 */     int[] arrayOfInt = new int[j + 1];
/*     */ 
/*  91 */     arrayOfInt[0] = 0;
/*  92 */     arrayOfInt[j] = paramInt1;
/*     */ 
/*  94 */     for (int k = 1; k < j; k++) {
/*  95 */       arrayOfInt[k] = (paramInt3 + i * k);
/*     */     }
/*     */ 
/*  98 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   protected abstract Texture getTile(int paramInt1, int paramInt2, ResourceFactory paramResourceFactory);
/*     */ 
/*     */   public void drawLazy(Graphics paramGraphics, Coords paramCoords, float paramFloat1, float paramFloat2) {
/* 104 */     new CompoundCoords(this, paramCoords).draw(paramGraphics, this, paramFloat1, paramFloat2);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.image.CompoundImage
 * JD-Core Version:    0.6.2
 */