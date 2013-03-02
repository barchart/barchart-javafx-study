/*    */ package com.sun.webpane.sg.prism;
/*    */ 
/*    */ import com.sun.prism.BasicStroke;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.paint.Paint;
/*    */ import com.sun.webpane.platform.graphics.WCStroke;
/*    */ 
/*    */ public final class WCStrokeImpl extends WCStroke<Paint, BasicStroke>
/*    */ {
/*    */   private BasicStroke stroke;
/*    */ 
/*    */   protected void invalidate()
/*    */   {
/* 17 */     this.stroke = null;
/*    */   }
/*    */ 
/*    */   public BasicStroke getPlatformStroke() {
/* 21 */     if (this.stroke == null) {
/* 22 */       int i = getStyle();
/* 23 */       if (i != 0) {
/* 24 */         float f = getThickness();
/* 25 */         float[] arrayOfFloat = getDashSizes();
/* 26 */         if (arrayOfFloat == null) {
/* 27 */           switch (i) {
/*    */           case 2:
/* 29 */             arrayOfFloat = new float[] { f, f };
/* 30 */             break;
/*    */           case 3:
/* 32 */             arrayOfFloat = new float[] { 3.0F * f, 3.0F * f };
/*    */           }
/*    */         }
/*    */ 
/* 36 */         this.stroke = new BasicStroke(f, getLineCap(), getLineJoin(), getMiterLimit(), arrayOfFloat, getDashOffset());
/*    */       }
/*    */     }
/*    */ 
/* 40 */     return this.stroke;
/*    */   }
/*    */ 
/*    */   public boolean apply(Graphics paramGraphics) {
/* 44 */     Paint localPaint = (Paint)getPaint();
/* 45 */     if (localPaint == null) {
/* 46 */       return false;
/*    */     }
/* 48 */     BasicStroke localBasicStroke = getPlatformStroke();
/* 49 */     if (localBasicStroke == null) {
/* 50 */       return false;
/*    */     }
/* 52 */     paramGraphics.setPaint(localPaint);
/* 53 */     paramGraphics.setStroke(localBasicStroke);
/* 54 */     return true;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCStrokeImpl
 * JD-Core Version:    0.6.2
 */