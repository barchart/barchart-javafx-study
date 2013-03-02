/*    */ package javafx.scene.shape;
/*    */ 
/*    */ public abstract class PathElementBuilder<B extends PathElementBuilder<B>>
/*    */ {
/*    */   private boolean __set;
/*    */   private boolean absolute;
/*    */ 
/*    */   public void applyTo(PathElement paramPathElement)
/*    */   {
/* 15 */     if (this.__set) paramPathElement.setAbsolute(this.absolute);
/*    */   }
/*    */ 
/*    */   public B absolute(boolean paramBoolean)
/*    */   {
/* 24 */     this.absolute = paramBoolean;
/* 25 */     this.__set = true;
/* 26 */     return this;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.PathElementBuilder
 * JD-Core Version:    0.6.2
 */