/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.ClipboardAssistance;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDragSourceInterface;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDropTargetInterface;
/*     */ import java.util.concurrent.Callable;
/*     */ import javafx.application.Platform;
/*     */ import javafx.scene.input.TransferMode;
/*     */ 
/*     */ final class EmbeddedSceneDropTarget
/*     */   implements EmbeddedSceneDropTargetInterface
/*     */ {
/*     */   private final EmbeddedSceneDnD dnd;
/*     */   private final GlassSceneDnDEventHandler dndHandler;
/*     */   private EmbeddedSceneDragSourceInterface dragSource;
/*     */   private int dndCounter;
/*     */ 
/*     */   public EmbeddedSceneDropTarget(EmbeddedSceneDnD paramEmbeddedSceneDnD, GlassSceneDnDEventHandler paramGlassSceneDnDEventHandler)
/*     */   {
/*  22 */     this.dnd = paramEmbeddedSceneDnD;
/*  23 */     this.dndHandler = paramGlassSceneDnDEventHandler;
/*     */   }
/*     */ 
/*     */   private boolean isDnDCounterValid() {
/*  27 */     assert (Platform.isFxApplicationThread());
/*  28 */     assert (this.dndCounter == 1);
/*     */ 
/*  30 */     return true;
/*     */   }
/*     */ 
/*     */   private ClipboardAssistance getClipboardAssistance() {
/*  34 */     assert (isDnDCounterValid());
/*  35 */     assert (this.dnd.isValid(this));
/*     */ 
/*  37 */     return this.dnd.getClipboardAssistance(this.dragSource);
/*     */   }
/*     */ 
/*     */   private void close() {
/*  41 */     assert (isDnDCounterValid());
/*     */ 
/*  43 */     this.dndCounter -= 1;
/*     */ 
/*  45 */     this.dnd.onDropTargetReleased(this);
/*     */   }
/*     */ 
/*     */   public TransferMode handleDragEnter(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final TransferMode paramTransferMode, final EmbeddedSceneDragSourceInterface paramEmbeddedSceneDragSourceInterface)
/*     */   {
/*  53 */     assert (this.dnd.isHostThread());
/*     */ 
/*  55 */     return (TransferMode)FxEventLoop.sendEvent(new Callable()
/*     */     {
/*     */       public TransferMode call()
/*     */       {
/*  59 */         EmbeddedSceneDropTarget.access$004(EmbeddedSceneDropTarget.this);
/*     */ 
/*  61 */         assert ((EmbeddedSceneDropTarget.this.dnd.isFxDragSource()) || (EmbeddedSceneDropTarget.this.dragSource == null));
/*     */ 
/*  64 */         EmbeddedSceneDropTarget.this.dragSource = paramEmbeddedSceneDragSourceInterface;
/*     */ 
/*  66 */         return EmbeddedSceneDropTarget.this.dndHandler.handleDragEnter(paramInt1, paramInt2, paramInt3, paramInt4, paramTransferMode, EmbeddedSceneDropTarget.this.getClipboardAssistance());
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void handleDragLeave()
/*     */   {
/*  75 */     assert (this.dnd.isHostThread());
/*     */ 
/*  77 */     FxEventLoop.sendEvent(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/*  82 */           EmbeddedSceneDropTarget.this.dndHandler.handleDragLeave(EmbeddedSceneDropTarget.this.getClipboardAssistance());
/*     */         } finally {
/*  84 */           EmbeddedSceneDropTarget.this.close();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public TransferMode handleDragDrop(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final TransferMode paramTransferMode)
/*     */   {
/*  94 */     assert (this.dnd.isHostThread());
/*     */ 
/*  96 */     return (TransferMode)FxEventLoop.sendEvent(new Callable()
/*     */     {
/*     */       public TransferMode call()
/*     */       {
/*     */         try {
/* 101 */           return EmbeddedSceneDropTarget.this.dndHandler.handleDragDrop(paramInt1, paramInt2, paramInt3, paramInt4, paramTransferMode, EmbeddedSceneDropTarget.this.getClipboardAssistance());
/*     */         }
/*     */         finally
/*     */         {
/* 105 */           EmbeddedSceneDropTarget.this.close();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public TransferMode handleDragOver(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final TransferMode paramTransferMode)
/*     */   {
/* 115 */     assert (this.dnd.isHostThread());
/*     */ 
/* 117 */     return (TransferMode)FxEventLoop.sendEvent(new Callable()
/*     */     {
/*     */       public TransferMode call()
/*     */       {
/* 121 */         return EmbeddedSceneDropTarget.this.dndHandler.handleDragOver(paramInt1, paramInt2, paramInt3, paramInt4, paramTransferMode, EmbeddedSceneDropTarget.this.getClipboardAssistance());
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.EmbeddedSceneDropTarget
 * JD-Core Version:    0.6.2
 */