/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.net.URL;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ 
/*     */ public class CssError
/*     */ {
/*     */   private static Reference<Scene> SCENE_REF;
/*     */   private final Reference<Scene> sceneRef;
/*     */   protected final String message;
/*     */ 
/*     */   public static void setCurrentScene(Scene paramScene)
/*     */   {
/*  53 */     if (StyleManager.getInstance().getErrors() == null) return;
/*     */ 
/*  55 */     if (paramScene != null)
/*     */     {
/*  57 */       Object localObject = SCENE_REF != null ? (Scene)SCENE_REF.get() : null;
/*  58 */       if (localObject != paramScene)
/*  59 */         SCENE_REF = new WeakReference(paramScene);
/*     */     }
/*     */     else {
/*  62 */       SCENE_REF = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final String getMessage()
/*     */   {
/*  68 */     return this.message;
/*     */   }
/*     */ 
/*     */   public CssError(String paramString) {
/*  72 */     this.message = paramString;
/*     */ 
/*  74 */     this.sceneRef = SCENE_REF;
/*     */   }
/*     */ 
/*     */   public Scene getScene()
/*     */   {
/*  81 */     return this.sceneRef != null ? (Scene)this.sceneRef.get() : null;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  92 */     return "CSS Error: " + this.message;
/*     */   }
/*     */ 
/*     */   public static final class PropertySetError extends CssError
/*     */   {
/*     */     private final StyleableProperty styleableProperty;
/*     */     private final Node node;
/*     */ 
/*     */     public PropertySetError(StyleableProperty paramStyleableProperty, Node paramNode, String paramString)
/*     */     {
/* 172 */       super();
/* 173 */       this.styleableProperty = paramStyleableProperty;
/* 174 */       this.node = paramNode;
/*     */     }
/*     */ 
/*     */     public Node getNode() {
/* 178 */       return this.node;
/*     */     }
/*     */ 
/*     */     public StyleableProperty getProperty() {
/* 182 */       return this.styleableProperty;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final class StringParsingError extends CssError
/*     */   {
/*     */     private final String style;
/*     */ 
/*     */     public StringParsingError(String paramString1, String paramString2)
/*     */     {
/* 150 */       super();
/* 151 */       this.style = paramString1;
/*     */     }
/*     */ 
/*     */     public String getStyle() {
/* 155 */       return this.style;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 163 */       return "CSS Error parsing '" + this.style + ": " + this.message;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final class InlineStyleParsingError extends CssError
/*     */   {
/*     */     private final Styleable styleable;
/*     */ 
/*     */     public InlineStyleParsingError(Styleable paramStyleable, String paramString)
/*     */     {
/* 122 */       super();
/* 123 */       this.styleable = paramStyleable;
/*     */     }
/*     */ 
/*     */     public Styleable getStyleable() {
/* 127 */       return this.styleable;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 134 */       String str1 = this.styleable.getStyle();
/* 135 */       String str2 = this.styleable.toString();
/*     */ 
/* 137 */       return "CSS Error parsing in-line style '" + str1 + "' from " + str2 + ": " + this.message;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final class StylesheetParsingError extends CssError
/*     */   {
/*     */     private final URL url;
/*     */ 
/*     */     public StylesheetParsingError(URL paramURL, String paramString)
/*     */     {
/*  99 */       super();
/* 100 */       this.url = paramURL;
/*     */     }
/*     */ 
/*     */     public URL getURL() {
/* 104 */       return this.url;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 111 */       String str = this.url != null ? this.url.toExternalForm() : "?";
/*     */ 
/* 113 */       return "CSS Error parsing " + str + ": " + this.message;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.CssError
 * JD-Core Version:    0.6.2
 */