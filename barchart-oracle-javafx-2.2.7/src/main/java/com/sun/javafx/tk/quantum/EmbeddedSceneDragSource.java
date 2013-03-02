/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ import com.sun.glass.ui.ClipboardAssistance;
/*    */ import com.sun.javafx.embed.EmbeddedSceneDragSourceInterface;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.Callable;
/*    */ import javafx.scene.input.TransferMode;
/*    */ 
/*    */ final class EmbeddedSceneDragSource
/*    */   implements EmbeddedSceneDragSourceInterface
/*    */ {
/*    */   private final EmbeddedSceneDnD dnd;
/*    */   private final GlassSceneDnDEventHandler dndHandler;
/*    */ 
/*    */   public EmbeddedSceneDragSource(EmbeddedSceneDnD paramEmbeddedSceneDnD, GlassSceneDnDEventHandler paramGlassSceneDnDEventHandler)
/*    */   {
/* 20 */     this.dnd = paramEmbeddedSceneDnD;
/* 21 */     this.dndHandler = paramGlassSceneDnDEventHandler;
/*    */   }
/*    */ 
/*    */   private ClipboardAssistance getClipboardAssistance() {
/* 25 */     assert (this.dnd.isValid(this));
/* 26 */     return this.dnd.getClipboardAssistance(this);
/*    */   }
/*    */ 
/*    */   public Set<TransferMode> getSupportedActions()
/*    */   {
/* 31 */     assert (this.dnd.isHostThread());
/* 32 */     return (Set)FxEventLoop.sendEvent(new Callable()
/*    */     {
/*    */       public Set<TransferMode> call()
/*    */       {
/* 36 */         return QuantumClipboard.clipboardActionsToTransferModes(EmbeddedSceneDragSource.this.getClipboardAssistance().getSupportedSourceActions());
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public Object getData(final String paramString)
/*    */   {
/* 44 */     assert (this.dnd.isHostThread());
/* 45 */     return FxEventLoop.sendEvent(new Callable()
/*    */     {
/*    */       public Object call()
/*    */       {
/* 49 */         return EmbeddedSceneDragSource.this.getClipboardAssistance().getData(paramString);
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public String[] getMimeTypes()
/*    */   {
/* 56 */     assert (this.dnd.isHostThread());
/* 57 */     return (String[])FxEventLoop.sendEvent(new Callable()
/*    */     {
/*    */       public String[] call()
/*    */       {
/* 61 */         return EmbeddedSceneDragSource.this.getClipboardAssistance().getMimeTypes();
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public boolean isMimeTypeAvailable(final String paramString)
/*    */   {
/* 68 */     assert (this.dnd.isHostThread());
/* 69 */     return ((Boolean)FxEventLoop.sendEvent(new Callable()
/*    */     {
/*    */       public Boolean call()
/*    */       {
/* 73 */         return Boolean.valueOf(Arrays.asList(EmbeddedSceneDragSource.this.getClipboardAssistance().getMimeTypes()).contains(paramString));
/*    */       }
/*    */     })).booleanValue();
/*    */   }
/*    */ 
/*    */   public void dragDropEnd(final TransferMode paramTransferMode)
/*    */   {
/* 81 */     assert (this.dnd.isHostThread());
/* 82 */     FxEventLoop.sendEvent(new Runnable()
/*    */     {
/*    */       public void run()
/*    */       {
/*    */         try {
/* 87 */           EmbeddedSceneDragSource.this.dndHandler.handleDragEnd(paramTransferMode, EmbeddedSceneDragSource.this.getClipboardAssistance());
/*    */         }
/*    */         finally {
/* 90 */           EmbeddedSceneDragSource.this.dnd.onDragSourceReleased(EmbeddedSceneDragSource.this);
/*    */         }
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.EmbeddedSceneDragSource
 * JD-Core Version:    0.6.2
 */