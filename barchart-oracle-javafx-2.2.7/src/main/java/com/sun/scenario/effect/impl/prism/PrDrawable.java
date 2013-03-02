/*    */ package com.sun.scenario.effect.impl.prism;
/*    */ 
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.RTTexture;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.Filterable;
/*    */ import com.sun.scenario.effect.impl.Renderer;
/*    */ 
/*    */ public abstract class PrDrawable extends PrTexture
/*    */   implements Filterable
/*    */ {
/*    */   public static PrDrawable create(FilterContext paramFilterContext, RTTexture paramRTTexture)
/*    */   {
/* 38 */     return ((PrRenderer)Renderer.getRenderer(paramFilterContext)).createDrawable(paramRTTexture);
/*    */   }
/*    */ 
/*    */   protected PrDrawable(RTTexture paramRTTexture) {
/* 42 */     super(paramRTTexture);
/*    */   }
/*    */ 
/*    */   public abstract Graphics createGraphics();
/*    */ 
/*    */   public void clear() {
/* 48 */     Graphics localGraphics = createGraphics();
/* 49 */     localGraphics.clear();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.PrDrawable
 * JD-Core Version:    0.6.2
 */