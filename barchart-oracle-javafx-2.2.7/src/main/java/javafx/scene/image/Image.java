/*      */ package javafx.scene.image;
/*      */ 
/*      */ import com.sun.javafx.runtime.async.AsyncOperation;
/*      */ import com.sun.javafx.runtime.async.AsyncOperationListener;
/*      */ import com.sun.javafx.tk.ImageLoader;
/*      */ import com.sun.javafx.tk.PlatformImage;
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import java.io.InputStream;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.nio.Buffer;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.util.LinkedList;
/*      */ import java.util.Queue;
/*      */ import javafx.animation.KeyFrame;
/*      */ import javafx.animation.KeyValue;
/*      */ import javafx.animation.Timeline;
/*      */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*      */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*      */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*      */ import javafx.beans.property.ReadOnlyDoublePropertyBase;
/*      */ import javafx.beans.property.ReadOnlyDoubleWrapper;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectPropertyBase;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.event.ActionEvent;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.scene.paint.Color;
/*      */ import javafx.util.Duration;
/*      */ 
/*      */ public class Image
/*      */ {
/*      */   private static final String URL_QUICKMATCH = "^\\p{Alpha}[\\p{Alnum}+.-]*:.*$";
/*      */   private final String url;
/*      */   private final InputStream impl_source;
/*      */   private ReadOnlyDoubleWrapper progress;
/*      */   private final double requestedWidth;
/*      */   private final double requestedHeight;
/*      */   private DoublePropertyImpl width;
/*      */   private DoublePropertyImpl height;
/*      */   private final boolean preserveRatio;
/*      */   private final boolean smooth;
/*      */   private final boolean backgroundLoading;
/*      */   private ReadOnlyBooleanWrapper error;
/*      */ 
/*      */   /** @deprecated */
/*      */   private ObjectPropertyImpl<PlatformImage> platformImage;
/*      */   private ImageTask backgroundTask;
/*      */   private Timeline timeline;
/*      */   private static final int MAX_RUNNING_TASKS = 4;
/*  875 */   private static int runningTasks = 0;
/*  876 */   private static final Queue<ImageTask> pendingTasks = new LinkedList();
/*      */   private PixelReader reader;
/*      */ 
/*      */   @Deprecated
/*      */   public final String impl_getUrl()
/*      */   {
/*  125 */     return this.url;
/*      */   }
/*      */ 
/*      */   final InputStream getImpl_source()
/*      */   {
/*  134 */     return this.impl_source;
/*      */   }
/*      */ 
/*      */   private void setProgress(double paramDouble)
/*      */   {
/*  148 */     progressPropertyImpl().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getProgress() {
/*  152 */     return this.progress == null ? 0.0D : this.progress.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyDoubleProperty progressProperty() {
/*  156 */     return progressPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyDoubleWrapper progressPropertyImpl() {
/*  160 */     if (this.progress == null) {
/*  161 */       this.progress = new ReadOnlyDoubleWrapper(this, "progress");
/*      */     }
/*  163 */     return this.progress;
/*      */   }
/*      */ 
/*      */   public final double getRequestedWidth()
/*      */   {
/*  191 */     return this.requestedWidth;
/*      */   }
/*      */ 
/*      */   public final double getRequestedHeight()
/*      */   {
/*  219 */     return this.requestedHeight;
/*      */   }
/*      */ 
/*      */   public final double getWidth()
/*      */   {
/*  229 */     return this.width == null ? 0.0D : this.width.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyDoubleProperty widthProperty() {
/*  233 */     return widthPropertyImpl();
/*      */   }
/*      */ 
/*      */   private DoublePropertyImpl widthPropertyImpl() {
/*  237 */     if (this.width == null) {
/*  238 */       this.width = new DoublePropertyImpl("width");
/*      */     }
/*      */ 
/*  241 */     return this.width;
/*      */   }
/*      */ 
/*      */   public final double getHeight()
/*      */   {
/*  286 */     return this.height == null ? 0.0D : this.height.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyDoubleProperty heightProperty() {
/*  290 */     return heightPropertyImpl();
/*      */   }
/*      */ 
/*      */   private DoublePropertyImpl heightPropertyImpl() {
/*  294 */     if (this.height == null) {
/*  295 */       this.height = new DoublePropertyImpl("height");
/*      */     }
/*      */ 
/*  298 */     return this.height;
/*      */   }
/*      */ 
/*      */   public final boolean isPreserveRatio()
/*      */   {
/*  366 */     return this.preserveRatio;
/*      */   }
/*      */ 
/*      */   public final boolean isSmooth()
/*      */   {
/*  400 */     return this.smooth;
/*      */   }
/*      */ 
/*      */   public final boolean isBackgroundLoading()
/*      */   {
/*  415 */     return this.backgroundLoading;
/*      */   }
/*      */ 
/*      */   private void setError(boolean paramBoolean)
/*      */   {
/*  427 */     errorPropertyImpl().set(paramBoolean);
/*      */   }
/*      */ 
/*      */   public final boolean isError() {
/*  431 */     return this.error == null ? false : this.error.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyBooleanProperty errorProperty() {
/*  435 */     return errorPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyBooleanWrapper errorPropertyImpl() {
/*  439 */     if (this.error == null) {
/*  440 */       this.error = new ReadOnlyBooleanWrapper(this, "error");
/*      */     }
/*  442 */     return this.error;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public final Object impl_getPlatformImage()
/*      */   {
/*  462 */     return this.platformImage == null ? null : (PlatformImage)this.platformImage.get();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public final ReadOnlyObjectProperty<PlatformImage> impl_platformImageProperty()
/*      */   {
/*  470 */     return platformImagePropertyImpl();
/*      */   }
/*      */ 
/*      */   private ObjectPropertyImpl<PlatformImage> platformImagePropertyImpl() {
/*  474 */     if (this.platformImage == null) {
/*  475 */       this.platformImage = new ObjectPropertyImpl("platformImage");
/*      */     }
/*      */ 
/*  478 */     return this.platformImage;
/*      */   }
/*      */ 
/*      */   void pixelsDirty() {
/*  482 */     platformImagePropertyImpl().fireValueChangedEvent();
/*      */   }
/*      */ 
/*      */   public Image(String paramString)
/*      */   {
/*  538 */     this(validateUrl(paramString), null, 0.0D, 0.0D, false, false, false);
/*  539 */     initialize(null);
/*      */   }
/*      */ 
/*      */   public Image(String paramString, boolean paramBoolean)
/*      */   {
/*  554 */     this(validateUrl(paramString), null, 0.0D, 0.0D, false, false, paramBoolean);
/*  555 */     initialize(null);
/*      */   }
/*      */ 
/*      */   public Image(String paramString, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  577 */     this(validateUrl(paramString), null, paramDouble1, paramDouble2, paramBoolean1, paramBoolean2, false);
/*      */ 
/*  579 */     initialize(null);
/*      */   }
/*      */ 
/*      */   public Image(String paramString, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*      */   {
/*  611 */     this(validateUrl(paramString), null, paramDouble1, paramDouble2, paramBoolean1, paramBoolean2, paramBoolean3);
/*      */ 
/*  613 */     initialize(null);
/*      */   }
/*      */ 
/*      */   public Image(InputStream paramInputStream)
/*      */   {
/*  624 */     this(null, validateInputStream(paramInputStream), 0.0D, 0.0D, false, false, false);
/*  625 */     initialize(null);
/*      */   }
/*      */ 
/*      */   public Image(InputStream paramInputStream, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  644 */     this(null, validateInputStream(paramInputStream), paramDouble1, paramDouble2, paramBoolean1, paramBoolean2, false);
/*      */ 
/*  646 */     initialize(null);
/*      */   }
/*      */ 
/*      */   Image(int paramInt1, int paramInt2)
/*      */   {
/*  658 */     this(null, null, paramInt1, paramInt2, false, false, false);
/*  659 */     if ((paramInt1 <= 0) || (paramInt2 <= 0)) {
/*  660 */       throw new IllegalArgumentException("Image dimensions must be positive (w,h > 0)");
/*      */     }
/*  662 */     initialize(Toolkit.getToolkit().createPlatformImage(paramInt1, paramInt2));
/*      */   }
/*      */ 
/*      */   private Image(Object paramObject) {
/*  666 */     this(null, null, 0.0D, 0.0D, false, false, false);
/*  667 */     initialize(paramObject);
/*      */   }
/*      */ 
/*      */   private Image(String paramString, InputStream paramInputStream, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*      */   {
/*  674 */     this.url = paramString;
/*  675 */     this.impl_source = paramInputStream;
/*  676 */     this.requestedWidth = paramDouble1;
/*  677 */     this.requestedHeight = paramDouble2;
/*  678 */     this.preserveRatio = paramBoolean1;
/*  679 */     this.smooth = paramBoolean2;
/*  680 */     this.backgroundLoading = paramBoolean3;
/*      */   }
/*      */ 
/*      */   public void cancel()
/*      */   {
/*  690 */     if (this.backgroundTask != null)
/*  691 */       this.backgroundTask.cancel();
/*      */   }
/*      */ 
/*      */   void dispose()
/*      */   {
/*  699 */     cancel();
/*  700 */     if (this.timeline != null)
/*  701 */       this.timeline.stop();
/*      */   }
/*      */ 
/*      */   private void initialize(Object paramObject)
/*      */   {
/*      */     ImageLoader localImageLoader;
/*  710 */     if (paramObject != null)
/*      */     {
/*  713 */       localImageLoader = loadPlatformImage(paramObject);
/*  714 */       finishImage(localImageLoader);
/*  715 */     } else if ((isBackgroundLoading()) && (this.impl_source == null))
/*      */     {
/*  717 */       loadInBackground();
/*      */     }
/*      */     else
/*      */     {
/*  721 */       if (this.impl_source != null) {
/*  722 */         localImageLoader = loadImage(this.impl_source, getRequestedWidth(), getRequestedHeight(), isPreserveRatio(), isSmooth());
/*      */       }
/*      */       else {
/*  725 */         localImageLoader = loadImage(impl_getUrl(), getRequestedWidth(), getRequestedHeight(), isPreserveRatio(), isSmooth());
/*      */       }
/*      */ 
/*  728 */       finishImage(localImageLoader);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void finishImage(ImageLoader paramImageLoader) {
/*  733 */     if ((paramImageLoader != null) && (!paramImageLoader.getError())) {
/*  734 */       if (paramImageLoader.getFrameCount() > 1)
/*  735 */         makeAnimationTimeline(paramImageLoader);
/*      */       else {
/*  737 */         setPlatformImageWH(paramImageLoader.getFrame(0), paramImageLoader.getWidth(), paramImageLoader.getHeight());
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  742 */       setError(true);
/*  743 */       setPlatformImageWH(null, 0.0D, 0.0D);
/*      */     }
/*      */ 
/*  746 */     setProgress(1.0D);
/*      */   }
/*      */ 
/*      */   private void makeAnimationTimeline(ImageLoader paramImageLoader)
/*      */   {
/*  755 */     PlatformImage[] arrayOfPlatformImage = paramImageLoader.getFrames();
/*      */ 
/*  757 */     this.timeline = new Timeline();
/*  758 */     this.timeline.setCycleCount(-1);
/*      */ 
/*  760 */     ObservableList localObservableList = this.timeline.getKeyFrames();
/*      */ 
/*  762 */     int i = 0;
/*  763 */     for (int j = 0; j < arrayOfPlatformImage.length; j++) {
/*  764 */       localObservableList.add(createPlatformImageSetKeyFrame(i, arrayOfPlatformImage[j]));
/*  765 */       i += paramImageLoader.getFrameDelay(j);
/*      */     }
/*      */ 
/*  770 */     localObservableList.add(createPlatformImageSetKeyFrame(i, arrayOfPlatformImage[0]));
/*      */ 
/*  772 */     setPlatformImageWH(arrayOfPlatformImage[0], paramImageLoader.getWidth(), paramImageLoader.getHeight());
/*  773 */     this.timeline.play();
/*      */   }
/*      */ 
/*      */   private KeyFrame createPlatformImageSetKeyFrame(long paramLong, final PlatformImage paramPlatformImage)
/*      */   {
/*  779 */     return new KeyFrame(Duration.millis(paramLong), new EventHandler()
/*      */     {
/*      */       public void handle(ActionEvent paramAnonymousActionEvent)
/*      */       {
/*  783 */         Image.this.platformImagePropertyImpl().set(paramPlatformImage);
/*      */       }
/*      */     }
/*      */     , new KeyValue[0]);
/*      */   }
/*      */ 
/*      */   private void cycleTasks()
/*      */   {
/*  790 */     synchronized (pendingTasks) {
/*  791 */       runningTasks -= 1;
/*      */ 
/*  795 */       ImageTask localImageTask = (ImageTask)pendingTasks.poll();
/*  796 */       if (localImageTask != null) {
/*  797 */         runningTasks += 1;
/*  798 */         localImageTask.start();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void loadInBackground() {
/*  804 */     this.backgroundTask = new ImageTask();
/*      */ 
/*  811 */     synchronized (pendingTasks) {
/*  812 */       if (runningTasks >= 4) {
/*  813 */         pendingTasks.offer(this.backgroundTask);
/*      */       } else {
/*  815 */         runningTasks += 1;
/*  816 */         this.backgroundTask.start();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public static Image impl_fromPlatformImage(Object paramObject)
/*      */   {
/*  831 */     return new Image(paramObject);
/*      */   }
/*      */ 
/*      */   private void setPlatformImageWH(PlatformImage paramPlatformImage, double paramDouble1, double paramDouble2)
/*      */   {
/*  837 */     if ((impl_getPlatformImage() == paramPlatformImage) && (getWidth() == paramDouble1) && (getHeight() == paramDouble2))
/*      */     {
/*  840 */       return;
/*      */     }
/*      */ 
/*  843 */     Object localObject = impl_getPlatformImage();
/*  844 */     double d1 = getWidth();
/*  845 */     double d2 = getHeight();
/*      */ 
/*  847 */     storePlatformImageWH(paramPlatformImage, paramDouble1, paramDouble2);
/*      */ 
/*  849 */     if (localObject != paramPlatformImage) {
/*  850 */       platformImagePropertyImpl().fireValueChangedEvent();
/*      */     }
/*      */ 
/*  853 */     if (d1 != paramDouble1) {
/*  854 */       widthPropertyImpl().fireValueChangedEvent();
/*      */     }
/*      */ 
/*  857 */     if (d2 != paramDouble2)
/*  858 */       heightPropertyImpl().fireValueChangedEvent();
/*      */   }
/*      */ 
/*      */   private void storePlatformImageWH(PlatformImage paramPlatformImage, double paramDouble1, double paramDouble2)
/*      */   {
/*  865 */     platformImagePropertyImpl().store(paramPlatformImage);
/*  866 */     widthPropertyImpl().store(paramDouble1);
/*  867 */     heightPropertyImpl().store(paramDouble2);
/*      */   }
/*      */ 
/*      */   void setPlatformImage(PlatformImage paramPlatformImage) {
/*  871 */     this.platformImage.set(paramPlatformImage);
/*      */   }
/*      */ 
/*      */   private static ImageLoader loadImage(String paramString, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  934 */     return Toolkit.getToolkit().loadImage(paramString, (int)paramDouble1, (int)paramDouble2, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */   private static ImageLoader loadImage(InputStream paramInputStream, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  942 */     return Toolkit.getToolkit().loadImage(paramInputStream, (int)paramDouble1, (int)paramDouble2, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */   private static AsyncOperation loadImageAsync(AsyncOperationListener<? extends ImageLoader> paramAsyncOperationListener, String paramString, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  951 */     return Toolkit.getToolkit().loadImageAsync(paramAsyncOperationListener, paramString, (int)paramDouble1, (int)paramDouble2, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */   private static ImageLoader loadPlatformImage(Object paramObject)
/*      */   {
/*  957 */     return Toolkit.getToolkit().loadPlatformImage(paramObject);
/*      */   }
/*      */ 
/*      */   private static String validateUrl(String paramString) {
/*  961 */     if (paramString == null) {
/*  962 */       throw new NullPointerException("URL must not be null");
/*      */     }
/*      */ 
/*  965 */     if (paramString.trim().isEmpty()) {
/*  966 */       throw new IllegalArgumentException("URL must not be empty");
/*      */     }
/*      */     try
/*      */     {
/*  970 */       if (!paramString.matches("^\\p{Alpha}[\\p{Alnum}+.-]*:.*$")) {
/*  971 */         ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
/*      */         URL localURL;
/*  973 */         if (paramString.charAt(0) == '/')
/*  974 */           localURL = localClassLoader.getResource(paramString.substring(1));
/*      */         else {
/*  976 */           localURL = localClassLoader.getResource(paramString);
/*      */         }
/*  978 */         if (localURL == null) {
/*  979 */           throw new IllegalArgumentException("Invalid URL or resource not found");
/*      */         }
/*  981 */         return localURL.toString();
/*      */       }
/*      */ 
/*  984 */       return new URL(paramString).toString();
/*      */     } catch (IllegalArgumentException localIllegalArgumentException) {
/*  986 */       throw new IllegalArgumentException(constructDetailedExceptionMessage("Invalid URL", localIllegalArgumentException), localIllegalArgumentException);
/*      */     }
/*      */     catch (MalformedURLException localMalformedURLException) {
/*  989 */       throw new IllegalArgumentException(constructDetailedExceptionMessage("Invalid URL", localMalformedURLException), localMalformedURLException);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static InputStream validateInputStream(InputStream paramInputStream)
/*      */   {
/*  996 */     if (paramInputStream == null) {
/*  997 */       throw new NullPointerException("Input stream must not be null");
/*      */     }
/*      */ 
/* 1000 */     return paramInputStream;
/*      */   }
/*      */ 
/*      */   private static String constructDetailedExceptionMessage(String paramString, Throwable paramThrowable)
/*      */   {
/* 1006 */     if (paramThrowable == null) {
/* 1007 */       return paramString;
/*      */     }
/*      */ 
/* 1010 */     String str = paramThrowable.getMessage();
/* 1011 */     return constructDetailedExceptionMessage(str != null ? paramString + ": " + str : paramString, paramThrowable.getCause());
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public Object impl_toExternalImage(Object paramObject)
/*      */   {
/* 1033 */     return Toolkit.getToolkit().toExternalImage(impl_getPlatformImage(), paramObject);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public static Image impl_fromExternalImage(Object paramObject)
/*      */   {
/* 1049 */     return impl_isExternalFormatSupported(paramObject.getClass()) ? new Image(paramObject) : null;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public static boolean impl_isExternalFormatSupported(Class paramClass)
/*      */   {
/* 1066 */     return Toolkit.getToolkit().isExternalFormatSupported(paramClass);
/*      */   }
/*      */ 
/*      */   boolean isAnimation()
/*      */   {
/* 1073 */     return this.timeline != null;
/*      */   }
/*      */ 
/*      */   boolean pixelsReadable() {
/* 1077 */     return (getProgress() >= 1.0D) && (!isAnimation()) && (!isError());
/*      */   }
/*      */ 
/*      */   public final PixelReader getPixelReader()
/*      */   {
/* 1096 */     if (!pixelsReadable()) {
/* 1097 */       return null;
/*      */     }
/* 1099 */     if (this.reader == null) {
/* 1100 */       this.reader = new PixelReader()
/*      */       {
/*      */         public PixelFormat getPixelFormat() {
/* 1103 */           PlatformImage localPlatformImage = (PlatformImage)Image.this.platformImage.get();
/* 1104 */           return localPlatformImage.getPlatformPixelFormat();
/*      */         }
/*      */ 
/*      */         public int getArgb(int paramAnonymousInt1, int paramAnonymousInt2)
/*      */         {
/* 1109 */           PlatformImage localPlatformImage = (PlatformImage)Image.this.platformImage.get();
/* 1110 */           return localPlatformImage.getArgb(paramAnonymousInt1, paramAnonymousInt2);
/*      */         }
/*      */ 
/*      */         public Color getColor(int paramAnonymousInt1, int paramAnonymousInt2)
/*      */         {
/* 1115 */           int i = getArgb(paramAnonymousInt1, paramAnonymousInt2);
/* 1116 */           int j = i >>> 24;
/* 1117 */           int k = i >> 16 & 0xFF;
/* 1118 */           int m = i >> 8 & 0xFF;
/* 1119 */           int n = i & 0xFF;
/* 1120 */           return Color.rgb(k, m, n, j / 255.0D);
/*      */         }
/*      */ 
/*      */         public <T extends Buffer> void getPixels(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, WritablePixelFormat<T> paramAnonymousWritablePixelFormat, T paramAnonymousT, int paramAnonymousInt5)
/*      */         {
/* 1129 */           PlatformImage localPlatformImage = (PlatformImage)Image.this.platformImage.get();
/* 1130 */           localPlatformImage.getPixels(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousWritablePixelFormat, paramAnonymousT, paramAnonymousInt5);
/*      */         }
/*      */ 
/*      */         public void getPixels(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, WritablePixelFormat<ByteBuffer> paramAnonymousWritablePixelFormat, byte[] paramAnonymousArrayOfByte, int paramAnonymousInt5, int paramAnonymousInt6)
/*      */         {
/* 1139 */           PlatformImage localPlatformImage = (PlatformImage)Image.this.platformImage.get();
/* 1140 */           localPlatformImage.getPixels(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousWritablePixelFormat, paramAnonymousArrayOfByte, paramAnonymousInt5, paramAnonymousInt6);
/*      */         }
/*      */ 
/*      */         public void getPixels(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, WritablePixelFormat<IntBuffer> paramAnonymousWritablePixelFormat, int[] paramAnonymousArrayOfInt, int paramAnonymousInt5, int paramAnonymousInt6)
/*      */         {
/* 1149 */           PlatformImage localPlatformImage = (PlatformImage)Image.this.platformImage.get();
/* 1150 */           localPlatformImage.getPixels(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousWritablePixelFormat, paramAnonymousArrayOfInt, paramAnonymousInt5, paramAnonymousInt6);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 1155 */     return this.reader;
/*      */   }
/*      */ 
/*      */   PlatformImage getWritablePlatformImage() {
/* 1159 */     PlatformImage localPlatformImage = (PlatformImage)this.platformImage.get();
/* 1160 */     if (!localPlatformImage.isWritable()) {
/* 1161 */       localPlatformImage = localPlatformImage.promoteToWritableImage();
/*      */ 
/* 1163 */       this.platformImage.set(localPlatformImage);
/*      */     }
/* 1165 */     return localPlatformImage;
/*      */   }
/*      */ 
/*      */   private final class ImageTask
/*      */     implements AsyncOperationListener<ImageLoader>
/*      */   {
/*      */     private final AsyncOperation peer;
/*      */ 
/*      */     public ImageTask()
/*      */     {
/*  885 */       this.peer = constructPeer();
/*      */     }
/*      */ 
/*      */     public void onCancel()
/*      */     {
/*  890 */       Image.this.finishImage(null);
/*  891 */       Image.this.cycleTasks();
/*      */     }
/*      */ 
/*      */     public void onException(Exception paramException)
/*      */     {
/*  896 */       Image.this.finishImage(null);
/*  897 */       Image.this.cycleTasks();
/*      */     }
/*      */ 
/*      */     public void onCompletion(ImageLoader paramImageLoader)
/*      */     {
/*  902 */       Image.this.finishImage(paramImageLoader);
/*  903 */       Image.this.cycleTasks();
/*      */     }
/*      */ 
/*      */     public void onProgress(int paramInt1, int paramInt2)
/*      */     {
/*  908 */       if (paramInt2 > 0) {
/*  909 */         double d = paramInt1 / paramInt2;
/*  910 */         if ((d < 1.0D) && (d >= Image.this.getProgress() + 0.1D))
/*  911 */           Image.this.setProgress(d);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void start()
/*      */     {
/*  917 */       this.peer.start();
/*      */     }
/*      */ 
/*      */     public void cancel() {
/*  921 */       this.peer.cancel();
/*      */     }
/*      */ 
/*      */     private AsyncOperation constructPeer() {
/*  925 */       return Image.loadImageAsync(this, Image.this.url, Image.this.requestedWidth, Image.this.requestedHeight, Image.this.preserveRatio, Image.this.smooth);
/*      */     }
/*      */   }
/*      */ 
/*      */   private final class ObjectPropertyImpl<T> extends ReadOnlyObjectPropertyBase<T>
/*      */   {
/*      */     private final String name;
/*      */     private T value;
/*      */ 
/*      */     public ObjectPropertyImpl(String arg2)
/*      */     {
/*      */       Object localObject;
/*  492 */       this.name = localObject;
/*      */     }
/*      */ 
/*      */     public void store(T paramT) {
/*  496 */       this.value = paramT;
/*      */     }
/*      */ 
/*      */     public void set(T paramT) {
/*  500 */       if (this.value != paramT) {
/*  501 */         this.value = paramT;
/*  502 */         fireValueChangedEvent();
/*      */       }
/*      */     }
/*      */ 
/*      */     public void fireValueChangedEvent()
/*      */     {
/*  508 */       super.fireValueChangedEvent();
/*      */     }
/*      */ 
/*      */     public T get()
/*      */     {
/*  513 */       return this.value;
/*      */     }
/*      */ 
/*      */     public Object getBean()
/*      */     {
/*  518 */       return Image.this;
/*      */     }
/*      */ 
/*      */     public String getName()
/*      */     {
/*  523 */       return this.name;
/*      */     }
/*      */   }
/*      */ 
/*      */   private final class DoublePropertyImpl extends ReadOnlyDoublePropertyBase
/*      */   {
/*      */     private final String name;
/*      */     private double value;
/*      */ 
/*      */     public DoublePropertyImpl(String arg2)
/*      */     {
/*      */       Object localObject;
/*  250 */       this.name = localObject;
/*      */     }
/*      */ 
/*      */     public void store(double paramDouble) {
/*  254 */       this.value = paramDouble;
/*      */     }
/*      */ 
/*      */     public void fireValueChangedEvent()
/*      */     {
/*  259 */       super.fireValueChangedEvent();
/*      */     }
/*      */ 
/*      */     public double get()
/*      */     {
/*  264 */       return this.value;
/*      */     }
/*      */ 
/*      */     public Object getBean()
/*      */     {
/*  269 */       return Image.this;
/*      */     }
/*      */ 
/*      */     public String getName()
/*      */     {
/*  274 */       return this.name;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.image.Image
 * JD-Core Version:    0.6.2
 */