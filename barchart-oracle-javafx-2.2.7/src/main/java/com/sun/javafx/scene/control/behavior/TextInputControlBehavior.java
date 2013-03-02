/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.scene.control.skin.TextInputControlSkin;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.control.IndexRange;
/*     */ import javafx.scene.control.TextInputControl;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ 
/*     */ public abstract class TextInputControlBehavior<T extends TextInputControl> extends BehaviorBase<T>
/*     */ {
/*  49 */   protected static final List<KeyBinding> TEXT_INPUT_BINDINGS = new ArrayList();
/*     */   private KeyEvent lastEvent;
/*  67 */   private TextInputControlBehavior<T>.UndoManager undoManager = new UndoManager();
/*     */ 
/* 382 */   private boolean editing = false;
/*     */ 
/*     */   public TextInputControlBehavior(T paramT)
/*     */   {
/*  78 */     super(paramT);
/*     */ 
/*  80 */     paramT.textProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/*  82 */         if (!TextInputControlBehavior.this.isEditing())
/*     */         {
/*  84 */           TextInputControlBehavior.this.undoManager.reset();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected abstract void deleteChar(boolean paramBoolean);
/*     */ 
/*     */   protected abstract void replaceText(int paramInt1, int paramInt2, String paramString);
/*     */ 
/*     */   protected abstract void setCaretAnimating(boolean paramBoolean);
/*     */ 
/*     */   protected void scrollCharacterToVisible(int paramInt)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/* 108 */     return TEXT_INPUT_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callActionForEvent(KeyEvent paramKeyEvent)
/*     */   {
/* 116 */     this.lastEvent = paramKeyEvent;
/* 117 */     super.callActionForEvent(paramKeyEvent);
/*     */   }
/*     */ 
/*     */   public void callAction(String paramString) {
/* 121 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 122 */     int i = 0;
/*     */ 
/* 124 */     setCaretAnimating(false);
/*     */ 
/* 126 */     if (localTextInputControl.isEditable()) {
/* 127 */       setEditing(true);
/* 128 */       i = 1;
/* 129 */       if ("InputCharacter".equals(paramString)) defaultKeyTyped(this.lastEvent);
/* 130 */       else if ("Cut".equals(paramString)) cut();
/* 131 */       else if ("Paste".equals(paramString)) paste();
/* 132 */       else if ("DeletePreviousChar".equals(paramString)) deletePreviousChar();
/* 133 */       else if ("DeleteNextChar".equals(paramString)) deleteNextChar();
/* 134 */       else if ("DeletePreviousWord".equals(paramString)) deletePreviousWord();
/* 135 */       else if ("DeleteNextWord".equals(paramString)) deleteNextWord();
/* 136 */       else if ("DeleteSelection".equals(paramString)) deleteSelection();
/* 137 */       else if ("Undo".equals(paramString)) this.undoManager.undo();
/* 138 */       else if ("Redo".equals(paramString)) this.undoManager.redo();
/*     */       else {
/* 140 */         i = 0;
/*     */       }
/* 142 */       setEditing(false);
/*     */     }
/* 144 */     if (i == 0) {
/* 145 */       i = 1;
/* 146 */       if ("Copy".equals(paramString)) localTextInputControl.copy();
/* 147 */       else if ("SelectBackward".equals(paramString)) localTextInputControl.selectBackward();
/* 148 */       else if ("SelectForward".equals(paramString)) localTextInputControl.selectForward();
/* 149 */       else if ("PreviousWord".equals(paramString)) previousWord();
/* 150 */       else if ("NextWord".equals(paramString)) nextWord();
/* 151 */       else if ("SelectPreviousWord".equals(paramString)) selectPreviousWord();
/* 152 */       else if ("SelectNextWord".equals(paramString)) selectNextWord();
/* 153 */       else if ("SelectWord".equals(paramString)) selectWord();
/* 154 */       else if ("SelectAll".equals(paramString)) localTextInputControl.selectAll();
/* 155 */       else if ("Home".equals(paramString)) localTextInputControl.home();
/* 156 */       else if ("End".equals(paramString)) localTextInputControl.end();
/* 157 */       else if ("Forward".equals(paramString)) localTextInputControl.forward();
/* 158 */       else if ("Backward".equals(paramString)) localTextInputControl.backward();
/* 159 */       else if ("Fire".equals(paramString)) fire(this.lastEvent);
/* 160 */       else if ("Unselect".equals(paramString)) localTextInputControl.deselect();
/* 161 */       else if ("SelectHome".equals(paramString)) selectHome();
/* 162 */       else if ("SelectEnd".equals(paramString)) selectEnd();
/* 163 */       else if ("SelectHomeExtend".equals(paramString)) selectHomeExtend();
/* 164 */       else if ("SelectEndExtend".equals(paramString)) selectEndExtend();
/* 165 */       else if ("ToParent".equals(paramString)) forwardToParent(this.lastEvent);
/* 166 */       else if (("UseVK".equals(paramString)) && (PlatformUtil.isEmbedded()))
/* 167 */         ((TextInputControlSkin)localTextInputControl.getSkin()).toggleUseVK();
/*     */       else {
/* 169 */         i = 0;
/*     */       }
/*     */     }
/* 172 */     setCaretAnimating(true);
/*     */ 
/* 174 */     if (i == 0)
/* 175 */       super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   protected TextInputControlBehavior<T>.UndoManager getUndoManager()
/*     */   {
/* 181 */     return this.undoManager;
/*     */   }
/*     */ 
/*     */   private void defaultKeyTyped(KeyEvent paramKeyEvent)
/*     */   {
/* 191 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/*     */ 
/* 194 */     if ((!localTextInputControl.isEditable()) || (localTextInputControl.isDisabled())) return;
/*     */ 
/* 198 */     String str = paramKeyEvent.getCharacter();
/* 199 */     if (str.length() == 0) return;
/*     */ 
/* 202 */     if (((paramKeyEvent.isControlDown()) || (paramKeyEvent.isAltDown()) || ((PlatformUtil.isMac()) && (paramKeyEvent.isMetaDown()))) && (
/* 203 */       ((!paramKeyEvent.isControlDown()) && (!PlatformUtil.isMac())) || (!paramKeyEvent.isAltDown()))) return;
/*     */ 
/* 208 */     if ((str.charAt(0) > '\037') && (str.charAt(0) != '') && (!paramKeyEvent.isMetaDown()))
/*     */     {
/* 211 */       IndexRange localIndexRange = localTextInputControl.getSelection();
/* 212 */       int i = localIndexRange.getStart();
/* 213 */       int j = localIndexRange.getEnd();
/*     */ 
/* 219 */       this.undoManager.addChange(i, localTextInputControl.textProperty().getValueSafe().substring(i, j), str, true);
/* 220 */       replaceText(i, j, str);
/*     */ 
/* 223 */       scrollCharacterToVisible(i);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void deletePreviousChar() {
/* 228 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 229 */     IndexRange localIndexRange = localTextInputControl.getSelection();
/* 230 */     int i = localIndexRange.getStart();
/* 231 */     int j = localIndexRange.getEnd();
/*     */ 
/* 233 */     if ((i > 0) || (j > i)) {
/* 234 */       if (localIndexRange.getLength() == 0) {
/* 235 */         j = i;
/* 236 */         i--;
/*     */       }
/* 238 */       this.undoManager.addChange(i, localTextInputControl.getText().substring(i, j), null);
/*     */     }
/* 240 */     deleteChar(true);
/*     */   }
/*     */ 
/*     */   private void deleteNextChar() {
/* 244 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 245 */     IndexRange localIndexRange = localTextInputControl.getSelection();
/* 246 */     int i = localIndexRange.getStart();
/* 247 */     int j = localIndexRange.getEnd();
/*     */ 
/* 249 */     if ((i < localTextInputControl.getLength()) || (j > i)) {
/* 250 */       if (localIndexRange.getLength() == 0) {
/* 251 */         j = i + 1;
/*     */       }
/* 253 */       this.undoManager.addChange(i, localTextInputControl.getText().substring(i, j), null);
/*     */     }
/* 255 */     deleteChar(false);
/*     */   }
/*     */ 
/*     */   protected void deletePreviousWord() {
/* 259 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 260 */     int i = localTextInputControl.getCaretPosition();
/*     */ 
/* 262 */     if (i > 0) {
/* 263 */       localTextInputControl.previousWord();
/* 264 */       int j = localTextInputControl.getCaretPosition();
/* 265 */       this.undoManager.addChange(j, localTextInputControl.getText().substring(j, i), null);
/* 266 */       replaceText(j, i, "");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void deleteNextWord() {
/* 271 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 272 */     int i = localTextInputControl.getCaretPosition();
/*     */ 
/* 274 */     if (i < localTextInputControl.getLength()) {
/* 275 */       nextWord();
/* 276 */       int j = localTextInputControl.getCaretPosition();
/* 277 */       this.undoManager.addChange(i, localTextInputControl.getText().substring(i, j), null);
/* 278 */       replaceText(i, j, "");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void deleteSelection() {
/* 283 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 284 */     IndexRange localIndexRange = localTextInputControl.getSelection();
/*     */ 
/* 286 */     if (localIndexRange.getLength() > 0) {
/* 287 */       int i = localIndexRange.getStart();
/* 288 */       int j = localIndexRange.getEnd();
/* 289 */       this.undoManager.addChange(i, localTextInputControl.getText().substring(i, j), null);
/* 290 */       deleteChar(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void cut() {
/* 295 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 296 */     IndexRange localIndexRange = localTextInputControl.getSelection();
/*     */ 
/* 298 */     if (localIndexRange.getLength() > 0) {
/* 299 */       int i = localIndexRange.getStart();
/* 300 */       int j = localIndexRange.getEnd();
/* 301 */       this.undoManager.addChange(i, localTextInputControl.getText().substring(i, j), null);
/*     */     }
/* 303 */     localTextInputControl.cut();
/*     */   }
/*     */ 
/*     */   private void paste() {
/* 307 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 308 */     IndexRange localIndexRange = localTextInputControl.getSelection();
/* 309 */     int i = localIndexRange.getStart();
/* 310 */     int j = localIndexRange.getEnd();
/* 311 */     String str1 = localTextInputControl.textProperty().getValueSafe();
/* 312 */     String str2 = str1.substring(i, j);
/* 313 */     int k = str1.length() - j;
/*     */ 
/* 315 */     localTextInputControl.paste();
/*     */ 
/* 317 */     str1 = localTextInputControl.textProperty().getValueSafe();
/* 318 */     this.undoManager.addChange(i, str2, str1.substring(i, str1.length() - k));
/*     */   }
/*     */ 
/*     */   protected void selectPreviousWord() {
/* 322 */     ((TextInputControl)getControl()).selectPreviousWord();
/*     */   }
/*     */ 
/*     */   protected void selectNextWord() {
/* 326 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 327 */     if ((PlatformUtil.isMac()) || (PlatformUtil.isLinux()))
/* 328 */       localTextInputControl.selectEndOfNextWord();
/*     */     else
/* 330 */       localTextInputControl.selectNextWord();
/*     */   }
/*     */ 
/*     */   protected void selectWord()
/*     */   {
/* 335 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 336 */     localTextInputControl.previousWord();
/* 337 */     if (PlatformUtil.isWindows())
/* 338 */       localTextInputControl.selectNextWord();
/*     */     else
/* 340 */       localTextInputControl.selectEndOfNextWord();
/*     */   }
/*     */ 
/*     */   protected void previousWord()
/*     */   {
/* 345 */     ((TextInputControl)getControl()).previousWord();
/*     */   }
/*     */ 
/*     */   protected void nextWord() {
/* 349 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 350 */     if ((PlatformUtil.isMac()) || (PlatformUtil.isLinux()))
/* 351 */       localTextInputControl.endOfNextWord();
/*     */     else
/* 353 */       localTextInputControl.nextWord();
/*     */   }
/*     */ 
/*     */   protected void fire(KeyEvent paramKeyEvent) {
/*     */   }
/*     */ 
/*     */   protected void forwardToParent(KeyEvent paramKeyEvent) {
/* 360 */     if (((TextInputControl)getControl()).getParent() != null)
/* 361 */       ((TextInputControl)getControl()).getParent().fireEvent(paramKeyEvent);
/*     */   }
/*     */ 
/*     */   private void selectHome()
/*     */   {
/* 366 */     ((TextInputControl)getControl()).selectHome();
/*     */   }
/*     */ 
/*     */   private void selectEnd() {
/* 370 */     ((TextInputControl)getControl()).selectEnd();
/*     */   }
/*     */ 
/*     */   private void selectHomeExtend() {
/* 374 */     ((TextInputControl)getControl()).extendSelection(0);
/*     */   }
/*     */ 
/*     */   private void selectEndExtend() {
/* 378 */     TextInputControl localTextInputControl = (TextInputControl)getControl();
/* 379 */     localTextInputControl.extendSelection(localTextInputControl.getLength());
/*     */   }
/*     */ 
/*     */   protected void setEditing(boolean paramBoolean)
/*     */   {
/* 384 */     this.editing = paramBoolean;
/*     */   }
/*     */   public boolean isEditing() {
/* 387 */     return this.editing;
/*     */   }
/*     */ 
/*     */   public boolean canUndo() {
/* 391 */     return this.undoManager.canUndo();
/*     */   }
/*     */ 
/*     */   public boolean canRedo() {
/* 395 */     return this.undoManager.canRedo();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  51 */     TEXT_INPUT_BINDINGS.addAll(TextInputControlBindings.BINDINGS);
/*     */ 
/*  54 */     TEXT_INPUT_BINDINGS.add(new KeyBinding(null, KeyEvent.KEY_PRESSED, "Consume"));
/*     */   }
/*     */ 
/*     */   class UndoManager
/*     */   {
/* 417 */     private ArrayList<TextInputControlBehavior.Change> chain = new ArrayList();
/* 418 */     private int currentIndex = 0;
/*     */ 
/*     */     UndoManager() {  } 
/* 421 */     public void addChange(int paramInt, String paramString1, String paramString2) { addChange(paramInt, paramString1, paramString2, false); }
/*     */ 
/*     */     public void addChange(int paramInt, String paramString1, String paramString2, boolean paramBoolean)
/*     */     {
/* 425 */       truncate();
/* 426 */       if ((paramBoolean) && (this.currentIndex > 0) && ((paramString1 == null) || (paramString1.length() == 0))) {
/* 427 */         TextInputControlBehavior.Change localChange = (TextInputControlBehavior.Change)this.chain.get(this.currentIndex - 1);
/* 428 */         if ((localChange.appendable) && (paramInt == localChange.start + localChange.newText.length()))
/*     */         {
/* 430 */           localChange.newText += paramString2;
/* 431 */           return;
/*     */         }
/*     */       }
/* 434 */       this.chain.add(new TextInputControlBehavior.Change(paramInt, paramString1, paramString2, paramBoolean));
/* 435 */       this.currentIndex += 1;
/*     */     }
/*     */ 
/*     */     public void undo() {
/* 439 */       if (this.currentIndex > 0)
/*     */       {
/* 441 */         TextInputControlBehavior.Change localChange = (TextInputControlBehavior.Change)this.chain.get(this.currentIndex - 1);
/* 442 */         TextInputControlBehavior.this.replaceText(localChange.start, localChange.start + (localChange.newText != null ? localChange.newText.length() : 0), localChange.oldText != null ? localChange.oldText : "");
/*     */ 
/* 445 */         this.currentIndex -= 1;
/* 446 */         if (this.currentIndex > 0)
/* 447 */           ((TextInputControlBehavior.Change)this.chain.get(this.currentIndex - 1)).appendable = false;
/*     */       }
/*     */     }
/*     */ 
/*     */     public void redo()
/*     */     {
/* 454 */       if (this.currentIndex < this.chain.size())
/*     */       {
/* 456 */         TextInputControlBehavior.Change localChange = (TextInputControlBehavior.Change)this.chain.get(this.currentIndex);
/* 457 */         TextInputControlBehavior.this.replaceText(localChange.start, localChange.start + (localChange.oldText != null ? localChange.oldText.length() : 0), localChange.newText != null ? localChange.newText : "");
/*     */ 
/* 460 */         localChange.appendable = false;
/* 461 */         this.currentIndex += 1;
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean canUndo()
/*     */     {
/* 467 */       return this.currentIndex > 0;
/*     */     }
/*     */ 
/*     */     public boolean canRedo() {
/* 471 */       return this.currentIndex < this.chain.size();
/*     */     }
/*     */ 
/*     */     public void reset() {
/* 475 */       this.chain.clear();
/* 476 */       this.currentIndex = 0;
/*     */     }
/*     */ 
/*     */     private void truncate() {
/* 480 */       if ((this.currentIndex > 0) && (this.chain.size() > this.currentIndex)) {
/* 481 */         ((TextInputControlBehavior.Change)this.chain.get(this.currentIndex - 1)).appendable = false;
/*     */       }
/*     */ 
/* 484 */       while (this.chain.size() > this.currentIndex)
/* 485 */         this.chain.remove(this.chain.size() - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Change
/*     */   {
/*     */     int start;
/*     */     String oldText;
/*     */     String newText;
/*     */     boolean appendable;
/*     */ 
/*     */     Change(int paramInt, String paramString1, String paramString2)
/*     */     {
/* 405 */       this(paramInt, paramString1, paramString2, false);
/*     */     }
/*     */ 
/*     */     Change(int paramInt, String paramString1, String paramString2, boolean paramBoolean) {
/* 409 */       this.start = paramInt;
/* 410 */       this.oldText = paramString1;
/* 411 */       this.newText = paramString2;
/* 412 */       this.appendable = paramBoolean;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.TextInputControlBehavior
 * JD-Core Version:    0.6.2
 */