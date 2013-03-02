/*     */ package com.sun.webpane.sg;
/*     */ 
/*     */ import com.sun.webpane.platform.UIClient;
/*     */ import com.sun.webpane.platform.WebPage;
/*     */ import com.sun.webpane.platform.graphics.WCImage;
/*     */ import com.sun.webpane.platform.graphics.WCRectangle;
/*     */ import java.io.File;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.input.ClipboardContent;
/*     */ import javafx.scene.input.DataFormat;
/*     */ import javafx.scene.input.Dragboard;
/*     */ import javafx.scene.input.TransferMode;
/*     */ import javafx.scene.web.PopupFeatures;
/*     */ import javafx.scene.web.PromptData;
/*     */ import javafx.scene.web.WebEngine;
/*     */ import javafx.scene.web.WebEvent;
/*     */ import javafx.scene.web.WebView;
/*     */ import javafx.stage.FileChooser;
/*     */ import javafx.stage.Window;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class UIClientImpl
/*     */   implements UIClient
/*     */ {
/*     */   private Accessor accessor;
/*     */   private FileChooser chooser;
/*     */   private ClipboardContent content;
/*     */ 
/*     */   public UIClientImpl(Accessor paramAccessor)
/*     */   {
/*  36 */     this.accessor = paramAccessor;
/*     */   }
/*     */ 
/*     */   private WebEngine getWebEngine() {
/*  40 */     return this.accessor.getEngine();
/*     */   }
/*     */ 
/*     */   public WebPage createPage(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*     */   {
/*  45 */     WebEngine localWebEngine1 = getWebEngine();
/*  46 */     if ((localWebEngine1 != null) && (localWebEngine1.getCreatePopupHandler() != null)) {
/*  47 */       WebEngine localWebEngine2 = (WebEngine)localWebEngine1.getCreatePopupHandler().call(new PopupFeatures(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4));
/*     */ 
/*  49 */       return Accessor.getPageFor(localWebEngine2);
/*     */     }
/*  51 */     return null;
/*     */   }
/*     */ 
/*     */   public void closePage() {
/*  55 */     WebEngine localWebEngine = getWebEngine();
/*  56 */     if ((localWebEngine != null) && (localWebEngine.getOnVisibilityChanged() != null))
/*  57 */       localWebEngine.getOnVisibilityChanged().handle(new WebEvent(localWebEngine, WebEvent.VISIBILITY_CHANGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   public void showView()
/*     */   {
/*  63 */     WebEngine localWebEngine = getWebEngine();
/*  64 */     if ((localWebEngine != null) && (localWebEngine.getOnVisibilityChanged() != null))
/*  65 */       localWebEngine.getOnVisibilityChanged().handle(new WebEvent(localWebEngine, WebEvent.VISIBILITY_CHANGED, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   public WCRectangle getViewBounds()
/*     */   {
/*  71 */     WebView localWebView = this.accessor.getView();
/*  72 */     Window localWindow = null;
/*  73 */     if ((localWebView != null) && (localWebView.getScene() != null) && ((localWindow = localWebView.getScene().getWindow()) != null))
/*     */     {
/*  77 */       return new WCRectangle((float)localWindow.getX(), (float)localWindow.getY(), (float)localWindow.getWidth(), (float)localWindow.getHeight());
/*     */     }
/*     */ 
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   public void setViewBounds(WCRectangle paramWCRectangle) {
/*  85 */     WebEngine localWebEngine = getWebEngine();
/*  86 */     if ((localWebEngine != null) && (localWebEngine.getOnResized() != null)) {
/*  87 */       WebEvent localWebEvent = new WebEvent(localWebEngine, WebEvent.RESIZED, new Rectangle2D(paramWCRectangle.getX(), paramWCRectangle.getY(), paramWCRectangle.getWidth(), paramWCRectangle.getHeight()));
/*     */ 
/*  89 */       localWebEngine.getOnResized().handle(localWebEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setStatusbarText(String paramString) {
/*  94 */     WebEngine localWebEngine = getWebEngine();
/*  95 */     if ((localWebEngine != null) && (localWebEngine.getOnStatusChanged() != null))
/*  96 */       localWebEngine.getOnStatusChanged().handle(new WebEvent(localWebEngine, WebEvent.STATUS_CHANGED, paramString));
/*     */   }
/*     */ 
/*     */   public void alert(String paramString)
/*     */   {
/* 102 */     WebEngine localWebEngine = getWebEngine();
/* 103 */     if ((localWebEngine != null) && (localWebEngine.getOnAlert() != null))
/* 104 */       localWebEngine.getOnAlert().handle(new WebEvent(localWebEngine, WebEvent.ALERT, paramString));
/*     */   }
/*     */ 
/*     */   public boolean confirm(String paramString)
/*     */   {
/* 109 */     WebEngine localWebEngine = getWebEngine();
/* 110 */     return (localWebEngine != null) && (localWebEngine.getConfirmHandler() != null) ? ((Boolean)localWebEngine.getConfirmHandler().call(paramString)).booleanValue() : false;
/*     */   }
/*     */ 
/*     */   public String prompt(String paramString1, String paramString2)
/*     */   {
/* 116 */     WebEngine localWebEngine = getWebEngine();
/* 117 */     return (localWebEngine != null) && (localWebEngine.getPromptHandler() != null) ? (String)localWebEngine.getPromptHandler().call(new PromptData(paramString1, paramString2)) : "";
/*     */   }
/*     */ 
/*     */   public String chooseFile(String paramString)
/*     */   {
/* 124 */     Window localWindow = null;
/* 125 */     WebView localWebView = this.accessor.getView();
/* 126 */     if ((localWebView != null) && (localWebView.getScene() != null)) {
/* 127 */       localWindow = localWebView.getScene().getWindow();
/*     */     }
/*     */ 
/* 130 */     if (this.chooser == null) {
/* 131 */       this.chooser = new FileChooser();
/*     */     }
/*     */ 
/* 135 */     if (paramString != null) {
/* 136 */       localFile = new File(paramString);
/* 137 */       while ((localFile != null) && (!localFile.isDirectory())) {
/* 138 */         localFile = localFile.getParentFile();
/*     */       }
/* 140 */       this.chooser.setInitialDirectory(localFile);
/*     */     }
/* 142 */     File localFile = this.chooser.showOpenDialog(localWindow);
/* 143 */     return localFile != null ? localFile.getAbsolutePath() : null;
/*     */   }
/*     */ 
/*     */   public void print()
/*     */   {
/*     */   }
/*     */ 
/*     */   private static DataFormat getDataFormat(String paramString)
/*     */   {
/* 153 */     synchronized (DataFormat.class) {
/* 154 */       DataFormat localDataFormat = DataFormat.lookupMimeType(paramString);
/* 155 */       if (localDataFormat == null) {
/* 156 */         localDataFormat = new DataFormat(new String[] { paramString });
/*     */       }
/* 158 */       return localDataFormat;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startDrag(WCImage paramWCImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String[] paramArrayOfString, Object[] paramArrayOfObject)
/*     */   {
/* 167 */     this.content = new ClipboardContent();
/* 168 */     for (int i = 0; i < paramArrayOfString.length; i++)
/* 169 */       this.content.put(getDataFormat(paramArrayOfString[i]), paramArrayOfObject[i]);
/*     */   }
/*     */ 
/*     */   public void confirmStartDrag()
/*     */   {
/* 174 */     WebView localWebView = this.accessor.getView();
/* 175 */     if ((localWebView != null) && (this.content != null))
/*     */     {
/* 177 */       Dragboard localDragboard = localWebView.startDragAndDrop(TransferMode.ANY);
/* 178 */       localDragboard.setContent(this.content);
/*     */     }
/* 180 */     this.content = null;
/*     */   }
/*     */ 
/*     */   public boolean isDragConfirmed() {
/* 184 */     return (this.accessor.getView() != null) && (this.content != null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.UIClientImpl
 * JD-Core Version:    0.6.2
 */