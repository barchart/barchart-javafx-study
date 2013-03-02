/*    */ package javafx.scene.media;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ public final class AudioTrack extends Track
/*    */ {
/*    */   private String language;
/*    */ 
/*    */   public final String getLanguage()
/*    */   {
/* 32 */     return this.language;
/*    */   }
/*    */ 
/*    */   AudioTrack(com.sun.media.jfxmedia.track.AudioTrack paramAudioTrack)
/*    */   {
/* 42 */     super(paramAudioTrack);
/*    */ 
/* 44 */     Locale localLocale = paramAudioTrack.getLocale();
/* 45 */     if (localLocale != null)
/* 46 */       this.language = localLocale.getLanguage();
/*    */     else
/* 48 */       this.language = null;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 58 */     return "Audio Track: name=" + super.getName() + ", language=" + this.language;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.AudioTrack
 * JD-Core Version:    0.6.2
 */