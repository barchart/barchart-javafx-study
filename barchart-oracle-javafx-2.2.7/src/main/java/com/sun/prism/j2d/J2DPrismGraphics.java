/*      */ package com.sun.prism.j2d;
/*      */ 
/*      */ import com.sun.glass.ui.Screen;
/*      */ import com.sun.javafx.PlatformUtil;
/*      */ import com.sun.javafx.font.FontResource;
/*      */ import com.sun.javafx.font.FontStrike;
/*      */ import com.sun.javafx.geom.RectBounds;
/*      */ import com.sun.javafx.geom.transform.Affine2D;
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ import com.sun.javafx.geom.transform.GeneralTransform3D;
/*      */ import com.sun.prism.CompositeMode;
/*      */ import com.sun.prism.RTTexture;
/*      */ import com.sun.prism.ReadbackGraphics;
/*      */ import com.sun.prism.RenderTarget;
/*      */ import com.sun.prism.ResourceFactory;
/*      */ import com.sun.prism.Texture;
/*      */ import com.sun.prism.camera.PrismCameraImpl;
/*      */ import com.sun.prism.camera.PrismParallelCameraImpl;
/*      */ import com.sun.prism.j2d.paint.MultipleGradientPaint.ColorSpaceType;
/*      */ import com.sun.prism.j2d.paint.RadialGradientPaint;
/*      */ import com.sun.prism.paint.Gradient;
/*      */ import com.sun.prism.paint.ImagePattern;
/*      */ import com.sun.prism.paint.LinearGradient;
/*      */ import com.sun.prism.paint.RadialGradient;
/*      */ import com.sun.prism.paint.Stop;
/*      */ import com.sun.t2k.CharToGlyphMapper;
/*      */ import com.sun.t2k.CompositeStrike;
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.GradientPaint;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.LinearGradientPaint;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.TexturePaint;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.font.TextLayout;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Area;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.Ellipse2D.Float;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Line2D.Float;
/*      */ import java.awt.geom.NoninvertibleTransformException;
/*      */ import java.awt.geom.Path2D;
/*      */ import java.awt.geom.Path2D.Float;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Point2D.Float;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.geom.Rectangle2D.Float;
/*      */ import java.awt.geom.RoundRectangle2D;
/*      */ import java.awt.geom.RoundRectangle2D.Float;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.text.AttributedString;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ 
/*      */ public final class J2DPrismGraphics
/*      */   implements ReadbackGraphics
/*      */ {
/*   77 */   static final java.awt.MultipleGradientPaint.CycleMethod[] LGP_CYCLE_METHODS = { java.awt.MultipleGradientPaint.CycleMethod.NO_CYCLE, java.awt.MultipleGradientPaint.CycleMethod.REFLECT, java.awt.MultipleGradientPaint.CycleMethod.REPEAT };
/*      */ 
/*   82 */   static final com.sun.prism.j2d.paint.MultipleGradientPaint.CycleMethod[] RGP_CYCLE_METHODS = { com.sun.prism.j2d.paint.MultipleGradientPaint.CycleMethod.NO_CYCLE, com.sun.prism.j2d.paint.MultipleGradientPaint.CycleMethod.REFLECT, com.sun.prism.j2d.paint.MultipleGradientPaint.CycleMethod.REPEAT };
/*      */ 
/*   88 */   private static final PrismParallelCameraImpl DEFAULT_CAMERA = PrismParallelCameraImpl.getInstance();
/*   89 */   private static final com.sun.prism.BasicStroke DEFAULT_STROKE = new com.sun.prism.BasicStroke(1.0F, 2, 0, 10.0F);
/*      */ 
/*   91 */   private static final com.sun.prism.paint.Paint DEFAULT_PAINT = com.sun.prism.paint.Color.WHITE;
/*   92 */   static AffineTransform J2D_IDENTITY = new AffineTransform();
/*      */   private GeneralTransform3D pvTx;
/*      */   private int clipRectIndex;
/*   96 */   private boolean hasPreCullingBits = false;
/*      */ 
/*  308 */   private static ConcurrentHashMap<Font, WeakReference<Font>> fontMap = new ConcurrentHashMap();
/*      */ 
/*  310 */   private static volatile int cleared = 0;
/*      */ 
/*  400 */   private static AffineTransform tmpAT = new AffineTransform();
/*      */ 
/*  425 */   private static Path2D tmpQuadShape = new Path2D.Float();
/*      */ 
/*  439 */   private static Rectangle2D.Float tmpRect = new Rectangle2D.Float();
/*      */ 
/*  446 */   private static Ellipse2D tmpEllipse = new Ellipse2D.Float();
/*      */ 
/*  453 */   private static RoundRectangle2D tmpRRect = new RoundRectangle2D.Float();
/*      */ 
/*  462 */   private static Line2D tmpLine = new Line2D.Float();
/*      */ 
/*  469 */   private static AdaptorShape tmpAdaptor = new AdaptorShape(null);
/*      */   J2DPresentable target;
/*      */   Graphics2D g2d;
/*      */   Affine2D transform;
/*      */   com.sun.javafx.geom.Rectangle clipRect;
/*      */   RectBounds devClipRect;
/*      */   RectBounds finalClipRect;
/*      */   com.sun.prism.paint.Paint paint;
/*      */   boolean paintWasProportional;
/*      */   com.sun.prism.BasicStroke stroke;
/*      */   boolean cull;
/*  759 */   Rectangle2D nodeBounds = null;
/*      */ 
/*      */   public static java.awt.Color toJ2DColor(com.sun.prism.paint.Color paramColor)
/*      */   {
/*   99 */     return new java.awt.Color(paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue(), paramColor.getAlpha());
/*      */   }
/*      */ 
/*      */   static int fixFractions(float[] paramArrayOfFloat, java.awt.Color[] paramArrayOfColor)
/*      */   {
/*  127 */     float f1 = paramArrayOfFloat[0];
/*  128 */     int i = 1;
/*  129 */     int j = 1;
/*  130 */     while (i < paramArrayOfFloat.length) {
/*  131 */       float f2 = paramArrayOfFloat[i];
/*  132 */       java.awt.Color localColor = paramArrayOfColor[(i++)];
/*  133 */       if (f2 <= f1)
/*      */       {
/*  138 */         if (f2 >= 1.0F)
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/*  143 */         f2 = f1 + Math.ulp(f1);
/*  144 */         while ((i < paramArrayOfFloat.length) && 
/*  145 */           (paramArrayOfFloat[i] <= f2))
/*      */         {
/*  148 */           localColor = paramArrayOfColor[(i++)];
/*      */         }
/*      */       }
/*      */       float tmp87_85 = f2; f1 = tmp87_85; paramArrayOfFloat[j] = tmp87_85;
/*  152 */       paramArrayOfColor[(j++)] = localColor;
/*      */     }
/*  154 */     return j;
/*      */   }
/*      */ 
/*      */   public java.awt.Paint toJ2DPaint(com.sun.prism.paint.Paint paramPaint, Rectangle2D paramRectangle2D) {
/*  158 */     if ((paramPaint instanceof com.sun.prism.paint.Color))
/*  159 */       return toJ2DColor((com.sun.prism.paint.Color)paramPaint);
/*      */     Object localObject1;
/*      */     float f5;
/*      */     float f8;
/*  160 */     if ((paramPaint instanceof Gradient)) {
/*  161 */       localObject1 = (Gradient)paramPaint;
/*  162 */       if ((((Gradient)localObject1).isProportional()) && 
/*  163 */         (paramRectangle2D == null)) {
/*  164 */         return null;
/*      */       }
/*      */ 
/*  167 */       List localList = ((Gradient)localObject1).getStops();
/*  168 */       int i = localList.size();
/*  169 */       Object localObject2 = new float[i];
/*  170 */       Object localObject3 = new java.awt.Color[i];
/*  171 */       f5 = -1.0F;
/*  172 */       int j = 0;
/*      */       Object localObject5;
/*      */       float f9;
/*  173 */       for (int k = 0; k < i; k++) {
/*  174 */         localObject5 = (Stop)localList.get(k);
/*  175 */         f9 = ((Stop)localObject5).getOffset();
/*  176 */         j = (j != 0) || (f9 <= f5) ? 1 : 0;
/*      */         float tmp132_130 = f9; f5 = tmp132_130; localObject2[k] = tmp132_130;
/*  178 */         localObject3[k] = toJ2DColor(((Stop)localObject5).getColor());
/*      */       }
/*      */       Object localObject4;
/*  180 */       if (j != 0) {
/*  181 */         i = fixFractions((float[])localObject2, (java.awt.Color[])localObject3);
/*  182 */         if (i < localObject2.length) {
/*  183 */           localObject4 = new float[i];
/*  184 */           System.arraycopy(localObject2, 0, localObject4, 0, i);
/*  185 */           localObject2 = localObject4;
/*  186 */           localObject5 = new java.awt.Color[i];
/*  187 */           System.arraycopy(localObject3, 0, localObject5, 0, i);
/*  188 */           localObject3 = localObject5;
/*      */         }
/*      */       }
/*      */       float f10;
/*      */       float f16;
/*      */       Object localObject6;
/*  191 */       if ((localObject1 instanceof LinearGradient)) {
/*  192 */         localObject4 = (LinearGradient)paramPaint;
/*  193 */         f8 = ((LinearGradient)localObject4).getX1();
/*  194 */         f9 = ((LinearGradient)localObject4).getY1();
/*  195 */         f10 = ((LinearGradient)localObject4).getX2();
/*  196 */         float f11 = ((LinearGradient)localObject4).getY2();
/*  197 */         if (((Gradient)localObject1).isProportional()) {
/*  198 */           float f12 = (float)paramRectangle2D.getX();
/*  199 */           float f13 = (float)paramRectangle2D.getY();
/*  200 */           float f15 = (float)paramRectangle2D.getWidth();
/*  201 */           f16 = (float)paramRectangle2D.getHeight();
/*  202 */           f8 = f12 + f15 * f8;
/*  203 */           f9 = f13 + f16 * f9;
/*  204 */           f10 = f12 + f15 * f10;
/*  205 */           f11 = f13 + f16 * f11;
/*      */         }
/*  207 */         if ((f8 == f10) && (f9 == f9)) {
/*  208 */           f8 -= 1.0E-04F;
/*  209 */           f10 += 1.0E-04F;
/*      */         }
/*  211 */         Point2D.Float localFloat1 = new Point2D.Float(f8, f9);
/*      */ 
/*  213 */         Point2D.Float localFloat2 = new Point2D.Float(f10, f11);
/*      */ 
/*  215 */         localObject6 = LGP_CYCLE_METHODS[localObject1.getSpreadMethod()];
/*      */ 
/*  217 */         return new LinearGradientPaint(localFloat1, localFloat2, (float[])localObject2, (java.awt.Color[])localObject3, (java.awt.MultipleGradientPaint.CycleMethod)localObject6);
/*  218 */       }if ((localObject1 instanceof RadialGradient)) {
/*  219 */         localObject4 = (RadialGradient)localObject1;
/*  220 */         f8 = ((RadialGradient)localObject4).getCenterX();
/*  221 */         f9 = ((RadialGradient)localObject4).getCenterY();
/*  222 */         f10 = ((RadialGradient)localObject4).getRadius();
/*  223 */         double d = Math.toRadians(((RadialGradient)localObject4).getFocusAngle());
/*  224 */         float f14 = ((RadialGradient)localObject4).getFocusDistance();
/*  225 */         localObject6 = J2D_IDENTITY;
/*  226 */         if (((Gradient)localObject1).isProportional()) {
/*  227 */           f16 = (float)paramRectangle2D.getX();
/*  228 */           f17 = (float)paramRectangle2D.getY();
/*  229 */           f18 = (float)paramRectangle2D.getWidth();
/*  230 */           float f19 = (float)paramRectangle2D.getHeight();
/*  231 */           float f20 = Math.min(f18, f19);
/*  232 */           float f21 = f16 + f18 * 0.5F;
/*  233 */           float f22 = f17 + f19 * 0.5F;
/*  234 */           f8 = f21 + (f8 - 0.5F) * f20;
/*  235 */           f9 = f22 + (f9 - 0.5F) * f20;
/*  236 */           f10 *= f20;
/*  237 */           if ((f18 != f19) && (f18 != 0.0D) && (f19 != 0.0D)) {
/*  238 */             localObject6 = AffineTransform.getTranslateInstance(f21, f22);
/*  239 */             ((AffineTransform)localObject6).scale(f18 / f20, f19 / f20);
/*  240 */             ((AffineTransform)localObject6).translate(-f21, -f22);
/*      */           }
/*      */         }
/*  243 */         Point2D.Float localFloat3 = new Point2D.Float(f8, f9);
/*      */ 
/*  245 */         float f17 = (float)(f8 + f14 * f10 * Math.cos(d));
/*  246 */         float f18 = (float)(f9 + f14 * f10 * Math.sin(d));
/*  247 */         Point2D.Float localFloat4 = new Point2D.Float(f17, f18);
/*      */ 
/*  249 */         com.sun.prism.j2d.paint.MultipleGradientPaint.CycleMethod localCycleMethod = RGP_CYCLE_METHODS[localObject1.getSpreadMethod()];
/*      */ 
/*  251 */         return new RadialGradientPaint(localFloat3, f10, localFloat4, (float[])localObject2, (java.awt.Color[])localObject3, localCycleMethod, MultipleGradientPaint.ColorSpaceType.SRGB, (AffineTransform)localObject6);
/*      */       }
/*      */     }
/*  254 */     else if ((paramPaint instanceof ImagePattern)) {
/*  255 */       localObject1 = (ImagePattern)paramPaint;
/*  256 */       float f1 = ((ImagePattern)localObject1).getX();
/*  257 */       float f2 = ((ImagePattern)localObject1).getY();
/*  258 */       float f3 = ((ImagePattern)localObject1).getWidth();
/*  259 */       float f4 = ((ImagePattern)localObject1).getHeight();
/*  260 */       if (paramPaint.isProportional()) {
/*  261 */         if (paramRectangle2D == null) {
/*  262 */           return null;
/*      */         }
/*  264 */         f5 = (float)paramRectangle2D.getX();
/*  265 */         float f6 = (float)paramRectangle2D.getY();
/*  266 */         float f7 = (float)paramRectangle2D.getWidth();
/*  267 */         f8 = (float)paramRectangle2D.getHeight();
/*  268 */         f3 += f1;
/*  269 */         f4 += f2;
/*  270 */         f1 = f5 + f1 * f7;
/*  271 */         f2 = f6 + f2 * f8;
/*  272 */         f3 = f5 + f3 * f7;
/*  273 */         f4 = f6 + f4 * f8;
/*  274 */         f3 -= f1;
/*  275 */         f4 -= f2;
/*      */       }
/*  277 */       Texture localTexture = getResourceFactory().getCachedTexture(((ImagePattern)localObject1).getImage());
/*  278 */       BufferedImage localBufferedImage = ((J2DTexture)localTexture).getBufferedImage();
/*  279 */       return new TexturePaint(localBufferedImage, tmpRect(f1, f2, f3, f4));
/*      */     }
/*  281 */     throw new UnsupportedOperationException("Paint " + paramPaint + " not supported yet.");
/*      */   }
/*      */ 
/*      */   public static Stroke toJ2DStroke(com.sun.prism.BasicStroke paramBasicStroke) {
/*  285 */     float f = paramBasicStroke.getLineWidth();
/*  286 */     int i = paramBasicStroke.getType();
/*  287 */     if (i != 0) {
/*  288 */       f *= 2.0F;
/*      */     }
/*  290 */     java.awt.BasicStroke localBasicStroke = new java.awt.BasicStroke(f, paramBasicStroke.getEndCap(), paramBasicStroke.getLineJoin(), paramBasicStroke.getMiterLimit(), paramBasicStroke.getDashArray(), paramBasicStroke.getDashPhase());
/*      */ 
/*  297 */     if (i == 1)
/*  298 */       return new InnerStroke(localBasicStroke);
/*  299 */     if (i == 2) {
/*  300 */       return new OuterStroke(localBasicStroke);
/*      */     }
/*  302 */     return localBasicStroke;
/*      */   }
/*      */ 
/*      */   private static Font toJ2DFont(FontStrike paramFontStrike)
/*      */   {
/*  313 */     FontResource localFontResource = paramFontStrike.getFontResource();
/*      */ 
/*  315 */     Object localObject2 = localFontResource.getPeer();
/*      */     Object localObject4;
/*  316 */     if ((localObject2 != null) && ((localObject2 instanceof Font))) {
/*  317 */       localObject1 = (Font)localObject2;
/*      */     } else {
/*  319 */       if (PlatformUtil.isMac())
/*      */       {
/*  324 */         localObject3 = localFontResource.getPSName();
/*      */ 
/*  326 */         localObject1 = new Font((String)localObject3, 0, 12);
/*      */ 
/*  331 */         if (!((Font)localObject1).getPSName().equals(localObject3))
/*      */         {
/*  336 */           int i = localFontResource.isBold() ? 1 : 0;
/*  337 */           i |= (localFontResource.isItalic() ? 2 : 0);
/*  338 */           localObject1 = new Font(localFontResource.getFamilyName(), i, 12);
/*      */ 
/*  340 */           if (!((Font)localObject1).getPSName().equals(localObject3))
/*      */           {
/*  344 */             localObject4 = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
/*      */ 
/*  347 */             for (Object localObject6 : localObject4)
/*  348 */               if (localObject6.getPSName().equals(localObject3)) {
/*  349 */                 localObject1 = localObject6;
/*  350 */                 break;
/*      */               }
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  357 */         localObject1 = new Font(localFontResource.getFullName(), 0, 12);
/*      */       }
/*      */ 
/*  363 */       localFontResource.setPeer(localObject1);
/*      */     }
/*      */ 
/*  366 */     Object localObject1 = ((Font)localObject1).deriveFont(paramFontStrike.getSize());
/*  367 */     Object localObject3 = null;
/*  368 */     WeakReference localWeakReference = (WeakReference)fontMap.get(localObject1);
/*  369 */     if (localWeakReference != null) {
/*  370 */       localObject3 = (Font)localWeakReference.get();
/*  371 */       if (localObject3 == null) {
/*  372 */         cleared += 1;
/*      */       }
/*      */     }
/*  375 */     if (localObject3 == null) {
/*  376 */       if ((fontMap.size() > 100) && (cleared > 10)) {
/*  377 */         for (localObject4 = fontMap.keySet().iterator(); ((Iterator)localObject4).hasNext(); ) { ??? = (Font)((Iterator)localObject4).next();
/*  378 */           localWeakReference = (WeakReference)fontMap.get(???);
/*  379 */           if ((localWeakReference == null) || (localWeakReference.get() == null)) {
/*  380 */             fontMap.remove(???);
/*      */           }
/*      */         }
/*  383 */         cleared = 0;
/*      */       }
/*  385 */       localObject3 = J2DFontFactory.getCompositeFont((Font)localObject1);
/*  386 */       localWeakReference = new WeakReference(localObject3);
/*  387 */       fontMap.put(localObject1, localWeakReference);
/*      */     }
/*  389 */     return localObject3;
/*      */   }
/*      */ 
/*      */   public static AffineTransform toJ2DTransform(BaseTransform paramBaseTransform)
/*      */   {
/*  395 */     return new AffineTransform(paramBaseTransform.getMxx(), paramBaseTransform.getMyx(), paramBaseTransform.getMxy(), paramBaseTransform.getMyy(), paramBaseTransform.getMxt(), paramBaseTransform.getMyt());
/*      */   }
/*      */ 
/*      */   public static AffineTransform tmpJ2DTransform(BaseTransform paramBaseTransform)
/*      */   {
/*  405 */     tmpAT.setTransform(paramBaseTransform.getMxx(), paramBaseTransform.getMyx(), paramBaseTransform.getMxy(), paramBaseTransform.getMyy(), paramBaseTransform.getMxt(), paramBaseTransform.getMyt());
/*      */ 
/*  408 */     return tmpAT;
/*      */   }
/*      */ 
/*      */   public static BaseTransform toPrTransform(AffineTransform paramAffineTransform)
/*      */   {
/*  414 */     return BaseTransform.getInstance(paramAffineTransform.getScaleX(), paramAffineTransform.getShearY(), paramAffineTransform.getShearX(), paramAffineTransform.getScaleY(), paramAffineTransform.getTranslateX(), paramAffineTransform.getTranslateY());
/*      */   }
/*      */ 
/*      */   public static com.sun.javafx.geom.Rectangle toPrRect(java.awt.Rectangle paramRectangle)
/*      */   {
/*  422 */     return new com.sun.javafx.geom.Rectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*      */   }
/*      */ 
/*      */   private static java.awt.Shape tmpQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  430 */     tmpQuadShape.reset();
/*  431 */     tmpQuadShape.moveTo(paramFloat1, paramFloat2);
/*  432 */     tmpQuadShape.lineTo(paramFloat3, paramFloat2);
/*  433 */     tmpQuadShape.lineTo(paramFloat3, paramFloat4);
/*  434 */     tmpQuadShape.lineTo(paramFloat1, paramFloat4);
/*  435 */     tmpQuadShape.closePath();
/*  436 */     return tmpQuadShape;
/*      */   }
/*      */ 
/*      */   private static Rectangle2D tmpRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  442 */     tmpRect.setRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  443 */     return tmpRect;
/*      */   }
/*      */ 
/*      */   private static java.awt.Shape tmpEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  449 */     tmpEllipse.setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  450 */     return tmpEllipse;
/*      */   }
/*      */ 
/*      */   private static java.awt.Shape tmpRRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */   {
/*  458 */     tmpRRect.setRoundRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*  459 */     return tmpRRect;
/*      */   }
/*      */ 
/*      */   private static java.awt.Shape tmpLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  465 */     tmpLine.setLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  466 */     return tmpLine;
/*      */   }
/*      */ 
/*      */   private static java.awt.Shape tmpShape(com.sun.javafx.geom.Shape paramShape)
/*      */   {
/*  471 */     tmpAdaptor.setShape(paramShape);
/*  472 */     return tmpAdaptor;
/*      */   }
/*      */ 
/*      */   public J2DPrismGraphics(J2DPresentable paramJ2DPresentable, Graphics2D paramGraphics2D)
/*      */   {
/*  487 */     this(paramGraphics2D, paramJ2DPresentable.getContentWidth(), paramJ2DPresentable.getContentHeight());
/*  488 */     this.target = paramJ2DPresentable;
/*      */   }
/*      */ 
/*      */   public J2DPrismGraphics(Graphics2D paramGraphics2D, int paramInt1, int paramInt2) {
/*  492 */     this.g2d = paramGraphics2D;
/*  493 */     this.transform = new Affine2D();
/*  494 */     this.devClipRect = new RectBounds(0.0F, 0.0F, paramInt1, paramInt2);
/*  495 */     this.finalClipRect = new RectBounds(0.0F, 0.0F, paramInt1, paramInt2);
/*  496 */     this.cull = true;
/*      */ 
/*  498 */     paramGraphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
/*      */ 
/*  500 */     paramGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*      */ 
/*  502 */     paramGraphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/*      */ 
/*  507 */     paramGraphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
/*      */ 
/*  509 */     paramGraphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*      */ 
/*  513 */     setTransform(BaseTransform.IDENTITY_TRANSFORM);
/*  514 */     setPaint(DEFAULT_PAINT);
/*  515 */     setStroke(DEFAULT_STROKE);
/*      */   }
/*      */ 
/*      */   public RenderTarget getRenderTarget() {
/*  519 */     return this.target;
/*      */   }
/*      */ 
/*      */   public Screen getAssociatedScreen() {
/*  523 */     return this.target.getAssociatedScreen();
/*      */   }
/*      */ 
/*      */   public ResourceFactory getResourceFactory() {
/*  527 */     return this.target.getResourceFactory();
/*      */   }
/*      */ 
/*      */   public void reset() {
/*      */   }
/*      */ 
/*      */   public com.sun.javafx.geom.Rectangle getClipRect() {
/*  534 */     return this.clipRect == null ? null : new com.sun.javafx.geom.Rectangle(this.clipRect);
/*      */   }
/*      */ 
/*      */   public com.sun.javafx.geom.Rectangle getClipRectNoClone() {
/*  538 */     return this.clipRect;
/*      */   }
/*      */ 
/*      */   public RectBounds getFinalClipNoClone() {
/*  542 */     return this.finalClipRect;
/*      */   }
/*      */ 
/*      */   public void setClipRect(com.sun.javafx.geom.Rectangle paramRectangle) {
/*  546 */     this.finalClipRect.setBounds(this.devClipRect);
/*  547 */     if (paramRectangle == null) {
/*  548 */       this.clipRect = null;
/*  549 */       this.g2d.setClip(null);
/*      */     } else {
/*  551 */       this.clipRect = new com.sun.javafx.geom.Rectangle(paramRectangle);
/*  552 */       this.finalClipRect.intersectWith(paramRectangle);
/*  553 */       this.g2d.setTransform(J2D_IDENTITY);
/*  554 */       this.g2d.setClip(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*  555 */       this.g2d.setTransform(tmpJ2DTransform(this.transform));
/*      */     }
/*      */   }
/*      */ 
/*      */   private AlphaComposite getAWTComposite() {
/*  560 */     return (AlphaComposite)this.g2d.getComposite();
/*      */   }
/*      */ 
/*      */   public float getExtraAlpha() {
/*  564 */     return getAWTComposite().getAlpha();
/*      */   }
/*      */ 
/*      */   public void setExtraAlpha(float paramFloat) {
/*  568 */     this.g2d.setComposite(getAWTComposite().derive(paramFloat));
/*      */   }
/*      */ 
/*      */   public CompositeMode getCompositeMode() {
/*  572 */     int i = getAWTComposite().getRule();
/*  573 */     switch (i) {
/*      */     case 1:
/*  575 */       return CompositeMode.CLEAR;
/*      */     case 2:
/*  577 */       return CompositeMode.SRC;
/*      */     case 3:
/*  579 */       return CompositeMode.SRC_OVER;
/*      */     }
/*  581 */     throw new InternalError("Unrecognized AlphaCompsite rule: " + i);
/*      */   }
/*      */ 
/*      */   public void setCompositeMode(CompositeMode paramCompositeMode)
/*      */   {
/*  586 */     AlphaComposite localAlphaComposite = getAWTComposite();
/*  587 */     switch (1.$SwitchMap$com$sun$prism$CompositeMode[paramCompositeMode.ordinal()]) {
/*      */     case 1:
/*  589 */       localAlphaComposite = localAlphaComposite.derive(1);
/*  590 */       break;
/*      */     case 2:
/*  592 */       localAlphaComposite = localAlphaComposite.derive(2);
/*  593 */       break;
/*      */     case 3:
/*  595 */       localAlphaComposite = localAlphaComposite.derive(3);
/*  596 */       break;
/*      */     default:
/*  598 */       throw new InternalError("Unrecognized composite mode: " + paramCompositeMode);
/*      */     }
/*  600 */     this.g2d.setComposite(localAlphaComposite);
/*      */   }
/*      */ 
/*      */   public com.sun.prism.paint.Paint getPaint() {
/*  604 */     return this.paint;
/*      */   }
/*      */ 
/*      */   public void setPaint(com.sun.prism.paint.Paint paramPaint) {
/*  608 */     this.paint = paramPaint;
/*  609 */     java.awt.Paint localPaint = toJ2DPaint(paramPaint, null);
/*  610 */     if (localPaint == null) {
/*  611 */       this.paintWasProportional = true;
/*      */     } else {
/*  613 */       this.paintWasProportional = false;
/*  614 */       this.g2d.setPaint(localPaint);
/*      */     }
/*      */   }
/*      */ 
/*      */   public com.sun.prism.BasicStroke getStroke() {
/*  619 */     return this.stroke;
/*      */   }
/*      */ 
/*      */   public void setStroke(com.sun.prism.BasicStroke paramBasicStroke) {
/*  623 */     this.stroke = paramBasicStroke;
/*  624 */     this.g2d.setStroke(toJ2DStroke(paramBasicStroke));
/*      */   }
/*      */ 
/*      */   public BaseTransform getTransformNoClone() {
/*  628 */     return this.transform;
/*      */   }
/*      */ 
/*      */   public void translate(float paramFloat1, float paramFloat2) {
/*  632 */     this.transform.translate(paramFloat1, paramFloat2);
/*  633 */     this.g2d.translate(paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */   public void scale(float paramFloat1, float paramFloat2) {
/*  637 */     this.transform.scale(paramFloat1, paramFloat2);
/*  638 */     this.g2d.scale(paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */   public void transform(BaseTransform paramBaseTransform) {
/*  642 */     if (!paramBaseTransform.is2D())
/*      */     {
/*  644 */       return;
/*      */     }
/*  646 */     this.transform.concatenate(paramBaseTransform);
/*  647 */     this.g2d.setTransform(tmpJ2DTransform(this.transform));
/*      */   }
/*      */ 
/*      */   public void setTransform(BaseTransform paramBaseTransform)
/*      */   {
/*  652 */     if (paramBaseTransform == null) paramBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*  653 */     this.transform.setTransform(paramBaseTransform);
/*  654 */     this.g2d.setTransform(tmpJ2DTransform(this.transform));
/*      */   }
/*      */ 
/*      */   public void setTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*      */   {
/*  661 */     this.transform.setTransform(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*  662 */     this.g2d.setTransform(tmpJ2DTransform(this.transform));
/*      */   }
/*      */ 
/*      */   public void clear() {
/*  666 */     clear(com.sun.prism.paint.Color.TRANSPARENT);
/*      */   }
/*      */ 
/*      */   public void clear(com.sun.prism.paint.Color paramColor) {
/*  670 */     getRenderTarget().setOpaque(paramColor.isOpaque());
/*  671 */     clear(toJ2DColor(paramColor));
/*      */   }
/*      */ 
/*      */   public void clear(java.awt.Color paramColor) {
/*  675 */     Graphics2D localGraphics2D = (Graphics2D)this.g2d.create();
/*  676 */     localGraphics2D.setTransform(J2D_IDENTITY);
/*  677 */     localGraphics2D.setComposite(AlphaComposite.Src);
/*  678 */     localGraphics2D.setColor(paramColor);
/*  679 */     localGraphics2D.fillRect(0, 0, this.target.getContentWidth(), this.target.getContentHeight());
/*  680 */     localGraphics2D.dispose();
/*      */   }
/*      */ 
/*      */   public void clearQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  684 */     this.g2d.setComposite(AlphaComposite.Clear);
/*  685 */     this.g2d.fill(tmpQuad(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*      */   }
/*      */ 
/*      */   public void fill(java.awt.Shape paramShape) {
/*  689 */     if (this.paintWasProportional) {
/*  690 */       if (this.nodeBounds != null)
/*  691 */         this.g2d.setPaint(toJ2DPaint(this.paint, this.nodeBounds));
/*      */       else {
/*  693 */         this.g2d.setPaint(toJ2DPaint(this.paint, paramShape.getBounds2D()));
/*      */       }
/*      */     }
/*  696 */     this.g2d.fill(paramShape);
/*      */   }
/*      */ 
/*      */   public void fill(com.sun.javafx.geom.Shape paramShape) {
/*  700 */     fill(tmpShape(paramShape));
/*      */   }
/*      */ 
/*      */   public void fillRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  704 */     fill(tmpRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*      */   }
/*      */ 
/*      */   public void fillRoundRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */   {
/*  710 */     fill(tmpRRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6));
/*      */   }
/*      */ 
/*      */   public void fillEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  714 */     fill(tmpEllipse(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*      */   }
/*      */ 
/*      */   public void fillQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  718 */     fill(tmpQuad(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*      */   }
/*      */ 
/*      */   public void draw(java.awt.Shape paramShape) {
/*  722 */     if (this.paintWasProportional) {
/*  723 */       if (this.nodeBounds != null)
/*  724 */         this.g2d.setPaint(toJ2DPaint(this.paint, this.nodeBounds));
/*      */       else
/*  726 */         this.g2d.setPaint(toJ2DPaint(this.paint, paramShape.getBounds2D()));
/*      */     }
/*      */     try
/*      */     {
/*  730 */       this.g2d.draw(paramShape);
/*      */     }
/*      */     catch (Throwable localThrowable)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void draw(com.sun.javafx.geom.Shape paramShape)
/*      */   {
/*  740 */     draw(tmpShape(paramShape));
/*      */   }
/*      */ 
/*      */   public void drawLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  744 */     draw(tmpLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*      */   }
/*      */ 
/*      */   public void drawRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  748 */     draw(tmpRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*      */   }
/*      */ 
/*      */   public void drawRoundRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
/*  752 */     draw(tmpRRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6));
/*      */   }
/*      */ 
/*      */   public void drawEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  756 */     draw(tmpEllipse(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*      */   }
/*      */ 
/*      */   public void setNodeBounds(RectBounds paramRectBounds)
/*      */   {
/*  762 */     this.nodeBounds = (paramRectBounds != null ? new Rectangle2D.Float(paramRectBounds.getMinX(), paramRectBounds.getMinY(), paramRectBounds.getWidth(), paramRectBounds.getHeight()) : null);
/*      */   }
/*      */ 
/*      */   public void drawString(String paramString, FontStrike paramFontStrike, float paramFloat1, float paramFloat2, com.sun.prism.paint.Color paramColor, int paramInt1, int paramInt2)
/*      */   {
/*  771 */     this.g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
/*      */ 
/*  776 */     if (paramFontStrike.getAAMode() == 1) {
/*  777 */       this.g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
/*      */     }
/*      */ 
/*  780 */     int i = 0;
/*  781 */     int j = paramString.length();
/*  782 */     int k = 0;
/*  783 */     CompositeStrike localCompositeStrike = null;
/*  784 */     CharToGlyphMapper localCharToGlyphMapper = null;
/*      */     int n;
/*      */     int m;
/*  785 */     if ((paramFontStrike instanceof CompositeStrike)) {
/*  786 */       localCompositeStrike = (CompositeStrike)paramFontStrike;
/*  787 */       localCharToGlyphMapper = localCompositeStrike.getFontResource().getGlyphMapper();
/*  788 */       n = localCharToGlyphMapper.getGlyphCode(paramString.charAt(0));
/*  789 */       m = localCompositeStrike.getStrikeSlotForGlyph(n);
/*  790 */       paramFontStrike = localCompositeStrike.getStrikeSlot(m);
/*  791 */       for (k = 1; k < j; k++) {
/*  792 */         n = localCharToGlyphMapper.getGlyphCode(paramString.charAt(k));
/*  793 */         if (m != localCompositeStrike.getStrikeSlotForGlyph(n)) {
/*  794 */           i = 1;
/*  795 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  800 */     Font localFont = toJ2DFont(paramFontStrike);
/*  801 */     AttributedString localAttributedString = null;
/*  802 */     if (i != 0) {
/*  803 */       localAttributedString = new AttributedString(paramString);
/*      */ 
/*  805 */       localAttributedString.addAttribute(TextAttribute.FONT, localFont, 0, k);
/*      */ 
/*  807 */       int i1 = k; int i2 = j; for (int i3 = j; i1 < i3; 
/*  808 */         i3 = i2)
/*      */       {
/*  810 */         n = localCharToGlyphMapper.getGlyphCode(paramString.charAt(k));
/*  811 */         m = localCompositeStrike.getStrikeSlotForGlyph(n);
/*  812 */         localFont = toJ2DFont(localCompositeStrike.getStrikeSlot(m));
/*      */         while (true) {
/*  814 */           k++; if (k < i3) {
/*  815 */             n = localCharToGlyphMapper.getGlyphCode(paramString.charAt(k));
/*  816 */             if (m != localCompositeStrike.getStrikeSlotForGlyph(n))
/*  817 */               break;
/*      */           }
/*      */         }
/*  820 */         if (i1 < k) {
/*  821 */           localAttributedString.addAttribute(TextAttribute.FONT, localFont, i1, k);
/*      */         }
/*      */ 
/*  824 */         while (i2 > k) {
/*  825 */           n = localCharToGlyphMapper.getGlyphCode(paramString.charAt(i2 - 1));
/*  826 */           if (m != localCompositeStrike.getStrikeSlotForGlyph(n)) {
/*      */             break;
/*      */           }
/*  829 */           i2--;
/*      */         }
/*  831 */         if (i2 < i3)
/*  832 */           localAttributedString.addAttribute(TextAttribute.FONT, localFont, i2, i3);
/*  808 */         i1 = k;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  836 */     if (this.paintWasProportional) {
/*  837 */       Object localObject = this.nodeBounds;
/*  838 */       if (localObject == null) {
/*  839 */         if (localAttributedString == null) {
/*  840 */           localObject = localFont.getStringBounds(paramString, this.g2d.getFontRenderContext());
/*      */         }
/*      */         else {
/*  843 */           TextLayout localTextLayout = new TextLayout(localAttributedString.getIterator(), this.g2d.getFontRenderContext());
/*      */ 
/*  845 */           localObject = new Rectangle2D.Float(0.0F, -localTextLayout.getAscent(), localTextLayout.getAdvance(), localTextLayout.getAscent() + localTextLayout.getDescent() + localTextLayout.getLeading());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  852 */       this.g2d.setPaint(toJ2DPaint(this.paint, (Rectangle2D)localObject));
/*      */     }
/*      */ 
/*  855 */     if (paramColor != null) {
/*  856 */       paramInt1 = paramInt1 < 0 ? 0 : paramInt1;
/*  857 */       paramInt2 = paramInt2 < j ? paramInt2 : j;
/*  858 */       if (paramInt1 < paramInt2) {
/*  859 */         if (localAttributedString == null) {
/*  860 */           localAttributedString = new AttributedString(paramString);
/*  861 */           localAttributedString.addAttribute(TextAttribute.FONT, localFont, 0, j);
/*      */         }
/*  863 */         localAttributedString.addAttribute(TextAttribute.FOREGROUND, toJ2DColor(paramColor), paramInt1, paramInt2);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  869 */     if (localAttributedString != null) {
/*  870 */       this.g2d.drawString(localAttributedString.getIterator(), paramFloat1, paramFloat2);
/*      */     } else {
/*  872 */       this.g2d.setFont(localFont);
/*  873 */       this.g2d.drawString(paramString, paramFloat1, paramFloat2);
/*      */     }
/*      */ 
/*  877 */     this.g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  878 */     this.g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*      */   }
/*      */ 
/*      */   public void drawString(String paramString, FontStrike paramFontStrike, float paramFloat1, float paramFloat2) {
/*  882 */     drawString(paramString, paramFontStrike, paramFloat1, paramFloat2, null, 0, 0);
/*      */   }
/*      */ 
/*      */   public void drawMappedTextureRaw(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
/*      */   {
/*  890 */     BufferedImage localBufferedImage = ((J2DTexture)paramTexture).getBufferedImage();
/*  891 */     float f1 = paramFloat7 - paramFloat5;
/*  892 */     float f2 = paramFloat8 - paramFloat6;
/*  893 */     float f3 = paramFloat9 - paramFloat5;
/*  894 */     float f4 = paramFloat10 - paramFloat6;
/*      */ 
/*  899 */     this.g2d.setTransform(J2D_IDENTITY);
/*  900 */     tmpAT.setTransform(f1, f2, f3, f4, paramFloat5, paramFloat6);
/*      */     try {
/*  902 */       tmpAT.invert();
/*  903 */       this.g2d.translate(paramFloat1, paramFloat2);
/*  904 */       this.g2d.scale(paramFloat3 - paramFloat1, paramFloat4 - paramFloat2);
/*  905 */       this.g2d.transform(tmpAT);
/*  906 */       this.g2d.drawImage(localBufferedImage, 0, 0, 1, 1, null);
/*      */     } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/*      */     }
/*  909 */     setTransform(this.transform);
/*      */   }
/*      */ 
/*      */   public void drawTexture(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  913 */     BufferedImage localBufferedImage = ((J2DTexture)paramTexture).getBufferedImage();
/*  914 */     this.g2d.drawImage(localBufferedImage, (int)paramFloat1, (int)paramFloat2, (int)(paramFloat1 + paramFloat3), (int)(paramFloat2 + paramFloat4), 0, 0, (int)paramFloat3, (int)paramFloat4, null);
/*      */   }
/*      */ 
/*      */   public void drawTexture(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*      */   {
/*  921 */     BufferedImage localBufferedImage = ((J2DTexture)paramTexture).getBufferedImage();
/*      */ 
/*  924 */     this.g2d.drawImage(localBufferedImage, (int)paramFloat1, (int)paramFloat2, (int)paramFloat3, (int)paramFloat4, (int)paramFloat5, (int)paramFloat6, (int)paramFloat7, (int)paramFloat8, null);
/*      */   }
/*      */ 
/*      */   public void drawTexture(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt)
/*      */   {
/*  942 */     BufferedImage localBufferedImage = ((J2DTexture)paramTexture).getBufferedImage();
/*      */ 
/*  945 */     this.g2d.drawImage(localBufferedImage, (int)paramFloat1, (int)paramFloat2, (int)paramFloat3, (int)paramFloat4, (int)paramFloat5, (int)paramFloat6, (int)paramFloat7, (int)paramFloat8, null);
/*      */   }
/*      */ 
/*      */   public void drawTextureRaw(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*      */   {
/*  955 */     int i = paramTexture.getContentWidth();
/*  956 */     int j = paramTexture.getContentHeight();
/*  957 */     paramFloat5 *= i;
/*  958 */     paramFloat6 *= j;
/*  959 */     paramFloat7 *= i;
/*  960 */     paramFloat8 *= j;
/*  961 */     drawTexture(paramTexture, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*      */   }
/*      */ 
/*      */   public void drawTextureVO(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, int paramInt)
/*      */   {
/*  988 */     java.awt.Paint localPaint = this.g2d.getPaint();
/*  989 */     Composite localComposite = this.g2d.getComposite();
/*  990 */     java.awt.Color localColor1 = new java.awt.Color(1.0F, 1.0F, 1.0F, paramFloat1);
/*  991 */     java.awt.Color localColor2 = new java.awt.Color(1.0F, 1.0F, 1.0F, paramFloat2);
/*  992 */     this.g2d.setPaint(new GradientPaint(0.0F, paramFloat4, localColor1, 0.0F, paramFloat6, localColor2, true));
/*  993 */     this.g2d.setComposite(AlphaComposite.Src);
/*  994 */     int i = (int)Math.floor(Math.min(paramFloat3, paramFloat5));
/*  995 */     int j = (int)Math.floor(Math.min(paramFloat4, paramFloat6));
/*  996 */     int k = (int)Math.ceil(Math.max(paramFloat3, paramFloat5)) - i;
/*  997 */     int m = (int)Math.ceil(Math.max(paramFloat4, paramFloat6)) - j;
/*  998 */     this.g2d.fillRect(i, j, k, m);
/*  999 */     this.g2d.setComposite(AlphaComposite.SrcIn);
/* 1000 */     drawTexture(paramTexture, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10);
/* 1001 */     this.g2d.setComposite(localComposite);
/* 1002 */     this.g2d.setPaint(localPaint);
/*      */   }
/*      */ 
/*      */   public boolean canReadBack() {
/* 1006 */     return true;
/*      */   }
/*      */ 
/*      */   public RTTexture readBack(com.sun.javafx.geom.Rectangle paramRectangle) {
/* 1010 */     J2DRTTexture localJ2DRTTexture = this.target.getReadbackBuffer();
/* 1011 */     Graphics2D localGraphics2D = localJ2DRTTexture.createAWTGraphics2D();
/* 1012 */     localGraphics2D.setComposite(AlphaComposite.Src);
/* 1013 */     int i = paramRectangle.x;
/* 1014 */     int j = paramRectangle.y;
/* 1015 */     int k = paramRectangle.width;
/* 1016 */     int m = paramRectangle.height;
/* 1017 */     int n = i + k;
/* 1018 */     int i1 = j + m;
/* 1019 */     localGraphics2D.drawImage(this.target.getBackBuffer(), 0, 0, k, m, i, j, n, i1, null);
/*      */ 
/* 1022 */     localGraphics2D.dispose();
/* 1023 */     return localJ2DRTTexture;
/*      */   }
/*      */ 
/*      */   public void releaseReadBackBuffer(RTTexture paramRTTexture)
/*      */   {
/*      */   }
/*      */ 
/*      */   public PrismCameraImpl getCameraNoClone() {
/* 1031 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */   public boolean hasOrthoCamera() {
/* 1035 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */   public boolean isDepthBuffer() {
/* 1039 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isDepthTest() {
/* 1043 */     return false;
/*      */   }
/*      */ 
/*      */   public void scale(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 1047 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */   public void setTransform3D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*      */   {
/* 1054 */     if ((paramDouble3 != 0.0D) || (paramDouble7 != 0.0D) || (paramDouble9 != 0.0D) || (paramDouble10 != 0.0D) || (paramDouble11 != 1.0D) || (paramDouble12 != 0.0D))
/*      */     {
/* 1057 */       throw new UnsupportedOperationException("3D transforms not supported.");
/*      */     }
/* 1059 */     setTransform(paramDouble1, paramDouble5, paramDouble2, paramDouble6, paramDouble4, paramDouble8);
/*      */   }
/*      */ 
/*      */   public void setCamera(PrismCameraImpl paramPrismCameraImpl)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setDepthBuffer(boolean paramBoolean)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setDepthTest(boolean paramBoolean)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void sync()
/*      */   {
/*      */   }
/*      */ 
/*      */   public void translate(float paramFloat1, float paramFloat2, float paramFloat3)
/*      */   {
/* 1084 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */   public void setWindowProjViewTx(GeneralTransform3D paramGeneralTransform3D) {
/* 1088 */     this.pvTx = paramGeneralTransform3D;
/*      */   }
/*      */ 
/*      */   public GeneralTransform3D getWindowProjViewTxNoClone() {
/* 1092 */     return this.pvTx;
/*      */   }
/*      */ 
/*      */   public void setCulling(boolean paramBoolean) {
/* 1096 */     this.cull = paramBoolean;
/*      */   }
/*      */ 
/*      */   public boolean isCulling() {
/* 1100 */     return this.cull;
/*      */   }
/*      */ 
/*      */   public void setClipRectIndex(int paramInt) {
/* 1104 */     this.clipRectIndex = paramInt;
/*      */   }
/*      */   public int getClipRectIndex() {
/* 1107 */     return this.clipRectIndex;
/*      */   }
/*      */ 
/*      */   public void setHasPreCullingBits(boolean paramBoolean) {
/* 1111 */     this.hasPreCullingBits = paramBoolean;
/*      */   }
/*      */ 
/*      */   public boolean hasPreCullingBits() {
/* 1115 */     return this.hasPreCullingBits;
/*      */   }
/*      */ 
/*      */   static class OuterStroke extends J2DPrismGraphics.FilterStroke
/*      */   {
/* 1369 */     static double SQRT_2 = Math.sqrt(2.0D);
/*      */ 
/*      */     OuterStroke(java.awt.BasicStroke paramBasicStroke) {
/* 1372 */       super();
/*      */     }
/*      */ 
/*      */     protected java.awt.Shape makeStrokedRect(Rectangle2D paramRectangle2D) {
/* 1376 */       if (this.stroke.getDashArray() != null) {
/* 1377 */         return null;
/*      */       }
/* 1379 */       float f1 = this.stroke.getLineWidth() / 2.0F;
/* 1380 */       float f2 = (float)paramRectangle2D.getX();
/* 1381 */       float f3 = (float)paramRectangle2D.getY();
/* 1382 */       float f4 = f2 + (float)paramRectangle2D.getWidth();
/* 1383 */       float f5 = f3 + (float)paramRectangle2D.getHeight();
/* 1384 */       GeneralPath localGeneralPath = new GeneralPath();
/*      */ 
/* 1386 */       localGeneralPath.moveTo(f2, f3);
/* 1387 */       localGeneralPath.lineTo(f4, f3);
/* 1388 */       localGeneralPath.lineTo(f4, f5);
/* 1389 */       localGeneralPath.lineTo(f2, f5);
/* 1390 */       localGeneralPath.closePath();
/* 1391 */       float f6 = f2 - f1;
/* 1392 */       float f7 = f3 - f1;
/* 1393 */       float f8 = f4 + f1;
/* 1394 */       float f9 = f5 + f1;
/* 1395 */       switch (this.stroke.getLineJoin())
/*      */       {
/*      */       case 0:
/* 1398 */         if (this.stroke.getMiterLimit() >= SQRT_2)
/*      */         {
/* 1400 */           localGeneralPath.moveTo(f6, f7);
/* 1401 */           localGeneralPath.lineTo(f6, f9);
/* 1402 */           localGeneralPath.lineTo(f8, f9);
/* 1403 */           localGeneralPath.lineTo(f8, f7);
/* 1404 */           localGeneralPath.closePath();
/* 1405 */         }break;
/*      */       case 2:
/* 1410 */         localGeneralPath.moveTo(f6, f3);
/* 1411 */         localGeneralPath.lineTo(f6, f5);
/* 1412 */         localGeneralPath.lineTo(f2, f9);
/* 1413 */         localGeneralPath.lineTo(f4, f9);
/* 1414 */         localGeneralPath.lineTo(f8, f5);
/* 1415 */         localGeneralPath.lineTo(f8, f3);
/* 1416 */         localGeneralPath.lineTo(f4, f7);
/* 1417 */         localGeneralPath.lineTo(f2, f7);
/* 1418 */         localGeneralPath.closePath();
/* 1419 */         break;
/*      */       case 1:
/* 1422 */         localGeneralPath.moveTo(f6, f3);
/* 1423 */         localGeneralPath.lineTo(f6, f5);
/* 1424 */         cornerArc(localGeneralPath, f6, f5, f6, f9, f2, f9);
/* 1425 */         localGeneralPath.lineTo(f4, f9);
/* 1426 */         cornerArc(localGeneralPath, f4, f9, f8, f9, f8, f5);
/* 1427 */         localGeneralPath.lineTo(f8, f3);
/* 1428 */         cornerArc(localGeneralPath, f8, f3, f8, f7, f4, f7);
/* 1429 */         localGeneralPath.lineTo(f2, f7);
/* 1430 */         cornerArc(localGeneralPath, f2, f7, f6, f7, f6, f3);
/* 1431 */         localGeneralPath.closePath();
/* 1432 */         break;
/*      */       }
/* 1434 */       throw new InternalError("Unrecognized line join style");
/*      */ 
/* 1436 */       return localGeneralPath;
/*      */     }
/*      */ 
/*      */     protected java.awt.Shape makeStrokedEllipse(Ellipse2D paramEllipse2D)
/*      */     {
/* 1441 */       if (this.stroke.getDashArray() != null) {
/* 1442 */         return null;
/*      */       }
/* 1444 */       float f1 = this.stroke.getLineWidth() / 2.0F;
/* 1445 */       float f2 = (float)paramEllipse2D.getWidth();
/* 1446 */       float f3 = (float)paramEllipse2D.getHeight();
/* 1447 */       if ((f2 > f3 * 2.0F) || (f3 > f2 * 2.0F))
/*      */       {
/* 1453 */         return null;
/*      */       }
/* 1455 */       float f4 = (float)paramEllipse2D.getX();
/* 1456 */       float f5 = (float)paramEllipse2D.getY();
/* 1457 */       float f6 = f4 + f2 / 2.0F;
/* 1458 */       float f7 = f5 + f3 / 2.0F;
/* 1459 */       float f8 = f4 + f2;
/* 1460 */       float f9 = f5 + f3;
/* 1461 */       GeneralPath localGeneralPath = new GeneralPath();
/* 1462 */       localGeneralPath.moveTo(f6, f5);
/* 1463 */       cornerArc(localGeneralPath, f6, f5, f8, f5, f8, f7);
/* 1464 */       cornerArc(localGeneralPath, f8, f7, f8, f9, f6, f9);
/* 1465 */       cornerArc(localGeneralPath, f6, f9, f4, f9, f4, f7);
/* 1466 */       cornerArc(localGeneralPath, f4, f7, f4, f5, f6, f5);
/* 1467 */       localGeneralPath.closePath();
/* 1468 */       f4 -= f1;
/* 1469 */       f5 -= f1;
/* 1470 */       f8 += f1;
/* 1471 */       f9 += f1;
/* 1472 */       localGeneralPath.moveTo(f6, f5);
/* 1473 */       cornerArc(localGeneralPath, f6, f5, f4, f5, f4, f7);
/* 1474 */       cornerArc(localGeneralPath, f4, f7, f4, f9, f6, f9);
/* 1475 */       cornerArc(localGeneralPath, f6, f9, f8, f9, f8, f7);
/* 1476 */       cornerArc(localGeneralPath, f8, f7, f8, f5, f6, f5);
/* 1477 */       localGeneralPath.closePath();
/* 1478 */       return localGeneralPath;
/*      */     }
/*      */ 
/*      */     protected java.awt.Shape makeStrokedShape(java.awt.Shape paramShape)
/*      */     {
/* 1483 */       java.awt.Shape localShape = this.stroke.createStrokedShape(paramShape);
/* 1484 */       Area localArea = new Area(localShape);
/* 1485 */       localArea.subtract(new Area(paramShape));
/* 1486 */       return localArea;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class InnerStroke extends J2DPrismGraphics.FilterStroke
/*      */   {
/*      */     InnerStroke(java.awt.BasicStroke paramBasicStroke)
/*      */     {
/* 1281 */       super();
/*      */     }
/*      */ 
/*      */     protected java.awt.Shape makeStrokedRect(Rectangle2D paramRectangle2D) {
/* 1285 */       if (this.stroke.getDashArray() != null) {
/* 1286 */         return null;
/*      */       }
/* 1288 */       float f1 = this.stroke.getLineWidth() / 2.0F;
/* 1289 */       if ((f1 >= paramRectangle2D.getWidth()) || (f1 >= paramRectangle2D.getHeight())) {
/* 1290 */         return paramRectangle2D;
/*      */       }
/* 1292 */       float f2 = (float)paramRectangle2D.getX();
/* 1293 */       float f3 = (float)paramRectangle2D.getY();
/* 1294 */       float f4 = f2 + (float)paramRectangle2D.getWidth();
/* 1295 */       float f5 = f3 + (float)paramRectangle2D.getHeight();
/* 1296 */       GeneralPath localGeneralPath = new GeneralPath();
/* 1297 */       localGeneralPath.moveTo(f2, f3);
/* 1298 */       localGeneralPath.lineTo(f4, f3);
/* 1299 */       localGeneralPath.lineTo(f4, f5);
/* 1300 */       localGeneralPath.lineTo(f2, f5);
/* 1301 */       localGeneralPath.closePath();
/* 1302 */       f2 += f1;
/* 1303 */       f3 += f1;
/* 1304 */       f4 -= f1;
/* 1305 */       f5 -= f1;
/* 1306 */       localGeneralPath.moveTo(f2, f3);
/* 1307 */       localGeneralPath.lineTo(f2, f5);
/* 1308 */       localGeneralPath.lineTo(f4, f5);
/* 1309 */       localGeneralPath.lineTo(f4, f3);
/* 1310 */       localGeneralPath.closePath();
/* 1311 */       return localGeneralPath;
/*      */     }
/*      */ 
/*      */     protected java.awt.Shape makeStrokedEllipse(Ellipse2D paramEllipse2D)
/*      */     {
/* 1316 */       if (this.stroke.getDashArray() != null) {
/* 1317 */         return null;
/*      */       }
/* 1319 */       float f1 = this.stroke.getLineWidth() / 2.0F;
/* 1320 */       float f2 = (float)paramEllipse2D.getWidth();
/* 1321 */       float f3 = (float)paramEllipse2D.getHeight();
/* 1322 */       if ((f2 - 2.0F * f1 > f3 * 2.0F) || (f3 - 2.0F * f1 > f2 * 2.0F))
/*      */       {
/* 1328 */         return null;
/*      */       }
/* 1330 */       if ((f1 >= f2) || (f1 >= f3)) {
/* 1331 */         return paramEllipse2D;
/*      */       }
/* 1333 */       float f4 = (float)paramEllipse2D.getX();
/* 1334 */       float f5 = (float)paramEllipse2D.getY();
/* 1335 */       float f6 = f4 + f2 / 2.0F;
/* 1336 */       float f7 = f5 + f3 / 2.0F;
/* 1337 */       float f8 = f4 + f2;
/* 1338 */       float f9 = f5 + f3;
/* 1339 */       GeneralPath localGeneralPath = new GeneralPath();
/* 1340 */       localGeneralPath.moveTo(f6, f5);
/* 1341 */       cornerArc(localGeneralPath, f6, f5, f8, f5, f8, f7);
/* 1342 */       cornerArc(localGeneralPath, f8, f7, f8, f9, f6, f9);
/* 1343 */       cornerArc(localGeneralPath, f6, f9, f4, f9, f4, f7);
/* 1344 */       cornerArc(localGeneralPath, f4, f7, f4, f5, f6, f5);
/* 1345 */       localGeneralPath.closePath();
/* 1346 */       f4 += f1;
/* 1347 */       f5 += f1;
/* 1348 */       f8 -= f1;
/* 1349 */       f9 -= f1;
/* 1350 */       localGeneralPath.moveTo(f6, f5);
/* 1351 */       cornerArc(localGeneralPath, f6, f5, f4, f5, f4, f7);
/* 1352 */       cornerArc(localGeneralPath, f4, f7, f4, f9, f6, f9);
/* 1353 */       cornerArc(localGeneralPath, f6, f9, f8, f9, f8, f7);
/* 1354 */       cornerArc(localGeneralPath, f8, f7, f8, f5, f6, f5);
/* 1355 */       localGeneralPath.closePath();
/* 1356 */       return localGeneralPath;
/*      */     }
/*      */ 
/*      */     protected java.awt.Shape makeStrokedShape(java.awt.Shape paramShape)
/*      */     {
/* 1361 */       java.awt.Shape localShape = this.stroke.createStrokedShape(paramShape);
/* 1362 */       Area localArea = new Area(localShape);
/* 1363 */       localArea.intersect(new Area(paramShape));
/* 1364 */       return localArea;
/*      */     }
/*      */   }
/*      */ 
/*      */   static abstract class FilterStroke
/*      */     implements Stroke
/*      */   {
/*      */     protected java.awt.BasicStroke stroke;
/*      */     static final double CtrlVal = 0.5522847498307933D;
/*      */ 
/*      */     FilterStroke(java.awt.BasicStroke paramBasicStroke)
/*      */     {
/* 1227 */       this.stroke = paramBasicStroke;
/*      */     }
/*      */     protected abstract java.awt.Shape makeStrokedRect(Rectangle2D paramRectangle2D);
/*      */ 
/*      */     protected abstract java.awt.Shape makeStrokedShape(java.awt.Shape paramShape);
/*      */ 
/*      */     public java.awt.Shape createStrokedShape(java.awt.Shape paramShape) {
/* 1234 */       if ((paramShape instanceof Rectangle2D)) {
/* 1235 */         java.awt.Shape localShape = makeStrokedRect((Rectangle2D)paramShape);
/* 1236 */         if (localShape != null) {
/* 1237 */           return localShape;
/*      */         }
/*      */       }
/* 1240 */       return makeStrokedShape(paramShape);
/*      */     }
/*      */ 
/*      */     static Point2D cornerArc(GeneralPath paramGeneralPath, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */     {
/* 1251 */       return cornerArc(paramGeneralPath, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, 0.5F);
/*      */     }
/*      */ 
/*      */     static Point2D cornerArc(GeneralPath paramGeneralPath, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7)
/*      */     {
/* 1259 */       float f1 = (float)(paramFloat1 + 0.5522847498307933D * (paramFloat3 - paramFloat1));
/* 1260 */       float f2 = (float)(paramFloat2 + 0.5522847498307933D * (paramFloat4 - paramFloat2));
/* 1261 */       float f3 = (float)(paramFloat5 + 0.5522847498307933D * (paramFloat3 - paramFloat5));
/* 1262 */       float f4 = (float)(paramFloat6 + 0.5522847498307933D * (paramFloat4 - paramFloat6));
/* 1263 */       paramGeneralPath.curveTo(f1, f2, f3, f4, paramFloat5, paramFloat6);
/*      */ 
/* 1265 */       return new Point2D.Float(eval(paramFloat1, f1, f3, paramFloat5, paramFloat7), eval(paramFloat2, f2, f4, paramFloat6, paramFloat7));
/*      */     }
/*      */ 
/*      */     static float eval(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*      */     {
/* 1270 */       paramFloat1 += (paramFloat2 - paramFloat1) * paramFloat5;
/* 1271 */       paramFloat2 += (paramFloat3 - paramFloat2) * paramFloat5;
/* 1272 */       paramFloat3 += (paramFloat4 - paramFloat3) * paramFloat5;
/* 1273 */       paramFloat1 += (paramFloat2 - paramFloat1) * paramFloat5;
/* 1274 */       paramFloat2 += (paramFloat3 - paramFloat2) * paramFloat5;
/* 1275 */       return paramFloat1 + (paramFloat2 - paramFloat1) * paramFloat5;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class AdaptorPathIterator
/*      */     implements java.awt.geom.PathIterator
/*      */   {
/* 1187 */     private static int[] NUM_COORDS = { 2, 2, 4, 6, 0 };
/*      */     com.sun.javafx.geom.PathIterator priterator;
/*      */     float[] tmpcoords;
/*      */ 
/*      */     public void setIterator(com.sun.javafx.geom.PathIterator paramPathIterator)
/*      */     {
/* 1192 */       this.priterator = paramPathIterator;
/*      */     }
/*      */ 
/*      */     public int currentSegment(float[] paramArrayOfFloat) {
/* 1196 */       return this.priterator.currentSegment(paramArrayOfFloat);
/*      */     }
/*      */ 
/*      */     public int currentSegment(double[] paramArrayOfDouble) {
/* 1200 */       if (this.tmpcoords == null) {
/* 1201 */         this.tmpcoords = new float[6];
/*      */       }
/* 1203 */       int i = this.priterator.currentSegment(this.tmpcoords);
/* 1204 */       for (int j = 0; j < NUM_COORDS[i]; j++) {
/* 1205 */         paramArrayOfDouble[j] = this.tmpcoords[j];
/*      */       }
/* 1207 */       return i;
/*      */     }
/*      */ 
/*      */     public int getWindingRule() {
/* 1211 */       return this.priterator.getWindingRule();
/*      */     }
/*      */ 
/*      */     public boolean isDone() {
/* 1215 */       return this.priterator.isDone();
/*      */     }
/*      */ 
/*      */     public void next() {
/* 1219 */       this.priterator.next();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class AdaptorShape
/*      */     implements java.awt.Shape
/*      */   {
/*      */     private com.sun.javafx.geom.Shape prshape;
/* 1161 */     private static J2DPrismGraphics.AdaptorPathIterator tmpAdaptor = new J2DPrismGraphics.AdaptorPathIterator(null);
/*      */ 
/*      */     public void setShape(com.sun.javafx.geom.Shape paramShape)
/*      */     {
/* 1122 */       this.prshape = paramShape;
/*      */     }
/*      */ 
/*      */     public boolean contains(double paramDouble1, double paramDouble2) {
/* 1126 */       return this.prshape.contains((float)paramDouble1, (float)paramDouble2);
/*      */     }
/*      */ 
/*      */     public boolean contains(Point2D paramPoint2D) {
/* 1130 */       return contains(paramPoint2D.getX(), paramPoint2D.getY());
/*      */     }
/*      */ 
/*      */     public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1134 */       return this.prshape.contains((float)paramDouble1, (float)paramDouble2, (float)paramDouble3, (float)paramDouble4);
/*      */     }
/*      */ 
/*      */     public boolean contains(Rectangle2D paramRectangle2D) {
/* 1138 */       return contains(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
/*      */     }
/*      */ 
/*      */     public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1142 */       return this.prshape.intersects((float)paramDouble1, (float)paramDouble2, (float)paramDouble3, (float)paramDouble4);
/*      */     }
/*      */ 
/*      */     public boolean intersects(Rectangle2D paramRectangle2D) {
/* 1146 */       return intersects(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
/*      */     }
/*      */ 
/*      */     public java.awt.Rectangle getBounds() {
/* 1150 */       return getBounds2D().getBounds();
/*      */     }
/*      */ 
/*      */     public Rectangle2D getBounds2D() {
/* 1154 */       RectBounds localRectBounds = this.prshape.getBounds();
/* 1155 */       Rectangle2D.Float localFloat = new Rectangle2D.Float();
/*      */ 
/* 1157 */       localFloat.setFrameFromDiagonal(localRectBounds.getMinX(), localRectBounds.getMinY(), localRectBounds.getMaxX(), localRectBounds.getMaxY());
/* 1158 */       return localFloat;
/*      */     }
/*      */ 
/*      */     private static java.awt.geom.PathIterator tmpAdaptor(com.sun.javafx.geom.PathIterator paramPathIterator)
/*      */     {
/* 1164 */       tmpAdaptor.setIterator(paramPathIterator);
/* 1165 */       return tmpAdaptor;
/*      */     }
/*      */ 
/*      */     public java.awt.geom.PathIterator getPathIterator(AffineTransform paramAffineTransform)
/*      */     {
/* 1171 */       BaseTransform localBaseTransform = paramAffineTransform == null ? null : J2DPrismGraphics.toPrTransform(paramAffineTransform);
/* 1172 */       return tmpAdaptor(this.prshape.getPathIterator(localBaseTransform));
/*      */     }
/*      */ 
/*      */     public java.awt.geom.PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble)
/*      */     {
/* 1179 */       BaseTransform localBaseTransform = paramAffineTransform == null ? null : J2DPrismGraphics.toPrTransform(paramAffineTransform);
/* 1180 */       return tmpAdaptor(this.prshape.getPathIterator(localBaseTransform, (float)paramDouble));
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.j2d.J2DPrismGraphics
 * JD-Core Version:    0.6.2
 */