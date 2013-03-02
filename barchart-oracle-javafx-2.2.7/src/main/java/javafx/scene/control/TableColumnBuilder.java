/*     */ package javafx.scene.control;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Node;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class TableColumnBuilder<S, T, B extends TableColumnBuilder<S, T, B>>
/*     */   implements Builder<TableColumn<S, T>>
/*     */ {
/*     */   private int __set;
/*     */   private Callback<TableColumn<S, T>, TableCell<S, T>> cellFactory;
/*     */   private Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> cellValueFactory;
/*     */   private Collection<? extends TableColumn<S, ?>> columns;
/*     */   private Comparator<T> comparator;
/*     */   private ContextMenu contextMenu;
/*     */   private boolean editable;
/*     */   private Node graphic;
/*     */   private String id;
/*     */   private double maxWidth;
/*     */   private double minWidth;
/*     */   private EventHandler<TableColumn.CellEditEvent<S, T>> onEditCancel;
/*     */   private EventHandler<TableColumn.CellEditEvent<S, T>> onEditCommit;
/*     */   private EventHandler<TableColumn.CellEditEvent<S, T>> onEditStart;
/*     */   private double prefWidth;
/*     */   private boolean resizable;
/*     */   private boolean sortable;
/*     */   private Node sortNode;
/*     */   private TableColumn.SortType sortType;
/*     */   private String style;
/*     */   private Collection<? extends String> styleClass;
/*     */   private String text;
/*     */   private Object userData;
/*     */   private boolean visible;
/*     */ 
/*     */   public static <S, T> TableColumnBuilder<S, T, ?> create()
/*     */   {
/*  15 */     return new TableColumnBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(TableColumn<S, T> paramTableColumn) {
/*  23 */     int i = this.__set;
/*  24 */     while (i != 0) {
/*  25 */       int j = Integer.numberOfTrailingZeros(i);
/*  26 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  27 */       switch (j) { case 0:
/*  28 */         paramTableColumn.setCellFactory(this.cellFactory); break;
/*     */       case 1:
/*  29 */         paramTableColumn.setCellValueFactory(this.cellValueFactory); break;
/*     */       case 2:
/*  30 */         paramTableColumn.getColumns().addAll(this.columns); break;
/*     */       case 3:
/*  31 */         paramTableColumn.setComparator(this.comparator); break;
/*     */       case 4:
/*  32 */         paramTableColumn.setContextMenu(this.contextMenu); break;
/*     */       case 5:
/*  33 */         paramTableColumn.setEditable(this.editable); break;
/*     */       case 6:
/*  34 */         paramTableColumn.setGraphic(this.graphic); break;
/*     */       case 7:
/*  35 */         paramTableColumn.setId(this.id); break;
/*     */       case 8:
/*  36 */         paramTableColumn.setMaxWidth(this.maxWidth); break;
/*     */       case 9:
/*  37 */         paramTableColumn.setMinWidth(this.minWidth); break;
/*     */       case 10:
/*  38 */         paramTableColumn.setOnEditCancel(this.onEditCancel); break;
/*     */       case 11:
/*  39 */         paramTableColumn.setOnEditCommit(this.onEditCommit); break;
/*     */       case 12:
/*  40 */         paramTableColumn.setOnEditStart(this.onEditStart); break;
/*     */       case 13:
/*  41 */         paramTableColumn.setPrefWidth(this.prefWidth); break;
/*     */       case 14:
/*  42 */         paramTableColumn.setResizable(this.resizable); break;
/*     */       case 15:
/*  43 */         paramTableColumn.setSortable(this.sortable); break;
/*     */       case 16:
/*  44 */         paramTableColumn.setSortNode(this.sortNode); break;
/*     */       case 17:
/*  45 */         paramTableColumn.setSortType(this.sortType); break;
/*     */       case 18:
/*  46 */         paramTableColumn.setStyle(this.style); break;
/*     */       case 19:
/*  47 */         paramTableColumn.getStyleClass().addAll(this.styleClass); break;
/*     */       case 20:
/*  48 */         paramTableColumn.setText(this.text); break;
/*     */       case 21:
/*  49 */         paramTableColumn.setUserData(this.userData); break;
/*     */       case 22:
/*  50 */         paramTableColumn.setVisible(this.visible);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B cellFactory(Callback<TableColumn<S, T>, TableCell<S, T>> paramCallback)
/*     */   {
/*  61 */     this.cellFactory = paramCallback;
/*  62 */     __set(0);
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   public B cellValueFactory(Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> paramCallback)
/*     */   {
/*  72 */     this.cellValueFactory = paramCallback;
/*  73 */     __set(1);
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   public B columns(Collection<? extends TableColumn<S, ?>> paramCollection)
/*     */   {
/*  83 */     this.columns = paramCollection;
/*  84 */     __set(2);
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public B columns(TableColumn<S, ?>[] paramArrayOfTableColumn)
/*     */   {
/*  92 */     return columns(Arrays.asList(paramArrayOfTableColumn));
/*     */   }
/*     */ 
/*     */   public B comparator(Comparator<T> paramComparator)
/*     */   {
/* 101 */     this.comparator = paramComparator;
/* 102 */     __set(3);
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   public B contextMenu(ContextMenu paramContextMenu)
/*     */   {
/* 112 */     this.contextMenu = paramContextMenu;
/* 113 */     __set(4);
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public B editable(boolean paramBoolean)
/*     */   {
/* 123 */     this.editable = paramBoolean;
/* 124 */     __set(5);
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   public B graphic(Node paramNode)
/*     */   {
/* 134 */     this.graphic = paramNode;
/* 135 */     __set(6);
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   public B id(String paramString)
/*     */   {
/* 145 */     this.id = paramString;
/* 146 */     __set(7);
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   public B maxWidth(double paramDouble)
/*     */   {
/* 156 */     this.maxWidth = paramDouble;
/* 157 */     __set(8);
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   public B minWidth(double paramDouble)
/*     */   {
/* 167 */     this.minWidth = paramDouble;
/* 168 */     __set(9);
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   public B onEditCancel(EventHandler<TableColumn.CellEditEvent<S, T>> paramEventHandler)
/*     */   {
/* 178 */     this.onEditCancel = paramEventHandler;
/* 179 */     __set(10);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public B onEditCommit(EventHandler<TableColumn.CellEditEvent<S, T>> paramEventHandler)
/*     */   {
/* 189 */     this.onEditCommit = paramEventHandler;
/* 190 */     __set(11);
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   public B onEditStart(EventHandler<TableColumn.CellEditEvent<S, T>> paramEventHandler)
/*     */   {
/* 200 */     this.onEditStart = paramEventHandler;
/* 201 */     __set(12);
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefWidth(double paramDouble)
/*     */   {
/* 211 */     this.prefWidth = paramDouble;
/* 212 */     __set(13);
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */   public B resizable(boolean paramBoolean)
/*     */   {
/* 222 */     this.resizable = paramBoolean;
/* 223 */     __set(14);
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */   public B sortable(boolean paramBoolean)
/*     */   {
/* 233 */     this.sortable = paramBoolean;
/* 234 */     __set(15);
/* 235 */     return this;
/*     */   }
/*     */ 
/*     */   public B sortNode(Node paramNode)
/*     */   {
/* 244 */     this.sortNode = paramNode;
/* 245 */     __set(16);
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */   public B sortType(TableColumn.SortType paramSortType)
/*     */   {
/* 255 */     this.sortType = paramSortType;
/* 256 */     __set(17);
/* 257 */     return this;
/*     */   }
/*     */ 
/*     */   public B style(String paramString)
/*     */   {
/* 266 */     this.style = paramString;
/* 267 */     __set(18);
/* 268 */     return this;
/*     */   }
/*     */ 
/*     */   public B styleClass(Collection<? extends String> paramCollection)
/*     */   {
/* 277 */     this.styleClass = paramCollection;
/* 278 */     __set(19);
/* 279 */     return this;
/*     */   }
/*     */ 
/*     */   public B styleClass(String[] paramArrayOfString)
/*     */   {
/* 286 */     return styleClass(Arrays.asList(paramArrayOfString));
/*     */   }
/*     */ 
/*     */   public B text(String paramString)
/*     */   {
/* 295 */     this.text = paramString;
/* 296 */     __set(20);
/* 297 */     return this;
/*     */   }
/*     */ 
/*     */   public B userData(Object paramObject)
/*     */   {
/* 306 */     this.userData = paramObject;
/* 307 */     __set(21);
/* 308 */     return this;
/*     */   }
/*     */ 
/*     */   public B visible(boolean paramBoolean)
/*     */   {
/* 317 */     this.visible = paramBoolean;
/* 318 */     __set(22);
/* 319 */     return this;
/*     */   }
/*     */ 
/*     */   public TableColumn<S, T> build()
/*     */   {
/* 326 */     TableColumn localTableColumn = new TableColumn();
/* 327 */     applyTo(localTableColumn);
/* 328 */     return localTableColumn;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TableColumnBuilder
 * JD-Core Version:    0.6.2
 */