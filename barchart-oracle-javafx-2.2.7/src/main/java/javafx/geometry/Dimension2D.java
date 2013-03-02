/*     */ package javafx.geometry;
/*     */ 
/*     */ public class Dimension2D
/*     */ {
/*     */   private double width;
/*     */   private double height;
/*  79 */   private int hash = 0;
/*     */ 
/*     */   public Dimension2D(double paramDouble1, double paramDouble2)
/*     */   {
/*  42 */     this.width = paramDouble1;
/*  43 */     this.height = paramDouble2;
/*     */   }
/*     */ 
/*     */   public final double getWidth()
/*     */   {
/*  58 */     return this.width;
/*     */   }
/*     */ 
/*     */   public final double getHeight()
/*     */   {
/*  73 */     return this.height;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  88 */     if (paramObject == this) return true;
/*  89 */     if ((paramObject instanceof Dimension2D)) {
/*  90 */       Dimension2D localDimension2D = (Dimension2D)paramObject;
/*  91 */       return (getWidth() == localDimension2D.getWidth()) && (getHeight() == localDimension2D.getHeight());
/*  92 */     }return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 100 */     if (this.hash == 0) {
/* 101 */       long l = 7L;
/* 102 */       l = 31L * l + Double.doubleToLongBits(getWidth());
/* 103 */       l = 31L * l + Double.doubleToLongBits(getHeight());
/* 104 */       this.hash = ((int)(l ^ l >> 32));
/*     */     }
/* 106 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 117 */     return "Dimension2D [width = " + getWidth() + ", height = " + getHeight() + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Dimension2D
 * JD-Core Version:    0.6.2
 */