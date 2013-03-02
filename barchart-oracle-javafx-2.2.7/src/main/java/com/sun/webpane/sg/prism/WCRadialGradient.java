/*     */ package com.sun.webpane.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.paint.RadialGradient;
/*     */ import com.sun.prism.paint.Stop;
/*     */ import com.sun.webpane.platform.graphics.WCGradient;
/*     */ import com.sun.webpane.platform.graphics.WCPoint;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ 
/*     */ final class WCRadialGradient extends WCGradient<RadialGradient>
/*     */ {
/*  20 */   static final Comparator<Stop> COMPARATOR = new Comparator() {
/*     */     public int compare(Stop paramAnonymousStop1, Stop paramAnonymousStop2) {
/*  22 */       float f1 = paramAnonymousStop1.getOffset();
/*  23 */       float f2 = paramAnonymousStop2.getOffset();
/*  24 */       if (f1 < f2) {
/*  25 */         return -1;
/*     */       }
/*  27 */       if (f1 > f2) {
/*  28 */         return 1;
/*     */       }
/*  30 */       return 0;
/*     */     }
/*  20 */   };
/*     */   private final boolean reverse;
/*     */   private final WCPoint p1;
/*     */   private final WCPoint p2;
/*     */   private final float r1over;
/*     */   private final float r1;
/*     */   private final float r2;
/*  40 */   private final List<Stop> stops = new ArrayList();
/*     */ 
/*     */   WCRadialGradient(WCPoint paramWCPoint1, float paramFloat1, WCPoint paramWCPoint2, float paramFloat2) {
/*  43 */     this.reverse = (paramFloat1 < paramFloat2);
/*  44 */     this.p1 = (this.reverse ? paramWCPoint2 : paramWCPoint1);
/*  45 */     this.p2 = (this.reverse ? paramWCPoint1 : paramWCPoint2);
/*  46 */     this.r1 = (this.reverse ? paramFloat2 : paramFloat1);
/*  47 */     this.r2 = (this.reverse ? paramFloat1 : paramFloat2);
/*  48 */     this.r1over = (this.r1 > 0.0F ? 1.0F / this.r1 : 0.0F);
/*     */   }
/*     */ 
/*     */   public void addStop(int paramInt, float paramFloat)
/*     */   {
/*  55 */     if (this.reverse) {
/*  56 */       paramFloat = 1.0F - paramFloat;
/*     */     }
/*  58 */     paramFloat = 1.0F - paramFloat + paramFloat * this.r2 * this.r1over;
/*  59 */     this.stops.add(new Stop(WCGraphicsPrismContext.createColor(paramInt), paramFloat));
/*     */   }
/*     */ 
/*     */   public RadialGradient getPlatformGradient() {
/*  63 */     Collections.sort(this.stops, COMPARATOR);
/*  64 */     float f1 = this.p2.getX() - this.p1.getX();
/*  65 */     float f2 = this.p2.getY() - this.p1.getY();
/*  66 */     return new RadialGradient(this.p1.getX(), this.p1.getY(), (float)(Math.atan2(f2, f1) * 180.0D / 3.141592653589793D), (float)Math.sqrt(f1 * f1 + f2 * f2) * this.r1over, this.r1, BaseTransform.IDENTITY_TRANSFORM, isProportional(), getSpreadMethod() - 1, this.stops);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  80 */     return toString(this, this.p1, this.p2, Float.valueOf(this.r1), this.stops);
/*     */   }
/*     */ 
/*     */   static String toString(WCGradient paramWCGradient, WCPoint paramWCPoint1, WCPoint paramWCPoint2, Float paramFloat, List<Stop> paramList) {
/*  84 */     StringBuilder localStringBuilder = new StringBuilder(paramWCGradient.getClass().getSimpleName());
/*  85 */     switch (paramWCGradient.getSpreadMethod()) {
/*     */     case 1:
/*  87 */       localStringBuilder.append("[spreadMethod=PAD");
/*  88 */       break;
/*     */     case 2:
/*  90 */       localStringBuilder.append("[spreadMethod=REFLECT");
/*  91 */       break;
/*     */     case 3:
/*  93 */       localStringBuilder.append("[spreadMethod=REPEAT");
/*     */     }
/*     */ 
/*  96 */     localStringBuilder.append(", proportional=").append(paramWCGradient.isProportional());
/*  97 */     if (paramFloat != null) {
/*  98 */       localStringBuilder.append(", radius=").append(paramFloat);
/*     */     }
/* 100 */     localStringBuilder.append(", x1=").append(paramWCPoint1.getX());
/* 101 */     localStringBuilder.append(", y1=").append(paramWCPoint1.getY());
/* 102 */     localStringBuilder.append(", x2=").append(paramWCPoint2.getX());
/* 103 */     localStringBuilder.append(", y2=").append(paramWCPoint2.getY());
/* 104 */     localStringBuilder.append(", stops=");
/* 105 */     for (int i = 0; i < paramList.size(); i++) {
/* 106 */       localStringBuilder.append(i == 0 ? "[" : ", ");
/* 107 */       localStringBuilder.append(((Stop)paramList.get(i)).getOffset()).append(":").append(((Stop)paramList.get(i)).getColor());
/*     */     }
/* 109 */     return localStringBuilder.append("]]").toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCRadialGradient
 * JD-Core Version:    0.6.2
 */