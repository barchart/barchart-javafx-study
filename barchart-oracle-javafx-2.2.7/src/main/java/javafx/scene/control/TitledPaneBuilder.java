/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class TitledPaneBuilder<B extends TitledPaneBuilder<B>> extends LabeledBuilder<B>
/*    */   implements Builder<TitledPane>
/*    */ {
/*    */   private int __set;
/*    */   private boolean animated;
/*    */   private boolean collapsible;
/*    */   private Node content;
/*    */   private boolean expanded;
/*    */ 
/*    */   public static TitledPaneBuilder<?> create()
/*    */   {
/* 15 */     return new TitledPaneBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(TitledPane paramTitledPane)
/*    */   {
/* 20 */     super.applyTo(paramTitledPane);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramTitledPane.setAnimated(this.animated);
/* 23 */     if ((i & 0x2) != 0) paramTitledPane.setCollapsible(this.collapsible);
/* 24 */     if ((i & 0x4) != 0) paramTitledPane.setContent(this.content);
/* 25 */     if ((i & 0x8) != 0) paramTitledPane.setExpanded(this.expanded);
/*    */   }
/*    */ 
/*    */   public B animated(boolean paramBoolean)
/*    */   {
/* 34 */     this.animated = paramBoolean;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B collapsible(boolean paramBoolean)
/*    */   {
/* 45 */     this.collapsible = paramBoolean;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B content(Node paramNode)
/*    */   {
/* 56 */     this.content = paramNode;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B expanded(boolean paramBoolean)
/*    */   {
/* 67 */     this.expanded = paramBoolean;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public TitledPane build()
/*    */   {
/* 76 */     TitledPane localTitledPane = new TitledPane();
/* 77 */     applyTo(localTitledPane);
/* 78 */     return localTitledPane;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TitledPaneBuilder
 * JD-Core Version:    0.6.2
 */