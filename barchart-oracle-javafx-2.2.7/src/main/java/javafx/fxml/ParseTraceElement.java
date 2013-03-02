/*    */ package javafx.fxml;
/*    */ 
/*    */ import java.net.URL;
/*    */ 
/*    */ public class ParseTraceElement
/*    */ {
/*    */   private URL location;
/*    */   private int lineNumber;
/*    */ 
/*    */   public ParseTraceElement(URL paramURL, int paramInt)
/*    */   {
/* 20 */     this.location = paramURL;
/* 21 */     this.lineNumber = paramInt;
/*    */   }
/*    */ 
/*    */   public URL getLocation() {
/* 25 */     return this.location;
/*    */   }
/*    */ 
/*    */   public int getLineNumber() {
/* 29 */     return this.lineNumber;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 34 */     return new StringBuilder().append(this.location == null ? "?" : this.location.getPath()).append(": ").append(this.lineNumber).toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.fxml.ParseTraceElement
 * JD-Core Version:    0.6.2
 */