/*    */ package javafx.scene.layout;
/*    */ 
/*    */ import javafx.geometry.Pos;
/*    */ 
/*    */ public class VBoxBuilder<B extends VBoxBuilder<B>> extends PaneBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private Pos alignment;
/*    */   private boolean fillWidth;
/*    */   private double spacing;
/*    */ 
/*    */   public static VBoxBuilder<?> create()
/*    */   {
/* 15 */     return new VBoxBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(VBox paramVBox)
/*    */   {
/* 20 */     super.applyTo(paramVBox);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramVBox.setAlignment(this.alignment);
/* 23 */     if ((i & 0x2) != 0) paramVBox.setFillWidth(this.fillWidth);
/* 24 */     if ((i & 0x4) != 0) paramVBox.setSpacing(this.spacing);
/*    */   }
/*    */ 
/*    */   public B alignment(Pos paramPos)
/*    */   {
/* 33 */     this.alignment = paramPos;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B fillWidth(boolean paramBoolean)
/*    */   {
/* 44 */     this.fillWidth = paramBoolean;
/* 45 */     this.__set |= 2;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   public B spacing(double paramDouble)
/*    */   {
/* 55 */     this.spacing = paramDouble;
/* 56 */     this.__set |= 4;
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   public VBox build()
/*    */   {
/* 64 */     VBox localVBox = new VBox();
/* 65 */     applyTo(localVBox);
/* 66 */     return localVBox;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.VBoxBuilder
 * JD-Core Version:    0.6.2
 */