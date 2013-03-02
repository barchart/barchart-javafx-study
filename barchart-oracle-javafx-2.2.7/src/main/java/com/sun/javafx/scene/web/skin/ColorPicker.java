/*     */ package com.sun.javafx.scene.web.skin;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.GridPane;
/*     */ import javafx.scene.layout.HBox;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.layout.VBox;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.paint.Paint;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ 
/*     */ public final class ColorPicker extends VBox
/*     */ {
/*     */   private GridPane colorGridContent;
/*     */   private HBox webNameHBox;
/*     */   private static final int SQUARE_SIZE = 12;
/*     */   private static final int NUM_OF_COLUMNS = 8;
/*     */   private static final int COLOR_GRID_WIDTH = 96;
/*     */   private ObservableList<ColorSquare> squares;
/*     */   public static final ObservableList<String> colorNames;
/*  47 */   private boolean initialized = false;
/*  48 */   private boolean updateModel = true;
/*     */   private TextField webTextField;
/*     */   private Rectangle valuePreview;
/* 259 */   int[] rawValues = { 0, 10105600, 3355392, 13056, 13158, 128, 3355546, 3355443, 8388608, 16737792, 8421376, 32768, 32896, 255, 6710937, 8421504, 16711680, 16750848, 10079232, 3184998, 3198156, 3368703, 8388736, 10066329, 16711935, 16763904, 16776960, 65280, 65535, 52479, 10105702, 12632256, 16751052, 16764057, 16777113, 13434828, 13434879, 10079487, 13408767, 16777215 };
/*     */ 
/* 297 */   private ObjectProperty<Color> colorProperty = new SimpleObjectProperty(this, "color");
/*     */ 
/* 342 */   private ObjectProperty<EventHandler<ActionEvent>> onAction = new SimpleObjectProperty()
/*     */   {
/*     */     protected void invalidated() {
/* 345 */       ColorPicker.this.setEventHandler(ActionEvent.ACTION, (EventHandler)get());
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 350 */       return ColorPicker.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 355 */       return "onAction";
/*     */     }
/* 342 */   };
/*     */ 
/*     */   ColorPicker()
/*     */   {
/* 148 */     getStyleClass().add("color-picker");
/*     */ 
/* 150 */     this.squares = FXCollections.observableArrayList();
/* 151 */     int i = this.rawValues.length;
/* 152 */     Color[] arrayOfColor = new Color[i];
/* 153 */     for (int j = 0; j < i; j++) {
/* 154 */       arrayOfColor[j] = Color.rgb(this.rawValues[j] >> 16, this.rawValues[j] >> 8 & 0xFF, this.rawValues[j] & 0xFF, 1.0D);
/* 155 */       ColorSquare localColorSquare = new ColorSquare(arrayOfColor[j]);
/* 156 */       this.squares.add(localColorSquare);
/*     */     }
/*     */ 
/* 159 */     this.valuePreview = new Rectangle(13.0D, 13.0D);
/* 160 */     this.valuePreview.getStyleClass().add("preview-square");
/* 161 */     this.valuePreview.setSmooth(false);
/*     */ 
/* 163 */     ColorPicker localColorPicker = this;
/*     */ 
/* 165 */     if (this.colorGridContent == null) {
/* 166 */       this.colorGridContent = makeColorGridContent();
/*     */     }
/*     */ 
/* 169 */     if (this.webNameHBox == null) {
/* 170 */       this.initialized = false;
/*     */ 
/* 172 */       this.webNameHBox = new HBox(5.0D);
/* 173 */       this.webNameHBox.setAlignment(Pos.CENTER);
/*     */ 
/* 175 */       this.webTextField = new TextField();
/* 176 */       this.webTextField.setPrefColumnCount(8);
/* 177 */       this.webTextField.setText("");
/* 178 */       this.webTextField.setId("ColorChooserWebTextField");
/*     */ 
/* 181 */       this.webTextField.textProperty().addListener(new InvalidationListener() {
/*     */         public void invalidated(Observable paramAnonymousObservable) {
/* 183 */           if (ColorPicker.this.updateModel) {
/* 184 */             String str = ColorPicker.this.webTextField.getText();
/* 185 */             if (str.length() >= (str.startsWith("#") ? 7 : 1))
/*     */               try {
/* 187 */                 ColorPicker.this.getValuePreview().setFill(Color.web(str));
/*     */               }
/*     */               catch (IllegalArgumentException localIllegalArgumentException)
/*     */               {
/*     */               }
/*     */           }
/*     */         }
/*     */       });
/* 196 */       this.webTextField.setOnAction(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 198 */           if (ColorPicker.this.updateModel) {
/* 199 */             String str = ColorPicker.this.webTextField.getText();
/* 200 */             if (str.length() >= (str.startsWith("#") ? 7 : 1))
/*     */               try {
/* 202 */                 ColorPicker.this.setColor(Color.web(str));
/* 203 */                 ColorPicker.this.fireEvent(new ActionEvent());
/*     */               }
/*     */               catch (IllegalArgumentException localIllegalArgumentException)
/*     */               {
/*     */               }
/*     */           }
/*     */         }
/*     */       });
/* 211 */       this.webNameHBox.getChildren().addAll(new Node[] { getValuePreview(), this.webTextField });
/*     */ 
/* 213 */       this.initialized = true;
/* 214 */       if (getColor() != null) {
/* 215 */         setValue(getColor());
/*     */       }
/*     */ 
/* 218 */       this.webNameHBox.setPrefWidth(96.0D);
/*     */     }
/*     */ 
/* 221 */     localColorPicker.getChildren().addAll(new Node[] { this.colorGridContent, this.webNameHBox });
/*     */   }
/*     */ 
/*     */   private GridPane makeColorGridContent() {
/* 225 */     GridPane localGridPane = new GridPane();
/* 226 */     int i = 0; int j = 0;
/* 227 */     for (ColorSquare localColorSquare : this.squares) {
/* 228 */       localGridPane.add(localColorSquare, i, j);
/* 229 */       i++;
/* 230 */       if (i == 8) {
/* 231 */         i = 0;
/* 232 */         j++;
/*     */       }
/*     */     }
/*     */ 
/* 236 */     return localGridPane;
/*     */   }
/*     */ 
/*     */   public boolean setValue(Object paramObject)
/*     */   {
/*     */     Iterator localIterator;
/* 240 */     if (this.squares != null)
/* 241 */       for (localIterator = this.squares.iterator(); localIterator.hasNext(); ) { localObject = (ColorSquare)localIterator.next();
/* 242 */         ((ColorSquare)localObject).setSelected(((ColorSquare)localObject).rect.getFill().equals(paramObject));
/*     */       }
/*     */     Object localObject;
/*     */     boolean bool;
/* 246 */     if (this.initialized) {
/* 247 */       localObject = (Color)paramObject;
/* 248 */       getValuePreview().setFill((Paint)localObject);
/* 249 */       this.updateModel = false;
/* 250 */       this.webTextField.setText(colorValueToWeb((Color)localObject));
/* 251 */       this.updateModel = true;
/* 252 */       bool = true;
/*     */     } else {
/* 254 */       bool = false;
/*     */     }
/* 256 */     return bool;
/*     */   }
/*     */ 
/*     */   static String colorValueToWeb(Color paramColor)
/*     */   {
/* 269 */     Object localObject = null;
/* 270 */     if (colorNames != null)
/*     */     {
/* 277 */       for (String str : colorNames) {
/* 278 */         if (Color.web(str, paramColor.getOpacity()).equals(paramColor)) {
/* 279 */           localObject = str;
/* 280 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 284 */     if (localObject == null) {
/* 285 */       localObject = colorValueToHex(paramColor);
/*     */     }
/* 287 */     return localObject;
/*     */   }
/*     */ 
/*     */   static String colorValueToHex(Color paramColor) {
/* 291 */     return String.format((Locale)null, "#%02x%02x%02x", new Object[] { Long.valueOf(Math.round(paramColor.getRed() * 255.0D)), Long.valueOf(Math.round(paramColor.getGreen() * 255.0D)), Long.valueOf(Math.round(paramColor.getBlue() * 255.0D)) });
/*     */   }
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 300 */     return (Color)this.colorProperty.getValue();
/*     */   }
/*     */ 
/*     */   public void setColor(Color paramColor) {
/* 304 */     if (paramColor == null) {
/* 305 */       return;
/*     */     }
/* 307 */     this.colorProperty.setValue(paramColor);
/* 308 */     setValue(getColor());
/*     */   }
/*     */ 
/*     */   public ObjectProperty<Color> colorProperty() {
/* 312 */     return this.colorProperty;
/*     */   }
/*     */ 
/*     */   public Rectangle getValuePreview() {
/* 316 */     return this.valuePreview;
/*     */   }
/*     */ 
/*     */   private Color getValue() {
/* 320 */     String str = this.webTextField.getText();
/* 321 */     if ((str != null) && (str.length() > 0))
/*     */       try {
/* 323 */         return Color.web(str);
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException) {
/*     */       }
/* 327 */     return Color.BLACK;
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {
/* 331 */     return this.onAction;
/*     */   }
/*     */ 
/*     */   public final void setOnAction(EventHandler<ActionEvent> paramEventHandler) {
/* 335 */     onActionProperty().set(paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final EventHandler<ActionEvent> getOnAction() {
/* 339 */     return (EventHandler)onActionProperty().get();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  54 */     Color.web("white", 1.0D);
/*  55 */     colorNames = FXCollections.observableArrayList();
/*  56 */     for (Field localField : Color.class.getDeclaredFields()) {
/*  57 */       int k = localField.getModifiers();
/*  58 */       if ((Modifier.isStatic(k)) && (Modifier.isPublic(k)) && (localField.getType().equals(Color.class))) {
/*  59 */         colorNames.add(localField.getName().toLowerCase());
/*     */       }
/*     */     }
/*  62 */     FXCollections.sort(colorNames);
/*     */   }
/*     */ 
/*     */   class ColorSquare extends StackPane
/*     */   {
/*     */     Rectangle rect;
/*     */     private ReadOnlyBooleanWrapper selected;
/* 138 */     private final long SELECTED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("selected");
/*     */ 
/*     */     ColorSquare(Color arg2)
/*     */     {
/*  69 */       getStyleClass().add("color-square");
/*  70 */       setFocusTraversable(true);
/*     */ 
/*  72 */       this.rect = new Rectangle(12.0D, 12.0D);
/*     */       Paint localPaint;
/*  73 */       this.rect.setFill(localPaint);
/*  74 */       this.rect.setSmooth(false);
/*  75 */       getChildren().add(this.rect);
/*     */ 
/*  77 */       addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  79 */           if ((paramAnonymousMouseEvent.getClickCount() == 1) && (!MouseEvent.impl_getPopupTrigger(paramAnonymousMouseEvent))) {
/*  80 */             ColorPicker.this.setColor(ColorPicker.this.getValue());
/*  81 */             paramAnonymousMouseEvent.consume();
/*  82 */             ColorPicker.ColorSquare.this.setHover(false);
/*  83 */             ColorPicker.ColorSquare.this.fireEvent(new ActionEvent());
/*     */           }
/*     */         }
/*     */       });
/*  87 */       hoverProperty().addListener(new InvalidationListener() {
/*     */         public void invalidated(Observable paramAnonymousObservable) {
/*  89 */           ColorPicker.this.updateModel = false;
/*  90 */           Color localColor = ColorPicker.ColorSquare.this.isHover() ? (Color)ColorPicker.ColorSquare.this.rect.getFill() : ColorPicker.this.getColor();
/*  91 */           ColorPicker.this.webTextField.setText(ColorPicker.colorValueToWeb(localColor));
/*  92 */           ColorPicker.this.getValuePreview().setFill(localColor);
/*  93 */           ColorPicker.ColorSquare.this.requestFocus();
/*  94 */           ColorPicker.this.updateModel = true;
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/*     */     protected final void setSelected(boolean paramBoolean) {
/* 100 */       selectedPropertyImpl().set(paramBoolean); } 
/* 101 */     public final boolean isSelected() { return this.selected == null ? false : this.selected.get(); }
/*     */ 
/*     */     public ReadOnlyBooleanProperty selectedProperty() {
/* 104 */       return selectedPropertyImpl().getReadOnlyProperty();
/*     */     }
/*     */     private ReadOnlyBooleanWrapper selectedPropertyImpl() {
/* 107 */       if (this.selected == null) {
/* 108 */         this.selected = new ReadOnlyBooleanWrapper() {
/*     */           protected void invalidated() {
/* 110 */             ColorPicker.ColorSquare.this.impl_pseudoClassStateChanged("selected");
/*     */           }
/*     */ 
/*     */           public Object getBean()
/*     */           {
/* 115 */             return ColorPicker.ColorSquare.this;
/*     */           }
/*     */ 
/*     */           public String getName()
/*     */           {
/* 120 */             return "selected";
/*     */           }
/*     */         };
/*     */       }
/* 124 */       return this.selected;
/*     */     }
/*     */ 
/*     */     protected double computePrefWidth(double paramDouble)
/*     */     {
/* 131 */       return 18.0D;
/*     */     }
/*     */ 
/*     */     protected double computePrefHeight(double paramDouble) {
/* 135 */       return 18.0D;
/*     */     }
/*     */ 
/*     */     public long impl_getPseudoClassState()
/*     */     {
/* 142 */       return super.impl_getPseudoClassState() | (isSelected() ? this.SELECTED_PSEUDOCLASS_STATE : 0L);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.web.skin.ColorPicker
 * JD-Core Version:    0.6.2
 */