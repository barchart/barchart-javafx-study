/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.PGPolyline;
/*     */ import com.sun.javafx.sg.PGShape.Mode;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.util.List;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.paint.Paint;
/*     */ 
/*     */ public class Polyline extends Shape
/*     */ {
/*  68 */   private final Path2D shape = new Path2D();
/*     */   private final ObservableList<Double> points;
/*     */ 
/*     */   public Polyline()
/*     */   {
/*  74 */     StyleableProperty localStyleableProperty1 = StyleableProperty.getStyleableProperty(fillProperty());
/*  75 */     localStyleableProperty1.set(this, null);
/*  76 */     StyleableProperty localStyleableProperty2 = StyleableProperty.getStyleableProperty(strokeProperty());
/*  77 */     localStyleableProperty2.set(this, Color.BLACK);
/*     */ 
/* 103 */     this.points = new TrackableObservableList()
/*     */     {
/*     */       protected void onChanged(ListChangeListener.Change<Double> paramAnonymousChange) {
/* 106 */         Polyline.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 107 */         Polyline.this.impl_geomChanged();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public Polyline(double[] paramArrayOfDouble)
/*     */   {
/*  74 */     Object localObject = StyleableProperty.getStyleableProperty(fillProperty());
/*  75 */     ((StyleableProperty)localObject).set(this, null);
/*  76 */     StyleableProperty localStyleableProperty = StyleableProperty.getStyleableProperty(strokeProperty());
/*  77 */     localStyleableProperty.set(this, Color.BLACK);
/*     */ 
/* 103 */     this.points = new TrackableObservableList()
/*     */     {
/*     */       protected void onChanged(ListChangeListener.Change<Double> paramAnonymousChange) {
/* 106 */         Polyline.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 107 */         Polyline.this.impl_geomChanged();
/*     */       }
/*     */     };
/*  91 */     if (paramArrayOfDouble != null)
/*  92 */       for (double d : paramArrayOfDouble)
/*  93 */         getPoints().add(Double.valueOf(d));
/*     */   }
/*     */ 
/*     */   public final ObservableList<Double> getPoints()
/*     */   {
/* 116 */     return this.points;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 124 */     return Toolkit.getToolkit().createPGPolyline();
/*     */   }
/*     */ 
/*     */   PGPolyline getPGPolyline() {
/* 128 */     return (PGPolyline)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_computeGeomBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*     */   {
/* 137 */     if ((this.impl_mode == PGShape.Mode.EMPTY) || (getPoints().size() <= 1)) {
/* 138 */       return paramBaseBounds.makeEmpty();
/*     */     }
/*     */ 
/* 141 */     if (getPoints().size() == 2) {
/* 142 */       if ((this.impl_mode == PGShape.Mode.FILL) || (getStrokeType() == StrokeType.INSIDE)) {
/* 143 */         return paramBaseBounds.makeEmpty();
/*     */       }
/* 145 */       double d = getStrokeWidth();
/* 146 */       if (getStrokeType() == StrokeType.CENTERED) {
/* 147 */         d /= 2.0D;
/*     */       }
/* 149 */       return computeBounds(paramBaseBounds, paramBaseTransform, d, 0.5D, ((Double)getPoints().get(0)).doubleValue(), ((Double)getPoints().get(1)).doubleValue(), 0.0D, 0.0D);
/*     */     }
/*     */ 
/* 152 */     return computeShapeBounds(paramBaseBounds, paramBaseTransform, impl_configShape());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Path2D impl_configShape()
/*     */   {
/* 163 */     double d1 = ((Double)getPoints().get(0)).doubleValue();
/* 164 */     double d2 = ((Double)getPoints().get(1)).doubleValue();
/* 165 */     this.shape.reset();
/* 166 */     this.shape.moveTo((float)d1, (float)d2);
/* 167 */     int i = getPoints().size() & 0xFFFFFFFE;
/* 168 */     for (int j = 2; j < i; j += 2) {
/* 169 */       d1 = ((Double)getPoints().get(j)).doubleValue(); d2 = ((Double)getPoints().get(j + 1)).doubleValue();
/* 170 */       this.shape.lineTo((float)d1, (float)d2);
/*     */     }
/* 172 */     return this.shape;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 181 */     super.impl_updatePG();
/*     */ 
/* 183 */     if (impl_isDirty(DirtyBits.NODE_GEOMETRY)) {
/* 184 */       int i = getPoints().size() & 0xFFFFFFFE;
/* 185 */       float[] arrayOfFloat = new float[i];
/* 186 */       for (int j = 0; j < i; j++) {
/* 187 */         arrayOfFloat[j] = ((float)((Double)getPoints().get(j)).doubleValue());
/*     */       }
/* 189 */       getPGPolyline().updatePolyline(arrayOfFloat);
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Paint impl_cssGetFillInitialValue()
/*     */   {
/* 208 */     return null;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Paint impl_cssGetStrokeInitialValue()
/*     */   {
/* 220 */     return Color.BLACK;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static List<StyleableProperty> impl_CSS_STYLEABLES()
/*     */   {
/* 230 */     return Shape.impl_CSS_STYLEABLES();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public List<StyleableProperty> impl_getStyleableProperties()
/*     */   {
/* 240 */     return impl_CSS_STYLEABLES();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.Polyline
 * JD-Core Version:    0.6.2
 */