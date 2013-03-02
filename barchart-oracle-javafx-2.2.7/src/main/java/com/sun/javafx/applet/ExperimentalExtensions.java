/*    */ package com.sun.javafx.applet;
/*    */ 
/*    */ import com.sun.applet2.Applet2Context;
/*    */ import com.sun.applet2.Applet2Host;
/*    */ import netscape.javascript.JSObject;
/*    */ import sun.plugin2.applet2.Plugin2Host;
/*    */ 
/*    */ public class ExperimentalExtensions
/*    */ {
/*    */   Applet2Context a2c;
/* 15 */   private static ExperimentalExtensions instance = null;
/*    */ 
/*    */   public static synchronized ExperimentalExtensions get() {
/* 18 */     return instance;
/*    */   }
/*    */ 
/*    */   public static synchronized void init(Applet2Context a2c)
/*    */   {
/* 24 */     instance = new ExperimentalExtensions(a2c);
/*    */   }
/*    */ 
/*    */   private ExperimentalExtensions(Applet2Context a2c)
/*    */   {
/* 30 */     this.a2c = a2c;
/*    */   }
/*    */ 
/*    */   public JSObject getOneWayJSObject()
/*    */   {
/* 36 */     Applet2Host host = this.a2c.getHost();
/* 37 */     if ((host instanceof Plugin2Host))
/*    */       try {
/* 39 */         return ((Plugin2Host)host).getOneWayJSObject();
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/*    */       }
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */   public Splash getSplash() {
/* 48 */     return new Splash(this.a2c);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.applet.ExperimentalExtensions
 * JD-Core Version:    0.6.2
 */