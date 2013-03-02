/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class SVGPathBuilder<B extends SVGPathBuilder<B>> extends ShapeBuilder<B>
/*    */   implements Builder<SVGPath>
/*    */ {
/*    */   private int __set;
/*    */   private String content;
/*    */   private FillRule fillRule;
/*    */ 
/*    */   public static SVGPathBuilder<?> create()
/*    */   {
/* 15 */     return new SVGPathBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(SVGPath paramSVGPath)
/*    */   {
/* 20 */     super.applyTo(paramSVGPath);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramSVGPath.setContent(this.content);
/* 23 */     if ((i & 0x2) != 0) paramSVGPath.setFillRule(this.fillRule);
/*    */   }
/*    */ 
/*    */   public B content(String paramString)
/*    */   {
/* 32 */     this.content = paramString;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B fillRule(FillRule paramFillRule)
/*    */   {
/* 43 */     this.fillRule = paramFillRule;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public SVGPath build()
/*    */   {
/* 52 */     SVGPath localSVGPath = new SVGPath();
/* 53 */     applyTo(localSVGPath);
/* 54 */     return localSVGPath;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.SVGPathBuilder
 * JD-Core Version:    0.6.2
 */