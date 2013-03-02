/*     */ package com.sun.prism.es2;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.Affine2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.GeneralTransform3D;
/*     */ import com.sun.prism.CompositeMode;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.RenderTarget;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.camera.PrismCameraImpl;
/*     */ import com.sun.prism.es2.gl.GLContext;
/*     */ import com.sun.prism.es2.gl.GLDrawable;
/*     */ import com.sun.prism.es2.gl.GLFactory;
/*     */ import com.sun.prism.es2.gl.GLPixelFormat;
/*     */ import com.sun.prism.impl.BufferUtil;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.prism.impl.ps.BaseShaderContext;
/*     */ import com.sun.prism.impl.ps.BaseShaderContext.State;
/*     */ import com.sun.prism.ps.Shader;
/*     */ import com.sun.prism.ps.ShaderFactory;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ class ES2Context extends BaseShaderContext
/*     */ {
/*  35 */   private static GeneralTransform3D projViewTx = new GeneralTransform3D();
/*  36 */   private static GeneralTransform3D scratchTx = new GeneralTransform3D();
/*  37 */   private static final GeneralTransform3D flipTx = new GeneralTransform3D();
/*     */   private RenderTarget currentTarget;
/*  40 */   private final FloatBuffer rawMatrix = BufferUtil.newFloatBuffer(16);
/*     */   private final GLContext glContext;
/*     */   private final GLDrawable dummyGLDrawable;
/*     */   private final GLPixelFormat pixelFormat;
/*     */   private final BaseShaderContext.State state;
/*     */   private final int quadIndices;
/*  47 */   private GLDrawable currentDrawable = null;
/*  48 */   private ES2RenderingContext currentRenderingContext = null;
/*  49 */   private int indexBuffer = 0;
/*     */   public static final int NUM_QUADS = 256;
/* 122 */   private int savedFBO = 0;
/*     */ 
/*     */   private static ES2VertexBuffer createVertexBuffer()
/*     */   {
/*  54 */     return new ES2VertexBuffer(256);
/*     */   }
/*     */ 
/*     */   ES2Context(Screen paramScreen, ShaderFactory paramShaderFactory) {
/*  58 */     super(paramScreen, paramShaderFactory, createVertexBuffer());
/*  59 */     GLFactory localGLFactory = ES2Pipeline.glFactory;
/*     */ 
/*  64 */     this.pixelFormat = localGLFactory.createGLPixelFormat(paramScreen.getNativeScreen(), ES2Pipeline.pixelFormatAttributes);
/*     */ 
/*  68 */     this.dummyGLDrawable = localGLFactory.createDummyGLDrawable(this.pixelFormat);
/*  69 */     this.glContext = localGLFactory.createGLContext(this.dummyGLDrawable, this.pixelFormat, localGLFactory.getShareContext(), PrismSettings.isVsyncEnabled);
/*     */ 
/*  71 */     makeCurrent(this.dummyGLDrawable);
/*  72 */     ES2VertexBuffer localES2VertexBuffer = (ES2VertexBuffer)getVertexBuffer();
/*  73 */     localES2VertexBuffer.enableVertexAttributes(this.glContext);
/*     */ 
/*  75 */     this.quadIndices = localES2VertexBuffer.genQuadsIndexBuffer(256);
/*  76 */     setIndexBuffer(this.quadIndices);
/*  77 */     this.state = new BaseShaderContext.State();
/*     */ 
/*  82 */     if (PlatformUtil.isMac()) {
/*  83 */       HashMap localHashMap = (HashMap)ES2Pipeline.getInstance().getDeviceDetails();
/*  84 */       ES2Pipeline.glFactory.updateDeviceDetails(localHashMap, this.glContext);
/*     */     }
/*     */   }
/*     */ 
/*     */   final void setIndexBuffer(int paramInt) {
/*  89 */     if (this.indexBuffer != paramInt)
/*  90 */       this.glContext.setIndexBuffer(this.indexBuffer = paramInt);
/*     */   }
/*     */ 
/*     */   GLContext getGLContext()
/*     */   {
/*  95 */     return this.glContext;
/*     */   }
/*     */ 
/*     */   GLDrawable getDummyDrawable() {
/*  99 */     return this.dummyGLDrawable;
/*     */   }
/*     */ 
/*     */   GLPixelFormat getPixelFormat() {
/* 103 */     return this.pixelFormat;
/*     */   }
/*     */ 
/*     */   void setCurrentRenderingContext(ES2RenderingContext paramES2RenderingContext, GLDrawable paramGLDrawable) {
/* 107 */     if ((paramES2RenderingContext != null) && (paramGLDrawable == null)) {
/* 108 */       System.err.println("Warning: ES2Context.setCurrentRenderingContext: rc = " + paramES2RenderingContext + ", drawable = " + paramGLDrawable);
/*     */     }
/*     */ 
/* 111 */     this.currentRenderingContext = paramES2RenderingContext;
/* 112 */     makeCurrent(paramGLDrawable);
/*     */   }
/*     */ 
/*     */   ES2RenderingContext getCurrentRenderingContext() {
/* 116 */     return this.currentRenderingContext;
/*     */   }
/*     */ 
/*     */   private void makeCurrent(GLDrawable paramGLDrawable)
/*     */   {
/* 124 */     if (PlatformUtil.isMac()) {
/* 125 */       if (paramGLDrawable != this.currentDrawable) {
/* 126 */         if (paramGLDrawable == this.dummyGLDrawable)
/*     */         {
/* 128 */           this.glContext.bindFBO(this.savedFBO);
/*     */         } else {
/* 130 */           this.savedFBO = this.glContext.getBoundFBO();
/* 131 */           this.glContext.makeCurrent(paramGLDrawable);
/*     */         }
/* 133 */         this.currentDrawable = paramGLDrawable;
/*     */       }
/*     */     }
/* 136 */     else if (paramGLDrawable != this.currentDrawable) {
/* 137 */       this.glContext.makeCurrent(paramGLDrawable);
/*     */ 
/* 139 */       this.glContext.bindFBO(0L);
/* 140 */       this.currentDrawable = paramGLDrawable;
/*     */     }
/*     */   }
/*     */ 
/*     */   void forceRenderTarget(ES2Graphics paramES2Graphics)
/*     */   {
/* 152 */     updateRenderTarget(paramES2Graphics.getRenderTarget(), paramES2Graphics.getCameraNoClone(), (paramES2Graphics.isDepthTest()) && (paramES2Graphics.isDepthBuffer()));
/*     */   }
/*     */ 
/*     */   int getShaderProgram()
/*     */   {
/* 157 */     return this.glContext.getShaderProgram();
/*     */   }
/*     */ 
/*     */   void setShaderProgram(int paramInt)
/*     */   {
/* 162 */     this.glContext.setShaderProgram(paramInt);
/*     */   }
/*     */ 
/*     */   void updateShaderProgram(int paramInt)
/*     */   {
/* 168 */     if (paramInt != getShaderProgram())
/* 169 */       setShaderProgram(paramInt);
/*     */   }
/*     */ 
/*     */   protected void init()
/*     */   {
/* 175 */     super.init();
/*     */   }
/*     */ 
/*     */   public boolean isEdgeSmoothingSupported(PixelFormat paramPixelFormat)
/*     */   {
/* 180 */     if (ES2Pipeline.isEmbededDevice)
/*     */     {
/* 183 */       return (!paramPixelFormat.isOpaque()) && (paramPixelFormat.isRGB());
/*     */     }
/*     */ 
/* 186 */     return paramPixelFormat.isRGB();
/*     */   }
/*     */ 
/*     */   protected void releaseRenderTarget()
/*     */   {
/* 192 */     this.currentTarget = null;
/* 193 */     super.releaseRenderTarget();
/*     */   }
/*     */ 
/*     */   protected BaseShaderContext.State updateRenderTarget(RenderTarget paramRenderTarget, PrismCameraImpl paramPrismCameraImpl, boolean paramBoolean)
/*     */   {
/* 199 */     this.glContext.bindFBO(paramRenderTarget.getNativeDestHandle());
/*     */ 
/* 201 */     if ((paramBoolean) && ((paramRenderTarget instanceof ES2RTTexture)))
/*     */     {
/* 203 */       ((ES2RTTexture)paramRenderTarget).attachDepthBuffer(this);
/*     */     }
/*     */ 
/* 207 */     int i = paramRenderTarget.getContentX();
/* 208 */     int j = paramRenderTarget.getContentY();
/* 209 */     int k = paramRenderTarget.getContentWidth();
/* 210 */     int m = paramRenderTarget.getContentHeight();
/* 211 */     this.glContext.updateViewportAndDepthTest(i, j, k, m, paramBoolean);
/*     */ 
/* 215 */     projViewTx = paramPrismCameraImpl.getProjViewTx(projViewTx, k, m);
/*     */ 
/* 217 */     this.currentTarget = paramRenderTarget;
/* 218 */     return this.state;
/*     */   }
/*     */ 
/*     */   protected void updateTexture(int paramInt, Texture paramTexture)
/*     */   {
/* 223 */     this.glContext.updateActiveTextureUnit(paramInt);
/*     */ 
/* 225 */     if (paramTexture == null) {
/* 226 */       this.glContext.updateBoundTexture(0);
/*     */     } else {
/* 228 */       int i = (int)paramTexture.getNativeSourceHandle();
/* 229 */       this.glContext.updateBoundTexture(i);
/*     */ 
/* 231 */       ES2Texture localES2Texture = (ES2Texture)paramTexture;
/* 232 */       localES2Texture.updateWrapState();
/* 233 */       localES2Texture.updateFilterState();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateShaderTransform(Shader paramShader, BaseTransform paramBaseTransform)
/*     */   {
/* 239 */     if (paramBaseTransform == null) {
/* 240 */       paramBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*     */     }
/*     */ 
/* 243 */     if ((this.currentTarget instanceof ES2RTTexture))
/* 244 */       scratchTx.set(flipTx);
/*     */     else {
/* 246 */       scratchTx.setIdentity();
/*     */     }
/* 248 */     loadMatrix(scratchTx.mul(projViewTx).mul(paramBaseTransform));
/*     */ 
/* 250 */     ES2Shader localES2Shader = (ES2Shader)paramShader;
/* 251 */     localES2Shader.setMatrix("mvpMatrix", this.rawMatrix, 0, 1);
/*     */ 
/* 253 */     if (localES2Shader.isPixcoordUsed())
/*     */     {
/* 261 */       float f1 = this.currentTarget.getContentX();
/* 262 */       float f2 = this.currentTarget.getContentY();
/*     */       float f3;
/*     */       float f4;
/* 264 */       if ((this.currentTarget instanceof ES2SwapChain))
/*     */       {
/* 266 */         f3 = this.currentTarget.getPhysicalHeight();
/* 267 */         f4 = 1.0F;
/*     */       }
/*     */       else {
/* 270 */         f3 = 0.0F;
/* 271 */         f4 = -1.0F;
/*     */       }
/* 273 */       paramShader.setConstant("jsl_pixCoordOffset", f1, f2, f3, f4);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateClipRect(Rectangle paramRectangle)
/*     */   {
/* 279 */     if ((paramRectangle == null) || (paramRectangle.isEmpty())) {
/* 280 */       this.glContext.scissorTest(false, 0, 0, 0, 0);
/*     */     }
/*     */     else
/*     */     {
/* 287 */       int i = paramRectangle.width;
/* 288 */       int j = paramRectangle.height;
/* 289 */       int k = this.currentTarget.getContentX();
/* 290 */       int m = this.currentTarget.getContentY();
/* 291 */       if ((this.currentTarget instanceof ES2RTTexture)) {
/* 292 */         k += paramRectangle.x;
/* 293 */         m += paramRectangle.y;
/*     */       } else {
/* 295 */         int n = this.currentTarget.getPhysicalHeight();
/* 296 */         k += paramRectangle.x;
/* 297 */         m += n - (paramRectangle.y + j);
/*     */       }
/* 299 */       this.glContext.scissorTest(true, k, m, i, j);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateCompositeMode(CompositeMode paramCompositeMode)
/*     */   {
/* 305 */     switch (1.$SwitchMap$com$sun$prism$CompositeMode[paramCompositeMode.ordinal()]) {
/*     */     case 1:
/* 307 */       this.glContext.blendFunc(0, 0);
/* 308 */       break;
/*     */     case 2:
/* 310 */       this.glContext.blendFunc(1, 0);
/* 311 */       break;
/*     */     case 3:
/* 313 */       this.glContext.blendFunc(1, 7);
/* 314 */       break;
/*     */     default:
/* 316 */       throw new InternalError("Unrecognized composite mode: " + paramCompositeMode);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void loadMatrix(GeneralTransform3D paramGeneralTransform3D) {
/* 321 */     this.rawMatrix.rewind();
/*     */ 
/* 323 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(0));
/* 324 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(4));
/* 325 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(8));
/* 326 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(12));
/* 327 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(1));
/* 328 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(5));
/* 329 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(9));
/* 330 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(13));
/* 331 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(2));
/* 332 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(6));
/* 333 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(10));
/* 334 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(14));
/* 335 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(3));
/* 336 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(7));
/* 337 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(11));
/* 338 */     this.rawMatrix.put((float)paramGeneralTransform3D.get(15));
/* 339 */     this.rawMatrix.rewind();
/*     */   }
/*     */ 
/*     */   static {
/* 343 */     BaseTransform localBaseTransform = Affine2D.getScaleInstance(1.0D, -1.0D);
/* 344 */     flipTx.setIdentity();
/* 345 */     flipTx.mul(localBaseTransform);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.ES2Context
 * JD-Core Version:    0.6.2
 */