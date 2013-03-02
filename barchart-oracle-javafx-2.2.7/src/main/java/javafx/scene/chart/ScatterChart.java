/*     */ package javafx.scene.chart;
/*     */ 
/*     */ import com.sun.javafx.charts.Legend;
/*     */ import com.sun.javafx.charts.Legend.LegendItem;
/*     */ import java.util.Iterator;
/*     */ import javafx.animation.FadeTransition;
/*     */ import javafx.animation.ParallelTransition;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class ScatterChart<X, Y> extends XYChart<X, Y>
/*     */ {
/*  47 */   private Legend legend = new Legend();
/*     */ 
/*     */   public ScatterChart(Axis<X> paramAxis, Axis<Y> paramAxis1)
/*     */   {
/*  58 */     this(paramAxis, paramAxis1, FXCollections.observableArrayList());
/*     */   }
/*     */ 
/*     */   public ScatterChart(Axis<X> paramAxis, Axis<Y> paramAxis1, ObservableList<XYChart.Series<X, Y>> paramObservableList)
/*     */   {
/*  69 */     super(paramAxis, paramAxis1);
/*  70 */     setLegend(this.legend);
/*  71 */     setData(paramObservableList);
/*     */   }
/*     */ 
/*     */   protected void dataItemAdded(XYChart.Series<X, Y> paramSeries, int paramInt, XYChart.Data<X, Y> paramData)
/*     */   {
/*  78 */     Object localObject = paramData.getNode();
/*     */ 
/*  80 */     if (localObject == null) {
/*  81 */       localObject = new StackPane();
/*  82 */       paramData.setNode((Node)localObject);
/*     */     }
/*     */ 
/*  85 */     ((Node)localObject).getStyleClass().setAll(new String[] { "chart-symbol", "series" + getData().indexOf(paramSeries), "data" + paramInt, paramSeries.defaultColorStyleClass });
/*     */ 
/*  88 */     if (shouldAnimate()) ((Node)localObject).setOpacity(0.0D);
/*  89 */     getPlotChildren().add(localObject);
/*  90 */     if (shouldAnimate()) {
/*  91 */       FadeTransition localFadeTransition = new FadeTransition(Duration.millis(500.0D), (Node)localObject);
/*  92 */       localFadeTransition.setToValue(1.0D);
/*  93 */       localFadeTransition.play();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void dataItemRemoved(final XYChart.Data<X, Y> paramData, final XYChart.Series<X, Y> paramSeries)
/*     */   {
/*  99 */     final Node localNode = paramData.getNode();
/* 100 */     if (shouldAnimate())
/*     */     {
/* 102 */       FadeTransition localFadeTransition = new FadeTransition(Duration.millis(500.0D), localNode);
/* 103 */       localFadeTransition.setToValue(0.0D);
/* 104 */       localFadeTransition.setOnFinished(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 106 */           ScatterChart.this.getPlotChildren().remove(localNode);
/* 107 */           ScatterChart.this.removeDataItemFromDisplay(paramSeries, paramData);
/*     */         }
/*     */       });
/* 110 */       localFadeTransition.play();
/*     */     } else {
/* 112 */       getPlotChildren().remove(localNode);
/* 113 */       removeDataItemFromDisplay(paramSeries, paramData);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void dataItemChanged(XYChart.Data<X, Y> paramData)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void seriesAdded(XYChart.Series<X, Y> paramSeries, int paramInt)
/*     */   {
/* 124 */     for (int i = 0; i < paramSeries.getData().size(); i++)
/* 125 */       dataItemAdded(paramSeries, i, (XYChart.Data)paramSeries.getData().get(i));
/*     */   }
/*     */ 
/*     */   protected void seriesRemoved(final XYChart.Series<X, Y> paramSeries)
/*     */   {
/*     */     Object localObject1;
/*     */     Object localObject2;
/*     */     Object localObject3;
/* 132 */     if (shouldAnimate()) {
/* 133 */       localObject1 = new ParallelTransition();
/* 134 */       ((ParallelTransition)localObject1).setOnFinished(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 136 */           ScatterChart.this.removeSeriesFromDisplay(paramSeries);
/*     */         }
/*     */       });
/* 139 */       for (localObject2 = paramSeries.getData().iterator(); ((Iterator)localObject2).hasNext(); ) { localObject3 = (XYChart.Data)((Iterator)localObject2).next();
/* 140 */         final Node localNode = ((XYChart.Data)localObject3).getNode();
/*     */ 
/* 142 */         FadeTransition localFadeTransition = new FadeTransition(Duration.millis(500.0D), localNode);
/* 143 */         localFadeTransition.setToValue(0.0D);
/* 144 */         localFadeTransition.setOnFinished(new EventHandler() {
/*     */           public void handle(ActionEvent paramAnonymousActionEvent) {
/* 146 */             ScatterChart.this.getPlotChildren().remove(localNode);
/*     */           }
/*     */         });
/* 149 */         ((ParallelTransition)localObject1).getChildren().add(localFadeTransition);
/*     */       }
/* 151 */       ((ParallelTransition)localObject1).play();
/*     */     } else {
/* 153 */       for (localObject1 = paramSeries.getData().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (XYChart.Data)((Iterator)localObject1).next();
/* 154 */         localObject3 = ((XYChart.Data)localObject2).getNode();
/* 155 */         getPlotChildren().remove(localObject3);
/*     */       }
/* 157 */       removeSeriesFromDisplay(paramSeries);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void layoutPlotChildren()
/*     */   {
/* 164 */     for (int i = 0; i < getDataSize(); i++) {
/* 165 */       XYChart.Series localSeries = (XYChart.Series)getData().get(i);
/* 166 */       for (XYChart.Data localData = localSeries.begin; localData != null; localData = localData.next) {
/* 167 */         double d1 = getXAxis().getDisplayPosition(localData.getCurrentX());
/* 168 */         double d2 = getYAxis().getDisplayPosition(localData.getCurrentY());
/* 169 */         Node localNode = localData.getNode();
/* 170 */         if (localNode != null) {
/* 171 */           double d3 = localNode.prefWidth(-1.0D);
/* 172 */           double d4 = localNode.prefHeight(-1.0D);
/* 173 */           localNode.resizeRelocate(d1 - d3 / 2.0D, d2 - d4 / 2.0D, d3, d4);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateLegend()
/*     */   {
/* 183 */     this.legend.getItems().clear();
/* 184 */     if (getData() != null) {
/* 185 */       for (int i = 0; i < getData().size(); i++) {
/* 186 */         XYChart.Series localSeries = (XYChart.Series)getData().get(i);
/* 187 */         Legend.LegendItem localLegendItem = new Legend.LegendItem(localSeries.getName());
/* 188 */         if (((XYChart.Data)localSeries.getData().get(0)).getNode() != null) {
/* 189 */           localLegendItem.getSymbol().getStyleClass().addAll(((XYChart.Data)localSeries.getData().get(0)).getNode().getStyleClass());
/*     */         }
/* 191 */         this.legend.getItems().add(localLegendItem);
/*     */       }
/*     */     }
/* 194 */     if (this.legend.getItems().size() > 0) {
/* 195 */       if (getLegend() == null)
/* 196 */         setLegend(this.legend);
/*     */     }
/*     */     else
/* 199 */       setLegend(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.ScatterChart
 * JD-Core Version:    0.6.2
 */