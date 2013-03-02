/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class PolylineBuilder<B extends PolylineBuilder<B>> extends ShapeBuilder<B>
/*    */   implements Builder<Polyline>
/*    */ {
/*    */   private boolean __set;
/*    */   private Collection<? extends Double> points;
/*    */ 
/*    */   public static PolylineBuilder<?> create()
/*    */   {
/* 15 */     return new PolylineBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Polyline paramPolyline)
/*    */   {
/* 20 */     super.applyTo(paramPolyline);
/* 21 */     if (this.__set) paramPolyline.getPoints().addAll(this.points);
/*    */   }
/*    */ 
/*    */   public B points(Collection<? extends Double> paramCollection)
/*    */   {
/* 30 */     this.points = paramCollection;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public B points(Double[] paramArrayOfDouble)
/*    */   {
/* 39 */     return points(Arrays.asList(paramArrayOfDouble));
/*    */   }
/*    */ 
/*    */   public Polyline build()
/*    */   {
/* 46 */     Polyline localPolyline = new Polyline();
/* 47 */     applyTo(localPolyline);
/* 48 */     return localPolyline;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.PolylineBuilder
 * JD-Core Version:    0.6.2
 */