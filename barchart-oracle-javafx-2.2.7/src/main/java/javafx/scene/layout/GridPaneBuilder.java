/*     */ package javafx.scene.layout;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Pos;
/*     */ 
/*     */ public class GridPaneBuilder<B extends GridPaneBuilder<B>> extends PaneBuilder<B>
/*     */ {
/*     */   private int __set;
/*     */   private Pos alignment;
/*     */   private Collection<? extends ColumnConstraints> columnConstraints;
/*     */   private boolean gridLinesVisible;
/*     */   private double hgap;
/*     */   private Collection<? extends RowConstraints> rowConstraints;
/*     */   private double vgap;
/*     */ 
/*     */   public static GridPaneBuilder<?> create()
/*     */   {
/*  15 */     return new GridPaneBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(GridPane paramGridPane)
/*     */   {
/*  20 */     super.applyTo(paramGridPane);
/*  21 */     int i = this.__set;
/*  22 */     if ((i & 0x1) != 0) paramGridPane.setAlignment(this.alignment);
/*  23 */     if ((i & 0x2) != 0) paramGridPane.getColumnConstraints().addAll(this.columnConstraints);
/*  24 */     if ((i & 0x4) != 0) paramGridPane.setGridLinesVisible(this.gridLinesVisible);
/*  25 */     if ((i & 0x8) != 0) paramGridPane.setHgap(this.hgap);
/*  26 */     if ((i & 0x10) != 0) paramGridPane.getRowConstraints().addAll(this.rowConstraints);
/*  27 */     if ((i & 0x20) != 0) paramGridPane.setVgap(this.vgap);
/*     */   }
/*     */ 
/*     */   public B alignment(Pos paramPos)
/*     */   {
/*  36 */     this.alignment = paramPos;
/*  37 */     this.__set |= 1;
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   public B columnConstraints(Collection<? extends ColumnConstraints> paramCollection)
/*     */   {
/*  47 */     this.columnConstraints = paramCollection;
/*  48 */     this.__set |= 2;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B columnConstraints(ColumnConstraints[] paramArrayOfColumnConstraints)
/*     */   {
/*  56 */     return columnConstraints(Arrays.asList(paramArrayOfColumnConstraints));
/*     */   }
/*     */ 
/*     */   public B gridLinesVisible(boolean paramBoolean)
/*     */   {
/*  65 */     this.gridLinesVisible = paramBoolean;
/*  66 */     this.__set |= 4;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public B hgap(double paramDouble)
/*     */   {
/*  76 */     this.hgap = paramDouble;
/*  77 */     this.__set |= 8;
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   public B rowConstraints(Collection<? extends RowConstraints> paramCollection)
/*     */   {
/*  87 */     this.rowConstraints = paramCollection;
/*  88 */     this.__set |= 16;
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   public B rowConstraints(RowConstraints[] paramArrayOfRowConstraints)
/*     */   {
/*  96 */     return rowConstraints(Arrays.asList(paramArrayOfRowConstraints));
/*     */   }
/*     */ 
/*     */   public B vgap(double paramDouble)
/*     */   {
/* 105 */     this.vgap = paramDouble;
/* 106 */     this.__set |= 32;
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   public GridPane build()
/*     */   {
/* 114 */     GridPane localGridPane = new GridPane();
/* 115 */     applyTo(localGridPane);
/* 116 */     return localGridPane;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.GridPaneBuilder
 * JD-Core Version:    0.6.2
 */