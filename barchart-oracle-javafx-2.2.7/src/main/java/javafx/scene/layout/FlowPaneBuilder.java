/*     */ package javafx.scene.layout;
/*     */ 
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.geometry.VPos;
/*     */ 
/*     */ public class FlowPaneBuilder<B extends FlowPaneBuilder<B>> extends PaneBuilder<B>
/*     */ {
/*     */   private int __set;
/*     */   private Pos alignment;
/*     */   private HPos columnHalignment;
/*     */   private double hgap;
/*     */   private Orientation orientation;
/*     */   private double prefWrapLength;
/*     */   private VPos rowValignment;
/*     */   private double vgap;
/*     */ 
/*     */   public static FlowPaneBuilder<?> create()
/*     */   {
/*  15 */     return new FlowPaneBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(FlowPane paramFlowPane)
/*     */   {
/*  20 */     super.applyTo(paramFlowPane);
/*  21 */     int i = this.__set;
/*  22 */     if ((i & 0x1) != 0) paramFlowPane.setAlignment(this.alignment);
/*  23 */     if ((i & 0x2) != 0) paramFlowPane.setColumnHalignment(this.columnHalignment);
/*  24 */     if ((i & 0x4) != 0) paramFlowPane.setHgap(this.hgap);
/*  25 */     if ((i & 0x8) != 0) paramFlowPane.setOrientation(this.orientation);
/*  26 */     if ((i & 0x10) != 0) paramFlowPane.setPrefWrapLength(this.prefWrapLength);
/*  27 */     if ((i & 0x20) != 0) paramFlowPane.setRowValignment(this.rowValignment);
/*  28 */     if ((i & 0x40) != 0) paramFlowPane.setVgap(this.vgap);
/*     */   }
/*     */ 
/*     */   public B alignment(Pos paramPos)
/*     */   {
/*  37 */     this.alignment = paramPos;
/*  38 */     this.__set |= 1;
/*  39 */     return this;
/*     */   }
/*     */ 
/*     */   public B columnHalignment(HPos paramHPos)
/*     */   {
/*  48 */     this.columnHalignment = paramHPos;
/*  49 */     this.__set |= 2;
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   public B hgap(double paramDouble)
/*     */   {
/*  59 */     this.hgap = paramDouble;
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
/*     */   public B prefWrapLength(double paramDouble)
/*     */   {
/*  81 */     this.prefWrapLength = paramDouble;
/*  82 */     this.__set |= 16;
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public B rowValignment(VPos paramVPos)
/*     */   {
/*  92 */     this.rowValignment = paramVPos;
/*  93 */     this.__set |= 32;
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public B vgap(double paramDouble)
/*     */   {
/* 103 */     this.vgap = paramDouble;
/* 104 */     this.__set |= 64;
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public FlowPane build()
/*     */   {
/* 112 */     FlowPane localFlowPane = new FlowPane();
/* 113 */     applyTo(localFlowPane);
/* 114 */     return localFlowPane;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.FlowPaneBuilder
 * JD-Core Version:    0.6.2
 */