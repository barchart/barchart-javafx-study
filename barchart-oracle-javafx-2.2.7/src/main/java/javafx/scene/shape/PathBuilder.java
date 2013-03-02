/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class PathBuilder<B extends PathBuilder<B>> extends ShapeBuilder<B>
/*    */   implements Builder<Path>
/*    */ {
/*    */   private int __set;
/*    */   private Collection<? extends PathElement> elements;
/*    */   private FillRule fillRule;
/*    */ 
/*    */   public static PathBuilder<?> create()
/*    */   {
/* 15 */     return new PathBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Path paramPath)
/*    */   {
/* 20 */     super.applyTo(paramPath);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramPath.getElements().addAll(this.elements);
/* 23 */     if ((i & 0x2) != 0) paramPath.setFillRule(this.fillRule);
/*    */   }
/*    */ 
/*    */   public B elements(Collection<? extends PathElement> paramCollection)
/*    */   {
/* 32 */     this.elements = paramCollection;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B elements(PathElement[] paramArrayOfPathElement)
/*    */   {
/* 41 */     return elements(Arrays.asList(paramArrayOfPathElement));
/*    */   }
/*    */ 
/*    */   public B fillRule(FillRule paramFillRule)
/*    */   {
/* 50 */     this.fillRule = paramFillRule;
/* 51 */     this.__set |= 2;
/* 52 */     return this;
/*    */   }
/*    */ 
/*    */   public Path build()
/*    */   {
/* 59 */     Path localPath = new Path();
/* 60 */     applyTo(localPath);
/* 61 */     return localPath;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.PathBuilder
 * JD-Core Version:    0.6.2
 */