/*     */ package com.sun.deploy.uitoolkit.impl.fx;
/*     */ 
/*     */ import com.sun.javafx.applet.HostServicesImpl;
/*     */ import com.sun.javafx.application.HostServicesDelegate;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import javafx.application.Application;
/*     */ import netscape.javascript.JSObject;
/*     */ 
/*     */ public class HostServicesFactory
/*     */ {
/*     */   public static HostServicesDelegate getInstance(Application app)
/*     */   {
/*  25 */     HostServicesDelegate instance = HostServicesImpl.getInstance(app);
/*     */ 
/*  27 */     if (instance == null)
/*     */     {
/*  29 */       instance = StandaloneHostService.getInstance(app);
/*     */     }
/*     */ 
/*  32 */     return instance;
/*     */   }
/*     */ 
/*     */   private HostServicesFactory() {
/*  36 */     throw new InternalError();
/*     */   }
/*     */ 
/*     */   private static class StandaloneHostService extends HostServicesDelegate
/*     */   {
/*  42 */     private static HostServicesDelegate instance = null;
/*     */ 
/*  44 */     private Class appClass = null;
/*     */ 
/* 116 */     static final String[] browsers = { "google-chrome", "firefox", "opera", "konqueror", "mozilla" };
/*     */ 
/*     */     public static HostServicesDelegate getInstance(Application app)
/*     */     {
/*  47 */       synchronized (StandaloneHostService.class) {
/*  48 */         if (instance == null) {
/*  49 */           instance = new StandaloneHostService(app);
/*     */         }
/*  51 */         return instance;
/*     */       }
/*     */     }
/*     */ 
/*     */     private StandaloneHostService(Application app) {
/*  56 */       this.appClass = app.getClass();
/*     */     }
/*     */ 
/*     */     public String getCodeBase()
/*     */     {
/*  65 */       String theClassFile = this.appClass.getName();
/*  66 */       int idx = theClassFile.lastIndexOf(".");
/*  67 */       if (idx >= 0)
/*     */       {
/*  71 */         theClassFile = theClassFile.substring(idx + 1);
/*     */       }
/*  73 */       theClassFile = theClassFile + ".class";
/*     */ 
/*  75 */       String classUrlString = this.appClass.getResource(theClassFile).toString();
/*  76 */       if ((!classUrlString.startsWith("jar:file:")) || (classUrlString.indexOf("!") == -1))
/*     */       {
/*  78 */         return "";
/*     */       }
/*     */ 
/*  81 */       String urlString = classUrlString.substring(4, classUrlString.lastIndexOf("!"));
/*     */ 
/*  83 */       File jarFile = null;
/*     */       try {
/*  85 */         jarFile = new File(new URI(urlString).getPath());
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*  89 */       if (jarFile != null) {
/*  90 */         String codebase = jarFile.getParent();
/*  91 */         if (codebase != null) {
/*  92 */           return toURIString(codebase);
/*     */         }
/*     */       }
/*     */ 
/*  96 */       return "";
/*     */     }
/*     */ 
/*     */     private String toURIString(String filePath) {
/*     */       try {
/* 101 */         return new File(filePath).toURI().toString();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 105 */         e.printStackTrace();
/*     */       }
/* 107 */       return "";
/*     */     }
/*     */ 
/*     */     public String getDocumentBase()
/*     */     {
/* 113 */       return toURIString(System.getProperty("user.dir"));
/*     */     }
/*     */ 
/*     */     public void showDocument(String uri)
/*     */     {
/* 123 */       String osName = System.getProperty("os.name");
/*     */       try {
/* 125 */         if (osName.startsWith("Mac OS")) {
/* 126 */           Class.forName("com.apple.eio.FileManager").getDeclaredMethod("openURL", new Class[] { String.class }).invoke(null, new Object[] { uri });
/*     */         }
/* 129 */         else if (osName.startsWith("Windows")) {
/* 130 */           Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + uri);
/*     */         }
/*     */         else {
/* 133 */           String browser = null;
/* 134 */           for (String b : browsers) {
/* 135 */             if (browser == null) if (Runtime.getRuntime().exec(new String[] { "which", b }).getInputStream().read() != -1)
/*     */               {
/*     */                 String[] tmp159_156 = new String[2];
/*     */                 String tmp163_161 = b; browser = tmp163_161; tmp159_156[0] = tmp163_161;
/*     */                 String[] tmp166_159 = tmp159_156; tmp166_159[1] = uri; Runtime.getRuntime().exec(tmp166_159);
/*     */               }
/*     */           }
/* 140 */           if (browser == null) {
/* 141 */             throw new Exception("No web browser found");
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 147 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/*     */     public JSObject getWebContext() {
/* 152 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory
 * JD-Core Version:    0.6.2
 */