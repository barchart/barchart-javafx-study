/*    */ package com.sun.webpane.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.tk.Toolkit;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.Image;
/*    */ import com.sun.webpane.platform.graphics.WCImage;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.Iterator;
/*    */ import javax.imageio.ImageIO;
/*    */ import javax.imageio.ImageWriter;
/*    */ import sun.misc.BASE64Encoder;
/*    */ 
/*    */ public abstract class PrismImage extends WCImage
/*    */ {
/*    */   public abstract Image getImage();
/*    */ 
/*    */   public abstract Graphics getGraphics();
/*    */ 
/*    */   public abstract void draw(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
/*    */ 
/*    */   public abstract void dispose();
/*    */ 
/*    */   public void deref()
/*    */   {
/* 33 */     super.deref();
/* 34 */     if (!hasRefs())
/* 35 */       dispose();
/*    */   }
/*    */ 
/*    */   public final String toDataURL(String paramString)
/*    */   {
/* 41 */     Object localObject1 = Toolkit.getToolkit().toExternalImage(getImage(), BufferedImage.class);
/* 42 */     if ((localObject1 instanceof BufferedImage)) { Iterator localIterator = ImageIO.getImageWritersByMIMEType(paramString);
/*    */       ByteArrayOutputStream localByteArrayOutputStream;
/*    */       while (true) { if (!localIterator.hasNext()) break label155;
/* 45 */         localByteArrayOutputStream = new ByteArrayOutputStream();
/* 46 */         ImageWriter localImageWriter = (ImageWriter)localIterator.next();
/*    */         try {
/* 48 */           localImageWriter.setOutput(ImageIO.createImageOutputStream(localByteArrayOutputStream));
/* 49 */           localImageWriter.write((BufferedImage)localObject1);
/*    */         }
/*    */         catch (IOException localIOException)
/*    */         {
/* 55 */           localImageWriter.dispose(); } finally { localImageWriter.dispose(); }
/*    */       }
/* 57 */       StringBuilder localStringBuilder = new StringBuilder();
/* 58 */       localStringBuilder.append("data:").append(paramString).append(";base64,");
/* 59 */       localStringBuilder.append(new BASE64Encoder().encode(localByteArrayOutputStream.toByteArray()));
/* 60 */       return localStringBuilder.toString();
/*    */     }
/*    */ 
/* 63 */     label155: return null;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.PrismImage
 * JD-Core Version:    0.6.2
 */