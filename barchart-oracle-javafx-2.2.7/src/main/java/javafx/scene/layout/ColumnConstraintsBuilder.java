/*     */ package javafx.scene.layout;
/*     */ 
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class ColumnConstraintsBuilder<B extends ColumnConstraintsBuilder<B>>
/*     */   implements Builder<ColumnConstraints>
/*     */ {
/*     */   private int __set;
/*     */   private boolean fillWidth;
/*     */   private HPos halignment;
/*     */   private Priority hgrow;
/*     */   private double maxWidth;
/*     */   private double minWidth;
/*     */   private double percentWidth;
/*     */   private double prefWidth;
/*     */ 
/*     */   public static ColumnConstraintsBuilder<?> create()
/*     */   {
/*  15 */     return new ColumnConstraintsBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(ColumnConstraints paramColumnConstraints)
/*     */   {
/*  20 */     int i = this.__set;
/*  21 */     if ((i & 0x1) != 0) paramColumnConstraints.setFillWidth(this.fillWidth);
/*  22 */     if ((i & 0x2) != 0) paramColumnConstraints.setHalignment(this.halignment);
/*  23 */     if ((i & 0x4) != 0) paramColumnConstraints.setHgrow(this.hgrow);
/*  24 */     if ((i & 0x8) != 0) paramColumnConstraints.setMaxWidth(this.maxWidth);
/*  25 */     if ((i & 0x10) != 0) paramColumnConstraints.setMinWidth(this.minWidth);
/*  26 */     if ((i & 0x20) != 0) paramColumnConstraints.setPercentWidth(this.percentWidth);
/*  27 */     if ((i & 0x40) != 0) paramColumnConstraints.setPrefWidth(this.prefWidth);
/*     */   }
/*     */ 
/*     */   public B fillWidth(boolean paramBoolean)
/*     */   {
/*  36 */     this.fillWidth = paramBoolean;
/*  37 */     this.__set |= 1;
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   public B halignment(HPos paramHPos)
/*     */   {
/*  47 */     this.halignment = paramHPos;
/*  48 */     this.__set |= 2;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B hgrow(Priority paramPriority)
/*     */   {
/*  58 */     this.hgrow = paramPriority;
/*  59 */     this.__set |= 4;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B maxWidth(double paramDouble)
/*     */   {
/*  69 */     this.maxWidth = paramDouble;
/*  70 */     this.__set |= 8;
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B minWidth(double paramDouble)
/*     */   {
/*  80 */     this.minWidth = paramDouble;
/*  81 */     this.__set |= 16;
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B percentWidth(double paramDouble)
/*     */   {
/*  91 */     this.percentWidth = paramDouble;
/*  92 */     this.__set |= 32;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefWidth(double paramDouble)
/*     */   {
/* 102 */     this.prefWidth = paramDouble;
/* 103 */     this.__set |= 64;
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnConstraints build()
/*     */   {
/* 111 */     ColumnConstraints localColumnConstraints = new ColumnConstraints();
/* 112 */     applyTo(localColumnConstraints);
/* 113 */     return localColumnConstraints;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.ColumnConstraintsBuilder
 * JD-Core Version:    0.6.2
 */