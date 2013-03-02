/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class LineBuilder<B extends LineBuilder<B>> extends ShapeBuilder<B>
/*    */   implements Builder<Line>
/*    */ {
/*    */   private int __set;
/*    */   private double endX;
/*    */   private double endY;
/*    */   private double startX;
/*    */   private double startY;
/*    */ 
/*    */   public static LineBuilder<?> create()
/*    */   {
/* 15 */     return new LineBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Line paramLine)
/*    */   {
/* 20 */     super.applyTo(paramLine);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramLine.setEndX(this.endX);
/* 23 */     if ((i & 0x2) != 0) paramLine.setEndY(this.endY);
/* 24 */     if ((i & 0x4) != 0) paramLine.setStartX(this.startX);
/* 25 */     if ((i & 0x8) != 0) paramLine.setStartY(this.startY);
/*    */   }
/*    */ 
/*    */   public B endX(double paramDouble)
/*    */   {
/* 34 */     this.endX = paramDouble;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B endY(double paramDouble)
/*    */   {
/* 45 */     this.endY = paramDouble;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B startX(double paramDouble)
/*    */   {
/* 56 */     this.startX = paramDouble;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B startY(double paramDouble)
/*    */   {
/* 67 */     this.startY = paramDouble;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public Line build()
/*    */   {
/* 76 */     Line localLine = new Line();
/* 77 */     applyTo(localLine);
/* 78 */     return localLine;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.LineBuilder
 * JD-Core Version:    0.6.2
 */