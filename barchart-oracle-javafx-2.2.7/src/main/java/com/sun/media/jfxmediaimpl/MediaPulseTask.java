/*      */ package com.sun.media.jfxmediaimpl;
/*      */ 
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.TimerTask;
/*      */ 
/*      */ class MediaPulseTask extends TimerTask
/*      */ {
/*      */   WeakReference<NativeMediaPlayer> playerRef;
/*      */ 
/*      */   MediaPulseTask(NativeMediaPlayer player)
/*      */   {
/* 1638 */     this.playerRef = new WeakReference(player);
/*      */   }
/*      */ 
/*      */   public void run()
/*      */   {
/* 1643 */     NativeMediaPlayer player = (NativeMediaPlayer)this.playerRef.get();
/* 1644 */     if (player != null) {
/* 1645 */       if (!player.doMediaPulseTask())
/* 1646 */         cancel();
/*      */     }
/*      */     else
/* 1649 */       cancel();
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.MediaPulseTask
 * JD-Core Version:    0.6.2
 */