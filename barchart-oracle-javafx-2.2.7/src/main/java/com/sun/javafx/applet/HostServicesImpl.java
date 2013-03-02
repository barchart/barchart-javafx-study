/*    */ package com.sun.javafx.applet;
/*    */ 
/*    */ import com.sun.applet2.Applet2Context;
/*    */ import com.sun.applet2.Applet2Host;
/*    */ import com.sun.javafx.application.HostServicesDelegate;
/*    */ import java.net.URI;
/*    */ import java.net.URL;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedActionException;
/*    */ import java.security.PrivilegedExceptionAction;
/*    */ import javafx.application.Application;
/*    */ import netscape.javascript.JSObject;
/*    */ import sun.plugin2.applet2.Plugin2Host;
/*    */ 
/*    */ public class HostServicesImpl extends HostServicesDelegate
/*    */ {
/* 20 */   private static HostServicesDelegate instance = null;
/*    */ 
/* 22 */   private Applet2Context a2c = null;
/*    */ 
/*    */   private HostServicesImpl(Applet2Context a2c) {
/* 25 */     this.a2c = a2c;
/*    */   }
/*    */ 
/*    */   public static void init(Applet2Context a2c)
/*    */   {
/* 30 */     instance = new HostServicesImpl(a2c);
/*    */   }
/*    */ 
/*    */   public static HostServicesDelegate getInstance(Application app)
/*    */   {
/* 35 */     return instance;
/*    */   }
/*    */ 
/*    */   public String getCodeBase()
/*    */   {
/* 40 */     return this.a2c.getCodeBase().toExternalForm();
/*    */   }
/*    */ 
/*    */   public String getDocumentBase()
/*    */   {
/* 45 */     return this.a2c.getHost().getDocumentBase().toExternalForm();
/*    */   }
/*    */ 
/*    */   private URL toURL(String uriString) {
/*    */     try {
/* 50 */       return new URI(uriString).toURL();
/*    */     } catch (RuntimeException ex) {
/* 52 */       throw ex;
/*    */     } catch (Exception ex) {
/* 54 */       throw new IllegalArgumentException(ex);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void showDocument(String uri)
/*    */   {
/* 60 */     this.a2c.getHost().showDocument(toURL(uri), "_blank");
/*    */   }
/*    */ 
/*    */   public JSObject getWebContext()
/*    */   {
/*    */     try
/*    */     {
/* 84 */       return (JSObject)AccessController.doPrivileged(new WCGetter());
/*    */     } catch (PrivilegedActionException ex) {
/* 86 */       ex.printStackTrace();
/* 87 */     }return null;
/*    */   }
/*    */ 
/*    */   class WCGetter
/*    */     implements PrivilegedExceptionAction<JSObject>
/*    */   {
/*    */     WCGetter()
/*    */     {
/*    */     }
/*    */ 
/*    */     public JSObject run()
/*    */     {
/* 70 */       Applet2Host host = HostServicesImpl.this.a2c.getHost();
/* 71 */       if ((host instanceof Plugin2Host))
/*    */         try {
/* 73 */           return ((Plugin2Host)host).getJSObject();
/*    */         }
/*    */         catch (Exception e)
/*    */         {
/*    */         }
/* 78 */       return null;
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.applet.HostServicesImpl
 * JD-Core Version:    0.6.2
 */