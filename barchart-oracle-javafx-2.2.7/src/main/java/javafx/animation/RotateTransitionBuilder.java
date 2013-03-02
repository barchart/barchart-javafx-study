/*    */ package javafx.animation;
/*    */ 
/*    */ import javafx.geometry.Point3D;
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ import javafx.util.Duration;
/*    */ 
/*    */ public final class RotateTransitionBuilder extends TransitionBuilder<RotateTransitionBuilder>
/*    */   implements Builder<RotateTransition>
/*    */ {
/*    */   private int __set;
/*    */   private Point3D axis;
/*    */   private double byAngle;
/*    */   private Duration duration;
/*    */   private double fromAngle;
/*    */   private Node node;
/*    */   private double toAngle;
/*    */ 
/*    */   public static RotateTransitionBuilder create()
/*    */   {
/* 15 */     return new RotateTransitionBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(RotateTransition paramRotateTransition)
/*    */   {
/* 20 */     super.applyTo(paramRotateTransition);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramRotateTransition.setAxis(this.axis);
/* 23 */     if ((i & 0x2) != 0) paramRotateTransition.setByAngle(this.byAngle);
/* 24 */     if ((i & 0x4) != 0) paramRotateTransition.setDuration(this.duration);
/* 25 */     if ((i & 0x8) != 0) paramRotateTransition.setFromAngle(this.fromAngle);
/* 26 */     if ((i & 0x10) != 0) paramRotateTransition.setNode(this.node);
/* 27 */     if ((i & 0x20) != 0) paramRotateTransition.setToAngle(this.toAngle);
/*    */   }
/*    */ 
/*    */   public RotateTransitionBuilder axis(Point3D paramPoint3D)
/*    */   {
/* 35 */     this.axis = paramPoint3D;
/* 36 */     this.__set |= 1;
/* 37 */     return this;
/*    */   }
/*    */ 
/*    */   public RotateTransitionBuilder byAngle(double paramDouble)
/*    */   {
/* 45 */     this.byAngle = paramDouble;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public RotateTransitionBuilder duration(Duration paramDuration)
/*    */   {
/* 55 */     this.duration = paramDuration;
/* 56 */     this.__set |= 4;
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   public RotateTransitionBuilder fromAngle(double paramDouble)
/*    */   {
/* 65 */     this.fromAngle = paramDouble;
/* 66 */     this.__set |= 8;
/* 67 */     return this;
/*    */   }
/*    */ 
/*    */   public RotateTransitionBuilder node(Node paramNode)
/*    */   {
/* 75 */     this.node = paramNode;
/* 76 */     this.__set |= 16;
/* 77 */     return this;
/*    */   }
/*    */ 
/*    */   public RotateTransitionBuilder toAngle(double paramDouble)
/*    */   {
/* 85 */     this.toAngle = paramDouble;
/* 86 */     this.__set |= 32;
/* 87 */     return this;
/*    */   }
/*    */ 
/*    */   public RotateTransition build()
/*    */   {
/* 94 */     RotateTransition localRotateTransition = new RotateTransition();
/* 95 */     applyTo(localRotateTransition);
/* 96 */     return localRotateTransition;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.RotateTransitionBuilder
 * JD-Core Version:    0.6.2
 */