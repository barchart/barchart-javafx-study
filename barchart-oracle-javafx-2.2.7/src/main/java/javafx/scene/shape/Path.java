/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.scene.shape.PathUtils;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import com.sun.javafx.sg.PGPath.FillRule;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.BoundingBox;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.paint.Paint;
/*     */ 
/*     */ public class Path extends Shape
/*     */ {
/*  98 */   private Path2D path2d = null;
/*     */   private ObjectProperty<FillRule> fillRule;
/*     */   private boolean isPathValid;
/*     */   private final ObservableList<PathElement> elements;
/*     */ 
/*     */   public Path()
/*     */   {
/* 139 */     StyleableProperty localStyleableProperty1 = StyleableProperty.getStyleableProperty(fillProperty());
/* 140 */     localStyleableProperty1.set(this, null);
/* 141 */     StyleableProperty localStyleableProperty2 = StyleableProperty.getStyleableProperty(strokeProperty());
/* 142 */     localStyleableProperty2.set(this, Color.BLACK);
/*     */ 
/* 201 */     this.elements = new TrackableObservableList()
/*     */     {
/*     */       protected void onChanged(ListChangeListener.Change<PathElement> paramAnonymousChange) {
/* 204 */         ObservableList localObservableList = paramAnonymousChange.getList();
/* 205 */         int i = 0;
/* 206 */         while (paramAnonymousChange.next()) {
/* 207 */           List localList = paramAnonymousChange.getRemoved();
/* 208 */           for (int k = 0; k < paramAnonymousChange.getRemovedSize(); k++) {
/* 209 */             ((PathElement)localList.get(k)).removeNode(Path.this);
/*     */           }
/* 211 */           for (k = paramAnonymousChange.getFrom(); k < paramAnonymousChange.getTo(); k++) {
/* 212 */             ((PathElement)localObservableList.get(k)).addNode(Path.this);
/*     */           }
/* 214 */           i |= (paramAnonymousChange.getFrom() == 0 ? 1 : 0);
/*     */         }
/*     */ 
/* 220 */         if (Path.this.path2d != null) {
/* 221 */           paramAnonymousChange.reset();
/* 222 */           paramAnonymousChange.next();
/*     */ 
/* 224 */           if ((paramAnonymousChange.getFrom() == paramAnonymousChange.getList().size()) && (!paramAnonymousChange.wasRemoved()) && (paramAnonymousChange.wasAdded()))
/*     */           {
/* 226 */             for (int j = paramAnonymousChange.getFrom(); j < paramAnonymousChange.getTo(); j++)
/* 227 */               ((PathElement)localObservableList.get(j)).impl_addTo(Path.this.path2d);
/*     */           }
/*     */           else {
/* 230 */             Path.this.path2d = null;
/*     */           }
/*     */         }
/* 233 */         if (i != 0) {
/* 234 */           Path.this.isPathValid = Path.this.impl_isFirstPathElementValid();
/*     */         }
/*     */ 
/* 237 */         Path.this.impl_markDirty(DirtyBits.NODE_CONTENTS);
/* 238 */         Path.this.impl_geomChanged();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public Path(PathElement[] paramArrayOfPathElement)
/*     */   {
/* 139 */     StyleableProperty localStyleableProperty1 = StyleableProperty.getStyleableProperty(fillProperty());
/* 140 */     localStyleableProperty1.set(this, null);
/* 141 */     StyleableProperty localStyleableProperty2 = StyleableProperty.getStyleableProperty(strokeProperty());
/* 142 */     localStyleableProperty2.set(this, Color.BLACK);
/*     */ 
/* 201 */     this.elements = new TrackableObservableList()
/*     */     {
/*     */       protected void onChanged(ListChangeListener.Change<PathElement> paramAnonymousChange) {
/* 204 */         ObservableList localObservableList = paramAnonymousChange.getList();
/* 205 */         int i = 0;
/* 206 */         while (paramAnonymousChange.next()) {
/* 207 */           List localList = paramAnonymousChange.getRemoved();
/* 208 */           for (int k = 0; k < paramAnonymousChange.getRemovedSize(); k++) {
/* 209 */             ((PathElement)localList.get(k)).removeNode(Path.this);
/*     */           }
/* 211 */           for (k = paramAnonymousChange.getFrom(); k < paramAnonymousChange.getTo(); k++) {
/* 212 */             ((PathElement)localObservableList.get(k)).addNode(Path.this);
/*     */           }
/* 214 */           i |= (paramAnonymousChange.getFrom() == 0 ? 1 : 0);
/*     */         }
/*     */ 
/* 220 */         if (Path.this.path2d != null) {
/* 221 */           paramAnonymousChange.reset();
/* 222 */           paramAnonymousChange.next();
/*     */ 
/* 224 */           if ((paramAnonymousChange.getFrom() == paramAnonymousChange.getList().size()) && (!paramAnonymousChange.wasRemoved()) && (paramAnonymousChange.wasAdded()))
/*     */           {
/* 226 */             for (int j = paramAnonymousChange.getFrom(); j < paramAnonymousChange.getTo(); j++)
/* 227 */               ((PathElement)localObservableList.get(j)).impl_addTo(Path.this.path2d);
/*     */           }
/*     */           else {
/* 230 */             Path.this.path2d = null;
/*     */           }
/*     */         }
/* 233 */         if (i != 0) {
/* 234 */           Path.this.isPathValid = Path.this.impl_isFirstPathElementValid();
/*     */         }
/*     */ 
/* 237 */         Path.this.impl_markDirty(DirtyBits.NODE_CONTENTS);
/* 238 */         Path.this.impl_geomChanged();
/*     */       }
/*     */     };
/* 111 */     if (paramArrayOfPathElement != null)
/* 112 */       this.elements.addAll(paramArrayOfPathElement);
/*     */   }
/*     */ 
/*     */   public Path(Collection<? extends PathElement> paramCollection)
/*     */   {
/* 139 */     StyleableProperty localStyleableProperty1 = StyleableProperty.getStyleableProperty(fillProperty());
/* 140 */     localStyleableProperty1.set(this, null);
/* 141 */     StyleableProperty localStyleableProperty2 = StyleableProperty.getStyleableProperty(strokeProperty());
/* 142 */     localStyleableProperty2.set(this, Color.BLACK);
/*     */ 
/* 201 */     this.elements = new TrackableObservableList()
/*     */     {
/*     */       protected void onChanged(ListChangeListener.Change<PathElement> paramAnonymousChange) {
/* 204 */         ObservableList localObservableList = paramAnonymousChange.getList();
/* 205 */         int i = 0;
/* 206 */         while (paramAnonymousChange.next()) {
/* 207 */           List localList = paramAnonymousChange.getRemoved();
/* 208 */           for (int k = 0; k < paramAnonymousChange.getRemovedSize(); k++) {
/* 209 */             ((PathElement)localList.get(k)).removeNode(Path.this);
/*     */           }
/* 211 */           for (k = paramAnonymousChange.getFrom(); k < paramAnonymousChange.getTo(); k++) {
/* 212 */             ((PathElement)localObservableList.get(k)).addNode(Path.this);
/*     */           }
/* 214 */           i |= (paramAnonymousChange.getFrom() == 0 ? 1 : 0);
/*     */         }
/*     */ 
/* 220 */         if (Path.this.path2d != null) {
/* 221 */           paramAnonymousChange.reset();
/* 222 */           paramAnonymousChange.next();
/*     */ 
/* 224 */           if ((paramAnonymousChange.getFrom() == paramAnonymousChange.getList().size()) && (!paramAnonymousChange.wasRemoved()) && (paramAnonymousChange.wasAdded()))
/*     */           {
/* 226 */             for (int j = paramAnonymousChange.getFrom(); j < paramAnonymousChange.getTo(); j++)
/* 227 */               ((PathElement)localObservableList.get(j)).impl_addTo(Path.this.path2d);
/*     */           }
/*     */           else {
/* 230 */             Path.this.path2d = null;
/*     */           }
/*     */         }
/* 233 */         if (i != 0) {
/* 234 */           Path.this.isPathValid = Path.this.impl_isFirstPathElementValid();
/*     */         }
/*     */ 
/* 237 */         Path.this.impl_markDirty(DirtyBits.NODE_CONTENTS);
/* 238 */         Path.this.impl_geomChanged();
/*     */       }
/*     */     };
/* 122 */     if (paramCollection != null)
/* 123 */       this.elements.addAll(paramCollection);
/*     */   }
/*     */ 
/*     */   static PGPath.FillRule toPGFillRule(FillRule paramFillRule)
/*     */   {
/* 128 */     if (paramFillRule == FillRule.NON_ZERO) {
/* 129 */       return PGPath.FillRule.NON_ZERO;
/*     */     }
/* 131 */     return PGPath.FillRule.EVEN_ODD;
/*     */   }
/*     */ 
/*     */   void markPathDirty()
/*     */   {
/* 146 */     this.path2d = null;
/* 147 */     impl_markDirty(DirtyBits.NODE_CONTENTS);
/* 148 */     impl_geomChanged();
/*     */   }
/*     */ 
/*     */   public final void setFillRule(FillRule paramFillRule)
/*     */   {
/* 162 */     if ((this.fillRule != null) || (paramFillRule != FillRule.NON_ZERO))
/* 163 */       fillRuleProperty().set(paramFillRule);
/*     */   }
/*     */ 
/*     */   public final FillRule getFillRule()
/*     */   {
/* 168 */     return this.fillRule == null ? FillRule.NON_ZERO : (FillRule)this.fillRule.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<FillRule> fillRuleProperty() {
/* 172 */     if (this.fillRule == null) {
/* 173 */       this.fillRule = new ObjectPropertyBase(FillRule.NON_ZERO)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 177 */           Path.this.impl_markDirty(DirtyBits.NODE_CONTENTS);
/* 178 */           Path.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 183 */           return Path.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 188 */           return "fillRule";
/*     */         }
/*     */       };
/*     */     }
/* 192 */     return this.fillRule;
/*     */   }
/*     */ 
/*     */   public final ObservableList<PathElement> getElements()
/*     */   {
/* 246 */     return this.elements;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 255 */     return Toolkit.getToolkit().createPGPath();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public PGPath impl_getPGPath()
/*     */   {
/* 264 */     return (PGPath)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Path2D impl_configShape()
/*     */   {
/* 274 */     if (this.isPathValid) {
/* 275 */       if (this.path2d == null)
/* 276 */         this.path2d = PathUtils.configShape(getElements(), getFillRule() == FillRule.EVEN_ODD);
/*     */       else {
/* 278 */         this.path2d.setWindingRule(getFillRule() == FillRule.NON_ZERO ? 1 : 0);
/*     */       }
/*     */ 
/* 281 */       return this.path2d;
/*     */     }
/* 283 */     return new Path2D();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Bounds impl_computeLayoutBounds()
/*     */   {
/* 294 */     if (this.isPathValid) {
/* 295 */       return super.impl_computeLayoutBounds();
/*     */     }
/* 297 */     return new BoundingBox(0.0D, 0.0D, -1.0D, -1.0D);
/*     */   }
/*     */ 
/*     */   private boolean impl_isFirstPathElementValid() {
/* 301 */     ObservableList localObservableList = getElements();
/* 302 */     if ((localObservableList != null) && (localObservableList.size() > 0)) {
/* 303 */       PathElement localPathElement = (PathElement)localObservableList.get(0);
/* 304 */       if (!localPathElement.isAbsolute()) {
/* 305 */         System.err.printf("First element of the path can not be relative. Path: %s\n", new Object[] { this });
/* 306 */         return false;
/* 307 */       }if ((localPathElement instanceof MoveTo)) {
/* 308 */         return true;
/*     */       }
/* 310 */       System.err.printf("Missing initial moveto in path definition. Path: %s\n", new Object[] { this });
/* 311 */       return false;
/*     */     }
/*     */ 
/* 314 */     return true;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 324 */     super.impl_updatePG();
/*     */ 
/* 326 */     if (impl_isDirty(DirtyBits.NODE_CONTENTS)) {
/* 327 */       PGPath localPGPath = impl_getPGPath();
/* 328 */       if (localPGPath.acceptsPath2dOnUpdate()) {
/* 329 */         localPGPath.updateWithPath2d(impl_configShape());
/*     */       } else {
/* 331 */         localPGPath.reset();
/* 332 */         if (this.isPathValid) {
/* 333 */           localPGPath.setFillRule(toPGFillRule(getFillRule()));
/* 334 */           for (PathElement localPathElement : getElements()) {
/* 335 */             localPathElement.addTo(localPGPath);
/*     */           }
/* 337 */           localPGPath.update();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Paint impl_cssGetFillInitialValue()
/*     */   {
/* 358 */     return null;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Paint impl_cssGetStrokeInitialValue()
/*     */   {
/* 370 */     return Color.BLACK;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static List<StyleableProperty> impl_CSS_STYLEABLES()
/*     */   {
/* 380 */     return Shape.impl_CSS_STYLEABLES();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public List<StyleableProperty> impl_getStyleableProperties()
/*     */   {
/* 390 */     return impl_CSS_STYLEABLES();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.Path
 * JD-Core Version:    0.6.2
 */