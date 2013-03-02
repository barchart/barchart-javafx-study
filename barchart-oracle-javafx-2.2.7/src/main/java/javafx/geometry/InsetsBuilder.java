/*    */ package javafx.geometry;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class InsetsBuilder<B extends InsetsBuilder<B>>
/*    */   implements Builder<Insets>
/*    */ {
/*    */   private double bottom;
/*    */   private double left;
/*    */   private double right;
/*    */   private double top;
/*    */ 
/*    */   public static InsetsBuilder<?> create()
/*    */   {
/* 15 */     return new InsetsBuilder();
/*    */   }
/*    */ 
/*    */   public B bottom(double paramDouble)
/*    */   {
/* 24 */     this.bottom = paramDouble;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B left(double paramDouble)
/*    */   {
/* 34 */     this.left = paramDouble;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B right(double paramDouble)
/*    */   {
/* 44 */     this.right = paramDouble;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public B top(double paramDouble)
/*    */   {
/* 54 */     this.top = paramDouble;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public Insets build()
/*    */   {
/* 62 */     Insets localInsets = new Insets(this.top, this.right, this.bottom, this.left);
/* 63 */     return localInsets;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.InsetsBuilder
 * JD-Core Version:    0.6.2
 */