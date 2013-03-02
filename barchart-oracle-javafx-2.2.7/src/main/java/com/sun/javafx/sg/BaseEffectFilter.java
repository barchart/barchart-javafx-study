/*    */ package com.sun.javafx.sg;
/*    */ 
/*    */ import com.sun.javafx.geom.BaseBounds;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.scenario.effect.Effect;
/*    */ 
/*    */ public abstract class BaseEffectFilter
/*    */ {
/*    */   private Effect effect;
/*    */   private BaseNodeEffectInput nodeInput;
/*    */ 
/*    */   protected BaseEffectFilter(Effect paramEffect, BaseNode paramBaseNode)
/*    */   {
/* 17 */     this.effect = paramEffect;
/* 18 */     this.nodeInput = createNodeEffectInput(paramBaseNode);
/*    */   }
/*    */   public Effect getEffect() {
/* 21 */     return this.effect;
/*    */   }
/* 23 */   public BaseNodeEffectInput getNodeInput() { return this.nodeInput; }
/*    */ 
/*    */   protected void dispose() {
/* 26 */     this.effect = null;
/* 27 */     this.nodeInput.setNode(null);
/* 28 */     this.nodeInput = null;
/*    */   }
/*    */ 
/*    */   public BaseBounds getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform) {
/* 32 */     BaseBounds localBaseBounds = getEffect().getBounds(paramBaseTransform, this.nodeInput);
/* 33 */     return paramBaseBounds.deriveWithNewBounds(localBaseBounds);
/*    */   }
/*    */ 
/*    */   protected abstract BaseNodeEffectInput createNodeEffectInput(BaseNode paramBaseNode);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.BaseEffectFilter
 * JD-Core Version:    0.6.2
 */