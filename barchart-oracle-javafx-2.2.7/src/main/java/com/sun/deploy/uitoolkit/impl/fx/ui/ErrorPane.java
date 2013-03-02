/*    */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*    */ 
/*    */ import com.sun.applet2.Applet2Context;
/*    */ import com.sun.applet2.Applet2Host;
/*    */ import com.sun.deploy.trace.Trace;
/*    */ import com.sun.deploy.ui.AppInfo;
/*    */ import com.sun.deploy.uitoolkit.ToolkitStore;
/*    */ import com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager;
/*    */ import com.sun.deploy.uitoolkit.ui.UIFactory;
/*    */ import java.lang.reflect.Method;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.event.EventHandler;
/*    */ import javafx.geometry.Insets;
/*    */ import javafx.scene.control.Label;
/*    */ import javafx.scene.image.ImageView;
/*    */ import javafx.scene.input.MouseEvent;
/*    */ import javafx.scene.layout.Pane;
/*    */ 
/*    */ public class ErrorPane extends Pane
/*    */ {
/*    */   Applet2Context a2c;
/*    */   Label label;
/*    */ 
/*    */   public ErrorPane(Applet2Context a2c, Throwable reason, final boolean offerReload)
/*    */   {
/* 24 */     this.a2c = a2c;
/*    */ 
/* 26 */     setStyle("-fx-background-color: white; -fx-padding: 4; -fx-border-color: lightgray;");
/*    */ 
/* 28 */     ImageView graphic = ResourceManager.getIcon("error.pane.icon");
/* 29 */     this.label = new Label(ResourceManager.getMessage("error.pane.message"), graphic);
/* 30 */     setOnMouseClicked(new EventHandler() {
/*    */       public void handle(MouseEvent t) {
/* 32 */         int selection = -1;
/*    */ 
/* 34 */         if (offerReload)
/*    */         {
/* 40 */           ToolkitStore.getUI(); selection = ToolkitStore.getUI().showMessageDialog(null, new AppInfo(), 0, null, ResourceManager.getMessage("applet.error.generic.masthead"), ResourceManager.getMessage("applet.error.generic.body"), null, "applet.error.details.btn", "applet.error.ignore.btn", "applet.error.reload.btn");
/*    */         }
/*    */         else
/*    */         {
/* 53 */           ToolkitStore.getUI(); selection = ToolkitStore.getUI().showMessageDialog(null, new AppInfo(), 0, null, ResourceManager.getMessage("applet.error.generic.masthead"), ResourceManager.getMessage("applet.error.generic.body"), null, "applet.error.details.btn", "applet.error.ignore.btn", null);
/*    */         }
/*    */ 
/* 65 */         ToolkitStore.getUI(); if (selection == 0)
/*    */         {
/*    */           try {
/* 68 */             Class cls = Class.forName("sun.plugin.JavaRunTime");
/* 69 */             Method m = cls.getDeclaredMethod("showJavaConsole", new Class[] { Boolean.TYPE });
/*    */ 
/* 71 */             m.invoke(null, new Object[] { Boolean.valueOf(true) });
/*    */           } catch (Exception e) {
/* 73 */             Trace.ignoredException(e);
/*    */           }
/*    */         } else { ToolkitStore.getUI(); if (selection != 1)
/*    */           {
/* 77 */             ToolkitStore.getUI(); if (selection == 3)
/*    */             {
/* 82 */               ErrorPane.this.reloadApplet();
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     });
/* 87 */     getChildren().add(this.label);
/*    */   }
/*    */ 
/*    */   public void layoutChildren() {
/* 91 */     super.layoutChildren();
/*    */ 
/* 93 */     Insets insets = getInsets();
/* 94 */     this.label.relocate(insets.getLeft(), insets.getTop());
/*    */   }
/*    */ 
/*    */   private void reloadApplet() {
/* 98 */     if ((this.a2c != null) && (this.a2c.getHost() != null))
/*    */     {
/* 100 */       this.a2c.getHost().reloadAppletPage();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.ErrorPane
 * JD-Core Version:    0.6.2
 */