/*     */ package javafx.scene.media;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.jmx.MXNodeAlgorithm;
/*     */ import com.sun.javafx.jmx.MXNodeAlgorithmContext;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.sg.PGMediaView;
/*     */ import com.sun.javafx.sg.PGMediaView.MediaFrameTracker;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.media.jfxmedia.control.VideoRenderControl;
/*     */ import com.sun.media.jfxmedia.events.VideoFrameRateListener;
/*     */ import javafx.application.Platform;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.value.ObservableObjectValue;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class MediaView extends Node
/*     */ {
/*     */   private static final String VIDEO_FRAME_RATE_PROPERTY_NAME = "jfxmedia.decodedVideoFPS";
/*  89 */   private InvalidationListener errorListener = new MediaErrorInvalidationListener(null);
/*     */ 
/*  92 */   private InvalidationListener mediaDimensionListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/*  94 */       MediaView.this.impl_markDirty(DirtyBits.NODE_VIEWPORT);
/*  95 */       MediaView.this.impl_geomChanged(); }  } ;
/*     */   private VideoFrameRateListener decodedFrameRateListener;
/* 101 */   private boolean registerVideoFrameRateListener = false;
/*     */   private ObjectProperty<MediaPlayer> mediaPlayer;
/*     */   private ObjectProperty<EventHandler<MediaErrorEvent>> onError;
/*     */   private BooleanProperty preserveRatio;
/*     */   private BooleanProperty smooth;
/*     */   private DoubleProperty x;
/*     */   private DoubleProperty y;
/*     */   private DoubleProperty fitWidth;
/*     */   private DoubleProperty fitHeight;
/*     */   private ObjectProperty<Rectangle2D> viewport;
/*     */   private int decodedFrameCount;
/*     */   private int renderedFrameCount;
/*     */ 
/* 108 */   private VideoFrameRateListener createVideoFrameRateListener() { String str = null;
/*     */     try {
/* 110 */       str = System.getProperty("jfxmedia.decodedVideoFPS");
/*     */     }
/*     */     catch (Throwable localThrowable) {
/*     */     }
/* 114 */     if ((str == null) || (!Boolean.getBoolean("jfxmedia.decodedVideoFPS"))) {
/* 115 */       return null;
/*     */     }
/* 117 */     return new VideoFrameRateListener()
/*     */     {
/*     */       public void onFrameRateChanged(final double paramAnonymousDouble)
/*     */       {
/* 121 */         Platform.runLater(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 125 */             ObservableMap localObservableMap = MediaView.this.getProperties();
/* 126 */             localObservableMap.put("jfxmedia.decodedVideoFPS", Double.valueOf(paramAnonymousDouble));
/*     */           }
/*     */         });
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   private MediaView getMediaView()
/*     */   {
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaView()
/*     */   {
/* 146 */     setSmooth(Toolkit.getToolkit().getDefaultImageSmooth());
/* 147 */     this.decodedFrameRateListener = createVideoFrameRateListener();
/*     */   }
/*     */ 
/*     */   public MediaView(MediaPlayer paramMediaPlayer)
/*     */   {
/* 163 */     setSmooth(Toolkit.getToolkit().getDefaultImageSmooth());
/* 164 */     this.decodedFrameRateListener = createVideoFrameRateListener();
/*     */ 
/* 166 */     setMediaPlayer(paramMediaPlayer);
/*     */   }
/*     */ 
/*     */   public final void setMediaPlayer(MediaPlayer paramMediaPlayer)
/*     */   {
/* 185 */     mediaPlayerProperty().set(paramMediaPlayer);
/*     */   }
/*     */ 
/*     */   public final MediaPlayer getMediaPlayer()
/*     */   {
/* 194 */     return this.mediaPlayer == null ? null : (MediaPlayer)this.mediaPlayer.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<MediaPlayer> mediaPlayerProperty() {
/* 198 */     if (this.mediaPlayer == null) {
/* 199 */       this.mediaPlayer = new ObjectPropertyBase() {
/* 200 */         MediaPlayer oldValue = null;
/*     */ 
/* 202 */         protected void invalidated() { if (this.oldValue != null) {
/* 203 */             localObject = this.oldValue.getMedia();
/* 204 */             if (localObject != null) {
/* 205 */               ((Media)localObject).widthProperty().removeListener(MediaView.this.mediaDimensionListener);
/* 206 */               ((Media)localObject).heightProperty().removeListener(MediaView.this.mediaDimensionListener);
/*     */             }
/* 208 */             if ((MediaView.this.decodedFrameRateListener != null) && (MediaView.this.getMediaPlayer().retrieveJfxPlayer() != null)) {
/* 209 */               MediaView.this.getMediaPlayer().retrieveJfxPlayer().getVideoRenderControl().removeVideoFrameRateListener(MediaView.this.decodedFrameRateListener);
/*     */             }
/* 211 */             this.oldValue.errorProperty().removeListener(MediaView.this.errorListener);
/* 212 */             this.oldValue.removeView(MediaView.this.getMediaView());
/*     */           }
/*     */ 
/* 219 */           Object localObject = (MediaPlayer)get();
/* 220 */           if (localObject != null) {
/* 221 */             ((MediaPlayer)localObject).addView(MediaView.this.getMediaView());
/* 222 */             ((MediaPlayer)localObject).errorProperty().addListener(MediaView.this.errorListener);
/* 223 */             if ((MediaView.this.decodedFrameRateListener != null) && (MediaView.this.getMediaPlayer().retrieveJfxPlayer() != null))
/* 224 */               MediaView.this.getMediaPlayer().retrieveJfxPlayer().getVideoRenderControl().addVideoFrameRateListener(MediaView.this.decodedFrameRateListener);
/* 225 */             else if (MediaView.this.decodedFrameRateListener != null) {
/* 226 */               MediaView.this.registerVideoFrameRateListener = true;
/*     */             }
/* 228 */             Media localMedia = ((MediaPlayer)localObject).getMedia();
/* 229 */             if (localMedia != null) {
/* 230 */               localMedia.widthProperty().addListener(MediaView.this.mediaDimensionListener);
/* 231 */               localMedia.heightProperty().addListener(MediaView.this.mediaDimensionListener);
/*     */             }
/*     */           }
/* 234 */           MediaView.this.impl_markDirty(DirtyBits.MEDIAVIEW_MEDIA);
/* 235 */           MediaView.this.impl_geomChanged();
/* 236 */           this.oldValue = ((MediaPlayer)localObject); }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 240 */           return MediaView.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 245 */           return "mediaPlayer";
/*     */         }
/*     */       };
/*     */     }
/* 249 */     return this.mediaPlayer;
/*     */   }
/*     */ 
/*     */   public final void setOnError(EventHandler<MediaErrorEvent> paramEventHandler)
/*     */   {
/* 264 */     onErrorProperty().set(paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final EventHandler<MediaErrorEvent> getOnError()
/*     */   {
/* 272 */     return this.onError == null ? null : (EventHandler)this.onError.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventHandler<MediaErrorEvent>> onErrorProperty() {
/* 276 */     if (this.onError == null) {
/* 277 */       this.onError = new ObjectPropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 281 */           MediaView.this.setEventHandler(MediaErrorEvent.MEDIA_ERROR, (EventHandler)get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 286 */           return MediaView.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 291 */           return "onError";
/*     */         }
/*     */       };
/*     */     }
/* 295 */     return this.onError;
/*     */   }
/*     */ 
/*     */   public final void setPreserveRatio(boolean paramBoolean)
/*     */   {
/* 310 */     preserveRatioProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isPreserveRatio()
/*     */   {
/* 318 */     return this.preserveRatio == null ? true : this.preserveRatio.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty preserveRatioProperty() {
/* 322 */     if (this.preserveRatio == null) {
/* 323 */       this.preserveRatio = new BooleanPropertyBase(true)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 327 */           MediaView.this.impl_markDirty(DirtyBits.NODE_VIEWPORT);
/* 328 */           MediaView.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 333 */           return MediaView.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 338 */           return "preserveRatio";
/*     */         }
/*     */       };
/*     */     }
/* 342 */     return this.preserveRatio;
/*     */   }
/*     */ 
/*     */   public final void setSmooth(boolean paramBoolean)
/*     */   {
/* 362 */     smoothProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isSmooth()
/*     */   {
/* 370 */     return this.smooth == null ? false : this.smooth.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty smoothProperty() {
/* 374 */     if (this.smooth == null) {
/* 375 */       this.smooth = new BooleanPropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 379 */           MediaView.this.impl_markDirty(DirtyBits.NODE_SMOOTH);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 384 */           return MediaView.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 389 */           return "smooth";
/*     */         }
/*     */       };
/*     */     }
/* 393 */     return this.smooth;
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/* 406 */     xProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/* 414 */     return this.x == null ? 0.0D : this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/* 418 */     if (this.x == null) {
/* 419 */       this.x = new DoublePropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 423 */           MediaView.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 424 */           MediaView.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 429 */           return MediaView.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 434 */           return "x";
/*     */         }
/*     */       };
/*     */     }
/* 438 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/* 451 */     yProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 459 */     return this.y == null ? 0.0D : this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 463 */     if (this.y == null) {
/* 464 */       this.y = new DoublePropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 468 */           MediaView.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 469 */           MediaView.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 474 */           return MediaView.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 479 */           return "y";
/*     */         }
/*     */       };
/*     */     }
/* 483 */     return this.y;
/*     */   }
/*     */ 
/*     */   public final void setFitWidth(double paramDouble)
/*     */   {
/* 503 */     fitWidthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getFitWidth()
/*     */   {
/* 511 */     return this.fitWidth == null ? 0.0D : this.fitWidth.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty fitWidthProperty() {
/* 515 */     if (this.fitWidth == null) {
/* 516 */       this.fitWidth = new DoublePropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 520 */           MediaView.this.impl_markDirty(DirtyBits.NODE_VIEWPORT);
/* 521 */           MediaView.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 526 */           return MediaView.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 531 */           return "fitWidth";
/*     */         }
/*     */       };
/*     */     }
/* 535 */     return this.fitWidth;
/*     */   }
/*     */ 
/*     */   public final void setFitHeight(double paramDouble)
/*     */   {
/* 555 */     fitHeightProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getFitHeight()
/*     */   {
/* 563 */     return this.fitHeight == null ? 0.0D : this.fitHeight.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty fitHeightProperty() {
/* 567 */     if (this.fitHeight == null) {
/* 568 */       this.fitHeight = new DoublePropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 572 */           MediaView.this.impl_markDirty(DirtyBits.NODE_VIEWPORT);
/* 573 */           MediaView.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 578 */           return MediaView.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 583 */           return "fitHeight";
/*     */         }
/*     */       };
/*     */     }
/* 587 */     return this.fitHeight;
/*     */   }
/*     */ 
/*     */   public final void setViewport(Rectangle2D paramRectangle2D)
/*     */   {
/* 606 */     viewportProperty().set(paramRectangle2D);
/*     */   }
/*     */ 
/*     */   public final Rectangle2D getViewport()
/*     */   {
/* 614 */     return this.viewport == null ? null : (Rectangle2D)this.viewport.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Rectangle2D> viewportProperty() {
/* 618 */     if (this.viewport == null) {
/* 619 */       this.viewport = new ObjectPropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 623 */           MediaView.this.impl_markDirty(DirtyBits.NODE_VIEWPORT);
/* 624 */           MediaView.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 629 */           return MediaView.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 634 */           return "viewport";
/*     */         }
/*     */       };
/*     */     }
/* 638 */     return this.viewport;
/*     */   }
/*     */ 
/*     */   void notifyMediaChange() {
/* 642 */     MediaPlayer localMediaPlayer = getMediaPlayer();
/* 643 */     if (localMediaPlayer != null) {
/* 644 */       getPGMediaView().setMediaProvider(localMediaPlayer);
/*     */     }
/* 646 */     impl_markDirty(DirtyBits.MEDIAVIEW_MEDIA);
/* 647 */     impl_geomChanged();
/*     */   }
/*     */ 
/*     */   void notifyMediaSizeChange() {
/* 651 */     impl_markDirty(DirtyBits.NODE_VIEWPORT);
/* 652 */     impl_geomChanged();
/*     */   }
/*     */ 
/*     */   void notifyMediaFrameUpdated() {
/* 656 */     this.decodedFrameCount += 1;
/* 657 */     impl_markDirty(DirtyBits.NODE_CONTENTS);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 667 */     PGMediaView localPGMediaView = Toolkit.getToolkit().createPGMediaView();
/*     */ 
/* 669 */     localPGMediaView.setFrameTracker(new MediaViewFrameTracker(null));
/* 670 */     return localPGMediaView;
/*     */   }
/*     */ 
/*     */   PGMediaView getPGMediaView() {
/* 674 */     return (PGMediaView)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_computeGeomBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*     */   {
/* 686 */     Media localMedia = getMediaPlayer() == null ? null : getMediaPlayer().getMedia();
/* 687 */     double d1 = localMedia != null ? localMedia.getWidth() : 0.0D;
/* 688 */     double d2 = localMedia != null ? localMedia.getHeight() : 0.0D;
/* 689 */     double d3 = getFitWidth();
/* 690 */     double d4 = getFitHeight();
/* 691 */     double d5 = getViewport() != null ? getViewport().getWidth() : 0.0D;
/* 692 */     double d6 = getViewport() != null ? getViewport().getHeight() : 0.0D;
/*     */ 
/* 694 */     if ((d5 > 0.0D) && (d6 > 0.0D)) {
/* 695 */       d1 = d5;
/* 696 */       d2 = d6;
/*     */     }
/*     */ 
/* 699 */     if ((getFitWidth() <= 0.0D) && (getFitHeight() <= 0.0D)) {
/* 700 */       d3 = d1;
/* 701 */       d4 = d2;
/* 702 */     } else if (isPreserveRatio()) {
/* 703 */       if (getFitWidth() <= 0.0D) {
/* 704 */         d3 = d2 > 0.0D ? d1 * (getFitHeight() / d2) : 0.0D;
/* 705 */         d4 = getFitHeight();
/* 706 */       } else if (getFitHeight() <= 0.0D) {
/* 707 */         d3 = getFitWidth();
/* 708 */         d4 = d1 > 0.0D ? d2 * (getFitWidth() / d1) : 0.0D;
/*     */       } else {
/* 710 */         if (d1 == 0.0D) d1 = getFitWidth();
/* 711 */         if (d2 == 0.0D) d2 = getFitHeight();
/* 712 */         double d7 = Math.min(getFitWidth() / d1, getFitHeight() / d2);
/* 713 */         d3 = d1 * d7;
/* 714 */         d4 = d2 * d7;
/*     */       }
/* 716 */     } else if (getFitHeight() <= 0.0D) {
/* 717 */       d4 = d2;
/* 718 */     } else if (getFitWidth() <= 0.0D) {
/* 719 */       d3 = d1;
/*     */     }
/* 721 */     if (d4 < 1.0D) {
/* 722 */       d4 = 1.0D;
/*     */     }
/* 724 */     if (d3 < 1.0D) {
/* 725 */       d3 = 1.0D;
/*     */     }
/*     */ 
/* 728 */     d1 = d3;
/* 729 */     d2 = d4;
/*     */ 
/* 733 */     if ((d1 <= 0.0D) || (d2 <= 0.0D)) {
/* 734 */       return paramBaseBounds.makeEmpty();
/*     */     }
/* 736 */     paramBaseBounds = paramBaseBounds.deriveWithNewBounds((float)getX(), (float)getY(), 0.0F, (float)(getX() + d1), (float)(getY() + d2), 0.0F);
/*     */ 
/* 738 */     paramBaseBounds = paramBaseTransform.transform(paramBaseBounds, paramBaseBounds);
/* 739 */     return paramBaseBounds;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected boolean impl_computeContains(double paramDouble1, double paramDouble2)
/*     */   {
/* 751 */     return true;
/*     */   }
/*     */ 
/*     */   void updateViewport()
/*     */   {
/* 756 */     if (getMediaPlayer() == null) {
/* 757 */       return;
/*     */     }
/*     */ 
/* 760 */     if (getViewport() != null) {
/* 761 */       getPGMediaView().setViewport((float)getFitWidth(), (float)getFitHeight(), (float)getViewport().getMinX(), (float)getViewport().getMinY(), (float)getViewport().getWidth(), (float)getViewport().getHeight(), isPreserveRatio());
/*     */     }
/*     */     else
/*     */     {
/* 766 */       getPGMediaView().setViewport((float)getFitWidth(), (float)getFitHeight(), 0.0F, 0.0F, 0.0F, 0.0F, isPreserveRatio());
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 780 */     super.impl_updatePG();
/*     */     Object localObject;
/* 782 */     if (impl_isDirty(DirtyBits.NODE_GEOMETRY)) {
/* 783 */       localObject = getPGMediaView();
/* 784 */       ((PGMediaView)localObject).setX((float)getX());
/* 785 */       ((PGMediaView)localObject).setY((float)getY());
/*     */     }
/* 787 */     if (impl_isDirty(DirtyBits.NODE_SMOOTH)) {
/* 788 */       getPGMediaView().setSmooth(isSmooth());
/*     */     }
/* 790 */     if (impl_isDirty(DirtyBits.NODE_VIEWPORT)) {
/* 791 */       updateViewport();
/*     */     }
/* 793 */     if (impl_isDirty(DirtyBits.NODE_CONTENTS)) {
/* 794 */       getPGMediaView().renderNextFrame();
/*     */     }
/* 796 */     if (impl_isDirty(DirtyBits.MEDIAVIEW_MEDIA)) {
/* 797 */       localObject = getMediaPlayer();
/* 798 */       if (localObject != null) {
/* 799 */         getPGMediaView().setMediaProvider(localObject);
/* 800 */         updateViewport();
/*     */       } else {
/* 802 */         getPGMediaView().setMediaProvider(null);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_perfReset()
/*     */   {
/* 817 */     this.decodedFrameCount = 0;
/* 818 */     this.renderedFrameCount = 0;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public int impl_perfGetDecodedFrameCount()
/*     */   {
/* 828 */     return this.decodedFrameCount;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public int impl_perfGetRenderedFrameCount()
/*     */   {
/* 838 */     return this.renderedFrameCount;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Object impl_processMXNode(MXNodeAlgorithm paramMXNodeAlgorithm, MXNodeAlgorithmContext paramMXNodeAlgorithmContext)
/*     */   {
/* 859 */     return paramMXNodeAlgorithm.processLeafNode(this, paramMXNodeAlgorithmContext);
/*     */   }
/*     */ 
/*     */   void _mediaPlayerOnReady()
/*     */   {
/* 866 */     if ((this.decodedFrameRateListener != null) && (getMediaPlayer().retrieveJfxPlayer() != null) && (this.registerVideoFrameRateListener)) {
/* 867 */       getMediaPlayer().retrieveJfxPlayer().getVideoRenderControl().addVideoFrameRateListener(this.decodedFrameRateListener);
/* 868 */       this.registerVideoFrameRateListener = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class MediaViewFrameTracker
/*     */     implements PGMediaView.MediaFrameTracker
/*     */   {
/*     */     private MediaViewFrameTracker()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void incrementDecodedFrameCount(int paramInt)
/*     */     {
/* 844 */       MediaView.access$2512(MediaView.this, paramInt);
/*     */     }
/*     */ 
/*     */     public void incrementRenderedFrameCount(int paramInt)
/*     */     {
/* 849 */       MediaView.access$2612(MediaView.this, paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class MediaErrorInvalidationListener
/*     */     implements InvalidationListener
/*     */   {
/*     */     private MediaErrorInvalidationListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void invalidated(Observable paramObservable)
/*     */     {
/*  83 */       ObservableObjectValue localObservableObjectValue = (ObservableObjectValue)paramObservable;
/*  84 */       MediaView.this.fireEvent(new MediaErrorEvent(MediaView.this.getMediaPlayer(), MediaView.this.getMediaView(), (MediaException)localObservableObjectValue.get()));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.MediaView
 * JD-Core Version:    0.6.2
 */