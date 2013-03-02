/*     */ package javafx.geometry;
/*     */ 
/*     */ public enum Pos
/*     */ {
/*  44 */   TOP_LEFT(VPos.TOP, HPos.LEFT), 
/*     */ 
/*  49 */   TOP_CENTER(VPos.TOP, HPos.CENTER), 
/*     */ 
/*  54 */   TOP_RIGHT(VPos.TOP, HPos.RIGHT), 
/*     */ 
/*  59 */   CENTER_LEFT(VPos.CENTER, HPos.LEFT), 
/*     */ 
/*  64 */   CENTER(VPos.CENTER, HPos.CENTER), 
/*     */ 
/*  69 */   CENTER_RIGHT(VPos.CENTER, HPos.RIGHT), 
/*     */ 
/*  74 */   BOTTOM_LEFT(VPos.BOTTOM, HPos.LEFT), 
/*     */ 
/*  79 */   BOTTOM_CENTER(VPos.BOTTOM, HPos.CENTER), 
/*     */ 
/*  84 */   BOTTOM_RIGHT(VPos.BOTTOM, HPos.RIGHT), 
/*     */ 
/*  89 */   BASELINE_LEFT(VPos.BASELINE, HPos.LEFT), 
/*     */ 
/*  94 */   BASELINE_CENTER(VPos.BASELINE, HPos.CENTER), 
/*     */ 
/*  99 */   BASELINE_RIGHT(VPos.BASELINE, HPos.RIGHT);
/*     */ 
/*     */   private final VPos vpos;
/*     */   private final HPos hpos;
/*     */ 
/* 105 */   private Pos(VPos paramVPos, HPos paramHPos) { this.vpos = paramVPos;
/* 106 */     this.hpos = paramHPos;
/*     */   }
/*     */ 
/*     */   public VPos getVpos()
/*     */   {
/* 114 */     return this.vpos;
/*     */   }
/*     */ 
/*     */   public HPos getHpos()
/*     */   {
/* 122 */     return this.hpos;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Pos
 * JD-Core Version:    0.6.2
 */