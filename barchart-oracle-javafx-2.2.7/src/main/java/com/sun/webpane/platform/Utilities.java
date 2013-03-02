/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ import com.sun.webpane.platform.graphics.WCImage;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.AccessControlContext;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedActionException;
/*    */ import java.security.PrivilegedExceptionAction;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import sun.reflect.misc.MethodUtil;
/*    */ 
/*    */ public abstract class Utilities
/*    */ {
/*    */   private static Utilities instance;
/*    */ 
/*    */   public static synchronized void setUtilities(Utilities paramUtilities)
/*    */   {
/* 22 */     instance = paramUtilities;
/*    */   }
/*    */ 
/*    */   public static synchronized Utilities getUtilities() {
/* 26 */     return instance;
/*    */   }
/*    */ 
/*    */   public abstract WCImage getIconImage(String paramString);
/*    */ 
/*    */   public abstract Object toPlatformImage(WCImage paramWCImage);
/*    */ 
/*    */   private static String fwkGetMIMETypeForExtension(String paramString)
/*    */   {
/* 35 */     return (String)MimeTypeMapHolder.MIME_TYPE_MAP.get(paramString);
/*    */   }
/*    */ 
/*    */   private static Map<String, String> createMimeTypeMap() {
/* 39 */     HashMap localHashMap = new HashMap(21);
/* 40 */     localHashMap.put("txt", "text/plain");
/* 41 */     localHashMap.put("html", "text/html");
/* 42 */     localHashMap.put("htm", "text/html");
/* 43 */     localHashMap.put("css", "text/css");
/* 44 */     localHashMap.put("xml", "text/xml");
/* 45 */     localHashMap.put("xsl", "text/xsl");
/* 46 */     localHashMap.put("js", "application/x-javascript");
/* 47 */     localHashMap.put("xhtml", "application/xhtml+xml");
/* 48 */     localHashMap.put("svg", "image/svg+xml");
/* 49 */     localHashMap.put("svgz", "image/svg+xml");
/* 50 */     localHashMap.put("gif", "image/gif");
/* 51 */     localHashMap.put("jpg", "image/jpeg");
/* 52 */     localHashMap.put("jpeg", "image/jpeg");
/* 53 */     localHashMap.put("png", "image/png");
/* 54 */     localHashMap.put("tif", "image/tiff");
/* 55 */     localHashMap.put("tiff", "image/tiff");
/* 56 */     localHashMap.put("ico", "image/ico");
/* 57 */     localHashMap.put("cur", "image/ico");
/* 58 */     localHashMap.put("bmp", "image/bmp");
/* 59 */     localHashMap.put("mp3", "audio/mpeg");
/* 60 */     return localHashMap;
/*    */   }
/*    */ 
/*    */   private static String[] fwkGetExtensionsForMIMEType(String paramString)
/*    */   {
/* 70 */     if ("application/x-shockwave-flash".equalsIgnoreCase(paramString)) {
/* 71 */       return new String[] { "swf", "flv" };
/*    */     }
/* 73 */     return new String[0];
/*    */   }
/*    */ 
/*    */   private static Object fwkInvokeWithContext(Method paramMethod, final Object paramObject, final Object[] paramArrayOfObject, AccessControlContext paramAccessControlContext)
/*    */     throws Throwable
/*    */   {
/*    */     try
/*    */     {
/* 82 */       return AccessController.doPrivileged(new PrivilegedExceptionAction()
/*    */       {
/*    */         public Object run() throws Exception {
/* 85 */           return MethodUtil.invoke(this.val$method, paramObject, paramArrayOfObject);
/*    */         }
/*    */       }
/*    */       , paramAccessControlContext);
/*    */     }
/*    */     catch (PrivilegedActionException localPrivilegedActionException)
/*    */     {
/* 89 */       Object localObject = localPrivilegedActionException.getCause();
/* 90 */       if (localObject == null)
/* 91 */         localObject = localPrivilegedActionException;
/* 92 */       else if (((localObject instanceof InvocationTargetException)) && (((Throwable)localObject).getCause() != null))
/*    */       {
/* 94 */         localObject = ((Throwable)localObject).getCause();
/* 95 */       }throw ((Throwable)localObject);
/*    */     }
/*    */   }
/*    */ 
/*    */   private static final class MimeTypeMapHolder
/*    */   {
/* 65 */     private static final Map<String, String> MIME_TYPE_MAP = Utilities.access$100();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.Utilities
 * JD-Core Version:    0.6.2
 */