/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class Cell<T> extends Labeled
/*     */ {
/* 316 */   private ObjectProperty<T> item = new SimpleObjectProperty(this, "item");
/*     */ 
/* 343 */   private ReadOnlyBooleanWrapper empty = new ReadOnlyBooleanWrapper(true) {
/*     */     protected void invalidated() {
/* 345 */       Cell.this.impl_pseudoClassStateChanged("empty");
/* 346 */       Cell.this.impl_pseudoClassStateChanged("filled");
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 351 */       return Cell.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 356 */       return "empty";
/*     */     }
/* 343 */   };
/*     */ 
/* 383 */   private ReadOnlyBooleanWrapper selected = new ReadOnlyBooleanWrapper() {
/*     */     protected void invalidated() {
/* 385 */       Cell.this.impl_pseudoClassStateChanged("selected");
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 390 */       return Cell.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 395 */       return "selected";
/*     */     }
/* 383 */   };
/*     */   private ReadOnlyBooleanWrapper editing;
/*     */   private BooleanProperty editable;
/*     */   private static final String DEFAULT_STYLE_CLASS = "cell";
/*     */   private static final String PSEUDO_CLASS_SELECTED = "selected";
/*     */   private static final String PSEUDO_CLASS_FOCUSED = "focused";
/*     */   private static final String PSEUDO_CLASS_EMPTY = "empty";
/*     */   private static final String PSEUDO_CLASS_FILLED = "filled";
/* 589 */   private static final long SELECTED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("selected");
/*     */ 
/* 591 */   private static final long EMPTY_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("empty");
/*     */ 
/* 593 */   private static final long FILLED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("filled");
/*     */ 
/*     */   public Cell()
/*     */   {
/* 281 */     setText(null);
/*     */ 
/* 286 */     StyleableProperty localStyleableProperty = StyleableProperty.getStyleableProperty(focusTraversableProperty());
/* 287 */     localStyleableProperty.set(this, Boolean.FALSE);
/* 288 */     getStyleClass().addAll(new String[] { "cell" });
/*     */ 
/* 295 */     super.focusedProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 297 */         Cell.this.impl_pseudoClassStateChanged("focused");
/*     */ 
/* 300 */         if ((!Cell.this.isFocused()) && (Cell.this.isEditing()))
/* 301 */           Cell.this.cancelEdit();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<T> itemProperty()
/*     */   {
/* 327 */     return this.item;
/*     */   }
/*     */ 
/*     */   public final void setItem(T paramT)
/*     */   {
/* 333 */     itemProperty().set(paramT);
/*     */   }
/*     */ 
/*     */   public final T getItem()
/*     */   {
/* 338 */     return itemProperty().get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyBooleanProperty emptyProperty()
/*     */   {
/* 370 */     return this.empty.getReadOnlyProperty();
/*     */   }
/* 372 */   private void setEmpty(boolean paramBoolean) { this.empty.set(paramBoolean); }
/*     */ 
/*     */ 
/*     */   public final boolean isEmpty()
/*     */   {
/* 378 */     return emptyProperty().get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyBooleanProperty selectedProperty()
/*     */   {
/* 403 */     return this.selected.getReadOnlyProperty();
/*     */   }
/* 405 */   void setSelected(boolean paramBoolean) { this.selected.set(paramBoolean); }
/*     */ 
/*     */ 
/*     */   public final boolean isSelected()
/*     */   {
/* 411 */     return selectedProperty().get();
/*     */   }
/*     */ 
/*     */   private void setEditing(boolean paramBoolean)
/*     */   {
/* 419 */     editingPropertyImpl().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isEditing()
/*     */   {
/* 426 */     return this.editing == null ? false : this.editing.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyBooleanProperty editingProperty()
/*     */   {
/* 433 */     return editingPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyBooleanWrapper editingPropertyImpl() {
/* 437 */     if (this.editing == null) {
/* 438 */       this.editing = new ReadOnlyBooleanWrapper(this, "editing");
/*     */     }
/* 440 */     return this.editing;
/*     */   }
/*     */ 
/*     */   public final void setEditable(boolean paramBoolean)
/*     */   {
/* 459 */     editableProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isEditable()
/*     */   {
/* 466 */     return this.editable == null ? true : this.editable.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty editableProperty()
/*     */   {
/* 478 */     if (this.editable == null) {
/* 479 */       this.editable = new SimpleBooleanProperty(this, "editable", true);
/*     */     }
/* 481 */     return this.editable;
/*     */   }
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 498 */     if ((isEditable()) && (!isEditing()) && (!isEmpty()))
/* 499 */       setEditing(true);
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 508 */     if (isEditing())
/* 509 */       setEditing(false);
/*     */   }
/*     */ 
/*     */   public void commitEdit(T paramT)
/*     */   {
/* 522 */     if (isEditing())
/* 523 */       setEditing(false);
/*     */   }
/*     */ 
/*     */   protected void updateItem(T paramT, boolean paramBoolean)
/*     */   {
/* 556 */     if (isEditing()) cancelEdit();
/* 557 */     setItem(paramT);
/* 558 */     setEmpty(paramBoolean);
/* 559 */     if ((paramBoolean) && (isSelected()))
/* 560 */       updateSelected(false);
/*     */   }
/*     */ 
/*     */   public void updateSelected(boolean paramBoolean)
/*     */   {
/* 570 */     if ((paramBoolean) && (isEmpty())) return;
/* 571 */     setSelected(paramBoolean);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 601 */     long l = super.impl_getPseudoClassState();
/* 602 */     if (isSelected()) l |= SELECTED_PSEUDOCLASS_STATE;
/* 603 */     l |= (isEmpty() ? EMPTY_PSEUDOCLASS_STATE : FILLED_PSEUDOCLASS_STATE);
/* 604 */     return l;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected Boolean impl_cssGetFocusTraversableInitialValue()
/*     */   {
/* 616 */     return Boolean.FALSE;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.Cell
 * JD-Core Version:    0.6.2
 */