/*    */ package com.sun.prism;
/*    */ 
/*    */ public class ExternalImageTools
/*    */ {
/*    */   static boolean isBufferedImage(Class paramClass)
/*    */   {
/* 23 */     return "java.awt.image.BufferedImage".equals(paramClass.getName());
/*    */   }
/*    */ 
/*    */   public static boolean isFormatSupported(Class paramClass)
/*    */   {
/* 28 */     return isBufferedImage(paramClass);
/*    */   }
/*    */ 
/*    */   public static IExporter getExporter(Object paramObject)
/*    */   {
/* 39 */     assert (paramObject != null);
/*    */ 
/* 41 */     boolean bool = paramObject instanceof Class;
/* 42 */     Class localClass = bool ? (Class)paramObject : paramObject.getClass();
/* 43 */     return isBufferedImage(localClass) ? new BufferedImageExporter(null) : null;
/*    */   }
/*    */ 
/*    */   public static IImporter getImporter(Object paramObject)
/*    */   {
/* 54 */     return isBufferedImage(paramObject.getClass()) ? new BufferedImageImporter(null) : null;
/*    */   }
/*    */ 
/*    */   private static class BufferedImageImporter
/*    */     implements ExternalImageTools.IImporter
/*    */   {
/*    */     public Image loadExternalImage(Object paramObject)
/*    */     {
/* 49 */       return BufferedImageTools.importBufferedImage(paramObject);
/*    */     }
/*    */   }
/*    */ 
/*    */   private static class BufferedImageExporter
/*    */     implements ExternalImageTools.IExporter
/*    */   {
/*    */     public Object exportPrismImage(Image paramImage, Object paramObject)
/*    */     {
/* 34 */       return BufferedImageTools.exportBufferedImage(paramImage, paramObject);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static abstract interface IImporter
/*    */   {
/*    */     public abstract Image loadExternalImage(Object paramObject);
/*    */   }
/*    */ 
/*    */   public static abstract interface IExporter
/*    */   {
/*    */     public abstract Object exportPrismImage(Image paramImage, Object paramObject);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.ExternalImageTools
 * JD-Core Version:    0.6.2
 */