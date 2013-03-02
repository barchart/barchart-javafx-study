/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.scene.control.skin.TextFieldSkin;
/*     */ import com.sun.javafx.scene.text.HitInfo;
/*     */ import java.util.List;
/*     */ import javafx.animation.KeyFrame;
/*     */ import javafx.animation.KeyValue;
/*     */ import javafx.animation.Timeline;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.HorizontalDirection;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.control.IndexRange;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.stage.Screen;
/*     */ import javafx.stage.Window;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class TextFieldBehavior extends TextInputControlBehavior<TextField>
/*     */ {
/*     */   public static final int SCROLL_RATE = 15;
/*     */   private TextFieldSkin skin;
/*  62 */   private HorizontalDirection scrollDirection = null;
/*  63 */   private Timeline scrollSelectionTimeline = new Timeline();
/*  64 */   private EventHandler<ActionEvent> scrollSelectionHandler = new EventHandler()
/*     */   {
/*     */     public void handle(ActionEvent paramAnonymousActionEvent) {
/*  67 */       TextField localTextField = (TextField)TextFieldBehavior.this.getControl();
/*     */ 
/*  69 */       IndexRange localIndexRange = localTextField.getSelection();
/*  70 */       int i = localIndexRange.getStart();
/*  71 */       int j = localIndexRange.getEnd();
/*     */ 
/*  73 */       switch (TextFieldBehavior.3.$SwitchMap$javafx$geometry$HorizontalDirection[TextFieldBehavior.this.scrollDirection.ordinal()]) {
/*     */       case 1:
/*  75 */         if (j < localTextField.getLength()) {
/*  76 */           j++;
/*  77 */           localTextField.selectRange(i, j); } break;
/*     */       case 2:
/*  84 */         if (i > 0) {
/*  85 */           i--;
/*  86 */           localTextField.selectRange(i, j); } break;
/*     */       default:
/*  93 */         throw new RuntimeException();
/*     */       }
/*     */     }
/*  64 */   };
/*     */   private ContextMenu contextMenu;
/* 181 */   private boolean focusGainedByMouseClick = false;
/* 182 */   private boolean shiftDown = false;
/* 183 */   private boolean deferClick = false;
/*     */ 
/*     */   public TextFieldBehavior(TextField paramTextField)
/*     */   {
/* 102 */     super(paramTextField);
/*     */ 
/* 104 */     this.contextMenu = new ContextMenu();
/* 105 */     if (PlatformUtil.isEmbedded()) {
/* 106 */       this.contextMenu.getStyleClass().add("text-input-context-menu");
/*     */     }
/*     */ 
/* 110 */     this.scrollSelectionTimeline.setCycleCount(-1);
/* 111 */     ObservableList localObservableList = this.scrollSelectionTimeline.getKeyFrames();
/* 112 */     localObservableList.add(new KeyFrame(Duration.millis(15.0D), this.scrollSelectionHandler, new KeyValue[0]));
/* 113 */     handleFocusChange();
/*     */ 
/* 116 */     paramTextField.focusedProperty().addListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 119 */         TextFieldBehavior.this.handleFocusChange();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void handleFocusChange() {
/* 125 */     TextField localTextField = (TextField)getControl();
/*     */ 
/* 127 */     if (localTextField.isFocused()) {
/* 128 */       if (!this.focusGainedByMouseClick) {
/* 129 */         localTextField.selectRange(localTextField.getLength(), 0);
/* 130 */         setCaretAnimating(true);
/*     */       }
/*     */     } else {
/* 133 */       localTextField.selectRange(0, 0);
/* 134 */       this.focusGainedByMouseClick = false;
/* 135 */       setCaretAnimating(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTextFieldSkin(TextFieldSkin paramTextFieldSkin)
/*     */   {
/* 141 */     this.skin = paramTextFieldSkin;
/*     */   }
/*     */ 
/*     */   protected void fire(KeyEvent paramKeyEvent) {
/* 145 */     TextField localTextField = (TextField)getControl();
/*     */ 
/* 147 */     if (localTextField.getOnAction() != null)
/* 148 */       localTextField.fireEvent(new ActionEvent(localTextField, null));
/*     */     else
/* 150 */       forwardToParent(paramKeyEvent);
/*     */   }
/*     */ 
/*     */   protected void deleteChar(boolean paramBoolean)
/*     */   {
/* 155 */     this.skin.deleteChar(paramBoolean);
/*     */   }
/*     */ 
/*     */   protected void replaceText(int paramInt1, int paramInt2, String paramString) {
/* 159 */     this.skin.replaceText(paramInt1, paramInt2, paramString);
/*     */   }
/*     */ 
/*     */   protected void setCaretAnimating(boolean paramBoolean) {
/* 163 */     if (this.skin != null)
/* 164 */       this.skin.setCaretAnimating(paramBoolean);
/*     */   }
/*     */ 
/*     */   private void beep()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 186 */     TextField localTextField = (TextField)getControl();
/* 187 */     super.mousePressed(paramMouseEvent);
/*     */ 
/* 189 */     if (!localTextField.isDisabled())
/*     */     {
/* 194 */       if (!localTextField.isFocused()) {
/* 195 */         this.focusGainedByMouseClick = true;
/* 196 */         localTextField.requestFocus();
/*     */       }
/*     */ 
/* 200 */       setCaretAnimating(false);
/*     */ 
/* 205 */       if ((paramMouseEvent.isPrimaryButtonDown()) && (!paramMouseEvent.isMiddleButtonDown()) && (!paramMouseEvent.isSecondaryButtonDown())) {
/* 206 */         HitInfo localHitInfo = this.skin.getIndex(paramMouseEvent);
/* 207 */         int i = localHitInfo.getInsertionIndex();
/* 208 */         int j = localTextField.getAnchor();
/* 209 */         int k = localTextField.getCaretPosition();
/* 210 */         if ((paramMouseEvent.getClickCount() < 2) && ((PlatformUtil.isEmbedded()) || ((j != k) && (((i > j) && (i < k)) || ((i < j) && (i > k))))))
/*     */         {
/* 220 */           this.deferClick = true;
/*     */         }
/* 224 */         else if ((!paramMouseEvent.isControlDown()) && (!paramMouseEvent.isAltDown()) && (!paramMouseEvent.isShiftDown()) && (!paramMouseEvent.isMetaDown())) {
/* 225 */           switch (paramMouseEvent.getClickCount()) { case 1:
/* 226 */             mouseSingleClick(localHitInfo); break;
/*     */           case 2:
/* 227 */             mouseDoubleClick(localHitInfo); break;
/*     */           case 3:
/* 228 */             mouseTripleClick(localHitInfo); }
/*     */         }
/* 230 */         else if ((paramMouseEvent.isShiftDown()) && (!paramMouseEvent.isControlDown()) && (!paramMouseEvent.isAltDown()) && (!paramMouseEvent.isMetaDown()) && (paramMouseEvent.getClickCount() == 1))
/*     */         {
/* 232 */           this.shiftDown = true;
/*     */ 
/* 239 */           if (PlatformUtil.isMac())
/* 240 */             localTextField.extendSelection(i);
/*     */           else {
/* 242 */             this.skin.positionCaret(localHitInfo, true);
/*     */           }
/*     */         }
/* 245 */         this.skin.setForwardBias(localHitInfo.isLeading());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 250 */     if (this.contextMenu.isShowing())
/* 251 */       this.contextMenu.hide();
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent paramMouseEvent)
/*     */   {
/* 256 */     TextField localTextField = (TextField)getControl();
/*     */ 
/* 259 */     if ((!localTextField.isDisabled()) && (!this.deferClick) && 
/* 260 */       (paramMouseEvent.isPrimaryButtonDown()) && (!paramMouseEvent.isMiddleButtonDown()) && (!paramMouseEvent.isSecondaryButtonDown()) && 
/* 261 */       (!paramMouseEvent.isControlDown()) && (!paramMouseEvent.isAltDown()) && (!paramMouseEvent.isShiftDown()) && (!paramMouseEvent.isMetaDown()))
/* 262 */       this.skin.positionCaret(this.skin.getIndex(paramMouseEvent), true);
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 269 */     TextField localTextField = (TextField)getControl();
/* 270 */     super.mouseReleased(paramMouseEvent);
/*     */ 
/* 273 */     if (!localTextField.isDisabled()) {
/* 274 */       setCaretAnimating(false);
/* 275 */       if (this.deferClick) {
/* 276 */         this.deferClick = false;
/* 277 */         this.skin.positionCaret(this.skin.getIndex(paramMouseEvent), this.shiftDown);
/* 278 */         this.shiftDown = false;
/*     */       }
/* 280 */       setCaretAnimating(true);
/*     */     }
/* 282 */     if (paramMouseEvent.getButton() == MouseButton.SECONDARY)
/* 283 */       if (this.contextMenu.isShowing()) {
/* 284 */         this.contextMenu.hide();
/*     */       } else {
/* 286 */         double d1 = paramMouseEvent.getScreenX();
/* 287 */         double d2 = paramMouseEvent.getScreenY();
/* 288 */         double d3 = paramMouseEvent.getSceneX();
/*     */ 
/* 290 */         if (PlatformUtil.isEmbedded())
/*     */         {
/*     */           Point2D localPoint2D1;
/* 292 */           if (localTextField.getSelection().getLength() == 0) {
/* 293 */             this.skin.positionCaret(this.skin.getIndex(paramMouseEvent), false);
/* 294 */             localPoint2D1 = this.skin.getMenuPosition();
/*     */           } else {
/* 296 */             localPoint2D1 = this.skin.getMenuPosition();
/* 297 */             if ((localPoint2D1 != null) && ((localPoint2D1.getX() <= 0.0D) || (localPoint2D1.getY() <= 0.0D))) {
/* 298 */               this.skin.positionCaret(this.skin.getIndex(paramMouseEvent), false);
/* 299 */               localPoint2D1 = this.skin.getMenuPosition();
/*     */             }
/*     */           }
/*     */ 
/* 303 */           if (localPoint2D1 != null) {
/* 304 */             Point2D localPoint2D2 = this.skin.localToScene(localPoint2D1);
/* 305 */             Scene localScene = this.skin.getScene();
/* 306 */             Window localWindow = localScene.getWindow();
/* 307 */             localObject = new Point2D(localWindow.getX() + localScene.getX() + localPoint2D2.getX(), localWindow.getY() + localScene.getY() + localPoint2D2.getY());
/*     */ 
/* 309 */             d1 = ((Point2D)localObject).getX();
/* 310 */             d3 = localPoint2D2.getX();
/* 311 */             d2 = ((Point2D)localObject).getY();
/*     */           }
/*     */         }
/*     */ 
/* 315 */         this.skin.populateContextMenu(this.contextMenu);
/* 316 */         double d4 = this.contextMenu.prefWidth(-1.0D);
/* 317 */         double d5 = d1 - (PlatformUtil.isEmbedded() ? d4 / 2.0D : 0.0D);
/* 318 */         Object localObject = Utils.getScreenForPoint(d1, 0.0D);
/* 319 */         Rectangle2D localRectangle2D = ((Screen)localObject).getBounds();
/*     */ 
/* 321 */         if (d5 < localRectangle2D.getMinX()) {
/* 322 */           this.skin.getProperties().put("CONTEXT_MENU_SCREEN_X", Double.valueOf(d1));
/* 323 */           this.skin.getProperties().put("CONTEXT_MENU_SCENE_X", Double.valueOf(d3));
/* 324 */           this.contextMenu.show(getControl(), localRectangle2D.getMinX(), d2);
/* 325 */         } else if (d1 + d4 > localRectangle2D.getMaxX()) {
/* 326 */           double d6 = d4 - (localRectangle2D.getMaxX() - d1);
/* 327 */           this.skin.getProperties().put("CONTEXT_MENU_SCREEN_X", Double.valueOf(d1));
/* 328 */           this.skin.getProperties().put("CONTEXT_MENU_SCENE_X", Double.valueOf(d3));
/* 329 */           this.contextMenu.show(getControl(), d1 - d6, d2);
/*     */         } else {
/* 331 */           this.skin.getProperties().put("CONTEXT_MENU_SCREEN_X", Integer.valueOf(0));
/* 332 */           this.skin.getProperties().put("CONTEXT_MENU_SCENE_X", Integer.valueOf(0));
/* 333 */           this.contextMenu.show(getControl(), d5, d2);
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   protected void mouseSingleClick(HitInfo paramHitInfo)
/*     */   {
/* 364 */     this.skin.positionCaret(paramHitInfo, false);
/*     */   }
/*     */ 
/*     */   protected void mouseDoubleClick(HitInfo paramHitInfo) {
/* 368 */     TextField localTextField = (TextField)getControl();
/* 369 */     localTextField.previousWord();
/* 370 */     if (PlatformUtil.isWindows())
/* 371 */       localTextField.selectNextWord();
/*     */     else
/* 373 */       localTextField.selectEndOfNextWord();
/*     */   }
/*     */ 
/*     */   protected void mouseTripleClick(HitInfo paramHitInfo)
/*     */   {
/* 378 */     ((TextField)getControl()).selectAll();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.TextFieldBehavior
 * JD-Core Version:    0.6.2
 */