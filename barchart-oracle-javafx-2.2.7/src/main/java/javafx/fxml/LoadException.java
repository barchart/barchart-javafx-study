/*    */ package javafx.fxml;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class LoadException extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */ 
/*    */   public LoadException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LoadException(String paramString)
/*    */   {
/* 22 */     super(paramString);
/*    */   }
/*    */ 
/*    */   public LoadException(Throwable paramThrowable) {
/* 26 */     super(paramThrowable);
/*    */   }
/*    */ 
/*    */   public LoadException(String paramString, Throwable paramThrowable) {
/* 30 */     super(paramString, paramThrowable);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.fxml.LoadException
 * JD-Core Version:    0.6.2
 */