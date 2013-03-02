/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ToggleButtonBuilder<B extends ToggleButtonBuilder<B>> extends ButtonBaseBuilder<B>
/*    */   implements Builder<ToggleButton>
/*    */ {
/*    */   private int __set;
/*    */   private boolean selected;
/*    */   private ToggleGroup toggleGroup;
/*    */ 
/*    */   public static ToggleButtonBuilder<?> create()
/*    */   {
/* 15 */     return new ToggleButtonBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ToggleButton paramToggleButton)
/*    */   {
/* 20 */     super.applyTo(paramToggleButton);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramToggleButton.setSelected(this.selected);
/* 23 */     if ((i & 0x2) != 0) paramToggleButton.setToggleGroup(this.toggleGroup);
/*    */   }
/*    */ 
/*    */   public B selected(boolean paramBoolean)
/*    */   {
/* 32 */     this.selected = paramBoolean;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B toggleGroup(ToggleGroup paramToggleGroup)
/*    */   {
/* 43 */     this.toggleGroup = paramToggleGroup;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public ToggleButton build()
/*    */   {
/* 52 */     ToggleButton localToggleButton = new ToggleButton();
/* 53 */     applyTo(localToggleButton);
/* 54 */     return localToggleButton;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ToggleButtonBuilder
 * JD-Core Version:    0.6.2
 */