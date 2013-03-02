/*    */ package javafx.scene.layout;
/*    */ 
/*    */ import javafx.geometry.Pos;
/*    */ 
/*    */ public class HBoxBuilder<B extends HBoxBuilder<B>> extends PaneBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private Pos alignment;
/*    */   private boolean fillHeight;
/*    */   private double spacing;
/*    */ 
/*    */   public static HBoxBuilder<?> create()
/*    */   {
/* 15 */     return new HBoxBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(HBox paramHBox)
/*    */   {
/* 20 */     super.applyTo(paramHBox);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramHBox.setAlignment(this.alignment);
/* 23 */     if ((i & 0x2) != 0) paramHBox.setFillHeight(this.fillHeight);
/* 24 */     if ((i & 0x4) != 0) paramHBox.setSpacing(this.spacing);
/*    */   }
/*    */ 
/*    */   public B alignment(Pos paramPos)
/*    */   {
/* 33 */     this.alignment = paramPos;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B fillHeight(boolean paramBoolean)
/*    */   {
/* 44 */     this.fillHeight = paramBoolean;
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
/*    */   public HBox build()
/*    */   {
/* 64 */     HBox localHBox = new HBox();
/* 65 */     applyTo(localHBox);
/* 66 */     return localHBox;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.HBoxBuilder
 * JD-Core Version:    0.6.2
 */