/*     */ package javafx.scene;
/*     */ 
/*     */ import com.sun.javafx.cursor.CursorFrame;
/*     */ import com.sun.javafx.cursor.ImageCursorFrame;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*     */ import javafx.beans.property.ReadOnlyDoublePropertyBase;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectPropertyBase;
/*     */ import javafx.geometry.Dimension2D;
/*     */ import javafx.scene.image.Image;
/*     */ 
/*     */ public class ImageCursor extends Cursor
/*     */ {
/*     */   private ObjectPropertyImpl<Image> image;
/*     */   private DoublePropertyImpl hotspotX;
/*     */   private DoublePropertyImpl hotspotY;
/*     */   private CursorFrame currentCursorFrame;
/*     */   private ImageCursorFrame firstCursorFrame;
/*     */   private Map<Object, ImageCursorFrame> otherCursorFrames;
/*     */   private int activeCounter;
/*     */   private InvalidationListener imageListener;
/*     */ 
/*     */   public final Image getImage()
/*     */   {
/*  76 */     return this.image == null ? null : (Image)this.image.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<Image> imageProperty() {
/*  80 */     return imagePropertyImpl();
/*     */   }
/*     */ 
/*     */   private ObjectPropertyImpl<Image> imagePropertyImpl() {
/*  84 */     if (this.image == null) {
/*  85 */       this.image = new ObjectPropertyImpl("image");
/*     */     }
/*     */ 
/*  88 */     return this.image;
/*     */   }
/*     */ 
/*     */   public final double getHotspotX()
/*     */   {
/* 103 */     return this.hotspotX == null ? 0.0D : this.hotspotX.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyDoubleProperty hotspotXProperty() {
/* 107 */     return hotspotXPropertyImpl();
/*     */   }
/*     */ 
/*     */   private DoublePropertyImpl hotspotXPropertyImpl() {
/* 111 */     if (this.hotspotX == null) {
/* 112 */       this.hotspotX = new DoublePropertyImpl("hotspotX");
/*     */     }
/*     */ 
/* 115 */     return this.hotspotX;
/*     */   }
/*     */ 
/*     */   public final double getHotspotY()
/*     */   {
/* 130 */     return this.hotspotY == null ? 0.0D : this.hotspotY.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyDoubleProperty hotspotYProperty() {
/* 134 */     return hotspotYPropertyImpl();
/*     */   }
/*     */ 
/*     */   private DoublePropertyImpl hotspotYPropertyImpl() {
/* 138 */     if (this.hotspotY == null) {
/* 139 */       this.hotspotY = new DoublePropertyImpl("hotspotY");
/*     */     }
/*     */ 
/* 142 */     return this.hotspotY;
/*     */   }
/*     */ 
/*     */   public ImageCursor()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ImageCursor(Image paramImage)
/*     */   {
/* 180 */     this(paramImage, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   public ImageCursor(Image paramImage, double paramDouble1, double paramDouble2)
/*     */   {
/* 194 */     if ((paramImage != null) && (paramImage.getProgress() < 1.0D)) {
/* 195 */       DelayedInitialization.applyTo(this, paramImage, paramDouble1, paramDouble2);
/*     */     }
/*     */     else
/* 198 */       initialize(paramImage, paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */   public static Dimension2D getBestSize(double paramDouble1, double paramDouble2)
/*     */   {
/* 228 */     return Toolkit.getToolkit().getBestCursorSize((int)paramDouble1, (int)paramDouble2);
/*     */   }
/*     */ 
/*     */   public static int getMaximumColors()
/*     */   {
/* 254 */     return Toolkit.getToolkit().getMaximumCursorColors();
/*     */   }
/*     */ 
/*     */   public static ImageCursor chooseBestCursor(Image[] paramArrayOfImage, double paramDouble1, double paramDouble2)
/*     */   {
/* 277 */     ImageCursor localImageCursor = new ImageCursor();
/*     */ 
/* 279 */     if (needsDelayedInitialization(paramArrayOfImage)) {
/* 280 */       DelayedInitialization.applyTo(localImageCursor, paramArrayOfImage, paramDouble1, paramDouble2);
/*     */     }
/*     */     else {
/* 283 */       localImageCursor.initialize(paramArrayOfImage, paramDouble1, paramDouble2);
/*     */     }
/*     */ 
/* 286 */     return localImageCursor;
/*     */   }
/*     */ 
/*     */   CursorFrame getCurrentFrame() {
/* 290 */     if (this.currentCursorFrame != null) {
/* 291 */       return this.currentCursorFrame;
/*     */     }
/*     */ 
/* 294 */     Image localImage = getImage();
/*     */ 
/* 296 */     if (localImage == null) {
/* 297 */       this.currentCursorFrame = Cursor.DEFAULT.getCurrentFrame();
/* 298 */       return this.currentCursorFrame;
/*     */     }
/*     */ 
/* 301 */     Object localObject = localImage.impl_getPlatformImage();
/* 302 */     if (localObject == null) {
/* 303 */       this.currentCursorFrame = Cursor.DEFAULT.getCurrentFrame();
/* 304 */       return this.currentCursorFrame;
/*     */     }
/*     */ 
/* 307 */     if (this.firstCursorFrame == null) {
/* 308 */       this.firstCursorFrame = new ImageCursorFrame(localObject, localImage.getWidth(), localImage.getHeight(), getHotspotX(), getHotspotY());
/*     */ 
/* 314 */       this.currentCursorFrame = this.firstCursorFrame;
/* 315 */     } else if (this.firstCursorFrame.getPlatformImage() == localObject) {
/* 316 */       this.currentCursorFrame = this.firstCursorFrame;
/*     */     } else {
/* 318 */       if (this.otherCursorFrames == null) {
/* 319 */         this.otherCursorFrames = new HashMap();
/*     */       }
/*     */ 
/* 322 */       this.currentCursorFrame = ((CursorFrame)this.otherCursorFrames.get(localObject));
/* 323 */       if (this.currentCursorFrame == null)
/*     */       {
/* 325 */         ImageCursorFrame localImageCursorFrame = new ImageCursorFrame(localObject, localImage.getWidth(), localImage.getHeight(), getHotspotX(), getHotspotY());
/*     */ 
/* 332 */         this.otherCursorFrames.put(localObject, localImageCursorFrame);
/* 333 */         this.currentCursorFrame = localImageCursorFrame;
/*     */       }
/*     */     }
/*     */ 
/* 337 */     return this.currentCursorFrame;
/*     */   }
/*     */ 
/*     */   private void invalidateCurrentFrame() {
/* 341 */     this.currentCursorFrame = null;
/*     */   }
/*     */ 
/*     */   void activate()
/*     */   {
/* 346 */     if (++this.activeCounter == 1) {
/* 347 */       bindImage(getImage());
/* 348 */       invalidateCurrentFrame();
/*     */     }
/*     */   }
/*     */ 
/*     */   void deactivate()
/*     */   {
/* 354 */     if (--this.activeCounter == 0)
/* 355 */       unbindImage(getImage());
/*     */   }
/*     */ 
/*     */   private void initialize(Image[] paramArrayOfImage, double paramDouble1, double paramDouble2)
/*     */   {
/* 362 */     Dimension2D localDimension2D = getBestSize(1.0D, 1.0D);
/*     */ 
/* 366 */     if ((paramArrayOfImage.length == 0) || (localDimension2D.getWidth() == 0.0D) || (localDimension2D.getHeight() == 0.0D))
/*     */     {
/* 368 */       return;
/*     */     }
/*     */ 
/* 372 */     if (paramArrayOfImage.length == 1) {
/* 373 */       initialize(paramArrayOfImage[0], paramDouble1, paramDouble2);
/* 374 */       return;
/*     */     }
/*     */ 
/* 377 */     Image localImage = findBestImage(paramArrayOfImage);
/* 378 */     double d1 = localImage.getWidth() / paramArrayOfImage[0].getWidth();
/* 379 */     double d2 = localImage.getHeight() / paramArrayOfImage[0].getHeight();
/*     */ 
/* 381 */     initialize(localImage, paramDouble1 * d1, paramDouble2 * d2);
/*     */   }
/*     */ 
/*     */   private void initialize(Image paramImage, double paramDouble1, double paramDouble2)
/*     */   {
/* 387 */     Image localImage = getImage();
/* 388 */     double d1 = getHotspotX();
/* 389 */     double d2 = getHotspotY();
/*     */ 
/* 391 */     if ((paramImage == null) || (paramImage.getWidth() < 1.0D) || (paramImage.getHeight() < 1.0D))
/*     */     {
/* 394 */       paramDouble1 = 0.0D;
/* 395 */       paramDouble2 = 0.0D;
/*     */     } else {
/* 397 */       if (paramDouble1 < 0.0D) {
/* 398 */         paramDouble1 = 0.0D;
/*     */       }
/* 400 */       if (paramDouble1 > paramImage.getWidth() - 1.0D) {
/* 401 */         paramDouble1 = paramImage.getWidth() - 1.0D;
/*     */       }
/* 403 */       if (paramDouble2 < 0.0D) {
/* 404 */         paramDouble2 = 0.0D;
/*     */       }
/* 406 */       if (paramDouble2 > paramImage.getHeight() - 1.0D) {
/* 407 */         paramDouble2 = paramImage.getHeight() - 1.0D;
/*     */       }
/*     */     }
/*     */ 
/* 411 */     imagePropertyImpl().store(paramImage);
/* 412 */     hotspotXPropertyImpl().store(paramDouble1);
/* 413 */     hotspotYPropertyImpl().store(paramDouble2);
/*     */ 
/* 415 */     if (localImage != paramImage) {
/* 416 */       if (this.activeCounter > 0) {
/* 417 */         unbindImage(localImage);
/* 418 */         bindImage(paramImage);
/*     */       }
/*     */ 
/* 421 */       invalidateCurrentFrame();
/* 422 */       this.image.fireValueChangedEvent();
/*     */     }
/*     */ 
/* 425 */     if (d1 != paramDouble1) {
/* 426 */       this.hotspotX.fireValueChangedEvent();
/*     */     }
/*     */ 
/* 429 */     if (d2 != paramDouble2)
/* 430 */       this.hotspotY.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   private InvalidationListener getImageListener()
/*     */   {
/* 437 */     if (this.imageListener == null) {
/* 438 */       this.imageListener = new InvalidationListener()
/*     */       {
/*     */         public void invalidated(Observable paramAnonymousObservable) {
/* 441 */           ImageCursor.this.invalidateCurrentFrame();
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/* 446 */     return this.imageListener;
/*     */   }
/*     */ 
/*     */   private void bindImage(Image paramImage) {
/* 450 */     if (paramImage == null) {
/* 451 */       return;
/*     */     }
/*     */ 
/* 454 */     paramImage.impl_platformImageProperty().addListener(getImageListener());
/*     */   }
/*     */ 
/*     */   private void unbindImage(Image paramImage) {
/* 458 */     if (paramImage == null) {
/* 459 */       return;
/*     */     }
/*     */ 
/* 462 */     paramImage.impl_platformImageProperty().removeListener(getImageListener());
/*     */   }
/*     */ 
/*     */   private static boolean needsDelayedInitialization(Image[] paramArrayOfImage) {
/* 466 */     for (Image localImage : paramArrayOfImage) {
/* 467 */       if (localImage.getProgress() < 1.0D) {
/* 468 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 472 */     return false;
/*     */   }
/*     */ 
/*     */   private static Image findBestImage(Image[] paramArrayOfImage)
/*     */   {
/*     */     Object localObject2;
/* 478 */     for (localObject2 : paramArrayOfImage) {
/* 479 */       Dimension2D localDimension2D1 = getBestSize((int)((Image)localObject2).getWidth(), (int)((Image)localObject2).getHeight());
/*     */ 
/* 481 */       if ((localDimension2D1.getWidth() == ((Image)localObject2).getWidth()) && (localDimension2D1.getHeight() == ((Image)localObject2).getHeight()))
/*     */       {
/* 483 */         return localObject2;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 489 */     ??? = null;
/* 490 */     double d1 = 1.7976931348623157E+308D;
/*     */     Object localObject3;
/*     */     Dimension2D localDimension2D2;
/*     */     double d2;
/*     */     double d3;
/*     */     double d4;
/* 491 */     for (localObject3 : paramArrayOfImage) {
/* 492 */       if ((localObject3.getWidth() > 0.0D) && (localObject3.getHeight() > 0.0D)) {
/* 493 */         localDimension2D2 = getBestSize(localObject3.getWidth(), localObject3.getHeight());
/*     */ 
/* 495 */         d2 = localDimension2D2.getWidth() / localObject3.getWidth();
/* 496 */         d3 = localDimension2D2.getHeight() / localObject3.getHeight();
/* 497 */         if ((d2 >= 1.0D) && (d3 >= 1.0D)) {
/* 498 */           d4 = Math.max(d2, d3);
/* 499 */           if (d4 < d1) {
/* 500 */             ??? = localObject3;
/* 501 */             d1 = d4;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 506 */     if (??? != null) {
/* 507 */       return ???;
/*     */     }
/*     */ 
/* 512 */     for (localObject3 : paramArrayOfImage) {
/* 513 */       if ((localObject3.getWidth() > 0.0D) && (localObject3.getHeight() > 0.0D)) {
/* 514 */         localDimension2D2 = getBestSize(localObject3.getWidth(), localObject3.getHeight());
/*     */ 
/* 516 */         if ((localDimension2D2.getWidth() > 0.0D) && (localDimension2D2.getHeight() > 0.0D)) {
/* 517 */           d2 = localDimension2D2.getWidth() / localObject3.getWidth();
/* 518 */           if (d2 < 1.0D) {
/* 519 */             d2 = 1.0D / d2;
/*     */           }
/* 521 */           d3 = localDimension2D2.getHeight() / localObject3.getHeight();
/* 522 */           if (d3 < 1.0D) {
/* 523 */             d3 = 1.0D / d3;
/*     */           }
/* 525 */           d4 = Math.max(d2, d3);
/* 526 */           if (d4 < d1) {
/* 527 */             ??? = localObject3;
/* 528 */             d1 = d4;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 533 */     if (??? != null) {
/* 534 */       return ???;
/*     */     }
/*     */ 
/* 537 */     return paramArrayOfImage[0];
/*     */   }
/*     */ 
/*     */   private static final class DelayedInitialization
/*     */     implements InvalidationListener
/*     */   {
/*     */     private final ImageCursor targetCursor;
/*     */     private final Image[] images;
/*     */     private final double hotspotX;
/*     */     private final double hotspotY;
/*     */     private final boolean initAsSingle;
/*     */     private int waitForImages;
/*     */ 
/*     */     private DelayedInitialization(ImageCursor paramImageCursor, Image[] paramArrayOfImage, double paramDouble1, double paramDouble2, boolean paramBoolean)
/*     */     {
/* 626 */       this.targetCursor = paramImageCursor;
/* 627 */       this.images = paramArrayOfImage;
/* 628 */       this.hotspotX = paramDouble1;
/* 629 */       this.hotspotY = paramDouble2;
/* 630 */       this.initAsSingle = paramBoolean;
/*     */     }
/*     */ 
/*     */     public static void applyTo(ImageCursor paramImageCursor, Image[] paramArrayOfImage, double paramDouble1, double paramDouble2)
/*     */     {
/* 638 */       DelayedInitialization localDelayedInitialization = new DelayedInitialization(paramImageCursor, (Image[])Arrays.copyOf(paramArrayOfImage, paramArrayOfImage.length), paramDouble1, paramDouble2, false);
/*     */ 
/* 644 */       localDelayedInitialization.start();
/*     */     }
/*     */ 
/*     */     public static void applyTo(ImageCursor paramImageCursor, Image paramImage, double paramDouble1, double paramDouble2)
/*     */     {
/* 651 */       DelayedInitialization localDelayedInitialization = new DelayedInitialization(paramImageCursor, new Image[] { paramImage }, paramDouble1, paramDouble2, true);
/*     */ 
/* 657 */       localDelayedInitialization.start();
/*     */     }
/*     */ 
/*     */     private void start() {
/* 661 */       for (Image localImage : this.images)
/* 662 */         if (localImage.getProgress() < 1.0D) {
/* 663 */           this.waitForImages += 1;
/* 664 */           localImage.progressProperty().addListener(this);
/*     */         }
/*     */     }
/*     */ 
/*     */     private void cleanupAndFinishInitialization()
/*     */     {
/* 670 */       for (Image localImage : this.images) {
/* 671 */         localImage.progressProperty().removeListener(this);
/*     */       }
/*     */ 
/* 674 */       if (this.initAsSingle)
/* 675 */         this.targetCursor.initialize(this.images[0], this.hotspotX, this.hotspotY);
/*     */       else
/* 677 */         this.targetCursor.initialize(this.images, this.hotspotX, this.hotspotY);
/*     */     }
/*     */ 
/*     */     public void invalidated(Observable paramObservable)
/*     */     {
/* 683 */       if ((((ReadOnlyDoubleProperty)paramObservable).get() == 1.0D) && 
/* 684 */         (--this.waitForImages == 0))
/* 685 */         cleanupAndFinishInitialization();
/*     */     }
/*     */   }
/*     */ 
/*     */   private final class ObjectPropertyImpl<T> extends ReadOnlyObjectPropertyBase<T>
/*     */   {
/*     */     private final String name;
/*     */     private T value;
/*     */ 
/*     */     public ObjectPropertyImpl(String arg2)
/*     */     {
/*     */       Object localObject;
/* 581 */       this.name = localObject;
/*     */     }
/*     */ 
/*     */     public void store(T paramT) {
/* 585 */       this.value = paramT;
/*     */     }
/*     */ 
/*     */     public void fireValueChangedEvent()
/*     */     {
/* 590 */       super.fireValueChangedEvent();
/*     */     }
/*     */ 
/*     */     public T get()
/*     */     {
/* 595 */       return this.value;
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 600 */       return ImageCursor.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 605 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */   private final class DoublePropertyImpl extends ReadOnlyDoublePropertyBase
/*     */   {
/*     */     private final String name;
/*     */     private double value;
/*     */ 
/*     */     public DoublePropertyImpl(String arg2)
/*     */     {
/*     */       Object localObject;
/* 546 */       this.name = localObject;
/*     */     }
/*     */ 
/*     */     public void store(double paramDouble) {
/* 550 */       this.value = paramDouble;
/*     */     }
/*     */ 
/*     */     public void fireValueChangedEvent()
/*     */     {
/* 555 */       super.fireValueChangedEvent();
/*     */     }
/*     */ 
/*     */     public double get()
/*     */     {
/* 560 */       return this.value;
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 565 */       return ImageCursor.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 570 */       return this.name;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.ImageCursor
 * JD-Core Version:    0.6.2
 */