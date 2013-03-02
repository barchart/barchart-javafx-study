/*     */ package com.sun.webpane.sg.prism;
/*     */ 
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.ResourceFactoryListener;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.webpane.platform.Invoker;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public class RTImage extends PrismImage
/*     */   implements ResourceFactoryListener
/*     */ {
/*     */   private RTTexture txt;
/*     */   private int width;
/*     */   private int height;
/*  24 */   private boolean listenerAdded = false;
/*     */   private ByteBuffer pixelBuffer;
/*     */ 
/*     */   public RTImage(int paramInt1, int paramInt2)
/*     */   {
/*  28 */     this.width = paramInt1;
/*  29 */     this.height = paramInt2;
/*     */   }
/*     */ 
/*     */   public Image getImage() {
/*  33 */     return Image.fromByteBgraPreData(getPixelBuffer(), getWidth(), getHeight());
/*     */   }
/*     */ 
/*     */   public Graphics getGraphics()
/*     */   {
/*  39 */     return getTexture().createGraphics();
/*     */   }
/*     */ 
/*     */   private RTTexture getTexture() {
/*  43 */     if (this.txt == null) {
/*  44 */       ResourceFactory localResourceFactory = GraphicsPipeline.getDefaultResourceFactory();
/*  45 */       this.txt = localResourceFactory.createRTTexture(this.width, this.height);
/*  46 */       if (!this.listenerAdded) {
/*  47 */         localResourceFactory.addFactoryListener(this);
/*  48 */         this.listenerAdded = true;
/*     */       }
/*     */     }
/*  51 */     return this.txt;
/*     */   }
/*     */ 
/*     */   public void draw(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
/*     */   {
/*  59 */     paramGraphics.drawTexture(getTexture(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*  66 */     Invoker.getInvoker().invokeOnRenderThread(new Runnable() {
/*     */       public void run() {
/*  68 */         if (RTImage.this.txt != null) {
/*  69 */           RTImage.this.txt.dispose();
/*  70 */           RTImage.this.txt = null;
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/*  78 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/*  83 */     return this.height;
/*     */   }
/*     */ 
/*     */   public ByteBuffer getPixelBuffer()
/*     */   {
/*  88 */     int i = 0;
/*  89 */     if (this.pixelBuffer == null) {
/*  90 */       this.pixelBuffer = ByteBuffer.allocateDirect(this.width * this.height * 4);
/*  91 */       if (this.pixelBuffer != null) {
/*  92 */         this.pixelBuffer.order(ByteOrder.nativeOrder());
/*  93 */         i = 1;
/*     */       }
/*     */     }
/*  96 */     if ((i != 0) || (isDirty())) {
/*  97 */       Invoker.getInvoker().runOnRenderThread(new Runnable() {
/*     */         public void run() {
/*  99 */           RTImage.this.flushRQ();
/* 100 */           if ((RTImage.this.txt != null) && (RTImage.this.pixelBuffer != null)) {
/* 101 */             PixelFormat localPixelFormat = RTImage.this.txt.getPixelFormat();
/* 102 */             if ((localPixelFormat != PixelFormat.INT_ARGB_PRE) && (localPixelFormat != PixelFormat.BYTE_BGRA_PRE))
/*     */             {
/* 105 */               throw new AssertionError("Unexpected pixel format: " + localPixelFormat);
/*     */             }
/*     */ 
/* 109 */             RTImage.this.pixelBuffer.rewind();
/* 110 */             int[] arrayOfInt = RTImage.this.txt.getPixels();
/* 111 */             if (arrayOfInt != null)
/* 112 */               RTImage.this.pixelBuffer.asIntBuffer().put(arrayOfInt);
/*     */             else {
/* 114 */               RTImage.this.txt.readPixels(RTImage.this.pixelBuffer);
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/*     */     }
/* 120 */     return this.pixelBuffer;
/*     */   }
/*     */ 
/*     */   public void drawPixelBuffer()
/*     */   {
/* 126 */     Invoker.getInvoker().invokeOnRenderThread(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 130 */         Graphics localGraphics = RTImage.this.getGraphics();
/* 131 */         if ((localGraphics != null) && (RTImage.this.pixelBuffer != null)) {
/* 132 */           RTImage.this.pixelBuffer.rewind();
/* 133 */           Image localImage = Image.fromByteBgraPreData(RTImage.this.pixelBuffer, RTImage.this.width, RTImage.this.height);
/*     */ 
/* 137 */           Texture localTexture = localGraphics.getResourceFactory().createTexture(localImage);
/* 138 */           localGraphics.clear();
/* 139 */           localGraphics.drawTexture(localTexture, 0.0F, 0.0F, RTImage.this.width, RTImage.this.height);
/* 140 */           localTexture.dispose();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void factoryReset() {
/* 147 */     if (this.txt != null) {
/* 148 */       this.txt.dispose();
/* 149 */       this.txt = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void factoryReleased()
/*     */   {
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.RTImage
 * JD-Core Version:    0.6.2
 */