/*     */ package com.sun.webpane.perf;
/*     */ 
/*     */ import com.sun.webpane.platform.graphics.Ref;
/*     */ import com.sun.webpane.platform.graphics.RenderTheme;
/*     */ import com.sun.webpane.platform.graphics.ScrollBarTheme;
/*     */ import com.sun.webpane.platform.graphics.WCFont;
/*     */ import com.sun.webpane.platform.graphics.WCGradient;
/*     */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*     */ import com.sun.webpane.platform.graphics.WCIcon;
/*     */ import com.sun.webpane.platform.graphics.WCImage;
/*     */ import com.sun.webpane.platform.graphics.WCPath;
/*     */ import com.sun.webpane.platform.graphics.WCPoint;
/*     */ import com.sun.webpane.platform.graphics.WCRectangle;
/*     */ import com.sun.webpane.platform.graphics.WCTransform;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class WCGraphicsPerfLogger extends WCGraphicsContext
/*     */ {
/*  23 */   private static Logger log = Logger.getLogger(WCGraphicsPerfLogger.class.getName());
/*     */ 
/*  25 */   private static PerfLogger logger = PerfLogger.getLogger(log);
/*     */   private WCGraphicsContext gc;
/*     */ 
/*     */   public WCGraphicsPerfLogger(WCGraphicsContext paramWCGraphicsContext)
/*     */   {
/*  30 */     this.gc = paramWCGraphicsContext;
/*     */   }
/*     */ 
/*     */   public static synchronized boolean isEnabled() {
/*  34 */     return logger.isEnabled();
/*     */   }
/*     */ 
/*     */   public static void log() {
/*  38 */     logger.log();
/*     */   }
/*     */ 
/*     */   public static void reset() {
/*  42 */     logger.reset();
/*     */   }
/*     */ 
/*     */   public Object getPlatformGraphics()
/*     */   {
/*  47 */     return this.gc.getPlatformGraphics();
/*     */   }
/*     */ 
/*     */   public void drawString(WCFont paramWCFont, int[] paramArrayOfInt, float[] paramArrayOfFloat, float paramFloat1, float paramFloat2)
/*     */   {
/*  55 */     logger.resumeCount("DRAWSTRING_GV");
/*  56 */     this.gc.drawString(paramWCFont, paramArrayOfInt, paramArrayOfFloat, paramFloat1, paramFloat2);
/*  57 */     logger.suspendCount("DRAWSTRING_GV");
/*     */   }
/*     */ 
/*     */   public void strokeRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*     */   {
/*  62 */     logger.resumeCount("STROKERECT_FFFFF");
/*  63 */     this.gc.strokeRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
/*  64 */     logger.suspendCount("STROKERECT_FFFFF");
/*     */   }
/*     */ 
/*     */   public void fillRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Integer paramInteger)
/*     */   {
/*  69 */     logger.resumeCount("FILLRECT_FFFFI");
/*  70 */     this.gc.fillRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInteger);
/*  71 */     logger.suspendCount("FILLRECT_FFFFI");
/*     */   }
/*     */ 
/*     */   public void fillRoundedRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, int paramInt)
/*     */   {
/*  78 */     logger.resumeCount("FILL_ROUNDED_RECT");
/*  79 */     this.gc.fillRoundedRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramInt);
/*     */ 
/*  81 */     logger.suspendCount("FILL_ROUNDED_RECT");
/*     */   }
/*     */ 
/*     */   public void clearRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  86 */     logger.resumeCount("CLEARRECT");
/*  87 */     this.gc.clearRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  88 */     logger.suspendCount("CLEARRECT");
/*     */   }
/*     */ 
/*     */   public void setFillColor(int paramInt)
/*     */   {
/*  93 */     logger.resumeCount("SETFILLCOLOR");
/*  94 */     this.gc.setFillColor(paramInt);
/*  95 */     logger.suspendCount("SETFILLCOLOR");
/*     */   }
/*     */ 
/*     */   public void setFillGradient(WCGradient paramWCGradient)
/*     */   {
/* 100 */     logger.resumeCount("SET_FILL_GRADIENT");
/* 101 */     this.gc.setFillGradient(paramWCGradient);
/* 102 */     logger.suspendCount("SET_FILL_GRADIENT");
/*     */   }
/*     */ 
/*     */   public void setTextMode(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*     */   {
/* 107 */     logger.resumeCount("SET_TEXT_MODE");
/* 108 */     this.gc.setTextMode(paramBoolean1, paramBoolean2, paramBoolean3);
/* 109 */     logger.suspendCount("SET_TEXT_MODE");
/*     */   }
/*     */ 
/*     */   public void setFontSmoothingType(int paramInt)
/*     */   {
/* 114 */     logger.resumeCount("SET_FONT_SMOOTHING_TYPE");
/* 115 */     this.gc.setFontSmoothingType(paramInt);
/* 116 */     logger.suspendCount("SET_FONT_SMOOTHING_TYPE");
/*     */   }
/*     */ 
/*     */   public void setStrokeStyle(int paramInt)
/*     */   {
/* 121 */     logger.resumeCount("SETSTROKESTYLE");
/* 122 */     this.gc.setStrokeStyle(paramInt);
/* 123 */     logger.suspendCount("SETSTROKESTYLE");
/*     */   }
/*     */ 
/*     */   public void setStrokeColor(int paramInt)
/*     */   {
/* 128 */     logger.resumeCount("SETSTROKECOLOR");
/* 129 */     this.gc.setStrokeColor(paramInt);
/* 130 */     logger.suspendCount("SETSTROKECOLOR");
/*     */   }
/*     */ 
/*     */   public void setStrokeWidth(float paramFloat)
/*     */   {
/* 135 */     logger.resumeCount("SETSTROKEWIDTH");
/* 136 */     this.gc.setStrokeWidth(paramFloat);
/* 137 */     logger.suspendCount("SETSTROKEWIDTH");
/*     */   }
/*     */ 
/*     */   public void setStrokeGradient(WCGradient paramWCGradient)
/*     */   {
/* 142 */     logger.resumeCount("SET_STROKE_GRADIENT");
/* 143 */     this.gc.setStrokeGradient(paramWCGradient);
/* 144 */     logger.suspendCount("SET_STROKE_GRADIENT");
/*     */   }
/*     */ 
/*     */   public void setLineDash(float paramFloat, float[] paramArrayOfFloat)
/*     */   {
/* 149 */     logger.resumeCount("SET_LINE_DASH");
/* 150 */     this.gc.setLineDash(paramFloat, paramArrayOfFloat);
/* 151 */     logger.suspendCount("SET_LINE_DASH");
/*     */   }
/*     */ 
/*     */   public void setLineCap(int paramInt)
/*     */   {
/* 156 */     logger.resumeCount("SET_LINE_CAP");
/* 157 */     this.gc.setLineCap(paramInt);
/* 158 */     logger.suspendCount("SET_LINE_CAP");
/*     */   }
/*     */ 
/*     */   public void setLineJoin(int paramInt)
/*     */   {
/* 163 */     logger.resumeCount("SET_LINE_JOIN");
/* 164 */     this.gc.setLineJoin(paramInt);
/* 165 */     logger.suspendCount("SET_LINE_JOIN");
/*     */   }
/*     */ 
/*     */   public void setMiterLimit(float paramFloat)
/*     */   {
/* 170 */     logger.resumeCount("SET_MITER_LIMIT");
/* 171 */     this.gc.setMiterLimit(paramFloat);
/* 172 */     logger.suspendCount("SET_MITER_LIMIT");
/*     */   }
/*     */ 
/*     */   public void setShadow(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt)
/*     */   {
/* 177 */     logger.resumeCount("SETSHADOW");
/* 178 */     this.gc.setShadow(paramFloat1, paramFloat2, paramFloat3, paramInt);
/* 179 */     logger.suspendCount("SETSHADOW");
/*     */   }
/*     */ 
/*     */   public void drawPolygon(float[] paramArrayOfFloat, boolean paramBoolean)
/*     */   {
/* 184 */     logger.resumeCount("DRAWPOLYGON");
/* 185 */     this.gc.drawPolygon(paramArrayOfFloat, paramBoolean);
/* 186 */     logger.suspendCount("DRAWPOLYGON");
/*     */   }
/*     */ 
/*     */   public void drawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 191 */     logger.resumeCount("DRAWLINE");
/* 192 */     this.gc.drawLine(paramInt1, paramInt2, paramInt3, paramInt4);
/* 193 */     logger.suspendCount("DRAWLINE");
/*     */   }
/*     */ 
/*     */   public void drawImage(WCImage paramWCImage, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 200 */     logger.resumeCount("DRAWIMAGE");
/* 201 */     this.gc.drawImage(paramWCImage, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/* 202 */     logger.suspendCount("DRAWIMAGE");
/*     */   }
/*     */ 
/*     */   public void drawIcon(WCIcon paramWCIcon, int paramInt1, int paramInt2)
/*     */   {
/* 207 */     logger.resumeCount("DRAWICON");
/* 208 */     this.gc.drawIcon(paramWCIcon, paramInt1, paramInt2);
/* 209 */     logger.suspendCount("DRAWICON");
/*     */   }
/*     */ 
/*     */   public void drawPattern(WCImage paramWCImage, WCRectangle paramWCRectangle1, WCTransform paramWCTransform, WCPoint paramWCPoint, WCRectangle paramWCRectangle2)
/*     */   {
/* 216 */     logger.resumeCount("DRAWPATTERN");
/* 217 */     this.gc.drawPattern(paramWCImage, paramWCRectangle1, paramWCTransform, paramWCPoint, paramWCRectangle2);
/* 218 */     logger.suspendCount("DRAWPATTERN");
/*     */   }
/*     */ 
/*     */   public void translate(float paramFloat1, float paramFloat2)
/*     */   {
/* 223 */     logger.resumeCount("TRANSLATE");
/* 224 */     this.gc.translate(paramFloat1, paramFloat2);
/* 225 */     logger.suspendCount("TRANSLATE");
/*     */   }
/*     */ 
/*     */   public void scale(float paramFloat1, float paramFloat2)
/*     */   {
/* 230 */     logger.resumeCount("SCALE");
/* 231 */     this.gc.scale(paramFloat1, paramFloat2);
/* 232 */     logger.suspendCount("SCALE");
/*     */   }
/*     */ 
/*     */   public void rotate(float paramFloat)
/*     */   {
/* 237 */     logger.resumeCount("ROTATE");
/* 238 */     this.gc.rotate(paramFloat);
/* 239 */     logger.suspendCount("ROTATE");
/*     */   }
/*     */ 
/*     */   public void saveState()
/*     */   {
/* 244 */     logger.resumeCount("SAVESTATE");
/* 245 */     this.gc.saveState();
/* 246 */     logger.suspendCount("SAVESTATE");
/*     */   }
/*     */ 
/*     */   public void restoreState()
/*     */   {
/* 251 */     logger.resumeCount("RESTORESTATE");
/* 252 */     this.gc.restoreState();
/* 253 */     logger.suspendCount("RESTORESTATE");
/*     */   }
/*     */ 
/*     */   public void setClip(WCPath paramWCPath, boolean paramBoolean)
/*     */   {
/* 258 */     logger.resumeCount("CLIP_PATH");
/* 259 */     this.gc.setClip(paramWCPath, paramBoolean);
/* 260 */     logger.suspendCount("CLIP_PATH");
/*     */   }
/*     */ 
/*     */   public void setClip(WCRectangle paramWCRectangle)
/*     */   {
/* 265 */     logger.resumeCount("SETCLIP_R");
/* 266 */     this.gc.setClip(paramWCRectangle);
/* 267 */     logger.suspendCount("SETCLIP_R");
/*     */   }
/*     */ 
/*     */   public void setClip(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 272 */     logger.resumeCount("SETCLIP_IIII");
/* 273 */     this.gc.setClip(paramInt1, paramInt2, paramInt3, paramInt4);
/* 274 */     logger.suspendCount("SETCLIP_IIII");
/*     */   }
/*     */ 
/*     */   public WCRectangle getClip()
/*     */   {
/* 279 */     logger.resumeCount("SETCLIP_IIII");
/* 280 */     WCRectangle localWCRectangle = this.gc.getClip();
/* 281 */     logger.suspendCount("SETCLIP_IIII");
/* 282 */     return localWCRectangle;
/*     */   }
/*     */ 
/*     */   public void drawRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 287 */     logger.resumeCount("DRAWRECT");
/* 288 */     this.gc.drawRect(paramInt1, paramInt2, paramInt3, paramInt4);
/* 289 */     logger.suspendCount("DRAWRECT");
/*     */   }
/*     */ 
/*     */   public void setComposite(int paramInt)
/*     */   {
/* 294 */     logger.resumeCount("SETCOMPOSITE");
/* 295 */     this.gc.setComposite(paramInt);
/* 296 */     logger.suspendCount("SETCOMPOSITE");
/*     */   }
/*     */ 
/*     */   public void strokeArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 302 */     logger.resumeCount("STROKEARC");
/* 303 */     this.gc.strokeArc(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/* 304 */     logger.suspendCount("STROKEARC");
/*     */   }
/*     */ 
/*     */   public void drawEllipse(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 309 */     logger.resumeCount("DRAWELLIPSE");
/* 310 */     this.gc.drawEllipse(paramInt1, paramInt2, paramInt3, paramInt4);
/* 311 */     logger.suspendCount("DRAWELLIPSE");
/*     */   }
/*     */ 
/*     */   public void drawFocusRing(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/* 316 */     logger.resumeCount("DRAWFOCUSRING");
/* 317 */     this.gc.drawFocusRing(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 318 */     logger.suspendCount("DRAWFOCUSRING");
/*     */   }
/*     */ 
/*     */   public void setAlpha(float paramFloat)
/*     */   {
/* 323 */     logger.resumeCount("SETALPHA");
/* 324 */     this.gc.setAlpha(paramFloat);
/* 325 */     logger.suspendCount("SETALPHA");
/*     */   }
/*     */ 
/*     */   public float getAlpha()
/*     */   {
/* 330 */     logger.resumeCount("GETALPHA");
/* 331 */     float f = this.gc.getAlpha();
/* 332 */     logger.suspendCount("GETALPHA");
/* 333 */     return f;
/*     */   }
/*     */ 
/*     */   public void beginTransparencyLayer(float paramFloat)
/*     */   {
/* 338 */     logger.resumeCount("BEGINTRANSPARENCYLAYER");
/* 339 */     this.gc.beginTransparencyLayer(paramFloat);
/* 340 */     logger.suspendCount("BEGINTRANSPARENCYLAYER");
/*     */   }
/*     */ 
/*     */   public void endTransparencyLayer()
/*     */   {
/* 345 */     logger.resumeCount("ENDTRANSPARENCYLAYER");
/* 346 */     this.gc.endTransparencyLayer();
/* 347 */     logger.suspendCount("ENDTRANSPARENCYLAYER");
/*     */   }
/*     */ 
/*     */   public void drawString(WCFont paramWCFont, String paramString, boolean paramBoolean, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 356 */     logger.resumeCount("DRAWSTRING");
/* 357 */     this.gc.drawString(paramWCFont, paramString, paramBoolean, paramInt1, paramInt2, paramFloat3, paramFloat4, paramFloat1, paramFloat2);
/* 358 */     logger.suspendCount("DRAWSTRING");
/*     */   }
/*     */ 
/*     */   public void strokePath(WCPath paramWCPath)
/*     */   {
/* 363 */     logger.resumeCount("STROKE_PATH");
/* 364 */     this.gc.strokePath(paramWCPath);
/* 365 */     logger.suspendCount("STROKE_PATH");
/*     */   }
/*     */ 
/*     */   public void fillPath(WCPath paramWCPath)
/*     */   {
/* 370 */     logger.resumeCount("FILL_PATH");
/* 371 */     this.gc.fillPath(paramWCPath);
/* 372 */     logger.suspendCount("FILL_PATH");
/*     */   }
/*     */ 
/*     */   public WCImage getImage()
/*     */   {
/* 377 */     logger.resumeCount("GETIMAGE");
/* 378 */     WCImage localWCImage = this.gc.getImage();
/* 379 */     logger.suspendCount("GETIMAGE");
/* 380 */     return localWCImage;
/*     */   }
/*     */ 
/*     */   public void drawWidget(RenderTheme paramRenderTheme, Ref paramRef, int paramInt1, int paramInt2)
/*     */   {
/* 385 */     logger.resumeCount("DRAWWIDGET");
/* 386 */     this.gc.drawWidget(paramRenderTheme, paramRef, paramInt1, paramInt2);
/* 387 */     logger.suspendCount("DRAWWIDGET");
/*     */   }
/*     */ 
/*     */   public void drawScrollbar(ScrollBarTheme paramScrollBarTheme, Ref paramRef, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 394 */     logger.resumeCount("DRAWSCROLLBAR");
/* 395 */     this.gc.drawScrollbar(paramScrollBarTheme, paramRef, paramInt1, paramInt2, paramInt3, paramInt4);
/* 396 */     logger.suspendCount("DRAWSCROLLBAR");
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 401 */     logger.resumeCount("DISPOSE");
/* 402 */     this.gc.dispose();
/* 403 */     logger.suspendCount("DISPOSE");
/*     */   }
/*     */ 
/*     */   public void setTransform(WCTransform paramWCTransform)
/*     */   {
/* 408 */     logger.resumeCount("SETTRANSFORM");
/* 409 */     this.gc.setTransform(paramWCTransform);
/* 410 */     logger.suspendCount("SETTRANSFORM");
/*     */   }
/*     */ 
/*     */   public WCTransform getTransform()
/*     */   {
/* 415 */     logger.resumeCount("GETTRANSFORM");
/* 416 */     WCTransform localWCTransform = this.gc.getTransform();
/* 417 */     logger.suspendCount("GETTRANSFORM");
/* 418 */     return localWCTransform;
/*     */   }
/*     */ 
/*     */   public void concatTransform(WCTransform paramWCTransform)
/*     */   {
/* 423 */     logger.resumeCount("CONCATTRANSFORM");
/* 424 */     this.gc.concatTransform(paramWCTransform);
/* 425 */     logger.suspendCount("CONCATTRANSFORM");
/*     */   }
/*     */ 
/*     */   public void drawBitmapImage(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 430 */     logger.resumeCount("DRAWBITMAPIMAGE");
/* 431 */     this.gc.drawBitmapImage(paramByteBuffer, paramInt1, paramInt2, paramInt3, paramInt4);
/* 432 */     logger.suspendCount("DRAWBITMAPIMAGE");
/*     */   }
/*     */ 
/*     */   public WCGradient createLinearGradient(WCPoint paramWCPoint1, WCPoint paramWCPoint2)
/*     */   {
/* 437 */     logger.resumeCount("CREATE_LINEAR_GRADIENT");
/* 438 */     WCGradient localWCGradient = this.gc.createLinearGradient(paramWCPoint1, paramWCPoint2);
/* 439 */     logger.suspendCount("CREATE_LINEAR_GRADIENT");
/* 440 */     return localWCGradient;
/*     */   }
/*     */ 
/*     */   public WCGradient createRadialGradient(WCPoint paramWCPoint1, float paramFloat1, WCPoint paramWCPoint2, float paramFloat2)
/*     */   {
/* 445 */     logger.resumeCount("CREATE_RADIAL_GRADIENT");
/* 446 */     WCGradient localWCGradient = this.gc.createRadialGradient(paramWCPoint1, paramFloat1, paramWCPoint2, paramFloat2);
/* 447 */     logger.suspendCount("CREATE_RADIAL_GRADIENT");
/* 448 */     return localWCGradient;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.perf.WCGraphicsPerfLogger
 * JD-Core Version:    0.6.2
 */