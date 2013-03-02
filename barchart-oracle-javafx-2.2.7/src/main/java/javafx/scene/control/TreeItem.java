/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.event.EventHandlerManager;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventDispatchChain;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class TreeItem<T>
/*     */   implements EventTarget
/*     */ {
/* 222 */   private static final EventType TREE_NOTIFICATION_EVENT = new EventType("TreeNotificationEvent");
/*     */ 
/* 239 */   private static final EventType TREE_ITEM_COUNT_CHANGE_EVENT = new EventType(treeNotificationEvent(), "TreeItemCountChangeEvent");
/*     */ 
/* 252 */   private static final EventType<?> BRANCH_EXPANDED_EVENT = new EventType(treeItemCountChangeEvent(), "BranchExpandedEvent");
/*     */ 
/* 265 */   private static final EventType<?> BRANCH_COLLAPSED_EVENT = new EventType(treeItemCountChangeEvent(), "BranchCollapsedEvent");
/*     */ 
/* 278 */   private static final EventType<?> CHILDREN_MODIFICATION_EVENT = new EventType(treeItemCountChangeEvent(), "ChildrenModificationEvent");
/*     */ 
/* 291 */   private static final EventType<?> VALUE_CHANGED_EVENT = new EventType(treeNotificationEvent(), "ValueChangedEvent");
/*     */ 
/* 304 */   private static final EventType<?> GRAPHIC_CHANGED_EVENT = new EventType(treeNotificationEvent(), "GraphicChangedEvent");
/*     */ 
/* 345 */   private final EventHandler<TreeModificationEvent<Object>> itemListener = new EventHandler()
/*     */   {
/*     */     public void handle(TreeItem.TreeModificationEvent paramAnonymousTreeModificationEvent) {
/* 348 */       TreeItem.this.expandedDescendentCountDirty = true;
/*     */     }
/* 345 */   };
/*     */ 
/* 359 */   private boolean expandedDescendentCountDirty = true;
/*     */   private ObservableList<TreeItem<T>> children;
/* 369 */   private final EventHandlerManager eventHandlerManager = new EventHandlerManager(this);
/*     */ 
/* 376 */   private int expandedDescendentCount = 1;
/*     */ 
/* 383 */   int previousExpandedDescendentCount = 1;
/*     */ 
/* 393 */   private ListChangeListener<TreeItem<T>> childrenListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change<? extends TreeItem<T>> paramAnonymousChange) {
/* 395 */       TreeItem.this.expandedDescendentCountDirty = true;
/* 396 */       while (paramAnonymousChange.next())
/* 397 */         TreeItem.this.updateChildren(paramAnonymousChange.getAddedSubList(), paramAnonymousChange.getRemoved());
/*     */     }
/* 393 */   };
/*     */   private ObjectProperty<T> value;
/*     */   private ObjectProperty<Node> graphic;
/*     */   private BooleanProperty expanded;
/*     */   private ReadOnlyBooleanWrapper leaf;
/* 579 */   private ReadOnlyObjectWrapper<TreeItem<T>> parent = new ReadOnlyObjectWrapper(this, "parent");
/*     */ 
/*     */   public static <T> EventType<TreeModificationEvent<T>> treeNotificationEvent()
/*     */   {
/* 220 */     return TREE_NOTIFICATION_EVENT;
/*     */   }
/*     */ 
/*     */   public static <T> EventType<TreeModificationEvent<T>> treeItemCountChangeEvent()
/*     */   {
/* 237 */     return TREE_ITEM_COUNT_CHANGE_EVENT;
/*     */   }
/*     */ 
/*     */   public static <T> EventType<TreeModificationEvent<T>> branchExpandedEvent()
/*     */   {
/* 250 */     return BRANCH_EXPANDED_EVENT;
/*     */   }
/*     */ 
/*     */   public static <T> EventType<TreeModificationEvent<T>> branchCollapsedEvent()
/*     */   {
/* 263 */     return BRANCH_COLLAPSED_EVENT;
/*     */   }
/*     */ 
/*     */   public static <T> EventType<TreeModificationEvent<T>> childrenModificationEvent()
/*     */   {
/* 276 */     return CHILDREN_MODIFICATION_EVENT;
/*     */   }
/*     */ 
/*     */   public static <T> EventType<TreeModificationEvent<T>> valueChangedEvent()
/*     */   {
/* 289 */     return VALUE_CHANGED_EVENT;
/*     */   }
/*     */ 
/*     */   public static <T> EventType<TreeModificationEvent<T>> graphicChangedEvent()
/*     */   {
/* 302 */     return GRAPHIC_CHANGED_EVENT;
/*     */   }
/*     */ 
/*     */   public TreeItem()
/*     */   {
/* 319 */     this(null);
/*     */   }
/*     */ 
/*     */   public TreeItem(T paramT)
/*     */   {
/* 328 */     this(paramT, (Node)null);
/*     */   }
/*     */ 
/*     */   public TreeItem(T paramT, Node paramNode)
/*     */   {
/* 339 */     setValue(paramT);
/* 340 */     setGraphic(paramNode);
/*     */ 
/* 342 */     addEventHandler(treeItemCountChangeEvent(), this.itemListener);
/*     */   }
/*     */ 
/*     */   public final void setValue(T paramT)
/*     */   {
/* 416 */     valueProperty().setValue(paramT);
/*     */   }
/*     */ 
/*     */   public final T getValue()
/*     */   {
/* 422 */     return this.value == null ? null : this.value.getValue();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<T> valueProperty()
/*     */   {
/* 429 */     if (this.value == null) {
/* 430 */       this.value = new ObjectPropertyBase() {
/*     */         protected void invalidated() {
/* 432 */           TreeItem.this.fireEvent(new TreeItem.TreeModificationEvent(TreeItem.VALUE_CHANGED_EVENT, TreeItem.this, get()));
/*     */         }
/*     */ 
/*     */         public Object getBean() {
/* 436 */           return TreeItem.this;
/*     */         }
/*     */ 
/*     */         public String getName() {
/* 440 */           return "value";
/*     */         }
/*     */       };
/*     */     }
/* 444 */     return this.value;
/*     */   }
/*     */ 
/*     */   public final void setGraphic(Node paramNode)
/*     */   {
/* 457 */     graphicProperty().setValue(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getGraphic()
/*     */   {
/* 465 */     return this.graphic == null ? null : (Node)this.graphic.getValue();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> graphicProperty()
/*     */   {
/* 472 */     if (this.graphic == null) {
/* 473 */       this.graphic = new ObjectPropertyBase() {
/*     */         protected void invalidated() {
/* 475 */           TreeItem.this.fireEvent(new TreeItem.TreeModificationEvent(TreeItem.GRAPHIC_CHANGED_EVENT, TreeItem.this));
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 480 */           return TreeItem.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 485 */           return "graphic";
/*     */         }
/*     */       };
/*     */     }
/* 489 */     return this.graphic;
/*     */   }
/*     */ 
/*     */   public final void setExpanded(boolean paramBoolean)
/*     */   {
/* 508 */     if ((!paramBoolean) && (this.expanded == null)) return;
/* 509 */     expandedProperty().setValue(Boolean.valueOf(paramBoolean));
/*     */   }
/*     */ 
/*     */   public final boolean isExpanded()
/*     */   {
/* 517 */     return this.expanded == null ? false : this.expanded.getValue().booleanValue();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty expandedProperty()
/*     */   {
/* 523 */     if (this.expanded == null) {
/* 524 */       this.expanded = new BooleanPropertyBase() {
/*     */         protected void invalidated() {
/* 526 */           EventType localEventType = TreeItem.this.isExpanded() ? TreeItem.BRANCH_EXPANDED_EVENT : TreeItem.BRANCH_COLLAPSED_EVENT;
/*     */ 
/* 529 */           TreeItem.this.fireEvent(new TreeItem.TreeModificationEvent(localEventType, TreeItem.this, TreeItem.this.isExpanded()));
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 534 */           return TreeItem.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 539 */           return "expanded";
/*     */         }
/*     */       };
/*     */     }
/* 543 */     return this.expanded;
/*     */   }
/*     */ 
/*     */   private void setLeaf(boolean paramBoolean)
/*     */   {
/* 550 */     if ((paramBoolean) && (this.leaf == null))
/* 551 */       return;
/* 552 */     if (this.leaf == null) {
/* 553 */       this.leaf = new ReadOnlyBooleanWrapper(this, "leaf", true);
/*     */     }
/* 555 */     this.leaf.setValue(Boolean.valueOf(paramBoolean));
/*     */   }
/*     */ 
/*     */   public boolean isLeaf()
/*     */   {
/* 565 */     return this.leaf == null ? true : this.leaf.getValue().booleanValue();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyBooleanProperty leafProperty()
/*     */   {
/* 571 */     if (this.leaf == null) {
/* 572 */       this.leaf = new ReadOnlyBooleanWrapper(this, "leaf", true);
/*     */     }
/* 574 */     return this.leaf.getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private void setParent(TreeItem<T> paramTreeItem)
/*     */   {
/* 580 */     this.parent.setValue(paramTreeItem);
/*     */   }
/*     */ 
/*     */   public final TreeItem<T> getParent()
/*     */   {
/* 588 */     return this.parent == null ? null : (TreeItem)this.parent.getValue();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<TreeItem<T>> parentProperty()
/*     */   {
/* 593 */     return this.parent.getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   public ObservableList<TreeItem<T>> getChildren()
/*     */   {
/* 611 */     if (this.children == null) {
/* 612 */       this.children = FXCollections.observableArrayList();
/* 613 */       this.children.addListener(this.childrenListener);
/*     */     }
/* 615 */     return this.children;
/*     */   }
/*     */ 
/*     */   public TreeItem<T> previousSibling()
/*     */   {
/* 635 */     return previousSibling(this);
/*     */   }
/*     */ 
/*     */   public TreeItem<T> previousSibling(TreeItem<T> paramTreeItem)
/*     */   {
/* 649 */     if ((getParent() == null) || (paramTreeItem == null)) {
/* 650 */       return null;
/*     */     }
/*     */ 
/* 653 */     ObservableList localObservableList = getParent().getChildren();
/* 654 */     int i = localObservableList.size();
/* 655 */     int j = -1;
/* 656 */     for (int k = 0; k < i; k++) {
/* 657 */       if (paramTreeItem.equals(localObservableList.get(k))) {
/* 658 */         j = k - 1;
/* 659 */         return j < 0 ? null : (TreeItem)localObservableList.get(j);
/*     */       }
/*     */     }
/* 662 */     return null;
/*     */   }
/*     */ 
/*     */   public TreeItem<T> nextSibling()
/*     */   {
/* 674 */     return nextSibling(this);
/*     */   }
/*     */ 
/*     */   public TreeItem<T> nextSibling(TreeItem<T> paramTreeItem)
/*     */   {
/* 688 */     if ((getParent() == null) || (paramTreeItem == null)) {
/* 689 */       return null;
/*     */     }
/*     */ 
/* 692 */     ObservableList localObservableList = getParent().getChildren();
/* 693 */     int i = localObservableList.size();
/* 694 */     int j = -1;
/* 695 */     for (int k = 0; k < i; k++) {
/* 696 */       if (paramTreeItem.equals(localObservableList.get(k))) {
/* 697 */         j = k + 1;
/* 698 */         return j >= i ? null : (TreeItem)localObservableList.get(j);
/*     */       }
/*     */     }
/* 701 */     return null;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 709 */     return "TreeItem [ value: " + getValue() + " ]";
/*     */   }
/*     */ 
/*     */   private void fireEvent(TreeModificationEvent paramTreeModificationEvent) {
/* 713 */     Event.fireEvent(this, paramTreeModificationEvent);
/*     */   }
/*     */ 
/*     */   public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain)
/*     */   {
/* 729 */     if (getParent() != null) {
/* 730 */       getParent().buildEventDispatchChain(paramEventDispatchChain);
/*     */     }
/* 732 */     return paramEventDispatchChain.append(this.eventHandlerManager);
/*     */   }
/*     */ 
/*     */   public <E extends Event> void addEventHandler(EventType<E> paramEventType, EventHandler<E> paramEventHandler)
/*     */   {
/* 749 */     this.eventHandlerManager.addEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   public <E extends Event> void removeEventHandler(EventType<E> paramEventType, EventHandler<E> paramEventHandler)
/*     */   {
/* 763 */     this.eventHandlerManager.removeEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   int getExpandedDescendentCount(boolean paramBoolean)
/*     */   {
/* 776 */     if ((paramBoolean) || (this.expandedDescendentCountDirty)) {
/* 777 */       updateExpandedDescendentCount(paramBoolean);
/* 778 */       this.expandedDescendentCountDirty = false;
/*     */     }
/* 780 */     return this.expandedDescendentCount;
/*     */   }
/*     */ 
/*     */   private void updateExpandedDescendentCount(boolean paramBoolean) {
/* 784 */     this.previousExpandedDescendentCount = this.expandedDescendentCount;
/* 785 */     this.expandedDescendentCount = 1;
/*     */ 
/* 787 */     if ((!isLeaf()) && (isExpanded()))
/* 788 */       for (TreeItem localTreeItem : getChildren())
/* 789 */         if (localTreeItem != null)
/* 790 */           this.expandedDescendentCount += (localTreeItem.isExpanded() ? localTreeItem.getExpandedDescendentCount(paramBoolean) : 1);
/*     */   }
/*     */ 
/*     */   private void updateChildren(List<? extends TreeItem<T>> paramList1, List<? extends TreeItem<T>> paramList2)
/*     */   {
/* 796 */     setLeaf(this.children.isEmpty());
/*     */ 
/* 800 */     updateChildrenParent(paramList2, null);
/* 801 */     updateChildrenParent(paramList1, this);
/*     */ 
/* 805 */     fireEvent(new TreeModificationEvent(CHILDREN_MODIFICATION_EVENT, this, paramList1, paramList2));
/*     */   }
/*     */ 
/*     */   private static <T> void updateChildrenParent(List<? extends TreeItem<T>> paramList, TreeItem<T> paramTreeItem)
/*     */   {
/* 812 */     if (paramList == null) return;
/* 813 */     for (TreeItem localTreeItem : paramList)
/* 814 */       if (localTreeItem != null)
/* 815 */         localTreeItem.setParent(paramTreeItem);
/*     */   }
/*     */ 
/*     */   public static class TreeModificationEvent<T> extends Event
/*     */   {
/*     */     private final transient TreeItem<T> treeItem;
/*     */     private final T newValue;
/*     */     private final List<? extends TreeItem<T>> added;
/*     */     private final List<? extends TreeItem<T>> removed;
/*     */     private final boolean wasExpanded;
/*     */     private final boolean wasCollapsed;
/*     */ 
/*     */     public TreeModificationEvent(EventType<? extends Event> paramEventType, TreeItem<T> paramTreeItem)
/*     */     {
/* 846 */       this(paramEventType, paramTreeItem, null);
/*     */     }
/*     */ 
/*     */     public TreeModificationEvent(EventType<? extends Event> paramEventType, TreeItem<T> paramTreeItem, T paramT)
/*     */     {
/* 860 */       super();
/* 861 */       this.treeItem = paramTreeItem;
/* 862 */       this.newValue = paramT;
/* 863 */       this.added = null;
/* 864 */       this.removed = null;
/* 865 */       this.wasExpanded = false;
/* 866 */       this.wasCollapsed = false;
/*     */     }
/*     */ 
/*     */     public TreeModificationEvent(EventType<? extends Event> paramEventType, TreeItem<T> paramTreeItem, boolean paramBoolean)
/*     */     {
/* 880 */       super();
/* 881 */       this.treeItem = paramTreeItem;
/* 882 */       this.newValue = null;
/* 883 */       this.added = null;
/* 884 */       this.removed = null;
/* 885 */       this.wasExpanded = paramBoolean;
/* 886 */       this.wasCollapsed = (!paramBoolean);
/*     */     }
/*     */ 
/*     */     public TreeModificationEvent(EventType<? extends Event> paramEventType, TreeItem<T> paramTreeItem, List<? extends TreeItem<T>> paramList1, List<? extends TreeItem<T>> paramList2)
/*     */     {
/* 903 */       super();
/* 904 */       this.treeItem = paramTreeItem;
/* 905 */       this.newValue = null;
/* 906 */       this.added = paramList1;
/* 907 */       this.removed = paramList2;
/* 908 */       this.wasExpanded = false;
/* 909 */       this.wasCollapsed = false;
/*     */     }
/*     */ 
/*     */     public TreeItem getSource()
/*     */     {
/* 916 */       return this.treeItem;
/*     */     }
/*     */ 
/*     */     public TreeItem<T> getTreeItem()
/*     */     {
/* 924 */       return this.treeItem;
/*     */     }
/*     */ 
/*     */     public T getNewValue()
/*     */     {
/* 933 */       return this.newValue;
/*     */     }
/*     */ 
/*     */     public List<? extends TreeItem<T>> getAddedChildren()
/*     */     {
/* 943 */       return this.added == null ? Collections.emptyList() : this.added;
/*     */     }
/*     */ 
/*     */     public List<? extends TreeItem<T>> getRemovedChildren()
/*     */     {
/* 953 */       return this.removed == null ? Collections.emptyList() : this.removed;
/*     */     }
/*     */ 
/*     */     public int getRemovedSize()
/*     */     {
/* 963 */       return getRemovedChildren().size();
/*     */     }
/*     */ 
/*     */     public int getAddedSize()
/*     */     {
/* 973 */       return getAddedChildren().size();
/*     */     }
/*     */ 
/*     */     public boolean wasExpanded()
/*     */     {
/* 980 */       return this.wasExpanded;
/*     */     }
/*     */ 
/*     */     public boolean wasCollapsed()
/*     */     {
/* 986 */       return this.wasCollapsed;
/*     */     }
/*     */ 
/*     */     public boolean wasAdded()
/*     */     {
/* 992 */       return getAddedSize() > 0;
/*     */     }
/*     */ 
/*     */     public boolean wasRemoved()
/*     */     {
/* 998 */       return getRemovedSize() > 0;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TreeItem
 * JD-Core Version:    0.6.2
 */