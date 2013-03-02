/*     */ package javafx.scene.control;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.Node;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class TableViewBuilder<S, B extends TableViewBuilder<S, B>> extends ControlBuilder<B>
/*     */   implements Builder<TableView<S>>
/*     */ {
/*     */   private int __set;
/*     */   private Callback<TableView.ResizeFeatures, Boolean> columnResizePolicy;
/*     */   private Collection<? extends TableColumn<S, ?>> columns;
/*     */   private boolean editable;
/*     */   private TableView.TableViewFocusModel<S> focusModel;
/*     */   private ObservableList<S> items;
/*     */   private Node placeholder;
/*     */   private Callback<TableView<S>, TableRow<S>> rowFactory;
/*     */   private TableView.TableViewSelectionModel<S> selectionModel;
/*     */   private Collection<? extends TableColumn<S, ?>> sortOrder;
/*     */   private boolean tableMenuButtonVisible;
/*     */ 
/*     */   public static <S> TableViewBuilder<S, ?> create()
/*     */   {
/*  15 */     return new TableViewBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(TableView<S> paramTableView) {
/*  23 */     super.applyTo(paramTableView);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramTableView.setColumnResizePolicy(this.columnResizePolicy); break;
/*     */       case 1:
/*  30 */         paramTableView.getColumns().addAll(this.columns); break;
/*     */       case 2:
/*  31 */         paramTableView.setEditable(this.editable); break;
/*     */       case 3:
/*  32 */         paramTableView.setFocusModel(this.focusModel); break;
/*     */       case 4:
/*  33 */         paramTableView.setItems(this.items); break;
/*     */       case 5:
/*  34 */         paramTableView.setPlaceholder(this.placeholder); break;
/*     */       case 6:
/*  35 */         paramTableView.setRowFactory(this.rowFactory); break;
/*     */       case 7:
/*  36 */         paramTableView.setSelectionModel(this.selectionModel); break;
/*     */       case 8:
/*  37 */         paramTableView.getSortOrder().addAll(this.sortOrder); break;
/*     */       case 9:
/*  38 */         paramTableView.setTableMenuButtonVisible(this.tableMenuButtonVisible);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B columnResizePolicy(Callback<TableView.ResizeFeatures, Boolean> paramCallback)
/*     */   {
/*  49 */     this.columnResizePolicy = paramCallback;
/*  50 */     __set(0);
/*  51 */     return this;
/*     */   }
/*     */ 
/*     */   public B columns(Collection<? extends TableColumn<S, ?>> paramCollection)
/*     */   {
/*  60 */     this.columns = paramCollection;
/*  61 */     __set(1);
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public B columns(TableColumn<S, ?>[] paramArrayOfTableColumn)
/*     */   {
/*  69 */     return columns(Arrays.asList(paramArrayOfTableColumn));
/*     */   }
/*     */ 
/*     */   public B editable(boolean paramBoolean)
/*     */   {
/*  78 */     this.editable = paramBoolean;
/*  79 */     __set(2);
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public B focusModel(TableView.TableViewFocusModel<S> paramTableViewFocusModel)
/*     */   {
/*  89 */     this.focusModel = paramTableViewFocusModel;
/*  90 */     __set(3);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   public B items(ObservableList<S> paramObservableList)
/*     */   {
/* 100 */     this.items = paramObservableList;
/* 101 */     __set(4);
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   public B placeholder(Node paramNode)
/*     */   {
/* 111 */     this.placeholder = paramNode;
/* 112 */     __set(5);
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   public B rowFactory(Callback<TableView<S>, TableRow<S>> paramCallback)
/*     */   {
/* 122 */     this.rowFactory = paramCallback;
/* 123 */     __set(6);
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   public B selectionModel(TableView.TableViewSelectionModel<S> paramTableViewSelectionModel)
/*     */   {
/* 133 */     this.selectionModel = paramTableViewSelectionModel;
/* 134 */     __set(7);
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   public B sortOrder(Collection<? extends TableColumn<S, ?>> paramCollection)
/*     */   {
/* 144 */     this.sortOrder = paramCollection;
/* 145 */     __set(8);
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   public B sortOrder(TableColumn<S, ?>[] paramArrayOfTableColumn)
/*     */   {
/* 153 */     return sortOrder(Arrays.asList(paramArrayOfTableColumn));
/*     */   }
/*     */ 
/*     */   public B tableMenuButtonVisible(boolean paramBoolean)
/*     */   {
/* 162 */     this.tableMenuButtonVisible = paramBoolean;
/* 163 */     __set(9);
/* 164 */     return this;
/*     */   }
/*     */ 
/*     */   public TableView<S> build()
/*     */   {
/* 171 */     TableView localTableView = new TableView();
/* 172 */     applyTo(localTableView);
/* 173 */     return localTableView;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TableViewBuilder
 * JD-Core Version:    0.6.2
 */