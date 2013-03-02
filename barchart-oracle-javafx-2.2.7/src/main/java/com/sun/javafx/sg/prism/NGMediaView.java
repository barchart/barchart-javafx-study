/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.sg.PGMediaView;
/*     */ import com.sun.javafx.sg.PGMediaView.MediaFrameTracker;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.Texture;
/*     */ import javafx.scene.media.Media;
/*     */ import javafx.scene.media.MediaPlayer;
/*     */ 
/*     */ public class NGMediaView extends NGNode
/*     */   implements PGMediaView
/*     */ {
/*  20 */   private boolean smooth = true;
/*  21 */   private final RectBounds dimension = new RectBounds();
/*  22 */   private final RectBounds viewport = new RectBounds();
/*     */   private PrismMediaFrameHandler handler;
/*     */   private MediaPlayer player;
/*     */   private PGMediaView.MediaFrameTracker frameTracker;
/*     */ 
/*     */   public void renderNextFrame()
/*     */   {
/*  28 */     visualsChanged();
/*     */   }
/*     */ 
/*     */   public boolean isSmooth() {
/*  32 */     return this.smooth;
/*     */   }
/*     */ 
/*     */   public void setSmooth(boolean paramBoolean) {
/*  36 */     if (paramBoolean != this.smooth) {
/*  37 */       this.smooth = paramBoolean;
/*  38 */       visualsChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setX(float paramFloat) {
/*  43 */     if (paramFloat != this.dimension.getMinX()) {
/*  44 */       float f = this.dimension.getWidth();
/*  45 */       this.dimension.setMinX(paramFloat);
/*  46 */       this.dimension.setMaxX(paramFloat + f);
/*  47 */       geometryChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setY(float paramFloat) {
/*  52 */     if (paramFloat != this.dimension.getMinY()) {
/*  53 */       float f = this.dimension.getHeight();
/*  54 */       this.dimension.setMinY(paramFloat);
/*  55 */       this.dimension.setMaxY(paramFloat + f);
/*  56 */       geometryChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setMediaProvider(Object paramObject) {
/*  61 */     if (paramObject == null) {
/*  62 */       this.player = null;
/*  63 */       this.handler = null;
/*  64 */       geometryChanged();
/*  65 */     } else if ((paramObject instanceof MediaPlayer)) {
/*  66 */       this.player = ((MediaPlayer)paramObject);
/*  67 */       this.handler = PrismMediaFrameHandler.getHandler(this.player);
/*  68 */       geometryChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setViewport(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean)
/*     */   {
/*  76 */     float f1 = 0.0F;
/*  77 */     float f2 = 0.0F;
/*  78 */     float f3 = paramFloat1;
/*  79 */     float f4 = paramFloat2;
/*     */ 
/*  82 */     if (null != this.player) {
/*  83 */       Media localMedia = this.player.getMedia();
/*  84 */       f1 = localMedia.getWidth();
/*  85 */       f2 = localMedia.getHeight();
/*     */     }
/*     */ 
/*  88 */     if ((paramFloat5 > 0.0F) && (paramFloat6 > 0.0F)) {
/*  89 */       this.viewport.setBounds(paramFloat3, paramFloat4, paramFloat3 + paramFloat5, paramFloat4 + paramFloat6);
/*  90 */       f1 = paramFloat5;
/*  91 */       f2 = paramFloat6;
/*     */     } else {
/*  93 */       this.viewport.setBounds(0.0F, 0.0F, f1, f2);
/*     */     }
/*  95 */     if ((paramFloat1 <= 0.0F) && (paramFloat2 <= 0.0F)) {
/*  96 */       f3 = f1;
/*  97 */       f4 = f2;
/*  98 */     } else if (paramBoolean)
/*     */     {
/* 100 */       if (paramFloat1 <= 0.0D) {
/* 101 */         f3 = f2 > 0.0F ? f1 * (paramFloat2 / f2) : 0.0F;
/* 102 */         f4 = paramFloat2;
/* 103 */       } else if (paramFloat2 <= 0.0D) {
/* 104 */         f3 = paramFloat1;
/* 105 */         f4 = f1 > 0.0F ? f2 * (paramFloat1 / f1) : 0.0F;
/*     */       } else {
/* 107 */         if (f1 == 0.0F) f1 = paramFloat1;
/* 108 */         if (f2 == 0.0F) f2 = paramFloat2;
/* 109 */         float f5 = Math.min(paramFloat1 / f1, paramFloat2 / f2);
/* 110 */         f3 = f1 * f5;
/* 111 */         f4 = f2 * f5;
/*     */       }
/* 113 */     } else if (paramFloat2 <= 0.0D) {
/* 114 */       f4 = f2;
/* 115 */     } else if (paramFloat1 <= 0.0D) {
/* 116 */       f3 = f1;
/*     */     }
/* 118 */     if (f4 < 1.0F) {
/* 119 */       f4 = 1.0F;
/*     */     }
/* 121 */     if (f3 < 1.0F) {
/* 122 */       f3 = 1.0F;
/*     */     }
/* 124 */     this.dimension.setMaxX(this.dimension.getMinX() + f3);
/* 125 */     this.dimension.setMaxY(this.dimension.getMinY() + f4);
/* 126 */     geometryChanged();
/*     */   }
/*     */ 
/*     */   protected void renderContent(Graphics paramGraphics)
/*     */   {
/* 131 */     if ((null == this.handler) || (null == this.player)) {
/* 132 */       return;
/*     */     }
/* 134 */     Texture localTexture = this.handler.getTexture(paramGraphics, this.player.impl_getLatestFrame());
/* 135 */     if (localTexture != null) {
/* 136 */       float f1 = this.viewport.getWidth();
/* 137 */       float f2 = this.viewport.getHeight();
/* 138 */       int i = !this.dimension.isEmpty() ? 1 : 0;
/* 139 */       int j = (i != 0) && ((f1 != this.dimension.getWidth()) || (f2 != this.dimension.getHeight())) ? 1 : 0;
/*     */ 
/* 142 */       paramGraphics.translate(this.dimension.getMinX(), this.dimension.getMinY());
/*     */ 
/* 144 */       if ((j != 0) && (f1 != 0.0F) && (f2 != 0.0F)) {
/* 145 */         f3 = this.dimension.getWidth() / f1;
/* 146 */         f4 = this.dimension.getHeight() / f2;
/* 147 */         paramGraphics.scale(f3, f4);
/*     */       }
/*     */ 
/* 150 */       float f3 = this.viewport.getMinX();
/* 151 */       float f4 = this.viewport.getMinY();
/* 152 */       float f5 = f3 + f1;
/* 153 */       float f6 = f4 + f2;
/*     */ 
/* 155 */       paramGraphics.drawTexture(localTexture, 0.0F, 0.0F, f1, f2, f3, f4, f5, f6);
/*     */ 
/* 159 */       if (null != this.frameTracker)
/* 160 */         this.frameTracker.incrementRenderedFrameCount(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean hasOverlappingContents()
/*     */   {
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */   public void setFrameTracker(PGMediaView.MediaFrameTracker paramMediaFrameTracker) {
/* 171 */     this.frameTracker = paramMediaFrameTracker;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGMediaView
 * JD-Core Version:    0.6.2
 */