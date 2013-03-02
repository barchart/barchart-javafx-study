/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.ClipboardAssistance;
/*     */ import com.sun.javafx.tk.TKDragGestureListener;
/*     */ import com.sun.javafx.tk.TKDragSourceListener;
/*     */ import com.sun.javafx.tk.TKDropTargetListener;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javafx.application.Platform;
/*     */ import javafx.scene.input.Dragboard;
/*     */ import javafx.scene.input.TransferMode;
/*     */ 
/*     */ public class GlassSceneDnDEventHandler
/*     */ {
/*     */   private final GlassScene scene;
/*     */   private final boolean verbose;
/*     */   private final DragEventListener del;
/*  42 */   private static final QuantumToolkit toolkit = (QuantumToolkit)QuantumToolkit.getToolkit();
/*     */ 
/*     */   public GlassSceneDnDEventHandler(GlassScene paramGlassScene, DragEventListener paramDragEventListener, boolean paramBoolean)
/*     */   {
/*  33 */     this.scene = paramGlassScene;
/*  34 */     this.verbose = paramBoolean;
/*  35 */     this.del = paramDragEventListener;
/*     */   }
/*     */ 
/*     */   public GlassSceneDnDEventHandler(GlassScene paramGlassScene, DragEventListener paramDragEventListener)
/*     */   {
/*  40 */     this(paramGlassScene, paramDragEventListener, false);
/*     */   }
/*     */ 
/*     */   private static boolean toolkit()
/*     */   {
/*  46 */     return QuantumToolkit.getFxUserThread() != null;
/*     */   }
/*     */ 
/*     */   public TransferMode handleDragEnter(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final int paramInt5, final ClipboardAssistance paramClipboardAssistance)
/*     */   {
/*  53 */     assert (Platform.isFxApplicationThread());
/*  54 */     return (TransferMode)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public TransferMode run() {
/*  56 */         if ((GlassSceneDnDEventHandler.this.scene.dropTargetListener != null) && (GlassSceneDnDEventHandler.access$100())) {
/*  57 */           Dragboard localDragboard = GlassSceneDnDEventHandler.createDragboard(paramClipboardAssistance);
/*  58 */           GlassDragEvent localGlassDragEvent = GlassSceneDnDEventHandler.createEvent(3, paramInt1, paramInt2, paramInt3, paramInt4, localDragboard, 0, GlassSceneDnDEventHandler.this.scene, paramInt5, GlassSceneDnDEventHandler.this.del);
/*     */ 
/*  63 */           return GlassSceneDnDEventHandler.this.scene.dropTargetListener.dragEnter(localGlassDragEvent);
/*     */         }
/*  65 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleDragLeave(final ClipboardAssistance paramClipboardAssistance)
/*     */   {
/*  75 */     assert (Platform.isFxApplicationThread());
/*  76 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/*  78 */         if ((GlassSceneDnDEventHandler.this.scene.dropTargetListener != null) && (GlassSceneDnDEventHandler.access$100())) {
/*  79 */           Dragboard localDragboard = GlassSceneDnDEventHandler.createDragboard(paramClipboardAssistance);
/*  80 */           GlassDragEvent localGlassDragEvent = GlassSceneDnDEventHandler.createEvent(5, 0, 0, 0, 0, localDragboard, 0, GlassSceneDnDEventHandler.this.scene, 0, GlassSceneDnDEventHandler.this.del);
/*     */ 
/*  85 */           GlassSceneDnDEventHandler.this.scene.dropTargetListener.dragExit(localGlassDragEvent);
/*     */         }
/*  87 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public TransferMode handleDragDrop(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final int paramInt5, final ClipboardAssistance paramClipboardAssistance)
/*     */   {
/*  96 */     assert (Platform.isFxApplicationThread());
/*  97 */     return (TransferMode)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public TransferMode run() {
/*  99 */         Dragboard localDragboard = GlassSceneDnDEventHandler.createDragboard(paramClipboardAssistance);
/* 100 */         GlassDragEvent localGlassDragEvent = GlassSceneDnDEventHandler.createEvent(2, paramInt1, paramInt2, paramInt3, paramInt4, localDragboard, 0, GlassSceneDnDEventHandler.this.scene, paramInt5, GlassSceneDnDEventHandler.this.del);
/*     */ 
/* 104 */         if ((GlassSceneDnDEventHandler.this.scene.dropTargetListener != null) && (GlassSceneDnDEventHandler.access$100())) {
/* 105 */           return GlassSceneDnDEventHandler.this.scene.dropTargetListener.drop(localGlassDragEvent);
/*     */         }
/* 107 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public TransferMode handleDragOver(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final int paramInt5, final ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 116 */     assert (Platform.isFxApplicationThread());
/* 117 */     return (TransferMode)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public TransferMode run() {
/* 119 */         if ((GlassSceneDnDEventHandler.this.scene.dropTargetListener != null) && (GlassSceneDnDEventHandler.access$100())) {
/* 120 */           Dragboard localDragboard = GlassSceneDnDEventHandler.createDragboard(paramClipboardAssistance);
/* 121 */           GlassDragEvent localGlassDragEvent = GlassSceneDnDEventHandler.createEvent(4, paramInt1, paramInt2, paramInt3, paramInt4, localDragboard, 0, GlassSceneDnDEventHandler.this.scene, paramInt5, GlassSceneDnDEventHandler.this.del);
/*     */ 
/* 126 */           return GlassSceneDnDEventHandler.this.scene.dropTargetListener.dragOver(localGlassDragEvent);
/*     */         }
/* 128 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleDragStart(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final int paramInt5, final ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 136 */     assert (Platform.isFxApplicationThread());
/* 137 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 139 */         if ((GlassSceneDnDEventHandler.this.scene.dragGestureListener != null) && (GlassSceneDnDEventHandler.access$100())) {
/* 140 */           Dragboard localDragboard = GlassSceneDnDEventHandler.createDragboard(paramClipboardAssistance);
/* 141 */           GlassDragEvent localGlassDragEvent = GlassSceneDnDEventHandler.createEvent(1, paramInt2, paramInt3, paramInt4, paramInt5, localDragboard, paramInt1, GlassSceneDnDEventHandler.this.scene, 0, GlassSceneDnDEventHandler.this.del);
/*     */ 
/* 145 */           GlassSceneDnDEventHandler.this.scene.dragGestureListener.dragGestureRecognized(localGlassDragEvent);
/*     */         }
/* 147 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleDragEnd(final int paramInt, final ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 158 */     assert (Platform.isFxApplicationThread());
/* 159 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 161 */         if ((GlassSceneDnDEventHandler.this.scene.dragSourceListener != null) && (GlassSceneDnDEventHandler.access$100())) {
/* 162 */           Dragboard localDragboard = GlassSceneDnDEventHandler.createDragboard(paramClipboardAssistance);
/* 163 */           GlassDragEvent localGlassDragEvent = GlassSceneDnDEventHandler.createEvent(2, 0, 0, 0, 0, localDragboard, 0, GlassSceneDnDEventHandler.this.scene, paramInt, GlassSceneDnDEventHandler.this.del);
/*     */ 
/* 166 */           GlassSceneDnDEventHandler.this.scene.dragSourceListener.dragDropEnd(localGlassDragEvent);
/*     */         }
/* 168 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public TransferMode handleDragEnter(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TransferMode paramTransferMode, ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 176 */     return handleDragEnter(paramInt1, paramInt2, paramInt3, paramInt4, TransferModeToAction(paramTransferMode), paramClipboardAssistance);
/*     */   }
/*     */ 
/*     */   public TransferMode handleDragDrop(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TransferMode paramTransferMode, ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 183 */     return handleDragDrop(paramInt1, paramInt2, paramInt3, paramInt4, TransferModeToAction(paramTransferMode), paramClipboardAssistance);
/*     */   }
/*     */ 
/*     */   public TransferMode handleDragOver(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TransferMode paramTransferMode, ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 190 */     return handleDragOver(paramInt1, paramInt2, paramInt3, paramInt4, TransferModeToAction(paramTransferMode), paramClipboardAssistance);
/*     */   }
/*     */ 
/*     */   public void handleDragEnd(TransferMode paramTransferMode, ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 196 */     handleDragEnd(TransferModeToAction(paramTransferMode), paramClipboardAssistance);
/*     */   }
/*     */ 
/*     */   public static int TransferModeToAction(TransferMode paramTransferMode) {
/* 200 */     if (paramTransferMode == null) {
/* 201 */       return 0;
/*     */     }
/*     */ 
/* 204 */     switch (7.$SwitchMap$javafx$scene$input$TransferMode[paramTransferMode.ordinal()]) {
/*     */     case 1:
/* 206 */       return 1;
/*     */     case 2:
/* 208 */       return 2;
/*     */     case 3:
/* 210 */       return 1073741824;
/*     */     }
/* 212 */     return 0;
/*     */   }
/*     */ 
/*     */   static Dragboard createDragboard(ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 217 */     QuantumClipboard localQuantumClipboard = QuantumClipboard.getDragboardInstance(paramClipboardAssistance);
/*     */ 
/* 219 */     return Dragboard.impl_create(localQuantumClipboard);
/*     */   }
/*     */ 
/*     */   private static GlassDragEvent createEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Dragboard paramDragboard, int paramInt6, GlassScene paramGlassScene, int paramInt7, DragEventListener paramDragEventListener)
/*     */   {
/* 227 */     if (paramDragEventListener == null) {
/* 228 */       return new GlassDragEvent(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramDragboard, paramInt6, paramGlassScene, paramInt7);
/*     */     }
/*     */ 
/* 231 */     return new GlassDragEventWithListener(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramDragboard, paramInt6, paramGlassScene, paramInt7, paramDragEventListener);
/*     */   }
/*     */ 
/*     */   private static class GlassDragEventWithListener extends GlassDragEvent
/*     */   {
/*     */     private final GlassSceneDnDEventHandler.DragEventListener del;
/*     */ 
/*     */     GlassDragEventWithListener(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Dragboard paramDragboard, int paramInt6, GlassScene paramGlassScene, int paramInt7, GlassSceneDnDEventHandler.DragEventListener paramDragEventListener)
/*     */     {
/* 245 */       super(paramInt2, paramInt3, paramInt4, paramInt5, paramDragboard, paramInt6, paramGlassScene, paramInt7);
/*     */ 
/* 247 */       this.del = paramDragEventListener;
/*     */     }
/*     */ 
/*     */     public void accept(TransferMode paramTransferMode)
/*     */     {
/* 252 */       this.del.eventAccepted(paramTransferMode);
/*     */     }
/*     */ 
/*     */     public void reject()
/*     */     {
/* 257 */       this.del.eventRejected();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface DragEventListener
/*     */   {
/*     */     public abstract void eventAccepted(TransferMode paramTransferMode);
/*     */ 
/*     */     public abstract void eventRejected();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassSceneDnDEventHandler
 * JD-Core Version:    0.6.2
 */