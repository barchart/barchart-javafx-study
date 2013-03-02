/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ class ArcIterator
/*     */   implements PathIterator
/*     */ {
/*     */   double x;
/*     */   double y;
/*     */   double w;
/*     */   double h;
/*     */   double angStRad;
/*     */   double increment;
/*     */   double cv;
/*     */   BaseTransform transform;
/*     */   int index;
/*     */   int arcSegs;
/*     */   int lineSegs;
/*     */ 
/*     */   ArcIterator(Arc2D paramArc2D, BaseTransform paramBaseTransform)
/*     */   {
/*  47 */     this.w = (paramArc2D.width / 2.0F);
/*  48 */     this.h = (paramArc2D.height / 2.0F);
/*  49 */     this.x = (paramArc2D.x + this.w);
/*  50 */     this.y = (paramArc2D.y + this.h);
/*  51 */     this.angStRad = (-Math.toRadians(paramArc2D.start));
/*  52 */     this.transform = paramBaseTransform;
/*  53 */     double d = -paramArc2D.extent;
/*  54 */     if ((d >= 360.0D) || (d <= -360.0D)) {
/*  55 */       this.arcSegs = 4;
/*  56 */       this.increment = 1.570796326794897D;
/*     */ 
/*  58 */       this.cv = 0.5522847498307933D;
/*  59 */       if (d < 0.0D) {
/*  60 */         this.increment = (-this.increment);
/*  61 */         this.cv = (-this.cv);
/*     */       }
/*     */     } else {
/*  64 */       this.arcSegs = ((int)Math.ceil(Math.abs(d) / 90.0D));
/*  65 */       this.increment = Math.toRadians(d / this.arcSegs);
/*  66 */       this.cv = btan(this.increment);
/*  67 */       if (this.cv == 0.0D) {
/*  68 */         this.arcSegs = 0;
/*     */       }
/*     */     }
/*  71 */     switch (paramArc2D.getArcType()) {
/*     */     case 0:
/*  73 */       this.lineSegs = 0;
/*  74 */       break;
/*     */     case 1:
/*  76 */       this.lineSegs = 1;
/*  77 */       break;
/*     */     case 2:
/*  79 */       this.lineSegs = 2;
/*     */     }
/*     */ 
/*  82 */     if ((this.w < 0.0D) || (this.h < 0.0D))
/*  83 */       this.arcSegs = (this.lineSegs = -1);
/*     */   }
/*     */ 
/*     */   public int getWindingRule()
/*     */   {
/*  94 */     return 1;
/*     */   }
/*     */ 
/*     */   public boolean isDone()
/*     */   {
/* 102 */     return this.index > this.arcSegs + this.lineSegs;
/*     */   }
/*     */ 
/*     */   public void next()
/*     */   {
/* 111 */     this.index += 1;
/*     */   }
/*     */ 
/*     */   private static double btan(double paramDouble)
/*     */   {
/* 192 */     paramDouble /= 2.0D;
/* 193 */     return 1.333333333333333D * Math.sin(paramDouble) / (1.0D + Math.cos(paramDouble));
/*     */   }
/*     */ 
/*     */   public int currentSegment(float[] paramArrayOfFloat)
/*     */   {
/* 215 */     if (isDone()) {
/* 216 */       throw new NoSuchElementException("arc iterator out of bounds");
/*     */     }
/* 218 */     double d1 = this.angStRad;
/* 219 */     if (this.index == 0) {
/* 220 */       paramArrayOfFloat[0] = ((float)(this.x + Math.cos(d1) * this.w));
/* 221 */       paramArrayOfFloat[1] = ((float)(this.y + Math.sin(d1) * this.h));
/* 222 */       if (this.transform != null) {
/* 223 */         this.transform.transform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, 1);
/*     */       }
/* 225 */       return 0;
/*     */     }
/* 227 */     if (this.index > this.arcSegs) {
/* 228 */       if (this.index == this.arcSegs + this.lineSegs) {
/* 229 */         return 4;
/*     */       }
/* 231 */       paramArrayOfFloat[0] = ((float)this.x);
/* 232 */       paramArrayOfFloat[1] = ((float)this.y);
/* 233 */       if (this.transform != null) {
/* 234 */         this.transform.transform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, 1);
/*     */       }
/* 236 */       return 1;
/*     */     }
/* 238 */     d1 += this.increment * (this.index - 1);
/* 239 */     double d2 = Math.cos(d1);
/* 240 */     double d3 = Math.sin(d1);
/* 241 */     paramArrayOfFloat[0] = ((float)(this.x + (d2 - this.cv * d3) * this.w));
/* 242 */     paramArrayOfFloat[1] = ((float)(this.y + (d3 + this.cv * d2) * this.h));
/* 243 */     d1 += this.increment;
/* 244 */     d2 = Math.cos(d1);
/* 245 */     d3 = Math.sin(d1);
/* 246 */     paramArrayOfFloat[2] = ((float)(this.x + (d2 + this.cv * d3) * this.w));
/* 247 */     paramArrayOfFloat[3] = ((float)(this.y + (d3 - this.cv * d2) * this.h));
/* 248 */     paramArrayOfFloat[4] = ((float)(this.x + d2 * this.w));
/* 249 */     paramArrayOfFloat[5] = ((float)(this.y + d3 * this.h));
/* 250 */     if (this.transform != null) {
/* 251 */       this.transform.transform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, 3);
/*     */     }
/* 253 */     return 3;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.ArcIterator
 * JD-Core Version:    0.6.2
 */