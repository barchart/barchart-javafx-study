/*    */ package com.sun.javafx.sg;
/*    */ 
/*    */ import com.sun.javafx.geom.BaseBounds;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.scenario.effect.Blend.Mode;
/*    */ 
/*    */ public abstract interface PGNode
/*    */ {
/*    */   public abstract void setTransformMatrix(BaseTransform paramBaseTransform);
/*    */ 
/*    */   public abstract void setContentBounds(BaseBounds paramBaseBounds);
/*    */ 
/*    */   public abstract void setTransformedBounds(BaseBounds paramBaseBounds);
/*    */ 
/*    */   public abstract void setVisible(boolean paramBoolean);
/*    */ 
/*    */   public abstract void setOpacity(float paramFloat);
/*    */ 
/*    */   public abstract void setNodeBlendMode(Blend.Mode paramMode);
/*    */ 
/*    */   public abstract void setDepthTest(boolean paramBoolean);
/*    */ 
/*    */   public abstract void setClipNode(PGNode paramPGNode);
/*    */ 
/*    */   public abstract void setCachedAsBitmap(boolean paramBoolean, CacheHint paramCacheHint);
/*    */ 
/*    */   public abstract void setEffect(Object paramObject);
/*    */ 
/*    */   public abstract void effectChanged();
/*    */ 
/*    */   public static enum CacheHint
/*    */   {
/* 30 */     DEFAULT, 
/* 31 */     SCALE, 
/* 32 */     ROTATE, 
/* 33 */     SCALE_AND_ROTATE;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.PGNode
 * JD-Core Version:    0.6.2
 */