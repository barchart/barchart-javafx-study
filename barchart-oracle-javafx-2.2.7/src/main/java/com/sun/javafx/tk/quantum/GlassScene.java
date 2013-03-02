/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.javafx.geom.CameraImpl;
/*     */ import com.sun.javafx.geom.PickRay;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.Vec3d;
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import com.sun.javafx.geom.transform.GeneralTransform3D;
/*     */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.prism.NGNode;
/*     */ import com.sun.javafx.sg.prism.SceneChangeListener;
/*     */ import com.sun.javafx.tk.TKDragGestureListener;
/*     */ import com.sun.javafx.tk.TKDragSourceListener;
/*     */ import com.sun.javafx.tk.TKDropTargetListener;
/*     */ import com.sun.javafx.tk.TKScene;
/*     */ import com.sun.javafx.tk.TKSceneListener;
/*     */ import com.sun.javafx.tk.TKScenePaintListener;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.prism.camera.PrismCameraImpl;
/*     */ import com.sun.prism.camera.PrismParallelCameraImpl;
/*     */ import com.sun.prism.camera.PrismPerspectiveCameraImpl;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AccessControlContext;
/*     */ import javafx.scene.input.Dragboard;
/*     */ import javafx.scene.input.InputMethodRequests;
/*     */ 
/*     */ public abstract class GlassScene
/*     */   implements TKScene, SceneChangeListener
/*     */ {
/*     */   protected boolean verbose;
/*     */   protected GlassStage glassStage;
/*     */   protected TKSceneListener sceneListener;
/*     */   protected TKDragGestureListener dragGestureListener;
/*     */   protected TKDragSourceListener dragSourceListener;
/*     */   protected TKDropTargetListener dropTargetListener;
/*     */   protected InputMethodRequests inputMethodRequests;
/*     */   private TKScenePaintListener scenePaintListener;
/*     */   protected NGNode root;
/*     */   protected PrismCameraImpl camera;
/*     */   protected Paint fillPaint;
/*     */   private boolean dirty;
/*  59 */   private boolean entireSceneDirty = true;
/*     */ 
/*  61 */   private boolean depthBuffer = false;
/*     */ 
/*  63 */   private AccessControlContext accessCtrlCtx = null;
/*     */ 
/* 219 */   private static final double[] projMatValues = new double[16];
/* 220 */   private static final Vec3d ptCc = new Vec3d();
/* 221 */   private static final Vec3d ptEc = new Vec3d();
/* 222 */   private static final Vec3d ptWc = new Vec3d();
/* 223 */   private static final Vec3d eyeEc = new Vec3d();
/* 224 */   private static final Vec3d eyeWc = new Vec3d();
/*     */ 
/*     */   protected GlassScene(boolean paramBoolean)
/*     */   {
/*  66 */     this(paramBoolean, false);
/*     */   }
/*     */ 
/*     */   protected GlassScene(boolean paramBoolean1, boolean paramBoolean2) {
/*  70 */     this.verbose = paramBoolean1;
/*  71 */     this.depthBuffer = paramBoolean2;
/*     */   }
/*     */ 
/*     */   boolean hasDepthBuffer() {
/*  75 */     return this.depthBuffer;
/*     */   }
/*     */ 
/*     */   protected abstract boolean isSynchronous();
/*     */ 
/*     */   public void setScene(Object paramObject)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setTKSceneListener(TKSceneListener paramTKSceneListener) {
/*  85 */     this.sceneListener = paramTKSceneListener;
/*     */   }
/*     */ 
/*     */   public synchronized void setTKScenePaintListener(TKScenePaintListener paramTKScenePaintListener) {
/*  89 */     this.scenePaintListener = paramTKScenePaintListener;
/*     */   }
/*     */ 
/*     */   public void setTKDropTargetListener(TKDropTargetListener paramTKDropTargetListener) {
/*  93 */     this.dropTargetListener = paramTKDropTargetListener;
/*     */   }
/*     */ 
/*     */   public void setTKDragSourceListener(TKDragSourceListener paramTKDragSourceListener) {
/*  97 */     this.dragSourceListener = paramTKDragSourceListener;
/*     */   }
/*     */ 
/*     */   public void setTKDragGestureListener(TKDragGestureListener paramTKDragGestureListener) {
/* 101 */     this.dragGestureListener = paramTKDragGestureListener;
/*     */   }
/*     */ 
/*     */   public void setInputMethodRequests(InputMethodRequests paramInputMethodRequests) {
/* 105 */     this.inputMethodRequests = paramInputMethodRequests;
/*     */   }
/*     */ 
/*     */   final AccessControlContext getAccessControlContext()
/*     */   {
/* 110 */     if (this.accessCtrlCtx == null) {
/* 111 */       throw new RuntimeException("Scene security context has not been set!");
/*     */     }
/* 113 */     return this.accessCtrlCtx;
/*     */   }
/*     */ 
/*     */   public final void setSecurityContext(AccessControlContext paramAccessControlContext) {
/* 117 */     if (this.accessCtrlCtx != null) {
/* 118 */       throw new RuntimeException("Scene security context has been already set!");
/*     */     }
/* 120 */     this.accessCtrlCtx = paramAccessControlContext;
/*     */   }
/*     */ 
/*     */   public void setRoot(PGNode paramPGNode)
/*     */   {
/* 125 */     this.root = ((NGNode)paramPGNode);
/* 126 */     entireSceneNeedsRepaint();
/*     */   }
/*     */ 
/*     */   protected PGNode getRoot() {
/* 130 */     return this.root;
/*     */   }
/*     */ 
/*     */   public void setCamera(CameraImpl paramCameraImpl)
/*     */   {
/* 135 */     if (paramCameraImpl != null)
/* 136 */       this.camera = ((PrismCameraImpl)paramCameraImpl);
/*     */     else {
/* 138 */       this.camera = PrismParallelCameraImpl.getInstance();
/*     */     }
/* 140 */     entireSceneNeedsRepaint();
/*     */   }
/*     */ 
/*     */   public void setFillPaint(Object paramObject)
/*     */   {
/* 145 */     this.fillPaint = ((Paint)paramObject);
/* 146 */     entireSceneNeedsRepaint();
/*     */   }
/*     */ 
/*     */   public void setCursor(Object paramObject)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void markDirty()
/*     */   {
/* 157 */     sceneChanged();
/*     */   }
/*     */ 
/*     */   public void entireSceneNeedsRepaint() {
/* 161 */     this.entireSceneDirty = true;
/* 162 */     sceneChanged();
/*     */   }
/*     */ 
/*     */   public boolean isEntireSceneDirty() {
/* 166 */     return this.entireSceneDirty;
/*     */   }
/*     */ 
/*     */   public void clearEntireSceneDirty() {
/* 170 */     this.entireSceneDirty = false;
/*     */   }
/*     */ 
/*     */   void clearDirty() {
/* 174 */     this.dirty = false;
/*     */   }
/*     */ 
/*     */   public void requestFocus() {
/* 178 */     if (this.glassStage != null)
/* 179 */       this.glassStage.requestFocus();
/*     */   }
/*     */ 
/*     */   public Dragboard createDragboard()
/*     */   {
/* 185 */     return Toolkit.getToolkit().createDragboard();
/*     */   }
/*     */ 
/*     */   void setGlassStage(GlassStage paramGlassStage) {
/* 189 */     this.glassStage = paramGlassStage;
/* 190 */     if (this.glassStage != null) {
/* 191 */       sceneChanged();
/*     */     }
/*     */     else
/*     */     {
/* 196 */       PaintCollector.getInstance().removeDirtyScene(this);
/* 197 */       clearDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   void repaint()
/*     */   {
/*     */   }
/*     */ 
/*     */   void stageVisible(boolean paramBoolean)
/*     */   {
/* 209 */     if ((!paramBoolean) && (PrismSettings.forceRepaint)) {
/* 210 */       PaintCollector.getInstance().removeDirtyScene(this);
/* 211 */       clearDirty();
/*     */     }
/* 213 */     if (paramBoolean)
/* 214 */       PaintCollector.getInstance().addDirtyScene(this);
/*     */   }
/*     */ 
/*     */   public PickRay computePickRay(float paramFloat1, float paramFloat2, PickRay paramPickRay)
/*     */   {
/* 227 */     GeneralTransform3D localGeneralTransform3D = this.camera.getProjectionTransform(null);
/* 228 */     localGeneralTransform3D.get(projMatValues);
/*     */ 
/* 230 */     Affine3D localAffine3D = this.camera.getViewTransform(null);
/* 231 */     Rectangle localRectangle = this.camera.getViewport(null);
/*     */ 
/* 233 */     double d1 = paramFloat1 / localRectangle.width * 2.0D - 1.0D;
/* 234 */     double d2 = paramFloat2 / localRectangle.height * -2.0D + 1.0D;
/*     */ 
/* 236 */     if (!(this.camera instanceof PrismPerspectiveCameraImpl))
/*     */     {
/* 238 */       System.err.println("Picking not implemented for parallel projection");
/*     */     }
/*     */     else {
/* 241 */       double[] arrayOfDouble = projMatValues;
/* 242 */       double d3 = (1.0D - arrayOfDouble[15]) / arrayOfDouble[14];
/* 243 */       double d4 = arrayOfDouble[10] * d3 + arrayOfDouble[11];
/* 244 */       ptCc.set(d1, d2, d4);
/*     */ 
/* 248 */       localGeneralTransform3D.invert();
/*     */ 
/* 251 */       localGeneralTransform3D.transform(ptCc, ptEc);
/*     */       try
/*     */       {
/* 258 */         localAffine3D.invert();
/*     */       } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/* 260 */         String str = ViewScene.class.getName();
/* 261 */         PlatformLogger.getLogger(str).severe("computePickRay", localNoninvertibleTransformException);
/*     */       }
/*     */ 
/* 264 */       localAffine3D.transform(ptEc, ptWc);
/*     */ 
/* 266 */       eyeEc.set(0.0D, 0.0D, 0.0D);
/* 267 */       localAffine3D.transform(eyeEc, eyeWc);
/*     */ 
/* 276 */       if (paramPickRay == null) {
/* 277 */         paramPickRay = new PickRay();
/*     */       }
/* 279 */       paramPickRay.getOriginNoClone().set(eyeWc);
/* 280 */       paramPickRay.getDirectionNoClone().sub(ptWc, eyeWc);
/*     */     }
/*     */ 
/* 283 */     return paramPickRay;
/*     */   }
/*     */ 
/*     */   public void sceneChanged()
/*     */   {
/* 289 */     if ((this.glassStage instanceof PopupStage)) {
/* 290 */       ((PopupStage)this.glassStage).getOwnerScene().sceneChanged();
/*     */     }
/* 292 */     if (this.glassStage != null)
/*     */     {
/* 296 */       PaintCollector.getInstance().addDirtyScene(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final synchronized void frameRendered() {
/* 301 */     if (this.scenePaintListener != null)
/* 302 */       this.scenePaintListener.frameRendered();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassScene
 * JD-Core Version:    0.6.2
 */