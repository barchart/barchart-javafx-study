/*    */ package com.sun.javafx.sg;
/*    */ 
/*    */ import com.sun.javafx.geom.BaseBounds;
/*    */ import com.sun.javafx.geom.RectBounds;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.scenario.effect.Effect;
/*    */ 
/*    */ public abstract class BaseNodeEffectInput extends Effect
/*    */ {
/*    */   private BaseNode node;
/* 15 */   private BaseBounds tempBounds = new RectBounds();
/*    */ 
/*    */   public BaseNodeEffectInput() {
/* 18 */     this(null);
/*    */   }
/*    */ 
/*    */   public BaseNodeEffectInput(BaseNode paramBaseNode) {
/* 22 */     setNode(paramBaseNode);
/*    */   }
/*    */ 
/*    */   public BaseNode getNode() {
/* 26 */     return this.node;
/*    */   }
/*    */ 
/*    */   public void setNode(BaseNode paramBaseNode) {
/* 30 */     if (this.node != paramBaseNode) {
/* 31 */       this.node = paramBaseNode;
/* 32 */       flush();
/*    */     }
/*    */   }
/*    */ 
/*    */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*    */   {
/* 42 */     BaseTransform localBaseTransform = paramBaseTransform == null ? BaseTransform.IDENTITY_TRANSFORM : paramBaseTransform;
/*    */ 
/* 44 */     this.tempBounds = this.node.getContentBounds(this.tempBounds, localBaseTransform);
/* 45 */     return this.tempBounds.copy();
/*    */   }
/*    */ 
/*    */   public abstract void flush();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.BaseNodeEffectInput
 * JD-Core Version:    0.6.2
 */