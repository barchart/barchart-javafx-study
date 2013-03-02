/*    */ package javafx.scene.control;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.geometry.Orientation;
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class SplitPaneBuilder<B extends SplitPaneBuilder<B>> extends ControlBuilder<B>
/*    */   implements Builder<SplitPane>
/*    */ {
/*    */   private int __set;
/*    */   private double[] dividerPositions;
/*    */   private Collection<? extends Node> items;
/*    */   private Orientation orientation;
/*    */ 
/*    */   public static SplitPaneBuilder<?> create()
/*    */   {
/* 15 */     return new SplitPaneBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(SplitPane paramSplitPane)
/*    */   {
/* 20 */     super.applyTo(paramSplitPane);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramSplitPane.setDividerPositions(this.dividerPositions);
/* 23 */     if ((i & 0x2) != 0) paramSplitPane.getItems().addAll(this.items);
/* 24 */     if ((i & 0x4) != 0) paramSplitPane.setOrientation(this.orientation);
/*    */   }
/*    */ 
/*    */   public B dividerPositions(double[] paramArrayOfDouble)
/*    */   {
/* 33 */     this.dividerPositions = paramArrayOfDouble;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(Collection<? extends Node> paramCollection)
/*    */   {
/* 44 */     this.items = paramCollection;
/* 45 */     this.__set |= 2;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(Node[] paramArrayOfNode)
/*    */   {
/* 53 */     return items(Arrays.asList(paramArrayOfNode));
/*    */   }
/*    */ 
/*    */   public B orientation(Orientation paramOrientation)
/*    */   {
/* 62 */     this.orientation = paramOrientation;
/* 63 */     this.__set |= 4;
/* 64 */     return this;
/*    */   }
/*    */ 
/*    */   public SplitPane build()
/*    */   {
/* 71 */     SplitPane localSplitPane = new SplitPane();
/* 72 */     applyTo(localSplitPane);
/* 73 */     return localSplitPane;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.SplitPaneBuilder
 * JD-Core Version:    0.6.2
 */