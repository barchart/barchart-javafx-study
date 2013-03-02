/*     */ package com.sun.scenario.effect.impl.prism.sw;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.Texture.WrapMode;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.prism.PrDrawable;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class PSWDrawable extends PrDrawable
/*     */   implements HeapImage
/*     */ {
/*     */   private RTTexture rtt;
/*     */   private Image image;
/*     */   private boolean heapDirty;
/*     */   private boolean vramDirty;
/*     */ 
/*     */   private PSWDrawable(RTTexture paramRTTexture)
/*     */   {
/*  50 */     super(paramRTTexture);
/*  51 */     this.rtt = paramRTTexture;
/*     */   }
/*     */ 
/*     */   static PSWDrawable create(RTTexture paramRTTexture) {
/*  55 */     return new PSWDrawable(paramRTTexture);
/*     */   }
/*     */ 
/*     */   static PSWDrawable create(Screen paramScreen, int paramInt1, int paramInt2) {
/*  59 */     RTTexture localRTTexture = GraphicsPipeline.getPipeline().getResourceFactory(paramScreen).createRTTexture(paramInt1, paramInt2);
/*     */ 
/*  63 */     localRTTexture.setWrapMode(Texture.WrapMode.CLAMP_TO_ZERO);
/*  64 */     return new PSWDrawable(localRTTexture);
/*     */   }
/*     */ 
/*     */   public void flush() {
/*  68 */     if (this.rtt != null) {
/*  69 */       this.rtt.dispose();
/*  70 */       this.rtt = null;
/*  71 */       this.image = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getData() {
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   public int getPhysicalWidth()
/*     */   {
/*  82 */     return this.rtt.getContentWidth();
/*     */   }
/*     */ 
/*     */   public int getPhysicalHeight()
/*     */   {
/*  88 */     return this.rtt.getContentHeight();
/*     */   }
/*     */ 
/*     */   public int getScanlineStride() {
/*  92 */     return this.rtt.getContentWidth();
/*     */   }
/*     */ 
/*     */   public int[] getPixelArray() {
/*  96 */     int[] arrayOfInt = this.rtt.getPixels();
/*  97 */     if (arrayOfInt != null) {
/*  98 */       return arrayOfInt;
/*     */     }
/* 100 */     if (this.image == null) {
/* 101 */       int i = this.rtt.getContentWidth();
/* 102 */       int j = this.rtt.getContentHeight();
/* 103 */       arrayOfInt = new int[i * j];
/* 104 */       this.image = Image.fromIntArgbPreData(arrayOfInt, i, j);
/*     */     }
/* 106 */     IntBuffer localIntBuffer = (IntBuffer)this.image.getPixelBuffer();
/* 107 */     if (this.vramDirty)
/*     */     {
/* 109 */       this.rtt.readPixels(localIntBuffer);
/* 110 */       this.vramDirty = false;
/*     */     }
/* 112 */     this.heapDirty = true;
/* 113 */     return localIntBuffer.array();
/*     */   }
/*     */ 
/*     */   public Texture getTextureObject()
/*     */   {
/* 118 */     if (this.heapDirty)
/*     */     {
/* 121 */       int i = this.rtt.getContentWidth();
/* 122 */       int j = this.rtt.getContentHeight();
/* 123 */       Screen localScreen = this.rtt.getAssociatedScreen();
/* 124 */       Texture localTexture = GraphicsPipeline.getPipeline().getResourceFactory(localScreen).createTexture(this.image);
/*     */ 
/* 126 */       Graphics localGraphics = createGraphics();
/* 127 */       localGraphics.drawTexture(localTexture, 0.0F, 0.0F, i, j);
/* 128 */       localGraphics.sync();
/* 129 */       localTexture.dispose();
/* 130 */       this.heapDirty = false;
/*     */     }
/* 132 */     return this.rtt;
/*     */   }
/*     */ 
/*     */   public Graphics createGraphics() {
/* 136 */     this.vramDirty = true;
/* 137 */     return this.rtt.createGraphics();
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 142 */     Graphics localGraphics = createGraphics();
/* 143 */     localGraphics.clear();
/* 144 */     if (this.image != null) {
/* 145 */       IntBuffer localIntBuffer = (IntBuffer)this.image.getPixelBuffer();
/* 146 */       Arrays.fill(localIntBuffer.array(), 0);
/*     */     }
/* 148 */     this.heapDirty = false;
/* 149 */     this.vramDirty = false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.sw.PSWDrawable
 * JD-Core Version:    0.6.2
 */