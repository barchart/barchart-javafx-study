/*     */ package javafx.animation;
/*     */ 
/*     */ import com.sun.scenario.animation.NumberTangentInterpolator;
/*     */ import com.sun.scenario.animation.SplineInterpolator;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public abstract class Interpolator
/*     */ {
/*     */   private static final double EPSILON = 1.0E-12D;
/*  57 */   public static final Interpolator DISCRETE = new Interpolator()
/*     */   {
/*     */     protected double curve(double paramAnonymousDouble) {
/*  60 */       return Math.abs(paramAnonymousDouble - 1.0D) < 1.0E-12D ? 1.0D : 0.0D;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/*  65 */       return "Interpolator.DISCRETE";
/*     */     }
/*  57 */   };
/*     */ 
/*  74 */   public static final Interpolator LINEAR = new Interpolator()
/*     */   {
/*     */     protected double curve(double paramAnonymousDouble) {
/*  77 */       return paramAnonymousDouble;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/*  82 */       return "Interpolator.LINEAR";
/*     */     }
/*  74 */   };
/*     */ 
/* 109 */   public static final Interpolator EASE_BOTH = new Interpolator()
/*     */   {
/*     */     protected double curve(double paramAnonymousDouble)
/*     */     {
/* 114 */       return Interpolator.clamp(paramAnonymousDouble > 0.8D ? -3.125D * paramAnonymousDouble * paramAnonymousDouble + 6.25D * paramAnonymousDouble - 2.125D : paramAnonymousDouble < 0.2D ? 3.125D * paramAnonymousDouble * paramAnonymousDouble : 1.25D * paramAnonymousDouble - 0.125D);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 121 */       return "Interpolator.EASE_BOTH";
/*     */     }
/* 109 */   };
/*     */ 
/* 133 */   public static final Interpolator EASE_IN = new Interpolator()
/*     */   {
/*     */     private static final double S1 = 2.777777777777778D;
/*     */     private static final double S3 = 1.111111111111111D;
/*     */     private static final double S4 = 0.111111111111111D;
/*     */ 
/*     */     protected double curve(double paramAnonymousDouble) {
/* 142 */       return Interpolator.clamp(paramAnonymousDouble < 0.2D ? 2.777777777777778D * paramAnonymousDouble * paramAnonymousDouble : 1.111111111111111D * paramAnonymousDouble - 0.111111111111111D);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 147 */       return "Interpolator.EASE_IN";
/*     */     }
/* 133 */   };
/*     */ 
/* 160 */   public static final Interpolator EASE_OUT = new Interpolator() {
/*     */     private static final double S1 = -2.777777777777778D;
/*     */     private static final double S2 = 5.555555555555555D;
/*     */     private static final double S3 = -1.777777777777778D;
/*     */     private static final double S4 = 1.111111111111111D;
/*     */ 
/*     */     protected double curve(double paramAnonymousDouble) {
/* 170 */       return Interpolator.clamp(paramAnonymousDouble > 0.8D ? -2.777777777777778D * paramAnonymousDouble * paramAnonymousDouble + 5.555555555555555D * paramAnonymousDouble + -1.777777777777778D : 1.111111111111111D * paramAnonymousDouble);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 176 */       return "Interpolator.EASE_OUT";
/*     */     }
/* 160 */   };
/*     */ 
/*     */   public static Interpolator SPLINE(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 198 */     return new SplineInterpolator(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */ 
/*     */   public static Interpolator TANGENT(Duration paramDuration1, double paramDouble1, Duration paramDuration2, double paramDouble2)
/*     */   {
/* 226 */     return NumberTangentInterpolator.create(paramDouble1, paramDuration1, paramDouble2, paramDuration2);
/*     */   }
/*     */ 
/*     */   public static Interpolator TANGENT(Duration paramDuration, double paramDouble)
/*     */   {
/* 244 */     return NumberTangentInterpolator.create(paramDouble, paramDuration);
/*     */   }
/*     */ 
/*     */   public Object interpolate(Object paramObject1, Object paramObject2, double paramDouble)
/*     */   {
/* 273 */     if (((paramObject1 instanceof Number)) && ((paramObject2 instanceof Number))) {
/* 274 */       double d1 = ((Number)paramObject1).doubleValue();
/* 275 */       double d2 = ((Number)paramObject2).doubleValue();
/* 276 */       double d3 = d1 + (d2 - d1) * curve(paramDouble);
/* 277 */       if (((paramObject1 instanceof Double)) || ((paramObject2 instanceof Double))) {
/* 278 */         return Double.valueOf(d3);
/*     */       }
/* 280 */       if (((paramObject1 instanceof Float)) || ((paramObject2 instanceof Float))) {
/* 281 */         return Float.valueOf((float)d3);
/*     */       }
/* 283 */       if (((paramObject1 instanceof Long)) || ((paramObject2 instanceof Long))) {
/* 284 */         return Long.valueOf(Math.round(d3));
/*     */       }
/* 286 */       return Integer.valueOf((int)Math.round(d3));
/* 287 */     }if (((paramObject1 instanceof Interpolatable)) && ((paramObject2 instanceof Interpolatable))) {
/* 288 */       return ((Interpolatable)paramObject1).interpolate(paramObject2, curve(paramDouble));
/*     */     }
/*     */ 
/* 292 */     return curve(paramDouble) == 1.0D ? paramObject2 : paramObject1;
/*     */   }
/*     */ 
/*     */   public boolean interpolate(boolean paramBoolean1, boolean paramBoolean2, double paramDouble)
/*     */   {
/* 313 */     return Math.abs(curve(paramDouble) - 1.0D) < 1.0E-12D ? paramBoolean2 : paramBoolean1;
/*     */   }
/*     */ 
/*     */   public double interpolate(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 333 */     return paramDouble1 + (paramDouble2 - paramDouble1) * curve(paramDouble3);
/*     */   }
/*     */ 
/*     */   public int interpolate(int paramInt1, int paramInt2, double paramDouble)
/*     */   {
/* 351 */     return paramInt1 + (int)Math.round((paramInt2 - paramInt1) * curve(paramDouble));
/*     */   }
/*     */ 
/*     */   public long interpolate(long paramLong1, long paramLong2, double paramDouble)
/*     */   {
/* 371 */     return paramLong1 + Math.round((paramLong2 - paramLong1) * curve(paramDouble));
/*     */   }
/*     */ 
/*     */   private static double clamp(double paramDouble)
/*     */   {
/* 376 */     return paramDouble > 1.0D ? 1.0D : paramDouble < 0.0D ? 0.0D : paramDouble;
/*     */   }
/*     */ 
/*     */   protected abstract double curve(double paramDouble);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.Interpolator
 * JD-Core Version:    0.6.2
 */