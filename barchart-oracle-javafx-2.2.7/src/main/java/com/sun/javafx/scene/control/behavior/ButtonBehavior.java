/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.scene.control.ButtonBase;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public class ButtonBehavior<C extends ButtonBase> extends BehaviorBase<C>
/*     */ {
/*     */   private boolean keyDown;
/*     */   private static final String PRESS_ACTION = "Press";
/*     */   private static final String RELEASE_ACTION = "Release";
/*  94 */   protected static final List<KeyBinding> BUTTON_BINDINGS = new ArrayList();
/*     */ 
/*     */   public ButtonBehavior(final C paramC)
/*     */   {
/*  63 */     super(paramC);
/*  64 */     InvalidationListener local1 = new InvalidationListener()
/*     */     {
/*     */       public void invalidated(Observable paramAnonymousObservable)
/*     */       {
/*  68 */         if ((ButtonBehavior.this.keyDown) && (!paramC.isFocused())) {
/*  69 */           ButtonBehavior.this.keyDown = false;
/*  70 */           paramC.disarm();
/*     */         }
/*     */       }
/*     */     };
/*  74 */     paramC.focusedProperty().addListener(local1);
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/* 109 */     return BUTTON_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callAction(String paramString) {
/* 113 */     if (!((ButtonBase)getControl()).isDisabled())
/* 114 */       if ("Press".equals(paramString))
/* 115 */         keyPressed();
/* 116 */       else if ("Release".equals(paramString))
/* 117 */         keyReleased();
/*     */       else
/* 119 */         super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   private void keyPressed()
/*     */   {
/* 130 */     ButtonBase localButtonBase = (ButtonBase)getControl();
/* 131 */     if ((!localButtonBase.isPressed()) && (!localButtonBase.isArmed())) {
/* 132 */       this.keyDown = true;
/* 133 */       localButtonBase.arm();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void keyReleased()
/*     */   {
/* 142 */     ButtonBase localButtonBase = (ButtonBase)getControl();
/* 143 */     if (this.keyDown) {
/* 144 */       this.keyDown = false;
/* 145 */       if (localButtonBase.isArmed()) {
/* 146 */         localButtonBase.disarm();
/* 147 */         localButtonBase.fire();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 163 */     ButtonBase localButtonBase = (ButtonBase)getControl();
/* 164 */     super.mousePressed(paramMouseEvent);
/*     */ 
/* 166 */     if ((!localButtonBase.isFocused()) && (localButtonBase.isFocusTraversable())) {
/* 167 */       localButtonBase.requestFocus();
/*     */     }
/*     */ 
/* 175 */     int i = (paramMouseEvent.getButton() == MouseButton.PRIMARY) && (!paramMouseEvent.isMiddleButtonDown()) && (!paramMouseEvent.isSecondaryButtonDown()) && (!paramMouseEvent.isShiftDown()) && (!paramMouseEvent.isControlDown()) && (!paramMouseEvent.isAltDown()) && (!paramMouseEvent.isMetaDown()) ? 1 : 0;
/*     */ 
/* 179 */     if ((!localButtonBase.isArmed()) && (i != 0))
/* 180 */       localButtonBase.arm();
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 191 */     ButtonBase localButtonBase = (ButtonBase)getControl();
/* 192 */     if ((!this.keyDown) && (localButtonBase.isArmed())) {
/* 193 */       localButtonBase.fire();
/* 194 */       localButtonBase.disarm();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseEntered(MouseEvent paramMouseEvent)
/*     */   {
/* 205 */     ButtonBase localButtonBase = (ButtonBase)getControl();
/* 206 */     super.mouseEntered(paramMouseEvent);
/* 207 */     if ((!this.keyDown) && (localButtonBase.isPressed()))
/* 208 */       localButtonBase.arm();
/*     */   }
/*     */ 
/*     */   public void mouseExited(MouseEvent paramMouseEvent)
/*     */   {
/* 219 */     ButtonBase localButtonBase = (ButtonBase)getControl();
/* 220 */     super.mouseExited(paramMouseEvent);
/* 221 */     if ((!this.keyDown) && (localButtonBase.isArmed()))
/* 222 */       localButtonBase.disarm();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  96 */     BUTTON_BINDINGS.addAll(TRAVERSAL_BINDINGS);
/*  97 */     if (PlatformUtil.isMac()) {
/*  98 */       BUTTON_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_PRESSED, "Press"));
/*  99 */       BUTTON_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_RELEASED, "Release"));
/*     */     } else {
/* 101 */       BUTTON_BINDINGS.add(new KeyBinding(KeyCode.ENTER, KeyEvent.KEY_PRESSED, "Press"));
/* 102 */       BUTTON_BINDINGS.add(new KeyBinding(KeyCode.ENTER, KeyEvent.KEY_RELEASED, "Release"));
/* 103 */       BUTTON_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_PRESSED, "Press"));
/* 104 */       BUTTON_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_RELEASED, "Release"));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.ButtonBehavior
 * JD-Core Version:    0.6.2
 */