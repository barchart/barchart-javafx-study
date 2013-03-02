/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.scene.control.skin.TextAreaSkin;
/*     */ import com.sun.javafx.scene.text.HitInfo;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.control.IndexRange;
/*     */ import javafx.scene.control.TextArea;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.stage.Screen;
/*     */ import javafx.stage.Window;
/*     */ 
/*     */ public class TextAreaBehavior extends TextInputControlBehavior<TextArea>
/*     */ {
/*  61 */   protected static final List<KeyBinding> TEXT_AREA_BINDINGS = new ArrayList();
/*     */   private TextAreaSkin skin;
/*     */   private ContextMenu contextMenu;
/* 265 */   private boolean focusGainedByMouseClick = false;
/* 266 */   private boolean shiftDown = false;
/* 267 */   private boolean deferClick = false;
/*     */ 
/*     */   public TextAreaBehavior(TextArea paramTextArea)
/*     */   {
/* 138 */     super(paramTextArea);
/*     */ 
/* 140 */     this.contextMenu = new ContextMenu();
/* 141 */     if (PlatformUtil.isEmbedded()) {
/* 142 */       this.contextMenu.getStyleClass().add("text-input-context-menu");
/*     */     }
/*     */ 
/* 146 */     paramTextArea.focusedProperty().addListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 149 */         TextArea localTextArea = (TextArea)TextAreaBehavior.this.getControl();
/* 150 */         if (localTextArea.isFocused()) {
/* 151 */           if (!TextAreaBehavior.this.focusGainedByMouseClick)
/* 152 */             TextAreaBehavior.this.setCaretAnimating(true);
/*     */         }
/*     */         else
/*     */         {
/* 156 */           TextAreaBehavior.this.focusGainedByMouseClick = false;
/* 157 */           TextAreaBehavior.this.setCaretAnimating(false);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void setTextAreaSkin(TextAreaSkin paramTextAreaSkin)
/*     */   {
/* 165 */     this.skin = paramTextAreaSkin;
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/* 173 */     return TEXT_AREA_BINDINGS;
/*     */   }
/*     */ 
/*     */   public void callAction(String paramString) {
/* 177 */     TextArea localTextArea = (TextArea)getControl();
/*     */ 
/* 179 */     int i = 0;
/*     */ 
/* 181 */     if (localTextArea.isEditable())
/*     */     {
/* 184 */       setEditing(true);
/* 185 */       i = 1;
/* 186 */       if ("InsertNewLine".equals(paramString)) insertNewLine();
/* 187 */       else if ("InsertTab".equals(paramString)) insertTab();
/*     */       else {
/* 189 */         i = 0;
/*     */       }
/* 191 */       setEditing(false);
/*     */     }
/*     */ 
/* 194 */     if (i == 0) {
/* 195 */       i = 1;
/* 196 */       if ("LineStart".equals(paramString)) this.skin.lineStart(false, false);
/* 197 */       else if ("LineEnd".equals(paramString)) this.skin.lineEnd(false, false);
/* 198 */       else if ("SelectLineStart".equals(paramString)) this.skin.lineStart(true, false);
/* 199 */       else if ("SelectLineStartExtend".equals(paramString)) this.skin.lineStart(true, true);
/* 200 */       else if ("SelectLineEnd".equals(paramString)) this.skin.lineEnd(true, false);
/* 201 */       else if ("SelectLineEndExtend".equals(paramString)) this.skin.lineEnd(true, true);
/* 202 */       else if ("PreviousLine".equals(paramString)) this.skin.previousLine(false);
/* 203 */       else if ("NextLine".equals(paramString)) this.skin.nextLine(false);
/* 204 */       else if ("SelectPreviousLine".equals(paramString)) this.skin.previousLine(true);
/* 205 */       else if ("SelectNextLine".equals(paramString)) this.skin.nextLine(true);
/* 207 */       else if ("ParagraphStart".equals(paramString)) this.skin.paragraphStart(true, false);
/* 208 */       else if ("ParagraphEnd".equals(paramString)) this.skin.paragraphEnd(true, false);
/* 209 */       else if ("SelectParagraphStart".equals(paramString)) this.skin.paragraphStart(true, true);
/* 210 */       else if ("SelectParagraphEnd".equals(paramString)) this.skin.paragraphEnd(true, true);
/* 212 */       else if ("PreviousPage".equals(paramString)) this.skin.previousPage(false);
/* 213 */       else if ("NextPage".equals(paramString)) this.skin.nextPage(false);
/* 214 */       else if ("SelectPreviousPage".equals(paramString)) this.skin.previousPage(true);
/* 215 */       else if ("SelectNextPage".equals(paramString)) this.skin.nextPage(true);
/*     */       else {
/* 217 */         i = 0;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 222 */     if (i == 0)
/* 223 */       super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   private void insertNewLine()
/*     */   {
/* 228 */     TextArea localTextArea = (TextArea)getControl();
/* 229 */     IndexRange localIndexRange = localTextArea.getSelection();
/* 230 */     int i = localIndexRange.getStart();
/* 231 */     int j = localIndexRange.getEnd();
/*     */ 
/* 233 */     getUndoManager().addChange(i, localTextArea.getText().substring(i, j), "\n", false);
/* 234 */     localTextArea.replaceSelection("\n");
/*     */   }
/*     */ 
/*     */   private void insertTab() {
/* 238 */     TextArea localTextArea = (TextArea)getControl();
/* 239 */     IndexRange localIndexRange = localTextArea.getSelection();
/* 240 */     int i = localIndexRange.getStart();
/* 241 */     int j = localIndexRange.getEnd();
/*     */ 
/* 243 */     getUndoManager().addChange(i, localTextArea.getText().substring(i, j), "\t", false);
/* 244 */     localTextArea.replaceSelection("\t");
/*     */   }
/*     */ 
/*     */   protected void deleteChar(boolean paramBoolean) {
/* 248 */     this.skin.deleteChar(paramBoolean);
/*     */   }
/*     */ 
/*     */   protected void scrollCharacterToVisible(int paramInt)
/*     */   {
/* 254 */     this.skin.scrollCharacterToVisible(paramInt);
/*     */   }
/*     */ 
/*     */   protected void replaceText(int paramInt1, int paramInt2, String paramString) {
/* 258 */     ((TextArea)getControl()).replaceText(paramInt1, paramInt2, paramString);
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 270 */     TextArea localTextArea = (TextArea)getControl();
/* 271 */     super.mousePressed(paramMouseEvent);
/*     */ 
/* 273 */     if (!localTextArea.isDisabled())
/*     */     {
/* 278 */       if (!localTextArea.isFocused()) {
/* 279 */         this.focusGainedByMouseClick = true;
/* 280 */         localTextArea.requestFocus();
/*     */       }
/*     */ 
/* 284 */       setCaretAnimating(false);
/*     */ 
/* 289 */       if ((paramMouseEvent.getButton() == MouseButton.PRIMARY) && (!paramMouseEvent.isMiddleButtonDown()) && (!paramMouseEvent.isSecondaryButtonDown())) {
/* 290 */         HitInfo localHitInfo = this.skin.getIndex(paramMouseEvent);
/* 291 */         int i = localHitInfo.getInsertionIndex();
/*     */ 
/* 293 */         int j = localTextArea.getAnchor();
/* 294 */         int k = localTextArea.getCaretPosition();
/* 295 */         if ((paramMouseEvent.getClickCount() < 2) && ((PlatformUtil.isEmbedded()) || ((j != k) && (((i > j) && (i < k)) || ((i < j) && (i > k))))))
/*     */         {
/* 305 */           this.deferClick = true;
/*     */         }
/* 309 */         else if ((!paramMouseEvent.isControlDown()) && (!paramMouseEvent.isAltDown()) && (!paramMouseEvent.isShiftDown()) && (!paramMouseEvent.isMetaDown())) {
/* 310 */           switch (paramMouseEvent.getClickCount()) { case 1:
/* 311 */             this.skin.positionCaret(localHitInfo, false, false); break;
/*     */           case 2:
/* 312 */             mouseDoubleClick(localHitInfo); break;
/*     */           case 3:
/* 313 */             mouseTripleClick(localHitInfo); }
/*     */         }
/* 315 */         else if ((paramMouseEvent.isShiftDown()) && (!paramMouseEvent.isControlDown()) && (!paramMouseEvent.isAltDown()) && (!paramMouseEvent.isMetaDown()) && (paramMouseEvent.getClickCount() == 1))
/*     */         {
/* 317 */           this.shiftDown = true;
/*     */ 
/* 324 */           if (PlatformUtil.isMac())
/* 325 */             localTextArea.extendSelection(i);
/*     */           else {
/* 327 */             this.skin.positionCaret(localHitInfo, true, false);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 334 */       if (this.contextMenu.isShowing())
/* 335 */         this.contextMenu.hide();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent paramMouseEvent)
/*     */   {
/* 341 */     TextArea localTextArea = (TextArea)getControl();
/*     */ 
/* 344 */     if ((!localTextArea.isDisabled()) && (!this.deferClick) && 
/* 345 */       (paramMouseEvent.getButton() == MouseButton.PRIMARY) && (!paramMouseEvent.isMiddleButtonDown()) && (!paramMouseEvent.isSecondaryButtonDown()) && 
/* 346 */       (!paramMouseEvent.isControlDown()) && (!paramMouseEvent.isAltDown()) && (!paramMouseEvent.isShiftDown()) && (!paramMouseEvent.isMetaDown()))
/* 347 */       this.skin.positionCaret(this.skin.getIndex(paramMouseEvent), true, false);
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 354 */     TextArea localTextArea = (TextArea)getControl();
/* 355 */     super.mouseReleased(paramMouseEvent);
/*     */ 
/* 358 */     if (!localTextArea.isDisabled()) {
/* 359 */       setCaretAnimating(false);
/* 360 */       if (this.deferClick) {
/* 361 */         this.deferClick = false;
/* 362 */         this.skin.positionCaret(this.skin.getIndex(paramMouseEvent), this.shiftDown, false);
/* 363 */         this.shiftDown = false;
/*     */       }
/* 365 */       setCaretAnimating(true);
/*     */     }
/* 367 */     if (paramMouseEvent.getButton() == MouseButton.SECONDARY)
/* 368 */       if (this.contextMenu.isShowing()) {
/* 369 */         this.contextMenu.hide();
/*     */       } else {
/* 371 */         double d1 = paramMouseEvent.getScreenX();
/* 372 */         double d2 = paramMouseEvent.getScreenY();
/* 373 */         double d3 = paramMouseEvent.getSceneX();
/*     */ 
/* 375 */         if (PlatformUtil.isEmbedded())
/*     */         {
/*     */           Point2D localPoint2D1;
/* 377 */           if (localTextArea.getSelection().getLength() == 0) {
/* 378 */             this.skin.positionCaret(this.skin.getIndex(paramMouseEvent), false, false);
/* 379 */             localPoint2D1 = this.skin.getMenuPosition();
/*     */           } else {
/* 381 */             localPoint2D1 = this.skin.getMenuPosition();
/* 382 */             if ((localPoint2D1 != null) && ((localPoint2D1.getX() <= 0.0D) || (localPoint2D1.getY() <= 0.0D))) {
/* 383 */               this.skin.positionCaret(this.skin.getIndex(paramMouseEvent), false, false);
/* 384 */               localPoint2D1 = this.skin.getMenuPosition();
/*     */             }
/*     */           }
/*     */ 
/* 388 */           if (localPoint2D1 != null) {
/* 389 */             Point2D localPoint2D2 = this.skin.localToScene(localPoint2D1);
/* 390 */             Scene localScene = this.skin.getScene();
/* 391 */             Window localWindow = localScene.getWindow();
/* 392 */             localObject = new Point2D(localWindow.getX() + localScene.getX() + localPoint2D2.getX(), localWindow.getY() + localScene.getY() + localPoint2D2.getY());
/*     */ 
/* 394 */             d1 = ((Point2D)localObject).getX();
/* 395 */             d3 = localPoint2D2.getX();
/* 396 */             d2 = ((Point2D)localObject).getY();
/*     */           }
/*     */         }
/*     */ 
/* 400 */         this.skin.populateContextMenu(this.contextMenu);
/* 401 */         double d4 = this.contextMenu.prefWidth(-1.0D);
/* 402 */         double d5 = d1 - (PlatformUtil.isEmbedded() ? d4 / 2.0D : 0.0D);
/* 403 */         Object localObject = Utils.getScreenForPoint(d1, 0.0D);
/* 404 */         Rectangle2D localRectangle2D = ((Screen)localObject).getBounds();
/*     */ 
/* 406 */         if (d5 < localRectangle2D.getMinX()) {
/* 407 */           this.skin.getProperties().put("CONTEXT_MENU_SCREEN_X", Double.valueOf(d1));
/* 408 */           this.skin.getProperties().put("CONTEXT_MENU_SCENE_X", Double.valueOf(d3));
/* 409 */           this.contextMenu.show(getControl(), localRectangle2D.getMinX(), d2);
/* 410 */         } else if (d1 + d4 > localRectangle2D.getMaxX()) {
/* 411 */           double d6 = d4 - (localRectangle2D.getMaxX() - d1);
/* 412 */           this.skin.getProperties().put("CONTEXT_MENU_SCREEN_X", Double.valueOf(d1));
/* 413 */           this.skin.getProperties().put("CONTEXT_MENU_SCENE_X", Double.valueOf(d3));
/* 414 */           this.contextMenu.show(getControl(), d1 - d6, d2);
/*     */         } else {
/* 416 */           this.skin.getProperties().put("CONTEXT_MENU_SCREEN_X", Integer.valueOf(0));
/* 417 */           this.skin.getProperties().put("CONTEXT_MENU_SCENE_X", Integer.valueOf(0));
/* 418 */           this.contextMenu.show(getControl(), d5, d2);
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   protected void setCaretAnimating(boolean paramBoolean)
/*     */   {
/* 425 */     this.skin.setCaretAnimating(paramBoolean);
/*     */   }
/*     */ 
/*     */   protected void mouseDoubleClick(HitInfo paramHitInfo) {
/* 429 */     TextArea localTextArea = (TextArea)getControl();
/* 430 */     localTextArea.previousWord();
/* 431 */     if (PlatformUtil.isWindows())
/* 432 */       localTextArea.selectNextWord();
/*     */     else
/* 434 */       localTextArea.selectEndOfNextWord();
/*     */   }
/*     */ 
/*     */   protected void mouseTripleClick(HitInfo paramHitInfo)
/*     */   {
/* 440 */     this.skin.paragraphStart(false, false);
/* 441 */     this.skin.paragraphEnd(false, true);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  63 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.TAB, KeyEvent.KEY_PRESSED, "TraverseNext").ctrl());
/*  64 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.TAB, KeyEvent.KEY_PRESSED, "TraversePrevious").ctrl().shift());
/*  65 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.HOME, KeyEvent.KEY_PRESSED, "LineStart"));
/*  66 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.END, KeyEvent.KEY_PRESSED, "LineEnd"));
/*  67 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.UP, KeyEvent.KEY_PRESSED, "PreviousLine"));
/*  68 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, KeyEvent.KEY_PRESSED, "PreviousLine"));
/*  69 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.DOWN, KeyEvent.KEY_PRESSED, "NextLine"));
/*  70 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, KeyEvent.KEY_PRESSED, "NextLine"));
/*  71 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, KeyEvent.KEY_PRESSED, "PreviousPage"));
/*  72 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, KeyEvent.KEY_PRESSED, "NextPage"));
/*  73 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.ENTER, KeyEvent.KEY_PRESSED, "InsertNewLine"));
/*  74 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.TAB, KeyEvent.KEY_PRESSED, "InsertTab"));
/*     */ 
/*  76 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.HOME, KeyEvent.KEY_PRESSED, "SelectLineStart").shift());
/*  77 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.END, KeyEvent.KEY_PRESSED, "SelectLineEnd").shift());
/*  78 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.UP, KeyEvent.KEY_PRESSED, "SelectPreviousLine").shift());
/*  79 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, KeyEvent.KEY_PRESSED, "SelectPreviousLine").shift());
/*  80 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.DOWN, KeyEvent.KEY_PRESSED, "SelectNextLine").shift());
/*  81 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, KeyEvent.KEY_PRESSED, "SelectNextLine").shift());
/*  82 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, KeyEvent.KEY_PRESSED, "SelectPreviousPage").shift());
/*  83 */     TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, KeyEvent.KEY_PRESSED, "SelectNextPage").shift());
/*     */ 
/*  85 */     if (PlatformUtil.isMac()) {
/*  86 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.LEFT, KeyEvent.KEY_PRESSED, "LineStart").meta());
/*  87 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, KeyEvent.KEY_PRESSED, "LineStart").meta());
/*  88 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, KeyEvent.KEY_PRESSED, "LineEnd").meta());
/*  89 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, KeyEvent.KEY_PRESSED, "LineEnd").meta());
/*  90 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.UP, KeyEvent.KEY_PRESSED, "Home").meta());
/*  91 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, KeyEvent.KEY_PRESSED, "Home").meta());
/*  92 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.DOWN, KeyEvent.KEY_PRESSED, "End").meta());
/*  93 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, KeyEvent.KEY_PRESSED, "End").meta());
/*     */ 
/*  95 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.LEFT, KeyEvent.KEY_PRESSED, "SelectLineStartExtend").shift().meta());
/*  96 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, KeyEvent.KEY_PRESSED, "SelectLineStartExtend").shift().meta());
/*  97 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, KeyEvent.KEY_PRESSED, "SelectLineEndExtend").shift().meta());
/*  98 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, KeyEvent.KEY_PRESSED, "SelectLineEndExtend").shift().meta());
/*  99 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.UP, KeyEvent.KEY_PRESSED, "SelectHomeExtend").meta().shift());
/* 100 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, KeyEvent.KEY_PRESSED, "SelectHomeExtend").meta().shift());
/* 101 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.DOWN, KeyEvent.KEY_PRESSED, "SelectEndExtend").meta().shift());
/* 102 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, KeyEvent.KEY_PRESSED, "SelectEndExtend").meta().shift());
/*     */ 
/* 104 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.UP, KeyEvent.KEY_PRESSED, "ParagraphStart").alt());
/* 105 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, KeyEvent.KEY_PRESSED, "ParagraphStart").alt());
/* 106 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.DOWN, KeyEvent.KEY_PRESSED, "ParagraphEnd").alt());
/* 107 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, KeyEvent.KEY_PRESSED, "ParagraphEnd").alt());
/*     */ 
/* 109 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.UP, KeyEvent.KEY_PRESSED, "SelectParagraphStart").alt().shift());
/* 110 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, KeyEvent.KEY_PRESSED, "SelectParagraphStart").alt().shift());
/* 111 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.DOWN, KeyEvent.KEY_PRESSED, "SelectParagraphEnd").alt().shift());
/* 112 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, KeyEvent.KEY_PRESSED, "SelectParagraphEnd").alt().shift());
/*     */     } else {
/* 114 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.UP, KeyEvent.KEY_PRESSED, "ParagraphStart").ctrl());
/* 115 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, KeyEvent.KEY_PRESSED, "ParagraphStart").ctrl());
/* 116 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.DOWN, KeyEvent.KEY_PRESSED, "ParagraphEnd").ctrl());
/* 117 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, KeyEvent.KEY_PRESSED, "ParagraphEnd").ctrl());
/* 118 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.UP, KeyEvent.KEY_PRESSED, "SelectParagraphStart").ctrl().shift());
/* 119 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, KeyEvent.KEY_PRESSED, "SelectParagraphStart").ctrl().shift());
/* 120 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.DOWN, KeyEvent.KEY_PRESSED, "SelectParagraphEnd").ctrl().shift());
/* 121 */       TEXT_AREA_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, KeyEvent.KEY_PRESSED, "SelectParagraphEnd").ctrl().shift());
/*     */     }
/*     */ 
/* 124 */     TEXT_AREA_BINDINGS.addAll(TextInputControlBindings.BINDINGS);
/*     */ 
/* 127 */     TEXT_AREA_BINDINGS.add(new KeyBinding(null, KeyEvent.KEY_PRESSED, "Consume"));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.TextAreaBehavior
 * JD-Core Version:    0.6.2
 */