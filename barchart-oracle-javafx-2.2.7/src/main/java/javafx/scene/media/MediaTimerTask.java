/*      */ package javafx.scene.media;
/*      */ 
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.TimerTask;
/*      */ import javafx.application.Platform;
/*      */ 
/*      */ class MediaTimerTask extends TimerTask
/*      */ {
/*      */   WeakReference<MediaPlayer> playerRef;
/*      */ 
/*      */   MediaTimerTask(MediaPlayer paramMediaPlayer)
/*      */   {
/* 2667 */     this.playerRef = new WeakReference(paramMediaPlayer);
/*      */   }
/*      */ 
/*      */   public void run()
/*      */   {
/* 2672 */     final MediaPlayer localMediaPlayer = (MediaPlayer)this.playerRef.get();
/* 2673 */     if (localMediaPlayer != null)
/* 2674 */       Platform.runLater(new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 2678 */           localMediaPlayer.updateTime();
/*      */         }
/*      */       });
/*      */     else
/* 2682 */       cancel();
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.MediaTimerTask
 * JD-Core Version:    0.6.2
 */