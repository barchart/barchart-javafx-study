/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import java.lang.ref.WeakReference;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.WeakInvalidationListener;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.beans.value.WeakChangeListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class TreeCell<T> extends IndexedCell<T>
/*     */ {
/*  91 */   private final InvalidationListener indexListener = new InvalidationListener()
/*     */   {
/*     */     public void invalidated(Observable paramAnonymousObservable)
/*     */     {
/*  95 */       TreeCell.this.updateItem();
/*  96 */       TreeCell.this.updateSelection();
/*  97 */       TreeCell.this.updateFocus();
/*     */     }
/*  91 */   };
/*     */ 
/* 101 */   private final ListChangeListener selectedListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/* 103 */       TreeCell.this.updateSelection();
/*     */     }
/* 101 */   };
/*     */ 
/* 111 */   private final ChangeListener selectionModelPropertyListener = new ChangeListener()
/*     */   {
/*     */     public void changed(ObservableValue paramAnonymousObservableValue, MultipleSelectionModel paramAnonymousMultipleSelectionModel1, MultipleSelectionModel paramAnonymousMultipleSelectionModel2)
/*     */     {
/* 115 */       if (paramAnonymousMultipleSelectionModel1 != null) {
/* 116 */         paramAnonymousMultipleSelectionModel1.getSelectedIndices().removeListener(TreeCell.this.weakSelectedListener);
/*     */       }
/* 118 */       if (paramAnonymousMultipleSelectionModel2 != null) {
/* 119 */         paramAnonymousMultipleSelectionModel2.getSelectedIndices().addListener(TreeCell.this.weakSelectedListener);
/*     */       }
/* 121 */       TreeCell.this.updateSelection();
/*     */     }
/* 111 */   };
/*     */ 
/* 125 */   private final InvalidationListener focusedListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 127 */       TreeCell.this.updateFocus();
/*     */     }
/* 125 */   };
/*     */ 
/* 135 */   private final ChangeListener focusModelPropertyListener = new ChangeListener()
/*     */   {
/*     */     public void changed(ObservableValue paramAnonymousObservableValue, FocusModel paramAnonymousFocusModel1, FocusModel paramAnonymousFocusModel2)
/*     */     {
/* 139 */       if (paramAnonymousFocusModel1 != null) {
/* 140 */         paramAnonymousFocusModel1.focusedIndexProperty().removeListener(TreeCell.this.weakFocusedListener);
/*     */       }
/* 142 */       if (paramAnonymousFocusModel2 != null) {
/* 143 */         paramAnonymousFocusModel2.focusedIndexProperty().addListener(TreeCell.this.weakFocusedListener);
/*     */       }
/* 145 */       TreeCell.this.updateFocus();
/*     */     }
/* 135 */   };
/*     */ 
/* 149 */   private final InvalidationListener editingListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 151 */       TreeCell.this.updateEditing();
/*     */     }
/* 149 */   };
/*     */ 
/* 155 */   private final WeakListChangeListener weakSelectedListener = new WeakListChangeListener(this.selectedListener);
/* 156 */   private final WeakChangeListener weakSelectionModelPropertyListener = new WeakChangeListener(this.selectionModelPropertyListener);
/* 157 */   private final WeakInvalidationListener weakFocusedListener = new WeakInvalidationListener(this.focusedListener);
/* 158 */   private final WeakChangeListener weakFocusModelPropertyListener = new WeakChangeListener(this.focusModelPropertyListener);
/* 159 */   private final WeakInvalidationListener weakEditingListener = new WeakInvalidationListener(this.editingListener);
/*     */ 
/* 169 */   private ReadOnlyObjectWrapper<TreeItem<T>> treeItem = new ReadOnlyObjectWrapper(this, "treeItem");
/*     */ 
/* 186 */   private ObjectProperty<Node> disclosureNode = new SimpleObjectProperty(this, "disclosureNode");
/*     */ 
/* 211 */   private ReadOnlyObjectWrapper<TreeView<T>> treeView = new ReadOnlyObjectWrapper()
/*     */   {
/*     */     private WeakReference<TreeView<T>> weakTreeViewRef;
/*     */ 
/*     */     protected void invalidated()
/*     */     {
/*     */       MultipleSelectionModel localMultipleSelectionModel;
/*     */       FocusModel localFocusModel;
/* 217 */       if (this.weakTreeViewRef != null) {
/* 218 */         TreeView localTreeView = (TreeView)this.weakTreeViewRef.get();
/* 219 */         if (localTreeView != null)
/*     */         {
/* 221 */           localMultipleSelectionModel = localTreeView.getSelectionModel();
/* 222 */           if (localMultipleSelectionModel != null) {
/* 223 */             localMultipleSelectionModel.getSelectedIndices().removeListener(TreeCell.this.weakSelectedListener);
/*     */           }
/*     */ 
/* 226 */           localFocusModel = localTreeView.getFocusModel();
/* 227 */           if (localFocusModel != null) {
/* 228 */             localFocusModel.focusedIndexProperty().removeListener(TreeCell.this.weakFocusedListener);
/*     */           }
/*     */ 
/* 231 */           localTreeView.editingItemProperty().removeListener(TreeCell.this.weakEditingListener);
/* 232 */           localTreeView.focusModelProperty().removeListener(TreeCell.this.weakFocusModelPropertyListener);
/* 233 */           localTreeView.selectionModelProperty().removeListener(TreeCell.this.weakSelectionModelPropertyListener);
/*     */         }
/*     */ 
/* 236 */         this.weakTreeViewRef = null;
/*     */       }
/*     */ 
/* 239 */       if (get() != null) {
/* 240 */         localMultipleSelectionModel = ((TreeView)get()).getSelectionModel();
/* 241 */         if (localMultipleSelectionModel != null)
/*     */         {
/* 244 */           localMultipleSelectionModel.getSelectedIndices().addListener(TreeCell.this.weakSelectedListener);
/*     */         }
/*     */ 
/* 247 */         localFocusModel = ((TreeView)get()).getFocusModel();
/* 248 */         if (localFocusModel != null)
/*     */         {
/* 250 */           localFocusModel.focusedIndexProperty().addListener(TreeCell.this.weakFocusedListener);
/*     */         }
/*     */ 
/* 253 */         ((TreeView)get()).editingItemProperty().addListener(TreeCell.this.weakEditingListener);
/* 254 */         ((TreeView)get()).focusModelProperty().addListener(TreeCell.this.weakFocusModelPropertyListener);
/* 255 */         ((TreeView)get()).selectionModelProperty().addListener(TreeCell.this.weakSelectionModelPropertyListener);
/*     */ 
/* 257 */         this.weakTreeViewRef = new WeakReference(get());
/*     */       }
/*     */ 
/* 260 */       TreeCell.this.requestLayout();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 265 */       return TreeCell.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 270 */       return "treeView";
/*     */     }
/* 211 */   };
/*     */   private static final String DEFAULT_STYLE_CLASS = "tree-cell";
/* 484 */   private static final long EXPANDED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("expanded");
/* 485 */   private static final long COLLAPSED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("collapsed");
/*     */ 
/*     */   public TreeCell()
/*     */   {
/*  79 */     getStyleClass().addAll(new String[] { "tree-cell" });
/*  80 */     indexProperty().addListener(this.indexListener);
/*     */   }
/*     */ 
/*     */   private void setTreeItem(TreeItem<T> paramTreeItem)
/*     */   {
/* 170 */     this.treeItem.set(paramTreeItem);
/*     */   }
/*     */ 
/*     */   public final TreeItem<T> getTreeItem()
/*     */   {
/* 175 */     return (TreeItem)this.treeItem.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<TreeItem<T>> treeItemProperty()
/*     */   {
/* 181 */     return this.treeItem.getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   public final void setDisclosureNode(Node paramNode)
/*     */   {
/* 195 */     disclosureNodeProperty().set(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getDisclosureNode()
/*     */   {
/* 200 */     return (Node)this.disclosureNode.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> disclosureNodeProperty()
/*     */   {
/* 207 */     return this.disclosureNode;
/*     */   }
/*     */ 
/*     */   private void setTreeView(TreeView<T> paramTreeView)
/*     */   {
/* 274 */     this.treeView.set(paramTreeView);
/*     */   }
/*     */ 
/*     */   public final TreeView<T> getTreeView()
/*     */   {
/* 279 */     return (TreeView)this.treeView.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<TreeView<T>> treeViewProperty()
/*     */   {
/* 285 */     return this.treeView.getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 297 */     TreeView localTreeView = getTreeView();
/* 298 */     if ((!isEditable()) || ((localTreeView != null) && (!localTreeView.isEditable())))
/*     */     {
/* 305 */       return;
/*     */     }
/*     */ 
/* 311 */     super.startEdit();
/*     */ 
/* 314 */     if (localTreeView != null) {
/* 315 */       localTreeView.fireEvent(new TreeView.EditEvent(localTreeView, TreeView.editStartEvent(), getTreeItem(), getItem(), null));
/*     */ 
/* 321 */       localTreeView.requestFocus();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void commitEdit(T paramT)
/*     */   {
/* 327 */     if (!isEditing()) return;
/* 328 */     TreeItem localTreeItem = getTreeItem();
/* 329 */     TreeView localTreeView = getTreeView();
/* 330 */     if (localTreeView != null)
/*     */     {
/* 332 */       localTreeView.fireEvent(new TreeView.EditEvent(localTreeView, TreeView.editCommitEvent(), localTreeItem, getItem(), paramT));
/*     */     }
/*     */ 
/* 340 */     if (localTreeItem != null) {
/* 341 */       localTreeItem.setValue(paramT);
/* 342 */       updateTreeItem(localTreeItem);
/* 343 */       updateItem(paramT, false);
/*     */     }
/*     */ 
/* 348 */     super.commitEdit(paramT);
/*     */ 
/* 350 */     if (localTreeView != null)
/*     */     {
/* 352 */       localTreeView.edit(null);
/* 353 */       localTreeView.requestFocus();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 359 */     if (!isEditing()) return;
/*     */ 
/* 361 */     TreeView localTreeView = getTreeView();
/*     */ 
/* 363 */     super.cancelEdit();
/*     */ 
/* 365 */     if (localTreeView != null)
/*     */     {
/* 367 */       localTreeView.edit(null);
/* 368 */       localTreeView.requestFocus();
/*     */ 
/* 370 */       localTreeView.fireEvent(new TreeView.EditEvent(localTreeView, TreeView.editCancelEvent(), getTreeItem(), getItem(), null));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateItem()
/*     */   {
/* 387 */     TreeView localTreeView = getTreeView();
/* 388 */     if (localTreeView == null) return;
/*     */ 
/* 391 */     int i = (getIndex() >= 0) && (getIndex() < localTreeView.impl_getTreeItemCount()) ? 1 : 0;
/*     */ 
/* 394 */     if (i != 0)
/*     */     {
/* 397 */       TreeItem localTreeItem = i != 0 ? localTreeView.getTreeItem(getIndex()) : null;
/*     */ 
/* 404 */       updateTreeItem(localTreeItem);
/* 405 */       updateItem(localTreeItem == null ? null : localTreeItem.getValue(), localTreeItem == null);
/*     */     } else {
/* 407 */       updateTreeItem(null);
/* 408 */       updateItem(null, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateSelection() {
/* 413 */     if (isEmpty()) return;
/* 414 */     if ((getIndex() == -1) || (getTreeView() == null)) return;
/* 415 */     if (getTreeView().getSelectionModel() == null) return;
/*     */ 
/* 417 */     boolean bool = getTreeView().getSelectionModel().isSelected(getIndex());
/* 418 */     if (isSelected() == bool) return;
/*     */ 
/* 420 */     updateSelected(bool);
/*     */   }
/*     */ 
/*     */   private void updateFocus() {
/* 424 */     if ((getIndex() == -1) || (getTreeView() == null)) return;
/* 425 */     if (getTreeView().getFocusModel() == null) return;
/*     */ 
/* 427 */     setFocused(getTreeView().getFocusModel().isFocused(getIndex()));
/*     */   }
/*     */ 
/*     */   private void updateEditing() {
/* 431 */     if ((getIndex() == -1) || (getTreeView() == null) || (getTreeItem() == null)) return;
/*     */ 
/* 433 */     TreeItem localTreeItem = getTreeView().getEditingItem();
/* 434 */     if ((!isEditing()) && (getTreeItem().equals(localTreeItem)))
/* 435 */       startEdit();
/* 436 */     else if ((isEditing()) && (!getTreeItem().equals(localTreeItem)))
/* 437 */       cancelEdit();
/*     */   }
/*     */ 
/*     */   public final void updateTreeView(TreeView<T> paramTreeView)
/*     */   {
/* 458 */     setTreeView(paramTreeView);
/*     */   }
/*     */ 
/*     */   public final void updateTreeItem(TreeItem<T> paramTreeItem)
/*     */   {
/* 471 */     setTreeItem(paramTreeItem);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public long impl_getPseudoClassState()
/*     */   {
/* 492 */     long l = super.impl_getPseudoClassState();
/* 493 */     if ((getTreeItem() != null) && (!getTreeItem().isLeaf())) {
/* 494 */       l |= (getTreeItem().isExpanded() ? EXPANDED_PSEUDOCLASS_STATE : COLLAPSED_PSEUDOCLASS_STATE);
/*     */     }
/* 496 */     return l;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TreeCell
 * JD-Core Version:    0.6.2
 */