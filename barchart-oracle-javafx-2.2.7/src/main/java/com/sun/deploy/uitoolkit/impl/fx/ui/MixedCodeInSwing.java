/*     */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*     */ 
/*     */ import com.sun.deploy.ui.AppInfo;
/*     */ import java.awt.Component;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.MouseInfo;
/*     */ import java.awt.Point;
/*     */ import java.awt.PointerInfo;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.SwingUtilities;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ public class MixedCodeInSwing
/*     */ {
/*  41 */   private static boolean haveAppContext = false;
/*     */   private static Class sysUtils;
/*     */   private static Class tClass;
/*     */   private static Constructor cMethod;
/*     */   private static Method setContentMethod;
/*     */   private static Method getDialogMethod;
/*     */   private static Method setVisibleMethod;
/*     */   private static Method getAnswerMethod;
/*     */   private static Method disposeMethod;
/*     */   private static Method createSysThreadMethod;
/*     */ 
/*     */   public static int show(Object owner, AppInfo appInfo, String title, String masthead, String message, String info, String okBtnStr, String cancelBtnStr, boolean useWarning)
/*     */   {
/* 113 */     Helper h = new Helper(null, appInfo, title, masthead, message, info, okBtnStr, cancelBtnStr, useWarning);
/*     */     try
/*     */     {
/* 116 */       Thread t = (Thread)createSysThreadMethod.invoke(null, new Object[] { h });
/* 117 */       t.start();
/* 118 */       t.join();
/*     */     } catch (Exception ex) {
/* 120 */       ex.printStackTrace();
/*     */     }
/* 122 */     return h.getAnswer();
/*     */   }
/*     */ 
/*     */   private static void placeWindow(Window window)
/*     */   {
/* 230 */     Rectangle screenBounds = getMouseScreenBounds();
/* 231 */     Rectangle winBounds = window.getBounds();
/*     */ 
/* 234 */     window.setLocation((screenBounds.width - winBounds.width) / 2, (screenBounds.height - winBounds.height) / 2);
/*     */   }
/*     */ 
/*     */   public static Rectangle getMouseScreenBounds()
/*     */   {
/* 239 */     Point mousePoint = MouseInfo.getPointerInfo().getLocation();
/* 240 */     GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
/*     */ 
/* 242 */     for (int i = 0; i < devices.length; i++) {
/* 243 */       Rectangle bounds = devices[i].getDefaultConfiguration().getBounds();
/*     */ 
/* 246 */       if ((mousePoint.x >= bounds.x) && (mousePoint.y >= bounds.y) && (mousePoint.x <= bounds.x + bounds.width) && (mousePoint.y <= bounds.y + bounds.height))
/*     */       {
/* 250 */         return bounds;
/*     */       }
/*     */     }
/* 253 */     return new Rectangle(new Point(0, 0), Toolkit.getDefaultToolkit().getScreenSize());
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  55 */       tClass = Class.forName("com.sun.deploy.ui.DialogTemplate", true, null);
/*     */ 
/*  58 */       cMethod = tClass.getDeclaredConstructor(new Class[] { AppInfo.class, Component.class, String.class, String.class, Boolean.TYPE });
/*     */ 
/*  65 */       cMethod.setAccessible(true);
/*  66 */       setContentMethod = tClass.getDeclaredMethod("setMixedCodeContent", new Class[] { String.class, Boolean.TYPE, String.class, String.class, String.class, String.class, Boolean.TYPE, Boolean.TYPE });
/*     */ 
/*  78 */       setContentMethod.setAccessible(true);
/*     */ 
/*  80 */       getDialogMethod = tClass.getDeclaredMethod("getDialog", new Class[0]);
/*     */ 
/*  83 */       getDialogMethod.setAccessible(true);
/*     */ 
/*  85 */       setVisibleMethod = tClass.getDeclaredMethod("setVisible", new Class[] { Boolean.TYPE });
/*     */ 
/*  88 */       setVisibleMethod.setAccessible(true);
/*     */ 
/*  90 */       disposeMethod = tClass.getDeclaredMethod("disposeDialog", new Class[0]);
/*     */ 
/*  93 */       disposeMethod.setAccessible(true);
/*     */ 
/*  95 */       getAnswerMethod = tClass.getDeclaredMethod("getUserAnswer", new Class[0]);
/*     */ 
/*  98 */       getAnswerMethod.setAccessible(true);
/*     */ 
/* 100 */       sysUtils = Class.forName("sun.plugin.util.PluginSysUtil", false, null);
/* 101 */       createSysThreadMethod = sysUtils.getMethod("createPluginSysThread", new Class[] { Runnable.class });
/*     */     }
/*     */     catch (Exception e) {
/* 104 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Helper
/*     */     implements Runnable
/*     */   {
/*     */     private AppInfo appInfo;
/*     */     private String title;
/*     */     private String masthead;
/*     */     private String message;
/*     */     private String info;
/*     */     private String okBtnStr;
/*     */     private String cancelBtnStr;
/*     */     private boolean useWarning;
/* 134 */     private int userAnswer = -1;
/*     */ 
/*     */     Helper(Component owner, AppInfo appInfo, String title, String masthead, String message, String info, String okBtnStr, String cancelBtnStr, boolean useWarning)
/*     */     {
/* 141 */       this.appInfo = (appInfo == null ? new AppInfo() : appInfo);
/* 142 */       this.title = title;
/* 143 */       this.masthead = masthead;
/* 144 */       this.message = message;
/* 145 */       this.info = info;
/* 146 */       this.okBtnStr = okBtnStr;
/* 147 */       this.cancelBtnStr = cancelBtnStr;
/* 148 */       this.useWarning = useWarning;
/*     */     }
/*     */ 
/*     */     public void run() {
/* 152 */       synchronized (MixedCodeInSwing.class) {
/* 153 */         if (!MixedCodeInSwing.haveAppContext) {
/* 154 */           AppContext awtAC = SunToolkit.createNewAppContext();
/*     */ 
/* 156 */           MixedCodeInSwing.access$002(true);
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 161 */         SwingUtilities.invokeAndWait(new Runnable() {
/*     */           public void run() {
/*     */             try {
/* 164 */               Object template = MixedCodeInSwing.cMethod.newInstance(new Object[] { MixedCodeInSwing.Helper.this.appInfo, null, MixedCodeInSwing.Helper.this.title, MixedCodeInSwing.Helper.this.masthead, Boolean.valueOf(false) });
/*     */ 
/* 167 */               MixedCodeInSwing.setContentMethod.invoke(template, new Object[] { null, Boolean.valueOf(false), MixedCodeInSwing.Helper.this.message, MixedCodeInSwing.Helper.this.info, MixedCodeInSwing.Helper.this.okBtnStr, MixedCodeInSwing.Helper.this.cancelBtnStr, Boolean.valueOf(true), Boolean.valueOf(MixedCodeInSwing.Helper.this.useWarning) });
/*     */ 
/* 204 */               MixedCodeInSwing.setVisibleMethod.invoke(template, new Object[] { Boolean.valueOf(true) });
/*     */ 
/* 206 */               MixedCodeInSwing.Helper.this.userAnswer = ((Integer)MixedCodeInSwing.getAnswerMethod.invoke(template, new Object[0])).intValue();
/*     */ 
/* 208 */               MixedCodeInSwing.disposeMethod.invoke(template, new Object[0]);
/*     */             } catch (Throwable e) {
/* 210 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */       catch (InterruptedException ex) {
/* 216 */         ex.printStackTrace();
/*     */       } catch (InvocationTargetException ex) {
/* 218 */         ex.printStackTrace();
/*     */       } catch (Throwable t) {
/* 220 */         t.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/*     */     int getAnswer() {
/* 225 */       return this.userAnswer;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.MixedCodeInSwing
 * JD-Core Version:    0.6.2
 */