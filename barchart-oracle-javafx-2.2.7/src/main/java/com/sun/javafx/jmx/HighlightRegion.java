/*     */ package com.sun.javafx.jmx;
/*     */ 
/*     */ import com.sun.javafx.tk.TKScene;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ 
/*     */ public class HighlightRegion extends Rectangle2D
/*     */ {
/*     */   private TKScene tkScene;
/*  43 */   private int hash = 0;
/*     */ 
/*     */   public HighlightRegion(TKScene paramTKScene, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  55 */     super(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*  56 */     this.tkScene = paramTKScene;
/*     */   }
/*     */ 
/*     */   public TKScene getTKScene()
/*     */   {
/*  65 */     return this.tkScene;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  72 */     if (paramObject == this) return true;
/*  73 */     if ((paramObject instanceof HighlightRegion)) {
/*  74 */       HighlightRegion localHighlightRegion = (HighlightRegion)paramObject;
/*  75 */       return (this.tkScene.equals(localHighlightRegion.tkScene)) && (super.equals(localHighlightRegion));
/*     */     }
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  84 */     if (this.hash == 0) {
/*  85 */       long l = 7L;
/*  86 */       l = 31L * l + super.hashCode();
/*  87 */       l = 31L * l + this.tkScene.hashCode();
/*  88 */       this.hash = ((int)(l ^ l >> 32));
/*     */     }
/*  90 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 103 */     return "HighlighRegion [tkScene = " + this.tkScene + ", rectangle = " + super.toString() + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.jmx.HighlightRegion
 * JD-Core Version:    0.6.2
 */