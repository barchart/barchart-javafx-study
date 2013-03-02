/*     */ package com.sun.prism.impl.packrect;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class LevelSet
/*     */ {
/*  50 */   private List<Level> levels = new ArrayList(150);
/*     */   private static final int MIN_HEIGHT = 8;
/*     */   private static final int ROUND_UP = 4;
/*  53 */   private int recentUsedLevelIndex = 0;
/*     */   private int nextAddY;
/*     */   private int w;
/*     */   private int h;
/*     */ 
/*     */   public LevelSet(int paramInt1, int paramInt2)
/*     */   {
/*  61 */     this.w = paramInt1;
/*  62 */     this.h = paramInt2;
/*     */   }
/*     */   public int w() {
/*  65 */     return this.w; } 
/*  66 */   public int h() { return this.h; }
/*     */ 
/*     */ 
/*     */   public boolean add(Rect paramRect)
/*     */   {
/*  71 */     if (paramRect.w() > this.w) {
/*  72 */       return false;
/*     */     }
/*  74 */     int i = 8 > paramRect.h() ? 8 : paramRect.h();
/*     */ 
/*  77 */     i = i + 4 - 1 - (i - 1) % 4;
/*     */     int j;
/*  82 */     if ((this.recentUsedLevelIndex < this.levels.size()) && (((Level)this.levels.get(this.recentUsedLevelIndex)).h() != i))
/*     */     {
/*  84 */       j = binarySearch(this.levels, i);
/*     */     }
/*  86 */     else j = this.recentUsedLevelIndex;
/*     */ 
/*  90 */     int k = this.nextAddY + i <= this.h ? 1 : 0;
/*     */ 
/*  94 */     for (int m = j; m < this.levels.size(); m++) {
/*  95 */       Level localLevel2 = (Level)this.levels.get(m);
/*     */ 
/*  98 */       if ((localLevel2.h() > i + 8) && (k != 0))
/*     */         break;
/* 100 */       if (localLevel2.add(paramRect)) {
/* 101 */         this.recentUsedLevelIndex = m;
/* 102 */         return true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 107 */     if (k == 0) {
/* 108 */       return false;
/*     */     }
/*     */ 
/* 111 */     Level localLevel1 = new Level(this.w, i, this.nextAddY, this);
/* 112 */     this.nextAddY += i;
/*     */ 
/* 116 */     if ((j < this.levels.size()) && (((Level)this.levels.get(j)).h() <= i)) {
/* 117 */       this.levels.add(j + 1, localLevel1);
/* 118 */       this.recentUsedLevelIndex = (j + 1);
/*     */     } else {
/* 120 */       this.levels.add(j, localLevel1);
/* 121 */       this.recentUsedLevelIndex = j;
/*     */     }
/* 123 */     return localLevel1.add(paramRect);
/*     */   }
/*     */ 
/*     */   public int getUsedHeight()
/*     */   {
/* 128 */     return this.nextAddY;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 133 */     this.levels.clear();
/* 134 */     this.nextAddY = 0;
/* 135 */     this.recentUsedLevelIndex = 0;
/*     */   }
/*     */ 
/*     */   private static int binarySearch(List<Level> paramList, int paramInt)
/*     */   {
/* 146 */     int i = paramInt + 1;
/* 147 */     int j = 0; int k = paramList.size() - 1;
/* 148 */     int m = 0;
/* 149 */     int n = 0;
/*     */ 
/* 151 */     if (k < 0) {
/* 152 */       return 0;
/*     */     }
/*     */ 
/* 155 */     while (j <= k) {
/* 156 */       m = (j + k) / 2;
/* 157 */       n = ((Level)paramList.get(m)).h();
/* 158 */       if (i < n)
/* 159 */         k = m - 1;
/*     */       else {
/* 161 */         j = m + 1;
/*     */       }
/*     */     }
/*     */ 
/* 165 */     if (n < paramInt)
/* 166 */       return m + 1;
/* 167 */     if (n > paramInt) {
/* 168 */       return m > 0 ? m - 1 : 0;
/*     */     }
/* 170 */     return m;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.packrect.LevelSet
 * JD-Core Version:    0.6.2
 */