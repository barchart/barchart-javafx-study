/*    */ package com.sun.glass.ui;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class Applet extends ApplicationBase
/*    */ {
/*    */   private static Applet applet;
/*    */   private long ptr;
/*    */   private View view;
/*    */ 
/*    */   public static void Run(String[] args, String name, Launchable launchable)
/*    */   {
/* 13 */     applet = new Applet();
/* 14 */     applet.platform = Platform.DeterminePlatform();
/* 15 */     applet.args = args;
/* 16 */     applet.name = name;
/* 17 */     applet.launchable = launchable;
/* 18 */     applet.run();
/*    */   }
/*    */ 
/*    */   private void run() {
/* 22 */     this.ptr = Long.parseLong(System.getProperty("sparkle.plugin", "0"));
/* 23 */     System.err.println("Applet.plugin: " + this.ptr);
/* 24 */     if (this.ptr != 0L)
/* 25 */       this.launchable.finishLaunching(this.args);
/*    */     else
/* 27 */       System.err.println("Applet could not start: no valid plugin found.");
/*    */   }
/*    */ 
/*    */   public static Applet GetApplet()
/*    */   {
/* 32 */     return applet;
/*    */   }
/*    */ 
/*    */   public long getNativePlugin() {
/* 36 */     return this.ptr;
/*    */   }
/*    */   private native void _setView(long paramLong1, long paramLong2);
/*    */ 
/*    */   public void setView(View view) {
/* 41 */     _setView(this.ptr, view.getNativeView());
/* 42 */     view.setVisible(true);
/*    */   }
/*    */ 
/*    */   public View getView() {
/* 46 */     return this.view;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Applet
 * JD-Core Version:    0.6.2
 */