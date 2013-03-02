/*    */ package com.sun.javafx.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.javafx.sg.PGSVGPath;
/*    */ 
/*    */ public class NGSVGPath extends NGShape
/*    */   implements PGSVGPath
/*    */ {
/*    */   private Shape path;
/*    */ 
/*    */   public void setContent(Object paramObject)
/*    */   {
/* 18 */     this.path = ((Shape)paramObject);
/* 19 */     geometryChanged();
/*    */   }
/*    */ 
/*    */   public Object getGeometry() {
/* 23 */     return this.path;
/*    */   }
/*    */ 
/*    */   public Shape getShape()
/*    */   {
/* 28 */     return this.path;
/*    */   }
/*    */ 
/*    */   public boolean acceptsPath2dOnUpdate() {
/* 32 */     return true;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGSVGPath
 * JD-Core Version:    0.6.2
 */