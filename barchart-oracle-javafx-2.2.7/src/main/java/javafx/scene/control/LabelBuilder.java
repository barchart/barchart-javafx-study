/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class LabelBuilder<B extends LabelBuilder<B>> extends LabeledBuilder<B>
/*    */   implements Builder<Label>
/*    */ {
/*    */   private boolean __set;
/*    */   private Node labelFor;
/*    */ 
/*    */   public static LabelBuilder<?> create()
/*    */   {
/* 15 */     return new LabelBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Label paramLabel)
/*    */   {
/* 20 */     super.applyTo(paramLabel);
/* 21 */     if (this.__set) paramLabel.setLabelFor(this.labelFor);
/*    */   }
/*    */ 
/*    */   public B labelFor(Node paramNode)
/*    */   {
/* 30 */     this.labelFor = paramNode;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public Label build()
/*    */   {
/* 39 */     Label localLabel = new Label();
/* 40 */     applyTo(localLabel);
/* 41 */     return localLabel;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.LabelBuilder
 * JD-Core Version:    0.6.2
 */