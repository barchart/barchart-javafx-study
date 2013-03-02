/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.PGPolygon;
/*     */ import com.sun.javafx.sg.PGShape.Mode;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class Polygon extends Shape
/*     */ {
/*  60 */   private final Path2D shape = new Path2D();
/*     */ 
/*  85 */   private final ObservableList<Double> points = new TrackableObservableList()
/*     */   {
/*     */     protected void onChanged(ListChangeListener.Change<Double> paramAnonymousChange) {
/*  88 */       Polygon.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/*  89 */       Polygon.this.impl_geomChanged();
/*     */     }
/*  85 */   };
/*     */ 
/*     */   public Polygon()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Polygon(double[] paramArrayOfDouble)
/*     */   {
/*  73 */     if (paramArrayOfDouble != null)
/*  74 */       for (double d : paramArrayOfDouble)
/*  75 */         getPoints().add(Double.valueOf(d));
/*     */   }
/*     */ 
/*     */   public final ObservableList<Double> getPoints()
/*     */   {
/*  97 */     return this.points;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 105 */     return Toolkit.getToolkit().createPGPolygon();
/*     */   }
/*     */ 
/*     */   PGPolygon getPGPolygon() {
/* 109 */     return (PGPolygon)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_computeGeomBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*     */   {
/* 118 */     if ((this.impl_mode == PGShape.Mode.EMPTY) || (getPoints().size() <= 1)) {
/* 119 */       return paramBaseBounds.makeEmpty();
/*     */     }
/*     */ 
/* 122 */     if (getPoints().size() == 2) {
/* 123 */       if ((this.impl_mode == PGShape.Mode.FILL) || (getStrokeType() == StrokeType.INSIDE)) {
/* 124 */         return paramBaseBounds.makeEmpty();
/*     */       }
/* 126 */       double d = getStrokeWidth();
/* 127 */       if (getStrokeType() == StrokeType.CENTERED) {
/* 128 */         d /= 2.0D;
/*     */       }
/* 130 */       return computeBounds(paramBaseBounds, paramBaseTransform, d, 0.5D, ((Double)getPoints().get(0)).doubleValue(), ((Double)getPoints().get(1)).doubleValue(), 0.0D, 0.0D);
/*     */     }
/*     */ 
/* 133 */     return computeShapeBounds(paramBaseBounds, paramBaseTransform, impl_configShape());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Path2D impl_configShape()
/*     */   {
/* 144 */     double d1 = ((Double)getPoints().get(0)).doubleValue();
/* 145 */     double d2 = ((Double)getPoints().get(1)).doubleValue();
/* 146 */     this.shape.reset();
/* 147 */     this.shape.moveTo((float)d1, (float)d2);
/* 148 */     int i = getPoints().size() & 0xFFFFFFFE;
/* 149 */     for (int j = 2; j < i; j += 2) {
/* 150 */       d1 = ((Double)getPoints().get(j)).doubleValue(); d2 = ((Double)getPoints().get(j + 1)).doubleValue();
/* 151 */       this.shape.lineTo((float)d1, (float)d2);
/*     */     }
/* 153 */     this.shape.closePath();
/* 154 */     return this.shape;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 163 */     super.impl_updatePG();
/* 164 */     if (impl_isDirty(DirtyBits.NODE_GEOMETRY)) {
/* 165 */       int i = getPoints().size() & 0xFFFFFFFE;
/* 166 */       float[] arrayOfFloat = new float[i];
/* 167 */       for (int j = 0; j < i; j++) {
/* 168 */         arrayOfFloat[j] = ((float)((Double)getPoints().get(j)).doubleValue());
/*     */       }
/* 170 */       getPGPolygon().updatePolygon(arrayOfFloat);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.Polygon
 * JD-Core Version:    0.6.2
 */