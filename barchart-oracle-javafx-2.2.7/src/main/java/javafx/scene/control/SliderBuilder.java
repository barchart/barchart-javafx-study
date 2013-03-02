/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class SliderBuilder<B extends SliderBuilder<B>> extends ControlBuilder<B>
/*     */   implements Builder<Slider>
/*     */ {
/*     */   private int __set;
/*     */   private double blockIncrement;
/*     */   private StringConverter<Double> labelFormatter;
/*     */   private double majorTickUnit;
/*     */   private double max;
/*     */   private double min;
/*     */   private int minorTickCount;
/*     */   private Orientation orientation;
/*     */   private boolean showTickLabels;
/*     */   private boolean showTickMarks;
/*     */   private boolean snapToTicks;
/*     */   private double value;
/*     */   private boolean valueChanging;
/*     */ 
/*     */   public static SliderBuilder<?> create()
/*     */   {
/*  15 */     return new SliderBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Slider paramSlider) {
/*  23 */     super.applyTo(paramSlider);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramSlider.setBlockIncrement(this.blockIncrement); break;
/*     */       case 1:
/*  30 */         paramSlider.setLabelFormatter(this.labelFormatter); break;
/*     */       case 2:
/*  31 */         paramSlider.setMajorTickUnit(this.majorTickUnit); break;
/*     */       case 3:
/*  32 */         paramSlider.setMax(this.max); break;
/*     */       case 4:
/*  33 */         paramSlider.setMin(this.min); break;
/*     */       case 5:
/*  34 */         paramSlider.setMinorTickCount(this.minorTickCount); break;
/*     */       case 6:
/*  35 */         paramSlider.setOrientation(this.orientation); break;
/*     */       case 7:
/*  36 */         paramSlider.setShowTickLabels(this.showTickLabels); break;
/*     */       case 8:
/*  37 */         paramSlider.setShowTickMarks(this.showTickMarks); break;
/*     */       case 9:
/*  38 */         paramSlider.setSnapToTicks(this.snapToTicks); break;
/*     */       case 10:
/*  39 */         paramSlider.setValue(this.value); break;
/*     */       case 11:
/*  40 */         paramSlider.setValueChanging(this.valueChanging);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B blockIncrement(double paramDouble)
/*     */   {
/*  51 */     this.blockIncrement = paramDouble;
/*  52 */     __set(0);
/*  53 */     return this;
/*     */   }
/*     */ 
/*     */   public B labelFormatter(StringConverter<Double> paramStringConverter)
/*     */   {
/*  62 */     this.labelFormatter = paramStringConverter;
/*  63 */     __set(1);
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */   public B majorTickUnit(double paramDouble)
/*     */   {
/*  73 */     this.majorTickUnit = paramDouble;
/*  74 */     __set(2);
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public B max(double paramDouble)
/*     */   {
/*  84 */     this.max = paramDouble;
/*  85 */     __set(3);
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   public B min(double paramDouble)
/*     */   {
/*  95 */     this.min = paramDouble;
/*  96 */     __set(4);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   public B minorTickCount(int paramInt)
/*     */   {
/* 106 */     this.minorTickCount = paramInt;
/* 107 */     __set(5);
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   public B orientation(Orientation paramOrientation)
/*     */   {
/* 117 */     this.orientation = paramOrientation;
/* 118 */     __set(6);
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public B showTickLabels(boolean paramBoolean)
/*     */   {
/* 128 */     this.showTickLabels = paramBoolean;
/* 129 */     __set(7);
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   public B showTickMarks(boolean paramBoolean)
/*     */   {
/* 139 */     this.showTickMarks = paramBoolean;
/* 140 */     __set(8);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public B snapToTicks(boolean paramBoolean)
/*     */   {
/* 150 */     this.snapToTicks = paramBoolean;
/* 151 */     __set(9);
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   public B value(double paramDouble)
/*     */   {
/* 161 */     this.value = paramDouble;
/* 162 */     __set(10);
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   public B valueChanging(boolean paramBoolean)
/*     */   {
/* 172 */     this.valueChanging = paramBoolean;
/* 173 */     __set(11);
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   public Slider build()
/*     */   {
/* 181 */     Slider localSlider = new Slider();
/* 182 */     applyTo(localSlider);
/* 183 */     return localSlider;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.SliderBuilder
 * JD-Core Version:    0.6.2
 */