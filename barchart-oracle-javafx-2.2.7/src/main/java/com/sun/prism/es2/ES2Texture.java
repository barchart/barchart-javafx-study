/*     */ package com.sun.prism.es2;
/*     */ 
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.MediaFrame;
/*     */ import com.sun.prism.MultiTexture;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.Texture.WrapMode;
/*     */ import com.sun.prism.es2.gl.GLContext;
/*     */ import com.sun.prism.es2.gl.GLFactory;
/*     */ import com.sun.prism.impl.BaseTexture;
/*     */ import com.sun.prism.impl.BufferUtil;
/*     */ import com.sun.prism.impl.Disposer;
/*     */ import com.sun.prism.impl.Disposer.Record;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.prism.impl.PrismTrace;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ class ES2Texture extends BaseTexture
/*     */ {
/*     */   final ES2Context context;
/*     */   private int texID;
/*  30 */   private Texture.WrapMode wrapMode = Texture.WrapMode.REPEAT;
/*  31 */   private boolean linearFilterMode = true;
/*     */ 
/*     */   ES2Texture(ES2Context paramES2Context, PixelFormat paramPixelFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, Disposer.Record paramRecord)
/*     */   {
/*  39 */     super(paramPixelFormat, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramRecord);
/*     */ 
/*  44 */     this.context = paramES2Context;
/*  45 */     this.texID = paramInt1;
/*     */   }
/*     */ 
/*     */   private ES2Texture(ES2Context paramES2Context, PixelFormat paramPixelFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/*     */   {
/*  53 */     this(paramES2Context, paramPixelFormat, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, new ES2TextureDisposerRecord(paramES2Context, paramInt1));
/*     */ 
/*  59 */     PrismTrace.textureCreated(paramInt1, paramInt2, paramInt3, paramPixelFormat.getBytesPerPixelUnit());
/*     */   }
/*     */ 
/*     */   static int nextPowerOfTwo(int paramInt1, int paramInt2)
/*     */   {
/*  69 */     if (paramInt1 > paramInt2) {
/*  70 */       return 0;
/*     */     }
/*  72 */     int i = 1;
/*  73 */     while (i < paramInt1) {
/*  74 */       i *= 2;
/*     */     }
/*  76 */     return i;
/*     */   }
/*     */ 
/*     */   static ES2Texture create(ES2Context paramES2Context, PixelFormat paramPixelFormat, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/*  81 */     if (!paramES2Context.getResourceFactory().isFormatSupported(paramPixelFormat)) {
/*  82 */       throw new UnsupportedOperationException("Pixel format " + paramPixelFormat + " not supported on this device");
/*     */     }
/*     */ 
/*  87 */     if (paramPixelFormat == PixelFormat.MULTI_YCbCr_420) {
/*  88 */       throw new IllegalArgumentException("Format requires multitexturing: " + paramPixelFormat);
/*     */     }
/*     */ 
/* 108 */     int i = (paramBoolean) && (!ES2Pipeline.glFactory.isGL2()) && (paramES2Context.isEdgeSmoothingSupported(paramPixelFormat)) ? 1 : 0;
/*     */ 
/* 113 */     int m = paramInt1;
/* 114 */     int n = paramInt2;
/*     */     int j;
/*     */     int k;
/*     */     int i1;
/*     */     int i2;
/* 116 */     if (i != 0) {
/* 117 */       j = 2;
/* 118 */       k = 2;
/* 119 */       i1 = m + 4;
/* 120 */       i2 = n + 4;
/*     */     } else {
/* 122 */       j = 0;
/* 123 */       k = 0;
/* 124 */       i1 = m;
/* 125 */       i2 = n;
/*     */     }
/*     */ 
/* 128 */     GLContext localGLContext = paramES2Context.getGLContext();
/* 129 */     int i3 = localGLContext.getMaxTextureSize();
/*     */ 
/* 131 */     if (localGLContext.canCreateNonPowTwoTextures()) {
/* 132 */       i4 = i1 <= i3 ? i1 : 0;
/* 133 */       i5 = i2 <= i3 ? i2 : 0;
/*     */     } else {
/* 135 */       i4 = nextPowerOfTwo(i1, i3);
/* 136 */       i5 = nextPowerOfTwo(i2, i3);
/*     */     }
/*     */ 
/* 139 */     if ((i4 == 0) || (i5 == 0)) {
/* 140 */       throw new RuntimeException("Requested texture dimensions (" + paramInt1 + "x" + paramInt2 + ") " + "require dimensions (" + i4 + "x" + i5 + ") " + "that exceed maximum texture size (" + i3 + ")");
/*     */     }
/*     */ 
/* 147 */     int i6 = PrismSettings.minTextureSize;
/* 148 */     int i4 = Math.max(i4, i6);
/* 149 */     int i5 = Math.max(i5, i6);
/*     */ 
/* 152 */     int i7 = localGLContext.getBoundTexture();
/* 153 */     int i8 = localGLContext.genAndBindTexture();
/*     */ 
/* 155 */     uploadPixels(paramES2Context.getGLContext(), 50, null, paramPixelFormat, i4, i5, j, k, 0, 0, m, n, 0, true);
/*     */ 
/* 160 */     localGLContext.texParamsMinMax(52);
/*     */ 
/* 163 */     localGLContext.setBoundTexture(i7);
/*     */ 
/* 165 */     return new ES2Texture(paramES2Context, paramPixelFormat, i8, i4, i5, j, k, m, n);
/*     */   }
/*     */ 
/*     */   public static Texture create(ES2Context paramES2Context, MediaFrame paramMediaFrame)
/*     */   {
/* 173 */     paramMediaFrame.holdFrame();
/*     */ 
/* 175 */     int i = paramMediaFrame.getEncodedWidth();
/* 176 */     int j = paramMediaFrame.getEncodedHeight();
/* 177 */     PixelFormat localPixelFormat = paramMediaFrame.getPixelFormat();
/*     */ 
/* 179 */     if (paramMediaFrame.getPixelFormat() == PixelFormat.MULTI_YCbCr_420)
/*     */     {
/* 181 */       k = paramMediaFrame.getEncodedWidth();
/* 182 */       int m = paramMediaFrame.getEncodedHeight();
/* 183 */       n = paramMediaFrame.planeCount();
/*     */ 
/* 186 */       MultiTexture localMultiTexture = new MultiTexture(localPixelFormat, paramMediaFrame.getWidth(), paramMediaFrame.getHeight());
/*     */ 
/* 191 */       for (i2 = 0; i2 < n; i2++) {
/* 192 */         i3 = k;
/* 193 */         int i4 = m;
/*     */ 
/* 195 */         if ((i2 == 2) || (i2 == 1))
/*     */         {
/* 197 */           i3 /= 2;
/* 198 */           i4 /= 2;
/*     */         }
/*     */ 
/* 202 */         ES2Texture localES2Texture2 = create(paramES2Context, PixelFormat.BYTE_ALPHA, i3, i4, false);
/*     */ 
/* 204 */         localMultiTexture.setTexture(localES2Texture2, i2);
/*     */       }
/*     */ 
/* 207 */       paramMediaFrame.releaseFrame();
/* 208 */       return localMultiTexture;
/*     */     }
/*     */ 
/* 212 */     GLContext localGLContext = paramES2Context.getGLContext();
/* 213 */     int n = localGLContext.getMaxTextureSize();
/*     */ 
/* 217 */     int k = paramMediaFrame.getEncodedHeight();
/* 218 */     i = paramMediaFrame.getEncodedWidth();
/* 219 */     j = k;
/*     */ 
/* 221 */     localPixelFormat = paramMediaFrame.getPixelFormat();
/*     */ 
/* 224 */     if (!localGLContext.canCreateNonPowTwoTextures()) {
/* 225 */       i = nextPowerOfTwo(i, n);
/* 226 */       j = nextPowerOfTwo(j, n);
/*     */     }
/*     */ 
/* 230 */     int i1 = PrismSettings.minTextureSize;
/* 231 */     i = Math.max(i, i1);
/* 232 */     j = Math.max(j, i1);
/*     */ 
/* 235 */     int i2 = localGLContext.getBoundTexture();
/*     */ 
/* 237 */     int i3 = localGLContext.genAndBindTexture();
/*     */ 
/* 239 */     uploadPixels(paramES2Context.getGLContext(), 50, paramMediaFrame, i, j, true);
/*     */ 
/* 242 */     localGLContext.texParamsMinMax(52);
/*     */ 
/* 245 */     localGLContext.setBoundTexture(i2);
/*     */ 
/* 247 */     ES2Texture localES2Texture1 = new ES2Texture(paramES2Context, localPixelFormat, i3, i, j, 0, 0, paramMediaFrame.getWidth(), paramMediaFrame.getHeight());
/*     */ 
/* 250 */     paramMediaFrame.releaseFrame();
/* 251 */     return localES2Texture1;
/*     */   }
/*     */ 
/*     */   private static void uploadPixels(GLContext paramGLContext, int paramInt1, Buffer paramBuffer, PixelFormat paramPixelFormat, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, boolean paramBoolean)
/*     */   {
/* 262 */     int i = 1;
/*     */ 
/* 266 */     boolean bool = ES2Pipeline.glFactory.isGL2();
/*     */     int j;
/*     */     int k;
/*     */     int m;
/* 268 */     switch (1.$SwitchMap$com$sun$prism$PixelFormat[paramPixelFormat.ordinal()]) {
/*     */     case 1:
/*     */     case 2:
/* 271 */       i = 4;
/* 272 */       j = 40;
/*     */ 
/* 275 */       k = 41;
/* 276 */       if (!bool) {
/* 277 */         m = 40;
/* 278 */         k = 40;
/* 279 */         m = 21;
/*     */       } else {
/* 281 */         m = 22;
/*     */       }
/* 283 */       break;
/*     */     case 3:
/* 293 */       j = bool ? 40 : 42;
/* 294 */       k = 42;
/* 295 */       m = 21;
/* 296 */       break;
/*     */     case 4:
/* 298 */       j = 43;
/* 299 */       k = 43;
/* 300 */       m = 21;
/* 301 */       break;
/*     */     case 5:
/* 303 */       j = 44;
/* 304 */       k = 44;
/* 305 */       m = 21;
/* 306 */       break;
/*     */     case 6:
/* 308 */       i = 4;
/*     */ 
/* 313 */       j = bool ? 45 : 40;
/* 314 */       k = 40;
/* 315 */       m = 20;
/* 316 */       break;
/*     */     case 7:
/* 319 */       i = 2;
/* 320 */       j = 42;
/* 321 */       k = 46;
/* 322 */       m = 24;
/* 323 */       break;
/*     */     case 8:
/*     */     default:
/* 326 */       throw new InternalError("Image format not supported: " + paramPixelFormat);
/*     */     }
/*     */ 
/* 329 */     if ((!bool) && (j != k))
/* 330 */       throw new InternalError("On ES 2.0 device, internalFormat must match pixelFormat");
/*     */     int n;
/*     */     int i1;
/*     */     int i2;
/* 334 */     if (paramBoolean)
/*     */     {
/* 340 */       paramGLContext.pixelStorei(60, 1);
/* 341 */       if ((paramPixelFormat == PixelFormat.FLOAT_XYZW) && (j == 40))
/*     */       {
/* 346 */         paramGLContext.texImage2D(paramInt1, 0, 40, paramInt2, paramInt3, 0, k, m, null);
/*     */       }
/*     */       else
/*     */       {
/* 359 */         if (bool) {
/* 360 */           n = 44;
/* 361 */           i1 = 21;
/* 362 */           i2 = 1;
/*     */         } else {
/* 364 */           n = k;
/* 365 */           i1 = m;
/* 366 */           i2 = paramPixelFormat.getBytesPerPixelUnit();
/*     */         }
/* 368 */         ByteBuffer localByteBuffer = null;
/* 369 */         if ((paramInt8 != paramInt2) || (paramInt9 != paramInt3))
/*     */         {
/* 380 */           int i4 = paramInt2 * paramInt3 * i2;
/* 381 */           localByteBuffer = BufferUtil.newByteBuffer(i4);
/*     */         }
/* 383 */         if (bool)
/*     */         {
/* 385 */           paramGLContext.pixelStorei(61, 0);
/* 386 */           paramGLContext.pixelStorei(62, 0);
/* 387 */           paramGLContext.pixelStorei(63, 0);
/* 388 */           paramGLContext.pixelStorei(60, i);
/*     */         }
/* 390 */         paramGLContext.texImage2D(paramInt1, 0, j, paramInt2, paramInt3, 0, n, i1, localByteBuffer);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 395 */     if (paramBuffer != null)
/*     */     {
/* 403 */       n = paramInt10 / paramPixelFormat.getBytesPerPixelUnit();
/*     */ 
/* 405 */       if ((!bool) && (
/* 406 */         (paramInt6 != 0) || (paramInt7 != 0) || (paramInt8 != n)))
/*     */       {
/* 408 */         paramBuffer = Image.createPackedBuffer(paramBuffer, paramPixelFormat, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);
/*     */ 
/* 411 */         paramInt6 = paramInt7 = 0;
/* 412 */         paramInt10 = paramInt8;
/* 413 */         n = paramInt10 / paramPixelFormat.getBytesPerPixelUnit();
/*     */       }
/*     */ 
/* 416 */       paramGLContext.pixelStorei(60, i);
/* 417 */       if (bool) {
/* 418 */         if (paramInt8 == n)
/* 419 */           paramGLContext.pixelStorei(61, 0);
/*     */         else {
/* 421 */           paramGLContext.pixelStorei(61, n);
/*     */         }
/*     */       }
/*     */ 
/* 425 */       i1 = paramBuffer.position();
/*     */ 
/* 427 */       i2 = getBufferElementSizeLog(paramBuffer);
/* 428 */       int i3 = paramPixelFormat.getBytesPerPixelUnit() >> i2;
/* 429 */       paramBuffer.position(paramInt6 * i3 + paramInt7 * (paramInt10 >> i2));
/*     */ 
/* 431 */       paramGLContext.texSubImage2D(paramInt1, 0, paramInt4, paramInt5, paramInt8, paramInt9, k, m, paramBuffer);
/*     */ 
/* 434 */       paramBuffer.position(i1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void uploadPixels(GLContext paramGLContext, int paramInt1, MediaFrame paramMediaFrame, int paramInt2, int paramInt3, boolean paramBoolean)
/*     */   {
/* 440 */     paramMediaFrame.holdFrame();
/*     */ 
/* 442 */     int i = 1;
/*     */ 
/* 446 */     int n = paramMediaFrame.getEncodedWidth();
/* 447 */     int i1 = paramMediaFrame.getEncodedHeight();
/* 448 */     int i2 = i1;
/* 449 */     ByteBuffer localByteBuffer1 = paramMediaFrame.getBuffer();
/*     */     int j;
/*     */     int k;
/*     */     int m;
/* 451 */     switch (1.$SwitchMap$com$sun$prism$PixelFormat[paramMediaFrame.getPixelFormat().ordinal()]) {
/*     */     case 2:
/* 453 */       i = 4;
/* 454 */       j = 40;
/* 455 */       k = 41;
/* 456 */       if (localByteBuffer1.order() == ByteOrder.LITTLE_ENDIAN)
/* 457 */         m = 22;
/*     */       else {
/* 459 */         m = 23;
/*     */       }
/* 461 */       break;
/*     */     case 7:
/* 464 */       i = 2;
/* 465 */       j = 42;
/* 466 */       k = 46;
/* 467 */       m = 24;
/* 468 */       break;
/*     */     case 8:
/*     */     default:
/* 471 */       paramMediaFrame.releaseFrame();
/* 472 */       throw new InternalError("Invalid video image format " + paramMediaFrame.getPixelFormat());
/*     */     }
/*     */ 
/* 476 */     if (paramBoolean) {
/* 477 */       paramGLContext.pixelStorei(60, 1);
/* 478 */       ByteBuffer localByteBuffer2 = null;
/* 479 */       if ((n != paramInt2) || (i2 != paramInt3)) {
/* 480 */         int i3 = paramInt2 * paramInt3;
/* 481 */         localByteBuffer2 = BufferUtil.newByteBuffer(i3);
/*     */       }
/* 483 */       paramGLContext.texImage2D(paramInt1, 0, j, paramInt2, paramInt3, 0, 44, 21, localByteBuffer2);
/*     */     }
/*     */ 
/* 488 */     if (localByteBuffer1 != null) {
/* 489 */       paramGLContext.pixelStorei(60, i);
/* 490 */       paramGLContext.pixelStorei(61, paramMediaFrame.strideForPlane(0) / i);
/*     */ 
/* 492 */       localByteBuffer1.position(paramMediaFrame.offsetForPlane(0));
/* 493 */       paramGLContext.texSubImage2D(paramInt1, 0, 0, 0, n, paramMediaFrame.getHeight(), k, m, localByteBuffer1.slice());
/*     */     }
/*     */ 
/* 497 */     paramMediaFrame.releaseFrame();
/*     */   }
/*     */ 
/*     */   public static int getBufferElementSizeLog(Buffer paramBuffer) {
/* 501 */     if ((paramBuffer instanceof ByteBuffer))
/* 502 */       return 0;
/* 503 */     if (((paramBuffer instanceof IntBuffer)) || ((paramBuffer instanceof FloatBuffer))) {
/* 504 */       return 2;
/*     */     }
/* 506 */     throw new InternalError("Unsupported Buffer type: " + paramBuffer.getClass());
/*     */   }
/*     */ 
/*     */   void updateWrapState()
/*     */   {
/* 511 */     Texture.WrapMode localWrapMode = getWrapMode();
/*     */ 
/* 516 */     if (this.wrapMode != localWrapMode) {
/* 517 */       GLContext localGLContext = this.context.getGLContext();
/* 518 */       int i = localGLContext.getBoundTexture();
/* 519 */       if (i != this.texID) {
/* 520 */         localGLContext.setBoundTexture(this.texID);
/*     */       }
/* 522 */       localGLContext.updateWrapState(this.texID, localWrapMode);
/* 523 */       if (i != this.texID) {
/* 524 */         localGLContext.setBoundTexture(i);
/*     */       }
/* 526 */       this.wrapMode = localWrapMode;
/*     */     }
/*     */   }
/*     */ 
/*     */   void updateFilterState() {
/* 531 */     boolean bool = getLinearFiltering();
/*     */ 
/* 536 */     if (this.linearFilterMode != bool) {
/* 537 */       GLContext localGLContext = this.context.getGLContext();
/* 538 */       int i = localGLContext.getBoundTexture();
/* 539 */       if (i != this.texID) {
/* 540 */         localGLContext.setBoundTexture(this.texID);
/*     */       }
/* 542 */       localGLContext.updateFilterState(this.texID, bool);
/* 543 */       if (i != this.texID) {
/* 544 */         localGLContext.setBoundTexture(i);
/*     */       }
/* 546 */       this.linearFilterMode = bool;
/*     */     }
/*     */   }
/*     */ 
/*     */   public long getNativeSourceHandle() {
/* 551 */     return this.texID;
/*     */   }
/*     */ 
/*     */   public void update(Buffer paramBuffer, PixelFormat paramPixelFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean)
/*     */   {
/* 560 */     checkUpdateParams(paramBuffer, paramPixelFormat, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*     */ 
/* 563 */     if (!paramBoolean) {
/* 564 */       this.context.flushVertexBuffer();
/*     */     }
/*     */ 
/* 567 */     if (this.texID != 0) {
/* 568 */       GLContext localGLContext = this.context.getGLContext();
/*     */ 
/* 570 */       int i = localGLContext.getActiveTextureUnit();
/* 571 */       int j = localGLContext.getBoundTexture();
/* 572 */       int k = 0;
/* 573 */       for (int m = 0; m < 2; m++) {
/* 574 */         if (localGLContext.getBoundTexture(m) == this.texID) {
/* 575 */           k = 1;
/* 576 */           if (i == m) break;
/* 577 */           localGLContext.setActiveTextureUnit(m); break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 582 */       if (k == 0) {
/* 583 */         localGLContext.setBoundTexture(this.texID);
/*     */       }
/*     */ 
/* 586 */       uploadPixels(localGLContext, 50, paramBuffer, paramPixelFormat, getPhysicalWidth(), getPhysicalHeight(), getContentX() + paramInt1, getContentY() + paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, false);
/*     */ 
/* 594 */       if (i != localGLContext.getActiveTextureUnit()) {
/* 595 */         localGLContext.setActiveTextureUnit(i);
/*     */       }
/* 597 */       if (j != localGLContext.getBoundTexture())
/* 598 */         localGLContext.setBoundTexture(j);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update(MediaFrame paramMediaFrame, boolean paramBoolean)
/*     */   {
/* 604 */     if (!paramBoolean) {
/* 605 */       this.context.flushVertexBuffer();
/*     */     }
/*     */ 
/* 608 */     if (this.texID != 0) {
/* 609 */       GLContext localGLContext = this.context.getGLContext();
/*     */ 
/* 611 */       int i = localGLContext.getActiveTextureUnit();
/* 612 */       int j = localGLContext.getBoundTexture();
/* 613 */       int k = 0;
/* 614 */       for (int m = 0; m < 2; m++) {
/* 615 */         if (localGLContext.getBoundTexture(m) == this.texID) {
/* 616 */           k = 1;
/* 617 */           if (i == m) break;
/* 618 */           localGLContext.setActiveTextureUnit(m); break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 623 */       if (k == 0) {
/* 624 */         localGLContext.setBoundTexture(this.texID);
/*     */       }
/*     */ 
/* 627 */       uploadPixels(localGLContext, 50, paramMediaFrame, getPhysicalWidth(), getPhysicalHeight(), false);
/*     */ 
/* 633 */       if (i != localGLContext.getActiveTextureUnit()) {
/* 634 */         localGLContext.setActiveTextureUnit(i);
/*     */       }
/* 636 */       if (j != localGLContext.getBoundTexture())
/* 637 */         localGLContext.setBoundTexture(j);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 643 */     if (this.texID != 0) {
/* 644 */       Disposer.disposeRecord(this.disposerRecord);
/* 645 */       this.texID = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   static class ES2TextureDisposerRecord implements Disposer.Record {
/*     */     final ES2Context context;
/*     */     private int texID;
/*     */ 
/*     */     ES2TextureDisposerRecord(ES2Context paramES2Context, int paramInt) {
/* 655 */       this.context = paramES2Context;
/* 656 */       this.texID = paramInt;
/*     */     }
/*     */ 
/*     */     void traceDispose() {
/*     */     }
/*     */ 
/*     */     public void dispose() {
/* 663 */       if (this.texID != 0) {
/* 664 */         traceDispose();
/* 665 */         GLContext localGLContext = this.context.getGLContext();
/*     */ 
/* 668 */         for (int i = 0; i < localGLContext.getNumBoundTexture(); i++) {
/* 669 */           if (this.texID == localGLContext.getBoundTexture(i))
/*     */           {
/* 672 */             this.context.flushVertexBuffer();
/*     */ 
/* 674 */             localGLContext.updateActiveTextureUnit(i);
/* 675 */             localGLContext.setBoundTexture(0);
/*     */           }
/*     */         }
/*     */ 
/* 679 */         localGLContext.deleteTexture(this.texID);
/* 680 */         this.texID = 0;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.ES2Texture
 * JD-Core Version:    0.6.2
 */