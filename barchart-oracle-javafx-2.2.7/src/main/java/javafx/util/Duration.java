/*     */ package javafx.util;
/*     */ 
/*     */ public class Duration
/*     */   implements Comparable<Duration>
/*     */ {
/*  18 */   public static final Duration ZERO = new Duration(0.0D);
/*     */ 
/*  23 */   public static final Duration ONE = new Duration(1.0D);
/*     */ 
/*  28 */   public static final Duration INDEFINITE = new Duration((1.0D / 0.0D));
/*     */ 
/*  33 */   public static final Duration UNKNOWN = new Duration((0.0D / 0.0D));
/*     */   private final double millis;
/*     */ 
/*     */   public static Duration valueOf(String paramString)
/*     */   {
/*  45 */     int i = -1;
/*  46 */     for (int j = 0; j < paramString.length(); j++) {
/*  47 */       char c = paramString.charAt(j);
/*  48 */       if ((!Character.isDigit(c)) && (c != '.') && (c != '-')) {
/*  49 */         i = j;
/*  50 */         break;
/*     */       }
/*     */     }
/*     */ 
/*  54 */     if (i == -1)
/*     */     {
/*  56 */       throw new IllegalArgumentException("The time parameter must have a suffix of [ms|s|m|h]");
/*     */     }
/*     */ 
/*  59 */     double d = Double.parseDouble(paramString.substring(0, i));
/*  60 */     String str = paramString.substring(i);
/*  61 */     if ("ms".equals(str))
/*  62 */       return millis(d);
/*  63 */     if ("s".equals(str))
/*  64 */       return seconds(d);
/*  65 */     if ("m".equals(str))
/*  66 */       return minutes(d);
/*  67 */     if ("h".equals(str)) {
/*  68 */       return hours(d);
/*     */     }
/*     */ 
/*  71 */     throw new IllegalArgumentException("The time parameter must have a suffix of [ms|s|m|h]");
/*     */   }
/*     */ 
/*     */   public static Duration millis(double paramDouble)
/*     */   {
/*  83 */     if (paramDouble == 0.0D)
/*  84 */       return ZERO;
/*  85 */     if (paramDouble == 1.0D)
/*  86 */       return ONE;
/*  87 */     if (paramDouble == (1.0D / 0.0D))
/*  88 */       return INDEFINITE;
/*  89 */     if (Double.isNaN(paramDouble)) {
/*  90 */       return UNKNOWN;
/*     */     }
/*  92 */     return new Duration(paramDouble);
/*     */   }
/*     */ 
/*     */   public static Duration seconds(double paramDouble)
/*     */   {
/* 104 */     if (paramDouble == 0.0D)
/* 105 */       return ZERO;
/* 106 */     if (paramDouble == (1.0D / 0.0D))
/* 107 */       return INDEFINITE;
/* 108 */     if (Double.isNaN(paramDouble)) {
/* 109 */       return UNKNOWN;
/*     */     }
/* 111 */     return new Duration(paramDouble * 1000.0D);
/*     */   }
/*     */ 
/*     */   public static Duration minutes(double paramDouble)
/*     */   {
/* 123 */     if (paramDouble == 0.0D)
/* 124 */       return ZERO;
/* 125 */     if (paramDouble == (1.0D / 0.0D))
/* 126 */       return INDEFINITE;
/* 127 */     if (Double.isNaN(paramDouble)) {
/* 128 */       return UNKNOWN;
/*     */     }
/* 130 */     return new Duration(paramDouble * 60000.0D);
/*     */   }
/*     */ 
/*     */   public static Duration hours(double paramDouble)
/*     */   {
/* 142 */     if (paramDouble == 0.0D)
/* 143 */       return ZERO;
/* 144 */     if (paramDouble == (1.0D / 0.0D))
/* 145 */       return INDEFINITE;
/* 146 */     if (Double.isNaN(paramDouble)) {
/* 147 */       return UNKNOWN;
/*     */     }
/* 149 */     return new Duration(paramDouble * 3600000.0D);
/*     */   }
/*     */ 
/*     */   public Duration(double paramDouble)
/*     */   {
/* 163 */     this.millis = paramDouble;
/*     */   }
/*     */ 
/*     */   public double toMillis()
/*     */   {
/* 172 */     return this.millis;
/*     */   }
/*     */ 
/*     */   public double toSeconds()
/*     */   {
/* 181 */     return this.millis / 1000.0D;
/*     */   }
/*     */ 
/*     */   public double toMinutes()
/*     */   {
/* 190 */     return this.millis / 60000.0D;
/*     */   }
/*     */ 
/*     */   public double toHours()
/*     */   {
/* 199 */     return this.millis / 3600000.0D;
/*     */   }
/*     */ 
/*     */   public Duration add(Duration paramDuration)
/*     */   {
/* 215 */     return millis(this.millis + paramDuration.millis);
/*     */   }
/*     */ 
/*     */   public Duration subtract(Duration paramDuration)
/*     */   {
/* 229 */     return millis(this.millis - paramDuration.millis);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Duration multiply(Duration paramDuration)
/*     */   {
/* 246 */     return millis(this.millis * paramDuration.millis);
/*     */   }
/*     */ 
/*     */   public Duration multiply(double paramDouble)
/*     */   {
/* 260 */     return millis(this.millis * paramDouble);
/*     */   }
/*     */ 
/*     */   public Duration divide(double paramDouble)
/*     */   {
/* 274 */     return millis(this.millis / paramDouble);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Duration divide(Duration paramDuration)
/*     */   {
/* 292 */     return millis(this.millis / paramDuration.millis);
/*     */   }
/*     */ 
/*     */   public Duration negate()
/*     */   {
/* 306 */     return millis(-this.millis);
/*     */   }
/*     */ 
/*     */   public boolean isIndefinite()
/*     */   {
/* 315 */     return this.millis == (1.0D / 0.0D);
/*     */   }
/*     */ 
/*     */   public boolean isUnknown()
/*     */   {
/* 324 */     return Double.isNaN(this.millis);
/*     */   }
/*     */ 
/*     */   public boolean lessThan(Duration paramDuration)
/*     */   {
/* 335 */     return this.millis < paramDuration.millis;
/*     */   }
/*     */ 
/*     */   public boolean lessThanOrEqualTo(Duration paramDuration)
/*     */   {
/* 346 */     return this.millis <= paramDuration.millis;
/*     */   }
/*     */ 
/*     */   public boolean greaterThan(Duration paramDuration)
/*     */   {
/* 357 */     return this.millis > paramDuration.millis;
/*     */   }
/*     */ 
/*     */   public boolean greaterThanOrEqualTo(Duration paramDuration)
/*     */   {
/* 368 */     return this.millis >= paramDuration.millis;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 376 */     return this.millis + " ms";
/*     */   }
/*     */ 
/*     */   public int compareTo(Duration paramDuration)
/*     */   {
/* 389 */     return Double.compare(this.millis, paramDuration.millis);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 399 */     return (paramObject == this) || (((paramObject instanceof Duration)) && (this.millis == ((Duration)paramObject).millis));
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 408 */     long l = Double.doubleToLongBits(this.millis);
/* 409 */     return (int)(l ^ l >>> 32);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.Duration
 * JD-Core Version:    0.6.2
 */