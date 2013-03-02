/*      */ package com.sun.javafx.sg.prism;
/*      */ 
/*      */ import com.sun.javafx.font.PGFont;
/*      */ import com.sun.javafx.geom.Arc2D;
/*      */ import com.sun.javafx.geom.BaseBounds;
/*      */ import com.sun.javafx.geom.Path2D;
/*      */ import com.sun.javafx.geom.PathIterator;
/*      */ import com.sun.javafx.geom.RectBounds;
/*      */ import com.sun.javafx.geom.Rectangle;
/*      */ import com.sun.javafx.geom.Shape;
/*      */ import com.sun.javafx.geom.transform.Affine2D;
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*      */ import com.sun.javafx.sg.GrowableDataBuffer;
/*      */ import com.sun.javafx.sg.PGCanvas;
/*      */ import com.sun.javafx.sg.PGShape.Mode;
/*      */ import com.sun.prism.BasicStroke;
/*      */ import com.sun.prism.CompositeMode;
/*      */ import com.sun.prism.Graphics;
/*      */ import com.sun.prism.Image;
/*      */ import com.sun.prism.RTTexture;
/*      */ import com.sun.prism.ResourceFactory;
/*      */ import com.sun.prism.Texture;
/*      */ import com.sun.prism.paint.Color;
/*      */ import com.sun.prism.paint.Paint;
/*      */ import com.sun.scenario.effect.Blend;
/*      */ import com.sun.scenario.effect.Blend.Mode;
/*      */ import com.sun.scenario.effect.Effect;
/*      */ import com.sun.scenario.effect.Effect.AccelType;
/*      */ import com.sun.scenario.effect.FilterContext;
/*      */ import com.sun.scenario.effect.Filterable;
/*      */ import com.sun.scenario.effect.ImageData;
/*      */ import com.sun.scenario.effect.impl.prism.PrDrawable;
/*      */ import com.sun.scenario.effect.impl.prism.PrFilterContext;
/*      */ import com.sun.scenario.effect.impl.prism.PrTexture;
/*      */ import java.nio.IntBuffer;
/*      */ import java.util.LinkedList;
/*      */ import javafx.geometry.VPos;
/*      */ import javafx.scene.text.Font;
/*      */ 
/*      */ public class NGCanvas extends NGNode
/*      */   implements PGCanvas
/*      */ {
/*      */   GrowableDataBuffer<Object> thebuf;
/*      */   int tw;
/*      */   int th;
/*      */   RenderBuf cv;
/*      */   RenderBuf temp;
/*      */   RenderBuf clip;
/*      */   Image tmpImage;
/*      */   Blend blender;
/*      */   float globalAlpha;
/*      */   byte fillRule;
/*      */   Blend.Mode blendmode;
/*      */   Paint fillPaint;
/*      */   Paint strokePaint;
/*      */   float linewidth;
/*      */   int linecap;
/*      */   int linejoin;
/*      */   float miterlimit;
/*      */   BasicStroke stroke;
/*      */   Path2D path;
/*      */   NGText ngtext;
/*      */   PGFont pgfont;
/*      */   int align;
/*      */   int baseline;
/*      */   Affine2D transform;
/*      */   Affine2D inverseTransform;
/*      */   boolean inversedirty;
/*      */   LinkedList<Path2D> clipStack;
/*      */   Effect effect;
/*  202 */   static float[] TEMP_COORDS = new float[6];
/*  203 */   static Arc2D TEMP_ARC = new Arc2D();
/*  204 */   static RectBounds TEMP_RECTBOUNDS = new RectBounds();
/*      */   int arctype;
/*      */   Shape untransformedPath;
/*  418 */   private static final int[] prcaps = { 0, 1, 2 };
/*      */ 
/*  423 */   private static final int[] prjoins = { 0, 1, 2 };
/*      */ 
/*  428 */   private static final int[] prbases = { VPos.TOP.ordinal(), VPos.CENTER.ordinal(), VPos.BASELINE.ordinal(), VPos.BOTTOM.ordinal() };
/*      */ 
/*  434 */   private static final Affine2D TEMP_TX = new Affine2D();
/*  435 */   private static final Affine2D TEMP_PATH_TX = new Affine2D();
/*      */ 
/*      */   public NGCanvas()
/*      */   {
/*  175 */     this.cv = new RenderBuf(InitType.PRESERVE_UPPER_LEFT);
/*  176 */     this.temp = new RenderBuf(InitType.CLEAR);
/*  177 */     this.clip = new RenderBuf(InitType.FILL_WHITE);
/*  178 */     this.tmpImage = Image.fromIntArgbPreData(new int[1], 1, 1);
/*      */ 
/*  180 */     this.blender = new MyBlend(Blend.Mode.SRC_OVER, null, null);
/*      */ 
/*  182 */     this.globalAlpha = 1.0F;
/*  183 */     this.fillRule = 0;
/*  184 */     this.blendmode = Blend.Mode.SRC_OVER;
/*  185 */     this.fillPaint = Color.BLACK;
/*  186 */     this.strokePaint = Color.BLACK;
/*  187 */     this.linewidth = 1.0F;
/*  188 */     this.linecap = 2;
/*  189 */     this.linejoin = 0;
/*  190 */     this.miterlimit = 10.0F;
/*  191 */     this.stroke = null;
/*  192 */     this.path = new Path2D();
/*  193 */     this.ngtext = new NGText();
/*  194 */     this.pgfont = ((PGFont)Font.getDefault().impl_getNativeFont());
/*  195 */     this.align = 0;
/*  196 */     this.baseline = VPos.BASELINE.ordinal();
/*  197 */     this.transform = new Affine2D();
/*  198 */     this.inverseTransform = new Affine2D();
/*      */ 
/*  200 */     this.clipStack = new LinkedList();
/*      */ 
/*  206 */     this.untransformedPath = new Shape()
/*      */     {
/*      */       public RectBounds getBounds()
/*      */       {
/*  210 */         if (NGCanvas.this.transform.isTranslateOrIdentity()) {
/*  211 */           RectBounds localRectBounds = NGCanvas.this.path.getBounds();
/*  212 */           if (NGCanvas.this.transform.isIdentity()) {
/*  213 */             return localRectBounds;
/*      */           }
/*  215 */           float f1 = (float)NGCanvas.this.transform.getMxt();
/*  216 */           float f2 = (float)NGCanvas.this.transform.getMyt();
/*  217 */           return new RectBounds(localRectBounds.getMinX() - f1, localRectBounds.getMinY() - f2, localRectBounds.getMaxX() - f1, localRectBounds.getMaxY() - f2);
/*      */         }
/*      */         float tmp105_103 = (1.0F / 1.0F); NGCanvas.TEMP_COORDS[2] = tmp105_103; NGCanvas.TEMP_COORDS[0] = tmp105_103;
/*      */         float tmp118_116 = (1.0F / -1.0F); NGCanvas.TEMP_COORDS[3] = tmp118_116; NGCanvas.TEMP_COORDS[1] = tmp118_116;
/*  223 */         Shape.accumulate(NGCanvas.TEMP_COORDS, NGCanvas.this.path, NGCanvas.this.getInverseTransform());
/*  224 */         return new RectBounds(NGCanvas.TEMP_COORDS[0], NGCanvas.TEMP_COORDS[1], NGCanvas.TEMP_COORDS[2], NGCanvas.TEMP_COORDS[3]);
/*      */       }
/*      */ 
/*      */       public boolean contains(float paramAnonymousFloat1, float paramAnonymousFloat2)
/*      */       {
/*  230 */         NGCanvas.TEMP_COORDS[0] = paramAnonymousFloat1;
/*  231 */         NGCanvas.TEMP_COORDS[1] = paramAnonymousFloat2;
/*  232 */         NGCanvas.this.transform.transform(NGCanvas.TEMP_COORDS, 0, NGCanvas.TEMP_COORDS, 0, 1);
/*  233 */         paramAnonymousFloat1 = NGCanvas.TEMP_COORDS[0];
/*  234 */         paramAnonymousFloat2 = NGCanvas.TEMP_COORDS[1];
/*  235 */         return NGCanvas.this.path.contains(paramAnonymousFloat1, paramAnonymousFloat2);
/*      */       }
/*      */ 
/*      */       public boolean intersects(float paramAnonymousFloat1, float paramAnonymousFloat2, float paramAnonymousFloat3, float paramAnonymousFloat4)
/*      */       {
/*  240 */         if (NGCanvas.this.transform.isTranslateOrIdentity()) {
/*  241 */           paramAnonymousFloat1 = (float)(paramAnonymousFloat1 + NGCanvas.this.transform.getMxt());
/*  242 */           paramAnonymousFloat2 = (float)(paramAnonymousFloat2 + NGCanvas.this.transform.getMyt());
/*  243 */           return NGCanvas.this.path.intersects(paramAnonymousFloat1, paramAnonymousFloat2, paramAnonymousFloat3, paramAnonymousFloat4);
/*      */         }
/*  245 */         PathIterator localPathIterator = NGCanvas.this.path.getPathIterator(NGCanvas.this.getInverseTransform());
/*  246 */         int i = Shape.rectCrossingsForPath(localPathIterator, paramAnonymousFloat1, paramAnonymousFloat2, paramAnonymousFloat1 + paramAnonymousFloat3, paramAnonymousFloat2 + paramAnonymousFloat4);
/*      */ 
/*  252 */         return i != 0;
/*      */       }
/*      */ 
/*      */       public boolean contains(float paramAnonymousFloat1, float paramAnonymousFloat2, float paramAnonymousFloat3, float paramAnonymousFloat4)
/*      */       {
/*  257 */         if (NGCanvas.this.transform.isTranslateOrIdentity()) {
/*  258 */           paramAnonymousFloat1 = (float)(paramAnonymousFloat1 + NGCanvas.this.transform.getMxt());
/*  259 */           paramAnonymousFloat2 = (float)(paramAnonymousFloat2 + NGCanvas.this.transform.getMyt());
/*  260 */           return NGCanvas.this.path.contains(paramAnonymousFloat1, paramAnonymousFloat2, paramAnonymousFloat3, paramAnonymousFloat4);
/*      */         }
/*  262 */         PathIterator localPathIterator = NGCanvas.this.path.getPathIterator(NGCanvas.this.getInverseTransform());
/*  263 */         int i = Shape.rectCrossingsForPath(localPathIterator, paramAnonymousFloat1, paramAnonymousFloat2, paramAnonymousFloat1 + paramAnonymousFloat3, paramAnonymousFloat2 + paramAnonymousFloat4);
/*      */ 
/*  268 */         return (i != -2147483648) && (i != 0);
/*      */       }
/*      */ 
/*      */       public BaseTransform getCombinedTransform(BaseTransform paramAnonymousBaseTransform) {
/*  272 */         if (NGCanvas.this.transform.isIdentity()) return paramAnonymousBaseTransform;
/*  273 */         Affine2D localAffine2D = NGCanvas.this.getInverseTransform();
/*  274 */         if ((paramAnonymousBaseTransform == null) || (paramAnonymousBaseTransform.isIdentity())) return localAffine2D;
/*  275 */         NGCanvas.TEMP_PATH_TX.setTransform(paramAnonymousBaseTransform);
/*  276 */         NGCanvas.TEMP_PATH_TX.concatenate(NGCanvas.this.transform);
/*  277 */         return NGCanvas.TEMP_PATH_TX;
/*      */       }
/*      */ 
/*      */       public PathIterator getPathIterator(BaseTransform paramAnonymousBaseTransform)
/*      */       {
/*  282 */         return NGCanvas.this.path.getPathIterator(getCombinedTransform(paramAnonymousBaseTransform));
/*      */       }
/*      */ 
/*      */       public PathIterator getPathIterator(BaseTransform paramAnonymousBaseTransform, float paramAnonymousFloat)
/*      */       {
/*  287 */         return NGCanvas.this.path.getPathIterator(getCombinedTransform(paramAnonymousBaseTransform), paramAnonymousFloat);
/*      */       }
/*      */ 
/*      */       public Shape copy()
/*      */       {
/*  292 */         throw new UnsupportedOperationException("Not supported yet.");
/*      */       } } ;
/*      */   }
/*      */ 
/*      */   private Affine2D getInverseTransform() {
/*  297 */     if (this.inversedirty) {
/*  298 */       this.inverseTransform.setTransform(this.transform);
/*      */       try {
/*  300 */         this.inverseTransform.invert();
/*      */       } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/*  302 */         this.inverseTransform.setToScale(0.0D, 0.0D);
/*      */       }
/*  304 */       this.inversedirty = false;
/*      */     }
/*  306 */     return this.inverseTransform;
/*      */   }
/*      */ 
/*      */   protected boolean hasOverlappingContents()
/*      */   {
/*  311 */     return true;
/*      */   }
/*      */ 
/*      */   protected void renderContent(Graphics paramGraphics)
/*      */   {
/*  316 */     initCanvas(paramGraphics);
/*  317 */     if (this.cv.tex != null) {
/*  318 */       if (this.thebuf != null) {
/*  319 */         this.thebuf.switchToRead();
/*  320 */         renderStream(this.thebuf);
/*  321 */         this.thebuf.resetForWrite();
/*  322 */         this.thebuf = null;
/*      */       }
/*  324 */       paramGraphics.drawTexture(this.cv.tex, 0.0F, 0.0F, this.tw, this.th);
/*      */ 
/*  326 */       this.cv.save(paramGraphics, this.tw, this.th);
/*      */     }
/*  328 */     this.temp.g = (this.clip.g = this.cv.g = null);
/*      */   }
/*      */ 
/*      */   private void initCanvas(Graphics paramGraphics) {
/*  332 */     if ((this.tw <= 0) || (this.th <= 0)) {
/*  333 */       this.cv.dispose();
/*  334 */       return;
/*      */     }
/*  336 */     this.cv.validate(paramGraphics, this.tw, this.th);
/*      */   }
/*      */ 
/*      */   private void initClip() {
/*  340 */     if (this.clip.validate(this.cv.g, this.tw, this.th))
/*  341 */       for (Path2D localPath2D : this.clipStack)
/*  342 */         renderClip(localPath2D);
/*      */   }
/*      */ 
/*      */   private void renderClip(Path2D paramPath2D)
/*      */   {
/*  348 */     this.temp.validate(this.cv.g, this.tw, this.th);
/*  349 */     this.temp.g.setPaint(Color.WHITE);
/*  350 */     this.temp.g.setTransform(BaseTransform.IDENTITY_TRANSFORM);
/*  351 */     this.temp.g.fill(paramPath2D);
/*  352 */     blendAthruBintoC(this.temp, Blend.Mode.SRC_IN, this.clip, null, CompositeMode.SRC, this.clip);
/*      */   }
/*      */ 
/*      */   private void applyEffectOnAintoC(Effect paramEffect1, Effect paramEffect2, BaseTransform paramBaseTransform, Rectangle paramRectangle, CompositeMode paramCompositeMode, RenderBuf paramRenderBuf)
/*      */   {
/*  362 */     PrFilterContext localPrFilterContext = PrFilterContext.getInstance(paramRenderBuf.tex.getAssociatedScreen());
/*      */ 
/*  364 */     ImageData localImageData = paramEffect2.filter(localPrFilterContext, paramBaseTransform, paramRectangle, null, paramEffect1);
/*      */ 
/*  366 */     Rectangle localRectangle = localImageData.getUntransformedBounds();
/*  367 */     Filterable localFilterable = localImageData.getUntransformedImage();
/*  368 */     Texture localTexture = ((PrTexture)localFilterable).getTextureObject();
/*  369 */     paramRenderBuf.g.setTransform(localImageData.getTransform());
/*  370 */     paramRenderBuf.g.setCompositeMode(paramCompositeMode);
/*  371 */     paramRenderBuf.g.drawTexture(localTexture, localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
/*  372 */     paramRenderBuf.g.setTransform(BaseTransform.IDENTITY_TRANSFORM);
/*  373 */     paramRenderBuf.g.setCompositeMode(CompositeMode.SRC_OVER);
/*  374 */     localImageData.unref();
/*      */   }
/*      */ 
/*      */   private void blendAthruBintoC(RenderBuf paramRenderBuf1, Blend.Mode paramMode, RenderBuf paramRenderBuf2, RectBounds paramRectBounds, CompositeMode paramCompositeMode, RenderBuf paramRenderBuf3)
/*      */   {
/*  384 */     this.blender.setTopInput(paramRenderBuf1.input);
/*  385 */     this.blender.setBottomInput(paramRenderBuf2.input);
/*  386 */     this.blender.setMode(paramMode);
/*      */     Rectangle localRectangle;
/*  388 */     if (paramRectBounds != null)
/*      */     {
/*  391 */       BaseBounds localBaseBounds = this.transform.transform(paramRectBounds, TEMP_BOUNDS);
/*  392 */       localRectangle = new Rectangle(localBaseBounds);
/*      */     } else {
/*  394 */       localRectangle = null;
/*      */     }
/*  396 */     applyEffectOnAintoC(null, this.blender, BaseTransform.IDENTITY_TRANSFORM, localRectangle, paramCompositeMode, paramRenderBuf3);
/*      */   }
/*      */ 
/*      */   private void setupFill(Graphics paramGraphics)
/*      */   {
/*  402 */     paramGraphics.setPaint(this.fillPaint);
/*      */   }
/*      */ 
/*      */   private BasicStroke getStroke() {
/*  406 */     if (this.stroke == null) {
/*  407 */       this.stroke = new BasicStroke(this.linewidth, this.linecap, this.linejoin, this.miterlimit);
/*      */     }
/*      */ 
/*  410 */     return this.stroke;
/*      */   }
/*      */ 
/*      */   private void setupStroke(Graphics paramGraphics) {
/*  414 */     paramGraphics.setStroke(getStroke());
/*  415 */     paramGraphics.setPaint(this.strokePaint);
/*      */   }
/*      */ 
/*      */   private void renderStream(GrowableDataBuffer paramGrowableDataBuffer)
/*      */   {
/*  437 */     while (!paramGrowableDataBuffer.isEmpty()) {
/*  438 */       int i = paramGrowableDataBuffer.getByte();
/*      */       int j;
/*      */       int m;
/*      */       int n;
/*      */       Object localObject3;
/*      */       Object localObject4;
/*      */       Object localObject1;
/*      */       Object localObject2;
/*  439 */       switch (i) {
/*      */       case 40:
/*  441 */         this.path.reset();
/*  442 */         break;
/*      */       case 41:
/*  444 */         this.path.moveTo(paramGrowableDataBuffer.getFloat(), paramGrowableDataBuffer.getFloat());
/*  445 */         break;
/*      */       case 42:
/*  447 */         this.path.lineTo(paramGrowableDataBuffer.getFloat(), paramGrowableDataBuffer.getFloat());
/*  448 */         break;
/*      */       case 43:
/*  450 */         this.path.quadTo(paramGrowableDataBuffer.getFloat(), paramGrowableDataBuffer.getFloat(), paramGrowableDataBuffer.getFloat(), paramGrowableDataBuffer.getFloat());
/*      */ 
/*  452 */         break;
/*      */       case 44:
/*  454 */         this.path.curveTo(paramGrowableDataBuffer.getFloat(), paramGrowableDataBuffer.getFloat(), paramGrowableDataBuffer.getFloat(), paramGrowableDataBuffer.getFloat(), paramGrowableDataBuffer.getFloat(), paramGrowableDataBuffer.getFloat());
/*      */ 
/*  457 */         break;
/*      */       case 45:
/*  459 */         this.path.closePath();
/*  460 */         break;
/*      */       case 46:
/*  463 */         break;
/*      */       case 13:
/*  466 */         Path2D localPath2D = (Path2D)paramGrowableDataBuffer.getObject();
/*  467 */         initClip();
/*  468 */         renderClip(localPath2D);
/*  469 */         this.clipStack.addLast(localPath2D);
/*  470 */         break;
/*      */       case 14:
/*  474 */         this.clip.dispose();
/*  475 */         this.clipStack.removeLast();
/*  476 */         break;
/*      */       case 15:
/*  479 */         j = paramGrowableDataBuffer.getByte();
/*  480 */         switch (j) { case 0:
/*  481 */           this.arctype = 0; break;
/*      */         case 1:
/*  482 */           this.arctype = 1; break;
/*      */         case 2:
/*  483 */           this.arctype = 2;
/*      */         }
/*  485 */         break;
/*      */       case 52:
/*  489 */         j = paramGrowableDataBuffer.getInt();
/*  490 */         m = paramGrowableDataBuffer.getInt();
/*  491 */         n = paramGrowableDataBuffer.getInt();
/*  492 */         this.tmpImage.setArgb(0, 0, n);
/*  493 */         Graphics localGraphics1 = this.cv.g;
/*  494 */         localGraphics1.setExtraAlpha(1.0F);
/*  495 */         localGraphics1.setCompositeMode(CompositeMode.SRC);
/*  496 */         localGraphics1.setTransform(BaseTransform.IDENTITY_TRANSFORM);
/*  497 */         localObject3 = localGraphics1.getResourceFactory();
/*  498 */         localObject4 = ((ResourceFactory)localObject3).getCachedTexture(this.tmpImage, false);
/*  499 */         localGraphics1.drawTexture((Texture)localObject4, j, m, 1.0F, 1.0F);
/*  500 */         break;
/*      */       case 53:
/*  504 */         j = paramGrowableDataBuffer.getInt();
/*  505 */         m = paramGrowableDataBuffer.getInt();
/*  506 */         n = paramGrowableDataBuffer.getInt();
/*  507 */         int i1 = paramGrowableDataBuffer.getInt();
/*  508 */         localObject3 = (byte[])paramGrowableDataBuffer.getObject();
/*  509 */         localObject4 = Image.fromByteBgraPreData((byte[])localObject3, n, i1);
/*  510 */         Graphics localGraphics2 = this.cv.g;
/*  511 */         ResourceFactory localResourceFactory = localGraphics2.getResourceFactory();
/*  512 */         Texture localTexture = localResourceFactory.getCachedTexture((Image)localObject4, false);
/*  513 */         localGraphics2.setTransform(BaseTransform.IDENTITY_TRANSFORM);
/*  514 */         localGraphics2.setCompositeMode(CompositeMode.SRC);
/*  515 */         localGraphics2.drawTexture(localTexture, j, m, n, i1);
/*  516 */         localGraphics2.setCompositeMode(CompositeMode.SRC_OVER);
/*  517 */         break;
/*      */       case 11:
/*  521 */         double d1 = paramGrowableDataBuffer.getDouble();
/*  522 */         double d2 = paramGrowableDataBuffer.getDouble();
/*  523 */         double d3 = paramGrowableDataBuffer.getDouble();
/*  524 */         double d4 = paramGrowableDataBuffer.getDouble();
/*  525 */         double d5 = paramGrowableDataBuffer.getDouble();
/*  526 */         double d6 = paramGrowableDataBuffer.getDouble();
/*  527 */         this.transform.setTransform(d1, d4, d2, d5, d3, d6);
/*  528 */         this.inversedirty = true;
/*  529 */         break;
/*      */       case 0:
/*  532 */         this.globalAlpha = paramGrowableDataBuffer.getFloat();
/*  533 */         break;
/*      */       case 16:
/*  535 */         this.fillRule = paramGrowableDataBuffer.getByte();
/*  536 */         if (this.fillRule == 0)
/*  537 */           this.path.setWindingRule(1);
/*      */         else {
/*  539 */           this.path.setWindingRule(0);
/*      */         }
/*  541 */         break;
/*      */       case 1:
/*  543 */         this.blendmode = ((Blend.Mode)paramGrowableDataBuffer.getObject());
/*  544 */         break;
/*      */       case 2:
/*  546 */         this.fillPaint = ((Paint)paramGrowableDataBuffer.getObject());
/*  547 */         break;
/*      */       case 3:
/*  549 */         this.strokePaint = ((Paint)paramGrowableDataBuffer.getObject());
/*  550 */         break;
/*      */       case 4:
/*  552 */         this.linewidth = paramGrowableDataBuffer.getFloat();
/*  553 */         this.stroke = null;
/*  554 */         break;
/*      */       case 5:
/*  556 */         this.linecap = prcaps[paramGrowableDataBuffer.getUByte()];
/*  557 */         this.stroke = null;
/*  558 */         break;
/*      */       case 6:
/*  560 */         this.linejoin = prjoins[paramGrowableDataBuffer.getUByte()];
/*  561 */         this.stroke = null;
/*  562 */         break;
/*      */       case 7:
/*  564 */         this.miterlimit = paramGrowableDataBuffer.getFloat();
/*  565 */         this.stroke = null;
/*  566 */         break;
/*      */       case 8:
/*  569 */         this.pgfont = ((PGFont)paramGrowableDataBuffer.getObject());
/*  570 */         break;
/*      */       case 9:
/*  573 */         this.align = paramGrowableDataBuffer.getUByte();
/*  574 */         break;
/*      */       case 10:
/*  576 */         this.baseline = prbases[paramGrowableDataBuffer.getUByte()];
/*  577 */         break;
/*      */       case 60:
/*  580 */         localObject1 = (Effect)paramGrowableDataBuffer.getObject();
/*  581 */         localObject2 = this.clipStack.isEmpty() ? this.cv : this.temp;
/*  582 */         applyEffectOnAintoC(this.cv.input, (Effect)localObject1, BaseTransform.IDENTITY_TRANSFORM, null, CompositeMode.SRC, (RenderBuf)localObject2);
/*      */ 
/*  585 */         if (localObject2 != this.cv)
/*  586 */           blendAthruBintoC((RenderBuf)localObject2, Blend.Mode.SRC_IN, this.clip, null, CompositeMode.SRC, this.cv); break;
/*      */       case 12:
/*  592 */         this.effect = ((Effect)paramGrowableDataBuffer.getObject());
/*  593 */         break;
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 47:
/*      */       case 48:
/*      */       case 50:
/*      */       case 51:
/*  611 */         if (this.effect != null) {
/*  612 */           paramGrowableDataBuffer.save();
/*  613 */           handleRenderOp(i, paramGrowableDataBuffer, null, TEMP_RECTBOUNDS);
/*  614 */           localObject1 = new RenderInput(i, paramGrowableDataBuffer, TEMP_RECTBOUNDS);
/*  615 */           localObject2 = (this.clipStack.isEmpty()) && (this.blendmode == Blend.Mode.SRC_OVER) ? this.cv : this.temp;
/*  616 */           applyEffectOnAintoC((Effect)localObject1, this.effect, this.transform, null, CompositeMode.SRC, (RenderBuf)localObject2);
/*      */         }
/*      */         else
/*      */         {
/*  620 */           int k = 0;
/*  621 */           if (!this.clipStack.isEmpty()) {
/*  622 */             initClip();
/*  623 */             k = 1;
/*      */           }
/*  625 */           if (this.blendmode != Blend.Mode.SRC_OVER) {
/*  626 */             k = 1;
/*      */           }
/*      */ 
/*  629 */           if (k != 0) {
/*  630 */             this.temp.validate(this.cv.g, this.tw, this.th);
/*  631 */             localObject2 = this.temp.g;
/*      */           } else {
/*  633 */             localObject2 = this.cv.g;
/*      */           }
/*  635 */           ((Graphics)localObject2).setExtraAlpha(this.globalAlpha);
/*  636 */           ((Graphics)localObject2).setTransform(this.transform);
/*  637 */           handleRenderOp(i, paramGrowableDataBuffer, (Graphics)localObject2, k != 0 ? TEMP_RECTBOUNDS : null);
/*      */         }
/*  639 */         if (this.blendmode != Blend.Mode.SRC_OVER) {
/*  640 */           RenderBuf localRenderBuf = this.clipStack.isEmpty() ? this.cv : this.temp;
/*  641 */           blendAthruBintoC(this.temp, this.blendmode, this.cv, TEMP_RECTBOUNDS, CompositeMode.SRC_OVER, localRenderBuf);
/*      */         }
/*      */ 
/*  644 */         if (!this.clipStack.isEmpty())
/*  645 */           blendAthruBintoC(this.temp, Blend.Mode.SRC_IN, this.clip, TEMP_RECTBOUNDS, CompositeMode.SRC_OVER, this.cv); break;
/*      */       case 17:
/*      */       case 18:
/*      */       case 19:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 49:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 59:
/*      */       default:
/*  651 */         throw new InternalError("Unrecognized PGCanvas token: " + i);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void handleRenderOp(int paramInt, GrowableDataBuffer paramGrowableDataBuffer, Graphics paramGraphics, RectBounds paramRectBounds)
/*      */   {
/*  669 */     int i = 0;
/*      */     float f1;
/*      */     float f2;
/*      */     float f3;
/*      */     float f4;
/*      */     float f5;
/*      */     float f7;
/*      */     float f8;
/*  670 */     switch (paramInt)
/*      */     {
/*      */     case 47:
/*  673 */       if (paramRectBounds != null) {
/*  674 */         Shape.accumulate(TEMP_COORDS, this.untransformedPath, this.transform);
/*  675 */         paramRectBounds.setBounds(TEMP_COORDS[0], TEMP_COORDS[1], TEMP_COORDS[2], TEMP_COORDS[3]);
/*      */       }
/*      */ 
/*  680 */       if (paramGraphics != null) {
/*  681 */         setupFill(paramGraphics);
/*  682 */         paramGraphics.fill(this.untransformedPath); } break;
/*      */     case 48:
/*  688 */       if (paramRectBounds != null) {
/*  689 */         getStroke().accumulateShapeBounds(TEMP_COORDS, this.untransformedPath, this.transform);
/*      */ 
/*  692 */         paramRectBounds.setBounds(TEMP_COORDS[0], TEMP_COORDS[1], TEMP_COORDS[2], TEMP_COORDS[3]);
/*      */       }
/*      */ 
/*  697 */       if (paramGraphics != null) {
/*  698 */         setupStroke(paramGraphics);
/*  699 */         paramGraphics.draw(this.untransformedPath); } break;
/*      */     case 23:
/*  705 */       f1 = paramGrowableDataBuffer.getFloat();
/*  706 */       f2 = paramGrowableDataBuffer.getFloat();
/*  707 */       f3 = paramGrowableDataBuffer.getFloat();
/*  708 */       f4 = paramGrowableDataBuffer.getFloat();
/*  709 */       if (paramRectBounds != null) {
/*  710 */         paramRectBounds.setBoundsAndSort(f1, f2, f3, f4);
/*  711 */         i = 1;
/*      */       }
/*  713 */       if (paramGraphics != null) {
/*  714 */         setupStroke(paramGraphics);
/*  715 */         paramGraphics.drawLine(f1, f2, f3, f4); } break;
/*      */     case 21:
/*      */     case 25:
/*  721 */       i = 1;
/*      */     case 20:
/*      */     case 22:
/*      */     case 24:
/*  726 */       f1 = paramGrowableDataBuffer.getFloat();
/*  727 */       f2 = paramGrowableDataBuffer.getFloat();
/*  728 */       f3 = paramGrowableDataBuffer.getFloat();
/*  729 */       f4 = paramGrowableDataBuffer.getFloat();
/*  730 */       if (paramRectBounds != null) {
/*  731 */         paramRectBounds.setBounds(f1, f2, f1 + f3, f2 + f4);
/*      */       }
/*  733 */       if (paramGraphics != null)
/*  734 */         switch (paramInt) {
/*      */         case 20:
/*  736 */           setupFill(paramGraphics);
/*  737 */           paramGraphics.fillRect(f1, f2, f3, f4);
/*  738 */           break;
/*      */         case 24:
/*  740 */           setupFill(paramGraphics);
/*  741 */           paramGraphics.fillEllipse(f1, f2, f3, f4);
/*  742 */           break;
/*      */         case 21:
/*  744 */           setupStroke(paramGraphics);
/*  745 */           paramGraphics.drawRect(f1, f2, f3, f4);
/*  746 */           break;
/*      */         case 25:
/*  748 */           setupStroke(paramGraphics);
/*  749 */           paramGraphics.drawEllipse(f1, f2, f3, f4);
/*  750 */           break;
/*      */         case 22:
/*  752 */           paramGraphics.setPaint(Color.TRANSPARENT);
/*  753 */           paramGraphics.setCompositeMode(CompositeMode.SRC);
/*  754 */           paramGraphics.fillRect(f1, f2, f3, f4);
/*  755 */           paramGraphics.setCompositeMode(CompositeMode.SRC_OVER);
/*  756 */         case 23: }  break;
/*      */     case 27:
/*  762 */       i = 1;
/*      */     case 26:
/*  765 */       f1 = paramGrowableDataBuffer.getFloat();
/*  766 */       f2 = paramGrowableDataBuffer.getFloat();
/*  767 */       f3 = paramGrowableDataBuffer.getFloat();
/*  768 */       f4 = paramGrowableDataBuffer.getFloat();
/*  769 */       f5 = paramGrowableDataBuffer.getFloat();
/*  770 */       f7 = paramGrowableDataBuffer.getFloat();
/*  771 */       if (paramRectBounds != null) {
/*  772 */         paramRectBounds.setBounds(f1, f2, f1 + f3, f2 + f4);
/*      */       }
/*  774 */       if (paramGraphics != null)
/*  775 */         if (paramInt == 26) {
/*  776 */           setupFill(paramGraphics);
/*  777 */           paramGraphics.fillRoundRect(f1, f2, f3, f4, f5, f7);
/*      */         } else {
/*  779 */           setupStroke(paramGraphics);
/*  780 */           paramGraphics.drawRoundRect(f1, f2, f3, f4, f5, f7); }  break;
/*      */     case 28:
/*      */     case 29:
/*  788 */       f1 = paramGrowableDataBuffer.getFloat();
/*  789 */       f2 = paramGrowableDataBuffer.getFloat();
/*  790 */       f3 = paramGrowableDataBuffer.getFloat();
/*  791 */       f4 = paramGrowableDataBuffer.getFloat();
/*  792 */       f5 = paramGrowableDataBuffer.getFloat();
/*  793 */       f7 = paramGrowableDataBuffer.getFloat();
/*  794 */       TEMP_ARC.setArc(f1, f2, f3, f4, f5, f7, this.arctype);
/*  795 */       if (paramInt == 28) {
/*  796 */         if (paramRectBounds != null) {
/*  797 */           Shape.accumulate(TEMP_COORDS, TEMP_ARC, this.transform);
/*      */         }
/*  799 */         if (paramGraphics != null) {
/*  800 */           setupFill(paramGraphics);
/*  801 */           paramGraphics.fill(TEMP_ARC);
/*      */         }
/*      */       } else {
/*  804 */         if (paramRectBounds != null) {
/*  805 */           getStroke().accumulateShapeBounds(TEMP_COORDS, TEMP_ARC, this.transform);
/*      */         }
/*  807 */         if (paramGraphics != null) {
/*  808 */           setupStroke(paramGraphics);
/*  809 */           paramGraphics.draw(TEMP_ARC); }  } break;
/*      */     case 50:
/*      */     case 51:
/*  817 */       f1 = paramGrowableDataBuffer.getFloat();
/*  818 */       f2 = paramGrowableDataBuffer.getFloat();
/*  819 */       f3 = paramGrowableDataBuffer.getFloat();
/*  820 */       f4 = paramGrowableDataBuffer.getFloat();
/*  821 */       Image localImage = (Image)paramGrowableDataBuffer.getObject();
/*      */       float f9;
/*      */       float f10;
/*  823 */       if (paramInt == 50) {
/*  824 */         f7 = f8 = 0.0F;
/*  825 */         f9 = localImage.getWidth();
/*  826 */         f10 = localImage.getHeight();
/*      */       } else {
/*  828 */         f7 = paramGrowableDataBuffer.getFloat();
/*  829 */         f8 = paramGrowableDataBuffer.getFloat();
/*  830 */         f9 = paramGrowableDataBuffer.getFloat();
/*  831 */         f10 = paramGrowableDataBuffer.getFloat();
/*      */       }
/*  833 */       if (paramRectBounds != null) {
/*  834 */         paramRectBounds.setBounds(f1, f2, f1 + f3, f2 + f4);
/*      */       }
/*  836 */       if (paramGraphics != null) {
/*  837 */         ResourceFactory localResourceFactory = paramGraphics.getResourceFactory();
/*  838 */         Texture localTexture = localResourceFactory.getCachedTexture(localImage, false);
/*  839 */         paramGraphics.drawTexture(localTexture, f1, f2, f1 + f3, f2 + f4, f7, f8, f7 + f9, f8 + f10, 0);
/*      */       }
/*      */ 
/*  843 */       break;
/*      */     case 30:
/*      */     case 31:
/*  849 */       f1 = paramGrowableDataBuffer.getFloat();
/*  850 */       f2 = paramGrowableDataBuffer.getFloat();
/*  851 */       f3 = paramGrowableDataBuffer.getFloat();
/*  852 */       String str = (String)paramGrowableDataBuffer.getObject();
/*      */ 
/*  854 */       this.ngtext.setFont(this.pgfont);
/*  855 */       this.ngtext.helper.setTextOrigin(this.baseline);
/*  856 */       this.ngtext.setText(str);
/*      */ 
/*  858 */       float f6 = this.ngtext.helper.getLogicalWidth();
/*      */ 
/*  861 */       f7 = 0.0F;
/*      */ 
/*  870 */       this.ngtext.helper.setTextAlignment(this.align);
/*  871 */       if (this.align != 0) {
/*  872 */         if (this.align == 2)
/*  873 */           f7 = -f6;
/*  874 */         else if (this.align == 1) {
/*  875 */           f7 = -f6 * 0.5F;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  880 */       if ((f3 > 0.0D) && (f6 > f3))
/*      */       {
/*  882 */         f8 = f3 / f6;
/*  883 */         TEMP_TX.setTransform(this.transform);
/*  884 */         f1 += f7 * f8;
/*  885 */         TEMP_TX.translate(f1, f2);
/*  886 */         TEMP_TX.scale(f8, 1.0D);
/*  887 */         f1 = 0.0F;
/*  888 */         f2 = 0.0F;
/*  889 */         paramGraphics.setTransform(TEMP_TX);
/*      */       } else {
/*  891 */         f1 += f7;
/*      */       }
/*      */ 
/*  895 */       if (paramInt == 30) {
/*  896 */         this.ngtext.setMode(PGShape.Mode.FILL);
/*  897 */         this.ngtext.setFillPaint(this.fillPaint);
/*  898 */         this.ngtext.setDrawPaint(null);
/*      */       } else {
/*  900 */         this.ngtext.setMode(PGShape.Mode.STROKE);
/*  901 */         BasicStroke localBasicStroke2 = getStroke();
/*  902 */         if (paramGraphics != null) {
/*  903 */           paramGraphics.setStroke(localBasicStroke2);
/*      */         }
/*  905 */         this.ngtext.setDrawStroke(localBasicStroke2);
/*  906 */         this.ngtext.setFillPaint(null);
/*  907 */         this.ngtext.setDrawPaint(this.strokePaint);
/*      */       }
/*      */ 
/*  910 */       this.ngtext.setLocation(f1, f2);
/*  911 */       if (paramRectBounds != null) {
/*  912 */         this.ngtext.helper.computeContentBounds(paramRectBounds, this.transform);
/*      */       }
/*  914 */       if (paramGraphics != null)
/*  915 */         this.ngtext.renderContent(paramGraphics); break;
/*      */     case 32:
/*      */     case 33:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 39:
/*      */     case 40:
/*      */     case 41:
/*      */     case 42:
/*      */     case 43:
/*      */     case 44:
/*      */     case 45:
/*      */     case 46:
/*      */     case 49:
/*      */     default:
/*  920 */       throw new InternalError("Unrecognized PGCanvas rendering token: " + paramInt);
/*      */     }
/*  922 */     if ((i != 0) && (paramRectBounds != null)) {
/*  923 */       BasicStroke localBasicStroke1 = getStroke();
/*  924 */       if (localBasicStroke1.getType() != 1) {
/*  925 */         f2 = localBasicStroke1.getLineWidth();
/*  926 */         if (localBasicStroke1.getType() == 0) {
/*  927 */           f2 /= 2.0F;
/*      */         }
/*  929 */         paramRectBounds.grow(f2, f2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateBounds(float paramFloat1, float paramFloat2) {
/*  935 */     this.tw = ((int)Math.ceil(paramFloat1));
/*  936 */     this.th = ((int)Math.ceil(paramFloat2));
/*  937 */     geometryChanged();
/*      */   }
/*      */ 
/*      */   public void updateRendering(GrowableDataBuffer paramGrowableDataBuffer) {
/*  941 */     this.thebuf = paramGrowableDataBuffer;
/*  942 */     geometryChanged();
/*      */   }
/*      */ 
/*      */   static class EffectInput extends Effect
/*      */   {
/*      */     RTTexture tex;
/*      */ 
/*      */     EffectInput(RTTexture paramRTTexture)
/*      */     {
/* 1020 */       this.tex = paramRTTexture;
/*      */     }
/*      */ 
/*      */     public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*      */     {
/* 1028 */       PrDrawable localPrDrawable = PrDrawable.create(paramFilterContext, this.tex);
/* 1029 */       Rectangle localRectangle = new Rectangle(this.tex.getContentWidth(), this.tex.getContentHeight());
/* 1030 */       return new ImageData(paramFilterContext, localPrDrawable, localRectangle);
/*      */     }
/*      */ 
/*      */     public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*      */     {
/* 1035 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */     public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*      */     {
/* 1040 */       Rectangle localRectangle = new Rectangle(this.tex.getContentWidth(), this.tex.getContentHeight());
/* 1041 */       return transformBounds(paramBaseTransform, new RectBounds(localRectangle));
/*      */     }
/*      */   }
/*      */ 
/*      */   static class MyBlend extends Blend
/*      */   {
/*      */     public MyBlend(Blend.Mode paramMode, Effect paramEffect1, Effect paramEffect2)
/*      */     {
/*  999 */       super(paramEffect1, paramEffect2);
/*      */     }
/*      */ 
/*      */     public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*      */     {
/* 1010 */       Rectangle localRectangle = super.getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/* 1011 */       localRectangle.intersectWith(paramRectangle);
/* 1012 */       return localRectangle;
/*      */     }
/*      */   }
/*      */ 
/*      */   class RenderInput extends Effect
/*      */   {
/*      */     float x;
/*      */     float y;
/*      */     float w;
/*      */     float h;
/*      */     int token;
/*      */     GrowableDataBuffer<Object> buf;
/*      */ 
/*      */     public RenderInput(GrowableDataBuffer<Object> paramRectBounds, RectBounds arg3)
/*      */     {
/*  951 */       this.token = paramRectBounds;
/*      */       Object localObject1;
/*  952 */       this.buf = localObject1;
/*      */       Object localObject2;
/*  953 */       this.x = localObject2.getMinX();
/*  954 */       this.y = localObject2.getMinY();
/*  955 */       this.w = localObject2.getWidth();
/*  956 */       this.h = localObject2.getHeight();
/*      */     }
/*      */ 
/*      */     public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*      */     {
/*  964 */       BaseBounds localBaseBounds = getBounds(paramBaseTransform, paramEffect);
/*  965 */       if (paramRectangle != null) {
/*  966 */         localBaseBounds.intersectWith(paramRectangle);
/*      */       }
/*  968 */       Rectangle localRectangle = new Rectangle(localBaseBounds);
/*  969 */       if (localRectangle.width < 1) localRectangle.width = 1;
/*  970 */       if (localRectangle.height < 1) localRectangle.height = 1;
/*  971 */       PrDrawable localPrDrawable = (PrDrawable)Effect.getCompatibleImage(paramFilterContext, localRectangle.width, localRectangle.height);
/*  972 */       if (localPrDrawable != null) {
/*  973 */         Graphics localGraphics = localPrDrawable.createGraphics();
/*  974 */         localGraphics.setExtraAlpha(NGCanvas.this.globalAlpha);
/*  975 */         localGraphics.translate(-localRectangle.x, -localRectangle.y);
/*  976 */         if (paramBaseTransform != null) {
/*  977 */           localGraphics.transform(paramBaseTransform);
/*      */         }
/*  979 */         this.buf.restore();
/*  980 */         NGCanvas.this.handleRenderOp(this.token, this.buf, localGraphics, null);
/*      */       }
/*  982 */       return new ImageData(paramFilterContext, localPrDrawable, localRectangle);
/*      */     }
/*      */ 
/*      */     public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*      */     {
/*  987 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */     public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*      */     {
/*  992 */       RectBounds localRectBounds = new RectBounds(this.x, this.y, this.x + this.w, this.y + this.h);
/*  993 */       return paramBaseTransform.transform(localRectBounds, localRectBounds);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class PixelData
/*      */   {
/*  138 */     private IntBuffer pixels = null;
/*  139 */     private boolean validPixels = false;
/*      */     private int cw;
/*      */     private int ch;
/*      */ 
/*      */     private PixelData(Graphics paramGraphics, int paramInt1, int paramInt2)
/*      */     {
/*  143 */       this.cw = paramInt1;
/*  144 */       this.ch = paramInt2;
/*  145 */       this.pixels = IntBuffer.allocate(paramInt1 * paramInt2);
/*      */     }
/*      */ 
/*      */     private void save(Graphics paramGraphics, RTTexture paramRTTexture) {
/*  149 */       int i = paramRTTexture.getContentWidth();
/*  150 */       int j = paramRTTexture.getContentHeight();
/*  151 */       if ((this.cw < i) || (this.ch < j)) {
/*  152 */         this.cw = i;
/*  153 */         this.ch = j;
/*  154 */         this.pixels = IntBuffer.allocate(this.cw * this.ch);
/*      */       }
/*  156 */       this.pixels.rewind();
/*  157 */       paramRTTexture.readPixels(this.pixels);
/*  158 */       this.validPixels = true;
/*      */     }
/*      */ 
/*      */     private void restore(Graphics paramGraphics, int paramInt1, int paramInt2) {
/*  162 */       if (this.validPixels) {
/*  163 */         Image localImage = Image.fromIntArgbPreData(this.pixels, paramInt1, paramInt2);
/*  164 */         Texture localTexture = paramGraphics.getResourceFactory().createTexture(localImage);
/*  165 */         paramGraphics.drawTexture(localTexture, 0.0F, 0.0F, paramInt1, paramInt2);
/*  166 */         localTexture.dispose();
/*  167 */         this.validPixels = false;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   static class RenderBuf
/*      */   {
/*      */     final NGCanvas.InitType init_type;
/*      */     RTTexture tex;
/*      */     Graphics g;
/*      */     NGCanvas.EffectInput input;
/*   55 */     private NGCanvas.PixelData savedPixelData = null;
/*      */ 
/*      */     public RenderBuf(NGCanvas.InitType paramInitType) {
/*   58 */       this.init_type = paramInitType;
/*      */     }
/*      */ 
/*      */     public void dispose() {
/*   62 */       if (this.tex != null) this.tex.dispose();
/*      */ 
/*   64 */       this.tex = null;
/*   65 */       this.g = null;
/*   66 */       this.input = null;
/*      */     }
/*      */ 
/*      */     public boolean validate(Graphics paramGraphics, int paramInt1, int paramInt2) {
/*   70 */       int i = this.tex == null ? 0 : this.tex.getContentWidth();
/*   71 */       int j = this.tex == null ? 0 : this.tex.getContentHeight();
/*   72 */       if ((this.tex == null) || (i < paramInt1) || (j < paramInt2)) {
/*   73 */         RTTexture localRTTexture1 = this.tex;
/*   74 */         RTTexture localRTTexture2 = paramGraphics.getResourceFactory().createRTTexture(paramInt1, paramInt2);
/*   75 */         this.tex = localRTTexture2;
/*   76 */         this.g = localRTTexture2.createGraphics();
/*   77 */         this.input = new NGCanvas.EffectInput(localRTTexture2);
/*   78 */         if (localRTTexture1 != null) {
/*   79 */           if (this.init_type == NGCanvas.InitType.PRESERVE_UPPER_LEFT) {
/*   80 */             this.g.setCompositeMode(CompositeMode.SRC);
/*   81 */             if (localRTTexture1.createGraphics() == null) {
/*   82 */               if (this.savedPixelData != null)
/*   83 */                 this.savedPixelData.restore(this.g, i, j);
/*      */             }
/*      */             else {
/*   86 */               this.g.drawTexture(localRTTexture1, 0.0F, 0.0F, i, j);
/*      */             }
/*   88 */             this.g.setCompositeMode(CompositeMode.SRC_OVER);
/*      */           }
/*   90 */           localRTTexture1.dispose();
/*      */         }
/*   92 */         if (this.init_type == NGCanvas.InitType.FILL_WHITE) {
/*   93 */           this.g.setPaint(Color.WHITE);
/*   94 */           this.g.fillRect(0.0F, 0.0F, paramInt1, paramInt2);
/*      */         }
/*   96 */         return true;
/*      */       }
/*   98 */       if (this.g == null) {
/*   99 */         this.g = this.tex.createGraphics();
/*  100 */         if (this.g == null) {
/*  101 */           this.tex.dispose();
/*  102 */           this.tex = paramGraphics.getResourceFactory().createRTTexture(paramInt1, paramInt2);
/*  103 */           this.g = this.tex.createGraphics();
/*  104 */           this.input = new NGCanvas.EffectInput(this.tex);
/*  105 */           if (this.savedPixelData != null) {
/*  106 */             this.g.setCompositeMode(CompositeMode.SRC);
/*  107 */             this.savedPixelData.restore(this.g, paramInt1, paramInt2);
/*  108 */             this.g.setCompositeMode(CompositeMode.SRC_OVER);
/*  109 */           } else if (this.init_type == NGCanvas.InitType.FILL_WHITE) {
/*  110 */             this.g.setPaint(Color.WHITE);
/*  111 */             this.g.fillRect(0.0F, 0.0F, paramInt1, paramInt2);
/*      */           }
/*  113 */           return true;
/*      */         }
/*      */       }
/*      */ 
/*  117 */       if (this.init_type == NGCanvas.InitType.CLEAR) {
/*  118 */         this.g.setCompositeMode(CompositeMode.CLEAR);
/*  119 */         this.g.fillRect(0.0F, 0.0F, paramInt1, paramInt2);
/*  120 */         this.g.setCompositeMode(CompositeMode.SRC_OVER);
/*      */       }
/*  122 */       return false;
/*      */     }
/*      */ 
/*      */     private void save(Graphics paramGraphics, int paramInt1, int paramInt2) {
/*  126 */       if (this.tex.isVolatile()) {
/*  127 */         if (this.savedPixelData == null) {
/*  128 */           this.savedPixelData = new NGCanvas.PixelData(paramGraphics, paramInt1, paramInt2, null);
/*      */         }
/*  130 */         this.savedPixelData.save(paramGraphics, this.tex);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   static enum InitType
/*      */   {
/*   45 */     CLEAR, 
/*   46 */     FILL_WHITE, 
/*   47 */     PRESERVE_UPPER_LEFT;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGCanvas
 * JD-Core Version:    0.6.2
 */