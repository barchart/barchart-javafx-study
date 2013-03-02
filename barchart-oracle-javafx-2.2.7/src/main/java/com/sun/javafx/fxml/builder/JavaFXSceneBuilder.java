/*    */ package com.sun.javafx.fxml.builder;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javafx.beans.DefaultProperty;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.Parent;
/*    */ import javafx.scene.Scene;
/*    */ import javafx.scene.paint.Color;
/*    */ import javafx.scene.paint.Paint;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ @DefaultProperty("root")
/*    */ public class JavaFXSceneBuilder
/*    */   implements Builder<Scene>
/*    */ {
/* 21 */   private Parent root = null;
/* 22 */   private double width = -1.0D;
/* 23 */   private double height = -1.0D;
/* 24 */   private Paint fill = Color.WHITE;
/* 25 */   private ArrayList<String> stylesheets = new ArrayList();
/*    */ 
/*    */   public Parent getRoot() {
/* 28 */     return this.root;
/*    */   }
/*    */ 
/*    */   public void setRoot(Parent paramParent) {
/* 32 */     this.root = paramParent;
/*    */   }
/*    */ 
/*    */   public double getWidth() {
/* 36 */     return this.width;
/*    */   }
/*    */ 
/*    */   public void setWidth(double paramDouble) {
/* 40 */     if (paramDouble < -1.0D) {
/* 41 */       throw new IllegalArgumentException();
/*    */     }
/*    */ 
/* 44 */     this.width = paramDouble;
/*    */   }
/*    */ 
/*    */   public double getHeight() {
/* 48 */     return this.height;
/*    */   }
/*    */ 
/*    */   public void setHeight(double paramDouble) {
/* 52 */     if (paramDouble < -1.0D) {
/* 53 */       throw new IllegalArgumentException();
/*    */     }
/*    */ 
/* 56 */     this.height = paramDouble;
/*    */   }
/*    */ 
/*    */   public Paint getFill() {
/* 60 */     return this.fill;
/*    */   }
/*    */ 
/*    */   public void setFill(Paint paramPaint) {
/* 64 */     if (paramPaint == null) {
/* 65 */       throw new NullPointerException();
/*    */     }
/*    */ 
/* 68 */     this.fill = paramPaint;
/*    */   }
/*    */ 
/*    */   public List<String> getStylesheets() {
/* 72 */     return this.stylesheets;
/*    */   }
/*    */ 
/*    */   public Scene build()
/*    */   {
/* 77 */     Scene localScene = new Scene(this.root, this.width, this.height, this.fill);
/*    */ 
/* 79 */     for (String str : this.stylesheets) {
/* 80 */       localScene.getStylesheets().add(str);
/*    */     }
/*    */ 
/* 83 */     return localScene;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.builder.JavaFXSceneBuilder
 * JD-Core Version:    0.6.2
 */