/*     */ package com.sun.prism.impl.packrect;
/*     */ 
/*     */ public class Rect
/*     */ {
/*     */   private int x;
/*     */   private int y;
/*     */   private int w;
/*     */   private int h;
/*     */ 
/*     */   public Rect()
/*     */   {
/*  61 */     this(0, 0, 0, 0);
/*     */   }
/*     */ 
/*     */   public Rect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  65 */     setPosition(paramInt1, paramInt2);
/*  66 */     setSize(paramInt3, paramInt4);
/*     */   }
/*     */   public int x() {
/*  69 */     return this.x; } 
/*  70 */   public int y() { return this.y; } 
/*  71 */   public int w() { return this.w; } 
/*  72 */   public int h() { return this.h; }
/*     */ 
/*     */   public void setPosition(int paramInt1, int paramInt2) {
/*  75 */     if (paramInt1 < 0)
/*  76 */       throw new IllegalArgumentException("Negative x");
/*  77 */     if (paramInt2 < 0)
/*  78 */       throw new IllegalArgumentException("Negative y");
/*  79 */     this.x = paramInt1;
/*  80 */     this.y = paramInt2;
/*     */   }
/*     */ 
/*     */   public void setSize(int paramInt1, int paramInt2) throws IllegalArgumentException {
/*  84 */     if (paramInt1 < 0)
/*  85 */       throw new IllegalArgumentException("Negative width");
/*  86 */     if (paramInt2 < 0)
/*  87 */       throw new IllegalArgumentException("Negative height");
/*  88 */     this.w = paramInt1;
/*  89 */     this.h = paramInt2;
/*     */   }
/*     */ 
/*     */   public int maxX()
/*     */   {
/* 100 */     if (w() < 1)
/* 101 */       return -1;
/* 102 */     return x() + w() - 1;
/*     */   }
/*     */ 
/*     */   public int maxY()
/*     */   {
/* 111 */     if (h() < 1)
/* 112 */       return -1;
/* 113 */     return y() + h() - 1;
/*     */   }
/*     */ 
/*     */   public boolean canContain(Rect paramRect) {
/* 117 */     return (w() >= paramRect.w()) && (h() >= paramRect.h());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 122 */     return "[Rect x: " + x() + " y: " + y() + " w: " + w() + " h: " + h() + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.packrect.Rect
 * JD-Core Version:    0.6.2
 */