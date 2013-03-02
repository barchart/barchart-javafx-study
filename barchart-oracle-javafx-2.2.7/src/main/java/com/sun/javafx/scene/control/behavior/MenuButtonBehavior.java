/*    */ package com.sun.javafx.scene.control.behavior;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javafx.scene.control.MenuButton;
/*    */ import javafx.scene.input.KeyCode;
/*    */ import javafx.scene.input.KeyEvent;
/*    */ 
/*    */ public class MenuButtonBehavior extends MenuButtonBehaviorBase<MenuButton>
/*    */ {
/* 66 */   protected static final List<KeyBinding> MENU_BUTTON_BINDINGS = new ArrayList();
/*    */ 
/*    */   public MenuButtonBehavior(MenuButton paramMenuButton)
/*    */   {
/* 54 */     super(paramMenuButton);
/*    */   }
/*    */ 
/*    */   protected List<KeyBinding> createKeyBindings()
/*    */   {
/* 77 */     return MENU_BUTTON_BINDINGS;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 68 */     MENU_BUTTON_BINDINGS.addAll(BASE_MENU_BUTTON_BINDINGS);
/* 69 */     MENU_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_PRESSED, "Open"));
/* 70 */     MENU_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.ENTER, KeyEvent.KEY_PRESSED, "Open"));
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.MenuButtonBehavior
 * JD-Core Version:    0.6.2
 */