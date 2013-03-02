/*     */ package com.sun.javafx.scene.paint;
/*     */ 
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.paint.Stop;
/*     */ 
/*     */ public class GradientUtils
/*     */ {
/*     */   public static String lengthToString(double paramDouble, boolean paramBoolean)
/*     */   {
/*  33 */     if (paramBoolean) {
/*  34 */       return paramDouble * 100.0D + "%";
/*     */     }
/*  36 */     return paramDouble + "px";
/*     */   }
/*     */ 
/*     */   public static class Parser
/*     */   {
/*     */     private int index;
/*     */     private String[] tokens;
/*     */     private boolean proportional;
/*  66 */     private boolean proportionalSet = false;
/*     */ 
/*     */     public Parser(String paramString) {
/*  69 */       this.tokens = paramString.split(",");
/*  70 */       this.index = 0;
/*     */     }
/*     */ 
/*     */     public int getSize() {
/*  74 */       return this.tokens.length;
/*     */     }
/*     */ 
/*     */     public void shift() {
/*  78 */       this.index += 1;
/*     */     }
/*     */ 
/*     */     public String getCurrentToken() {
/*  82 */       return this.tokens[this.index].trim();
/*     */     }
/*     */ 
/*     */     public String[] splitCurrentToken() {
/*  86 */       return this.tokens[this.index].trim().split("\\s");
/*     */     }
/*     */ 
/*     */     public static void checkNumberOfArguments(String[] paramArrayOfString, int paramInt) {
/*  90 */       if (paramArrayOfString.length < paramInt + 1)
/*  91 */         throw new IllegalArgumentException("Invalid gradient specification: parameter '" + paramArrayOfString[0] + "' needs " + paramInt + " argument(s).");
/*     */     }
/*     */ 
/*     */     public static double parseAngle(String paramString)
/*     */     {
/*  97 */       double d = 0.0D;
/*  98 */       if (paramString.endsWith("deg")) {
/*  99 */         paramString = paramString.substring(0, paramString.length() - 3);
/* 100 */         d = Double.parseDouble(paramString);
/* 101 */       } else if (paramString.endsWith("grad")) {
/* 102 */         paramString = paramString.substring(0, paramString.length() - 4);
/* 103 */         d = Double.parseDouble(paramString);
/* 104 */         d = d * 9.0D / 10.0D;
/* 105 */       } else if (paramString.endsWith("rad")) {
/* 106 */         paramString = paramString.substring(0, paramString.length() - 3);
/* 107 */         d = Double.parseDouble(paramString);
/* 108 */         d = d * 180.0D / 3.141592653589793D;
/* 109 */       } else if (paramString.endsWith("turn")) {
/* 110 */         paramString = paramString.substring(0, paramString.length() - 4);
/* 111 */         d = Double.parseDouble(paramString);
/* 112 */         d *= 360.0D;
/*     */       } else {
/* 114 */         throw new IllegalArgumentException("Invalid gradient specification:angle must end in deg, rad, grad, or turn");
/*     */       }
/*     */ 
/* 118 */       return d;
/*     */     }
/*     */ 
/*     */     public static double parsePercentage(String paramString)
/*     */     {
/*     */       double d;
/* 123 */       if (paramString.endsWith("%")) {
/* 124 */         paramString = paramString.substring(0, paramString.length() - 1);
/* 125 */         d = Double.parseDouble(paramString) / 100.0D;
/*     */       } else {
/* 127 */         throw new IllegalArgumentException("Invalid gradient specification: focus-distance must be specified as percentage");
/*     */       }
/*     */ 
/* 130 */       return d;
/*     */     }
/*     */ 
/*     */     public GradientUtils.Point parsePoint(String paramString) {
/* 134 */       GradientUtils.Point localPoint = new GradientUtils.Point();
/* 135 */       if (paramString.endsWith("%")) {
/* 136 */         localPoint.proportional = true;
/* 137 */         paramString = paramString.substring(0, paramString.length() - 1);
/* 138 */       } else if (paramString.endsWith("px")) {
/* 139 */         paramString = paramString.substring(0, paramString.length() - 2);
/*     */       }
/* 141 */       localPoint.value = Double.parseDouble(paramString);
/* 142 */       if (localPoint.proportional) {
/* 143 */         localPoint.value /= 100.0D;
/*     */       }
/*     */ 
/* 146 */       if ((this.proportionalSet) && (this.proportional != localPoint.proportional)) {
/* 147 */         throw new IllegalArgumentException("Invalid gradient specification:cannot mix proportional and non-proportional values");
/*     */       }
/*     */ 
/* 151 */       this.proportionalSet = true;
/* 152 */       this.proportional = localPoint.proportional;
/*     */ 
/* 154 */       return localPoint;
/*     */     }
/*     */ 
/*     */     public Stop[] parseStops(boolean paramBoolean, double paramDouble)
/*     */     {
/* 160 */       int i = this.tokens.length - this.index;
/* 161 */       Color[] arrayOfColor = new Color[i];
/* 162 */       double[] arrayOfDouble = new double[i];
/* 163 */       Stop[] arrayOfStop = new Stop[i];
/*     */ 
/* 165 */       for (int j = 0; j < i; j++) {
/* 166 */         String[] arrayOfString = this.tokens[(j + this.index)].trim().split("\\s+");
/* 167 */         String str1 = arrayOfString[0];
/* 168 */         double d2 = -1.0D;
/*     */ 
/* 170 */         Color localColor = Color.web(str1);
/* 171 */         if (arrayOfString.length == 2)
/*     */         {
/* 173 */           String str2 = arrayOfString[1];
/* 174 */           if (str2.endsWith("%")) {
/* 175 */             str2 = str2.substring(0, str2.length() - 1);
/* 176 */             d2 = Double.parseDouble(str2) / 100.0D;
/* 177 */           } else if (!paramBoolean) {
/* 178 */             if (str2.endsWith("px")) {
/* 179 */               str2 = str2.substring(0, str2.length() - 2);
/*     */             }
/* 181 */             d2 = Double.parseDouble(str2) / paramDouble;
/*     */           } else {
/* 183 */             throw new IllegalArgumentException("Invalid gradient specification, non-proportional stops not permited in proportional gradient: " + str2);
/*     */           }
/*     */         }
/* 186 */         else if (arrayOfString.length > 2) {
/* 187 */           throw new IllegalArgumentException("Invalid gradient specification, unexpected content in stop specification: " + arrayOfString[2]);
/*     */         }
/*     */ 
/* 191 */         arrayOfColor[j] = localColor;
/* 192 */         arrayOfDouble[j] = d2;
/*     */       }
/*     */ 
/* 198 */       if (arrayOfDouble[0] < 0.0D) {
/* 199 */         arrayOfDouble[0] = 0.0D;
/*     */       }
/* 201 */       if (arrayOfDouble[(arrayOfDouble.length - 1)] < 0.0D) {
/* 202 */         arrayOfDouble[(arrayOfDouble.length - 1)] = 1.0D;
/*     */       }
/*     */ 
/* 208 */       double d1 = arrayOfDouble[0];
/* 209 */       for (int k = 1; k < arrayOfDouble.length; k++) {
/* 210 */         if ((arrayOfDouble[k] < d1) && (arrayOfDouble[k] > 0.0D))
/* 211 */           arrayOfDouble[k] = d1;
/*     */         else {
/* 213 */           d1 = arrayOfDouble[k];
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 221 */       k = -1;
/* 222 */       for (int m = 1; m < arrayOfDouble.length; m++) {
/* 223 */         double d3 = arrayOfDouble[m];
/* 224 */         if ((d3 < 0.0D) && (k < 0)) {
/* 225 */           k = m;
/* 226 */         } else if ((d3 >= 0.0D) && (k > 0)) {
/* 227 */           int n = m - k + 1;
/* 228 */           double d4 = (arrayOfDouble[m] - arrayOfDouble[(k - 1)]) / n;
/* 229 */           for (int i1 = 0; i1 < n - 1; i1++) {
/* 230 */             arrayOfDouble[(k + i1)] = (arrayOfDouble[(k - 1)] + d4 * (i1 + 1));
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 235 */       for (m = 0; m < arrayOfStop.length; m++) {
/* 236 */         Stop localStop = new Stop(arrayOfDouble[m], arrayOfColor[m]);
/* 237 */         arrayOfStop[m] = localStop;
/*     */       }
/*     */ 
/* 240 */       return arrayOfStop;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Point
/*     */   {
/*  42 */     public static Point MIN = new Point(0.0D, true);
/*  43 */     public static Point MAX = new Point(1.0D, true);
/*     */     public double value;
/*     */     public boolean proportional;
/*     */ 
/*     */     public String toString()
/*     */     {
/*  49 */       return "value = " + this.value + ", proportional = " + this.proportional;
/*     */     }
/*     */ 
/*     */     public Point(double paramDouble, boolean paramBoolean) {
/*  53 */       this.value = paramDouble;
/*  54 */       this.proportional = paramBoolean;
/*     */     }
/*     */ 
/*     */     public Point()
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.paint.GradientUtils
 * JD-Core Version:    0.6.2
 */