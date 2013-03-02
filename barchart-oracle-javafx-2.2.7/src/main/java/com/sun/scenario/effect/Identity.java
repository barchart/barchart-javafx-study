/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class Identity extends Effect
/*     */ {
/*     */   private Filterable src;
/*  46 */   private Point2D loc = new Point2D();
/*  47 */   private final Map<FilterContext, ImageData> datacache = new HashMap();
/*     */ 
/*     */   public Identity(Filterable paramFilterable)
/*     */   {
/*  57 */     this.src = paramFilterable;
/*     */   }
/*     */ 
/*     */   public final Filterable getSource()
/*     */   {
/*  66 */     return this.src;
/*     */   }
/*     */ 
/*     */   public void setSource(Filterable paramFilterable)
/*     */   {
/*  75 */     Filterable localFilterable = this.src;
/*  76 */     this.src = paramFilterable;
/*  77 */     clearCache();
/*  78 */     firePropertyChange("source", localFilterable, paramFilterable);
/*     */   }
/*     */ 
/*     */   public final Point2D getLocation()
/*     */   {
/*  88 */     return this.loc;
/*     */   }
/*     */ 
/*     */   public void setLocation(Point2D paramPoint2D)
/*     */   {
/*  99 */     if (paramPoint2D == null) {
/* 100 */       throw new IllegalArgumentException("Location must be non-null");
/*     */     }
/* 102 */     Point2D localPoint2D = this.loc;
/* 103 */     this.loc.setLocation(paramPoint2D);
/* 104 */     firePropertyChange("location", localPoint2D, paramPoint2D);
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 111 */     if (this.src == null)
/*     */     {
/* 113 */       return new RectBounds();
/*     */     }
/* 115 */     int i = this.src.getPhysicalWidth();
/* 116 */     int j = this.src.getPhysicalHeight();
/* 117 */     Object localObject = new RectBounds(this.loc.x, this.loc.y, this.loc.x + i, this.loc.y + j);
/* 118 */     if ((paramBaseTransform != null) && (!paramBaseTransform.isIdentity())) {
/* 119 */       localObject = transformBounds(paramBaseTransform, (BaseBounds)localObject);
/*     */     }
/* 121 */     return localObject;
/*     */   }
/*     */ 
/*     */   public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*     */   {
/* 133 */     ImageData localImageData = (ImageData)this.datacache.get(paramFilterContext);
/* 134 */     if (localImageData == null) {
/* 135 */       Renderer localRenderer = Renderer.getRenderer(paramFilterContext);
/* 136 */       Filterable localFilterable = this.src;
/* 137 */       if (localFilterable == null) {
/* 138 */         localFilterable = getCompatibleImage(paramFilterContext, 1, 1);
/* 139 */         localImageData = new ImageData(paramFilterContext, localFilterable, new Rectangle(1, 1));
/*     */       } else {
/* 141 */         localImageData = localRenderer.createImageData(paramFilterContext, localFilterable);
/*     */       }
/* 143 */       if (localImageData == null) {
/* 144 */         return new ImageData(paramFilterContext, null, null);
/*     */       }
/* 146 */       this.datacache.put(paramFilterContext, localImageData);
/*     */     }
/* 148 */     localImageData.addref();
/*     */ 
/* 150 */     paramBaseTransform = Offset.getOffsetTransform(paramBaseTransform, this.loc.x, this.loc.y);
/* 151 */     localImageData = localImageData.transform(paramBaseTransform);
/* 152 */     return localImageData;
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*     */   {
/* 158 */     return Effect.AccelType.INTRINSIC;
/*     */   }
/*     */ 
/*     */   private void clearCache() {
/* 162 */     this.datacache.clear();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Identity
 * JD-Core Version:    0.6.2
 */