/*    */ package com.sun.scenario.effect.impl.prism;
/*    */ 
/*    */ import com.sun.prism.Image;
/*    */ import com.sun.scenario.effect.Filterable;
/*    */ 
/*    */ public class PrImage
/*    */   implements Filterable
/*    */ {
/*    */   private final Image image;
/*    */ 
/*    */   private PrImage(Image paramImage)
/*    */   {
/* 23 */     this.image = paramImage;
/*    */   }
/*    */ 
/*    */   public static PrImage create(Image paramImage) {
/* 27 */     return new PrImage(paramImage);
/*    */   }
/*    */ 
/*    */   public Image getImage() {
/* 31 */     return this.image;
/*    */   }
/*    */ 
/*    */   public Object getData() {
/* 35 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */ 
/*    */   public int getPhysicalWidth() {
/* 39 */     return this.image.getWidth();
/*    */   }
/*    */ 
/*    */   public int getPhysicalHeight() {
/* 43 */     return this.image.getHeight();
/*    */   }
/*    */ 
/*    */   public void flush() {
/* 47 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.PrImage
 * JD-Core Version:    0.6.2
 */