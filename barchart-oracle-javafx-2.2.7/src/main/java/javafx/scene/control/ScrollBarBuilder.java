/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class ScrollBarBuilder<B extends ScrollBarBuilder<B>> extends ControlBuilder<B>
/*     */   implements Builder<ScrollBar>
/*     */ {
/*     */   private int __set;
/*     */   private double blockIncrement;
/*     */   private double max;
/*     */   private double min;
/*     */   private Orientation orientation;
/*     */   private double unitIncrement;
/*     */   private double value;
/*     */   private double visibleAmount;
/*     */ 
/*     */   public static ScrollBarBuilder<?> create()
/*     */   {
/*  15 */     return new ScrollBarBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(ScrollBar paramScrollBar)
/*     */   {
/*  20 */     super.applyTo(paramScrollBar);
/*  21 */     int i = this.__set;
/*  22 */     if ((i & 0x1) != 0) paramScrollBar.setBlockIncrement(this.blockIncrement);
/*  23 */     if ((i & 0x2) != 0) paramScrollBar.setMax(this.max);
/*  24 */     if ((i & 0x4) != 0) paramScrollBar.setMin(this.min);
/*  25 */     if ((i & 0x8) != 0) paramScrollBar.setOrientation(this.orientation);
/*  26 */     if ((i & 0x10) != 0) paramScrollBar.setUnitIncrement(this.unitIncrement);
/*  27 */     if ((i & 0x20) != 0) paramScrollBar.setValue(this.value);
/*  28 */     if ((i & 0x40) != 0) paramScrollBar.setVisibleAmount(this.visibleAmount);
/*     */   }
/*     */ 
/*     */   public B blockIncrement(double paramDouble)
/*     */   {
/*  37 */     this.blockIncrement = paramDouble;
/*  38 */     this.__set |= 1;
/*  39 */     return this;
/*     */   }
/*     */ 
/*     */   public B max(double paramDouble)
/*     */   {
/*  48 */     this.max = paramDouble;
/*  49 */     this.__set |= 2;
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   public B min(double paramDouble)
/*     */   {
/*  59 */     this.min = paramDouble;
/*  60 */     this.__set |= 4;
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   public B orientation(Orientation paramOrientation)
/*     */   {
/*  70 */     this.orientation = paramOrientation;
/*  71 */     this.__set |= 8;
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public B unitIncrement(double paramDouble)
/*     */   {
/*  81 */     this.unitIncrement = paramDouble;
/*  82 */     this.__set |= 16;
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public B value(double paramDouble)
/*     */   {
/*  92 */     this.value = paramDouble;
/*  93 */     this.__set |= 32;
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public B visibleAmount(double paramDouble)
/*     */   {
/* 103 */     this.visibleAmount = paramDouble;
/* 104 */     this.__set |= 64;
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public ScrollBar build()
/*     */   {
/* 112 */     ScrollBar localScrollBar = new ScrollBar();
/* 113 */     applyTo(localScrollBar);
/* 114 */     return localScrollBar;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ScrollBarBuilder
 * JD-Core Version:    0.6.2
 */