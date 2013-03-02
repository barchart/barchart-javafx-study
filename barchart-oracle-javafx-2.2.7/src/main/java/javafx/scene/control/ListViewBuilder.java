/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class ListViewBuilder<T, B extends ListViewBuilder<T, B>> extends ControlBuilder<B>
/*     */   implements Builder<ListView<T>>
/*     */ {
/*     */   private int __set;
/*     */   private Callback<ListView<T>, ListCell<T>> cellFactory;
/*     */   private boolean editable;
/*     */   private FocusModel<T> focusModel;
/*     */   private ObservableList<T> items;
/*     */   private EventHandler<ListView.EditEvent<T>> onEditCancel;
/*     */   private EventHandler<ListView.EditEvent<T>> onEditCommit;
/*     */   private EventHandler<ListView.EditEvent<T>> onEditStart;
/*     */   private Orientation orientation;
/*     */   private MultipleSelectionModel<T> selectionModel;
/*     */ 
/*     */   public static <T> ListViewBuilder<T, ?> create()
/*     */   {
/*  15 */     return new ListViewBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(ListView<T> paramListView) {
/*  23 */     super.applyTo(paramListView);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramListView.setCellFactory(this.cellFactory); break;
/*     */       case 1:
/*  30 */         paramListView.setEditable(this.editable); break;
/*     */       case 2:
/*  31 */         paramListView.setFocusModel(this.focusModel); break;
/*     */       case 3:
/*  32 */         paramListView.setItems(this.items); break;
/*     */       case 4:
/*  33 */         paramListView.setOnEditCancel(this.onEditCancel); break;
/*     */       case 5:
/*  34 */         paramListView.setOnEditCommit(this.onEditCommit); break;
/*     */       case 6:
/*  35 */         paramListView.setOnEditStart(this.onEditStart); break;
/*     */       case 7:
/*  36 */         paramListView.setOrientation(this.orientation); break;
/*     */       case 8:
/*  37 */         paramListView.setSelectionModel(this.selectionModel);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B cellFactory(Callback<ListView<T>, ListCell<T>> paramCallback)
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
/*     */   public B focusModel(FocusModel<T> paramFocusModel)
/*     */   {
/*  70 */     this.focusModel = paramFocusModel;
/*  71 */     __set(2);
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public B items(ObservableList<T> paramObservableList)
/*     */   {
/*  81 */     this.items = paramObservableList;
/*  82 */     __set(3);
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public B onEditCancel(EventHandler<ListView.EditEvent<T>> paramEventHandler)
/*     */   {
/*  92 */     this.onEditCancel = paramEventHandler;
/*  93 */     __set(4);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public B onEditCommit(EventHandler<ListView.EditEvent<T>> paramEventHandler)
/*     */   {
/* 103 */     this.onEditCommit = paramEventHandler;
/* 104 */     __set(5);
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public B onEditStart(EventHandler<ListView.EditEvent<T>> paramEventHandler)
/*     */   {
/* 114 */     this.onEditStart = paramEventHandler;
/* 115 */     __set(6);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public B orientation(Orientation paramOrientation)
/*     */   {
/* 125 */     this.orientation = paramOrientation;
/* 126 */     __set(7);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public B selectionModel(MultipleSelectionModel<T> paramMultipleSelectionModel)
/*     */   {
/* 136 */     this.selectionModel = paramMultipleSelectionModel;
/* 137 */     __set(8);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   public ListView<T> build()
/*     */   {
/* 145 */     ListView localListView = new ListView();
/* 146 */     applyTo(localListView);
/* 147 */     return localListView;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ListViewBuilder
 * JD-Core Version:    0.6.2
 */