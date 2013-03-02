/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import com.sun.javafx.geom.Path2D;
/*    */ import com.sun.javafx.sg.PGPath;
/*    */ 
/*    */ public class ClosePath extends PathElement
/*    */ {
/*    */   void addTo(PGPath paramPGPath)
/*    */   {
/* 46 */     paramPGPath.addClosePath();
/*    */   }
/*    */ 
/*    */   @Deprecated
/*    */   public void impl_addTo(Path2D paramPath2D)
/*    */   {
/* 55 */     paramPath2D.closePath();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.ClosePath
 * JD-Core Version:    0.6.2
 */