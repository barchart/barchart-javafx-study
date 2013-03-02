/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.ClipboardAssistance;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDragSourceInterface;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDragStartListenerInterface;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDropTargetInterface;
/*     */ import java.util.concurrent.Callable;
/*     */ import javafx.application.Platform;
/*     */ import javafx.scene.input.Dragboard;
/*     */ import javafx.scene.input.TransferMode;
/*     */ 
/*     */ final class EmbeddedSceneDnD
/*     */ {
/*     */   private final GlassSceneDnDEventHandler dndHandler;
/*     */   private EmbeddedSceneDragStartListenerInterface dragStartListener;
/*     */   private EmbeddedSceneDragSourceInterface fxDragSource;
/*     */   private EmbeddedSceneDropTargetInterface fxDropTarget;
/*     */   private ClipboardAssistance clipboardAssistant;
/*     */   private Thread hostThread;
/*     */ 
/*     */   public EmbeddedSceneDnD(GlassScene paramGlassScene)
/*     */   {
/*  28 */     this.dndHandler = new GlassSceneDnDEventHandler(paramGlassScene, null);
/*     */   }
/*     */ 
/*     */   private void startDrag() {
/*  32 */     assert (Platform.isFxApplicationThread());
/*     */ 
/*  34 */     assert (this.fxDragSource == null);
/*     */ 
/*  36 */     this.fxDragSource = new EmbeddedSceneDragSource(this, this.dndHandler);
/*     */ 
/*  39 */     TransferMode localTransferMode = TransferMode.COPY;
/*     */ 
/*  41 */     this.dragStartListener.dragStarted(this.fxDragSource, localTransferMode);
/*     */   }
/*     */ 
/*     */   private void setHostThread() {
/*  45 */     if (this.hostThread == null)
/*  46 */       this.hostThread = Thread.currentThread();
/*     */   }
/*     */ 
/*     */   public boolean isHostThread()
/*     */   {
/*  51 */     return Thread.currentThread() == this.hostThread;
/*     */   }
/*     */ 
/*     */   public boolean isValid(EmbeddedSceneDragSourceInterface paramEmbeddedSceneDragSourceInterface) {
/*  55 */     assert (Platform.isFxApplicationThread());
/*  56 */     assert (paramEmbeddedSceneDragSourceInterface != null);
/*  57 */     assert (this.fxDragSource == paramEmbeddedSceneDragSourceInterface);
/*  58 */     assert (this.clipboardAssistant != null);
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isValid(EmbeddedSceneDropTargetInterface paramEmbeddedSceneDropTargetInterface) {
/*  63 */     assert (Platform.isFxApplicationThread());
/*  64 */     assert (paramEmbeddedSceneDropTargetInterface != null);
/*  65 */     assert (this.fxDropTarget == paramEmbeddedSceneDropTargetInterface);
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isFxDragSource() {
/*  70 */     assert (Platform.isFxApplicationThread());
/*     */ 
/*  72 */     return this.fxDragSource != null;
/*     */   }
/*     */ 
/*     */   public void onDragSourceReleased(EmbeddedSceneDragSourceInterface paramEmbeddedSceneDragSourceInterface) {
/*  76 */     assert (isValid(paramEmbeddedSceneDragSourceInterface));
/*     */ 
/*  78 */     this.fxDragSource = null;
/*  79 */     this.clipboardAssistant = null;
/*     */ 
/*  81 */     FxEventLoop.leaveNestedLoop();
/*     */   }
/*     */ 
/*     */   public void onDropTargetReleased(EmbeddedSceneDropTargetInterface paramEmbeddedSceneDropTargetInterface) {
/*  85 */     assert (isValid(paramEmbeddedSceneDropTargetInterface));
/*     */ 
/*  87 */     this.fxDropTarget = null;
/*  88 */     if (!isFxDragSource())
/*  89 */       this.clipboardAssistant = null;
/*     */   }
/*     */ 
/*     */   public Dragboard createDragboard()
/*     */   {
/*  95 */     assert (Platform.isFxApplicationThread());
/*  96 */     assert (this.fxDropTarget == null);
/*  97 */     assert (this.clipboardAssistant == null);
/*  98 */     assert (this.fxDragSource == null);
/*     */ 
/* 100 */     this.clipboardAssistant = new ClipboardAssistanceImpl(null);
/*     */ 
/* 102 */     return GlassSceneDnDEventHandler.createDragboard(this.clipboardAssistant);
/*     */   }
/*     */ 
/*     */   public void setDragStartListener(EmbeddedSceneDragStartListenerInterface paramEmbeddedSceneDragStartListenerInterface) {
/* 106 */     setHostThread();
/*     */ 
/* 108 */     assert (isHostThread());
/*     */ 
/* 110 */     this.dragStartListener = paramEmbeddedSceneDragStartListenerInterface;
/*     */   }
/*     */ 
/*     */   public EmbeddedSceneDropTargetInterface createDropTarget() {
/* 114 */     setHostThread();
/*     */ 
/* 116 */     assert (isHostThread());
/* 117 */     assert (this.fxDropTarget == null);
/*     */ 
/* 119 */     return (EmbeddedSceneDropTargetInterface)FxEventLoop.sendEvent(new Callable()
/*     */     {
/*     */       public EmbeddedSceneDropTargetInterface call()
/*     */       {
/* 123 */         EmbeddedSceneDnD.this.fxDropTarget = new EmbeddedSceneDropTarget(EmbeddedSceneDnD.this, EmbeddedSceneDnD.this.dndHandler);
/*     */ 
/* 126 */         return EmbeddedSceneDnD.this.fxDropTarget;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public ClipboardAssistance getClipboardAssistance(EmbeddedSceneDragSourceInterface paramEmbeddedSceneDragSourceInterface)
/*     */   {
/* 133 */     assert (Platform.isFxApplicationThread());
/* 134 */     assert ((!isFxDragSource()) || (isValid(paramEmbeddedSceneDragSourceInterface)));
/* 135 */     assert ((!isFxDragSource()) || (this.clipboardAssistant != null));
/*     */ 
/* 137 */     if (this.clipboardAssistant == null) {
/* 138 */       this.clipboardAssistant = new ClipboardAssistanceImpl(paramEmbeddedSceneDragSourceInterface);
/*     */     }
/* 140 */     return this.clipboardAssistant;
/*     */   }
/*     */ 
/*     */   private class ClipboardAssistanceImpl extends ClipboardAssistance {
/*     */     final EmbeddedSceneDragSourceInterface source;
/*     */ 
/*     */     private boolean isValid() {
/* 147 */       assert (Platform.isFxApplicationThread());
/* 148 */       assert (EmbeddedSceneDnD.this.clipboardAssistant == this);
/*     */ 
/* 150 */       return true;
/*     */     }
/*     */ 
/*     */     ClipboardAssistanceImpl(EmbeddedSceneDragSourceInterface arg2) {
/* 154 */       super();
/*     */       Object localObject;
/* 156 */       this.source = localObject;
/*     */     }
/*     */ 
/*     */     public void flush()
/*     */     {
/* 161 */       assert (isValid());
/*     */ 
/* 163 */       super.flush();
/*     */ 
/* 165 */       EmbeddedSceneDnD.this.startDrag();
/*     */ 
/* 167 */       FxEventLoop.enterNestedLoop();
/*     */     }
/*     */ 
/*     */     public void emptyCache()
/*     */     {
/* 172 */       assert (isValid());
/*     */ 
/* 174 */       super.emptyCache();
/*     */     }
/*     */ 
/*     */     public Object getData(String paramString)
/*     */     {
/* 179 */       assert (isValid());
/*     */ 
/* 181 */       if (this.source == null) {
/* 182 */         return super.getData(paramString);
/*     */       }
/*     */ 
/* 185 */       return this.source.getData(paramString);
/*     */     }
/*     */ 
/*     */     public void setData(String paramString, Object paramObject)
/*     */     {
/* 190 */       assert (isValid());
/*     */ 
/* 192 */       if (this.source != null) {
/* 193 */         return;
/*     */       }
/*     */ 
/* 196 */       super.setData(paramString, paramObject);
/*     */     }
/*     */ 
/*     */     public void setSupportedActions(int paramInt)
/*     */     {
/* 201 */       assert (isValid());
/*     */ 
/* 203 */       if (this.source != null) {
/* 204 */         return;
/*     */       }
/*     */ 
/* 207 */       super.setSupportedActions(paramInt);
/*     */     }
/*     */ 
/*     */     public int getSupportedSourceActions()
/*     */     {
/* 212 */       assert (isValid());
/*     */ 
/* 214 */       if (this.source == null) {
/* 215 */         return super.getSupportedSourceActions();
/*     */       }
/*     */ 
/* 218 */       return QuantumClipboard.transferModesToClipboardActions(this.source.getSupportedActions());
/*     */     }
/*     */ 
/*     */     public void setTargetAction(int paramInt)
/*     */     {
/* 224 */       assert (isValid());
/*     */ 
/* 226 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public String[] getMimeTypes()
/*     */     {
/* 231 */       assert (isValid());
/*     */ 
/* 233 */       if (this.source == null) {
/* 234 */         return super.getMimeTypes();
/*     */       }
/*     */ 
/* 237 */       return this.source.getMimeTypes();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.EmbeddedSceneDnD
 * JD-Core Version:    0.6.2
 */