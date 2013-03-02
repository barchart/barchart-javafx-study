/*    */ package com.sun.webpane.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.prism.paint.LinearGradient;
/*    */ import com.sun.prism.paint.Stop;
/*    */ import com.sun.webpane.platform.graphics.WCGradient;
/*    */ import com.sun.webpane.platform.graphics.WCPoint;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ final class WCLinearGradient extends WCGradient<LinearGradient>
/*    */ {
/*    */   private final WCPoint p1;
/*    */   private final WCPoint p2;
/* 20 */   private final List<Stop> stops = new ArrayList();
/*    */ 
/*    */   WCLinearGradient(WCPoint paramWCPoint1, WCPoint paramWCPoint2) {
/* 23 */     this.p1 = paramWCPoint1;
/* 24 */     this.p2 = paramWCPoint2;
/*    */   }
/*    */ 
/*    */   public void addStop(int paramInt, float paramFloat)
/*    */   {
/* 29 */     this.stops.add(new Stop(WCGraphicsPrismContext.createColor(paramInt), paramFloat));
/*    */   }
/*    */ 
/*    */   public LinearGradient getPlatformGradient() {
/* 33 */     Collections.sort(this.stops, WCRadialGradient.COMPARATOR);
/* 34 */     return new LinearGradient(this.p1.getX(), this.p1.getY(), this.p2.getX(), this.p2.getY(), BaseTransform.IDENTITY_TRANSFORM, isProportional(), getSpreadMethod() - 1, this.stops);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 47 */     return WCRadialGradient.toString(this, this.p1, this.p2, null, this.stops);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCLinearGradient
 * JD-Core Version:    0.6.2
 */