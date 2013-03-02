/*    */ package com.sun.scenario.effect.impl.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ 
/*    */ public class PrTexture
/*    */   implements com.sun.scenario.effect.impl.hw.Texture
/*    */ {
/*    */   private final com.sun.prism.Texture tex;
/*    */   private final Rectangle bounds;
/*    */ 
/*    */   public PrTexture(com.sun.prism.Texture paramTexture)
/*    */   {
/* 38 */     if (paramTexture == null) {
/* 39 */       throw new IllegalArgumentException("Texture must be non-null");
/*    */     }
/* 41 */     this.tex = paramTexture;
/* 42 */     this.bounds = new Rectangle(paramTexture.getPhysicalWidth(), paramTexture.getPhysicalHeight());
/*    */   }
/*    */ 
/*    */   public Rectangle getNativeBounds() {
/* 46 */     return this.bounds;
/*    */   }
/*    */ 
/*    */   public long getNativeSourceHandle() {
/* 50 */     return this.tex.getNativeSourceHandle();
/*    */   }
/*    */ 
/*    */   public com.sun.prism.Texture getTextureObject() {
/* 54 */     return this.tex;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.PrTexture
 * JD-Core Version:    0.6.2
 */