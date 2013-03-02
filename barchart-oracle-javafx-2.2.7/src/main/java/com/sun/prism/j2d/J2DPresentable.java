/*     */ package com.sun.prism.j2d;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.Presentable;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.SinglePixelPackedSampleModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public abstract class J2DPresentable
/*     */   implements Presentable
/*     */ {
/*     */   J2DResourceFactory factory;
/*     */   boolean needsResize;
/*     */   BufferedImage buffer;
/*     */   IntBuffer ib;
/*     */   J2DRTTexture readbackBuffer;
/*     */ 
/*     */   public static J2DPresentable create(View paramView, J2DResourceFactory paramJ2DResourceFactory)
/*     */   {
/*  29 */     return new Glass(paramView, paramJ2DResourceFactory);
/*     */   }
/*     */ 
/*     */   public static J2DPresentable create(BufferedImage paramBufferedImage, J2DResourceFactory paramJ2DResourceFactory)
/*     */   {
/*  35 */     return new Bimg(paramBufferedImage, paramJ2DResourceFactory);
/*     */   }
/*     */ 
/*     */   protected J2DPresentable(BufferedImage paramBufferedImage, J2DResourceFactory paramJ2DResourceFactory)
/*     */   {
/* 170 */     this.buffer = paramBufferedImage;
/* 171 */     this.factory = paramJ2DResourceFactory;
/*     */   }
/*     */ 
/*     */   public void setNeedsResize() {
/* 175 */     this.needsResize = true;
/*     */   }
/*     */ 
/*     */   ResourceFactory getResourceFactory() {
/* 179 */     return this.factory;
/*     */   }
/*     */ 
/*     */   public abstract BufferedImage createBuffer(int paramInt1, int paramInt2);
/*     */ 
/*     */   public Graphics createGraphics()
/*     */   {
/* 187 */     int i = getContentWidth();
/* 188 */     int j = getContentHeight();
/*     */ 
/* 191 */     if ((this.buffer == null) || (this.buffer.getWidth() != i) || (this.buffer.getHeight() != j))
/*     */     {
/* 195 */       this.buffer = null;
/* 196 */       this.readbackBuffer = null;
/* 197 */       this.buffer = createBuffer(i, j);
/* 198 */       WritableRaster localWritableRaster = this.buffer.getRaster();
/* 199 */       DataBuffer localDataBuffer = localWritableRaster.getDataBuffer();
/* 200 */       SinglePixelPackedSampleModel localSinglePixelPackedSampleModel = (SinglePixelPackedSampleModel)localWritableRaster.getSampleModel();
/*     */ 
/* 202 */       int[] arrayOfInt = ((DataBufferInt)localDataBuffer).getData();
/* 203 */       this.ib = IntBuffer.wrap(arrayOfInt, localDataBuffer.getOffset(), localDataBuffer.getSize());
/*     */     }
/* 205 */     this.needsResize = false;
/*     */ 
/* 207 */     Graphics2D localGraphics2D = (Graphics2D)this.buffer.getGraphics();
/* 208 */     return new J2DPrismGraphics(this, localGraphics2D);
/*     */   }
/*     */ 
/*     */   public J2DRTTexture getReadbackBuffer() {
/* 212 */     if (this.readbackBuffer == null) {
/* 213 */       this.readbackBuffer = ((J2DRTTexture)this.factory.createRTTexture(getContentWidth(), getContentHeight()));
/*     */     }
/*     */ 
/* 216 */     return this.readbackBuffer;
/*     */   }
/*     */ 
/*     */   public BufferedImage getBackBuffer() {
/* 220 */     return this.buffer;
/*     */   }
/*     */ 
/*     */   public Screen getAssociatedScreen() {
/* 224 */     return this.factory.getScreen();
/*     */   }
/*     */ 
/*     */   public long getNativeDestHandle() {
/* 228 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public int getContentX() {
/* 232 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getContentY() {
/* 236 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getPhysicalWidth()
/*     */   {
/* 243 */     return this.buffer == null ? getContentWidth() : this.buffer.getWidth();
/*     */   }
/*     */ 
/*     */   public int getPhysicalHeight()
/*     */   {
/* 250 */     return this.buffer == null ? getContentHeight() : this.buffer.getHeight();
/*     */   }
/*     */ 
/*     */   public boolean recreateOnResize() {
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */   private static class Bimg extends J2DPresentable
/*     */   {
/*     */     private boolean opaque;
/*     */ 
/*     */     public Bimg(BufferedImage paramBufferedImage, J2DResourceFactory paramJ2DResourceFactory)
/*     */     {
/* 128 */       super(paramJ2DResourceFactory);
/*     */     }
/*     */ 
/*     */     public BufferedImage createBuffer(int paramInt1, int paramInt2)
/*     */     {
/* 133 */       throw new UnsupportedOperationException("cannot create new buffers for image");
/*     */     }
/*     */ 
/*     */     public boolean prepare(Rectangle paramRectangle) {
/* 137 */       throw new UnsupportedOperationException("cannot prepare/present on image");
/*     */     }
/*     */ 
/*     */     public boolean present() {
/* 141 */       throw new UnsupportedOperationException("cannot prepare/present on image");
/*     */     }
/*     */ 
/*     */     public int getContentWidth() {
/* 145 */       return this.buffer.getWidth();
/*     */     }
/*     */ 
/*     */     public int getContentHeight() {
/* 149 */       return this.buffer.getHeight();
/*     */     }
/*     */ 
/*     */     public void setOpaque(boolean paramBoolean) {
/* 153 */       this.opaque = paramBoolean;
/*     */     }
/*     */ 
/*     */     public boolean isOpaque() {
/* 157 */       return this.opaque;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Glass extends J2DPresentable
/*     */   {
/*     */     View glassview;
/*     */     Pixels pixels;
/*     */     private boolean opaque;
/*  78 */     private final Application app = Application.GetApplication();
/*     */ 
/*     */     public Glass(View paramView, J2DResourceFactory paramJ2DResourceFactory)
/*     */     {
/*  45 */       super(paramJ2DResourceFactory);
/*  46 */       this.glassview = paramView;
/*  47 */       setNeedsResize();
/*     */     }
/*     */ 
/*     */     public BufferedImage createBuffer(int paramInt1, int paramInt2)
/*     */     {
/*  52 */       this.pixels = null;
/*  53 */       int i = Pixels.getNativeFormat();
/*  54 */       if (PrismSettings.verbose) {
/*  55 */         System.out.println("Glass native format: " + i);
/*     */       }
/*  57 */       ByteOrder localByteOrder = ByteOrder.nativeOrder();
/*  58 */       switch (i) {
/*     */       case 1:
/*  60 */         if (localByteOrder == ByteOrder.LITTLE_ENDIAN) {
/*  61 */           return new BufferedImage(paramInt1, paramInt2, 3);
/*     */         }
/*  63 */         throw new UnsupportedOperationException("BYTE_BGRA_PRE pixel format on BIG_ENDIAN");
/*     */       case 2:
/*  67 */         if (localByteOrder == ByteOrder.BIG_ENDIAN) {
/*  68 */           return new BufferedImage(paramInt1, paramInt2, 2);
/*     */         }
/*  70 */         throw new UnsupportedOperationException("BYTE_ARGB pixel format on LITTLE_ENDIAN");
/*     */       }
/*     */ 
/*  74 */       throw new UnsupportedOperationException("unrecognized pixel format: " + i);
/*     */     }
/*     */ 
/*     */     public boolean prepare(Rectangle paramRectangle)
/*     */     {
/*  81 */       if (!this.glassview.isClosed())
/*     */       {
/*  86 */         if (this.pixels == null) {
/*  87 */           this.pixels = this.app.createPixels(getPhysicalWidth(), getPhysicalHeight(), this.ib);
/*     */         }
/*  89 */         return this.pixels != null;
/*     */       }
/*  91 */       return false;
/*     */     }
/*     */ 
/*     */     public boolean present()
/*     */     {
/*  96 */       final Pixels localPixels = this.pixels;
/*  97 */       Application.postOnEventQueue(new Runnable() {
/*     */         public void run() {
/*  99 */           if (!J2DPresentable.Glass.this.glassview.isClosed())
/* 100 */             J2DPresentable.Glass.this.glassview.uploadPixels(localPixels);
/*     */         }
/*     */       });
/* 104 */       return true;
/*     */     }
/*     */ 
/*     */     public int getContentWidth() {
/* 108 */       return this.glassview.getWidth();
/*     */     }
/*     */ 
/*     */     public int getContentHeight() {
/* 112 */       return this.glassview.getHeight();
/*     */     }
/*     */ 
/*     */     public void setOpaque(boolean paramBoolean) {
/* 116 */       this.opaque = paramBoolean;
/*     */     }
/*     */ 
/*     */     public boolean isOpaque() {
/* 120 */       return this.opaque;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.j2d.J2DPresentable
 * JD-Core Version:    0.6.2
 */