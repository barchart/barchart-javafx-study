/*     */ package javafx.scene.web;
/*     */ 
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public final class WebEngineBuilder
/*     */   implements Builder<WebEngine>
/*     */ {
/*     */   private Callback<String, Boolean> confirmHandler;
/*     */   private boolean confirmHandlerSet;
/*     */   private Callback<PopupFeatures, WebEngine> createPopupHandler;
/*     */   private boolean createPopupHandlerSet;
/*     */   private EventHandler<WebEvent<String>> onAlert;
/*     */   private boolean onAlertSet;
/*     */   private EventHandler<WebEvent<Rectangle2D>> onResized;
/*     */   private boolean onResizedSet;
/*     */   private EventHandler<WebEvent<String>> onStatusChanged;
/*     */   private boolean onStatusChangedSet;
/*     */   private EventHandler<WebEvent<Boolean>> onVisibilityChanged;
/*     */   private boolean onVisibilityChangedSet;
/*     */   private Callback<PromptData, String> promptHandler;
/*     */   private boolean promptHandlerSet;
/*     */   private String location;
/*     */   private boolean locationSet;
/*     */ 
/*     */   public static WebEngineBuilder create()
/*     */   {
/*  22 */     return new WebEngineBuilder();
/*     */   }
/*     */ 
/*     */   public WebEngine build()
/*     */   {
/*  30 */     WebEngine localWebEngine = new WebEngine();
/*  31 */     applyTo(localWebEngine);
/*  32 */     return localWebEngine;
/*     */   }
/*     */ 
/*     */   public void applyTo(WebEngine paramWebEngine)
/*     */   {
/*  41 */     if (this.confirmHandlerSet) {
/*  42 */       paramWebEngine.setConfirmHandler(this.confirmHandler);
/*     */     }
/*  44 */     if (this.createPopupHandlerSet) {
/*  45 */       paramWebEngine.setCreatePopupHandler(this.createPopupHandler);
/*     */     }
/*  47 */     if (this.onAlertSet) {
/*  48 */       paramWebEngine.setOnAlert(this.onAlert);
/*     */     }
/*  50 */     if (this.onResizedSet) {
/*  51 */       paramWebEngine.setOnResized(this.onResized);
/*     */     }
/*  53 */     if (this.onStatusChangedSet) {
/*  54 */       paramWebEngine.setOnStatusChanged(this.onStatusChanged);
/*     */     }
/*  56 */     if (this.onVisibilityChangedSet) {
/*  57 */       paramWebEngine.setOnVisibilityChanged(this.onVisibilityChanged);
/*     */     }
/*  59 */     if (this.promptHandlerSet) {
/*  60 */       paramWebEngine.setPromptHandler(this.promptHandler);
/*     */     }
/*  62 */     if (this.locationSet)
/*  63 */       paramWebEngine.load(this.location);
/*     */   }
/*     */ 
/*     */   public WebEngineBuilder confirmHandler(Callback<String, Boolean> paramCallback)
/*     */   {
/*  75 */     this.confirmHandler = paramCallback;
/*  76 */     this.confirmHandlerSet = true;
/*  77 */     return this;
/*     */   }
/*     */ 
/*     */   public WebEngineBuilder createPopupHandler(Callback<PopupFeatures, WebEngine> paramCallback)
/*     */   {
/*  91 */     this.createPopupHandler = paramCallback;
/*  92 */     this.createPopupHandlerSet = true;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public WebEngineBuilder onAlert(EventHandler<WebEvent<String>> paramEventHandler)
/*     */   {
/* 107 */     this.onAlert = paramEventHandler;
/* 108 */     this.onAlertSet = true;
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   public WebEngineBuilder onResized(EventHandler<WebEvent<Rectangle2D>> paramEventHandler)
/*     */   {
/* 123 */     this.onResized = paramEventHandler;
/* 124 */     this.onResizedSet = true;
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   public WebEngineBuilder onStatusChanged(EventHandler<WebEvent<String>> paramEventHandler)
/*     */   {
/* 139 */     this.onStatusChanged = paramEventHandler;
/* 140 */     this.onStatusChangedSet = true;
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public WebEngineBuilder onVisibilityChanged(EventHandler<WebEvent<Boolean>> paramEventHandler)
/*     */   {
/* 155 */     this.onVisibilityChanged = paramEventHandler;
/* 156 */     this.onVisibilityChangedSet = true;
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */   public WebEngineBuilder promptHandler(Callback<PromptData, String> paramCallback)
/*     */   {
/* 171 */     this.promptHandler = paramCallback;
/* 172 */     this.promptHandlerSet = true;
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */   public WebEngineBuilder location(String paramString)
/*     */   {
/* 187 */     this.location = paramString;
/* 188 */     this.locationSet = true;
/* 189 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.web.WebEngineBuilder
 * JD-Core Version:    0.6.2
 */