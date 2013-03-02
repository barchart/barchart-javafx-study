/*    */ package javafx.embed.swt;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class CustomTransferBuilder<B extends CustomTransferBuilder<B>>
/*    */   implements Builder<CustomTransfer>
/*    */ {
/*    */   private String mime;
/*    */   private String name;
/*    */ 
/*    */   public static CustomTransferBuilder<?> create()
/*    */   {
/* 15 */     return new CustomTransferBuilder();
/*    */   }
/*    */ 
/*    */   public B mime(String paramString)
/*    */   {
/* 24 */     this.mime = paramString;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B name(String paramString)
/*    */   {
/* 34 */     this.name = paramString;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public CustomTransfer build()
/*    */   {
/* 42 */     CustomTransfer localCustomTransfer = new CustomTransfer(this.name, this.mime);
/* 43 */     return localCustomTransfer;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swt.CustomTransferBuilder
 * JD-Core Version:    0.6.2
 */