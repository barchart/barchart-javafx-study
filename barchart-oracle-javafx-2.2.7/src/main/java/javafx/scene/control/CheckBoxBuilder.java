/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class CheckBoxBuilder<B extends CheckBoxBuilder<B>> extends ButtonBaseBuilder<B>
/*    */   implements Builder<CheckBox>
/*    */ {
/*    */   private int __set;
/*    */   private boolean allowIndeterminate;
/*    */   private boolean indeterminate;
/*    */   private boolean selected;
/*    */ 
/*    */   public static CheckBoxBuilder<?> create()
/*    */   {
/* 15 */     return new CheckBoxBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(CheckBox paramCheckBox)
/*    */   {
/* 20 */     super.applyTo(paramCheckBox);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramCheckBox.setAllowIndeterminate(this.allowIndeterminate);
/* 23 */     if ((i & 0x2) != 0) paramCheckBox.setIndeterminate(this.indeterminate);
/* 24 */     if ((i & 0x4) != 0) paramCheckBox.setSelected(this.selected);
/*    */   }
/*    */ 
/*    */   public B allowIndeterminate(boolean paramBoolean)
/*    */   {
/* 33 */     this.allowIndeterminate = paramBoolean;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B indeterminate(boolean paramBoolean)
/*    */   {
/* 44 */     this.indeterminate = paramBoolean;
/* 45 */     this.__set |= 2;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   public B selected(boolean paramBoolean)
/*    */   {
/* 55 */     this.selected = paramBoolean;
/* 56 */     this.__set |= 4;
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   public CheckBox build()
/*    */   {
/* 64 */     CheckBox localCheckBox = new CheckBox();
/* 65 */     applyTo(localCheckBox);
/* 66 */     return localCheckBox;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.CheckBoxBuilder
 * JD-Core Version:    0.6.2
 */