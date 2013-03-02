/*    */ package com.sun.deploy.uitoolkit.impl.fx.ui;
/*    */ 
/*    */ import com.sun.deploy.appcontext.AppContext;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class FXAppContext
/*    */   implements AppContext
/*    */ {
/* 21 */   private HashMap storage = new HashMap();
/*    */ 
/* 29 */   private static FXAppContext theInstance = new FXAppContext();
/*    */ 
/*    */   public static synchronized FXAppContext getInstance() {
/* 32 */     return theInstance;
/*    */   }
/*    */ 
/*    */   public Object get(Object key)
/*    */   {
/* 39 */     return this.storage.get(key);
/*    */   }
/*    */ 
/*    */   public Object put(Object key, Object value)
/*    */   {
/* 48 */     return this.storage.put(key, value);
/*    */   }
/*    */ 
/*    */   public Object remove(Object key)
/*    */   {
/* 58 */     return this.storage.remove(key);
/*    */   }
/*    */ 
/*    */   public void invokeLater(Runnable runnable)
/*    */   {
/* 63 */     runnable.run();
/*    */   }
/*    */ 
/*    */   public void invokeAndWait(Runnable runnable)
/*    */   {
/* 68 */     runnable.run();
/*    */   }
/*    */ 
/*    */   public ThreadGroup getThreadGroup() {
/* 72 */     return Thread.currentThread().getThreadGroup();
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/* 77 */     this.storage.clear();
/*    */   }
/*    */ 
/*    */   public boolean destroy(long timeToWait)
/*    */   {
/* 82 */     return true;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.ui.FXAppContext
 * JD-Core Version:    0.6.2
 */