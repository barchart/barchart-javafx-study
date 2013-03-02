/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import java.util.List;
/*     */ import javafx.beans.DefaultProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ @DefaultProperty("items")
/*     */ public class ChoiceBox<T> extends Control
/*     */ {
/* 136 */   private ObjectProperty<SingleSelectionModel<T>> selectionModel = new SimpleObjectProperty(this, "selectionModel")
/*     */   {
/* 138 */     private SelectionModel<T> oldSM = null;
/*     */ 
/* 140 */     protected void invalidated() { if (this.oldSM != null) {
/* 141 */         this.oldSM.selectedItemProperty().removeListener(ChoiceBox.this.selectedItemListener);
/*     */       }
/* 143 */       SelectionModel localSelectionModel = (SelectionModel)get();
/* 144 */       this.oldSM = localSelectionModel;
/* 145 */       if (localSelectionModel != null)
/* 146 */         localSelectionModel.selectedItemProperty().addListener(ChoiceBox.this.selectedItemListener);
/*     */     }
/* 136 */   };
/*     */ 
/* 151 */   private ChangeListener<T> selectedItemListener = new ChangeListener() {
/*     */     public void changed(ObservableValue<? extends T> paramAnonymousObservableValue, T paramAnonymousT1, T paramAnonymousT2) {
/* 153 */       if (!ChoiceBox.this.valueProperty().isBound())
/* 154 */         ChoiceBox.this.setValue(paramAnonymousT2);
/*     */     }
/* 151 */   };
/*     */ 
/* 170 */   private ReadOnlyBooleanWrapper showing = new ReadOnlyBooleanWrapper() {
/*     */     protected void invalidated() {
/* 172 */       ChoiceBox.this.impl_pseudoClassStateChanged("showing");
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 177 */       return ChoiceBox.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 182 */       return "showing";
/*     */     }
/* 170 */   };
/*     */ 
/* 192 */   private ObjectProperty<ObservableList<T>> items = new ObjectPropertyBase() {
/*     */     ObservableList<T> old;
/*     */ 
/* 195 */     protected void invalidated() { ObservableList localObservableList = (ObservableList)get();
/* 196 */       if (this.old != localObservableList)
/*     */       {
/* 198 */         if (this.old != null) this.old.removeListener(ChoiceBox.this.itemsListener);
/* 199 */         if (localObservableList != null) localObservableList.addListener(ChoiceBox.this.itemsListener);
/*     */ 
/* 201 */         SingleSelectionModel localSingleSelectionModel = ChoiceBox.this.getSelectionModel();
/* 202 */         if (localSingleSelectionModel != null) {
/* 203 */           if ((localObservableList != null) && (localObservableList.isEmpty())) {
/* 204 */             localSingleSelectionModel.setSelectedIndex(-1);
/* 205 */           } else if ((localSingleSelectionModel.getSelectedIndex() == -1) && (localSingleSelectionModel.getSelectedItem() != null)) {
/* 206 */             int i = ChoiceBox.this.getItems().indexOf(localSingleSelectionModel.getSelectedItem());
/* 207 */             if (i != -1)
/* 208 */               localSingleSelectionModel.setSelectedIndex(i);
/*     */           } else {
/* 210 */             localSingleSelectionModel.clearSelection();
/*     */           }
/*     */         }
/*     */ 
/* 214 */         this.old = localObservableList;
/*     */       }
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 220 */       return ChoiceBox.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 225 */       return "items";
/*     */     }
/* 192 */   };
/*     */ 
/* 232 */   private final ListChangeListener<T> itemsListener = new Object() {
/*     */     public void onChanged(ListChangeListener.Change<? extends T> paramAnonymousChange) {
/* 234 */       SingleSelectionModel localSingleSelectionModel = ChoiceBox.this.getSelectionModel();
/* 235 */       if (localSingleSelectionModel != null) {
/* 236 */         if ((ChoiceBox.this.getItems() == null) || (ChoiceBox.this.getItems().isEmpty())) {
/* 237 */           localSingleSelectionModel.clearSelection();
/*     */         } else {
/* 239 */           int i = ChoiceBox.this.getItems().indexOf(localSingleSelectionModel.getSelectedItem());
/* 240 */           localSingleSelectionModel.setSelectedIndex(i);
/*     */         }
/*     */       }
/* 243 */       if (localSingleSelectionModel != null)
/*     */       {
/* 247 */         Object localObject = localSingleSelectionModel.getSelectedItem();
/* 248 */         while (paramAnonymousChange.next())
/* 249 */           if ((localObject != null) && (paramAnonymousChange.getRemoved().contains(localObject)))
/* 250 */             localSingleSelectionModel.clearSelection();
/*     */       }
/*     */     }
/* 232 */   };
/*     */ 
/* 267 */   private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty(this, "converter", null);
/*     */ 
/* 279 */   private ObjectProperty<T> value = new SimpleObjectProperty(this, "value") {
/*     */     protected void invalidated() {
/* 281 */       super.invalidated();
/* 282 */       ChoiceBox.this.fireEvent(new ActionEvent());
/*     */ 
/* 284 */       SingleSelectionModel localSingleSelectionModel = ChoiceBox.this.getSelectionModel();
/* 285 */       if (localSingleSelectionModel != null)
/* 286 */         localSingleSelectionModel.select(getValue());
/*     */     }
/* 279 */   };
/*     */ 
/* 319 */   private static final long SHOWING_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("showing");
/*     */ 
/*     */   public ChoiceBox()
/*     */   {
/*  95 */     this(FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public ChoiceBox(ObservableList<T> paramObservableList)
/*     */   {
/* 105 */     getStyleClass().setAll(new String[] { "choice-box" });
/* 106 */     setItems(paramObservableList);
/* 107 */     setSelectionModel(new ChoiceBoxSelectionModel(this));
/*     */ 
/* 112 */     valueProperty().addListener(new ChangeListener() {
/*     */       public void changed(ObservableValue<? extends T> paramAnonymousObservableValue, T paramAnonymousT1, T paramAnonymousT2) {
/* 114 */         if (ChoiceBox.this.getItems() == null) return;
/* 115 */         int i = ChoiceBox.this.getItems().indexOf(paramAnonymousT2);
/* 116 */         if (i > -1)
/* 117 */           ChoiceBox.this.getSelectionModel().select(i);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public final void setSelectionModel(SingleSelectionModel<T> paramSingleSelectionModel)
/*     */   {
/* 160 */     this.selectionModel.set(paramSingleSelectionModel); } 
/* 161 */   public final SingleSelectionModel<T> getSelectionModel() { return (SingleSelectionModel)this.selectionModel.get(); } 
/* 162 */   public final ObjectProperty<SingleSelectionModel<T>> selectionModelProperty() { return this.selectionModel; }
/*     */ 
/*     */ 
/*     */   public final boolean isShowing()
/*     */   {
/* 185 */     return this.showing.get(); } 
/* 186 */   public final ReadOnlyBooleanProperty showingProperty() { return this.showing.getReadOnlyProperty(); }
/*     */ 
/*     */ 
/*     */   public final void setItems(ObservableList<T> paramObservableList)
/*     */   {
/* 228 */     this.items.set(paramObservableList); } 
/* 229 */   public final ObservableList<T> getItems() { return (ObservableList)this.items.get(); } 
/* 230 */   public final ObjectProperty<ObservableList<T>> itemsProperty() { return this.items; }
/*     */ 
/*     */ 
/*     */   public ObjectProperty<StringConverter<T>> converterProperty()
/*     */   {
/* 266 */     return this.converter;
/*     */   }
/*     */   public final void setConverter(StringConverter<T> paramStringConverter) {
/* 269 */     converterProperty().set(paramStringConverter); } 
/* 270 */   public final StringConverter<T> getConverter() { return (StringConverter)converterProperty().get(); }
/*     */ 
/*     */ 
/*     */   public ObjectProperty<T> valueProperty()
/*     */   {
/* 278 */     return this.value;
/*     */   }
/*     */ 
/*     */   public final void setValue(T paramT)
/*     */   {
/* 290 */     valueProperty().set(paramT); } 
/* 291 */   public final T getValue() { return valueProperty().get(); }
/*     */ 
/*     */ 
/*     */   public void show()
/*     */   {
/* 303 */     if (!isDisabled()) this.showing.set(true);
/*     */   }
/*     */ 
/*     */   public void hide()
/*     */   {
/* 310 */     this.showing.set(false);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 327 */     long l = super.impl_getPseudoClassState();
/* 328 */     if (isShowing()) l |= SHOWING_PSEUDOCLASS_STATE;
/* 329 */     return l;
/*     */   }
/*     */ 
/*     */   static class ChoiceBoxSelectionModel<T> extends SingleSelectionModel<T>
/*     */   {
/*     */     private final ChoiceBox<T> choiceBox;
/*     */ 
/*     */     public ChoiceBoxSelectionModel(ChoiceBox<T> paramChoiceBox) {
/* 337 */       if (paramChoiceBox == null) {
/* 338 */         throw new NullPointerException("ChoiceBox can not be null");
/*     */       }
/* 340 */       this.choiceBox = paramChoiceBox;
/*     */ 
/* 352 */       final ListChangeListener local1 = new ListChangeListener() {
/*     */         public void onChanged(ListChangeListener.Change<? extends T> paramAnonymousChange) {
/* 354 */           if ((ChoiceBox.this.getItems() == null) || (ChoiceBox.this.getItems().isEmpty())) {
/* 355 */             ChoiceBox.ChoiceBoxSelectionModel.this.setSelectedIndex(-1);
/* 356 */           } else if ((ChoiceBox.ChoiceBoxSelectionModel.this.getSelectedIndex() == -1) && (ChoiceBox.ChoiceBoxSelectionModel.this.getSelectedItem() != null)) {
/* 357 */             int i = ChoiceBox.this.getItems().indexOf(ChoiceBox.ChoiceBoxSelectionModel.this.getSelectedItem());
/* 358 */             if (i != -1)
/* 359 */               ChoiceBox.ChoiceBoxSelectionModel.this.setSelectedIndex(i);
/*     */           }
/*     */         }
/*     */       };
/* 364 */       if (this.choiceBox.getItems() != null) {
/* 365 */         this.choiceBox.getItems().addListener(local1);
/*     */       }
/*     */ 
/* 369 */       ChangeListener local2 = new ChangeListener()
/*     */       {
/*     */         public void changed(ObservableValue<? extends ObservableList<T>> paramAnonymousObservableValue, ObservableList<T> paramAnonymousObservableList1, ObservableList<T> paramAnonymousObservableList2) {
/* 372 */           if (paramAnonymousObservableList1 != null) {
/* 373 */             paramAnonymousObservableList1.removeListener(local1);
/*     */           }
/* 375 */           if (paramAnonymousObservableList2 != null) {
/* 376 */             paramAnonymousObservableList2.addListener(local1);
/*     */           }
/* 378 */           ChoiceBox.ChoiceBoxSelectionModel.this.setSelectedIndex(-1);
/* 379 */           if (ChoiceBox.ChoiceBoxSelectionModel.this.getSelectedItem() != null) {
/* 380 */             int i = ChoiceBox.this.getItems().indexOf(ChoiceBox.ChoiceBoxSelectionModel.this.getSelectedItem());
/* 381 */             if (i != -1)
/* 382 */               ChoiceBox.ChoiceBoxSelectionModel.this.setSelectedIndex(i);
/*     */           }
/*     */         }
/*     */       };
/* 387 */       this.choiceBox.itemsProperty().addListener(local2);
/*     */     }
/*     */ 
/*     */     protected T getModelItem(int paramInt)
/*     */     {
/* 392 */       ObservableList localObservableList = this.choiceBox.getItems();
/* 393 */       if (localObservableList == null) return null;
/* 394 */       if ((paramInt < 0) || (paramInt >= localObservableList.size())) return null;
/* 395 */       return localObservableList.get(paramInt);
/*     */     }
/*     */ 
/*     */     protected int getItemCount() {
/* 399 */       ObservableList localObservableList = this.choiceBox.getItems();
/* 400 */       return localObservableList == null ? 0 : localObservableList.size();
/*     */     }
/*     */ 
/*     */     public void select(int paramInt)
/*     */     {
/* 411 */       int i = getItemCount();
/*     */ 
/* 413 */       Object localObject = getModelItem(paramInt);
/* 414 */       if ((localObject instanceof Separator))
/* 415 */         select(++paramInt);
/*     */       else {
/* 417 */         super.select(paramInt);
/*     */       }
/*     */ 
/* 420 */       if (this.choiceBox.isShowing())
/* 421 */         this.choiceBox.hide();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ChoiceBox
 * JD-Core Version:    0.6.2
 */