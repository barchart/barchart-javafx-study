/*      */ package javafx.scene.media;
/*      */ 
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ 
/*      */ class MediaPlayerShutdownHook
/*      */   implements Runnable
/*      */ {
/* 2629 */   private static List<WeakReference<MediaPlayer>> playerRefs = new ArrayList();
/*      */ 
/*      */   public static void addMediaPlayer(MediaPlayer paramMediaPlayer)
/*      */   {
/* 2636 */     for (ListIterator localListIterator = playerRefs.listIterator(); localListIterator.hasNext(); ) {
/* 2637 */       MediaPlayer localMediaPlayer = (MediaPlayer)((WeakReference)localListIterator.next()).get();
/* 2638 */       if (localMediaPlayer == null) {
/* 2639 */         localListIterator.remove();
/*      */       }
/*      */     }
/*      */ 
/* 2643 */     playerRefs.add(new WeakReference(paramMediaPlayer));
/*      */   }
/*      */ 
/*      */   public void run()
/*      */   {
/* 2648 */     for (ListIterator localListIterator = playerRefs.listIterator(); localListIterator.hasNext(); ) {
/* 2649 */       MediaPlayer localMediaPlayer = (MediaPlayer)((WeakReference)localListIterator.next()).get();
/* 2650 */       if (localMediaPlayer != null) {
/* 2651 */         localMediaPlayer.destroyMediaTimer();
/* 2652 */         com.sun.media.jfxmedia.MediaPlayer localMediaPlayer1 = localMediaPlayer.retrieveJfxPlayer();
/* 2653 */         if (localMediaPlayer1 != null)
/* 2654 */           localMediaPlayer1.dispose();
/*      */       }
/*      */       else {
/* 2657 */         localListIterator.remove();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 2632 */     Toolkit.getToolkit().addShutdownHook(new MediaPlayerShutdownHook());
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.MediaPlayerShutdownHook
 * JD-Core Version:    0.6.2
 */