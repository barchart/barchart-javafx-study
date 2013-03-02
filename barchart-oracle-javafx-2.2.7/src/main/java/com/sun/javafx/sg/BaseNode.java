/*      */ package com.sun.javafx.sg;
/*      */ 
/*      */ import com.sun.javafx.geom.BaseBounds;
/*      */ import com.sun.javafx.geom.BoxBounds;
/*      */ import com.sun.javafx.geom.RectBounds;
/*      */ import com.sun.javafx.geom.transform.Affine3D;
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ import com.sun.javafx.geom.transform.GeneralTransform3D;
/*      */ import com.sun.scenario.effect.Blend.Mode;
/*      */ import com.sun.scenario.effect.Effect;
/*      */ import java.io.PrintStream;
/*      */ import java.util.List;
/*      */ 
/*      */ public abstract class BaseNode<G>
/*      */   implements PGNode
/*      */ {
/*   44 */   public boolean debug = false;
/*      */ 
/*   52 */   protected static final BaseBounds TEMP_BOUNDS = new BoxBounds();
/*      */ 
/*   62 */   private static boolean CLEAR_DIRTY = true;
/*      */ 
/*   70 */   private BaseTransform transform = BaseTransform.IDENTITY_TRANSFORM;
/*      */ 
/*   78 */   protected BaseBounds transformedBounds = new RectBounds();
/*      */ 
/*   85 */   private BaseBounds contentBounds = new RectBounds();
/*      */ 
/*   94 */   private BaseBounds dirtyBounds = new RectBounds();
/*      */ 
/*  101 */   private boolean visible = true;
/*      */ 
/*  110 */   protected boolean dirty = true;
/*      */   private BaseNode parent;
/*      */   private boolean isClip;
/*      */   private BaseNode clipNode;
/*  133 */   private float opacity = 1.0F;
/*      */   private Blend.Mode nodeBlendMode;
/*  145 */   private boolean depthTest = true;
/*      */   private BaseCacheFilter cacheFilter;
/*      */   private BaseEffectFilter effectFilter;
/*  169 */   protected boolean childDirty = false;
/*      */ 
/*  174 */   protected int dirtyChildrenAccumulated = 0;
/*      */   protected static final int DIRTY_CHILDREN_ACCUMULATED_THRESHOLD = 12;
/*  185 */   protected int cullingBits = 0;
/*      */ 
/*      */   public void setVisible(boolean paramBoolean)
/*      */   {
/*  205 */     if (this.visible != paramBoolean) {
/*  206 */       this.visible = paramBoolean;
/*  207 */       markDirty();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setContentBounds(BaseBounds paramBaseBounds)
/*      */   {
/*  219 */     this.contentBounds = this.contentBounds.deriveWithNewBounds(paramBaseBounds);
/*      */   }
/*      */ 
/*      */   public void setTransformedBounds(BaseBounds paramBaseBounds)
/*      */   {
/*  227 */     if (this.transformedBounds.equals(paramBaseBounds))
/*      */     {
/*  236 */       return;
/*      */     }
/*      */ 
/*  244 */     if (this.dirtyBounds.isEmpty()) {
/*  245 */       this.dirtyBounds = this.dirtyBounds.deriveWithNewBounds(this.transformedBounds);
/*  246 */       this.dirtyBounds = this.dirtyBounds.deriveWithUnion(paramBaseBounds);
/*      */     }
/*      */     else
/*      */     {
/*  250 */       this.dirtyBounds = this.dirtyBounds.deriveWithUnion(this.transformedBounds);
/*      */     }
/*  252 */     this.transformedBounds = this.transformedBounds.deriveWithNewBounds(paramBaseBounds);
/*  253 */     if (hasVisuals())
/*  254 */       markDirty();
/*      */   }
/*      */ 
/*      */   public void setTransformMatrix(BaseTransform paramBaseTransform)
/*      */   {
/*  272 */     this.transform = this.transform.deriveWithNewTransform(paramBaseTransform);
/*  273 */     markDirty();
/*      */   }
/*      */ 
/*      */   public void setClipNode(PGNode paramPGNode)
/*      */   {
/*  285 */     BaseNode localBaseNode = (BaseNode)paramPGNode;
/*  286 */     if (localBaseNode != this.clipNode)
/*      */     {
/*  288 */       if (this.clipNode != null) this.clipNode.setParent(null);
/*      */ 
/*  290 */       if (localBaseNode != null) localBaseNode.setParent(this, true);
/*      */ 
/*  292 */       this.clipNode = localBaseNode;
/*      */ 
/*  294 */       visualsChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setOpacity(float paramFloat)
/*      */   {
/*  305 */     if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
/*  306 */       throw new IllegalArgumentException("Internal Error: The opacity must be between 0 and 1");
/*      */     }
/*      */ 
/*  311 */     if (paramFloat != this.opacity) {
/*  312 */       this.opacity = paramFloat;
/*  313 */       markDirty();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setNodeBlendMode(Blend.Mode paramMode)
/*      */   {
/*  325 */     if (this.nodeBlendMode != paramMode) {
/*  326 */       this.nodeBlendMode = paramMode;
/*  327 */       markDirty();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setDepthTest(boolean paramBoolean)
/*      */   {
/*  339 */     if (paramBoolean != this.depthTest) {
/*  340 */       this.depthTest = paramBoolean;
/*      */ 
/*  342 */       visualsChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setCachedAsBitmap(boolean paramBoolean, PGNode.CacheHint paramCacheHint)
/*      */   {
/*  356 */     if (paramCacheHint == null) {
/*  357 */       throw new IllegalArgumentException("Internal Error: cacheHint must not be null");
/*      */     }
/*      */ 
/*  360 */     if (paramBoolean) {
/*  361 */       if (this.cacheFilter == null) {
/*  362 */         this.cacheFilter = createCacheFilter(this, paramCacheHint);
/*      */ 
/*  370 */         markDirty();
/*      */       }
/*  372 */       else if (!this.cacheFilter.matchesHint(paramCacheHint)) {
/*  373 */         this.cacheFilter.setHint(paramCacheHint);
/*      */ 
/*  382 */         markDirty();
/*      */       }
/*      */ 
/*      */     }
/*  386 */     else if (this.cacheFilter != null) {
/*  387 */       this.cacheFilter.dispose();
/*  388 */       this.cacheFilter = null;
/*      */ 
/*  393 */       markDirty();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setEffect(Object paramObject)
/*      */   {
/*  412 */     if ((this.effectFilter == null) && (paramObject != null)) {
/*  413 */       this.effectFilter = createEffectFilter((Effect)paramObject);
/*  414 */       visualsChanged();
/*  415 */     } else if ((this.effectFilter != null) && (this.effectFilter.getEffect() != paramObject)) {
/*  416 */       this.effectFilter.dispose();
/*  417 */       this.effectFilter = null;
/*  418 */       if (paramObject != null) {
/*  419 */         this.effectFilter = createEffectFilter((Effect)paramObject);
/*      */       }
/*  421 */       visualsChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void effectChanged()
/*      */   {
/*  430 */     visualsChanged();
/*      */   }
/*      */ 
/*      */   public boolean isContentBounds2D()
/*      */   {
/*  438 */     return (this.contentBounds.is2D()) || ((Affine3D.almostZero(this.contentBounds.getMaxZ())) && (Affine3D.almostZero(this.contentBounds.getMinZ())));
/*      */   }
/*      */ 
/*      */   public BaseNode getParent()
/*      */   {
/*  457 */     return this.parent;
/*      */   }
/*      */ 
/*      */   public void setParent(BaseNode paramBaseNode)
/*      */   {
/*  463 */     setParent(paramBaseNode, false);
/*      */   }
/*      */ 
/*      */   private void setParent(BaseNode paramBaseNode, boolean paramBoolean) {
/*  467 */     this.parent = paramBaseNode;
/*  468 */     this.isClip = paramBoolean;
/*      */   }
/*      */   protected final Effect getEffect() {
/*  471 */     return this.effectFilter == null ? null : this.effectFilter.getEffect();
/*      */   }
/*      */ 
/*      */   public final BaseTransform getTransform()
/*      */   {
/*  478 */     return this.transform; } 
/*  479 */   public final float getOpacity() { return this.opacity; } 
/*  480 */   public final Blend.Mode getNodeBlendMode() { return this.nodeBlendMode; } 
/*  481 */   public final boolean isDepthTest() { return this.depthTest; } 
/*  482 */   public final BaseCacheFilter getCacheFilter() { return this.cacheFilter; } 
/*  483 */   public final BaseEffectFilter getEffectFilter() { return this.effectFilter; } 
/*  484 */   public final BaseNode getClipNode() { return this.clipNode; }
/*      */ 
/*      */   public BaseBounds getContentBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform) {
/*  487 */     if (paramBaseTransform.isTranslateOrIdentity()) {
/*  488 */       paramBaseBounds = paramBaseBounds.deriveWithNewBounds(this.contentBounds);
/*  489 */       if (!paramBaseTransform.isIdentity()) {
/*  490 */         float f1 = (float)paramBaseTransform.getMxt();
/*  491 */         float f2 = (float)paramBaseTransform.getMyt();
/*  492 */         float f3 = (float)paramBaseTransform.getMzt();
/*  493 */         paramBaseBounds = paramBaseBounds.deriveWithNewBounds(paramBaseBounds.getMinX() + f1, paramBaseBounds.getMinY() + f2, paramBaseBounds.getMinZ() + f3, paramBaseBounds.getMaxX() + f1, paramBaseBounds.getMaxY() + f2, paramBaseBounds.getMaxZ() + f3);
/*      */       }
/*      */ 
/*  501 */       return paramBaseBounds;
/*      */     }
/*      */ 
/*  506 */     return computeBounds(paramBaseBounds, paramBaseTransform);
/*      */   }
/*      */ 
/*      */   private BaseBounds computeBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*      */   {
/*  533 */     paramBaseBounds = paramBaseBounds.deriveWithNewBounds(this.contentBounds);
/*  534 */     return paramBaseTransform.transform(this.contentBounds, paramBaseBounds);
/*      */   }
/*      */ 
/*      */   public final BaseBounds getClippedBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*      */   {
/*  541 */     BaseBounds localBaseBounds = getEffectBounds(paramBaseBounds, paramBaseTransform);
/*  542 */     if (this.clipNode != null)
/*      */     {
/*  546 */       float f1 = localBaseBounds.getMinX();
/*  547 */       float f2 = localBaseBounds.getMinY();
/*  548 */       float f3 = localBaseBounds.getMinZ();
/*  549 */       float f4 = localBaseBounds.getMaxX();
/*  550 */       float f5 = localBaseBounds.getMaxY();
/*  551 */       float f6 = localBaseBounds.getMaxZ();
/*  552 */       localBaseBounds = this.clipNode.getCompleteBounds(localBaseBounds, paramBaseTransform);
/*  553 */       localBaseBounds.intersectWith(f1, f2, f3, f4, f5, f6);
/*      */     }
/*  555 */     return localBaseBounds;
/*      */   }
/*      */ 
/*      */   public final BaseBounds getEffectBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform) {
/*  559 */     if (this.effectFilter != null) {
/*  560 */       return this.effectFilter.getBounds(paramBaseBounds, paramBaseTransform);
/*      */     }
/*  562 */     return getContentBounds(paramBaseBounds, paramBaseTransform);
/*      */   }
/*      */ 
/*      */   public final BaseBounds getCompleteBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*      */   {
/*  567 */     if (paramBaseTransform.isIdentity()) {
/*  568 */       paramBaseBounds = paramBaseBounds.deriveWithNewBounds(this.transformedBounds);
/*  569 */       return paramBaseBounds;
/*  570 */     }if (this.transform.isIdentity()) {
/*  571 */       return getClippedBounds(paramBaseBounds, paramBaseTransform);
/*      */     }
/*  573 */     double d1 = paramBaseTransform.getMxx();
/*  574 */     double d2 = paramBaseTransform.getMxy();
/*  575 */     double d3 = paramBaseTransform.getMxz();
/*  576 */     double d4 = paramBaseTransform.getMxt();
/*  577 */     double d5 = paramBaseTransform.getMyx();
/*  578 */     double d6 = paramBaseTransform.getMyy();
/*  579 */     double d7 = paramBaseTransform.getMyz();
/*  580 */     double d8 = paramBaseTransform.getMyt();
/*  581 */     double d9 = paramBaseTransform.getMzx();
/*  582 */     double d10 = paramBaseTransform.getMzy();
/*  583 */     double d11 = paramBaseTransform.getMzz();
/*  584 */     double d12 = paramBaseTransform.getMzt();
/*  585 */     BaseTransform localBaseTransform = paramBaseTransform.deriveWithConcatenation(this.transform);
/*  586 */     paramBaseBounds = getClippedBounds(paramBaseBounds, paramBaseTransform);
/*  587 */     if (localBaseTransform == paramBaseTransform) {
/*  588 */       paramBaseTransform.restoreTransform(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12);
/*      */     }
/*      */ 
/*  592 */     return paramBaseBounds;
/*      */   }
/*      */ 
/*      */   protected void visualsChanged()
/*      */   {
/*  607 */     invalidateCache();
/*  608 */     markDirty();
/*      */   }
/*      */ 
/*      */   protected void geometryChanged() {
/*  612 */     invalidateCache();
/*  613 */     if (hasVisuals())
/*  614 */       markDirty();
/*      */   }
/*      */ 
/*      */   public void markDirty()
/*      */   {
/*  629 */     if (!this.dirty) {
/*  630 */       this.dirty = true;
/*  631 */       markTreeDirty();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected final void markTreeDirtyNoIncrement()
/*      */   {
/*  641 */     if ((this.parent != null) && (!this.parent.childDirty))
/*  642 */       markTreeDirty();
/*      */   }
/*      */ 
/*      */   protected void markTreeDirty()
/*      */   {
/*  662 */     BaseNode localBaseNode = this.parent;
/*  663 */     boolean bool = this.isClip;
/*  664 */     while ((localBaseNode != null) && (!localBaseNode.dirty) && ((!localBaseNode.childDirty) || (bool))) {
/*  665 */       if (bool) {
/*  666 */         localBaseNode.dirty = true;
/*      */       } else {
/*  668 */         localBaseNode.childDirty = true;
/*  669 */         localBaseNode.dirtyChildrenAccumulated += 1;
/*      */       }
/*  671 */       localBaseNode.invalidateCache();
/*  672 */       bool = localBaseNode.isClip;
/*  673 */       localBaseNode = localBaseNode.parent;
/*      */     }
/*      */ 
/*  678 */     if ((localBaseNode != null) && (!localBaseNode.dirty) && (!bool)) {
/*  679 */       localBaseNode.dirtyChildrenAccumulated += 1;
/*      */     }
/*      */ 
/*  685 */     if (localBaseNode != null) localBaseNode.invalidateCache();
/*      */   }
/*      */ 
/*      */   protected void clearDirty()
/*      */   {
/*  707 */     this.dirty = false;
/*  708 */     this.childDirty = false;
/*  709 */     this.dirtyBounds.makeEmpty();
/*  710 */     this.dirtyChildrenAccumulated = 0;
/*      */   }
/*      */ 
/*      */   public void clearDirtyTree() {
/*  714 */     clearDirty();
/*  715 */     if (getClipNode() != null) {
/*  716 */       getClipNode().clearDirtyTree();
/*      */     }
/*  718 */     if ((this instanceof PGGroup)) {
/*  719 */       List localList = ((PGGroup)this).getChildren();
/*  720 */       for (int i = 0; i < localList.size(); i++) {
/*  721 */         BaseNode localBaseNode = (BaseNode)localList.get(i);
/*  722 */         if ((localBaseNode.dirty) || (localBaseNode.childDirty))
/*  723 */           localBaseNode.clearDirtyTree();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected final void invalidateCache()
/*      */   {
/*  736 */     if (this.cacheFilter != null) this.cacheFilter.invalidate();
/*      */   }
/*      */ 
/*      */   public int accumulateDirtyRegions(RectBounds paramRectBounds1, RectBounds paramRectBounds2, DirtyRegionPool paramDirtyRegionPool, DirtyRegionContainer paramDirtyRegionContainer, BaseTransform paramBaseTransform, GeneralTransform3D paramGeneralTransform3D)
/*      */   {
/*  795 */     if ((!this.dirty) && (!this.childDirty)) {
/*  796 */       return 1;
/*      */     }
/*      */ 
/*  803 */     if (this.dirty) {
/*  804 */       return accumulateNodeDirtyRegion(paramRectBounds1, paramRectBounds2, paramDirtyRegionContainer, paramBaseTransform, paramGeneralTransform3D);
/*      */     }
/*  806 */     assert (this.childDirty == true);
/*  807 */     return accumulateGroupDirtyRegion(paramRectBounds1, paramRectBounds2, paramDirtyRegionPool, paramDirtyRegionContainer, paramBaseTransform, paramGeneralTransform3D);
/*      */   }
/*      */ 
/*      */   protected int accumulateNodeDirtyRegion(RectBounds paramRectBounds1, RectBounds paramRectBounds2, DirtyRegionContainer paramDirtyRegionContainer, BaseTransform paramBaseTransform, GeneralTransform3D paramGeneralTransform3D)
/*      */   {
/*  823 */     BaseBounds localBaseBounds = computeDirtyRegion(paramRectBounds2, paramBaseTransform, paramGeneralTransform3D);
/*      */ 
/*  828 */     paramRectBounds2.setMinX(localBaseBounds.getMinX());
/*  829 */     paramRectBounds2.setMinY(localBaseBounds.getMinY());
/*  830 */     paramRectBounds2.setMaxX(localBaseBounds.getMaxX());
/*  831 */     paramRectBounds2.setMaxY(localBaseBounds.getMaxY());
/*      */ 
/*  836 */     if ((paramRectBounds2.isEmpty()) || (paramRectBounds1.disjoint(paramRectBounds2))) {
/*  837 */       return 1;
/*      */     }
/*      */ 
/*  841 */     RectBounds localRectBounds = paramDirtyRegionContainer.addDirtyRegion(paramRectBounds2);
/*  842 */     assert (localRectBounds != null);
/*      */ 
/*  844 */     if ((localRectBounds.getMinX() <= paramRectBounds1.getMinX()) && (localRectBounds.getMinY() <= paramRectBounds1.getMinY()) && (localRectBounds.getMaxX() >= paramRectBounds1.getMaxX()) && (localRectBounds.getMaxY() >= paramRectBounds1.getMaxY()))
/*      */     {
/*  848 */       return 0;
/*      */     }
/*      */ 
/*  851 */     localRectBounds.setMinX(Math.max(localRectBounds.getMinX(), paramRectBounds1.getMinX()));
/*  852 */     localRectBounds.setMinY(Math.max(localRectBounds.getMinY(), paramRectBounds1.getMinY()));
/*  853 */     localRectBounds.setMaxX(Math.min(localRectBounds.getMaxX(), paramRectBounds1.getMaxX()));
/*  854 */     localRectBounds.setMaxY(Math.min(localRectBounds.getMaxY(), paramRectBounds1.getMaxY()));
/*  855 */     return 1;
/*      */   }
/*      */ 
/*      */   protected int accumulateGroupDirtyRegion(RectBounds paramRectBounds1, RectBounds paramRectBounds2, DirtyRegionPool paramDirtyRegionPool, DirtyRegionContainer paramDirtyRegionContainer, BaseTransform paramBaseTransform, GeneralTransform3D paramGeneralTransform3D)
/*      */   {
/*  876 */     assert (this.childDirty == true);
/*  877 */     assert (!this.dirty);
/*      */ 
/*  879 */     int i = 1;
/*      */ 
/*  882 */     if (this.effectFilter != null) {
/*  883 */       return accumulateNodeDirtyRegion(paramRectBounds1, paramRectBounds2, paramDirtyRegionContainer, paramBaseTransform, paramGeneralTransform3D);
/*      */     }
/*      */ 
/*  886 */     if (this.dirtyChildrenAccumulated > 12) {
/*  887 */       i = accumulateNodeDirtyRegion(paramRectBounds1, paramRectBounds2, paramDirtyRegionContainer, paramBaseTransform, paramGeneralTransform3D);
/*  888 */       return i;
/*      */     }
/*      */ 
/*  896 */     double d1 = paramBaseTransform.getMxx();
/*  897 */     double d2 = paramBaseTransform.getMxy();
/*  898 */     double d3 = paramBaseTransform.getMxz();
/*  899 */     double d4 = paramBaseTransform.getMxt();
/*      */ 
/*  901 */     double d5 = paramBaseTransform.getMyx();
/*  902 */     double d6 = paramBaseTransform.getMyy();
/*  903 */     double d7 = paramBaseTransform.getMyz();
/*  904 */     double d8 = paramBaseTransform.getMyt();
/*      */ 
/*  906 */     double d9 = paramBaseTransform.getMzx();
/*  907 */     double d10 = paramBaseTransform.getMzy();
/*  908 */     double d11 = paramBaseTransform.getMzz();
/*  909 */     double d12 = paramBaseTransform.getMzt();
/*  910 */     BaseTransform localBaseTransform = paramBaseTransform;
/*  911 */     if (this.transform != null) localBaseTransform = localBaseTransform.deriveWithConcatenation(this.transform);
/*      */ 
/*  927 */     RectBounds localRectBounds = paramRectBounds1;
/*      */ 
/*  931 */     DirtyRegionContainer localDirtyRegionContainer = null;
/*  932 */     if (this.clipNode != null) {
/*  933 */       localDirtyRegionContainer = paramDirtyRegionContainer;
/*  934 */       localRectBounds = new RectBounds();
/*  935 */       localObject1 = this.clipNode.getCompleteBounds(localRectBounds, localBaseTransform);
/*  936 */       paramGeneralTransform3D.transform((BaseBounds)localObject1, (BaseBounds)localObject1);
/*  937 */       localRectBounds.deriveWithNewBounds(((BaseBounds)localObject1).getMinX(), ((BaseBounds)localObject1).getMinY(), 0.0F, ((BaseBounds)localObject1).getMaxX(), ((BaseBounds)localObject1).getMaxY(), 0.0F);
/*      */ 
/*  939 */       localRectBounds.intersectWith(paramRectBounds1);
/*  940 */       paramDirtyRegionContainer = paramDirtyRegionPool.checkOut();
/*      */     }
/*      */ 
/*  945 */     Object localObject1 = ((PGGroup)this).getRemovedChildren();
/*  946 */     if (localObject1 != null)
/*      */     {
/*  948 */       for (j = ((List)localObject1).size() - 1; j >= 0; j--) {
/*  949 */         localObject2 = (BaseNode)((List)localObject1).get(j);
/*  950 */         ((BaseNode)localObject2).dirty = true;
/*  951 */         i = ((BaseNode)localObject2).accumulateDirtyRegions(localRectBounds, paramRectBounds2, paramDirtyRegionPool, paramDirtyRegionContainer, localBaseTransform, paramGeneralTransform3D);
/*      */ 
/*  953 */         if (i == 0)
/*      */         {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/*  959 */     Object localObject2 = ((PGGroup)this).getChildren();
/*  960 */     int j = ((List)localObject2).size();
/*  961 */     for (int k = 0; (k < j) && (i == 1); k++) {
/*  962 */       BaseNode localBaseNode = (BaseNode)((List)localObject2).get(k);
/*      */ 
/*  967 */       i = localBaseNode.accumulateDirtyRegions(localRectBounds, paramRectBounds2, paramDirtyRegionPool, paramDirtyRegionContainer, localBaseTransform, paramGeneralTransform3D);
/*      */ 
/*  969 */       if (i == 0)
/*      */       {
/*      */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  978 */     if (localBaseTransform == paramBaseTransform) {
/*  979 */       paramBaseTransform.restoreTransform(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12);
/*      */     }
/*      */ 
/*  988 */     if (this.clipNode != null) {
/*  989 */       if (i == 0)
/*  990 */         i = accumulateNodeDirtyRegion(paramRectBounds1, paramRectBounds2, localDirtyRegionContainer, paramBaseTransform, paramGeneralTransform3D);
/*      */       else {
/*  992 */         localDirtyRegionContainer.merge(paramDirtyRegionContainer);
/*      */       }
/*  994 */       paramDirtyRegionPool.checkIn(paramDirtyRegionContainer);
/*      */     }
/*  996 */     return i;
/*      */   }
/*      */ 
/*      */   private BaseBounds computeDirtyRegion(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, GeneralTransform3D paramGeneralTransform3D)
/*      */   {
/* 1014 */     if (!this.dirtyBounds.isEmpty()) {
/* 1015 */       paramBaseBounds = paramBaseBounds.deriveWithNewBounds(this.dirtyBounds);
/*      */     }
/*      */     else
/*      */     {
/* 1023 */       paramBaseBounds = paramBaseBounds.deriveWithNewBounds(this.transformedBounds);
/*      */     }
/*      */ 
/* 1028 */     if (!paramBaseBounds.isEmpty())
/*      */     {
/* 1033 */       paramBaseBounds = paramBaseBounds.deriveWithNewBounds(paramBaseBounds.getMinX() - 1.0F, paramBaseBounds.getMinY() - 1.0F, paramBaseBounds.getMinZ(), paramBaseBounds.getMaxX() + 1.0F, paramBaseBounds.getMaxY() + 1.0F, paramBaseBounds.getMaxZ());
/*      */ 
/* 1039 */       paramBaseBounds = paramBaseTransform.transform(paramBaseBounds, paramBaseBounds);
/* 1040 */       paramBaseBounds = paramGeneralTransform3D.transform(paramBaseBounds, paramBaseBounds);
/*      */     }
/* 1042 */     return paramBaseBounds;
/*      */   }
/*      */ 
/*      */   protected boolean hasVisuals()
/*      */   {
/* 1052 */     return true;
/*      */   }
/*      */ 
/*      */   public void doPreCulling(DirtyRegionContainer paramDirtyRegionContainer, BaseTransform paramBaseTransform, GeneralTransform3D paramGeneralTransform3D)
/*      */   {
/* 1069 */     markCullRegions(paramDirtyRegionContainer, -1, paramBaseTransform, paramGeneralTransform3D);
/*      */   }
/*      */ 
/*      */   protected int setCullBits(BaseBounds paramBaseBounds, int paramInt, RectBounds paramRectBounds)
/*      */   {
/* 1080 */     int i = 0;
/* 1081 */     if ((paramRectBounds != null) && (!paramRectBounds.isEmpty()) && 
/* 1082 */       (paramRectBounds.intersects(paramBaseBounds))) {
/* 1083 */       i = 1;
/* 1084 */       if (paramRectBounds.contains(paramBaseBounds.getMinX(), paramBaseBounds.getMinY(), paramBaseBounds.getWidth(), paramBaseBounds.getHeight())) {
/* 1085 */         i = 2;
/*      */       }
/* 1087 */       this.cullingBits |= i << 2 * paramInt;
/*      */     }
/*      */ 
/* 1090 */     return i;
/*      */   }
/*      */ 
/*      */   protected abstract void markCullRegions(DirtyRegionContainer paramDirtyRegionContainer, int paramInt, BaseTransform paramBaseTransform, GeneralTransform3D paramGeneralTransform3D);
/*      */ 
/*      */   public abstract void drawCullBits(G paramG);
/*      */ 
/*      */   public final void render(G paramG)
/*      */   {
/* 1134 */     if (this.debug) System.out.println("render called on " + getClass().getSimpleName());
/*      */ 
/* 1136 */     if (CLEAR_DIRTY) clearDirty();
/*      */ 
/* 1138 */     if ((!this.visible) || (this.opacity == 0.0F)) return;
/*      */ 
/* 1145 */     doRender(paramG);
/*      */   }
/*      */ 
/*      */   protected abstract void doRender(G paramG);
/*      */ 
/*      */   protected abstract BaseCacheFilter createCacheFilter(BaseNode paramBaseNode, PGNode.CacheHint paramCacheHint);
/*      */ 
/*      */   protected abstract BaseEffectFilter createEffectFilter(Effect paramEffect);
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.BaseNode
 * JD-Core Version:    0.6.2
 */