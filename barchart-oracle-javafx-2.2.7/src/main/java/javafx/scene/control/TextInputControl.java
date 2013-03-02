/*      */ package javafx.scene.control;
/*      */ 
/*      */ import com.sun.javafx.Utils;
/*      */ import com.sun.javafx.binding.ExpressionHelper;
/*      */ import com.sun.javafx.css.StyleManager;
/*      */ import java.text.BreakIterator;
/*      */ import javafx.beans.DefaultProperty;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.binding.IntegerBinding;
/*      */ import javafx.beans.binding.StringBinding;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*      */ import javafx.beans.property.ReadOnlyIntegerWrapper;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*      */ import javafx.beans.property.ReadOnlyStringProperty;
/*      */ import javafx.beans.property.ReadOnlyStringWrapper;
/*      */ import javafx.beans.property.SimpleBooleanProperty;
/*      */ import javafx.beans.property.SimpleStringProperty;
/*      */ import javafx.beans.property.StringProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableStringValue;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.scene.input.Clipboard;
/*      */ import javafx.scene.input.ClipboardContent;
/*      */ 
/*      */ @DefaultProperty("text")
/*      */ public abstract class TextInputControl extends Control
/*      */ {
/*  164 */   private StringProperty promptText = new SimpleStringProperty(this, "promptText", "")
/*      */   {
/*      */     protected void invalidated() {
/*  167 */       String str = get();
/*  168 */       if ((str != null) && (str.contains("\n"))) {
/*  169 */         str = str.replace("\n", "");
/*  170 */         set(str);
/*      */       }
/*      */     }
/*  164 */   };
/*      */   private final Content content;
/*  191 */   private TextProperty text = new TextProperty(null);
/*      */ 
/*  199 */   private ReadOnlyIntegerWrapper length = new ReadOnlyIntegerWrapper(this, "length");
/*      */ 
/*  206 */   private BooleanProperty editable = new SimpleBooleanProperty(this, "editable", true) {
/*      */     protected void invalidated() {
/*  208 */       TextInputControl.this.impl_pseudoClassStateChanged("readonly");
/*      */     }
/*  206 */   };
/*      */ 
/*  218 */   private ReadOnlyObjectWrapper<IndexRange> selection = new ReadOnlyObjectWrapper(this, "selection", new IndexRange(0, 0));
/*      */ 
/*  225 */   private ReadOnlyStringWrapper selectedText = new ReadOnlyStringWrapper(this, "selectedText");
/*      */ 
/*  237 */   private ReadOnlyIntegerWrapper anchor = new ReadOnlyIntegerWrapper(this, "anchor", 0);
/*      */ 
/*  249 */   private ReadOnlyIntegerWrapper caretPosition = new ReadOnlyIntegerWrapper(this, "caretPosition", 0);
/*      */ 
/*  264 */   private boolean doNotAdjustCaret = false;
/*      */   private BreakIterator breakIterator;
/*      */   private static final String PSEUDO_CLASS_READONLY = "readonly";
/* 1077 */   private static final long PSEUDO_CLASS_READONLY_MASK = StyleManager.getInstance().getPseudoclassMask("readonly");
/*      */ 
/*      */   protected TextInputControl(final Content paramContent)
/*      */   {
/*  110 */     this.content = paramContent;
/*      */ 
/*  114 */     paramContent.addListener(new InvalidationListener() {
/*      */       public void invalidated(Observable paramAnonymousObservable) {
/*  116 */         if (paramContent.length() > 0) {
/*  117 */           TextInputControl.this.text.textIsNull = false;
/*      */         }
/*  119 */         TextInputControl.this.text.invalidate();
/*      */       }
/*      */     });
/*  124 */     this.length.bind(new IntegerBinding()
/*      */     {
/*      */       protected int computeValue() {
/*  127 */         String str = TextInputControl.this.text.get();
/*  128 */         return str == null ? 0 : str.length();
/*      */       }
/*      */     });
/*  133 */     this.selectedText.bind(new StringBinding()
/*      */     {
/*      */       protected String computeValue() {
/*  136 */         String str = TextInputControl.this.text.get();
/*  137 */         IndexRange localIndexRange = (IndexRange)TextInputControl.this.selection.get();
/*  138 */         if ((str == null) || (localIndexRange == null)) return "";
/*      */ 
/*  140 */         int i = localIndexRange.getStart();
/*  141 */         int j = localIndexRange.getEnd();
/*  142 */         int k = str.length();
/*  143 */         if (j > i + k) j = k;
/*  144 */         if (i > k - 1) i = j = 0;
/*  145 */         return str.substring(i, j);
/*      */       }
/*      */     });
/*  150 */     getStyleClass().add("text-input");
/*      */   }
/*      */ 
/*      */   public final StringProperty promptTextProperty()
/*      */   {
/*  174 */     return this.promptText;
/*      */   }
/*  176 */   public final String getPromptText() { return (String)this.promptText.get(); } 
/*  177 */   public final void setPromptText(String paramString) { this.promptText.set(paramString); }
/*      */ 
/*      */ 
/*      */   protected final Content getContent()
/*      */   {
/*  185 */     return this.content;
/*      */   }
/*      */ 
/*      */   public final String getText()
/*      */   {
/*  192 */     return this.text.get(); } 
/*  193 */   public final void setText(String paramString) { this.text.set(paramString); } 
/*  194 */   public final StringProperty textProperty() { return this.text; }
/*      */ 
/*      */ 
/*      */   public final int getLength()
/*      */   {
/*  200 */     return this.length.get(); } 
/*  201 */   public final ReadOnlyIntegerProperty lengthProperty() { return this.length.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   public final boolean isEditable()
/*      */   {
/*  211 */     return this.editable.getValue().booleanValue(); } 
/*  212 */   public final void setEditable(boolean paramBoolean) { this.editable.setValue(Boolean.valueOf(paramBoolean)); } 
/*  213 */   public final BooleanProperty editableProperty() { return this.editable; }
/*      */ 
/*      */ 
/*      */   public final IndexRange getSelection()
/*      */   {
/*  219 */     return (IndexRange)this.selection.getValue(); } 
/*  220 */   public final ReadOnlyObjectProperty<IndexRange> selectionProperty() { return this.selection.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   public final String getSelectedText()
/*      */   {
/*  226 */     return this.selectedText.get(); } 
/*  227 */   public final ReadOnlyStringProperty selectedTextProperty() { return this.selectedText.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   public final int getAnchor()
/*      */   {
/*  238 */     return this.anchor.get(); } 
/*  239 */   public final ReadOnlyIntegerProperty anchorProperty() { return this.anchor.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   public final int getCaretPosition()
/*      */   {
/*  250 */     return this.caretPosition.get(); } 
/*  251 */   public final ReadOnlyIntegerProperty caretPositionProperty() { return this.caretPosition.getReadOnlyProperty(); }
/*      */ 
/*      */ 
/*      */   public String getText(int paramInt1, int paramInt2)
/*      */   {
/*  280 */     if (paramInt1 > paramInt2) {
/*  281 */       throw new IllegalArgumentException("The start must be <= the end");
/*      */     }
/*      */ 
/*  284 */     if ((paramInt1 < 0) || (paramInt2 > getLength()))
/*      */     {
/*  286 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*  289 */     return getContent().get(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public void appendText(String paramString)
/*      */   {
/*  298 */     insertText(getLength(), paramString);
/*      */   }
/*      */ 
/*      */   public void insertText(int paramInt, String paramString)
/*      */   {
/*  308 */     replaceText(paramInt, paramInt, paramString);
/*      */   }
/*      */ 
/*      */   public void deleteText(IndexRange paramIndexRange)
/*      */   {
/*  319 */     replaceText(paramIndexRange, "");
/*      */   }
/*      */ 
/*      */   public void deleteText(int paramInt1, int paramInt2)
/*      */   {
/*  328 */     replaceText(paramInt1, paramInt2, "");
/*      */   }
/*      */ 
/*      */   public void replaceText(IndexRange paramIndexRange, String paramString)
/*      */   {
/*  340 */     if (paramIndexRange == null) {
/*  341 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*  344 */     int i = paramIndexRange.getStart();
/*  345 */     int j = i + paramIndexRange.getLength();
/*      */ 
/*  347 */     replaceText(i, j, paramString);
/*      */   }
/*      */ 
/*      */   public void replaceText(int paramInt1, int paramInt2, String paramString)
/*      */   {
/*  358 */     if (paramInt1 > paramInt2) {
/*  359 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*  362 */     if (paramString == null) {
/*  363 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*  366 */     if ((paramInt1 < 0) || (paramInt2 > getLength()))
/*      */     {
/*  368 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*  371 */     if (!this.text.isBound()) {
/*  372 */       getContent().delete(paramInt1, paramInt2, paramString.isEmpty());
/*  373 */       getContent().insert(paramInt1, paramString, true);
/*      */ 
/*  375 */       paramInt1 += paramString.length();
/*  376 */       selectRange(paramInt1, paramInt1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void cut()
/*      */   {
/*  385 */     copy();
/*  386 */     IndexRange localIndexRange = getSelection();
/*  387 */     deleteText(localIndexRange.getStart(), localIndexRange.getEnd());
/*      */   }
/*      */ 
/*      */   public void copy()
/*      */   {
/*  395 */     String str = getSelectedText();
/*  396 */     if (str.length() > 0) {
/*  397 */       ClipboardContent localClipboardContent = new ClipboardContent();
/*  398 */       localClipboardContent.putString(str);
/*  399 */       Clipboard.getSystemClipboard().setContent(localClipboardContent);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paste()
/*      */   {
/*  409 */     Clipboard localClipboard = Clipboard.getSystemClipboard();
/*  410 */     if (localClipboard.hasString()) {
/*  411 */       String str = localClipboard.getString();
/*  412 */       if (str != null)
/*  413 */         replaceSelection(str);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void selectBackward()
/*      */   {
/*  424 */     if ((getCaretPosition() > 0) && (getLength() > 0))
/*      */     {
/*  427 */       selectRange(getAnchor(), getCaretPosition() - 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void selectForward()
/*      */   {
/*  437 */     int i = getLength();
/*  438 */     if ((i > 0) && (getCaretPosition() < i))
/*  439 */       selectRange(getAnchor(), getCaretPosition() + 1);
/*      */   }
/*      */ 
/*      */   public void previousWord()
/*      */   {
/*  454 */     previousWord(false);
/*      */   }
/*      */ 
/*      */   public void nextWord()
/*      */   {
/*  462 */     nextWord(false);
/*      */   }
/*      */ 
/*      */   public void endOfNextWord()
/*      */   {
/*  470 */     endOfNextWord(false);
/*      */   }
/*      */ 
/*      */   public void selectPreviousWord()
/*      */   {
/*  479 */     previousWord(true);
/*      */   }
/*      */ 
/*      */   public void selectNextWord()
/*      */   {
/*  488 */     nextWord(true);
/*      */   }
/*      */ 
/*      */   public void selectEndOfNextWord()
/*      */   {
/*  496 */     endOfNextWord(true);
/*      */   }
/*      */ 
/*      */   private void previousWord(boolean paramBoolean) {
/*  500 */     int i = getLength();
/*  501 */     String str = getText();
/*  502 */     if (i <= 0) {
/*  503 */       return;
/*      */     }
/*      */ 
/*  506 */     if (this.breakIterator == null) {
/*  507 */       this.breakIterator = BreakIterator.getWordInstance();
/*      */     }
/*  509 */     this.breakIterator.setText(str);
/*      */ 
/*  511 */     int j = this.breakIterator.preceding(Utils.clamp(0, getCaretPosition(), i - 1));
/*      */ 
/*  514 */     while ((j != -1) && (!Character.isLetter(str.charAt(Utils.clamp(0, j, i - 1)))))
/*      */     {
/*  516 */       j = this.breakIterator.preceding(Utils.clamp(0, j, i - 1));
/*      */     }
/*      */ 
/*  520 */     selectRange(paramBoolean ? getAnchor() : j, j);
/*      */   }
/*      */ 
/*      */   private void nextWord(boolean paramBoolean) {
/*  524 */     int i = getLength();
/*  525 */     String str = getText();
/*  526 */     if (i <= 0) {
/*  527 */       return;
/*      */     }
/*      */ 
/*  530 */     if (this.breakIterator == null) {
/*  531 */       this.breakIterator = BreakIterator.getWordInstance();
/*      */     }
/*  533 */     this.breakIterator.setText(str);
/*      */ 
/*  535 */     int j = this.breakIterator.following(Utils.clamp(0, getCaretPosition(), i - 1));
/*  536 */     int k = this.breakIterator.next();
/*      */ 
/*  539 */     while (k != -1) {
/*  540 */       for (int m = j; m <= k; m++) {
/*  541 */         if (Character.isLetter(str.charAt(Utils.clamp(0, m, i - 1)))) {
/*  542 */           if (paramBoolean)
/*  543 */             selectRange(getAnchor(), m);
/*      */           else {
/*  545 */             selectRange(m, m);
/*      */           }
/*  547 */           return;
/*      */         }
/*      */       }
/*  550 */       j = k;
/*  551 */       k = this.breakIterator.next();
/*      */     }
/*      */ 
/*  555 */     if (paramBoolean)
/*  556 */       selectRange(getAnchor(), i);
/*      */     else
/*  558 */       end();
/*      */   }
/*      */ 
/*      */   private void endOfNextWord(boolean paramBoolean)
/*      */   {
/*  563 */     int i = getLength();
/*  564 */     String str = getText();
/*  565 */     if (i <= 0) {
/*  566 */       return;
/*      */     }
/*      */ 
/*  569 */     if (this.breakIterator == null) {
/*  570 */       this.breakIterator = BreakIterator.getWordInstance();
/*      */     }
/*  572 */     this.breakIterator.setText(str);
/*      */ 
/*  574 */     int j = this.breakIterator.following(Utils.clamp(0, getCaretPosition(), i - 1));
/*  575 */     int k = this.breakIterator.next();
/*      */ 
/*  578 */     while (k != -1) {
/*  579 */       for (int m = j; m <= k; m++) {
/*  580 */         if (!Character.isLetter(str.charAt(Utils.clamp(0, m, i - 1)))) {
/*  581 */           if (paramBoolean)
/*  582 */             selectRange(getAnchor(), m);
/*      */           else {
/*  584 */             selectRange(m, m);
/*      */           }
/*  586 */           return;
/*      */         }
/*      */       }
/*  589 */       j = k;
/*  590 */       k = this.breakIterator.next();
/*      */     }
/*      */ 
/*  594 */     if (paramBoolean)
/*  595 */       selectRange(getAnchor(), i);
/*      */     else
/*  597 */       end();
/*      */   }
/*      */ 
/*      */   public void selectAll()
/*      */   {
/*  605 */     selectRange(0, getLength());
/*      */   }
/*      */ 
/*      */   public void home()
/*      */   {
/*  614 */     selectRange(0, 0);
/*      */   }
/*      */ 
/*      */   public void end()
/*      */   {
/*  623 */     int i = getLength();
/*  624 */     if (i > 0)
/*  625 */       selectRange(i, i);
/*      */   }
/*      */ 
/*      */   public void selectHome()
/*      */   {
/*  635 */     selectRange(getAnchor(), 0);
/*      */   }
/*      */ 
/*      */   public void selectEnd()
/*      */   {
/*  644 */     int i = getLength();
/*  645 */     if (i > 0) selectRange(getAnchor(), i);
/*      */   }
/*      */ 
/*      */   public boolean deletePreviousChar()
/*      */   {
/*  654 */     int i = 1;
/*  655 */     if ((isEditable()) && (!isDisabled())) {
/*  656 */       String str = getText();
/*  657 */       int j = getCaretPosition();
/*  658 */       int k = getAnchor();
/*  659 */       if (j != k)
/*      */       {
/*  661 */         replaceSelection("");
/*  662 */         i = 0;
/*  663 */       } else if (j > 0)
/*      */       {
/*  668 */         int m = 1;
/*      */ 
/*  672 */         if (j > 1)
/*      */         {
/*  674 */           n = str.codePointAt(j - 2);
/*  675 */           i1 = str.codePointAt(j - 1);
/*  676 */           if ((n >= 55296) && (n <= 56319) && (i1 >= 56320) && (i1 <= 57343))
/*      */           {
/*  678 */             m = 2;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  683 */         this.doNotAdjustCaret = true;
/*  684 */         int n = j;
/*  685 */         deleteText(j - m, j);
/*  686 */         int i1 = n - m;
/*  687 */         selectRange(i1, i1);
/*  688 */         i = 0;
/*  689 */         this.doNotAdjustCaret = false;
/*      */       }
/*      */     }
/*  692 */     return i == 0;
/*      */   }
/*      */ 
/*      */   public boolean deleteNextChar()
/*      */   {
/*  701 */     int i = 1;
/*  702 */     if ((isEditable()) && (!isDisabled())) {
/*  703 */       String str = getText();
/*  704 */       int j = getCaretPosition();
/*  705 */       int k = getAnchor();
/*      */       int m;
/*  706 */       if (j != k)
/*      */       {
/*  708 */         replaceSelection("");
/*  709 */         m = Math.min(j, k);
/*  710 */         selectRange(m, m);
/*  711 */         i = 0;
/*  712 */       } else if ((str.length() > 0) && (j < str.length()))
/*      */       {
/*  717 */         m = 1;
/*      */ 
/*  721 */         if (j < str.length() - 2)
/*      */         {
/*  723 */           int n = str.codePointAt(j + 2);
/*  724 */           int i1 = str.codePointAt(j + 1);
/*  725 */           if ((n >= 55296) && (n <= 56319) && (i1 >= 56320) && (i1 <= 57343))
/*      */           {
/*  727 */             m = 2;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  732 */         this.doNotAdjustCaret = true;
/*      */ 
/*  734 */         deleteText(j, j + m);
/*  735 */         i = 0;
/*  736 */         this.doNotAdjustCaret = false;
/*      */       }
/*      */     }
/*  739 */     return i == 0;
/*      */   }
/*      */ 
/*      */   public void forward()
/*      */   {
/*  750 */     int i = getLength();
/*  751 */     int j = getCaretPosition();
/*  752 */     int k = getAnchor();
/*      */     int m;
/*  753 */     if (j != k) {
/*  754 */       m = Math.max(j, k);
/*  755 */       selectRange(m, m);
/*  756 */     } else if ((j < i) && (i > 0)) {
/*  757 */       m = j + 1;
/*  758 */       selectRange(m, m);
/*      */     }
/*  760 */     deselect();
/*      */   }
/*      */ 
/*      */   public void backward()
/*      */   {
/*  776 */     int i = getLength();
/*  777 */     int j = getCaretPosition();
/*  778 */     int k = getAnchor();
/*      */     int m;
/*  779 */     if (j != k) {
/*  780 */       m = Math.min(j, k);
/*  781 */       selectRange(m, m);
/*  782 */     } else if ((j > 0) && (i > 0)) {
/*  783 */       m = j - 1;
/*  784 */       selectRange(m, m);
/*      */     }
/*  786 */     deselect();
/*      */   }
/*      */ 
/*      */   public void positionCaret(int paramInt)
/*      */   {
/*  794 */     int i = Utils.clamp(0, paramInt, getLength());
/*  795 */     selectRange(i, i);
/*      */   }
/*      */ 
/*      */   public void selectPositionCaret(int paramInt)
/*      */   {
/*  805 */     selectRange(getAnchor(), Utils.clamp(0, paramInt, getLength()));
/*      */   }
/*      */ 
/*      */   public void selectRange(int paramInt1, int paramInt2)
/*      */   {
/*  812 */     this.caretPosition.set(Utils.clamp(0, paramInt2, getLength()));
/*  813 */     this.anchor.set(Utils.clamp(0, paramInt1, getLength()));
/*  814 */     this.selection.set(IndexRange.normalize(getAnchor(), getCaretPosition()));
/*      */   }
/*      */ 
/*      */   public void extendSelection(int paramInt)
/*      */   {
/*  825 */     int i = Utils.clamp(0, paramInt, getLength());
/*  826 */     int j = getCaretPosition();
/*  827 */     int k = getAnchor();
/*  828 */     int m = Math.min(j, k);
/*  829 */     int n = Math.max(j, k);
/*  830 */     if (i < m)
/*  831 */       selectRange(n, i);
/*      */     else
/*  833 */       selectRange(m, i);
/*      */   }
/*      */ 
/*      */   public void clear()
/*      */   {
/*  841 */     deselect();
/*  842 */     if (!this.text.isBound())
/*  843 */       setText("");
/*      */   }
/*      */ 
/*      */   public void deselect()
/*      */   {
/*  853 */     selectRange(getCaretPosition(), getCaretPosition());
/*      */   }
/*      */ 
/*      */   public void replaceSelection(String paramString)
/*      */   {
/*  863 */     if (this.text.isBound()) return;
/*      */ 
/*  865 */     if (paramString == null) {
/*  866 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*  869 */     int i = getCaretPosition();
/*  870 */     int j = getAnchor();
/*  871 */     int k = Math.min(i, j);
/*  872 */     int m = Math.max(i, j);
/*  873 */     int n = i;
/*      */ 
/*  875 */     if (getLength() == 0) {
/*  876 */       this.doNotAdjustCaret = true;
/*  877 */       setText(paramString);
/*  878 */       selectRange(getLength(), getLength());
/*  879 */       this.doNotAdjustCaret = false;
/*      */     } else {
/*  881 */       deselect();
/*      */ 
/*  883 */       this.doNotAdjustCaret = true;
/*  884 */       int i1 = getLength();
/*  885 */       m = Math.min(m, i1);
/*  886 */       if (m > k) {
/*  887 */         getContent().delete(k, m, paramString.isEmpty());
/*  888 */         i1 -= m - k;
/*      */       }
/*  890 */       getContent().insert(k, paramString, true);
/*      */ 
/*  892 */       int i2 = k + getLength() - i1;
/*  893 */       selectRange(i2, i2);
/*  894 */       this.doNotAdjustCaret = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   void textUpdated()
/*      */   {
/*      */   }
/*      */ 
/*      */   static String filterInput(String paramString, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  916 */     if (containsInvalidCharacters(paramString, paramBoolean1, paramBoolean2)) {
/*  917 */       StringBuilder localStringBuilder = new StringBuilder(paramString.length());
/*  918 */       for (int i = 0; i < paramString.length(); i++) {
/*  919 */         char c = paramString.charAt(i);
/*  920 */         if (!isInvalidCharacter(c, paramBoolean1, paramBoolean2)) {
/*  921 */           localStringBuilder.append(c);
/*      */         }
/*      */       }
/*  924 */       paramString = localStringBuilder.toString();
/*      */     }
/*  926 */     return paramString;
/*      */   }
/*      */ 
/*      */   static boolean containsInvalidCharacters(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/*  930 */     for (int i = 0; i < paramString.length(); i++) {
/*  931 */       char c = paramString.charAt(i);
/*  932 */       if (isInvalidCharacter(c, paramBoolean1, paramBoolean2)) return true;
/*      */     }
/*  934 */     return false;
/*      */   }
/*      */ 
/*      */   private static boolean isInvalidCharacter(char paramChar, boolean paramBoolean1, boolean paramBoolean2) {
/*  938 */     if (paramChar == '') return true;
/*  939 */     if (paramChar == '\n') return paramBoolean1;
/*  940 */     if (paramChar == '\t') return paramBoolean2;
/*  941 */     if (paramChar < ' ') return true;
/*  942 */     return false;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public long impl_getPseudoClassState()
/*      */   {
/* 1085 */     long l = super.impl_getPseudoClassState();
/*      */ 
/* 1087 */     if (!isEditable()) l |= PSEUDO_CLASS_READONLY_MASK;
/*      */ 
/* 1089 */     return l;
/*      */   }
/*      */ 
/*      */   private class TextProperty extends StringProperty
/*      */   {
/*      */     private ObservableValue<? extends String> observable;
/*      */     private InvalidationListener listener;
/*      */     private ExpressionHelper<String> helper;
/*      */     private boolean textIsNull;
/*      */ 
/*      */     private TextProperty()
/*      */     {
/*  952 */       this.observable = null;
/*      */ 
/*  954 */       this.listener = null;
/*      */ 
/*  956 */       this.helper = null;
/*      */ 
/*  961 */       this.textIsNull = false;
/*      */     }
/*      */ 
/*      */     public String get()
/*      */     {
/*  966 */       return this.textIsNull ? null : (String)TextInputControl.this.content.get();
/*      */     }
/*      */ 
/*      */     public void set(String paramString) {
/*  970 */       if (isBound()) {
/*  971 */         throw new RuntimeException("A bound value cannot be set.");
/*      */       }
/*  973 */       doSet(paramString);
/*  974 */       markInvalid();
/*      */     }
/*      */ 
/*      */     private void invalidate() {
/*  978 */       markInvalid();
/*      */     }
/*      */ 
/*      */     public void bind(ObservableValue<? extends String> paramObservableValue) {
/*  982 */       if (paramObservableValue == null) {
/*  983 */         throw new NullPointerException("Cannot bind to null");
/*      */       }
/*  985 */       if (!paramObservableValue.equals(this.observable)) {
/*  986 */         unbind();
/*  987 */         this.observable = paramObservableValue;
/*  988 */         if (this.listener == null) {
/*  989 */           this.listener = new Listener(null);
/*      */         }
/*  991 */         this.observable.addListener(this.listener);
/*  992 */         markInvalid();
/*  993 */         doSet((String)paramObservableValue.getValue());
/*      */       }
/*      */     }
/*      */ 
/*      */     public void unbind() {
/*  998 */       if (this.observable != null) {
/*  999 */         doSet((String)this.observable.getValue());
/* 1000 */         this.observable.removeListener(this.listener);
/* 1001 */         this.observable = null;
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean isBound() {
/* 1006 */       return this.observable != null;
/*      */     }
/*      */ 
/*      */     public void addListener(InvalidationListener paramInvalidationListener) {
/* 1010 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*      */     }
/*      */ 
/*      */     public void removeListener(InvalidationListener paramInvalidationListener) {
/* 1014 */       this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*      */     }
/*      */ 
/*      */     public void addListener(ChangeListener<? super String> paramChangeListener) {
/* 1018 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*      */     }
/*      */ 
/*      */     public void removeListener(ChangeListener<? super String> paramChangeListener) {
/* 1022 */       this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*      */     }
/*      */ 
/*      */     public Object getBean() {
/* 1026 */       return TextInputControl.this;
/*      */     }
/*      */ 
/*      */     public String getName() {
/* 1030 */       return "text";
/*      */     }
/*      */ 
/*      */     private void fireValueChangedEvent() {
/* 1034 */       ExpressionHelper.fireValueChangedEvent(this.helper);
/*      */     }
/*      */ 
/*      */     private void markInvalid() {
/* 1038 */       fireValueChangedEvent();
/*      */     }
/*      */ 
/*      */     private void doSet(String paramString)
/*      */     {
/* 1043 */       this.textIsNull = (paramString == null);
/* 1044 */       if (paramString == null) paramString = "";
/*      */ 
/* 1046 */       TextInputControl.this.content.delete(0, TextInputControl.this.content.length(), paramString.isEmpty());
/* 1047 */       TextInputControl.this.content.insert(0, paramString, true);
/* 1048 */       if (!TextInputControl.this.doNotAdjustCaret) {
/* 1049 */         TextInputControl.this.selectRange(0, 0);
/* 1050 */         TextInputControl.this.textUpdated();
/*      */       }
/*      */     }
/*      */ 
/*      */     private class Listener implements InvalidationListener
/*      */     {
/*      */       private Listener()
/*      */       {
/*      */       }
/*      */ 
/*      */       public void invalidated(Observable paramObservable)
/*      */       {
/* 1062 */         TextInputControl.TextProperty.this.doSet((String)TextInputControl.TextProperty.this.observable.getValue());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static abstract interface Content extends ObservableStringValue
/*      */   {
/*      */     public abstract String get(int paramInt1, int paramInt2);
/*      */ 
/*      */     public abstract void insert(int paramInt, String paramString, boolean paramBoolean);
/*      */ 
/*      */     public abstract void delete(int paramInt1, int paramInt2, boolean paramBoolean);
/*      */ 
/*      */     public abstract int length();
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TextInputControl
 * JD-Core Version:    0.6.2
 */