/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import com.sun.javafx.collections.ListListenerHelper;
/*     */ import com.sun.javafx.collections.NonIterableChange;
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.IntegerPropertyBase;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class TextArea extends TextInputControl
/*     */ {
/*     */   public static final int DEFAULT_PREF_COLUMN_COUNT = 40;
/*     */   public static final int DEFAULT_PREF_ROW_COUNT = 10;
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final int DEFAULT_PARAGRAPH_CAPACITY = 32;
/* 473 */   private BooleanProperty wrapText = new SimpleBooleanProperty(this, "wrapText");
/*     */ 
/* 483 */   private IntegerProperty prefColumnCount = new IntegerPropertyBase(40)
/*     */   {
/*     */     public void set(int paramAnonymousInt) {
/* 486 */       if (paramAnonymousInt < 0) {
/* 487 */         throw new IllegalArgumentException("value cannot be negative.");
/*     */       }
/*     */ 
/* 490 */       super.set(paramAnonymousInt);
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 495 */       return TextArea.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 500 */       return "prefColumnCount";
/*     */     }
/* 483 */   };
/*     */ 
/* 512 */   private IntegerProperty prefRowCount = new IntegerPropertyBase(10)
/*     */   {
/*     */     public void set(int paramAnonymousInt) {
/* 515 */       if (paramAnonymousInt < 0) {
/* 516 */         throw new IllegalArgumentException("value cannot be negative.");
/*     */       }
/*     */ 
/* 519 */       super.set(paramAnonymousInt);
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 524 */       return TextArea.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 529 */       return "prefRowCount";
/*     */     }
/* 512 */   };
/*     */ 
/* 541 */   private DoubleProperty scrollTop = new SimpleDoubleProperty(this, "scrollTop", 0.0D);
/*     */ 
/* 551 */   private DoubleProperty scrollLeft = new SimpleDoubleProperty(this, "scrollLeft", 0.0D);
/*     */ 
/*     */   public TextArea()
/*     */   {
/* 433 */     this("");
/*     */   }
/*     */ 
/*     */   public TextArea(String paramString)
/*     */   {
/* 442 */     super(new TextAreaContent(null));
/*     */ 
/* 444 */     getStyleClass().add("text-area");
/* 445 */     setText(paramString);
/*     */   }
/*     */ 
/*     */   final void textUpdated() {
/* 449 */     setScrollTop(0.0D);
/* 450 */     setScrollLeft(0.0D);
/*     */   }
/*     */ 
/*     */   public ObservableList<CharSequence> getParagraphs()
/*     */   {
/* 458 */     return ((TextAreaContent)getContent()).paragraphList;
/*     */   }
/*     */ 
/*     */   public final BooleanProperty wrapTextProperty()
/*     */   {
/* 474 */     return this.wrapText; } 
/* 475 */   public final boolean isWrapText() { return this.wrapText.getValue().booleanValue(); } 
/* 476 */   public final void setWrapText(boolean paramBoolean) { this.wrapText.setValue(Boolean.valueOf(paramBoolean)); }
/*     */ 
/*     */ 
/*     */   public final IntegerProperty prefColumnCountProperty()
/*     */   {
/* 503 */     return this.prefColumnCount; } 
/* 504 */   public final int getPrefColumnCount() { return this.prefColumnCount.getValue().intValue(); } 
/* 505 */   public final void setPrefColumnCount(int paramInt) { this.prefColumnCount.setValue(Integer.valueOf(paramInt)); }
/*     */ 
/*     */ 
/*     */   public final IntegerProperty prefRowCountProperty()
/*     */   {
/* 532 */     return this.prefRowCount; } 
/* 533 */   public final int getPrefRowCount() { return this.prefRowCount.getValue().intValue(); } 
/* 534 */   public final void setPrefRowCount(int paramInt) { this.prefRowCount.setValue(Integer.valueOf(paramInt)); }
/*     */ 
/*     */ 
/*     */   public final DoubleProperty scrollTopProperty()
/*     */   {
/* 542 */     return this.scrollTop; } 
/* 543 */   public final double getScrollTop() { return this.scrollTop.getValue().doubleValue(); } 
/* 544 */   public final void setScrollTop(double paramDouble) { this.scrollTop.setValue(Double.valueOf(paramDouble)); }
/*     */ 
/*     */ 
/*     */   public final DoubleProperty scrollLeftProperty()
/*     */   {
/* 552 */     return this.scrollLeft; } 
/* 553 */   public final double getScrollLeft() { return this.scrollLeft.getValue().doubleValue(); } 
/* 554 */   public final void setScrollLeft(double paramDouble) { this.scrollLeft.setValue(Double.valueOf(paramDouble)); }
/*     */ 
/*     */ 
/*     */   private static final class ParagraphListChange extends NonIterableChange<CharSequence>
/*     */   {
/*     */     private List<CharSequence> removed;
/*     */ 
/*     */     protected ParagraphListChange(ObservableList<CharSequence> paramObservableList, int paramInt1, int paramInt2, List<CharSequence> paramList)
/*     */     {
/* 397 */       super(paramInt2, paramObservableList);
/*     */ 
/* 399 */       this.removed = paramList;
/*     */     }
/*     */ 
/*     */     public List<CharSequence> getRemoved()
/*     */     {
/* 404 */       return this.removed;
/*     */     }
/*     */ 
/*     */     protected int[] getPermutation()
/*     */     {
/* 409 */       return new int[0];
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final class ParagraphList extends AbstractList<CharSequence>
/*     */     implements ObservableList<CharSequence>
/*     */   {
/*     */     private TextArea.TextAreaContent content;
/*     */ 
/*     */     public CharSequence get(int paramInt)
/*     */     {
/* 327 */       return (CharSequence)TextArea.TextAreaContent.access$200(this.content).get(paramInt);
/*     */     }
/*     */ 
/*     */     public boolean addAll(Collection<? extends CharSequence> paramCollection)
/*     */     {
/* 332 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public boolean addAll(CharSequence[] paramArrayOfCharSequence)
/*     */     {
/* 337 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public boolean setAll(Collection<? extends CharSequence> paramCollection)
/*     */     {
/* 342 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public boolean setAll(CharSequence[] paramArrayOfCharSequence)
/*     */     {
/* 347 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public int size()
/*     */     {
/* 352 */       return TextArea.TextAreaContent.access$200(this.content).size();
/*     */     }
/*     */ 
/*     */     public void addListener(ListChangeListener<? super CharSequence> paramListChangeListener)
/*     */     {
/* 357 */       TextArea.TextAreaContent.access$302(this.content, ListListenerHelper.addListener(TextArea.TextAreaContent.access$300(this.content), paramListChangeListener));
/*     */     }
/*     */ 
/*     */     public void removeListener(ListChangeListener<? super CharSequence> paramListChangeListener)
/*     */     {
/* 362 */       TextArea.TextAreaContent.access$302(this.content, ListListenerHelper.removeListener(TextArea.TextAreaContent.access$300(this.content), paramListChangeListener));
/*     */     }
/*     */ 
/*     */     public boolean removeAll(CharSequence[] paramArrayOfCharSequence)
/*     */     {
/* 367 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public boolean retainAll(CharSequence[] paramArrayOfCharSequence)
/*     */     {
/* 372 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public void remove(int paramInt1, int paramInt2)
/*     */     {
/* 377 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public void addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 382 */       TextArea.TextAreaContent.access$302(this.content, ListListenerHelper.addListener(TextArea.TextAreaContent.access$300(this.content), paramInvalidationListener));
/*     */     }
/*     */ 
/*     */     public void removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 387 */       TextArea.TextAreaContent.access$302(this.content, ListListenerHelper.removeListener(TextArea.TextAreaContent.access$300(this.content), paramInvalidationListener));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final class TextAreaContent
/*     */     implements TextInputControl.Content
/*     */   {
/*  67 */     private ExpressionHelper<String> helper = null;
/*  68 */     private ArrayList<StringBuilder> paragraphs = new ArrayList();
/*  69 */     private int contentLength = 0;
/*  70 */     private TextArea.ParagraphList paragraphList = new TextArea.ParagraphList(null);
/*     */     private ListListenerHelper<CharSequence> listenerHelper;
/*     */ 
/*     */     private TextAreaContent()
/*     */     {
/*  74 */       this.paragraphs.add(new StringBuilder(32));
/*  75 */       this.paragraphList.content = this;
/*     */     }
/*     */ 
/*     */     public String get(int paramInt1, int paramInt2) {
/*  79 */       int i = paramInt2 - paramInt1;
/*  80 */       StringBuilder localStringBuilder1 = new StringBuilder(i);
/*     */ 
/*  82 */       int j = this.paragraphs.size();
/*     */ 
/*  84 */       int k = 0;
/*  85 */       int m = paramInt1;
/*     */ 
/*  87 */       while (k < j) {
/*  88 */         localStringBuilder2 = (StringBuilder)this.paragraphs.get(k);
/*  89 */         n = localStringBuilder2.length() + 1;
/*     */ 
/*  91 */         if (m < n)
/*     */         {
/*     */           break;
/*     */         }
/*  95 */         m -= n;
/*  96 */         k++;
/*     */       }
/*     */ 
/* 101 */       StringBuilder localStringBuilder2 = (StringBuilder)this.paragraphs.get(k);
/*     */ 
/* 103 */       int n = 0;
/* 104 */       while (n < i) {
/* 105 */         if ((m == localStringBuilder2.length()) && (n < this.contentLength))
/*     */         {
/* 107 */           localStringBuilder1.append('\n');
/* 108 */           localStringBuilder2 = (StringBuilder)this.paragraphs.get(++k);
/* 109 */           m = 0;
/*     */         } else {
/* 111 */           localStringBuilder1.append(localStringBuilder2.charAt(m++));
/*     */         }
/*     */ 
/* 114 */         n++;
/*     */       }
/*     */ 
/* 117 */       return localStringBuilder1.toString();
/*     */     }
/*     */ 
/*     */     public void insert(int paramInt, String paramString, boolean paramBoolean)
/*     */     {
/* 123 */       if ((paramInt < 0) || (paramInt > this.contentLength))
/*     */       {
/* 125 */         throw new IndexOutOfBoundsException();
/*     */       }
/*     */ 
/* 128 */       if (paramString == null) {
/* 129 */         throw new IllegalArgumentException();
/*     */       }
/* 131 */       paramString = TextInputControl.filterInput(paramString, false, false);
/* 132 */       int i = paramString.length();
/* 133 */       if (i > 0)
/*     */       {
/* 135 */         ArrayList localArrayList = new ArrayList();
/*     */ 
/* 137 */         StringBuilder localStringBuilder1 = new StringBuilder(32);
/* 138 */         for (int j = 0; j < i; j++) {
/* 139 */           char c = paramString.charAt(j);
/*     */ 
/* 141 */           if (c == '\n') {
/* 142 */             localArrayList.add(localStringBuilder1);
/* 143 */             localStringBuilder1 = new StringBuilder(32);
/*     */           } else {
/* 145 */             localStringBuilder1.append(c);
/*     */           }
/*     */         }
/*     */ 
/* 149 */         localArrayList.add(localStringBuilder1);
/*     */ 
/* 153 */         j = this.paragraphs.size();
/* 154 */         int k = this.contentLength + 1;
/*     */ 
/* 156 */         StringBuilder localStringBuilder2 = null;
/*     */         do
/*     */         {
/* 159 */           localStringBuilder2 = (StringBuilder)this.paragraphs.get(--j);
/* 160 */           k -= localStringBuilder2.length() + 1;
/* 161 */         }while (paramInt < k);
/*     */ 
/* 163 */         int m = paramInt - k;
/*     */ 
/* 165 */         int n = localArrayList.size();
/* 166 */         if (n == 1)
/*     */         {
/* 169 */           localStringBuilder2.insert(m, localStringBuilder1);
/* 170 */           fireParagraphListChangeEvent(j, j + 1, Collections.singletonList(localStringBuilder2));
/*     */         }
/*     */         else
/*     */         {
/* 175 */           int i1 = localStringBuilder2.length();
/* 176 */           CharSequence localCharSequence = localStringBuilder2.subSequence(m, i1);
/* 177 */           localStringBuilder2.delete(m, i1);
/*     */ 
/* 181 */           StringBuilder localStringBuilder3 = (StringBuilder)localArrayList.get(0);
/* 182 */           localStringBuilder2.insert(m, localStringBuilder3);
/* 183 */           localStringBuilder1.append(localCharSequence);
/* 184 */           fireParagraphListChangeEvent(j, j + 1, Collections.singletonList(localStringBuilder2));
/*     */ 
/* 188 */           this.paragraphs.addAll(j + 1, localArrayList.subList(1, n));
/* 189 */           fireParagraphListChangeEvent(j + 1, j + n, Collections.EMPTY_LIST);
/*     */         }
/*     */ 
/* 194 */         this.contentLength += i;
/* 195 */         if (paramBoolean)
/* 196 */           ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void delete(int paramInt1, int paramInt2, boolean paramBoolean)
/*     */     {
/* 202 */       if (paramInt1 > paramInt2) {
/* 203 */         throw new IllegalArgumentException();
/*     */       }
/*     */ 
/* 206 */       if ((paramInt1 < 0) || (paramInt2 > this.contentLength))
/*     */       {
/* 208 */         throw new IndexOutOfBoundsException();
/*     */       }
/*     */ 
/* 211 */       int i = paramInt2 - paramInt1;
/*     */ 
/* 213 */       if (i > 0)
/*     */       {
/* 215 */         int j = this.paragraphs.size();
/* 216 */         int k = this.contentLength + 1;
/*     */ 
/* 218 */         StringBuilder localStringBuilder1 = null;
/*     */         do
/*     */         {
/* 221 */           localStringBuilder1 = (StringBuilder)this.paragraphs.get(--j);
/* 222 */           k -= localStringBuilder1.length() + 1;
/* 223 */         }while (paramInt2 < k);
/*     */ 
/* 225 */         int m = j;
/* 226 */         int n = k;
/* 227 */         StringBuilder localStringBuilder2 = localStringBuilder1;
/*     */ 
/* 230 */         j++;
/* 231 */         k += localStringBuilder1.length() + 1;
/*     */         do
/*     */         {
/* 234 */           localStringBuilder1 = (StringBuilder)this.paragraphs.get(--j);
/* 235 */           k -= localStringBuilder1.length() + 1;
/* 236 */         }while (paramInt1 < k);
/*     */ 
/* 238 */         int i1 = j;
/* 239 */         int i2 = k;
/* 240 */         StringBuilder localStringBuilder3 = localStringBuilder1;
/*     */ 
/* 243 */         if (i1 == m)
/*     */         {
/* 245 */           localStringBuilder3.delete(paramInt1 - i2, paramInt2 - i2);
/*     */ 
/* 248 */           fireParagraphListChangeEvent(i1, i1 + 1, Collections.singletonList(localStringBuilder3));
/*     */         }
/*     */         else
/*     */         {
/* 253 */           CharSequence localCharSequence = localStringBuilder3.subSequence(0, paramInt1 - i2);
/*     */ 
/* 255 */           int i3 = paramInt1 + i - n;
/*     */ 
/* 257 */           localStringBuilder2.delete(0, i3);
/* 258 */           fireParagraphListChangeEvent(m, m + 1, Collections.singletonList(localStringBuilder2));
/*     */ 
/* 261 */           if (m - i1 > 0) {
/* 262 */             ArrayList localArrayList = new ArrayList(this.paragraphs.subList(i1, m));
/*     */ 
/* 264 */             this.paragraphs.subList(i1, m).clear();
/*     */ 
/* 266 */             fireParagraphListChangeEvent(i1, i1, localArrayList);
/*     */           }
/*     */ 
/* 271 */           n = i2;
/* 272 */           localStringBuilder2.insert(0, localCharSequence);
/* 273 */           fireParagraphListChangeEvent(i1, i1 + 1, Collections.singletonList(localStringBuilder3));
/*     */         }
/*     */ 
/* 278 */         this.contentLength -= i;
/* 279 */         if (paramBoolean)
/* 280 */           ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */       }
/*     */     }
/*     */ 
/*     */     public int length()
/*     */     {
/* 286 */       return this.contentLength;
/*     */     }
/*     */ 
/*     */     public String get() {
/* 290 */       return get(0, length());
/*     */     }
/*     */ 
/*     */     public void addListener(ChangeListener<? super String> paramChangeListener) {
/* 294 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(ChangeListener<? super String> paramChangeListener) {
/* 298 */       this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public String getValue() {
/* 302 */       return get();
/*     */     }
/*     */ 
/*     */     public void addListener(InvalidationListener paramInvalidationListener) {
/* 306 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(InvalidationListener paramInvalidationListener) {
/* 310 */       this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     private void fireParagraphListChangeEvent(int paramInt1, int paramInt2, List<CharSequence> paramList) {
/* 314 */       TextArea.ParagraphListChange localParagraphListChange = new TextArea.ParagraphListChange(this.paragraphList, paramInt1, paramInt2, paramList);
/* 315 */       ListListenerHelper.fireValueChangedEvent(this.listenerHelper, localParagraphListChange);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TextArea
 * JD-Core Version:    0.6.2
 */