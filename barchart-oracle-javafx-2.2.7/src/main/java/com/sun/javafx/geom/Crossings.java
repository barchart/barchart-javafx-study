/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public abstract class Crossings
/*     */ {
/*     */   public static final boolean debug = false;
/*  34 */   int limit = 0;
/*  35 */   double[] yranges = new double[10];
/*     */   double xlo;
/*     */   double ylo;
/*     */   double xhi;
/*     */   double yhi;
/*     */ 
/*     */   public Crossings(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  40 */     this.xlo = paramDouble1;
/*  41 */     this.ylo = paramDouble2;
/*  42 */     this.xhi = paramDouble3;
/*  43 */     this.yhi = paramDouble4;
/*     */   }
/*     */ 
/*     */   public final double getXLo() {
/*  47 */     return this.xlo;
/*     */   }
/*     */ 
/*     */   public final double getYLo() {
/*  51 */     return this.ylo;
/*     */   }
/*     */ 
/*     */   public final double getXHi() {
/*  55 */     return this.xhi;
/*     */   }
/*     */ 
/*     */   public final double getYHi() {
/*  59 */     return this.yhi;
/*     */   }
/*     */ 
/*     */   public abstract void record(double paramDouble1, double paramDouble2, int paramInt);
/*     */ 
/*     */   public void print() {
/*  65 */     System.out.println("Crossings [");
/*  66 */     System.out.println("  bounds = [" + this.ylo + ", " + this.yhi + "]");
/*  67 */     for (int i = 0; i < this.limit; i += 2) {
/*  68 */       System.out.println("  [" + this.yranges[i] + ", " + this.yranges[(i + 1)] + "]");
/*     */     }
/*  70 */     System.out.println("]");
/*     */   }
/*     */ 
/*     */   public final boolean isEmpty() {
/*  74 */     return this.limit == 0;
/*     */   }
/*     */ 
/*     */   public abstract boolean covers(double paramDouble1, double paramDouble2);
/*     */ 
/*     */   public static Crossings findCrossings(Vector paramVector, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  83 */     EvenOdd localEvenOdd = new EvenOdd(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*  84 */     Enumeration localEnumeration = paramVector.elements();
/*  85 */     while (localEnumeration.hasMoreElements()) {
/*  86 */       Curve localCurve = (Curve)localEnumeration.nextElement();
/*  87 */       if (localCurve.accumulateCrossings(localEvenOdd)) {
/*  88 */         return null;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  94 */     return localEvenOdd;
/*     */   }
/*     */ 
/*     */   public static final class EvenOdd extends Crossings {
/*     */     public EvenOdd(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  99 */       super(paramDouble2, paramDouble3, paramDouble4);
/*     */     }
/*     */ 
/*     */     public final boolean covers(double paramDouble1, double paramDouble2) {
/* 103 */       return (this.limit == 2) && (this.yranges[0] <= paramDouble1) && (this.yranges[1] >= paramDouble2);
/*     */     }
/*     */ 
/*     */     public void record(double paramDouble1, double paramDouble2, int paramInt) {
/* 107 */       if (paramDouble1 >= paramDouble2) {
/* 108 */         return;
/*     */       }
/* 110 */       int i = 0;
/*     */ 
/* 112 */       while ((i < this.limit) && (paramDouble1 > this.yranges[(i + 1)])) {
/* 113 */         i += 2;
/*     */       }
/* 115 */       int j = i;
/* 116 */       while (i < this.limit) {
/* 117 */         double d1 = this.yranges[(i++)];
/* 118 */         double d2 = this.yranges[(i++)];
/* 119 */         if (paramDouble2 < d1)
/*     */         {
/* 121 */           this.yranges[(j++)] = paramDouble1;
/* 122 */           this.yranges[(j++)] = paramDouble2;
/* 123 */           paramDouble1 = d1;
/* 124 */           paramDouble2 = d2;
/*     */         }
/*     */         else
/*     */         {
/*     */           double d3;
/*     */           double d4;
/* 129 */           if (paramDouble1 < d1) {
/* 130 */             d3 = paramDouble1;
/* 131 */             d4 = d1;
/*     */           } else {
/* 133 */             d3 = d1;
/* 134 */             d4 = paramDouble1;
/*     */           }
/*     */           double d5;
/*     */           double d6;
/* 136 */           if (paramDouble2 < d2) {
/* 137 */             d5 = paramDouble2;
/* 138 */             d6 = d2;
/*     */           } else {
/* 140 */             d5 = d2;
/* 141 */             d6 = paramDouble2;
/*     */           }
/* 143 */           if (d4 == d5) {
/* 144 */             paramDouble1 = d3;
/* 145 */             paramDouble2 = d6;
/*     */           } else {
/* 147 */             if (d4 > d5) {
/* 148 */               paramDouble1 = d5;
/* 149 */               d5 = d4;
/* 150 */               d4 = paramDouble1;
/*     */             }
/* 152 */             if (d3 != d4) {
/* 153 */               this.yranges[(j++)] = d3;
/* 154 */               this.yranges[(j++)] = d4;
/*     */             }
/* 156 */             paramDouble1 = d5;
/* 157 */             paramDouble2 = d6;
/*     */           }
/* 159 */           if (paramDouble1 >= paramDouble2)
/*     */             break;
/*     */         }
/*     */       }
/* 163 */       if ((j < i) && (i < this.limit)) {
/* 164 */         System.arraycopy(this.yranges, i, this.yranges, j, this.limit - i);
/*     */       }
/* 166 */       j += this.limit - i;
/* 167 */       if (paramDouble1 < paramDouble2) {
/* 168 */         if (j >= this.yranges.length) {
/* 169 */           double[] arrayOfDouble = new double[j + 10];
/* 170 */           System.arraycopy(this.yranges, 0, arrayOfDouble, 0, j);
/* 171 */           this.yranges = arrayOfDouble;
/*     */         }
/* 173 */         this.yranges[(j++)] = paramDouble1;
/* 174 */         this.yranges[(j++)] = paramDouble2;
/*     */       }
/* 176 */       this.limit = j;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Crossings
 * JD-Core Version:    0.6.2
 */