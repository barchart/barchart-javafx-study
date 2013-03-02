/*     */ package com.sun.prism.impl;
/*     */ 
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.ResourceFactoryListener;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.Texture.Usage;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ public abstract class BaseResourceFactory
/*     */   implements ResourceFactory
/*     */ {
/*  22 */   private static final Map<Image, Texture> texCache = new WeakHashMap();
/*     */ 
/*  27 */   private final WeakHashMap<ResourceFactoryListener, Boolean> listenerMap = new WeakHashMap();
/*     */ 
/*     */   public void addFactoryListener(ResourceFactoryListener paramResourceFactoryListener)
/*     */   {
/*  31 */     this.listenerMap.put(paramResourceFactoryListener, Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   public void removeFactoryListener(ResourceFactoryListener paramResourceFactoryListener)
/*     */   {
/*  37 */     this.listenerMap.remove(paramResourceFactoryListener);
/*     */   }
/*     */ 
/*     */   public boolean isDeviceReady() {
/*  41 */     return true;
/*     */   }
/*     */ 
/*     */   protected void clearTextureCache() {
/*  45 */     Collection localCollection = texCache.values();
/*  46 */     for (Texture localTexture : localCollection) {
/*  47 */       localTexture.dispose();
/*     */     }
/*  49 */     texCache.clear();
/*     */   }
/*     */ 
/*     */   protected ResourceFactoryListener[] getFactoryListeners() {
/*  53 */     return (ResourceFactoryListener[])this.listenerMap.keySet().toArray(new ResourceFactoryListener[0]);
/*     */   }
/*     */ 
/*     */   protected void notifyReset()
/*     */   {
/*  61 */     texCache.clear();
/*     */ 
/*  65 */     ResourceFactoryListener[] arrayOfResourceFactoryListener1 = getFactoryListeners();
/*  66 */     for (ResourceFactoryListener localResourceFactoryListener : arrayOfResourceFactoryListener1)
/*  67 */       if (null != localResourceFactoryListener)
/*  68 */         localResourceFactoryListener.factoryReset();
/*     */   }
/*     */ 
/*     */   protected void notifyReleased()
/*     */   {
/*  77 */     texCache.clear();
/*     */ 
/*  81 */     ResourceFactoryListener[] arrayOfResourceFactoryListener1 = getFactoryListeners();
/*  82 */     for (ResourceFactoryListener localResourceFactoryListener : arrayOfResourceFactoryListener1)
/*  83 */       if (null != localResourceFactoryListener)
/*  84 */         localResourceFactoryListener.factoryReleased();
/*     */   }
/*     */ 
/*     */   public Texture getCachedTexture(Image paramImage)
/*     */   {
/*  90 */     return getCachedTexture(paramImage, true);
/*     */   }
/*     */ 
/*     */   public Texture getCachedTexture(Image paramImage, boolean paramBoolean) {
/*  94 */     if (paramImage == null) {
/*  95 */       throw new IllegalArgumentException("Image must be non-null");
/*     */     }
/*  97 */     Texture localTexture = (Texture)texCache.get(paramImage);
/*  98 */     int i = paramImage.getSerial();
/*  99 */     if (localTexture == null) {
/* 100 */       localTexture = createTexture(paramImage, Texture.Usage.DEFAULT, paramBoolean);
/* 101 */       if (localTexture == null) {
/* 102 */         clearTextureCache();
/* 103 */         localTexture = createTexture(paramImage, Texture.Usage.DEFAULT, paramBoolean);
/*     */       }
/* 105 */       if (localTexture != null) {
/* 106 */         localTexture.setLastImageSerial(i);
/*     */       }
/*     */ 
/* 109 */       texCache.put(paramImage, localTexture);
/* 110 */     } else if (localTexture.getLastImageSerial() != i) {
/* 111 */       localTexture.update(paramImage, 0, 0, paramImage.getWidth(), paramImage.getHeight(), false);
/* 112 */       localTexture.setLastImageSerial(i);
/*     */     }
/* 114 */     return localTexture;
/*     */   }
/*     */ 
/*     */   public Texture createTexture(Image paramImage) {
/* 118 */     return createTexture(paramImage, Texture.Usage.DEFAULT, true);
/*     */   }
/*     */ 
/*     */   public Texture createTexture(Image paramImage, Texture.Usage paramUsage, boolean paramBoolean)
/*     */   {
/* 124 */     PixelFormat localPixelFormat = paramImage.getPixelFormat();
/* 125 */     int i = paramImage.getWidth();
/* 126 */     int j = paramImage.getHeight();
/* 127 */     Texture localTexture = createTexture(localPixelFormat, paramUsage, i, j, paramBoolean);
/*     */ 
/* 131 */     if (localTexture != null) {
/* 132 */       localTexture.update(paramImage, 0, 0, i, j, true);
/*     */     }
/* 134 */     return localTexture;
/*     */   }
/*     */ 
/*     */   public Texture createTexture(PixelFormat paramPixelFormat, Texture.Usage paramUsage, int paramInt1, int paramInt2) {
/* 138 */     return createTexture(paramPixelFormat, paramUsage, paramInt1, paramInt2, true);
/*     */   }
/*     */ 
/*     */   public Texture createMaskTexture(int paramInt1, int paramInt2) {
/* 142 */     return createTexture(PixelFormat.BYTE_ALPHA, Texture.Usage.DEFAULT, paramInt1, paramInt2, false);
/*     */   }
/*     */ 
/*     */   public Texture createFloatTexture(int paramInt1, int paramInt2) {
/* 146 */     return createTexture(PixelFormat.FLOAT_XYZW, Texture.Usage.DEFAULT, paramInt1, paramInt2, false);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.BaseResourceFactory
 * JD-Core Version:    0.6.2
 */