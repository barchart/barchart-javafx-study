/*    */ package com.sun.javafx.scene.control.behavior;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javafx.scene.control.SplitMenuButton;
/*    */ import javafx.scene.input.KeyCode;
/*    */ import javafx.scene.input.KeyEvent;
/*    */ 
/*    */ public class SplitMenuButtonBehavior extends MenuButtonBehaviorBase<SplitMenuButton>
/*    */ {
/* 68 */   protected static final List<KeyBinding> SPLIT_MENU_BUTTON_BINDINGS = new ArrayList();
/*    */ 
/*    */   public SplitMenuButtonBehavior(SplitMenuButton paramSplitMenuButton)
/*    */   {
/* 55 */     super(paramSplitMenuButton);
/*    */   }
/*    */ 
/*    */   protected List<KeyBinding> createKeyBindings()
/*    */   {
/* 87 */     return SPLIT_MENU_BUTTON_BINDINGS;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 70 */     SPLIT_MENU_BUTTON_BINDINGS.addAll(BASE_MENU_BUTTON_BINDINGS);
/*    */ 
/* 76 */     SPLIT_MENU_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_PRESSED, "Press"));
/* 77 */     SPLIT_MENU_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_RELEASED, "Release"));
/*    */ 
/* 79 */     SPLIT_MENU_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.ENTER, KeyEvent.KEY_PRESSED, "Press"));
/* 80 */     SPLIT_MENU_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.ENTER, KeyEvent.KEY_RELEASED, "Release"));
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.SplitMenuButtonBehavior
 * JD-Core Version:    0.6.2
 */