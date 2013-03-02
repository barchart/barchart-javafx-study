/*    */ package com.sun.prism.image;
/*    */ 
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.Texture;
/*    */ 
/*    */ public class Coords
/*    */ {
/*    */   float x0;
/*    */   float y0;
/*    */   float x1;
/*    */   float y1;
/*    */   float u0;
/*    */   float v0;
/*    */   float u1;
/*    */   float v1;
/*    */   int isomask;
/*    */ 
/*    */   public Coords(float paramFloat1, float paramFloat2, ViewPort paramViewPort)
/*    */   {
/* 19 */     this.x0 = 0.0F; this.x1 = paramFloat1;
/* 20 */     this.y0 = 0.0F; this.y1 = paramFloat2;
/* 21 */     this.u0 = paramViewPort.u0; this.u1 = paramViewPort.u1;
/* 22 */     this.v0 = paramViewPort.v0; this.v1 = paramViewPort.v1;
/* 23 */     this.isomask = 0;
/*    */   }
/*    */ 
/*    */   public Coords() {
/* 27 */     this.isomask = 0;
/*    */   }
/*    */ 
/*    */   public void draw(Texture paramTexture, Graphics paramGraphics, float paramFloat1, float paramFloat2) {
/* 31 */     paramGraphics.drawTexture(paramTexture, paramFloat1 + this.x0, paramFloat2 + this.y0, paramFloat1 + this.x1, paramFloat2 + this.y1, this.u0, this.v0, this.u1, this.v1, this.isomask);
/*    */   }
/*    */ 
/*    */   public float getX(float paramFloat)
/*    */   {
/* 39 */     return (this.x0 * (this.u1 - paramFloat) + this.x1 * (paramFloat - this.u0)) / (this.u1 - this.u0);
/*    */   }
/*    */ 
/*    */   public float getY(float paramFloat)
/*    */   {
/* 44 */     return (this.y0 * (this.v1 - paramFloat) + this.y1 * (paramFloat - this.v0)) / (this.v1 - this.v0);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.image.Coords
 * JD-Core Version:    0.6.2
 */