/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import com.sun.javafx.scene.control.behavior.BehaviorBase;
/*    */ import javafx.scene.control.Label;
/*    */ 
/*    */ public class LabelSkin extends LabeledSkinBase<Label, BehaviorBase<Label>>
/*    */ {
/*    */   public LabelSkin(Label paramLabel)
/*    */   {
/* 38 */     super(paramLabel, new BehaviorBase(paramLabel));
/*    */ 
/* 41 */     consumeMouseEvents(false);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.LabelSkin
 * JD-Core Version:    0.6.2
 */