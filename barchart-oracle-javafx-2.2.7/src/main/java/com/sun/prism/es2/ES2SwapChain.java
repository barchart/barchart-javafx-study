/*     */ package com.sun.prism.es2;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.prism.GraphicsResource;
/*     */ import com.sun.prism.Presentable;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.es2.gl.GLContext;
/*     */ import com.sun.prism.es2.gl.GLDrawable;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.prism.impl.VertexBuffer;
/*     */ 
/*     */ class ES2SwapChain
/*     */   implements Presentable, GraphicsResource
/*     */ {
/*     */   private final ES2Context context;
/*     */   private final View view;
/*     */   private GLDrawable drawable;
/*     */   private boolean needsResize;
/*  26 */   private boolean opaque = false;
/*     */   private int w;
/*     */   private int h;
/*  30 */   long nativeDestHandle = 0L;
/*     */   private RTTexture stableBackbuffer;
/*     */   private boolean copyFullBuffer;
/*     */ 
/*     */   public boolean recreateOnResize()
/*     */   {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isOpaque() {
/*  53 */     if (this.stableBackbuffer != null) {
/*  54 */       return this.stableBackbuffer.isOpaque();
/*     */     }
/*  56 */     return this.opaque;
/*     */   }
/*     */ 
/*     */   public void setOpaque(boolean paramBoolean)
/*     */   {
/*  61 */     if (this.stableBackbuffer != null)
/*  62 */       this.stableBackbuffer.setOpaque(paramBoolean);
/*     */     else
/*  64 */       this.opaque = paramBoolean;
/*     */   }
/*     */ 
/*     */   void setNeedsResize(boolean paramBoolean)
/*     */   {
/*  74 */     this.needsResize = paramBoolean;
/*     */   }
/*     */ 
/*     */   ES2SwapChain(ES2Context paramES2Context, View paramView) {
/*  78 */     this.context = paramES2Context;
/*  79 */     this.view = paramView;
/*     */   }
/*     */ 
/*     */   public boolean prepare(Rectangle paramRectangle) {
/*     */     try {
/*  84 */       ES2Graphics localES2Graphics = ES2Graphics.create(this.context, this);
/*  85 */       if (this.stableBackbuffer != null) {
/*  86 */         if (this.needsResize) {
/*  87 */           localES2Graphics.forceRenderTarget();
/*  88 */           this.needsResize = false;
/*     */         }
/*     */ 
/*  92 */         if ((paramRectangle == null) || (this.copyFullBuffer)) {
/*  93 */           this.w = getPhysicalWidth();
/*  94 */           this.h = getPhysicalHeight();
/*  95 */           localES2Graphics.copyTexture(this.stableBackbuffer, 0.0F, 0.0F, this.w, this.h, 0.0F, 0.0F, this.w, this.h);
/*  96 */           this.copyFullBuffer = false;
/*     */         } else {
/*  98 */           localES2Graphics.copyTexture(this.stableBackbuffer, paramRectangle.x, paramRectangle.y, paramRectangle.x + paramRectangle.width, paramRectangle.y + paramRectangle.height, paramRectangle.x, paramRectangle.y, paramRectangle.x + paramRectangle.width, paramRectangle.y + paramRectangle.height);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 103 */       this.context.getVertexBuffer().flush();
/* 104 */       this.drawable = this.context.getCurrentRenderingContext().getDrawable();
/* 105 */       return this.drawable != null;
/*     */     } catch (Throwable localThrowable) {
/* 107 */       if (PrismSettings.verbose)
/* 108 */         localThrowable.printStackTrace();
/*     */     }
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean present()
/*     */   {
/* 115 */     this.drawable.swapBuffers(this.context.getGLContext());
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */   public ES2Graphics createGraphics() {
/* 120 */     GLContext localGLContext = this.context.getGLContext();
/* 121 */     this.nativeDestHandle = localGLContext.getBoundFBO();
/*     */ 
/* 123 */     this.needsResize = ((this.w != getPhysicalWidth()) || (this.h != getPhysicalHeight()));
/*     */ 
/* 125 */     if ((this.stableBackbuffer == null) || (this.needsResize))
/*     */     {
/* 129 */       if (this.stableBackbuffer != null) {
/* 130 */         this.stableBackbuffer.dispose();
/* 131 */         this.stableBackbuffer = null;
/*     */       }
/*     */       else
/*     */       {
/* 137 */         ES2Graphics.create(this.context, this);
/*     */       }
/* 139 */       this.w = getPhysicalWidth();
/* 140 */       this.h = getPhysicalHeight();
/* 141 */       this.stableBackbuffer = this.context.getResourceFactory().createRTTexture(this.w, this.h);
/*     */ 
/* 143 */       this.copyFullBuffer = true;
/*     */     }
/* 145 */     return ES2Graphics.create(this.context, this.stableBackbuffer);
/*     */   }
/*     */ 
/*     */   public long getNativeDestHandle() {
/* 149 */     return this.nativeDestHandle;
/*     */   }
/*     */ 
/*     */   public Screen getAssociatedScreen() {
/* 153 */     return this.context.getAssociatedScreen();
/*     */   }
/*     */ 
/*     */   public int getPhysicalWidth() {
/* 157 */     return this.view.getWidth();
/*     */   }
/*     */ 
/*     */   public int getPhysicalHeight() {
/* 161 */     return this.view.getHeight();
/*     */   }
/*     */ 
/*     */   public int getContentX() {
/* 165 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getContentY() {
/* 169 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getContentWidth() {
/* 173 */     return this.view.getWidth();
/*     */   }
/*     */ 
/*     */   public int getContentHeight() {
/* 177 */     return this.view.getHeight();
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 182 */     if (this.stableBackbuffer != null) {
/* 183 */       this.stableBackbuffer.dispose();
/* 184 */       this.stableBackbuffer = null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.ES2SwapChain
 * JD-Core Version:    0.6.2
 */