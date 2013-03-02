/*    */ package javafx.scene.transform;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class TranslateBuilder<B extends TranslateBuilder<B>>
/*    */   implements Builder<Translate>
/*    */ {
/*    */   private int __set;
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */ 
/*    */   public static TranslateBuilder<?> create()
/*    */   {
/* 15 */     return new TranslateBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Translate paramTranslate)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramTranslate.setX(this.x);
/* 22 */     if ((i & 0x2) != 0) paramTranslate.setY(this.y);
/* 23 */     if ((i & 0x4) != 0) paramTranslate.setZ(this.z);
/*    */   }
/*    */ 
/*    */   public B x(double paramDouble)
/*    */   {
/* 32 */     this.x = paramDouble;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B y(double paramDouble)
/*    */   {
/* 43 */     this.y = paramDouble;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public B z(double paramDouble)
/*    */   {
/* 54 */     this.z = paramDouble;
/* 55 */     this.__set |= 4;
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   public Translate build()
/*    */   {
/* 63 */     Translate localTranslate = new Translate();
/* 64 */     applyTo(localTranslate);
/* 65 */     return localTranslate;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.transform.TranslateBuilder
 * JD-Core Version:    0.6.2
 */