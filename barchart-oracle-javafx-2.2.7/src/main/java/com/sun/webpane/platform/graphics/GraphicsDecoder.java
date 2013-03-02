/*     */ package com.sun.webpane.platform.graphics;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class GraphicsDecoder
/*     */ {
/*     */   public static final int FILLRECT_FFFFI = 0;
/*     */   public static final int SETFILLCOLOR = 1;
/*     */   public static final int SETSTROKESTYLE = 2;
/*     */   public static final int SETSTROKECOLOR = 3;
/*     */   public static final int SETSTROKEWIDTH = 4;
/*     */   public static final int DRAWPOLYGON = 6;
/*     */   public static final int DRAWLINE = 7;
/*     */   public static final int DRAWIMAGE = 8;
/*     */   public static final int DRAWICON = 9;
/*     */   public static final int DRAWPATTERN = 10;
/*     */   public static final int TRANSLATE = 11;
/*     */   public static final int SAVESTATE = 12;
/*     */   public static final int RESTORESTATE = 13;
/*     */   public static final int CLIP_PATH = 14;
/*     */   public static final int SETCLIP_IIII = 15;
/*     */   public static final int DRAWRECT = 16;
/*     */   public static final int SETCOMPOSITE = 17;
/*     */   public static final int STROKEARC = 18;
/*     */   public static final int DRAWELLIPSE = 19;
/*     */   public static final int DRAWFOCUSRING = 20;
/*     */   public static final int SETALPHA = 21;
/*     */   public static final int BEGINTRANSPARENCYLAYER = 22;
/*     */   public static final int ENDTRANSPARENCYLAYER = 23;
/*     */   public static final int STROKE_PATH = 24;
/*     */   public static final int FILL_PATH = 25;
/*     */   public static final int GETIMAGE = 26;
/*     */   public static final int SCALE = 27;
/*     */   public static final int SETSHADOW = 28;
/*     */   public static final int DRAWSTRING = 29;
/*     */   public static final int DRAWSTRING_FAST = 31;
/*     */   public static final int DRAWWIDGET = 33;
/*     */   public static final int DRAWSCROLLBAR = 34;
/*     */   public static final int CLEARRECT_FFFF = 36;
/*     */   public static final int STROKERECT_FFFFF = 37;
/*     */   public static final int RENDERMEDIAPLAYER = 38;
/*     */   public static final int CONCATTRANSFORM_FFFFFF = 39;
/*     */   public static final int COPYREGION = 40;
/*     */   public static final int DECODERQ = 41;
/*     */   public static final int SET_TRANSFORM = 42;
/*     */   public static final int ROTATE = 43;
/*     */   public static final int RENDERMEDIACONTROL = 44;
/*     */   public static final int RENDERMEDIA_TIMETRACK = 45;
/*     */   public static final int RENDERMEDIA_VOLUMETRACK = 46;
/*     */   public static final int FILLRECT_FFFF = 47;
/*     */   public static final int FILL_ROUNDED_RECT = 48;
/*     */   public static final int SET_FILL_GRADIENT = 49;
/*     */   public static final int SET_STROKE_GRADIENT = 50;
/*     */   public static final int SET_LINE_DASH = 51;
/*     */   public static final int SET_LINE_CAP = 52;
/*     */   public static final int SET_LINE_JOIN = 53;
/*     */   public static final int SET_MITER_LIMIT = 54;
/*     */   public static final int SET_TEXT_MODE = 55;
/*  64 */   private static final Logger log = Logger.getLogger(GraphicsDecoder.class.getName());
/*     */ 
/*     */   public static void decode(WCGraphicsManager paramWCGraphicsManager, WCGraphicsContext paramWCGraphicsContext, BufferData paramBufferData)
/*     */   {
/*  68 */     if (paramWCGraphicsContext == null) {
/*  69 */       return;
/*     */     }
/*  71 */     ByteBuffer localByteBuffer = paramBufferData.getBuffer();
/*  72 */     localByteBuffer.order(ByteOrder.nativeOrder());
/*  73 */     while (localByteBuffer.remaining() > 0) {
/*  74 */       int i = localByteBuffer.getInt();
/*     */       int j;
/*  75 */       switch (i) {
/*     */       case 47:
/*  77 */         paramWCGraphicsContext.fillRect(localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), null);
/*     */ 
/*  83 */         break;
/*     */       case 0:
/*  85 */         paramWCGraphicsContext.fillRect(localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), Integer.valueOf(localByteBuffer.getInt()));
/*     */ 
/*  91 */         break;
/*     */       case 48:
/*  93 */         paramWCGraphicsContext.fillRoundedRect(localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getInt());
/*     */ 
/* 101 */         break;
/*     */       case 36:
/* 103 */         paramWCGraphicsContext.clearRect(localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat());
/*     */ 
/* 108 */         break;
/*     */       case 37:
/* 110 */         paramWCGraphicsContext.strokeRect(localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat());
/*     */ 
/* 116 */         break;
/*     */       case 1:
/* 118 */         paramWCGraphicsContext.setFillColor(localByteBuffer.getInt());
/* 119 */         break;
/*     */       case 55:
/* 121 */         paramWCGraphicsContext.setTextMode(getBoolean(localByteBuffer), getBoolean(localByteBuffer), getBoolean(localByteBuffer));
/* 122 */         break;
/*     */       case 2:
/* 124 */         paramWCGraphicsContext.setStrokeStyle(localByteBuffer.getInt());
/* 125 */         break;
/*     */       case 3:
/* 127 */         paramWCGraphicsContext.setStrokeColor(localByteBuffer.getInt());
/* 128 */         break;
/*     */       case 4:
/* 130 */         paramWCGraphicsContext.setStrokeWidth(localByteBuffer.getFloat());
/* 131 */         break;
/*     */       case 49:
/* 133 */         paramWCGraphicsContext.setFillGradient(getGradient(paramWCGraphicsContext, localByteBuffer));
/* 134 */         break;
/*     */       case 50:
/* 136 */         paramWCGraphicsContext.setStrokeGradient(getGradient(paramWCGraphicsContext, localByteBuffer));
/* 137 */         break;
/*     */       case 51:
/* 139 */         paramWCGraphicsContext.setLineDash(localByteBuffer.getFloat(), getFloatArray(localByteBuffer));
/* 140 */         break;
/*     */       case 52:
/* 142 */         paramWCGraphicsContext.setLineCap(localByteBuffer.getInt());
/* 143 */         break;
/*     */       case 53:
/* 145 */         paramWCGraphicsContext.setLineJoin(localByteBuffer.getInt());
/* 146 */         break;
/*     */       case 54:
/* 148 */         paramWCGraphicsContext.setMiterLimit(localByteBuffer.getFloat());
/* 149 */         break;
/*     */       case 6:
/* 151 */         boolean bool = localByteBuffer.getInt() == -1;
/* 152 */         j = localByteBuffer.getInt();
/* 153 */         float[] arrayOfFloat1 = new float[j];
/* 154 */         localByteBuffer.asFloatBuffer().get(arrayOfFloat1);
/* 155 */         localByteBuffer.position(localByteBuffer.position() + j * 4);
/* 156 */         paramWCGraphicsContext.drawPolygon(arrayOfFloat1, bool);
/* 157 */         break;
/*     */       case 7:
/* 159 */         paramWCGraphicsContext.drawLine(localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 164 */         break;
/*     */       case 8:
/* 166 */         drawImage(paramWCGraphicsContext, paramWCGraphicsManager.getRef(localByteBuffer.getInt()), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat());
/*     */ 
/* 178 */         break;
/*     */       case 9:
/* 180 */         paramWCGraphicsContext.drawIcon((WCIcon)paramWCGraphicsManager.getRef(localByteBuffer.getInt()), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 183 */         break;
/*     */       case 10:
/* 185 */         drawPattern(paramWCGraphicsContext, paramWCGraphicsManager.getRef(localByteBuffer.getInt()), getRectangle(localByteBuffer), (WCTransform)paramWCGraphicsManager.getRef(localByteBuffer.getInt()), getPoint(localByteBuffer), getRectangle(localByteBuffer));
/*     */ 
/* 191 */         break;
/*     */       case 11:
/* 193 */         paramWCGraphicsContext.translate(localByteBuffer.getFloat(), localByteBuffer.getFloat());
/* 194 */         break;
/*     */       case 27:
/* 196 */         paramWCGraphicsContext.scale(localByteBuffer.getFloat(), localByteBuffer.getFloat());
/* 197 */         break;
/*     */       case 12:
/* 199 */         paramWCGraphicsContext.saveState();
/* 200 */         break;
/*     */       case 13:
/* 202 */         paramWCGraphicsContext.restoreState();
/* 203 */         break;
/*     */       case 14:
/* 205 */         paramWCGraphicsContext.setClip(getPath(paramWCGraphicsManager, localByteBuffer), localByteBuffer.getInt() > 0);
/*     */ 
/* 208 */         break;
/*     */       case 15:
/* 210 */         paramWCGraphicsContext.setClip(localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 215 */         break;
/*     */       case 16:
/* 217 */         paramWCGraphicsContext.drawRect(localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 222 */         break;
/*     */       case 17:
/* 224 */         paramWCGraphicsContext.setComposite(localByteBuffer.getInt());
/* 225 */         break;
/*     */       case 18:
/* 227 */         paramWCGraphicsContext.strokeArc(localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 234 */         break;
/*     */       case 19:
/* 236 */         paramWCGraphicsContext.drawEllipse(localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 241 */         break;
/*     */       case 20:
/* 243 */         paramWCGraphicsContext.drawFocusRing(localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 249 */         break;
/*     */       case 21:
/* 251 */         paramWCGraphicsContext.setAlpha(localByteBuffer.getFloat());
/* 252 */         break;
/*     */       case 22:
/* 254 */         paramWCGraphicsContext.beginTransparencyLayer(localByteBuffer.getFloat());
/* 255 */         break;
/*     */       case 23:
/* 257 */         paramWCGraphicsContext.endTransparencyLayer();
/* 258 */         break;
/*     */       case 24:
/* 260 */         paramWCGraphicsContext.strokePath(getPath(paramWCGraphicsManager, localByteBuffer));
/* 261 */         break;
/*     */       case 25:
/* 263 */         paramWCGraphicsContext.fillPath(getPath(paramWCGraphicsManager, localByteBuffer));
/* 264 */         break;
/*     */       case 28:
/* 266 */         paramWCGraphicsContext.setShadow(localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getInt());
/*     */ 
/* 271 */         break;
/*     */       case 29:
/* 273 */         paramWCGraphicsContext.drawString((WCFont)paramWCGraphicsManager.getRef(localByteBuffer.getInt()), paramBufferData.getString(localByteBuffer.getInt()), localByteBuffer.getInt() == -1, localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat());
/*     */ 
/* 281 */         break;
/*     */       case 31:
/* 283 */         paramWCGraphicsContext.drawString((WCFont)paramWCGraphicsManager.getRef(localByteBuffer.getInt()), paramBufferData.getIntArray(localByteBuffer.getInt()), paramBufferData.getFloatArray(localByteBuffer.getInt()), localByteBuffer.getFloat(), localByteBuffer.getFloat());
/*     */ 
/* 289 */         break;
/*     */       case 33:
/* 291 */         paramWCGraphicsContext.drawWidget((RenderTheme)paramWCGraphicsManager.getRef(localByteBuffer.getInt()), paramWCGraphicsManager.getRef(localByteBuffer.getInt()), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 293 */         break;
/*     */       case 34:
/* 295 */         paramWCGraphicsContext.drawScrollbar((ScrollBarTheme)paramWCGraphicsManager.getRef(localByteBuffer.getInt()), paramWCGraphicsManager.getRef(localByteBuffer.getInt()), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 298 */         break;
/*     */       case 38:
/* 300 */         WCMediaPlayer localWCMediaPlayer = (WCMediaPlayer)paramWCGraphicsManager.getRef(localByteBuffer.getInt());
/* 301 */         localWCMediaPlayer.render(paramWCGraphicsContext, localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 306 */         break;
/*     */       case 39:
/* 308 */         paramWCGraphicsContext.concatTransform(new WCTransform(localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat()));
/*     */ 
/* 311 */         break;
/*     */       case 42:
/* 313 */         paramWCGraphicsContext.setTransform(new WCTransform(localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat(), localByteBuffer.getFloat()));
/*     */ 
/* 316 */         break;
/*     */       case 40:
/* 318 */         WCPageBackBuffer localWCPageBackBuffer = (WCPageBackBuffer)paramWCGraphicsManager.getRef(localByteBuffer.getInt());
/* 319 */         localWCPageBackBuffer.copyArea(localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 321 */         break;
/*     */       case 41:
/* 323 */         WCRenderQueue localWCRenderQueue = (WCRenderQueue)paramWCGraphicsManager.getRef(localByteBuffer.getInt());
/* 324 */         localWCRenderQueue.decode();
/* 325 */         break;
/*     */       case 43:
/* 327 */         paramWCGraphicsContext.rotate(localByteBuffer.getFloat());
/* 328 */         break;
/*     */       case 44:
/* 330 */         RenderMediaControls.paintControl(paramWCGraphicsContext, localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 336 */         break;
/*     */       case 45:
/* 338 */         j = localByteBuffer.getInt();
/* 339 */         float[] arrayOfFloat2 = new float[j * 2];
/* 340 */         localByteBuffer.asFloatBuffer().get(arrayOfFloat2);
/* 341 */         localByteBuffer.position(localByteBuffer.position() + j * 4 * 2);
/* 342 */         RenderMediaControls.paintTimeSliderTrack(paramWCGraphicsContext, localByteBuffer.getFloat(), localByteBuffer.getFloat(), arrayOfFloat2, localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 350 */         break;
/*     */       case 46:
/* 352 */         RenderMediaControls.paintVolumeTrack(paramWCGraphicsContext, localByteBuffer.getFloat(), localByteBuffer.getInt() != 0, localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt(), localByteBuffer.getInt());
/*     */ 
/* 359 */         break;
/*     */       case 5:
/*     */       case 26:
/*     */       case 30:
/*     */       case 32:
/*     */       case 35:
/*     */       default:
/* 361 */         log.fine("ERROR. Unknown primitive found");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static WCImage getImage(Object paramObject)
/*     */   {
/* 369 */     WCImage localWCImage = null;
/* 370 */     if ((paramObject instanceof WCImage))
/*     */     {
/* 373 */       localWCImage = (WCImage)paramObject;
/* 374 */     } else if ((paramObject instanceof WCImageFrame))
/*     */     {
/* 377 */       localWCImage = ((WCImageFrame)paramObject).getFrame();
/*     */     }
/* 379 */     return localWCImage;
/*     */   }
/*     */ 
/*     */   private static void drawPattern(WCGraphicsContext paramWCGraphicsContext, Object paramObject, WCRectangle paramWCRectangle1, WCTransform paramWCTransform, WCPoint paramWCPoint, WCRectangle paramWCRectangle2)
/*     */   {
/* 390 */     WCImage localWCImage = getImage(paramObject);
/* 391 */     if (localWCImage != null)
/*     */     {
/*     */       try
/*     */       {
/* 398 */         paramWCGraphicsContext.drawPattern(localWCImage, paramWCRectangle1, paramWCTransform, paramWCPoint, paramWCRectangle2);
/*     */       }
/*     */       catch (OutOfMemoryError localOutOfMemoryError)
/*     */       {
/* 405 */         localOutOfMemoryError.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void drawImage(WCGraphicsContext paramWCGraphicsContext, Object paramObject, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 416 */     WCImage localWCImage = getImage(paramObject);
/* 417 */     if (localWCImage != null)
/*     */     {
/*     */       try
/*     */       {
/* 424 */         paramWCGraphicsContext.drawImage(localWCImage, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*     */       }
/*     */       catch (OutOfMemoryError localOutOfMemoryError)
/*     */       {
/* 429 */         localOutOfMemoryError.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static boolean getBoolean(ByteBuffer paramByteBuffer) {
/* 435 */     return 0 != paramByteBuffer.getInt();
/*     */   }
/*     */ 
/*     */   private static float[] getFloatArray(ByteBuffer paramByteBuffer) {
/* 439 */     float[] arrayOfFloat = new float[paramByteBuffer.getInt()];
/* 440 */     for (int i = 0; i < arrayOfFloat.length; i++) {
/* 441 */       arrayOfFloat[i] = paramByteBuffer.getFloat();
/*     */     }
/* 443 */     return arrayOfFloat;
/*     */   }
/*     */ 
/*     */   private static WCPath getPath(WCGraphicsManager paramWCGraphicsManager, ByteBuffer paramByteBuffer) {
/* 447 */     WCPath localWCPath = (WCPath)paramWCGraphicsManager.getRef(paramByteBuffer.getInt());
/* 448 */     localWCPath.setWindingRule(paramByteBuffer.getInt());
/* 449 */     return localWCPath;
/*     */   }
/*     */ 
/*     */   private static WCPoint getPoint(ByteBuffer paramByteBuffer) {
/* 453 */     return new WCPoint(paramByteBuffer.getFloat(), paramByteBuffer.getFloat());
/*     */   }
/*     */ 
/*     */   private static WCRectangle getRectangle(ByteBuffer paramByteBuffer)
/*     */   {
/* 458 */     return new WCRectangle(paramByteBuffer.getFloat(), paramByteBuffer.getFloat(), paramByteBuffer.getFloat(), paramByteBuffer.getFloat());
/*     */   }
/*     */ 
/*     */   private static WCGradient getGradient(WCGraphicsContext paramWCGraphicsContext, ByteBuffer paramByteBuffer)
/*     */   {
/* 465 */     WCPoint localWCPoint1 = getPoint(paramByteBuffer);
/* 466 */     WCPoint localWCPoint2 = getPoint(paramByteBuffer);
/* 467 */     WCGradient localWCGradient = getBoolean(paramByteBuffer) ? paramWCGraphicsContext.createRadialGradient(localWCPoint1, paramByteBuffer.getFloat(), localWCPoint2, paramByteBuffer.getFloat()) : paramWCGraphicsContext.createLinearGradient(localWCPoint1, localWCPoint2);
/*     */ 
/* 471 */     boolean bool = getBoolean(paramByteBuffer);
/* 472 */     int i = paramByteBuffer.getInt();
/* 473 */     if (localWCGradient != null) {
/* 474 */       localWCGradient.setProportional(bool);
/* 475 */       localWCGradient.setSpreadMethod(i);
/*     */     }
/* 477 */     int j = paramByteBuffer.getInt();
/* 478 */     for (int k = 0; k < j; k++) {
/* 479 */       int m = paramByteBuffer.getInt();
/* 480 */       float f = paramByteBuffer.getFloat();
/* 481 */       if (localWCGradient != null) {
/* 482 */         localWCGradient.addStop(m, f);
/*     */       }
/*     */     }
/* 485 */     return localWCGradient;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.GraphicsDecoder
 * JD-Core Version:    0.6.2
 */