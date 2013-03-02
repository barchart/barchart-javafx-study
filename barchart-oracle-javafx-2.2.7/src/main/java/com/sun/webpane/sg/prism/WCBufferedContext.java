/*    */ package com.sun.webpane.sg.prism;
/*    */ 
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.webpane.platform.graphics.WCImage;
/*    */ 
/*    */ public class WCBufferedContext extends WCGraphicsPrismContext
/*    */ {
/*    */   private PrismImage img;
/*    */ 
/*    */   public WCBufferedContext(WCImage paramWCImage)
/*    */   {
/* 14 */     this((PrismImage)paramWCImage);
/*    */   }
/*    */ 
/*    */   public WCBufferedContext(PrismImage paramPrismImage) {
/* 18 */     this.img = paramPrismImage;
/* 19 */     setClip(0, 0, paramPrismImage.getWidth(), paramPrismImage.getHeight());
/*    */   }
/*    */ 
/*    */   public WCImage getImage()
/*    */   {
/* 24 */     return this.img;
/*    */   }
/*    */ 
/*    */   public Graphics getGraphics(boolean paramBoolean)
/*    */   {
/* 29 */     init(this.img.getGraphics(), false);
/* 30 */     return super.getGraphics(paramBoolean);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCBufferedContext
 * JD-Core Version:    0.6.2
 */