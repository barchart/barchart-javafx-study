/*    */ package javafx.scene;
/*    */ 
/*    */ import com.sun.javafx.geom.CameraImpl;
/*    */ import com.sun.javafx.tk.Toolkit;
/*    */ 
/*    */ public class ParallelCamera extends Camera
/*    */ {
/*    */   CameraImpl createPlatformCamera()
/*    */   {
/* 49 */     return Toolkit.getToolkit().createParallelCamera();
/*    */   }
/*    */ 
/*    */   void update()
/*    */   {
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.ParallelCamera
 * JD-Core Version:    0.6.2
 */