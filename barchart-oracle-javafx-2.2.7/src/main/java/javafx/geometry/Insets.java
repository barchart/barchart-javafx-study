/*     */ package javafx.geometry;
/*     */ 
/*     */ public class Insets
/*     */ {
/*  35 */   public static final Insets EMPTY = new Insets(0.0D, 0.0D, 0.0D, 0.0D);
/*     */   private double top;
/*     */   private double right;
/*     */   private double bottom;
/*     */   private double left;
/*  65 */   int hash = 0;
/*     */ 
/*     */   public final double getTop()
/*     */   {
/*  40 */     return this.top;
/*     */   }
/*     */ 
/*     */   public final double getRight()
/*     */   {
/*  46 */     return this.right;
/*     */   }
/*     */ 
/*     */   public final double getBottom()
/*     */   {
/*  52 */     return this.bottom;
/*     */   }
/*     */ 
/*     */   public final double getLeft()
/*     */   {
/*  58 */     return this.left;
/*     */   }
/*     */ 
/*     */   public Insets(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  76 */     this.top = paramDouble1;
/*  77 */     this.right = paramDouble2;
/*  78 */     this.bottom = paramDouble3;
/*  79 */     this.left = paramDouble4;
/*     */   }
/*     */ 
/*     */   public Insets(double paramDouble)
/*     */   {
/*  89 */     this.top = paramDouble;
/*  90 */     this.right = paramDouble;
/*  91 */     this.bottom = paramDouble;
/*  92 */     this.left = paramDouble;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 102 */     if (paramObject == this) return true;
/* 103 */     if ((paramObject instanceof Insets)) {
/* 104 */       Insets localInsets = (Insets)paramObject;
/* 105 */       return (this.top == localInsets.top) && (this.right == localInsets.right) && (this.bottom == localInsets.bottom) && (this.left == localInsets.left);
/*     */     }
/*     */ 
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 118 */     if (this.hash == 0) {
/* 119 */       long l = 17L;
/* 120 */       l = 37L * l + Double.doubleToLongBits(this.top);
/* 121 */       l = 37L * l + Double.doubleToLongBits(this.right);
/* 122 */       l = 37L * l + Double.doubleToLongBits(this.bottom);
/* 123 */       l = 37L * l + Double.doubleToLongBits(this.left);
/* 124 */       this.hash = ((int)(l ^ l >> 32));
/*     */     }
/* 126 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 134 */     return "Insets [top=" + this.top + ", right=" + this.right + ", bottom=" + this.bottom + ",left=" + this.left + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Insets
 * JD-Core Version:    0.6.2
 */