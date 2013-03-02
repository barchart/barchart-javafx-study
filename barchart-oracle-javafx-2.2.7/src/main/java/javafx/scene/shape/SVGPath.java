/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.Logging;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.PGSVGPath;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.beans.property.StringPropertyBase;
/*     */ 
/*     */ public class SVGPath extends Shape
/*     */ {
/*     */   private ObjectProperty<FillRule> fillRule;
/*     */   private Path2D path2d;
/*     */   private StringProperty content;
/*     */   private Object svgPathObject;
/*     */ 
/*     */   public final void setFillRule(FillRule paramFillRule)
/*     */   {
/*  69 */     if ((this.fillRule != null) || (paramFillRule != FillRule.NON_ZERO))
/*  70 */       fillRuleProperty().set(paramFillRule);
/*     */   }
/*     */ 
/*     */   public final FillRule getFillRule()
/*     */   {
/*  75 */     return this.fillRule == null ? FillRule.NON_ZERO : (FillRule)this.fillRule.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<FillRule> fillRuleProperty() {
/*  79 */     if (this.fillRule == null) {
/*  80 */       this.fillRule = new ObjectPropertyBase(FillRule.NON_ZERO)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/*  84 */           SVGPath.this.impl_markDirty(DirtyBits.SHAPE_FILLRULE);
/*  85 */           SVGPath.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/*  90 */           return SVGPath.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/*  95 */           return "fillRule";
/*     */         }
/*     */       };
/*     */     }
/*  99 */     return this.fillRule;
/*     */   }
/*     */ 
/*     */   public final void setContent(String paramString)
/*     */   {
/* 112 */     contentProperty().set(paramString);
/*     */   }
/*     */ 
/*     */   public final String getContent() {
/* 116 */     return this.content == null ? "" : (String)this.content.get();
/*     */   }
/*     */ 
/*     */   public final StringProperty contentProperty() {
/* 120 */     if (this.content == null) {
/* 121 */       this.content = new StringPropertyBase("")
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 125 */           SVGPath.this.impl_markDirty(DirtyBits.NODE_CONTENTS);
/* 126 */           SVGPath.this.impl_geomChanged();
/* 127 */           SVGPath.this.path2d = null;
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 132 */           return SVGPath.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 137 */           return "content";
/*     */         }
/*     */       };
/*     */     }
/* 141 */     return this.content;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 153 */     return Toolkit.getToolkit().createPGSVGPath();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public PGSVGPath impl_getPGSVGPath()
/*     */   {
/* 162 */     return (PGSVGPath)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Path2D impl_configShape()
/*     */   {
/* 172 */     if (this.path2d == null)
/* 173 */       this.path2d = createSVGPath2D();
/*     */     else {
/* 175 */       this.path2d.setWindingRule(getFillRule() == FillRule.NON_ZERO ? 1 : 0);
/*     */     }
/*     */ 
/* 179 */     return this.path2d;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 189 */     super.impl_updatePG();
/*     */ 
/* 191 */     if ((impl_isDirty(DirtyBits.SHAPE_FILLRULE)) || (impl_isDirty(DirtyBits.NODE_CONTENTS)))
/*     */     {
/* 194 */       if (impl_getPGSVGPath().acceptsPath2dOnUpdate()) {
/* 195 */         if (this.svgPathObject == null) {
/* 196 */           this.svgPathObject = new Path2D();
/*     */         }
/* 198 */         Path2D localPath2D = (Path2D)this.svgPathObject;
/* 199 */         localPath2D.setTo(impl_configShape());
/*     */       } else {
/* 201 */         this.svgPathObject = createSVGPathObject();
/*     */       }
/* 203 */       impl_getPGSVGPath().setContent(this.svgPathObject);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 213 */     return "SVGPath[" + getContent() + "]";
/*     */   }
/*     */ 
/*     */   private Path2D createSVGPath2D() {
/*     */     try {
/* 218 */       return Toolkit.getToolkit().createSVGPath2D(this);
/*     */     } catch (RuntimeException localRuntimeException) {
/* 220 */       Logging.getJavaFXLogger().warning("Failed to configure svg path \"{0}\": {1}", new Object[] { getContent(), localRuntimeException.getMessage() });
/*     */     }
/*     */ 
/* 224 */     return Toolkit.getToolkit().createSVGPath2D(new SVGPath());
/*     */   }
/*     */ 
/*     */   private Object createSVGPathObject()
/*     */   {
/*     */     try {
/* 230 */       return Toolkit.getToolkit().createSVGPathObject(this);
/*     */     } catch (RuntimeException localRuntimeException) {
/* 232 */       Logging.getJavaFXLogger().warning("Failed to configure svg path \"{0}\": {1}", new Object[] { getContent(), localRuntimeException.getMessage() });
/*     */     }
/*     */ 
/* 236 */     return Toolkit.getToolkit().createSVGPathObject(new SVGPath());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.SVGPath
 * JD-Core Version:    0.6.2
 */