/*     */ package javafx.scene.media;
/*     */ 
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.NodeBuilder;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class MediaViewBuilder<B extends MediaViewBuilder<B>> extends NodeBuilder<B>
/*     */   implements Builder<MediaView>
/*     */ {
/*     */   private int __set;
/*     */   private double fitHeight;
/*     */   private double fitWidth;
/*     */   private MediaPlayer mediaPlayer;
/*     */   private EventHandler<MediaErrorEvent> onError;
/*     */   private boolean preserveRatio;
/*     */   private boolean smooth;
/*     */   private Rectangle2D viewport;
/*     */   private double x;
/*     */   private double y;
/*     */ 
/*     */   public static MediaViewBuilder<?> create()
/*     */   {
/*  15 */     return new MediaViewBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(MediaView paramMediaView) {
/*  23 */     super.applyTo(paramMediaView);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramMediaView.setFitHeight(this.fitHeight); break;
/*     */       case 1:
/*  30 */         paramMediaView.setFitWidth(this.fitWidth); break;
/*     */       case 2:
/*  31 */         paramMediaView.setMediaPlayer(this.mediaPlayer); break;
/*     */       case 3:
/*  32 */         paramMediaView.setOnError(this.onError); break;
/*     */       case 4:
/*  33 */         paramMediaView.setPreserveRatio(this.preserveRatio); break;
/*     */       case 5:
/*  34 */         paramMediaView.setSmooth(this.smooth); break;
/*     */       case 6:
/*  35 */         paramMediaView.setViewport(this.viewport); break;
/*     */       case 7:
/*  36 */         paramMediaView.setX(this.x); break;
/*     */       case 8:
/*  37 */         paramMediaView.setY(this.y);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B fitHeight(double paramDouble)
/*     */   {
/*  48 */     this.fitHeight = paramDouble;
/*  49 */     __set(0);
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   public B fitWidth(double paramDouble)
/*     */   {
/*  59 */     this.fitWidth = paramDouble;
/*  60 */     __set(1);
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   public B mediaPlayer(MediaPlayer paramMediaPlayer)
/*     */   {
/*  70 */     this.mediaPlayer = paramMediaPlayer;
/*  71 */     __set(2);
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   public B onError(EventHandler<MediaErrorEvent> paramEventHandler)
/*     */   {
/*  81 */     this.onError = paramEventHandler;
/*  82 */     __set(3);
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   public B preserveRatio(boolean paramBoolean)
/*     */   {
/*  92 */     this.preserveRatio = paramBoolean;
/*  93 */     __set(4);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public B smooth(boolean paramBoolean)
/*     */   {
/* 103 */     this.smooth = paramBoolean;
/* 104 */     __set(5);
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public B viewport(Rectangle2D paramRectangle2D)
/*     */   {
/* 114 */     this.viewport = paramRectangle2D;
/* 115 */     __set(6);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public B x(double paramDouble)
/*     */   {
/* 125 */     this.x = paramDouble;
/* 126 */     __set(7);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public B y(double paramDouble)
/*     */   {
/* 136 */     this.y = paramDouble;
/* 137 */     __set(8);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaView build()
/*     */   {
/* 145 */     MediaView localMediaView = new MediaView();
/* 146 */     applyTo(localMediaView);
/* 147 */     return localMediaView;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.MediaViewBuilder
 * JD-Core Version:    0.6.2
 */