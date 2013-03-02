/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class PolygonBuilder<B extends PolygonBuilder<B>> extends ShapeBuilder<B>
/*    */   implements Builder<Polygon>
/*    */ {
/*    */   private boolean __set;
/*    */   private Collection<? extends Double> points;
/*    */ 
/*    */   public static PolygonBuilder<?> create()
/*    */   {
/* 15 */     return new PolygonBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Polygon paramPolygon)
/*    */   {
/* 20 */     super.applyTo(paramPolygon);
/* 21 */     if (this.__set) paramPolygon.getPoints().addAll(this.points);
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
/*    */   public Polygon build()
/*    */   {
/* 46 */     Polygon localPolygon = new Polygon();
/* 47 */     applyTo(localPolygon);
/* 48 */     return localPolygon;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.PolygonBuilder
 * JD-Core Version:    0.6.2
 */