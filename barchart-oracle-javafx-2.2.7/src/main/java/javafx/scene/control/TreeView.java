/*      */ package javafx.scene.control;
/*      */ 
/*      */ import com.sun.javafx.scene.control.WeakEventHandler;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.List;
/*      */ import javafx.beans.DefaultProperty;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.BooleanPropertyBase;
/*      */ import javafx.beans.property.IntegerProperty;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ObjectPropertyBase;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*      */ import javafx.beans.property.SimpleBooleanProperty;
/*      */ import javafx.beans.property.SimpleIntegerProperty;
/*      */ import javafx.beans.property.SimpleObjectProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.beans.value.WeakChangeListener;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.collections.ObservableMap;
/*      */ import javafx.event.Event;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.event.EventType;
/*      */ import javafx.util.Callback;
/*      */ 
/*      */ @DefaultProperty("root")
/*      */ public class TreeView<T> extends Control
/*      */ {
/*  137 */   private static final EventType<?> EDIT_ANY_EVENT = new EventType(Event.ANY, "EDIT");
/*      */ 
/*  150 */   private static final EventType<?> EDIT_START_EVENT = new EventType(editAnyEvent(), "EDIT_START");
/*      */ 
/*  164 */   private static final EventType<?> EDIT_CANCEL_EVENT = new EventType(editAnyEvent(), "EDIT_CANCEL");
/*      */ 
/*  179 */   private static final EventType<?> EDIT_COMMIT_EVENT = new EventType(editAnyEvent(), "EDIT_COMMIT");
/*      */ 
/*  255 */   private boolean treeItemCountDirty = true;
/*      */ 
/*  267 */   private final EventHandler<TreeItem.TreeModificationEvent<T>> rootEvent = new EventHandler()
/*      */   {
/*      */     public void handle(TreeItem.TreeModificationEvent<T> paramAnonymousTreeModificationEvent)
/*      */     {
/*  271 */       EventType localEventType = paramAnonymousTreeModificationEvent.getEventType();
/*  272 */       int i = 0;
/*  273 */       while (localEventType != null) {
/*  274 */         if (localEventType.equals(TreeItem.treeItemCountChangeEvent())) {
/*  275 */           i = 1;
/*  276 */           break;
/*      */         }
/*  278 */         localEventType = localEventType.getSuperType();
/*      */       }
/*      */ 
/*  281 */       if (i != 0) {
/*  282 */         TreeView.this.treeItemCountDirty = true;
/*  283 */         TreeView.this.requestLayout();
/*      */       }
/*      */     }
/*  267 */   };
/*      */   private WeakEventHandler weakRootEventListener;
/*      */   private ObjectProperty<Callback<TreeView<T>, TreeCell<T>>> cellFactory;
/*  341 */   private ObjectProperty<TreeItem<T>> root = new ObjectPropertyBase() {
/*      */     private WeakReference<TreeItem<T>> weakOldItem;
/*      */ 
/*      */     protected void invalidated() {
/*  345 */       TreeItem localTreeItem1 = this.weakOldItem == null ? null : (TreeItem)this.weakOldItem.get();
/*  346 */       if ((localTreeItem1 != null) && (TreeView.this.weakRootEventListener != null)) {
/*  347 */         localTreeItem1.removeEventHandler(TreeItem.treeNotificationEvent(), TreeView.this.weakRootEventListener);
/*      */       }
/*      */ 
/*  350 */       TreeItem localTreeItem2 = TreeView.this.getRoot();
/*  351 */       if (localTreeItem2 != null) {
/*  352 */         TreeView.this.weakRootEventListener = new WeakEventHandler(localTreeItem2, TreeItem.treeNotificationEvent(), TreeView.this.rootEvent);
/*  353 */         TreeView.this.getRoot().addEventHandler(TreeItem.treeNotificationEvent(), TreeView.this.weakRootEventListener);
/*  354 */         this.weakOldItem = new WeakReference(localTreeItem2);
/*      */       }
/*      */ 
/*  357 */       TreeView.this.setTreeItemCount(0);
/*  358 */       TreeView.this.updateRootExpanded();
/*      */     }
/*      */ 
/*      */     public Object getBean() {
/*  362 */       return TreeView.this;
/*      */     }
/*      */ 
/*      */     public String getName() {
/*  366 */       return "root";
/*      */     }
/*  341 */   };
/*      */   private BooleanProperty showRoot;
/*      */   private ObjectProperty<MultipleSelectionModel<TreeItem<T>>> selectionModel;
/*      */   private ObjectProperty<FocusModel<TreeItem<T>>> focusModel;
/*  512 */   private IntegerProperty treeItemCount = new SimpleIntegerProperty(this, "impl_treeItemCount", 0);
/*      */   private BooleanProperty editable;
/*      */   private ReadOnlyObjectWrapper<TreeItem<T>> editingItem;
/*      */   private ObjectProperty<EventHandler<EditEvent<T>>> onEditStart;
/*      */   private ObjectProperty<EventHandler<EditEvent<T>>> onEditCommit;
/*      */   private ObjectProperty<EventHandler<EditEvent<T>>> onEditCancel;
/*      */ 
/*      */   public static <T> EventType<EditEvent<T>> editAnyEvent()
/*      */   {
/*  135 */     return EDIT_ANY_EVENT;
/*      */   }
/*      */ 
/*      */   public static <T> EventType<EditEvent<T>> editStartEvent()
/*      */   {
/*  148 */     return EDIT_START_EVENT;
/*      */   }
/*      */ 
/*      */   public static <T> EventType<EditEvent<T>> editCancelEvent()
/*      */   {
/*  162 */     return EDIT_CANCEL_EVENT;
/*      */   }
/*      */ 
/*      */   public static <T> EventType<EditEvent<T>> editCommitEvent()
/*      */   {
/*  177 */     return EDIT_COMMIT_EVENT;
/*      */   }
/*      */ 
/*      */   public static int getNodeLevel(TreeItem<?> paramTreeItem)
/*      */   {
/*  194 */     if (paramTreeItem == null) return -1;
/*      */ 
/*  196 */     int i = 0;
/*  197 */     TreeItem localTreeItem = paramTreeItem.getParent();
/*  198 */     while (localTreeItem != null) {
/*  199 */       i++;
/*  200 */       localTreeItem = localTreeItem.getParent();
/*      */     }
/*      */ 
/*  203 */     return i;
/*      */   }
/*      */ 
/*      */   public TreeView()
/*      */   {
/*  220 */     this(null);
/*      */   }
/*      */ 
/*      */   public TreeView(TreeItem<T> paramTreeItem)
/*      */   {
/*  232 */     getStyleClass().setAll(new String[] { "tree-view" });
/*      */ 
/*  234 */     setRoot(paramTreeItem);
/*  235 */     updateTreeItemCount();
/*      */ 
/*  239 */     TreeViewBitSetSelectionModel localTreeViewBitSetSelectionModel = new TreeViewBitSetSelectionModel(this);
/*  240 */     setSelectionModel(localTreeViewBitSetSelectionModel);
/*  241 */     setFocusModel(new TreeViewFocusModel(this));
/*      */   }
/*      */ 
/*      */   public final void setCellFactory(Callback<TreeView<T>, TreeCell<T>> paramCallback)
/*      */   {
/*  316 */     cellFactoryProperty().set(paramCallback);
/*      */   }
/*      */ 
/*      */   public final Callback<TreeView<T>, TreeCell<T>> getCellFactory()
/*      */   {
/*  325 */     return this.cellFactory == null ? null : (Callback)this.cellFactory.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Callback<TreeView<T>, TreeCell<T>>> cellFactoryProperty()
/*      */   {
/*  333 */     if (this.cellFactory == null) {
/*  334 */       this.cellFactory = new SimpleObjectProperty(this, "cellFactory");
/*      */     }
/*  336 */     return this.cellFactory;
/*      */   }
/*      */ 
/*      */   public final void setRoot(TreeItem<T> paramTreeItem)
/*      */   {
/*  378 */     rootProperty().set(paramTreeItem);
/*      */   }
/*      */ 
/*      */   public final TreeItem<T> getRoot()
/*      */   {
/*  387 */     return this.root == null ? null : (TreeItem)this.root.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<TreeItem<T>> rootProperty()
/*      */   {
/*  394 */     return this.root;
/*      */   }
/*      */ 
/*      */   public final void setShowRoot(boolean paramBoolean)
/*      */   {
/*  410 */     showRootProperty().set(paramBoolean);
/*      */   }
/*      */ 
/*      */   public final boolean isShowRoot()
/*      */   {
/*  418 */     return this.showRoot == null ? true : this.showRoot.get();
/*      */   }
/*      */ 
/*      */   public final BooleanProperty showRootProperty()
/*      */   {
/*  425 */     if (this.showRoot == null) {
/*  426 */       this.showRoot = new BooleanPropertyBase(true) {
/*      */         protected void invalidated() {
/*  428 */           TreeView.this.updateRootExpanded();
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  433 */           return TreeView.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  438 */           return "showRoot";
/*      */         }
/*      */       };
/*      */     }
/*  442 */     return this.showRoot;
/*      */   }
/*      */ 
/*      */   public final void setSelectionModel(MultipleSelectionModel<TreeItem<T>> paramMultipleSelectionModel)
/*      */   {
/*  457 */     selectionModelProperty().set(paramMultipleSelectionModel);
/*      */   }
/*      */ 
/*      */   public final MultipleSelectionModel<TreeItem<T>> getSelectionModel()
/*      */   {
/*  464 */     return this.selectionModel == null ? null : (MultipleSelectionModel)this.selectionModel.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<MultipleSelectionModel<TreeItem<T>>> selectionModelProperty()
/*      */   {
/*  474 */     if (this.selectionModel == null) {
/*  475 */       this.selectionModel = new SimpleObjectProperty(this, "selectionModel");
/*      */     }
/*  477 */     return this.selectionModel;
/*      */   }
/*      */ 
/*      */   public final void setFocusModel(FocusModel<TreeItem<T>> paramFocusModel)
/*      */   {
/*  488 */     focusModelProperty().set(paramFocusModel);
/*      */   }
/*      */ 
/*      */   public final FocusModel<TreeItem<T>> getFocusModel()
/*      */   {
/*  495 */     return this.focusModel == null ? null : (FocusModel)this.focusModel.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<FocusModel<TreeItem<T>>> focusModelProperty()
/*      */   {
/*  504 */     if (this.focusModel == null) {
/*  505 */       this.focusModel = new SimpleObjectProperty(this, "focusModel");
/*      */     }
/*  507 */     return this.focusModel;
/*      */   }
/*      */ 
/*      */   private void setTreeItemCount(int paramInt)
/*      */   {
/*  515 */     impl_treeItemCountProperty().set(paramInt);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public final int impl_getTreeItemCount()
/*      */   {
/*  532 */     if (this.treeItemCountDirty) {
/*  533 */       updateTreeItemCount();
/*      */     }
/*  535 */     return this.treeItemCount.get();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public final IntegerProperty impl_treeItemCountProperty()
/*      */   {
/*  544 */     return this.treeItemCount;
/*      */   }
/*      */ 
/*      */   public final void setEditable(boolean paramBoolean)
/*      */   {
/*  551 */     editableProperty().set(paramBoolean);
/*      */   }
/*      */   public final boolean isEditable() {
/*  554 */     return this.editable == null ? false : this.editable.get();
/*      */   }
/*      */ 
/*      */   public final BooleanProperty editableProperty()
/*      */   {
/*  562 */     if (this.editable == null) {
/*  563 */       this.editable = new SimpleBooleanProperty(this, "editable", false);
/*      */     }
/*  565 */     return this.editable;
/*      */   }
/*      */ 
/*      */   private void setEditingItem(TreeItem<T> paramTreeItem)
/*      */   {
/*  573 */     editingItemPropertyImpl().set(paramTreeItem);
/*      */   }
/*      */ 
/*      */   public final TreeItem<T> getEditingItem()
/*      */   {
/*  581 */     return this.editingItem == null ? null : (TreeItem)this.editingItem.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyObjectProperty<TreeItem<T>> editingItemProperty()
/*      */   {
/*  592 */     return editingItemPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyObjectWrapper<TreeItem<T>> editingItemPropertyImpl() {
/*  596 */     if (this.editingItem == null) {
/*  597 */       this.editingItem = new ReadOnlyObjectWrapper(this, "editingItem");
/*      */     }
/*  599 */     return this.editingItem;
/*      */   }
/*      */ 
/*      */   public final void setOnEditStart(EventHandler<EditEvent<T>> paramEventHandler)
/*      */   {
/*  611 */     onEditStartProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<EditEvent<T>> getOnEditStart()
/*      */   {
/*  619 */     return this.onEditStart == null ? null : (EventHandler)this.onEditStart.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<EditEvent<T>>> onEditStartProperty()
/*      */   {
/*  627 */     if (this.onEditStart == null) {
/*  628 */       this.onEditStart = new ObjectPropertyBase() {
/*      */         protected void invalidated() {
/*  630 */           TreeView.this.setEventHandler(TreeView.editStartEvent(), (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  635 */           return TreeView.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  640 */           return "onEditStart";
/*      */         }
/*      */       };
/*      */     }
/*  644 */     return this.onEditStart;
/*      */   }
/*      */ 
/*      */   public final void setOnEditCommit(EventHandler<EditEvent<T>> paramEventHandler)
/*      */   {
/*  656 */     onEditCommitProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<EditEvent<T>> getOnEditCommit()
/*      */   {
/*  664 */     return this.onEditCommit == null ? null : (EventHandler)this.onEditCommit.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<EditEvent<T>>> onEditCommitProperty()
/*      */   {
/*  677 */     if (this.onEditCommit == null) {
/*  678 */       this.onEditCommit = new ObjectPropertyBase() {
/*      */         protected void invalidated() {
/*  680 */           TreeView.this.setEventHandler(TreeView.editCommitEvent(), (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  685 */           return TreeView.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  690 */           return "onEditCommit";
/*      */         }
/*      */       };
/*      */     }
/*  694 */     return this.onEditCommit;
/*      */   }
/*      */ 
/*      */   public final void setOnEditCancel(EventHandler<EditEvent<T>> paramEventHandler)
/*      */   {
/*  706 */     onEditCancelProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<EditEvent<T>> getOnEditCancel()
/*      */   {
/*  714 */     return this.onEditCancel == null ? null : (EventHandler)this.onEditCancel.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<EditEvent<T>>> onEditCancelProperty()
/*      */   {
/*  721 */     if (this.onEditCancel == null) {
/*  722 */       this.onEditCancel = new ObjectPropertyBase() {
/*      */         protected void invalidated() {
/*  724 */           TreeView.this.setEventHandler(TreeView.editCancelEvent(), (EventHandler)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  729 */           return TreeView.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  734 */           return "onEditCancel";
/*      */         }
/*      */       };
/*      */     }
/*  738 */     return this.onEditCancel;
/*      */   }
/*      */ 
/*      */   protected void layoutChildren()
/*      */   {
/*  752 */     if (this.treeItemCountDirty) {
/*  753 */       updateTreeItemCount();
/*      */     }
/*      */ 
/*  756 */     super.layoutChildren();
/*      */   }
/*      */ 
/*      */   public void edit(TreeItem<T> paramTreeItem)
/*      */   {
/*  770 */     if (!isEditable()) return;
/*  771 */     setEditingItem(paramTreeItem);
/*      */   }
/*      */ 
/*      */   public void scrollTo(int paramInt)
/*      */   {
/*  784 */     getProperties().put("VirtualContainerBase.scrollToIndexCentered", Integer.valueOf(paramInt));
/*      */   }
/*      */ 
/*      */   public int getRow(TreeItem<T> paramTreeItem)
/*      */   {
/*  797 */     if (paramTreeItem == null)
/*  798 */       return -1;
/*  799 */     if ((isShowRoot()) && (paramTreeItem.equals(getRoot()))) {
/*  800 */       return 0;
/*      */     }
/*      */ 
/*  803 */     int i = 0;
/*  804 */     Object localObject = paramTreeItem;
/*  805 */     TreeItem localTreeItem1 = paramTreeItem.getParent();
/*      */ 
/*  810 */     while ((!localObject.equals(getRoot())) && (localTreeItem1 != null)) {
/*  811 */       ObservableList localObservableList = localTreeItem1.getChildren();
/*      */ 
/*  814 */       int j = localObservableList.indexOf(localObject);
/*  815 */       for (int k = j - 1; k > -1; k--) {
/*  816 */         TreeItem localTreeItem2 = (TreeItem)localObservableList.get(k);
/*  817 */         if (localTreeItem2 != null)
/*      */         {
/*  819 */           i += getExpandedDescendantCount(localTreeItem2);
/*      */ 
/*  821 */           if (localTreeItem2.equals(getRoot())) {
/*  822 */             if (!isShowRoot())
/*      */             {
/*  826 */               return -1;
/*      */             }
/*  828 */             return i;
/*      */           }
/*      */         }
/*      */       }
/*  832 */       localObject = localTreeItem1;
/*  833 */       localTreeItem1 = localTreeItem1.getParent();
/*  834 */       i++;
/*      */     }
/*      */ 
/*  837 */     return isShowRoot() ? i : (localTreeItem1 == null) && (i == 0) ? -1 : i - 1;
/*      */   }
/*      */ 
/*      */   public TreeItem<T> getTreeItem(int paramInt)
/*      */   {
/*  848 */     int i = isShowRoot() ? paramInt : paramInt + 1;
/*  849 */     return getItem(getRoot(), i);
/*      */   }
/*      */ 
/*      */   private int getExpandedDescendantCount(TreeItem<T> paramTreeItem)
/*      */   {
/*  861 */     if (paramTreeItem == null) return 0;
/*  862 */     if (paramTreeItem.isLeaf()) return 1;
/*      */ 
/*  864 */     return paramTreeItem.getExpandedDescendentCount(this.treeItemCountDirty);
/*      */   }
/*      */ 
/*      */   private void updateTreeItemCount() {
/*  868 */     if (getRoot() == null) {
/*  869 */       setTreeItemCount(0);
/*  870 */     } else if (!getRoot().isExpanded()) {
/*  871 */       setTreeItemCount(1);
/*      */     } else {
/*  873 */       int i = getExpandedDescendantCount(getRoot());
/*  874 */       if (!isShowRoot()) i--;
/*      */ 
/*  876 */       setTreeItemCount(i);
/*      */     }
/*  878 */     this.treeItemCountDirty = false;
/*      */   }
/*      */ 
/*      */   private TreeItem getItem(TreeItem<T> paramTreeItem, int paramInt) {
/*  882 */     if (paramTreeItem == null) return null;
/*      */ 
/*  885 */     if (paramInt == 0) return paramTreeItem;
/*      */ 
/*  888 */     if (paramInt >= getExpandedDescendantCount(paramTreeItem)) return null;
/*      */ 
/*  891 */     ObservableList localObservableList = paramTreeItem.getChildren();
/*  892 */     if (localObservableList == null) return null;
/*      */ 
/*  894 */     int i = paramInt - 1;
/*      */ 
/*  897 */     for (int j = 0; j < localObservableList.size(); j++) {
/*  898 */       TreeItem localTreeItem1 = (TreeItem)localObservableList.get(j);
/*  899 */       if (i == 0) return localTreeItem1;
/*      */ 
/*  901 */       if ((localTreeItem1.isLeaf()) || (!localTreeItem1.isExpanded())) {
/*  902 */         i--;
/*      */       }
/*      */       else
/*      */       {
/*  906 */         int k = getExpandedDescendantCount(localTreeItem1);
/*  907 */         if (i >= k) {
/*  908 */           i -= k;
/*      */         }
/*      */         else
/*      */         {
/*  912 */           TreeItem localTreeItem2 = getItem(localTreeItem1, i);
/*  913 */           if (localTreeItem2 != null) return localTreeItem2;
/*  914 */           i--;
/*      */         }
/*      */       }
/*      */     }
/*  918 */     return null;
/*      */   }
/*      */ 
/*      */   private void updateRootExpanded()
/*      */   {
/*  924 */     if ((!isShowRoot()) && (getRoot() != null) && (!getRoot().isExpanded())) {
/*  925 */       getRoot().setExpanded(true);
/*      */     }
/*      */ 
/*  928 */     updateTreeItemCount();
/*      */   }
/*      */ 
/*      */   static class TreeViewFocusModel<T> extends FocusModel<TreeItem<T>>
/*      */   {
/*      */     private final TreeView<T> treeView;
/* 1226 */     private final ChangeListener rootPropertyListener = new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends TreeItem<T>> paramAnonymousObservableValue, TreeItem<T> paramAnonymousTreeItem1, TreeItem<T> paramAnonymousTreeItem2) {
/* 1229 */         TreeView.TreeViewFocusModel.this.updateTreeEventListener(paramAnonymousTreeItem1, paramAnonymousTreeItem2);
/*      */       }
/* 1226 */     };
/*      */ 
/* 1233 */     private final WeakChangeListener weakRootPropertyListener = new WeakChangeListener(this.rootPropertyListener);
/*      */ 
/* 1247 */     private EventHandler<TreeItem.TreeModificationEvent<T>> treeItemListener = new EventHandler()
/*      */     {
/*      */       public void handle(TreeItem.TreeModificationEvent<T> paramAnonymousTreeModificationEvent)
/*      */       {
/* 1251 */         if (TreeView.TreeViewFocusModel.this.getFocusedIndex() == -1) return;
/*      */ 
/* 1253 */         int i = TreeView.this.getRow(paramAnonymousTreeModificationEvent.getTreeItem());
/* 1254 */         int j = 0;
/* 1255 */         if (paramAnonymousTreeModificationEvent.wasExpanded()) {
/* 1256 */           if (i > TreeView.TreeViewFocusModel.this.getFocusedIndex())
/*      */           {
/* 1258 */             j = paramAnonymousTreeModificationEvent.getTreeItem().getExpandedDescendentCount(false) - 1;
/*      */           }
/* 1260 */         } else if (paramAnonymousTreeModificationEvent.wasCollapsed()) {
/* 1261 */           if (i > TreeView.TreeViewFocusModel.this.getFocusedIndex())
/*      */           {
/* 1264 */             j = -paramAnonymousTreeModificationEvent.getTreeItem().previousExpandedDescendentCount + 1;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*      */           int k;
/*      */           TreeItem localTreeItem;
/* 1266 */           if (paramAnonymousTreeModificationEvent.wasAdded()) {
/* 1267 */             for (k = 0; k < paramAnonymousTreeModificationEvent.getAddedChildren().size(); k++) {
/* 1268 */               localTreeItem = (TreeItem)paramAnonymousTreeModificationEvent.getAddedChildren().get(k);
/* 1269 */               i = TreeView.this.getRow(localTreeItem);
/*      */ 
/* 1271 */               if ((localTreeItem != null) && (i <= TreeView.TreeViewFocusModel.this.getFocusedIndex()))
/*      */               {
/* 1273 */                 j += localTreeItem.getExpandedDescendentCount(false);
/*      */               }
/*      */             }
/* 1276 */           } else if (paramAnonymousTreeModificationEvent.wasRemoved()) {
/* 1277 */             for (k = 0; k < paramAnonymousTreeModificationEvent.getRemovedChildren().size(); k++) {
/* 1278 */               localTreeItem = (TreeItem)paramAnonymousTreeModificationEvent.getRemovedChildren().get(k);
/* 1279 */               if ((localTreeItem != null) && (localTreeItem.equals(TreeView.TreeViewFocusModel.this.getFocusedItem()))) {
/* 1280 */                 TreeView.TreeViewFocusModel.this.focus(-1);
/* 1281 */                 return;
/*      */               }
/*      */             }
/*      */ 
/* 1285 */             if (i <= TreeView.TreeViewFocusModel.this.getFocusedIndex())
/*      */             {
/* 1287 */               j = paramAnonymousTreeModificationEvent.getTreeItem().isExpanded() ? -paramAnonymousTreeModificationEvent.getRemovedSize() : 0;
/*      */             }
/*      */           }
/*      */         }
/* 1291 */         TreeView.TreeViewFocusModel.this.focus(TreeView.TreeViewFocusModel.this.getFocusedIndex() + j);
/*      */       }
/* 1247 */     };
/*      */     private WeakEventHandler weakTreeItemListener;
/*      */ 
/*      */     public TreeViewFocusModel(TreeView<T> paramTreeView)
/*      */     {
/* 1221 */       this.treeView = paramTreeView;
/* 1222 */       this.treeView.rootProperty().addListener(this.weakRootPropertyListener);
/* 1223 */       updateTreeEventListener(null, paramTreeView.getRoot());
/*      */     }
/*      */ 
/*      */     private void updateTreeEventListener(TreeItem<T> paramTreeItem1, TreeItem<T> paramTreeItem2)
/*      */     {
/* 1237 */       if ((paramTreeItem1 != null) && (this.weakTreeItemListener != null)) {
/* 1238 */         paramTreeItem1.removeEventHandler(TreeItem.treeItemCountChangeEvent(), this.weakTreeItemListener);
/*      */       }
/*      */ 
/* 1241 */       if (paramTreeItem2 != null) {
/* 1242 */         this.weakTreeItemListener = new WeakEventHandler(paramTreeItem2, TreeItem.treeItemCountChangeEvent(), this.treeItemListener);
/* 1243 */         paramTreeItem2.addEventHandler(TreeItem.treeItemCountChangeEvent(), this.weakTreeItemListener);
/*      */       }
/*      */     }
/*      */ 
/*      */     protected int getItemCount()
/*      */     {
/* 1298 */       return this.treeView == null ? -1 : this.treeView.impl_getTreeItemCount();
/*      */     }
/*      */ 
/*      */     protected TreeItem<T> getModelItem(int paramInt) {
/* 1302 */       if (this.treeView == null) return null;
/*      */ 
/* 1304 */       if ((paramInt < 0) || (paramInt >= this.treeView.impl_getTreeItemCount())) return null;
/*      */ 
/* 1306 */       return this.treeView.getTreeItem(paramInt);
/*      */     }
/*      */   }
/*      */ 
/*      */   static class TreeViewBitSetSelectionModel<T> extends MultipleSelectionModelBase<TreeItem<T>>
/*      */   {
/* 1051 */     private ChangeListener rootPropertyListener = new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends TreeItem<T>> paramAnonymousObservableValue, TreeItem<T> paramAnonymousTreeItem1, TreeItem<T> paramAnonymousTreeItem2) {
/* 1054 */         TreeView.TreeViewBitSetSelectionModel.this.setSelectedIndex(-1);
/* 1055 */         TreeView.TreeViewBitSetSelectionModel.this.updateTreeEventListener(paramAnonymousTreeItem1, paramAnonymousTreeItem2);
/*      */       }
/* 1051 */     };
/*      */ 
/* 1059 */     private EventHandler<TreeItem.TreeModificationEvent<T>> treeItemListener = new EventHandler()
/*      */     {
/*      */       public void handle(TreeItem.TreeModificationEvent<T> paramAnonymousTreeModificationEvent) {
/* 1062 */         if ((TreeView.TreeViewBitSetSelectionModel.this.getSelectedIndex() == -1) && (TreeView.TreeViewBitSetSelectionModel.this.getSelectedItem() == null)) return;
/*      */ 
/* 1066 */         int i = TreeView.this.getRow(paramAnonymousTreeModificationEvent.getTreeItem());
/*      */ 
/* 1068 */         int j = 0;
/* 1069 */         if (paramAnonymousTreeModificationEvent.wasExpanded())
/*      */         {
/* 1071 */           j = paramAnonymousTreeModificationEvent.getTreeItem().getExpandedDescendentCount(false) - 1;
/* 1072 */           i++;
/*      */         }
/*      */         else
/*      */         {
/*      */           int m;
/*      */           int n;
/* 1073 */           if (paramAnonymousTreeModificationEvent.wasCollapsed())
/*      */           {
/* 1075 */             int k = TreeView.this.getRow(paramAnonymousTreeModificationEvent.getTreeItem());
/* 1076 */             m = paramAnonymousTreeModificationEvent.getTreeItem().previousExpandedDescendentCount;
/* 1077 */             n = 0;
/* 1078 */             for (int i1 = k; i1 < k + m; i1++) {
/* 1079 */               if (TreeView.TreeViewBitSetSelectionModel.this.isSelected(i1)) {
/* 1080 */                 n = 1;
/* 1081 */                 break;
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 1086 */             if (n != 0) {
/* 1087 */               TreeView.TreeViewBitSetSelectionModel.this.select(i);
/*      */             }
/*      */ 
/* 1090 */             j = -paramAnonymousTreeModificationEvent.getTreeItem().previousExpandedDescendentCount + 1;
/* 1091 */             i++;
/* 1092 */           } else if (paramAnonymousTreeModificationEvent.wasAdded())
/*      */           {
/* 1094 */             j = paramAnonymousTreeModificationEvent.getTreeItem().isExpanded() ? paramAnonymousTreeModificationEvent.getAddedSize() : 0;
/* 1095 */           } else if (paramAnonymousTreeModificationEvent.wasRemoved())
/*      */           {
/* 1097 */             j = paramAnonymousTreeModificationEvent.getTreeItem().isExpanded() ? -paramAnonymousTreeModificationEvent.getRemovedSize() : 0;
/*      */ 
/* 1102 */             ObservableList localObservableList = TreeView.TreeViewBitSetSelectionModel.this.getSelectedIndices();
/* 1103 */             for (m = 0; (m < localObservableList.size()) && (!TreeView.TreeViewBitSetSelectionModel.this.getSelectedItems().isEmpty()); m++) {
/* 1104 */               n = ((Integer)localObservableList.get(m)).intValue();
/* 1105 */               if (n > TreeView.TreeViewBitSetSelectionModel.this.getSelectedItems().size())
/*      */                 break;
/* 1107 */               TreeItem localTreeItem = (TreeItem)TreeView.TreeViewBitSetSelectionModel.this.getSelectedItems().get(n);
/* 1108 */               if ((localTreeItem == null) || (paramAnonymousTreeModificationEvent.getRemovedChildren().contains(localTreeItem))) {
/* 1109 */                 TreeView.TreeViewBitSetSelectionModel.this.clearSelection(n);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 1114 */         TreeView.TreeViewBitSetSelectionModel.this.shiftSelection(i, j);
/*      */       }
/* 1059 */     };
/*      */ 
/* 1118 */     private WeakChangeListener weakRootPropertyListener = new WeakChangeListener(this.rootPropertyListener);
/*      */     private WeakEventHandler weakTreeItemListener;
/*      */     private final TreeView<T> treeView;
/*      */ 
/*      */     public TreeViewBitSetSelectionModel(TreeView<T> paramTreeView)
/*      */     {
/* 1030 */       if (paramTreeView == null) {
/* 1031 */         throw new IllegalArgumentException("TreeView can not be null");
/*      */       }
/*      */ 
/* 1034 */       this.treeView = paramTreeView;
/* 1035 */       this.treeView.rootProperty().addListener(this.weakRootPropertyListener);
/*      */ 
/* 1037 */       updateTreeEventListener(null, paramTreeView.getRoot());
/*      */     }
/*      */ 
/*      */     private void updateTreeEventListener(TreeItem<T> paramTreeItem1, TreeItem<T> paramTreeItem2) {
/* 1041 */       if ((paramTreeItem1 != null) && (this.weakTreeItemListener != null)) {
/* 1042 */         paramTreeItem1.removeEventHandler(TreeItem.treeItemCountChangeEvent(), this.weakTreeItemListener);
/*      */       }
/*      */ 
/* 1045 */       if (paramTreeItem2 != null) {
/* 1046 */         this.weakTreeItemListener = new WeakEventHandler(paramTreeItem2, TreeItem.treeItemCountChangeEvent(), this.treeItemListener);
/* 1047 */         paramTreeItem2.addEventHandler(TreeItem.treeItemCountChangeEvent(), this.weakTreeItemListener);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void select(TreeItem<T> paramTreeItem)
/*      */     {
/* 1147 */       Object localObject = paramTreeItem;
/* 1148 */       while (localObject != null) {
/* 1149 */         ((TreeItem)localObject).setExpanded(true);
/* 1150 */         localObject = ((TreeItem)localObject).getParent();
/*      */       }
/*      */ 
/* 1155 */       this.treeView.updateTreeItemCount();
/*      */ 
/* 1160 */       int i = this.treeView.getRow(paramTreeItem);
/*      */ 
/* 1162 */       if (i == -1)
/*      */       {
/* 1168 */         setSelectedItem(paramTreeItem);
/*      */       }
/* 1170 */       else select(i);
/*      */     }
/*      */ 
/*      */     protected void focus(int paramInt)
/*      */     {
/* 1184 */       if (this.treeView.getFocusModel() != null)
/* 1185 */         this.treeView.getFocusModel().focus(paramInt);
/*      */     }
/*      */ 
/*      */     protected int getFocusedIndex()
/*      */     {
/* 1191 */       if (this.treeView.getFocusModel() == null) return -1;
/* 1192 */       return this.treeView.getFocusModel().getFocusedIndex();
/*      */     }
/*      */ 
/*      */     protected int getItemCount()
/*      */     {
/* 1197 */       return this.treeView == null ? 0 : this.treeView.impl_getTreeItemCount();
/*      */     }
/*      */ 
/*      */     public TreeItem<T> getModelItem(int paramInt)
/*      */     {
/* 1202 */       if (this.treeView == null) return null;
/*      */ 
/* 1204 */       if ((paramInt < 0) || (paramInt >= this.treeView.impl_getTreeItemCount())) return null;
/*      */ 
/* 1206 */       return this.treeView.getTreeItem(paramInt);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class EditEvent<T> extends Event
/*      */   {
/*      */     private final T oldValue;
/*      */     private final T newValue;
/*      */     private final transient TreeItem<T> treeItem;
/*      */ 
/*      */     public EditEvent(TreeView<T> paramTreeView, EventType<? extends EditEvent> paramEventType, TreeItem<T> paramTreeItem, T paramT1, T paramT2)
/*      */     {
/*  978 */       super(Event.NULL_SOURCE_TARGET, paramEventType);
/*  979 */       this.oldValue = paramT1;
/*  980 */       this.newValue = paramT2;
/*  981 */       this.treeItem = paramTreeItem;
/*      */     }
/*      */ 
/*      */     public TreeView<T> getSource()
/*      */     {
/*  988 */       return (TreeView)super.getSource();
/*      */     }
/*      */ 
/*      */     public TreeItem<T> getTreeItem()
/*      */     {
/*  995 */       return this.treeItem;
/*      */     }
/*      */ 
/*      */     public T getNewValue()
/*      */     {
/* 1002 */       return this.newValue;
/*      */     }
/*      */ 
/*      */     public T getOldValue()
/*      */     {
/* 1010 */       return this.oldValue;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TreeView
 * JD-Core Version:    0.6.2
 */