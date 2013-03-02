/*     */ package javafx.scene.image;
/*     */ 
/*     */ import com.sun.javafx.tk.ImageLoader;
/*     */ import com.sun.javafx.tk.PlatformImage;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.javafx.tk.Toolkit.WritableImageAccessor;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.scene.paint.Color;
/*     */ 
/*     */ public class WritableImage extends Image
/*     */ {
/*     */   private ImageLoader tkImageLoader;
/*     */   private PixelWriter writer;
/*     */ 
/*     */   public WritableImage(int paramInt1, int paramInt2)
/*     */   {
/*  73 */     super(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public WritableImage(PixelReader paramPixelReader, int paramInt1, int paramInt2)
/*     */   {
/*  98 */     super(paramInt1, paramInt2);
/*  99 */     getPixelWriter().setPixels(0, 0, paramInt1, paramInt2, paramPixelReader, 0, 0);
/*     */   }
/*     */ 
/*     */   public WritableImage(PixelReader paramPixelReader, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 130 */     super(paramInt3, paramInt4);
/* 131 */     getPixelWriter().setPixels(0, 0, paramInt3, paramInt4, paramPixelReader, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   boolean isAnimation()
/*     */   {
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */   boolean pixelsReadable()
/*     */   {
/* 141 */     return true;
/*     */   }
/*     */ 
/*     */   public final PixelWriter getPixelWriter()
/*     */   {
/* 152 */     if ((getProgress() < 1.0D) || (isError())) {
/* 153 */       return null;
/*     */     }
/* 155 */     if (this.writer == null) {
/* 156 */       this.writer = new PixelWriter() {
/* 157 */         ReadOnlyObjectProperty<PlatformImage> pimgprop = WritableImage.this.impl_platformImageProperty();
/*     */ 
/*     */         public PixelFormat getPixelFormat()
/*     */         {
/* 162 */           PlatformImage localPlatformImage = WritableImage.this.getWritablePlatformImage();
/* 163 */           return localPlatformImage.getPlatformPixelFormat();
/*     */         }
/*     */ 
/*     */         public void setArgb(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
/*     */         {
/* 168 */           WritableImage.this.getWritablePlatformImage().setArgb(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
/* 169 */           WritableImage.this.pixelsDirty();
/*     */         }
/*     */ 
/*     */         public void setColor(int paramAnonymousInt1, int paramAnonymousInt2, Color paramAnonymousColor)
/*     */         {
/* 174 */           int i = (int)Math.round(paramAnonymousColor.getOpacity() * 255.0D);
/* 175 */           int j = (int)Math.round(paramAnonymousColor.getRed() * 255.0D);
/* 176 */           int k = (int)Math.round(paramAnonymousColor.getGreen() * 255.0D);
/* 177 */           int m = (int)Math.round(paramAnonymousColor.getBlue() * 255.0D);
/* 178 */           setArgb(paramAnonymousInt1, paramAnonymousInt2, i << 24 | j << 16 | k << 8 | m);
/*     */         }
/*     */ 
/*     */         public <T extends Buffer> void setPixels(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, PixelFormat<T> paramAnonymousPixelFormat, T paramAnonymousT, int paramAnonymousInt5)
/*     */         {
/* 187 */           PlatformImage localPlatformImage = WritableImage.this.getWritablePlatformImage();
/* 188 */           localPlatformImage.setPixels(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousPixelFormat, paramAnonymousT, paramAnonymousInt5);
/*     */ 
/* 190 */           WritableImage.this.pixelsDirty();
/*     */         }
/*     */ 
/*     */         public void setPixels(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, PixelFormat<ByteBuffer> paramAnonymousPixelFormat, byte[] paramAnonymousArrayOfByte, int paramAnonymousInt5, int paramAnonymousInt6)
/*     */         {
/* 198 */           PlatformImage localPlatformImage = WritableImage.this.getWritablePlatformImage();
/* 199 */           localPlatformImage.setPixels(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousPixelFormat, paramAnonymousArrayOfByte, paramAnonymousInt5, paramAnonymousInt6);
/*     */ 
/* 201 */           WritableImage.this.pixelsDirty();
/*     */         }
/*     */ 
/*     */         public void setPixels(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, PixelFormat<IntBuffer> paramAnonymousPixelFormat, int[] paramAnonymousArrayOfInt, int paramAnonymousInt5, int paramAnonymousInt6)
/*     */         {
/* 209 */           PlatformImage localPlatformImage = WritableImage.this.getWritablePlatformImage();
/* 210 */           localPlatformImage.setPixels(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousPixelFormat, paramAnonymousArrayOfInt, paramAnonymousInt5, paramAnonymousInt6);
/*     */ 
/* 212 */           WritableImage.this.pixelsDirty();
/*     */         }
/*     */ 
/*     */         public void setPixels(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, PixelReader paramAnonymousPixelReader, int paramAnonymousInt5, int paramAnonymousInt6)
/*     */         {
/* 219 */           PlatformImage localPlatformImage = WritableImage.this.getWritablePlatformImage();
/* 220 */           localPlatformImage.setPixels(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousPixelReader, paramAnonymousInt5, paramAnonymousInt6);
/* 221 */           WritableImage.this.pixelsDirty();
/*     */         }
/*     */       };
/*     */     }
/* 225 */     return this.writer;
/*     */   }
/*     */ 
/*     */   private void loadTkImage(Object paramObject) {
/* 229 */     if (!(paramObject instanceof ImageLoader)) {
/* 230 */       throw new IllegalArgumentException("Unrecognized image loader: " + paramObject);
/*     */     }
/*     */ 
/* 233 */     ImageLoader localImageLoader = (ImageLoader)paramObject;
/* 234 */     if ((localImageLoader.getWidth() != (int)getWidth()) || (localImageLoader.getHeight() != (int)getHeight()))
/*     */     {
/* 237 */       throw new IllegalArgumentException("Size of loader does not match size of image");
/*     */     }
/*     */ 
/* 240 */     super.setPlatformImage(localImageLoader.getFrame(0));
/* 241 */     this.tkImageLoader = localImageLoader;
/*     */   }
/*     */ 
/*     */   private Object getTkImageLoader() {
/* 245 */     return this.tkImageLoader;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  47 */     Toolkit.setWritableImageAccessor(new Toolkit.WritableImageAccessor() {
/*     */       public void loadTkImage(WritableImage paramAnonymousWritableImage, Object paramAnonymousObject) {
/*  49 */         paramAnonymousWritableImage.loadTkImage(paramAnonymousObject);
/*     */       }
/*     */ 
/*     */       public Object getTkImageLoader(WritableImage paramAnonymousWritableImage) {
/*  53 */         return paramAnonymousWritableImage.getTkImageLoader();
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.image.WritableImage
 * JD-Core Version:    0.6.2
 */