/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Vector;
/*     */ 
/*     */ class AreaIterator
/*     */   implements PathIterator
/*     */ {
/*     */   private BaseTransform transform;
/*     */   private Vector curves;
/*     */   private int index;
/*     */   private Curve prevcurve;
/*     */   private Curve thiscurve;
/*     */ 
/*     */   public AreaIterator(Vector paramVector, BaseTransform paramBaseTransform)
/*     */   {
/* 638 */     this.curves = paramVector;
/* 639 */     this.transform = paramBaseTransform;
/* 640 */     if (paramVector.size() >= 1)
/* 641 */       this.thiscurve = ((Curve)paramVector.get(0));
/*     */   }
/*     */ 
/*     */   public int getWindingRule()
/*     */   {
/* 649 */     return 1;
/*     */   }
/*     */ 
/*     */   public boolean isDone() {
/* 653 */     return (this.prevcurve == null) && (this.thiscurve == null);
/*     */   }
/*     */ 
/*     */   public void next() {
/* 657 */     if (this.prevcurve != null) {
/* 658 */       this.prevcurve = null;
/*     */     } else {
/* 660 */       this.prevcurve = this.thiscurve;
/* 661 */       this.index += 1;
/* 662 */       if (this.index < this.curves.size()) {
/* 663 */         this.thiscurve = ((Curve)this.curves.get(this.index));
/* 664 */         if ((this.thiscurve.getOrder() != 0) && (this.prevcurve.getX1() == this.thiscurve.getX0()) && (this.prevcurve.getY1() == this.thiscurve.getY0()))
/*     */         {
/* 668 */           this.prevcurve = null;
/*     */         }
/*     */       } else {
/* 671 */         this.thiscurve = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int currentSegment(float[] paramArrayOfFloat)
/*     */   {
/*     */     int i;
/*     */     int j;
/* 679 */     if (this.prevcurve != null)
/*     */     {
/* 681 */       if ((this.thiscurve == null) || (this.thiscurve.getOrder() == 0)) {
/* 682 */         return 4;
/*     */       }
/* 684 */       paramArrayOfFloat[0] = ((float)this.thiscurve.getX0());
/* 685 */       paramArrayOfFloat[1] = ((float)this.thiscurve.getY0());
/* 686 */       i = 1;
/* 687 */       j = 1; } else {
/* 688 */       if (this.thiscurve == null) {
/* 689 */         throw new NoSuchElementException("area iterator out of bounds");
/*     */       }
/* 691 */       i = this.thiscurve.getSegment(paramArrayOfFloat);
/* 692 */       j = this.thiscurve.getOrder();
/* 693 */       if (j == 0) {
/* 694 */         j = 1;
/*     */       }
/*     */     }
/* 697 */     if (this.transform != null) {
/* 698 */       this.transform.transform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, j);
/*     */     }
/* 700 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.AreaIterator
 * JD-Core Version:    0.6.2
 */