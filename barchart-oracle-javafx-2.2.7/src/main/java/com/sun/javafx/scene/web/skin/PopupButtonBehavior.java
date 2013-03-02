/*    */ package com.sun.javafx.scene.web.skin;
/*    */ 
/*    */ import com.sun.javafx.scene.control.behavior.BehaviorBase;
/*    */ import com.sun.javafx.scene.control.behavior.KeyBinding;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javafx.scene.input.KeyCode;
/*    */ import javafx.scene.input.KeyEvent;
/*    */ import javafx.scene.input.MouseEvent;
/*    */ 
/*    */ public class PopupButtonBehavior extends BehaviorBase<PopupButton>
/*    */ {
/* 18 */   protected static final List<KeyBinding> POPUP_BUTTON_BINDINGS = new ArrayList();
/*    */ 
/*    */   protected void callAction(String paramString)
/*    */   {
/* 28 */     PopupButton localPopupButton = (PopupButton)getControl();
/*    */ 
/* 30 */     if ("Close".equals(paramString))
/* 31 */       localPopupButton.hide();
/* 32 */     else if ("Open".equals(paramString)) {
/* 33 */       if (localPopupButton.isShowing())
/* 34 */         localPopupButton.hide();
/*    */       else
/* 36 */         localPopupButton.show();
/*    */     }
/* 38 */     else if ((!localPopupButton.isShowing()) && ("TraverseDown".equals(paramString)))
/* 39 */       localPopupButton.show();
/*    */     else
/* 41 */       super.callAction(paramString);
/*    */   }
/*    */ 
/*    */   public PopupButtonBehavior(PopupButton paramPopupButton)
/*    */   {
/* 46 */     super(paramPopupButton);
/*    */   }
/*    */ 
/*    */   protected List<KeyBinding> createKeyBindings() {
/* 50 */     return POPUP_BUTTON_BINDINGS;
/*    */   }
/*    */ 
/*    */   public void mousePressed(MouseEvent paramMouseEvent)
/*    */   {
/* 55 */     PopupButton localPopupButton = (PopupButton)getControl();
/*    */ 
/* 57 */     if ((!localPopupButton.isFocused()) && (localPopupButton.isFocusTraversable())) {
/* 58 */       localPopupButton.requestFocus();
/*    */     }
/* 60 */     if (localPopupButton.isShowing())
/* 61 */       localPopupButton.hide();
/*    */     else
/* 63 */       localPopupButton.show();
/*    */   }
/*    */ 
/*    */   public void mouseReleased(MouseEvent paramMouseEvent)
/*    */   {
/* 69 */     if ((((PopupButton)getControl()).isShowing()) && (!((PopupButton)getControl()).contains(paramMouseEvent.getX(), paramMouseEvent.getY())))
/* 70 */       ((PopupButton)getControl()).hide();
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 20 */     POPUP_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_PRESSED, "Open"));
/* 21 */     POPUP_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.ENTER, KeyEvent.KEY_PRESSED, "Open"));
/* 22 */     POPUP_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.ESCAPE, KeyEvent.KEY_RELEASED, "Close"));
/* 23 */     POPUP_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.CANCEL, KeyEvent.KEY_RELEASED, "Close"));
/* 24 */     POPUP_BUTTON_BINDINGS.addAll(TRAVERSAL_BINDINGS);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.web.skin.PopupButtonBehavior
 * JD-Core Version:    0.6.2
 */