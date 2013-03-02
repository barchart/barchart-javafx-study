/*    */ package com.sun.javafx.tk.desktop;
/*    */ 
/*    */ import com.sun.javafx.PlatformUtil;
/*    */ import com.sun.javafx.perf.PerformanceTracker;
/*    */ import com.sun.javafx.tk.Toolkit;
/*    */ import com.sun.scenario.animation.AbstractMasterTimer;
/*    */ import javafx.scene.input.KeyCode;
/*    */ 
/*    */ public abstract class DesktopToolkit extends Toolkit
/*    */ {
/*    */   protected static boolean hasPlugin;
/* 48 */   private final PerformanceTracker perfTracker = new PerformanceTrackerImpl();
/*    */ 
/*    */   public static void setPluginMode(boolean paramBoolean)
/*    */   {
/* 20 */     hasPlugin = paramBoolean;
/*    */   }
/*    */ 
/*    */   public abstract AppletWindow createAppletWindow(long paramLong, String paramString);
/*    */ 
/*    */   public AppletWindow createAppletWindow(long paramLong)
/*    */   {
/* 35 */     return createAppletWindow(paramLong, null);
/*    */   }
/*    */ 
/*    */   public abstract void closeAppletWindow();
/*    */ 
/*    */   public static DesktopToolkit getDesktopToolkit()
/*    */   {
/* 45 */     return (DesktopToolkit)Toolkit.getToolkit();
/*    */   }
/*    */ 
/*    */   public abstract boolean isAppletDragSupported();
/*    */ 
/*    */   public AbstractMasterTimer getMasterTimer()
/*    */   {
/* 53 */     return MasterTimer.getInstance();
/*    */   }
/*    */ 
/*    */   public PerformanceTracker getPerformanceTracker() {
/* 57 */     return this.perfTracker;
/*    */   }
/*    */ 
/*    */   public PerformanceTracker createPerformanceTracker() {
/* 61 */     return new PerformanceTrackerImpl();
/*    */   }
/*    */ 
/*    */   public KeyCode getPlatformShortcutKey() {
/* 65 */     return PlatformUtil.isMac() ? KeyCode.META : KeyCode.CONTROL;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.desktop.DesktopToolkit
 * JD-Core Version:    0.6.2
 */