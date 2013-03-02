/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.ComboBoxBase;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public class ComboBoxBaseBehavior<T> extends BehaviorBase<ComboBoxBase<T>>
/*     */ {
/*     */   private boolean keyDown;
/*     */   private static final String PRESS_ACTION = "Press";
/*     */   private static final String RELEASE_ACTION = "Release";
/*  87 */   protected static final List<KeyBinding> COMBO_BOX_BASE_BINDINGS = new ArrayList();
/*     */ 
/* 229 */   boolean wasComboBoxButtonClickedForAutoHide = false;
/* 230 */   boolean mouseInsideButton = false;
/*     */ 
/*     */   public ComboBoxBaseBehavior(ComboBoxBase<T> paramComboBoxBase)
/*     */   {
/*  56 */     super(paramComboBoxBase);
/*  57 */     InvalidationListener local1 = new InvalidationListener()
/*     */     {
/*     */       public void invalidated(Observable paramAnonymousObservable)
/*     */       {
/*  61 */         if ((ComboBoxBaseBehavior.this.keyDown) && (!((ComboBoxBase)ComboBoxBaseBehavior.this.getControl()).isFocused())) {
/*  62 */           ComboBoxBaseBehavior.this.keyDown = false;
/*  63 */           ((ComboBoxBase)ComboBoxBaseBehavior.this.getControl()).disarm();
/*     */         }
/*     */       }
/*     */     };
/*  67 */     ((ComboBoxBase)getControl()).focusedProperty().addListener(local1);
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/* 107 */     return COMBO_BOX_BASE_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callAction(String paramString) {
/* 111 */     if ("Press".equals(paramString))
/* 112 */       keyPressed();
/* 113 */     else if ("Release".equals(paramString))
/* 114 */       keyReleased();
/* 115 */     else if ("showPopup".equals(paramString))
/* 116 */       show();
/* 117 */     else if ("togglePopup".equals(paramString)) {
/* 118 */       if (((ComboBoxBase)getControl()).isShowing()) hide(); else
/* 119 */         show();
/*     */     }
/* 121 */     else super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   private void keyPressed()
/*     */   {
/* 131 */     if ((!((ComboBoxBase)getControl()).isPressed()) && (!((ComboBoxBase)getControl()).isArmed())) {
/* 132 */       this.keyDown = true;
/* 133 */       ((ComboBoxBase)getControl()).arm();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void keyReleased()
/*     */   {
/* 142 */     if (this.keyDown) {
/* 143 */       this.keyDown = false;
/* 144 */       if (((ComboBoxBase)getControl()).isArmed())
/* 145 */         ((ComboBoxBase)getControl()).disarm();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 159 */     super.mousePressed(paramMouseEvent);
/* 160 */     getFocus();
/* 161 */     arm(paramMouseEvent);
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent) {
/* 165 */     super.mousePressed(paramMouseEvent);
/*     */ 
/* 167 */     disarm();
/*     */ 
/* 174 */     if (((ComboBoxBase)getControl()).isShowing())
/* 175 */       hide();
/* 176 */     else if ((!this.wasComboBoxButtonClickedForAutoHide) && (((ComboBoxBase)getControl()).contains(paramMouseEvent.getX(), paramMouseEvent.getY())))
/* 177 */       show();
/*     */     else
/* 179 */       this.wasComboBoxButtonClickedForAutoHide = false;
/*     */   }
/*     */ 
/*     */   public void mouseEntered(MouseEvent paramMouseEvent)
/*     */   {
/* 184 */     if (((ComboBoxBase)getControl()).isEditable()) {
/* 185 */       Node localNode = ((ComboBoxBase)getControl()).lookup("#arrow-button");
/* 186 */       this.mouseInsideButton = ((localNode != null) && (localNode.localToScene(localNode.getBoundsInLocal()).contains(paramMouseEvent.getSceneX(), paramMouseEvent.getSceneY())));
/*     */     } else {
/* 188 */       this.mouseInsideButton = true;
/*     */     }
/*     */ 
/* 191 */     super.mouseEntered(paramMouseEvent);
/* 192 */     arm();
/*     */   }
/*     */ 
/*     */   public void mouseExited(MouseEvent paramMouseEvent) {
/* 196 */     this.mouseInsideButton = false;
/* 197 */     super.mouseExited(paramMouseEvent);
/* 198 */     disarm();
/*     */   }
/*     */ 
/*     */   private void getFocus() {
/* 202 */     if ((!((ComboBoxBase)getControl()).isFocused()) && (((ComboBoxBase)getControl()).isFocusTraversable()))
/* 203 */       ((ComboBoxBase)getControl()).requestFocus();
/*     */   }
/*     */ 
/*     */   private void arm(MouseEvent paramMouseEvent)
/*     */   {
/* 208 */     int i = (paramMouseEvent.getButton() == MouseButton.PRIMARY) && (!paramMouseEvent.isMiddleButtonDown()) && (!paramMouseEvent.isSecondaryButtonDown()) && (!paramMouseEvent.isShiftDown()) && (!paramMouseEvent.isControlDown()) && (!paramMouseEvent.isAltDown()) && (!paramMouseEvent.isMetaDown()) ? 1 : 0;
/*     */ 
/* 212 */     if ((!((ComboBoxBase)getControl()).isArmed()) && (i != 0))
/* 213 */       ((ComboBoxBase)getControl()).arm();
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/* 218 */     if (!((ComboBoxBase)getControl()).isShowing())
/* 219 */       ((ComboBoxBase)getControl()).show();
/*     */   }
/*     */ 
/*     */   public void hide()
/*     */   {
/* 224 */     if (((ComboBoxBase)getControl()).isShowing())
/* 225 */       ((ComboBoxBase)getControl()).hide();
/*     */   }
/*     */ 
/*     */   public void onAutoHide()
/*     */   {
/* 236 */     this.wasComboBoxButtonClickedForAutoHide = this.mouseInsideButton;
/* 237 */     hide();
/*     */   }
/*     */ 
/*     */   public void arm() {
/* 241 */     if (((ComboBoxBase)getControl()).isPressed())
/* 242 */       ((ComboBoxBase)getControl()).arm();
/*     */   }
/*     */ 
/*     */   public void disarm()
/*     */   {
/* 247 */     if ((!this.keyDown) && (((ComboBoxBase)getControl()).isArmed()))
/* 248 */       ((ComboBoxBase)getControl()).disarm();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  89 */     COMBO_BOX_BASE_BINDINGS.add(new KeyBinding(KeyCode.F4, KeyEvent.KEY_RELEASED, "togglePopup"));
/*  90 */     COMBO_BOX_BASE_BINDINGS.add(new KeyBinding(KeyCode.UP, "togglePopup").alt());
/*  91 */     COMBO_BOX_BASE_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "togglePopup").alt());
/*     */ 
/*  93 */     if (Utils.isWindows()) {
/*  94 */       COMBO_BOX_BASE_BINDINGS.add(new KeyBinding(KeyCode.ENTER, KeyEvent.KEY_PRESSED, "Press"));
/*  95 */       COMBO_BOX_BASE_BINDINGS.add(new KeyBinding(KeyCode.ENTER, KeyEvent.KEY_RELEASED, "Release"));
/*  96 */       COMBO_BOX_BASE_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_PRESSED, "Press"));
/*  97 */       COMBO_BOX_BASE_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_RELEASED, "Release"));
/*     */     } else {
/*  99 */       COMBO_BOX_BASE_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_PRESSED, "Press"));
/* 100 */       COMBO_BOX_BASE_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_RELEASED, "Release"));
/*     */     }
/*     */ 
/* 103 */     COMBO_BOX_BASE_BINDINGS.addAll(TRAVERSAL_BINDINGS);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior
 * JD-Core Version:    0.6.2
 */