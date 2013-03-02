/*     */ package com.sun.prism;
/*     */ 
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public final class MultiTexture
/*     */   implements Texture
/*     */ {
/*     */   private int width;
/*     */   private int height;
/*     */   private PixelFormat format;
/*  19 */   private Texture.WrapMode wrapMode = Texture.WrapMode.CLAMP_TO_EDGE;
/*  20 */   private boolean linearFiltering = true;
/*     */   private final ArrayList<Texture> textures;
/*     */   private int lastImageSerial;
/*     */ 
/*     */   public MultiTexture(PixelFormat paramPixelFormat, int paramInt1, int paramInt2)
/*     */   {
/*  25 */     this.width = paramInt1;
/*  26 */     this.height = paramInt2;
/*  27 */     this.format = paramPixelFormat;
/*  28 */     this.textures = new ArrayList(4);
/*     */   }
/*     */ 
/*     */   public int textureCount() {
/*  32 */     return this.textures.size();
/*     */   }
/*     */ 
/*     */   public void setTexture(Texture paramTexture, int paramInt) {
/*  36 */     if (this.textures.size() < paramInt + 1)
/*     */     {
/*  38 */       for (int i = this.textures.size(); i < paramInt; i++) {
/*  39 */         this.textures.add(null);
/*     */       }
/*  41 */       this.textures.add(paramTexture);
/*     */     } else {
/*  43 */       this.textures.set(paramInt, paramTexture);
/*     */     }
/*  45 */     paramTexture.setWrapMode(this.wrapMode);
/*  46 */     paramTexture.setLinearFiltering(this.linearFiltering);
/*     */   }
/*     */ 
/*     */   public Texture getTexture(int paramInt) {
/*  50 */     return (Texture)this.textures.get(paramInt);
/*     */   }
/*     */ 
/*     */   public Texture[] getTextures() {
/*  54 */     return (Texture[])this.textures.toArray(new Texture[this.textures.size()]);
/*     */   }
/*     */ 
/*     */   public void removeTexture(Texture paramTexture) {
/*  58 */     this.textures.remove(paramTexture);
/*     */   }
/*     */ 
/*     */   public void removeTexture(int paramInt) {
/*  62 */     this.textures.remove(paramInt);
/*     */   }
/*     */ 
/*     */   public PixelFormat getPixelFormat()
/*     */   {
/*  67 */     return this.format;
/*     */   }
/*     */ 
/*     */   public int getPhysicalWidth()
/*     */   {
/*  72 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getPhysicalHeight()
/*     */   {
/*  77 */     return this.height;
/*     */   }
/*     */ 
/*     */   public int getContentX()
/*     */   {
/*  82 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getContentY()
/*     */   {
/*  87 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getContentWidth()
/*     */   {
/*  92 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getContentHeight()
/*     */   {
/*  97 */     return this.height;
/*     */   }
/*     */ 
/*     */   public long getNativeSourceHandle()
/*     */   {
/* 102 */     return 0L;
/*     */   }
/*     */ 
/*     */   public int getLastImageSerial()
/*     */   {
/* 107 */     return this.lastImageSerial;
/*     */   }
/*     */ 
/*     */   public void setLastImageSerial(int paramInt)
/*     */   {
/* 112 */     this.lastImageSerial = paramInt;
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage)
/*     */   {
/* 117 */     throw new UnsupportedOperationException("Update from Image not supported");
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2)
/*     */   {
/* 122 */     throw new UnsupportedOperationException("Update from Image not supported");
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 127 */     throw new UnsupportedOperationException("Update from Image not supported");
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
/*     */   {
/* 132 */     throw new UnsupportedOperationException("Update from Image not supported");
/*     */   }
/*     */ 
/*     */   public void update(Buffer paramBuffer, PixelFormat paramPixelFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean)
/*     */   {
/* 141 */     throw new UnsupportedOperationException("Update from generic Buffer not supported");
/*     */   }
/*     */ 
/*     */   public void update(MediaFrame paramMediaFrame, boolean paramBoolean)
/*     */   {
/* 146 */     if (paramMediaFrame.getPixelFormat() == PixelFormat.MULTI_YCbCr_420)
/*     */     {
/* 148 */       ByteBuffer localByteBuffer = paramMediaFrame.getBuffer();
/*     */ 
/* 150 */       int i = paramMediaFrame.getEncodedWidth();
/* 151 */       int j = paramMediaFrame.getEncodedHeight();
/*     */ 
/* 153 */       for (int k = 0; k < paramMediaFrame.planeCount(); k++) {
/* 154 */         Texture localTexture = (Texture)this.textures.get(k);
/* 155 */         if (null != localTexture) {
/* 156 */           int m = i;
/* 157 */           int n = j;
/*     */ 
/* 159 */           if ((k == 2) || (k == 1))
/*     */           {
/* 161 */             m /= 2;
/* 162 */             n /= 2;
/*     */           }
/*     */ 
/* 165 */           localByteBuffer.position(paramMediaFrame.offsetForPlane(k));
/* 166 */           localTexture.update(localByteBuffer.slice(), PixelFormat.BYTE_ALPHA, 0, 0, 0, 0, m, n, paramMediaFrame.strideForPlane(k), paramBoolean);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 173 */       throw new IllegalArgumentException("Invalid pixel format in MediaFrame");
/*     */     }
/*     */   }
/*     */ 
/*     */   public Texture.WrapMode getWrapMode()
/*     */   {
/* 179 */     return this.wrapMode;
/*     */   }
/*     */ 
/*     */   public void setWrapMode(Texture.WrapMode paramWrapMode)
/*     */   {
/* 184 */     this.wrapMode = paramWrapMode;
/* 185 */     for (Texture localTexture : this.textures)
/* 186 */       localTexture.setWrapMode(paramWrapMode);
/*     */   }
/*     */ 
/*     */   public boolean getLinearFiltering()
/*     */   {
/* 192 */     return this.linearFiltering;
/*     */   }
/*     */ 
/*     */   public void setLinearFiltering(boolean paramBoolean)
/*     */   {
/* 197 */     this.linearFiltering = paramBoolean;
/* 198 */     for (Texture localTexture : this.textures)
/* 199 */       localTexture.setLinearFiltering(paramBoolean);
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 205 */     for (Texture localTexture : this.textures) {
/* 206 */       localTexture.dispose();
/*     */     }
/* 208 */     this.textures.clear();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.MultiTexture
 * JD-Core Version:    0.6.2
 */