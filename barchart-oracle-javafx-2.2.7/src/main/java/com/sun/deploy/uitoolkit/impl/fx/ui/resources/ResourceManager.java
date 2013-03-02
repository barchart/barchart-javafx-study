/*     */ package com.sun.deploy.uitoolkit.impl.fx.ui.resources;
/*     */ 
/*     */ import com.sun.deploy.trace.Trace;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javafx.scene.image.Image;
/*     */ import javafx.scene.image.ImageView;
/*     */ 
/*     */ public class ResourceManager
/*     */ {
/*     */   private static ResourceBundle rbFX;
/*     */   private static ResourceBundle rbJRE;
/*     */   private static final String UNDERSCORE = "_";
/*     */   private static final String ESC_UNDERSCORE = "__";
/*     */   private static final String AMPERSAND = "&";
/*     */   private static final String ESC_AMPERSAND = "&&";
/*  37 */   private static Pattern p_start = Pattern.compile("^&[^&]");
/*     */ 
/*  40 */   private static Pattern p_other = Pattern.compile("[^&]&[^&]");
/*     */ 
/*  42 */   private static Pattern p_underscore = Pattern.compile("_");
/*  43 */   private static Pattern p_escampersand = Pattern.compile("&&");
/*     */ 
/*     */   static void reset()
/*     */   {
/*  52 */     rbFX = ResourceBundle.getBundle("com.sun.deploy.uitoolkit.impl.fx.ui.resources.Deployment");
/*     */     try
/*     */     {
/*  56 */       rbJRE = ResourceBundle.getBundle("com.sun.deploy.resources.Deployment");
/*     */     } catch (MissingResourceException ex) {
/*  58 */       Trace.ignoredException(ex);
/*  59 */       rbJRE = rbFX;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getMessage(String key)
/*     */   {
/*  67 */     return convertMnemonics(getString(key));
/*     */   }
/*     */ 
/*     */   private static String escapeUnderscore(String s) {
/*  71 */     return p_underscore.matcher(s).replaceAll("__");
/*     */   }
/*     */ 
/*     */   private static String unescapeAmpersand(String s) {
/*  75 */     return p_escampersand.matcher(s).replaceAll("&");
/*     */   }
/*     */ 
/*     */   private static String convertMnemonics(String s)
/*     */   {
/*     */     String tmp;
/*     */     String tmp;
/* 112 */     if (p_start.matcher(s).find())
/*     */     {
/* 115 */       tmp = "_" + s.substring(1);
/*     */     }
/*     */     else
/*     */     {
/*     */       Matcher m;
/*     */       String tmp;
/* 116 */       if ((m = p_other.matcher(s)).find())
/*     */       {
/* 120 */         tmp = escapeUnderscore(s.substring(0, m.start() + 1)) + "_" + s.substring(m.end() - 1);
/*     */       }
/*     */       else
/*     */       {
/* 125 */         tmp = escapeUnderscore(s);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 130 */     return unescapeAmpersand(tmp);
/*     */   }
/*     */ 
/*     */   public static String getFormattedMessage(String key, Object[] args)
/*     */   {
/*     */     try
/*     */     {
/* 139 */       return new MessageFormat(getMessage(key)).format(args);
/*     */     } catch (MissingResourceException ex) {
/* 141 */       Trace.ignoredException(ex);
/* 142 */     }return key;
/*     */   }
/*     */ 
/*     */   public static String getString(String key)
/*     */   {
/*     */     try
/*     */     {
/* 151 */       return rbFX.containsKey(key) ? rbFX.getString(key) : rbJRE.getString(key);
/*     */     } catch (MissingResourceException ex) {
/*     */     }
/* 154 */     return key;
/*     */   }
/*     */ 
/*     */   public static String getString(String key, Object[] args)
/*     */   {
/* 162 */     return MessageFormat.format(getString(key), args);
/*     */   }
/*     */ 
/*     */   public static ImageView getIcon(String key)
/*     */   {
/*     */     try
/*     */     {
/* 172 */       return (ImageView)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public ImageView run() {
/* 175 */           return ResourceManager.getIcon_(this.val$key);
/*     */         } } );
/*     */     }
/*     */     catch (Exception ex) {
/* 179 */       Trace.ignoredException(ex);
/* 180 */     }return null;
/*     */   }
/*     */ 
/*     */   public static ImageView getIcon_(String key)
/*     */   {
/* 185 */     String resourceName = getString(key);
/* 186 */     URL url = rbFX.getClass().getResource(resourceName);
/* 187 */     String className = rbFX.getClass().getName();
/* 188 */     if ((url == null) || (key.equals("about.java.image"))) {
/* 189 */       url = rbJRE.getClass().getResource(resourceName);
/* 190 */       className = rbJRE.getClass().getName();
/*     */     }
/* 192 */     return getIcon(url);
/*     */   }
/*     */ 
/*     */   public static ImageView getIcon(URL url) {
/* 196 */     Image image = new Image(url.toString());
/* 197 */     return new ImageView(image);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  46 */     reset();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager
 * JD-Core Version:    0.6.2
 */