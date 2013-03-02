/*     */ package com.sun.scenario.effect.impl.prism.ps;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.GraphicsPipeline.ShaderModel;
/*     */ import com.sun.prism.GraphicsPipeline.ShaderType;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.ResourceFactoryListener;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.ps.Shader;
/*     */ import com.sun.prism.ps.ShaderFactory;
/*     */ import com.sun.prism.ps.ShaderGraphics;
/*     */ import com.sun.scenario.effect.Effect.AccelType;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import com.sun.scenario.effect.FloatMap;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.EffectPeer;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.Renderer.RendererState;
/*     */ import com.sun.scenario.effect.impl.hw.ShaderSource;
/*     */ import com.sun.scenario.effect.impl.prism.PrDrawable;
/*     */ import com.sun.scenario.effect.impl.prism.PrImage;
/*     */ import com.sun.scenario.effect.impl.prism.PrRenderer;
/*     */ import com.sun.scenario.effect.impl.prism.PrTexture;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class PPSRenderer extends PrRenderer
/*     */ {
/*     */   private final Screen screen;
/*     */   private final ShaderSource shaderSource;
/*     */   private Renderer.RendererState state;
/*  68 */   private final ResourceFactoryListener listener = new ResourceFactoryListener()
/*     */   {
/*     */     public void factoryReset()
/*     */     {
/*  72 */       PPSRenderer.this.dispose();
/*     */     }
/*     */ 
/*     */     public void factoryReleased() {
/*  76 */       PPSRenderer.this.dispose();
/*     */     }
/*  68 */   };
/*     */ 
/*     */   private PPSRenderer(Screen paramScreen, ShaderSource paramShaderSource)
/*     */   {
/*  81 */     this.screen = paramScreen;
/*  82 */     this.shaderSource = paramShaderSource;
/*  83 */     synchronized (this) {
/*  84 */       this.state = Renderer.RendererState.OK;
/*     */     }
/*  86 */     GraphicsPipeline.getPipeline().getResourceFactory(paramScreen).addFactoryListener(this.listener);
/*     */   }
/*     */ 
/*     */   public PrDrawable createDrawable(RTTexture paramRTTexture)
/*     */   {
/*  92 */     return PPSDrawable.create(paramRTTexture);
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType()
/*     */   {
/*  97 */     return this.shaderSource.getAccelType();
/*     */   }
/*     */ 
/*     */   public synchronized Renderer.RendererState getRendererState()
/*     */   {
/* 105 */     return this.state;
/*     */   }
/*     */ 
/*     */   protected Renderer getBackupRenderer()
/*     */   {
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   protected void dispose()
/*     */   {
/* 125 */     for (EffectPeer localEffectPeer : getPeers()) {
/* 126 */       localEffectPeer.dispose();
/*     */     }
/* 128 */     synchronized (this) {
/* 129 */       this.state = Renderer.RendererState.DISPOSED;
/*     */     }
/* 131 */     GraphicsPipeline.getPipeline().getResourceFactory(this.screen).removeFactoryListener(this.listener);
/*     */   }
/*     */ 
/*     */   protected final synchronized void markLost()
/*     */   {
/* 141 */     if (this.state == Renderer.RendererState.OK)
/* 142 */       this.state = Renderer.RendererState.LOST;
/*     */   }
/*     */ 
/*     */   public final PPSDrawable createCompatibleImage(int paramInt1, int paramInt2)
/*     */   {
/* 148 */     return PPSDrawable.create(this.screen, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public PPSDrawable getCompatibleImage(int paramInt1, int paramInt2)
/*     */   {
/* 153 */     PPSDrawable localPPSDrawable = (PPSDrawable)super.getCompatibleImage(paramInt1, paramInt2);
/*     */ 
/* 155 */     if (localPPSDrawable == null) {
/* 156 */       markLost();
/*     */     }
/* 158 */     return localPPSDrawable;
/*     */   }
/*     */ 
/*     */   public Object createFloatTexture(int paramInt1, int paramInt2)
/*     */   {
/* 163 */     Texture localTexture = GraphicsPipeline.getPipeline().getResourceFactory(this.screen).createFloatTexture(paramInt1, paramInt2);
/*     */ 
/* 165 */     return new PrTexture(localTexture);
/*     */   }
/*     */ 
/*     */   public void updateFloatTexture(Object paramObject, FloatMap paramFloatMap)
/*     */   {
/* 170 */     FloatBuffer localFloatBuffer = paramFloatMap.getBuffer();
/* 171 */     int i = paramFloatMap.getWidth();
/* 172 */     int j = paramFloatMap.getHeight();
/* 173 */     Image localImage = Image.fromFloatMapData(localFloatBuffer, i, j);
/* 174 */     Texture localTexture = ((PrTexture)paramObject).getTextureObject();
/* 175 */     localTexture.update(localImage);
/*     */   }
/*     */ 
/*     */   public Shader createShader(String paramString, Map<String, Integer> paramMap1, Map<String, Integer> paramMap2, boolean paramBoolean)
/*     */   {
/* 183 */     InputStream localInputStream = this.shaderSource.loadSource(paramString);
/* 184 */     int i = paramMap1.keySet().size() - 1;
/* 185 */     ShaderFactory localShaderFactory = (ShaderFactory)GraphicsPipeline.getPipeline().getResourceFactory(this.screen);
/*     */ 
/* 187 */     return localShaderFactory.createShader(localInputStream, paramMap1, paramMap2, i, paramBoolean, false);
/*     */   }
/*     */ 
/*     */   private EffectPeer createIntrinsicPeer(FilterContext paramFilterContext, String paramString)
/*     */   {
/* 204 */     Class localClass = null;
/*     */     EffectPeer localEffectPeer;
/*     */     try
/*     */     {
/* 207 */       localClass = Class.forName("com.sun.scenario.effect.impl.prism.Pr" + paramString + "Peer");
/* 208 */       Constructor localConstructor = localClass.getConstructor(new Class[] { FilterContext.class, Renderer.class, String.class });
/*     */ 
/* 210 */       localEffectPeer = (EffectPeer)localConstructor.newInstance(new Object[] { paramFilterContext, this, paramString });
/*     */     }
/*     */     catch (Exception localException) {
/* 213 */       return null;
/*     */     }
/* 215 */     return localEffectPeer;
/*     */   }
/*     */ 
/*     */   private EffectPeer createPlatformPeer(FilterContext paramFilterContext, String paramString, int paramInt)
/*     */   {
/* 232 */     String str = paramString;
/* 233 */     if (paramInt > 0)
/* 234 */       str = str + "_" + paramInt;
/*     */     EffectPeer localEffectPeer;
/*     */     try {
/* 237 */       Class localClass = Class.forName("com.sun.scenario.effect.impl.prism.ps.PPS" + paramString + "Peer");
/* 238 */       Constructor localConstructor = localClass.getConstructor(new Class[] { FilterContext.class, Renderer.class, String.class });
/*     */ 
/* 240 */       localEffectPeer = (EffectPeer)localConstructor.newInstance(new Object[] { paramFilterContext, this, str });
/*     */     }
/*     */     catch (Exception localException) {
/* 243 */       System.err.println("Error: Prism peer not found for: " + paramString + " due to error: " + localException.getMessage());
/*     */ 
/* 245 */       return null;
/*     */     }
/* 247 */     return localEffectPeer;
/*     */   }
/*     */ 
/*     */   protected EffectPeer createPeer(FilterContext paramFilterContext, String paramString, int paramInt)
/*     */   {
/* 254 */     if (PrRenderer.isIntrinsicPeer(paramString))
/*     */     {
/* 256 */       return createIntrinsicPeer(paramFilterContext, paramString);
/*     */     }
/*     */ 
/* 259 */     return createPlatformPeer(paramFilterContext, paramString, paramInt);
/*     */   }
/*     */ 
/*     */   public boolean isImageDataCompatible(ImageData paramImageData)
/*     */   {
/* 265 */     if (getRendererState() == Renderer.RendererState.OK) {
/* 266 */       Filterable localFilterable = paramImageData.getUntransformedImage();
/* 267 */       return ((localFilterable instanceof PPSDrawable)) && (!((PPSDrawable)localFilterable).isLost());
/*     */     }
/*     */ 
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */   public void clearImage(Filterable paramFilterable)
/*     */   {
/* 275 */     PPSDrawable localPPSDrawable = (PPSDrawable)paramFilterable;
/* 276 */     localPPSDrawable.clear();
/*     */   }
/*     */ 
/*     */   public ImageData createImageData(FilterContext paramFilterContext, Filterable paramFilterable)
/*     */   {
/* 281 */     if (!(paramFilterable instanceof PrImage)) {
/* 282 */       throw new IllegalArgumentException("Identity source must be PrImage");
/*     */     }
/* 284 */     Image localImage = ((PrImage)paramFilterable).getImage();
/* 285 */     int i = localImage.getWidth();
/* 286 */     int j = localImage.getHeight();
/* 287 */     PPSDrawable localPPSDrawable = createCompatibleImage(i, j);
/* 288 */     if (localPPSDrawable == null) {
/* 289 */       return null;
/*     */     }
/*     */ 
/* 293 */     ShaderGraphics localShaderGraphics = localPPSDrawable.createGraphics();
/* 294 */     ResourceFactory localResourceFactory = localShaderGraphics.getResourceFactory();
/* 295 */     Texture localTexture = localResourceFactory.createTexture(localImage);
/* 296 */     localShaderGraphics.drawTexture(localTexture, 0.0F, 0.0F, i, j);
/*     */ 
/* 300 */     localShaderGraphics.sync();
/* 301 */     localTexture.dispose();
/* 302 */     return new ImageData(paramFilterContext, localPPSDrawable, new Rectangle(i, j));
/*     */   }
/*     */ 
/*     */   public Filterable transform(FilterContext paramFilterContext, Filterable paramFilterable, BaseTransform paramBaseTransform, Rectangle paramRectangle1, Rectangle paramRectangle2)
/*     */   {
/* 312 */     PPSDrawable localPPSDrawable = getCompatibleImage(paramRectangle2.width, paramRectangle2.height);
/*     */ 
/* 314 */     if (localPPSDrawable != null) {
/* 315 */       ShaderGraphics localShaderGraphics = localPPSDrawable.createGraphics();
/* 316 */       localShaderGraphics.translate(-paramRectangle2.x, -paramRectangle2.y);
/* 317 */       localShaderGraphics.transform(paramBaseTransform);
/* 318 */       localShaderGraphics.drawTexture(((PPSDrawable)paramFilterable).getTextureObject(), paramRectangle1.x, paramRectangle1.y, paramRectangle1.width, paramRectangle1.height);
/*     */     }
/*     */ 
/* 322 */     return localPPSDrawable;
/*     */   }
/*     */ 
/*     */   public ImageData transform(FilterContext paramFilterContext, ImageData paramImageData, BaseTransform paramBaseTransform, Rectangle paramRectangle1, Rectangle paramRectangle2)
/*     */   {
/* 331 */     PPSDrawable localPPSDrawable1 = getCompatibleImage(paramRectangle2.width, paramRectangle2.height);
/*     */ 
/* 333 */     if (localPPSDrawable1 != null) {
/* 334 */       PPSDrawable localPPSDrawable2 = (PPSDrawable)paramImageData.getUntransformedImage();
/* 335 */       ShaderGraphics localShaderGraphics = localPPSDrawable1.createGraphics();
/* 336 */       localShaderGraphics.translate(-paramRectangle2.x, -paramRectangle2.y);
/* 337 */       localShaderGraphics.transform(paramBaseTransform);
/* 338 */       localShaderGraphics.drawTexture(localPPSDrawable2.getTextureObject(), paramRectangle1.x, paramRectangle1.y, paramRectangle1.width, paramRectangle1.height);
/*     */     }
/*     */ 
/* 342 */     paramImageData.unref();
/* 343 */     return new ImageData(paramFilterContext, localPPSDrawable1, paramRectangle2);
/*     */   }
/*     */ 
/*     */   private static ShaderSource createShaderSource(String paramString) {
/* 347 */     Class localClass = null;
/*     */     try {
/* 349 */       localClass = Class.forName(paramString);
/* 350 */       return (ShaderSource)localClass.newInstance();
/*     */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 352 */       System.err.println(paramString + " class not found");
/* 353 */       return null;
/*     */     }
/*     */     catch (Throwable localThrowable) {
/*     */     }
/* 357 */     return null;
/*     */   }
/*     */ 
/*     */   public static Renderer createRenderer(FilterContext paramFilterContext)
/*     */   {
/* 362 */     Object localObject = paramFilterContext.getReferent();
/* 363 */     GraphicsPipeline localGraphicsPipeline = GraphicsPipeline.getPipeline();
/* 364 */     if ((localGraphicsPipeline == null) || (!(localObject instanceof Screen))) {
/* 365 */       return null;
/*     */     }
/* 367 */     Screen localScreen = (Screen)localObject;
/* 368 */     ShaderSource localShaderSource = null;
/* 369 */     if (localGraphicsPipeline.supportsShader(GraphicsPipeline.ShaderType.HLSL, GraphicsPipeline.ShaderModel.SM3))
/* 370 */       localShaderSource = createShaderSource("com.sun.scenario.effect.impl.hw.d3d.D3DShaderSource");
/* 371 */     else if (localGraphicsPipeline.supportsShader(GraphicsPipeline.ShaderType.GLSL, GraphicsPipeline.ShaderModel.SM3))
/* 372 */       localShaderSource = createShaderSource("com.sun.scenario.effect.impl.es2.ES2ShaderSource");
/*     */     else {
/* 374 */       throw new InternalError("Unknown GraphicsPipeline");
/*     */     }
/* 376 */     if (localShaderSource == null) {
/* 377 */       return null;
/*     */     }
/* 379 */     return new PPSRenderer(localScreen, localShaderSource);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSRenderer
 * JD-Core Version:    0.6.2
 */