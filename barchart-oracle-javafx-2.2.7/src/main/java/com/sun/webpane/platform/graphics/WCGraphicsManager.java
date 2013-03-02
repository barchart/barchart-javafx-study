/*     */ package com.sun.webpane.platform.graphics;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ public abstract class WCGraphicsManager
/*     */ {
/*  16 */   private AtomicInteger idCount = new AtomicInteger(0);
/*     */ 
/*  18 */   HashMap<Integer, Ref> refMap = new HashMap();
/*     */ 
/*  20 */   private static ResourceBundle imageProperties = null;
/*  21 */   private static WCGraphicsManager manager = null;
/*     */ 
/*     */   public static void setGraphicsManager(WCGraphicsManager paramWCGraphicsManager) {
/*  24 */     manager = paramWCGraphicsManager;
/*     */   }
/*     */ 
/*     */   public static WCGraphicsManager getGraphicsManager() {
/*  28 */     return manager;
/*     */   }
/*     */ 
/*     */   public abstract WCImgDecoder getImgDecoder();
/*     */ 
/*     */   public abstract WCGraphicsContext createGraphicsContext(Object paramObject);
/*     */ 
/*     */   public abstract WCRenderQueue createBufferedContextRQ(WCImage paramWCImage);
/*     */ 
/*     */   public abstract WCPageBackBuffer createPageBackBuffer();
/*     */ 
/*     */   public abstract WCFont getWCFont(String paramString, boolean paramBoolean1, boolean paramBoolean2, float paramFloat);
/*     */ 
/*     */   public abstract WCPath createWCPath();
/*     */ 
/*     */   public abstract WCPath createWCPath(WCPath paramWCPath);
/*     */ 
/*     */   public abstract WCImage createWCImage(int paramInt1, int paramInt2);
/*     */ 
/*     */   public abstract WCImage createRTImage(int paramInt1, int paramInt2);
/*     */ 
/*     */   protected abstract WCImageFrame createFrame(int paramInt1, int paramInt2, ByteBuffer paramByteBuffer);
/*     */ 
/*     */   public static String getResourceName(String paramString) {
/*  52 */     if (imageProperties == null) {
/*  53 */       imageProperties = ResourceBundle.getBundle("com.sun.webpane.platform.graphics.Images");
/*     */     }
/*     */     try
/*     */     {
/*  57 */       return imageProperties.getString(paramString);
/*     */     } catch (MissingResourceException localMissingResourceException) {
/*     */     }
/*  60 */     return paramString;
/*     */   }
/*     */ 
/*     */   private void fwkLoadFromResource(String paramString, long paramLong)
/*     */   {
/*  65 */     InputStream localInputStream = getClass().getResourceAsStream(getResourceName(paramString));
/*  66 */     if (localInputStream == null) {
/*  67 */       return;
/*     */     }
/*     */ 
/*  70 */     byte[] arrayOfByte = new byte[1024];
/*     */     try
/*     */     {
/*     */       int i;
/*  73 */       while ((i = localInputStream.read(arrayOfByte)) > -1) {
/*  74 */         append(paramLong, arrayOfByte, i);
/*     */       }
/*  76 */       localInputStream.close();
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract WCTransform createTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */ 
/*     */   public abstract WCIcon getSystemIcon(String paramString);
/*     */ 
/*     */   public String[] getSupportedMediaTypes()
/*     */   {
/*  89 */     return new String[0];
/*     */   }
/*     */ 
/*     */   private WCMediaPlayer fwkCreateMediaPlayer(long paramLong) {
/*  93 */     WCMediaPlayer localWCMediaPlayer = createMediaPlayer();
/*  94 */     localWCMediaPlayer.setNativePointer(paramLong);
/*  95 */     return localWCMediaPlayer;
/*     */   }
/*     */ 
/*     */   protected abstract WCMediaPlayer createMediaPlayer();
/*     */ 
/*     */   int createID() {
/* 101 */     return this.idCount.incrementAndGet();
/*     */   }
/*     */ 
/*     */   synchronized void ref(Ref paramRef) {
/* 105 */     this.refMap.put(Integer.valueOf(paramRef.id), paramRef);
/*     */   }
/*     */ 
/*     */   synchronized Ref deref(Ref paramRef) {
/* 109 */     return (Ref)this.refMap.remove(Integer.valueOf(paramRef.id));
/*     */   }
/*     */ 
/*     */   public synchronized Ref getRef(int paramInt) {
/* 113 */     return (Ref)this.refMap.get(Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   private static native void append(long paramLong, byte[] paramArrayOfByte, int paramInt);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCGraphicsManager
 * JD-Core Version:    0.6.2
 */