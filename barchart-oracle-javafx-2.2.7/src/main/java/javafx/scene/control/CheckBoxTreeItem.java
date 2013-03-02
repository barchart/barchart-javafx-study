/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class CheckBoxTreeItem<T> extends TreeItem<T>
/*     */ {
/*  99 */   private static final EventType<?> CHECK_BOX_SELECTION_CHANGED_EVENT = new EventType("checkBoxSelectionChangedEvent");
/*     */ 
/* 180 */   private final ChangeListener<Boolean> stateChangeListener = new ChangeListener() {
/*     */     public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 182 */       CheckBoxTreeItem.this.updateState();
/*     */     }
/* 180 */   };
/*     */ 
/* 194 */   private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected", false) {
/*     */     protected void invalidated() {
/* 196 */       super.invalidated();
/* 197 */       CheckBoxTreeItem.this.fireEvent(CheckBoxTreeItem.this, true);
/*     */     }
/* 194 */   };
/*     */ 
/* 209 */   private final BooleanProperty indeterminate = new SimpleBooleanProperty(this, "indeterminate", false) {
/*     */     protected void invalidated() {
/* 211 */       super.invalidated();
/* 212 */       CheckBoxTreeItem.this.fireEvent(CheckBoxTreeItem.this, false);
/*     */     }
/* 209 */   };
/*     */ 
/* 236 */   private final BooleanProperty independent = new SimpleBooleanProperty(this, "independent", false);
/*     */ 
/* 248 */   private static boolean updateLock = false;
/*     */ 
/*     */   public static <T> EventType<TreeModificationEvent<T>> checkBoxSelectionChangedEvent()
/*     */   {
/*  97 */     return CHECK_BOX_SELECTION_CHANGED_EVENT;
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeItem()
/*     */   {
/* 112 */     this(null);
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeItem(T paramT)
/*     */   {
/* 122 */     this(paramT, null, false);
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeItem(T paramT, Node paramNode)
/*     */   {
/* 133 */     this(paramT, paramNode, false);
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeItem(T paramT, Node paramNode, boolean paramBoolean)
/*     */   {
/* 147 */     this(paramT, paramNode, paramBoolean, false);
/*     */   }
/*     */ 
/*     */   public CheckBoxTreeItem(T paramT, Node paramNode, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 165 */     super(paramT, paramNode);
/* 166 */     setSelected(Boolean.valueOf(paramBoolean1));
/* 167 */     setIndependent(Boolean.valueOf(paramBoolean2));
/*     */ 
/* 169 */     selectedProperty().addListener(this.stateChangeListener);
/* 170 */     indeterminateProperty().addListener(this.stateChangeListener);
/*     */   }
/*     */ 
/*     */   public final void setSelected(Boolean paramBoolean)
/*     */   {
/* 201 */     selectedProperty().setValue(paramBoolean);
/*     */   }
/* 203 */   public final Boolean isSelected() { return Boolean.valueOf(this.selected == null ? false : this.selected.getValue().booleanValue()); } 
/*     */   public final BooleanProperty selectedProperty() {
/* 205 */     return this.selected;
/*     */   }
/*     */ 
/*     */   public final void setIndeterminate(Boolean paramBoolean)
/*     */   {
/* 216 */     indeterminateProperty().setValue(paramBoolean);
/*     */   }
/* 218 */   public final Boolean isIndeterminate() { return Boolean.valueOf(this.indeterminate == null ? false : this.indeterminate.getValue().booleanValue()); } 
/*     */   public final BooleanProperty indeterminateProperty() {
/* 220 */     return this.indeterminate;
/*     */   }
/*     */ 
/*     */   public final BooleanProperty independentProperty()
/*     */   {
/* 235 */     return this.independent;
/*     */   }
/* 237 */   public final void setIndependent(Boolean paramBoolean) { independentProperty().setValue(paramBoolean); } 
/* 238 */   public final Boolean isIndependent() { return Boolean.valueOf(this.independent == null ? false : this.independent.getValue().booleanValue()); }
/*     */ 
/*     */ 
/*     */   private void updateState()
/*     */   {
/* 251 */     if (isIndependent().booleanValue()) return;
/*     */ 
/* 253 */     int i = !updateLock ? 1 : 0;
/*     */ 
/* 256 */     updateLock = true;
/* 257 */     updateUpwards();
/*     */ 
/* 259 */     if (i != 0) updateLock = false;
/*     */ 
/* 262 */     if (updateLock) return;
/* 263 */     updateDownwards();
/*     */   }
/*     */ 
/*     */   private void updateUpwards() {
/* 267 */     if (!(getParent() instanceof CheckBoxTreeItem)) return;
/*     */ 
/* 269 */     CheckBoxTreeItem localCheckBoxTreeItem1 = (CheckBoxTreeItem)getParent();
/* 270 */     int i = 0;
/* 271 */     int j = 0;
/* 272 */     for (TreeItem localTreeItem : localCheckBoxTreeItem1.getChildren()) {
/* 273 */       if ((localTreeItem instanceof CheckBoxTreeItem))
/*     */       {
/* 275 */         CheckBoxTreeItem localCheckBoxTreeItem2 = (CheckBoxTreeItem)localTreeItem;
/*     */ 
/* 277 */         i += ((localCheckBoxTreeItem2.isSelected().booleanValue()) && (!localCheckBoxTreeItem2.isIndeterminate().booleanValue()) ? 1 : 0);
/* 278 */         j += (localCheckBoxTreeItem2.isIndeterminate().booleanValue() ? 1 : 0);
/*     */       }
/*     */     }
/* 281 */     if (i == localCheckBoxTreeItem1.getChildren().size()) {
/* 282 */       localCheckBoxTreeItem1.setSelected(Boolean.valueOf(true));
/* 283 */       localCheckBoxTreeItem1.setIndeterminate(Boolean.valueOf(false));
/* 284 */     } else if ((i == 0) && (j == 0)) {
/* 285 */       localCheckBoxTreeItem1.setSelected(Boolean.valueOf(false));
/* 286 */       localCheckBoxTreeItem1.setIndeterminate(Boolean.valueOf(false));
/*     */     } else {
/* 288 */       localCheckBoxTreeItem1.setIndeterminate(Boolean.valueOf(true));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateDownwards()
/*     */   {
/* 295 */     if (!isLeaf())
/* 296 */       for (TreeItem localTreeItem : getChildren())
/* 297 */         if ((localTreeItem instanceof CheckBoxTreeItem)) {
/* 298 */           CheckBoxTreeItem localCheckBoxTreeItem = (CheckBoxTreeItem)localTreeItem;
/* 299 */           localCheckBoxTreeItem.setSelected(isSelected());
/*     */         }
/*     */   }
/*     */ 
/*     */   private void fireEvent(CheckBoxTreeItem paramCheckBoxTreeItem, boolean paramBoolean)
/*     */   {
/* 306 */     TreeModificationEvent localTreeModificationEvent = new TreeModificationEvent(CHECK_BOX_SELECTION_CHANGED_EVENT, paramCheckBoxTreeItem, paramBoolean);
/* 307 */     Event.fireEvent(this, localTreeModificationEvent);
/*     */   }
/*     */ 
/*     */   public static class TreeModificationEvent<T> extends Event
/*     */   {
/*     */     private final CheckBoxTreeItem<T> treeItem;
/*     */     private final boolean selectionChanged;
/*     */ 
/*     */     public TreeModificationEvent(EventType<? extends Event> paramEventType, CheckBoxTreeItem<T> paramCheckBoxTreeItem, boolean paramBoolean)
/*     */     {
/* 331 */       super();
/* 332 */       this.treeItem = paramCheckBoxTreeItem;
/* 333 */       this.selectionChanged = paramBoolean;
/*     */     }
/*     */ 
/*     */     public CheckBoxTreeItem<T> getTreeItem()
/*     */     {
/* 341 */       return this.treeItem;
/*     */     }
/*     */ 
/*     */     public boolean wasSelectionChanged()
/*     */     {
/* 349 */       return this.selectionChanged;
/*     */     }
/*     */ 
/*     */     public boolean wasIndeterminateChanged()
/*     */     {
/* 358 */       return !this.selectionChanged;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.CheckBoxTreeItem
 * JD-Core Version:    0.6.2
 */