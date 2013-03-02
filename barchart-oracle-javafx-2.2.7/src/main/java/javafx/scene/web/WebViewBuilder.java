/*     */ package javafx.scene.web;
/*     */ 
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.ParentBuilder;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public final class WebViewBuilder extends ParentBuilder<WebViewBuilder>
/*     */   implements Builder<WebView>
/*     */ {
/*     */   private double fontScale;
/*     */   private boolean fontScaleSet;
/*     */   private double maxHeight;
/*     */   private boolean maxHeightSet;
/*     */   private double maxWidth;
/*     */   private boolean maxWidthSet;
/*     */   private double minHeight;
/*     */   private boolean minHeightSet;
/*     */   private double minWidth;
/*     */   private boolean minWidthSet;
/*     */   private double prefHeight;
/*     */   private boolean prefHeightSet;
/*     */   private double prefWidth;
/*     */   private boolean prefWidthSet;
/*     */   private WebEngineBuilder engineBuilder;
/*     */ 
/*     */   public static WebViewBuilder create()
/*     */   {
/*  24 */     return new WebViewBuilder();
/*     */   }
/*     */ 
/*     */   public WebView build()
/*     */   {
/*  32 */     WebView localWebView = new WebView();
/*  33 */     applyTo(localWebView);
/*  34 */     return localWebView;
/*     */   }
/*     */ 
/*     */   public void applyTo(WebView paramWebView)
/*     */   {
/*  43 */     super.applyTo(paramWebView);
/*  44 */     if (this.fontScaleSet) {
/*  45 */       paramWebView.setFontScale(this.fontScale);
/*     */     }
/*  47 */     if (this.maxHeightSet) {
/*  48 */       paramWebView.setMaxHeight(this.maxHeight);
/*     */     }
/*  50 */     if (this.maxWidthSet) {
/*  51 */       paramWebView.setMaxWidth(this.maxWidth);
/*     */     }
/*  53 */     if (this.minHeightSet) {
/*  54 */       paramWebView.setMinHeight(this.minHeight);
/*     */     }
/*  56 */     if (this.minWidthSet) {
/*  57 */       paramWebView.setMinWidth(this.minWidth);
/*     */     }
/*  59 */     if (this.prefHeightSet) {
/*  60 */       paramWebView.setPrefHeight(this.prefHeight);
/*     */     }
/*  62 */     if (this.prefWidthSet) {
/*  63 */       paramWebView.setPrefWidth(this.prefWidth);
/*     */     }
/*  65 */     if (this.engineBuilder != null)
/*  66 */       this.engineBuilder.applyTo(paramWebView.getEngine());
/*     */   }
/*     */ 
/*     */   public WebViewBuilder fontScale(double paramDouble)
/*     */   {
/*  78 */     this.fontScale = paramDouble;
/*  79 */     this.fontScaleSet = true;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder maxHeight(double paramDouble)
/*     */   {
/*  94 */     this.maxHeight = paramDouble;
/*  95 */     this.maxHeightSet = true;
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder maxWidth(double paramDouble)
/*     */   {
/* 110 */     this.maxWidth = paramDouble;
/* 111 */     this.maxWidthSet = true;
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder minHeight(double paramDouble)
/*     */   {
/* 126 */     this.minHeight = paramDouble;
/* 127 */     this.minHeightSet = true;
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder minWidth(double paramDouble)
/*     */   {
/* 142 */     this.minWidth = paramDouble;
/* 143 */     this.minWidthSet = true;
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder prefHeight(double paramDouble)
/*     */   {
/* 158 */     this.prefHeight = paramDouble;
/* 159 */     this.prefHeightSet = true;
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder prefWidth(double paramDouble)
/*     */   {
/* 174 */     this.prefWidth = paramDouble;
/* 175 */     this.prefWidthSet = true;
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder confirmHandler(Callback<String, Boolean> paramCallback)
/*     */   {
/* 191 */     engineBuilder().confirmHandler(paramCallback);
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder createPopupHandler(Callback<PopupFeatures, WebEngine> paramCallback)
/*     */   {
/* 204 */     engineBuilder().createPopupHandler(paramCallback);
/* 205 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder onAlert(EventHandler<WebEvent<String>> paramEventHandler)
/*     */   {
/* 217 */     engineBuilder().onAlert(paramEventHandler);
/* 218 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder onResized(EventHandler<WebEvent<Rectangle2D>> paramEventHandler)
/*     */   {
/* 230 */     engineBuilder().onResized(paramEventHandler);
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder onStatusChanged(EventHandler<WebEvent<String>> paramEventHandler)
/*     */   {
/* 243 */     engineBuilder().onStatusChanged(paramEventHandler);
/* 244 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder onVisibilityChanged(EventHandler<WebEvent<Boolean>> paramEventHandler)
/*     */   {
/* 256 */     engineBuilder().onVisibilityChanged(paramEventHandler);
/* 257 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder promptHandler(Callback<PromptData, String> paramCallback)
/*     */   {
/* 269 */     engineBuilder().promptHandler(paramCallback);
/* 270 */     return this;
/*     */   }
/*     */ 
/*     */   public WebViewBuilder location(String paramString)
/*     */   {
/* 282 */     engineBuilder().location(paramString);
/* 283 */     return this;
/*     */   }
/*     */ 
/*     */   private WebEngineBuilder engineBuilder()
/*     */   {
/* 289 */     if (this.engineBuilder == null) {
/* 290 */       this.engineBuilder = WebEngineBuilder.create();
/*     */     }
/* 292 */     return this.engineBuilder;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.web.WebViewBuilder
 * JD-Core Version:    0.6.2
 */