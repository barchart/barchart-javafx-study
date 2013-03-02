/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.GeneralTransform3D;
/*     */ import com.sun.javafx.sg.BaseNode;
/*     */ import com.sun.javafx.sg.DirtyRegionContainer;
/*     */ import com.sun.javafx.sg.PGGroup;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.scenario.effect.Blend;
/*     */ import com.sun.scenario.effect.Blend.Mode;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.prism.PrDrawable;
/*     */ import com.sun.scenario.effect.impl.prism.PrEffectHelper;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public class NGGroup extends NGNode
/*     */   implements PGGroup
/*     */ {
/*  35 */   private Blend.Mode blendMode = Blend.Mode.SRC_OVER;
/*     */ 
/*  41 */   private List<PGNode> children = new ArrayList(1);
/*  42 */   private List<PGNode> unmod = Collections.unmodifiableList(this.children);
/*     */   private List<PGNode> removed;
/*     */   private static final int REGION_INTERSECTS_MASK = 357913941;
/*  53 */   private final RectBounds TEMP_CULLING_BOUNDS = new RectBounds();
/*     */ 
/*     */   public List<PGNode> getChildren()
/*     */   {
/*  64 */     return this.unmod;
/*     */   }
/*     */ 
/*     */   public void add(int paramInt, PGNode paramPGNode)
/*     */   {
/*  74 */     if ((paramInt < -1) || (paramInt > this.children.size())) {
/*  75 */       throw new IndexOutOfBoundsException("invalid index");
/*     */     }
/*     */ 
/*  82 */     BaseNode localBaseNode1 = (BaseNode)paramPGNode;
/*  83 */     BaseNode localBaseNode2 = localBaseNode1.getParent();
/*     */ 
/*  98 */     localBaseNode1.setParent(this);
/*  99 */     this.childDirty = true;
/* 100 */     if (paramInt == -1)
/* 101 */       this.children.add(paramPGNode);
/*     */     else {
/* 103 */       this.children.add(paramInt, paramPGNode);
/*     */     }
/* 105 */     localBaseNode1.markDirty();
/* 106 */     markTreeDirtyNoIncrement();
/* 107 */     geometryChanged();
/*     */   }
/*     */ 
/*     */   public void clearFrom(int paramInt) {
/* 111 */     if (paramInt < this.children.size()) {
/* 112 */       this.children.subList(paramInt, this.children.size()).clear();
/* 113 */       geometryChanged();
/* 114 */       this.childDirty = true;
/* 115 */       markTreeDirtyNoIncrement();
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<PGNode> getRemovedChildren() {
/* 120 */     return this.removed;
/*     */   }
/*     */ 
/*     */   public void addToRemoved(PGNode paramPGNode) {
/* 124 */     if (this.removed == null) this.removed = new ArrayList();
/* 125 */     if (this.dirtyChildrenAccumulated > 12) {
/* 126 */       return;
/*     */     }
/*     */ 
/* 129 */     this.removed.add(paramPGNode);
/* 130 */     this.dirtyChildrenAccumulated += 1;
/*     */ 
/* 132 */     if (this.dirtyChildrenAccumulated > 12)
/* 133 */       this.removed.clear();
/*     */   }
/*     */ 
/*     */   protected void clearDirty()
/*     */   {
/* 139 */     super.clearDirty();
/* 140 */     if (this.removed != null) this.removed.clear();
/*     */   }
/*     */ 
/*     */   public void remove(PGNode paramPGNode)
/*     */   {
/* 149 */     this.children.remove(paramPGNode);
/* 150 */     geometryChanged();
/* 151 */     this.childDirty = true;
/* 152 */     markTreeDirtyNoIncrement();
/*     */   }
/*     */ 
/*     */   public void remove(int paramInt) {
/* 156 */     this.children.remove(paramInt);
/* 157 */     geometryChanged();
/* 158 */     this.childDirty = true;
/* 159 */     markTreeDirtyNoIncrement();
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 163 */     this.children.clear();
/* 164 */     this.childDirty = false;
/* 165 */     geometryChanged();
/* 166 */     markTreeDirtyNoIncrement();
/*     */   }
/*     */ 
/*     */   public void setBlendMode(Object paramObject)
/*     */   {
/* 175 */     if (paramObject == null) {
/* 176 */       throw new IllegalArgumentException("Mode must be non-null");
/*     */     }
/*     */ 
/* 180 */     if (this.blendMode != paramObject) {
/* 181 */       this.blendMode = ((Blend.Mode)paramObject);
/* 182 */       visualsChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void renderContent(Graphics paramGraphics)
/*     */   {
/* 188 */     if (this.children == null) {
/* 189 */       return;
/*     */     }
/*     */ 
/* 192 */     if ((this.blendMode == Blend.Mode.SRC_OVER) || (this.children.size() < 2))
/*     */     {
/* 195 */       for (int i = 0; i < this.children.size(); i++)
/*     */       {
/*     */         try {
/* 198 */           localObject1 = (NGNode)this.children.get(i);
/*     */         } catch (Exception localException) {
/* 200 */           localObject1 = null;
/*     */         }
/*     */ 
/* 203 */         if (localObject1 != null) {
/* 204 */           ((NGNode)localObject1).render(paramGraphics);
/*     */         }
/*     */       }
/* 207 */       return;
/*     */     }
/*     */ 
/* 210 */     Blend localBlend = new Blend(this.blendMode, null, null);
/* 211 */     Object localObject1 = getFilterContext(paramGraphics);
/*     */ 
/* 213 */     Object localObject2 = null;
/* 214 */     boolean bool = true;
/*     */     do
/*     */     {
/* 217 */       BaseTransform localBaseTransform = paramGraphics.getTransformNoClone().copy();
/* 218 */       if (localObject2 != null) {
/* 219 */         localObject2.unref();
/* 220 */         localObject2 = null;
/*     */       }
/* 222 */       Rectangle localRectangle1 = PrEffectHelper.getGraphicsClipNoClone(paramGraphics);
/*     */       Object localObject3;
/* 223 */       for (int j = 0; j < this.children.size(); j++) {
/* 224 */         localObject3 = (NGNode)this.children.get(j);
/* 225 */         ImageData localImageData1 = NodeEffectInput.getImageDataForNode((FilterContext)localObject1, (NGNode)localObject3, false, localBaseTransform, localRectangle1);
/*     */ 
/* 227 */         if (localObject2 == null) {
/* 228 */           localObject2 = localImageData1;
/*     */         } else {
/* 230 */           ImageData localImageData2 = localBlend.filterImageDatas((FilterContext)localObject1, localBaseTransform, localRectangle1, new ImageData[] { localObject2, localImageData1 });
/*     */ 
/* 232 */           localObject2.unref();
/* 233 */           localImageData1.unref();
/* 234 */           localObject2 = localImageData2;
/*     */         }
/*     */       }
/* 237 */       if ((localObject2 != null) && ((bool = localObject2.validate((FilterContext)localObject1)))) {
/* 238 */         Rectangle localRectangle2 = localObject2.getUntransformedBounds();
/* 239 */         localObject3 = (PrDrawable)localObject2.getUntransformedImage();
/* 240 */         paramGraphics.setTransform(localObject2.getTransform());
/* 241 */         paramGraphics.drawTexture(((PrDrawable)localObject3).getTextureObject(), localRectangle2.x, localRectangle2.y, localRectangle2.width, localRectangle2.height);
/*     */       }
/*     */     }
/* 244 */     while ((localObject2 == null) || (!bool));
/*     */ 
/* 246 */     if (localObject2 != null)
/* 247 */       localObject2.unref();
/*     */   }
/*     */ 
/*     */   protected boolean hasOverlappingContents()
/*     */   {
/* 253 */     if (this.blendMode != Blend.Mode.SRC_OVER)
/*     */     {
/* 255 */       return false;
/*     */     }
/* 257 */     int i = this.children == null ? 0 : this.children.size();
/* 258 */     if (i == 1) {
/* 259 */       return ((NGNode)this.children.get(0)).hasOverlappingContents();
/*     */     }
/* 261 */     return i != 0;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/* 265 */     return (this.children == null) || (this.children.isEmpty());
/*     */   }
/*     */ 
/*     */   protected boolean hasVisuals()
/*     */   {
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean needsBlending()
/*     */   {
/* 276 */     Blend.Mode localMode = getNodeBlendMode();
/*     */ 
/* 278 */     return localMode != null;
/*     */   }
/*     */ 
/*     */   protected void markCullRegions(DirtyRegionContainer paramDirtyRegionContainer, int paramInt, BaseTransform paramBaseTransform, GeneralTransform3D paramGeneralTransform3D)
/*     */   {
/* 294 */     super.markCullRegions(paramDirtyRegionContainer, paramInt, paramBaseTransform, paramGeneralTransform3D);
/*     */ 
/* 299 */     if ((this.cullingBits == -1) || ((this.cullingBits != 0) && ((this.cullingBits & 0x15555555) != 0)))
/*     */     {
/* 301 */       double d1 = paramBaseTransform.getMxx();
/* 302 */       double d2 = paramBaseTransform.getMxy();
/* 303 */       double d3 = paramBaseTransform.getMxz();
/* 304 */       double d4 = paramBaseTransform.getMxt();
/*     */ 
/* 306 */       double d5 = paramBaseTransform.getMyx();
/* 307 */       double d6 = paramBaseTransform.getMyy();
/* 308 */       double d7 = paramBaseTransform.getMyz();
/* 309 */       double d8 = paramBaseTransform.getMyt();
/*     */ 
/* 311 */       double d9 = paramBaseTransform.getMzx();
/* 312 */       double d10 = paramBaseTransform.getMzy();
/* 313 */       double d11 = paramBaseTransform.getMzz();
/* 314 */       double d12 = paramBaseTransform.getMzt();
/* 315 */       BaseTransform localBaseTransform = paramBaseTransform.deriveWithConcatenation(getTransform());
/*     */ 
/* 318 */       for (int i = 0; i < this.children.size(); i++) {
/* 319 */         NGNode localNGNode = (NGNode)this.children.get(i);
/* 320 */         localNGNode.markCullRegions(paramDirtyRegionContainer, this.cullingBits, localBaseTransform, paramGeneralTransform3D);
/*     */       }
/*     */ 
/* 327 */       paramBaseTransform.restoreTransform(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void drawCullBits(Graphics paramGraphics)
/*     */   {
/* 333 */     if (getParent() != null) {
/* 334 */       super.drawCullBits(paramGraphics);
/*     */     }
/* 336 */     if (this.cullingBits != 0)
/*     */     {
/* 338 */       BaseTransform localBaseTransform = paramGraphics.getTransformNoClone();
/*     */ 
/* 340 */       double d1 = localBaseTransform.getMxx();
/* 341 */       double d2 = localBaseTransform.getMxy();
/* 342 */       double d3 = localBaseTransform.getMxz();
/* 343 */       double d4 = localBaseTransform.getMxt();
/*     */ 
/* 345 */       double d5 = localBaseTransform.getMyx();
/* 346 */       double d6 = localBaseTransform.getMyy();
/* 347 */       double d7 = localBaseTransform.getMyz();
/* 348 */       double d8 = localBaseTransform.getMyt();
/*     */ 
/* 350 */       double d9 = localBaseTransform.getMzx();
/* 351 */       double d10 = localBaseTransform.getMzy();
/* 352 */       double d11 = localBaseTransform.getMzz();
/* 353 */       double d12 = localBaseTransform.getMzt();
/*     */ 
/* 355 */       paramGraphics.transform(getTransform());
/*     */ 
/* 357 */       for (int i = 0; i < this.children.size(); i++) {
/* 358 */         NGNode localNGNode = (NGNode)this.children.get(i);
/* 359 */         localNGNode.drawCullBits(paramGraphics);
/*     */       }
/*     */ 
/* 362 */       paramGraphics.setTransform3D(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGGroup
 * JD-Core Version:    0.6.2
 */