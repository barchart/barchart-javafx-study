/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.CameraImpl;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import com.sun.javafx.geom.transform.GeneralTransform3D;
/*     */ import com.sun.javafx.jmx.HighlightRegion;
/*     */ import com.sun.javafx.runtime.SystemProperties;
/*     */ import com.sun.javafx.sg.DirtyRegionContainer;
/*     */ import com.sun.javafx.sg.DirtyRegionPool;
/*     */ import com.sun.javafx.sg.prism.NGNode;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.prism.BasicStroke;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.GraphicsResource;
/*     */ import com.sun.prism.Presentable;
/*     */ import com.sun.prism.RenderingContext;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.camera.PrismPerspectiveCameraImpl;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.prism.paint.Color;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ public abstract class AbstractPainter
/*     */ {
/*  43 */   protected static boolean verbose = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */   {
/*     */     public Boolean run() {
/*  46 */       return Boolean.valueOf(Boolean.getBoolean("quantum.paint")); }  } )).booleanValue();
/*     */ 
/*  54 */   protected static final ReentrantLock renderLock = new ReentrantLock();
/*     */ 
/*  56 */   protected static final PaintCollector collector = PaintCollector.getInstance();
/*     */   protected Presentable presentable;
/*  59 */   protected RenderingContext context = null;
/*     */   protected ResourceFactory factory;
/*  61 */   protected AtomicBoolean liveRepaint = new AtomicBoolean(false);
/*     */   protected int width;
/*     */   protected int height;
/*  67 */   private boolean renderOverlay = false;
/*     */   Rectangle dirtyRect;
/*     */   RectBounds clip;
/*     */   RectBounds dirtyRegionTemp;
/*     */   DirtyRegionPool dirtyRegionPool;
/*     */   DirtyRegionContainer dirtyRegionContainer;
/*     */   Affine3D tx;
/*     */   Affine3D scaleTx;
/*     */   GeneralTransform3D viewProjTx;
/*     */   GeneralTransform3D projTx;
/*     */   NGNode root;
/*     */   NGNode overlayRoot;
/*     */   GlassScene scene;
/*     */ 
/*  82 */   protected AbstractPainter(GlassScene paramGlassScene) { if (PrismSettings.dirtyOptsEnabled) {
/*  83 */       this.tx = new Affine3D();
/*  84 */       this.viewProjTx = new GeneralTransform3D();
/*  85 */       this.projTx = new GeneralTransform3D();
/*  86 */       this.scaleTx = new Affine3D();
/*  87 */       this.clip = new RectBounds();
/*  88 */       this.dirtyRect = new Rectangle();
/*  89 */       this.dirtyRegionTemp = new RectBounds();
/*  90 */       this.dirtyRegionPool = new DirtyRegionPool(PrismSettings.dirtyRegionCount);
/*  91 */       this.dirtyRegionContainer = this.dirtyRegionPool.checkOut();
/*     */     }
/*  93 */     this.scene = paramGlassScene; }
/*     */ 
/*     */   protected void setPaintBounds(int paramInt1, int paramInt2)
/*     */   {
/*  97 */     this.width = paramInt1;
/*  98 */     this.height = paramInt2;
/*     */   }
/*     */ 
/*     */   protected void setRoot(NGNode paramNGNode) {
/* 102 */     this.root = paramNGNode;
/*     */   }
/*     */ 
/*     */   protected NGNode getRoot() {
/* 106 */     return this.root;
/*     */   }
/*     */ 
/*     */   protected void setOverlayRoot(NGNode paramNGNode) {
/* 110 */     this.overlayRoot = paramNGNode;
/*     */   }
/*     */ 
/*     */   protected NGNode getOverlayRoot() {
/* 114 */     return this.overlayRoot;
/*     */   }
/*     */ 
/*     */   protected void setRenderOverlay(boolean paramBoolean) {
/* 118 */     this.renderOverlay = paramBoolean;
/*     */   }
/*     */ 
/*     */   protected abstract void doPaint(Graphics paramGraphics);
/*     */ 
/*     */   protected abstract CameraImpl getCamera();
/*     */ 
/*     */   private void adjustPerspective(CameraImpl paramCameraImpl) {
/* 126 */     if ((paramCameraImpl instanceof PrismPerspectiveCameraImpl)) {
/* 127 */       PrismPerspectiveCameraImpl localPrismPerspectiveCameraImpl = (PrismPerspectiveCameraImpl)paramCameraImpl;
/* 128 */       this.scaleTx.setToScale(this.width / 2.0D, -this.height / 2.0D, 1.0D);
/* 129 */       this.scaleTx.translate(1.0D, -1.0D);
/* 130 */       this.projTx.mul(this.scaleTx);
/* 131 */       this.viewProjTx = localPrismPerspectiveCameraImpl.getProjViewTx(this.viewProjTx, this.width, this.height);
/* 132 */       this.projTx.mul(this.viewProjTx);
/*     */     }
/*     */   }
/*     */ 
/*     */   private int setDirtyRect(Graphics paramGraphics) {
/* 137 */     this.clip.setBounds(0.0F, 0.0F, this.width, this.height);
/* 138 */     this.dirtyRegionTemp.makeEmpty();
/* 139 */     this.dirtyRegionContainer.reset();
/* 140 */     this.tx.setToIdentity();
/* 141 */     this.projTx.setIdentity();
/* 142 */     adjustPerspective(getCamera());
/* 143 */     int i = this.root.accumulateDirtyRegions(this.clip, this.dirtyRegionTemp, this.dirtyRegionPool, this.dirtyRegionContainer, this.tx, this.projTx);
/*     */ 
/* 147 */     return i;
/*     */   }
/*     */ 
/*     */   public void paintImpl(Graphics paramGraphics) {
/* 151 */     int i = 0;
/* 152 */     if ((PrismSettings.dirtyOptsEnabled) && 
/* 153 */       (!this.scene.isEntireSceneDirty()) && (!this.renderOverlay)) {
/* 154 */       i = setDirtyRect(paramGraphics);
/* 155 */       if (i == 1)
/* 156 */         this.root.doPreCulling(this.dirtyRegionContainer, this.tx, this.projTx);
/*     */     }
/*     */     int j;
/*     */     Object localObject;
/* 162 */     if ((!PrismSettings.showDirtyRegions) && (i == 1)) {
/* 163 */       paramGraphics.setHasPreCullingBits(true);
/* 164 */       for (j = 0; j < this.dirtyRegionContainer.size(); j++) {
/* 165 */         localObject = this.dirtyRegionContainer.getDirtyRegion(j);
/*     */ 
/* 167 */         if ((((RectBounds)localObject).getWidth() > 0.0F) && (((RectBounds)localObject).getHeight() > 0.0F))
/*     */         {
/* 172 */           this.dirtyRect.setBounds((BaseBounds)localObject);
/* 173 */           paramGraphics.setClipRect(this.dirtyRect);
/* 174 */           paramGraphics.setClipRectIndex(j);
/* 175 */           doPaint(paramGraphics);
/*     */         }
/*     */       }
/*     */     } else {
/* 179 */       paramGraphics.setHasPreCullingBits(false);
/* 180 */       paramGraphics.setClipRect(null);
/* 181 */       doPaint(paramGraphics);
/*     */     }
/*     */ 
/* 184 */     if (PrismSettings.showDirtyRegions) {
/* 185 */       if (PrismSettings.showCull) {
/* 186 */         this.root.drawCullBits(paramGraphics);
/*     */       }
/* 188 */       paramGraphics.setDepthTest(false);
/* 189 */       if (i == 1) {
/* 190 */         paramGraphics.setPaint(new Color(1.0F, 0.0F, 0.0F, 0.3F));
/* 191 */         for (j = 0; j < this.dirtyRegionContainer.size(); j++) {
/* 192 */           localObject = this.dirtyRegionContainer.getDirtyRegion(j);
/* 193 */           paramGraphics.fillRect(((RectBounds)localObject).getMinX(), ((RectBounds)localObject).getMinY(), ((RectBounds)localObject).getWidth(), ((RectBounds)localObject).getHeight());
/*     */         }
/*     */       }
/*     */       else {
/* 197 */         paramGraphics.setPaint(new Color(1.0F, 0.0F, 0.0F, 0.3F));
/* 198 */         paramGraphics.fillRect(0.0F, 0.0F, this.width, this.height);
/*     */       }
/*     */     }
/*     */ 
/* 202 */     if (SystemProperties.isDebug()) {
/* 203 */       Set localSet = Toolkit.getToolkit().getHighlightedRegions();
/*     */ 
/* 205 */       if (localSet != null) {
/* 206 */         paramGraphics.setStroke(new BasicStroke(1.0F, 0, 2, 10.0F));
/*     */ 
/* 210 */         for (localObject = localSet.iterator(); ((Iterator)localObject).hasNext(); ) { HighlightRegion localHighlightRegion = (HighlightRegion)((Iterator)localObject).next();
/* 211 */           if (this.scene.equals(localHighlightRegion.getTKScene())) {
/* 212 */             paramGraphics.setPaint(new Color(1.0F, 1.0F, 1.0F, 1.0F));
/* 213 */             paramGraphics.drawRect((float)localHighlightRegion.getMinX(), (float)localHighlightRegion.getMinY(), (float)localHighlightRegion.getWidth(), (float)localHighlightRegion.getHeight());
/*     */ 
/* 217 */             paramGraphics.setPaint(new Color(0.0F, 0.0F, 0.0F, 1.0F));
/* 218 */             paramGraphics.drawRect((float)localHighlightRegion.getMinX() - 1.0F, (float)localHighlightRegion.getMinY() - 1.0F, (float)localHighlightRegion.getWidth() + 2.0F, (float)localHighlightRegion.getHeight() + 2.0F);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 226 */     if (this.renderOverlay)
/* 227 */       this.overlayRoot.render(paramGraphics);
/*     */   }
/*     */ 
/*     */   void disposePresentable()
/*     */   {
/* 232 */     if ((this.presentable instanceof GraphicsResource)) {
/* 233 */       ((GraphicsResource)this.presentable).dispose();
/*     */     }
/* 235 */     this.presentable = null;
/*     */   }
/*     */ 
/*     */   protected boolean validateStageGraphics() {
/* 239 */     if (this.scene == null) {
/* 240 */       return false;
/*     */     }
/*     */ 
/* 243 */     if (this.scene.glassStage == null) {
/* 244 */       return false;
/*     */     }
/* 246 */     return true;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.AbstractPainter
 * JD-Core Version:    0.6.2
 */