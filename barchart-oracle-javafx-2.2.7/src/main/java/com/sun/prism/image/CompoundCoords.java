/*     */ package com.sun.prism.image;
/*     */ 
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ 
/*     */ public class CompoundCoords
/*     */ {
/*     */   private int xImg0;
/*     */   private int xImg1;
/*     */   private int yImg0;
/*     */   private int yImg1;
/*     */   private Coords[] tileCoords;
/*     */ 
/*     */   public CompoundCoords(CompoundImage paramCompoundImage, Coords paramCoords)
/*     */   {
/*  20 */     int i = find1(fastFloor(paramCoords.u0), paramCompoundImage.uSubdivision);
/*  21 */     int j = find2(fastCeil(paramCoords.u1), paramCompoundImage.uSubdivision);
/*  22 */     int k = find1(fastFloor(paramCoords.v0), paramCompoundImage.vSubdivision);
/*  23 */     int m = find2(fastCeil(paramCoords.v1), paramCompoundImage.vSubdivision);
/*     */ 
/*  26 */     if ((i < 0) || (j < 0) || (k < 0) || (m < 0)) return;
/*     */ 
/*  28 */     this.xImg0 = i; this.xImg1 = j;
/*  29 */     this.yImg0 = k; this.yImg1 = m;
/*  30 */     this.tileCoords = new Coords[(j - i + 1) * (m - k + 1)];
/*     */ 
/*  32 */     float[] arrayOfFloat1 = new float[j - i];
/*  33 */     float[] arrayOfFloat2 = new float[m - k];
/*     */ 
/*  35 */     for (int n = i; n < j; n++) {
/*  36 */       arrayOfFloat1[(n - i)] = paramCoords.getX(paramCompoundImage.uSubdivision[(n + 1)]);
/*     */     }
/*  38 */     for (n = k; n < m; n++) {
/*  39 */       arrayOfFloat2[(n - k)] = paramCoords.getY(paramCompoundImage.vSubdivision[(n + 1)]);
/*     */     }
/*     */ 
/*  42 */     n = 0;
/*  43 */     for (int i1 = k; i1 <= m; i1++) {
/*  44 */       float f1 = (i1 == k ? paramCoords.v0 : paramCompoundImage.vSubdivision[i1]) - paramCompoundImage.v0[i1];
/*  45 */       float f2 = (i1 == m ? paramCoords.v1 : paramCompoundImage.vSubdivision[(i1 + 1)]) - paramCompoundImage.v0[i1];
/*  46 */       float f3 = i1 == k ? paramCoords.y0 : arrayOfFloat2[(i1 - k - 1)];
/*  47 */       float f4 = i1 == m ? paramCoords.y1 : arrayOfFloat2[(i1 - k)];
/*     */ 
/*  49 */       for (int i2 = i; i2 <= j; i2++) {
/*  50 */         Coords localCoords = new Coords();
/*  51 */         localCoords.v0 = f1;
/*  52 */         localCoords.v1 = f2;
/*  53 */         localCoords.y0 = f3;
/*  54 */         localCoords.y1 = f4;
/*     */ 
/*  56 */         localCoords.u0 = ((i2 == i ? paramCoords.u0 : paramCompoundImage.uSubdivision[i2]) - paramCompoundImage.u0[i2]);
/*  57 */         localCoords.u1 = ((i2 == j ? paramCoords.u1 : paramCompoundImage.uSubdivision[(i2 + 1)]) - paramCompoundImage.u0[i2]);
/*  58 */         localCoords.x0 = (i2 == i ? paramCoords.x0 : arrayOfFloat1[(i2 - i - 1)]);
/*  59 */         localCoords.x1 = (i2 == j ? paramCoords.x1 : arrayOfFloat1[(i2 - i)]);
/*     */ 
/*  61 */         this.tileCoords[(n++)] = localCoords;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void draw(Graphics paramGraphics, CompoundImage paramCompoundImage, float paramFloat1, float paramFloat2) {
/*  67 */     if (this.tileCoords == null) return;
/*     */ 
/*  69 */     ResourceFactory localResourceFactory = paramGraphics.getResourceFactory();
/*     */ 
/*  71 */     int i = 0;
/*  72 */     for (int j = this.yImg0; j <= this.yImg1; j++)
/*  73 */       for (int k = this.xImg0; k <= this.xImg1; k++)
/*  74 */         this.tileCoords[(i++)].draw(paramCompoundImage.getTile(k, j, localResourceFactory), paramGraphics, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   private static int find1(int paramInt, int[] paramArrayOfInt)
/*     */   {
/*  83 */     for (int i = 0; i < paramArrayOfInt.length - 1; i++) {
/*  84 */       if ((paramArrayOfInt[i] <= paramInt) && (paramInt < paramArrayOfInt[(i + 1)])) {
/*  85 */         return i;
/*     */       }
/*     */     }
/*  88 */     return -1;
/*     */   }
/*     */ 
/*     */   private static int find2(int paramInt, int[] paramArrayOfInt)
/*     */   {
/*  95 */     for (int i = 0; i < paramArrayOfInt.length - 1; i++) {
/*  96 */       if ((paramArrayOfInt[i] < paramInt) && (paramInt <= paramArrayOfInt[(i + 1)])) {
/*  97 */         return i;
/*     */       }
/*     */     }
/* 100 */     return -1;
/*     */   }
/*     */ 
/*     */   private static int fastFloor(float paramFloat) {
/* 104 */     int i = (int)paramFloat;
/* 105 */     return i <= paramFloat ? i : i - 1;
/*     */   }
/*     */ 
/*     */   private static int fastCeil(float paramFloat) {
/* 109 */     int i = (int)paramFloat;
/* 110 */     return i >= paramFloat ? i : i + 1;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.image.CompoundCoords
 * JD-Core Version:    0.6.2
 */