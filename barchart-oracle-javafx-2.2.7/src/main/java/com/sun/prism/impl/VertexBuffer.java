/*     */ package com.sun.prism.impl;
/*     */ 
/*     */ import com.sun.prism.paint.Color;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class VertexBuffer
/*     */ {
/*     */   protected static final int VERTS_PER_QUAD = 4;
/*     */   protected static final int FLOATS_PER_TC = 2;
/*     */   protected static final int FLOATS_PER_VC = 3;
/*     */   protected static final int FLOATS_PER_VERT = 7;
/*     */   protected static final int BYTES_PER_VERT = 4;
/*     */   protected int capacity;
/*     */   protected int index;
/*     */   protected byte r;
/*     */   protected byte g;
/*     */   protected byte b;
/*     */   protected byte a;
/*     */   protected byte[] colorArray;
/*     */   protected float[] coordArray;
/*     */ 
/*     */   public VertexBuffer(int paramInt)
/*     */   {
/*  27 */     this.capacity = (paramInt * 4);
/*  28 */     this.index = 0;
/*     */ 
/*  30 */     this.colorArray = new byte[this.capacity * 4];
/*  31 */     this.coordArray = new float[this.capacity * 7];
/*     */   }
/*     */ 
/*     */   protected void drawQuads(int paramInt) {
/*  35 */     throw new Error("flush not implemented for lightweight");
/*     */   }
/*     */ 
/*     */   protected void drawTriangles(int paramInt, float[] paramArrayOfFloat, byte[] paramArrayOfByte)
/*     */   {
/*  41 */     throw new Error("flush not implemented for lightweight");
/*     */   }
/*     */ 
/*     */   public final void setPerVertexColor(Color paramColor, float paramFloat) {
/*  45 */     float f = paramColor.getAlpha() * paramFloat;
/*  46 */     this.r = ((byte)(int)(paramColor.getRed() * f * 255.0F));
/*  47 */     this.g = ((byte)(int)(paramColor.getGreen() * f * 255.0F));
/*  48 */     this.b = ((byte)(int)(paramColor.getBlue() * f * 255.0F));
/*  49 */     this.a = ((byte)(int)(f * 255.0F));
/*     */   }
/*     */ 
/*     */   public final void setPerVertexColor(float paramFloat) {
/*  53 */     this.r = (this.g = this.b = this.a = (byte)(int)(paramFloat * 255.0F));
/*     */   }
/*     */ 
/*     */   public final void updateVertexColors(int paramInt) {
/*  57 */     for (int i = 0; i != paramInt; i++)
/*  58 */       putColor(i);
/*     */   }
/*     */ 
/*     */   private void putColor(int paramInt)
/*     */   {
/*  63 */     int i = paramInt * 4;
/*  64 */     this.colorArray[(i + 0)] = this.r;
/*  65 */     this.colorArray[(i + 1)] = this.g;
/*  66 */     this.colorArray[(i + 2)] = this.b;
/*  67 */     this.colorArray[(i + 3)] = this.a;
/*     */   }
/*     */ 
/*     */   public final void flush()
/*     */   {
/*  77 */     if (this.index > 0) {
/*  78 */       drawQuads(this.index);
/*  79 */       this.index = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void rewind() {
/*  84 */     this.index = 0;
/*     */   }
/*     */ 
/*     */   private void grow() {
/*  88 */     this.capacity *= 2;
/*  89 */     this.colorArray = Arrays.copyOf(this.colorArray, this.capacity * 4);
/*  90 */     this.coordArray = Arrays.copyOf(this.coordArray, this.capacity * 7);
/*     */   }
/*     */ 
/*     */   public final void addVert(float paramFloat1, float paramFloat2)
/*     */   {
/*  95 */     if (this.index == this.capacity) {
/*  96 */       grow();
/*     */     }
/*     */ 
/*  99 */     int i = 7 * this.index;
/* 100 */     this.coordArray[(i + 0)] = paramFloat1;
/* 101 */     this.coordArray[(i + 1)] = paramFloat2;
/* 102 */     this.coordArray[(i + 2)] = 0.0F;
/* 103 */     putColor(this.index);
/* 104 */     this.index += 1;
/*     */   }
/*     */ 
/*     */   public final void addVert(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 109 */     if (this.index == this.capacity) {
/* 110 */       grow();
/*     */     }
/*     */ 
/* 113 */     int i = 7 * this.index;
/* 114 */     this.coordArray[(i + 0)] = paramFloat1;
/* 115 */     this.coordArray[(i + 1)] = paramFloat2;
/* 116 */     this.coordArray[(i + 2)] = 0.0F;
/* 117 */     this.coordArray[(i + 3)] = paramFloat3;
/* 118 */     this.coordArray[(i + 4)] = paramFloat4;
/* 119 */     putColor(this.index);
/* 120 */     this.index += 1;
/*     */   }
/*     */ 
/*     */   public final void addVert(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 125 */     if (this.index == this.capacity) {
/* 126 */       grow();
/*     */     }
/*     */ 
/* 129 */     int i = 7 * this.index;
/* 130 */     this.coordArray[(i + 0)] = paramFloat1;
/* 131 */     this.coordArray[(i + 1)] = paramFloat2;
/* 132 */     this.coordArray[(i + 2)] = 0.0F;
/* 133 */     this.coordArray[(i + 3)] = paramFloat3;
/* 134 */     this.coordArray[(i + 4)] = paramFloat4;
/* 135 */     this.coordArray[(i + 5)] = paramFloat5;
/* 136 */     this.coordArray[(i + 6)] = paramFloat6;
/* 137 */     putColor(this.index);
/* 138 */     this.index += 1;
/*     */   }
/*     */ 
/*     */   private void addVertNoCheck(float paramFloat1, float paramFloat2)
/*     */   {
/* 143 */     int i = 7 * this.index;
/* 144 */     this.coordArray[(i + 0)] = paramFloat1;
/* 145 */     this.coordArray[(i + 1)] = paramFloat2;
/* 146 */     this.coordArray[(i + 2)] = 0.0F;
/* 147 */     putColor(this.index);
/* 148 */     this.index += 1;
/*     */   }
/*     */ 
/*     */   private void addVertNoCheck(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 153 */     int i = 7 * this.index;
/* 154 */     this.coordArray[(i + 0)] = paramFloat1;
/* 155 */     this.coordArray[(i + 1)] = paramFloat2;
/* 156 */     this.coordArray[(i + 2)] = 0.0F;
/* 157 */     this.coordArray[(i + 3)] = paramFloat3;
/* 158 */     this.coordArray[(i + 4)] = paramFloat4;
/* 159 */     putColor(this.index);
/* 160 */     this.index += 1;
/*     */   }
/*     */ 
/*     */   private void addVertNoCheck(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 165 */     int i = 7 * this.index;
/* 166 */     this.coordArray[(i + 0)] = paramFloat1;
/* 167 */     this.coordArray[(i + 1)] = paramFloat2;
/* 168 */     this.coordArray[(i + 2)] = 0.0F;
/* 169 */     this.coordArray[(i + 3)] = paramFloat3;
/* 170 */     this.coordArray[(i + 4)] = paramFloat4;
/* 171 */     this.coordArray[(i + 5)] = paramFloat5;
/* 172 */     this.coordArray[(i + 6)] = paramFloat6;
/* 173 */     putColor(this.index);
/* 174 */     this.index += 1;
/*     */   }
/*     */ 
/*     */   public final void addVerts(VertexBuffer paramVertexBuffer, int paramInt)
/*     */   {
/* 179 */     flush();
/* 180 */     drawTriangles(paramInt / 3, paramVertexBuffer.coordArray, paramVertexBuffer.colorArray);
/*     */   }
/*     */ 
/*     */   private void ensureCapacityForQuad() {
/* 184 */     if (this.index + 4 > this.capacity) {
/* 185 */       drawQuads(this.index);
/* 186 */       this.index = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void addQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 191 */     ensureCapacityForQuad();
/*     */ 
/* 193 */     addVertNoCheck(paramFloat1, paramFloat2);
/* 194 */     addVertNoCheck(paramFloat1, paramFloat4);
/* 195 */     addVertNoCheck(paramFloat3, paramFloat2);
/* 196 */     addVertNoCheck(paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public final void addQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
/*     */   {
/* 204 */     ensureCapacityForQuad();
/*     */ 
/* 206 */     addVertNoCheck(paramFloat1, paramFloat2, paramFloat5, paramFloat6, paramFloat9, paramFloat10);
/* 207 */     addVertNoCheck(paramFloat1, paramFloat4, paramFloat5, paramFloat8, paramFloat9, paramFloat12);
/* 208 */     addVertNoCheck(paramFloat3, paramFloat2, paramFloat7, paramFloat6, paramFloat11, paramFloat10);
/* 209 */     addVertNoCheck(paramFloat3, paramFloat4, paramFloat7, paramFloat8, paramFloat11, paramFloat12);
/*     */   }
/*     */ 
/*     */   public final void addMappedQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
/*     */   {
/* 217 */     ensureCapacityForQuad();
/*     */ 
/* 219 */     addVertNoCheck(paramFloat1, paramFloat2, paramFloat5, paramFloat6);
/* 220 */     addVertNoCheck(paramFloat1, paramFloat4, paramFloat9, paramFloat10);
/* 221 */     addVertNoCheck(paramFloat3, paramFloat2, paramFloat7, paramFloat8);
/* 222 */     addVertNoCheck(paramFloat3, paramFloat4, paramFloat11, paramFloat12);
/*     */   }
/*     */ 
/*     */   public final void addMappedQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16, float paramFloat17, float paramFloat18, float paramFloat19, float paramFloat20)
/*     */   {
/* 232 */     ensureCapacityForQuad();
/*     */ 
/* 234 */     addVertNoCheck(paramFloat1, paramFloat2, paramFloat5, paramFloat6, paramFloat13, paramFloat14);
/* 235 */     addVertNoCheck(paramFloat1, paramFloat4, paramFloat9, paramFloat10, paramFloat17, paramFloat18);
/* 236 */     addVertNoCheck(paramFloat3, paramFloat2, paramFloat7, paramFloat8, paramFloat15, paramFloat16);
/* 237 */     addVertNoCheck(paramFloat3, paramFloat4, paramFloat11, paramFloat12, paramFloat19, paramFloat20);
/*     */   }
/*     */ 
/*     */   public final void addQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 245 */     int i = this.index;
/* 246 */     if (i + 4 > this.capacity) {
/* 247 */       drawQuads(i);
/* 248 */       i = this.index = 0;
/*     */     }
/*     */ 
/* 251 */     int j = 7 * i;
/* 252 */     float[] arrayOfFloat = this.coordArray;
/*     */ 
/* 255 */     arrayOfFloat[j] = paramFloat1; arrayOfFloat[(++j)] = paramFloat2; arrayOfFloat[(++j)] = 0.0F;
/* 256 */     arrayOfFloat[(++j)] = paramFloat5; arrayOfFloat[(++j)] = paramFloat6;
/* 257 */     j += 3;
/*     */ 
/* 259 */     arrayOfFloat[j] = paramFloat1; arrayOfFloat[(++j)] = paramFloat4; arrayOfFloat[(++j)] = 0.0F;
/* 260 */     arrayOfFloat[(++j)] = paramFloat5; arrayOfFloat[(++j)] = paramFloat8;
/* 261 */     j += 3;
/*     */ 
/* 263 */     arrayOfFloat[j] = paramFloat3; arrayOfFloat[(++j)] = paramFloat2; arrayOfFloat[(++j)] = 0.0F;
/* 264 */     arrayOfFloat[(++j)] = paramFloat7; arrayOfFloat[(++j)] = paramFloat6;
/* 265 */     j += 3;
/*     */ 
/* 267 */     arrayOfFloat[j] = paramFloat3; arrayOfFloat[(++j)] = paramFloat4; arrayOfFloat[(++j)] = 0.0F;
/* 268 */     arrayOfFloat[(++j)] = paramFloat7; arrayOfFloat[(++j)] = paramFloat8;
/*     */ 
/* 270 */     byte[] arrayOfByte = this.colorArray;
/* 271 */     int k = this.r; int m = this.g; int n = this.b; int i1 = this.a;
/* 272 */     int i2 = 4 * i;
/* 273 */     arrayOfByte[i2] = k; arrayOfByte[(++i2)] = m; arrayOfByte[(++i2)] = n; arrayOfByte[(++i2)] = i1;
/* 274 */     arrayOfByte[(++i2)] = k; arrayOfByte[(++i2)] = m; arrayOfByte[(++i2)] = n; arrayOfByte[(++i2)] = i1;
/* 275 */     arrayOfByte[(++i2)] = k; arrayOfByte[(++i2)] = m; arrayOfByte[(++i2)] = n; arrayOfByte[(++i2)] = i1;
/* 276 */     arrayOfByte[(++i2)] = k; arrayOfByte[(++i2)] = m; arrayOfByte[(++i2)] = n; arrayOfByte[(++i2)] = i1;
/*     */ 
/* 278 */     this.index = (i + 4);
/*     */   }
/*     */ 
/*     */   public final void addQuadVO(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10)
/*     */   {
/* 285 */     int i = this.index;
/* 286 */     if (i + 4 > this.capacity) {
/* 287 */       drawQuads(i);
/* 288 */       i = this.index = 0;
/*     */     }
/*     */ 
/* 291 */     int j = 7 * i;
/* 292 */     float[] arrayOfFloat = this.coordArray;
/*     */ 
/* 295 */     arrayOfFloat[j] = paramFloat3; arrayOfFloat[(++j)] = paramFloat4; arrayOfFloat[(++j)] = 0.0F;
/* 296 */     arrayOfFloat[(++j)] = paramFloat7; arrayOfFloat[(++j)] = paramFloat8;
/* 297 */     j += 3;
/*     */ 
/* 300 */     arrayOfFloat[j] = paramFloat3; arrayOfFloat[(++j)] = paramFloat6; arrayOfFloat[(++j)] = 0.0F;
/* 301 */     arrayOfFloat[(++j)] = paramFloat7; arrayOfFloat[(++j)] = paramFloat10;
/* 302 */     j += 3;
/*     */ 
/* 305 */     arrayOfFloat[j] = paramFloat5; arrayOfFloat[(++j)] = paramFloat4; arrayOfFloat[(++j)] = 0.0F;
/* 306 */     arrayOfFloat[(++j)] = paramFloat9; arrayOfFloat[(++j)] = paramFloat8;
/* 307 */     j += 3;
/*     */ 
/* 310 */     arrayOfFloat[j] = paramFloat5; arrayOfFloat[(++j)] = paramFloat6; arrayOfFloat[(++j)] = 0.0F;
/* 311 */     arrayOfFloat[(++j)] = paramFloat9; arrayOfFloat[(++j)] = paramFloat10;
/*     */ 
/* 313 */     byte[] arrayOfByte = this.colorArray;
/* 314 */     int k = 4 * i;
/*     */ 
/* 316 */     int m = (byte)(int)(paramFloat1 * 255.0F);
/* 317 */     int n = (byte)(int)(paramFloat2 * 255.0F);
/*     */ 
/* 319 */     arrayOfByte[k] = m; arrayOfByte[(++k)] = m; arrayOfByte[(++k)] = m; arrayOfByte[(++k)] = m;
/* 320 */     arrayOfByte[(++k)] = n; arrayOfByte[(++k)] = n; arrayOfByte[(++k)] = n; arrayOfByte[(++k)] = n;
/* 321 */     arrayOfByte[(++k)] = m; arrayOfByte[(++k)] = m; arrayOfByte[(++k)] = m; arrayOfByte[(++k)] = m;
/* 322 */     arrayOfByte[(++k)] = n; arrayOfByte[(++k)] = n; arrayOfByte[(++k)] = n; arrayOfByte[(++k)] = n;
/*     */ 
/* 324 */     this.index = (i + 4);
/*     */   }
/*     */ 
/*     */   public final void addMappedPgram(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16, float paramFloat17, float paramFloat18)
/*     */   {
/* 334 */     int i = this.index;
/* 335 */     if (i + 4 > this.capacity) {
/* 336 */       drawQuads(i);
/* 337 */       i = this.index = 0;
/*     */     }
/*     */ 
/* 340 */     int j = 7 * i;
/* 341 */     float[] arrayOfFloat = this.coordArray;
/*     */ 
/* 344 */     arrayOfFloat[j] = paramFloat1; arrayOfFloat[(++j)] = paramFloat2; arrayOfFloat[(++j)] = 0.0F;
/* 345 */     arrayOfFloat[(++j)] = paramFloat9; arrayOfFloat[(++j)] = paramFloat10;
/* 346 */     arrayOfFloat[(++j)] = paramFloat17; arrayOfFloat[(++j)] = paramFloat18;
/*     */ 
/* 349 */     arrayOfFloat[(++j)] = paramFloat5; arrayOfFloat[(++j)] = paramFloat6; arrayOfFloat[(++j)] = 0.0F;
/* 350 */     arrayOfFloat[(++j)] = paramFloat13; arrayOfFloat[(++j)] = paramFloat14;
/* 351 */     arrayOfFloat[(++j)] = paramFloat17; arrayOfFloat[(++j)] = paramFloat18;
/*     */ 
/* 354 */     arrayOfFloat[(++j)] = paramFloat3; arrayOfFloat[(++j)] = paramFloat4; arrayOfFloat[(++j)] = 0.0F;
/* 355 */     arrayOfFloat[(++j)] = paramFloat11; arrayOfFloat[(++j)] = paramFloat12;
/* 356 */     arrayOfFloat[(++j)] = paramFloat17; arrayOfFloat[(++j)] = paramFloat18;
/*     */ 
/* 359 */     arrayOfFloat[(++j)] = paramFloat7; arrayOfFloat[(++j)] = paramFloat8; arrayOfFloat[(++j)] = 0.0F;
/* 360 */     arrayOfFloat[(++j)] = paramFloat15; arrayOfFloat[(++j)] = paramFloat16;
/* 361 */     arrayOfFloat[(++j)] = paramFloat17; arrayOfFloat[(++j)] = paramFloat18;
/*     */ 
/* 363 */     byte[] arrayOfByte = this.colorArray;
/* 364 */     int k = this.r; int m = this.g; int n = this.b; int i1 = this.a;
/* 365 */     int i2 = 4 * i;
/* 366 */     arrayOfByte[i2] = k; arrayOfByte[(++i2)] = m; arrayOfByte[(++i2)] = n; arrayOfByte[(++i2)] = i1;
/* 367 */     arrayOfByte[(++i2)] = k; arrayOfByte[(++i2)] = m; arrayOfByte[(++i2)] = n; arrayOfByte[(++i2)] = i1;
/* 368 */     arrayOfByte[(++i2)] = k; arrayOfByte[(++i2)] = m; arrayOfByte[(++i2)] = n; arrayOfByte[(++i2)] = i1;
/* 369 */     arrayOfByte[(++i2)] = k; arrayOfByte[(++i2)] = m; arrayOfByte[(++i2)] = n; arrayOfByte[(++i2)] = i1;
/*     */ 
/* 371 */     this.index = (i + 4);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.VertexBuffer
 * JD-Core Version:    0.6.2
 */