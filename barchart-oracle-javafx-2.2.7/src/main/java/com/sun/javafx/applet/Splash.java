/*    */ package com.sun.javafx.applet;
/*    */ 
/*    */ import com.sun.applet2.Applet2Context;
/*    */ import com.sun.deploy.trace.Trace;
/*    */ import com.sun.deploy.trace.TraceLevel;
/*    */ import java.util.Map;
/*    */ import netscape.javascript.JSObject;
/*    */ 
/*    */ public class Splash
/*    */ {
/* 16 */   Applet2Context a2c = null;
/*    */ 
/*    */   public Splash(Applet2Context a2c) {
/* 19 */     this.a2c = a2c;
/*    */   }
/*    */ 
/*    */   public void hide()
/*    */   {
/* 26 */     Runnable r = new Runnable() {
/*    */       public void run() {
/*    */         try {
/* 29 */           JSObject o = ExperimentalExtensions.get().getOneWayJSObject();
/* 30 */           if (o == null) {
/* 31 */             Trace.println("Can not hide splash as Javascript handle is not avaialble", TraceLevel.UI);
/*    */ 
/* 34 */             return;
/*    */           }
/* 36 */           Map params = null;
/* 37 */           if (Splash.this.a2c != null) {
/* 38 */             params = Splash.this.a2c.getParameters();
/*    */           }
/* 40 */           if (params != null)
/*    */           {
/* 42 */             if (!"false".equals(params.get("javafx_splash"))) {
/* 43 */               String aid = (String)params.get("javafx_applet_id");
/* 44 */               if (aid != null)
/* 45 */                 o.eval("dtjava.hideSplash('" + aid + "');");
/*    */               else {
/* 47 */                 Trace.println("Missing applet id parameter!");
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */         catch (Exception e)
/*    */         {
/* 54 */           Trace.ignored(e);
/*    */         }
/*    */       }
/*    */     };
/* 59 */     Thread t = new Thread(r);
/* 60 */     t.setDaemon(true);
/* 61 */     t.start();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.applet.Splash
 * JD-Core Version:    0.6.2
 */