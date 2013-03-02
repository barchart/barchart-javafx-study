/*     */ package com.sun.webpane.sg.prism;
/*     */ 
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.ResourceFactoryListener;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*     */ import com.sun.webpane.platform.graphics.WCGraphicsManager;
/*     */ import com.sun.webpane.platform.graphics.WCPageBackBuffer;
/*     */ 
/*     */ public class WCPageBackBufferImpl extends WCPageBackBuffer
/*     */   implements ResourceFactoryListener
/*     */ {
/*     */   private RTTexture texture;
/*  19 */   private boolean listenerAdded = false;
/*     */ 
/*     */   private RTTexture createTexture(int paramInt1, int paramInt2) {
/*  22 */     RTTexture localRTTexture = GraphicsPipeline.getDefaultResourceFactory().createRTTexture(paramInt1, paramInt2);
/*  23 */     localRTTexture.createGraphics().clear(Color.WHITE);
/*  24 */     return localRTTexture;
/*     */   }
/*     */ 
/*     */   public WCGraphicsContext createGraphics() {
/*  28 */     return WCGraphicsManager.getGraphicsManager().createGraphicsContext(this.texture.createGraphics());
/*     */   }
/*     */ 
/*     */   public void disposeGraphics(WCGraphicsContext paramWCGraphicsContext) {
/*  32 */     paramWCGraphicsContext.dispose();
/*     */   }
/*     */ 
/*     */   public void flush(final WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, final int paramInt3, final int paramInt4) {
/*  36 */     PrismImage local1 = new PrismImage() {
/*     */       public int getWidth() {
/*  38 */         return paramInt3;
/*     */       }
/*     */       public int getHeight() {
/*  41 */         return paramInt4;
/*     */       }
/*     */       public Graphics getGraphics() {
/*  44 */         return (Graphics)paramWCGraphicsContext.getPlatformGraphics();
/*     */       }
/*     */       public Image getImage() {
/*  47 */         throw new UnsupportedOperationException();
/*     */       }
/*     */ 
/*     */       public void draw(Graphics paramAnonymousGraphics, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8)
/*     */       {
/*  53 */         paramAnonymousGraphics.drawTexture(WCPageBackBufferImpl.this.texture, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousInt5, paramAnonymousInt6, paramAnonymousInt7, paramAnonymousInt8);
/*     */       }
/*     */ 
/*     */       public void dispose()
/*     */       {
/*  58 */         throw new UnsupportedOperationException();
/*     */       }
/*     */     };
/*  61 */     paramWCGraphicsContext.drawImage(local1, paramInt1, paramInt2, paramInt3, paramInt4, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   public void copyArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  65 */     RTTexture localRTTexture = createTexture(paramInt3, paramInt4);
/*  66 */     localRTTexture.createGraphics().drawTexture(this.texture, 0.0F, 0.0F, paramInt3, paramInt4, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
/*  67 */     this.texture.createGraphics().drawTexture(localRTTexture, paramInt1 + paramInt5, paramInt2 + paramInt6, paramInt1 + paramInt3 + paramInt5, paramInt2 + paramInt4 + paramInt6, 0.0F, 0.0F, paramInt3, paramInt4);
/*     */ 
/*  69 */     localRTTexture.dispose();
/*     */   }
/*     */ 
/*     */   public boolean validate(int paramInt1, int paramInt2) {
/*  73 */     if (this.texture == null) {
/*  74 */       this.texture = createTexture(paramInt1, paramInt2);
/*  75 */       if (!this.listenerAdded)
/*     */       {
/*  78 */         GraphicsPipeline.getDefaultResourceFactory().addFactoryListener(this);
/*  79 */         this.listenerAdded = true;
/*     */       }
/*     */       else
/*     */       {
/*  83 */         return false;
/*     */       }
/*     */     } else {
/*  86 */       int i = this.texture.getContentWidth();
/*  87 */       int j = this.texture.getContentHeight();
/*  88 */       if ((i != paramInt1) || (j != paramInt2))
/*     */       {
/*  90 */         RTTexture localRTTexture = createTexture(paramInt1, paramInt2);
/*  91 */         localRTTexture.createGraphics().drawTexture(this.texture, 0.0F, 0.0F, Math.min(paramInt1, i), Math.min(paramInt2, j));
/*     */ 
/*  93 */         this.texture.dispose();
/*  94 */         this.texture = localRTTexture;
/*     */       }
/*     */     }
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isValid() {
/* 101 */     return (this.texture != null) || (!this.listenerAdded);
/*     */   }
/*     */ 
/*     */   public void factoryReset() {
/* 105 */     if (this.texture != null) {
/* 106 */       this.texture.dispose();
/* 107 */       this.texture = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void factoryReleased()
/*     */   {
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCPageBackBufferImpl
 * JD-Core Version:    0.6.2
 */