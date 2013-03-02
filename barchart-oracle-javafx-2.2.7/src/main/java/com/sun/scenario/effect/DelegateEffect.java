/*    */ package com.sun.scenario.effect;
/*    */ 
/*    */ import com.sun.javafx.geom.BaseBounds;
/*    */ import com.sun.javafx.geom.Point2D;
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ 
/*    */ public abstract class DelegateEffect extends Effect
/*    */ {
/*    */   protected DelegateEffect(Effect paramEffect)
/*    */   {
/* 40 */     super(paramEffect);
/*    */   }
/*    */ 
/*    */   protected DelegateEffect(Effect paramEffect1, Effect paramEffect2) {
/* 44 */     super(paramEffect1, paramEffect2);
/*    */   }
/*    */ 
/*    */   protected abstract Effect getDelegate();
/*    */ 
/*    */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*    */   {
/* 58 */     return getDelegate().getBounds(paramBaseTransform, paramEffect);
/*    */   }
/*    */ 
/*    */   public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*    */   {
/* 68 */     return getDelegate().filter(paramFilterContext, paramBaseTransform, paramRectangle, paramObject, paramEffect);
/*    */   }
/*    */ 
/*    */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*    */   {
/* 74 */     return getDelegate().untransform(paramPoint2D, paramEffect);
/*    */   }
/*    */ 
/*    */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*    */   {
/* 79 */     return getDelegate().transform(paramPoint2D, paramEffect);
/*    */   }
/*    */ 
/*    */   public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*    */   {
/* 84 */     return getDelegate().getAccelType(paramFilterContext);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.DelegateEffect
 * JD-Core Version:    0.6.2
 */