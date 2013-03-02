/*     */ package javafx.scene.chart;
/*     */ 
/*     */ import com.sun.javafx.charts.Legend;
/*     */ import com.sun.javafx.charts.Legend.LegendItem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.animation.FadeTransition;
/*     */ import javafx.animation.ParallelTransition;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.shape.Ellipse;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class BubbleChart<X, Y> extends XYChart<X, Y>
/*     */ {
/*  53 */   private Legend legend = new Legend();
/*     */ 
/*     */   public BubbleChart(Axis<X> paramAxis, Axis<Y> paramAxis1)
/*     */   {
/*  64 */     this(paramAxis, paramAxis1, FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public BubbleChart(Axis<X> paramAxis, Axis<Y> paramAxis1, ObservableList<XYChart.Series<X, Y>> paramObservableList)
/*     */   {
/*  75 */     super(paramAxis, paramAxis1);
/*  76 */     setLegend(this.legend);
/*  77 */     setData(paramObservableList);
/*     */   }
/*     */ 
/*     */   private static double getDoubleValue(Object paramObject, double paramDouble)
/*     */   {
/*  90 */     return !(paramObject instanceof Number) ? paramDouble : ((Number)paramObject).doubleValue();
/*     */   }
/*     */ 
/*     */   protected void layoutPlotChildren()
/*     */   {
/*  96 */     for (int i = 0; i < getDataSize(); i++) {
/*  97 */       XYChart.Series localSeries = (XYChart.Series)getData().get(i);
/*     */ 
/*  99 */       Iterator localIterator = getDisplayedDataIterator(localSeries);
/* 100 */       while (localIterator.hasNext()) {
/* 101 */         XYChart.Data localData = (XYChart.Data)localIterator.next();
/* 102 */         double d1 = getXAxis().getDisplayPosition(localData.getCurrentX());
/* 103 */         double d2 = getYAxis().getDisplayPosition(localData.getCurrentY());
/* 104 */         Node localNode = localData.getNode();
/*     */ 
/* 106 */         if ((localNode != null) && 
/* 107 */           ((localNode instanceof StackPane))) {
/* 108 */           StackPane localStackPane = (StackPane)localData.getNode();
/*     */           Ellipse localEllipse;
/* 109 */           if (localStackPane.impl_getShape() == null) {
/* 110 */             localEllipse = new Ellipse(getDoubleValue(localData.getExtraValue(), 1.0D), getDoubleValue(localData.getExtraValue(), 1.0D));
/* 111 */             localStackPane.impl_setShape(localEllipse);
/* 112 */           } else if ((localStackPane.impl_getShape() instanceof Ellipse)) {
/* 113 */             localEllipse = (Ellipse)localStackPane.impl_getShape();
/*     */           } else {
/* 115 */             return;
/*     */           }
/* 117 */           localEllipse.setRadiusX(getDoubleValue(localData.getExtraValue(), 1.0D) * Math.abs(((NumberAxis)getXAxis()).getScale()));
/* 118 */           localEllipse.setRadiusY(getDoubleValue(localData.getExtraValue(), 1.0D) * Math.abs(((NumberAxis)getYAxis()).getScale()));
/*     */ 
/* 122 */           localStackPane.impl_setShape(null);
/* 123 */           localStackPane.impl_setShape(localEllipse);
/* 124 */           localStackPane.impl_setScaleShape(false);
/* 125 */           localStackPane.impl_setPositionShape(false);
/*     */ 
/* 127 */           localNode.setLayoutX(d1);
/* 128 */           localNode.setLayoutY(d2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void dataItemAdded(XYChart.Series<X, Y> paramSeries, int paramInt, XYChart.Data<X, Y> paramData)
/*     */   {
/* 136 */     Node localNode = createBubble(paramSeries, getData().indexOf(paramSeries), paramData, paramInt);
/* 137 */     if (shouldAnimate()) {
/* 138 */       localNode.setOpacity(0.0D);
/* 139 */       getPlotChildren().add(localNode);
/*     */ 
/* 141 */       FadeTransition localFadeTransition = new FadeTransition(Duration.millis(500.0D), localNode);
/* 142 */       localFadeTransition.setToValue(1.0D);
/* 143 */       localFadeTransition.play();
/*     */     } else {
/* 145 */       getPlotChildren().add(localNode);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void dataItemRemoved(final XYChart.Data<X, Y> paramData, final XYChart.Series<X, Y> paramSeries) {
/* 150 */     final Node localNode = paramData.getNode();
/* 151 */     if (shouldAnimate())
/*     */     {
/* 153 */       FadeTransition localFadeTransition = new FadeTransition(Duration.millis(500.0D), localNode);
/* 154 */       localFadeTransition.setToValue(0.0D);
/* 155 */       localFadeTransition.setOnFinished(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 157 */           BubbleChart.this.getPlotChildren().remove(localNode);
/* 158 */           BubbleChart.this.removeDataItemFromDisplay(paramSeries, paramData);
/*     */         }
/*     */       });
/* 161 */       localFadeTransition.play();
/*     */     } else {
/* 163 */       getPlotChildren().remove(localNode);
/* 164 */       removeDataItemFromDisplay(paramSeries, paramData);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void dataItemChanged(XYChart.Data<X, Y> paramData)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void seriesAdded(XYChart.Series<X, Y> paramSeries, int paramInt)
/*     */   {
/* 174 */     for (int i = 0; i < paramSeries.getData().size(); i++) {
/* 175 */       XYChart.Data localData = (XYChart.Data)paramSeries.getData().get(i);
/* 176 */       Node localNode = createBubble(paramSeries, paramInt, localData, i);
/* 177 */       if (shouldAnimate()) {
/* 178 */         localNode.setOpacity(0.0D);
/* 179 */         getPlotChildren().add(localNode);
/*     */ 
/* 181 */         FadeTransition localFadeTransition = new FadeTransition(Duration.millis(500.0D), localNode);
/* 182 */         localFadeTransition.setToValue(1.0D);
/* 183 */         localFadeTransition.play();
/*     */       } else {
/* 185 */         getPlotChildren().add(localNode);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void seriesRemoved(final XYChart.Series<X, Y> paramSeries)
/*     */   {
/*     */     Object localObject1;
/*     */     Object localObject2;
/*     */     Object localObject3;
/* 192 */     if (shouldAnimate()) {
/* 193 */       localObject1 = new ParallelTransition();
/* 194 */       ((ParallelTransition)localObject1).setOnFinished(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 196 */           BubbleChart.this.removeSeriesFromDisplay(paramSeries);
/*     */         }
/*     */       });
/* 199 */       for (localObject2 = paramSeries.getData().iterator(); ((Iterator)localObject2).hasNext(); ) { localObject3 = (XYChart.Data)((Iterator)localObject2).next();
/* 200 */         final Node localNode = ((XYChart.Data)localObject3).getNode();
/*     */ 
/* 202 */         FadeTransition localFadeTransition = new FadeTransition(Duration.millis(500.0D), localNode);
/* 203 */         localFadeTransition.setToValue(0.0D);
/* 204 */         localFadeTransition.setOnFinished(new EventHandler() {
/*     */           public void handle(ActionEvent paramAnonymousActionEvent) {
/* 206 */             BubbleChart.this.getPlotChildren().remove(localNode);
/*     */           }
/*     */         });
/* 209 */         ((ParallelTransition)localObject1).getChildren().add(localFadeTransition);
/*     */       }
/* 211 */       ((ParallelTransition)localObject1).play();
/*     */     } else {
/* 213 */       for (localObject1 = paramSeries.getData().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (XYChart.Data)((Iterator)localObject1).next();
/* 214 */         localObject3 = ((XYChart.Data)localObject2).getNode();
/* 215 */         getPlotChildren().remove(localObject3);
/*     */       }
/* 217 */       removeSeriesFromDisplay(paramSeries);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Node createBubble(XYChart.Series<X, Y> paramSeries, int paramInt1, XYChart.Data paramData, int paramInt2)
/*     */   {
/* 233 */     Object localObject = paramData.getNode();
/*     */ 
/* 235 */     if (localObject == null) {
/* 236 */       localObject = new StackPane();
/* 237 */       paramData.setNode((Node)localObject);
/*     */     }
/*     */ 
/* 240 */     ((Node)localObject).getStyleClass().setAll(new String[] { "chart-bubble", "series" + paramInt1, "data" + paramInt2, paramSeries.defaultColorStyleClass });
/*     */ 
/* 242 */     return localObject;
/*     */   }
/*     */ 
/*     */   protected void updateAxisRange()
/*     */   {
/* 253 */     Axis localAxis1 = getXAxis();
/* 254 */     Axis localAxis2 = getYAxis();
/* 255 */     ArrayList localArrayList1 = null;
/* 256 */     ArrayList localArrayList2 = null;
/* 257 */     if (localAxis1.isAutoRanging()) localArrayList1 = new ArrayList();
/* 258 */     if (localAxis2.isAutoRanging()) localArrayList2 = new ArrayList();
/* 259 */     boolean bool1 = localAxis1 instanceof CategoryAxis;
/* 260 */     boolean bool2 = localAxis2 instanceof CategoryAxis;
/* 261 */     if ((localArrayList1 != null) || (localArrayList2 != null)) {
/* 262 */       for (XYChart.Series localSeries : getData()) {
/* 263 */         for (XYChart.Data localData : localSeries.getData()) {
/* 264 */           if (localArrayList1 != null) {
/* 265 */             if (bool1) {
/* 266 */               localArrayList1.add(localData.getXValue());
/*     */             } else {
/* 268 */               localArrayList1.add(localAxis1.toRealValue(localAxis1.toNumericValue(localData.getXValue()) + getDoubleValue(localData.getExtraValue(), 0.0D)));
/* 269 */               localArrayList1.add(localAxis1.toRealValue(localAxis1.toNumericValue(localData.getXValue()) - getDoubleValue(localData.getExtraValue(), 0.0D)));
/*     */             }
/*     */           }
/* 272 */           if (localArrayList2 != null) {
/* 273 */             if (bool2) {
/* 274 */               localArrayList2.add(localData.getYValue());
/*     */             } else {
/* 276 */               localArrayList2.add(localAxis2.toRealValue(localAxis2.toNumericValue(localData.getYValue()) + getDoubleValue(localData.getExtraValue(), 0.0D)));
/* 277 */               localArrayList2.add(localAxis2.toRealValue(localAxis2.toNumericValue(localData.getYValue()) - getDoubleValue(localData.getExtraValue(), 0.0D)));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 282 */       if (localArrayList1 != null) localAxis1.invalidateRange(localArrayList1);
/* 283 */       if (localArrayList2 != null) localAxis2.invalidateRange(localArrayList2);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateLegend()
/*     */   {
/* 291 */     this.legend.getItems().clear();
/* 292 */     if (getData() != null) {
/* 293 */       for (int i = 0; i < getData().size(); i++) {
/* 294 */         XYChart.Series localSeries = (XYChart.Series)getData().get(i);
/* 295 */         Legend.LegendItem localLegendItem = new Legend.LegendItem(localSeries.getName());
/* 296 */         localLegendItem.getSymbol().getStyleClass().addAll(new String[] { "series" + i, "chart-bubble", "bubble-legend-symbol", localSeries.defaultColorStyleClass });
/*     */ 
/* 298 */         this.legend.getItems().add(localLegendItem);
/*     */       }
/*     */     }
/* 301 */     if (this.legend.getItems().size() > 0) {
/* 302 */       if (getLegend() == null)
/* 303 */         setLegend(this.legend);
/*     */     }
/*     */     else
/* 306 */       setLegend(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.BubbleChart
 * JD-Core Version:    0.6.2
 */