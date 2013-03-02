/*     */ package javafx.scene.canvas;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.jmx.MXNodeAlgorithm;
/*     */ import com.sun.javafx.jmx.MXNodeAlgorithmContext;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.sg.GrowableDataBuffer;
/*     */ import com.sun.javafx.sg.PGCanvas;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class Canvas extends Node
/*     */ {
/*     */   private static final int DEFAULT_BUF_SIZE = 1024;
/*     */   private GrowableDataBuffer<Object> theBuffer;
/*     */   private GraphicsContext theContext;
/*     */   private DoubleProperty width;
/*     */   private DoubleProperty height;
/*     */ 
/*     */   public Canvas()
/*     */   {
/*  83 */     this(0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   public Canvas(double paramDouble1, double paramDouble2)
/*     */   {
/*  93 */     setWidth(paramDouble1);
/*  94 */     setHeight(paramDouble2);
/*     */   }
/*     */ 
/*     */   GrowableDataBuffer<Object> getBuffer() {
/*  98 */     impl_markDirty(DirtyBits.NODE_CONTENTS);
/*  99 */     if (this.theBuffer == null) {
/* 100 */       this.theBuffer = new GrowableDataBuffer(1024);
/*     */     }
/* 102 */     return this.theBuffer;
/*     */   }
/*     */ 
/*     */   public GraphicsContext getGraphicsContext2D()
/*     */   {
/* 109 */     if (this.theContext == null) {
/* 110 */       this.theContext = new GraphicsContext(this);
/*     */     }
/* 112 */     return this.theContext;
/*     */   }
/*     */ 
/*     */   public final void setWidth(double paramDouble)
/*     */   {
/* 124 */     widthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getWidth() {
/* 128 */     return this.width == null ? 0.0D : this.width.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty widthProperty() {
/* 132 */     if (this.width == null) {
/* 133 */       this.width = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 137 */           Canvas.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 138 */           Canvas.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 143 */           return Canvas.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 148 */           return "width";
/*     */         }
/*     */       };
/*     */     }
/* 152 */     return this.width;
/*     */   }
/*     */ 
/*     */   public final void setHeight(double paramDouble)
/*     */   {
/* 164 */     heightProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getHeight() {
/* 168 */     return this.height == null ? 0.0D : this.height.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty heightProperty() {
/* 172 */     if (this.height == null) {
/* 173 */       this.height = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 177 */           Canvas.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 178 */           Canvas.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 183 */           return Canvas.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 188 */           return "height";
/*     */         }
/*     */       };
/*     */     }
/* 192 */     return this.height;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 201 */     return Toolkit.getToolkit().createPGCanvas();
/*     */   }
/*     */ 
/*     */   PGCanvas getPGCanvas() {
/* 205 */     return (PGCanvas)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 215 */     super.impl_updatePG();
/*     */     PGCanvas localPGCanvas;
/* 216 */     if (impl_isDirty(DirtyBits.NODE_GEOMETRY)) {
/* 217 */       localPGCanvas = getPGCanvas();
/* 218 */       localPGCanvas.updateBounds((float)getWidth(), (float)getHeight());
/*     */     }
/*     */ 
/* 221 */     if (impl_isDirty(DirtyBits.NODE_CONTENTS)) {
/* 222 */       localPGCanvas = getPGCanvas();
/* 223 */       if ((this.theBuffer != null) && (this.theBuffer.position() > 0))
/* 224 */         localPGCanvas.updateRendering(this.theBuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected boolean impl_computeContains(double paramDouble1, double paramDouble2)
/*     */   {
/* 235 */     double d1 = getWidth();
/* 236 */     double d2 = getHeight();
/* 237 */     return (d1 > 0.0D) && (d2 > 0.0D) && (paramDouble1 >= 0.0D) && (paramDouble2 >= 0.0D) && (paramDouble1 < d1) && (paramDouble2 < d2);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_computeGeomBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*     */   {
/* 249 */     paramBaseBounds = new RectBounds(0.0F, 0.0F, (float)getWidth(), (float)getHeight());
/* 250 */     paramBaseBounds = paramBaseTransform.transform(paramBaseBounds, paramBaseBounds);
/* 251 */     return paramBaseBounds;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Object impl_processMXNode(MXNodeAlgorithm paramMXNodeAlgorithm, MXNodeAlgorithmContext paramMXNodeAlgorithmContext)
/*     */   {
/* 262 */     return paramMXNodeAlgorithm.processLeafNode(this, paramMXNodeAlgorithmContext);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.canvas.Canvas
 * JD-Core Version:    0.6.2
 */