/*     */ package javafx.scene.chart;
/*     */ 
/*     */ import com.sun.javafx.charts.Legend;
/*     */ import com.sun.javafx.charts.Legend.LegendItem;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javafx.animation.FadeTransition;
/*     */ import javafx.animation.Interpolator;
/*     */ import javafx.animation.KeyFrame;
/*     */ import javafx.animation.KeyValue;
/*     */ import javafx.animation.Timeline;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Group;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.shape.ClosePath;
/*     */ import javafx.scene.shape.LineTo;
/*     */ import javafx.scene.shape.MoveTo;
/*     */ import javafx.scene.shape.Path;
/*     */ import javafx.scene.shape.StrokeLineJoin;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class StackedAreaChart<X, Y> extends XYChart<X, Y>
/*     */ {
/*  64 */   private Map<XYChart.Series, DoubleProperty> seriesYMultiplierMap = new HashMap();
/*  65 */   private Legend legend = new Legend();
/*     */ 
/*     */   public StackedAreaChart(Axis<X> paramAxis, Axis<Y> paramAxis1)
/*     */   {
/*  76 */     this(paramAxis, paramAxis1, FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public StackedAreaChart(Axis<X> paramAxis, Axis<Y> paramAxis1, ObservableList<XYChart.Series<X, Y>> paramObservableList)
/*     */   {
/*  87 */     super(paramAxis, paramAxis1);
/*  88 */     setLegend(this.legend);
/*  89 */     setData(paramObservableList);
/*     */   }
/*     */ 
/*     */   private static double doubleValue(Number paramNumber)
/*     */   {
/*  94 */     return doubleValue(paramNumber, 0.0D);
/*     */   }
/*  96 */   private static double doubleValue(Number paramNumber, double paramDouble) { return paramNumber == null ? paramDouble : paramNumber.doubleValue(); }
/*     */ 
/*     */   protected void dataItemAdded(XYChart.Series<X, Y> paramSeries, int paramInt, XYChart.Data<X, Y> paramData)
/*     */   {
/* 100 */     Node localNode = createSymbol(paramSeries, getData().indexOf(paramSeries), paramData, paramInt);
/* 101 */     if (shouldAnimate()) {
/* 102 */       int i = 0;
/* 103 */       if ((paramInt > 0) && (paramInt < paramSeries.getData().size() - 1)) {
/* 104 */         i = 1;
/* 105 */         XYChart.Data localData1 = (XYChart.Data)paramSeries.getData().get(paramInt - 1);
/* 106 */         XYChart.Data localData2 = (XYChart.Data)paramSeries.getData().get(paramInt + 1);
/* 107 */         double d1 = getXAxis().toNumericValue(localData1.getXValue());
/* 108 */         double d2 = getYAxis().toNumericValue(localData1.getYValue());
/* 109 */         double d3 = getXAxis().toNumericValue(localData2.getXValue());
/* 110 */         double d4 = getYAxis().toNumericValue(localData2.getYValue());
/*     */ 
/* 112 */         double d5 = getXAxis().toNumericValue(paramData.getXValue());
/* 113 */         double d6 = getYAxis().toNumericValue(paramData.getYValue());
/*     */ 
/* 116 */         double d7 = (d4 - d2) / (d3 - d1) * d5 + (d3 * d2 - d4 * d1) / (d3 - d1);
/* 117 */         paramData.setCurrentY(getYAxis().toRealValue(d7));
/* 118 */         paramData.setCurrentX(getXAxis().toRealValue(d5));
/*     */       }
/* 124 */       else if ((paramInt == 0) && (paramSeries.getData().size() > 1)) {
/* 125 */         i = 1;
/* 126 */         paramData.setCurrentX(((XYChart.Data)paramSeries.getData().get(1)).getXValue());
/* 127 */         paramData.setCurrentY(((XYChart.Data)paramSeries.getData().get(1)).getYValue());
/* 128 */       } else if ((paramInt == paramSeries.getData().size() - 1) && (paramSeries.getData().size() > 1)) {
/* 129 */         i = 1;
/* 130 */         int j = paramSeries.getData().size() - 2;
/* 131 */         paramData.setCurrentX(((XYChart.Data)paramSeries.getData().get(j)).getXValue());
/* 132 */         paramData.setCurrentY(((XYChart.Data)paramSeries.getData().get(j)).getYValue());
/*     */       }
/*     */       else {
/* 135 */         FadeTransition localFadeTransition = new FadeTransition(Duration.millis(500.0D), localNode);
/* 136 */         localFadeTransition.setToValue(1.0D);
/* 137 */         localFadeTransition.play();
/*     */       }
/* 139 */       if (i != 0) {
/* 140 */         animate(new KeyFrame[] { new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue(paramData.currentYProperty(), paramData.getCurrentY()), new KeyValue(paramData.currentXProperty(), paramData.getCurrentX()) }), new KeyFrame(Duration.millis(800.0D), new KeyValue[] { new KeyValue(paramData.currentYProperty(), paramData.getYValue(), Interpolator.EASE_BOTH), new KeyValue(paramData.currentXProperty(), paramData.getXValue(), Interpolator.EASE_BOTH) }) });
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 153 */     getPlotChildren().add(localNode);
/*     */   }
/*     */ 
/*     */   protected void dataItemRemoved(final XYChart.Data<X, Y> paramData, final XYChart.Series<X, Y> paramSeries) {
/* 157 */     final Node localNode = paramData.getNode();
/*     */ 
/* 159 */     int i = paramSeries.getItemIndex(paramData);
/* 160 */     if (shouldAnimate()) {
/* 161 */       int j = 0;
/*     */       int k;
/* 162 */       if ((i > 0) && (i < paramSeries.getDataSize())) {
/* 163 */         j = 1;
/* 164 */         k = 0;
/* 165 */         for (XYChart.Data localData1 = paramSeries.begin; (localData1 != null) && (k != i - 1); localData1 = localData1.next) k++;
/* 166 */         XYChart.Data localData2 = localData1;
/* 167 */         XYChart.Data localData3 = localData1.next.next;
/* 168 */         double d1 = getXAxis().toNumericValue(localData2.getXValue());
/* 169 */         double d2 = getYAxis().toNumericValue(localData2.getYValue());
/* 170 */         double d3 = getXAxis().toNumericValue(localData3.getXValue());
/* 171 */         double d4 = getYAxis().toNumericValue(localData3.getYValue());
/*     */ 
/* 173 */         double d5 = getXAxis().toNumericValue(paramData.getXValue());
/* 174 */         double d6 = getYAxis().toNumericValue(paramData.getYValue());
/*     */ 
/* 177 */         double d7 = (d4 - d2) / (d3 - d1) * d5 + (d3 * d2 - d4 * d1) / (d3 - d1);
/* 178 */         paramData.setCurrentX(getXAxis().toRealValue(d5));
/* 179 */         paramData.setCurrentY(getYAxis().toRealValue(d6));
/* 180 */         paramData.setXValue(getXAxis().toRealValue(d5));
/* 181 */         paramData.setYValue(getYAxis().toRealValue(d7));
/*     */       }
/* 187 */       else if ((i == 0) && (paramSeries.getDataSize() > 1)) {
/* 188 */         j = 1;
/* 189 */         paramData.setXValue(((XYChart.Data)paramSeries.getData().get(0)).getXValue());
/* 190 */         paramData.setYValue(((XYChart.Data)paramSeries.getData().get(0)).getYValue());
/* 191 */       } else if ((i == paramSeries.getDataSize() - 1) && (paramSeries.getDataSize() > 1)) {
/* 192 */         j = 1;
/* 193 */         k = paramSeries.getData().size() - 1;
/* 194 */         paramData.setXValue(((XYChart.Data)paramSeries.getData().get(k)).getXValue());
/* 195 */         paramData.setYValue(((XYChart.Data)paramSeries.getData().get(k)).getYValue());
/*     */       }
/*     */       else {
/* 198 */         localNode.setOpacity(0.0D);
/* 199 */         FadeTransition localFadeTransition = new FadeTransition(Duration.millis(500.0D), localNode);
/* 200 */         localFadeTransition.setToValue(0.0D);
/* 201 */         localFadeTransition.setOnFinished(new EventHandler() {
/*     */           public void handle(ActionEvent paramAnonymousActionEvent) {
/* 203 */             StackedAreaChart.this.getPlotChildren().remove(localNode);
/* 204 */             StackedAreaChart.this.removeDataItemFromDisplay(paramSeries, paramData);
/*     */           }
/*     */         });
/* 207 */         localFadeTransition.play();
/*     */       }
/* 209 */       if (j != 0) {
/* 210 */         animate(new KeyFrame[] { new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue(paramData.currentYProperty(), paramData.getCurrentY()), new KeyValue(paramData.currentXProperty(), paramData.getCurrentX()) }), new KeyFrame(Duration.millis(800.0D), new EventHandler()
/*     */         {
/*     */           public void handle(ActionEvent paramAnonymousActionEvent)
/*     */           {
/* 215 */             StackedAreaChart.this.getPlotChildren().remove(localNode);
/* 216 */             StackedAreaChart.this.removeDataItemFromDisplay(paramSeries, paramData);
/*     */           }
/*     */         }
/*     */         , new KeyValue[] { new KeyValue(paramData.currentYProperty(), paramData.getYValue(), Interpolator.EASE_BOTH), new KeyValue(paramData.currentXProperty(), paramData.getXValue(), Interpolator.EASE_BOTH) }) });
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 226 */       getPlotChildren().remove(localNode);
/* 227 */       removeDataItemFromDisplay(paramSeries, paramData);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void dataItemChanged(XYChart.Data<X, Y> paramData)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void seriesChanged(ListChangeListener.Change<? extends XYChart.Series> paramChange)
/*     */   {
/* 238 */     for (int i = 0; i < getDataSize(); i++) {
/* 239 */       XYChart.Series localSeries = (XYChart.Series)getData().get(i);
/* 240 */       Path localPath1 = (Path)((Group)localSeries.getNode()).getChildren().get(1);
/* 241 */       Path localPath2 = (Path)((Group)localSeries.getNode()).getChildren().get(0);
/* 242 */       localPath1.getStyleClass().setAll(new String[] { "chart-series-area-line", "series" + i, localSeries.defaultColorStyleClass });
/* 243 */       localPath2.getStyleClass().setAll(new String[] { "chart-series-area-fill", "series" + i, localSeries.defaultColorStyleClass });
/* 244 */       for (int j = 0; j < localSeries.getData().size(); j++) {
/* 245 */         XYChart.Data localData = (XYChart.Data)localSeries.getData().get(j);
/* 246 */         Node localNode = localData.getNode();
/* 247 */         if (localNode != null) localNode.getStyleClass().setAll(new String[] { "chart-area-symbol", "series" + i, "data" + j, localSeries.defaultColorStyleClass });
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void seriesAdded(XYChart.Series<X, Y> paramSeries, int paramInt)
/*     */   {
/* 254 */     Path localPath1 = new Path();
/* 255 */     Path localPath2 = new Path();
/* 256 */     localPath1.setStrokeLineJoin(StrokeLineJoin.BEVEL);
/* 257 */     localPath2.setStrokeLineJoin(StrokeLineJoin.BEVEL);
/* 258 */     Group localGroup = new Group(new Node[] { localPath2, localPath1 });
/* 259 */     paramSeries.setNode(localGroup);
/*     */ 
/* 261 */     SimpleDoubleProperty localSimpleDoubleProperty = new SimpleDoubleProperty(this, "seriesYMultiplier");
/* 262 */     this.seriesYMultiplierMap.put(paramSeries, localSimpleDoubleProperty);
/*     */ 
/* 264 */     if (shouldAnimate()) {
/* 265 */       localPath1.setOpacity(0.0D);
/* 266 */       localPath2.setOpacity(0.0D);
/* 267 */       localSimpleDoubleProperty.setValue(Double.valueOf(0.0D));
/*     */     } else {
/* 269 */       localSimpleDoubleProperty.setValue(Double.valueOf(1.0D));
/*     */     }
/* 271 */     getPlotChildren().add(localGroup);
/* 272 */     ArrayList localArrayList = new ArrayList();
/* 273 */     if (shouldAnimate())
/*     */     {
/* 275 */       localArrayList.add(new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue(localPath1.opacityProperty(), Integer.valueOf(0)), new KeyValue(localPath2.opacityProperty(), Integer.valueOf(0)), new KeyValue(localSimpleDoubleProperty, Integer.valueOf(0)) }));
/*     */ 
/* 280 */       localArrayList.add(new KeyFrame(Duration.millis(200.0D), new KeyValue[] { new KeyValue(localPath1.opacityProperty(), Integer.valueOf(1)), new KeyValue(localPath2.opacityProperty(), Integer.valueOf(1)) }));
/*     */ 
/* 284 */       localArrayList.add(new KeyFrame(Duration.millis(500.0D), new KeyValue[] { new KeyValue(localSimpleDoubleProperty, Integer.valueOf(1)) }));
/*     */     }
/*     */ 
/* 288 */     for (int i = 0; i < paramSeries.getData().size(); i++) {
/* 289 */       XYChart.Data localData = (XYChart.Data)paramSeries.getData().get(i);
/* 290 */       Node localNode = createSymbol(paramSeries, paramInt, localData, i);
/* 291 */       if (shouldAnimate()) localNode.setOpacity(0.0D);
/* 292 */       getPlotChildren().add(localNode);
/* 293 */       if (shouldAnimate())
/*     */       {
/* 295 */         localArrayList.add(new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue(localNode.opacityProperty(), Integer.valueOf(0)) }));
/* 296 */         localArrayList.add(new KeyFrame(Duration.millis(200.0D), new KeyValue[] { new KeyValue(localNode.opacityProperty(), Integer.valueOf(1)) }));
/*     */       }
/*     */     }
/* 299 */     if (shouldAnimate()) animate((KeyFrame[])localArrayList.toArray(new KeyFrame[localArrayList.size()]));
/*     */   }
/*     */ 
/*     */   protected void seriesRemoved(final XYChart.Series<X, Y> paramSeries)
/*     */   {
/* 304 */     this.seriesYMultiplierMap.remove(paramSeries);
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 306 */     if (shouldAnimate())
/*     */     {
/* 308 */       localObject1 = new ArrayList();
/* 309 */       ((List)localObject1).add(paramSeries.getNode());
/* 310 */       for (localObject2 = paramSeries.getData().iterator(); ((Iterator)localObject2).hasNext(); ((List)localObject1).add(((XYChart.Data)localObject3).getNode())) localObject3 = (XYChart.Data)((Iterator)localObject2).next();
/*     */ 
/* 312 */       localObject2 = new KeyValue[((List)localObject1).size()];
/* 313 */       Object localObject3 = new KeyValue[((List)localObject1).size()];
/* 314 */       for (int i = 0; i < ((List)localObject1).size(); i++) {
/* 315 */         localObject2[i] = new KeyValue(((Node)((List)localObject1).get(i)).opacityProperty(), Integer.valueOf(0));
/* 316 */         localObject3[i] = new KeyValue(((Node)((List)localObject1).get(i)).opacityProperty(), Integer.valueOf(1));
/*     */       }
/* 318 */       Timeline localTimeline = new Timeline();
/* 319 */       localTimeline.getKeyFrames().addAll(new KeyFrame[] { new KeyFrame(Duration.ZERO, (KeyValue[])localObject2), new KeyFrame(Duration.millis(400.0D), new EventHandler()
/*     */       {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent)
/*     */         {
/* 323 */           StackedAreaChart.this.getPlotChildren().removeAll(this.val$nodes);
/* 324 */           StackedAreaChart.this.removeSeriesFromDisplay(paramSeries);
/*     */         }
/*     */       }
/*     */       , (KeyValue[])localObject3) });
/*     */ 
/* 328 */       localTimeline.play();
/*     */     } else {
/* 330 */       getPlotChildren().remove(paramSeries.getNode());
/* 331 */       for (localObject1 = paramSeries.getData().iterator(); ((Iterator)localObject1).hasNext(); getPlotChildren().remove(((XYChart.Data)localObject2).getNode())) localObject2 = (XYChart.Data)((Iterator)localObject1).next();
/* 332 */       removeSeriesFromDisplay(paramSeries);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateAxisRange()
/*     */   {
/* 340 */     Axis localAxis1 = getXAxis();
/* 341 */     Axis localAxis2 = getYAxis();
/*     */     ArrayList localArrayList;
/* 342 */     if (localAxis1.isAutoRanging()) {
/* 343 */       localArrayList = new ArrayList();
/* 344 */       if (localArrayList != null) {
/* 345 */         for (XYChart.Series localSeries1 : getData()) {
/* 346 */           for (XYChart.Data localData1 : localSeries1.getData()) {
/* 347 */             if (localArrayList != null) localArrayList.add(localData1.getXValue());
/*     */           }
/*     */         }
/* 350 */         if (localArrayList != null) localAxis1.invalidateRange(localArrayList);
/*     */       }
/*     */     }
/* 353 */     if (localAxis2.isAutoRanging()) {
/* 354 */       localArrayList = new ArrayList();
/* 355 */       if (localArrayList != null) {
/* 356 */         double d1 = 0.0D;
/* 357 */         ??? = getDisplayedSeriesIterator();
/* 358 */         while (???.hasNext()) {
/* 359 */           double d2 = 0.0D;
/* 360 */           XYChart.Series localSeries2 = (XYChart.Series)???.next();
/* 361 */           for (XYChart.Data localData2 : localSeries2.getData()) {
/* 362 */             if (localData2 != null) d2 = Math.max(d2, localAxis2.toNumericValue(localData2.getYValue()));
/*     */           }
/* 364 */           d1 += d2;
/*     */         }
/*     */ 
/* 367 */         if (d1 > 0.0D) localArrayList.add(Double.valueOf(d1));
/* 368 */         localAxis2.invalidateRange(localArrayList);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void layoutPlotChildren()
/*     */   {
/* 376 */     ArrayList localArrayList1 = new ArrayList();
/*     */ 
/* 380 */     ArrayList localArrayList2 = new ArrayList();
/*     */ 
/* 382 */     for (int i = 0; i < getDataSize(); i++) {
/* 383 */       XYChart.Series localSeries = (XYChart.Series)getData().get(i);
/* 384 */       localArrayList2.clear();
/*     */ 
/* 386 */       for (Object localObject1 = localArrayList1.iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (DataPointInfo)((Iterator)localObject1).next();
/* 387 */         ((DataPointInfo)localObject2).partOf = PartOf.PREVIOUS;
/* 388 */         localArrayList2.add(localObject2);
/*     */       }
/* 390 */       localArrayList1.clear();
/*     */ 
/* 392 */       for (localObject1 = localSeries.begin; localObject1 != null; localObject1 = ((XYChart.Data)localObject1).next) {
/* 393 */         localObject2 = new DataPointInfo((XYChart.Data)localObject1, getXAxis().toNumericValue(((XYChart.Data)localObject1).getXValue()), getYAxis().toNumericValue(((XYChart.Data)localObject1).getYValue()), PartOf.CURRENT);
/*     */ 
/* 395 */         localArrayList2.add(localObject2);
/*     */       }
/* 397 */       localObject1 = (DoubleProperty)this.seriesYMultiplierMap.get(localSeries);
/* 398 */       Object localObject2 = (Path)((Group)localSeries.getNode()).getChildren().get(1);
/* 399 */       Path localPath = (Path)((Group)localSeries.getNode()).getChildren().get(0);
/* 400 */       ((Path)localObject2).getElements().clear();
/* 401 */       localPath.getElements().clear();
/* 402 */       int j = 0;
/*     */ 
/* 404 */       sortAggregateList(localArrayList2);
/*     */ 
/* 406 */       int k = 0;
/* 407 */       int m = 0;
/* 408 */       int n = findNextCurrent(localArrayList2, -1);
/* 409 */       int i1 = findPreviousCurrent(localArrayList2, localArrayList2.size());
/*     */ 
/* 412 */       for (Iterator localIterator = localArrayList2.iterator(); localIterator.hasNext(); ) { localDataPointInfo1 = (DataPointInfo)localIterator.next();
/* 413 */         if (j == i1) m = 1;
/* 414 */         if (j == n) k = 1;
/* 415 */         double d1 = 0.0D;
/* 416 */         double d3 = 0.0D;
/* 417 */         double d5 = 0.0D;
/* 418 */         double d6 = 0.0D;
/* 419 */         DataPointInfo localDataPointInfo2 = new DataPointInfo();
/* 420 */         DataPointInfo localDataPointInfo3 = new DataPointInfo(true);
/* 421 */         XYChart.Data localData = localDataPointInfo1.dataItem;
/*     */         int i3;
/*     */         int i4;
/*     */         DataPointInfo localDataPointInfo4;
/*     */         DataPointInfo localDataPointInfo5;
/*     */         double d7;
/* 422 */         if (localDataPointInfo1.partOf.equals(PartOf.CURRENT)) {
/* 423 */           i3 = findPreviousPrevious(localArrayList2, j);
/* 424 */           i4 = findNextPrevious(localArrayList2, j);
/*     */ 
/* 427 */           if ((i3 == -1) || ((i4 == -1) && (((DataPointInfo)localArrayList2.get(i3)).x != localDataPointInfo1.x))) {
/* 428 */             if (k != 0)
/*     */             {
/* 430 */               localData = new XYChart.Data(Double.valueOf(localDataPointInfo1.x), Integer.valueOf(0));
/* 431 */               d1 = getXAxis().getDisplayPosition(localData.getCurrentX());
/* 432 */               d3 = getYAxis().getZeroPosition();
/* 433 */               d5 = getXAxis().toNumericValue(localData.getXValue());
/* 434 */               d6 = getYAxis().toNumericValue(localData.getYValue());
/* 435 */               localDataPointInfo3.setValues(localData, d5, d6, d1, d3, PartOf.CURRENT, true, false);
/* 436 */               localArrayList1.add(localDataPointInfo3);
/*     */             }
/*     */ 
/* 439 */             localData = localDataPointInfo1.dataItem;
/* 440 */             d1 = getXAxis().getDisplayPosition(localData.getCurrentX());
/* 441 */             d3 = getYAxis().getDisplayPosition(getYAxis().toRealValue(getYAxis().toNumericValue(localData.getCurrentY()) * ((DoubleProperty)localObject1).getValue().doubleValue()));
/*     */ 
/* 443 */             d5 = getXAxis().toNumericValue(localData.getXValue());
/* 444 */             d6 = getYAxis().toNumericValue(localData.getYValue());
/* 445 */             localDataPointInfo2.setValues(localData, d5, d6, d1, d3, PartOf.CURRENT, false, k == 0);
/* 446 */             localArrayList1.add(localDataPointInfo2);
/* 447 */             if (j == i1)
/*     */             {
/* 449 */               localData = new XYChart.Data(Double.valueOf(localDataPointInfo1.x), Integer.valueOf(0));
/* 450 */               d1 = getXAxis().getDisplayPosition(localData.getCurrentX());
/* 451 */               d3 = getYAxis().getZeroPosition();
/* 452 */               d5 = getXAxis().toNumericValue(localData.getXValue());
/* 453 */               d6 = getYAxis().toNumericValue(localData.getYValue());
/* 454 */               localDataPointInfo3.setValues(localData, d5, d6, d1, d3, PartOf.CURRENT, true, false);
/* 455 */               localArrayList1.add(localDataPointInfo3);
/*     */             }
/*     */           } else {
/* 458 */             localDataPointInfo4 = (DataPointInfo)localArrayList2.get(i3);
/* 459 */             if (localDataPointInfo4.x == localDataPointInfo1.x)
/*     */             {
/* 462 */               DataPointInfo localDataPointInfo6 = localDataPointInfo4;
/* 463 */               if (localDataPointInfo4.dropDown) {
/* 464 */                 i3 = findPreviousPrevious(localArrayList2, i3);
/* 465 */                 localDataPointInfo4 = (DataPointInfo)localArrayList2.get(i3);
/*     */               }
/*     */ 
/* 468 */               if (localDataPointInfo4.x == localDataPointInfo1.x) {
/* 469 */                 d1 = getXAxis().getDisplayPosition(localData.getCurrentX());
/* 470 */                 d3 = getYAxis().getDisplayPosition(getYAxis().toRealValue(getYAxis().toNumericValue(localData.getCurrentY()) * ((DoubleProperty)localObject1).getValue().doubleValue()));
/*     */ 
/* 472 */                 d3 += -(getYAxis().getZeroPosition() - localDataPointInfo4.displayY);
/* 473 */                 localDataPointInfo2.setValues(localData, localDataPointInfo1.x, localDataPointInfo1.y, d1, d3, PartOf.CURRENT, false, k == 0);
/*     */ 
/* 475 */                 localArrayList1.add(localDataPointInfo2);
/*     */               }
/* 477 */               if (m != 0) {
/* 478 */                 localDataPointInfo3.setValues(localDataPointInfo6.dataItem, localDataPointInfo6.x, localDataPointInfo6.y, localDataPointInfo6.displayX, localDataPointInfo6.displayY, PartOf.CURRENT, true, false);
/*     */ 
/* 480 */                 localArrayList1.add(localDataPointInfo3);
/*     */               }
/*     */             }
/*     */             else {
/* 484 */               localDataPointInfo5 = i4 == -1 ? null : (DataPointInfo)localArrayList2.get(i4);
/* 485 */               localDataPointInfo4 = i3 == -1 ? null : (DataPointInfo)localArrayList2.get(i3);
/* 486 */               d1 = getXAxis().getDisplayPosition(localData.getCurrentX());
/* 487 */               d3 = getYAxis().getDisplayPosition(getYAxis().toRealValue(getYAxis().toNumericValue(localData.getCurrentY()) * ((DoubleProperty)localObject1).getValue().doubleValue()));
/*     */ 
/* 489 */               if ((localDataPointInfo4 != null) && (localDataPointInfo5 != null)) {
/* 490 */                 d7 = interpolate(localDataPointInfo4.displayX, localDataPointInfo4.displayY, localDataPointInfo5.displayX, localDataPointInfo5.displayY, d1);
/*     */ 
/* 492 */                 d3 += -(getYAxis().getZeroPosition() - d7);
/* 493 */                 double d8 = interpolate(localDataPointInfo4.x, localDataPointInfo4.y, localDataPointInfo5.x, localDataPointInfo5.y, localDataPointInfo1.x);
/* 494 */                 if (k != 0)
/*     */                 {
/* 496 */                   localData = new XYChart.Data(Double.valueOf(localDataPointInfo1.x), Double.valueOf(d8));
/* 497 */                   localDataPointInfo3.setValues(localData, localDataPointInfo1.x, d8, d1, d7, PartOf.CURRENT, true, false);
/* 498 */                   localArrayList1.add(localDataPointInfo3);
/*     */                 }
/*     */ 
/* 501 */                 localDataPointInfo2.setValues(localData, localDataPointInfo1.x, localDataPointInfo1.y, d1, d3, PartOf.CURRENT, false, k == 0);
/*     */ 
/* 503 */                 localArrayList1.add(localDataPointInfo2);
/* 504 */                 if (j == i1)
/*     */                 {
/* 506 */                   localData = new XYChart.Data(Double.valueOf(localDataPointInfo1.x), Double.valueOf(d8));
/* 507 */                   localDataPointInfo3.setValues(localData, localDataPointInfo1.x, d8, d1, d7, PartOf.CURRENT, true, false);
/* 508 */                   localArrayList1.add(localDataPointInfo3);
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 520 */           i3 = findPreviousCurrent(localArrayList2, j);
/* 521 */           i4 = findNextCurrent(localArrayList2, j);
/*     */ 
/* 524 */           if (localDataPointInfo1.dropDown) {
/* 525 */             if ((localDataPointInfo1.x <= ((DataPointInfo)localArrayList2.get(n)).x) || (localDataPointInfo1.x > ((DataPointInfo)localArrayList2.get(i1)).x))
/*     */             {
/* 527 */               localDataPointInfo2.setValues(localData, localDataPointInfo1.x, localDataPointInfo1.y, localDataPointInfo1.displayX, localDataPointInfo1.displayY, PartOf.CURRENT, true, false);
/*     */ 
/* 529 */               localDataPointInfo2.dropDown = true;
/* 530 */               localArrayList1.add(localDataPointInfo2);
/*     */             }
/*     */           }
/* 533 */           else if ((i3 == -1) || (i4 == -1)) {
/* 534 */             localDataPointInfo2.setValues(localData, localDataPointInfo1.x, localDataPointInfo1.y, localDataPointInfo1.displayX, localDataPointInfo1.displayY, PartOf.CURRENT, true, false);
/*     */ 
/* 536 */             localArrayList1.add(localDataPointInfo2);
/*     */           } else {
/* 538 */             localDataPointInfo5 = (DataPointInfo)localArrayList2.get(i4);
/* 539 */             if (localDataPointInfo5.x != localDataPointInfo1.x)
/*     */             {
/* 543 */               localDataPointInfo4 = (DataPointInfo)localArrayList2.get(i3);
/* 544 */               d1 = getXAxis().getDisplayPosition(localData.getCurrentX());
/* 545 */               d7 = interpolate(localDataPointInfo4.x, localDataPointInfo4.y, localDataPointInfo5.x, localDataPointInfo5.y, localDataPointInfo1.x);
/* 546 */               d3 = getYAxis().getDisplayPosition(getYAxis().toRealValue(d7 * ((DoubleProperty)localObject1).getValue().doubleValue()));
/*     */ 
/* 548 */               d3 += -(getYAxis().getZeroPosition() - localDataPointInfo1.displayY);
/* 549 */               localDataPointInfo2.setValues(new XYChart.Data(Double.valueOf(localDataPointInfo1.x), Double.valueOf(d7)), localDataPointInfo1.x, d7, d1, d3, PartOf.CURRENT, true, true);
/* 550 */               localArrayList1.add(localDataPointInfo2);
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 555 */         j++;
/* 556 */         if (k != 0) k = 0;
/* 557 */         if (m != 0) m = 0;
/*     */       }
/* 563 */       DataPointInfo localDataPointInfo1;
/* 561 */       ((Path)localObject2).getElements().add(new MoveTo(((DataPointInfo)localArrayList1.get(0)).displayX, ((DataPointInfo)localArrayList1.get(0)).displayY));
/* 562 */       localPath.getElements().add(new MoveTo(((DataPointInfo)localArrayList1.get(0)).displayX, ((DataPointInfo)localArrayList1.get(0)).displayY));
/* 563 */       for (localIterator = localArrayList1.iterator(); localIterator.hasNext(); ) { localDataPointInfo1 = (DataPointInfo)localIterator.next();
/* 564 */         if (!localDataPointInfo1.lineTo)
/* 565 */           ((Path)localObject2).getElements().add(new MoveTo(localDataPointInfo1.displayX, localDataPointInfo1.displayY));
/*     */         else {
/* 567 */           ((Path)localObject2).getElements().add(new LineTo(localDataPointInfo1.displayX, localDataPointInfo1.displayY));
/*     */         }
/* 569 */         localPath.getElements().add(new LineTo(localDataPointInfo1.displayX, localDataPointInfo1.displayY));
/*     */ 
/* 571 */         if (!localDataPointInfo1.skipSymbol) {
/* 572 */           Node localNode = localDataPointInfo1.dataItem.getNode();
/* 573 */           if (localNode != null) {
/* 574 */             double d2 = localNode.prefWidth(-1.0D);
/* 575 */             double d4 = localNode.prefHeight(-1.0D);
/* 576 */             localNode.resizeRelocate(localDataPointInfo1.displayX - d2 / 2.0D, localDataPointInfo1.displayY - d4 / 2.0D, d2, d4);
/*     */           }
/*     */         }
/*     */       }
/* 580 */       for (int i2 = localArrayList2.size() - 1; i2 > 0; i2--) {
/* 581 */         localDataPointInfo1 = (DataPointInfo)localArrayList2.get(i2);
/* 582 */         if (PartOf.PREVIOUS.equals(localDataPointInfo1.partOf)) {
/* 583 */           localPath.getElements().add(new LineTo(localDataPointInfo1.displayX, localDataPointInfo1.displayY));
/*     */         }
/*     */       }
/* 586 */       localPath.getElements().add(new ClosePath());
/*     */     }
/*     */   }
/*     */ 
/*     */   private int findNextCurrent(ArrayList<DataPointInfo> paramArrayList, int paramInt)
/*     */   {
/* 594 */     for (int i = paramInt + 1; i < paramArrayList.size(); i++) {
/* 595 */       if (((DataPointInfo)paramArrayList.get(i)).partOf.equals(PartOf.CURRENT)) {
/* 596 */         return i;
/*     */       }
/*     */     }
/* 599 */     return -1;
/*     */   }
/*     */ 
/*     */   private int findPreviousCurrent(ArrayList<DataPointInfo> paramArrayList, int paramInt) {
/* 603 */     for (int i = paramInt - 1; i >= 0; i--) {
/* 604 */       if (((DataPointInfo)paramArrayList.get(i)).partOf.equals(PartOf.CURRENT)) {
/* 605 */         return i;
/*     */       }
/*     */     }
/* 608 */     return -1;
/*     */   }
/*     */ 
/*     */   private int findPreviousPrevious(ArrayList<DataPointInfo> paramArrayList, int paramInt)
/*     */   {
/* 613 */     for (int i = paramInt - 1; i >= 0; i--) {
/* 614 */       if (((DataPointInfo)paramArrayList.get(i)).partOf.equals(PartOf.PREVIOUS)) {
/* 615 */         return i;
/*     */       }
/*     */     }
/* 618 */     return -1;
/*     */   }
/*     */   private int findNextPrevious(ArrayList<DataPointInfo> paramArrayList, int paramInt) {
/* 621 */     for (int i = paramInt + 1; i < paramArrayList.size(); i++) {
/* 622 */       if (((DataPointInfo)paramArrayList.get(i)).partOf.equals(PartOf.PREVIOUS)) {
/* 623 */         return i;
/*     */       }
/*     */     }
/* 626 */     return -1;
/*     */   }
/*     */ 
/*     */   private void sortAggregateList(ArrayList<DataPointInfo> paramArrayList)
/*     */   {
/* 631 */     Collections.sort(paramArrayList, new Object() {
/*     */       public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2) {
/* 633 */         XYChart.Data localData1 = ((StackedAreaChart.DataPointInfo)paramAnonymousObject1).dataItem;
/* 634 */         XYChart.Data localData2 = ((StackedAreaChart.DataPointInfo)paramAnonymousObject2).dataItem;
/* 635 */         double d1 = StackedAreaChart.this.getXAxis().toNumericValue(localData1.getXValue());
/* 636 */         double d2 = StackedAreaChart.this.getXAxis().toNumericValue(localData2.getXValue());
/* 637 */         return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private double interpolate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5)
/*     */   {
/* 644 */     return (paramDouble4 - paramDouble2) / (paramDouble3 - paramDouble1) * (paramDouble5 - paramDouble1) + paramDouble2;
/*     */   }
/*     */ 
/*     */   private Node createSymbol(XYChart.Series paramSeries, int paramInt1, XYChart.Data paramData, int paramInt2) {
/* 648 */     Object localObject = paramData.getNode();
/*     */ 
/* 650 */     if (localObject == null) {
/* 651 */       localObject = new StackPane();
/* 652 */       paramData.setNode((Node)localObject);
/*     */     }
/*     */ 
/* 656 */     ((Node)localObject).getStyleClass().setAll(new String[] { "chart-area-symbol", "series" + paramInt1, "data" + paramInt2, paramSeries.defaultColorStyleClass });
/*     */ 
/* 658 */     return localObject;
/*     */   }
/*     */ 
/*     */   protected void updateLegend()
/*     */   {
/* 665 */     this.legend.getItems().clear();
/* 666 */     if (getData() != null) {
/* 667 */       for (int i = 0; i < getData().size(); i++) {
/* 668 */         XYChart.Series localSeries = (XYChart.Series)getData().get(i);
/* 669 */         Legend.LegendItem localLegendItem = new Legend.LegendItem(localSeries.getName());
/* 670 */         localLegendItem.getSymbol().getStyleClass().addAll(new String[] { "chart-area-symbol", "series" + i, "area-legend-symbol", localSeries.defaultColorStyleClass });
/*     */ 
/* 672 */         this.legend.getItems().add(localLegendItem);
/*     */       }
/*     */     }
/* 675 */     if (this.legend.getItems().size() > 0) {
/* 676 */       if (getLegend() == null)
/* 677 */         setLegend(this.legend);
/*     */     }
/*     */     else
/* 680 */       setLegend(null);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static List<StyleableProperty> impl_CSS_STYLEABLES()
/*     */   {
/* 754 */     return StyleableProperties.STYLEABLES;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public List<StyleableProperty> impl_getStyleableProperties()
/*     */   {
/* 763 */     return impl_CSS_STYLEABLES();
/*     */   }
/*     */ 
/*     */   private static class StyleableProperties
/*     */   {
/* 743 */     private static final List<StyleableProperty> STYLEABLES = Collections.unmodifiableList(localArrayList);
/*     */ 
/*     */     static
/*     */     {
/* 740 */       ArrayList localArrayList = new ArrayList(XYChart.impl_CSS_STYLEABLES());
/*     */     }
/*     */   }
/*     */ 
/*     */   static enum PartOf
/*     */   {
/* 729 */     CURRENT, 
/* 730 */     PREVIOUS;
/*     */   }
/*     */ 
/*     */   static final class DataPointInfo
/*     */   {
/*     */     double x;
/*     */     double y;
/*     */     double displayX;
/*     */     double displayY;
/*     */     XYChart.Data dataItem;
/*     */     StackedAreaChart.PartOf partOf;
/* 696 */     boolean skipSymbol = false;
/* 697 */     boolean lineTo = false;
/* 698 */     boolean dropDown = false;
/*     */ 
/*     */     DataPointInfo() {
/*     */     }
/*     */ 
/*     */     DataPointInfo(XYChart.Data paramData, double paramDouble1, double paramDouble2, StackedAreaChart.PartOf paramPartOf) {
/* 704 */       this.dataItem = paramData;
/* 705 */       this.x = paramDouble1;
/* 706 */       this.y = paramDouble2;
/* 707 */       this.partOf = paramPartOf;
/*     */     }
/*     */ 
/*     */     DataPointInfo(boolean paramBoolean) {
/* 711 */       this.dropDown = paramBoolean;
/*     */     }
/*     */ 
/*     */     void setValues(XYChart.Data paramData, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, StackedAreaChart.PartOf paramPartOf, boolean paramBoolean1, boolean paramBoolean2)
/*     */     {
/* 716 */       this.dataItem = paramData;
/* 717 */       this.x = paramDouble1;
/* 718 */       this.y = paramDouble2;
/* 719 */       this.displayX = paramDouble3;
/* 720 */       this.displayY = paramDouble4;
/* 721 */       this.partOf = paramPartOf;
/* 722 */       this.skipSymbol = paramBoolean1;
/* 723 */       this.lineTo = paramBoolean2;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.StackedAreaChart
 * JD-Core Version:    0.6.2
 */