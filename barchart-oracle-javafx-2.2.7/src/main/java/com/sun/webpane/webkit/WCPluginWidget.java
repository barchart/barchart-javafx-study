/*     */ package com.sun.webpane.webkit;
/*     */ 
/*     */ import com.sun.browser.plugin.Plugin;
/*     */ import com.sun.browser.plugin.PluginListener;
/*     */ import com.sun.browser.plugin.PluginManager;
/*     */ import com.sun.webpane.platform.WebPage;
/*     */ import com.sun.webpane.platform.WebPageClient;
/*     */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*     */ import com.sun.webpane.platform.graphics.WCRectangle;
/*     */ import com.sun.webpane.webkit.network.URLs;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class WCPluginWidget extends WCWidget
/*     */   implements PluginListener
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(WCPluginWidget.class.getName());
/*     */   private Plugin plugin;
/*  27 */   private long pData = 0L;
/*     */ 
/*     */   private static native void initIDs();
/*     */ 
/*     */   protected WCPluginWidget(WebPage paramWebPage, Plugin paramPlugin, int paramInt1, int paramInt2)
/*     */   {
/*  40 */     super(paramWebPage);
/*  41 */     this.plugin = paramPlugin;
/*  42 */     setBounds(0, 0, paramInt1, paramInt2);
/*     */ 
/*  44 */     WebPageClient localWebPageClient = paramWebPage.getPageClient();
/*  45 */     this.plugin.activate(null == localWebPageClient ? null : localWebPageClient.getContainer(), this);
/*     */   }
/*     */ 
/*     */   protected void requestFocus()
/*     */   {
/*  52 */     this.plugin.requestFocus();
/*     */   }
/*     */ 
/*     */   protected static WCPluginWidget create(WebPage paramWebPage, int paramInt1, int paramInt2, String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2)
/*     */   {
/*  63 */     URL localURL = null;
/*     */     try {
/*  65 */       localURL = URLs.newURL(paramString1);
/*     */     } catch (MalformedURLException localMalformedURLException) {
/*  67 */       log.log(Level.FINE, null, localMalformedURLException);
/*     */     }
/*  69 */     return new WCPluginWidget(paramWebPage, PluginManager.createPlugin(localURL, paramString2, paramArrayOfString1, paramArrayOfString2), paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void fwkSetNativeContainerBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  81 */     this.plugin.setNativeContainerBounds(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  92 */     super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
/*  93 */     this.plugin.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean paramBoolean)
/*     */   {
/*  99 */     this.plugin.setEnabled(paramBoolean);
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean paramBoolean)
/*     */   {
/* 104 */     this.plugin.setVisible(paramBoolean);
/*     */   }
/*     */ 
/*     */   protected void destroy()
/*     */   {
/* 110 */     this.pData = 0L;
/* 111 */     this.plugin.destroy();
/*     */   }
/*     */ 
/*     */   public void paint(WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 121 */     WCRectangle localWCRectangle1 = getBounds();
/* 122 */     WCRectangle localWCRectangle2 = localWCRectangle1.intersection(new WCRectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/* 123 */     if (!localWCRectangle2.isEmpty()) {
/* 124 */       paramWCGraphicsContext.translate(localWCRectangle1.getX(), localWCRectangle1.getY());
/* 125 */       localWCRectangle2.translate(-localWCRectangle1.getX(), -localWCRectangle1.getY());
/* 126 */       paramWCGraphicsContext.setClip(localWCRectangle2.getIntX(), localWCRectangle2.getIntY(), localWCRectangle2.getIntWidth(), localWCRectangle2.getIntHeight());
/* 127 */       this.plugin.paint(paramWCGraphicsContext, localWCRectangle2.getIntX(), localWCRectangle2.getIntY(), localWCRectangle2.getIntWidth(), localWCRectangle2.getIntHeight());
/*     */     }
/*     */   }
/*     */ 
/*     */   private native WCRectangle twkConvertToPage(WCRectangle paramWCRectangle);
/*     */ 
/*     */   private native void twkInvalidateWindowlessPluginRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   public boolean fwkHandleMouseEvent(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, long paramLong)
/*     */   {
/* 161 */     return this.plugin.handleMouseEvent(paramString, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramLong);
/*     */   }
/*     */ 
/*     */   public void fwkRedraw(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
/*     */   {
/* 178 */     twkInvalidateWindowlessPluginRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   private native void twkSetPlugunFocused(boolean paramBoolean);
/*     */ 
/*     */   public String fwkEvent(int paramInt, String paramString1, String paramString2)
/*     */   {
/* 188 */     if ((-1 == paramInt) && (Boolean.parseBoolean(paramString2))) {
/* 189 */       twkSetPlugunFocused(Boolean.valueOf(paramString2).booleanValue());
/*     */     }
/* 191 */     return "";
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  32 */     initIDs();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.WCPluginWidget
 * JD-Core Version:    0.6.2
 */