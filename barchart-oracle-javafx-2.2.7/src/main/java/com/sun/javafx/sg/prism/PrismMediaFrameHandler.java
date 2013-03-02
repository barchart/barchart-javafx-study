/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.media.jfxmedia.control.VideoDataBuffer;
/*     */ import com.sun.media.jfxmedia.control.VideoFormat;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.MediaFrame;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.ResourceFactoryListener;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.render.RenderJob;
/*     */ import com.sun.prism.render.ToolkitInterface;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ public class PrismMediaFrameHandler
/*     */   implements ResourceFactoryListener
/*     */ {
/*  30 */   private final Map<Screen, TextureMapEntry> textures = new WeakHashMap(1);
/*     */   private static Map<Object, PrismMediaFrameHandler> handlers;
/*  49 */   private boolean registeredWithFactory = false;
/*     */ 
/* 136 */   private RenderJob releaseRenderJob = new RenderJob(new Runnable() {
/*     */     public void run() {
/* 138 */       PrismMediaFrameHandler.this.releaseData();
/*     */     }
/*     */   });
/*     */ 
/*     */   public static synchronized PrismMediaFrameHandler getHandler(Object paramObject)
/*     */   {
/*  35 */     if (paramObject == null) {
/*  36 */       throw new IllegalArgumentException("provider must be non-null");
/*     */     }
/*  38 */     if (handlers == null) {
/*  39 */       handlers = new WeakHashMap(1);
/*     */     }
/*  41 */     PrismMediaFrameHandler localPrismMediaFrameHandler = (PrismMediaFrameHandler)handlers.get(paramObject);
/*  42 */     if (localPrismMediaFrameHandler == null) {
/*  43 */       localPrismMediaFrameHandler = new PrismMediaFrameHandler(paramObject);
/*  44 */       handlers.put(paramObject, localPrismMediaFrameHandler);
/*     */     }
/*  46 */     return localPrismMediaFrameHandler;
/*     */   }
/*     */ 
/*     */   private PrismMediaFrameHandler(Object paramObject)
/*     */   {
/*     */   }
/*     */ 
/*     */   public Texture getTexture(Graphics paramGraphics, VideoDataBuffer paramVideoDataBuffer)
/*     */   {
/*  66 */     Screen localScreen = paramGraphics.getAssociatedScreen();
/*  67 */     TextureMapEntry localTextureMapEntry = (TextureMapEntry)this.textures.get(localScreen);
/*     */ 
/*  69 */     if (null == paramVideoDataBuffer)
/*     */     {
/*  71 */       if (this.textures.containsKey(localScreen)) {
/*  72 */         this.textures.remove(localScreen);
/*     */       }
/*  74 */       return null;
/*     */     }
/*     */ 
/*  77 */     if (null == localTextureMapEntry)
/*     */     {
/*  79 */       localTextureMapEntry = new TextureMapEntry(null);
/*  80 */       this.textures.put(localScreen, localTextureMapEntry);
/*     */     }
/*     */ 
/*  84 */     if ((null == localTextureMapEntry.texture) || (localTextureMapEntry.lastFrameTime != paramVideoDataBuffer.getTimestamp())) {
/*  85 */       updateTexture(paramGraphics, paramVideoDataBuffer, localTextureMapEntry);
/*     */     }
/*     */ 
/*  88 */     return localTextureMapEntry.texture;
/*     */   }
/*     */ 
/*     */   private void updateTexture(Graphics paramGraphics, VideoDataBuffer paramVideoDataBuffer, TextureMapEntry paramTextureMapEntry)
/*     */   {
/*  93 */     Screen localScreen = paramGraphics.getAssociatedScreen();
/*     */ 
/*  96 */     if ((paramTextureMapEntry.texture != null) && ((paramTextureMapEntry.encodedWidth != paramVideoDataBuffer.getEncodedWidth()) || (paramTextureMapEntry.encodedHeight != paramVideoDataBuffer.getEncodedHeight())))
/*     */     {
/* 100 */       paramTextureMapEntry.texture.dispose();
/* 101 */       paramTextureMapEntry.texture = null;
/*     */     }
/*     */ 
/* 104 */     PrismFrameBuffer localPrismFrameBuffer = new PrismFrameBuffer(paramVideoDataBuffer);
/* 105 */     if (paramTextureMapEntry.texture == null) {
/* 106 */       if (!this.registeredWithFactory)
/*     */       {
/* 109 */         GraphicsPipeline.getDefaultResourceFactory().addFactoryListener(this);
/* 110 */         this.registeredWithFactory = true;
/*     */       }
/*     */ 
/* 113 */       paramTextureMapEntry.texture = GraphicsPipeline.getPipeline().getResourceFactory(localScreen).createTexture(localPrismFrameBuffer);
/*     */ 
/* 116 */       paramTextureMapEntry.encodedWidth = paramVideoDataBuffer.getEncodedWidth();
/* 117 */       paramTextureMapEntry.encodedHeight = paramVideoDataBuffer.getEncodedHeight();
/*     */     }
/*     */ 
/* 121 */     if (paramTextureMapEntry.texture != null) {
/* 122 */       paramTextureMapEntry.texture.update(localPrismFrameBuffer, false);
/*     */     }
/* 124 */     paramTextureMapEntry.lastFrameTime = paramVideoDataBuffer.getTimestamp();
/*     */   }
/*     */ 
/*     */   private void releaseData() {
/* 128 */     for (TextureMapEntry localTextureMapEntry : this.textures.values()) {
/* 129 */       if ((localTextureMapEntry != null) && (localTextureMapEntry.texture != null)) {
/* 130 */         localTextureMapEntry.texture.dispose();
/*     */       }
/*     */     }
/* 133 */     this.textures.clear();
/*     */   }
/*     */ 
/*     */   public void releaseTextures()
/*     */   {
/* 147 */     Toolkit localToolkit = Toolkit.getToolkit();
/* 148 */     ToolkitInterface localToolkitInterface = (ToolkitInterface)localToolkit;
/* 149 */     localToolkitInterface.addRenderJob(this.releaseRenderJob);
/*     */   }
/*     */ 
/*     */   public void factoryReset() {
/* 153 */     releaseData();
/*     */   }
/*     */ 
/*     */   public void factoryReleased() {
/* 157 */     releaseData();
/*     */   }
/*     */ 
/*     */   private static class TextureMapEntry
/*     */   {
/* 262 */     public double lastFrameTime = -1.0D;
/*     */     public Texture texture;
/*     */     public int encodedWidth;
/*     */     public int encodedHeight;
/*     */   }
/*     */ 
/*     */   private class PrismFrameBuffer
/*     */     implements MediaFrame
/*     */   {
/*     */     private final PixelFormat videoFormat;
/*     */     private final VideoDataBuffer master;
/*     */ 
/*     */     public PrismFrameBuffer(VideoDataBuffer arg2)
/*     */     {
/*     */       Object localObject;
/* 169 */       if (null == localObject) {
/* 170 */         throw new NullPointerException();
/*     */       }
/*     */ 
/* 173 */       this.master = localObject;
/* 174 */       switch (PrismMediaFrameHandler.2.$SwitchMap$com$sun$media$jfxmedia$control$VideoFormat[this.master.getFormat().ordinal()]) {
/*     */       case 1:
/* 176 */         this.videoFormat = PixelFormat.INT_ARGB_PRE;
/* 177 */         break;
/*     */       case 2:
/* 179 */         this.videoFormat = PixelFormat.MULTI_YCbCr_420;
/* 180 */         break;
/*     */       case 3:
/* 182 */         this.videoFormat = PixelFormat.BYTE_APPLE_422;
/* 183 */         break;
/*     */       case 4:
/*     */       default:
/* 187 */         throw new IllegalArgumentException("Unsupported video format " + this.master.getFormat());
/*     */       }
/*     */     }
/*     */ 
/*     */     public ByteBuffer getBuffer() {
/* 192 */       return this.master.getBuffer();
/*     */     }
/*     */ 
/*     */     public void holdFrame() {
/* 196 */       this.master.holdFrame();
/*     */     }
/*     */ 
/*     */     public void releaseFrame() {
/* 200 */       this.master.releaseFrame();
/*     */     }
/*     */ 
/*     */     public PixelFormat getPixelFormat() {
/* 204 */       return this.videoFormat;
/*     */     }
/*     */ 
/*     */     public int getWidth() {
/* 208 */       return this.master.getWidth();
/*     */     }
/*     */ 
/*     */     public int getHeight() {
/* 212 */       return this.master.getHeight();
/*     */     }
/*     */ 
/*     */     public int getEncodedWidth() {
/* 216 */       return this.master.getEncodedWidth();
/*     */     }
/*     */ 
/*     */     public int getEncodedHeight() {
/* 220 */       return this.master.getEncodedHeight();
/*     */     }
/*     */ 
/*     */     public int planeCount() {
/* 224 */       return this.master.getPlaneCount();
/*     */     }
/*     */ 
/*     */     public int[] planeOffsets() {
/* 228 */       return this.master.getPlaneOffsets();
/*     */     }
/*     */ 
/*     */     public int offsetForPlane(int paramInt) {
/* 232 */       return this.master.getOffsetForPlane(paramInt);
/*     */     }
/*     */ 
/*     */     public int[] planeStrides() {
/* 236 */       return this.master.getPlaneStrides();
/*     */     }
/*     */ 
/*     */     public int strideForPlane(int paramInt) {
/* 240 */       return this.master.getStrideForPlane(paramInt);
/*     */     }
/*     */ 
/*     */     public MediaFrame convertToFormat(PixelFormat paramPixelFormat) {
/* 244 */       if (paramPixelFormat == getPixelFormat()) {
/* 245 */         return this;
/*     */       }
/*     */ 
/* 249 */       if (paramPixelFormat != PixelFormat.INT_ARGB_PRE) {
/* 250 */         return null;
/*     */       }
/*     */ 
/* 253 */       VideoDataBuffer localVideoDataBuffer = this.master.convertToFormat(VideoFormat.BGRA_PRE);
/* 254 */       if (null == localVideoDataBuffer) {
/* 255 */         return null;
/*     */       }
/* 257 */       return new PrismFrameBuffer(PrismMediaFrameHandler.this, localVideoDataBuffer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.PrismMediaFrameHandler
 * JD-Core Version:    0.6.2
 */