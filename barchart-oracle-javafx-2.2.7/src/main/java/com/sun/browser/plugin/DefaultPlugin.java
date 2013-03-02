/*    */ package com.sun.browser.plugin;
/*    */ 
/*    */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*    */ import java.io.IOError;
/*    */ import java.net.URL;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class DefaultPlugin
/*    */   implements Plugin
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger("com.sun.browser.plugin.DefaultPlugin");
/*    */ 
/* 52 */   private int x = 0;
/* 53 */   private int y = 0;
/* 54 */   private int w = 0;
/* 55 */   private int h = 0;
/*    */ 
/*    */   public void init(String paramString)
/*    */   {
/*    */   }
/*    */ 
/*    */   public DefaultPlugin()
/*    */   {
/* 28 */     init("Default Plugin");
/*    */   }
/*    */ 
/*    */   DefaultPlugin(URL paramURL, String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2) {
/* 32 */     init(new StringBuilder().append("Default Plugin for: ").append(null == paramURL ? "(null)" : paramURL.toExternalForm()).toString());
/*    */   }
/*    */ 
/*    */   public void paint(WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 41 */     paramWCGraphicsContext.fillRect(this.x, this.y, this.w, this.h, Integer.valueOf(296419327));
/*    */   }
/*    */ 
/*    */   public void activate(Object paramObject, PluginListener paramPluginListener) {
/*    */   }
/*    */ 
/*    */   public void destroy() {
/*    */   }
/*    */ 
/*    */   public void setVisible(boolean paramBoolean) {
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean paramBoolean) {
/*    */   }
/*    */ 
/*    */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 57 */     this.x = paramInt1;
/* 58 */     this.y = paramInt2;
/* 59 */     this.w = paramInt3;
/* 60 */     this.h = paramInt4;
/*    */   }
/*    */ 
/*    */   public Object invoke(String paramString1, String paramString2, Object[] paramArrayOfObject) throws IOError
/*    */   {
/* 65 */     return null;
/*    */   }
/*    */ 
/*    */   public boolean handleMouseEvent(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, long paramLong)
/*    */   {
/* 82 */     return false;
/*    */   }
/*    */ 
/*    */   public void requestFocus()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setNativeContainerBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.browser.plugin.DefaultPlugin
 * JD-Core Version:    0.6.2
 */