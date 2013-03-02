/*     */ package javafx.application;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ public abstract class Preloader extends Application
/*     */ {
/* 122 */   private static final String lineSeparator = str != null ? str : "\n";
/*     */ 
/*     */   public void handleProgressNotification(ProgressNotification paramProgressNotification)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void handleStateChangeNotification(StateChangeNotification paramStateChangeNotification)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void handleApplicationNotification(PreloaderNotification paramPreloaderNotification)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean handleErrorNotification(ErrorNotification paramErrorNotification)
/*     */   {
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 117 */     String str = (String)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public String run() {
/* 119 */         return System.getProperty("line.separator");
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static class StateChangeNotification
/*     */     implements Preloader.PreloaderNotification
/*     */   {
/*     */     private final Type notificationType;
/*     */     private final Application application;
/*     */ 
/*     */     public StateChangeNotification(Type paramType)
/*     */     {
/* 377 */       this.notificationType = paramType;
/* 378 */       this.application = null;
/*     */     }
/*     */ 
/*     */     public StateChangeNotification(Type paramType, Application paramApplication)
/*     */     {
/* 390 */       this.notificationType = paramType;
/* 391 */       this.application = paramApplication;
/*     */     }
/*     */ 
/*     */     public Type getType()
/*     */     {
/* 400 */       return this.notificationType;
/*     */     }
/*     */ 
/*     */     public Application getApplication()
/*     */     {
/* 411 */       return this.application;
/*     */     }
/*     */ 
/*     */     public static enum Type
/*     */     {
/* 355 */       BEFORE_LOAD, 
/*     */ 
/* 360 */       BEFORE_INIT, 
/*     */ 
/* 365 */       BEFORE_START;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class ProgressNotification
/*     */     implements Preloader.PreloaderNotification
/*     */   {
/*     */     private final double progress;
/*     */     private final String details;
/*     */ 
/*     */     public ProgressNotification(double paramDouble)
/*     */     {
/* 298 */       this(paramDouble, "");
/*     */     }
/*     */ 
/*     */     private ProgressNotification(double paramDouble, String paramString)
/*     */     {
/* 314 */       this.progress = paramDouble;
/* 315 */       this.details = paramString;
/*     */     }
/*     */ 
/*     */     public double getProgress()
/*     */     {
/* 325 */       return this.progress;
/*     */     }
/*     */ 
/*     */     private String getDetails()
/*     */     {
/* 334 */       return this.details;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class ErrorNotification
/*     */     implements Preloader.PreloaderNotification
/*     */   {
/*     */     private String location;
/* 214 */     private String details = "";
/*     */     private Throwable cause;
/*     */ 
/*     */     public ErrorNotification(String paramString1, String paramString2, Throwable paramThrowable)
/*     */     {
/* 225 */       if (paramString2 == null) throw new NullPointerException();
/*     */ 
/* 227 */       this.location = paramString1;
/* 228 */       this.details = paramString2;
/* 229 */       this.cause = paramThrowable;
/*     */     }
/*     */ 
/*     */     public String getLocation()
/*     */     {
/* 241 */       return this.location;
/*     */     }
/*     */ 
/*     */     public String getDetails()
/*     */     {
/* 251 */       return this.details;
/*     */     }
/*     */ 
/*     */     public Throwable getCause()
/*     */     {
/* 261 */       return this.cause;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 269 */       StringBuilder localStringBuilder = new StringBuilder("Preloader.ErrorNotification: ");
/* 270 */       localStringBuilder.append(this.details);
/* 271 */       if (this.cause != null) {
/* 272 */         localStringBuilder.append(Preloader.lineSeparator).append("Caused by: ").append(this.cause.toString());
/*     */       }
/* 274 */       if (this.location != null) {
/* 275 */         localStringBuilder.append(Preloader.lineSeparator).append("Location: ").append(this.location);
/*     */       }
/* 277 */       return localStringBuilder.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface PreloaderNotification
/*     */   {
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.application.Preloader
 * JD-Core Version:    0.6.2
 */