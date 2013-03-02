/*     */ package javafx.fxml;
/*     */ 
/*     */ import com.sun.javafx.fxml.builder.JavaFXFontBuilder;
/*     */ import com.sun.javafx.fxml.builder.JavaFXImageBuilder;
/*     */ import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
/*     */ import com.sun.javafx.fxml.builder.URLBuilder;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.image.Image;
/*     */ import javafx.scene.text.Font;
/*     */ import javafx.scene.web.WebView;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.BuilderFactory;
/*     */ import sun.reflect.misc.ConstructorUtil;
/*     */ 
/*     */ public final class JavaFXBuilderFactory
/*     */   implements BuilderFactory
/*     */ {
/*  42 */   private final JavaFXBuilder NO_BUILDER = new JavaFXBuilder();
/*     */ 
/*  44 */   private final Map<Class<?>, JavaFXBuilder> builders = new HashMap();
/*     */   private final ClassLoader classLoader;
/*     */   private final boolean alwaysUseBuilders;
/*     */ 
/*     */   public JavaFXBuilderFactory()
/*     */   {
/*  53 */     this(FXMLLoader.getDefaultClassLoader(), false);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public JavaFXBuilderFactory(boolean paramBoolean)
/*     */   {
/*  64 */     this(FXMLLoader.getDefaultClassLoader(), paramBoolean);
/*     */   }
/*     */ 
/*     */   public JavaFXBuilderFactory(ClassLoader paramClassLoader)
/*     */   {
/*  73 */     this(paramClassLoader, false);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public JavaFXBuilderFactory(ClassLoader paramClassLoader, boolean paramBoolean)
/*     */   {
/*  84 */     if (paramClassLoader == null) {
/*  85 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*  88 */     this.classLoader = paramClassLoader;
/*  89 */     this.alwaysUseBuilders = paramBoolean;
/*     */   }
/*     */ 
/*     */   public Builder<?> getBuilder(Class<?> paramClass)
/*     */   {
/*     */     Object localObject;
/*  97 */     if (paramClass == Scene.class) {
/*  98 */       localObject = new JavaFXSceneBuilder();
/*  99 */     } else if (paramClass == Font.class) {
/* 100 */       localObject = new JavaFXFontBuilder();
/* 101 */     } else if (paramClass == Image.class) {
/* 102 */       localObject = new JavaFXImageBuilder();
/* 103 */     } else if (paramClass == URL.class) {
/* 104 */       localObject = new URLBuilder(this.classLoader);
/*     */     } else {
/* 106 */       Builder localBuilder = null;
/* 107 */       JavaFXBuilder localJavaFXBuilder = (JavaFXBuilder)this.builders.get(paramClass);
/*     */ 
/* 109 */       if (localJavaFXBuilder != this.NO_BUILDER) {
/* 110 */         if (localJavaFXBuilder == null)
/*     */         {
/*     */           int i;
/*     */           try
/*     */           {
/* 121 */             ConstructorUtil.getConstructor(paramClass, new Class[0]);
/*     */ 
/* 126 */             if (this.alwaysUseBuilders) throw new Exception();
/*     */ 
/* 128 */             i = 1;
/*     */           } catch (Exception localException) {
/* 130 */             i = 0;
/*     */           }
/*     */ 
/* 135 */           if ((i == 0) || (paramClass == WebView.class)) {
/*     */             try {
/* 137 */               localJavaFXBuilder = createTypeBuilder(paramClass);
/*     */             }
/*     */             catch (ClassNotFoundException localClassNotFoundException)
/*     */             {
/*     */             }
/*     */           }
/*     */ 
/* 144 */           this.builders.put(paramClass, localJavaFXBuilder == null ? this.NO_BUILDER : localJavaFXBuilder);
/*     */         }
/* 146 */         if (localJavaFXBuilder != null) {
/* 147 */           localBuilder = localJavaFXBuilder.createBuilder();
/*     */         }
/*     */       }
/*     */ 
/* 151 */       localObject = localBuilder;
/*     */     }
/*     */ 
/* 154 */     return localObject;
/*     */   }
/*     */ 
/*     */   JavaFXBuilder createTypeBuilder(Class<?> paramClass) throws ClassNotFoundException {
/* 158 */     JavaFXBuilder localJavaFXBuilder = null;
/* 159 */     Class localClass = this.classLoader.loadClass(paramClass.getName() + "Builder");
/*     */     try {
/* 161 */       localJavaFXBuilder = new JavaFXBuilder(localClass);
/*     */     }
/*     */     catch (Exception localException) {
/* 164 */       Logger.getLogger(JavaFXBuilderFactory.class.getName()).log(Level.WARNING, "Failed to instantiate JavaFXBuilder for " + localClass, localException);
/*     */     }
/*     */ 
/* 167 */     if (!this.alwaysUseBuilders) {
/* 168 */       Logger.getLogger(JavaFXBuilderFactory.class.getName()).log(Level.FINER, "class {0} requires a builder.", paramClass);
/*     */     }
/*     */ 
/* 171 */     return localJavaFXBuilder;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.fxml.JavaFXBuilderFactory
 * JD-Core Version:    0.6.2
 */