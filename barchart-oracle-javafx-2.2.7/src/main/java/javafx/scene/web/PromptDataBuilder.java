/*    */ package javafx.scene.web;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class PromptDataBuilder<B extends PromptDataBuilder<B>>
/*    */   implements Builder<PromptData>
/*    */ {
/*    */   private String defaultValue;
/*    */   private String message;
/*    */ 
/*    */   public static PromptDataBuilder<?> create()
/*    */   {
/* 15 */     return new PromptDataBuilder();
/*    */   }
/*    */ 
/*    */   public B defaultValue(String paramString)
/*    */   {
/* 24 */     this.defaultValue = paramString;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B message(String paramString)
/*    */   {
/* 34 */     this.message = paramString;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public PromptData build()
/*    */   {
/* 42 */     PromptData localPromptData = new PromptData(this.message, this.defaultValue);
/* 43 */     return localPromptData;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.web.PromptDataBuilder
 * JD-Core Version:    0.6.2
 */