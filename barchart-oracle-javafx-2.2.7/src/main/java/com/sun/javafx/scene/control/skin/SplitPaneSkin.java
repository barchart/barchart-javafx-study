/*      */ package com.sun.javafx.scene.control.skin;
/*      */ 
/*      */ import com.sun.javafx.scene.control.behavior.BehaviorBase;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ListChangeListener;
/*      */ import javafx.collections.ListChangeListener.Change;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.geometry.HPos;
/*      */ import javafx.geometry.Insets;
/*      */ import javafx.geometry.Orientation;
/*      */ import javafx.geometry.VPos;
/*      */ import javafx.scene.Cursor;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.control.SplitPane;
/*      */ import javafx.scene.control.SplitPane.Divider;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.scene.layout.StackPane;
/*      */ import javafx.scene.shape.Rectangle;
/*      */ 
/*      */ public class SplitPaneSkin extends SkinBase<SplitPane, BehaviorBase<SplitPane>>
/*      */ {
/*      */   private ObservableList<Content> contentRegions;
/*      */   private ObservableList<ContentDivider> contentDividers;
/*      */   private boolean horizontal;
/*  521 */   private double previousArea = -1.0D;
/*  522 */   private double previousWidth = -1.0D;
/*  523 */   private double previousHeight = -1.0D;
/*  524 */   private int lastDividerUpdate = 0;
/*  525 */   private boolean resize = false;
/*  526 */   private boolean checkDividerPos = true;
/*      */ 
/*      */   public SplitPaneSkin(SplitPane paramSplitPane)
/*      */   {
/*   58 */     super(paramSplitPane, new BehaviorBase(paramSplitPane));
/*   59 */     setManaged(false);
/*   60 */     this.horizontal = (((SplitPane)getSkinnable()).getOrientation() == Orientation.HORIZONTAL);
/*      */ 
/*   62 */     this.contentRegions = FXCollections.observableArrayList();
/*   63 */     this.contentDividers = FXCollections.observableArrayList();
/*      */ 
/*   65 */     int i = 0;
/*   66 */     for (Iterator localIterator = ((SplitPane)getSkinnable()).getItems().iterator(); localIterator.hasNext(); ) { localObject = (Node)localIterator.next();
/*   67 */       addContent(i++, (Node)localObject);
/*      */     }
/*   71 */     Object localObject;
/*   69 */     initializeContentListener();
/*      */ 
/*   71 */     for (localIterator = ((SplitPane)getSkinnable()).getDividers().iterator(); localIterator.hasNext(); ) { localObject = (SplitPane.Divider)localIterator.next();
/*   72 */       addDivider((SplitPane.Divider)localObject);
/*      */     }
/*      */ 
/*   75 */     registerChangeListener(paramSplitPane.orientationProperty(), "ORIENTATION");
/*   76 */     registerChangeListener(paramSplitPane.widthProperty(), "WIDTH");
/*   77 */     registerChangeListener(paramSplitPane.heightProperty(), "HEIGHT");
/*      */   }
/*      */ 
/*      */   private void addContent(int paramInt, Node paramNode) {
/*   81 */     Content localContent = new Content(paramNode);
/*   82 */     this.contentRegions.add(paramInt, localContent);
/*   83 */     getChildren().add(paramInt, localContent);
/*      */   }
/*      */ 
/*      */   private void removeContent(Node paramNode) {
/*   87 */     for (Content localContent : this.contentRegions)
/*   88 */       if (localContent.getContent().equals(paramNode)) {
/*   89 */         getChildren().remove(localContent);
/*   90 */         this.contentRegions.remove(localContent);
/*   91 */         break;
/*      */       }
/*      */   }
/*      */ 
/*      */   private void initializeContentListener()
/*      */   {
/*   97 */     ((SplitPane)getSkinnable()).getItems().addListener(new ListChangeListener()
/*      */     {
/*      */       public void onChanged(ListChangeListener.Change<? extends Node> paramAnonymousChange)
/*      */       {
/*      */         Object localObject;
/*      */         int i;
/*   99 */         while (paramAnonymousChange.next()) {
/*  100 */           for (Iterator localIterator1 = paramAnonymousChange.getRemoved().iterator(); localIterator1.hasNext(); ) { localObject = (Node)localIterator1.next();
/*  101 */             SplitPaneSkin.this.removeContent((Node)localObject);
/*      */           }
/*      */ 
/*  104 */           i = paramAnonymousChange.getFrom();
/*  105 */           for (localObject = paramAnonymousChange.getAddedSubList().iterator(); ((Iterator)localObject).hasNext(); ) { Node localNode = (Node)((Iterator)localObject).next();
/*  106 */             SplitPaneSkin.this.addContent(i++, localNode);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  111 */         SplitPaneSkin.this.removeAllDividers();
/*  112 */         for (Iterator localIterator2 = ((SplitPane)SplitPaneSkin.this.getSkinnable()).getDividers().iterator(); localIterator2.hasNext(); ) { localObject = (SplitPane.Divider)localIterator2.next();
/*  113 */           SplitPaneSkin.this.addDivider((SplitPane.Divider)localObject);
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void checkDividerPosition(ContentDivider paramContentDivider, double paramDouble1, double paramDouble2)
/*      */   {
/*  138 */     double d1 = paramContentDivider.prefWidth(-1.0D);
/*  139 */     Content localContent1 = getLeft(paramContentDivider);
/*  140 */     Content localContent2 = getRight(paramContentDivider);
/*  141 */     double d2 = this.horizontal ? localContent1.minWidth(-1.0D) : localContent1 == null ? 0.0D : localContent1.minHeight(-1.0D);
/*  142 */     double d3 = this.horizontal ? localContent2.minWidth(-1.0D) : localContent2 == null ? 0.0D : localContent2.minHeight(-1.0D);
/*  143 */     double d4 = localContent1.getContent() != null ? localContent1.getContent().maxHeight(-1.0D) : this.horizontal ? localContent1.getContent().maxWidth(-1.0D) : localContent1 == null ? 0.0D : 0.0D;
/*      */ 
/*  145 */     double d5 = localContent2.getContent() != null ? localContent2.getContent().maxHeight(-1.0D) : this.horizontal ? localContent2.getContent().maxWidth(-1.0D) : localContent2 == null ? 0.0D : 0.0D;
/*      */ 
/*  148 */     double d6 = 0.0D;
/*  149 */     double d7 = getSize();
/*  150 */     int i = this.contentDividers.indexOf(paramContentDivider);
/*      */ 
/*  152 */     if (i - 1 >= 0) {
/*  153 */       d6 = ((ContentDivider)this.contentDividers.get(i - 1)).getDividerPos();
/*  154 */       if (d6 == -1.0D)
/*      */       {
/*  156 */         d6 = getAbsoluteDividerPos((ContentDivider)this.contentDividers.get(i - 1));
/*      */       }
/*      */     }
/*  159 */     if (i + 1 < this.contentDividers.size()) {
/*  160 */       d7 = ((ContentDivider)this.contentDividers.get(i + 1)).getDividerPos();
/*  161 */       if (d7 == -1.0D)
/*      */       {
/*  163 */         d7 = getAbsoluteDividerPos((ContentDivider)this.contentDividers.get(i + 1));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  168 */     this.checkDividerPos = false;
/*      */     double d8;
/*      */     double d9;
/*      */     double d10;
/*      */     double d11;
/*  169 */     if (paramDouble1 > paramDouble2) {
/*  170 */       d8 = d6 == 0.0D ? d4 : d6 + d1 + d4;
/*  171 */       d9 = d7 - d3 - d1;
/*  172 */       d10 = Math.min(d8, d9);
/*  173 */       if (paramDouble1 >= d10) {
/*  174 */         setAbsoluteDividerPos(paramContentDivider, d10);
/*      */       } else {
/*  176 */         d11 = d7 - d5 - d1;
/*  177 */         if (paramDouble1 <= d11)
/*  178 */           setAbsoluteDividerPos(paramContentDivider, d11);
/*      */         else
/*  180 */           setAbsoluteDividerPos(paramContentDivider, paramDouble1);
/*      */       }
/*      */     }
/*      */     else {
/*  184 */       d8 = d7 - d5 - d1;
/*  185 */       d9 = d6 == 0.0D ? d2 : d6 + d2 + d1;
/*  186 */       d10 = Math.max(d8, d9);
/*  187 */       if (paramDouble1 <= d10) {
/*  188 */         setAbsoluteDividerPos(paramContentDivider, d10);
/*      */       } else {
/*  190 */         d11 = d6 + d4 + d1;
/*  191 */         if (paramDouble1 >= d11)
/*  192 */           setAbsoluteDividerPos(paramContentDivider, d11);
/*      */         else {
/*  194 */           setAbsoluteDividerPos(paramContentDivider, paramDouble1);
/*      */         }
/*      */       }
/*      */     }
/*  198 */     this.checkDividerPos = true;
/*      */   }
/*      */ 
/*      */   private void addDivider(SplitPane.Divider paramDivider) {
/*  202 */     ContentDivider localContentDivider = new ContentDivider(paramDivider);
/*  203 */     localContentDivider.setInitialPos(paramDivider.getPosition());
/*  204 */     localContentDivider.setDividerPos(-1.0D);
/*  205 */     PosPropertyListener localPosPropertyListener = new PosPropertyListener(localContentDivider);
/*  206 */     localContentDivider.setPosPropertyListener(localPosPropertyListener);
/*  207 */     paramDivider.positionProperty().addListener(localPosPropertyListener);
/*  208 */     initializeDivderEventHandlers(localContentDivider);
/*  209 */     this.contentDividers.add(localContentDivider);
/*  210 */     getChildren().add(localContentDivider);
/*      */   }
/*      */ 
/*      */   private void removeAllDividers() {
/*  214 */     ListIterator localListIterator = this.contentDividers.listIterator();
/*  215 */     while (localListIterator.hasNext()) {
/*  216 */       ContentDivider localContentDivider = (ContentDivider)localListIterator.next();
/*  217 */       getChildren().remove(localContentDivider);
/*  218 */       localContentDivider.getDivider().positionProperty().removeListener(localContentDivider.getPosPropertyListener());
/*  219 */       localListIterator.remove();
/*      */     }
/*  221 */     this.lastDividerUpdate = 0;
/*      */   }
/*      */ 
/*      */   private void initializeDivderEventHandlers(final ContentDivider paramContentDivider)
/*      */   {
/*  227 */     paramContentDivider.addEventHandler(MouseEvent.ANY, new EventHandler() {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  229 */         paramAnonymousMouseEvent.consume();
/*      */       }
/*      */     });
/*  233 */     paramContentDivider.setOnMousePressed(new EventHandler() {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  235 */         if (SplitPaneSkin.this.horizontal) {
/*  236 */           paramContentDivider.setInitialPos(paramContentDivider.getDividerPos());
/*  237 */           paramContentDivider.setPressPos(paramAnonymousMouseEvent.getSceneX());
/*      */         } else {
/*  239 */           paramContentDivider.setInitialPos(paramContentDivider.getDividerPos());
/*  240 */           paramContentDivider.setPressPos(paramAnonymousMouseEvent.getSceneY());
/*      */         }
/*  242 */         paramAnonymousMouseEvent.consume();
/*      */       }
/*      */     });
/*  246 */     paramContentDivider.setOnMouseDragged(new EventHandler() {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  248 */         double d1 = (SplitPaneSkin.this.horizontal ? paramAnonymousMouseEvent.getSceneX() : paramAnonymousMouseEvent.getSceneY()) - paramContentDivider.getPressPos();
/*  249 */         double d2 = Math.ceil(paramContentDivider.getInitialPos() + d1);
/*  250 */         SplitPaneSkin.this.checkDividerPos = true;
/*  251 */         SplitPaneSkin.this.setAbsoluteDividerPos(paramContentDivider, d2);
/*  252 */         paramAnonymousMouseEvent.consume();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private Content getLeft(ContentDivider paramContentDivider) {
/*  258 */     int i = this.contentDividers.indexOf(paramContentDivider);
/*  259 */     if (i != -1) {
/*  260 */       return (Content)this.contentRegions.get(i);
/*      */     }
/*  262 */     return null;
/*      */   }
/*      */ 
/*      */   private Content getRight(ContentDivider paramContentDivider) {
/*  266 */     int i = this.contentDividers.indexOf(paramContentDivider);
/*  267 */     if (i != -1) {
/*  268 */       return (Content)this.contentRegions.get(i + 1);
/*      */     }
/*  270 */     return null;
/*      */   }
/*      */ 
/*      */   protected void handleControlPropertyChanged(String paramString) {
/*  274 */     super.handleControlPropertyChanged(paramString);
/*  275 */     if (paramString == "ORIENTATION") {
/*  276 */       this.horizontal = (((SplitPane)getSkinnable()).getOrientation() == Orientation.HORIZONTAL);
/*  277 */       for (ContentDivider localContentDivider : this.contentDividers) {
/*  278 */         localContentDivider.setGrabberStyle(this.horizontal);
/*      */       }
/*  280 */       ((SplitPane)getSkinnable()).requestLayout();
/*  281 */     } else if ((paramString == "WIDTH") || (paramString == "HEIGHT")) {
/*  282 */       requestLayout();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setAbsoluteDividerPos(ContentDivider paramContentDivider, double paramDouble)
/*      */   {
/*  288 */     if ((getWidth() > 0.0D) && (getHeight() > 0.0D) && (paramContentDivider != null)) {
/*  289 */       SplitPane.Divider localDivider = paramContentDivider.getDivider();
/*  290 */       paramContentDivider.setDividerPos(paramDouble);
/*  291 */       double d1 = getSize();
/*  292 */       if (d1 != 0.0D)
/*      */       {
/*  295 */         double d2 = paramDouble + paramContentDivider.prefWidth(-1.0D) / 2.0D;
/*  296 */         localDivider.setPosition(d2 / d1);
/*      */       } else {
/*  298 */         localDivider.setPosition(0.0D);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private double getAbsoluteDividerPos(ContentDivider paramContentDivider)
/*      */   {
/*  307 */     if ((getWidth() > 0.0D) && (getHeight() > 0.0D) && (paramContentDivider != null)) {
/*  308 */       SplitPane.Divider localDivider = paramContentDivider.getDivider();
/*  309 */       double d = posToDividerPos(paramContentDivider, localDivider.getPosition());
/*  310 */       paramContentDivider.setDividerPos(d);
/*  311 */       return d;
/*      */     }
/*  313 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   private double posToDividerPos(ContentDivider paramContentDivider, double paramDouble)
/*      */   {
/*  319 */     double d = getSize() * paramDouble;
/*  320 */     if (paramDouble == 1.0D)
/*  321 */       d -= paramContentDivider.prefWidth(-1.0D);
/*      */     else {
/*  323 */       d -= paramContentDivider.prefWidth(-1.0D) / 2.0D;
/*      */     }
/*  325 */     return Math.round(d);
/*      */   }
/*      */ 
/*      */   private double totalMinSize() {
/*  329 */     double d1 = !this.contentDividers.isEmpty() ? this.contentDividers.size() * ((ContentDivider)this.contentDividers.get(0)).prefWidth(-1.0D) : 0.0D;
/*  330 */     double d2 = 0.0D;
/*  331 */     for (Content localContent : this.contentRegions) {
/*  332 */       if (this.horizontal)
/*  333 */         d2 += localContent.minWidth(-1.0D);
/*      */       else {
/*  335 */         d2 += localContent.minHeight(-1.0D);
/*      */       }
/*      */     }
/*  338 */     return d2 + d1;
/*      */   }
/*      */ 
/*      */   private double getSize() {
/*  342 */     double d = totalMinSize();
/*  343 */     if (this.horizontal) {
/*  344 */       if (getWidth() > d) {
/*  345 */         d = getWidth() - getInsets().getLeft() - getInsets().getRight();
/*      */       }
/*      */     }
/*  348 */     else if (getHeight() > d) {
/*  349 */       d = getHeight() - getInsets().getTop() - getInsets().getBottom();
/*      */     }
/*      */ 
/*  352 */     return d;
/*      */   }
/*      */ 
/*      */   private double distributeTo(List<Content> paramList, double paramDouble)
/*      */   {
/*  358 */     if (paramList.isEmpty()) {
/*  359 */       return paramDouble;
/*      */     }
/*      */ 
/*  362 */     paramDouble = snapSize(paramDouble);
/*  363 */     int i = (int)paramDouble / paramList.size();
/*      */ 
/*  366 */     while ((paramDouble > 0.0D) && (!paramList.isEmpty())) {
/*  367 */       Iterator localIterator = paramList.iterator();
/*  368 */       while (localIterator.hasNext()) {
/*  369 */         Content localContent = (Content)localIterator.next();
/*  370 */         double d1 = Math.min(this.horizontal ? localContent.maxWidth(-1.0D) : localContent.maxHeight(-1.0D), 1.7976931348623157E+308D);
/*  371 */         double d2 = this.horizontal ? localContent.minWidth(-1.0D) : localContent.minHeight(-1.0D);
/*      */ 
/*  374 */         if (localContent.getArea() >= d1) {
/*  375 */           localContent.setAvailable(localContent.getArea() - d2);
/*  376 */           localIterator.remove();
/*      */         }
/*      */         else
/*      */         {
/*  380 */           if (i >= d1 - localContent.getArea()) {
/*  381 */             paramDouble -= d1 - localContent.getArea();
/*  382 */             localContent.setArea(d1);
/*  383 */             localContent.setAvailable(d1 - d2);
/*  384 */             localIterator.remove();
/*      */           }
/*      */           else {
/*  387 */             localContent.setArea(localContent.getArea() + i);
/*  388 */             localContent.setAvailable(localContent.getArea() - d2);
/*  389 */             paramDouble -= i;
/*      */           }
/*  391 */           if ((int)paramDouble == 0)
/*  392 */             return paramDouble;
/*      */         }
/*      */       }
/*  395 */       if (paramList.isEmpty())
/*      */       {
/*  397 */         return paramDouble;
/*      */       }
/*  399 */       i = (int)paramDouble / paramList.size();
/*  400 */       int j = (int)paramDouble % paramList.size();
/*  401 */       if ((i == 0) && (j != 0)) {
/*  402 */         i = j;
/*  403 */         j = 0;
/*      */       }
/*      */     }
/*  406 */     return paramDouble;
/*      */   }
/*      */ 
/*      */   private double distributeFrom(double paramDouble, List<Content> paramList)
/*      */   {
/*  412 */     if (paramList.isEmpty()) {
/*  413 */       return paramDouble;
/*      */     }
/*      */ 
/*  416 */     paramDouble = snapSize(paramDouble);
/*  417 */     int i = (int)paramDouble / paramList.size();
/*      */ 
/*  420 */     while ((paramDouble > 0.0D) && (!paramList.isEmpty())) {
/*  421 */       Iterator localIterator = paramList.iterator();
/*  422 */       while (localIterator.hasNext()) {
/*  423 */         Content localContent = (Content)localIterator.next();
/*      */ 
/*  425 */         if (i >= localContent.getAvailable()) {
/*  426 */           localContent.setArea(localContent.getArea() - localContent.getAvailable());
/*  427 */           paramDouble -= localContent.getAvailable();
/*  428 */           localContent.setAvailable(0.0D);
/*  429 */           localIterator.remove();
/*      */         }
/*      */         else {
/*  432 */           localContent.setArea(localContent.getArea() - i);
/*  433 */           localContent.setAvailable(localContent.getAvailable() - i);
/*  434 */           paramDouble -= i;
/*      */         }
/*  436 */         if ((int)paramDouble == 0) {
/*  437 */           return paramDouble;
/*      */         }
/*      */       }
/*  440 */       if (paramList.isEmpty())
/*      */       {
/*  442 */         return paramDouble;
/*      */       }
/*  444 */       i = (int)paramDouble / paramList.size();
/*  445 */       int j = (int)paramDouble % paramList.size();
/*  446 */       if ((i == 0) && (j != 0)) {
/*  447 */         i = j;
/*  448 */         j = 0;
/*      */       }
/*      */     }
/*  451 */     return paramDouble;
/*      */   }
/*      */ 
/*      */   private void setupContentAndDividerForLayout()
/*      */   {
/*  456 */     double d1 = this.contentDividers.isEmpty() ? 0.0D : ((ContentDivider)this.contentDividers.get(0)).prefWidth(-1.0D);
/*  457 */     double d2 = 0.0D;
/*  458 */     double d3 = 0.0D;
/*  459 */     for (Iterator localIterator = this.contentRegions.iterator(); localIterator.hasNext(); ) { localObject = (Content)localIterator.next();
/*  460 */       if ((this.resize) && (!((Content)localObject).isResizableWithParent())) {
/*  461 */         ((Content)localObject).setArea(((Content)localObject).getResizableWithParentArea());
/*      */       }
/*      */ 
/*  464 */       ((Content)localObject).setX(d2);
/*  465 */       ((Content)localObject).setY(d3);
/*  466 */       if (this.horizontal)
/*  467 */         d2 += ((Content)localObject).getArea() + d1;
/*      */       else
/*  469 */         d3 += ((Content)localObject).getArea() + d1;
/*      */     }
/*      */     Object localObject;
/*  473 */     d2 = 0.0D;
/*  474 */     d3 = 0.0D;
/*      */ 
/*  477 */     this.checkDividerPos = false;
/*  478 */     for (int i = 0; i < this.contentDividers.size(); i++) {
/*  479 */       localObject = (ContentDivider)this.contentDividers.get(i);
/*  480 */       if (this.horizontal)
/*  481 */         d2 += getLeft((ContentDivider)localObject).getArea() + (i == 0 ? 0.0D : d1);
/*      */       else {
/*  483 */         d3 += getLeft((ContentDivider)localObject).getArea() + (i == 0 ? 0.0D : d1);
/*      */       }
/*  485 */       ((ContentDivider)localObject).setX(d2);
/*  486 */       ((ContentDivider)localObject).setY(d3);
/*  487 */       setAbsoluteDividerPos((ContentDivider)localObject, this.horizontal ? ((ContentDivider)localObject).getX() : ((ContentDivider)localObject).getY());
/*      */     }
/*  489 */     this.checkDividerPos = true;
/*      */   }
/*      */ 
/*      */   private void layoutDividersAndContent(double paramDouble1, double paramDouble2) {
/*  493 */     double d1 = getInsets().getLeft();
/*  494 */     double d2 = getInsets().getTop();
/*  495 */     double d3 = this.contentDividers.isEmpty() ? 0.0D : ((ContentDivider)this.contentDividers.get(0)).prefWidth(-1.0D);
/*      */ 
/*  497 */     for (Iterator localIterator = this.contentRegions.iterator(); localIterator.hasNext(); ) { localObject = (Content)localIterator.next();
/*      */ 
/*  499 */       if (this.horizontal) {
/*  500 */         layoutInArea((Node)localObject, ((Content)localObject).getX() + d1, ((Content)localObject).getY() + d2, ((Content)localObject).getArea(), paramDouble2, 0.0D, HPos.CENTER, VPos.CENTER);
/*      */       }
/*      */       else
/*  503 */         layoutInArea((Node)localObject, ((Content)localObject).getX() + d1, ((Content)localObject).getY() + d2, paramDouble1, ((Content)localObject).getArea(), 0.0D, HPos.CENTER, VPos.CENTER);
/*      */     }
/*  507 */     Object localObject;
/*  507 */     for (localIterator = this.contentDividers.iterator(); localIterator.hasNext(); ) { localObject = (ContentDivider)localIterator.next();
/*      */ 
/*  509 */       if (this.horizontal) {
/*  510 */         ((ContentDivider)localObject).resize(d3, paramDouble2);
/*  511 */         positionInArea((Node)localObject, ((ContentDivider)localObject).getX() + d1, ((ContentDivider)localObject).getY() + d2, d3, paramDouble2, 0.0D, HPos.CENTER, VPos.CENTER);
/*      */       }
/*      */       else {
/*  514 */         ((ContentDivider)localObject).resize(paramDouble1, d3);
/*  515 */         positionInArea((Node)localObject, ((ContentDivider)localObject).getX() + d1, ((ContentDivider)localObject).getY() + d2, paramDouble1, d3, 0.0D, HPos.CENTER, VPos.CENTER);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void layoutChildren()
/*      */   {
/*  529 */     if ((((SplitPane)getSkinnable()).isVisible()) && (this.horizontal ? getWidth() != 0.0D : getHeight() != 0.0D)) { if (!this.contentRegions.isEmpty()); }
/*      */     else {
/*  532 */       return;
/*      */     }
/*      */ 
/*  535 */     double d1 = this.contentDividers.isEmpty() ? 0.0D : ((ContentDivider)this.contentDividers.get(0)).prefWidth(-1.0D);
/*  536 */     double d2 = getWidth() - (getInsets().getLeft() + getInsets().getRight());
/*  537 */     double d3 = getHeight() - (getInsets().getTop() + getInsets().getBottom());
/*      */     double d12;
/*      */     Content localContent2;
/*  539 */     if ((this.contentDividers.size() > 0) && (this.previousArea != -1.0D) && (this.previousArea != getWidth() * getHeight()))
/*      */     {
/*  541 */       ArrayList localArrayList1 = new ArrayList();
/*  542 */       for (Content localContent1 : this.contentRegions) {
/*  543 */         if (localContent1.isResizableWithParent()) {
/*  544 */           localArrayList1.add(localContent1);
/*      */         }
/*      */       }
/*      */ 
/*  548 */       double d5 = this.horizontal ? getWidth() - this.previousWidth : getHeight() - this.previousHeight;
/*  549 */       int j = d5 > 0.0D ? 1 : 0;
/*      */ 
/*  551 */       d5 = Math.abs(d5);
/*      */ 
/*  553 */       if (!localArrayList1.isEmpty()) {
/*  554 */         int k = (int)d5 / localArrayList1.size();
/*  555 */         int n = (int)d5 % localArrayList1.size();
/*  556 */         int i2 = 0;
/*  557 */         if (k == 0) {
/*  558 */           k = n;
/*  559 */           i2 = n;
/*  560 */           n = 0;
/*      */         } else {
/*  562 */           i2 = k * localArrayList1.size();
/*      */         }
/*      */ 
/*  565 */         while ((i2 > 0) && (!localArrayList1.isEmpty())) {
/*  566 */           if (j != 0) {
/*  567 */             this.lastDividerUpdate += 1;
/*      */           } else {
/*  569 */             this.lastDividerUpdate -= 1;
/*  570 */             if (this.lastDividerUpdate < 0) {
/*  571 */               this.lastDividerUpdate = (this.contentRegions.size() - 1);
/*      */             }
/*      */           }
/*  574 */           int i3 = this.lastDividerUpdate % this.contentRegions.size();
/*  575 */           Content localContent3 = (Content)this.contentRegions.get(i3);
/*  576 */           if ((localContent3.isResizableWithParent()) && (localArrayList1.contains(localContent3))) {
/*  577 */             double d11 = localContent3.getArea();
/*  578 */             if (j != 0) {
/*  579 */               d12 = this.horizontal ? localContent3.maxWidth(-1.0D) : localContent3.maxHeight(-1.0D);
/*  580 */               if (d11 + k <= d12) {
/*  581 */                 d11 += k;
/*      */               } else {
/*  583 */                 localArrayList1.remove(localContent3);
/*  584 */                 continue;
/*      */               }
/*      */             } else {
/*  587 */               d12 = this.horizontal ? localContent3.minWidth(-1.0D) : localContent3.minHeight(-1.0D);
/*  588 */               if (d11 - k >= d12) {
/*  589 */                 d11 -= k;
/*      */               } else {
/*  591 */                 localArrayList1.remove(localContent3);
/*  592 */                 continue;
/*      */               }
/*      */ 
/*  595 */               localContent3.setArea(d11);
/*  596 */               i2 -= k;
/*  597 */               if ((i2 == 0) && (n != 0)) {
/*  598 */                 k = n;
/*  599 */                 i2 = n;
/*  600 */                 n = 0; } else {
/*  601 */                 if (i2 == 0)
/*      */                   break;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  608 */       this.previousArea = (getWidth() * getHeight());
/*  609 */       this.previousWidth = getWidth();
/*  610 */       this.previousHeight = getHeight();
/*      */ 
/*  614 */       for (Iterator localIterator2 = this.contentRegions.iterator(); localIterator2.hasNext(); ) { localContent2 = (Content)localIterator2.next();
/*  615 */         localContent2.setResizableWithParentArea(localContent2.getArea());
/*  616 */         localContent2.setAvailable(0.0D);
/*      */       }
/*  618 */       this.resize = true;
/*      */     } else {
/*  620 */       this.previousArea = (getWidth() * getHeight());
/*  621 */       this.previousWidth = getWidth();
/*  622 */       this.previousHeight = getHeight();
/*      */     }
/*      */ 
/*  627 */     double d4 = totalMinSize();
/*      */     double d8;
/*  628 */     if (d4 > (this.horizontal ? d2 : d3)) {
/*  629 */       double d6 = 0.0D;
/*  630 */       for (int m = 0; m < this.contentRegions.size(); m++) {
/*  631 */         localContent2 = (Content)this.contentRegions.get(m);
/*  632 */         d8 = this.horizontal ? localContent2.minWidth(-1.0D) : localContent2.minHeight(-1.0D);
/*  633 */         d6 = d8 / d4;
/*  634 */         localContent2.setArea(snapSpace(d6 * (this.horizontal ? d2 : d3)));
/*  635 */         localContent2.setAvailable(0.0D);
/*      */       }
/*  637 */       setupContentAndDividerForLayout();
/*  638 */       layoutDividersAndContent(d2, d3);
/*  639 */       this.resize = false;
/*  640 */       return;
/*      */     }
/*      */ 
/*  643 */     for (int i = 0; i < 10; i++)
/*      */     {
/*  645 */       Object localObject1 = null;
/*  646 */       ContentDivider localContentDivider = null;
/*  647 */       for (int i1 = 0; i1 < this.contentRegions.size(); i1++) {
/*  648 */         d8 = 0.0D;
/*  649 */         if (i1 < this.contentDividers.size()) {
/*  650 */           localContentDivider = (ContentDivider)this.contentDividers.get(i1);
/*  651 */           if (i1 == 0)
/*      */           {
/*  653 */             d8 = getAbsoluteDividerPos(localContentDivider);
/*      */           }
/*      */           else {
/*  656 */             if (getAbsoluteDividerPos(localContentDivider) <= getAbsoluteDividerPos((ContentDivider)localObject1))
/*      */             {
/*  660 */               double d10 = getAbsoluteDividerPos((ContentDivider)localObject1);
/*  661 */               this.checkDividerPos = true;
/*  662 */               setAbsoluteDividerPos(localContentDivider, d10 + d1);
/*      */             }
/*  664 */             d8 = getAbsoluteDividerPos(localContentDivider) - (getAbsoluteDividerPos((ContentDivider)localObject1) + d1);
/*      */           }
/*  666 */         } else if (i1 == this.contentDividers.size())
/*      */         {
/*  668 */           d8 = (this.horizontal ? d2 : d3) - (localObject1 != null ? getAbsoluteDividerPos((ContentDivider)localObject1) + d1 : 0.0D);
/*      */         }
/*  670 */         if (!this.resize) {
/*  671 */           ((Content)this.contentRegions.get(i1)).setArea(d8);
/*      */         }
/*  673 */         localObject1 = localContentDivider;
/*      */       }
/*      */ 
/*  681 */       double d7 = 0.0D;
/*  682 */       double d9 = 0.0D;
/*  683 */       for (Object localObject2 = this.contentRegions.iterator(); ((Iterator)localObject2).hasNext(); ) { localObject3 = (Content)((Iterator)localObject2).next();
/*  684 */         d12 = 0.0D;
/*  685 */         double d14 = 0.0D;
/*  686 */         if (localObject3 != null) {
/*  687 */           d12 = this.horizontal ? ((Content)localObject3).maxWidth(-1.0D) : ((Content)localObject3).maxHeight(-1.0D);
/*  688 */           d14 = this.horizontal ? ((Content)localObject3).minWidth(-1.0D) : ((Content)localObject3).minHeight(-1.0D);
/*      */         }
/*      */ 
/*  691 */         if (((Content)localObject3).getArea() >= d12)
/*      */         {
/*  693 */           d9 += ((Content)localObject3).getArea() - d12;
/*  694 */           ((Content)localObject3).setArea(d12);
/*      */         }
/*  696 */         ((Content)localObject3).setAvailable(((Content)localObject3).getArea() - d14);
/*  697 */         if (((Content)localObject3).getAvailable() < 0.0D) {
/*  698 */           d7 += ((Content)localObject3).getAvailable();
/*      */         }
/*      */       }
/*      */ 
/*  702 */       d7 = Math.abs(d7);
/*      */ 
/*  705 */       localObject2 = new ArrayList();
/*  706 */       Object localObject3 = new ArrayList();
/*  707 */       ArrayList localArrayList2 = new ArrayList();
/*  708 */       double d13 = 0.0D;
/*  709 */       for (Iterator localIterator3 = this.contentRegions.iterator(); localIterator3.hasNext(); ) { localObject4 = (Content)localIterator3.next();
/*  710 */         if (((Content)localObject4).getAvailable() >= 0.0D) {
/*  711 */           d13 += ((Content)localObject4).getAvailable();
/*  712 */           ((List)localObject2).add(localObject4);
/*      */         }
/*      */ 
/*  715 */         if ((this.resize) && (!((Content)localObject4).isResizableWithParent()))
/*      */         {
/*  718 */           if (((Content)localObject4).getArea() >= ((Content)localObject4).getResizableWithParentArea()) {
/*  719 */             d9 += ((Content)localObject4).getArea() - ((Content)localObject4).getResizableWithParentArea();
/*      */           }
/*      */           else
/*      */           {
/*  723 */             d7 += ((Content)localObject4).getResizableWithParentArea() - ((Content)localObject4).getArea();
/*      */           }
/*  725 */           ((Content)localObject4).setAvailable(0.0D);
/*      */         }
/*      */ 
/*  728 */         if (this.resize) {
/*  729 */           if (((Content)localObject4).isResizableWithParent())
/*  730 */             ((List)localObject3).add(localObject4);
/*      */         }
/*      */         else {
/*  733 */           ((List)localObject3).add(localObject4);
/*      */         }
/*      */ 
/*  736 */         if (((Content)localObject4).getAvailable() < 0.0D) {
/*  737 */           localArrayList2.add(localObject4);
/*      */         }
/*      */       }
/*      */ 
/*  741 */       if (d9 > 0.0D) {
/*  742 */         d9 = distributeTo((List)localObject3, d9);
/*      */ 
/*  745 */         d7 = 0.0D;
/*  746 */         localArrayList2.clear();
/*  747 */         d13 = 0.0D;
/*  748 */         ((List)localObject2).clear();
/*  749 */         for (localIterator3 = this.contentRegions.iterator(); localIterator3.hasNext(); ) { localObject4 = (Content)localIterator3.next();
/*  750 */           if (((Content)localObject4).getAvailable() < 0.0D) {
/*  751 */             d7 += ((Content)localObject4).getAvailable();
/*  752 */             localArrayList2.add(localObject4);
/*      */           } else {
/*  754 */             d13 += ((Content)localObject4).getAvailable();
/*  755 */             ((List)localObject2).add(localObject4);
/*      */           }
/*      */         }
/*  758 */         d7 = Math.abs(d7);
/*      */       }
/*      */       Object localObject5;
/*  761 */       if (d13 >= d7) {
/*  762 */         for (localIterator3 = localArrayList2.iterator(); localIterator3.hasNext(); ) { localObject4 = (Content)localIterator3.next();
/*  763 */           double d16 = this.horizontal ? ((Content)localObject4).minWidth(-1.0D) : ((Content)localObject4).minHeight(-1.0D);
/*  764 */           ((Content)localObject4).setArea(d16);
/*  765 */           ((Content)localObject4).setAvailable(0.0D);
/*      */         }
/*      */ 
/*  770 */         if ((d7 > 0.0D) && (!localArrayList2.isEmpty())) {
/*  771 */           distributeFrom(d7, (List)localObject2);
/*      */         }
/*      */ 
/*  777 */         if (this.resize) {
/*  778 */           double d15 = 0.0D;
/*  779 */           for (localObject5 = this.contentRegions.iterator(); ((Iterator)localObject5).hasNext(); ) { Content localContent4 = (Content)((Iterator)localObject5).next();
/*  780 */             if (localContent4.isResizableWithParent())
/*  781 */               d15 += localContent4.getArea();
/*      */             else {
/*  783 */               d15 += localContent4.getResizableWithParentArea();
/*      */             }
/*      */           }
/*  786 */           d15 += d1 * this.contentDividers.size();
/*  787 */           if (d15 < (this.horizontal ? d2 : d3)) {
/*  788 */             d9 += (this.horizontal ? d2 : d3) - d15;
/*  789 */             distributeTo((List)localObject3, d9);
/*      */           } else {
/*  791 */             d7 += d15 - (this.horizontal ? d2 : d3);
/*  792 */             distributeFrom(d7, (List)localObject3);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  797 */       setupContentAndDividerForLayout();
/*      */ 
/*  800 */       int i4 = 1;
/*  801 */       for (Object localObject4 = this.contentRegions.iterator(); ((Iterator)localObject4).hasNext(); ) { localObject5 = (Content)((Iterator)localObject4).next();
/*  802 */         double d17 = this.horizontal ? ((Content)localObject5).maxWidth(-1.0D) : ((Content)localObject5).maxHeight(-1.0D);
/*  803 */         double d18 = this.horizontal ? ((Content)localObject5).minWidth(-1.0D) : ((Content)localObject5).minHeight(-1.0D);
/*  804 */         if ((((Content)localObject5).getArea() < d18) || (((Content)localObject5).getArea() > d17)) {
/*  805 */           i4 = 0;
/*  806 */           break;
/*      */         }
/*      */       }
/*  809 */       if (i4 != 0)
/*      */       {
/*      */         break;
/*      */       }
/*      */     }
/*  814 */     layoutDividersAndContent(d2, d3);
/*  815 */     this.resize = false;
/*      */   }
/*      */ 
/*      */   protected double computeMinWidth(double paramDouble) {
/*  819 */     double d1 = 0.0D;
/*  820 */     double d2 = 0.0D;
/*  821 */     for (Iterator localIterator = this.contentRegions.iterator(); localIterator.hasNext(); ) { localObject = (Content)localIterator.next();
/*  822 */       d1 += ((Content)localObject).minWidth(-1.0D);
/*  823 */       d2 = Math.max(d2, ((Content)localObject).minWidth(-1.0D));
/*      */     }
/*  825 */     Object localObject;
/*  825 */     for (localIterator = this.contentDividers.iterator(); localIterator.hasNext(); ) { localObject = (ContentDivider)localIterator.next();
/*  826 */       d1 += ((ContentDivider)localObject).prefWidth(-1.0D);
/*      */     }
/*  828 */     if (this.horizontal) {
/*  829 */       return d1 + getInsets().getLeft() + getInsets().getRight();
/*      */     }
/*  831 */     return d2 + getInsets().getLeft() + getInsets().getRight();
/*      */   }
/*      */ 
/*      */   protected double computeMinHeight(double paramDouble)
/*      */   {
/*  836 */     double d1 = 0.0D;
/*  837 */     double d2 = 0.0D;
/*  838 */     for (Iterator localIterator = this.contentRegions.iterator(); localIterator.hasNext(); ) { localObject = (Content)localIterator.next();
/*  839 */       d1 += ((Content)localObject).minHeight(-1.0D);
/*  840 */       d2 = Math.max(d2, ((Content)localObject).minHeight(-1.0D));
/*      */     }
/*  842 */     Object localObject;
/*  842 */     for (localIterator = this.contentDividers.iterator(); localIterator.hasNext(); ) { localObject = (ContentDivider)localIterator.next();
/*  843 */       d1 += ((ContentDivider)localObject).prefWidth(-1.0D);
/*      */     }
/*  845 */     if (this.horizontal) {
/*  846 */       return d2 + getInsets().getTop() + getInsets().getBottom();
/*      */     }
/*  848 */     return d1 + getInsets().getTop() + getInsets().getBottom();
/*      */   }
/*      */ 
/*      */   protected double computePrefWidth(double paramDouble)
/*      */   {
/*  853 */     double d1 = 0.0D;
/*  854 */     double d2 = 0.0D;
/*  855 */     for (Iterator localIterator = this.contentRegions.iterator(); localIterator.hasNext(); ) { localObject = (Content)localIterator.next();
/*  856 */       d1 += ((Content)localObject).prefWidth(-1.0D);
/*  857 */       d2 = Math.max(d2, ((Content)localObject).prefWidth(-1.0D));
/*      */     }
/*  859 */     Object localObject;
/*  859 */     for (localIterator = this.contentDividers.iterator(); localIterator.hasNext(); ) { localObject = (ContentDivider)localIterator.next();
/*  860 */       d1 += ((ContentDivider)localObject).prefWidth(-1.0D);
/*      */     }
/*  862 */     if (this.horizontal) {
/*  863 */       return d1 + getInsets().getLeft() + getInsets().getRight();
/*      */     }
/*  865 */     return d2 + getInsets().getLeft() + getInsets().getRight();
/*      */   }
/*      */ 
/*      */   protected double computePrefHeight(double paramDouble)
/*      */   {
/*  870 */     double d1 = 0.0D;
/*  871 */     double d2 = 0.0D;
/*  872 */     for (Iterator localIterator = this.contentRegions.iterator(); localIterator.hasNext(); ) { localObject = (Content)localIterator.next();
/*  873 */       d1 += ((Content)localObject).prefHeight(-1.0D);
/*  874 */       d2 = Math.max(d2, ((Content)localObject).prefHeight(-1.0D));
/*      */     }
/*  876 */     Object localObject;
/*  876 */     for (localIterator = this.contentDividers.iterator(); localIterator.hasNext(); ) { localObject = (ContentDivider)localIterator.next();
/*  877 */       d1 += ((ContentDivider)localObject).prefWidth(-1.0D);
/*      */     }
/*  879 */     if (this.horizontal) {
/*  880 */       return d2 + getInsets().getTop() + getInsets().getBottom();
/*      */     }
/*  882 */     return d1 + getInsets().getTop() + getInsets().getBottom();
/*      */   }
/*      */ 
/*      */   private void printDividerPositions()
/*      */   {
/*  887 */     for (int i = 0; i < this.contentDividers.size(); i++) {
/*  888 */       System.out.print("DIVIDER[" + i + "] " + ((ContentDivider)this.contentDividers.get(i)).getDividerPos() + " ");
/*      */     }
/*  890 */     System.out.println("");
/*      */   }
/*      */ 
/*      */   private void printAreaAndAvailable() {
/*  894 */     for (int i = 0; i < this.contentRegions.size(); i++) {
/*  895 */       System.out.print("AREA[" + i + "] " + ((Content)this.contentRegions.get(i)).getArea() + " ");
/*      */     }
/*  897 */     System.out.println("");
/*  898 */     for (i = 0; i < this.contentRegions.size(); i++) {
/*  899 */       System.out.print("AVAILABLE[" + i + "] " + ((Content)this.contentRegions.get(i)).getAvailable() + " ");
/*      */     }
/*  901 */     System.out.println("");
/*  902 */     for (i = 0; i < this.contentRegions.size(); i++) {
/*  903 */       System.out.print("RESIZABLEWTIHPARENT[" + i + "] " + ((Content)this.contentRegions.get(i)).getResizableWithParentArea() + " ");
/*      */     }
/*  905 */     System.out.println("");
/*      */   }
/*      */ 
/*      */   class Content extends StackPane
/*      */   {
/*      */     private Node content;
/*      */     private Rectangle clipRect;
/*      */     private double x;
/*      */     private double y;
/*      */     private double area;
/*      */     private double resizableWithParentArea;
/*      */     private double available;
/*      */ 
/*      */     public Content(Node arg2)
/*      */     {
/* 1065 */       this.clipRect = new Rectangle();
/* 1066 */       setClip(this.clipRect);
/*      */       Object localObject;
/* 1067 */       this.content = localObject;
/* 1068 */       if (localObject != null) {
/* 1069 */         setId(localObject.getId());
/* 1070 */         getChildren().add(localObject);
/*      */       }
/* 1072 */       this.x = 0.0D;
/* 1073 */       this.y = 0.0D;
/*      */     }
/*      */ 
/*      */     public Node getContent() {
/* 1077 */       return this.content;
/*      */     }
/*      */ 
/*      */     public double getX() {
/* 1081 */       return this.x;
/*      */     }
/*      */ 
/*      */     public void setX(double paramDouble) {
/* 1085 */       this.x = paramDouble;
/*      */     }
/*      */ 
/*      */     public double getY() {
/* 1089 */       return this.y;
/*      */     }
/*      */ 
/*      */     public void setY(double paramDouble) {
/* 1093 */       this.y = paramDouble;
/*      */     }
/*      */ 
/*      */     public double getArea()
/*      */     {
/* 1099 */       return this.area;
/*      */     }
/*      */ 
/*      */     public void setArea(double paramDouble) {
/* 1103 */       this.area = paramDouble;
/*      */     }
/*      */ 
/*      */     public double getAvailable()
/*      */     {
/* 1109 */       return this.available;
/*      */     }
/*      */ 
/*      */     public void setAvailable(double paramDouble) {
/* 1113 */       this.available = paramDouble;
/*      */     }
/*      */ 
/*      */     public boolean isResizableWithParent() {
/* 1117 */       return SplitPane.isResizableWithParent(this.content).booleanValue();
/*      */     }
/*      */ 
/*      */     public double getResizableWithParentArea() {
/* 1121 */       return this.resizableWithParentArea;
/*      */     }
/*      */ 
/*      */     public void setResizableWithParentArea(double paramDouble)
/*      */     {
/* 1127 */       if (!isResizableWithParent())
/* 1128 */         this.resizableWithParentArea = paramDouble;
/*      */       else
/* 1130 */         this.resizableWithParentArea = 0.0D;
/*      */     }
/*      */ 
/*      */     protected void setWidth(double paramDouble)
/*      */     {
/* 1135 */       super.setWidth(paramDouble);
/* 1136 */       this.clipRect.setWidth(paramDouble);
/*      */     }
/*      */ 
/*      */     protected void setHeight(double paramDouble) {
/* 1140 */       super.setHeight(paramDouble);
/* 1141 */       this.clipRect.setHeight(paramDouble);
/*      */     }
/*      */ 
/*      */     protected double computeMaxWidth(double paramDouble) {
/* 1145 */       return snapSize(this.content.maxWidth(paramDouble));
/*      */     }
/*      */ 
/*      */     protected double computeMaxHeight(double paramDouble) {
/* 1149 */       return snapSize(this.content.maxHeight(paramDouble));
/*      */     }
/*      */   }
/*      */ 
/*      */   class ContentDivider extends StackPane
/*      */   {
/*      */     private double initialPos;
/*      */     private double dividerPos;
/*      */     private double pressPos;
/*      */     private SplitPane.Divider d;
/*      */     private StackPane grabber;
/*      */     private double x;
/*      */     private double y;
/*      */     private ChangeListener listener;
/*      */ 
/*      */     public ContentDivider(SplitPane.Divider arg2)
/*      */     {
/*  919 */       getStyleClass().setAll(new String[] { "split-pane-divider" });
/*      */       Object localObject;
/*  921 */       this.d = localObject;
/*  922 */       this.initialPos = 0.0D;
/*  923 */       this.dividerPos = 0.0D;
/*  924 */       this.pressPos = 0.0D;
/*      */ 
/*  926 */       this.grabber = new StackPane() {
/*      */         protected double computeMinWidth(double paramAnonymousDouble) {
/*  928 */           return 0.0D;
/*      */         }
/*      */ 
/*      */         protected double computeMinHeight(double paramAnonymousDouble) {
/*  932 */           return 0.0D;
/*      */         }
/*      */ 
/*      */         protected double computePrefWidth(double paramAnonymousDouble) {
/*  936 */           return getInsets().getLeft() + getInsets().getRight();
/*      */         }
/*      */ 
/*      */         protected double computePrefHeight(double paramAnonymousDouble) {
/*  940 */           return getInsets().getTop() + getInsets().getBottom();
/*      */         }
/*      */ 
/*      */         protected double computeMaxWidth(double paramAnonymousDouble) {
/*  944 */           return computePrefWidth(-1.0D);
/*      */         }
/*      */ 
/*      */         protected double computeMaxHeight(double paramAnonymousDouble) {
/*  948 */           return computePrefHeight(-1.0D);
/*      */         }
/*      */       };
/*  951 */       setGrabberStyle(SplitPaneSkin.this.horizontal);
/*  952 */       getChildren().add(this.grabber);
/*      */     }
/*      */ 
/*      */     public SplitPane.Divider getDivider()
/*      */     {
/*  958 */       return this.d;
/*      */     }
/*      */ 
/*      */     public final void setGrabberStyle(boolean paramBoolean) {
/*  962 */       this.grabber.getStyleClass().clear();
/*  963 */       this.grabber.getStyleClass().setAll(new String[] { "vertical-grabber" });
/*  964 */       setCursor(Cursor.V_RESIZE);
/*  965 */       if (paramBoolean) {
/*  966 */         this.grabber.getStyleClass().setAll(new String[] { "horizontal-grabber" });
/*  967 */         setCursor(Cursor.H_RESIZE);
/*      */       }
/*      */     }
/*      */ 
/*      */     public double getInitialPos() {
/*  972 */       return this.initialPos;
/*      */     }
/*      */ 
/*      */     public void setInitialPos(double paramDouble) {
/*  976 */       this.initialPos = paramDouble;
/*      */     }
/*      */ 
/*      */     public double getDividerPos() {
/*  980 */       return this.dividerPos;
/*      */     }
/*      */ 
/*      */     public void setDividerPos(double paramDouble) {
/*  984 */       this.dividerPos = paramDouble;
/*      */     }
/*      */ 
/*      */     public double getPressPos() {
/*  988 */       return this.pressPos;
/*      */     }
/*      */ 
/*      */     public void setPressPos(double paramDouble) {
/*  992 */       this.pressPos = paramDouble;
/*      */     }
/*      */ 
/*      */     public double getX()
/*      */     {
/*  997 */       return this.x;
/*      */     }
/*      */ 
/*      */     public void setX(double paramDouble) {
/* 1001 */       this.x = paramDouble;
/*      */     }
/*      */ 
/*      */     public double getY() {
/* 1005 */       return this.y;
/*      */     }
/*      */ 
/*      */     public void setY(double paramDouble) {
/* 1009 */       this.y = paramDouble;
/*      */     }
/*      */ 
/*      */     public ChangeListener getPosPropertyListener() {
/* 1013 */       return this.listener;
/*      */     }
/*      */ 
/*      */     public void setPosPropertyListener(ChangeListener paramChangeListener) {
/* 1017 */       this.listener = paramChangeListener;
/*      */     }
/*      */ 
/*      */     protected double computeMinWidth(double paramDouble) {
/* 1021 */       return computePrefWidth(paramDouble);
/*      */     }
/*      */ 
/*      */     protected double computeMinHeight(double paramDouble) {
/* 1025 */       return computePrefHeight(paramDouble);
/*      */     }
/*      */ 
/*      */     protected double computePrefWidth(double paramDouble) {
/* 1029 */       return snapSpace(getInsets().getLeft()) + snapSpace(getInsets().getRight());
/*      */     }
/*      */ 
/*      */     protected double computePrefHeight(double paramDouble) {
/* 1033 */       return snapSpace(getInsets().getTop()) + snapSpace(getInsets().getBottom());
/*      */     }
/*      */ 
/*      */     protected double computeMaxWidth(double paramDouble) {
/* 1037 */       return computePrefWidth(paramDouble);
/*      */     }
/*      */ 
/*      */     protected double computeMaxHeight(double paramDouble) {
/* 1041 */       return computePrefHeight(paramDouble);
/*      */     }
/*      */ 
/*      */     protected void layoutChildren() {
/* 1045 */       double d1 = this.grabber.prefWidth(-1.0D);
/* 1046 */       double d2 = this.grabber.prefHeight(-1.0D);
/* 1047 */       double d3 = (getWidth() - d1) / 2.0D;
/* 1048 */       double d4 = (getHeight() - d2) / 2.0D;
/* 1049 */       this.grabber.resize(d1, d2);
/* 1050 */       positionInArea(this.grabber, d3, d4, d1, d2, 0.0D, HPos.CENTER, VPos.CENTER);
/*      */     }
/*      */   }
/*      */ 
/*      */   class PosPropertyListener
/*      */     implements ChangeListener<Double>
/*      */   {
/*      */     SplitPaneSkin.ContentDivider divider;
/*      */ 
/*      */     public PosPropertyListener(SplitPaneSkin.ContentDivider arg2)
/*      */     {
/*      */       Object localObject;
/*  124 */       this.divider = localObject;
/*      */     }
/*      */ 
/*      */     public void changed(ObservableValue paramObservableValue, Double paramDouble1, Double paramDouble2)
/*      */     {
/*  130 */       if (SplitPaneSkin.this.checkDividerPos) {
/*  131 */         SplitPaneSkin.this.checkDividerPosition(this.divider, SplitPaneSkin.access$500(SplitPaneSkin.this, this.divider, paramDouble2.doubleValue()), SplitPaneSkin.access$500(SplitPaneSkin.this, this.divider, paramDouble1.doubleValue()));
/*      */       }
/*  133 */       SplitPaneSkin.this.requestLayout();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.SplitPaneSkin
 * JD-Core Version:    0.6.2
 */