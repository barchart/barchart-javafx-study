/*     */ package javafx.scene.paint;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public final class RadialGradientBuilder
/*     */   implements Builder<RadialGradient>
/*     */ {
/*     */   private double centerX;
/*     */   private double centerY;
/*     */   private CycleMethod cycleMethod;
/*     */   private double focusAngle;
/*     */   private double focusDistance;
/*  63 */   private boolean proportional = true;
/*     */ 
/*  72 */   private double radius = 1.0D;
/*     */   private List<Stop> stops;
/*     */ 
/*     */   public static RadialGradientBuilder create()
/*     */   {
/*  15 */     return new RadialGradientBuilder();
/*     */   }
/*     */ 
/*     */   public RadialGradientBuilder centerX(double paramDouble)
/*     */   {
/*  23 */     this.centerX = paramDouble;
/*  24 */     return this;
/*     */   }
/*     */ 
/*     */   public RadialGradientBuilder centerY(double paramDouble)
/*     */   {
/*  32 */     this.centerY = paramDouble;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   public RadialGradientBuilder cycleMethod(CycleMethod paramCycleMethod)
/*     */   {
/*  41 */     this.cycleMethod = paramCycleMethod;
/*  42 */     return this;
/*     */   }
/*     */ 
/*     */   public RadialGradientBuilder focusAngle(double paramDouble)
/*     */   {
/*  50 */     this.focusAngle = paramDouble;
/*  51 */     return this;
/*     */   }
/*     */ 
/*     */   public RadialGradientBuilder focusDistance(double paramDouble)
/*     */   {
/*  59 */     this.focusDistance = paramDouble;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public RadialGradientBuilder proportional(boolean paramBoolean)
/*     */   {
/*  68 */     this.proportional = paramBoolean;
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */   public RadialGradientBuilder radius(double paramDouble)
/*     */   {
/*  77 */     this.radius = paramDouble;
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   public RadialGradientBuilder stops(List<Stop> paramList)
/*     */   {
/*  86 */     this.stops = paramList;
/*  87 */     return this;
/*     */   }
/*     */ 
/*     */   public RadialGradientBuilder stops(Stop[] paramArrayOfStop)
/*     */   {
/*  94 */     return stops(Arrays.asList(paramArrayOfStop));
/*     */   }
/*     */ 
/*     */   public RadialGradient build()
/*     */   {
/* 101 */     RadialGradient localRadialGradient = new RadialGradient(this.focusAngle, this.focusDistance, this.centerX, this.centerY, this.radius, this.proportional, this.cycleMethod, this.stops);
/* 102 */     return localRadialGradient;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.paint.RadialGradientBuilder
 * JD-Core Version:    0.6.2
 */