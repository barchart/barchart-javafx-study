/*    */ package javafx.scene.canvas;
/*    */ 
/*    */ import javafx.scene.NodeBuilder;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class CanvasBuilder<B extends CanvasBuilder<B>> extends NodeBuilder<B>
/*    */   implements Builder<Canvas>
/*    */ {
/*    */   private int __set;
/*    */   private double height;
/*    */   private double width;
/*    */ 
/*    */   public static CanvasBuilder<?> create()
/*    */   {
/* 15 */     return new CanvasBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Canvas paramCanvas)
/*    */   {
/* 20 */     super.applyTo(paramCanvas);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramCanvas.setHeight(this.height);
/* 23 */     if ((i & 0x2) != 0) paramCanvas.setWidth(this.width);
/*    */   }
/*    */ 
/*    */   public B height(double paramDouble)
/*    */   {
/* 32 */     this.height = paramDouble;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B width(double paramDouble)
/*    */   {
/* 43 */     this.width = paramDouble;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public Canvas build()
/*    */   {
/* 52 */     Canvas localCanvas = new Canvas();
/* 53 */     applyTo(localCanvas);
/* 54 */     return localCanvas;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.canvas.CanvasBuilder
 * JD-Core Version:    0.6.2
 */