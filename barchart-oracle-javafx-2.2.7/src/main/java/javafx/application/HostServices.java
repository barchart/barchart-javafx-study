/*     */ package javafx.application;
/*     */ 
/*     */ import com.sun.javafx.application.HostServicesDelegate;
/*     */ import java.net.URI;
/*     */ import netscape.javascript.JSObject;
/*     */ 
/*     */ public final class HostServices
/*     */ {
/*     */   private final HostServicesDelegate delegate;
/*     */ 
/*     */   HostServices(Application paramApplication)
/*     */   {
/*  51 */     this.delegate = HostServicesDelegate.getInstance(paramApplication);
/*     */   }
/*     */ 
/*     */   public final String getCodeBase()
/*     */   {
/*  66 */     return this.delegate.getCodeBase();
/*     */   }
/*     */ 
/*     */   public final String getDocumentBase()
/*     */   {
/*  82 */     return this.delegate.getDocumentBase();
/*     */   }
/*     */ 
/*     */   public final String resolveURI(String paramString1, String paramString2)
/*     */   {
/* 112 */     URI localURI = URI.create(paramString1).resolve(paramString2);
/* 113 */     return localURI.toString();
/*     */   }
/*     */ 
/*     */   public final void showDocument(String paramString)
/*     */   {
/* 126 */     this.delegate.showDocument(paramString);
/*     */   }
/*     */ 
/*     */   public final JSObject getWebContext()
/*     */   {
/* 154 */     return this.delegate.getWebContext();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.application.HostServices
 * JD-Core Version:    0.6.2
 */