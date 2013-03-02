/*    */ package javafx.scene.input;
/*    */ 
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class MnemonicBuilder<B extends MnemonicBuilder<B>>
/*    */   implements Builder<Mnemonic>
/*    */ {
/*    */   private KeyCombination keyCombination;
/*    */   private Node node;
/*    */ 
/*    */   public static MnemonicBuilder<?> create()
/*    */   {
/* 15 */     return new MnemonicBuilder();
/*    */   }
/*    */ 
/*    */   public B keyCombination(KeyCombination paramKeyCombination)
/*    */   {
/* 24 */     this.keyCombination = paramKeyCombination;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B node(Node paramNode)
/*    */   {
/* 34 */     this.node = paramNode;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public Mnemonic build()
/*    */   {
/* 42 */     Mnemonic localMnemonic = new Mnemonic(this.node, this.keyCombination);
/* 43 */     return localMnemonic;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.MnemonicBuilder
 * JD-Core Version:    0.6.2
 */