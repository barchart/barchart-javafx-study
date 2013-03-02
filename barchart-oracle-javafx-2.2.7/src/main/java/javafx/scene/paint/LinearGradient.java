/*     */ package javafx.scene.paint;
/*     */ 
/*     */ import com.sun.javafx.scene.paint.GradientUtils;
/*     */ import com.sun.javafx.scene.paint.GradientUtils.Parser;
/*     */ import com.sun.javafx.scene.paint.GradientUtils.Point;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class LinearGradient extends Paint
/*     */ {
/*     */   private double startX;
/*     */   private double startY;
/*     */   private double endX;
/*     */   private double endY;
/*     */   private boolean proportional;
/*     */   private CycleMethod cycleMethod;
/*     */   private List<Stop> stops;
/*     */   private Object platformPaint;
/*     */   private int hash;
/*     */ 
/*     */   public final double getStartX()
/*     */   {
/*  87 */     return this.startX;
/*     */   }
/*     */ 
/*     */   public final double getStartY()
/*     */   {
/* 101 */     return this.startY;
/*     */   }
/*     */ 
/*     */   public final double getEndX()
/*     */   {
/* 115 */     return this.endX;
/*     */   }
/*     */ 
/*     */   public final double getEndY()
/*     */   {
/* 129 */     return this.endY;
/*     */   }
/*     */ 
/*     */   public final boolean isProportional()
/*     */   {
/* 145 */     return this.proportional;
/*     */   }
/*     */ 
/*     */   public final CycleMethod getCycleMethod()
/*     */   {
/* 158 */     return this.cycleMethod;
/*     */   }
/*     */ 
/*     */   public final List<Stop> getStops()
/*     */   {
/* 179 */     return this.stops;
/*     */   }
/*     */ 
/*     */   public LinearGradient(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean, CycleMethod paramCycleMethod, Stop[] paramArrayOfStop)
/*     */   {
/* 212 */     this.startX = paramDouble1;
/* 213 */     this.startY = paramDouble2;
/* 214 */     this.endX = paramDouble3;
/* 215 */     this.endY = paramDouble4;
/* 216 */     this.proportional = paramBoolean;
/* 217 */     this.cycleMethod = (paramCycleMethod == null ? CycleMethod.NO_CYCLE : paramCycleMethod);
/* 218 */     this.stops = Stop.normalize(paramArrayOfStop);
/*     */   }
/*     */ 
/*     */   public LinearGradient(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean, CycleMethod paramCycleMethod, List<Stop> paramList)
/*     */   {
/* 240 */     this.startX = paramDouble1;
/* 241 */     this.startY = paramDouble2;
/* 242 */     this.endX = paramDouble3;
/* 243 */     this.endY = paramDouble4;
/* 244 */     this.proportional = paramBoolean;
/* 245 */     this.cycleMethod = (paramCycleMethod == null ? CycleMethod.NO_CYCLE : paramCycleMethod);
/* 246 */     this.stops = Stop.normalize(paramList);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Object impl_getPlatformPaint()
/*     */   {
/* 255 */     if (this.platformPaint == null) {
/* 256 */       this.platformPaint = Toolkit.getToolkit().getPaint(this);
/*     */     }
/* 258 */     return this.platformPaint;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 267 */     if (paramObject == null) return false;
/* 268 */     if (paramObject == this) return true;
/* 269 */     if ((paramObject instanceof LinearGradient)) {
/* 270 */       LinearGradient localLinearGradient = (LinearGradient)paramObject;
/* 271 */       if ((this.startX != localLinearGradient.startX) || (this.startY != localLinearGradient.startY) || (this.endX != localLinearGradient.endX) || (this.endY != localLinearGradient.endY) || (this.proportional != localLinearGradient.proportional) || (this.cycleMethod != localLinearGradient.cycleMethod))
/*     */       {
/* 276 */         return false;
/* 277 */       }if (!this.stops.equals(localLinearGradient.stops)) return false;
/* 278 */       return true;
/* 279 */     }return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 287 */     if (this.hash == 0) {
/* 288 */       long l = 17L;
/* 289 */       l = 37L * l + Double.doubleToLongBits(this.startX);
/* 290 */       l = 37L * l + Double.doubleToLongBits(this.startY);
/* 291 */       l = 37L * l + Double.doubleToLongBits(this.endX);
/* 292 */       l = 37L * l + Double.doubleToLongBits(this.endY);
/* 293 */       l = 37L * l + (this.proportional ? 1231L : 1237L);
/* 294 */       l = 37L * l + this.cycleMethod.hashCode();
/* 295 */       for (Stop localStop : this.stops) {
/* 296 */         l = 37L * l + localStop.hashCode();
/*     */       }
/* 298 */       this.hash = ((int)(l ^ l >> 32));
/*     */     }
/* 300 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 308 */     StringBuilder localStringBuilder = new StringBuilder("linear-gradient(from ").append(GradientUtils.lengthToString(this.startX, this.proportional)).append(" ").append(GradientUtils.lengthToString(this.startY, this.proportional)).append(" to ").append(GradientUtils.lengthToString(this.endX, this.proportional)).append(" ").append(GradientUtils.lengthToString(this.endY, this.proportional)).append(", ");
/*     */ 
/* 315 */     switch (1.$SwitchMap$javafx$scene$paint$CycleMethod[this.cycleMethod.ordinal()]) {
/*     */     case 1:
/* 317 */       localStringBuilder.append("reflect").append(", ");
/* 318 */       break;
/*     */     case 2:
/* 320 */       localStringBuilder.append("repeat").append(", ");
/*     */     }
/*     */ 
/* 324 */     for (Stop localStop : this.stops) {
/* 325 */       localStringBuilder.append(localStop).append(", ");
/*     */     }
/*     */ 
/* 328 */     localStringBuilder.delete(localStringBuilder.length() - 2, localStringBuilder.length());
/* 329 */     localStringBuilder.append(")");
/*     */ 
/* 331 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public static LinearGradient valueOf(String paramString)
/*     */   {
/* 374 */     if (paramString == null) {
/* 375 */       throw new NullPointerException("gradient must be specified");
/*     */     }
/*     */ 
/* 378 */     String str1 = "linear-gradient(";
/* 379 */     String str2 = ")";
/* 380 */     if (paramString.startsWith(str1)) {
/* 381 */       if (!paramString.endsWith(str2)) {
/* 382 */         throw new IllegalArgumentException(new StringBuilder().append("Invalid gradient specification, must end with \"").append(str2).append('"').toString());
/*     */       }
/*     */ 
/* 385 */       paramString = paramString.substring(str1.length(), paramString.length() - str2.length());
/*     */     }
/*     */ 
/* 388 */     GradientUtils.Parser localParser = new GradientUtils.Parser(paramString);
/* 389 */     if (localParser.getSize() < 2) {
/* 390 */       throw new IllegalArgumentException("Invalid gradient specification");
/*     */     }
/*     */ 
/* 393 */     GradientUtils.Point localPoint1 = GradientUtils.Point.MIN;
/* 394 */     GradientUtils.Point localPoint2 = GradientUtils.Point.MIN;
/* 395 */     GradientUtils.Point localPoint3 = GradientUtils.Point.MIN;
/* 396 */     GradientUtils.Point localPoint4 = GradientUtils.Point.MIN;
/*     */ 
/* 398 */     String[] arrayOfString = localParser.splitCurrentToken();
/* 399 */     if ("from".equals(arrayOfString[0])) {
/* 400 */       GradientUtils.Parser.checkNumberOfArguments(arrayOfString, 5);
/* 401 */       localPoint1 = localParser.parsePoint(arrayOfString[1]);
/* 402 */       localPoint2 = localParser.parsePoint(arrayOfString[2]);
/* 403 */       if (!"to".equals(arrayOfString[3])) {
/* 404 */         throw new IllegalArgumentException("Invalid gradient specification, \"to\" expected");
/*     */       }
/* 406 */       localPoint3 = localParser.parsePoint(arrayOfString[4]);
/* 407 */       localPoint4 = localParser.parsePoint(arrayOfString[5]);
/* 408 */       localParser.shift();
/* 409 */     } else if ("to".equals(arrayOfString[0])) {
/* 410 */       int i = 0;
/* 411 */       int j = 0;
/*     */ 
/* 413 */       for (int k = 1; (k < 3) && (k < arrayOfString.length); k++) {
/* 414 */         if ("left".equals(arrayOfString[k])) {
/* 415 */           localPoint1 = GradientUtils.Point.MAX;
/* 416 */           localPoint3 = GradientUtils.Point.MIN;
/* 417 */           i++;
/* 418 */         } else if ("right".equals(arrayOfString[k])) {
/* 419 */           localPoint1 = GradientUtils.Point.MIN;
/* 420 */           localPoint3 = GradientUtils.Point.MAX;
/* 421 */           i++;
/* 422 */         } else if ("top".equals(arrayOfString[k])) {
/* 423 */           localPoint2 = GradientUtils.Point.MAX;
/* 424 */           localPoint4 = GradientUtils.Point.MIN;
/* 425 */           j++;
/* 426 */         } else if ("bottom".equals(arrayOfString[k])) {
/* 427 */           localPoint2 = GradientUtils.Point.MIN;
/* 428 */           localPoint4 = GradientUtils.Point.MAX;
/* 429 */           j++;
/*     */         } else {
/* 431 */           throw new IllegalArgumentException("Invalid gradient specification, unknown value after 'to'");
/*     */         }
/*     */       }
/*     */ 
/* 435 */       if (j > 1) {
/* 436 */         throw new IllegalArgumentException("Invalid gradient specification, vertical direction set twice after 'to'");
/*     */       }
/*     */ 
/* 439 */       if (i > 1) {
/* 440 */         throw new IllegalArgumentException("Invalid gradient specification, horizontal direction set twice after 'to'");
/*     */       }
/*     */ 
/* 443 */       localParser.shift();
/*     */     }
/*     */     else {
/* 446 */       localPoint2 = GradientUtils.Point.MIN;
/* 447 */       localPoint4 = GradientUtils.Point.MAX;
/*     */     }
/*     */ 
/* 451 */     CycleMethod localCycleMethod = CycleMethod.NO_CYCLE;
/* 452 */     String str3 = localParser.getCurrentToken();
/* 453 */     if ("repeat".equals(str3)) {
/* 454 */       localCycleMethod = CycleMethod.REPEAT;
/* 455 */       localParser.shift();
/* 456 */     } else if ("reflect".equals(str3)) {
/* 457 */       localCycleMethod = CycleMethod.REFLECT;
/* 458 */       localParser.shift();
/*     */     }
/*     */ 
/* 461 */     double d1 = 0.0D;
/* 462 */     if (!localPoint1.proportional) {
/* 463 */       double d2 = localPoint3.value - localPoint1.value;
/* 464 */       double d3 = localPoint4.value - localPoint2.value;
/* 465 */       d1 = Math.sqrt(d2 * d2 + d3 * d3);
/*     */     }
/*     */ 
/* 468 */     Stop[] arrayOfStop = localParser.parseStops(localPoint1.proportional, d1);
/*     */ 
/* 470 */     return new LinearGradient(localPoint1.value, localPoint2.value, localPoint3.value, localPoint4.value, localPoint1.proportional, localCycleMethod, arrayOfStop);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.paint.LinearGradient
 * JD-Core Version:    0.6.2
 */