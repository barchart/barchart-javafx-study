/*     */ package javafx.scene.chart;
/*     */ 
/*     */ import com.sun.javafx.charts.Legend;
/*     */ import com.sun.javafx.charts.Legend.LegendItem;
/*     */ import java.util.ArrayList;
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
/*     */ public class AreaChart<X, Y> extends XYChart<X, Y>
/*     */ {
/*  66 */   private Map<XYChart.Series, DoubleProperty> seriesYMultiplierMap = new HashMap();
/*  67 */   private Legend legend = new Legend();
/*     */ 
/*     */   public AreaChart(Axis<X> paramAxis, Axis<Y> paramAxis1)
/*     */   {
/*  78 */     this(paramAxis, paramAxis1, FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public AreaChart(Axis<X> paramAxis, Axis<Y> paramAxis1, ObservableList<XYChart.Series<X, Y>> paramObservableList)
/*     */   {
/*  89 */     super(paramAxis, paramAxis1);
/*  90 */     setLegend(this.legend);
/*  91 */     setData(paramObservableList);
/*     */   }
/*     */ 
/*     */   private static double doubleValue(Number paramNumber)
/*     */   {
/*  96 */     return doubleValue(paramNumber, 0.0D);
/*     */   }
/*  98 */   private static double doubleValue(Number paramNumber, double paramDouble) { return paramNumber == null ? paramDouble : paramNumber.doubleValue(); }
/*     */ 
/*     */ 
/*     */   protected void updateAxisRange()
/*     */   {
/* 103 */     Axis localAxis1 = getXAxis();
/* 104 */     Axis localAxis2 = getYAxis();
/* 105 */     ArrayList localArrayList1 = null;
/* 106 */     ArrayList localArrayList2 = null;
/* 107 */     if (localAxis1.isAutoRanging()) localArrayList1 = new ArrayList();
/* 108 */     if (localAxis2.isAutoRanging()) localArrayList2 = new ArrayList();
/* 109 */     if ((localArrayList1 != null) || (localArrayList2 != null)) {
/* 110 */       for (XYChart.Series localSeries : getData()) {
/* 111 */         for (XYChart.Data localData : localSeries.getData()) {
/* 112 */           if (localArrayList1 != null) localArrayList1.add(localData.getXValue());
/* 113 */           if (localArrayList2 != null) localArrayList2.add(localData.getYValue());
/*     */         }
/*     */       }
/* 116 */       if ((localArrayList1 != null) && ((localArrayList1.size() != 1) || (getXAxis().toNumericValue(localArrayList1.get(0)) != 0.0D))) {
/* 117 */         localAxis1.invalidateRange(localArrayList1);
/*     */       }
/* 119 */       if ((localArrayList2 != null) && ((localArrayList2.size() != 1) || (getYAxis().toNumericValue(localArrayList2.get(0)) != 0.0D)))
/* 120 */         localAxis2.invalidateRange(localArrayList2);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void dataItemAdded(XYChart.Series<X, Y> paramSeries, int paramInt, XYChart.Data<X, Y> paramData)
/*     */   {
/* 126 */     Node localNode = createSymbol(paramSeries, getData().indexOf(paramSeries), paramData, paramInt);
/* 127 */     if (shouldAnimate()) {
/* 128 */       int i = 0;
/* 129 */       if ((paramInt > 0) && (paramInt < paramSeries.getData().size() - 1)) {
/* 130 */         i = 1;
/* 131 */         XYChart.Data localData1 = (XYChart.Data)paramSeries.getData().get(paramInt - 1);
/* 132 */         XYChart.Data localData2 = (XYChart.Data)paramSeries.getData().get(paramInt + 1);
/* 133 */         double d1 = getXAxis().toNumericValue(localData1.getXValue());
/* 134 */         double d2 = getYAxis().toNumericValue(localData1.getYValue());
/* 135 */         double d3 = getXAxis().toNumericValue(localData2.getXValue());
/* 136 */         double d4 = getYAxis().toNumericValue(localData2.getYValue());
/*     */ 
/* 138 */         double d5 = getXAxis().toNumericValue(paramData.getXValue());
/* 139 */         double d6 = getYAxis().toNumericValue(paramData.getYValue());
/*     */ 
/* 142 */         double d7 = (d4 - d2) / (d3 - d1) * d5 + (d3 * d2 - d4 * d1) / (d3 - d1);
/* 143 */         paramData.setCurrentY(getYAxis().toRealValue(d7));
/* 144 */         paramData.setCurrentX(getXAxis().toRealValue(d5));
/*     */       }
/* 150 */       else if ((paramInt == 0) && (paramSeries.getData().size() > 1)) {
/* 151 */         i = 1;
/* 152 */         paramData.setCurrentX(((XYChart.Data)paramSeries.getData().get(1)).getXValue());
/* 153 */         paramData.setCurrentY(((XYChart.Data)paramSeries.getData().get(1)).getYValue());
/* 154 */       } else if ((paramInt == paramSeries.getData().size() - 1) && (paramSeries.getData().size() > 1)) {
/* 155 */         i = 1;
/* 156 */         int j = paramSeries.getData().size() - 2;
/* 157 */         paramData.setCurrentX(((XYChart.Data)paramSeries.getData().get(j)).getXValue());
/* 158 */         paramData.setCurrentY(((XYChart.Data)paramSeries.getData().get(j)).getYValue());
/*     */       }
/*     */       else {
/* 161 */         FadeTransition localFadeTransition = new FadeTransition(Duration.millis(500.0D), localNode);
/* 162 */         localFadeTransition.setToValue(1.0D);
/* 163 */         localFadeTransition.play();
/*     */       }
/* 165 */       if (i != 0) {
/* 166 */         animate(new KeyFrame[] { new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue(paramData.currentYProperty(), paramData.getCurrentY()), new KeyValue(paramData.currentXProperty(), paramData.getCurrentX()) }), new KeyFrame(Duration.millis(800.0D), new KeyValue[] { new KeyValue(paramData.currentYProperty(), paramData.getYValue(), Interpolator.EASE_BOTH), new KeyValue(paramData.currentXProperty(), paramData.getXValue(), Interpolator.EASE_BOTH) }) });
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 179 */     getPlotChildren().add(localNode);
/*     */   }
/*     */ 
/*     */   protected void dataItemRemoved(final XYChart.Data<X, Y> paramData, final XYChart.Series<X, Y> paramSeries) {
/* 183 */     final Node localNode = paramData.getNode();
/*     */ 
/* 185 */     int i = paramSeries.getItemIndex(paramData);
/* 186 */     if (shouldAnimate()) {
/* 187 */       int j = 0;
/*     */       int k;
/* 188 */       if ((i > 0) && (i < paramSeries.getDataSize() - 1)) {
/* 189 */         j = 1;
/* 190 */         k = 0;
/* 191 */         for (XYChart.Data localData1 = paramSeries.begin; (localData1 != null) && (k != i - 1); localData1 = localData1.next) k++;
/* 192 */         XYChart.Data localData2 = localData1;
/* 193 */         XYChart.Data localData3 = localData1.next.next;
/* 194 */         double d1 = getXAxis().toNumericValue(localData2.getXValue());
/* 195 */         double d2 = getYAxis().toNumericValue(localData2.getYValue());
/* 196 */         double d3 = getXAxis().toNumericValue(localData3.getXValue());
/* 197 */         double d4 = getYAxis().toNumericValue(localData3.getYValue());
/*     */ 
/* 199 */         double d5 = getXAxis().toNumericValue(paramData.getXValue());
/* 200 */         double d6 = getYAxis().toNumericValue(paramData.getYValue());
/*     */ 
/* 203 */         double d7 = (d4 - d2) / (d3 - d1) * d5 + (d3 * d2 - d4 * d1) / (d3 - d1);
/* 204 */         paramData.setCurrentX(getXAxis().toRealValue(d5));
/* 205 */         paramData.setCurrentY(getYAxis().toRealValue(d6));
/* 206 */         paramData.setXValue(getXAxis().toRealValue(d5));
/* 207 */         paramData.setYValue(getYAxis().toRealValue(d7));
/*     */       }
/* 213 */       else if ((i == 0) && (paramSeries.getDataSize() > 1)) {
/* 214 */         j = 1;
/* 215 */         paramData.setXValue(((XYChart.Data)paramSeries.getData().get(0)).getXValue());
/* 216 */         paramData.setYValue(((XYChart.Data)paramSeries.getData().get(0)).getYValue());
/* 217 */       } else if ((i == paramSeries.getDataSize() - 1) && (paramSeries.getDataSize() > 1)) {
/* 218 */         j = 1;
/* 219 */         k = paramSeries.getData().size() - 1;
/* 220 */         paramData.setXValue(((XYChart.Data)paramSeries.getData().get(k)).getXValue());
/* 221 */         paramData.setYValue(((XYChart.Data)paramSeries.getData().get(k)).getYValue());
/*     */       }
/*     */       else {
/* 224 */         localNode.setOpacity(0.0D);
/* 225 */         FadeTransition localFadeTransition = new FadeTransition(Duration.millis(500.0D), localNode);
/* 226 */         localFadeTransition.setToValue(0.0D);
/* 227 */         localFadeTransition.setOnFinished(new EventHandler() {
/*     */           public void handle(ActionEvent paramAnonymousActionEvent) {
/* 229 */             AreaChart.this.getPlotChildren().remove(localNode);
/* 230 */             AreaChart.this.removeDataItemFromDisplay(paramSeries, paramData);
/*     */           }
/*     */         });
/* 233 */         localFadeTransition.play();
/*     */       }
/* 235 */       if (j != 0) {
/* 236 */         animate(new KeyFrame[] { new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue(paramData.currentYProperty(), paramData.getCurrentY()), new KeyValue(paramData.currentXProperty(), paramData.getCurrentX()) }), new KeyFrame(Duration.millis(800.0D), new EventHandler()
/*     */         {
/*     */           public void handle(ActionEvent paramAnonymousActionEvent)
/*     */           {
/* 241 */             AreaChart.this.getPlotChildren().remove(localNode);
/* 242 */             AreaChart.this.removeDataItemFromDisplay(paramSeries, paramData);
/*     */           }
/*     */         }
/*     */         , new KeyValue[] { new KeyValue(paramData.currentYProperty(), paramData.getYValue(), Interpolator.EASE_BOTH), new KeyValue(paramData.currentXProperty(), paramData.getXValue(), Interpolator.EASE_BOTH) }) });
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 252 */       getPlotChildren().remove(localNode);
/* 253 */       removeDataItemFromDisplay(paramSeries, paramData);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void dataItemChanged(XYChart.Data<X, Y> paramData)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void seriesChanged(ListChangeListener.Change<? extends XYChart.Series> paramChange)
/*     */   {
/* 265 */     for (int i = 0; i < getDataSize(); i++) {
/* 266 */       XYChart.Series localSeries = (XYChart.Series)getData().get(i);
/* 267 */       Path localPath1 = (Path)((Group)localSeries.getNode()).getChildren().get(1);
/* 268 */       Path localPath2 = (Path)((Group)localSeries.getNode()).getChildren().get(0);
/* 269 */       localPath1.getStyleClass().setAll(new String[] { "chart-series-area-line", "series" + i, localSeries.defaultColorStyleClass });
/* 270 */       localPath2.getStyleClass().setAll(new String[] { "chart-series-area-fill", "series" + i, localSeries.defaultColorStyleClass });
/* 271 */       for (int j = 0; j < localSeries.getData().size(); j++) {
/* 272 */         XYChart.Data localData = (XYChart.Data)localSeries.getData().get(j);
/* 273 */         Node localNode = localData.getNode();
/* 274 */         if (localNode != null) localNode.getStyleClass().setAll(new String[] { "chart-area-symbol", "series" + i, "data" + j, localSeries.defaultColorStyleClass });
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void seriesAdded(XYChart.Series<X, Y> paramSeries, int paramInt)
/*     */   {
/* 281 */     Path localPath1 = new Path();
/* 282 */     Path localPath2 = new Path();
/* 283 */     localPath1.setStrokeLineJoin(StrokeLineJoin.BEVEL);
/* 284 */     Group localGroup = new Group(new Node[] { localPath2, localPath1 });
/* 285 */     paramSeries.setNode(localGroup);
/*     */ 
/* 287 */     SimpleDoubleProperty localSimpleDoubleProperty = new SimpleDoubleProperty(this, "seriesYMultiplier");
/* 288 */     this.seriesYMultiplierMap.put(paramSeries, localSimpleDoubleProperty);
/*     */ 
/* 290 */     if (shouldAnimate()) {
/* 291 */       localPath1.setOpacity(0.0D);
/* 292 */       localPath2.setOpacity(0.0D);
/* 293 */       localSimpleDoubleProperty.setValue(Double.valueOf(0.0D));
/*     */     } else {
/* 295 */       localSimpleDoubleProperty.setValue(Double.valueOf(1.0D));
/*     */     }
/* 297 */     getPlotChildren().add(localGroup);
/* 298 */     ArrayList localArrayList = new ArrayList();
/* 299 */     if (shouldAnimate())
/*     */     {
/* 301 */       localArrayList.add(new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue(localPath1.opacityProperty(), Integer.valueOf(0)), new KeyValue(localPath2.opacityProperty(), Integer.valueOf(0)), new KeyValue(localSimpleDoubleProperty, Integer.valueOf(0)) }));
/*     */ 
/* 306 */       localArrayList.add(new KeyFrame(Duration.millis(200.0D), new KeyValue[] { new KeyValue(localPath1.opacityProperty(), Integer.valueOf(1)), new KeyValue(localPath2.opacityProperty(), Integer.valueOf(1)) }));
/*     */ 
/* 310 */       localArrayList.add(new KeyFrame(Duration.millis(500.0D), new KeyValue[] { new KeyValue(localSimpleDoubleProperty, Integer.valueOf(1)) }));
/*     */     }
/*     */ 
/* 314 */     for (int i = 0; i < paramSeries.getData().size(); i++) {
/* 315 */       XYChart.Data localData = (XYChart.Data)paramSeries.getData().get(i);
/* 316 */       Node localNode = createSymbol(paramSeries, paramInt, localData, i);
/* 317 */       if (shouldAnimate()) localNode.setOpacity(0.0D);
/* 318 */       getPlotChildren().add(localNode);
/* 319 */       if (shouldAnimate())
/*     */       {
/* 321 */         localArrayList.add(new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue(localNode.opacityProperty(), Integer.valueOf(0)) }));
/* 322 */         localArrayList.add(new KeyFrame(Duration.millis(200.0D), new KeyValue[] { new KeyValue(localNode.opacityProperty(), Integer.valueOf(1)) }));
/*     */       }
/*     */     }
/* 325 */     if (shouldAnimate()) animate((KeyFrame[])localArrayList.toArray(new KeyFrame[localArrayList.size()]));
/*     */   }
/*     */ 
/*     */   protected void seriesRemoved(final XYChart.Series<X, Y> paramSeries)
/*     */   {
/* 330 */     this.seriesYMultiplierMap.remove(paramSeries);
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 332 */     if (shouldAnimate())
/*     */     {
/* 334 */       localObject1 = new ArrayList();
/* 335 */       ((List)localObject1).add(paramSeries.getNode());
/* 336 */       for (localObject2 = paramSeries.getData().iterator(); ((Iterator)localObject2).hasNext(); ((List)localObject1).add(((XYChart.Data)localObject3).getNode())) localObject3 = (XYChart.Data)((Iterator)localObject2).next();
/*     */ 
/* 338 */       localObject2 = new KeyValue[((List)localObject1).size()];
/* 339 */       Object localObject3 = new KeyValue[((List)localObject1).size()];
/* 340 */       for (int i = 0; i < ((List)localObject1).size(); i++) {
/* 341 */         localObject2[i] = new KeyValue(((Node)((List)localObject1).get(i)).opacityProperty(), Integer.valueOf(0));
/* 342 */         localObject3[i] = new KeyValue(((Node)((List)localObject1).get(i)).opacityProperty(), Integer.valueOf(1));
/*     */       }
/* 344 */       Timeline localTimeline = new Timeline();
/* 345 */       localTimeline.getKeyFrames().addAll(new KeyFrame[] { new KeyFrame(Duration.ZERO, (KeyValue[])localObject2), new KeyFrame(Duration.millis(400.0D), new EventHandler()
/*     */       {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent)
/*     */         {
/* 349 */           AreaChart.this.getPlotChildren().removeAll(this.val$nodes);
/* 350 */           AreaChart.this.removeSeriesFromDisplay(paramSeries);
/*     */         }
/*     */       }
/*     */       , (KeyValue[])localObject3) });
/*     */ 
/* 354 */       localTimeline.play();
/*     */     } else {
/* 356 */       getPlotChildren().remove(paramSeries.getNode());
/* 357 */       for (localObject1 = paramSeries.getData().iterator(); ((Iterator)localObject1).hasNext(); getPlotChildren().remove(((XYChart.Data)localObject2).getNode())) localObject2 = (XYChart.Data)((Iterator)localObject1).next();
/* 358 */       removeSeriesFromDisplay(paramSeries);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void layoutPlotChildren()
/*     */   {
/* 364 */     for (int i = 0; i < getDataSize(); i++) {
/* 365 */       XYChart.Series localSeries = (XYChart.Series)getData().get(i);
/* 366 */       DoubleProperty localDoubleProperty = (DoubleProperty)this.seriesYMultiplierMap.get(localSeries);
/* 367 */       int j = 1;
/* 368 */       double d1 = 0.0D;
/* 369 */       Path localPath1 = (Path)((Group)localSeries.getNode()).getChildren().get(1);
/* 370 */       Path localPath2 = (Path)((Group)localSeries.getNode()).getChildren().get(0);
/* 371 */       localPath1.getElements().clear();
/* 372 */       localPath2.getElements().clear();
/* 373 */       for (XYChart.Data localData = localSeries.begin; localData != null; localData = localData.next) {
/* 374 */         double d2 = d1 = getXAxis().getDisplayPosition(localData.getCurrentX());
/* 375 */         double d3 = getYAxis().getDisplayPosition(getYAxis().toRealValue(getYAxis().toNumericValue(localData.getCurrentY()) * localDoubleProperty.getValue().doubleValue()));
/*     */ 
/* 377 */         if (j != 0) {
/* 378 */           j = 0;
/* 379 */           localPath2.getElements().add(new MoveTo(d2, getYAxis().getZeroPosition()));
/* 380 */           localPath1.getElements().add(new MoveTo(d2, d3));
/*     */         } else {
/* 382 */           localPath1.getElements().add(new LineTo(d2, d3));
/*     */         }
/* 384 */         localPath2.getElements().add(new LineTo(d2, d3));
/* 385 */         Node localNode = localData.getNode();
/* 386 */         if (localNode != null) {
/* 387 */           double d4 = localNode.prefWidth(-1.0D);
/* 388 */           double d5 = localNode.prefHeight(-1.0D);
/* 389 */           localNode.resizeRelocate(d2 - d4 / 2.0D, d3 - d5 / 2.0D, d4, d5);
/*     */         }
/*     */       }
/* 392 */       if (localPath2.getElements().size() >= 1)
/* 393 */         localPath2.getElements().add(new LineTo(d1, getYAxis().getZeroPosition()));
/*     */       else {
/* 395 */         localPath2.getElements().add(new MoveTo(d1, getYAxis().getZeroPosition()));
/*     */       }
/* 397 */       localPath2.getElements().add(new ClosePath());
/*     */     }
/*     */   }
/*     */ 
/*     */   private Node createSymbol(XYChart.Series paramSeries, int paramInt1, XYChart.Data paramData, int paramInt2) {
/* 402 */     Object localObject = paramData.getNode();
/*     */ 
/* 404 */     if (localObject == null) {
/* 405 */       localObject = new StackPane();
/* 406 */       paramData.setNode((Node)localObject);
/*     */     }
/*     */ 
/* 410 */     ((Node)localObject).getStyleClass().setAll(new String[] { "chart-area-symbol", "series" + paramInt1, "data" + paramInt2, paramSeries.defaultColorStyleClass });
/*     */ 
/* 412 */     return localObject;
/*     */   }
/*     */ 
/*     */   protected void updateLegend()
/*     */   {
/* 420 */     this.legend.getItems().clear();
/* 421 */     if (getData() != null) {
/* 422 */       for (int i = 0; i < getData().size(); i++) {
/* 423 */         XYChart.Series localSeries = (XYChart.Series)getData().get(i);
/* 424 */         Legend.LegendItem localLegendItem = new Legend.LegendItem(localSeries.getName());
/* 425 */         localLegendItem.getSymbol().getStyleClass().addAll(new String[] { "chart-area-symbol", "series" + i, "area-legend-symbol", localSeries.defaultColorStyleClass });
/*     */ 
/* 427 */         this.legend.getItems().add(localLegendItem);
/*     */       }
/*     */     }
/* 430 */     if (this.legend.getItems().size() > 0) {
/* 431 */       if (getLegend() == null)
/* 432 */         setLegend(this.legend);
/*     */     }
/*     */     else
/* 435 */       setLegend(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.AreaChart
 * JD-Core Version:    0.6.2
 */