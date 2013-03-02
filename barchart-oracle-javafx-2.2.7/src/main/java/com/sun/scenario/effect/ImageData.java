/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class ImageData
/*     */ {
/*     */   private static HashSet<ImageData> alldatas;
/*     */   private ImageData sharedOwner;
/*     */   private FilterContext fctx;
/*     */   private int refcount;
/*     */   private Filterable image;
/*     */   private final Rectangle bounds;
/*     */   private BaseTransform transform;
/*     */   private Throwable fromwhere;
/*     */ 
/*     */   public ImageData(FilterContext paramFilterContext, Filterable paramFilterable, Rectangle paramRectangle)
/*     */   {
/*  81 */     this(paramFilterContext, paramFilterable, paramRectangle, BaseTransform.IDENTITY_TRANSFORM);
/*     */   }
/*     */ 
/*     */   private ImageData(FilterContext paramFilterContext, Filterable paramFilterable, Rectangle paramRectangle, BaseTransform paramBaseTransform)
/*     */   {
/*  87 */     this.fctx = paramFilterContext;
/*  88 */     this.refcount = 1;
/*  89 */     this.image = paramFilterable;
/*  90 */     this.bounds = paramRectangle;
/*  91 */     this.transform = paramBaseTransform;
/*  92 */     if (alldatas != null) {
/*  93 */       alldatas.add(this);
/*  94 */       this.fromwhere = new Throwable();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ImageData transform(BaseTransform paramBaseTransform) {
/*  99 */     if (paramBaseTransform.isIdentity())
/* 100 */       return this;
/*     */     BaseTransform localBaseTransform;
/* 103 */     if (this.transform.isIdentity())
/* 104 */       localBaseTransform = paramBaseTransform;
/*     */     else {
/* 106 */       localBaseTransform = paramBaseTransform.copy().deriveWithConcatenation(this.transform);
/*     */     }
/* 108 */     return new ImageData(this, localBaseTransform, this.bounds);
/*     */   }
/*     */ 
/*     */   private ImageData(ImageData paramImageData, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 114 */     this(paramImageData.fctx, paramImageData.image, paramRectangle, paramBaseTransform);
/* 115 */     this.sharedOwner = paramImageData;
/*     */   }
/*     */ 
/*     */   public FilterContext getFilterContext() {
/* 119 */     return this.fctx;
/*     */   }
/*     */ 
/*     */   public Filterable getUntransformedImage() {
/* 123 */     return this.image;
/*     */   }
/*     */ 
/*     */   public Rectangle getUntransformedBounds() {
/* 127 */     return this.bounds;
/*     */   }
/*     */ 
/*     */   public BaseTransform getTransform() {
/* 131 */     return this.transform;
/*     */   }
/*     */ 
/*     */   public Filterable getTransformedImage(Rectangle paramRectangle) {
/* 135 */     if ((this.image == null) || (this.fctx == null)) {
/* 136 */       return null;
/*     */     }
/* 138 */     if (this.transform.isIdentity()) {
/* 139 */       return this.image;
/*     */     }
/* 141 */     Rectangle localRectangle = getTransformedBounds(paramRectangle);
/* 142 */     return Renderer.getRenderer(this.fctx).transform(this.fctx, this.image, this.transform, this.bounds, localRectangle);
/*     */   }
/*     */ 
/*     */   public void releaseTransformedImage(Filterable paramFilterable)
/*     */   {
/* 148 */     if ((this.fctx != null) && (paramFilterable != null) && (paramFilterable != this.image))
/* 149 */       Effect.releaseCompatibleImage(this.fctx, paramFilterable);
/*     */   }
/*     */ 
/*     */   public Rectangle getTransformedBounds(Rectangle paramRectangle)
/*     */   {
/* 154 */     if (this.transform.isIdentity()) {
/* 155 */       return this.bounds;
/*     */     }
/* 157 */     Rectangle localRectangle = new Rectangle();
/* 158 */     this.transform.transform(this.bounds, localRectangle);
/* 159 */     if (paramRectangle != null) {
/* 160 */       localRectangle.intersectWith(paramRectangle);
/*     */     }
/* 162 */     return localRectangle;
/*     */   }
/*     */ 
/*     */   public int getReferenceCount() {
/* 166 */     return this.refcount;
/*     */   }
/*     */ 
/*     */   public void addref() {
/* 170 */     this.refcount += 1;
/*     */   }
/*     */ 
/*     */   public void unref() {
/* 174 */     if (--this.refcount == 0) {
/* 175 */       if (this.sharedOwner != null) {
/* 176 */         this.sharedOwner.unref();
/* 177 */         this.sharedOwner = null;
/* 178 */       } else if ((this.fctx != null) && (this.image != null)) {
/* 179 */         Effect.releaseCompatibleImage(this.fctx, this.image);
/*     */       }
/*     */ 
/* 182 */       this.fctx = null;
/* 183 */       this.image = null;
/* 184 */       if (alldatas != null)
/* 185 */         alldatas.remove(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean validate(FilterContext paramFilterContext)
/*     */   {
/* 199 */     return (this.image != null) && (Renderer.getRenderer(paramFilterContext).isImageDataCompatible(this));
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  50 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Object run() {
/*  52 */         if (System.getProperty("decora.showleaks") != null) {
/*  53 */           ImageData.access$002(new HashSet());
/*  54 */           Runtime.getRuntime().addShutdownHook(new Thread()
/*     */           {
/*     */             public void run() {
/*  57 */               Iterator localIterator = ImageData.alldatas.iterator();
/*  58 */               while (localIterator.hasNext()) {
/*  59 */                 ImageData localImageData = (ImageData)localIterator.next();
/*  60 */                 Rectangle localRectangle = localImageData.getUntransformedBounds();
/*  61 */                 System.out.println("id[" + localRectangle.width + "x" + localRectangle.height + ", refcount=" + localImageData.refcount + "] leaked from:");
/*  62 */                 localImageData.fromwhere.printStackTrace(System.out);
/*     */               }
/*     */             }
/*     */           });
/*     */         }
/*  67 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.ImageData
 * JD-Core Version:    0.6.2
 */