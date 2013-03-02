/*    */ package com.sun.javafx.scene.input;
/*    */ 
/*    */ import javafx.geometry.Point2D;
/*    */ import javafx.scene.Node;
/*    */ 
/*    */ public class InputEventUtils
/*    */ {
/*    */   public static Point2D recomputeCoordinates(Point2D paramPoint2D, Object paramObject1, Object paramObject2)
/*    */   {
/* 46 */     Object localObject1 = (paramObject1 instanceof Node) ? (Node)paramObject1 : null;
/*    */ 
/* 49 */     Object localObject2 = (paramObject2 instanceof Node) ? (Node)paramObject2 : null;
/*    */ 
/* 52 */     double d1 = paramPoint2D.getX();
/* 53 */     double d2 = paramPoint2D.getY();
/*    */     Point2D localPoint2D;
/* 55 */     if (localObject2 != null) {
/* 56 */       if (localObject1 != null) {
/* 57 */         localPoint2D = localObject1.localToScene(d1, d2);
/* 58 */         localPoint2D = localObject2.sceneToLocal(localPoint2D);
/* 59 */         if (localPoint2D != null) {
/* 60 */           d1 = localPoint2D.getX();
/* 61 */           d2 = localPoint2D.getY();
/*    */         } else {
/* 63 */           d1 = (0.0D / 0.0D);
/* 64 */           d2 = (0.0D / 0.0D);
/*    */         }
/*    */       }
/*    */       else
/*    */       {
/* 69 */         localPoint2D = localObject2.sceneToLocal(d1, d2);
/* 70 */         if (localPoint2D != null) {
/* 71 */           d1 = localPoint2D.getX();
/* 72 */           d2 = localPoint2D.getY();
/*    */         } else {
/* 74 */           d1 = (0.0D / 0.0D);
/* 75 */           d2 = (0.0D / 0.0D);
/*    */         }
/*    */       }
/*    */     }
/* 79 */     else if (localObject1 != null)
/*    */     {
/* 81 */       localPoint2D = localObject1.localToScene(d1, d2);
/* 82 */       d1 = localPoint2D.getX();
/* 83 */       d2 = localPoint2D.getY();
/*    */     }
/*    */ 
/* 87 */     return new Point2D(d1, d2);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.input.InputEventUtils
 * JD-Core Version:    0.6.2
 */