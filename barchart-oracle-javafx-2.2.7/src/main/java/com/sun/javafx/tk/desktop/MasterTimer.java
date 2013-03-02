/*     */ package com.sun.javafx.tk.desktop;
/*     */ 
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.scenario.DelayedRunnable;
/*     */ import com.sun.scenario.Settings;
/*     */ import com.sun.scenario.animation.AbstractMasterTimer;
/*     */ import com.sun.scenario.animation.AnimationPulse;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class MasterTimer extends AbstractMasterTimer
/*     */ {
/*  55 */   private static PropertyChangeListener pcl = new PropertyChangeListener() {
/*     */     public void propertyChange(PropertyChangeEvent paramAnonymousPropertyChangeEvent) {
/*  57 */       if (paramAnonymousPropertyChangeEvent.getPropertyName().equals("com.sun.scenario.animation.nogaps"))
/*  58 */         MasterTimer.access$002(Settings.getBoolean("com.sun.scenario.animation.nogaps"));
/*  59 */       else if (paramAnonymousPropertyChangeEvent.getPropertyName().equals("javafx.animation.fullspeed"))
/*  60 */         MasterTimer.access$102(Settings.getBoolean("javafx.animation.fullspeed"));
/*  61 */       else if (paramAnonymousPropertyChangeEvent.getPropertyName().equals("com.sun.scenario.animation.adaptivepulse"))
/*  62 */         MasterTimer.access$202(Settings.getBoolean("com.sun.scenario.animation.adaptivepulse"));
/*  63 */       else if (paramAnonymousPropertyChangeEvent.getPropertyName().equals("com.sun.scenario.animation.AnimationMBean.enabled"))
/*  64 */         AnimationPulse.getDefaultBean().setEnabled(Settings.getBoolean("com.sun.scenario.animation.AnimationMBean.enabled"));
/*     */     }
/*  55 */   };
/*     */ 
/*  71 */   private static ReflectAppContext reflectInfo = null;
/*     */ 
/*  99 */   private static final Object MASTER_TIMER_KEY = new StringBuilder("MasterTimerKey");
/*     */ 
/*     */   public static synchronized MasterTimer getInstance()
/*     */   {
/* 103 */     Map localMap = Toolkit.getToolkit().getContextMap();
/* 104 */     MasterTimer localMasterTimer = (MasterTimer)localMap.get(MASTER_TIMER_KEY);
/* 105 */     if (localMasterTimer == null) {
/* 106 */       localMasterTimer = new MasterTimer();
/* 107 */       localMap.put(MASTER_TIMER_KEY, localMasterTimer);
/* 108 */       if (Settings.getBoolean("com.sun.scenario.animation.AnimationMBean.enabled", false))
/*     */       {
/* 110 */         AnimationPulse.getDefaultBean().setEnabled(true);
/*     */       }
/*     */     }
/* 113 */     return localMasterTimer;
/*     */   }
/*     */ 
/*     */   protected boolean shouldUseNanoTime() {
/* 117 */     boolean bool = false;
/*     */     try {
/* 119 */       System.nanoTime();
/* 120 */       bool = true;
/*     */     } catch (NoSuchMethodError localNoSuchMethodError) {
/*     */     }
/* 123 */     return bool;
/*     */   }
/*     */ 
/*     */   protected int getPulseDuration(int paramInt)
/*     */   {
/* 138 */     int i = paramInt / 60;
/*     */     int j;
/* 140 */     if (Settings.get("javafx.animation.framerate") != null) {
/* 141 */       j = Settings.getInt("javafx.animation.framerate", 60);
/* 142 */       if (j > 0)
/* 143 */         i = paramInt / j;
/*     */     }
/* 145 */     else if (Settings.get("javafx.animation.pulse") != null) {
/* 146 */       j = Settings.getInt("javafx.animation.pulse", 60);
/* 147 */       if (j > 0) {
/* 148 */         i = paramInt / j;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 153 */       j = Toolkit.getToolkit().getRefreshRate();
/* 154 */       if (j > 0) {
/* 155 */         i = paramInt / j;
/*     */       }
/*     */     }
/*     */ 
/* 159 */     return i;
/*     */   }
/*     */ 
/*     */   protected void postUpdateAnimationRunnable(DelayedRunnable paramDelayedRunnable) {
/* 163 */     Toolkit.getToolkit().setAnimationRunnable(paramDelayedRunnable);
/*     */   }
/*     */ 
/*     */   static void timePulse(long paramLong)
/*     */   {
/* 173 */     getInstance().timePulseImpl(paramLong);
/*     */   }
/*     */ 
/*     */   protected void recordStart(long paramLong)
/*     */   {
/* 178 */     AnimationPulse.getDefaultBean().recordStart(paramLong);
/*     */   }
/*     */ 
/*     */   protected void recordEnd()
/*     */   {
/* 183 */     AnimationPulse.getDefaultBean().recordEnd();
/*     */   }
/*     */ 
/*     */   protected void recordAnimationEnd()
/*     */   {
/* 188 */     AnimationPulse.getDefaultBean().recordAnimationEnd();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  89 */     Settings.addPropertyChangeListener("com.sun.scenario.animation.nogaps", pcl);
/*  90 */     Settings.addPropertyChangeListener("com.sun.scenario.animation.adaptivepulse", pcl);
/*  91 */     Settings.addPropertyChangeListener("javafx.animation.fullspeed", pcl);
/*  92 */     Settings.addPropertyChangeListener("com.sun.scenario.animation.AnimationMBean.enabled", pcl);
/*     */   }
/*     */ 
/*     */   private static class ReflectAppContext
/*     */     implements PrivilegedAction
/*     */   {
/*  73 */     Class clazz = null;
/*  74 */     Method getAppCtx = null;
/*  75 */     Method isDisposed = null;
/*     */ 
/*     */     public Object run() {
/*     */       try {
/*  79 */         this.clazz = Class.forName("sun.awt.AppContext", true, null);
/*  80 */         this.getAppCtx = this.clazz.getDeclaredMethod("getAppContext", (Class[])null);
/*  81 */         this.isDisposed = this.clazz.getDeclaredMethod("isDisposed", (Class[])null); } catch (ClassNotFoundException localClassNotFoundException) {
/*     */       } catch (NoSuchMethodException localNoSuchMethodException) {
/*     */       }
/*  84 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.desktop.MasterTimer
 * JD-Core Version:    0.6.2
 */