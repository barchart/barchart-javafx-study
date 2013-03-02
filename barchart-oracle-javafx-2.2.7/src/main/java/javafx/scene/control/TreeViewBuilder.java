/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class TreeViewBuilder<T, B extends TreeViewBuilder<T, B>> extends ControlBuilder<B>
/*     */   implements Builder<TreeView<T>>
/*     */ {
/*     */   private int __set;
/*     */   private Callback<TreeView<T>, TreeCell<T>> cellFactory;
/*     */   private boolean editable;
/*     */   private FocusModel<TreeItem<T>> focusModel;
/*     */   private EventHandler<TreeView.EditEvent<T>> onEditCancel;
/*     */   private EventHandler<TreeView.EditEvent<T>> onEditCommit;
/*     */   private EventHandler<TreeView.EditEvent<T>> onEditStart;
/*     */   private TreeItem<T> root;
/*     */   private MultipleSelectionModel<TreeItem<T>> selectionModel;
/*     */   private boolean showRoot;
/*     */ 
/*     */   public static <T> TreeViewBuilder<T, ?> create()
/*     */   {
/*  15 */     return new TreeViewBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(TreeView<T> paramTreeView) {
/*  23 */     super.applyTo(paramTreeView);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramTreeView.setCellFactory(this.cellFactory); break;
/*     */       case 1:
/*  30 */         paramTreeView.setEditable(this.editable); break;
/*     */       case 2:
/*  31 */         paramTreeView.setFocusModel(this.focusModel); break;
/*     */       case 3:
/*  32 */         paramTreeView.setOnEditCancel(this.onEditCancel); break;
/*     */       case 4:
/*  33 */         paramTreeView.setOnEditCommit(this.onEditCommit); break;
/*     */       case 5:
/*  34 */         paramTreeView.setOnEditStart(this.onEditStart); break;
/*     */       case 6:
/*  35 */         paramTreeView.setRoot(this.root); break;
/*     */       case 7:
/*  36 */         paramTreeView.setSelectionModel(this.selectionModel); break;
/*     */       case 8:
/*  37 */         paramTreeView.setShowRoot(this.showRoot);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B cellFactory(Callback<TreeView<T>, TreeCell<T>> paramCallback)
/*     */   {
/*  48 */     this.cellFactory = paramCallback;
/*  49 */     __set(0);
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   public B editable(boolean paramBoolean)
/*     */   {
/*  59 */     this.editable = paramBoolean;
/*  60 */     __set(1);
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   public B focusModel(FocusModel<TreeItem<T>> paramFocusModel)
/*     */   {
/*  70 */     this.focusModel = paramFocusModel;
/*  71 */     __set(2);
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public B onEditCancel(EventHandler<TreeView.EditEvent<T>> paramEventHandler)
/*     */   {
/*  81 */     this.onEditCancel = paramEventHandler;
/*  82 */     __set(3);
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public B onEditCommit(EventHandler<TreeView.EditEvent<T>> paramEventHandler)
/*     */   {
/*  92 */     this.onEditCommit = paramEventHandler;
/*  93 */     __set(4);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public B onEditStart(EventHandler<TreeView.EditEvent<T>> paramEventHandler)
/*     */   {
/* 103 */     this.onEditStart = paramEventHandler;
/* 104 */     __set(5);
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public B root(TreeItem<T> paramTreeItem)
/*     */   {
/* 114 */     this.root = paramTreeItem;
/* 115 */     __set(6);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public B selectionModel(MultipleSelectionModel<TreeItem<T>> paramMultipleSelectionModel)
/*     */   {
/* 125 */     this.selectionModel = paramMultipleSelectionModel;
/* 126 */     __set(7);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public B showRoot(boolean paramBoolean)
/*     */   {
/* 136 */     this.showRoot = paramBoolean;
/* 137 */     __set(8);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   public TreeView<T> build()
/*     */   {
/* 145 */     TreeView localTreeView = new TreeView();
/* 146 */     applyTo(localTreeView);
/* 147 */     return localTreeView;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TreeViewBuilder
 * JD-Core Version:    0.6.2
 */