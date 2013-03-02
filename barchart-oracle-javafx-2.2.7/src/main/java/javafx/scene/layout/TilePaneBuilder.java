/*     */ package javafx.scene.layout;
/*     */ 
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.geometry.Pos;
/*     */ 
/*     */ public class TilePaneBuilder<B extends TilePaneBuilder<B>> extends PaneBuilder<B>
/*     */ {
/*     */   private int __set;
/*     */   private Pos alignment;
/*     */   private double hgap;
/*     */   private Orientation orientation;
/*     */   private int prefColumns;
/*     */   private int prefRows;
/*     */   private double prefTileHeight;
/*     */   private double prefTileWidth;
/*     */   private Pos tileAlignment;
/*     */   private double vgap;
/*     */ 
/*     */   public static TilePaneBuilder<?> create()
/*     */   {
/*  15 */     return new TilePaneBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(TilePane paramTilePane) {
/*  23 */     super.applyTo(paramTilePane);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramTilePane.setAlignment(this.alignment); break;
/*     */       case 1:
/*  30 */         paramTilePane.setHgap(this.hgap); break;
/*     */       case 2:
/*  31 */         paramTilePane.setOrientation(this.orientation); break;
/*     */       case 3:
/*  32 */         paramTilePane.setPrefColumns(this.prefColumns); break;
/*     */       case 4:
/*  33 */         paramTilePane.setPrefRows(this.prefRows); break;
/*     */       case 5:
/*  34 */         paramTilePane.setPrefTileHeight(this.prefTileHeight); break;
/*     */       case 6:
/*  35 */         paramTilePane.setPrefTileWidth(this.prefTileWidth); break;
/*     */       case 7:
/*  36 */         paramTilePane.setTileAlignment(this.tileAlignment); break;
/*     */       case 8:
/*  37 */         paramTilePane.setVgap(this.vgap);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B alignment(Pos paramPos)
/*     */   {
/*  48 */     this.alignment = paramPos;
/*  49 */     __set(0);
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   public B hgap(double paramDouble)
/*     */   {
/*  59 */     this.hgap = paramDouble;
/*  60 */     __set(1);
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   public B orientation(Orientation paramOrientation)
/*     */   {
/*  70 */     this.orientation = paramOrientation;
/*  71 */     __set(2);
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefColumns(int paramInt)
/*     */   {
/*  81 */     this.prefColumns = paramInt;
/*  82 */     __set(3);
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefRows(int paramInt)
/*     */   {
/*  92 */     this.prefRows = paramInt;
/*  93 */     __set(4);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefTileHeight(double paramDouble)
/*     */   {
/* 103 */     this.prefTileHeight = paramDouble;
/* 104 */     __set(5);
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefTileWidth(double paramDouble)
/*     */   {
/* 114 */     this.prefTileWidth = paramDouble;
/* 115 */     __set(6);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public B tileAlignment(Pos paramPos)
/*     */   {
/* 125 */     this.tileAlignment = paramPos;
/* 126 */     __set(7);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public B vgap(double paramDouble)
/*     */   {
/* 136 */     this.vgap = paramDouble;
/* 137 */     __set(8);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   public TilePane build()
/*     */   {
/* 145 */     TilePane localTilePane = new TilePane();
/* 146 */     applyTo(localTilePane);
/* 147 */     return localTilePane;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.TilePaneBuilder
 * JD-Core Version:    0.6.2
 */