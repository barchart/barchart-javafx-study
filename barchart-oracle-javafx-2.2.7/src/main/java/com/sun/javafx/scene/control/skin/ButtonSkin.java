/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.behavior.ButtonBehavior;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyCodeCombination;
/*     */ import javafx.scene.input.KeyCombination.Modifier;
/*     */ 
/*     */ public class ButtonSkin extends LabeledSkinBase<Button, ButtonBehavior<Button>>
/*     */ {
/* 101 */   Runnable defaultButtonRunnable = new Runnable() {
/*     */     public void run() {
/* 103 */       if (!((Button)ButtonSkin.this.getSkinnable()).isDisabled())
/* 104 */         ((Button)ButtonSkin.this.getSkinnable()).fire();
/*     */     }
/* 101 */   };
/*     */ 
/* 109 */   Runnable cancelButtonRunnable = new Runnable() {
/*     */     public void run() {
/* 111 */       if (!((Button)ButtonSkin.this.getSkinnable()).isDisabled())
/* 112 */         ((Button)ButtonSkin.this.getSkinnable()).fire();
/*     */     }
/* 109 */   };
/*     */   private KeyCodeCombination defaultAcceleratorKeyCodeCombination;
/*     */   private KeyCodeCombination cancelAcceleratorKeyCodeCombination;
/*     */ 
/*     */   public ButtonSkin(Button paramButton)
/*     */   {
/*  45 */     super(paramButton, new ButtonBehavior(paramButton));
/*     */ 
/*  48 */     registerChangeListener(paramButton.defaultButtonProperty(), "DEFAULT_BUTTON");
/*  49 */     registerChangeListener(paramButton.cancelButtonProperty(), "CANCEL_BUTTON");
/*  50 */     registerChangeListener(paramButton.focusedProperty(), "FOCUSED");
/*     */ 
/*  52 */     if (((Button)getSkinnable()).isDefaultButton())
/*     */     {
/*  57 */       setDefaultButton(true);
/*     */     }
/*     */ 
/*  60 */     if (((Button)getSkinnable()).isCancelButton())
/*     */     {
/*  65 */       setCancelButton(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString)
/*     */   {
/*  72 */     super.handleControlPropertyChanged(paramString);
/*  73 */     if (paramString == "DEFAULT_BUTTON") {
/*  74 */       setDefaultButton(((Button)getSkinnable()).isDefaultButton());
/*     */     }
/*  76 */     else if (paramString == "CANCEL_BUTTON") {
/*  77 */       setCancelButton(((Button)getSkinnable()).isCancelButton());
/*     */     }
/*  79 */     else if (paramString == "FOCUSED") {
/*  80 */       if (!((Button)getSkinnable()).isFocused()) {
/*  81 */         ContextMenu localContextMenu = ((Button)getSkinnable()).getContextMenu();
/*  82 */         if ((localContextMenu != null) && 
/*  83 */           (localContextMenu.isShowing())) {
/*  84 */           localContextMenu.hide();
/*  85 */           Utils.removeMnemonics(localContextMenu, ((Button)getSkinnable()).getScene());
/*     */         }
/*     */       }
/*     */     }
/*  89 */     else if ((paramString == "PARENT") && 
/*  90 */       (((Button)getSkinnable()).getParent() == null) && (getScene() != null)) {
/*  91 */       if (((Button)getSkinnable()).isDefaultButton()) {
/*  92 */         getScene().getAccelerators().remove(this.defaultAcceleratorKeyCodeCombination);
/*     */       }
/*  94 */       if (((Button)getSkinnable()).isCancelButton())
/*  95 */         getScene().getAccelerators().remove(this.cancelAcceleratorKeyCodeCombination);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setDefaultButton(boolean paramBoolean)
/*     */   {
/* 121 */     KeyCode localKeyCode = KeyCode.ENTER;
/* 122 */     this.defaultAcceleratorKeyCodeCombination = new KeyCodeCombination(localKeyCode, new KeyCombination.Modifier[0]);
/*     */     Runnable localRunnable;
/* 125 */     if (!paramBoolean)
/*     */     {
/* 129 */       localRunnable = (Runnable)((Button)getSkinnable()).getParent().getScene().getAccelerators().get(this.defaultAcceleratorKeyCodeCombination);
/* 130 */       if (!this.defaultButtonRunnable.equals(localRunnable))
/*     */       {
/* 134 */         ((Button)getSkinnable()).getParent().getScene().getAccelerators().remove(this.defaultAcceleratorKeyCodeCombination);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 141 */       localRunnable = (Runnable)((Button)getSkinnable()).getParent().getScene().getAccelerators().get(this.defaultAcceleratorKeyCodeCombination);
/*     */     }
/* 143 */     ((Button)getSkinnable()).getParent().getScene().getAccelerators().put(this.defaultAcceleratorKeyCodeCombination, this.defaultButtonRunnable);
/*     */   }
/*     */ 
/*     */   private void setCancelButton(boolean paramBoolean)
/*     */   {
/* 149 */     KeyCode localKeyCode = KeyCode.ESCAPE;
/* 150 */     this.cancelAcceleratorKeyCodeCombination = new KeyCodeCombination(localKeyCode, new KeyCombination.Modifier[0]);
/*     */     Runnable localRunnable;
/* 153 */     if (!paramBoolean)
/*     */     {
/* 157 */       localRunnable = (Runnable)((Button)getSkinnable()).getParent().getScene().getAccelerators().get(this.cancelAcceleratorKeyCodeCombination);
/* 158 */       if (!this.defaultButtonRunnable.equals(localRunnable))
/*     */       {
/* 162 */         ((Button)getSkinnable()).getParent().getScene().getAccelerators().remove(this.cancelAcceleratorKeyCodeCombination);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 169 */       localRunnable = (Runnable)((Button)getSkinnable()).getParent().getScene().getAccelerators().get(this.cancelAcceleratorKeyCodeCombination);
/*     */     }
/* 171 */     ((Button)getSkinnable()).getParent().getScene().getAccelerators().put(this.cancelAcceleratorKeyCodeCombination, this.cancelButtonRunnable);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ButtonSkin
 * JD-Core Version:    0.6.2
 */