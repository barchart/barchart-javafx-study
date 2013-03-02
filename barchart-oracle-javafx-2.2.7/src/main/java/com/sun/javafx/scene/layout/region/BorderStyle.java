/*     */ package com.sun.javafx.scene.layout.region;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javafx.scene.shape.StrokeLineCap;
/*     */ import javafx.scene.shape.StrokeLineJoin;
/*     */ import javafx.scene.shape.StrokeType;
/*     */ 
/*     */ public class BorderStyle
/*     */ {
/*  37 */   public static final BorderStyle NONE = new BorderStyle();
/*     */ 
/*  40 */   public static final BorderStyle SOLID = new BorderStyle(StrokeType.CENTERED, StrokeLineJoin.MITER, StrokeLineCap.BUTT, Double.valueOf(10.0D), Double.valueOf(0.0D), new double[0]);
/*     */   private final StrokeType strokeType;
/*     */   private final StrokeLineJoin strokeLineJoin;
/*     */   private final StrokeLineCap strokeLineCap;
/*     */   private final double strokeMiterLimit;
/*     */   private final double strokeDashOffset;
/*     */   private final double[] strokeDashArray;
/* 141 */   boolean adjusted = false;
/*     */ 
/*     */   public StrokeType getStrokeType()
/*     */   {
/*  56 */     return this.strokeType;
/*     */   }
/*     */ 
/*     */   public StrokeLineJoin getStrokeLineJoin()
/*     */   {
/*  67 */     return this.strokeLineJoin;
/*     */   }
/*     */ 
/*     */   public StrokeLineCap getStrokeLineCap()
/*     */   {
/*  78 */     return this.strokeLineCap;
/*     */   }
/*     */ 
/*     */   public double getStrokeMiterLimit()
/*     */   {
/*  86 */     return this.strokeMiterLimit;
/*     */   }
/*     */ 
/*     */   public double getStrokeDashOffset()
/*     */   {
/*  97 */     return this.strokeDashOffset;
/*     */   }
/*     */ 
/*     */   public double[] getStrokeDashArray()
/*     */   {
/* 112 */     return this.strokeDashArray;
/*     */   }
/*     */ 
/*     */   public BorderStyle(StrokeType paramStrokeType, StrokeLineJoin paramStrokeLineJoin, StrokeLineCap paramStrokeLineCap, Double paramDouble1, Double paramDouble2, double[] paramArrayOfDouble)
/*     */   {
/* 118 */     this.strokeType = (paramStrokeType != null ? paramStrokeType : StrokeType.CENTERED);
/*     */ 
/* 120 */     this.strokeLineJoin = (paramStrokeLineJoin != null ? paramStrokeLineJoin : StrokeLineJoin.MITER);
/*     */ 
/* 122 */     this.strokeLineCap = (paramStrokeLineCap != null ? paramStrokeLineCap : StrokeLineCap.BUTT);
/*     */ 
/* 124 */     this.strokeMiterLimit = (paramDouble1 != null ? paramDouble1.doubleValue() : 10.0D);
/*     */ 
/* 126 */     this.strokeDashOffset = (paramDouble2 != null ? paramDouble2.doubleValue() : 0.0D);
/*     */ 
/* 128 */     this.strokeDashArray = (paramArrayOfDouble != null ? paramArrayOfDouble : null);
/*     */   }
/*     */ 
/*     */   private BorderStyle()
/*     */   {
/* 133 */     this.strokeType = StrokeType.CENTERED;
/* 134 */     this.strokeLineJoin = StrokeLineJoin.MITER;
/* 135 */     this.strokeLineCap = StrokeLineCap.BUTT;
/* 136 */     this.strokeMiterLimit = 10.0D;
/* 137 */     this.strokeDashOffset = 0.0D;
/* 138 */     this.strokeDashArray = null;
/*     */   }
/*     */ 
/*     */   void adjustForStrokeWidth(double paramDouble)
/*     */   {
/* 143 */     if ((paramDouble > 1.0D) && (!this.adjusted)) {
/* 144 */       if ((this.strokeDashArray != null) && (this.strokeDashArray.length > 0)) {
/* 145 */         double d = paramDouble - 1.0D;
/* 146 */         for (int i = 0; i < this.strokeDashArray.length; i++)
/* 147 */           this.strokeDashArray[i] *= d;
/*     */       }
/* 149 */       this.adjusted = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 155 */     String str = "BorderStyle.NONE";
/* 156 */     if (this == SOLID) {
/* 157 */       str = "BorderStyle.SOLID";
/* 158 */     } else if (this != NONE) {
/* 159 */       StringBuilder localStringBuilder = new StringBuilder();
/* 160 */       localStringBuilder.append("BorderStyle: ");
/* 161 */       localStringBuilder.append(this.strokeType);
/* 162 */       localStringBuilder.append(", ");
/* 163 */       localStringBuilder.append(this.strokeLineJoin);
/* 164 */       localStringBuilder.append(", ");
/* 165 */       localStringBuilder.append(this.strokeLineCap);
/* 166 */       localStringBuilder.append(", ");
/* 167 */       localStringBuilder.append(this.strokeMiterLimit);
/* 168 */       localStringBuilder.append(", ");
/* 169 */       localStringBuilder.append(this.strokeDashOffset);
/* 170 */       localStringBuilder.append(", [");
/* 171 */       if (this.strokeDashArray != null) {
/* 172 */         for (int i = 0; i < this.strokeDashArray.length - 1; i++) {
/* 173 */           localStringBuilder.append(this.strokeDashArray[i]);
/* 174 */           localStringBuilder.append(", ");
/*     */         }
/* 176 */         if (this.strokeDashArray.length > 0) {
/* 177 */           localStringBuilder.append(this.strokeDashArray[(this.strokeDashArray.length - 1)]);
/*     */         }
/*     */       }
/* 180 */       localStringBuilder.append("]");
/* 181 */       str = localStringBuilder.toString();
/*     */     }
/* 183 */     return str;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 189 */     if (this == paramObject) return true;
/* 190 */     if ((paramObject instanceof BorderStyle)) {
/* 191 */       BorderStyle localBorderStyle = (BorderStyle)paramObject;
/* 192 */       if ((this.strokeType == localBorderStyle.strokeType) && (this.strokeLineJoin == localBorderStyle.strokeLineJoin) && (this.strokeLineCap == localBorderStyle.strokeLineCap) && (this.strokeMiterLimit == localBorderStyle.strokeMiterLimit) && (this.strokeDashOffset == localBorderStyle.strokeDashOffset))
/*     */       {
/* 198 */         if ((this.strokeDashArray != null) && (localBorderStyle.strokeDashArray != null) && (this.strokeDashArray.length == localBorderStyle.strokeDashArray.length))
/*     */         {
/* 201 */           return Arrays.equals(this.strokeDashArray, localBorderStyle.strokeDashArray);
/*     */         }
/* 203 */         return (this.strokeDashArray == null) && (localBorderStyle.strokeDashArray == null);
/*     */       }
/*     */     }
/*     */ 
/* 207 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.layout.region.BorderStyle
 * JD-Core Version:    0.6.2
 */