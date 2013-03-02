/*     */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*     */ 
/*     */ import com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.security.cert.Certificate;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.Hyperlink;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.ScrollPane;
/*     */ import javafx.scene.control.Separator;
/*     */ import javafx.scene.control.TextArea;
/*     */ import javafx.scene.image.ImageView;
/*     */ import javafx.scene.layout.BorderPane;
/*     */ import javafx.scene.layout.HBox;
/*     */ import javafx.scene.layout.Pane;
/*     */ import javafx.scene.layout.Priority;
/*     */ import javafx.scene.layout.VBox;
/*     */ import javafx.stage.Stage;
/*     */ 
/*     */ class MoreInfoDialog extends FXDialog
/*     */ {
/*     */   private Hyperlink details;
/*     */   private String[] alerts;
/*     */   private String[] infos;
/*     */   private int securityInfoCount;
/*     */   private Certificate[] certs;
/*     */   private int start;
/*     */   private int end;
/*  42 */   private final String WARNING_ICON = "warning16.image";
/*  43 */   private final String INFO_ICON = "info16.image";
/*  44 */   private final int VERTICAL_STRUT = 18;
/*  45 */   private final int HORIZONTAL_STRUT = 12;
/*     */ 
/*  48 */   private final int TEXT_WIDTH = 326;
/*     */ 
/*     */   MoreInfoDialog(Stage parent, String[] alerts, String[] infos, int securityInfoCount, Certificate[] certs, int start, int end)
/*     */   {
/*  52 */     super(ResourceManager.getMessage("security.more.info.title"), parent, true);
/*  53 */     this.alerts = alerts;
/*  54 */     this.infos = infos;
/*  55 */     this.securityInfoCount = securityInfoCount;
/*  56 */     this.certs = certs;
/*  57 */     this.start = start;
/*  58 */     this.end = end;
/*  59 */     initComponents(null, null);
/*  60 */     setResizable(false);
/*     */   }
/*     */ 
/*     */   MoreInfoDialog(Stage parent, Pane detailPanel, Throwable throwable, Certificate[] certs)
/*     */   {
/*  66 */     super(ResourceManager.getMessage("security.more.info.title"));
/*  67 */     this.certs = certs;
/*  68 */     this.start = 0;
/*  69 */     this.end = (certs == null ? 0 : certs.length);
/*     */ 
/*  71 */     initComponents(detailPanel, throwable);
/*     */   }
/*     */ 
/*     */   private void initComponents(Pane detailPanel, Throwable throwable)
/*     */   {
/*  79 */     VBox contentPanel = new VBox();
/*  80 */     contentPanel.setId("more-info-dialog");
/*     */ 
/*  82 */     if (detailPanel != null) {
/*  83 */       VBox.setVgrow(detailPanel, Priority.ALWAYS);
/*  84 */       contentPanel.getChildren().add(detailPanel);
/*  85 */     } else if (throwable != null) {
/*  86 */       BorderPane labelPanel = new BorderPane();
/*     */ 
/*  88 */       Label label = new Label(ResourceManager.getString("exception.details.label"));
/*     */ 
/*  90 */       labelPanel.setLeft(label);
/*     */ 
/*  92 */       contentPanel.getChildren().add(labelPanel);
/*     */ 
/*  94 */       StringWriter sw = new StringWriter();
/*  95 */       PrintWriter pw = new PrintWriter(sw);
/*  96 */       throwable.printStackTrace(pw);
/*  97 */       TextArea text = new TextArea(sw.toString());
/*  98 */       text.setEditable(false);
/*  99 */       text.setWrapText(true);
/* 100 */       text.setPrefWidth(480.0D);
/* 101 */       text.setPrefHeight(240.0D);
/*     */ 
/* 103 */       ScrollPane sp = new ScrollPane();
/* 104 */       sp.setContent(text);
/* 105 */       sp.setFitToWidth(true);
/* 106 */       VBox.setVgrow(sp, Priority.ALWAYS);
/* 107 */       contentPanel.getChildren().add(sp);
/*     */ 
/* 109 */       if (this.certs != null)
/* 110 */         contentPanel.getChildren().add(getLinkPanel());
/*     */     }
/*     */     else {
/* 113 */       Pane securityPanel = getSecurityPanel();
/* 114 */       if (securityPanel.getChildren().size() > 0) {
/* 115 */         VBox.setVgrow(securityPanel, Priority.ALWAYS);
/* 116 */         contentPanel.getChildren().add(securityPanel);
/*     */       }
/* 118 */       if (this.certs != null) {
/* 119 */         contentPanel.getChildren().add(getLinkPanel());
/*     */       }
/* 121 */       contentPanel.getChildren().add(getIntegrationPanel());
/*     */     }
/* 123 */     contentPanel.getChildren().add(getBtnPanel());
/*     */ 
/* 125 */     setContentPane(contentPanel);
/*     */   }
/*     */ 
/*     */   private Pane getSecurityPanel()
/*     */   {
/* 142 */     VBox securityPanel = new VBox();
/*     */ 
/* 155 */     boolean showall = this.certs == null;
/*     */ 
/* 157 */     int start = (showall) || (this.alerts == null) ? 0 : 1;
/* 158 */     int end = this.alerts == null ? 0 : this.alerts.length;
/* 159 */     if (end > start) {
/* 160 */       securityPanel.getChildren().add(blockPanel("warning16.image", this.alerts, start, end));
/*     */     }
/*     */ 
/* 163 */     start = (showall) || (this.alerts != null) ? 0 : 1;
/* 164 */     end = this.securityInfoCount;
/* 165 */     if (end > start) {
/* 166 */       securityPanel.getChildren().add(blockPanel("info16.image", this.infos, start, end));
/*     */     }
/* 168 */     return securityPanel;
/*     */   }
/*     */ 
/*     */   private Pane getLinkPanel()
/*     */   {
/* 176 */     HBox linkPanel = new HBox();
/*     */ 
/* 178 */     this.details = new Hyperlink(ResourceManager.getMessage("security.more.info.details"));
/* 179 */     this.details.setMnemonicParsing(true);
/*     */ 
/* 182 */     this.details.setOnAction(new EventHandler() {
/*     */       public void handle(ActionEvent e) {
/* 184 */         MoreInfoDialog.this.showCertDetails();
/*     */       }
/*     */     });
/* 187 */     linkPanel.getChildren().add(this.details);
/*     */ 
/* 189 */     return linkPanel;
/*     */   }
/*     */ 
/*     */   private Pane getIntegrationPanel()
/*     */   {
/* 198 */     int start = this.securityInfoCount;
/* 199 */     int end = this.infos == null ? 0 : this.infos.length;
/* 200 */     return blockPanel("info16.image", this.infos, start, end);
/*     */   }
/*     */ 
/*     */   private Pane getBtnPanel()
/*     */   {
/* 210 */     HBox btnPanel = new HBox();
/* 211 */     btnPanel.setId("more-info-dialog-button-panel");
/*     */ 
/* 214 */     Button dismissBtn = new Button(ResourceManager.getMessage("common.close_btn"));
/* 215 */     dismissBtn.setOnAction(new EventHandler() {
/*     */       public void handle(ActionEvent e) {
/* 217 */         MoreInfoDialog.this.dismissAction();
/*     */       }
/*     */     });
/* 221 */     dismissBtn.setDefaultButton(true);
/* 222 */     btnPanel.getChildren().add(dismissBtn);
/* 223 */     return btnPanel;
/*     */   }
/*     */ 
/*     */   private Pane blockPanel(String iconLblStr, String[] strs, int start, int end)
/*     */   {
/* 232 */     VBox panel = new VBox(5.0D);
/* 233 */     if (strs != null) {
/* 234 */       for (int i = start; i < end; i++) {
/* 235 */         HBox p = new HBox(12.0D);
/* 236 */         p.setAlignment(Pos.TOP_LEFT);
/*     */ 
/* 238 */         ImageView iconLbl = ResourceManager.getIcon(iconLblStr);
/* 239 */         UITextArea text = new UITextArea(326.0D);
/* 240 */         text.setWrapText(true);
/* 241 */         text.setId("more-info-text-block");
/* 242 */         text.setText(strs[i]);
/*     */ 
/* 244 */         p.getChildren().add(iconLbl);
/* 245 */         p.getChildren().add(text);
/*     */ 
/* 247 */         panel.getChildren().add(p);
/* 248 */         if (i < end - 1) {
/* 249 */           panel.getChildren().add(new Separator());
/*     */         }
/*     */       }
/*     */     }
/* 253 */     return panel;
/*     */   }
/*     */ 
/*     */   private void showCertDetails()
/*     */   {
/* 262 */     CertificateDialog.showCertificates(this, this.certs, this.start, this.end);
/*     */   }
/*     */ 
/*     */   private void dismissAction()
/*     */   {
/* 269 */     hide();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.MoreInfoDialog
 * JD-Core Version:    0.6.2
 */