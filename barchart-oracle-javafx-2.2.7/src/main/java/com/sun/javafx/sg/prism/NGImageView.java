/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.sg.PGImageView;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.image.CachingCompoundImage;
/*     */ import com.sun.prism.image.CompoundCoords;
/*     */ import com.sun.prism.image.Coords;
/*     */ import com.sun.prism.image.ViewPort;
/*     */ 
/*     */ public class NGImageView extends NGNode
/*     */   implements PGImageView
/*     */ {
/*     */   private Image image;
/*     */   private CachingCompoundImage compoundImage;
/*     */   private CompoundCoords compoundCoords;
/*     */   private float x;
/*     */   private float y;
/*     */   private float w;
/*     */   private float h;
/*     */   private Coords coords;
/*     */   private ViewPort viewport;
/*  32 */   private boolean renderable = false;
/*  33 */   private boolean coordsOK = false;
/*     */   static final int MAX_SIZE_OVERRIDE = 0;
/*     */ 
/*     */   private void invalidate()
/*     */   {
/*  36 */     this.coordsOK = false;
/*  37 */     this.coords = null;
/*  38 */     this.compoundCoords = null;
/*  39 */     geometryChanged();
/*     */   }
/*     */ 
/*     */   public void setViewport(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean)
/*     */   {
/*  46 */     float f1 = this.image != null ? this.image.getWidth() : 0.0F;
/*  47 */     float f2 = this.image != null ? this.image.getHeight() : 0.0F;
/*     */ 
/*  49 */     if ((paramFloat5 > 0.0F) && (paramFloat6 > 0.0F)) {
/*  50 */       this.viewport = new ViewPort(paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*  51 */       f1 = paramFloat5;
/*  52 */       f2 = paramFloat6;
/*     */     } else {
/*  54 */       this.viewport = null;
/*     */     }
/*     */ 
/*  57 */     if ((paramBoolean) && ((paramFloat1 > 0.0F) || (paramFloat2 > 0.0F))) {
/*  58 */       if ((paramFloat1 <= 0.0F) || ((paramFloat2 > 0.0F) && (paramFloat1 * f2 > paramFloat2 * f1))) {
/*  59 */         this.w = (f1 * paramFloat2 / f2);
/*  60 */         this.h = paramFloat2;
/*     */       } else {
/*  62 */         this.w = paramFloat1;
/*  63 */         this.h = (f2 * paramFloat1 / f1);
/*     */       }
/*     */     } else {
/*  66 */       this.w = (paramFloat1 > 0.0F ? paramFloat1 : f1);
/*  67 */       this.h = (paramFloat2 > 0.0F ? paramFloat2 : f2);
/*     */     }
/*     */ 
/*  70 */     invalidate();
/*     */   }
/*     */ 
/*     */   private void calculatePositionAndClipping()
/*     */   {
/*  75 */     this.renderable = false;
/*  76 */     this.coordsOK = true;
/*     */ 
/*  78 */     if (this.viewport == null) {
/*  79 */       this.renderable = (this.image != null);
/*  80 */       return;
/*     */     }
/*     */ 
/*  83 */     float f1 = this.image.getWidth();
/*  84 */     float f2 = this.image.getHeight();
/*  85 */     if ((f1 == 0.0F) || (f2 == 0.0F)) return;
/*     */ 
/*  87 */     this.coords = this.viewport.getClippedCoords(f1, f2, this.w, this.h);
/*  88 */     this.renderable = (this.coords != null);
/*     */   }
/*     */ 
/*     */   protected void doRender(Graphics paramGraphics)
/*     */   {
/*  93 */     if (!this.coordsOK) {
/*  94 */       calculatePositionAndClipping();
/*     */     }
/*  96 */     if (this.renderable)
/*  97 */       super.doRender(paramGraphics);
/*     */   }
/*     */ 
/*     */   private int maxSizeWrapper(ResourceFactory paramResourceFactory)
/*     */   {
/* 104 */     return paramResourceFactory.getMaximumTextureSize();
/*     */   }
/*     */ 
/*     */   protected void renderContent(Graphics paramGraphics)
/*     */   {
/* 109 */     int i = this.image.getWidth();
/* 110 */     int j = this.image.getHeight();
/*     */ 
/* 112 */     ResourceFactory localResourceFactory = paramGraphics.getResourceFactory();
/* 113 */     int k = maxSizeWrapper(localResourceFactory);
/* 114 */     if ((i <= k) && (j <= k)) {
/* 115 */       Texture localTexture = localResourceFactory.getCachedTexture(this.image, false);
/* 116 */       if (this.coords == null)
/* 117 */         paramGraphics.drawTexture(localTexture, this.x, this.y, this.x + this.w, this.y + this.h, 0.0F, 0.0F, i, j, 0);
/*     */       else
/* 119 */         this.coords.draw(localTexture, paramGraphics, this.x, this.y);
/*     */     }
/*     */     else {
/* 122 */       if (this.compoundImage == null) this.compoundImage = new CachingCompoundImage(this.image, k);
/*     */ 
/* 125 */       if (this.coords == null) this.coords = new Coords(this.w, this.h, new ViewPort(0.0F, 0.0F, i, j));
/* 126 */       if (this.compoundCoords == null) this.compoundCoords = new CompoundCoords(this.compoundImage, this.coords);
/* 127 */       this.compoundCoords.draw(paramGraphics, this.compoundImage, this.x, this.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean hasOverlappingContents()
/*     */   {
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */   public void setImage(Object paramObject) {
/* 137 */     Image localImage = (Image)paramObject;
/*     */ 
/* 139 */     if (this.image == localImage) return;
/*     */ 
/* 141 */     int i = (localImage == null) || (this.image == null) || (this.image.getHeight() != localImage.getHeight()) || (this.image.getWidth() != localImage.getWidth()) ? 1 : 0;
/*     */ 
/* 145 */     this.image = localImage;
/* 146 */     this.compoundImage = null;
/*     */ 
/* 148 */     if (i != 0) invalidate(); 
/*     */   }
/*     */ 
/*     */   public void setX(float paramFloat)
/*     */   {
/* 152 */     if (this.x != paramFloat) {
/* 153 */       this.x = paramFloat;
/* 154 */       geometryChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setY(float paramFloat) {
/* 159 */     if (this.y != paramFloat) {
/* 160 */       this.y = paramFloat;
/* 161 */       geometryChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setSmooth(boolean paramBoolean)
/*     */   {
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGImageView
 * JD-Core Version:    0.6.2
 */