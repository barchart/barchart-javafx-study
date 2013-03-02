/*     */ package javafx.scene.media;
/*     */ 
/*     */ import com.sun.media.jfxmedia.MediaManager;
/*     */ import com.sun.media.jfxmedia.MetadataParser;
/*     */ import com.sun.media.jfxmedia.events.MetadataListener;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmedia.track.VideoResolution;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javafx.application.Platform;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerWrapper;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.scene.image.Image;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class Media
/*     */ {
/*     */   private ReadOnlyObjectWrapper<MediaException> error;
/*     */   private ObjectProperty<Runnable> onError;
/* 160 */   private MetadataListener metadataListener = new _MetadataListener(null);
/*     */   private ObservableMap<String, Object> metadata;
/*     */   private final ObservableMap<String, Object> metadataBacking;
/*     */   private ReadOnlyIntegerWrapper width;
/*     */   private ReadOnlyIntegerWrapper height;
/*     */   private ReadOnlyObjectWrapper<Duration> duration;
/*     */   private ObservableList<Track> tracks;
/*     */   private final ObservableList<Track> tracksBacking;
/*     */   private ObservableMap<String, Duration> markers;
/*     */   private final String source;
/*     */   private final Locator jfxLocator;
/*     */   private MetadataParser jfxParser;
/*     */ 
/*     */   private void setError(MediaException paramMediaException)
/*     */   {
/*  68 */     errorPropertyImpl().set(paramMediaException);
/*     */   }
/*     */ 
/*     */   public final MediaException getError()
/*     */   {
/*  76 */     return this.error == null ? null : (MediaException)this.error.get();
/*     */   }
/*     */ 
/*     */   public ReadOnlyObjectProperty<MediaException> errorProperty() {
/*  80 */     return errorPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyObjectWrapper<MediaException> errorPropertyImpl() {
/*  84 */     if (this.error == null) {
/*  85 */       this.error = new ReadOnlyObjectWrapper()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/*  89 */           if (Media.this.getOnError() != null)
/*  90 */             Platform.runLater(Media.this.getOnError());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/*  96 */           return Media.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 101 */           return "error";
/*     */         }
/*     */       };
/*     */     }
/* 105 */     return this.error;
/*     */   }
/*     */ 
/*     */   public final void setOnError(Runnable paramRunnable)
/*     */   {
/* 119 */     onErrorProperty().set(paramRunnable);
/*     */   }
/*     */ 
/*     */   public final Runnable getOnError()
/*     */   {
/* 127 */     return this.onError == null ? null : (Runnable)this.onError.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<Runnable> onErrorProperty() {
/* 131 */     if (this.onError == null) {
/* 132 */       this.onError = new ObjectPropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 141 */           if ((get() != null) && (Media.this.getError() != null))
/* 142 */             Platform.runLater((Runnable)get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 148 */           return Media.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 153 */           return "onError";
/*     */         }
/*     */       };
/*     */     }
/* 157 */     return this.onError;
/*     */   }
/*     */ 
/*     */   public final ObservableMap<String, Object> getMetadata()
/*     */   {
/* 179 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   final void setWidth(int paramInt)
/*     */   {
/* 194 */     widthPropertyImpl().set(paramInt);
/*     */   }
/*     */ 
/*     */   public final int getWidth()
/*     */   {
/* 202 */     return this.width == null ? 0 : this.width.get();
/*     */   }
/*     */ 
/*     */   public ReadOnlyIntegerProperty widthProperty() {
/* 206 */     return widthPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyIntegerWrapper widthPropertyImpl() {
/* 210 */     if (this.width == null) {
/* 211 */       this.width = new ReadOnlyIntegerWrapper(this, "width");
/*     */     }
/* 213 */     return this.width;
/*     */   }
/*     */ 
/*     */   final void setHeight(int paramInt)
/*     */   {
/* 226 */     heightPropertyImpl().set(paramInt);
/*     */   }
/*     */ 
/*     */   public final int getHeight()
/*     */   {
/* 234 */     return this.height == null ? 0 : this.height.get();
/*     */   }
/*     */ 
/*     */   public ReadOnlyIntegerProperty heightProperty() {
/* 238 */     return heightPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyIntegerWrapper heightPropertyImpl() {
/* 242 */     if (this.height == null) {
/* 243 */       this.height = new ReadOnlyIntegerWrapper(this, "height");
/*     */     }
/* 245 */     return this.height;
/*     */   }
/*     */ 
/*     */   final void setDuration(Duration paramDuration)
/*     */   {
/* 254 */     durationPropertyImpl().set(paramDuration);
/*     */   }
/*     */ 
/*     */   public final Duration getDuration()
/*     */   {
/* 262 */     return this.duration == null ? Duration.UNKNOWN : (Duration)this.duration.get();
/*     */   }
/*     */ 
/*     */   public ReadOnlyObjectProperty<Duration> durationProperty() {
/* 266 */     return durationPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyObjectWrapper<Duration> durationPropertyImpl() {
/* 270 */     if (this.duration == null) {
/* 271 */       this.duration = new ReadOnlyObjectWrapper(this, "duration");
/*     */     }
/* 273 */     return this.duration;
/*     */   }
/*     */ 
/*     */   public final ObservableList<Track> getTracks()
/*     */   {
/* 290 */     return this.tracks;
/*     */   }
/*     */ 
/*     */   public final ObservableMap<String, Duration> getMarkers()
/*     */   {
/* 310 */     return this.markers;
/*     */   }
/*     */ 
/*     */   public Media(String paramString)
/*     */   {
/* 354 */     URI localURI = null;
/*     */     try
/*     */     {
/* 357 */       localURI = new URI(paramString);
/*     */     } catch (URISyntaxException localURISyntaxException1) {
/* 359 */       throw new IllegalArgumentException(localURISyntaxException1);
/*     */     }
/*     */ 
/* 362 */     Locator localLocator = null;
/*     */     try {
/* 364 */       localLocator = new Locator(localURI);
/* 365 */       this.jfxLocator = localLocator;
/* 366 */       if (localLocator.canBlock()) {
/* 367 */         InitLocator localInitLocator = new InitLocator(null);
/* 368 */         Thread localThread = new Thread(localInitLocator);
/* 369 */         localThread.setDaemon(true);
/* 370 */         localThread.start();
/*     */       } else {
/* 372 */         localLocator.init();
/* 373 */         runMetadataParser();
/*     */       }
/*     */     } catch (URISyntaxException localURISyntaxException2) {
/* 376 */       throw new IllegalArgumentException(localURISyntaxException2);
/*     */     } catch (FileNotFoundException localFileNotFoundException) {
/* 378 */       throw new MediaException(MediaException.Type.MEDIA_UNAVAILABLE, localFileNotFoundException.getMessage());
/*     */     } catch (IOException localIOException) {
/* 380 */       throw new MediaException(MediaException.Type.MEDIA_INACCESSIBLE, localIOException.getMessage());
/*     */     } catch (com.sun.media.jfxmedia.MediaException localMediaException) {
/* 382 */       throw new MediaException(MediaException.Type.MEDIA_UNSUPPORTED, localMediaException.getMessage());
/*     */     }
/*     */ 
/* 385 */     this.source = paramString;
/*     */ 
/* 387 */     this.metadataBacking = FXCollections.observableMap(new HashMap());
/* 388 */     this.metadata = FXCollections.unmodifiableObservableMap(this.metadataBacking);
/*     */ 
/* 390 */     this.tracksBacking = FXCollections.observableArrayList();
/* 391 */     this.tracks = FXCollections.unmodifiableObservableList(this.tracksBacking);
/*     */ 
/* 393 */     this.markers = FXCollections.observableMap(new HashMap());
/*     */   }
/*     */ 
/*     */   private void runMetadataParser()
/*     */   {
/* 398 */     MetadataParser localMetadataParser = null;
/*     */     try {
/* 400 */       localMetadataParser = MediaManager.getMetadataParser(this.jfxLocator);
/* 401 */       localMetadataParser.addListener(this.metadataListener);
/* 402 */       localMetadataParser.startParser();
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 406 */     this.jfxParser = localMetadataParser;
/*     */   }
/*     */ 
/*     */   public String getSource()
/*     */   {
/* 419 */     return this.source;
/*     */   }
/*     */ 
/*     */   Locator retrieveJfxLocator()
/*     */   {
/* 427 */     return this.jfxLocator;
/*     */   }
/*     */ 
/*     */   void _updateMedia(com.sun.media.jfxmedia.Media paramMedia)
/*     */   {
/*     */     try
/*     */     {
/* 434 */       List localList = paramMedia.getTracks();
/*     */ 
/* 436 */       if (localList != null) {
/* 437 */         localHashSet = new HashSet();
/*     */ 
/* 439 */         for (com.sun.media.jfxmedia.track.Track localTrack : localList)
/*     */         {
/*     */           Object localObject1;
/*     */           Object localObject2;
/* 440 */           if ((localTrack instanceof com.sun.media.jfxmedia.track.VideoTrack)) {
/* 441 */             localObject1 = (com.sun.media.jfxmedia.track.VideoTrack)localTrack;
/*     */ 
/* 443 */             setWidth(((com.sun.media.jfxmedia.track.VideoTrack)localObject1).getFrameSize().getWidth());
/* 444 */             setHeight(((com.sun.media.jfxmedia.track.VideoTrack)localObject1).getFrameSize().getHeight());
/* 445 */             localObject2 = new VideoTrack((com.sun.media.jfxmedia.track.VideoTrack)localObject1);
/* 446 */             localHashSet.add(localObject2);
/* 447 */           } else if ((localTrack instanceof com.sun.media.jfxmedia.track.AudioTrack)) {
/* 448 */             localObject1 = (com.sun.media.jfxmedia.track.AudioTrack)localTrack;
/*     */ 
/* 450 */             localObject2 = new AudioTrack((com.sun.media.jfxmedia.track.AudioTrack)localObject1);
/* 451 */             localHashSet.add(localObject2);
/*     */           }
/*     */ 
/* 460 */           this.tracksBacking.setAll(localHashSet);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */       HashSet localHashSet;
/* 465 */       setError(new MediaException(MediaException.Type.UNKNOWN, localException));
/*     */     }
/*     */   }
/*     */ 
/*     */   void _setError(MediaException.Type paramType, String paramString) {
/* 470 */     setError(new MediaException(paramType, paramString));
/*     */   }
/*     */ 
/*     */   private synchronized void updateMetadata(Map<String, Object> paramMap) {
/* 474 */     if (paramMap != null)
/* 475 */       for (String str : paramMap.keySet()) {
/* 476 */         Object localObject1 = paramMap.get(str);
/*     */         Object localObject2;
/* 477 */         if ((str.equals("image")) && ((localObject1 instanceof byte[]))) {
/* 478 */           localObject2 = (byte[])localObject1;
/* 479 */           Image localImage = new Image(new ByteArrayInputStream((byte[])localObject2));
/* 480 */           if (!localImage.isError())
/* 481 */             this.metadataBacking.put("image", localImage);
/*     */         }
/* 483 */         else if ((str.equals("duration")) && ((localObject1 instanceof Long))) {
/* 484 */           localObject2 = new Duration(((Long)localObject1).longValue());
/* 485 */           if (localObject2 != null)
/* 486 */             this.metadataBacking.put("duration", localObject2);
/*     */         }
/*     */         else {
/* 489 */           this.metadataBacking.put(str, localObject1);
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   private class InitLocator
/*     */     implements Runnable
/*     */   {
/*     */     private InitLocator()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/*     */       try
/*     */       {
/* 516 */         Media.this.jfxLocator.init();
/* 517 */         Media.this.runMetadataParser();
/*     */       } catch (URISyntaxException localURISyntaxException) {
/* 519 */         Media.this._setError(MediaException.Type.OPERATION_UNSUPPORTED, localURISyntaxException.getMessage());
/*     */       } catch (FileNotFoundException localFileNotFoundException) {
/* 521 */         Media.this._setError(MediaException.Type.MEDIA_UNAVAILABLE, localFileNotFoundException.getMessage());
/*     */       } catch (IOException localIOException) {
/* 523 */         Media.this._setError(MediaException.Type.MEDIA_INACCESSIBLE, localIOException.getMessage());
/*     */       } catch (com.sun.media.jfxmedia.MediaException localMediaException) {
/* 525 */         Media.this._setError(MediaException.Type.MEDIA_UNSUPPORTED, localMediaException.getMessage());
/*     */       } catch (Exception localException) {
/* 527 */         Media.this._setError(MediaException.Type.UNKNOWN, localException.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private class _MetadataListener
/*     */     implements MetadataListener
/*     */   {
/*     */     private _MetadataListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void onMetadata(final Map<String, Object> paramMap)
/*     */     {
/* 499 */       Platform.runLater(new Runnable()
/*     */       {
/*     */         public void run() {
/* 502 */           Media.this.updateMetadata(paramMap);
/* 503 */           Media.this.jfxParser.removeListener(Media.this.metadataListener);
/* 504 */           Media.this.jfxParser.stopParser();
/* 505 */           Media.this.jfxParser = null;
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.Media
 * JD-Core Version:    0.6.2
 */