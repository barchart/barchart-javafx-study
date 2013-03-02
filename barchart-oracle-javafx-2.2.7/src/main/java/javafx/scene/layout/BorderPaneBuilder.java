/*    */ package javafx.scene.layout;
/*    */ 
/*    */ import javafx.scene.Node;
/*    */ 
/*    */ public class BorderPaneBuilder<B extends BorderPaneBuilder<B>> extends PaneBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private Node bottom;
/*    */   private Node center;
/*    */   private Node left;
/*    */   private Node right;
/*    */   private Node top;
/*    */ 
/*    */   public static BorderPaneBuilder<?> create()
/*    */   {
/* 15 */     return new BorderPaneBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(BorderPane paramBorderPane)
/*    */   {
/* 20 */     super.applyTo(paramBorderPane);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramBorderPane.setBottom(this.bottom);
/* 23 */     if ((i & 0x2) != 0) paramBorderPane.setCenter(this.center);
/* 24 */     if ((i & 0x4) != 0) paramBorderPane.setLeft(this.left);
/* 25 */     if ((i & 0x8) != 0) paramBorderPane.setRight(this.right);
/* 26 */     if ((i & 0x10) != 0) paramBorderPane.setTop(this.top);
/*    */   }
/*    */ 
/*    */   public B bottom(Node paramNode)
/*    */   {
/* 35 */     this.bottom = paramNode;
/* 36 */     this.__set |= 1;
/* 37 */     return this;
/*    */   }
/*    */ 
/*    */   public B center(Node paramNode)
/*    */   {
/* 46 */     this.center = paramNode;
/* 47 */     this.__set |= 2;
/* 48 */     return this;
/*    */   }
/*    */ 
/*    */   public B left(Node paramNode)
/*    */   {
/* 57 */     this.left = paramNode;
/* 58 */     this.__set |= 4;
/* 59 */     return this;
/*    */   }
/*    */ 
/*    */   public B right(Node paramNode)
/*    */   {
/* 68 */     this.right = paramNode;
/* 69 */     this.__set |= 8;
/* 70 */     return this;
/*    */   }
/*    */ 
/*    */   public B top(Node paramNode)
/*    */   {
/* 79 */     this.top = paramNode;
/* 80 */     this.__set |= 16;
/* 81 */     return this;
/*    */   }
/*    */ 
/*    */   public BorderPane build()
/*    */   {
/* 88 */     BorderPane localBorderPane = new BorderPane();
/* 89 */     applyTo(localBorderPane);
/* 90 */     return localBorderPane;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.BorderPaneBuilder
 * JD-Core Version:    0.6.2
 */