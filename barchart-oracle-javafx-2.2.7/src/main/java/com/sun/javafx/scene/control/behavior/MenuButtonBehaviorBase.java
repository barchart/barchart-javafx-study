/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.geometry.Side;
/*     */ import javafx.scene.control.MenuButton;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public abstract class MenuButtonBehaviorBase<C extends MenuButton> extends ButtonBehavior<C>
/*     */ {
/*     */   protected static final String OPEN_ACTION = "Open";
/*     */   protected static final String CLOSE_ACTION = "Close";
/*  75 */   protected static final List<KeyBinding> BASE_MENU_BUTTON_BINDINGS = new ArrayList();
/*     */ 
/*     */   public MenuButtonBehaviorBase(C paramC)
/*     */   {
/*  51 */     super(paramC);
/*     */   }
/*     */ 
/*     */   protected void callAction(String paramString)
/*     */   {
/*  88 */     MenuButton localMenuButton = (MenuButton)getControl();
/*  89 */     Side localSide = localMenuButton.getPopupSide();
/*  90 */     if ("Close".equals(paramString))
/*  91 */       localMenuButton.hide();
/*  92 */     else if ("Open".equals(paramString)) {
/*  93 */       if (localMenuButton.isShowing())
/*  94 */         localMenuButton.hide();
/*     */       else
/*  96 */         localMenuButton.show();
/*     */     }
/*  98 */     else if (((!localMenuButton.isShowing()) && ("TraverseUp".equals(paramString)) && (localSide == Side.TOP)) || (("TraverseDown".equals(paramString)) && ((localSide == Side.BOTTOM) || (localSide == Side.TOP))) || (("TraverseLeft".equals(paramString)) && (localSide == Side.LEFT)) || (("TraverseRight".equals(paramString)) && ((localSide == Side.RIGHT) || (localSide == Side.LEFT))))
/*     */     {
/* 107 */       localMenuButton.show();
/*     */     }
/* 109 */     else super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent, boolean paramBoolean)
/*     */   {
/* 127 */     MenuButton localMenuButton = (MenuButton)getControl();
/*     */ 
/* 137 */     if (paramBoolean) {
/* 138 */       if (localMenuButton.isShowing()) {
/* 139 */         localMenuButton.hide();
/*     */       }
/* 141 */       super.mousePressed(paramMouseEvent);
/*     */     } else {
/* 143 */       if ((!localMenuButton.isFocused()) && (localMenuButton.isFocusTraversable())) {
/* 144 */         localMenuButton.requestFocus();
/*     */       }
/* 146 */       if (localMenuButton.isShowing())
/* 147 */         localMenuButton.hide();
/*     */       else
/* 149 */         localMenuButton.show();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent, boolean paramBoolean)
/*     */   {
/* 167 */     if (paramBoolean) {
/* 168 */       super.mouseReleased(paramMouseEvent);
/*     */     } else {
/* 170 */       if ((((MenuButton)getControl()).isShowing()) && (!((MenuButton)getControl()).contains(paramMouseEvent.getX(), paramMouseEvent.getY()))) {
/* 171 */         ((MenuButton)getControl()).hide();
/*     */       }
/* 173 */       ((MenuButton)getControl()).disarm();
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  77 */     BASE_MENU_BUTTON_BINDINGS.addAll(TRAVERSAL_BINDINGS);
/*  78 */     BASE_MENU_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.ESCAPE, KeyEvent.KEY_PRESSED, "Close"));
/*  79 */     BASE_MENU_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.CANCEL, KeyEvent.KEY_PRESSED, "Close"));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.MenuButtonBehaviorBase
 * JD-Core Version:    0.6.2
 */