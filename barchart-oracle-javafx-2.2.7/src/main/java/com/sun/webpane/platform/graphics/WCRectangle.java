/*     */ package com.sun.webpane.platform.graphics;
/*     */ 
/*     */ public class WCRectangle
/*     */ {
/*     */   float x;
/*     */   float y;
/*     */   float w;
/*     */   float h;
/*     */ 
/*     */   public WCRectangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  13 */     this.x = paramFloat1;
/*  14 */     this.y = paramFloat2;
/*  15 */     this.w = paramFloat3;
/*  16 */     this.h = paramFloat4;
/*     */   }
/*     */ 
/*     */   public WCRectangle(WCRectangle paramWCRectangle) {
/*  20 */     this.x = paramWCRectangle.x;
/*  21 */     this.y = paramWCRectangle.y;
/*  22 */     this.w = paramWCRectangle.w;
/*  23 */     this.h = paramWCRectangle.h;
/*     */   }
/*     */ 
/*     */   public WCRectangle() {
/*     */   }
/*     */ 
/*     */   public float getX() {
/*  30 */     return this.x;
/*     */   }
/*     */ 
/*     */   public int getIntX() {
/*  34 */     return (int)this.x;
/*     */   }
/*     */ 
/*     */   public float getY() {
/*  38 */     return this.y;
/*     */   }
/*     */ 
/*     */   public int getIntY() {
/*  42 */     return (int)this.y;
/*     */   }
/*     */ 
/*     */   public float getWidth() {
/*  46 */     return this.w;
/*     */   }
/*     */ 
/*     */   public int getIntWidth() {
/*  50 */     return (int)this.w;
/*     */   }
/*     */ 
/*     */   public float getHeight() {
/*  54 */     return this.h;
/*     */   }
/*     */ 
/*     */   public int getIntHeight() {
/*  58 */     return (int)this.h;
/*     */   }
/*     */ 
/*     */   public boolean contains(WCRectangle paramWCRectangle) {
/*  62 */     return (this.x <= paramWCRectangle.x) && (this.x + this.w >= paramWCRectangle.x + paramWCRectangle.w) && (this.y <= paramWCRectangle.y) && (this.y + this.h >= paramWCRectangle.y + paramWCRectangle.h);
/*     */   }
/*     */ 
/*     */   public WCRectangle intersection(WCRectangle paramWCRectangle)
/*     */   {
/*  76 */     float f1 = this.x;
/*  77 */     float f2 = this.y;
/*  78 */     float f3 = paramWCRectangle.x;
/*  79 */     float f4 = paramWCRectangle.y;
/*  80 */     float f5 = f1; f5 += this.w;
/*  81 */     float f6 = f2; f6 += this.h;
/*  82 */     float f7 = f3; f7 += paramWCRectangle.w;
/*  83 */     float f8 = f4; f8 += paramWCRectangle.h;
/*  84 */     if (f1 < f3) f1 = f3;
/*  85 */     if (f2 < f4) f2 = f4;
/*  86 */     if (f5 > f7) f5 = f7;
/*  87 */     if (f6 > f8) f6 = f8;
/*  88 */     f5 -= f1;
/*  89 */     f6 -= f2;
/*     */ 
/*  93 */     if (f5 < 1.4E-45F) f5 = 1.4E-45F;
/*  94 */     if (f6 < 1.4E-45F) f6 = 1.4E-45F;
/*  95 */     return new WCRectangle(f1, f2, f5, f6);
/*     */   }
/*     */ 
/*     */   public void translate(float paramFloat1, float paramFloat2)
/*     */   {
/* 108 */     float f1 = this.x;
/* 109 */     float f2 = f1 + paramFloat1;
/* 110 */     if (paramFloat1 < 0.0F)
/*     */     {
/* 112 */       if (f2 > f1)
/*     */       {
/* 115 */         if (this.w >= 0.0F)
/*     */         {
/* 123 */           this.w += f2 - 1.4E-45F;
/*     */         }
/*     */ 
/* 129 */         f2 = 1.4E-45F;
/*     */       }
/*     */ 
/*     */     }
/* 133 */     else if (f2 < f1)
/*     */     {
/* 135 */       if (this.w >= 0.0F)
/*     */       {
/* 138 */         this.w += f2 - 3.4028235E+38F;
/*     */ 
/* 141 */         if (this.w < 0.0F) this.w = 3.4028235E+38F;
/*     */       }
/* 143 */       f2 = 3.4028235E+38F;
/*     */     }
/*     */ 
/* 146 */     this.x = f2;
/*     */ 
/* 148 */     f1 = this.y;
/* 149 */     f2 = f1 + paramFloat2;
/* 150 */     if (paramFloat2 < 0.0F)
/*     */     {
/* 152 */       if (f2 > f1)
/*     */       {
/* 154 */         if (this.h >= 0.0F) {
/* 155 */           this.h += f2 - 1.4E-45F;
/*     */         }
/*     */ 
/* 158 */         f2 = 1.4E-45F;
/*     */       }
/*     */ 
/*     */     }
/* 162 */     else if (f2 < f1)
/*     */     {
/* 164 */       if (this.h >= 0.0F) {
/* 165 */         this.h += f2 - 3.4028235E+38F;
/* 166 */         if (this.h < 0.0F) this.h = 3.4028235E+38F;
/*     */       }
/* 168 */       f2 = 3.4028235E+38F;
/*     */     }
/*     */ 
/* 171 */     this.y = f2;
/*     */   }
/*     */ 
/*     */   public WCRectangle createUnion(WCRectangle paramWCRectangle) {
/* 175 */     WCRectangle localWCRectangle = new WCRectangle();
/* 176 */     union(this, paramWCRectangle, localWCRectangle);
/* 177 */     return localWCRectangle;
/*     */   }
/*     */ 
/*     */   public static void union(WCRectangle paramWCRectangle1, WCRectangle paramWCRectangle2, WCRectangle paramWCRectangle3)
/*     */   {
/* 184 */     float f1 = Math.min(paramWCRectangle1.getMinX(), paramWCRectangle2.getMinX());
/* 185 */     float f2 = Math.min(paramWCRectangle1.getMinY(), paramWCRectangle2.getMinY());
/* 186 */     float f3 = Math.max(paramWCRectangle1.getMaxX(), paramWCRectangle2.getMaxX());
/* 187 */     float f4 = Math.max(paramWCRectangle1.getMaxY(), paramWCRectangle2.getMaxY());
/* 188 */     paramWCRectangle3.setFrameFromDiagonal(f1, f2, f3, f4);
/*     */   }
/*     */ 
/*     */   public void setFrameFromDiagonal(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*     */     float f;
/* 192 */     if (paramFloat3 < paramFloat1) {
/* 193 */       f = paramFloat1;
/* 194 */       paramFloat1 = paramFloat3;
/* 195 */       paramFloat3 = f;
/*     */     }
/* 197 */     if (paramFloat4 < paramFloat2) {
/* 198 */       f = paramFloat2;
/* 199 */       paramFloat2 = paramFloat4;
/* 200 */       paramFloat4 = f;
/*     */     }
/* 202 */     setFrame(paramFloat1, paramFloat2, paramFloat3 - paramFloat1, paramFloat4 - paramFloat2);
/*     */   }
/*     */ 
/*     */   public void setFrame(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 206 */     this.x = paramFloat1;
/* 207 */     this.y = paramFloat2;
/* 208 */     this.w = paramFloat3;
/* 209 */     this.h = paramFloat4;
/*     */   }
/*     */ 
/*     */   public float getMinX() {
/* 213 */     return getX();
/*     */   }
/*     */ 
/*     */   public float getMaxX() {
/* 217 */     return getX() + getWidth();
/*     */   }
/*     */ 
/*     */   public float getMinY() {
/* 221 */     return getY();
/*     */   }
/*     */ 
/*     */   public float getMaxY() {
/* 225 */     return getY() + getHeight();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/* 229 */     return (this.w <= 0.0F) || (this.h <= 0.0F);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 234 */     if ((paramObject instanceof WCRectangle)) {
/* 235 */       WCRectangle localWCRectangle = (WCRectangle)paramObject;
/* 236 */       return (this.x == localWCRectangle.x) && (this.y == localWCRectangle.y) && (this.w == localWCRectangle.w) && (this.h == localWCRectangle.h);
/*     */     }
/* 238 */     return super.equals(paramObject);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 243 */     return "WCRectangle{x:" + this.x + " y:" + this.y + " w:" + this.w + " h:" + this.h + "}";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCRectangle
 * JD-Core Version:    0.6.2
 */