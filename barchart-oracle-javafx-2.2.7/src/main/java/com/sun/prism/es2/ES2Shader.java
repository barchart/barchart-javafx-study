/*     */ package com.sun.prism.es2;
/*     */ 
/*     */ import com.sun.prism.es2.gl.GLContext;
/*     */ import com.sun.prism.impl.BaseGraphicsResource;
/*     */ import com.sun.prism.impl.Disposer.Record;
/*     */ import com.sun.prism.ps.Shader;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ES2Shader extends BaseGraphicsResource
/*     */   implements Shader
/*     */ {
/*     */   private int programID;
/*     */   private final ES2Context context;
/*  79 */   private final Map<String, Uniform> uniforms = new HashMap();
/*     */   private final int maxTexCoordIndex;
/*     */   private final boolean isPixcoordUsed;
/*     */   private boolean valid;
/*     */ 
/*     */   private ES2Shader(ES2Context paramES2Context, int paramInt1, int paramInt2, int paramInt3, Map<String, Integer> paramMap, int paramInt4, boolean paramBoolean)
/*     */     throws RuntimeException
/*     */   {
/*  89 */     super(new ES2ShaderDisposerRecord(paramES2Context, paramInt2, paramInt3, paramInt1, null));
/*     */ 
/*  93 */     this.context = paramES2Context;
/*  94 */     this.programID = paramInt1;
/*  95 */     this.maxTexCoordIndex = paramInt4;
/*  96 */     this.isPixcoordUsed = paramBoolean;
/*  97 */     this.valid = (paramInt1 != 0);
/*     */ 
/*  99 */     if ((this.valid) && (paramMap != null))
/*     */     {
/* 102 */       int i = paramES2Context.getShaderProgram();
/* 103 */       paramES2Context.setShaderProgram(paramInt1);
/* 104 */       for (String str : paramMap.keySet()) {
/* 105 */         setConstant(str, ((Integer)paramMap.get(str)).intValue());
/*     */       }
/* 107 */       paramES2Context.setShaderProgram(i);
/*     */     }
/*     */   }
/*     */ 
/*     */   static ES2Shader createFromSource(ES2Context paramES2Context, String paramString, InputStream paramInputStream, Map<String, Integer> paramMap1, Map<String, Integer> paramMap2, int paramInt, boolean paramBoolean)
/*     */   {
/* 117 */     GLContext localGLContext = paramES2Context.getGLContext();
/* 118 */     if (!localGLContext.isShaderCompilerSupported()) {
/* 119 */       throw new RuntimeException("Shader compiler not available on this device");
/*     */     }
/*     */ 
/* 122 */     String str1 = paramString;
/* 123 */     String str2 = readStreamIntoString(paramInputStream);
/* 124 */     if ((str1 == null) || (str2 == null)) {
/* 125 */       throw new RuntimeException("Both vertexShaderSource and fragmentShaderSource must be specified");
/*     */     }
/*     */ 
/* 130 */     int i = localGLContext.compileShader(str1, true);
/* 131 */     if (i == 0) {
/* 132 */       throw new RuntimeException("Error creating vertex shader");
/*     */     }
/*     */ 
/* 135 */     int j = localGLContext.compileShader(str2, false);
/* 136 */     if (j == 0) {
/* 137 */       localGLContext.deleteShader(i);
/* 138 */       throw new RuntimeException("Error creating fragment shader");
/*     */     }
/*     */ 
/* 141 */     String[] arrayOfString = paramMap2 == null ? null : new String[paramMap2.size()];
/* 142 */     int[] arrayOfInt = null;
/* 143 */     if (arrayOfString != null) {
/* 144 */       arrayOfInt = new int[arrayOfString.length];
/* 145 */       k = 0;
/* 146 */       for (String str3 : paramMap2.keySet()) {
/* 147 */         arrayOfString[k] = str3;
/* 148 */         arrayOfInt[k] = ((Integer)paramMap2.get(str3)).intValue();
/* 149 */         k++;
/*     */       }
/*     */     }
/* 152 */     int k = localGLContext.createProgram(i, j, arrayOfString, arrayOfInt);
/*     */ 
/* 154 */     if (k == 0)
/*     */     {
/* 157 */       throw new RuntimeException("Error creating shader program");
/*     */     }
/*     */ 
/* 160 */     return new ES2Shader(paramES2Context, k, i, j, paramMap1, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */   private static String readStreamIntoString(InputStream paramInputStream)
/*     */   {
/* 166 */     StringBuffer localStringBuffer = new StringBuffer(1024);
/* 167 */     BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
/*     */     try {
/* 169 */       char[] arrayOfChar = new char[1024];
/* 170 */       int i = 0;
/* 171 */       while ((i = localBufferedReader.read(arrayOfChar)) > -1)
/* 172 */         localStringBuffer.append(String.valueOf(arrayOfChar, 0, i));
/*     */     }
/*     */     catch (IOException localIOException2) {
/* 175 */       throw new RuntimeException("Error reading shader stream");
/*     */     } finally {
/*     */       try {
/* 178 */         localBufferedReader.close();
/*     */       } catch (IOException localIOException3) {
/* 180 */         throw new RuntimeException("Error closing reader");
/*     */       }
/*     */     }
/* 183 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public int getProgramObject()
/*     */   {
/* 194 */     return this.programID;
/*     */   }
/*     */ 
/*     */   public int getMaxTexCoordIndex()
/*     */   {
/* 203 */     return this.maxTexCoordIndex;
/*     */   }
/*     */ 
/*     */   public boolean isPixcoordUsed()
/*     */   {
/* 213 */     return this.isPixcoordUsed;
/*     */   }
/*     */ 
/*     */   private Uniform getUniform(String paramString) {
/* 217 */     Uniform localUniform = (Uniform)this.uniforms.get(paramString);
/* 218 */     if (localUniform == null)
/*     */     {
/* 220 */       int i = this.context.getGLContext().getUniformLocation(this.programID, paramString);
/* 221 */       localUniform = new Uniform(null);
/* 222 */       localUniform.location = i;
/* 223 */       this.uniforms.put(paramString, localUniform);
/*     */     }
/* 225 */     return localUniform;
/*     */   }
/*     */ 
/*     */   public void enable()
/*     */     throws RuntimeException
/*     */   {
/* 235 */     this.context.updateShaderProgram(this.programID);
/*     */   }
/*     */ 
/*     */   public void disable()
/*     */     throws RuntimeException
/*     */   {
/* 246 */     this.context.updateShaderProgram(0);
/*     */   }
/*     */ 
/*     */   public boolean isValid() {
/* 250 */     return this.valid;
/*     */   }
/*     */ 
/*     */   public void setConstant(String paramString, int paramInt)
/*     */     throws RuntimeException
/*     */   {
/* 264 */     Uniform localUniform = getUniform(paramString);
/* 265 */     if (localUniform.location == -1) {
/* 266 */       return;
/*     */     }
/* 268 */     if (localUniform.values == null) {
/* 269 */       localUniform.values = new int[1];
/*     */     }
/* 271 */     int[] arrayOfInt = (int[])localUniform.values;
/* 272 */     if (arrayOfInt[0] != paramInt) {
/* 273 */       arrayOfInt[0] = paramInt;
/* 274 */       this.context.getGLContext().uniform1i(localUniform.location, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setConstant(String paramString, int paramInt1, int paramInt2)
/*     */     throws RuntimeException
/*     */   {
/* 290 */     Uniform localUniform = getUniform(paramString);
/* 291 */     if (localUniform.location == -1) {
/* 292 */       return;
/*     */     }
/* 294 */     if (localUniform.values == null) {
/* 295 */       localUniform.values = new int[2];
/*     */     }
/* 297 */     int[] arrayOfInt = (int[])localUniform.values;
/* 298 */     if ((arrayOfInt[0] != paramInt1) || (arrayOfInt[1] != paramInt2)) {
/* 299 */       arrayOfInt[0] = paramInt1;
/* 300 */       arrayOfInt[1] = paramInt2;
/* 301 */       this.context.getGLContext().uniform2i(localUniform.location, paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setConstant(String paramString, int paramInt1, int paramInt2, int paramInt3)
/*     */     throws RuntimeException
/*     */   {
/* 318 */     Uniform localUniform = getUniform(paramString);
/* 319 */     if (localUniform.location == -1) {
/* 320 */       return;
/*     */     }
/* 322 */     if (localUniform.values == null) {
/* 323 */       localUniform.values = new int[3];
/*     */     }
/* 325 */     int[] arrayOfInt = (int[])localUniform.values;
/* 326 */     if ((arrayOfInt[0] != paramInt1) || (arrayOfInt[1] != paramInt2) || (arrayOfInt[2] != paramInt3)) {
/* 327 */       arrayOfInt[0] = paramInt1;
/* 328 */       arrayOfInt[1] = paramInt2;
/* 329 */       arrayOfInt[2] = paramInt3;
/* 330 */       this.context.getGLContext().uniform3i(localUniform.location, paramInt1, paramInt2, paramInt3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setConstant(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */     throws RuntimeException
/*     */   {
/* 348 */     Uniform localUniform = getUniform(paramString);
/* 349 */     if (localUniform.location == -1) {
/* 350 */       return;
/*     */     }
/* 352 */     if (localUniform.values == null) {
/* 353 */       localUniform.values = new int[4];
/*     */     }
/* 355 */     int[] arrayOfInt = (int[])localUniform.values;
/* 356 */     if ((arrayOfInt[0] != paramInt1) || (arrayOfInt[1] != paramInt2) || (arrayOfInt[2] != paramInt3) || (arrayOfInt[3] != paramInt4)) {
/* 357 */       arrayOfInt[0] = paramInt1;
/* 358 */       arrayOfInt[1] = paramInt2;
/* 359 */       arrayOfInt[2] = paramInt3;
/* 360 */       arrayOfInt[3] = paramInt4;
/* 361 */       this.context.getGLContext().uniform4i(localUniform.location, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setConstant(String paramString, float paramFloat)
/*     */     throws RuntimeException
/*     */   {
/* 376 */     Uniform localUniform = getUniform(paramString);
/* 377 */     if (localUniform.location == -1) {
/* 378 */       return;
/*     */     }
/* 380 */     if (localUniform.values == null) {
/* 381 */       localUniform.values = new float[1];
/*     */     }
/* 383 */     float[] arrayOfFloat = (float[])localUniform.values;
/* 384 */     if (arrayOfFloat[0] != paramFloat) {
/* 385 */       arrayOfFloat[0] = paramFloat;
/* 386 */       this.context.getGLContext().uniform1f(localUniform.location, paramFloat);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setConstant(String paramString, float paramFloat1, float paramFloat2)
/*     */     throws RuntimeException
/*     */   {
/* 402 */     Uniform localUniform = getUniform(paramString);
/* 403 */     if (localUniform.location == -1) {
/* 404 */       return;
/*     */     }
/* 406 */     if (localUniform.values == null) {
/* 407 */       localUniform.values = new float[2];
/*     */     }
/* 409 */     float[] arrayOfFloat = (float[])localUniform.values;
/* 410 */     if ((arrayOfFloat[0] != paramFloat1) || (arrayOfFloat[1] != paramFloat2)) {
/* 411 */       arrayOfFloat[0] = paramFloat1;
/* 412 */       arrayOfFloat[1] = paramFloat2;
/* 413 */       this.context.getGLContext().uniform2f(localUniform.location, paramFloat1, paramFloat2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setConstant(String paramString, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */     throws RuntimeException
/*     */   {
/* 430 */     Uniform localUniform = getUniform(paramString);
/* 431 */     if (localUniform.location == -1) {
/* 432 */       return;
/*     */     }
/* 434 */     if (localUniform.values == null) {
/* 435 */       localUniform.values = new float[3];
/*     */     }
/* 437 */     float[] arrayOfFloat = (float[])localUniform.values;
/* 438 */     if ((arrayOfFloat[0] != paramFloat1) || (arrayOfFloat[1] != paramFloat2) || (arrayOfFloat[2] != paramFloat3)) {
/* 439 */       arrayOfFloat[0] = paramFloat1;
/* 440 */       arrayOfFloat[1] = paramFloat2;
/* 441 */       arrayOfFloat[2] = paramFloat3;
/* 442 */       this.context.getGLContext().uniform3f(localUniform.location, paramFloat1, paramFloat2, paramFloat3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setConstant(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     throws RuntimeException
/*     */   {
/* 460 */     Uniform localUniform = getUniform(paramString);
/* 461 */     if (localUniform.location == -1) {
/* 462 */       return;
/*     */     }
/* 464 */     if (localUniform.values == null) {
/* 465 */       localUniform.values = new float[4];
/*     */     }
/* 467 */     float[] arrayOfFloat = (float[])localUniform.values;
/* 468 */     if ((arrayOfFloat[0] != paramFloat1) || (arrayOfFloat[1] != paramFloat2) || (arrayOfFloat[2] != paramFloat3) || (arrayOfFloat[3] != paramFloat4)) {
/* 469 */       arrayOfFloat[0] = paramFloat1;
/* 470 */       arrayOfFloat[1] = paramFloat2;
/* 471 */       arrayOfFloat[2] = paramFloat3;
/* 472 */       arrayOfFloat[3] = paramFloat4;
/* 473 */       this.context.getGLContext().uniform4f(localUniform.location, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setConstants(String paramString, IntBuffer paramIntBuffer, int paramInt1, int paramInt2)
/*     */     throws RuntimeException
/*     */   {
/* 491 */     int i = getUniform(paramString).location;
/* 492 */     if (i == -1) {
/* 493 */       return;
/*     */     }
/* 495 */     this.context.getGLContext().uniform4iv(i, paramInt2, paramIntBuffer);
/*     */   }
/*     */ 
/*     */   public void setConstants(String paramString, FloatBuffer paramFloatBuffer, int paramInt1, int paramInt2)
/*     */     throws RuntimeException
/*     */   {
/* 512 */     int i = getUniform(paramString).location;
/* 513 */     if (i == -1) {
/* 514 */       return;
/*     */     }
/* 516 */     this.context.getGLContext().uniform4fv(i, paramInt2, paramFloatBuffer);
/*     */   }
/*     */ 
/*     */   public void setMatrix(String paramString, FloatBuffer paramFloatBuffer, int paramInt1, int paramInt2)
/*     */     throws RuntimeException
/*     */   {
/* 532 */     int i = getUniform(paramString).location;
/* 533 */     if (i == -1) {
/* 534 */       return;
/*     */     }
/* 536 */     this.context.getGLContext().uniformMatrix4fv(i, paramInt2, false, paramFloatBuffer);
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */     throws RuntimeException
/*     */   {
/* 546 */     if (this.programID != 0) {
/* 547 */       this.disposerRecord.dispose();
/* 548 */       this.programID = 0;
/*     */     }
/* 550 */     this.valid = false;
/*     */   }
/*     */ 
/*     */   private static class ES2ShaderDisposerRecord implements Disposer.Record
/*     */   {
/*     */     private final ES2Context context;
/*     */     private int vertexShaderID;
/*     */     private int fragmentShaderID;
/*     */     private int programID;
/*     */ 
/*     */     private ES2ShaderDisposerRecord(ES2Context paramES2Context, int paramInt1, int paramInt2, int paramInt3) {
/* 564 */       this.context = paramES2Context;
/* 565 */       this.vertexShaderID = paramInt1;
/* 566 */       this.fragmentShaderID = paramInt2;
/* 567 */       this.programID = paramInt3;
/*     */     }
/*     */ 
/*     */     public void dispose() {
/* 571 */       if (this.programID != 0) {
/* 572 */         this.context.getGLContext().disposeShaders(this.programID, this.vertexShaderID, this.fragmentShaderID);
/*     */ 
/* 574 */         this.programID = (this.vertexShaderID = this.fragmentShaderID = 0);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Uniform
/*     */   {
/*     */     private int location;
/*     */     private Object values;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.ES2Shader
 * JD-Core Version:    0.6.2
 */