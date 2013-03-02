/*    */ package javafx.scene.input;
/*    */ 
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.scene.Node;
/*    */ 
/*    */ public class Mnemonic
/*    */ {
/*    */   private KeyCombination keyCombination;
/*    */   private Node node;
/*    */ 
/*    */   public Mnemonic(Node paramNode, KeyCombination paramKeyCombination)
/*    */   {
/* 55 */     this.node = paramNode;
/* 56 */     this.keyCombination = paramKeyCombination;
/*    */   }
/*    */ 
/*    */   public KeyCombination getKeyCombination()
/*    */   {
/* 63 */     return this.keyCombination;
/*    */   }
/*    */ 
/*    */   public void setKeyCombination(KeyCombination paramKeyCombination)
/*    */   {
/* 69 */     this.keyCombination = paramKeyCombination;
/*    */   }
/*    */ 
/*    */   public Node getNode()
/*    */   {
/* 77 */     return this.node;
/*    */   }
/*    */ 
/*    */   public void setNode(Node paramNode)
/*    */   {
/* 83 */     this.node = paramNode;
/*    */   }
/*    */ 
/*    */   public void fire()
/*    */   {
/* 90 */     if (this.node != null)
/* 91 */       this.node.fireEvent(new ActionEvent());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.Mnemonic
 * JD-Core Version:    0.6.2
 */