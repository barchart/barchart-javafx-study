/*    */ package com.sun.javafx.stage;
/*    */ 
/*    */ import com.sun.javafx.tk.FocusCause;
/*    */ import javafx.stage.PopupWindow;
/*    */ 
/*    */ public class PopupWindowPeerListener extends WindowPeerListener
/*    */ {
/*    */   private final PopupWindow popupWindow;
/*    */ 
/*    */   public PopupWindowPeerListener(PopupWindow paramPopupWindow)
/*    */   {
/* 38 */     super(paramPopupWindow);
/* 39 */     this.popupWindow = paramPopupWindow;
/*    */   }
/*    */ 
/*    */   public void changedFocused(boolean paramBoolean, FocusCause paramFocusCause)
/*    */   {
/* 48 */     this.popupWindow.setFocused(paramBoolean);
/*    */   }
/*    */ 
/*    */   public void closing()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void changedLocation(float paramFloat1, float paramFloat2)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void changedIconified(boolean paramBoolean)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void changedResizable(boolean paramBoolean)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void changedFullscreen(boolean paramBoolean)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void focusUngrab()
/*    */   {
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.stage.PopupWindowPeerListener
 * JD-Core Version:    0.6.2
 */