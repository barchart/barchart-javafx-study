/*     */ package javafx.scene.paint;
/*     */ 
/*     */ import com.sun.javafx.scene.paint.GradientUtils;
/*     */ import com.sun.javafx.scene.paint.GradientUtils.Parser;
/*     */ import com.sun.javafx.scene.paint.GradientUtils.Point;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class RadialGradient extends Paint
/*     */ {
/*     */   private double focusAngle;
/*     */   private double focusDistance;
/*     */   private double centerX;
/*     */   private double centerY;
/*     */   private double radius;
/*     */   private boolean proportional;
/*     */   private CycleMethod cycleMethod;
/*     */   private List<Stop> stops;
/*     */   private Object platformPaint;
/*     */   private int hash;
/*     */ 
/*     */   public final double getFocusAngle()
/*     */   {
/*  76 */     return this.focusAngle;
/*     */   }
/*     */ 
/*     */   public final double getFocusDistance()
/*     */   {
/*  88 */     return this.focusDistance;
/*     */   }
/*     */ 
/*     */   public final double getCenterX()
/*     */   {
/* 103 */     return this.centerX;
/*     */   }
/*     */ 
/*     */   public final double getCenterY()
/*     */   {
/* 118 */     return this.centerY;
/*     */   }
/*     */ 
/*     */   public final double getRadius()
/*     */   {
/* 132 */     return this.radius;
/*     */   }
/*     */ 
/*     */   public final boolean isProportional()
/*     */   {
/* 149 */     return this.proportional;
/*     */   }
/*     */ 
/*     */   public final CycleMethod getCycleMethod()
/*     */   {
/* 162 */     return this.cycleMethod;
/*     */   }
/*     */ 
/*     */   public final List<Stop> getStops()
/*     */   {
/* 183 */     return this.stops;
/*     */   }
/*     */ 
/*     */   public RadialGradient(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, boolean paramBoolean, CycleMethod paramCycleMethod, Stop[] paramArrayOfStop)
/*     */   {
/* 220 */     this.focusAngle = paramDouble1;
/* 221 */     this.focusDistance = paramDouble2;
/* 222 */     this.centerX = paramDouble3;
/* 223 */     this.centerY = paramDouble4;
/* 224 */     this.radius = paramDouble5;
/* 225 */     this.proportional = paramBoolean;
/* 226 */     this.cycleMethod = (paramCycleMethod == null ? CycleMethod.NO_CYCLE : paramCycleMethod);
/* 227 */     this.stops = Stop.normalize(paramArrayOfStop);
/*     */   }
/*     */ 
/*     */   public RadialGradient(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, boolean paramBoolean, CycleMethod paramCycleMethod, List<Stop> paramList)
/*     */   {
/* 253 */     this.focusAngle = paramDouble1;
/* 254 */     this.focusDistance = paramDouble2;
/* 255 */     this.centerX = paramDouble3;
/* 256 */     this.centerY = paramDouble4;
/* 257 */     this.radius = paramDouble5;
/* 258 */     this.proportional = paramBoolean;
/* 259 */     this.cycleMethod = (paramCycleMethod == null ? CycleMethod.NO_CYCLE : paramCycleMethod);
/* 260 */     this.stops = Stop.normalize(paramList);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Object impl_getPlatformPaint()
/*     */   {
/* 269 */     if (this.platformPaint == null) {
/* 270 */       this.platformPaint = Toolkit.getToolkit().getPaint(this);
/*     */     }
/* 272 */     return this.platformPaint;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 281 */     if (paramObject == this) return true;
/* 282 */     if ((paramObject instanceof RadialGradient)) {
/* 283 */       RadialGradient localRadialGradient = (RadialGradient)paramObject;
/* 284 */       if ((this.focusAngle != localRadialGradient.focusAngle) || (this.focusDistance != localRadialGradient.focusDistance) || (this.centerX != localRadialGradient.centerX) || (this.centerY != localRadialGradient.centerY) || (this.radius != localRadialGradient.radius) || (this.proportional != localRadialGradient.proportional) || (this.cycleMethod != localRadialGradient.cycleMethod))
/*     */       {
/* 290 */         return false;
/* 291 */       }if (!this.stops.equals(localRadialGradient.stops)) return false;
/* 292 */       return true;
/* 293 */     }return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 303 */     if (this.hash == 0) {
/* 304 */       long l = 17L;
/* 305 */       l = 37L * l + Double.doubleToLongBits(this.focusAngle);
/* 306 */       l = 37L * l + Double.doubleToLongBits(this.focusDistance);
/* 307 */       l = 37L * l + Double.doubleToLongBits(this.centerX);
/* 308 */       l = 37L * l + Double.doubleToLongBits(this.centerY);
/* 309 */       l = 37L * l + Double.doubleToLongBits(this.radius);
/* 310 */       l = 37L * l + (this.proportional ? 1231 : 1237);
/* 311 */       l = 37L * l + this.cycleMethod.hashCode();
/* 312 */       for (Stop localStop : this.stops) {
/* 313 */         l = 37L * l + localStop.hashCode();
/*     */       }
/* 315 */       this.hash = ((int)(l ^ l >> 32));
/*     */     }
/* 317 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 325 */     StringBuilder localStringBuilder = new StringBuilder("radial-gradient(focus-angle ").append(this.focusAngle).append("deg, focus-distance ").append(this.focusDistance * 100.0D).append("% , center ").append(GradientUtils.lengthToString(this.centerX, this.proportional)).append(" ").append(GradientUtils.lengthToString(this.centerY, this.proportional)).append(", radius ").append(GradientUtils.lengthToString(this.radius, this.proportional)).append(", ");
/*     */ 
/* 332 */     switch (1.$SwitchMap$javafx$scene$paint$CycleMethod[this.cycleMethod.ordinal()]) {
/*     */     case 1:
/* 334 */       localStringBuilder.append("reflect").append(", ");
/* 335 */       break;
/*     */     case 2:
/* 337 */       localStringBuilder.append("repeat").append(", ");
/*     */     }
/*     */ 
/* 341 */     for (Stop localStop : this.stops) {
/* 342 */       localStringBuilder.append(localStop).append(", ");
/*     */     }
/*     */ 
/* 345 */     localStringBuilder.delete(localStringBuilder.length() - 2, localStringBuilder.length());
/* 346 */     localStringBuilder.append(")");
/*     */ 
/* 348 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public static RadialGradient valueOf(String paramString)
/*     */   {
/* 395 */     if (paramString == null) {
/* 396 */       throw new NullPointerException("gradient must be specified");
/*     */     }
/*     */ 
/* 399 */     String str1 = "radial-gradient(";
/* 400 */     String str2 = ")";
/* 401 */     if (paramString.startsWith(str1)) {
/* 402 */       if (!paramString.endsWith(str2)) {
/* 403 */         throw new IllegalArgumentException(new StringBuilder().append("Invalid gradient specification, must end with \"").append(str2).append('"').toString());
/*     */       }
/*     */ 
/* 406 */       paramString = paramString.substring(str1.length(), paramString.length() - str2.length());
/*     */     }
/*     */ 
/* 409 */     GradientUtils.Parser localParser = new GradientUtils.Parser(paramString);
/* 410 */     if (localParser.getSize() < 2) {
/* 411 */       throw new IllegalArgumentException("Invalid gradient specification");
/*     */     }
/*     */ 
/* 414 */     double d1 = 0.0D; double d2 = 0.0D;
/*     */ 
/* 417 */     String[] arrayOfString = localParser.splitCurrentToken();
/* 418 */     if ("focus-angle".equals(arrayOfString[0])) {
/* 419 */       GradientUtils.Parser.checkNumberOfArguments(arrayOfString, 1);
/* 420 */       d1 = GradientUtils.Parser.parseAngle(arrayOfString[1]);
/* 421 */       localParser.shift();
/*     */     }
/*     */ 
/* 424 */     arrayOfString = localParser.splitCurrentToken();
/* 425 */     if ("focus-distance".equals(arrayOfString[0])) {
/* 426 */       GradientUtils.Parser.checkNumberOfArguments(arrayOfString, 1);
/* 427 */       d2 = GradientUtils.Parser.parsePercentage(arrayOfString[1]);
/*     */ 
/* 429 */       localParser.shift();
/*     */     }
/*     */ 
/* 432 */     arrayOfString = localParser.splitCurrentToken();
/*     */     GradientUtils.Point localPoint1;
/*     */     GradientUtils.Point localPoint2;
/* 433 */     if ("center".equals(arrayOfString[0])) {
/* 434 */       GradientUtils.Parser.checkNumberOfArguments(arrayOfString, 2);
/* 435 */       localPoint1 = localParser.parsePoint(arrayOfString[1]);
/* 436 */       localPoint2 = localParser.parsePoint(arrayOfString[2]);
/* 437 */       localParser.shift();
/*     */     } else {
/* 439 */       localPoint1 = GradientUtils.Point.MIN;
/* 440 */       localPoint2 = GradientUtils.Point.MIN;
/*     */     }
/*     */ 
/* 443 */     arrayOfString = localParser.splitCurrentToken();
/*     */     GradientUtils.Point localPoint3;
/* 444 */     if ("radius".equals(arrayOfString[0])) {
/* 445 */       GradientUtils.Parser.checkNumberOfArguments(arrayOfString, 1);
/* 446 */       localPoint3 = localParser.parsePoint(arrayOfString[1]);
/* 447 */       localParser.shift();
/*     */     } else {
/* 449 */       throw new IllegalArgumentException("Invalid gradient specification: radius must be specified");
/*     */     }
/*     */ 
/* 453 */     CycleMethod localCycleMethod = CycleMethod.NO_CYCLE;
/* 454 */     String str3 = localParser.getCurrentToken();
/* 455 */     if ("repeat".equals(str3)) {
/* 456 */       localCycleMethod = CycleMethod.REPEAT;
/* 457 */       localParser.shift();
/* 458 */     } else if ("reflect".equals(str3)) {
/* 459 */       localCycleMethod = CycleMethod.REFLECT;
/* 460 */       localParser.shift();
/*     */     }
/*     */ 
/* 463 */     Stop[] arrayOfStop = localParser.parseStops(localPoint3.proportional, localPoint3.value);
/*     */ 
/* 465 */     return new RadialGradient(d1, d2, localPoint1.value, localPoint2.value, localPoint3.value, localPoint3.proportional, localCycleMethod, arrayOfStop);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.paint.RadialGradient
 * JD-Core Version:    0.6.2
 */