/*     */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*     */ 
/*     */ import com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager;
/*     */ import java.awt.Desktop;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.text.MessageFormat;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.Hyperlink;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.image.ImageView;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.layout.VBox;
/*     */ 
/*     */ public class FXAboutDialog
/*     */ {
/*     */   static void showAboutJavaDialog()
/*     */   {
/*  30 */     FXDialog dialog = new FXDialog(ResourceManager.getMessage("dialogfactory.aboutDialogTitle"));
/*  31 */     dialog.setResizable(false);
/*     */ 
/*  33 */     VBox outerVBox = new VBox();
/*     */ 
/*  35 */     VBox topPanel = new VBox();
/*  36 */     topPanel.setId("about-dialog-top-panel");
/*  37 */     outerVBox.getChildren().add(topPanel);
/*     */ 
/*  40 */     String javaVersion = System.getProperty("java.version");
/*     */ 
/*  42 */     int firstDot = javaVersion.indexOf(".");
/*  43 */     String marketingVersion = javaVersion.substring(firstDot + 1, javaVersion.indexOf(".", firstDot + 1));
/*  44 */     ImageView headerImageView = ResourceManager.getIcon("about.java" + ("6".equals(marketingVersion) ? "6" : "") + ".image");
/*  45 */     topPanel.getChildren().add(headerImageView);
/*     */ 
/*  48 */     VBox centerPanel = new VBox();
/*  49 */     centerPanel.setId("about-dialog-center-panel");
/*  50 */     topPanel.getChildren().add(centerPanel);
/*     */ 
/*  52 */     StringBuilder textAreaText = new StringBuilder();
/*     */ 
/*  56 */     textAreaText.append(getVersionStr());
/*  57 */     textAreaText.append("\n");
/*  58 */     textAreaText.append(ResourceManager.getMessage("about.copyright"));
/*  59 */     textAreaText.append("\n \n");
/*  60 */     textAreaText.append(ResourceManager.getMessage("about.prompt.info"));
/*     */ 
/*  74 */     Label textArea = new Label();
/*  75 */     textArea.setWrapText(true);
/*  76 */     textArea.setText(textAreaText.toString());
/*  77 */     textArea.setPrefWidth(headerImageView.prefWidth(-1.0D) - 16.0D);
/*  78 */     textArea.setMinWidth((-1.0D / 0.0D));
/*  79 */     centerPanel.getChildren().add(textArea);
/*     */ 
/*  82 */     String linkURL = ResourceManager.getMessage("about.home.link");
/*  83 */     Hyperlink link = new Hyperlink(linkURL);
/*  84 */     link.setOnAction(new EventHandler() {
/*  85 */       public void handle(Event event) { FXAboutDialog.browserToUrl(this.val$linkURL); }
/*     */ 
/*     */     });
/*  88 */     centerPanel.getChildren().add(link);
/*     */ 
/*  91 */     ImageView logoImageView = ResourceManager.getIcon("sun.logo.image");
/*     */ 
/*  93 */     centerPanel.getChildren().add(logoImageView);
/*     */ 
/*  96 */     StackPane buttonBar = new StackPane();
/*  97 */     buttonBar.getStyleClass().add("button-bar");
/*  98 */     buttonBar.setId("about-dialog-button-bar");
/*  99 */     outerVBox.getChildren().add(buttonBar);
/*     */ 
/* 102 */     Button closeButton = new Button(ResourceManager.getMessage("about.option.close"));
/* 103 */     closeButton.setDefaultButton(true);
/* 104 */     closeButton.setOnAction(new EventHandler() {
/* 105 */       public void handle(Event event) { this.val$dialog.close(); }
/*     */ 
/*     */     });
/* 107 */     closeButton.setAlignment(Pos.TOP_LEFT);
/*     */ 
/* 109 */     buttonBar.getChildren().add(closeButton);
/*     */ 
/* 111 */     dialog.setContentPane(outerVBox);
/*     */ 
/* 114 */     dialog.show();
/* 115 */     closeButton.requestFocus();
/*     */   }
/*     */ 
/*     */   private static void browserToUrl(String url)
/*     */   {
/*     */     try {
/* 121 */       Desktop.getDesktop().browse(new URI(url));
/*     */     } catch (URISyntaxException e) {
/* 123 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 125 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getVersionStr() {
/* 130 */     String javaVersion = System.getProperty("java.version");
/*     */ 
/* 132 */     int firstDot = javaVersion.indexOf(".");
/* 133 */     String marketingVersion = javaVersion.substring(firstDot + 1, javaVersion.indexOf(".", firstDot + 1));
/* 134 */     int pos = javaVersion.lastIndexOf("_");
/* 135 */     String updateVersion = null;
/* 136 */     if (pos != -1)
/*     */     {
/* 138 */       int dashIdx = javaVersion.indexOf("-");
/* 139 */       if (dashIdx != -1) {
/* 140 */         updateVersion = javaVersion.substring(pos + 1, dashIdx);
/*     */       }
/*     */       else {
/* 143 */         updateVersion = javaVersion.substring(pos + 1, javaVersion.length());
/*     */       }
/*     */ 
/* 146 */       if (updateVersion.startsWith("0"))
/*     */       {
/* 148 */         updateVersion = updateVersion.substring(1);
/*     */       }
/*     */     }
/* 151 */     String version = null;
/* 152 */     if (updateVersion != null) {
/* 153 */       version = MessageFormat.format(ResourceManager.getMessage("about.java.version.update"), new Object[] { marketingVersion, updateVersion });
/*     */     }
/*     */     else {
/* 156 */       version = MessageFormat.format(ResourceManager.getMessage("about.java.version"), new Object[] { marketingVersion });
/*     */     }
/*     */ 
/* 160 */     String buildInfo = MessageFormat.format(ResourceManager.getMessage("about.java.build"), new Object[] { System.getProperty("java.runtime.version") });
/*     */ 
/* 163 */     return version + " " + buildInfo;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.FXAboutDialog
 * JD-Core Version:    0.6.2
 */