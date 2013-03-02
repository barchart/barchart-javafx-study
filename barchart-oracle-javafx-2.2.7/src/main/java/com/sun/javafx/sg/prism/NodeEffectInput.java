/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.sg.BaseNodeEffectInput;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.Effect.AccelType;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.prism.PrDrawable;
/*     */ import com.sun.scenario.effect.impl.prism.PrRenderInfo;
/*     */ 
/*     */ public class NodeEffectInput extends BaseNodeEffectInput
/*     */ {
/*     */   private NGNode node;
/*     */   private RenderType renderType;
/*     */   private ImageData cachedIdentityImageData;
/*     */   private ImageData cachedTransformedImageData;
/*     */   private BaseTransform cachedTransform;
/*     */ 
/*     */   public NodeEffectInput(NGNode paramNGNode)
/*     */   {
/*  36 */     this(paramNGNode, RenderType.EFFECT_CONTENT);
/*     */   }
/*     */ 
/*     */   public NodeEffectInput(NGNode paramNGNode, RenderType paramRenderType) {
/*  40 */     super(paramNGNode);
/*  41 */     this.node = paramNGNode;
/*  42 */     this.renderType = paramRenderType;
/*     */   }
/*     */ 
/*     */   static boolean contains(ImageData paramImageData, Rectangle paramRectangle)
/*     */   {
/*  47 */     Rectangle localRectangle = paramImageData.getUntransformedBounds();
/*  48 */     return localRectangle.contains(paramRectangle);
/*     */   }
/*     */ 
/*     */   public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*     */   {
/*  58 */     if ((paramObject instanceof PrRenderInfo)) {
/*  59 */       localObject = ((PrRenderInfo)paramObject).getGraphics();
/*  60 */       if (localObject != null) {
/*  61 */         render((Graphics)localObject, paramBaseTransform);
/*  62 */         return null;
/*     */       }
/*     */     }
/*  65 */     Object localObject = getImageBoundsForNode(this.node, this.renderType, paramBaseTransform, paramRectangle);
/*     */ 
/*  67 */     if (paramBaseTransform.isIdentity()) {
/*  68 */       if ((this.cachedIdentityImageData != null) && (contains(this.cachedIdentityImageData, (Rectangle)localObject)) && (this.cachedIdentityImageData.validate(paramFilterContext)))
/*     */       {
/*  72 */         this.cachedIdentityImageData.addref();
/*  73 */         return this.cachedIdentityImageData;
/*     */       }
/*  75 */     } else if ((this.cachedTransformedImageData != null) && (contains(this.cachedTransformedImageData, (Rectangle)localObject)) && (this.cachedTransformedImageData.validate(paramFilterContext)) && (this.cachedTransform.equals(paramBaseTransform)))
/*     */     {
/*  80 */       this.cachedTransformedImageData.addref();
/*  81 */       return this.cachedTransformedImageData;
/*     */     }
/*     */ 
/*  85 */     ImageData localImageData = getImageDataForBoundedNode(paramFilterContext, this.node, this.renderType, paramBaseTransform, (Rectangle)localObject);
/*     */ 
/*  87 */     if (paramBaseTransform.isIdentity()) {
/*  88 */       flushIdentityImage();
/*  89 */       this.cachedIdentityImageData = localImageData;
/*  90 */       this.cachedIdentityImageData.addref();
/*     */     } else {
/*  92 */       flushTransformedImage();
/*  93 */       this.cachedTransform = paramBaseTransform.copy();
/*  94 */       this.cachedTransformedImageData = localImageData;
/*  95 */       this.cachedTransformedImageData.addref();
/*     */     }
/*  97 */     return localImageData;
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*     */   {
/* 102 */     return Effect.AccelType.INTRINSIC;
/*     */   }
/*     */ 
/*     */   public void flushIdentityImage() {
/* 106 */     if (this.cachedIdentityImageData != null) {
/* 107 */       this.cachedIdentityImageData.unref();
/* 108 */       this.cachedIdentityImageData = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void flushTransformedImage() {
/* 113 */     if (this.cachedTransformedImageData != null) {
/* 114 */       this.cachedTransformedImageData.unref();
/* 115 */       this.cachedTransformedImageData = null;
/*     */     }
/* 117 */     this.cachedTransform = null;
/*     */   }
/*     */ 
/*     */   public void flush() {
/* 121 */     flushIdentityImage();
/* 122 */     flushTransformedImage();
/*     */   }
/*     */ 
/*     */   public void render(Graphics paramGraphics, BaseTransform paramBaseTransform) {
/* 126 */     BaseTransform localBaseTransform = null;
/* 127 */     if (!paramBaseTransform.isIdentity()) {
/* 128 */       localBaseTransform = paramGraphics.getTransformNoClone().copy();
/* 129 */       paramGraphics.transform(paramBaseTransform);
/*     */     }
/* 131 */     this.node.renderContent(paramGraphics);
/* 132 */     if (localBaseTransform != null)
/* 133 */       paramGraphics.setTransform(localBaseTransform);
/*     */   }
/*     */ 
/*     */   static ImageData getImageDataForNode(FilterContext paramFilterContext, NGNode paramNGNode, boolean paramBoolean, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 142 */     RenderType localRenderType = paramBoolean ? RenderType.EFFECT_CONTENT : RenderType.FULL_CONTENT;
/*     */ 
/* 145 */     Rectangle localRectangle = getImageBoundsForNode(paramNGNode, localRenderType, paramBaseTransform, paramRectangle);
/* 146 */     return getImageDataForBoundedNode(paramFilterContext, paramNGNode, localRenderType, paramBaseTransform, localRectangle);
/*     */   }
/*     */ 
/*     */   static Rectangle getImageBoundsForNode(NGNode paramNGNode, RenderType paramRenderType, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 153 */     Object localObject = new RectBounds();
/* 154 */     switch (1.$SwitchMap$com$sun$javafx$sg$prism$NodeEffectInput$RenderType[paramRenderType.ordinal()]) {
/*     */     case 1:
/* 156 */       localObject = paramNGNode.getContentBounds((BaseBounds)localObject, paramBaseTransform);
/* 157 */       break;
/*     */     case 2:
/* 159 */       localObject = paramNGNode.getCompleteBounds((BaseBounds)localObject, paramBaseTransform);
/* 160 */       break;
/*     */     case 3:
/* 162 */       localObject = paramNGNode.getClippedBounds((BaseBounds)localObject, paramBaseTransform);
/*     */     }
/*     */ 
/* 165 */     Rectangle localRectangle = new Rectangle((BaseBounds)localObject);
/* 166 */     if (paramRectangle != null) {
/* 167 */       localRectangle.intersectWith(paramRectangle);
/*     */     }
/* 169 */     return localRectangle;
/*     */   }
/*     */ 
/*     */   private static ImageData getImageDataForBoundedNode(FilterContext paramFilterContext, NGNode paramNGNode, RenderType paramRenderType, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 182 */     PrDrawable localPrDrawable = (PrDrawable)Effect.getCompatibleImage(paramFilterContext, paramRectangle.width, paramRectangle.height);
/*     */ 
/* 184 */     if (localPrDrawable != null) {
/* 185 */       Graphics localGraphics = localPrDrawable.createGraphics();
/* 186 */       localGraphics.translate(-paramRectangle.x, -paramRectangle.y);
/* 187 */       if (paramBaseTransform != null) {
/* 188 */         localGraphics.transform(paramBaseTransform);
/*     */       }
/* 190 */       switch (1.$SwitchMap$com$sun$javafx$sg$prism$NodeEffectInput$RenderType[paramRenderType.ordinal()]) {
/*     */       case 1:
/* 192 */         paramNGNode.renderContent(localGraphics);
/* 193 */         break;
/*     */       case 2:
/* 195 */         paramNGNode.render(localGraphics);
/* 196 */         break;
/*     */       case 3:
/* 198 */         paramNGNode.renderForClip(localGraphics);
/*     */       }
/*     */     }
/*     */ 
/* 202 */     return new ImageData(paramFilterContext, localPrDrawable, paramRectangle);
/*     */   }
/*     */ 
/*     */   public static enum RenderType
/*     */   {
/*  23 */     EFFECT_CONTENT, 
/*  24 */     CLIPPED_CONTENT, 
/*  25 */     FULL_CONTENT;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NodeEffectInput
 * JD-Core Version:    0.6.2
 */