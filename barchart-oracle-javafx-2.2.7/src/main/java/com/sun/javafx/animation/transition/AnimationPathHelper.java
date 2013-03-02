/*     */ package com.sun.javafx.animation.transition;
/*     */ 
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.PathIterator;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class AnimationPathHelper
/*     */ {
/*     */   private static final int SMOOTH_ZONE = 10;
/*  43 */   private ArrayList<Segment> segments = new ArrayList();
/*  44 */   private double totalLength = 0.0D;
/*     */ 
/*     */   public AnimationPathHelper(Path2D paramPath2D, BaseTransform paramBaseTransform, double paramDouble)
/*     */   {
/*  64 */     Segment localSegment1 = Segment.getZeroSegment();
/*  65 */     Object localObject = Segment.getZeroSegment();
/*     */ 
/*  67 */     float[] arrayOfFloat = new float[6];
/*  68 */     for (PathIterator localPathIterator = paramPath2D.getPathIterator(paramBaseTransform, (float)paramDouble); !localPathIterator.isDone(); localPathIterator.next()) {
/*  69 */       Segment localSegment2 = null;
/*  70 */       int i = localPathIterator.currentSegment(arrayOfFloat);
/*  71 */       double d1 = arrayOfFloat[0];
/*  72 */       double d2 = arrayOfFloat[1];
/*     */ 
/*  74 */       switch (i) {
/*     */       case 0:
/*  76 */         localSegment1 = Segment.newMoveTo(d1, d2, ((Segment)localObject).accumLength);
/*  77 */         localSegment2 = localSegment1;
/*  78 */         break;
/*     */       case 4:
/*  80 */         localSegment2 = Segment.newClosePath((Segment)localObject, localSegment1);
/*  81 */         if (localSegment2 == null)
/*     */         {
/*  83 */           ((Segment)localObject).convertToClosePath(localSegment1); } break;
/*     */       case 1:
/*  87 */         localSegment2 = Segment.newLineTo((Segment)localObject, d1, d2);
/*     */       case 2:
/*     */       case 3:
/*     */       }
/*  91 */       if (localSegment2 != null) {
/*  92 */         this.segments.add(localSegment2);
/*  93 */         localObject = localSegment2;
/*     */       }
/*     */     }
/*  96 */     this.totalLength = ((Segment)localObject).accumLength;
/*     */   }
/*     */ 
/*     */   public Position2D getPosition2D(double paramDouble, boolean paramBoolean, Position2D paramPosition2D)
/*     */   {
/* 114 */     double d1 = this.totalLength * Math.min(1.0D, Math.max(0.0D, paramDouble));
/* 115 */     int i = findSegment(0, this.segments.size() - 1, d1);
/* 116 */     Segment localSegment1 = (Segment)this.segments.get(i);
/*     */ 
/* 118 */     double d2 = localSegment1.accumLength - localSegment1.length;
/*     */ 
/* 120 */     double d3 = d1 - d2;
/*     */ 
/* 122 */     if (paramPosition2D == null) {
/* 123 */       paramPosition2D = new Position2D();
/*     */     }
/*     */ 
/* 126 */     double d4 = d3 / localSegment1.length;
/* 127 */     Segment localSegment2 = localSegment1.prevSeg;
/* 128 */     paramPosition2D.x = (localSegment2.toX + (localSegment1.toX - localSegment2.toX) * d4);
/* 129 */     paramPosition2D.y = (localSegment2.toY + (localSegment1.toY - localSegment2.toY) * d4);
/* 130 */     paramPosition2D.rotateAngle = localSegment1.rotateAngle;
/*     */ 
/* 133 */     double d5 = Math.min(10.0D, localSegment1.length / 2.0D);
/* 134 */     if ((d3 < d5) && (!localSegment2.isMoveTo))
/*     */     {
/* 136 */       paramPosition2D.rotateAngle = interpolate(localSegment2.rotateAngle, localSegment1.rotateAngle, d3 / d5 / 2.0D + 0.5D);
/*     */     }
/*     */     else
/*     */     {
/* 140 */       double d6 = localSegment1.length - d3;
/* 141 */       Segment localSegment3 = localSegment1.nextSeg;
/* 142 */       if ((d6 < d5) && (localSegment3 != null))
/*     */       {
/* 144 */         if (!localSegment3.isMoveTo) {
/* 145 */           paramPosition2D.rotateAngle = interpolate(localSegment1.rotateAngle, localSegment3.rotateAngle, (d5 - d6) / d5 / 2.0D);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 151 */     return paramPosition2D;
/*     */   }
/*     */ 
/*     */   private static double interpolate(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 158 */     double d = paramDouble2 - paramDouble1;
/* 159 */     if (Math.abs(d) > 180.0D) {
/* 160 */       paramDouble2 += (d > 0.0D ? -360.0D : 360.0D);
/*     */     }
/* 162 */     return normalize(paramDouble1 + paramDouble3 * (paramDouble2 - paramDouble1));
/*     */   }
/*     */ 
/*     */   private static double normalize(double paramDouble)
/*     */   {
/* 168 */     while (paramDouble > 360.0D) {
/* 169 */       paramDouble -= 360.0D;
/*     */     }
/* 171 */     while (paramDouble < 0.0D) {
/* 172 */       paramDouble += 360.0D;
/*     */     }
/* 174 */     return paramDouble;
/*     */   }
/*     */ 
/*     */   private int findSegment(int paramInt1, int paramInt2, double paramDouble)
/*     */   {
/* 183 */     if (paramInt1 == paramInt2)
/*     */     {
/* 185 */       return (((Segment)this.segments.get(paramInt1)).isMoveTo) && (paramInt1 > 0) ? findSegment(paramInt1 - 1, paramInt1 - 1, paramDouble) : paramInt1;
/*     */     }
/*     */ 
/* 190 */     int i = paramInt1 + (paramInt2 - paramInt1) / 2;
/* 191 */     return ((Segment)this.segments.get(i)).accumLength > paramDouble ? findSegment(paramInt1, i, paramDouble) : findSegment(i + 1, paramInt2, paramDouble);
/*     */   }
/*     */   private static class Segment {
/*     */     private static Segment zeroSegment;
/*     */     boolean isMoveTo;
/*     */     double length;
/*     */     double accumLength;
/*     */     double toX;
/*     */     double toY;
/*     */     double rotateAngle;
/*     */     Segment prevSeg;
/*     */     Segment nextSeg;
/*     */ 
/*     */     private Segment(boolean paramBoolean, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5) {
/* 213 */       this.isMoveTo = paramBoolean;
/* 214 */       this.toX = paramDouble1;
/* 215 */       this.toY = paramDouble2;
/* 216 */       this.length = paramDouble3;
/* 217 */       this.accumLength = (paramDouble4 + paramDouble3);
/* 218 */       this.rotateAngle = paramDouble5;
/*     */     }
/*     */ 
/*     */     public static Segment getZeroSegment() {
/* 222 */       if (zeroSegment == null) {
/* 223 */         zeroSegment = new Segment(true, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */       }
/* 225 */       return zeroSegment;
/*     */     }
/*     */ 
/*     */     public static Segment newMoveTo(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */     {
/* 230 */       return new Segment(true, paramDouble1, paramDouble2, 0.0D, paramDouble3, 0.0D);
/*     */     }
/*     */ 
/*     */     public static Segment newLineTo(Segment paramSegment, double paramDouble1, double paramDouble2) {
/* 234 */       double d1 = paramDouble1 - paramSegment.toX;
/* 235 */       double d2 = paramDouble2 - paramSegment.toY;
/* 236 */       double d3 = Math.sqrt(d1 * d1 + d2 * d2);
/* 237 */       if ((d3 >= 1.0D) || (paramSegment.isMoveTo)) {
/* 238 */         double d4 = Math.signum(d2 == 0.0D ? d1 : d2);
/* 239 */         double d5 = d4 * Math.acos(d1 / d3);
/* 240 */         d5 = AnimationPathHelper.normalize(d5 / 3.141592653589793D * 180.0D);
/* 241 */         Segment localSegment = new Segment(false, paramDouble1, paramDouble2, d3, paramSegment.accumLength, d5);
/*     */ 
/* 243 */         paramSegment.nextSeg = localSegment;
/* 244 */         localSegment.prevSeg = paramSegment;
/* 245 */         return localSegment;
/*     */       }
/* 247 */       return null;
/*     */     }
/*     */ 
/*     */     public static Segment newClosePath(Segment paramSegment1, Segment paramSegment2) {
/* 251 */       Segment localSegment = newLineTo(paramSegment1, paramSegment2.toX, paramSegment2.toY);
/* 252 */       if (localSegment != null) {
/* 253 */         localSegment.convertToClosePath(paramSegment2);
/*     */       }
/* 255 */       return localSegment;
/*     */     }
/*     */ 
/*     */     public void convertToClosePath(Segment paramSegment) {
/* 259 */       Segment localSegment = paramSegment.nextSeg;
/* 260 */       this.nextSeg = localSegment;
/* 261 */       localSegment.prevSeg = this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.animation.transition.AnimationPathHelper
 * JD-Core Version:    0.6.2
 */