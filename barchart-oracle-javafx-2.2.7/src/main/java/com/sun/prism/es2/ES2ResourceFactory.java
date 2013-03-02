/*     */ package com.sun.prism.es2;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.prism.MediaFrame;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.Presentable;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.RenderingContext;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.Texture.Usage;
/*     */ import com.sun.prism.es2.gl.GLContext;
/*     */ import com.sun.prism.es2.gl.GLFactory;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.prism.impl.VertexBuffer;
/*     */ import com.sun.prism.impl.ps.BaseShaderFactory;
/*     */ import com.sun.prism.ps.Shader;
/*     */ import com.sun.prism.ps.ShaderFactory;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ES2ResourceFactory extends BaseShaderFactory
/*     */ {
/*     */   private ES2Context context;
/*     */   private final int maxTextureSize;
/*     */ 
/*     */   ES2ResourceFactory(Screen paramScreen)
/*     */   {
/*  36 */     this.context = new ES2Context(paramScreen, this);
/*  37 */     this.maxTextureSize = computeMaxTextureSize();
/*     */   }
/*     */ 
/*     */   public Presentable createPresentable(View paramView) {
/*  41 */     return new ES2SwapChain(this.context, paramView);
/*     */   }
/*     */ 
/*     */   public Texture createTexture(PixelFormat paramPixelFormat, Texture.Usage paramUsage, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/*  46 */     return ES2Texture.create(this.context, paramPixelFormat, paramInt1, paramInt2, paramBoolean);
/*     */   }
/*     */ 
/*     */   public Texture createTexture(MediaFrame paramMediaFrame) {
/*  50 */     return ES2Texture.create(this.context, paramMediaFrame);
/*     */   }
/*     */ 
/*     */   public RTTexture createRTTexture(int paramInt1, int paramInt2) {
/*  54 */     return ES2RTTexture.create(this.context, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public boolean isFormatSupported(PixelFormat paramPixelFormat) {
/*  58 */     GLFactory localGLFactory = ES2Pipeline.glFactory;
/*  59 */     switch (1.$SwitchMap$com$sun$prism$PixelFormat[paramPixelFormat.ordinal()]) {
/*     */     case 1:
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*  64 */       return true;
/*     */     case 5:
/*     */     case 6:
/*  67 */       return localGLFactory.isGL2();
/*     */     case 7:
/*  69 */       return (localGLFactory.isGL2()) || (localGLFactory.isGLExtensionSupported("GL_OES_texture_float"));
/*     */     case 8:
/*  72 */       return localGLFactory.isGLExtensionSupported("GL_APPLE_ycbcr_422");
/*     */     }
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   private int computeMaxTextureSize()
/*     */   {
/*  79 */     int i = this.context.getGLContext().getMaxTextureSize();
/*  80 */     if (PrismSettings.verbose) {
/*  81 */       System.err.println(new StringBuilder().append("Maximum supported texture size: ").append(i).toString());
/*     */     }
/*  83 */     if (i > PrismSettings.maxTextureSize) {
/*  84 */       i = PrismSettings.maxTextureSize;
/*  85 */       if (PrismSettings.verbose) {
/*  86 */         System.err.println(new StringBuilder().append("Maximum texture size clamped to ").append(i).toString());
/*     */       }
/*     */     }
/*  89 */     return i;
/*     */   }
/*     */ 
/*     */   public int getMaximumTextureSize() {
/*  93 */     return this.maxTextureSize;
/*     */   }
/*     */ 
/*     */   public Shader createShader(InputStream paramInputStream, Map<String, Integer> paramMap1, Map<String, Integer> paramMap2, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 104 */     Map localMap = getVertexAttributes(paramBoolean2, paramInt);
/*     */ 
/* 109 */     String str = createVertexShaderCode(paramBoolean2, paramInt);
/*     */ 
/* 111 */     ES2Shader localES2Shader = ES2Shader.createFromSource(this.context, str, paramInputStream, paramMap1, localMap, paramInt, paramBoolean1);
/*     */ 
/* 115 */     return localES2Shader;
/*     */   }
/*     */ 
/*     */   private static String createVertexShaderCode(boolean paramBoolean, int paramInt)
/*     */   {
/* 120 */     StringBuilder localStringBuilder1 = new StringBuilder();
/* 121 */     StringBuilder localStringBuilder2 = new StringBuilder();
/* 122 */     StringBuilder localStringBuilder3 = new StringBuilder();
/* 123 */     localStringBuilder3.append("void main() {\n");
/*     */ 
/* 125 */     int i = 1;
/* 126 */     if (i != 0) {
/* 127 */       localStringBuilder1.append("attribute vec2 positionAttr;\n");
/* 128 */       localStringBuilder3.append("    vec4 tmp = vec4(positionAttr, 0, 1);\n");
/* 129 */       localStringBuilder3.append("    gl_Position = mvpMatrix * tmp;\n");
/*     */     }
/* 131 */     if (paramBoolean) {
/* 132 */       localStringBuilder1.append("attribute vec4 colorAttr;\n");
/* 133 */       localStringBuilder2.append("varying LOWP vec4 perVertexColor;\n");
/* 134 */       localStringBuilder3.append("    perVertexColor = colorAttr;\n");
/*     */     }
/* 136 */     if (paramInt >= 0) {
/* 137 */       localStringBuilder1.append("attribute vec2 texCoord0Attr;\n");
/* 138 */       localStringBuilder2.append("varying vec2 texCoord0;\n");
/* 139 */       localStringBuilder3.append("    texCoord0 = texCoord0Attr;\n");
/*     */     }
/* 141 */     if (paramInt >= 1) {
/* 142 */       localStringBuilder1.append("attribute vec2 texCoord1Attr;\n");
/* 143 */       localStringBuilder2.append("varying vec2 texCoord1;\n");
/* 144 */       localStringBuilder3.append("    texCoord1 = texCoord1Attr;\n");
/*     */     }
/*     */ 
/* 147 */     localStringBuilder3.append("}\n");
/* 148 */     StringBuilder localStringBuilder4 = new StringBuilder();
/*     */ 
/* 150 */     localStringBuilder4.append("#ifdef GL_ES\n");
/* 151 */     localStringBuilder4.append("#define LOWP lowp\n");
/* 152 */     localStringBuilder4.append("#else\n");
/* 153 */     localStringBuilder4.append("#define LOWP\n");
/* 154 */     localStringBuilder4.append("#endif\n");
/* 155 */     localStringBuilder4.append("uniform mat4 mvpMatrix;\n");
/* 156 */     localStringBuilder4.append(localStringBuilder1);
/* 157 */     localStringBuilder4.append(localStringBuilder2);
/* 158 */     localStringBuilder4.append(localStringBuilder3);
/*     */ 
/* 160 */     return localStringBuilder4.toString();
/*     */   }
/*     */ 
/*     */   private Map<String, Integer> getVertexAttributes(boolean paramBoolean, int paramInt)
/*     */   {
/* 165 */     HashMap localHashMap = new HashMap();
/*     */ 
/* 167 */     int i = 1;
/* 168 */     if (i != 0) {
/* 169 */       localHashMap.put("positionAttr", Integer.valueOf(0));
/*     */     }
/* 171 */     if (paramBoolean) {
/* 172 */       localHashMap.put("colorAttr", Integer.valueOf(1));
/*     */     }
/* 174 */     if (paramInt >= 0) {
/* 175 */       localHashMap.put("texCoord0Attr", Integer.valueOf(2));
/*     */     }
/* 177 */     if (paramInt >= 1) {
/* 178 */       localHashMap.put("texCoord1Attr", Integer.valueOf(3));
/*     */     }
/*     */ 
/* 181 */     return localHashMap;
/*     */   }
/*     */ 
/*     */   public Shader createStockShader(String paramString) {
/* 185 */     if (paramString == null)
/* 186 */       throw new IllegalArgumentException("Shader name must be non-null");
/*     */     try
/*     */     {
/* 189 */       InputStream localInputStream = ES2ResourceFactory.class.getResourceAsStream(new StringBuilder().append("glsl/").append(paramString).append(".frag").toString());
/*     */ 
/* 192 */       Class localClass = Class.forName(new StringBuilder().append("com.sun.prism.shader.").append(paramString).append("_Loader").toString());
/*     */ 
/* 194 */       Method localMethod = localClass.getMethod("loadShader", new Class[] { ShaderFactory.class, InputStream.class });
/*     */ 
/* 197 */       return (Shader)localMethod.invoke(null, new Object[] { this, localInputStream });
/*     */     } catch (Throwable localThrowable) {
/* 199 */       localThrowable.printStackTrace();
/* 200 */     }throw new InternalError(new StringBuilder().append("Error loading stock shader ").append(paramString).toString());
/*     */   }
/*     */ 
/*     */   public VertexBuffer createVertexBuffer(int paramInt)
/*     */   {
/* 205 */     return new VertexBuffer(paramInt);
/*     */   }
/*     */ 
/*     */   public RenderingContext createRenderingContext(View paramView)
/*     */   {
/* 216 */     return new ES2RenderingContext(this.context, paramView);
/*     */   }
/*     */ 
/*     */   public void dispose() {
/* 220 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.ES2ResourceFactory
 * JD-Core Version:    0.6.2
 */