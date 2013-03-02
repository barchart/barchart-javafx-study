/*     */ package javafx.application;
/*     */ 
/*     */ import com.sun.javafx.application.LauncherImpl;
/*     */ import com.sun.javafx.application.ParametersImpl;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javafx.stage.Stage;
/*     */ 
/*     */ public abstract class Application
/*     */ {
/*     */   private HostServices hostServices;
/*     */ 
/*     */   public Application()
/*     */   {
/* 273 */     this.hostServices = null;
/*     */   }
/*     */ 
/*     */   public static void launch(Class<? extends Application> paramClass, String[] paramArrayOfString)
/*     */   {
/* 148 */     LauncherImpl.launchApplication(paramClass, paramArrayOfString);
/*     */   }
/*     */ 
/*     */   public static void launch(String[] paramArrayOfString)
/*     */   {
/* 182 */     StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
/*     */ 
/* 184 */     int i = 0;
/* 185 */     Object localObject1 = null;
/* 186 */     for (Object localObject4 : arrayOfStackTraceElement)
/*     */     {
/* 188 */       String str1 = localObject4.getClassName();
/* 189 */       String str2 = localObject4.getMethodName();
/* 190 */       if (i != 0) {
/* 191 */         localObject1 = str1;
/* 192 */         break;
/* 193 */       }if ((Application.class.getName().equals(str1)) && ("launch".equals(str2)))
/*     */       {
/* 196 */         i = 1;
/*     */       }
/*     */     }
/*     */ 
/* 200 */     if (localObject1 == null) {
/* 201 */       throw new RuntimeException("Error: unable to determine Application class");
/*     */     }
/*     */     try
/*     */     {
/* 205 */       ??? = Class.forName(localObject1, true, Thread.currentThread().getContextClassLoader());
/*     */ 
/* 207 */       if (Application.class.isAssignableFrom((Class)???)) {
/* 208 */         Object localObject3 = ???;
/* 209 */         LauncherImpl.launchApplication(localObject3, paramArrayOfString);
/*     */       } else {
/* 211 */         throw new RuntimeException("Error: " + ??? + " is not a subclass of javafx.application.Application");
/*     */       }
/*     */     }
/*     */     catch (RuntimeException localRuntimeException) {
/* 215 */       throw localRuntimeException;
/*     */     } catch (Exception localException) {
/* 217 */       throw new RuntimeException(localException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void init()
/*     */     throws Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public abstract void start(Stage paramStage)
/*     */     throws Exception;
/*     */ 
/*     */   public void stop()
/*     */     throws Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public final HostServices getHostServices()
/*     */   {
/* 283 */     synchronized (this) {
/* 284 */       if (this.hostServices == null) {
/* 285 */         this.hostServices = new HostServices(this);
/*     */       }
/* 287 */       return this.hostServices;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final Parameters getParameters()
/*     */   {
/* 306 */     return ParametersImpl.getParameters(this);
/*     */   }
/*     */ 
/*     */   public final void notifyPreloader(Preloader.PreloaderNotification paramPreloaderNotification)
/*     */   {
/* 320 */     LauncherImpl.notifyPreloader(this, paramPreloaderNotification);
/*     */   }
/*     */ 
/*     */   public static abstract class Parameters
/*     */   {
/*     */     public abstract List<String> getRaw();
/*     */ 
/*     */     public abstract List<String> getUnnamed();
/*     */ 
/*     */     public abstract Map<String, String> getNamed();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.application.Application
 * JD-Core Version:    0.6.2
 */