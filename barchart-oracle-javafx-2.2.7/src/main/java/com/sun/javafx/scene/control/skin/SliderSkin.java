/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.behavior.SliderBehavior;
/*     */ import com.sun.javafx.scene.layout.region.BackgroundFill;
/*     */ import java.util.List;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.geometry.Side;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.chart.NumberAxis;
/*     */ import javafx.scene.control.Slider;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public class SliderSkin extends SkinBase<Slider, SliderBehavior>
/*     */ {
/*  45 */   private NumberAxis tickLine = null;
/*  46 */   private double trackToTickGap = 2.0D;
/*     */   private boolean showTickMarks;
/*     */   private double thumbWidth;
/*     */   private double thumbHeight;
/*     */   private double trackStart;
/*     */   private double trackLength;
/*     */   private double thumbTop;
/*     */   private double thumbLeft;
/*     */   private double preDragThumbPos;
/*     */   private Point2D dragStart;
/*     */   private StackPane thumb;
/*     */   private StackPane track;
/*     */ 
/*     */   public SliderSkin(Slider paramSlider)
/*     */   {
/*  64 */     super(paramSlider, new SliderBehavior(paramSlider));
/*     */ 
/*  66 */     initialize();
/*  67 */     requestLayout();
/*  68 */     registerChangeListener(paramSlider.minProperty(), "MIN");
/*  69 */     registerChangeListener(paramSlider.maxProperty(), "MAX");
/*  70 */     registerChangeListener(paramSlider.valueProperty(), "VALUE");
/*  71 */     registerChangeListener(paramSlider.orientationProperty(), "ORIENTATION");
/*  72 */     registerChangeListener(paramSlider.showTickMarksProperty(), "SHOW_TICK_MARKS");
/*  73 */     registerChangeListener(paramSlider.showTickLabelsProperty(), "SHOW_TICK_LABELS");
/*  74 */     registerChangeListener(paramSlider.majorTickUnitProperty(), "MAJOR_TICK_UNIT");
/*  75 */     registerChangeListener(paramSlider.minorTickCountProperty(), "MINOR_TICK_COUNT");
/*     */   }
/*     */ 
/*     */   private void initialize() {
/*  79 */     this.thumb = new StackPane();
/*  80 */     this.thumb.getStyleClass().setAll(new String[] { "thumb" });
/*  81 */     this.track = new StackPane();
/*  82 */     this.track.getStyleClass().setAll(new String[] { "track" });
/*     */ 
/*  85 */     getChildren().clear();
/*  86 */     getChildren().addAll(new Node[] { this.track, this.thumb });
/*  87 */     setShowTickMarks(((Slider)getSkinnable()).isShowTickMarks(), ((Slider)getSkinnable()).isShowTickLabels());
/*  88 */     this.track.setOnMousePressed(new EventHandler() {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  90 */         if (!SliderSkin.this.thumb.isPressed())
/*  91 */           if (((Slider)SliderSkin.this.getSkinnable()).getOrientation() == Orientation.HORIZONTAL)
/*  92 */             ((SliderBehavior)SliderSkin.this.getBehavior()).trackPress(paramAnonymousMouseEvent, paramAnonymousMouseEvent.getX() / SliderSkin.this.trackLength);
/*  93 */           else ((SliderBehavior)SliderSkin.this.getBehavior()).trackPress(paramAnonymousMouseEvent, paramAnonymousMouseEvent.getY() / SliderSkin.this.trackLength);
/*     */       }
/*     */     });
/*  98 */     this.track.setOnMouseReleased(new EventHandler()
/*     */     {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/* 102 */         ((SliderBehavior)SliderSkin.this.getBehavior()).trackRelease(paramAnonymousMouseEvent, 0.0D);
/*     */       }
/*     */     });
/* 106 */     this.thumb.setOnMousePressed(new EventHandler() {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 108 */         ((SliderBehavior)SliderSkin.this.getBehavior()).thumbPressed(paramAnonymousMouseEvent, 0.0D);
/* 109 */         SliderSkin.this.dragStart = SliderSkin.this.thumb.localToParent(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/* 110 */         SliderSkin.this.preDragThumbPos = ((((Slider)SliderSkin.this.getSkinnable()).getValue() - ((Slider)SliderSkin.this.getSkinnable()).getMin()) / (((Slider)SliderSkin.this.getSkinnable()).getMax() - ((Slider)SliderSkin.this.getSkinnable()).getMin()));
/*     */       }
/*     */     });
/* 115 */     this.thumb.setOnMouseReleased(new EventHandler() {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 117 */         ((SliderBehavior)SliderSkin.this.getBehavior()).thumbReleased(paramAnonymousMouseEvent);
/*     */       }
/*     */     });
/* 121 */     this.thumb.setOnMouseDragged(new EventHandler() {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 123 */         Point2D localPoint2D = SliderSkin.this.thumb.localToParent(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/* 124 */         double d = ((Slider)SliderSkin.this.getSkinnable()).getOrientation() == Orientation.HORIZONTAL ? localPoint2D.getX() - SliderSkin.this.dragStart.getX() : -(localPoint2D.getY() - SliderSkin.this.dragStart.getY());
/*     */ 
/* 126 */         ((SliderBehavior)SliderSkin.this.getBehavior()).thumbDragged(paramAnonymousMouseEvent, SliderSkin.this.preDragThumbPos + d / SliderSkin.this.trackLength);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void setShowTickMarks(boolean paramBoolean1, boolean paramBoolean2) {
/* 132 */     this.showTickMarks = ((paramBoolean1) || (paramBoolean2));
/* 133 */     Slider localSlider = (Slider)getSkinnable();
/* 134 */     if (this.showTickMarks) {
/* 135 */       if (this.tickLine == null) {
/* 136 */         this.tickLine = new NumberAxis();
/* 137 */         this.tickLine.setAutoRanging(false);
/* 138 */         this.tickLine.setSide(localSlider.getOrientation() == null ? Side.RIGHT : localSlider.getOrientation() == Orientation.VERTICAL ? Side.RIGHT : Side.BOTTOM);
/* 139 */         this.tickLine.setUpperBound(localSlider.getMax());
/* 140 */         this.tickLine.setLowerBound(localSlider.getMin());
/* 141 */         this.tickLine.setTickUnit(localSlider.getMajorTickUnit());
/* 142 */         this.tickLine.setTickMarkVisible(paramBoolean1);
/* 143 */         this.tickLine.setTickLabelsVisible(paramBoolean2);
/* 144 */         this.tickLine.setMinorTickVisible(paramBoolean1);
/*     */ 
/* 147 */         this.tickLine.setMinorTickCount(Math.max(localSlider.getMinorTickCount(), 0) + 1);
/*     */ 
/* 152 */         getChildren().clear();
/* 153 */         getChildren().addAll(new Node[] { this.tickLine, this.track, this.thumb });
/*     */       } else {
/* 155 */         this.tickLine.setTickLabelsVisible(paramBoolean2);
/* 156 */         this.tickLine.setTickMarkVisible(paramBoolean1);
/* 157 */         this.tickLine.setMinorTickVisible(paramBoolean1);
/*     */       }
/*     */     }
/*     */     else {
/* 161 */       getChildren().clear();
/* 162 */       getChildren().addAll(new Node[] { this.track, this.thumb });
/*     */     }
/*     */ 
/* 166 */     requestLayout();
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString) {
/* 170 */     super.handleControlPropertyChanged(paramString);
/* 171 */     if ("ORIENTATION".equals(paramString)) {
/* 172 */       if ((this.showTickMarks) && (this.tickLine != null)) {
/* 173 */         this.tickLine.setSide(((Slider)getSkinnable()).getOrientation() == null ? Side.RIGHT : ((Slider)getSkinnable()).getOrientation() == Orientation.VERTICAL ? Side.RIGHT : Side.BOTTOM);
/*     */       }
/* 175 */       requestLayout();
/* 176 */     } else if ("VALUE".equals(paramString)) {
/* 177 */       positionThumb();
/* 178 */     } else if ("MIN".equals(paramString)) {
/* 179 */       if ((this.showTickMarks) && (this.tickLine != null)) {
/* 180 */         this.tickLine.setLowerBound(((Slider)getSkinnable()).getMin());
/*     */       }
/* 182 */       requestLayout();
/* 183 */     } else if ("MAX".equals(paramString)) {
/* 184 */       if ((this.showTickMarks) && (this.tickLine != null)) {
/* 185 */         this.tickLine.setUpperBound(((Slider)getSkinnable()).getMax());
/*     */       }
/* 187 */       requestLayout();
/* 188 */     } else if (("SHOW_TICK_MARKS".equals(paramString)) || ("SHOW_TICK_LABELS".equals(paramString))) {
/* 189 */       setShowTickMarks(((Slider)getSkinnable()).isShowTickMarks(), ((Slider)getSkinnable()).isShowTickLabels());
/* 190 */     } else if ("MAJOR_TICK_UNIT".equals(paramString)) {
/* 191 */       if (this.tickLine != null) {
/* 192 */         this.tickLine.setTickUnit(((Slider)getSkinnable()).getMajorTickUnit());
/* 193 */         requestLayout();
/*     */       }
/* 195 */     } else if (("MINOR_TICK_COUNT".equals(paramString)) && 
/* 196 */       (this.tickLine != null)) {
/* 197 */       this.tickLine.setMinorTickCount(Math.max(((Slider)getSkinnable()).getMinorTickCount(), 0) + 1);
/* 198 */       requestLayout();
/*     */     }
/*     */   }
/*     */ 
/*     */   void positionThumb()
/*     */   {
/* 207 */     Slider localSlider = (Slider)getSkinnable();
/* 208 */     int i = ((Slider)getSkinnable()).getOrientation() == Orientation.HORIZONTAL ? 1 : 0;
/* 209 */     double d1 = i != 0 ? this.trackStart + (this.trackLength * ((localSlider.getValue() - localSlider.getMin()) / (localSlider.getMax() - localSlider.getMin())) - this.thumbWidth / 2.0D) : this.thumbLeft;
/*     */ 
/* 211 */     double d2 = i != 0 ? this.thumbTop : getInsets().getTop() + this.trackLength - this.trackLength * ((localSlider.getValue() - localSlider.getMin()) / (localSlider.getMax() - localSlider.getMin()));
/*     */ 
/* 214 */     this.thumb.setLayoutX(d1);
/* 215 */     this.thumb.setLayoutY(d2);
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/* 221 */     double d1 = getInsets().getLeft();
/* 222 */     double d2 = getInsets().getTop();
/* 223 */     double d3 = getWidth() - (getInsets().getLeft() + getInsets().getRight());
/* 224 */     double d4 = getHeight() - (getInsets().getTop() + getInsets().getBottom());
/*     */ 
/* 227 */     this.thumbWidth = this.thumb.prefWidth(-1.0D);
/* 228 */     this.thumbHeight = this.thumb.prefHeight(-1.0D);
/* 229 */     this.thumb.resize(this.thumbWidth, this.thumbHeight);
/*     */ 
/* 231 */     double d5 = (this.track.impl_getBackgroundFills() != null) && (this.track.impl_getBackgroundFills().size() > 0) ? ((BackgroundFill)this.track.impl_getBackgroundFills().get(0)).getTopLeftCornerRadius() : 0.0D;
/*     */     double d6;
/*     */     double d7;
/*     */     double d8;
/*     */     double d9;
/*     */     double d10;
/*     */     double d11;
/* 234 */     if (((Slider)getSkinnable()).getOrientation() == Orientation.HORIZONTAL) {
/* 235 */       d6 = this.showTickMarks ? this.tickLine.prefHeight(-1.0D) : 0.0D;
/* 236 */       d7 = this.track.prefHeight(-1.0D);
/* 237 */       d8 = Math.max(d7, this.thumbHeight);
/* 238 */       d9 = d8 + (this.showTickMarks ? this.trackToTickGap + d6 : 0.0D);
/* 239 */       d10 = d2 + (d4 - d9) / 2.0D;
/* 240 */       this.trackLength = (d3 - this.thumbWidth);
/* 241 */       this.trackStart = (d1 + this.thumbWidth / 2.0D);
/* 242 */       d11 = (int)(d10 + (d8 - d7) / 2.0D);
/* 243 */       this.thumbTop = ((int)(d10 + (d8 - this.thumbHeight) / 2.0D));
/*     */ 
/* 245 */       positionThumb();
/*     */ 
/* 247 */       this.track.resizeRelocate(this.trackStart - d5, d11, this.trackLength + d5 + d5, d7);
/*     */ 
/* 249 */       if (this.showTickMarks) {
/* 250 */         this.tickLine.setLayoutX(this.trackStart);
/* 251 */         this.tickLine.setLayoutY(d11 + d7 + this.trackToTickGap);
/* 252 */         this.tickLine.resize(this.trackLength, d6);
/* 253 */         this.tickLine.requestAxisLayout();
/*     */       } else {
/* 255 */         if (this.tickLine != null) {
/* 256 */           this.tickLine.resize(0.0D, 0.0D);
/* 257 */           this.tickLine.requestAxisLayout();
/*     */         }
/* 259 */         this.tickLine = null;
/*     */       }
/*     */     } else {
/* 262 */       d6 = this.showTickMarks ? this.tickLine.prefWidth(-1.0D) : 0.0D;
/* 263 */       d7 = this.track.prefWidth(-1.0D);
/* 264 */       d8 = Math.max(d7, this.thumbWidth);
/* 265 */       d9 = d8 + (this.showTickMarks ? this.trackToTickGap + d6 : 0.0D);
/* 266 */       d10 = d1 + (d3 - d9) / 2.0D;
/* 267 */       this.trackLength = (d4 - this.thumbHeight);
/* 268 */       this.trackStart = (d2 + this.thumbHeight / 2.0D);
/* 269 */       d11 = (int)(d10 + (d8 - d7) / 2.0D);
/* 270 */       this.thumbLeft = ((int)(d10 + (d8 - this.thumbWidth) / 2.0D));
/*     */ 
/* 272 */       positionThumb();
/*     */ 
/* 274 */       this.track.resizeRelocate(d11, this.trackStart - d5, d7, this.trackLength + d5 + d5);
/*     */ 
/* 276 */       if (this.showTickMarks) {
/* 277 */         this.tickLine.setLayoutX(d11 + d7 + this.trackToTickGap);
/* 278 */         this.tickLine.setLayoutY(this.trackStart);
/* 279 */         this.tickLine.resize(d6, this.trackLength);
/* 280 */         this.tickLine.requestAxisLayout();
/*     */       } else {
/* 282 */         if (this.tickLine != null) {
/* 283 */           this.tickLine.resize(0.0D, 0.0D);
/* 284 */           this.tickLine.requestAxisLayout();
/*     */         }
/* 286 */         this.tickLine = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   double minTrackLength() {
/* 292 */     return 2.0D * this.thumb.prefWidth(-1.0D);
/*     */   }
/*     */ 
/*     */   protected double computeMinWidth(double paramDouble) {
/* 296 */     if (((Slider)getSkinnable()).getOrientation() == Orientation.HORIZONTAL) {
/* 297 */       return getInsets().getLeft() + minTrackLength() + this.thumb.minWidth(-1.0D) + getInsets().getRight();
/*     */     }
/* 299 */     return getInsets().getLeft() + this.thumb.prefWidth(-1.0D) + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computeMinHeight(double paramDouble)
/*     */   {
/* 304 */     if (((Slider)getSkinnable()).getOrientation() == Orientation.HORIZONTAL) {
/* 305 */       return getInsets().getTop() + this.thumb.prefHeight(-1.0D) + getInsets().getBottom();
/*     */     }
/* 307 */     return getInsets().getTop() + minTrackLength() + this.thumb.prefHeight(-1.0D) + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 312 */     if (((Slider)getSkinnable()).getOrientation() == Orientation.HORIZONTAL) {
/* 313 */       if (this.showTickMarks) {
/* 314 */         return Math.max(140.0D, this.tickLine.prefWidth(-1.0D));
/*     */       }
/* 316 */       return 140.0D;
/*     */     }
/*     */ 
/* 320 */     return getInsets().getLeft() + Math.max(this.thumb.prefWidth(-1.0D), this.track.prefWidth(-1.0D)) + (this.showTickMarks ? this.trackToTickGap + this.tickLine.prefWidth(-1.0D) : 0.0D) + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble)
/*     */   {
/* 326 */     if (((Slider)getSkinnable()).getOrientation() == Orientation.HORIZONTAL) {
/* 327 */       return getInsets().getTop() + Math.max(this.thumb.prefHeight(-1.0D), this.track.prefHeight(-1.0D)) + (this.showTickMarks ? this.trackToTickGap + this.tickLine.prefHeight(-1.0D) : 0.0D) + getInsets().getBottom();
/*     */     }
/*     */ 
/* 330 */     if (this.showTickMarks) {
/* 331 */       return Math.max(140.0D, this.tickLine.prefHeight(-1.0D));
/*     */     }
/* 333 */     return 140.0D;
/*     */   }
/*     */ 
/*     */   protected double computeMaxWidth(double paramDouble)
/*     */   {
/* 339 */     if (((Slider)getSkinnable()).getOrientation() == Orientation.HORIZONTAL) {
/* 340 */       return 1.7976931348623157E+308D;
/*     */     }
/* 342 */     return ((Slider)getSkinnable()).prefWidth(-1.0D);
/*     */   }
/*     */ 
/*     */   protected double computeMaxHeight(double paramDouble)
/*     */   {
/* 347 */     if (((Slider)getSkinnable()).getOrientation() == Orientation.HORIZONTAL) {
/* 348 */       return ((Slider)getSkinnable()).prefHeight(paramDouble);
/*     */     }
/* 350 */     return 1.7976931348623157E+308D;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.SliderSkin
 * JD-Core Version:    0.6.2
 */