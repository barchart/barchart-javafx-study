/*     */ package com.sun.javafx.charts;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.beans.property.StringPropertyBase;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Dimension2D;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.ContentDisplay;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.layout.Region;
/*     */ 
/*     */ public class Legend extends Region
/*     */ {
/*     */   private static final int GAP = 5;
/*  52 */   private int rows = 0; private int columns = 0;
/*  53 */   private ListChangeListener<LegendItem> itemsListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change<? extends Legend.LegendItem> paramAnonymousChange) {
/*  55 */       Legend.this.getChildren().clear();
/*     */       Legend.LegendItem localLegendItem;
/*  56 */       for (Iterator localIterator = Legend.this.getItems().iterator(); localIterator.hasNext(); Legend.this.getChildren().add(localLegendItem.label)) localLegendItem = (Legend.LegendItem)localIterator.next();
/*  57 */       if (Legend.this.isVisible()) Legend.this.requestLayout();
/*     */     }
/*  53 */   };
/*     */ 
/*  65 */   private BooleanProperty vertical = new BooleanPropertyBase(false) {
/*     */     protected void invalidated() {
/*  67 */       Legend.this.requestLayout();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/*  72 */       return Legend.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/*  77 */       return "vertical";
/*     */     }
/*  65 */   };
/*     */ 
/*  85 */   private ObjectProperty<ObservableList<LegendItem>> items = new ObjectPropertyBase() {
/*  86 */     ObservableList<Legend.LegendItem> oldItems = null;
/*     */ 
/*  88 */     protected void invalidated() { if (this.oldItems != null) this.oldItems.removeListener(Legend.this.itemsListener);
/*  89 */       Legend.this.getChildren().clear();
/*  90 */       ObservableList localObservableList = (ObservableList)get();
/*  91 */       if (localObservableList != null) {
/*  92 */         localObservableList.addListener(Legend.this.itemsListener);
/*     */         Legend.LegendItem localLegendItem;
/*  93 */         for (Iterator localIterator = localObservableList.iterator(); localIterator.hasNext(); Legend.this.getChildren().add(localLegendItem.label)) localLegendItem = (Legend.LegendItem)localIterator.next();
/*     */       }
/*  95 */       this.oldItems = ((ObservableList)get());
/*  96 */       Legend.this.requestLayout();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 101 */       return Legend.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 106 */       return "items";
/*     */     }
/*  85 */   };
/*     */ 
/*     */   public final boolean isVertical()
/*     */   {
/*  80 */     return this.vertical.get(); } 
/*  81 */   public final void setVertical(boolean paramBoolean) { this.vertical.set(paramBoolean); } 
/*  82 */   public final BooleanProperty verticalProperty() { return this.vertical; }
/*     */ 
/*     */ 
/*     */   public final void setItems(ObservableList<LegendItem> paramObservableList)
/*     */   {
/* 109 */     itemsProperty().set(paramObservableList); } 
/* 110 */   public final ObservableList<LegendItem> getItems() { return (ObservableList)this.items.get(); } 
/* 111 */   public final ObjectProperty<ObservableList<LegendItem>> itemsProperty() { return this.items; }
/*     */ 
/*     */ 
/*     */   public Legend()
/*     */   {
/* 118 */     setItems(FXCollections.observableArrayList());
/* 119 */     getStyleClass().setAll(new String[] { "chart-legend" });
/*     */   }
/*     */ 
/*     */   private Dimension2D getTileSize()
/*     */   {
/* 125 */     double d1 = 0.0D;
/* 126 */     double d2 = 0.0D;
/* 127 */     for (LegendItem localLegendItem : getItems()) {
/* 128 */       d1 = Math.max(d1, localLegendItem.label.prefWidth(-1.0D));
/* 129 */       d2 = Math.max(d2, localLegendItem.label.prefHeight(-1.0D));
/*     */     }
/* 131 */     return new Dimension2D(Math.ceil(d1), Math.ceil(d2));
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble) {
/* 135 */     double d = paramDouble - getInsets().getTop() - getInsets().getBottom();
/* 136 */     Dimension2D localDimension2D = getTileSize();
/* 137 */     if (paramDouble == -1.0D) {
/* 138 */       if (this.columns <= 1) return localDimension2D.getWidth() + getInsets().getLeft() + getInsets().getRight(); 
/*     */     }
/* 140 */     else { this.rows = ((int)Math.floor(d / (localDimension2D.getHeight() + 5.0D)));
/* 141 */       this.columns = (this.rows == 0 ? (int)Math.ceil(getItems().size()) : (int)Math.ceil(getItems().size() / this.rows));
/*     */     }
/*     */ 
/* 144 */     if (this.columns == 1) this.rows = Math.min(this.rows, getItems().size());
/* 145 */     return this.columns * (localDimension2D.getWidth() + 5.0D) - 5.0D + getInsets().getLeft() + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/* 149 */     double d = paramDouble - getInsets().getLeft() - getInsets().getRight();
/* 150 */     Dimension2D localDimension2D = getTileSize();
/* 151 */     if (paramDouble == -1.0D) {
/* 152 */       if (this.rows <= 1) return localDimension2D.getHeight() + getInsets().getTop() + getInsets().getBottom(); 
/*     */     }
/* 154 */     else { this.columns = ((int)Math.floor(d / (localDimension2D.getWidth() + 5.0D)));
/* 155 */       this.rows = (this.columns == 0 ? (int)Math.ceil(getItems().size()) : (int)Math.ceil(getItems().size() / this.columns));
/*     */     }
/*     */ 
/* 158 */     if (this.rows == 1) this.columns = Math.min(this.columns, getItems().size());
/* 159 */     return this.rows * (localDimension2D.getHeight() + 5.0D) - 5.0D + getInsets().getTop() + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 163 */     Dimension2D localDimension2D = getTileSize();
/*     */     double d1;
/*     */     int i;
/*     */     double d2;
/*     */     int j;
/*     */     int k;
/* 164 */     if (isVertical()) {
/* 165 */       d1 = getInsets().getLeft();
/* 166 */       for (i = 0; i < this.columns; i++) {
/* 167 */         d2 = getInsets().getTop();
/* 168 */         for (j = 0; j < this.rows; j++) {
/* 169 */           k = i * this.rows + j;
/* 170 */           if (k >= getItems().size()) return;
/* 171 */           ((LegendItem)getItems().get(k)).label.resizeRelocate(d1, d2, localDimension2D.getWidth(), localDimension2D.getHeight());
/* 172 */           d2 += localDimension2D.getHeight() + 5.0D;
/*     */         }
/* 174 */         d1 += localDimension2D.getWidth() + 5.0D;
/*     */       }
/*     */     } else {
/* 177 */       d1 = getInsets().getTop();
/* 178 */       for (i = 0; i < this.rows; i++) {
/* 179 */         d2 = getInsets().getLeft();
/* 180 */         for (j = 0; j < this.columns; j++) {
/* 181 */           k = i * this.columns + j;
/* 182 */           if (k >= getItems().size()) return;
/* 183 */           ((LegendItem)getItems().get(k)).label.resizeRelocate(d2, d1, localDimension2D.getWidth(), localDimension2D.getHeight());
/* 184 */           d2 += localDimension2D.getWidth() + 5.0D;
/*     */         }
/* 186 */         d1 += localDimension2D.getHeight() + 5.0D;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class LegendItem
/*     */   {
/* 195 */     private Label label = new Label();
/*     */ 
/* 198 */     private StringProperty text = new StringPropertyBase() {
/*     */       protected void invalidated() {
/* 200 */         Legend.LegendItem.this.label.setText(get());
/*     */       }
/*     */ 
/*     */       public Object getBean()
/*     */       {
/* 205 */         return Legend.LegendItem.this;
/*     */       }
/*     */ 
/*     */       public String getName()
/*     */       {
/* 210 */         return "text";
/*     */       }
/* 198 */     };
/*     */ 
/* 219 */     private ObjectProperty<Node> symbol = new ObjectPropertyBase(new Region()) {
/*     */       protected void invalidated() {
/* 221 */         Node localNode = (Node)get();
/* 222 */         if (localNode != null) localNode.getStyleClass().setAll(new String[] { "chart-legend-item-symbol" });
/* 223 */         Legend.LegendItem.this.label.setGraphic(localNode);
/*     */       }
/*     */ 
/*     */       public Object getBean()
/*     */       {
/* 228 */         return Legend.LegendItem.this;
/*     */       }
/*     */ 
/*     */       public String getName()
/*     */       {
/* 233 */         return "symbol";
/*     */       }
/* 219 */     };
/*     */ 
/*     */     public final String getText()
/*     */     {
/* 213 */       return this.text.getValue(); } 
/* 214 */     public final void setText(String paramString) { this.text.setValue(paramString); } 
/* 215 */     public final StringProperty textProperty() { return this.text; }
/*     */ 
/*     */ 
/*     */     public final Node getSymbol()
/*     */     {
/* 236 */       return (Node)this.symbol.getValue(); } 
/* 237 */     public final void setSymbol(Node paramNode) { this.symbol.setValue(paramNode); } 
/* 238 */     public final ObjectProperty<Node> symbolProperty() { return this.symbol; }
/*     */ 
/*     */     public LegendItem(String paramString) {
/* 241 */       setText(paramString);
/* 242 */       this.label.getStyleClass().add("chart-legend-item");
/* 243 */       this.label.setAlignment(Pos.CENTER_LEFT);
/* 244 */       this.label.setContentDisplay(ContentDisplay.LEFT);
/* 245 */       this.label.setGraphic(getSymbol());
/* 246 */       getSymbol().getStyleClass().setAll(new String[] { "chart-legend-item-symbol" });
/*     */     }
/*     */ 
/*     */     public LegendItem(String paramString, Node paramNode) {
/* 250 */       this(paramString);
/* 251 */       setSymbol(paramNode);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.charts.Legend
 * JD-Core Version:    0.6.2
 */