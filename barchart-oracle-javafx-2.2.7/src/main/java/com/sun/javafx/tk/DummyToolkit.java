/*     */ package com.sun.javafx.tk;
/*     */ 
/*     */ import com.sun.javafx.embed.HostInterface;
/*     */ import com.sun.javafx.geom.ParallelCameraImpl;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.PerspectiveCameraImpl;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.perf.PerformanceTracker;
/*     */ import com.sun.javafx.runtime.async.AsyncOperation;
/*     */ import com.sun.javafx.runtime.async.AsyncOperationListener;
/*     */ import com.sun.javafx.scene.text.HitInfo;
/*     */ import com.sun.javafx.sg.PGArc;
/*     */ import com.sun.javafx.sg.PGCanvas;
/*     */ import com.sun.javafx.sg.PGCircle;
/*     */ import com.sun.javafx.sg.PGCubicCurve;
/*     */ import com.sun.javafx.sg.PGEllipse;
/*     */ import com.sun.javafx.sg.PGGroup;
/*     */ import com.sun.javafx.sg.PGImageView;
/*     */ import com.sun.javafx.sg.PGLine;
/*     */ import com.sun.javafx.sg.PGMediaView;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import com.sun.javafx.sg.PGPolygon;
/*     */ import com.sun.javafx.sg.PGPolyline;
/*     */ import com.sun.javafx.sg.PGQuadCurve;
/*     */ import com.sun.javafx.sg.PGRectangle;
/*     */ import com.sun.javafx.sg.PGRegion;
/*     */ import com.sun.javafx.sg.PGSVGPath;
/*     */ import com.sun.javafx.sg.PGShape.StrokeLineCap;
/*     */ import com.sun.javafx.sg.PGShape.StrokeLineJoin;
/*     */ import com.sun.javafx.sg.PGShape.StrokeType;
/*     */ import com.sun.javafx.sg.PGText;
/*     */ import com.sun.scenario.DelayedRunnable;
/*     */ import com.sun.scenario.animation.AbstractMasterTimer;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javafx.geometry.Dimension2D;
/*     */ import javafx.scene.image.Image;
/*     */ import javafx.scene.input.DragEvent;
/*     */ import javafx.scene.input.Dragboard;
/*     */ import javafx.scene.input.InputMethodEvent;
/*     */ import javafx.scene.input.InputMethodRequests;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.input.TransferMode;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.paint.ImagePattern;
/*     */ import javafx.scene.paint.LinearGradient;
/*     */ import javafx.scene.paint.RadialGradient;
/*     */ import javafx.scene.shape.PathElement;
/*     */ import javafx.scene.shape.SVGPath;
/*     */ import javafx.stage.FileChooser.ExtensionFilter;
/*     */ import javafx.stage.Modality;
/*     */ import javafx.stage.StageStyle;
/*     */ 
/*     */ public final class DummyToolkit extends Toolkit
/*     */ {
/*     */   public boolean init()
/*     */   {
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   public Object enterNestedEventLoop(Object paramObject)
/*     */   {
/* 105 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public void exitNestedEventLoop(Object paramObject1, Object paramObject2)
/*     */   {
/* 110 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public TKStage createTKStage(StageStyle paramStageStyle)
/*     */   {
/* 115 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public TKStage createTKStage(StageStyle paramStageStyle, boolean paramBoolean, Modality paramModality, TKStage paramTKStage)
/*     */   {
/* 120 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public TKStage createTKPopupStage(StageStyle paramStageStyle, Object paramObject)
/*     */   {
/* 125 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public TKStage createTKEmbeddedStage(HostInterface paramHostInterface)
/*     */   {
/* 130 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public TKSystemMenu getSystemMenu()
/*     */   {
/* 135 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public ImageLoader loadImage(String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 140 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public ImageLoader loadImage(InputStream paramInputStream, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 145 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public AsyncOperation loadImageAsync(AsyncOperationListener<? extends ImageLoader> paramAsyncOperationListener, String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 150 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public ImageLoader loadPlatformImage(Object paramObject)
/*     */   {
/* 155 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PlatformImage createPlatformImage(int paramInt1, int paramInt2)
/*     */   {
/* 160 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public void startup(Runnable paramRunnable)
/*     */   {
/* 165 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public void defer(Runnable paramRunnable)
/*     */   {
/* 170 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public Map<Object, Object> getContextMap()
/*     */   {
/* 175 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public int getRefreshRate()
/*     */   {
/* 180 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public void setAnimationRunnable(DelayedRunnable paramDelayedRunnable)
/*     */   {
/* 185 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PerformanceTracker getPerformanceTracker()
/*     */   {
/* 190 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PerformanceTracker createPerformanceTracker() {
/* 194 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public void waitFor(Toolkit.Task paramTask)
/*     */   {
/* 199 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PerspectiveCameraImpl createPerspectiveCamera()
/*     */   {
/* 204 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public ParallelCameraImpl createParallelCamera()
/*     */   {
/* 209 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   protected Object createColorPaint(Color paramColor)
/*     */   {
/* 214 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   protected Object createLinearGradientPaint(LinearGradient paramLinearGradient)
/*     */   {
/* 219 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   protected Object createRadialGradientPaint(RadialGradient paramRadialGradient)
/*     */   {
/* 224 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   protected Object createImagePatternPaint(ImagePattern paramImagePattern)
/*     */   {
/* 229 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public void accumulateStrokeBounds(Shape paramShape, float[] paramArrayOfFloat, PGShape.StrokeType paramStrokeType, double paramDouble, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat, BaseTransform paramBaseTransform)
/*     */   {
/* 234 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public boolean strokeContains(Shape paramShape, double paramDouble1, double paramDouble2, PGShape.StrokeType paramStrokeType, double paramDouble3, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat)
/*     */   {
/* 239 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public Shape createStrokedShape(Shape paramShape, PGShape.StrokeType paramStrokeType, double paramDouble, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat1, float[] paramArrayOfFloat, float paramFloat2)
/*     */   {
/* 244 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public int getKeyCodeForChar(String paramString)
/*     */   {
/* 249 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public MouseEvent convertMouseEventToFX(Object paramObject)
/*     */   {
/* 254 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public KeyEvent convertKeyEventToFX(Object paramObject)
/*     */   {
/* 259 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public DragEvent convertDragRecognizedEventToFX(Object paramObject, Dragboard paramDragboard)
/*     */   {
/* 264 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public DragEvent convertDragSourceEventToFX(Object paramObject, Dragboard paramDragboard)
/*     */   {
/* 269 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public DragEvent convertDropTargetEventToFX(Object paramObject, Dragboard paramDragboard)
/*     */   {
/* 274 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public InputMethodEvent convertInputMethodEventToFX(Object paramObject)
/*     */   {
/* 279 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public Dimension2D getBestCursorSize(int paramInt1, int paramInt2)
/*     */   {
/* 284 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public int getMaximumCursorColors()
/*     */   {
/* 289 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PathElement[] convertShapeToFXPath(Object paramObject)
/*     */   {
/* 295 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public HitInfo convertHitInfoToFX(Object paramObject)
/*     */   {
/* 300 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public Filterable toFilterable(Image paramImage)
/*     */   {
/* 305 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public FilterContext getFilterContext(Object paramObject)
/*     */   {
/* 310 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public boolean isForwardTraversalKey(KeyEvent paramKeyEvent)
/*     */   {
/* 315 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public boolean isBackwardTraversalKey(KeyEvent paramKeyEvent)
/*     */   {
/* 320 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public AbstractMasterTimer getMasterTimer()
/*     */   {
/* 325 */     return null;
/*     */   }
/*     */ 
/*     */   public FontLoader getFontLoader()
/*     */   {
/* 330 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGArc createPGArc()
/*     */   {
/* 335 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGCircle createPGCircle()
/*     */   {
/* 340 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGCubicCurve createPGCubicCurve()
/*     */   {
/* 345 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGEllipse createPGEllipse()
/*     */   {
/* 350 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGLine createPGLine()
/*     */   {
/* 355 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGPath createPGPath()
/*     */   {
/* 360 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGSVGPath createPGSVGPath()
/*     */   {
/* 365 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGPolygon createPGPolygon()
/*     */   {
/* 370 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGPolyline createPGPolyline()
/*     */   {
/* 375 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGQuadCurve createPGQuadCurve()
/*     */   {
/* 380 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGRectangle createPGRectangle()
/*     */   {
/* 385 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGImageView createPGImageView()
/*     */   {
/* 390 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGMediaView createPGMediaView()
/*     */   {
/* 395 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGGroup createPGGroup()
/*     */   {
/* 400 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGText createPGText()
/*     */   {
/* 405 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGRegion createPGRegion()
/*     */   {
/* 410 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PGCanvas createPGCanvas()
/*     */   {
/* 415 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public Object createSVGPathObject(SVGPath paramSVGPath)
/*     */   {
/* 420 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public Path2D createSVGPath2D(SVGPath paramSVGPath)
/*     */   {
/* 425 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public boolean imageContains(Object paramObject, float paramFloat1, float paramFloat2)
/*     */   {
/* 430 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public TKClipboard getSystemClipboard()
/*     */   {
/* 435 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public TKClipboard getNamedClipboard(String paramString)
/*     */   {
/* 440 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public Dragboard createDragboard()
/*     */   {
/* 445 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public ScreenConfigurationAccessor setScreenConfigurationListener(TKScreenConfigurationListener paramTKScreenConfigurationListener)
/*     */   {
/* 450 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public Object getPrimaryScreen()
/*     */   {
/* 455 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public List<?> getScreens()
/*     */   {
/* 460 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public void registerDragGestureListener(TKScene paramTKScene, Set<TransferMode> paramSet, TKDragGestureListener paramTKDragGestureListener)
/*     */   {
/* 465 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public void startDrag(Object paramObject, Set<TransferMode> paramSet, TKDragSourceListener paramTKDragSourceListener, Dragboard paramDragboard)
/*     */   {
/* 470 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public void enableDrop(TKScene paramTKScene, TKDropTargetListener paramTKDropTargetListener)
/*     */   {
/* 475 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public void installInputMethodRequests(TKScene paramTKScene, InputMethodRequests paramInputMethodRequests)
/*     */   {
/* 480 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public Object renderToImage(Toolkit.ImageRenderingContext paramImageRenderingContext)
/*     */   {
/* 485 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public boolean isExternalFormatSupported(Class paramClass)
/*     */   {
/* 490 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public Object toExternalImage(Object paramObject1, Object paramObject2)
/*     */   {
/* 495 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public KeyCode getPlatformShortcutKey()
/*     */   {
/* 500 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public List<File> showFileChooser(TKStage paramTKStage, String paramString, File paramFile, FileChooserType paramFileChooserType, List<FileChooser.ExtensionFilter> paramList)
/*     */   {
/* 509 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public File showDirectoryChooser(TKStage paramTKStage, String paramString, File paramFile)
/*     */   {
/* 517 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public long getMultiClickTime()
/*     */   {
/* 522 */     return 0L;
/*     */   }
/*     */ 
/*     */   public int getMultiClickMaxX()
/*     */   {
/* 527 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getMultiClickMaxY()
/*     */   {
/* 532 */     return 0;
/*     */   }
/*     */ 
/*     */   public void requestNextPulse()
/*     */   {
/* 537 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.DummyToolkit
 * JD-Core Version:    0.6.2
 */