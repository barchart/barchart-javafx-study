/*    */ package javafx.scene.text;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class FontBuilder
/*    */   implements Builder<Font>
/*    */ {
/*    */   private String name;
/*    */   private double size;
/*    */ 
/*    */   public static FontBuilder create()
/*    */   {
/* 15 */     return new FontBuilder();
/*    */   }
/*    */ 
/*    */   public FontBuilder name(String paramString)
/*    */   {
/* 23 */     this.name = paramString;
/* 24 */     return this;
/*    */   }
/*    */ 
/*    */   public FontBuilder size(double paramDouble)
/*    */   {
/* 32 */     this.size = paramDouble;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public Font build()
/*    */   {
/* 40 */     Font localFont = new Font(this.name, this.size);
/* 41 */     return localFont;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.text.FontBuilder
 * JD-Core Version:    0.6.2
 */