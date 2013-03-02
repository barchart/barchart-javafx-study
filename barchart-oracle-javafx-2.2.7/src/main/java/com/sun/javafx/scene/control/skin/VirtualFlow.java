/*      */ package com.sun.javafx.scene.control.skin;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.BooleanPropertyBase;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.property.DoublePropertyBase;
/*      */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.event.Event;
/*      */ import javafx.event.EventDispatchChain;
/*      */ import javafx.event.EventDispatcher;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.geometry.Bounds;
/*      */ import javafx.geometry.Insets;
/*      */ import javafx.geometry.Orientation;
/*      */ import javafx.scene.Group;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.Parent;
/*      */ import javafx.scene.Scene;
/*      */ import javafx.scene.control.Cell;
/*      */ import javafx.scene.control.IndexedCell;
/*      */ import javafx.scene.control.ScrollBar;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.scene.input.ScrollEvent;
/*      */ import javafx.scene.input.ScrollEvent.HorizontalTextScrollUnits;
/*      */ import javafx.scene.input.ScrollEvent.VerticalTextScrollUnits;
/*      */ import javafx.scene.layout.Region;
/*      */ import javafx.scene.layout.StackPane;
/*      */ import javafx.scene.shape.Rectangle;
/*      */ import javafx.util.Callback;
/*      */ 
/*      */ public class VirtualFlow extends Region
/*      */ {
/*      */   private BooleanProperty vertical;
/*  164 */   private boolean pannable = true;
/*      */   private int cellCount;
/*      */   private DoubleProperty position;
/*      */   private Callback<VirtualFlow, ? extends IndexedCell> createCell;
/*      */   private final PositionMapper mapper;
/*  312 */   private int numCellsVisibleOnScreen = -1;
/*      */   double maxPrefBreadth;
/*      */   double viewportBreadth;
/*      */   double viewportLength;
/*  355 */   double lastWidth = -1.0D;
/*      */ 
/*  361 */   double lastHeight = -1.0D;
/*      */ 
/*  368 */   int lastCellCount = 0;
/*      */   boolean lastVertical;
/*      */   double lastPosition;
/*  389 */   double lastCellBreadth = -1.0D;
/*      */ 
/*  394 */   double lastCellLength = -1.0D;
/*      */ 
/*  405 */   final ArrayLinkedList<IndexedCell> cells = new ArrayLinkedList();
/*      */ 
/*  414 */   final ArrayLinkedList<IndexedCell> pile = new ArrayLinkedList();
/*      */   IndexedCell accumCell;
/*      */   Group accumCellParent;
/*      */   Group sheet;
/*  439 */   private VirtualScrollBar hbar = new VirtualScrollBar(this);
/*      */ 
/*  448 */   private VirtualScrollBar vbar = new VirtualScrollBar(this);
/*      */   ClippedContainer clipView;
/*      */   StackPane corner;
/*      */   private double lastX;
/*      */   private double lastY;
/*  472 */   private boolean isPanning = false;
/*      */ 
/* 1952 */   private boolean needsReconfigureCells = false;
/* 1953 */   private boolean needsRecreateCells = false;
/*      */   private static final double GOLDEN_RATIO_MULTIPLIER = 0.618033987D;
/*      */ 
/*      */   public final void setVertical(boolean paramBoolean)
/*      */   {
/*  121 */     verticalProperty().set(paramBoolean);
/*      */   }
/*      */ 
/*      */   public final boolean isVertical() {
/*  125 */     return this.vertical == null ? true : this.vertical.get();
/*      */   }
/*      */ 
/*      */   public final BooleanProperty verticalProperty() {
/*  129 */     if (this.vertical == null) {
/*  130 */       this.vertical = new BooleanPropertyBase(true) {
/*      */         protected void invalidated() {
/*  132 */           VirtualFlow.this.addAllToPile();
/*  133 */           VirtualFlow.this.pile.clear();
/*  134 */           VirtualFlow.this.sheet.getChildren().clear();
/*  135 */           VirtualFlow.this.cells.clear();
/*  136 */           VirtualFlow.this.numCellsVisibleOnScreen = -1;
/*  137 */           VirtualFlow.this.lastWidth = (VirtualFlow.this.lastHeight = VirtualFlow.this.maxPrefBreadth = -1.0D);
/*  138 */           VirtualFlow.this.viewportBreadth = (VirtualFlow.this.viewportLength = VirtualFlow.this.lastPosition = 0.0D);
/*  139 */           VirtualFlow.this.hbar.setValue(0.0D);
/*  140 */           VirtualFlow.this.vbar.setValue(0.0D);
/*  141 */           VirtualFlow.this.mapper.adjustPosition(0.0D);
/*  142 */           VirtualFlow.this.setNeedsLayout(true);
/*  143 */           VirtualFlow.this.requestLayout();
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  148 */           return VirtualFlow.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  153 */           return "vertical";
/*      */         }
/*      */       };
/*      */     }
/*  157 */     return this.vertical;
/*      */   }
/*      */ 
/*      */   public boolean isPannable()
/*      */   {
/*  165 */     return this.pannable; } 
/*  166 */   public void setPannable(boolean paramBoolean) { this.pannable = paramBoolean; }
/*      */ 
/*      */ 
/*      */   public int getCellCount()
/*      */   {
/*  176 */     return this.cellCount;
/*      */   }
/*  178 */   public void setCellCount(int paramInt) { int i = getCellCount();
/*  179 */     this.cellCount = paramInt;
/*      */ 
/*  181 */     int j = i != this.cellCount ? 1 : 0;
/*      */     Object localObject;
/*  185 */     if (j != 0) {
/*  186 */       localObject = isVertical() ? this.vbar : this.hbar;
/*  187 */       ((VirtualScrollBar)localObject).setMax(paramInt);
/*      */     }
/*      */ 
/*  206 */     if (j != 0) layoutChildren();
/*      */ 
/*  212 */     this.sheet.getChildren().clear();
/*      */ 
/*  214 */     if (j != 0) {
/*  215 */       localObject = getParent();
/*  216 */       if (localObject != null) ((Parent)localObject).requestLayout();
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void setPosition(double paramDouble)
/*      */   {
/*  231 */     positionProperty().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getPosition() {
/*  235 */     return this.position == null ? 0.0D : this.position.get();
/*      */   }
/*      */ 
/*      */   public final DoubleProperty positionProperty() {
/*  239 */     if (this.position == null) {
/*  240 */       this.position = new DoublePropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  246 */           if (get() != VirtualFlow.this.mapper.getPosition()) {
/*  247 */             VirtualFlow.this.mapper.adjustPosition(get());
/*  248 */             VirtualFlow.this.requestLayout();
/*      */           }
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  254 */           return VirtualFlow.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  259 */           return "position";
/*      */         }
/*      */       };
/*      */     }
/*  263 */     return this.position;
/*      */   }
/*      */ 
/*      */   public Callback<VirtualFlow, ? extends IndexedCell> getCreateCell()
/*      */   {
/*  272 */     return this.createCell;
/*      */   }
/*  274 */   protected void setCreateCell(Callback<VirtualFlow, ? extends IndexedCell> paramCallback) { this.createCell = paramCallback;
/*      */ 
/*  276 */     if (this.createCell != null) {
/*  277 */       this.accumCell = null;
/*  278 */       setNeedsLayout(true);
/*  279 */       recreateCells();
/*  280 */       if (getParent() != null) getParent().requestLayout();
/*      */     }
/*      */   }
/*      */ 
/*      */   final VirtualScrollBar getHbar()
/*      */   {
/*  442 */     return this.hbar;
/*      */   }
/*      */ 
/*      */   final VirtualScrollBar getVbar()
/*      */   {
/*  451 */     return this.vbar;
/*      */   }
/*      */ 
/*      */   public VirtualFlow()
/*      */   {
/*  475 */     this.mapper = new PositionMapper();
/*  476 */     this.mapper.setGetItemSize(new Callback() {
/*      */       public Double call(Integer paramAnonymousInteger) {
/*  478 */         return Double.valueOf(VirtualFlow.this.getCellLength(paramAnonymousInteger.intValue()));
/*      */       }
/*      */     });
/*  482 */     initContent();
/*  483 */     initBinds();
/*      */   }
/*      */ 
/*      */   private void initContent()
/*      */   {
/*  488 */     this.sheet = new Group();
/*  489 */     this.sheet.setAutoSizeChildren(false);
/*      */ 
/*  492 */     this.clipView = new ClippedContainer(this);
/*  493 */     this.clipView.setNode(this.sheet);
/*  494 */     getChildren().add(this.clipView);
/*      */ 
/*  497 */     this.accumCellParent = new Group();
/*  498 */     this.accumCellParent.setManaged(false);
/*  499 */     this.accumCellParent.setVisible(false);
/*  500 */     getChildren().add(this.accumCellParent);
/*      */ 
/*  508 */     final EventDispatcher local4 = new EventDispatcher()
/*      */     {
/*      */       public Event dispatchEvent(Event paramAnonymousEvent, EventDispatchChain paramAnonymousEventDispatchChain) {
/*  511 */         return paramAnonymousEvent;
/*      */       }
/*      */     };
/*  515 */     final EventDispatcher localEventDispatcher1 = this.hbar.getEventDispatcher();
/*  516 */     this.hbar.setEventDispatcher(new EventDispatcher() {
/*      */       public Event dispatchEvent(Event paramAnonymousEvent, EventDispatchChain paramAnonymousEventDispatchChain) {
/*  518 */         if ((paramAnonymousEvent.getEventType() == ScrollEvent.SCROLL) && (!((ScrollEvent)paramAnonymousEvent).isDirect()))
/*      */         {
/*  520 */           paramAnonymousEventDispatchChain = paramAnonymousEventDispatchChain.prepend(local4);
/*  521 */           paramAnonymousEventDispatchChain = paramAnonymousEventDispatchChain.prepend(localEventDispatcher1);
/*  522 */           return paramAnonymousEventDispatchChain.dispatchEvent(paramAnonymousEvent);
/*      */         }
/*  524 */         return localEventDispatcher1.dispatchEvent(paramAnonymousEvent, paramAnonymousEventDispatchChain);
/*      */       }
/*      */     });
/*  528 */     final EventDispatcher localEventDispatcher2 = this.vbar.getEventDispatcher();
/*  529 */     this.vbar.setEventDispatcher(new EventDispatcher() {
/*      */       public Event dispatchEvent(Event paramAnonymousEvent, EventDispatchChain paramAnonymousEventDispatchChain) {
/*  531 */         if ((paramAnonymousEvent.getEventType() == ScrollEvent.SCROLL) && (!((ScrollEvent)paramAnonymousEvent).isDirect()))
/*      */         {
/*  533 */           paramAnonymousEventDispatchChain = paramAnonymousEventDispatchChain.prepend(local4);
/*  534 */           paramAnonymousEventDispatchChain = paramAnonymousEventDispatchChain.prepend(localEventDispatcher2);
/*  535 */           return paramAnonymousEventDispatchChain.dispatchEvent(paramAnonymousEvent);
/*      */         }
/*  537 */         return localEventDispatcher2.dispatchEvent(paramAnonymousEvent, paramAnonymousEventDispatchChain);
/*      */       }
/*      */     });
/*  545 */     setOnScroll(new EventHandler()
/*      */     {
/*      */       public void handle(ScrollEvent paramAnonymousScrollEvent)
/*      */       {
/*  550 */         double d1 = 0.0D;
/*  551 */         if (VirtualFlow.this.isVertical())
/*  552 */           switch (VirtualFlow.16.$SwitchMap$javafx$scene$input$ScrollEvent$VerticalTextScrollUnits[paramAnonymousScrollEvent.getTextDeltaYUnits().ordinal()]) {
/*      */           case 1:
/*  554 */             d1 = paramAnonymousScrollEvent.getTextDeltaY() * VirtualFlow.this.lastHeight;
/*  555 */             break;
/*      */           case 2:
/*  562 */             if (VirtualFlow.this.lastCellLength != -1.0D) {
/*  563 */               d1 = paramAnonymousScrollEvent.getTextDeltaY() * VirtualFlow.this.lastCellLength;
/*      */             }
/*  565 */             else if (VirtualFlow.this.getFirstVisibleCell() != null)
/*  566 */               d1 = paramAnonymousScrollEvent.getTextDeltaY() * VirtualFlow.this.getCellLength(VirtualFlow.this.getFirstVisibleCell()); break;
/*      */           case 3:
/*  570 */             d1 = paramAnonymousScrollEvent.getDeltaY();
/*      */           }
/*      */         else {
/*  573 */           switch (VirtualFlow.16.$SwitchMap$javafx$scene$input$ScrollEvent$HorizontalTextScrollUnits[paramAnonymousScrollEvent.getTextDeltaXUnits().ordinal()])
/*      */           {
/*      */           case 1:
/*      */           case 2:
/*  578 */             double d2 = paramAnonymousScrollEvent.getDeltaX();
/*  579 */             double d4 = paramAnonymousScrollEvent.getDeltaY();
/*      */ 
/*  581 */             d1 = Math.abs(d2) > Math.abs(d4) ? d2 : d4;
/*      */           }
/*      */         }
/*      */ 
/*  585 */         if (d1 != 0.0D)
/*      */         {
/*  589 */           VirtualFlow.this.adjustPixels(-d1);
/*  590 */           paramAnonymousScrollEvent.consume();
/*      */         }
/*      */         else
/*      */         {
/*  597 */           VirtualScrollBar localVirtualScrollBar = VirtualFlow.this.isVertical() ? VirtualFlow.this.hbar : VirtualFlow.this.vbar;
/*  598 */           if (localVirtualScrollBar.isVisible())
/*      */           {
/*  600 */             double d3 = VirtualFlow.this.isVertical() ? paramAnonymousScrollEvent.getDeltaX() : paramAnonymousScrollEvent.getDeltaY();
/*  601 */             double d5 = localVirtualScrollBar.getValue() - d3;
/*      */ 
/*  603 */             if (d5 < localVirtualScrollBar.getMin())
/*  604 */               localVirtualScrollBar.setValue(localVirtualScrollBar.getMin());
/*  605 */             else if (d5 > localVirtualScrollBar.getMax())
/*  606 */               localVirtualScrollBar.setValue(localVirtualScrollBar.getMax());
/*      */             else {
/*  608 */               localVirtualScrollBar.setValue(d5);
/*      */             }
/*  610 */             paramAnonymousScrollEvent.consume();
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*  617 */     addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler()
/*      */     {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  620 */         if (VirtualFlow.this.isFocusTraversable()) {
/*  621 */           VirtualFlow.this.requestFocus();
/*      */         }
/*      */ 
/*  624 */         VirtualFlow.this.lastX = paramAnonymousMouseEvent.getX();
/*  625 */         VirtualFlow.this.lastY = paramAnonymousMouseEvent.getY();
/*      */ 
/*  632 */         VirtualFlow.this.isPanning = ((!VirtualFlow.this.vbar.getBoundsInParent().contains(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY())) && (!VirtualFlow.this.hbar.getBoundsInParent().contains(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY())));
/*      */       }
/*      */     });
/*  636 */     addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler()
/*      */     {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  639 */         if ((!VirtualFlow.this.isPanning) || (!VirtualFlow.this.isPannable())) return;
/*      */ 
/*  644 */         double d1 = VirtualFlow.this.lastX - paramAnonymousMouseEvent.getX();
/*  645 */         double d2 = VirtualFlow.this.lastY - paramAnonymousMouseEvent.getY();
/*      */ 
/*  650 */         double d3 = VirtualFlow.this.isVertical() ? d2 : d1;
/*  651 */         double d4 = VirtualFlow.this.adjustPixels(d3);
/*  652 */         if (d4 != 0.0D)
/*      */         {
/*  658 */           if (VirtualFlow.this.isVertical()) VirtualFlow.this.lastY = paramAnonymousMouseEvent.getY(); else {
/*  659 */             VirtualFlow.this.lastX = paramAnonymousMouseEvent.getX();
/*      */           }
/*      */         }
/*      */ 
/*  663 */         double d5 = VirtualFlow.this.isVertical() ? d1 : d2;
/*  664 */         VirtualScrollBar localVirtualScrollBar = VirtualFlow.this.isVertical() ? VirtualFlow.this.hbar : VirtualFlow.this.vbar;
/*  665 */         if (localVirtualScrollBar.isVisible()) {
/*  666 */           double d6 = localVirtualScrollBar.getValue() + d5;
/*  667 */           if (d6 < localVirtualScrollBar.getMin()) {
/*  668 */             localVirtualScrollBar.setValue(localVirtualScrollBar.getMin());
/*  669 */           } else if (d6 > localVirtualScrollBar.getMax()) {
/*  670 */             localVirtualScrollBar.setValue(localVirtualScrollBar.getMax());
/*      */           } else {
/*  672 */             localVirtualScrollBar.setValue(d6);
/*      */ 
/*  675 */             if (VirtualFlow.this.isVertical()) VirtualFlow.this.lastX = paramAnonymousMouseEvent.getX(); else
/*  676 */               VirtualFlow.this.lastY = paramAnonymousMouseEvent.getY();
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*  690 */     this.vbar.setOrientation(Orientation.VERTICAL);
/*  691 */     this.vbar.addEventHandler(MouseEvent.ANY, new EventHandler() {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  693 */         paramAnonymousMouseEvent.consume();
/*      */       }
/*      */     });
/*  696 */     getChildren().add(this.vbar);
/*      */ 
/*  699 */     this.hbar.setOrientation(Orientation.HORIZONTAL);
/*  700 */     this.hbar.addEventHandler(MouseEvent.ANY, new EventHandler() {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  702 */         paramAnonymousMouseEvent.consume();
/*      */       }
/*      */     });
/*  705 */     getChildren().add(this.hbar);
/*      */ 
/*  708 */     this.corner = new StackPane();
/*  709 */     this.corner.getStyleClass().setAll(new String[] { "corner" });
/*  710 */     getChildren().add(this.corner);
/*      */   }
/*      */ 
/*      */   private void initBinds()
/*      */   {
/*  715 */     InvalidationListener local12 = new InvalidationListener() {
/*      */       public void invalidated(Observable paramAnonymousObservable) {
/*  717 */         VirtualFlow.this.updateHbar();
/*      */       }
/*      */     };
/*  720 */     verticalProperty().addListener(local12);
/*  721 */     this.hbar.valueProperty().addListener(local12);
/*  722 */     this.hbar.visibleProperty().addListener(local12);
/*      */ 
/*  732 */     Object local13 = new Object()
/*      */     {
/*      */       public void changed(ObservableValue paramAnonymousObservableValue, Object paramAnonymousObject1, Object paramAnonymousObject2) {
/*  735 */         VirtualFlow.this.clipView.setClipY(VirtualFlow.this.isVertical() ? 0.0D : VirtualFlow.this.vbar.getValue());
/*      */       }
/*      */     };
/*  738 */     this.vbar.valueProperty().addListener(local13);
/*      */ 
/*  746 */     this.mapper.positionProperty().addListener(new InvalidationListener() {
/*      */       public void invalidated(Observable paramAnonymousObservable) {
/*  748 */         VirtualFlow.this.setPosition(VirtualFlow.this.mapper.getPosition());
/*      */       }
/*      */     });
/*  752 */     super.heightProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
/*      */       {
/*  757 */         if ((paramAnonymousNumber1.doubleValue() == 0.0D) && (paramAnonymousNumber2.doubleValue() > 0.0D))
/*  758 */           VirtualFlow.this.recreateCells();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   void updateHbar()
/*      */   {
/*  767 */     if ((!isVisible()) || (getScene() == null)) return;
/*      */ 
/*  769 */     if (isVertical())
/*  770 */       if (this.hbar.isVisible()) {
/*  771 */         this.clipView.setClipX(this.hbar.getValue());
/*      */       }
/*      */       else
/*      */       {
/*  776 */         this.clipView.setClipX(0.0D);
/*  777 */         this.hbar.setValue(0.0D);
/*      */       }
/*      */   }
/*      */ 
/*      */   public void requestLayout()
/*      */   {
/*  801 */     if (getScene() != null) {
/*  802 */       getScene().addToDirtyLayoutList(this);
/*  803 */       setNeedsLayout(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void layoutChildren() {
/*  808 */     if (this.needsRecreateCells) {
/*  809 */       this.maxPrefBreadth = -1.0D;
/*  810 */       this.lastWidth = -1.0D;
/*  811 */       this.lastHeight = -1.0D;
/*  812 */       releaseCell(this.accumCell);
/*  813 */       this.accumCell = null;
/*  814 */       this.accumCellParent.getChildren().clear();
/*  815 */       addAllToPile();
/*  816 */       this.pile.clear();
/*  817 */       this.needsRecreateCells = false;
/*      */     }
/*      */ 
/*  820 */     if (this.needsReconfigureCells) {
/*  821 */       this.maxPrefBreadth = -1.0D;
/*  822 */       this.lastWidth = -1.0D;
/*  823 */       this.lastHeight = -1.0D;
/*  824 */       this.needsReconfigureCells = false;
/*      */     }
/*      */ 
/*  829 */     if ((getWidth() <= 0.0D) || (getHeight() <= 0.0D)) {
/*  830 */       addAllToPile();
/*  831 */       this.hbar.setVisible(false);
/*  832 */       this.vbar.setVisible(false);
/*  833 */       this.corner.setVisible(false);
/*  834 */       return;
/*      */     }
/*      */ 
/*  843 */     boolean bool = false;
/*  844 */     for (int i = 0; i < this.cells.size(); i++) {
/*  845 */       localCell = (Cell)this.cells.get(i);
/*  846 */       bool = localCell.isNeedsLayout();
/*  847 */       if (bool) break;
/*      */     }
/*  849 */     Cell localCell = null;
/*      */ 
/*  851 */     IndexedCell localIndexedCell = getFirstVisibleCell();
/*      */     double d2;
/*  856 */     if (!bool) {
/*  857 */       j = 0;
/*  858 */       if (localIndexedCell != null) {
/*  859 */         double d1 = getCellBreadth(localIndexedCell);
/*  860 */         d2 = getCellLength(localIndexedCell);
/*  861 */         j = (d1 != this.lastCellBreadth) || (d2 != this.lastCellLength) ? 1 : 0;
/*  862 */         this.lastCellBreadth = d1;
/*  863 */         this.lastCellLength = d2;
/*      */       }
/*      */ 
/*  866 */       if ((getWidth() == this.lastWidth) && (getHeight() == this.lastHeight) && (getCellCount() == this.lastCellCount) && (isVertical() == this.lastVertical) && (getPosition() == this.lastPosition) && (j == 0))
/*      */       {
/*  880 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  939 */     int j = 0;
/*  940 */     int k = (bool) || (isVertical() != this.lastVertical) || (this.cells.isEmpty()) || (this.maxPrefBreadth == -1.0D) || (getPosition() != this.lastPosition) || (getCellCount() != this.lastCellCount) ? 1 : 0;
/*      */ 
/*  947 */     if (k == 0) {
/*  948 */       if (((isVertical()) && (getHeight() < this.lastHeight)) || ((!isVertical()) && (getWidth() < this.lastWidth)))
/*      */       {
/*  950 */         k = 1;
/*  951 */       } else if (((isVertical()) && (getHeight() > this.lastHeight)) || ((!isVertical()) && (getWidth() > this.lastWidth)))
/*      */       {
/*  953 */         j = 1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  964 */     updateViewport();
/*      */ 
/*  967 */     int m = this.mapper.computeCurrentIndex();
/*  968 */     if (this.lastCellCount != getCellCount())
/*      */     {
/*  975 */       if ((getPosition() == 0.0D) || (getPosition() == 1.0D))
/*      */       {
/*  977 */         this.mapper.setItemCount(getCellCount());
/*  978 */       } else if (m >= getCellCount()) {
/*  979 */         this.mapper.adjustPosition(1.0D);
/*  980 */         this.mapper.setItemCount(getCellCount());
/*  981 */       } else if (localIndexedCell != null) {
/*  982 */         d2 = getCellPosition(localIndexedCell);
/*  983 */         int n = localIndexedCell.getIndex();
/*  984 */         this.mapper.setItemCount(getCellCount());
/*  985 */         if (localIndexedCell != null) {
/*  986 */           this.mapper.adjustPositionToIndex(n);
/*  987 */           double d3 = -this.mapper.computeOffsetForCell(n);
/*  988 */           this.mapper.adjustByPixelAmount(d3 - d2);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  993 */       m = this.mapper.computeCurrentIndex();
/*      */     }
/*      */ 
/*  996 */     if (k != 0)
/*      */     {
/*  998 */       addAllToPile();
/*      */ 
/* 1001 */       d2 = -this.mapper.computeViewportOffset(this.mapper.getPosition());
/*      */ 
/* 1005 */       addLeadingCells(m, d2);
/*      */ 
/* 1007 */       addTrailingCells(true);
/* 1008 */     } else if (j != 0) {
/* 1009 */       addTrailingCells(true);
/*      */     }
/*      */ 
/* 1012 */     updateViewport();
/*      */ 
/* 1014 */     this.lastWidth = getWidth();
/* 1015 */     this.lastHeight = getHeight();
/* 1016 */     this.lastCellCount = getCellCount();
/* 1017 */     this.lastVertical = isVertical();
/* 1018 */     this.lastPosition = getPosition();
/*      */   }
/*      */ 
/*      */   private void addLeadingCells(int paramInt, double paramDouble)
/*      */   {
/* 1032 */     double d1 = paramDouble;
/*      */ 
/* 1034 */     int i = paramInt;
/*      */ 
/* 1037 */     int j = 1;
/*      */ 
/* 1045 */     IndexedCell localIndexedCell = null;
/*      */ 
/* 1047 */     while ((i >= 0) && ((d1 > 0.0D) || (j != 0))) {
/* 1048 */       localIndexedCell = getAvailableCell(i);
/* 1049 */       setCellIndex(localIndexedCell, i);
/* 1050 */       resizeCellSize(localIndexedCell);
/* 1051 */       this.cells.addFirst(localIndexedCell);
/*      */ 
/* 1058 */       if (j != 0)
/* 1059 */         j = 0;
/*      */       else {
/* 1061 */         d1 -= getCellLength(localIndexedCell);
/*      */       }
/*      */ 
/* 1065 */       positionCell(localIndexedCell, d1);
/* 1066 */       this.maxPrefBreadth = Math.max(this.maxPrefBreadth, getCellBreadth(localIndexedCell));
/* 1067 */       localIndexedCell.setVisible(true);
/* 1068 */       i--;
/*      */     }
/*      */ 
/* 1076 */     localIndexedCell = (IndexedCell)this.cells.getFirst();
/* 1077 */     int k = localIndexedCell.getIndex();
/* 1078 */     double d2 = getCellPosition(localIndexedCell);
/* 1079 */     if ((k == 0) && (d2 > 0.0D)) {
/* 1080 */       this.mapper.adjustPosition(0.0D);
/* 1081 */       d1 = 0.0D;
/* 1082 */       for (int m = 0; m < this.cells.size(); m++) {
/* 1083 */         localIndexedCell = (IndexedCell)this.cells.get(m);
/* 1084 */         positionCell(localIndexedCell, d1);
/* 1085 */         d1 += getCellLength(localIndexedCell);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean addTrailingCells(boolean paramBoolean)
/*      */   {
/* 1097 */     if (this.cells.isEmpty()) return false;
/*      */ 
/* 1103 */     IndexedCell localIndexedCell1 = (IndexedCell)this.cells.getLast();
/* 1104 */     double d1 = getCellPosition(localIndexedCell1) + getCellLength(localIndexedCell1);
/* 1105 */     int i = localIndexedCell1.getIndex() + 1;
/* 1106 */     boolean bool = i <= getCellCount();
/*      */ 
/* 1108 */     while (d1 < this.viewportLength) {
/* 1109 */       if (i >= getCellCount()) {
/* 1110 */         if (d1 < this.viewportLength) bool = false;
/* 1111 */         if (!paramBoolean) return bool;
/*      */       }
/* 1113 */       localIndexedCell2 = getAvailableCell(i);
/* 1114 */       setCellIndex(localIndexedCell2, i);
/* 1115 */       resizeCellSize(localIndexedCell2);
/* 1116 */       this.cells.addLast(localIndexedCell2);
/*      */ 
/* 1119 */       positionCell(localIndexedCell2, d1);
/* 1120 */       this.maxPrefBreadth = Math.max(this.maxPrefBreadth, getCellBreadth(localIndexedCell2));
/*      */ 
/* 1122 */       d1 += getCellLength(localIndexedCell2);
/* 1123 */       localIndexedCell2.setVisible(true);
/* 1124 */       i++;
/*      */     }
/*      */ 
/* 1134 */     IndexedCell localIndexedCell2 = (IndexedCell)this.cells.getFirst();
/* 1135 */     i = localIndexedCell2.getIndex();
/* 1136 */     IndexedCell localIndexedCell3 = getLastVisibleCell();
/* 1137 */     double d2 = getCellPosition(localIndexedCell2);
/* 1138 */     double d3 = getCellPosition(localIndexedCell3) + getCellLength(localIndexedCell3);
/* 1139 */     if (((i != 0) || ((i == 0) && (d2 < 0.0D))) && (paramBoolean) && (localIndexedCell3 != null) && (localIndexedCell3.getIndex() == getCellCount() - 1) && (d3 < this.viewportLength))
/*      */     {
/* 1142 */       double d4 = d3;
/* 1143 */       double d5 = this.viewportLength - d3;
/* 1144 */       while ((d4 < this.viewportLength) && (i != 0) && (-d2 < d5)) {
/* 1145 */         i--;
/* 1146 */         IndexedCell localIndexedCell4 = getAvailableCell(i);
/* 1147 */         setCellIndex(localIndexedCell4, i);
/* 1148 */         resizeCellSize(localIndexedCell4);
/* 1149 */         this.cells.addFirst(localIndexedCell4);
/* 1150 */         double d7 = getCellLength(localIndexedCell4);
/* 1151 */         d2 -= d7;
/* 1152 */         d4 += d7;
/* 1153 */         positionCell(localIndexedCell4, d2);
/* 1154 */         this.maxPrefBreadth = Math.max(this.maxPrefBreadth, getCellBreadth(localIndexedCell4));
/* 1155 */         localIndexedCell4.setVisible(true);
/*      */       }
/*      */ 
/* 1159 */       localIndexedCell2 = (IndexedCell)this.cells.getFirst();
/* 1160 */       d2 = getCellPosition(localIndexedCell2);
/* 1161 */       double d6 = this.viewportLength - d3;
/* 1162 */       if ((localIndexedCell2.getIndex() == 0) && (d6 > -d2)) {
/* 1163 */         d6 = -d2;
/*      */       }
/*      */ 
/* 1166 */       for (int j = 0; j < this.cells.size(); j++) {
/* 1167 */         IndexedCell localIndexedCell5 = (IndexedCell)this.cells.get(j);
/* 1168 */         positionCell(localIndexedCell5, getCellPosition(localIndexedCell5) + d6);
/*      */       }
/*      */ 
/* 1174 */       d2 = getCellPosition(localIndexedCell2);
/* 1175 */       if ((localIndexedCell2.getIndex() == 0) && (d2 == 0.0D))
/* 1176 */         this.mapper.adjustPosition(0.0D);
/* 1177 */       else if (getPosition() != 1.0D) {
/* 1178 */         this.mapper.adjustPosition(1.0D);
/*      */       }
/*      */     }
/*      */ 
/* 1182 */     return bool;
/*      */   }
/*      */ 
/*      */   private void updateViewport()
/*      */   {
/* 1188 */     double d1 = this.viewportLength;
/* 1189 */     this.viewportLength = snapSize(isVertical() ? getHeight() : getWidth());
/* 1190 */     this.viewportBreadth = snapSize(isVertical() ? getWidth() : getHeight());
/*      */ 
/* 1194 */     VirtualScrollBar localVirtualScrollBar1 = isVertical() ? this.hbar : this.vbar;
/* 1195 */     VirtualScrollBar localVirtualScrollBar2 = isVertical() ? this.vbar : this.hbar;
/* 1196 */     double d2 = snapSize(isVertical() ? this.hbar.prefHeight(-1.0D) : this.vbar.prefWidth(-1.0D));
/* 1197 */     double d3 = snapSize(isVertical() ? this.vbar.prefWidth(-1.0D) : this.hbar.prefHeight(-1.0D));
/*      */ 
/* 1201 */     localVirtualScrollBar1.setVirtual(false);
/* 1202 */     localVirtualScrollBar2.setVirtual(true);
/*      */ 
/* 1213 */     if (this.maxPrefBreadth == -1.0D) {
/* 1214 */       return;
/*      */     }
/*      */ 
/* 1222 */     boolean bool1 = (getPosition() > 0.0D) && ((getCellCount() >= this.cells.size()) || (this.viewportLength >= getHeight()));
/* 1223 */     boolean bool2 = (this.maxPrefBreadth > this.viewportBreadth) || ((bool1) && (this.maxPrefBreadth > this.viewportBreadth - d3));
/*      */ 
/* 1230 */     if (bool2) this.viewportLength -= d2;
/* 1231 */     if (bool1) this.viewportBreadth -= d3;
/*      */ 
/* 1234 */     this.mapper.setViewportSize(this.viewportLength);
/*      */ 
/* 1236 */     localVirtualScrollBar1.setVisible(bool2);
/* 1237 */     localVirtualScrollBar2.setVisible(bool1);
/*      */ 
/* 1239 */     updateScrollBarsAndViewport(d1);
/*      */   }
/*      */ 
/*      */   protected void setWidth(double paramDouble) {
/* 1243 */     super.setWidth(paramDouble);
/* 1244 */     setNeedsLayout(true);
/* 1245 */     requestLayout();
/*      */   }
/*      */ 
/*      */   protected void setHeight(double paramDouble) {
/* 1249 */     super.setHeight(paramDouble);
/* 1250 */     setNeedsLayout(true);
/* 1251 */     requestLayout();
/*      */   }
/*      */ 
/*      */   private void updateScrollBarsAndViewport(double paramDouble)
/*      */   {
/* 1257 */     VirtualScrollBar localVirtualScrollBar1 = isVertical() ? this.hbar : this.vbar;
/* 1258 */     VirtualScrollBar localVirtualScrollBar2 = isVertical() ? this.vbar : this.hbar;
/* 1259 */     double d1 = snapSize(isVertical() ? this.hbar.prefHeight(-1.0D) : this.vbar.prefWidth(-1.0D));
/* 1260 */     double d2 = snapSize(isVertical() ? this.vbar.prefWidth(-1.0D) : this.hbar.prefHeight(-1.0D));
/*      */ 
/* 1268 */     for (int i = 0; i < 2; i++) {
/* 1269 */       if (!localVirtualScrollBar2.isVisible())
/*      */       {
/* 1272 */         if (getCellCount() > this.cells.size()) {
/* 1273 */           localVirtualScrollBar2.setVisible(true);
/* 1274 */         } else if (getCellCount() == this.cells.size())
/*      */         {
/* 1279 */           IndexedCell localIndexedCell1 = (IndexedCell)this.cells.getLast();
/* 1280 */           localVirtualScrollBar2.setVisible(getCellPosition(localIndexedCell1) + getCellLength(localIndexedCell1) > this.viewportLength);
/*      */         }
/*      */ 
/* 1283 */         if (localVirtualScrollBar2.isVisible()) this.viewportBreadth -= d2;
/*      */       }
/* 1285 */       if (!localVirtualScrollBar1.isVisible()) {
/* 1286 */         localVirtualScrollBar1.setVisible(this.maxPrefBreadth > this.viewportBreadth);
/* 1287 */         if (localVirtualScrollBar1.isVisible()) this.viewportLength -= d1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1292 */     this.corner.setVisible((localVirtualScrollBar1.isVisible()) && (localVirtualScrollBar2.isVisible()));
/*      */ 
/* 1294 */     this.numCellsVisibleOnScreen = 0;
/* 1295 */     double d3 = 0.0D;
/* 1296 */     double d4 = (isVertical() ? getHeight() : getWidth()) - (localVirtualScrollBar1.isVisible() ? localVirtualScrollBar1.prefHeight(-1.0D) : 0.0D);
/*      */ 
/* 1298 */     for (int j = 0; j < this.cells.size(); j++) {
/* 1299 */       IndexedCell localIndexedCell2 = (IndexedCell)this.cells.get(j);
/* 1300 */       if ((localIndexedCell2 != null) && (!localIndexedCell2.isEmpty())) {
/* 1301 */         d3 += (isVertical() ? localIndexedCell2.getHeight() : localIndexedCell2.getWidth());
/* 1302 */         if (d3 > d4)
/*      */         {
/*      */           break;
/*      */         }
/* 1306 */         this.numCellsVisibleOnScreen += 1;
/*      */       }
/*      */     }
/*      */     Parent localParent;
/*      */     double d5;
/*      */     double d6;
/*      */     double d7;
/*      */     double d8;
/* 1311 */     if (localVirtualScrollBar1.isVisible())
/*      */     {
/* 1317 */       localParent = getParent();
/* 1318 */       if ((localParent instanceof Region)) {
/* 1319 */         d5 = ((Region)localParent).getInsets().getTop();
/* 1320 */         d6 = ((Region)localParent).getInsets().getBottom();
/* 1321 */         d7 = ((Region)localParent).getInsets().getLeft();
/* 1322 */         d8 = ((Region)localParent).getInsets().getRight();
/*      */       }
/*      */       else {
/* 1325 */         d5 = d6 = d7 = d8 = 0.0D;
/*      */       }
/*      */ 
/* 1331 */       if (isVertical()) {
/* 1332 */         this.hbar.resizeRelocate(0.0D, this.viewportLength, this.viewportBreadth, this.hbar.prefHeight(this.viewportBreadth));
/*      */       }
/*      */       else {
/* 1335 */         this.vbar.resizeRelocate(this.viewportLength, 0.0D, this.vbar.prefWidth(this.viewportBreadth), this.viewportBreadth);
/*      */       }
/*      */ 
/* 1342 */       double d9 = Math.max(1.0D, this.maxPrefBreadth - this.viewportBreadth);
/* 1343 */       if (d9 != localVirtualScrollBar1.getMax()) {
/* 1344 */         localVirtualScrollBar1.setMax(d9);
/*      */ 
/* 1346 */         int k = (localVirtualScrollBar1.getValue() != 0.0D) && (localVirtualScrollBar1.getMax() == localVirtualScrollBar1.getValue()) ? 1 : 0;
/* 1347 */         if ((k != 0) || (localVirtualScrollBar1.getValue() > d9)) {
/* 1348 */           localVirtualScrollBar1.setValue(d9);
/*      */         }
/*      */ 
/* 1351 */         localVirtualScrollBar1.setVisibleAmount(this.viewportBreadth / this.maxPrefBreadth * d9);
/*      */       }
/*      */     }
/*      */ 
/* 1355 */     if (localVirtualScrollBar2.isVisible()) {
/* 1356 */       localVirtualScrollBar2.setMax(1.0D);
/*      */ 
/* 1358 */       if (this.numCellsVisibleOnScreen == 0)
/*      */       {
/* 1360 */         localVirtualScrollBar2.setVisibleAmount(d4 / d3);
/*      */       }
/* 1362 */       else localVirtualScrollBar2.setVisibleAmount(this.numCellsVisibleOnScreen / getCellCount());
/*      */ 
/* 1383 */       localParent = getParent();
/* 1384 */       if ((localParent instanceof Region)) {
/* 1385 */         d5 = ((Region)localParent).getInsets().getTop();
/* 1386 */         d6 = ((Region)localParent).getInsets().getBottom();
/* 1387 */         d7 = ((Region)localParent).getInsets().getLeft();
/* 1388 */         d8 = ((Region)localParent).getInsets().getRight();
/*      */       }
/*      */       else {
/* 1391 */         d5 = d6 = d7 = d8 = 0.0D;
/*      */       }
/*      */ 
/* 1397 */       if (isVertical())
/* 1398 */         this.vbar.resizeRelocate(this.viewportBreadth, 0.0D, this.vbar.prefWidth(this.viewportLength), this.viewportLength);
/*      */       else {
/* 1400 */         this.hbar.resizeRelocate(0.0D, this.viewportBreadth, this.viewportLength, this.hbar.prefHeight(-1.0D));
/*      */       }
/*      */     }
/*      */ 
/* 1404 */     if (this.corner.isVisible()) {
/* 1405 */       this.corner.resize(this.vbar.getWidth(), this.hbar.getHeight());
/* 1406 */       this.corner.relocate(this.hbar.getLayoutX() + this.hbar.getWidth(), this.vbar.getLayoutY() + this.vbar.getHeight());
/*      */     }
/*      */ 
/* 1409 */     this.clipView.resize(snapSize(isVertical() ? this.viewportBreadth : this.viewportLength), snapSize(isVertical() ? this.viewportLength : this.viewportBreadth));
/*      */ 
/* 1413 */     if (this.mapper.getViewportSize() != this.viewportLength) {
/* 1414 */       this.mapper.setViewportSize(this.viewportLength);
/*      */     }
/*      */ 
/* 1423 */     fitCells();
/*      */ 
/* 1427 */     if (getPosition() != localVirtualScrollBar2.getValue())
/* 1428 */       localVirtualScrollBar2.setValue(getPosition());
/*      */   }
/*      */ 
/*      */   private void fitCells()
/*      */   {
/* 1439 */     double d = Math.max(this.maxPrefBreadth, this.viewportBreadth);
/* 1440 */     for (int i = 0; i < this.cells.size(); i++) {
/* 1441 */       Cell localCell = (Cell)this.cells.get(i);
/* 1442 */       if (isVertical())
/* 1443 */         localCell.resize(d, localCell.getHeight());
/*      */       else
/* 1445 */         localCell.resize(localCell.getWidth(), d);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void cull()
/*      */   {
/* 1451 */     for (int i = this.cells.size() - 1; i >= 0; i--) {
/* 1452 */       IndexedCell localIndexedCell = (IndexedCell)this.cells.get(i);
/* 1453 */       double d1 = getCellLength(localIndexedCell);
/* 1454 */       double d2 = getCellPosition(localIndexedCell);
/* 1455 */       double d3 = d2 + d1;
/* 1456 */       if ((d2 > this.viewportLength) || (d3 < 0.0D))
/* 1457 */         addToPile((IndexedCell)this.cells.remove(i));
/*      */     }
/*      */   }
/*      */ 
/*      */   IndexedCell getCell(int paramInt)
/*      */   {
/* 1476 */     if (!this.cells.isEmpty())
/*      */     {
/* 1479 */       IndexedCell localIndexedCell1 = getVisibleCell(paramInt);
/* 1480 */       if (localIndexedCell1 != null) return localIndexedCell1;
/*      */ 
/*      */     }
/*      */ 
/* 1484 */     for (int i = 0; i < this.pile.size(); i++) {
/* 1485 */       IndexedCell localIndexedCell2 = (IndexedCell)this.pile.get(i);
/* 1486 */       if ((localIndexedCell2 != null) && (localIndexedCell2.getIndex() == paramInt))
/*      */       {
/* 1491 */         return localIndexedCell2;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1496 */     if ((this.accumCell == null) && (getCreateCell() != null)) {
/* 1497 */       this.accumCell = ((IndexedCell)getCreateCell().call(this));
/* 1498 */       this.accumCellParent.getChildren().add(this.accumCell);
/*      */     }
/* 1500 */     setCellIndex(this.accumCell, paramInt);
/* 1501 */     resizeCellSize(this.accumCell);
/* 1502 */     return this.accumCell;
/*      */   }
/*      */ 
/*      */   private void releaseCell(IndexedCell paramIndexedCell)
/*      */   {
/* 1509 */     if ((this.accumCell != null) && (paramIndexedCell == this.accumCell))
/* 1510 */       this.accumCell.updateIndex(-1);
/*      */   }
/*      */ 
/*      */   double getCellLength(int paramInt)
/*      */   {
/* 1523 */     IndexedCell localIndexedCell = getCell(paramInt);
/* 1524 */     double d = getCellLength(localIndexedCell);
/* 1525 */     releaseCell(localIndexedCell);
/* 1526 */     return d;
/*      */   }
/*      */ 
/*      */   double getCellBreadth(int paramInt)
/*      */   {
/* 1532 */     IndexedCell localIndexedCell = getCell(paramInt);
/* 1533 */     double d = getCellBreadth(localIndexedCell);
/* 1534 */     releaseCell(localIndexedCell);
/* 1535 */     return d;
/*      */   }
/*      */ 
/*      */   private double getCellLength(IndexedCell paramIndexedCell)
/*      */   {
/* 1542 */     if (paramIndexedCell == null) return 0.0D;
/*      */ 
/* 1544 */     return isVertical() ? paramIndexedCell.getLayoutBounds().getHeight() : paramIndexedCell.getLayoutBounds().getWidth();
/*      */   }
/*      */ 
/*      */   private double getCellPrefLength(IndexedCell paramIndexedCell)
/*      */   {
/* 1550 */     return isVertical() ? paramIndexedCell.prefHeight(-1.0D) : paramIndexedCell.prefWidth(-1.0D);
/*      */   }
/*      */ 
/*      */   private double getCellBreadth(Cell paramCell)
/*      */   {
/* 1559 */     return isVertical() ? paramCell.prefWidth(-1.0D) : paramCell.prefHeight(-1.0D);
/*      */   }
/*      */ 
/*      */   private double getCellPosition(IndexedCell paramIndexedCell)
/*      */   {
/* 1568 */     if (paramIndexedCell == null) return 0.0D;
/*      */ 
/* 1570 */     return isVertical() ? paramIndexedCell.getLayoutY() : paramIndexedCell.getLayoutX();
/*      */   }
/*      */ 
/*      */   private void positionCell(IndexedCell paramIndexedCell, double paramDouble)
/*      */   {
/* 1576 */     if (isVertical()) {
/* 1577 */       paramIndexedCell.setLayoutX(0.0D);
/* 1578 */       paramIndexedCell.setLayoutY(snapSize(paramDouble));
/*      */     } else {
/* 1580 */       paramIndexedCell.setLayoutX(snapSize(paramDouble));
/* 1581 */       paramIndexedCell.setLayoutY(0.0D);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void resizeCellSize(IndexedCell paramIndexedCell) {
/* 1586 */     if (paramIndexedCell == null) return;
/* 1587 */     if (isVertical())
/* 1588 */       paramIndexedCell.resize(paramIndexedCell.getWidth(), paramIndexedCell.prefHeight(-1.0D));
/*      */     else
/* 1590 */       paramIndexedCell.resize(paramIndexedCell.prefWidth(-1.0D), paramIndexedCell.getHeight());
/*      */   }
/*      */ 
/*      */   private void setCellIndex(IndexedCell paramIndexedCell, int paramInt) {
/* 1594 */     if (paramIndexedCell == null) return;
/*      */ 
/* 1596 */     paramIndexedCell.updateIndex(paramInt);
/* 1597 */     if ((paramIndexedCell.isNeedsLayout()) && (paramIndexedCell.getScene() != null))
/* 1598 */       paramIndexedCell.impl_processCSS(false);
/*      */   }
/*      */ 
/*      */   private IndexedCell getAvailableCell(int paramInt)
/*      */   {
/* 1614 */     Object localObject = null;
/*      */ 
/* 1618 */     for (int i = 0; i < this.pile.size(); i++) {
/* 1619 */       IndexedCell localIndexedCell = (IndexedCell)this.pile.get(i);
/* 1620 */       if ((localIndexedCell != null) && (localIndexedCell.getIndex() == paramInt)) {
/* 1621 */         this.pile.remove(i);
/* 1622 */         localObject = localIndexedCell;
/*      */       }
/*      */     }
/*      */ 
/* 1626 */     if (localObject == null) {
/* 1627 */       if (this.pile.size() > 0)
/* 1628 */         localObject = paramInt < ((IndexedCell)this.pile.getFirst()).getIndex() ? (IndexedCell)this.pile.removeLast() : (IndexedCell)this.pile.removeFirst();
/*      */       else {
/* 1630 */         localObject = (IndexedCell)this.createCell.call(this);
/*      */       }
/*      */     }
/*      */ 
/* 1634 */     if (!this.sheet.getChildren().contains(localObject)) {
/* 1635 */       this.sheet.getChildren().add(localObject);
/*      */     }
/*      */ 
/* 1638 */     return localObject;
/*      */   }
/*      */ 
/*      */   private void addAllToPile() {
/* 1642 */     while (this.cells.size() > 0)
/* 1643 */       addToPile((IndexedCell)this.cells.removeFirst());
/*      */   }
/*      */ 
/*      */   private void addToPile(IndexedCell paramIndexedCell)
/*      */   {
/* 1652 */     paramIndexedCell.setVisible(false);
/* 1653 */     this.pile.addLast(paramIndexedCell);
/*      */   }
/*      */ 
/*      */   public IndexedCell getVisibleCell(int paramInt)
/*      */   {
/* 1664 */     if (this.cells.isEmpty()) return null;
/*      */ 
/* 1667 */     IndexedCell localIndexedCell1 = (IndexedCell)this.cells.getLast();
/* 1668 */     int i = localIndexedCell1.getIndex();
/* 1669 */     if (paramInt == i) return localIndexedCell1;
/*      */ 
/* 1672 */     IndexedCell localIndexedCell2 = (IndexedCell)this.cells.getFirst();
/* 1673 */     int j = localIndexedCell2.getIndex();
/* 1674 */     if (paramInt == j) return localIndexedCell2;
/*      */ 
/* 1677 */     if ((paramInt > j) && (paramInt < i)) {
/* 1678 */       return (IndexedCell)this.cells.get(paramInt - j);
/*      */     }
/*      */ 
/* 1682 */     return null;
/*      */   }
/*      */ 
/*      */   public IndexedCell getLastVisibleCell()
/*      */   {
/* 1691 */     if ((this.cells.isEmpty()) || (this.viewportLength <= 0.0D)) return null;
/*      */ 
/* 1694 */     for (int i = this.cells.size() - 1; i >= 0; i--) {
/* 1695 */       IndexedCell localIndexedCell = (IndexedCell)this.cells.get(i);
/* 1696 */       if (!localIndexedCell.isEmpty()) {
/* 1697 */         return localIndexedCell;
/*      */       }
/*      */     }
/*      */ 
/* 1701 */     return null;
/*      */   }
/*      */ 
/*      */   public IndexedCell getFirstVisibleCell()
/*      */   {
/* 1710 */     if ((this.cells.isEmpty()) || (this.viewportLength <= 0.0D)) return null;
/* 1711 */     IndexedCell localIndexedCell = (IndexedCell)this.cells.getFirst();
/* 1712 */     return localIndexedCell.isEmpty() ? null : localIndexedCell;
/*      */   }
/*      */ 
/*      */   public IndexedCell getLastVisibleCellWithinViewPort() {
/* 1716 */     if ((this.cells.isEmpty()) || (this.viewportLength <= 0.0D)) return null;
/*      */ 
/* 1719 */     for (int i = this.cells.size() - 1; i >= 0; i--) {
/* 1720 */       IndexedCell localIndexedCell = (IndexedCell)this.cells.get(i);
/* 1721 */       if (!localIndexedCell.isEmpty())
/*      */       {
/* 1723 */         if (localIndexedCell.getLayoutY() < getHeight()) {
/* 1724 */           return localIndexedCell;
/*      */         }
/*      */       }
/*      */     }
/* 1728 */     return null;
/*      */   }
/*      */ 
/*      */   public IndexedCell getFirstVisibleCellWithinViewPort() {
/* 1732 */     if ((this.cells.isEmpty()) || (this.viewportLength <= 0.0D)) return null;
/*      */ 
/* 1735 */     for (int i = 0; i < this.cells.size(); i++) {
/* 1736 */       IndexedCell localIndexedCell = (IndexedCell)this.cells.get(i);
/* 1737 */       if (!localIndexedCell.isEmpty())
/*      */       {
/* 1739 */         if ((isVertical()) && (localIndexedCell.getLayoutY() + localIndexedCell.getHeight() > 0.0D))
/* 1740 */           return localIndexedCell;
/* 1741 */         if ((!isVertical()) && (localIndexedCell.getLayoutX() + localIndexedCell.getWidth() > 0.0D)) {
/* 1742 */           return localIndexedCell;
/*      */         }
/*      */       }
/*      */     }
/* 1746 */     return null;
/*      */   }
/*      */ 
/*      */   public void showAsFirst(IndexedCell paramIndexedCell)
/*      */   {
/* 1755 */     if (paramIndexedCell != null)
/* 1756 */       adjustPixels(getCellPosition(paramIndexedCell));
/*      */   }
/*      */ 
/*      */   public void showAsLast(IndexedCell paramIndexedCell)
/*      */   {
/* 1766 */     if (paramIndexedCell != null)
/* 1767 */       adjustPixels(getCellPosition(paramIndexedCell) + getCellLength(paramIndexedCell) - this.viewportLength);
/*      */   }
/*      */ 
/*      */   public void show(IndexedCell paramIndexedCell)
/*      */   {
/* 1776 */     if (paramIndexedCell != null) {
/* 1777 */       double d1 = getCellPosition(paramIndexedCell);
/* 1778 */       double d2 = getCellLength(paramIndexedCell);
/* 1779 */       double d3 = d1 + d2;
/* 1780 */       if (d1 < 0.0D)
/* 1781 */         adjustPixels(d1);
/* 1782 */       else if (d3 > this.viewportLength)
/* 1783 */         adjustPixels(d3 - this.viewportLength);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void show(int paramInt)
/*      */   {
/* 1789 */     IndexedCell localIndexedCell1 = getVisibleCell(paramInt);
/* 1790 */     if (localIndexedCell1 != null) {
/* 1791 */       show(localIndexedCell1);
/*      */     }
/*      */     else {
/* 1794 */       IndexedCell localIndexedCell2 = getVisibleCell(paramInt - 1);
/* 1795 */       if (localIndexedCell2 != null)
/*      */       {
/* 1798 */         localIndexedCell1 = getAvailableCell(paramInt);
/* 1799 */         setCellIndex(localIndexedCell1, paramInt);
/* 1800 */         resizeCellSize(localIndexedCell1);
/* 1801 */         this.cells.addLast(localIndexedCell1);
/* 1802 */         positionCell(localIndexedCell1, getCellPosition(localIndexedCell2) + getCellLength(localIndexedCell2));
/* 1803 */         this.maxPrefBreadth = Math.max(this.maxPrefBreadth, getCellBreadth(localIndexedCell1));
/* 1804 */         localIndexedCell1.setVisible(true);
/* 1805 */         show(localIndexedCell1);
/*      */ 
/* 1807 */         return;
/*      */       }
/*      */ 
/* 1810 */       IndexedCell localIndexedCell3 = getVisibleCell(paramInt + 1);
/* 1811 */       if (localIndexedCell3 != null)
/*      */       {
/* 1813 */         localIndexedCell1 = getAvailableCell(paramInt);
/* 1814 */         setCellIndex(localIndexedCell1, paramInt);
/* 1815 */         resizeCellSize(localIndexedCell1);
/* 1816 */         this.cells.addFirst(localIndexedCell1);
/* 1817 */         positionCell(localIndexedCell1, getCellPosition(localIndexedCell3) - getCellLength(localIndexedCell1));
/* 1818 */         this.maxPrefBreadth = Math.max(this.maxPrefBreadth, getCellBreadth(localIndexedCell1));
/* 1819 */         localIndexedCell1.setVisible(true);
/* 1820 */         show(localIndexedCell1);
/*      */ 
/* 1822 */         return;
/*      */       }
/*      */ 
/* 1827 */       this.mapper.adjustPositionToIndex(paramInt);
/* 1828 */       addAllToPile();
/* 1829 */       requestLayout();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void scrollTo(int paramInt, boolean paramBoolean)
/*      */   {
/* 1835 */     int i = 0;
/*      */ 
/* 1837 */     if (paramInt >= getCellCount() - 1) {
/* 1838 */       setPosition(1.0D);
/* 1839 */       i = 1;
/* 1840 */     } else if (paramInt < 0) {
/* 1841 */       setPosition(0.0D);
/* 1842 */       i = 1;
/*      */     }
/*      */ 
/* 1845 */     if (i == 0) {
/* 1846 */       double d = 0.0D;
/* 1847 */       for (int j = 0; j < paramInt; j++) {
/* 1848 */         d += getCellLength(j);
/*      */       }
/*      */ 
/* 1851 */       if (paramBoolean) {
/* 1852 */         d += getCellLength(paramInt) / 2.0D;
/* 1853 */         d -= getHeight() / 2.0D;
/*      */       }
/*      */ 
/* 1856 */       this.mapper.adjustByPixelChunk(d);
/*      */     }
/*      */ 
/* 1859 */     requestLayout();
/*      */   }
/*      */ 
/*      */   public void scrollToOffset(int paramInt)
/*      */   {
/* 1865 */     adjustPixels(paramInt * getCellLength(0));
/*      */   }
/*      */ 
/*      */   public double adjustPixels(double paramDouble)
/*      */   {
/* 1876 */     if (paramDouble == 0.0D) return 0.0D;
/*      */ 
/* 1878 */     if (((isVertical()) && (!this.vbar.isVisible())) || ((!isVertical()) && (!this.hbar.isVisible()))) return 0.0D;
/* 1879 */     if ((this.mapper.getPosition() == 0.0D) && (paramDouble < 0.0D)) return 0.0D;
/* 1880 */     if ((this.mapper.getPosition() == 1.0D) && (paramDouble > 0.0D)) return 0.0D;
/*      */ 
/* 1882 */     double d1 = this.mapper.getPosition();
/* 1883 */     this.mapper.adjustByPixelAmount(paramDouble);
/* 1884 */     if (d1 == this.mapper.getPosition())
/*      */     {
/* 1887 */       return 0.0D;
/*      */     }
/*      */ 
/* 1904 */     if (this.cells.size() > 0) {
/* 1905 */       for (int i = 0; i < this.cells.size(); i++) {
/* 1906 */         IndexedCell localIndexedCell2 = (IndexedCell)this.cells.get(i);
/* 1907 */         positionCell(localIndexedCell2, getCellPosition(localIndexedCell2) - paramDouble);
/*      */       }
/*      */ 
/* 1911 */       IndexedCell localIndexedCell1 = (IndexedCell)this.cells.getFirst();
/* 1912 */       int j = localIndexedCell1.getIndex();
/* 1913 */       double d2 = getCellLength(j - 1);
/* 1914 */       addLeadingCells(j - 1, getCellPosition(localIndexedCell1) - d2);
/*      */ 
/* 1921 */       if (!addTrailingCells(false))
/*      */       {
/* 1925 */         IndexedCell localIndexedCell3 = getLastVisibleCell();
/* 1926 */         double d3 = getCellLength(localIndexedCell3);
/* 1927 */         double d4 = getCellPosition(localIndexedCell3) + d3;
/* 1928 */         if (d4 < this.viewportLength)
/*      */         {
/* 1930 */           double d5 = this.viewportLength - d4;
/* 1931 */           for (int k = 0; k < this.cells.size(); k++) {
/* 1932 */             IndexedCell localIndexedCell4 = (IndexedCell)this.cells.get(k);
/* 1933 */             positionCell(localIndexedCell4, getCellPosition(localIndexedCell4) + d5);
/*      */           }
/* 1935 */           this.mapper.adjustPosition(1.0D);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1940 */       cull();
/*      */     }
/*      */ 
/* 1944 */     updateScrollBarsAndViewport(this.viewportLength);
/* 1945 */     this.lastPosition = getPosition();
/*      */ 
/* 1949 */     return paramDouble;
/*      */   }
/*      */ 
/*      */   public void reconfigureCells()
/*      */   {
/* 1956 */     this.needsReconfigureCells = true;
/* 1957 */     requestLayout();
/*      */   }
/*      */ 
/*      */   public void recreateCells() {
/* 1961 */     this.needsRecreateCells = true;
/* 1962 */     requestLayout();
/*      */   }
/*      */ 
/*      */   private double getPrefBreadth(double paramDouble)
/*      */   {
/* 1968 */     double d1 = getMaxCellWidth(10);
/*      */ 
/* 1974 */     if (paramDouble > -1.0D) {
/* 1975 */       double d2 = getPrefLength();
/* 1976 */       d1 = Math.max(d1, d2 * 0.618033987D);
/*      */     }
/*      */ 
/* 1979 */     return d1;
/*      */   }
/*      */ 
/*      */   private double getPrefLength() {
/* 1983 */     double d = 0.0D;
/* 1984 */     int i = Math.min(10, getCellCount());
/* 1985 */     for (int j = 0; j < i; j++) {
/* 1986 */       d += getCellLength(j);
/*      */     }
/* 1988 */     return d;
/*      */   }
/*      */ 
/*      */   protected double computePrefWidth(double paramDouble) {
/* 1992 */     double d = isVertical() ? getPrefBreadth(paramDouble) : getPrefLength();
/* 1993 */     return d + this.vbar.prefWidth(-1.0D);
/*      */   }
/*      */ 
/*      */   protected double computePrefHeight(double paramDouble) {
/* 1997 */     double d = isVertical() ? getPrefLength() : getPrefBreadth(paramDouble);
/* 1998 */     return d + this.hbar.prefHeight(-1.0D);
/*      */   }
/*      */ 
/*      */   double getMaxCellWidth(int paramInt) {
/* 2002 */     double d = 0.0D;
/* 2003 */     int i = paramInt == -1 ? getCellCount() : Math.min(paramInt, getCellCount());
/* 2004 */     for (int j = 0; j < i; j++) {
/* 2005 */       d = Math.max(d, getCellBreadth(j));
/*      */     }
/* 2007 */     return d;
/*      */   }
/*      */ 
/*      */   static class ArrayLinkedList<T>
/*      */   {
/*      */     private final ArrayList<T> array;
/* 2183 */     private int firstIndex = -1;
/* 2184 */     private int lastIndex = -1;
/*      */ 
/*      */     public ArrayLinkedList() {
/* 2187 */       this.array = new ArrayList(50);
/*      */ 
/* 2189 */       for (int i = 0; i < 50; i++)
/* 2190 */         this.array.add(null);
/*      */     }
/*      */ 
/*      */     public T getFirst()
/*      */     {
/* 2195 */       return this.firstIndex == -1 ? null : this.array.get(this.firstIndex);
/*      */     }
/*      */ 
/*      */     public T getLast() {
/* 2199 */       return this.lastIndex == -1 ? null : this.array.get(this.lastIndex);
/*      */     }
/*      */ 
/*      */     public void addFirst(T paramT)
/*      */     {
/* 2205 */       if (this.firstIndex == -1) {
/* 2206 */         this.firstIndex = (this.lastIndex = this.array.size() / 2);
/* 2207 */         this.array.set(this.firstIndex, paramT);
/* 2208 */       } else if (this.firstIndex == 0)
/*      */       {
/* 2211 */         this.array.add(0, paramT);
/* 2212 */         this.lastIndex += 1;
/*      */       }
/*      */       else
/*      */       {
/* 2216 */         this.array.set(--this.firstIndex, paramT);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void addLast(T paramT)
/*      */     {
/* 2223 */       if (this.firstIndex == -1) {
/* 2224 */         this.firstIndex = (this.lastIndex = this.array.size() / 2);
/* 2225 */         this.array.set(this.lastIndex, paramT);
/* 2226 */       } else if (this.lastIndex == this.array.size() - 1)
/*      */       {
/* 2229 */         this.array.add(++this.lastIndex, paramT);
/*      */       } else {
/* 2231 */         this.array.set(++this.lastIndex, paramT);
/*      */       }
/*      */     }
/*      */ 
/*      */     public int size() {
/* 2236 */       return this.firstIndex == -1 ? 0 : this.lastIndex - this.firstIndex + 1;
/*      */     }
/*      */ 
/*      */     public boolean isEmpty() {
/* 2240 */       return this.firstIndex == -1;
/*      */     }
/*      */ 
/*      */     public T get(int paramInt) {
/* 2244 */       if ((paramInt > this.lastIndex - this.firstIndex) || (paramInt < 0)) {
/* 2245 */         throw new ArrayIndexOutOfBoundsException();
/*      */       }
/*      */ 
/* 2248 */       return this.array.get(this.firstIndex + paramInt);
/*      */     }
/*      */ 
/*      */     public void clear() {
/* 2252 */       for (int i = 0; i < this.array.size(); i++) {
/* 2253 */         this.array.set(i, null);
/*      */       }
/*      */ 
/* 2256 */       this.firstIndex = (this.lastIndex = -1);
/*      */     }
/*      */ 
/*      */     public T removeFirst() {
/* 2260 */       if (isEmpty()) return null;
/* 2261 */       return remove(0);
/*      */     }
/*      */ 
/*      */     public T removeLast() {
/* 2265 */       if (isEmpty()) return null;
/* 2266 */       return remove(this.lastIndex - this.firstIndex);
/*      */     }
/*      */ 
/*      */     public T remove(int paramInt) {
/* 2270 */       if ((paramInt > this.lastIndex - this.firstIndex) || (paramInt < 0)) {
/* 2271 */         throw new ArrayIndexOutOfBoundsException();
/*      */       }
/*      */ 
/* 2278 */       if (paramInt == 0) {
/* 2279 */         localObject = this.array.get(this.firstIndex);
/* 2280 */         this.array.set(this.firstIndex, null);
/* 2281 */         if (this.firstIndex == this.lastIndex)
/* 2282 */           this.firstIndex = (this.lastIndex = -1);
/*      */         else {
/* 2284 */           this.firstIndex += 1;
/*      */         }
/* 2286 */         return localObject;
/* 2287 */       }if (paramInt == this.lastIndex - this.firstIndex)
/*      */       {
/* 2291 */         localObject = this.array.get(this.lastIndex);
/* 2292 */         this.array.set(this.lastIndex--, null);
/* 2293 */         return localObject;
/*      */       }
/*      */ 
/* 2297 */       Object localObject = this.array.get(this.firstIndex + paramInt);
/* 2298 */       this.array.set(this.firstIndex + paramInt, null);
/* 2299 */       for (int i = this.firstIndex + paramInt + 1; i <= this.lastIndex; i++) {
/* 2300 */         this.array.set(i - 1, this.array.get(i));
/*      */       }
/* 2302 */       this.array.set(this.lastIndex--, null);
/* 2303 */       return localObject;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class ClippedContainer extends Region
/*      */   {
/*      */     private VirtualFlow flow;
/*      */     private Node node;
/*      */     private final Group contentGroup;
/*      */     private final Rectangle clipRect;
/*      */ 
/*      */     public Node getNode()
/*      */     {
/* 2021 */       return this.node;
/*      */     }
/* 2023 */     public void setNode(Node paramNode) { this.node = paramNode;
/*      */ 
/* 2025 */       this.contentGroup.getChildren().clear();
/* 2026 */       this.contentGroup.getChildren().add(this.node); }
/*      */ 
/*      */     public void setClipX(double paramDouble)
/*      */     {
/* 2030 */       this.contentGroup.setLayoutX(-paramDouble);
/*      */     }
/*      */ 
/*      */     public void setClipY(double paramDouble) {
/* 2034 */       this.contentGroup.setLayoutY(-paramDouble);
/*      */     }
/*      */ 
/*      */     public ClippedContainer(VirtualFlow paramVirtualFlow)
/*      */     {
/* 2041 */       if (paramVirtualFlow == null) {
/* 2042 */         throw new IllegalArgumentException("VirtualFlow can not be null");
/*      */       }
/*      */ 
/* 2045 */       this.flow = paramVirtualFlow;
/*      */ 
/* 2049 */       this.clipRect = new Rectangle();
/* 2050 */       this.clipRect.setSmooth(false);
/* 2051 */       setClip(this.clipRect);
/*      */ 
/* 2057 */       this.contentGroup = new Group();
/* 2058 */       getChildren().add(this.contentGroup);
/*      */ 
/* 2060 */       super.widthProperty().addListener(new InvalidationListener() {
/*      */         public void invalidated(Observable paramAnonymousObservable) {
/* 2062 */           VirtualFlow.ClippedContainer.this.clipRect.setWidth(VirtualFlow.ClippedContainer.this.getWidth());
/*      */         }
/*      */       });
/* 2065 */       super.heightProperty().addListener(new InvalidationListener() {
/*      */         public void invalidated(Observable paramAnonymousObservable) {
/* 2067 */           VirtualFlow.ClippedContainer.this.clipRect.setHeight(VirtualFlow.ClippedContainer.this.getHeight());
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.VirtualFlow
 * JD-Core Version:    0.6.2
 */