/*     */ package com.sun.webpane.sg.prism;
/*     */ 
/*     */ import com.sun.media.jfxmedia.MediaManager;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.webpane.perf.WCFontPerfLogger;
/*     */ import com.sun.webpane.perf.WCGraphicsPerfLogger;
/*     */ import com.sun.webpane.platform.graphics.WCFont;
/*     */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*     */ import com.sun.webpane.platform.graphics.WCGraphicsManager;
/*     */ import com.sun.webpane.platform.graphics.WCIcon;
/*     */ import com.sun.webpane.platform.graphics.WCImage;
/*     */ import com.sun.webpane.platform.graphics.WCImageFrame;
/*     */ import com.sun.webpane.platform.graphics.WCImgDecoder;
/*     */ import com.sun.webpane.platform.graphics.WCMediaPlayer;
/*     */ import com.sun.webpane.platform.graphics.WCPageBackBuffer;
/*     */ import com.sun.webpane.platform.graphics.WCPath;
/*     */ import com.sun.webpane.platform.graphics.WCRenderQueue;
/*     */ import com.sun.webpane.platform.graphics.WCTransform;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class FXGraphicsManager extends WCGraphicsManager
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(FXGraphicsManager.class.getName());
/*     */ 
/*  33 */   private static final Logger gPerfLog = Logger.getLogger("com.sun.webpane.platform.graphics.WCGPerfMeter");
/*     */ 
/*     */   public WCImgDecoder getImgDecoder()
/*     */   {
/*  39 */     return new WCImgDecoderImpl();
/*     */   }
/*     */   public static boolean gPerfLogEnabled() {
/*  42 */     return gPerfLog.isLoggable(Level.FINE);
/*     */   }
/*     */ 
/*     */   public WCRenderQueue createBufferedContextRQ(WCImage paramWCImage) {
/*  46 */     WCBufferedContext localWCBufferedContext = new WCBufferedContext(paramWCImage);
/*  47 */     WCRenderQueue localWCRenderQueue = new WCRenderQueue(WCGraphicsPerfLogger.isEnabled() ? new WCGraphicsPerfLogger(localWCBufferedContext) : localWCBufferedContext);
/*     */ 
/*  50 */     paramWCImage.setRQ(localWCRenderQueue);
/*  51 */     return localWCRenderQueue;
/*     */   }
/*     */ 
/*     */   public WCFont getWCFont(String paramString, boolean paramBoolean1, boolean paramBoolean2, float paramFloat)
/*     */   {
/*  56 */     WCFont localWCFont = WCFontImpl.getFont(paramString, paramBoolean1, paramBoolean2, paramFloat);
/*  57 */     return (WCFontPerfLogger.isEnabled()) && (localWCFont != null) ? new WCFontPerfLogger(localWCFont) : localWCFont;
/*     */   }
/*     */ 
/*     */   public WCGraphicsContext createGraphicsContext(Object paramObject)
/*     */   {
/*  63 */     WCGraphicsPrismContext localWCGraphicsPrismContext = new WCGraphicsPrismContext((Graphics)paramObject);
/*  64 */     return WCGraphicsPerfLogger.isEnabled() ? new WCGraphicsPerfLogger(localWCGraphicsPrismContext) : localWCGraphicsPrismContext;
/*     */   }
/*     */ 
/*     */   public WCPageBackBuffer createPageBackBuffer() {
/*  68 */     return new WCPageBackBufferImpl();
/*     */   }
/*     */ 
/*     */   public WCPath createWCPath()
/*     */   {
/*  73 */     return new WCPathImpl();
/*     */   }
/*     */ 
/*     */   public WCPath createWCPath(WCPath paramWCPath)
/*     */   {
/*  78 */     return new WCPathImpl((WCPathImpl)paramWCPath);
/*     */   }
/*     */ 
/*     */   public WCImage createWCImage(int paramInt1, int paramInt2)
/*     */   {
/*  83 */     return new WCImageImpl(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public WCImage createRTImage(int paramInt1, int paramInt2)
/*     */   {
/*  88 */     return new RTImage(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   protected WCImageFrame createFrame(int paramInt1, int paramInt2, ByteBuffer paramByteBuffer)
/*     */   {
/*  93 */     int[] arrayOfInt = new int[paramByteBuffer.capacity() / 4];
/*  94 */     paramByteBuffer.order(ByteOrder.nativeOrder());
/*  95 */     paramByteBuffer.asIntBuffer().get(arrayOfInt);
/*  96 */     final WCImageImpl localWCImageImpl = new WCImageImpl(arrayOfInt, paramInt1, paramInt2);
/*     */ 
/*  98 */     return new WCImageFrame() {
/*  99 */       public WCImage getFrame() { return localWCImageImpl; }
/*     */ 
/*     */     };
/*     */   }
/*     */ 
/*     */   public WCTransform createTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 107 */     return new WCTransformImpl(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*     */   }
/*     */ 
/*     */   public WCIcon getSystemIcon(String paramString)
/*     */   {
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */   public String[] getSupportedMediaTypes()
/*     */   {
/* 117 */     Object localObject = MediaManager.getSupportedContentTypes();
/*     */ 
/* 121 */     int i = localObject.length;
/* 122 */     for (int j = 0; j < i; j++) {
/* 123 */       if ("video/x-flv".compareToIgnoreCase(localObject[j]) == 0) {
/* 124 */         System.arraycopy(localObject, j + 1, localObject, j, i - (j + 1));
/* 125 */         i--;
/*     */       }
/*     */     }
/* 128 */     if (i < localObject.length) {
/* 129 */       String[] arrayOfString = new String[i];
/* 130 */       System.arraycopy(localObject, 0, arrayOfString, 0, i);
/* 131 */       localObject = arrayOfString;
/*     */     }
/* 133 */     return localObject;
/*     */   }
/*     */ 
/*     */   protected WCMediaPlayer createMediaPlayer()
/*     */   {
/* 138 */     return new WCMediaPlayerImpl();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.FXGraphicsManager
 * JD-Core Version:    0.6.2
 */