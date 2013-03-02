/*    */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import javafx.application.Platform;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.event.EventHandler;
/*    */ import javafx.scene.Group;
/*    */ import javafx.scene.Scene;
/*    */ import javafx.scene.control.Button;
/*    */ import javafx.scene.text.Text;
/*    */ import javafx.stage.Stage;
/*    */ 
/*    */ public class FXMessageDialog
/*    */ {
/*    */   Stage stage;
/*    */   Scene scene;
/*    */   Group group;
/*    */   Button button1;
/* 23 */   private final Object responseLock = new Object();
/* 24 */   private int response = -1;
/*    */ 
/*    */   public FXMessageDialog() {
/* 27 */     this.stage = new Stage();
/* 28 */     this.group = new Group();
/* 29 */     this.group.getChildren().add(new Text("MessageDialog"));
/* 30 */     this.group.getChildren().add(this.button1 = new Button("Button"));
/* 31 */     this.button1.setOnAction(new DialogEventHandler(1));
/* 32 */     this.scene = new Scene(this.group);
/* 33 */     Platform.runLater(new Runnable() {
/*    */       public void run() {
/* 35 */         FXMessageDialog.this.stage.show();
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public int getResponse()
/*    */   {
/* 60 */     synchronized (this.responseLock) {
/* 61 */       if (this.response == -1) {
/*    */         try {
/* 63 */           this.responseLock.wait();
/*    */         } catch (InterruptedException ie) {
/* 65 */           System.out.println("interupted " + ie);
/*    */         }
/*    */       }
/* 68 */       return this.response;
/*    */     }
/*    */   }
/*    */ 
/*    */   class DialogEventHandler
/*    */     implements EventHandler<ActionEvent>
/*    */   {
/*    */     int id;
/*    */ 
/*    */     DialogEventHandler(int id)
/*    */     {
/* 43 */       this.id = id;
/*    */     }
/*    */ 
/*    */     public void handle(ActionEvent t) {
/* 47 */       synchronized (FXMessageDialog.this.responseLock) {
/* 48 */         FXMessageDialog.this.response = this.id;
/* 49 */         FXMessageDialog.this.responseLock.notifyAll();
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.FXMessageDialog
 * JD-Core Version:    0.6.2
 */