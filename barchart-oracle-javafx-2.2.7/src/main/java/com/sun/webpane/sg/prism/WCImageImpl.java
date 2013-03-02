/*     */ package com.sun.webpane.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.iio.ImageFrame;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.image.CompoundCoords;
/*     */ import com.sun.prism.image.CompoundTexture;
/*     */ import com.sun.prism.image.Coords;
/*     */ import com.sun.prism.image.ViewPort;
/*     */ import com.sun.webpane.platform.ConfigManager;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class WCImageImpl extends PrismImage
/*     */ {
/*  24 */   private static final Logger log = Logger.getLogger(WCImageImpl.class.getName());
/*     */   public static final int INT_ARGB_PRE = 0;
/*     */   public static final int BYTE_BGRA_PRE = 1;
/*  30 */   public static int defaultType = ConfigManager.getProperty("USE_BGRA") != null ? 1 : 0;
/*     */   Image img;
/*     */   Texture texture;
/*     */   CompoundTexture compoundTexture;
/*     */ 
/*     */   public WCImageImpl(int paramInt1, int paramInt2)
/*     */   {
/*  40 */     this(paramInt1, paramInt2, defaultType);
/*     */   }
/*     */ 
/*     */   public WCImageImpl(int paramInt1, int paramInt2, int paramInt3) {
/*  44 */     if (log.isLoggable(Level.FINE)) {
/*  45 */       log.log(Level.FINE, "Creating empty image({0},{1},{2})", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) });
/*     */     }
/*     */ 
/*  49 */     switch (paramInt3) {
/*     */     case 0:
/*  51 */       this.img = Image.fromIntArgbPreData(new int[paramInt1 * paramInt2], paramInt1, paramInt2);
/*  52 */       break;
/*     */     case 1:
/*  54 */       this.img = Image.fromByteBgraPreData(new byte[paramInt1 * paramInt2 * 4], paramInt1, paramInt2);
/*  55 */       break;
/*     */     default:
/*  57 */       throw new RuntimeException("Unknown image type: " + paramInt3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public WCImageImpl(int[] paramArrayOfInt, int paramInt1, int paramInt2) {
/*  62 */     this(paramArrayOfInt, paramInt1, paramInt2, defaultType);
/*     */   }
/*     */ 
/*     */   public WCImageImpl(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3) {
/*  66 */     if (log.isLoggable(Level.FINE)) {
/*  67 */       log.log(Level.FINE, "Creating image({0},{1},{2}) from buffer", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) });
/*     */     }
/*     */ 
/*  70 */     switch (paramInt3) {
/*     */     case 0:
/*  72 */       this.img = Image.fromIntArgbPreData(paramArrayOfInt, paramInt1, paramInt2);
/*  73 */       break;
/*     */     case 1:
/*  75 */       byte[] arrayOfByte = new byte[paramArrayOfInt.length * 4];
/*     */ 
/*  78 */       for (int i = 0; i < paramArrayOfInt.length; i++) {
/*  79 */         int j = i * 4;
/*  80 */         arrayOfByte[(j++)] = ((byte)paramArrayOfInt[i]);
/*  81 */         arrayOfByte[(j++)] = ((byte)(paramArrayOfInt[i] >> 8));
/*  82 */         arrayOfByte[(j++)] = ((byte)(paramArrayOfInt[i] >> 16));
/*  83 */         arrayOfByte[(j++)] = ((byte)(paramArrayOfInt[i] >> 24));
/*     */       }
/*     */ 
/*  86 */       this.img = Image.fromByteBgraPreData(arrayOfByte, paramInt1, paramInt2);
/*  87 */       break;
/*     */     default:
/*  89 */       throw new RuntimeException("Unknown image type: " + paramInt3);
/*     */     }
/*     */   }
/*     */ 
/*     */   public WCImageImpl(IntBuffer paramIntBuffer, int paramInt1, int paramInt2) {
/*  94 */     if (log.isLoggable(Level.FINE)) {
/*  95 */       log.fine(String.format("Creating image %dx%d from buffer", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) }));
/*     */     }
/*  97 */     this.img = Image.fromIntArgbPreData(paramIntBuffer, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public WCImageImpl(ImageFrame paramImageFrame) {
/* 101 */     if (log.isLoggable(Level.FINE)) {
/* 102 */       log.log(Level.FINE, "Creating image {0}x{1} of type {2} from buffer", new Object[] { Integer.valueOf(paramImageFrame.getWidth()), Integer.valueOf(paramImageFrame.getHeight()), paramImageFrame.getImageType() });
/*     */     }
/*     */ 
/* 106 */     this.img = Image.convertImageFrame(paramImageFrame);
/*     */   }
/*     */ 
/*     */   public WCImageImpl(Image paramImage) {
/* 110 */     this.img = paramImage;
/*     */   }
/*     */ 
/*     */   public Image getImage() {
/* 114 */     return this.img;
/*     */   }
/*     */ 
/*     */   public Graphics getGraphics()
/*     */   {
/* 119 */     return null;
/*     */   }
/*     */ 
/*     */   public void draw(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
/*     */   {
/*     */     Object localObject;
/* 127 */     if ((this.texture == null) && (this.compoundTexture == null)) {
/* 128 */       localObject = paramGraphics.getResourceFactory();
/* 129 */       int i = ((ResourceFactory)localObject).getMaximumTextureSize();
/* 130 */       if ((this.img.getWidth() <= i) && (this.img.getHeight() <= i)) {
/* 131 */         this.texture = ((ResourceFactory)localObject).createTexture(this.img);
/* 132 */         if ((!$assertionsDisabled) && (this.texture == null)) throw new AssertionError(); 
/*     */       }
/* 134 */       else { this.compoundTexture = new CompoundTexture(this.img, i); }
/*     */ 
/*     */     }
/*     */ 
/* 138 */     if (this.texture != null) {
/* 139 */       assert (this.compoundTexture == null);
/* 140 */       paramGraphics.drawTexture(this.texture, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
/*     */     }
/*     */     else
/*     */     {
/* 145 */       assert (this.compoundTexture != null);
/* 146 */       localObject = new ViewPort(paramInt5, paramInt6, paramInt7 - paramInt5, paramInt8 - paramInt6);
/*     */ 
/* 151 */       Coords localCoords = new Coords(paramInt3 - paramInt1, paramInt4 - paramInt2, (ViewPort)localObject);
/* 152 */       CompoundCoords localCompoundCoords = new CompoundCoords(this.compoundTexture, localCoords);
/*     */ 
/* 155 */       localCompoundCoords.draw(paramGraphics, this.compoundTexture, paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 161 */     if (this.texture != null) {
/* 162 */       this.texture.dispose();
/* 163 */       this.texture = null;
/*     */     }
/* 165 */     if (this.compoundTexture != null) {
/* 166 */       this.compoundTexture.dispose();
/* 167 */       this.compoundTexture = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 173 */     return this.img.getWidth();
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 178 */     return this.img.getHeight();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCImageImpl
 * JD-Core Version:    0.6.2
 */