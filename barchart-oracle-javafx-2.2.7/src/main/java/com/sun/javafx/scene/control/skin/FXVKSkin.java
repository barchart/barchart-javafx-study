/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.robot.impl.FXRobotHelper;
/*     */ import com.sun.javafx.robot.impl.FXRobotHelper.FXRobotInputAccessor;
/*     */ import com.sun.javafx.scene.control.behavior.BehaviorBase;
/*     */ import com.sun.javafx.scene.control.skin.resources.EmbeddedResources;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ResourceBundle;
/*     */ import javafx.animation.Interpolator;
/*     */ import javafx.animation.KeyFrame;
/*     */ import javafx.animation.KeyValue;
/*     */ import javafx.animation.Timeline;
/*     */ import javafx.application.Platform;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.ContentDisplay;
/*     */ import javafx.scene.control.Control;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.image.ImageView;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.HBox;
/*     */ import javafx.scene.layout.Priority;
/*     */ import javafx.scene.layout.VBox;
/*     */ import javafx.stage.Popup;
/*     */ import javafx.stage.Screen;
/*     */ import javafx.stage.Window;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class FXVKSkin extends SkinBase<FXVK, BehaviorBase<FXVK>>
/*     */ {
/*     */   private static Popup vkPopup;
/*     */   private static Popup secondaryPopup;
/*     */   private static FXVK primaryVK;
/*     */   private static Timeline slideInTimeline;
/*     */   private static Timeline slideOutTimeline;
/*     */   private static FXVK secondaryVK;
/*     */   private static Timeline secondaryVKDelay;
/*     */   private static CharKey secondaryVKKey;
/*     */   private Node attachedNode;
/*     */   FXVK fxvk;
/*     */   Control[][] keyRows;
/*  93 */   private State state = State.NORMAL;
/*     */   static final double VK_WIDTH = 640.0D;
/*     */   static final double VK_HEIGHT = 243.0D;
/*     */   static final double VK_PORTRAIT_HEIGHT = 326.0D;
/*     */   static final double VK_SLIDE_MILLIS = 250.0D;
/*     */   static final double PREF_KEY_WIDTH = 56.0D;
/*     */   static final double PREF_PORTRAIT_KEY_WIDTH = 40.0D;
/*     */   static final double PREF_KEY_HEIGHT = 56.0D;
/* 105 */   double keyWidth = 56.0D;
/* 106 */   double keyHeight = 56.0D;
/*     */   private ShiftKey shiftKey;
/*     */   private SymbolKey symbolKey;
/*     */   private VBox vbox;
/* 114 */   private static DoubleProperty winY = new SimpleDoubleProperty();
/*     */ 
/*     */   public FXVKSkin(final FXVK paramFXVK)
/*     */   {
/* 128 */     super(paramFXVK, new BehaviorBase(paramFXVK));
/* 129 */     this.fxvk = paramFXVK;
/*     */ 
/* 131 */     paramFXVK.setFocusTraversable(false);
/*     */ 
/* 133 */     paramFXVK.attachedNodeProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 135 */         Node localNode = FXVKSkin.this.attachedNode;
/* 136 */         FXVKSkin.this.attachedNode = paramFXVK.getAttachedNode();
/*     */ 
/* 138 */         if (paramFXVK != FXVK.vk)
/*     */         {
/* 140 */           return;
/*     */         }
/*     */ 
/* 143 */         if (FXVKSkin.this.attachedNode != null) {
/* 144 */           if (FXVKSkin.this.keyRows == null) {
/* 145 */             FXVKSkin.this.createKeys();
/*     */           }
/*     */ 
/* 148 */           Scene localScene = FXVKSkin.this.attachedNode.getScene();
/* 149 */           paramFXVK.setVisible(true);
/*     */ 
/* 151 */           if (paramFXVK != FXVKSkin.secondaryVK) {
/* 152 */             if (FXVKSkin.secondaryVKDelay == null) {
/* 153 */               FXVKSkin.access$502(new Timeline());
/*     */             }
/* 155 */             KeyFrame localKeyFrame = new KeyFrame(Duration.millis(500.0D), new EventHandler() {
/*     */               public void handle(ActionEvent paramAnonymous2ActionEvent) {
/* 157 */                 if (FXVKSkin.secondaryVKKey != null)
/* 158 */                   FXVKSkin.this.showSecondaryVK(FXVKSkin.secondaryVKKey);
/*     */               }
/*     */             }
/*     */             , new KeyValue[0]);
/*     */ 
/* 162 */             FXVKSkin.secondaryVKDelay.getKeyFrames().setAll(new KeyFrame[] { localKeyFrame });
/*     */ 
/* 164 */             if (FXVKSkin.vkPopup == null) {
/* 165 */               FXVKSkin.access$002(new Popup());
/*     */ 
/* 175 */               double d1 = Utils.getScreen(FXVKSkin.this.attachedNode).getBounds().getHeight();
/*     */ 
/* 177 */               double d2 = Utils.getScreen(FXVKSkin.this.attachedNode).getVisualBounds().getHeight();
/*     */ 
/* 179 */               d2 = Math.min(d1, d2 + 4.0D);
/*     */ 
/* 181 */               FXVKSkin.access$802(new Timeline());
/* 182 */               FXVKSkin.slideInTimeline.getKeyFrames().setAll(new KeyFrame[] { new KeyFrame(Duration.millis(250.0D), new KeyValue[] { new KeyValue(FXVKSkin.winY, Double.valueOf(d2 - paramFXVK.prefHeight(-1.0D)), Interpolator.EASE_BOTH) }) });
/*     */ 
/* 187 */               FXVKSkin.access$902(new Timeline());
/* 188 */               FXVKSkin.slideOutTimeline.getKeyFrames().setAll(new KeyFrame[] { new KeyFrame(Duration.millis(250.0D), new KeyValue[] { new KeyValue(FXVKSkin.winY, Double.valueOf(d1), Interpolator.EASE_BOTH) }) });
/*     */             }
/*     */ 
/* 193 */             FXVKSkin.vkPopup.getContent().setAll(new Node[] { paramFXVK });
/*     */ 
/* 195 */             if (!FXVKSkin.vkPopup.isShowing()) {
/* 196 */               Platform.runLater(new Runnable() {
/*     */                 public void run() {
/* 198 */                   Rectangle2D localRectangle2D = Utils.getScreen(FXVKSkin.this.attachedNode).getBounds();
/*     */ 
/* 200 */                   FXVKSkin.vkPopup.show(FXVKSkin.this.attachedNode, (localRectangle2D.getWidth() - FXVKSkin.2.this.val$fxvk.prefWidth(-1.0D)) / 2.0D, localRectangle2D.getHeight() - FXVKSkin.2.this.val$fxvk.prefHeight(-1.0D));
/*     */                 }
/*     */ 
/*     */               });
/*     */             }
/*     */ 
/* 207 */             if ((localNode == null) || (localNode.getScene() != FXVKSkin.this.attachedNode.getScene())) {
/* 208 */               paramFXVK.setPrefWidth(localScene.getWidth());
/* 209 */               paramFXVK.setMaxWidth((-1.0D / 0.0D));
/* 210 */               paramFXVK.setPrefHeight(200.0D);
/*     */             }
/*     */ 
/* 213 */             if ((paramFXVK.getHeight() > 0.0D) && ((paramFXVK.getLayoutY() == 0.0D) || (paramFXVK.getLayoutY() > localScene.getHeight() - paramFXVK.getHeight())))
/*     */             {
/* 216 */               FXVKSkin.slideOutTimeline.stop();
/* 217 */               FXVKSkin.winY.set(FXVKSkin.vkPopup.getY());
/* 218 */               FXVKSkin.slideInTimeline.playFromStart();
/*     */             }
/*     */ 
/* 222 */             if ((localNode == null) || (localNode.getScene() != FXVKSkin.this.attachedNode.getScene())) {
/* 223 */               paramFXVK.setPrefWidth(640.0D);
/* 224 */               paramFXVK.setMinWidth((-1.0D / 0.0D));
/* 225 */               paramFXVK.setMaxWidth((-1.0D / 0.0D));
/* 226 */               paramFXVK.setPrefHeight(243.0D);
/* 227 */               paramFXVK.setMinHeight((-1.0D / 0.0D));
/*     */             }
/*     */           }
/*     */         } else {
/* 231 */           if (paramFXVK != FXVKSkin.secondaryVK) {
/* 232 */             FXVKSkin.slideInTimeline.stop();
/* 233 */             FXVKSkin.winY.set(FXVKSkin.vkPopup.getY());
/* 234 */             FXVKSkin.slideOutTimeline.playFromStart();
/*     */           }
/*     */ 
/* 237 */           if (FXVKSkin.secondaryVK != null) {
/* 238 */             FXVKSkin.secondaryVK.setAttachedNode(null);
/* 239 */             FXVKSkin.secondaryPopup.hide();
/*     */           }
/* 241 */           return;
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void createKeys() {
/* 248 */     getChildren().clear();
/*     */     Object localObject2;
/*     */     Object localObject1;
/*     */     String str1;
/*     */     int i3;
/*     */     Double localDouble;
/*     */     Object localObject3;
/* 250 */     if (this.fxvk.chars != null)
/*     */     {
/* 252 */       int i = this.fxvk.chars.length;
/* 253 */       if (i > 1)
/*     */       {
/* 255 */         localObject2 = new String[i];
/* 256 */         int k = 0;
/*     */         String str4;
/* 257 */         for (str4 : this.fxvk.chars) {
/* 258 */           if (Character.isLetter(str4.charAt(0))) {
/* 259 */             localObject2[(k++)] = str4;
/*     */           }
/*     */         }
/* 262 */         for (str4 : this.fxvk.chars) {
/* 263 */           if (!Character.isLetter(str4.charAt(0))) {
/* 264 */             localObject2[(k++)] = str4;
/*     */           }
/*     */         }
/*     */ 
/* 268 */         int n = (int)Math.floor(Math.sqrt(Math.max(1, i - 2)));
/* 269 */         ??? = (int)Math.ceil(i / n);
/* 270 */         this.keyRows = new Control[n][];
/* 271 */         for (??? = 0; ??? < n; ???++) {
/* 272 */           this.keyRows[???] = makeKeyRow((String[])(String[])Arrays.copyOfRange((Object[])localObject2, ??? * ???, Math.min((??? + 1) * ???, i)));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 277 */         this.keyRows = new Control[0][];
/*     */       }
/*     */     }
/*     */     else {
/* 281 */       localObject1 = new ArrayList();
/* 282 */       localObject2 = new ArrayList();
/* 283 */       ArrayList localArrayList = new ArrayList();
/* 284 */       str1 = FXVK.VK_TYPE_NAMES[this.fxvk.vkType];
/* 285 */       ??? = 0;
/*     */       try {
/* 287 */         String str2 = "FXVK." + str1 + ".row%d.key%02d";
/* 288 */         while (EmbeddedResources.getBundle().containsKey(String.format(str2, new Object[] { Integer.valueOf(++???), Integer.valueOf(1) }))) {
/* 289 */           i3 = 0;
/*     */ 
/* 291 */           while (EmbeddedResources.getBundle().containsKey(String.format(str2, new Object[] { Integer.valueOf(???), Integer.valueOf(++i3) }))) {
/* 292 */             ((ArrayList)localObject2).add(EmbeddedResources.getString(String.format(str2, new Object[] { Integer.valueOf(???), Integer.valueOf(i3) })));
/* 293 */             localDouble = Double.valueOf(-1.0D);
/* 294 */             localObject3 = String.format(str2 + ".width", new Object[] { Integer.valueOf(???), Integer.valueOf(i3) });
/* 295 */             if (EmbeddedResources.getBundle().containsKey((String)localObject3)) {
/*     */               try {
/* 297 */                 localDouble = new Double(EmbeddedResources.getString((String)localObject3));
/*     */               } catch (NumberFormatException localNumberFormatException) {
/* 299 */                 System.err.println((String)localObject3 + "=" + EmbeddedResources.getString((String)localObject3));
/* 300 */                 System.err.println(localNumberFormatException);
/*     */               }
/*     */             }
/* 303 */             localArrayList.add(localDouble);
/*     */           }
/* 305 */           ((ArrayList)localObject1).add(makeKeyRow((List)localObject2, localArrayList));
/* 306 */           ((ArrayList)localObject2).clear();
/* 307 */           localArrayList.clear();
/*     */         }
/*     */       } catch (Exception localException) {
/* 310 */         localException.printStackTrace();
/*     */       }
/* 312 */       this.keyRows = ((Control[][])((ArrayList)localObject1).toArray(new Control[((ArrayList)localObject1).size()][]));
/*     */     }
/*     */ 
/* 315 */     this.vbox = new VBox();
/* 316 */     this.vbox.setId("vbox");
/* 317 */     getChildren().add(this.vbox);
/*     */ 
/* 322 */     for (str1 : this.keyRows) {
/* 323 */       HBox localHBox = new HBox();
/* 324 */       localHBox.setId("hbox");
/*     */ 
/* 326 */       localHBox.setAlignment(this.fxvk.chars != null ? Pos.CENTER_LEFT : Pos.CENTER);
/* 327 */       this.vbox.getChildren().add(localHBox);
/* 328 */       for (localDouble : str1) {
/* 329 */         localHBox.getChildren().add(localDouble);
/* 330 */         HBox.setHgrow(localDouble, Priority.ALWAYS);
/* 331 */         if ((localDouble instanceof Key)) {
/* 332 */           localObject3 = (Key)localDouble;
/* 333 */           int i5 = ((Key)localObject3).getText().length();
/* 334 */           if ((i5 != 1) && (localObject3.getClass().getSimpleName().equals("CharKey")))
/*     */           {
/* 338 */             ((Key)localObject3).setGraphicTextGap(((Key)localObject3).getGraphicTextGap() + 2 * i5);
/*     */           }
/* 340 */           if (!(((Key)localObject3).getGraphic() instanceof Label));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private Control[] makeKeyRow(String[] paramArrayOfString)
/*     */   {
/* 350 */     return makeKeyRow(Arrays.asList(paramArrayOfString), null);
/*     */   }
/*     */ 
/*     */   private Control[] makeKeyRow(List<String> paramList, List<Double> paramList1) {
/* 354 */     Control[] arrayOfControl = new Control[paramList.size()];
/* 355 */     for (int i = 0; i < arrayOfControl.length; i++) {
/* 356 */       String str = (String)paramList.get(i);
/* 357 */       Double localDouble = Double.valueOf(1.0D);
/* 358 */       if ((paramList1 != null) && (((Double)paramList1.get(i)).doubleValue() > 0.0D))
/* 359 */         localDouble = (Double)paramList1.get(i);
/*     */       CommandKey localCommandKey;
/* 361 */       if ("BACKSPACE".equals(str)) {
/* 362 */         localCommandKey = new CommandKey("⌫", KeyCode.BACK_SPACE, localDouble.doubleValue());
/* 363 */         localCommandKey.setId("backspace-key");
/*     */ 
/* 365 */         setIcon(localCommandKey, "fxvk-backspace-button.png");
/* 366 */         arrayOfControl[i] = localCommandKey;
/* 367 */       } else if ("ENTER".equals(str)) {
/* 368 */         localCommandKey = new CommandKey("↵", KeyCode.ENTER, localDouble.doubleValue());
/* 369 */         localCommandKey.setId("enter-key");
/*     */ 
/* 371 */         setIcon(localCommandKey, "fxvk-enter-button.png");
/* 372 */         arrayOfControl[i] = localCommandKey;
/* 373 */       } else if ("SHIFT".equals(str)) {
/* 374 */         this.shiftKey = new ShiftKey(localDouble.doubleValue());
/* 375 */         this.shiftKey.setId("shift-key");
/*     */ 
/* 377 */         setIcon(this.shiftKey, "fxvk-shift-button.png");
/* 378 */         arrayOfControl[i] = this.shiftKey;
/* 379 */       } else if ("SYM".equals(str)) {
/* 380 */         this.symbolKey = new SymbolKey("!#123 ABC", localDouble.doubleValue());
/* 381 */         this.symbolKey.setId("symbol-key");
/* 382 */         arrayOfControl[i] = this.symbolKey;
/*     */       } else {
/* 384 */         arrayOfControl[i] = new CharKey((String)paramList.get(i), localDouble.doubleValue());
/*     */       }
/*     */     }
/* 387 */     return arrayOfControl;
/*     */   }
/*     */ 
/*     */   private void setState(State paramState) {
/* 391 */     this.state = paramState;
/*     */ 
/* 393 */     this.shiftKey.setPressState((paramState == State.SHIFTED) || (paramState == State.SHIFT_LOCK));
/* 394 */     this.shiftKey.setDisable(paramState == State.NUMERIC);
/* 395 */     this.shiftKey.setId(paramState == State.SHIFT_LOCK ? "capslock-key" : "shift-key");
/*     */ 
/* 398 */     switch (4.$SwitchMap$com$sun$javafx$scene$control$skin$FXVKSkin$State[paramState.ordinal()]) { case 1:
/* 399 */       setIcon(this.shiftKey, null); break;
/*     */     case 2:
/* 400 */       setIcon(this.shiftKey, "fxvk-capslock-button.png"); break;
/*     */     default:
/* 401 */       setIcon(this.shiftKey, "fxvk-shift-button.png");
/*     */     }
/* 403 */     if (this.fxvk == secondaryVK)
/* 404 */       ((FXVKSkin)primaryVK.getSkin()).updateLabels();
/*     */     else
/* 406 */       updateLabels();
/*     */   }
/*     */ 
/*     */   private void setIcon(Key paramKey, String paramString)
/*     */   {
/* 412 */     if (paramString != null) {
/* 413 */       String str = getClass().getResource("caspian/" + paramString).toExternalForm();
/* 414 */       paramKey.setGraphic(new ImageView(str));
/*     */     } else {
/* 416 */       paramKey.setGraphic(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateLabels() {
/* 421 */     for (Control[] arrayOfControl1 : this.keyRows) {
/* 422 */       for (Control localControl : arrayOfControl1) {
/* 423 */         if ((localControl instanceof CharKey)) {
/* 424 */           CharKey localCharKey = (CharKey)localControl;
/* 425 */           String str1 = localCharKey.chars[0];
/* 426 */           String str2 = localCharKey.chars.length > 1 ? localCharKey.chars[1] : "";
/* 427 */           if ((localCharKey.chars.length > 1) && (this.state == State.NUMERIC)) {
/* 428 */             str1 = localCharKey.chars[1];
/* 429 */             if ((localCharKey.chars.length > 2) && (!Character.isLetter(localCharKey.chars[2].charAt(0))))
/* 430 */               str2 = localCharKey.chars[2];
/*     */             else
/* 432 */               str2 = "";
/*     */           }
/* 434 */           else if ((this.state == State.SHIFTED) || (this.state == State.SHIFT_LOCK)) {
/* 435 */             str1 = str1.toUpperCase();
/*     */           }
/* 437 */           localCharKey.setText(str1);
/* 438 */           if (localCharKey.graphic != null) {
/* 439 */             localCharKey.graphic.setText(str2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 444 */     if (this.symbolKey != null)
/* 445 */       this.symbolKey.setText(this.symbolKey.chars[0]);
/*     */   }
/*     */ 
/*     */   private void fireKeyEvent(Node paramNode, EventType<? extends KeyEvent> paramEventType, KeyCode paramKeyCode, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*     */   {
/*     */     try
/*     */     {
/* 454 */       Field localField = FXRobotHelper.class.getDeclaredField("inputAccessor");
/* 455 */       localField.setAccessible(true);
/* 456 */       FXRobotHelper.FXRobotInputAccessor localFXRobotInputAccessor = (FXRobotHelper.FXRobotInputAccessor)localField.get(null);
/* 457 */       paramNode.fireEvent(localFXRobotInputAccessor.createKeyEvent(paramEventType, paramKeyCode, paramString1, paramString2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4));
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 462 */       System.err.println(localException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void layoutChildren()
/*     */   {
/* 653 */     Insets localInsets = getInsets();
/* 654 */     if (this.vbox == null) {
/* 655 */       createKeys();
/*     */     }
/* 657 */     HBox localHBox = (HBox)this.vbox.getChildren().get(0);
/* 658 */     double d3 = localHBox.getSpacing();
/*     */ 
/* 660 */     double d4 = 0.0D;
/* 661 */     int i = 0;
/* 662 */     for (Iterator localIterator1 = this.vbox.getChildren().iterator(); localIterator1.hasNext(); ) { localNode1 = (Node)localIterator1.next();
/* 663 */       localHBox = (HBox)localNode1;
/* 664 */       j = 0;
/* 665 */       double d5 = 0.0D;
/* 666 */       for (Iterator localIterator2 = localHBox.getChildren().iterator(); localIterator2.hasNext(); ) { localObject1 = (Node)localIterator2.next();
/* 667 */         localObject2 = (Key)localObject1;
/* 668 */         j++;
/* 669 */         d5 += ((Key)localObject2).keyWidth;
/*     */       }
/*     */ 
/* 672 */       i = Math.max(i, j);
/* 673 */       d4 = Math.max(d4, d5);
/*     */     }
/*     */     Node localNode1;
/*     */     int j;
/*     */     Object localObject1;
/*     */     Object localObject2;
/*     */     double d1;
/*     */     double d2;
/* 677 */     if (this.fxvk == secondaryVK) {
/* 678 */       d1 = 40.0D;
/* 679 */       d2 = ((FXVKSkin)primaryVK.getSkin()).keyHeight;
/*     */     } else {
/* 681 */       d1 = (localHBox.getWidth() - (i - 1) * d3) / Math.max(d4, 10.0D);
/* 682 */       d2 = (getHeight() - localInsets.getTop() - localInsets.getBottom() - (this.keyRows.length - 1) * this.vbox.getSpacing()) / this.keyRows.length;
/*     */     }
/*     */ 
/* 685 */     if ((this.keyWidth != d1) || (this.keyHeight != d2)) {
/* 686 */       this.keyWidth = d1;
/* 687 */       this.keyHeight = d2;
/* 688 */       createKeys();
/*     */     }
/*     */ 
/* 691 */     super.layoutChildren();
/*     */ 
/* 693 */     for (localIterator1 = this.vbox.getChildren().iterator(); localIterator1.hasNext(); ) { localNode1 = (Node)localIterator1.next();
/* 694 */       localHBox = (HBox)localNode1;
/* 695 */       j = 0;
/* 696 */       k = 0;
/* 697 */       double d6 = 0.0D;
/* 698 */       for (localObject1 = localHBox.getChildren().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (Node)((Iterator)localObject1).next();
/* 699 */         localObject3 = (Key)localObject2;
/* 700 */         j++;
/* 701 */         if (((Key)localObject3).keyWidth > 1.0D) {
/* 702 */           k++;
/*     */         }
/* 704 */         d6 += ((Key)localObject3).keyWidth;
/*     */       }
/*     */ 
/* 707 */       d7 = localHBox.getWidth() - (j - 1) * d3 - d6 * d1;
/* 708 */       for (localObject3 = localHBox.getChildren().iterator(); ((Iterator)localObject3).hasNext(); ) { Node localNode2 = (Node)((Iterator)localObject3).next();
/* 709 */         Key localKey = (Key)localNode2;
/*     */ 
/* 711 */         if (((this.fxvk.vkType != 1) || (this.fxvk.getStyleClass().contains("fxvk-portrait"))) && (d7 > 0.0D) && (localKey.keyWidth > 1.0D))
/*     */         {
/* 714 */           localKey.setPrefWidth(localKey.keyWidth * this.keyWidth + d7 / k);
/*     */         }
/* 716 */         else localKey.setPrefWidth(localKey.keyWidth * this.keyWidth);  }  } int k;
/*     */     Object localObject3;
/*     */     double d7;
/*     */   }
/*     */ 
/* 723 */   private void showSecondaryVK(final CharKey paramCharKey) { if (paramCharKey != null) {
/* 724 */       primaryVK = this.fxvk;
/* 725 */       Node localNode = primaryVK.getAttachedNode();
/*     */ 
/* 727 */       if (secondaryVK == null) {
/* 728 */         secondaryVK = new FXVK();
/* 729 */         secondaryVK.getStyleClass().addAll(new String[] { "fxvk-secondary", "fxvk-portrait" });
/* 730 */         secondaryVK.setSkin(new FXVKSkin(secondaryVK));
/* 731 */         secondaryPopup = new Popup();
/* 732 */         secondaryPopup.setAutoHide(true);
/* 733 */         secondaryPopup.getContent().add(secondaryVK);
/*     */ 
/* 735 */         secondaryVK.impl_processCSS(false);
/*     */       }
/*     */       Object localObject1;
/*     */       Object localObject3;
/* 738 */       if (this.state == State.NUMERIC) {
/* 739 */         localObject1 = new ArrayList();
/* 740 */         for (localObject3 : paramCharKey.chars) {
/* 741 */           if (!Character.isLetter(localObject3.charAt(0))) {
/* 742 */             ((ArrayList)localObject1).add(localObject3);
/*     */           }
/*     */         }
/* 745 */         secondaryVK.chars = ((String[])((ArrayList)localObject1).toArray(new String[((ArrayList)localObject1).size()]));
/*     */       } else {
/* 747 */         localObject1 = new ArrayList();
/*     */ 
/* 749 */         for (localObject3 : paramCharKey.chars) {
/* 750 */           if (Character.isLetter(localObject3.charAt(0))) {
/* 751 */             if ((this.state == State.SHIFTED) || (this.state == State.SHIFT_LOCK))
/* 752 */               ((ArrayList)localObject1).add(localObject3.toUpperCase());
/*     */             else {
/* 754 */               ((ArrayList)localObject1).add(localObject3);
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 759 */         if ((paramCharKey.chars.length > 1) && (!Character.isLetter(paramCharKey.chars[1].charAt(0))))
/*     */         {
/* 761 */           ((ArrayList)localObject1).add(paramCharKey.chars[1]);
/*     */         }
/* 763 */         secondaryVK.chars = ((String[])((ArrayList)localObject1).toArray(new String[((ArrayList)localObject1).size()]));
/*     */       }
/*     */ 
/* 766 */       if (secondaryVK.chars.length > 1) {
/* 767 */         if (secondaryVK.getSkin() != null) {
/* 768 */           ((FXVKSkin)secondaryVK.getSkin()).createKeys();
/*     */         }
/*     */ 
/* 771 */         secondaryVK.setAttachedNode(localNode);
/* 772 */         localObject1 = (FXVKSkin)primaryVK.getSkin();
/* 773 */         ??? = (FXVKSkin)secondaryVK.getSkin();
/* 774 */         Insets localInsets = ((FXVKSkin)???).getInsets();
/* 775 */         ??? = secondaryVK.chars.length;
/* 776 */         int k = (int)Math.floor(Math.sqrt(Math.max(1, ??? - 2)));
/* 777 */         int m = (int)Math.ceil(??? / k);
/* 778 */         HBox localHBox = (HBox)this.vbox.getChildren().get(0);
/* 779 */         final double d1 = localInsets.getLeft() + localInsets.getRight() + m * 40.0D + (m - 1) * localHBox.getSpacing();
/*     */ 
/* 781 */         double d2 = localInsets.getTop() + localInsets.getBottom() + k * ((FXVKSkin)localObject1).keyHeight + (k - 1) * this.vbox.getSpacing();
/*     */ 
/* 783 */         secondaryVK.setPrefWidth(d1);
/* 784 */         secondaryVK.setMinWidth((-1.0D / 0.0D));
/* 785 */         secondaryVK.setPrefHeight(d2);
/* 786 */         secondaryVK.setMinHeight((-1.0D / 0.0D));
/* 787 */         Platform.runLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 790 */             Point2D localPoint2D = Utils.pointRelativeTo(paramCharKey, d1, this.val$h, HPos.CENTER, VPos.TOP, 5.0D, -3.0D, true);
/*     */ 
/* 793 */             double d1 = localPoint2D.getX();
/* 794 */             double d2 = localPoint2D.getY();
/* 795 */             Scene localScene = paramCharKey.getScene();
/* 796 */             d1 = Math.min(d1, localScene.getWindow().getX() + localScene.getWidth() - d1);
/* 797 */             FXVKSkin.secondaryPopup.show(paramCharKey.getScene().getWindow(), d1, d2);
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/* 802 */     else if (secondaryVK != null) {
/* 803 */       secondaryVK.setAttachedNode(null);
/* 804 */       secondaryPopup.hide();
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 116 */     winY.addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 118 */         if (FXVKSkin.vkPopup != null)
/* 119 */           FXVKSkin.vkPopup.setY(FXVKSkin.winY.get());
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private class SymbolKey extends FXVKSkin.Key
/*     */   {
/*     */     String str;
/*     */     String[] chars;
/* 628 */     EventHandler<ActionEvent> actionHandler = new EventHandler() {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent) {
/* 630 */         FXVKSkin.this.setState(FXVKSkin.this.state == FXVKSkin.State.NUMERIC ? FXVKSkin.State.NORMAL : FXVKSkin.State.NUMERIC);
/* 631 */         FXVKSkin.this.showSecondaryVK(null);
/*     */       }
/* 628 */     };
/*     */ 
/*     */     SymbolKey(String paramDouble, double arg3)
/*     */     {
/* 636 */       super(null, localObject, null);
/* 637 */       this.str = paramDouble;
/* 638 */       getStyleClass().add("special-key");
/*     */ 
/* 640 */       if (paramDouble.length() == 1)
/* 641 */         this.chars = new String[] { paramDouble };
/*     */       else {
/* 643 */         this.chars = paramDouble.split(" ");
/*     */       }
/* 645 */       setText(this.chars[0]);
/*     */ 
/* 647 */       setOnAction(this.actionHandler);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class ShiftKey extends FXVKSkin.Key
/*     */   {
/* 594 */     EventHandler<ActionEvent> actionHandler = new EventHandler() {
/* 595 */       long lastTime = -1L;
/*     */ 
/*     */       public void handle(ActionEvent paramAnonymousActionEvent) {
/* 598 */         FXVKSkin.this.showSecondaryVK(null);
/* 599 */         long l = System.currentTimeMillis();
/* 600 */         if ((this.lastTime > 0L) && (l - this.lastTime < 600L))
/* 601 */           FXVKSkin.this.setState(FXVKSkin.State.SHIFT_LOCK);
/* 602 */         else if ((FXVKSkin.this.state == FXVKSkin.State.SHIFTED) || (FXVKSkin.this.state == FXVKSkin.State.SHIFT_LOCK))
/* 603 */           FXVKSkin.this.setState(FXVKSkin.State.NORMAL);
/*     */         else {
/* 605 */           FXVKSkin.this.setState(FXVKSkin.State.SHIFTED);
/*     */         }
/* 607 */         this.lastTime = l;
/*     */       }
/* 594 */     };
/*     */ 
/*     */     ShiftKey(double arg2)
/*     */     {
/* 612 */       super("⇑", localObject, null);
/* 613 */       setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
/* 614 */       getStyleClass().add("special-key");
/* 615 */       setFocusTraversable(false);
/* 616 */       setOnAction(this.actionHandler);
/*     */     }
/*     */ 
/*     */     private void setPressState(boolean paramBoolean) {
/* 620 */       setPressed(paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class CommandKey extends FXVKSkin.Key
/*     */   {
/*     */     KeyCode code;
/* 569 */     EventHandler<ActionEvent> actionHandler = new EventHandler() {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent) {
/* 571 */         FXVKSkin.this.showSecondaryVK(null);
/* 572 */         Node localNode = FXVKSkin.this.fxvk.getAttachedNode();
/* 573 */         if ((localNode instanceof EventTarget)) {
/* 574 */           String str = FXVKSkin.CommandKey.this.getText();
/* 575 */           FXVKSkin.this.fireKeyEvent(localNode, KeyEvent.KEY_PRESSED, FXVKSkin.CommandKey.this.code, null, null, false, false, false, false);
/*     */ 
/* 577 */           if (FXVKSkin.this.state == FXVKSkin.State.SHIFTED)
/* 578 */             FXVKSkin.this.setState(FXVKSkin.State.NORMAL);
/*     */         }
/*     */       }
/* 569 */     };
/*     */ 
/*     */     CommandKey(String paramKeyCode, KeyCode paramDouble, double arg4)
/*     */     {
/* 585 */       super(paramKeyCode, localObject, null);
/* 586 */       this.code = paramDouble;
/* 587 */       setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
/* 588 */       getStyleClass().add("special-key");
/* 589 */       setOnAction(this.actionHandler);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class CharKey extends FXVKSkin.Key
/*     */   {
/*     */     String str;
/*     */     String[] chars;
/*     */     Label graphic;
/* 490 */     EventHandler<ActionEvent> actionHandler = new EventHandler() {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent) {
/* 492 */         if ((FXVKSkin.this.fxvk != FXVKSkin.secondaryVK) && (FXVKSkin.secondaryPopup != null) && (FXVKSkin.secondaryPopup.isShowing())) {
/* 493 */           return;
/*     */         }
/*     */ 
/* 496 */         Node localNode = FXVKSkin.this.fxvk.getAttachedNode();
/* 497 */         if ((localNode instanceof EventTarget)) {
/* 498 */           String str1 = FXVKSkin.CharKey.this.getText();
/* 499 */           if ((str1.length() > 1) && (str1.contains(" ")))
/*     */           {
/* 501 */             str1 = str1.split(" ")[0];
/*     */           }
/* 503 */           for (int i = 0; i < str1.length(); i++) {
/* 504 */             String str2 = str1.substring(i, i + 1);
/* 505 */             FXVKSkin.this.fireKeyEvent(localNode, KeyEvent.KEY_TYPED, null, str2, str2, FXVKSkin.this.state == FXVKSkin.State.SHIFTED, false, false, false);
/*     */           }
/*     */ 
/* 509 */           if (FXVKSkin.this.state == FXVKSkin.State.SHIFTED) {
/* 510 */             FXVKSkin.this.setState(FXVKSkin.State.NORMAL);
/*     */           }
/*     */         }
/*     */ 
/* 514 */         if (FXVKSkin.this.fxvk == FXVKSkin.secondaryVK)
/* 515 */           FXVKSkin.this.showSecondaryVK(null);
/*     */       }
/* 490 */     };
/*     */ 
/*     */     CharKey(String paramDouble, double arg3)
/*     */     {
/* 521 */       super(null, localObject, null);
/*     */ 
/* 523 */       this.str = paramDouble;
/* 524 */       setOnAction(this.actionHandler);
/*     */ 
/* 526 */       if (FXVKSkin.this.fxvk != FXVKSkin.secondaryVK) {
/* 527 */         setOnMousePressed(new EventHandler() {
/*     */           public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 529 */             FXVKSkin.this.showSecondaryVK(null);
/* 530 */             FXVKSkin.access$602(FXVKSkin.CharKey.this);
/* 531 */             FXVKSkin.secondaryVKDelay.playFromStart();
/*     */           }
/*     */         });
/* 535 */         setOnMouseReleased(new EventHandler() {
/*     */           public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 537 */             FXVKSkin.secondaryVKDelay.stop();
/*     */           }
/*     */         });
/*     */       }
/*     */ 
/* 542 */       if (paramDouble.length() == 1) {
/* 543 */         this.chars = new String[] { paramDouble };
/*     */       } else {
/* 545 */         this.chars = paramDouble.split(" ");
/* 546 */         for (int i = 0; i < this.chars.length; i++) {
/* 547 */           this.chars[i] = FXVKCharEntities.get(this.chars[i]);
/*     */         }
/*     */       }
/* 550 */       setContentDisplay(ContentDisplay.TOP);
/* 551 */       setGraphicTextGap(-16.0D);
/* 552 */       setText(this.chars[0]);
/* 553 */       setId(this.chars[0]);
/* 554 */       if (getText().length() > 1) {
/* 555 */         getStyleClass().add("multi-char-key");
/*     */       }
/*     */ 
/* 558 */       this.graphic = new Label(this.chars.length > 1 ? this.chars[1] : " ");
/* 559 */       this.graphic.setPrefWidth(FXVKSkin.this.keyWidth - 6.0D);
/* 560 */       this.graphic.setMinWidth((-1.0D / 0.0D));
/* 561 */       this.graphic.setPrefHeight(FXVKSkin.this.keyHeight / 2.0D - 8.0D);
/* 562 */       setGraphic(this.graphic);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class Key extends Button
/*     */   {
/*     */     private double keyWidth;
/*     */ 
/*     */     private Key(String paramDouble, double arg3)
/*     */     {
/* 472 */       super();
/*     */       Object localObject;
/* 474 */       this.keyWidth = localObject;
/*     */ 
/* 476 */       getStyleClass().add("key");
/* 477 */       setFocusTraversable(false);
/*     */ 
/* 479 */       setMinHeight((-1.0D / 0.0D));
/* 480 */       setPrefHeight(FXVKSkin.this.keyHeight);
/*     */     }
/*     */   }
/*     */ 
/*     */   static enum State
/*     */   {
/*  91 */     NORMAL, SHIFTED, SHIFT_LOCK, NUMERIC;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.FXVKSkin
 * JD-Core Version:    0.6.2
 */