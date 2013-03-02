/*     */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*     */ 
/*     */ import com.sun.deploy.ui.AppInfo;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.security.cert.Certificate;
/*     */ import javafx.application.Platform;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.layout.HBox;
/*     */ import javafx.scene.layout.VBox;
/*     */ import javafx.scene.text.Text;
/*     */ import javafx.stage.Stage;
/*     */ 
/*     */ class FXSecurityDialog
/*     */ {
/*     */   private Stage stage;
/*     */   private Scene scene;
/*     */   private Button okButton;
/*     */   private Button cancelButton;
/*  31 */   private final Object responseLock = new Object();
/*  32 */   private int response = -1;
/*     */ 
/*     */   FXSecurityDialog(AppInfo ainfo, final String title, final String topText, String publisher, URL appFrom, boolean showAlways, boolean checkAlways, final String okBtnStr, final String cancelBtnStr, String[] securityAlerts, String[] securityInfo, boolean showMoreInfo, Certificate[] certs, int start, int end, boolean majorWarning)
/*     */   {
/*  50 */     Platform.runLater(new Runnable()
/*     */     {
/*     */       public void run() {
/*  53 */         FXSecurityDialog.this.stage = new Stage();
/*  54 */         FXSecurityDialog.this.stage.setTitle(title);
/*     */ 
/*  56 */         VBox vbox = new VBox();
/*     */ 
/*  65 */         Text text = new Text(topText);
/*     */ 
/*  67 */         vbox.getChildren().add(text);
/*  68 */         HBox hbox = new HBox();
/*  69 */         FXSecurityDialog.this.okButton = new Button(okBtnStr);
/*  70 */         FXSecurityDialog.this.cancelButton = new Button(cancelBtnStr);
/*     */ 
/*  73 */         hbox.getChildren().add(FXSecurityDialog.this.okButton);
/*     */ 
/*  75 */         hbox.getChildren().add(FXSecurityDialog.this.cancelButton);
/*  76 */         vbox.getChildren().add(hbox);
/*  77 */         FXSecurityDialog.DialogEventHandler deh = new FXSecurityDialog.DialogEventHandler(FXSecurityDialog.this, null);
/*  78 */         FXSecurityDialog.this.okButton.setOnAction(deh);
/*  79 */         FXSecurityDialog.this.cancelButton.setOnAction(deh);
/*     */ 
/*  93 */         FXSecurityDialog.this.scene = new Scene(vbox, 640.0D, 480.0D);
/*  94 */         FXSecurityDialog.this.stage.setScene(FXSecurityDialog.this.scene);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   void setVisible(boolean visible) {
/* 100 */     Platform.runLater(new Runnable() {
/*     */       public void run() {
/* 102 */         FXSecurityDialog.this.stage.show();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   int getResponse()
/*     */   {
/* 130 */     synchronized (this.responseLock) {
/* 131 */       if (this.response == -1) {
/*     */         try {
/* 133 */           this.responseLock.wait();
/*     */         } catch (InterruptedException ie) {
/* 135 */           System.out.println("interupted " + ie);
/*     */         }
/*     */       }
/* 138 */       return this.response;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class DialogEventHandler
/*     */     implements EventHandler<ActionEvent>
/*     */   {
/*     */     private DialogEventHandler()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handle(ActionEvent t)
/*     */     {
/* 112 */       synchronized (FXSecurityDialog.this.responseLock) {
/* 113 */         if (t.getSource() == FXSecurityDialog.this.okButton)
/* 114 */           FXSecurityDialog.this.response = 0;
/*     */         else {
/* 116 */           FXSecurityDialog.this.response = 1;
/*     */         }
/* 118 */         FXSecurityDialog.this.responseLock.notifyAll();
/* 119 */         FXSecurityDialog.this.stage.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.FXSecurityDialog
 * JD-Core Version:    0.6.2
 */