/*    */ package com.sun.javafx.scene.shape;
/*    */ 
/*    */ import com.sun.javafx.geom.Path2D;
/*    */ import java.util.Collection;
/*    */ import javafx.scene.shape.PathElement;
/*    */ 
/*    */ public final class PathUtils
/*    */ {
/*    */   public static Path2D configShape(Collection<PathElement> paramCollection, boolean paramBoolean)
/*    */   {
/* 42 */     Path2D localPath2D = new Path2D(paramBoolean ? 0 : 1, paramCollection.size());
/*    */ 
/* 45 */     for (PathElement localPathElement : paramCollection) {
/* 46 */       localPathElement.impl_addTo(localPath2D);
/*    */     }
/* 48 */     return localPath2D;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.shape.PathUtils
 * JD-Core Version:    0.6.2
 */