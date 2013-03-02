/*    */ package com.sun.scenario.effect.impl.prism.ps;
/*    */ 
/*    */ import com.sun.glass.ui.Screen;
/*    */ import com.sun.prism.GraphicsPipeline;
/*    */ import com.sun.prism.RTTexture;
/*    */ import com.sun.prism.ResourceFactory;
/*    */ import com.sun.prism.Texture.WrapMode;
/*    */ import com.sun.prism.ps.ShaderGraphics;
/*    */ import com.sun.scenario.effect.impl.prism.PrDrawable;
/*    */ 
/*    */ public class PPSDrawable extends PrDrawable
/*    */ {
/*    */   private RTTexture rtt;
/*    */ 
/*    */   private PPSDrawable(RTTexture paramRTTexture)
/*    */   {
/* 41 */     super(paramRTTexture);
/* 42 */     this.rtt = paramRTTexture;
/*    */   }
/*    */ 
/*    */   static PPSDrawable create(RTTexture paramRTTexture) {
/* 46 */     return new PPSDrawable(paramRTTexture);
/*    */   }
/*    */ 
/*    */   static PPSDrawable create(Screen paramScreen, int paramInt1, int paramInt2) {
/* 50 */     RTTexture localRTTexture = GraphicsPipeline.getPipeline().getResourceFactory(paramScreen).createRTTexture(paramInt1, paramInt2);
/*    */ 
/* 54 */     localRTTexture.setWrapMode(Texture.WrapMode.CLAMP_TO_ZERO);
/* 55 */     return new PPSDrawable(localRTTexture);
/*    */   }
/*    */ 
/*    */   boolean isLost() {
/* 59 */     return (this.rtt == null) || (this.rtt.getNativeDestHandle() == 0L);
/*    */   }
/*    */ 
/*    */   public void flush() {
/* 63 */     if (this.rtt != null) {
/* 64 */       this.rtt.dispose();
/* 65 */       this.rtt = null;
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object getData() {
/* 70 */     return this;
/*    */   }
/*    */ 
/*    */   public int getPhysicalWidth() {
/* 74 */     return this.rtt.getPhysicalWidth();
/*    */   }
/*    */ 
/*    */   public int getPhysicalHeight() {
/* 78 */     return this.rtt.getPhysicalHeight();
/*    */   }
/*    */ 
/*    */   public ShaderGraphics createGraphics() {
/* 82 */     return (ShaderGraphics)this.rtt.createGraphics();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSDrawable
 * JD-Core Version:    0.6.2
 */