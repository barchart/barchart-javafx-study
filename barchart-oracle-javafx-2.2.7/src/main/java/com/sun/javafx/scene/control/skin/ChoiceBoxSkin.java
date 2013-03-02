/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import com.sun.javafx.scene.control.behavior.ChoiceBoxBehavior;
/*     */ import java.util.Iterator;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Side;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.ChoiceBox;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.MenuItem;
/*     */ import javafx.scene.control.RadioMenuItem;
/*     */ import javafx.scene.control.SelectionModel;
/*     */ import javafx.scene.control.Separator;
/*     */ import javafx.scene.control.SeparatorMenuItem;
/*     */ import javafx.scene.control.SingleSelectionModel;
/*     */ import javafx.scene.control.Skin;
/*     */ import javafx.scene.control.ToggleGroup;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.text.Text;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class ChoiceBoxSkin<T> extends SkinBase<ChoiceBox<T>, ChoiceBoxBehavior<T>>
/*     */ {
/*     */   private ObservableList<?> choiceBoxItems;
/*     */   private ContextMenu popup;
/*     */   private StackPane openButton;
/*  80 */   private final ToggleGroup toggleGroup = new ToggleGroup();
/*     */   private SelectionModel selectionModel;
/*     */   private Label label;
/*  90 */   private final ListChangeListener choiceBoxItemsListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/*  92 */       while (paramAnonymousChange.next())
/*     */       {
/*     */         Object localObject1;
/*  93 */         if (paramAnonymousChange.getRemovedSize() > 0) {
/*  94 */           ChoiceBoxSkin.this.popup.getItems().clear();
/*  95 */           i = 0;
/*  96 */           for (localObject1 = paramAnonymousChange.getList().iterator(); ((Iterator)localObject1).hasNext(); ) { Object localObject2 = ((Iterator)localObject1).next();
/*  97 */             ChoiceBoxSkin.this.addPopupItem(localObject2, i);
/*  98 */             i++;
/*     */           }
/* 100 */           ChoiceBoxSkin.this.requestLayout();
/* 101 */           return;
/*     */         }
/* 103 */         for (int i = paramAnonymousChange.getFrom(); i < paramAnonymousChange.getTo(); i++) {
/* 104 */           localObject1 = paramAnonymousChange.getList().get(i);
/* 105 */           ChoiceBoxSkin.this.addPopupItem(localObject1, i);
/*     */         }
/*     */       }
/* 108 */       ChoiceBoxSkin.this.updateSelection();
/*     */ 
/* 111 */       ChoiceBoxSkin.this.popup.getScene().getRoot().impl_processCSS(true);
/* 112 */       ChoiceBoxSkin.this.requestLayout();
/*     */     }
/*  90 */   };
/*     */ 
/* 116 */   private final WeakListChangeListener weakChoiceBoxItemsListener = new WeakListChangeListener(this.choiceBoxItemsListener);
/*     */ 
/* 298 */   private InvalidationListener selectionChangeListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 300 */       ChoiceBoxSkin.this.updateSelection();
/*     */     }
/* 298 */   };
/*     */ 
/*     */   public ChoiceBoxSkin(ChoiceBox paramChoiceBox)
/*     */   {
/*  63 */     super(paramChoiceBox, new ChoiceBoxBehavior(paramChoiceBox));
/*  64 */     initialize();
/*  65 */     requestLayout();
/*  66 */     registerChangeListener(paramChoiceBox.selectionModelProperty(), "SELECTION_MODEL");
/*  67 */     registerChangeListener(paramChoiceBox.showingProperty(), "SHOWING");
/*  68 */     registerChangeListener(paramChoiceBox.itemsProperty(), "ITEMS");
/*  69 */     registerChangeListener(paramChoiceBox.getSelectionModel().selectedItemProperty(), "SELECTION_CHANGED");
/*  70 */     registerChangeListener(paramChoiceBox.converterProperty(), "CONVERTER");
/*     */   }
/*     */ 
/*     */   private void initialize()
/*     */   {
/* 120 */     updateChoiceBoxItems();
/*     */ 
/* 122 */     this.label = new Label();
/* 123 */     this.label.setMnemonicParsing(false);
/*     */ 
/* 125 */     this.openButton = new StackPane();
/* 126 */     this.openButton.getStyleClass().setAll(new String[] { "open-button" });
/*     */ 
/* 128 */     StackPane localStackPane = new StackPane();
/* 129 */     localStackPane.getStyleClass().setAll(new String[] { "arrow" });
/* 130 */     this.openButton.getChildren().clear();
/* 131 */     this.openButton.getChildren().addAll(new Node[] { localStackPane });
/*     */ 
/* 133 */     this.popup = new ContextMenu();
/*     */ 
/* 137 */     this.popup.setOnAutoHide(new EventHandler() {
/*     */       public void handle(Event paramAnonymousEvent) {
/* 139 */         ((ChoiceBox)ChoiceBoxSkin.this.getSkinnable()).hide();
/*     */       }
/*     */     });
/* 144 */     ((ChoiceBox)getSkinnable()).focusedProperty().addListener(new ChangeListener() {
/*     */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 146 */         if (!paramAnonymousBoolean2.booleanValue())
/* 147 */           ((ChoiceBox)ChoiceBoxSkin.this.getSkinnable()).hide();
/*     */       }
/*     */     });
/* 152 */     this.popup.setId("choice-box-popup-menu");
/*     */ 
/* 166 */     getChildren().setAll(new Node[] { this.label, this.openButton });
/*     */ 
/* 168 */     updatePopupItems();
/*     */ 
/* 170 */     updateSelectionModel();
/* 171 */     updateSelection();
/* 172 */     if ((this.selectionModel != null) && (this.selectionModel.getSelectedIndex() == -1))
/* 173 */       this.label.setText("");
/*     */   }
/*     */ 
/*     */   private void updateChoiceBoxItems()
/*     */   {
/* 178 */     if (this.choiceBoxItems != null) {
/* 179 */       this.choiceBoxItems.removeListener(this.weakChoiceBoxItemsListener);
/*     */     }
/* 181 */     this.choiceBoxItems = ((ChoiceBox)getSkinnable()).getItems();
/* 182 */     if (this.choiceBoxItems != null)
/* 183 */       this.choiceBoxItems.addListener(this.weakChoiceBoxItemsListener);
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString)
/*     */   {
/* 189 */     super.handleControlPropertyChanged(paramString);
/* 190 */     if ("ITEMS".equals(paramString)) {
/* 191 */       updateChoiceBoxItems();
/* 192 */       updatePopupItems();
/* 193 */     } else if ("SELECTION_MODEL".equals(paramString)) {
/* 194 */       updateSelectionModel();
/*     */     }
/*     */     else
/*     */     {
/*     */       Object localObject;
/* 195 */       if ("SELECTION_CHANGED".equals(paramString)) {
/* 196 */         if (((ChoiceBox)getSkinnable()).getSelectionModel() != null) {
/* 197 */           int i = ((ChoiceBox)getSkinnable()).getSelectionModel().getSelectedIndex();
/* 198 */           if (i != -1) {
/* 199 */             localObject = (MenuItem)this.popup.getItems().get(i);
/* 200 */             if ((localObject instanceof RadioMenuItem)) ((RadioMenuItem)localObject).setSelected(true); 
/*     */           }
/*     */         }
/*     */       }
/* 203 */       else if ("SHOWING".equals(paramString)) {
/* 204 */         if (((ChoiceBox)getSkinnable()).isShowing()) {
/* 205 */           MenuItem localMenuItem = null;
/*     */ 
/* 207 */           localObject = ((ChoiceBox)getSkinnable()).getSelectionModel();
/* 208 */           if (localObject == null) return;
/*     */ 
/* 210 */           long l = ((SelectionModel)localObject).getSelectedIndex();
/* 211 */           int j = this.choiceBoxItems.size();
/* 212 */           int k = (l >= 0L) && (l < j) ? 1 : 0;
/* 213 */           if (k != 0) {
/* 214 */             localMenuItem = (MenuItem)this.popup.getItems().get((int)l);
/* 215 */             if ((localMenuItem != null) && ((localMenuItem instanceof RadioMenuItem))) ((RadioMenuItem)localMenuItem).setSelected(true);
/*     */           }
/* 217 */           else if (j > 0) { localMenuItem = (MenuItem)this.popup.getItems().get(0); }
/*     */ 
/*     */ 
/* 224 */           ((ChoiceBox)getSkinnable()).autosize();
/*     */ 
/* 227 */           double d = 0.0D;
/*     */ 
/* 231 */           if (this.popup.getSkin() != null) {
/* 232 */             ContextMenuContent localContextMenuContent = (ContextMenuContent)this.popup.getSkin().getNode();
/* 233 */             if ((localContextMenuContent != null) && (l != -1L)) {
/* 234 */               d = -localContextMenuContent.getMenuYOffset((int)l);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 241 */           this.popup.show(getSkinnable(), Side.BOTTOM, 0.0D, d);
/*     */         } else {
/* 243 */           this.popup.hide();
/*     */         }
/* 245 */       } else if ("CONVERTER".equals(paramString)) {
/* 246 */         updateChoiceBoxItems();
/* 247 */         updatePopupItems();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/* 252 */   private void addPopupItem(Object paramObject, int paramInt) { Object localObject = null;
/* 253 */     if ((paramObject instanceof Separator))
/*     */     {
/* 255 */       localObject = new SeparatorMenuItem();
/* 256 */     } else if ((paramObject instanceof SeparatorMenuItem)) {
/* 257 */       localObject = (SeparatorMenuItem)paramObject;
/*     */     } else {
/* 259 */       StringConverter localStringConverter = ((ChoiceBox)getSkinnable()).getConverter();
/* 260 */       String str = localStringConverter == null ? paramObject.toString() : paramObject == null ? "" : localStringConverter.toString(paramObject);
/* 261 */       final RadioMenuItem localRadioMenuItem = new RadioMenuItem(str);
/* 262 */       localRadioMenuItem.setId("choice-box-menu-item");
/* 263 */       localRadioMenuItem.setToggleGroup(this.toggleGroup);
/* 264 */       final int i = paramInt;
/* 265 */       localRadioMenuItem.setOnAction(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 267 */           if (ChoiceBoxSkin.this.selectionModel == null) return;
/* 268 */           ChoiceBoxSkin.this.selectionModel.select(i);
/* 269 */           localRadioMenuItem.setSelected(true);
/*     */         }
/*     */       });
/* 272 */       localObject = localRadioMenuItem;
/*     */     }
/* 274 */     ((MenuItem)localObject).setMnemonicParsing(false);
/* 275 */     this.popup.getItems().add(paramInt, localObject); }
/*     */ 
/*     */   private void updatePopupItems()
/*     */   {
/* 279 */     this.popup.getItems().clear();
/* 280 */     this.toggleGroup.selectToggle(null);
/*     */ 
/* 282 */     for (int i = 0; i < this.choiceBoxItems.size(); i++) {
/* 283 */       Object localObject = this.choiceBoxItems.get(i);
/* 284 */       addPopupItem(localObject, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateSelectionModel() {
/* 289 */     if (this.selectionModel != null) {
/* 290 */       this.selectionModel.selectedIndexProperty().removeListener(this.selectionChangeListener);
/*     */     }
/* 292 */     this.selectionModel = ((ChoiceBox)getSkinnable()).getSelectionModel();
/* 293 */     if (this.selectionModel != null)
/* 294 */       this.selectionModel.selectedIndexProperty().addListener(this.selectionChangeListener);
/*     */   }
/*     */ 
/*     */   private void updateSelection()
/*     */   {
/* 305 */     if ((this.selectionModel == null) || (this.selectionModel.isEmpty())) {
/* 306 */       this.toggleGroup.selectToggle(null);
/* 307 */       this.label.setText("");
/*     */     } else {
/* 309 */       int i = this.selectionModel.getSelectedIndex();
/* 310 */       if ((i == -1) || (i > this.popup.getItems().size())) {
/* 311 */         this.label.setText("");
/* 312 */         return;
/*     */       }
/* 314 */       if (i < this.popup.getItems().size()) {
/* 315 */         MenuItem localMenuItem = (MenuItem)this.popup.getItems().get(i);
/* 316 */         if ((localMenuItem instanceof RadioMenuItem)) {
/* 317 */           ((RadioMenuItem)localMenuItem).setSelected(true);
/* 318 */           this.toggleGroup.selectToggle(null);
/*     */         }
/*     */ 
/* 321 */         this.label.setText(((MenuItem)this.popup.getItems().get(i)).getText());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/* 328 */     double d1 = this.openButton.prefWidth(-1.0D);
/*     */ 
/* 331 */     double d2 = ((ChoiceBox)getSkinnable()).getHeight() - (getInsets().getTop() + getInsets().getBottom());
/*     */ 
/* 333 */     double d3 = ((ChoiceBox)getSkinnable()).getWidth() - (getInsets().getLeft() + getInsets().getRight() + d1);
/*     */ 
/* 336 */     this.label.resizeRelocate(getInsets().getLeft(), getInsets().getTop(), d3, d2);
/* 337 */     this.openButton.resize(d1, this.openButton.prefHeight(-1.0D));
/* 338 */     positionInArea(this.openButton, getWidth() - getInsets().getRight() - d1, getInsets().getTop(), d1, d2, 0.0D, HPos.CENTER, VPos.CENTER);
/*     */   }
/*     */ 
/*     */   protected double computeMinWidth(double paramDouble)
/*     */   {
/* 343 */     double d1 = this.label.minWidth(-1.0D) + this.openButton.minWidth(-1.0D);
/* 344 */     double d2 = this.popup.minWidth(-1.0D);
/* 345 */     return getInsets().getLeft() + Math.max(d1, d2) + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computeMinHeight(double paramDouble)
/*     */   {
/* 350 */     double d1 = this.label.minHeight(-1.0D);
/* 351 */     double d2 = this.openButton.minHeight(-1.0D);
/* 352 */     return getInsets().getTop() + Math.max(d1, d2) + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 358 */     double d1 = this.label.prefWidth(-1.0D) + this.openButton.prefWidth(-1.0D);
/*     */ 
/* 360 */     double d2 = this.popup.prefWidth(-1.0D);
/* 361 */     if ((d2 <= 0.0D) && 
/* 362 */       (this.popup.getItems().size() > 0)) {
/* 363 */       d2 = new Text(((MenuItem)this.popup.getItems().get(0)).getText()).prefWidth(-1.0D);
/*     */     }
/*     */ 
/* 366 */     return this.popup.getItems().size() == 0 ? 50.0D : getInsets().getLeft() + Math.max(d1, d2) + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble)
/*     */   {
/* 371 */     double d1 = this.label.prefHeight(-1.0D);
/* 372 */     double d2 = this.openButton.prefHeight(-1.0D);
/* 373 */     return getInsets().getTop() + Math.max(d1, d2) + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   protected double computeMaxHeight(double paramDouble)
/*     */   {
/* 379 */     return ((ChoiceBox)getSkinnable()).prefHeight(paramDouble);
/*     */   }
/*     */ 
/*     */   protected double computeMaxWidth(double paramDouble) {
/* 383 */     return ((ChoiceBox)getSkinnable()).prefWidth(paramDouble);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ChoiceBoxSkin
 * JD-Core Version:    0.6.2
 */