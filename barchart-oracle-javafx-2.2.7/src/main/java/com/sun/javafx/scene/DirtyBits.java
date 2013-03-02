/*    */ package com.sun.javafx.scene;
/*    */ 
/*    */ public enum DirtyBits
/*    */ {
/* 32 */   NODE_CACHE, 
/* 33 */   NODE_CLIP, 
/* 34 */   NODE_EFFECT, 
/* 35 */   NODE_OPACITY, 
/* 36 */   NODE_TRANSFORM, 
/* 37 */   NODE_BOUNDS, 
/* 38 */   NODE_VISIBLE, 
/* 39 */   NODE_DEPTH_TEST, 
/* 40 */   NODE_BLENDMODE, 
/* 41 */   NODE_CSS, 
/*    */ 
/* 44 */   NODE_GEOMETRY, 
/* 45 */   NODE_SMOOTH, 
/* 46 */   NODE_VIEWPORT, 
/* 47 */   NODE_CONTENTS, 
/*    */ 
/* 50 */   PARENT_CHILDREN, 
/*    */ 
/* 53 */   SHAPE_FILL, 
/* 54 */   SHAPE_FILLRULE, 
/* 55 */   SHAPE_MODE, 
/* 56 */   SHAPE_STROKE, 
/* 57 */   SHAPE_STROKEATTRS, 
/*    */ 
/* 60 */   REGION_SHAPE, 
/*    */ 
/* 63 */   TEXT_ATTRS, 
/* 64 */   TEXT_FONT, 
/* 65 */   TEXT_SELECTION, 
/* 66 */   TEXT_HELPER, 
/*    */ 
/* 69 */   MEDIAVIEW_MEDIA, 
/*    */ 
/* 72 */   WEBVIEW_VIEW, 
/*    */ 
/* 75 */   EFFECT_EFFECT, 
/*    */ 
/* 80 */   MAX_DIRTY;
/*    */ 
/*    */   private long mask;
/*    */ 
/*    */   private DirtyBits() {
/* 85 */     this.mask = (1 << ordinal());
/*    */   }
/*    */   public final long getMask() {
/* 88 */     return this.mask;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.DirtyBits
 * JD-Core Version:    0.6.2
 */