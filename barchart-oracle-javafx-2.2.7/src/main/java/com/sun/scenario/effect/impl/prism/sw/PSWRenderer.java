/*     */ package com.sun.scenario.effect.impl.prism.sw;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.scenario.effect.Effect.AccelType;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.EffectPeer;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.Renderer.RendererState;
/*     */ import com.sun.scenario.effect.impl.prism.PrDrawable;
/*     */ import com.sun.scenario.effect.impl.prism.PrImage;
/*     */ import com.sun.scenario.effect.impl.prism.PrRenderer;
/*     */ import com.sun.scenario.effect.impl.sw.RendererDelegate;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ 
/*     */ public class PSWRenderer extends PrRenderer
/*     */ {
/*     */   private final Screen screen;
/*     */   private final RendererDelegate delegate;
/*     */   private Renderer.RendererState state;
/*     */ 
/*     */   private PSWRenderer(Screen paramScreen, RendererDelegate paramRendererDelegate)
/*     */   {
/*  59 */     this.screen = paramScreen;
/*  60 */     this.delegate = paramRendererDelegate;
/*  61 */     synchronized (this) {
/*  62 */       this.state = Renderer.RendererState.OK;
/*     */     }
/*     */   }
/*     */ 
/*     */   public PrDrawable createDrawable(RTTexture paramRTTexture)
/*     */   {
/*  68 */     return PSWDrawable.create(paramRTTexture);
/*     */   }
/*     */ 
/*     */   private static synchronized PSWRenderer createJSWInstance(Screen paramScreen)
/*     */   {
/*  77 */     PSWRenderer localPSWRenderer = null;
/*     */     try {
/*  79 */       Class localClass = Class.forName("com.sun.scenario.effect.impl.sw.java.JSWRendererDelegate");
/*  80 */       RendererDelegate localRendererDelegate = (RendererDelegate)localClass.newInstance();
/*  81 */       localPSWRenderer = new PSWRenderer(paramScreen, localRendererDelegate); } catch (Throwable localThrowable) {
/*     */     }
/*  83 */     return localPSWRenderer;
/*     */   }
/*     */ 
/*     */   private static synchronized PSWRenderer createSSEInstance(Screen paramScreen)
/*     */   {
/*  92 */     PSWRenderer localPSWRenderer = null;
/*     */     try {
/*  94 */       Class localClass = Class.forName("com.sun.scenario.effect.impl.sw.sse.SSERendererDelegate");
/*  95 */       RendererDelegate localRendererDelegate = (RendererDelegate)localClass.newInstance();
/*  96 */       localPSWRenderer = new PSWRenderer(paramScreen, localRendererDelegate); } catch (Throwable localThrowable) {
/*     */     }
/*  98 */     return localPSWRenderer;
/*     */   }
/*     */ 
/*     */   public static Renderer createRenderer(FilterContext paramFilterContext) {
/* 102 */     Object localObject = paramFilterContext.getReferent();
/* 103 */     GraphicsPipeline localGraphicsPipeline = GraphicsPipeline.getPipeline();
/* 104 */     if ((localGraphicsPipeline == null) || (!(localObject instanceof Screen))) {
/* 105 */       return null;
/*     */     }
/* 107 */     Screen localScreen = (Screen)localObject;
/* 108 */     PSWRenderer localPSWRenderer = createSSEInstance(localScreen);
/* 109 */     if (localPSWRenderer == null) {
/* 110 */       localPSWRenderer = createJSWInstance(localScreen);
/*     */     }
/* 112 */     return localPSWRenderer;
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType()
/*     */   {
/* 117 */     return this.delegate.getAccelType();
/*     */   }
/*     */ 
/*     */   public synchronized Renderer.RendererState getRendererState()
/*     */   {
/* 125 */     return this.state;
/*     */   }
/*     */ 
/*     */   protected Renderer getBackupRenderer()
/*     */   {
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   protected void dispose()
/*     */   {
/* 142 */     synchronized (this) {
/* 143 */       this.state = Renderer.RendererState.DISPOSED;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected final synchronized void markLost()
/*     */   {
/* 153 */     if (this.state == Renderer.RendererState.OK)
/* 154 */       this.state = Renderer.RendererState.LOST;
/*     */   }
/*     */ 
/*     */   public final PSWDrawable createCompatibleImage(int paramInt1, int paramInt2)
/*     */   {
/* 160 */     return PSWDrawable.create(this.screen, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public PSWDrawable getCompatibleImage(int paramInt1, int paramInt2)
/*     */   {
/* 165 */     PSWDrawable localPSWDrawable = (PSWDrawable)super.getCompatibleImage(paramInt1, paramInt2);
/*     */ 
/* 167 */     if (localPSWDrawable == null) {
/* 168 */       markLost();
/*     */     }
/* 170 */     return localPSWDrawable;
/*     */   }
/*     */ 
/*     */   private EffectPeer createIntrinsicPeer(FilterContext paramFilterContext, String paramString)
/*     */   {
/* 185 */     Class localClass = null;
/*     */     EffectPeer localEffectPeer;
/*     */     try
/*     */     {
/* 188 */       localClass = Class.forName("com.sun.scenario.effect.impl.prism.Pr" + paramString + "Peer");
/* 189 */       Constructor localConstructor = localClass.getConstructor(new Class[] { FilterContext.class, Renderer.class, String.class });
/*     */ 
/* 191 */       localEffectPeer = (EffectPeer)localConstructor.newInstance(new Object[] { paramFilterContext, this, paramString });
/*     */     } catch (Exception localException) {
/* 193 */       return null;
/*     */     }
/* 195 */     return localEffectPeer;
/*     */   }
/*     */ 
/*     */   private EffectPeer createPlatformPeer(FilterContext paramFilterContext, String paramString, int paramInt)
/*     */   {
/* 210 */     String str = this.delegate.getPlatformPeerName(paramString, paramInt);
/*     */     EffectPeer localEffectPeer;
/*     */     try
/*     */     {
/* 213 */       Class localClass = Class.forName(str);
/* 214 */       Constructor localConstructor = localClass.getConstructor(new Class[] { FilterContext.class, Renderer.class, String.class });
/*     */ 
/* 216 */       localEffectPeer = (EffectPeer)localConstructor.newInstance(new Object[] { paramFilterContext, this, paramString });
/*     */     } catch (Exception localException) {
/* 218 */       System.err.println("Error: " + getAccelType() + " peer not found for: " + paramString + " due to error: " + localException.getMessage());
/*     */ 
/* 221 */       return null;
/*     */     }
/* 223 */     return localEffectPeer;
/*     */   }
/*     */ 
/*     */   protected EffectPeer createPeer(FilterContext paramFilterContext, String paramString, int paramInt)
/*     */   {
/* 230 */     if (PrRenderer.isIntrinsicPeer(paramString))
/*     */     {
/* 232 */       return createIntrinsicPeer(paramFilterContext, paramString);
/*     */     }
/*     */ 
/* 235 */     return createPlatformPeer(paramFilterContext, paramString, paramInt);
/*     */   }
/*     */ 
/*     */   public boolean isImageDataCompatible(ImageData paramImageData)
/*     */   {
/* 241 */     return (getRendererState() == Renderer.RendererState.OK) && ((paramImageData.getUntransformedImage() instanceof PSWDrawable));
/*     */   }
/*     */ 
/*     */   public void clearImage(Filterable paramFilterable)
/*     */   {
/* 247 */     PSWDrawable localPSWDrawable = (PSWDrawable)paramFilterable;
/* 248 */     localPSWDrawable.clear();
/*     */   }
/*     */ 
/*     */   public ImageData createImageData(FilterContext paramFilterContext, Filterable paramFilterable)
/*     */   {
/* 253 */     if (!(paramFilterable instanceof PrImage)) {
/* 254 */       throw new IllegalArgumentException("Identity source must be PrImage");
/*     */     }
/* 256 */     Image localImage = ((PrImage)paramFilterable).getImage();
/* 257 */     int i = localImage.getWidth();
/* 258 */     int j = localImage.getHeight();
/* 259 */     PSWDrawable localPSWDrawable = createCompatibleImage(i, j);
/* 260 */     if (localPSWDrawable == null) {
/* 261 */       return null;
/*     */     }
/*     */ 
/* 265 */     Graphics localGraphics = localPSWDrawable.createGraphics();
/* 266 */     ResourceFactory localResourceFactory = localGraphics.getResourceFactory();
/* 267 */     Texture localTexture = localResourceFactory.createTexture(localImage);
/* 268 */     localGraphics.drawTexture(localTexture, 0.0F, 0.0F, i, j);
/*     */ 
/* 272 */     localGraphics.sync();
/* 273 */     localTexture.dispose();
/* 274 */     return new ImageData(paramFilterContext, localPSWDrawable, new Rectangle(i, j));
/*     */   }
/*     */ 
/*     */   public Filterable transform(FilterContext paramFilterContext, Filterable paramFilterable, BaseTransform paramBaseTransform, Rectangle paramRectangle1, Rectangle paramRectangle2)
/*     */   {
/* 284 */     PSWDrawable localPSWDrawable = getCompatibleImage(paramRectangle2.width, paramRectangle2.height);
/*     */ 
/* 286 */     if (localPSWDrawable != null) {
/* 287 */       Graphics localGraphics = localPSWDrawable.createGraphics();
/* 288 */       localGraphics.translate(-paramRectangle2.x, -paramRectangle2.y);
/* 289 */       localGraphics.transform(paramBaseTransform);
/* 290 */       localGraphics.drawTexture(((PSWDrawable)paramFilterable).getTextureObject(), paramRectangle1.x, paramRectangle1.y, paramRectangle1.width, paramRectangle1.height);
/*     */     }
/*     */ 
/* 294 */     return localPSWDrawable;
/*     */   }
/*     */ 
/*     */   public ImageData transform(FilterContext paramFilterContext, ImageData paramImageData, BaseTransform paramBaseTransform, Rectangle paramRectangle1, Rectangle paramRectangle2)
/*     */   {
/* 303 */     PSWDrawable localPSWDrawable1 = getCompatibleImage(paramRectangle2.width, paramRectangle2.height);
/*     */ 
/* 305 */     if (localPSWDrawable1 != null) {
/* 306 */       PSWDrawable localPSWDrawable2 = (PSWDrawable)paramImageData.getUntransformedImage();
/* 307 */       Graphics localGraphics = localPSWDrawable1.createGraphics();
/* 308 */       localGraphics.translate(-paramRectangle2.x, -paramRectangle2.y);
/* 309 */       localGraphics.transform(paramBaseTransform);
/* 310 */       localGraphics.drawTexture(localPSWDrawable2.getTextureObject(), paramRectangle1.x, paramRectangle1.y, paramRectangle1.width, paramRectangle1.height);
/*     */     }
/*     */ 
/* 314 */     paramImageData.unref();
/* 315 */     return new ImageData(paramFilterContext, localPSWDrawable1, paramRectangle2);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.sw.PSWRenderer
 * JD-Core Version:    0.6.2
 */