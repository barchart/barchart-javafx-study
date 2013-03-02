/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class RadioButtonBuilder<B extends RadioButtonBuilder<B>> extends ToggleButtonBuilder<B>
/*    */ {
/*    */   public static RadioButtonBuilder<?> create()
/*    */   {
/* 15 */     return new RadioButtonBuilder();
/*    */   }
/*    */ 
/*    */   public RadioButton build()
/*    */   {
/* 22 */     RadioButton localRadioButton = new RadioButton();
/* 23 */     applyTo(localRadioButton);
/* 24 */     return localRadioButton;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.RadioButtonBuilder
 * JD-Core Version:    0.6.2
 */