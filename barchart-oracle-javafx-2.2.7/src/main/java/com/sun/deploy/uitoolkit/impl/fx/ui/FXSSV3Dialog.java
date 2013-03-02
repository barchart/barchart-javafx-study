/*     */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*     */ 
/*     */ import com.sun.deploy.trace.Trace;
/*     */ import com.sun.deploy.ui.AppInfo;
/*     */ import com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.CheckBox;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.Tooltip;
/*     */ import javafx.scene.layout.BorderPane;
/*     */ import javafx.scene.layout.HBox;
/*     */ import javafx.scene.layout.Pane;
/*     */ import javafx.scene.layout.VBox;
/*     */ import javafx.scene.text.Text;
/*     */ import javafx.stage.Stage;
/*     */ 
/*     */ public class FXSSV3Dialog
/*     */ {
/*  37 */   private static int MAIN_TEXT_WIDTH = 380;
/*     */   private AppInfo ainfo;
/*     */   private String masthead;
/*     */   private String mainText;
/*     */   private String location;
/*     */   private String prompt;
/*     */   private String multiPrompt;
/*     */   private String multiText;
/*     */   private String runText;
/*     */   private String updateText;
/*     */   private String cancelText;
/*     */   private String alwaysText;
/*     */   private URL updateURL;
/*  55 */   private String locationURL = "";
/*     */ 
/*  60 */   private int userAnswer = 1;
/*     */   private FXDialog dialog;
/*     */   private Button runButton;
/*     */   private Button updateButton;
/*     */   private Button cancelButton;
/*     */   private CheckBox alwaysCheckbox;
/*     */   private CheckBox multiClickCheckBox;
/*     */   private static final int MAX_URL_WIDTH = 280;
/*     */ 
/*     */   public static int showSSV3Dialog(Object owner, AppInfo ainfo, int messageType, String title, String masthead, String mainText, String location, String prompt, String multiPrompt, String multiText, String run, String update, String cancel, String alwaysText, URL updateURL)
/*     */   {
/* 103 */     FXSSV3Dialog dialog = new FXSSV3Dialog(owner, title);
/*     */ 
/* 106 */     dialog.ainfo = ainfo;
/* 107 */     dialog.masthead = masthead;
/* 108 */     dialog.mainText = mainText;
/* 109 */     dialog.location = location;
/* 110 */     dialog.prompt = prompt;
/* 111 */     dialog.multiPrompt = multiPrompt;
/* 112 */     dialog.multiText = multiText;
/* 113 */     dialog.runText = run;
/* 114 */     dialog.updateText = update;
/* 115 */     dialog.cancelText = cancel;
/* 116 */     dialog.alwaysText = alwaysText;
/* 117 */     dialog.updateURL = updateURL;
/*     */ 
/* 119 */     dialog.initComponents();
/*     */ 
/* 121 */     dialog.setVisible(true);
/* 122 */     return dialog.getAnswer();
/*     */   }
/*     */ 
/*     */   private FXSSV3Dialog(Object owner, String title)
/*     */   {
/* 131 */     Stage stage = null;
/*     */ 
/* 133 */     if ((owner instanceof Stage)) {
/* 134 */       stage = (Stage)owner;
/*     */     }
/* 136 */     this.dialog = new FXDialog(title, stage, true);
/*     */   }
/*     */ 
/*     */   private void initComponents() {
/*     */     try {
/*     */       try {
/* 142 */         this.locationURL = this.ainfo.getFrom().toString();
/*     */       } catch (Exception e) {
/* 144 */         this.locationURL = "";
/*     */       }
/*     */ 
/* 147 */       this.dialog.setResizable(false);
/* 148 */       this.dialog.setIconifiable(false);
/*     */ 
/* 150 */       Pane contentPane = createContentPane();
/*     */ 
/* 152 */       contentPane.getChildren().add(createMastHead());
/* 153 */       contentPane.getChildren().add(createMainContent());
/* 154 */       contentPane.getChildren().add(createOkCancelPanel());
/*     */     }
/*     */     catch (Throwable t) {
/* 157 */       t.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private Pane createContentPane()
/*     */   {
/* 167 */     VBox contentPane = new VBox() {
/*     */       protected double computePrefHeight(double width) {
/* 169 */         double h = super.computePrefHeight(width);
/* 170 */         return h;
/*     */       }
/*     */     };
/* 173 */     contentPane.setId("ssv3-content-panel");
/* 174 */     this.dialog.setContentPane(contentPane);
/* 175 */     return contentPane;
/*     */   }
/*     */ 
/*     */   private Node createMastHead()
/*     */   {
/* 188 */     UITextArea text = new UITextArea(this.masthead);
/* 189 */     text.setId("security-masthead-label");
/* 190 */     return text;
/*     */   }
/*     */ 
/*     */   private Pane createMainContent()
/*     */   {
/* 205 */     Pane warningPanel = createWarningPanel();
/*     */ 
/* 207 */     UITextArea text = new UITextArea(436.0D);
/* 208 */     text.setText(this.prompt);
/* 209 */     text.setId("ssv3-prompt");
/*     */ 
/* 211 */     BorderPane box = new BorderPane();
/* 212 */     box.setTop(warningPanel);
/* 213 */     box.setBottom(text);
/* 214 */     return box;
/*     */   }
/*     */ 
/*     */   private Pane createWarningPanel()
/*     */   {
/* 226 */     BorderPane border = new BorderPane();
/*     */ 
/* 228 */     border.setLeft(createShieldIcon());
/* 229 */     VBox box = createLocationPanel();
/*     */ 
/* 231 */     border.setCenter(box);
/* 232 */     return border;
/*     */   }
/*     */ 
/*     */   private VBox createLocationPanel()
/*     */   {
/* 241 */     VBox box = new VBox();
/*     */ 
/* 243 */     Text main = new Text(this.mainText);
/* 244 */     main.setId("ssv3-main-text");
/* 245 */     main.setWrappingWidth(MAIN_TEXT_WIDTH);
/* 246 */     box.getChildren().add(main);
/*     */ 
/* 248 */     Label locationLabel = new Label(this.location);
/* 249 */     locationLabel.setText(this.location);
/* 250 */     locationLabel.setId("ssv3-location-label");
/*     */ 
/* 252 */     Label url = new Label(this.locationURL);
/* 253 */     url.setTooltip(new Tooltip(this.locationURL));
/* 254 */     url.setMaxWidth(280.0D);
/* 255 */     url.setId("ssv3-location-url");
/*     */ 
/* 257 */     HBox hBox = new HBox();
/* 258 */     hBox.setId("ssv3-location-label-url");
/* 259 */     hBox.getChildren().add(locationLabel);
/* 260 */     hBox.getChildren().add(url);
/* 261 */     box.getChildren().add(hBox);
/* 262 */     return box;
/*     */   }
/*     */ 
/*     */   private Pane createShieldIcon()
/*     */   {
/* 270 */     Label warningIcon = new Label(null, ResourceManager.getIcon("yellowShield48.image"));
/* 271 */     warningIcon.setId("ssv3-shield");
/* 272 */     VBox box = new VBox();
/* 273 */     box.getChildren().add(warningIcon);
/* 274 */     return box;
/*     */   }
/*     */ 
/*     */   private Pane createOkCancelPanel()
/*     */   {
/* 292 */     HBox decisionPanel = new HBox();
/* 293 */     decisionPanel.getStyleClass().add("security-button-bar");
/*     */ 
/* 295 */     this.runButton = new Button(this.runText);
/*     */ 
/* 297 */     decisionPanel.getChildren().add(this.runButton);
/* 298 */     this.runButton.setOnAction(new EventHandler() {
/*     */       public void handle(ActionEvent t) {
/* 300 */         FXSSV3Dialog.this.runAction();
/*     */       }
/*     */     });
/* 304 */     if (this.updateText != null) {
/* 305 */       this.updateButton = new Button(this.updateText);
/* 306 */       this.updateButton.setOnAction(new EventHandler()
/*     */       {
/*     */         public void handle(ActionEvent t) {
/* 309 */           FXSSV3Dialog.this.updateAction();
/*     */         }
/*     */       });
/* 312 */       decisionPanel.getChildren().add(this.updateButton);
/*     */     }
/*     */ 
/* 315 */     this.cancelButton = new Button(this.cancelText);
/* 316 */     this.cancelButton.setOnAction(new EventHandler() {
/*     */       public void handle(ActionEvent e) {
/* 318 */         FXSSV3Dialog.this.userAnswer = 1;
/* 319 */         FXSSV3Dialog.this.closeDialog();
/*     */       }
/*     */     });
/* 322 */     decisionPanel.getChildren().add(this.cancelButton);
/* 323 */     this.runButton.setDefaultButton(true);
/*     */ 
/* 325 */     this.alwaysCheckbox = new CheckBox(this.alwaysText);
/* 326 */     this.alwaysCheckbox.setSelected(false);
/*     */ 
/* 328 */     VBox vBox = new VBox();
/*     */ 
/* 330 */     createMultSelectionBox(vBox);
/* 331 */     vBox.getChildren().add(decisionPanel);
/* 332 */     vBox.getChildren().add(this.alwaysCheckbox);
/*     */ 
/* 334 */     return vBox;
/*     */   }
/*     */ 
/*     */   private void createMultSelectionBox(VBox box)
/*     */   {
/* 341 */     if ((this.multiPrompt != null) && (this.multiText != null)) {
/* 342 */       this.runButton.setDisable(true);
/* 343 */       Label multiMessage = new Label(this.multiPrompt);
/* 344 */       multiMessage.setId("ssv3-multi-click");
/*     */ 
/* 346 */       HBox mb = new HBox();
/* 347 */       mb.getChildren().add(multiMessage);
/*     */ 
/* 349 */       box.getChildren().add(mb);
/*     */ 
/* 351 */       this.multiClickCheckBox = new CheckBox(this.multiText);
/* 352 */       this.multiClickCheckBox.setId("ssv3-checkbox");
/* 353 */       box.getChildren().add(this.multiClickCheckBox);
/*     */ 
/* 355 */       this.multiClickCheckBox.setOnAction(new EventHandler()
/*     */       {
/*     */         public void handle(ActionEvent t) {
/* 358 */           FXSSV3Dialog.this.runButton.setDisable(!FXSSV3Dialog.this.multiClickCheckBox.isSelected());
/* 359 */           if (FXSSV3Dialog.this.multiClickCheckBox.isSelected()) {
/* 360 */             FXSSV3Dialog.this.runButton.requestFocus();
/*     */           }
/* 363 */           else if (FXSSV3Dialog.this.updateButton != null) {
/* 364 */             FXSSV3Dialog.this.updateButton.requestFocus();
/*     */           }
/* 366 */           else if (FXSSV3Dialog.this.cancelButton != null)
/* 367 */             FXSSV3Dialog.this.cancelButton.requestFocus();
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   private void runAction()
/*     */   {
/* 381 */     if ((this.alwaysCheckbox != null) && (this.alwaysCheckbox.isSelected()))
/* 382 */       this.userAnswer = 2;
/*     */     else {
/* 384 */       this.userAnswer = 0;
/*     */     }
/* 386 */     closeDialog();
/*     */   }
/*     */ 
/*     */   private void updateAction()
/*     */   {
/*     */     try
/*     */     {
/* 419 */       Class factory = Class.forName("com.sun.deploy.config.Platform");
/* 420 */       Method m = factory.getMethod("get", new Class[0]);
/* 421 */       Object platform = m.invoke(null, new Object[0]);
/* 422 */       Method showDocument = platform.getClass().getMethod("showDocument", new Class[] { String.class });
/* 423 */       showDocument.invoke(platform, new Object[] { this.updateURL.toString() });
/*     */     } catch (Exception e) {
/* 425 */       Trace.ignored(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void closeDialog() {
/* 430 */     setVisible(false);
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean visible) {
/* 434 */     if (visible) {
/* 435 */       final FXDialog dlg = this.dialog;
/* 436 */       this.dialog.centerOnScreen();
/*     */ 
/* 438 */       Runnable runner = new Runnable() {
/*     */         public void run() {
/* 440 */           dlg.showAndWait();
/*     */         }
/*     */       };
/* 444 */       runner.run();
/*     */     } else {
/* 446 */       this.dialog.hide();
/*     */     }
/*     */   }
/*     */ 
/* 450 */   private int getAnswer() { return this.userAnswer; }
/*     */ 
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.FXSSV3Dialog
 * JD-Core Version:    0.6.2
 */