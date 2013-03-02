/*     */ package javafx.scene.layout;
/*     */ 
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class RowConstraintsBuilder<B extends RowConstraintsBuilder<B>>
/*     */   implements Builder<RowConstraints>
/*     */ {
/*     */   private int __set;
/*     */   private boolean fillHeight;
/*     */   private double maxHeight;
/*     */   private double minHeight;
/*     */   private double percentHeight;
/*     */   private double prefHeight;
/*     */   private VPos valignment;
/*     */   private Priority vgrow;
/*     */ 
/*     */   public static RowConstraintsBuilder<?> create()
/*     */   {
/*  15 */     return new RowConstraintsBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(RowConstraints paramRowConstraints)
/*     */   {
/*  20 */     int i = this.__set;
/*  21 */     if ((i & 0x1) != 0) paramRowConstraints.setFillHeight(this.fillHeight);
/*  22 */     if ((i & 0x2) != 0) paramRowConstraints.setMaxHeight(this.maxHeight);
/*  23 */     if ((i & 0x4) != 0) paramRowConstraints.setMinHeight(this.minHeight);
/*  24 */     if ((i & 0x8) != 0) paramRowConstraints.setPercentHeight(this.percentHeight);
/*  25 */     if ((i & 0x10) != 0) paramRowConstraints.setPrefHeight(this.prefHeight);
/*  26 */     if ((i & 0x20) != 0) paramRowConstraints.setValignment(this.valignment);
/*  27 */     if ((i & 0x40) != 0) paramRowConstraints.setVgrow(this.vgrow);
/*     */   }
/*     */ 
/*     */   public B fillHeight(boolean paramBoolean)
/*     */   {
/*  36 */     this.fillHeight = paramBoolean;
/*  37 */     this.__set |= 1;
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   public B maxHeight(double paramDouble)
/*     */   {
/*  47 */     this.maxHeight = paramDouble;
/*  48 */     this.__set |= 2;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B minHeight(double paramDouble)
/*     */   {
/*  58 */     this.minHeight = paramDouble;
/*  59 */     this.__set |= 4;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B percentHeight(double paramDouble)
/*     */   {
/*  69 */     this.percentHeight = paramDouble;
/*  70 */     this.__set |= 8;
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefHeight(double paramDouble)
/*     */   {
/*  80 */     this.prefHeight = paramDouble;
/*  81 */     this.__set |= 16;
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B valignment(VPos paramVPos)
/*     */   {
/*  91 */     this.valignment = paramVPos;
/*  92 */     this.__set |= 32;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B vgrow(Priority paramPriority)
/*     */   {
/* 102 */     this.vgrow = paramPriority;
/* 103 */     this.__set |= 64;
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public RowConstraints build()
/*     */   {
/* 111 */     RowConstraints localRowConstraints = new RowConstraints();
/* 112 */     applyTo(localRowConstraints);
/* 113 */     return localRowConstraints;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.RowConstraintsBuilder
 * JD-Core Version:    0.6.2
 */