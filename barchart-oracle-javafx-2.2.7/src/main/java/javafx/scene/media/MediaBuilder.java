/*    */ package javafx.scene.media;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class MediaBuilder
/*    */   implements Builder<Media>
/*    */ {
/*    */   private int __set;
/*    */   private Runnable onError;
/*    */   private String source;
/*    */   private Collection<? extends Track> tracks;
/*    */ 
/*    */   public static MediaBuilder create()
/*    */   {
/* 15 */     return new MediaBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Media paramMedia)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramMedia.setOnError(this.onError);
/* 22 */     if ((i & 0x2) != 0) paramMedia.getTracks().addAll(this.tracks);
/*    */   }
/*    */ 
/*    */   public MediaBuilder onError(Runnable paramRunnable)
/*    */   {
/* 30 */     this.onError = paramRunnable;
/* 31 */     this.__set |= 1;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public MediaBuilder source(String paramString)
/*    */   {
/* 40 */     this.source = paramString;
/* 41 */     return this;
/*    */   }
/*    */ 
/*    */   public MediaBuilder tracks(Collection<? extends Track> paramCollection)
/*    */   {
/* 49 */     this.tracks = paramCollection;
/* 50 */     this.__set |= 2;
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   public MediaBuilder tracks(Track[] paramArrayOfTrack)
/*    */   {
/* 58 */     return tracks(Arrays.asList(paramArrayOfTrack));
/*    */   }
/*    */ 
/*    */   public Media build()
/*    */   {
/* 65 */     Media localMedia = new Media(this.source);
/* 66 */     applyTo(localMedia);
/* 67 */     return localMedia;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.MediaBuilder
 * JD-Core Version:    0.6.2
 */