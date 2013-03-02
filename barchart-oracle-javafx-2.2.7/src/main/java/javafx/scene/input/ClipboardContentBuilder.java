/*    */ package javafx.scene.input;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ClipboardContentBuilder<B extends ClipboardContentBuilder<B>>
/*    */   implements Builder<ClipboardContent>
/*    */ {
/*    */   private boolean __set;
/*    */   private Collection<? extends File> files;
/*    */ 
/*    */   public static ClipboardContentBuilder<?> create()
/*    */   {
/* 15 */     return new ClipboardContentBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ClipboardContent paramClipboardContent)
/*    */   {
/* 20 */     if (this.__set) { paramClipboardContent.getFiles().clear(); paramClipboardContent.getFiles().addAll(this.files);
/*    */     }
/*    */   }
/*    */ 
/*    */   public B files(Collection<? extends File> paramCollection)
/*    */   {
/* 29 */     this.files = paramCollection;
/* 30 */     this.__set = true;
/* 31 */     return this;
/*    */   }
/*    */ 
/*    */   public B files(File[] paramArrayOfFile)
/*    */   {
/* 38 */     return files(Arrays.asList(paramArrayOfFile));
/*    */   }
/*    */ 
/*    */   public ClipboardContent build()
/*    */   {
/* 45 */     ClipboardContent localClipboardContent = new ClipboardContent();
/* 46 */     applyTo(localClipboardContent);
/* 47 */     return localClipboardContent;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.ClipboardContentBuilder
 * JD-Core Version:    0.6.2
 */