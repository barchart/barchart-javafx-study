/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.scene.control.ChoiceBox;
/*     */ import javafx.scene.control.SelectionModel;
/*     */ import javafx.scene.control.SingleSelectionModel;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public class ChoiceBoxBehavior<T> extends BehaviorBase<ChoiceBox<T>>
/*     */ {
/*  56 */   protected static final List<KeyBinding> CHOICE_BUTTON_BINDINGS = new ArrayList();
/*     */ 
/*     */   protected void callAction(String paramString)
/*     */   {
/*  71 */     if (paramString.equals("Cancel")) cancel();
/*  72 */     else if (paramString.equals("Press")) keyPressed();
/*  73 */     else if (paramString.equals("Release")) keyReleased();
/*  74 */     else if (paramString.equals("Down")) showPopup(); else
/*  75 */       super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   public ChoiceBoxBehavior(ChoiceBox paramChoiceBox) {
/*  79 */     super(paramChoiceBox);
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings() {
/*  83 */     return CHOICE_BUTTON_BINDINGS;
/*     */   }
/*     */ 
/*     */   public void select(int paramInt) {
/*  87 */     SingleSelectionModel localSingleSelectionModel = ((ChoiceBox)getControl()).getSelectionModel();
/*  88 */     if (localSingleSelectionModel == null) return;
/*     */ 
/*  90 */     localSingleSelectionModel.select(paramInt);
/*     */   }
/*     */ 
/*     */   public void close() {
/*  94 */     ((ChoiceBox)getControl()).hide();
/*     */   }
/*     */ 
/*     */   public void showPopup() {
/*  98 */     ((ChoiceBox)getControl()).show();
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 106 */     ChoiceBox localChoiceBox = (ChoiceBox)getControl();
/* 107 */     super.mousePressed(paramMouseEvent);
/* 108 */     if (localChoiceBox.isShowing()) {
/* 109 */       ((ChoiceBox)getControl()).hide();
/*     */     }
/*     */     else {
/* 112 */       if (localChoiceBox.isFocusTraversable()) localChoiceBox.requestFocus();
/* 113 */       localChoiceBox.show();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 123 */     ChoiceBox localChoiceBox = (ChoiceBox)getControl();
/* 124 */     super.mouseReleased(paramMouseEvent);
/* 125 */     if (!localChoiceBox.contains(paramMouseEvent.getX(), paramMouseEvent.getY()))
/* 126 */       localChoiceBox.hide();
/*     */   }
/*     */ 
/*     */   private void keyPressed()
/*     */   {
/* 136 */     ChoiceBox localChoiceBox = (ChoiceBox)getControl();
/* 137 */     if (!localChoiceBox.isShowing())
/* 138 */       localChoiceBox.show();
/*     */   }
/*     */ 
/*     */   private void keyReleased()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void cancel()
/*     */   {
/* 154 */     ChoiceBox localChoiceBox = (ChoiceBox)getControl();
/* 155 */     localChoiceBox.hide();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  58 */     CHOICE_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_PRESSED, "Press"));
/*  59 */     CHOICE_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_RELEASED, "Release"));
/*  60 */     CHOICE_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.ESCAPE, KeyEvent.KEY_RELEASED, "Cancel"));
/*  61 */     CHOICE_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.DOWN, KeyEvent.KEY_RELEASED, "Down"));
/*  62 */     CHOICE_BUTTON_BINDINGS.add(new KeyBinding(KeyCode.CANCEL, KeyEvent.KEY_RELEASED, "Cancel"));
/*     */ 
/*  64 */     CHOICE_BUTTON_BINDINGS.addAll(TRAVERSAL_BINDINGS);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.ChoiceBoxBehavior
 * JD-Core Version:    0.6.2
 */