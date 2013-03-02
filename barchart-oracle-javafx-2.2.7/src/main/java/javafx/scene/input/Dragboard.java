/*    */ package javafx.scene.input;
/*    */ 
/*    */ import com.sun.javafx.tk.TKClipboard;
/*    */ import java.util.Set;
/*    */ 
/*    */ public final class Dragboard extends Clipboard
/*    */ {
/*    */   Dragboard(TKClipboard paramTKClipboard)
/*    */   {
/* 38 */     super(paramTKClipboard);
/*    */   }
/*    */ 
/*    */   public final Set<TransferMode> getTransferModes()
/*    */   {
/* 46 */     return this.peer.getTransferModes();
/*    */   }
/*    */ 
/*    */   @Deprecated
/*    */   public TKClipboard impl_getPeer()
/*    */   {
/* 55 */     return this.peer;
/*    */   }
/*    */ 
/*    */   @Deprecated
/*    */   public static Dragboard impl_create(TKClipboard paramTKClipboard)
/*    */   {
/* 64 */     return new Dragboard(paramTKClipboard);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.Dragboard
 * JD-Core Version:    0.6.2
 */