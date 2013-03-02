/*     */ package com.sun.prism.es2.gl;
/*     */ 
/*     */ import com.sun.prism.Texture.WrapMode;
/*     */ import com.sun.prism.es2.ES2Pipeline;
/*     */ import com.sun.prism.paint.Color;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public abstract class GLContext
/*     */ {
/*     */   public static final int GL_ZERO = 0;
/*     */   public static final int GL_ONE = 1;
/*     */   public static final int GL_SRC_COLOR = 2;
/*     */   public static final int GL_ONE_MINUS_SRC_COLOR = 3;
/*     */   public static final int GL_DST_COLOR = 4;
/*     */   public static final int GL_ONE_MINUS_DST_COLOR = 5;
/*     */   public static final int GL_SRC_ALPHA = 6;
/*     */   public static final int GL_ONE_MINUS_SRC_ALPHA = 7;
/*     */   public static final int GL_DST_ALPHA = 8;
/*     */   public static final int GL_ONE_MINUS_DST_ALPHA = 9;
/*     */   public static final int GL_CONSTANT_COLOR = 10;
/*     */   public static final int GL_ONE_MINUS_CONSTANT_COLOR = 11;
/*     */   public static final int GL_CONSTANT_ALPHA = 12;
/*     */   public static final int GL_ONE_MINUS_CONSTANT_ALPHA = 13;
/*     */   public static final int GL_SRC_ALPHA_SATURATE = 14;
/*     */   public static final int GL_FLOAT = 20;
/*     */   public static final int GL_UNSIGNED_BYTE = 21;
/*     */   public static final int GL_UNSIGNED_INT_8_8_8_8_REV = 22;
/*     */   public static final int GL_UNSIGNED_INT_8_8_8_8 = 23;
/*     */   public static final int GL_UNSIGNED_SHORT_8_8_APPLE = 24;
/*     */   public static final int GL_RGBA = 40;
/*     */   public static final int GL_BGRA = 41;
/*     */   public static final int GL_RGB = 42;
/*     */   public static final int GL_LUMINANCE = 43;
/*     */   public static final int GL_ALPHA = 44;
/*     */   public static final int GL_RGBA32F = 45;
/*     */   public static final int GL_YCBCR_422_APPLE = 46;
/*     */   public static final int GL_TEXTURE_2D = 50;
/*     */   public static final int GL_TEXTURE_BINDING_2D = 51;
/*     */   public static final int GL_LINEAR = 52;
/*     */   public static final int GL_UNPACK_ALIGNMENT = 60;
/*     */   public static final int GL_UNPACK_ROW_LENGTH = 61;
/*     */   public static final int GL_UNPACK_SKIP_PIXELS = 62;
/*     */   public static final int GL_UNPACK_SKIP_ROWS = 63;
/*     */   public static final int WRAPMODE_REPEAT = 100;
/*     */   public static final int WRAPMODE_CLAMP_TO_EDGE = 101;
/*     */   public static final int WRAPMODE_CLAMP_TO_BORDER = 102;
/*     */   protected long nativeCtxInfo;
/*  71 */   private int maxTextureSize = -1;
/*     */   private Boolean nonPowTwoExtAvailable;
/*     */   private int activeTexUnit;
/*  76 */   private int[] boundTextures = new int[4];
/*     */   private int shaderProgram;
/*     */ 
/*     */   private static native void nActiveTexture(long paramLong, int paramInt);
/*     */ 
/*     */   private static native void nBindFBO(long paramLong, int paramInt);
/*     */ 
/*     */   private static native void nBindTexture(long paramLong, int paramInt);
/*     */ 
/*     */   private static native void nBlendFunc(int paramInt1, int paramInt2);
/*     */ 
/*     */   private static native void nClearBuffers(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
/*     */ 
/*     */   private static native int nCompileShader(long paramLong, String paramString, boolean paramBoolean);
/*     */ 
/*     */   private static native int nCreateDepthBuffer(long paramLong, int paramInt1, int paramInt2);
/*     */ 
/*     */   private static native int nCreateFBO(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int nCreateProgram(long paramLong, int paramInt1, int paramInt2, int paramInt3, String[] paramArrayOfString, int[] paramArrayOfInt);
/*     */ 
/*     */   private static native int nCreateTexture(long paramLong, int paramInt1, int paramInt2);
/*     */ 
/*     */   private static native void nDeleteRenderBuffer(long paramLong, int paramInt);
/*     */ 
/*     */   private static native void nDeleteFBO(long paramLong, int paramInt);
/*     */ 
/*     */   private static native void nDeleteShader(long paramLong, int paramInt);
/*     */ 
/*     */   private static native void nDeleteTexture(long paramLong, int paramInt);
/*     */ 
/*     */   private static native void nDisposeShaders(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   private static native void nDrawTriangleArrays(int paramInt1, int paramInt2);
/*     */ 
/*     */   private static native void nFinish();
/*     */ 
/*     */   private static native int nGenAndBindTexture();
/*     */ 
/*     */   private static native int nGetFBO();
/*     */ 
/*     */   private static native int nGetMaxTextureSize();
/*     */ 
/*     */   private static native int nGetUniformLocation(long paramLong, int paramInt, String paramString);
/*     */ 
/*     */   private static native int nOneValueGetIntegerv(int paramInt);
/*     */ 
/*     */   private static native void nPixelStorei(int paramInt1, int paramInt2);
/*     */ 
/*     */   private static native boolean nReadPixelsByte(long paramLong, int paramInt1, Buffer paramBuffer, byte[] paramArrayOfByte, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */ 
/*     */   private static native boolean nReadPixelsInt(long paramLong, int paramInt1, Buffer paramBuffer, int[] paramArrayOfInt, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */ 
/*     */   private static native void nScissorTest(long paramLong, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   private static native void nTexParamsMinMax(int paramInt);
/*     */ 
/*     */   private static native void nTexImage2D0(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Object paramObject, int paramInt9);
/*     */ 
/*     */   private static native void nTexImage2D1(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Object paramObject, int paramInt9);
/*     */ 
/*     */   private static native void nTexSubImage2D0(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Object paramObject, int paramInt9);
/*     */ 
/*     */   private static native void nTexSubImage2D1(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Object paramObject, int paramInt9);
/*     */ 
/*     */   private static native void nUpdateViewportAndDepthTest(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean);
/*     */ 
/*     */   private static native void nUniform1f(long paramLong, int paramInt, float paramFloat);
/*     */ 
/*     */   private static native void nUniform2f(long paramLong, int paramInt, float paramFloat1, float paramFloat2);
/*     */ 
/*     */   private static native void nUniform3f(long paramLong, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3);
/*     */ 
/*     */   private static native void nUniform4f(long paramLong, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*     */ 
/*     */   private static native void nUniform4fv0(long paramLong, int paramInt1, int paramInt2, Object paramObject, int paramInt3);
/*     */ 
/*     */   private static native void nUniform4fv1(long paramLong, int paramInt1, int paramInt2, Object paramObject, int paramInt3);
/*     */ 
/*     */   private static native void nUniform1i(long paramLong, int paramInt1, int paramInt2);
/*     */ 
/*     */   private static native void nUniform2i(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   private static native void nUniform3i(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   private static native void nUniform4i(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */ 
/*     */   private static native void nUniform4iv0(long paramLong, int paramInt1, int paramInt2, Object paramObject, int paramInt3);
/*     */ 
/*     */   private static native void nUniform4iv1(long paramLong, int paramInt1, int paramInt2, Object paramObject, int paramInt3);
/*     */ 
/*     */   private static native void nUniformMatrix4fv0(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean, Object paramObject, int paramInt3);
/*     */ 
/*     */   private static native void nUniformMatrix4fv1(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean, Object paramObject, int paramInt3);
/*     */ 
/*     */   private static native void nUpdateFilterState(long paramLong, int paramInt, boolean paramBoolean);
/*     */ 
/*     */   private static native void nUpdateWrapState(long paramLong, int paramInt1, int paramInt2);
/*     */ 
/*     */   private static native void nUseProgram(long paramLong, int paramInt);
/*     */ 
/*     */   private static native void nEnableVertexAttributes(long paramLong);
/*     */ 
/*     */   private static native void nDrawIndexedQuads(long paramLong, int paramInt, float[] paramArrayOfFloat, byte[] paramArrayOfByte);
/*     */ 
/*     */   private static native void nDrawTriangleList(long paramLong, int paramInt, float[] paramArrayOfFloat, byte[] paramArrayOfByte);
/*     */ 
/*     */   private static native int nCreateIndexBuffer16(long paramLong, short[] paramArrayOfShort, int paramInt);
/*     */ 
/*     */   private static native void nSetIndexBuffer(long paramLong, int paramInt);
/*     */ 
/*     */   public void activeTexture(int paramInt)
/*     */   {
/* 173 */     nActiveTexture(this.nativeCtxInfo, paramInt);
/*     */   }
/*     */ 
/*     */   public void bindFBO(long paramLong) {
/* 177 */     nBindFBO(this.nativeCtxInfo, (int)paramLong);
/*     */   }
/*     */ 
/*     */   public void bindTexture(int paramInt) {
/* 181 */     nBindTexture(this.nativeCtxInfo, paramInt);
/*     */   }
/*     */ 
/*     */   public void blendFunc(int paramInt1, int paramInt2) {
/* 185 */     nBlendFunc(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public boolean canCreateNonPowTwoTextures() {
/* 189 */     if (this.nonPowTwoExtAvailable == null)
/*     */     {
/* 194 */       this.nonPowTwoExtAvailable = Boolean.valueOf(ES2Pipeline.glFactory.isGLExtensionSupported("GL_ARB_texture_non_power_of_two"));
/*     */     }
/*     */ 
/* 197 */     return this.nonPowTwoExtAvailable.booleanValue();
/*     */   }
/*     */ 
/*     */   public void clearBuffers(Color paramColor, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*     */   {
/* 202 */     float f1 = paramColor.getRedPremult();
/* 203 */     float f2 = paramColor.getGreenPremult();
/* 204 */     float f3 = paramColor.getBluePremult();
/* 205 */     float f4 = paramColor.getAlpha();
/* 206 */     nClearBuffers(this.nativeCtxInfo, f1, f2, f3, f4, paramBoolean1, paramBoolean2, paramBoolean3);
/*     */   }
/*     */ 
/*     */   public int compileShader(String paramString, boolean paramBoolean)
/*     */   {
/* 215 */     return nCompileShader(this.nativeCtxInfo, paramString, paramBoolean);
/*     */   }
/*     */ 
/*     */   public int createDepthBuffer(int paramInt1, int paramInt2) {
/* 219 */     return nCreateDepthBuffer(this.nativeCtxInfo, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public int createFBO(int paramInt1, int paramInt2, int paramInt3) {
/* 223 */     return nCreateFBO(this.nativeCtxInfo, paramInt1);
/*     */   }
/*     */ 
/*     */   public int createProgram(int paramInt1, int paramInt2, String[] paramArrayOfString, int[] paramArrayOfInt)
/*     */   {
/* 234 */     if ((paramInt1 == 0) || (paramInt2 == 0)) {
/* 235 */       System.err.println("Both vertexShader and fragmentShader must be specified");
/*     */     }
/* 237 */     if (paramArrayOfString.length != paramArrayOfInt.length) {
/* 238 */       System.err.println("attrs.length must be equal to index.length");
/*     */     }
/* 240 */     return nCreateProgram(this.nativeCtxInfo, paramInt1, paramInt2, paramArrayOfString.length, paramArrayOfString, paramArrayOfInt);
/*     */   }
/*     */ 
/*     */   public int createTexture(int paramInt1, int paramInt2)
/*     */   {
/* 245 */     return nCreateTexture(this.nativeCtxInfo, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void deleteRenderBuffer(int paramInt) {
/* 249 */     nDeleteRenderBuffer(this.nativeCtxInfo, paramInt);
/*     */   }
/*     */ 
/*     */   public void deleteFBO(int paramInt) {
/* 253 */     nDeleteFBO(this.nativeCtxInfo, paramInt);
/*     */   }
/*     */ 
/*     */   public void deleteShader(int paramInt) {
/* 257 */     nDeleteShader(this.nativeCtxInfo, paramInt);
/*     */   }
/*     */ 
/*     */   public void deleteTexture(int paramInt) {
/* 261 */     nDeleteTexture(this.nativeCtxInfo, paramInt);
/*     */   }
/*     */ 
/*     */   public void disposeShaders(int paramInt1, int paramInt2, int paramInt3) {
/* 265 */     nDisposeShaders(this.nativeCtxInfo, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   public void drawTriangleArrays(int paramInt1, int paramInt2) {
/* 269 */     nDrawTriangleArrays(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void finish() {
/* 273 */     nFinish();
/*     */   }
/*     */ 
/*     */   public int genAndBindTexture() {
/* 277 */     int i = nGenAndBindTexture();
/* 278 */     this.boundTextures[this.activeTexUnit] = i;
/* 279 */     return i;
/*     */   }
/*     */ 
/*     */   public int getBoundFBO() {
/* 283 */     return nGetFBO();
/*     */   }
/*     */ 
/*     */   public int getBoundTextureID()
/*     */   {
/* 288 */     return nOneValueGetIntegerv(51);
/*     */   }
/*     */ 
/*     */   public long getNativeCtxInfo() {
/* 292 */     return this.nativeCtxInfo;
/*     */   }
/*     */ 
/*     */   public abstract long getNativeHandle();
/*     */ 
/*     */   public int getActiveTextureUnit()
/*     */   {
/* 299 */     return this.activeTexUnit;
/*     */   }
/*     */ 
/*     */   public void setActiveTextureUnit(int paramInt)
/*     */   {
/* 304 */     activeTexture(paramInt);
/* 305 */     this.activeTexUnit = paramInt;
/*     */   }
/*     */ 
/*     */   public void updateActiveTextureUnit(int paramInt)
/*     */   {
/* 311 */     if (paramInt != getActiveTextureUnit())
/* 312 */       setActiveTextureUnit(paramInt);
/*     */   }
/*     */ 
/*     */   public int getBoundTexture()
/*     */   {
/* 317 */     return this.boundTextures[this.activeTexUnit];
/*     */   }
/*     */ 
/*     */   public int getBoundTexture(int paramInt) {
/* 321 */     return this.boundTextures[paramInt];
/*     */   }
/*     */ 
/*     */   public int getNumBoundTexture() {
/* 325 */     return this.boundTextures.length;
/*     */   }
/*     */ 
/*     */   public void setBoundTexture(int paramInt)
/*     */   {
/* 330 */     bindTexture(paramInt);
/* 331 */     this.boundTextures[this.activeTexUnit] = paramInt;
/*     */   }
/*     */ 
/*     */   public void updateBoundTexture(int paramInt)
/*     */   {
/* 337 */     if (paramInt != getBoundTexture())
/* 338 */       setBoundTexture(paramInt);
/*     */   }
/*     */ 
/*     */   public int getMaxTextureSize()
/*     */   {
/* 344 */     if (this.maxTextureSize > -1) {
/* 345 */       return this.maxTextureSize;
/*     */     }
/* 347 */     return this.maxTextureSize = nGetMaxTextureSize();
/*     */   }
/*     */ 
/*     */   public int getShaderProgram() {
/* 351 */     return this.shaderProgram;
/*     */   }
/*     */ 
/*     */   public int getUniformLocation(int paramInt, String paramString) {
/* 355 */     return nGetUniformLocation(this.nativeCtxInfo, paramInt, paramString);
/*     */   }
/*     */ 
/*     */   public boolean isShaderCompilerSupported()
/*     */   {
/* 360 */     return true;
/*     */   }
/*     */ 
/*     */   public abstract void makeCurrent(GLDrawable paramGLDrawable);
/*     */ 
/*     */   public void pixelStorei(int paramInt1, int paramInt2)
/*     */   {
/* 367 */     nPixelStorei(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public boolean readPixels(Buffer paramBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 371 */     boolean bool = false;
/*     */     Object localObject;
/*     */     byte[] arrayOfByte;
/*     */     int i;
/* 372 */     if ((paramBuffer instanceof ByteBuffer)) {
/* 373 */       localObject = (ByteBuffer)paramBuffer;
/* 374 */       arrayOfByte = ((ByteBuffer)localObject).hasArray() ? ((ByteBuffer)localObject).array() : null;
/* 375 */       i = ((ByteBuffer)localObject).capacity();
/* 376 */       bool = nReadPixelsByte(this.nativeCtxInfo, i, paramBuffer, arrayOfByte, paramInt1, paramInt2, paramInt3, paramInt4);
/* 377 */     } else if ((paramBuffer instanceof IntBuffer)) {
/* 378 */       localObject = (IntBuffer)paramBuffer;
/* 379 */       arrayOfByte = ((IntBuffer)localObject).hasArray() ? ((IntBuffer)localObject).array() : null;
/* 380 */       i = ((IntBuffer)localObject).capacity() * 4;
/*     */ 
/* 385 */       bool = nReadPixelsInt(this.nativeCtxInfo, i, paramBuffer, arrayOfByte, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } else {
/* 387 */       throw new IllegalArgumentException("readPixel: pixel's buffer type is not supported: " + paramBuffer);
/*     */     }
/*     */ 
/* 390 */     return bool;
/*     */   }
/*     */ 
/*     */   public void scissorTest(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 394 */     nScissorTest(this.nativeCtxInfo, paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   public void setShaderProgram(int paramInt) {
/* 398 */     this.shaderProgram = paramInt;
/* 399 */     nUseProgram(this.nativeCtxInfo, paramInt);
/*     */   }
/*     */ 
/*     */   public void texParamsMinMax(int paramInt) {
/* 403 */     nTexParamsMinMax(paramInt);
/*     */   }
/*     */ 
/*     */   public void texImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Buffer paramBuffer)
/*     */   {
/* 409 */     boolean bool = BufferFactory.isDirect(paramBuffer);
/* 410 */     if (bool) {
/* 411 */       nTexImage2D0(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBuffer, BufferFactory.getDirectBufferByteOffset(paramBuffer));
/*     */     }
/*     */     else
/* 414 */       nTexImage2D1(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, BufferFactory.getArray(paramBuffer), BufferFactory.getIndirectBufferByteOffset(paramBuffer));
/*     */   }
/*     */ 
/*     */   public void texSubImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Buffer paramBuffer)
/*     */   {
/* 423 */     boolean bool = BufferFactory.isDirect(paramBuffer);
/* 424 */     if (bool) {
/* 425 */       nTexSubImage2D0(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBuffer, BufferFactory.getDirectBufferByteOffset(paramBuffer));
/*     */     }
/*     */     else
/*     */     {
/* 429 */       nTexSubImage2D1(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, BufferFactory.getArray(paramBuffer), BufferFactory.getIndirectBufferByteOffset(paramBuffer));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateViewportAndDepthTest(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
/*     */   {
/* 437 */     nUpdateViewportAndDepthTest(this.nativeCtxInfo, paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
/*     */   }
/*     */ 
/*     */   public void updateFilterState(int paramInt, boolean paramBoolean) {
/* 441 */     nUpdateFilterState(this.nativeCtxInfo, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */   public void updateWrapState(int paramInt, Texture.WrapMode paramWrapMode)
/*     */   {
/*     */     int i;
/* 446 */     switch (1.$SwitchMap$com$sun$prism$Texture$WrapMode[paramWrapMode.ordinal()]) {
/*     */     case 1:
/* 448 */       i = 100;
/* 449 */       break;
/*     */     case 2:
/* 451 */       i = 101;
/* 452 */       break;
/*     */     case 3:
/*     */     default:
/* 457 */       if (!ES2Pipeline.glFactory.isGL2())
/* 458 */         i = 101;
/*     */       else {
/* 460 */         i = 102;
/*     */       }
/*     */       break;
/*     */     }
/* 464 */     nUpdateWrapState(this.nativeCtxInfo, paramInt, i);
/*     */   }
/*     */ 
/*     */   public void uniform1f(int paramInt, float paramFloat) {
/* 468 */     nUniform1f(this.nativeCtxInfo, paramInt, paramFloat);
/*     */   }
/*     */ 
/*     */   public void uniform2f(int paramInt, float paramFloat1, float paramFloat2) {
/* 472 */     nUniform2f(this.nativeCtxInfo, paramInt, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   public void uniform3f(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 476 */     nUniform3f(this.nativeCtxInfo, paramInt, paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ 
/*     */   public void uniform4f(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 480 */     nUniform4f(this.nativeCtxInfo, paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public void uniform4fv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer) {
/* 484 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 485 */     if (bool) {
/* 486 */       nUniform4fv0(this.nativeCtxInfo, paramInt1, paramInt2, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer));
/*     */     }
/*     */     else
/* 489 */       nUniform4fv1(this.nativeCtxInfo, paramInt1, paramInt2, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer));
/*     */   }
/*     */ 
/*     */   public void uniform1i(int paramInt1, int paramInt2)
/*     */   {
/* 495 */     nUniform1i(this.nativeCtxInfo, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void uniform2i(int paramInt1, int paramInt2, int paramInt3) {
/* 499 */     nUniform2i(this.nativeCtxInfo, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   public void uniform3i(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 503 */     nUniform3i(this.nativeCtxInfo, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   public void uniform4i(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 507 */     nUniform4i(this.nativeCtxInfo, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */   }
/*     */ 
/*     */   public void uniform4iv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer) {
/* 511 */     boolean bool = BufferFactory.isDirect(paramIntBuffer);
/* 512 */     if (bool) {
/* 513 */       nUniform4iv0(this.nativeCtxInfo, paramInt1, paramInt2, paramIntBuffer, BufferFactory.getDirectBufferByteOffset(paramIntBuffer));
/*     */     }
/*     */     else
/* 516 */       nUniform4iv1(this.nativeCtxInfo, paramInt1, paramInt2, BufferFactory.getArray(paramIntBuffer), BufferFactory.getIndirectBufferByteOffset(paramIntBuffer));
/*     */   }
/*     */ 
/*     */   public void uniformMatrix4fv(int paramInt1, int paramInt2, boolean paramBoolean, FloatBuffer paramFloatBuffer)
/*     */   {
/* 523 */     boolean bool = BufferFactory.isDirect(paramFloatBuffer);
/* 524 */     if (bool) {
/* 525 */       nUniformMatrix4fv0(this.nativeCtxInfo, paramInt1, paramInt2, paramBoolean, paramFloatBuffer, BufferFactory.getDirectBufferByteOffset(paramFloatBuffer));
/*     */     }
/*     */     else
/* 528 */       nUniformMatrix4fv1(this.nativeCtxInfo, paramInt1, paramInt2, paramBoolean, BufferFactory.getArray(paramFloatBuffer), BufferFactory.getIndirectBufferByteOffset(paramFloatBuffer));
/*     */   }
/*     */ 
/*     */   public void updateShaderProgram(int paramInt)
/*     */   {
/* 536 */     if (paramInt != this.shaderProgram)
/* 537 */       setShaderProgram(paramInt);
/*     */   }
/*     */ 
/*     */   public void enableVertexAttributes()
/*     */   {
/* 542 */     nEnableVertexAttributes(this.nativeCtxInfo);
/*     */   }
/*     */ 
/*     */   public void drawIndexedQuads(float[] paramArrayOfFloat, byte[] paramArrayOfByte, int paramInt) {
/* 546 */     nDrawIndexedQuads(this.nativeCtxInfo, paramInt, paramArrayOfFloat, paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public void drawTriangleList(int paramInt, float[] paramArrayOfFloat, byte[] paramArrayOfByte) {
/* 550 */     nDrawTriangleList(this.nativeCtxInfo, paramInt, paramArrayOfFloat, paramArrayOfByte);
/*     */   }
/*     */ 
/*     */   public int createIndexBuffer16(short[] paramArrayOfShort) {
/* 554 */     return nCreateIndexBuffer16(this.nativeCtxInfo, paramArrayOfShort, paramArrayOfShort.length);
/*     */   }
/*     */ 
/*     */   public void setIndexBuffer(int paramInt) {
/* 558 */     nSetIndexBuffer(this.nativeCtxInfo, paramInt);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.gl.GLContext
 * JD-Core Version:    0.6.2
 */