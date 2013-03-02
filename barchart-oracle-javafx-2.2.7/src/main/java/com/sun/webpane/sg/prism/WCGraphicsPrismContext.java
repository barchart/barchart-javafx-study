/*      */ package com.sun.webpane.sg.prism;
/*      */ 
/*      */ import com.sun.javafx.font.FontStrike;
/*      */ import com.sun.javafx.font.PGFont;
/*      */ import com.sun.javafx.geom.Arc2D;
/*      */ import com.sun.javafx.geom.Path2D;
/*      */ import com.sun.javafx.geom.RectBounds;
/*      */ import com.sun.javafx.geom.Rectangle;
/*      */ import com.sun.javafx.geom.Shape;
/*      */ import com.sun.javafx.geom.transform.Affine2D;
/*      */ import com.sun.javafx.geom.transform.Affine3D;
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ import com.sun.javafx.sg.PGImageView;
/*      */ import com.sun.javafx.sg.PGNode;
/*      */ import com.sun.javafx.sg.PGPath;
/*      */ import com.sun.javafx.sg.PGRectangle;
/*      */ import com.sun.javafx.sg.PGShape.Mode;
/*      */ import com.sun.javafx.sg.PGText;
/*      */ import com.sun.javafx.sg.PGTextHelper;
/*      */ import com.sun.javafx.sg.prism.NGNode;
/*      */ import com.sun.javafx.sg.prism.NGShape;
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import com.sun.prism.BasicStroke;
/*      */ import com.sun.prism.CompositeMode;
/*      */ import com.sun.prism.Graphics;
/*      */ import com.sun.prism.Image;
/*      */ import com.sun.prism.RTTexture;
/*      */ import com.sun.prism.ReadbackGraphics;
/*      */ import com.sun.prism.RenderTarget;
/*      */ import com.sun.prism.ResourceFactory;
/*      */ import com.sun.prism.Texture;
/*      */ import com.sun.prism.paint.Color;
/*      */ import com.sun.prism.paint.Gradient;
/*      */ import com.sun.prism.paint.ImagePattern;
/*      */ import com.sun.prism.paint.Paint;
/*      */ import com.sun.scenario.effect.Blend;
/*      */ import com.sun.scenario.effect.Blend.Mode;
/*      */ import com.sun.scenario.effect.Color4f;
/*      */ import com.sun.scenario.effect.DropShadow;
/*      */ import com.sun.scenario.effect.Effect;
/*      */ import com.sun.scenario.effect.Effect.AccelType;
/*      */ import com.sun.scenario.effect.FilterContext;
/*      */ import com.sun.scenario.effect.ImageData;
/*      */ import com.sun.scenario.effect.impl.prism.PrDrawable;
/*      */ import com.sun.scenario.effect.impl.prism.PrEffectHelper;
/*      */ import com.sun.scenario.effect.impl.prism.PrFilterContext;
/*      */ import com.sun.webpane.platform.graphics.Ref;
/*      */ import com.sun.webpane.platform.graphics.RenderTheme;
/*      */ import com.sun.webpane.platform.graphics.ScrollBarTheme;
/*      */ import com.sun.webpane.platform.graphics.WCFont;
/*      */ import com.sun.webpane.platform.graphics.WCGradient;
/*      */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*      */ import com.sun.webpane.platform.graphics.WCIcon;
/*      */ import com.sun.webpane.platform.graphics.WCImage;
/*      */ import com.sun.webpane.platform.graphics.WCPath;
/*      */ import com.sun.webpane.platform.graphics.WCPoint;
/*      */ import com.sun.webpane.platform.graphics.WCRectangle;
/*      */ import com.sun.webpane.platform.graphics.WCTransform;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.util.Stack;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ 
/*      */ public class WCGraphicsPrismContext extends WCGraphicsContext
/*      */ {
/*   49 */   private static final Logger log = Logger.getLogger(WCGraphicsPrismContext.class.getName());
/*      */   private Graphics baseGraphics;
/*   54 */   private Stack<ContextState> states = new Stack();
/*      */ 
/*   56 */   private ContextState state = new ContextState();
/*      */   private boolean isInitialized;
/*   61 */   private Graphics cachedGraphics = null;
/*      */   private int fontSmoothingType;
/*  845 */   private static final BasicStroke focusRingStroke = new BasicStroke(1.1F, 0, 1, 0.0F, new float[] { 1.0F }, 0.0F);
/*      */ 
/*      */   public WCGraphicsPrismContext(Graphics paramGraphics)
/*      */   {
/*   66 */     init(paramGraphics, true);
/*      */   }
/*      */ 
/*      */   protected WCGraphicsPrismContext() {
/*      */   }
/*      */ 
/*      */   protected final void init(Graphics paramGraphics, boolean paramBoolean) {
/*   73 */     if (this.isInitialized) {
/*   74 */       return;
/*      */     }
/*   76 */     if ((paramGraphics != null) && (paramBoolean)) {
/*   77 */       this.state.setClip(paramGraphics.getClipRect());
/*   78 */       this.state.setTransform(new Affine3D(paramGraphics.getTransformNoClone()));
/*   79 */       this.state.setAlpha(paramGraphics.getExtraAlpha());
/*      */     }
/*      */ 
/*   83 */     this.baseGraphics = (paramGraphics != null ? paramGraphics.getRenderTarget().createGraphics() : null);
/*   84 */     this.isInitialized = true;
/*      */   }
/*      */ 
/*      */   protected boolean isInitialized() {
/*   88 */     return this.isInitialized;
/*      */   }
/*      */ 
/*      */   private void resetCachedGraphics() {
/*   92 */     this.cachedGraphics = null;
/*      */   }
/*      */ 
/*      */   public Object getPlatformGraphics()
/*      */   {
/*   97 */     return getGraphics(false);
/*      */   }
/*      */ 
/*      */   public Graphics getGraphics(boolean paramBoolean) {
/*  101 */     if (this.cachedGraphics == null) {
/*  102 */       localObject = this.state.getLayerNoClone();
/*  103 */       this.cachedGraphics = (localObject != null ? ((Layer)localObject).getGraphics() : this.baseGraphics);
/*      */ 
/*  107 */       this.state.apply(this.cachedGraphics);
/*      */ 
/*  109 */       if (log.isLoggable(Level.FINE)) {
/*  110 */         log.fine("getPlatformGraphics for " + this + " : " + this.cachedGraphics);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  115 */     Object localObject = this.cachedGraphics.getClipRectNoClone();
/*  116 */     return (paramBoolean) && (localObject != null) && (((Rectangle)localObject).isEmpty()) ? null : this.cachedGraphics;
/*      */   }
/*      */ 
/*      */   public void saveState()
/*      */   {
/*  123 */     this.state.markAsRestorePoint();
/*  124 */     saveStateInternal();
/*      */   }
/*      */ 
/*      */   private void saveStateInternal()
/*      */   {
/*  129 */     this.states.push(this.state);
/*  130 */     this.state = this.state.clone();
/*      */   }
/*      */ 
/*      */   private void startNewLayer(Layer paramLayer) {
/*  134 */     saveStateInternal();
/*      */ 
/*  137 */     Rectangle localRectangle = this.state.getClipNoClone();
/*      */ 
/*  141 */     Affine3D localAffine3D = new Affine3D(BaseTransform.getTranslateInstance(-localRectangle.x, -localRectangle.y));
/*      */ 
/*  144 */     localAffine3D.concatenate(this.state.getTransformNoClone());
/*      */ 
/*  147 */     localRectangle.x = 0;
/*  148 */     localRectangle.y = 0;
/*      */ 
/*  151 */     Graphics localGraphics = getGraphics(true);
/*  152 */     if ((localGraphics != null) && (localGraphics != this.baseGraphics)) {
/*  153 */       paramLayer.init(localGraphics);
/*      */     }
/*      */ 
/*  156 */     this.state.setTransform(localAffine3D);
/*  157 */     this.state.setLayer(paramLayer);
/*      */ 
/*  159 */     resetCachedGraphics();
/*      */   }
/*      */ 
/*      */   private void renderLayer(Layer paramLayer) {
/*  163 */     WCTransform localWCTransform = getTransform();
/*      */ 
/*  166 */     setTransform(new WCTransform(1.0D, 0.0D, 0.0D, 1.0D, paramLayer.getX(), paramLayer.getY()));
/*      */ 
/*  172 */     Graphics localGraphics = getGraphics(true);
/*  173 */     if (localGraphics != null) {
/*  174 */       paramLayer.render(localGraphics);
/*      */     }
/*      */ 
/*  177 */     paramLayer.dispose();
/*      */ 
/*  179 */     setTransform(localWCTransform);
/*      */   }
/*      */ 
/*      */   private void restoreStateInternal() {
/*  183 */     if (this.states.isEmpty()) {
/*  184 */       if (!$assertionsDisabled) throw new AssertionError("Unbalanced restoreState");
/*  185 */       return;
/*      */     }
/*      */ 
/*  188 */     Layer localLayer = this.state.getLayerNoClone();
/*  189 */     this.state = ((ContextState)this.states.pop());
/*  190 */     if (localLayer != this.state.getLayerNoClone()) {
/*  191 */       renderLayer(localLayer);
/*  192 */       if (log.isLoggable(Level.FINE))
/*  193 */         log.fine("Popped layer " + localLayer);
/*      */     }
/*      */     else {
/*  196 */       resetCachedGraphics();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void restoreState()
/*      */   {
/*  202 */     log.fine("restoring state");
/*      */     do
/*  204 */       restoreStateInternal();
/*  205 */     while (!this.state.isRestorePoint());
/*      */   }
/*      */ 
/*      */   public void setClip(WCPath paramWCPath, boolean paramBoolean) {
/*  209 */     Affine3D localAffine3D = new Affine3D(this.state.getTransformNoClone());
/*  210 */     paramWCPath.transform(localAffine3D.getMxx(), localAffine3D.getMyx(), localAffine3D.getMxy(), localAffine3D.getMyy(), localAffine3D.getMxt(), localAffine3D.getMyt());
/*      */ 
/*  216 */     if (!paramBoolean) {
/*  217 */       localObject = paramWCPath.getBounds();
/*  218 */       this.state.clip(new Rectangle(((WCRectangle)localObject).getIntX(), ((WCRectangle)localObject).getIntY(), ((WCRectangle)localObject).getIntWidth(), ((WCRectangle)localObject).getIntHeight()));
/*      */     }
/*      */ 
/*  225 */     Object localObject = this.state.getClipNoClone();
/*      */ 
/*  227 */     if (paramBoolean) {
/*  228 */       paramWCPath.addRect(((Rectangle)localObject).x, ((Rectangle)localObject).y, ((Rectangle)localObject).width, ((Rectangle)localObject).height);
/*      */     }
/*      */ 
/*  232 */     paramWCPath.translate(-((Rectangle)localObject).x, -((Rectangle)localObject).y);
/*      */ 
/*  234 */     ClipLayer localClipLayer = new ClipLayer(getGraphics(false), (Rectangle)localObject, paramWCPath);
/*      */ 
/*  237 */     startNewLayer(localClipLayer);
/*      */ 
/*  239 */     if (log.isLoggable(Level.FINE)) {
/*  240 */       log.fine("setClip(WCPath " + paramWCPath.getID() + ")");
/*  241 */       log.fine("Pushed layer " + localClipLayer);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Rectangle transformClip(Rectangle paramRectangle)
/*      */   {
/*  247 */     if (paramRectangle == null) {
/*  248 */       return null;
/*      */     }
/*      */ 
/*  251 */     float[] arrayOfFloat = { paramRectangle.x, paramRectangle.y, paramRectangle.x + paramRectangle.width, paramRectangle.y, paramRectangle.x, paramRectangle.y + paramRectangle.height, paramRectangle.x + paramRectangle.width, paramRectangle.y + paramRectangle.height };
/*      */ 
/*  256 */     this.state.getTransformNoClone().transform(arrayOfFloat, 0, arrayOfFloat, 0, 4);
/*  257 */     float f1 = Math.min(arrayOfFloat[0], Math.min(arrayOfFloat[2], Math.min(arrayOfFloat[4], arrayOfFloat[6])));
/*      */ 
/*  261 */     float f2 = Math.max(arrayOfFloat[0], Math.max(arrayOfFloat[2], Math.max(arrayOfFloat[4], arrayOfFloat[6])));
/*      */ 
/*  265 */     float f3 = Math.min(arrayOfFloat[1], Math.min(arrayOfFloat[3], Math.min(arrayOfFloat[5], arrayOfFloat[7])));
/*      */ 
/*  269 */     float f4 = Math.max(arrayOfFloat[1], Math.max(arrayOfFloat[3], Math.max(arrayOfFloat[5], arrayOfFloat[7])));
/*      */ 
/*  273 */     return new Rectangle(new RectBounds(f1, f3, f2, f4));
/*      */   }
/*      */ 
/*      */   public void setClip(Rectangle paramRectangle)
/*      */   {
/*  289 */     Affine3D localAffine3D = this.state.getTransformNoClone();
/*      */     Object localObject;
/*  290 */     if ((localAffine3D.getMxy() == 0.0D) && (localAffine3D.getMxz() == 0.0D) && (localAffine3D.getMyx() == 0.0D) && (localAffine3D.getMyz() == 0.0D) && (localAffine3D.getMzx() == 0.0D) && (localAffine3D.getMzy() == 0.0D))
/*      */     {
/*  295 */       this.state.clip(transformClip(paramRectangle));
/*  296 */       if (log.isLoggable(Level.FINE)) {
/*  297 */         log.log(Level.FINE, "setClip({0})", paramRectangle);
/*      */ 
/*  299 */         localObject = this.state.getClipNoClone();
/*  300 */         if ((localObject != null) && (((Rectangle)localObject).width >= 2) && (((Rectangle)localObject).height >= 2)) {
/*  301 */           WCTransform localWCTransform = getTransform();
/*      */ 
/*  303 */           setTransform(new WCTransform(1.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D));
/*      */ 
/*  308 */           Graphics localGraphics = getGraphics(true);
/*  309 */           if (localGraphics != null) {
/*  310 */             float f = (float)Math.random();
/*  311 */             localGraphics.setPaint(new Color(f, 1.0F - f, 0.5F, 0.1F));
/*      */ 
/*  316 */             localGraphics.setStroke(new BasicStroke());
/*  317 */             localGraphics.fillRect(((Rectangle)localObject).x, ((Rectangle)localObject).y, ((Rectangle)localObject).width, ((Rectangle)localObject).height);
/*      */ 
/*  319 */             localGraphics.setPaint(new Color(1.0F - f, f, 0.5F, 1.0F));
/*      */ 
/*  324 */             localGraphics.drawRect(((Rectangle)localObject).x, ((Rectangle)localObject).y, ((Rectangle)localObject).width, ((Rectangle)localObject).height);
/*      */           }
/*      */ 
/*  327 */           setTransform(localWCTransform);
/*  328 */           this.state.clip(new Rectangle(((Rectangle)localObject).x + 1, ((Rectangle)localObject).y + 1, ((Rectangle)localObject).width - 2, ((Rectangle)localObject).height - 2));
/*      */         }
/*      */       }
/*  331 */       if (this.cachedGraphics != null)
/*  332 */         this.cachedGraphics.setClipRect(this.state.getClipNoClone());
/*      */     }
/*      */     else
/*      */     {
/*  336 */       localObject = new WCPathImpl();
/*  337 */       ((WCPath)localObject).addRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*  338 */       setClip((WCPath)localObject, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setClip(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  343 */     setClip(new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */   }
/*      */ 
/*      */   public void setClip(WCRectangle paramWCRectangle) {
/*  347 */     setClip(new Rectangle((int)paramWCRectangle.getX(), (int)paramWCRectangle.getY(), (int)paramWCRectangle.getWidth(), (int)paramWCRectangle.getHeight()));
/*      */   }
/*      */ 
/*      */   public WCRectangle getClip()
/*      */   {
/*  352 */     Rectangle localRectangle = this.state.getClipNoClone();
/*  353 */     return localRectangle == null ? null : new WCRectangle(localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
/*      */   }
/*      */ 
/*      */   public void translate(float paramFloat1, float paramFloat2) {
/*  357 */     if (log.isLoggable(Level.FINE)) {
/*  358 */       log.log(Level.FINE, "translate({0},{1})", new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2) });
/*      */     }
/*  360 */     this.state.translate(paramFloat1, paramFloat2);
/*  361 */     if (this.cachedGraphics != null)
/*  362 */       this.cachedGraphics.translate(paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */   public void scale(float paramFloat1, float paramFloat2)
/*      */   {
/*  367 */     if (log.isLoggable(Level.FINE)) {
/*  368 */       log.fine("scale(" + paramFloat1 + " " + paramFloat2 + ")");
/*      */     }
/*  370 */     this.state.scale(paramFloat1, paramFloat2);
/*  371 */     if (this.cachedGraphics != null)
/*  372 */       this.cachedGraphics.scale(paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */   public void rotate(float paramFloat)
/*      */   {
/*  377 */     if (log.isLoggable(Level.FINE)) {
/*  378 */       log.fine("rotate(" + paramFloat + ")");
/*      */     }
/*  380 */     this.state.rotate(paramFloat);
/*  381 */     if (this.cachedGraphics != null)
/*  382 */       this.cachedGraphics.setTransform(this.state.getTransformNoClone());
/*      */   }
/*      */ 
/*      */   public void fillRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final Integer paramInteger)
/*      */   {
/*  388 */     if (log.isLoggable(Level.FINE)) {
/*  389 */       String str = paramInteger != null ? "fillRect(%f, %f, %f, %f, 0x%x)" : "fillRect(%f, %f, %f, %f, null)";
/*      */ 
/*  392 */       log.fine(String.format(str, new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), Float.valueOf(paramFloat3), Float.valueOf(paramFloat4), paramInteger }));
/*      */     }
/*      */ 
/*  395 */     new Composite(paramInteger, paramFloat1) {
/*      */       void doPaint(Graphics paramAnonymousGraphics) {
/*  397 */         Paint localPaint = paramInteger != null ? WCGraphicsPrismContext.createColor(paramInteger.intValue()) : WCGraphicsPrismContext.this.state.getPaintNoClone();
/*  398 */         DropShadow localDropShadow = WCGraphicsPrismContext.this.state.getShadowNoClone();
/*  399 */         if (localDropShadow != null) {
/*  400 */           PGRectangle localPGRectangle = Toolkit.getToolkit().createPGRectangle();
/*  401 */           localPGRectangle.updateRectangle(paramFloat1, paramFloat2, paramFloat3, paramFloat4, 0.0F, 0.0F);
/*  402 */           WCGraphicsPrismContext.this.render(paramAnonymousGraphics, localDropShadow, localPaint, null, localPGRectangle);
/*      */         } else {
/*  404 */           paramAnonymousGraphics.setPaint(localPaint);
/*  405 */           paramAnonymousGraphics.fillRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */         }
/*      */       }
/*      */     }
/*  395 */     .paint();
/*      */   }
/*      */ 
/*      */   public void fillRoundedRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final float paramFloat5, final float paramFloat6, final float paramFloat7, final float paramFloat8, final float paramFloat9, final float paramFloat10, final float paramFloat11, final float paramFloat12, final int paramInt)
/*      */   {
/*  415 */     if (log.isLoggable(Level.FINE)) {
/*  416 */       log.fine(String.format("fillRoundedRect(%f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, 0x%x)", new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), Float.valueOf(paramFloat3), Float.valueOf(paramFloat4), Float.valueOf(paramFloat5), Float.valueOf(paramFloat6), Float.valueOf(paramFloat7), Float.valueOf(paramFloat8), Float.valueOf(paramFloat9), Float.valueOf(paramFloat10), Float.valueOf(paramFloat11), Float.valueOf(paramFloat12), Integer.valueOf(paramInt) }));
/*      */     }
/*      */ 
/*  422 */     new Composite(paramFloat5, paramFloat7)
/*      */     {
/*      */       void doPaint(Graphics paramAnonymousGraphics)
/*      */       {
/*  427 */         float f1 = (paramFloat5 + paramFloat7 + paramFloat9 + paramFloat11) / 2.0F;
/*  428 */         float f2 = (paramFloat6 + paramFloat8 + paramFloat10 + paramFloat12) / 2.0F;
/*      */ 
/*  430 */         Color localColor = WCGraphicsPrismContext.createColor(paramInt);
/*  431 */         DropShadow localDropShadow = WCGraphicsPrismContext.this.state.getShadowNoClone();
/*  432 */         if (localDropShadow != null) {
/*  433 */           PGRectangle localPGRectangle = Toolkit.getToolkit().createPGRectangle();
/*  434 */           localPGRectangle.updateRectangle(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2);
/*  435 */           WCGraphicsPrismContext.this.render(paramAnonymousGraphics, localDropShadow, localColor, null, localPGRectangle);
/*      */         } else {
/*  437 */           paramAnonymousGraphics.setPaint(localColor);
/*  438 */           paramAnonymousGraphics.fillRoundRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2);
/*      */         }
/*      */       }
/*      */     }
/*  422 */     .paint();
/*      */   }
/*      */ 
/*      */   public void clearRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4)
/*      */   {
/*  446 */     if (log.isLoggable(Level.FINE)) {
/*  447 */       log.fine(String.format("clearRect(%f, %f, %f, %f)", new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), Float.valueOf(paramFloat3), Float.valueOf(paramFloat4) }));
/*      */     }
/*      */ 
/*  450 */     new Composite(paramFloat1, paramFloat2) {
/*      */       void doPaint(Graphics paramAnonymousGraphics) {
/*  452 */         paramAnonymousGraphics.clearQuad(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
/*      */       }
/*      */     }
/*  450 */     .paint();
/*      */   }
/*      */ 
/*      */   public void setFillColor(int paramInt)
/*      */   {
/*  459 */     if (log.isLoggable(Level.FINE)) {
/*  460 */       log.log(Level.FINE, String.format("setFillColor(0x%x)", new Object[] { Integer.valueOf(paramInt) }));
/*      */     }
/*  462 */     this.state.setPaint(createColor(paramInt));
/*      */   }
/*      */ 
/*      */   public void setFillGradient(WCGradient paramWCGradient)
/*      */   {
/*  467 */     if (log.isLoggable(Level.FINE)) {
/*  468 */       log.fine("setFillGradient(" + paramWCGradient + ")");
/*      */     }
/*  470 */     this.state.setPaint((Gradient)paramWCGradient.getPlatformGradient());
/*      */   }
/*      */ 
/*      */   public void setTextMode(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*      */   {
/*  475 */     if (log.isLoggable(Level.FINE)) {
/*  476 */       log.fine("setTextMode(fill:" + paramBoolean1 + ",stroke:" + paramBoolean2 + ",clip:" + paramBoolean3 + ")");
/*      */     }
/*  478 */     this.state.setTextMode(paramBoolean1, paramBoolean2, paramBoolean3);
/*      */   }
/*      */ 
/*      */   public void setFontSmoothingType(int paramInt)
/*      */   {
/*  483 */     this.fontSmoothingType = paramInt;
/*      */   }
/*      */ 
/*      */   public void setStrokeStyle(int paramInt)
/*      */   {
/*  488 */     if (log.isLoggable(Level.FINE)) {
/*  489 */       log.log(Level.FINE, "setStrokeStyle({0})", Integer.valueOf(paramInt));
/*      */     }
/*  491 */     this.state.getStrokeNoClone().setStyle(paramInt);
/*      */   }
/*      */ 
/*      */   public void setStrokeColor(int paramInt)
/*      */   {
/*  496 */     if (log.isLoggable(Level.FINE)) {
/*  497 */       log.log(Level.FINE, String.format("setStrokeColor(0x%x)", new Object[] { Integer.valueOf(paramInt) }));
/*      */     }
/*  499 */     this.state.getStrokeNoClone().setPaint(createColor(paramInt));
/*      */   }
/*      */ 
/*      */   public void setStrokeWidth(float paramFloat)
/*      */   {
/*  504 */     if (log.isLoggable(Level.FINE)) {
/*  505 */       log.log(Level.FINE, "setStrokeWidth({0})", new Object[] { Float.valueOf(paramFloat) });
/*      */     }
/*  507 */     this.state.getStrokeNoClone().setThickness(paramFloat);
/*      */   }
/*      */ 
/*      */   public void setStrokeGradient(WCGradient paramWCGradient)
/*      */   {
/*  512 */     if (log.isLoggable(Level.FINE)) {
/*  513 */       log.fine("setStrokeGradient(" + paramWCGradient + ")");
/*      */     }
/*  515 */     this.state.getStrokeNoClone().setPaint((Gradient)paramWCGradient.getPlatformGradient());
/*      */   }
/*      */ 
/*      */   public void setLineDash(float paramFloat, float[] paramArrayOfFloat)
/*      */   {
/*  520 */     if (log.isLoggable(Level.FINE)) {
/*  521 */       log.fine("setLineDash(" + paramFloat + ":" + paramArrayOfFloat + ")");
/*      */     }
/*  523 */     this.state.getStrokeNoClone().setDashOffset(paramFloat);
/*  524 */     this.state.getStrokeNoClone().setDashSizes(paramArrayOfFloat);
/*      */   }
/*      */ 
/*      */   public void setLineCap(int paramInt)
/*      */   {
/*  529 */     if (log.isLoggable(Level.FINE)) {
/*  530 */       log.fine("setLineCap(" + paramInt + ")");
/*      */     }
/*  532 */     this.state.getStrokeNoClone().setLineCap(paramInt);
/*      */   }
/*      */ 
/*      */   public void setLineJoin(int paramInt)
/*      */   {
/*  537 */     if (log.isLoggable(Level.FINE)) {
/*  538 */       log.fine("setLineJoin(" + paramInt + ")");
/*      */     }
/*  540 */     this.state.getStrokeNoClone().setLineJoin(paramInt);
/*      */   }
/*      */ 
/*      */   public void setMiterLimit(float paramFloat)
/*      */   {
/*  545 */     if (log.isLoggable(Level.FINE)) {
/*  546 */       log.fine("setMiterLimit(" + paramFloat + ")");
/*      */     }
/*  548 */     this.state.getStrokeNoClone().setMiterLimit(paramFloat);
/*      */   }
/*      */ 
/*      */   public void setShadow(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt)
/*      */   {
/*  553 */     if (log.isLoggable(Level.FINE)) {
/*  554 */       String str = "setShadow(%f, %f, %f, 0x%x)";
/*  555 */       log.fine(String.format(str, new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), Float.valueOf(paramFloat3), Integer.valueOf(paramInt) }));
/*      */     }
/*  557 */     this.state.setShadow(createShadow(paramFloat1, paramFloat2, paramFloat3, paramInt)); } 
/*      */   public void drawPolygon(final float[] paramArrayOfFloat, boolean paramBoolean) { // Byte code:
/*      */     //   0: getstatic 29	com/sun/webpane/sg/prism/WCGraphicsPrismContext:log	Ljava/util/logging/Logger;
/*      */     //   3: getstatic 30	java/util/logging/Level:FINE	Ljava/util/logging/Level;
/*      */     //   6: invokevirtual 31	java/util/logging/Logger:isLoggable	(Ljava/util/logging/Level;)Z
/*      */     //   9: ifeq +95 -> 104
/*      */     //   12: ldc 199
/*      */     //   14: astore_3
/*      */     //   15: iconst_0
/*      */     //   16: istore 4
/*      */     //   18: iload 4
/*      */     //   20: aload_1
/*      */     //   21: arraylength
/*      */     //   22: if_icmpge +36 -> 58
/*      */     //   25: new 32	java/lang/StringBuilder
/*      */     //   28: dup
/*      */     //   29: invokespecial 33	java/lang/StringBuilder:<init>	()V
/*      */     //   32: aload_3
/*      */     //   33: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   36: aload_1
/*      */     //   37: iload 4
/*      */     //   39: faload
/*      */     //   40: invokevirtual 144	java/lang/StringBuilder:append	(F)Ljava/lang/StringBuilder;
/*      */     //   43: ldc 200
/*      */     //   45: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   48: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   51: astore_3
/*      */     //   52: iinc 4 1
/*      */     //   55: goto -37 -> 18
/*      */     //   58: new 32	java/lang/StringBuilder
/*      */     //   61: dup
/*      */     //   62: invokespecial 33	java/lang/StringBuilder:<init>	()V
/*      */     //   65: aload_3
/*      */     //   66: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   69: ldc 201
/*      */     //   71: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   74: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   77: astore_3
/*      */     //   78: getstatic 29	com/sun/webpane/sg/prism/WCGraphicsPrismContext:log	Ljava/util/logging/Logger;
/*      */     //   81: getstatic 30	java/util/logging/Level:FINE	Ljava/util/logging/Level;
/*      */     //   84: ldc 202
/*      */     //   86: iconst_2
/*      */     //   87: anewarray 138	java/lang/Object
/*      */     //   90: dup
/*      */     //   91: iconst_0
/*      */     //   92: aload_3
/*      */     //   93: aastore
/*      */     //   94: dup
/*      */     //   95: iconst_1
/*      */     //   96: iload_2
/*      */     //   97: invokestatic 203	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
/*      */     //   100: aastore
/*      */     //   101: invokevirtual 140	java/util/logging/Logger:log	(Ljava/util/logging/Level;Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   104: new 204	com/sun/webpane/sg/prism/WCGraphicsPrismContext$4
/*      */     //   107: dup
/*      */     //   108: aload_0
/*      */     //   109: aload_1
/*      */     //   110: invokespecial 205	com/sun/webpane/sg/prism/WCGraphicsPrismContext$4:<init>	(Lcom/sun/webpane/sg/prism/WCGraphicsPrismContext;[F)V
/*      */     //   113: invokevirtual 206	com/sun/webpane/sg/prism/WCGraphicsPrismContext$4:paint	()V
/*      */     //   116: return } 
/*  596 */   public void drawLine(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4) { if (log.isLoggable(Level.FINE)) {
/*  597 */       log.log(Level.FINE, "drawLine({0}, {1}, {2}, {3})", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4) });
/*      */     }
/*      */ 
/*  601 */     new Composite(paramInt1, paramInt2) {
/*      */       void doPaint(Graphics paramAnonymousGraphics) {
/*  603 */         if (WCGraphicsPrismContext.this.state.getStrokeNoClone().apply(paramAnonymousGraphics))
/*  604 */           paramAnonymousGraphics.drawLine(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       }
/*      */     }
/*  601 */     .paint();
/*      */   }
/*      */ 
/*      */   public void drawPattern(final WCImage paramWCImage, final WCRectangle paramWCRectangle1, final WCTransform paramWCTransform, final WCPoint paramWCPoint, final WCRectangle paramWCRectangle2)
/*      */   {
/*  618 */     if (log.isLoggable(Level.FINE)) {
/*  619 */       log.log(Level.FINE, "drawPattern({0}, {1}, {2}, {3})", new Object[] { Integer.valueOf(paramWCRectangle2.getIntX()), Integer.valueOf(paramWCRectangle2.getIntY()), Integer.valueOf(paramWCRectangle2.getIntWidth()), Integer.valueOf(paramWCRectangle2.getIntHeight()) });
/*      */     }
/*      */ 
/*  625 */     if (paramWCImage != null)
/*  626 */       new Composite(paramWCPoint, paramWCRectangle1)
/*      */       {
/*      */         void doPaint(Graphics paramAnonymousGraphics)
/*      */         {
/*  630 */           float f1 = paramWCPoint.getX() + paramWCRectangle1.getX() * (float)paramWCTransform.getMatrix()[0];
/*      */ 
/*  632 */           float f2 = paramWCPoint.getY() + paramWCRectangle1.getY() * (float)paramWCTransform.getMatrix()[3];
/*      */ 
/*  634 */           float f3 = paramWCRectangle1.getWidth() * (float)paramWCTransform.getMatrix()[0];
/*      */ 
/*  636 */           float f4 = paramWCRectangle1.getHeight() * (float)paramWCTransform.getMatrix()[3];
/*      */ 
/*  639 */           Image localImage = ((PrismImage)paramWCImage).getImage();
/*      */ 
/*  642 */           if (!paramWCRectangle1.contains(new WCRectangle(0.0F, 0.0F, paramWCImage.getWidth(), paramWCImage.getHeight())))
/*      */           {
/*  644 */             localImage = localImage.createSubImage(paramWCRectangle1.getIntX(), paramWCRectangle1.getIntY(), (int)Math.ceil(paramWCRectangle1.getWidth()), (int)Math.ceil(paramWCRectangle1.getHeight()));
/*      */           }
/*      */ 
/*  649 */           paramAnonymousGraphics.setPaint(new ImagePattern(localImage, f1, f2, f3, f4, false));
/*      */ 
/*  655 */           paramAnonymousGraphics.fillRect(paramWCRectangle2.getX(), paramWCRectangle2.getY(), paramWCRectangle2.getWidth(), paramWCRectangle2.getHeight());
/*      */         }
/*      */       }
/*  626 */       .paint();
/*      */   }
/*      */ 
/*      */   public void drawImage(final WCImage paramWCImage, final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final float paramFloat5, final float paramFloat6, final float paramFloat7, final float paramFloat8)
/*      */   {
/*  667 */     if (log.isLoggable(Level.FINE)) {
/*  668 */       log.log(Level.FINE, "drawImage(img, dst({0},{1},{2},{3}), src({4},{5},{6},{7}))", new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), Float.valueOf(paramFloat3), Float.valueOf(paramFloat4), Float.valueOf(paramFloat5), Float.valueOf(paramFloat6), Float.valueOf(paramFloat7), Float.valueOf(paramFloat8) });
/*      */     }
/*      */ 
/*  674 */     if ((paramWCImage instanceof PrismImage))
/*  675 */       new Composite(paramWCImage, paramFloat1) {
/*      */         void doPaint(Graphics paramAnonymousGraphics) {
/*  677 */           PrismImage localPrismImage = (PrismImage)paramWCImage;
/*  678 */           DropShadow localDropShadow = WCGraphicsPrismContext.this.state.getShadowNoClone();
/*  679 */           if (localDropShadow != null) {
/*  680 */             PGImageView localPGImageView = Toolkit.getToolkit().createPGImageView();
/*  681 */             localPGImageView.setImage(localPrismImage.getImage());
/*  682 */             localPGImageView.setX(paramFloat1);
/*  683 */             localPGImageView.setY(paramFloat2);
/*  684 */             localPGImageView.setViewport(paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, false);
/*  685 */             localPGImageView.setContentBounds(new RectBounds(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4));
/*  686 */             WCGraphicsPrismContext.this.render(paramAnonymousGraphics, localDropShadow, null, null, localPGImageView);
/*      */           } else {
/*  688 */             localPrismImage.draw(paramAnonymousGraphics, (int)paramFloat1, (int)paramFloat2, (int)(paramFloat1 + paramFloat3), (int)(paramFloat2 + paramFloat4), (int)paramFloat5, (int)paramFloat6, (int)(paramFloat5 + paramFloat7), (int)(paramFloat6 + paramFloat8));
/*      */           }
/*      */         }
/*      */       }
/*  675 */       .paint();
/*      */   }
/*      */ 
/*      */   public void drawBitmapImage(final ByteBuffer paramByteBuffer, final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4)
/*      */   {
/*  701 */     new Composite(paramByteBuffer, paramInt3) {
/*      */       void doPaint(Graphics paramAnonymousGraphics) {
/*  703 */         paramByteBuffer.order(ByteOrder.nativeOrder());
/*  704 */         Image localImage = Image.fromByteBgraPreData(paramByteBuffer, paramInt3, paramInt4);
/*  705 */         Texture localTexture = paramAnonymousGraphics.getResourceFactory().createTexture(localImage);
/*  706 */         paramAnonymousGraphics.drawTexture(localTexture, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4, 0.0F, 0.0F, paramInt3, paramInt4);
/*  707 */         localTexture.dispose();
/*      */       }
/*      */     }
/*  701 */     .paint();
/*      */   }
/*      */ 
/*      */   public void drawIcon(WCIcon paramWCIcon, int paramInt1, int paramInt2)
/*      */   {
/*  714 */     if (log.isLoggable(Level.FINE))
/*  715 */       log.log(Level.FINE, "UNIMPLEMENTED drawIcon ({0}, {1})", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
/*      */   }
/*      */ 
/*      */   public void drawRect(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4)
/*      */   {
/*  721 */     if (log.isLoggable(Level.FINE)) {
/*  722 */       log.log(Level.FINE, "drawRect({0}, {1}, {2}, {3})", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4) });
/*      */     }
/*      */ 
/*  726 */     new Composite(paramInt1, paramInt2) {
/*      */       void doPaint(Graphics paramAnonymousGraphics) {
/*  728 */         Paint localPaint = WCGraphicsPrismContext.this.state.getPaintNoClone();
/*  729 */         if ((localPaint != null) && (localPaint.isOpaque())) {
/*  730 */           paramAnonymousGraphics.setPaint(localPaint);
/*  731 */           paramAnonymousGraphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */         }
/*      */ 
/*  734 */         if (WCGraphicsPrismContext.this.state.getStrokeNoClone().apply(paramAnonymousGraphics))
/*  735 */           paramAnonymousGraphics.drawRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       }
/*      */     }
/*  726 */     .paint();
/*      */   }
/*      */ 
/*      */   public void drawString(final WCFont paramWCFont, final int[] paramArrayOfInt, float[] paramArrayOfFloat, final float paramFloat1, final float paramFloat2)
/*      */   {
/*  748 */     if (log.isLoggable(Level.FINE)) {
/*  749 */       log.log(Level.FINE, "drawStringAsGlyph({0} {1}, {2})", new Object[] { paramArrayOfInt, Float.valueOf(paramFloat1), Float.valueOf(paramFloat2) });
/*      */     }
/*      */ 
/*  752 */     new Composite(paramArrayOfInt, paramWCFont)
/*      */     {
/*      */       void doPaint(Graphics paramAnonymousGraphics)
/*      */       {
/*  757 */         String str = new String(paramArrayOfInt, 0, paramArrayOfInt.length);
/*  758 */         PGFont localPGFont = (PGFont)paramWCFont.getPlatformFont();
/*  759 */         Paint localPaint = WCGraphicsPrismContext.this.state.isTextFill() ? WCGraphicsPrismContext.this.state.getPaintNoClone() : null;
/*      */ 
/*  762 */         BasicStroke localBasicStroke = WCGraphicsPrismContext.this.state.isTextStroke() ? WCGraphicsPrismContext.this.state.getStrokeNoClone().getPlatformStroke() : null;
/*      */ 
/*  765 */         DropShadow localDropShadow = WCGraphicsPrismContext.this.state.getShadowNoClone();
/*      */         Object localObject;
/*  766 */         if (localDropShadow != null) {
/*  767 */           localObject = Toolkit.getToolkit().createPGText();
/*  768 */           PGTextHelper localPGTextHelper = ((PGText)localObject).getTextHelper();
/*  769 */           localPGTextHelper.setText(str);
/*  770 */           localPGTextHelper.setFont(localPGFont);
/*  771 */           localPGTextHelper.setLocation(paramFloat1, paramFloat2);
/*  772 */           localPGTextHelper.setFontSmoothingType(WCGraphicsPrismContext.this.fontSmoothingType);
/*  773 */           ((PGText)localObject).updateText();
/*  774 */           WCGraphicsPrismContext.this.render(paramAnonymousGraphics, localDropShadow, localPaint, localBasicStroke, (PGNode)localObject);
/*      */         } else {
/*  776 */           localObject = localPGFont.getStrike(paramAnonymousGraphics.getTransformNoClone(), WCGraphicsPrismContext.this.fontSmoothingType);
/*  777 */           if (localPaint != null) {
/*  778 */             paramAnonymousGraphics.setPaint(localPaint);
/*  779 */             paramAnonymousGraphics.drawString(str, (FontStrike)localObject, paramFloat1, paramFloat2);
/*      */           }
/*  781 */           if (localBasicStroke != null) {
/*  782 */             localPaint = (Paint)WCGraphicsPrismContext.this.state.getStrokeNoClone().getPaint();
/*  783 */             if (localPaint != null) {
/*  784 */               paramAnonymousGraphics.setPaint(localPaint);
/*  785 */               paramAnonymousGraphics.setStroke(localBasicStroke);
/*  786 */               paramAnonymousGraphics.draw(((FontStrike)localObject).getOutline(str, BaseTransform.getTranslateInstance(paramFloat1, paramFloat2)));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  752 */     .paint();
/*      */   }
/*      */ 
/*      */   public void drawString(WCFont paramWCFont, String paramString, boolean paramBoolean, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  803 */     if (log.isLoggable(Level.FINE)) {
/*  804 */       log.log(Level.FINE, "drawString({0}, {1}, {2})", new Object[] { paramString, Float.valueOf(paramFloat3), Float.valueOf(paramFloat4) });
/*      */     }
/*      */ 
/*  810 */     int[] arrayOfInt = new int[paramInt2 - paramInt1];
/*  811 */     for (int i = 0; i < paramInt2 - paramInt1; i++) {
/*  812 */       arrayOfInt[i] = paramString.codePointAt(paramBoolean ? paramInt2 - i - 1 : paramInt1 + i);
/*      */     }
/*      */ 
/*  816 */     drawString(paramWCFont, arrayOfInt, null, paramFloat3, paramFloat4);
/*      */   }
/*      */ 
/*      */   public void setComposite(int paramInt)
/*      */   {
/*  824 */     log.log(Level.FINE, "setComposite({0})", Integer.valueOf(paramInt));
/*  825 */     this.state.setCompositeOperation(paramInt);
/*      */   }
/*      */ 
/*      */   public void drawEllipse(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4) {
/*  829 */     if (log.isLoggable(Level.FINE)) {
/*  830 */       log.log(Level.FINE, "drawEllipse({0}, {1}, {2}, {3})", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4) });
/*      */     }
/*      */ 
/*  833 */     new Composite(paramInt1, paramInt2) {
/*      */       void doPaint(Graphics paramAnonymousGraphics) {
/*  835 */         paramAnonymousGraphics.setPaint(WCGraphicsPrismContext.this.state.getPaintNoClone());
/*  836 */         paramAnonymousGraphics.fillEllipse(paramInt1, paramInt2, paramInt3, paramInt4);
/*  837 */         if (WCGraphicsPrismContext.this.state.getStrokeNoClone().apply(paramAnonymousGraphics))
/*  838 */           paramAnonymousGraphics.drawEllipse(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       }
/*      */     }
/*  833 */     .paint();
/*      */   }
/*      */ 
/*      */   public void drawFocusRing(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final int paramInt5)
/*      */   {
/*  851 */     if (log.isLoggable(Level.FINE)) {
/*  852 */       log.log(Level.FINE, String.format("drawFocusRing: %d, %d, %d, %d, 0x%x", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Integer.valueOf(paramInt5) }));
/*      */     }
/*      */ 
/*  857 */     new Composite(paramInt5, paramInt1) {
/*      */       void doPaint(Graphics paramAnonymousGraphics) {
/*  859 */         paramAnonymousGraphics.setPaint(WCGraphicsPrismContext.createColor(paramInt5));
/*  860 */         BasicStroke localBasicStroke = paramAnonymousGraphics.getStroke();
/*  861 */         paramAnonymousGraphics.setStroke(WCGraphicsPrismContext.focusRingStroke);
/*  862 */         paramAnonymousGraphics.drawRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, 4.0F, 4.0F);
/*  863 */         paramAnonymousGraphics.setStroke(localBasicStroke);
/*      */       }
/*      */     }
/*  857 */     .paint();
/*      */   }
/*      */ 
/*      */   public void setAlpha(float paramFloat)
/*      */   {
/*  869 */     log.log(Level.FINE, "setAlpha({0})", Float.valueOf(paramFloat));
/*      */ 
/*  871 */     this.state.setAlpha(paramFloat);
/*      */ 
/*  873 */     if (null != this.cachedGraphics)
/*  874 */       this.cachedGraphics.setExtraAlpha(this.state.getAlpha());
/*      */   }
/*      */ 
/*      */   public float getAlpha()
/*      */   {
/*  879 */     return this.state.getAlpha();
/*      */   }
/*      */ 
/*      */   public void beginTransparencyLayer(float paramFloat) {
/*  883 */     TransparencyLayer localTransparencyLayer = new TransparencyLayer(getGraphics(false), this.state.getClipNoClone(), paramFloat);
/*      */ 
/*  886 */     if (log.isLoggable(Level.FINE)) {
/*  887 */       log.fine(String.format("beginTransparencyLayer(%s)", new Object[] { localTransparencyLayer }));
/*      */     }
/*      */ 
/*  891 */     this.state.markAsRestorePoint();
/*      */ 
/*  893 */     startNewLayer(localTransparencyLayer);
/*      */   }
/*      */ 
/*      */   public void endTransparencyLayer() {
/*  897 */     if (log.isLoggable(Level.FINE)) {
/*  898 */       log.fine(String.format("endTransparencyLayer(%s)", new Object[] { this.state.getLayerNoClone() }));
/*      */     }
/*      */ 
/*  902 */     restoreState();
/*      */   }
/*      */ 
/*      */   public void drawWidget(RenderTheme paramRenderTheme, Ref paramRef, int paramInt1, int paramInt2)
/*      */   {
/*  907 */     paramRenderTheme.drawWidget(this, paramRef, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public void drawScrollbar(ScrollBarTheme paramScrollBarTheme, Ref paramRef, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/*  914 */     paramScrollBarTheme.paint(this, paramRef, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */   private static Rectangle intersect(Rectangle paramRectangle1, Rectangle paramRectangle2) {
/*  918 */     if (paramRectangle1 == null) {
/*  919 */       return paramRectangle2;
/*      */     }
/*  921 */     RectBounds localRectBounds = paramRectangle1.toRectBounds();
/*  922 */     localRectBounds.intersectWith(paramRectangle2);
/*  923 */     paramRectangle1.setBounds(localRectBounds);
/*  924 */     return paramRectangle1;
/*      */   }
/*      */ 
/*      */   static Color createColor(int paramInt) {
/*  928 */     float f1 = (0xFF & paramInt >> 24) / 255.0F;
/*  929 */     float f2 = (0xFF & paramInt >> 16) / 255.0F;
/*  930 */     float f3 = (0xFF & paramInt >> 8) / 255.0F;
/*  931 */     float f4 = (0xFF & paramInt) / 255.0F;
/*  932 */     return new Color(f2, f3, f4, f1);
/*      */   }
/*      */ 
/*      */   private static Color4f createColor4f(int paramInt) {
/*  936 */     float f1 = (0xFF & paramInt >> 24) / 255.0F;
/*  937 */     float f2 = (0xFF & paramInt >> 16) / 255.0F;
/*  938 */     float f3 = (0xFF & paramInt >> 8) / 255.0F;
/*  939 */     float f4 = (0xFF & paramInt) / 255.0F;
/*  940 */     return new Color4f(f2, f3, f4, f1);
/*      */   }
/*      */ 
/*      */   private DropShadow createShadow(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt) {
/*  944 */     if ((paramFloat1 == 0.0F) && (paramFloat2 == 0.0F) && (paramFloat3 == 0.0F)) {
/*  945 */       return null;
/*      */     }
/*  947 */     DropShadow localDropShadow = new DropShadow();
/*  948 */     localDropShadow.setOffsetX((int)paramFloat1);
/*  949 */     localDropShadow.setOffsetY((int)paramFloat2);
/*  950 */     localDropShadow.setRadius(paramFloat3 > 127.0F ? 127.0F : paramFloat3 < 0.0F ? 0.0F : paramFloat3);
/*  951 */     localDropShadow.setColor(createColor4f(paramInt));
/*  952 */     return localDropShadow;
/*      */   }
/*      */ 
/*      */   private void render(Graphics paramGraphics, Effect paramEffect, Paint paramPaint, BasicStroke paramBasicStroke, PGNode paramPGNode) {
/*  956 */     if ((paramPGNode instanceof NGShape)) {
/*  957 */       NGShape localNGShape = (NGShape)paramPGNode;
/*  958 */       Shape localShape = localNGShape.getShape();
/*  959 */       Paint localPaint = (Paint)this.state.getStrokeNoClone().getPaint();
/*  960 */       if ((paramBasicStroke != null) && (localPaint != null)) {
/*  961 */         localShape = paramBasicStroke.createStrokedShape(localShape);
/*  962 */         localNGShape.setDrawStroke(paramBasicStroke);
/*  963 */         localNGShape.setDrawPaint(localPaint);
/*  964 */         localNGShape.setMode(paramPaint == null ? PGShape.Mode.STROKE : PGShape.Mode.STROKE_FILL);
/*      */       } else {
/*  966 */         localNGShape.setMode(paramPaint == null ? PGShape.Mode.EMPTY : PGShape.Mode.FILL);
/*      */       }
/*  968 */       localNGShape.setFillPaint(paramPaint);
/*  969 */       localNGShape.setContentBounds(localShape.getBounds());
/*      */     }
/*  971 */     boolean bool = paramGraphics.hasPreCullingBits();
/*  972 */     paramGraphics.setHasPreCullingBits(false);
/*  973 */     paramPGNode.setEffect(paramEffect);
/*  974 */     ((NGNode)paramPGNode).render(paramGraphics);
/*  975 */     paramGraphics.setHasPreCullingBits(bool);
/*      */   }
/*      */ 
/*      */   public void strokeArc(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final int paramInt5, final int paramInt6)
/*      */   {
/* 1420 */     if (log.isLoggable(Level.FINE)) {
/* 1421 */       log.fine(String.format("strokeArc(%d, %d, %d, %d, %d, %d)", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Integer.valueOf(paramInt5), Integer.valueOf(paramInt6) }));
/*      */     }
/*      */ 
/* 1424 */     new Composite(paramInt1, paramInt2) {
/*      */       void doPaint(Graphics paramAnonymousGraphics) {
/* 1426 */         if (WCGraphicsPrismContext.this.state.getStrokeNoClone().apply(paramAnonymousGraphics))
/* 1427 */           paramAnonymousGraphics.draw(new Arc2D(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 0));
/*      */       }
/*      */     }
/* 1424 */     .paint();
/*      */   }
/*      */ 
/*      */   public WCImage getImage()
/*      */   {
/* 1434 */     return null;
/*      */   }
/*      */ 
/*      */   public void strokeRect(final float paramFloat1, final float paramFloat2, final float paramFloat3, final float paramFloat4, final float paramFloat5)
/*      */   {
/* 1439 */     if (log.isLoggable(Level.FINE)) {
/* 1440 */       log.fine(String.format("strokeRect_FFFFF(%f, %f, %f, %f, %f)", new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), Float.valueOf(paramFloat3), Float.valueOf(paramFloat4), Float.valueOf(paramFloat5) }));
/*      */     }
/*      */ 
/* 1444 */     new Composite(paramFloat5, paramFloat1) {
/*      */       void doPaint(Graphics paramAnonymousGraphics) {
/* 1446 */         paramAnonymousGraphics.setStroke(new BasicStroke(paramFloat5, 0, 0, Math.max(1.0F, paramFloat5)));
/*      */ 
/* 1451 */         Paint localPaint = (Paint)WCGraphicsPrismContext.this.state.getStrokeNoClone().getPaint();
/* 1452 */         if (localPaint == null) {
/* 1453 */           localPaint = WCGraphicsPrismContext.this.state.getPaintNoClone();
/*      */         }
/* 1455 */         paramAnonymousGraphics.setPaint(localPaint);
/* 1456 */         paramAnonymousGraphics.drawRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */       }
/*      */     }
/* 1444 */     .paint();
/*      */   }
/*      */ 
/*      */   public void strokePath(final WCPath paramWCPath)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: getstatic 29	com/sun/webpane/sg/prism/WCGraphicsPrismContext:log	Ljava/util/logging/Logger;
/*      */     //   3: ldc_w 297
/*      */     //   6: invokevirtual 39	java/util/logging/Logger:fine	(Ljava/lang/String;)V
/*      */     //   9: aload_1
/*      */     //   10: ifnull +15 -> 25
/*      */     //   13: new 298	com/sun/webpane/sg/prism/WCGraphicsPrismContext$15
/*      */     //   16: dup
/*      */     //   17: aload_0
/*      */     //   18: aload_1
/*      */     //   19: invokespecial 299	com/sun/webpane/sg/prism/WCGraphicsPrismContext$15:<init>	(Lcom/sun/webpane/sg/prism/WCGraphicsPrismContext;Lcom/sun/webpane/platform/graphics/WCPath;)V
/*      */     //   22: invokevirtual 300	com/sun/webpane/sg/prism/WCGraphicsPrismContext$15:paint	()V
/*      */     //   25: return
/*      */   }
/*      */ 
/*      */   public void fillPath(final WCPath paramWCPath)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: getstatic 29	com/sun/webpane/sg/prism/WCGraphicsPrismContext:log	Ljava/util/logging/Logger;
/*      */     //   3: ldc_w 301
/*      */     //   6: invokevirtual 39	java/util/logging/Logger:fine	(Ljava/lang/String;)V
/*      */     //   9: aload_1
/*      */     //   10: ifnull +15 -> 25
/*      */     //   13: new 302	com/sun/webpane/sg/prism/WCGraphicsPrismContext$16
/*      */     //   16: dup
/*      */     //   17: aload_0
/*      */     //   18: aload_1
/*      */     //   19: invokespecial 303	com/sun/webpane/sg/prism/WCGraphicsPrismContext$16:<init>	(Lcom/sun/webpane/sg/prism/WCGraphicsPrismContext;Lcom/sun/webpane/platform/graphics/WCPath;)V
/*      */     //   22: invokevirtual 304	com/sun/webpane/sg/prism/WCGraphicsPrismContext$16:paint	()V
/*      */     //   25: return
/*      */   }
/*      */ 
/*      */   public void setTransform(WCTransform paramWCTransform)
/*      */   {
/* 1509 */     double[] arrayOfDouble = paramWCTransform.getMatrix();
/* 1510 */     Affine3D localAffine3D = new Affine3D(new Affine2D(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], arrayOfDouble[3], arrayOfDouble[4], arrayOfDouble[5]));
/* 1511 */     this.state.setTransform(localAffine3D);
/* 1512 */     resetCachedGraphics();
/*      */   }
/*      */ 
/*      */   public WCTransform getTransform() {
/* 1516 */     Affine3D localAffine3D = this.state.getTransformNoClone();
/* 1517 */     return new WCTransform(localAffine3D.getMxx(), localAffine3D.getMyx(), localAffine3D.getMxy(), localAffine3D.getMyy(), localAffine3D.getMxt(), localAffine3D.getMyt());
/*      */   }
/*      */ 
/*      */   public void concatTransform(WCTransform paramWCTransform)
/*      */   {
/* 1523 */     double[] arrayOfDouble = paramWCTransform.getMatrix();
/* 1524 */     Affine3D localAffine3D = new Affine3D(new Affine2D(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], arrayOfDouble[3], arrayOfDouble[4], arrayOfDouble[5]));
/* 1525 */     this.state.concatTransform(localAffine3D);
/* 1526 */     resetCachedGraphics();
/*      */   }
/*      */ 
/*      */   public void dispose() {
/* 1530 */     if (!this.states.isEmpty()) {
/* 1531 */       new IllegalStateException("Unbalanced saveState/restoreState").printStackTrace();
/*      */     }
/*      */ 
/* 1534 */     while (!this.states.isEmpty()) {
/* 1535 */       restoreStateInternal();
/*      */     }
/*      */ 
/* 1538 */     Layer localLayer = this.state.getLayerNoClone();
/* 1539 */     if (localLayer != null)
/* 1540 */       renderLayer(localLayer);
/*      */   }
/*      */ 
/*      */   public WCGradient createLinearGradient(WCPoint paramWCPoint1, WCPoint paramWCPoint2)
/*      */   {
/* 1546 */     return new WCLinearGradient(paramWCPoint1, paramWCPoint2);
/*      */   }
/*      */ 
/*      */   public WCGradient createRadialGradient(WCPoint paramWCPoint1, float paramFloat1, WCPoint paramWCPoint2, float paramFloat2)
/*      */   {
/* 1551 */     return new WCRadialGradient(paramWCPoint1, paramFloat1, paramWCPoint2, paramFloat2);
/*      */   }
/*      */ 
/*      */   public static class PassThrough extends Effect
/*      */   {
/*      */     private final PrDrawable img;
/*      */     private final int width;
/*      */     private final int height;
/*      */ 
/*      */     public PassThrough(PrDrawable paramPrDrawable, int paramInt1, int paramInt2)
/*      */     {
/* 1390 */       this.img = paramPrDrawable;
/* 1391 */       this.width = paramInt1;
/* 1392 */       this.height = paramInt2;
/*      */     }
/*      */ 
/*      */     public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*      */     {
/* 1401 */       return new ImageData(paramFilterContext, this.img, new Rectangle((int)paramBaseTransform.getMxt(), (int)paramBaseTransform.getMyt(), this.width, this.height));
/*      */     }
/*      */ 
/*      */     public RectBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*      */     {
/* 1409 */       return null;
/*      */     }
/*      */ 
/*      */     public Effect.AccelType getAccelType(FilterContext paramFilterContext) {
/* 1413 */       return Effect.AccelType.INTRINSIC;
/*      */     }
/*      */   }
/*      */ 
/*      */   private abstract class Composite
/*      */   {
/*      */     private Composite()
/*      */     {
/*      */     }
/*      */ 
/*      */     abstract void doPaint(Graphics paramGraphics);
/*      */ 
/*      */     public void paint()
/*      */     {
/* 1265 */       paint(WCGraphicsPrismContext.this.getGraphics(true));
/*      */     }
/*      */ 
/*      */     public void paint(Graphics paramGraphics) {
/* 1269 */       if (paramGraphics != null) {
/* 1270 */         CompositeMode localCompositeMode = paramGraphics.getCompositeMode();
/* 1271 */         switch (WCGraphicsPrismContext.this.state.getCompositeOperation())
/*      */         {
/*      */         case 1:
/* 1274 */           paramGraphics.setCompositeMode(CompositeMode.SRC);
/* 1275 */           doPaint(paramGraphics);
/* 1276 */           paramGraphics.setCompositeMode(localCompositeMode);
/* 1277 */           break;
/*      */         case 2:
/* 1279 */           paramGraphics.setCompositeMode(CompositeMode.SRC_OVER);
/* 1280 */           doPaint(paramGraphics);
/* 1281 */           paramGraphics.setCompositeMode(localCompositeMode);
/* 1282 */           break;
/*      */         default:
/* 1285 */           blend(paramGraphics);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     private void blend(Graphics paramGraphics)
/*      */     {
/* 1292 */       PrFilterContext localPrFilterContext = PrFilterContext.getInstance(paramGraphics.getAssociatedScreen());
/* 1293 */       PrDrawable localPrDrawable1 = null;
/* 1294 */       PrDrawable localPrDrawable2 = null;
/* 1295 */       ReadbackGraphics localReadbackGraphics = null;
/* 1296 */       RTTexture localRTTexture = null;
/* 1297 */       Rectangle localRectangle = WCGraphicsPrismContext.this.state.getClipNoClone();
/* 1298 */       WCImage localWCImage = WCGraphicsPrismContext.this.getImage();
/*      */       try {
/* 1300 */         if ((localWCImage != null) && ((localWCImage instanceof PrismImage)))
/*      */         {
/* 1302 */           localPrDrawable1 = (PrDrawable)Effect.createCompatibleImage(localPrFilterContext, localRectangle.width, localRectangle.height);
/* 1303 */           localGraphics = localPrDrawable1.createGraphics();
/* 1304 */           ((PrismImage)localWCImage).draw(localGraphics, 0, 0, localRectangle.width, localRectangle.height, localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
/*      */         }
/*      */         else
/*      */         {
/* 1309 */           localReadbackGraphics = (ReadbackGraphics)paramGraphics;
/* 1310 */           localRTTexture = localReadbackGraphics.readBack(localRectangle);
/* 1311 */           localPrDrawable1 = PrDrawable.create(localPrFilterContext, localRTTexture);
/*      */         }
/*      */ 
/* 1314 */         localPrDrawable2 = (PrDrawable)Effect.createCompatibleImage(localPrFilterContext, localRectangle.width, localRectangle.height);
/* 1315 */         Graphics localGraphics = localPrDrawable2.createGraphics();
/* 1316 */         WCGraphicsPrismContext.this.state.apply(localGraphics);
/* 1317 */         doPaint(localGraphics);
/*      */ 
/* 1319 */         paramGraphics.clear();
/* 1320 */         PrEffectHelper.render(createEffect(localPrDrawable1, localPrDrawable2, localRectangle.width, localRectangle.height), paramGraphics, 0.0F, 0.0F, null);
/*      */       }
/*      */       finally {
/* 1323 */         if (localPrDrawable2 != null) {
/* 1324 */           Effect.releaseCompatibleImage(localPrFilterContext, localPrDrawable2);
/*      */         }
/* 1326 */         if (localPrDrawable1 != null)
/* 1327 */           if ((localReadbackGraphics != null) && (localRTTexture != null))
/* 1328 */             localReadbackGraphics.releaseReadBackBuffer(localRTTexture);
/*      */           else
/* 1330 */             Effect.releaseCompatibleImage(localPrFilterContext, localPrDrawable1);
/*      */       }
/*      */     }
/*      */ 
/*      */     private Effect createBlend(Blend.Mode paramMode, PrDrawable paramPrDrawable1, PrDrawable paramPrDrawable2, int paramInt1, int paramInt2)
/*      */     {
/* 1343 */       return new Blend(paramMode, new WCGraphicsPrismContext.PassThrough(paramPrDrawable1, paramInt1, paramInt2), new WCGraphicsPrismContext.PassThrough(paramPrDrawable2, paramInt1, paramInt2));
/*      */     }
/*      */ 
/*      */     private Effect createEffect(PrDrawable paramPrDrawable1, PrDrawable paramPrDrawable2, int paramInt1, int paramInt2)
/*      */     {
/* 1354 */       switch (WCGraphicsPrismContext.this.state.getCompositeOperation()) {
/*      */       case 0:
/*      */       case 10:
/* 1357 */         return new Blend(Blend.Mode.SRC_OVER, createBlend(Blend.Mode.SRC_OUT, paramPrDrawable1, paramPrDrawable2, paramInt1, paramInt2), createBlend(Blend.Mode.SRC_OUT, paramPrDrawable2, paramPrDrawable1, paramInt1, paramInt2));
/*      */       case 3:
/* 1363 */         return createBlend(Blend.Mode.SRC_IN, paramPrDrawable1, paramPrDrawable2, paramInt1, paramInt2);
/*      */       case 4:
/* 1365 */         return createBlend(Blend.Mode.SRC_OUT, paramPrDrawable1, paramPrDrawable2, paramInt1, paramInt2);
/*      */       case 5:
/* 1367 */         return createBlend(Blend.Mode.SRC_ATOP, paramPrDrawable1, paramPrDrawable2, paramInt1, paramInt2);
/*      */       case 6:
/* 1369 */         return createBlend(Blend.Mode.SRC_OVER, paramPrDrawable2, paramPrDrawable1, paramInt1, paramInt2);
/*      */       case 7:
/* 1371 */         return createBlend(Blend.Mode.SRC_IN, paramPrDrawable2, paramPrDrawable1, paramInt1, paramInt2);
/*      */       case 8:
/* 1373 */         return createBlend(Blend.Mode.SRC_OUT, paramPrDrawable2, paramPrDrawable1, paramInt1, paramInt2);
/*      */       case 9:
/* 1375 */         return createBlend(Blend.Mode.SRC_ATOP, paramPrDrawable2, paramPrDrawable1, paramInt1, paramInt2);
/*      */       case 12:
/* 1377 */         return createBlend(Blend.Mode.ADD, paramPrDrawable1, paramPrDrawable2, paramInt1, paramInt2);
/*      */       case 1:
/*      */       case 2:
/* 1379 */       case 11: } return createBlend(Blend.Mode.SRC_OVER, paramPrDrawable1, paramPrDrawable2, paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ClipLayer extends WCGraphicsPrismContext.Layer
/*      */   {
/*      */     private WCPath normalizedToClipPath;
/*      */ 
/*      */     public ClipLayer(Graphics paramGraphics, Rectangle paramRectangle, WCPath paramWCPath)
/*      */     {
/* 1213 */       super(paramRectangle);
/* 1214 */       this.normalizedToClipPath = paramWCPath;
/*      */     }
/*      */ 
/*      */     public void init(Graphics paramGraphics) {
/* 1218 */       RTTexture localRTTexture = null;
/* 1219 */       ReadbackGraphics localReadbackGraphics = null;
/*      */       try {
/* 1221 */         localReadbackGraphics = (ReadbackGraphics)paramGraphics;
/* 1222 */         localRTTexture = localReadbackGraphics.readBack(this.bounds);
/* 1223 */         getGraphics().drawTexture(localRTTexture, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
/*      */       } finally {
/* 1225 */         if ((localReadbackGraphics != null) && (localRTTexture != null))
/* 1226 */           localReadbackGraphics.releaseReadBackBuffer(localRTTexture);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void render(Graphics paramGraphics)
/*      */     {
/* 1233 */       Path2D localPath2D = ((WCPathImpl)this.normalizedToClipPath).getPlatformPath();
/* 1234 */       PGPath localPGPath = Toolkit.getToolkit().createPGPath();
/* 1235 */       localPGPath.updateWithPath2d(localPath2D);
/* 1236 */       localPGPath.setFillPaint(Color.BLACK);
/*      */ 
/* 1239 */       PrDrawable localPrDrawable = (PrDrawable)Effect.getCompatibleImage(this.fctx, this.bounds.width, this.bounds.height);
/*      */ 
/* 1241 */       Graphics localGraphics = localPrDrawable.createGraphics();
/*      */ 
/* 1243 */       ((NGNode)localPGPath).render(localGraphics);
/*      */ 
/* 1246 */       Blend localBlend = new Blend(Blend.Mode.SRC_IN, new WCGraphicsPrismContext.PassThrough(localPrDrawable, this.bounds.width, this.bounds.height), new WCGraphicsPrismContext.PassThrough(this.buffer, this.bounds.width, this.bounds.height));
/*      */ 
/* 1249 */       PrEffectHelper.render(localBlend, paramGraphics, 0.0F, 0.0F, null);
/*      */ 
/* 1251 */       Effect.releaseCompatibleImage(this.fctx, localPrDrawable);
/*      */     }
/*      */ 
/*      */     public String toString() {
/* 1255 */       return String.format("ClipLayer[%d,%d + %dx%d, path %s]", new Object[] { Integer.valueOf(this.bounds.x), Integer.valueOf(this.bounds.y), Integer.valueOf(this.bounds.width), Integer.valueOf(this.bounds.height), this.normalizedToClipPath });
/*      */     }
/*      */   }
/*      */ 
/*      */   private class TransparencyLayer extends WCGraphicsPrismContext.Layer
/*      */   {
/*      */     private float opacity;
/*      */ 
/*      */     public TransparencyLayer(Graphics paramRectangle, Rectangle paramFloat, float arg4)
/*      */     {
/* 1183 */       super(paramFloat);
/*      */       Object localObject;
/* 1184 */       this.opacity = localObject;
/*      */     }
/*      */ 
/*      */     public void init(Graphics paramGraphics) {
/* 1188 */       WCGraphicsPrismContext.this.state.setCompositeOperation(2); } 
/*      */     public void render(Graphics paramGraphics) { // Byte code:
/*      */       //   0: new 6	com/sun/webpane/sg/prism/WCGraphicsPrismContext$TransparencyLayer$1
/*      */       //   3: dup
/*      */       //   4: aload_0
/*      */       //   5: invokespecial 7	com/sun/webpane/sg/prism/WCGraphicsPrismContext$TransparencyLayer$1:<init>	(Lcom/sun/webpane/sg/prism/WCGraphicsPrismContext$TransparencyLayer;)V
/*      */       //   8: aload_1
/*      */       //   9: invokevirtual 8	com/sun/webpane/sg/prism/WCGraphicsPrismContext$TransparencyLayer$1:paint	(Lcom/sun/prism/Graphics;)V
/*      */       //   12: return } 
/* 1204 */     public String toString() { return String.format("TransparencyLayer[%d,%d + %dx%d, opacity %.2f]", new Object[] { Integer.valueOf(this.bounds.x), Integer.valueOf(this.bounds.y), Integer.valueOf(this.bounds.width), Integer.valueOf(this.bounds.height), Float.valueOf(this.opacity) }); }
/*      */ 
/*      */   }
/*      */ 
/*      */   private static abstract class Layer
/*      */   {
/*      */     FilterContext fctx;
/*      */     PrDrawable buffer;
/*      */     Graphics graphics;
/*      */     final Rectangle bounds;
/*      */ 
/*      */     Layer(Graphics paramGraphics, Rectangle paramRectangle)
/*      */     {
/* 1151 */       this.bounds = new Rectangle(paramRectangle);
/*      */ 
/* 1154 */       int i = Math.max(paramRectangle.width, 1);
/* 1155 */       int j = Math.max(paramRectangle.height, 1);
/* 1156 */       this.fctx = PrFilterContext.getInstance(paramGraphics.getAssociatedScreen());
/* 1157 */       this.buffer = ((PrDrawable)Effect.getCompatibleImage(this.fctx, i, j));
/*      */     }
/*      */ 
/*      */     public Graphics getGraphics() {
/* 1161 */       if (this.graphics == null) {
/* 1162 */         this.graphics = this.buffer.createGraphics();
/*      */       }
/* 1164 */       return this.graphics;
/*      */     }
/*      */ 
/*      */     public abstract void init(Graphics paramGraphics);
/*      */ 
/*      */     public abstract void render(Graphics paramGraphics);
/*      */ 
/*      */     public void dispose() {
/* 1172 */       Effect.releaseCompatibleImage(this.fctx, this.buffer);
/*      */     }
/*      */     public double getX() {
/* 1175 */       return this.bounds.x; } 
/* 1176 */     public double getY() { return this.bounds.y; }
/*      */ 
/*      */   }
/*      */ 
/*      */   private static class ContextState
/*      */   {
/*  980 */     private final WCStrokeImpl stroke = new WCStrokeImpl();
/*      */     private Rectangle clip;
/*      */     private Paint paint;
/*      */     private float alpha;
/*  985 */     private boolean textFill = true;
/*  986 */     private boolean textStroke = false;
/*  987 */     private boolean textClip = false;
/*  988 */     private boolean restorePoint = false;
/*      */     private DropShadow shadow;
/*      */     private Affine3D xform;
/*      */     private WCGraphicsPrismContext.Layer layer;
/*      */     private int compositeOperation;
/*      */ 
/*      */     public ContextState()
/*      */     {
/*  996 */       this.clip = null;
/*  997 */       this.paint = Color.BLACK;
/*  998 */       this.stroke.setPaint(Color.BLACK);
/*  999 */       this.alpha = 1.0F;
/* 1000 */       this.xform = new Affine3D();
/* 1001 */       this.compositeOperation = 2;
/*      */     }
/*      */ 
/*      */     private ContextState(ContextState paramContextState) {
/* 1005 */       this.stroke.copyFrom(paramContextState.getStrokeNoClone());
/* 1006 */       setPaint(paramContextState.getPaintNoClone());
/* 1007 */       this.clip = paramContextState.getClipNoClone();
/* 1008 */       if (this.clip != null) {
/* 1009 */         this.clip = new Rectangle(this.clip);
/*      */       }
/* 1011 */       this.xform = new Affine3D(paramContextState.getTransformNoClone());
/* 1012 */       setShadow(paramContextState.getShadowNoClone());
/* 1013 */       setLayer(paramContextState.getLayerNoClone());
/* 1014 */       setAlpha(paramContextState.getAlpha());
/* 1015 */       setTextMode(paramContextState.isTextFill(), paramContextState.isTextStroke(), paramContextState.isTextClip());
/* 1016 */       setCompositeOperation(paramContextState.getCompositeOperation());
/*      */     }
/*      */ 
/*      */     protected ContextState clone()
/*      */     {
/* 1021 */       return new ContextState(this);
/*      */     }
/*      */ 
/*      */     public void apply(Graphics paramGraphics)
/*      */     {
/* 1026 */       paramGraphics.setTransform(getTransformNoClone());
/* 1027 */       paramGraphics.setClipRect(getClipNoClone());
/* 1028 */       paramGraphics.setExtraAlpha(getAlpha());
/*      */     }
/*      */ 
/*      */     public int getCompositeOperation() {
/* 1032 */       return this.compositeOperation;
/*      */     }
/*      */ 
/*      */     public void setCompositeOperation(int paramInt) {
/* 1036 */       this.compositeOperation = paramInt;
/*      */     }
/*      */ 
/*      */     public WCStrokeImpl getStrokeNoClone()
/*      */     {
/* 1041 */       return this.stroke;
/*      */     }
/*      */ 
/*      */     public Paint getPaintNoClone() {
/* 1045 */       return this.paint;
/*      */     }
/*      */ 
/*      */     public void setPaint(Paint paramPaint) {
/* 1049 */       this.paint = paramPaint;
/*      */     }
/*      */ 
/*      */     public Rectangle getClipNoClone()
/*      */     {
/* 1054 */       return this.clip;
/*      */     }
/*      */ 
/*      */     public WCGraphicsPrismContext.Layer getLayerNoClone() {
/* 1058 */       return this.layer;
/*      */     }
/*      */ 
/*      */     public void setLayer(WCGraphicsPrismContext.Layer paramLayer) {
/* 1062 */       this.layer = paramLayer;
/*      */     }
/*      */ 
/*      */     public void setClip(Rectangle paramRectangle) {
/* 1066 */       this.clip = paramRectangle;
/*      */     }
/*      */ 
/*      */     public void clip(Rectangle paramRectangle) {
/* 1070 */       if (null == this.clip)
/* 1071 */         this.clip = paramRectangle;
/*      */       else
/* 1073 */         this.clip.intersectWith(paramRectangle);
/*      */     }
/*      */ 
/*      */     public void setAlpha(float paramFloat)
/*      */     {
/* 1078 */       this.alpha = paramFloat;
/*      */     }
/*      */ 
/*      */     public float getAlpha() {
/* 1082 */       return this.alpha;
/*      */     }
/*      */ 
/*      */     public void setTextMode(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 1086 */       this.textFill = paramBoolean1;
/* 1087 */       this.textStroke = paramBoolean2;
/* 1088 */       this.textClip = paramBoolean3;
/*      */     }
/*      */ 
/*      */     public boolean isTextFill() {
/* 1092 */       return this.textFill;
/*      */     }
/*      */ 
/*      */     public boolean isTextStroke() {
/* 1096 */       return this.textStroke;
/*      */     }
/*      */ 
/*      */     public boolean isTextClip() {
/* 1100 */       return this.textClip;
/*      */     }
/*      */ 
/*      */     public void markAsRestorePoint() {
/* 1104 */       this.restorePoint = true;
/*      */     }
/*      */ 
/*      */     public boolean isRestorePoint() {
/* 1108 */       return this.restorePoint;
/*      */     }
/*      */ 
/*      */     public void setShadow(DropShadow paramDropShadow) {
/* 1112 */       this.shadow = paramDropShadow;
/*      */     }
/*      */ 
/*      */     public DropShadow getShadowNoClone() {
/* 1116 */       return this.shadow;
/*      */     }
/*      */ 
/*      */     public Affine3D getTransformNoClone() {
/* 1120 */       return this.xform;
/*      */     }
/*      */ 
/*      */     public void setTransform(Affine3D paramAffine3D) {
/* 1124 */       this.xform.setTransform(paramAffine3D);
/*      */     }
/*      */ 
/*      */     public void concatTransform(Affine3D paramAffine3D) {
/* 1128 */       this.xform.concatenate(paramAffine3D);
/*      */     }
/*      */ 
/*      */     public void translate(double paramDouble1, double paramDouble2) {
/* 1132 */       this.xform.translate(paramDouble1, paramDouble2);
/*      */     }
/*      */ 
/*      */     public void scale(double paramDouble1, double paramDouble2) {
/* 1136 */       this.xform.scale(paramDouble1, paramDouble2);
/*      */     }
/*      */ 
/*      */     public void rotate(double paramDouble) {
/* 1140 */       this.xform.rotate(paramDouble);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCGraphicsPrismContext
 * JD-Core Version:    0.6.2
 */