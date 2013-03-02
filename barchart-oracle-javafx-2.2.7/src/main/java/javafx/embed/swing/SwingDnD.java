/*     */ package javafx.embed.swing;
/*     */ 
/*     */ import com.sun.javafx.embed.EmbeddedSceneDragSourceInterface;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDragStartListenerInterface;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDropTargetInterface;
/*     */ import com.sun.javafx.embed.EmbeddedSceneInterface;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.awt.Point;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.awt.dnd.DragGestureEvent;
/*     */ import java.awt.dnd.DragGestureRecognizer;
/*     */ import java.awt.dnd.DragSource;
/*     */ import java.awt.dnd.DragSourceAdapter;
/*     */ import java.awt.dnd.DragSourceDropEvent;
/*     */ import java.awt.dnd.DragSourceListener;
/*     */ import java.awt.dnd.DropTarget;
/*     */ import java.awt.dnd.DropTargetAdapter;
/*     */ import java.awt.dnd.DropTargetDragEvent;
/*     */ import java.awt.dnd.DropTargetDropEvent;
/*     */ import java.awt.dnd.DropTargetEvent;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javafx.scene.input.TransferMode;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ final class SwingDnD
/*     */ {
/*     */   private final JFXPanelFacade facade;
/*  74 */   private final Transferable dndTransferable = new DnDTransferable(null);
/*     */   private final DragSourceListener dragSourceListener;
/*     */   private SwingDragSource swingDragSource;
/*     */   private EmbeddedSceneDragSourceInterface fxDragSource;
/*     */   private EmbeddedSceneDropTargetInterface dropTarget;
/*     */   private MouseEvent me;
/*     */ 
/*     */   SwingDnD(final JComponent paramJComponent, JFXPanelFacade paramJFXPanelFacade)
/*     */   {
/*  87 */     this.facade = paramJFXPanelFacade;
/*     */ 
/*  89 */     paramJComponent.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseClicked(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  93 */         SwingDnD.this.storeMouseEvent(paramAnonymousMouseEvent);
/*     */       }
/*     */ 
/*     */       public void mouseDragged(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  98 */         SwingDnD.this.storeMouseEvent(paramAnonymousMouseEvent);
/*     */       }
/*     */ 
/*     */       public void mousePressed(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/* 103 */         SwingDnD.this.storeMouseEvent(paramAnonymousMouseEvent);
/*     */       }
/*     */ 
/*     */       public void mouseReleased(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/* 108 */         SwingDnD.this.storeMouseEvent(paramAnonymousMouseEvent);
/*     */       }
/*     */     });
/* 112 */     this.dragSourceListener = new DragSourceAdapter()
/*     */     {
/*     */       public void dragDropEnd(DragSourceDropEvent paramAnonymousDragSourceDropEvent)
/*     */       {
/* 117 */         if (SwingDnD.this.fxDragSource == null) {
/* 118 */           return;
/*     */         }
/*     */ 
/* 121 */         assert (SwingDnD.this.hasFxScene());
/*     */         try
/*     */         {
/* 124 */           SwingDnD.this.fxDragSource.dragDropEnd(SwingDnD.dropActionToTransferMode(paramAnonymousDragSourceDropEvent.getDropAction()));
/*     */         }
/*     */         finally {
/* 127 */           SwingDnD.this.fxDragSource = null;
/*     */         }
/*     */       }
/*     */     };
/* 132 */     new DropTarget(paramJComponent, 1073741827, new DropTargetAdapter()
/*     */     {
/*     */       public void dragEnter(DropTargetDragEvent paramAnonymousDropTargetDragEvent)
/*     */       {
/* 137 */         if (!SwingDnD.this.hasFxScene()) {
/* 138 */           paramAnonymousDropTargetDragEvent.rejectDrag();
/* 139 */           return;
/*     */         }
/*     */ 
/* 142 */         if (SwingDnD.this.fxDragSource == null)
/*     */         {
/* 145 */           assert (SwingDnD.this.swingDragSource == null);
/* 146 */           SwingDnD.this.swingDragSource = new SwingDragSource(paramAnonymousDropTargetDragEvent);
/*     */         }
/*     */ 
/* 149 */         Point localPoint1 = paramAnonymousDropTargetDragEvent.getLocation();
/* 150 */         Point localPoint2 = new Point(localPoint1);
/* 151 */         SwingUtilities.convertPointToScreen(localPoint2, paramJComponent);
/* 152 */         SwingDnD.applyDragResult(SwingDnD.access$600(SwingDnD.this).handleDragEnter(localPoint1.x, localPoint1.y, localPoint2.x, localPoint2.y, SwingDnD.dropActionToTransferMode(paramAnonymousDropTargetDragEvent.getDropAction()), SwingDnD.access$500(SwingDnD.this)), paramAnonymousDropTargetDragEvent);
/*     */       }
/*     */ 
/*     */       public void dragExit(DropTargetEvent paramAnonymousDropTargetEvent)
/*     */       {
/* 161 */         if (!SwingDnD.this.hasFxScene())
/*     */         {
/* 164 */           return;
/*     */         }
/*     */         try
/*     */         {
/* 168 */           SwingDnD.this.dropTarget.handleDragLeave();
/*     */         } finally {
/* 170 */           SwingDnD.this.endDnD();
/*     */         }
/*     */       }
/*     */ 
/*     */       public void dragOver(DropTargetDragEvent paramAnonymousDropTargetDragEvent)
/*     */       {
/* 176 */         if (!SwingDnD.this.hasFxScene())
/*     */         {
/* 179 */           return;
/*     */         }
/*     */ 
/* 182 */         if (SwingDnD.this.swingDragSource != null) {
/* 183 */           SwingDnD.this.swingDragSource.updateContents(paramAnonymousDropTargetDragEvent);
/*     */         }
/*     */ 
/* 186 */         Point localPoint1 = paramAnonymousDropTargetDragEvent.getLocation();
/* 187 */         Point localPoint2 = new Point(localPoint1);
/* 188 */         SwingUtilities.convertPointToScreen(localPoint2, paramJComponent);
/* 189 */         SwingDnD.applyDragResult(SwingDnD.this.dropTarget.handleDragOver(localPoint1.x, localPoint1.y, localPoint2.x, localPoint2.y, SwingDnD.dropActionToTransferMode(paramAnonymousDropTargetDragEvent.getDropAction())), paramAnonymousDropTargetDragEvent);
/*     */       }
/*     */ 
/*     */       public void drop(DropTargetDropEvent paramAnonymousDropTargetDropEvent)
/*     */       {
/* 197 */         if (!SwingDnD.this.hasFxScene())
/*     */         {
/* 200 */           return;
/*     */         }
/*     */ 
/* 203 */         Point localPoint1 = paramAnonymousDropTargetDropEvent.getLocation();
/* 204 */         Point localPoint2 = new Point(localPoint1);
/* 205 */         SwingUtilities.convertPointToScreen(localPoint2, paramJComponent);
/*     */         try
/*     */         {
/* 208 */           TransferMode localTransferMode = SwingDnD.this.dropTarget.handleDragDrop(localPoint1.x, localPoint1.y, localPoint2.x, localPoint2.y, SwingDnD.dropActionToTransferMode(paramAnonymousDropTargetDropEvent.getDropAction()));
/*     */ 
/* 213 */           SwingDnD.applyDropResult(localTransferMode, paramAnonymousDropTargetDropEvent);
/*     */ 
/* 215 */           paramAnonymousDropTargetDropEvent.dropComplete(localTransferMode != null);
/*     */         } finally {
/* 217 */           SwingDnD.this.endDnD();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   void addNotify() {
/* 224 */     DragSource.getDefaultDragSource().addDragSourceListener(this.dragSourceListener);
/*     */   }
/*     */ 
/*     */   void removeNotify()
/*     */   {
/* 231 */     DragSource.getDefaultDragSource().removeDragSourceListener(this.dragSourceListener);
/*     */   }
/*     */ 
/*     */   EmbeddedSceneDragStartListenerInterface getDragStartListener()
/*     */   {
/* 236 */     return new EmbeddedSceneDragStartListenerInterface()
/*     */     {
/*     */       public void dragStarted(final EmbeddedSceneDragSourceInterface paramAnonymousEmbeddedSceneDragSourceInterface, final TransferMode paramAnonymousTransferMode)
/*     */       {
/* 242 */         assert (Toolkit.getToolkit().isFxUserThread());
/* 243 */         assert (paramAnonymousEmbeddedSceneDragSourceInterface != null);
/*     */ 
/* 251 */         SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 255 */             assert (SwingDnD.this.fxDragSource == null);
/* 256 */             assert (SwingDnD.this.swingDragSource == null);
/* 257 */             assert (SwingDnD.this.dropTarget == null);
/*     */ 
/* 259 */             SwingDnD.this.fxDragSource = paramAnonymousEmbeddedSceneDragSourceInterface;
/*     */ 
/* 261 */             SwingDnD.startDrag(SwingDnD.this.me, SwingDnD.this.dndTransferable, paramAnonymousEmbeddedSceneDragSourceInterface.getSupportedActions(), paramAnonymousTransferMode);
/*     */           }
/*     */         });
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   private static void startDrag(MouseEvent paramMouseEvent, Transferable paramTransferable, final Set<TransferMode> paramSet, TransferMode paramTransferMode)
/*     */   {
/* 273 */     assert (paramSet.contains(paramTransferMode));
/*     */ 
/* 297 */     Point localPoint = new Point(paramMouseEvent.getX(), paramMouseEvent.getY());
/*     */ 
/* 299 */     int i = transferModeToDropAction(paramTransferMode);
/*     */ 
/* 301 */     DragGestureRecognizer local1StubDragGestureRecognizer = new DragGestureRecognizer(paramMouseEvent, paramSet)
/*     */     {
/*     */       protected void registerListeners()
/*     */       {
/*     */       }
/*     */ 
/*     */       protected void unregisterListeners()
/*     */       {
/*     */       }
/*     */     };
/* 303 */     List localList = Arrays.asList(new InputEvent[] { local1StubDragGestureRecognizer.getTriggerEvent() });
/*     */ 
/* 306 */     DragGestureEvent localDragGestureEvent = new DragGestureEvent(local1StubDragGestureRecognizer, i, localPoint, localList);
/*     */ 
/* 309 */     localDragGestureEvent.startDrag(null, paramTransferable);
/*     */   }
/*     */ 
/*     */   private boolean hasFxScene() {
/* 313 */     assert (SwingUtilities.isEventDispatchThread());
/* 314 */     return getFxScene() != null;
/*     */   }
/*     */ 
/*     */   private EmbeddedSceneInterface getFxScene() {
/* 318 */     return this.facade.getScene();
/*     */   }
/*     */ 
/*     */   private EmbeddedSceneDragSourceInterface getDragSource() {
/* 322 */     assert (hasFxScene());
/*     */ 
/* 324 */     if (!$assertionsDisabled) if ((this.swingDragSource == null ? 1 : 0) == (this.fxDragSource == null ? 1 : 0)) throw new AssertionError();
/*     */ 
/* 326 */     if (this.swingDragSource != null) {
/* 327 */       return this.swingDragSource;
/*     */     }
/* 329 */     return this.fxDragSource;
/*     */   }
/*     */ 
/*     */   private EmbeddedSceneDropTargetInterface getDropTarget() {
/* 333 */     assert (hasFxScene());
/*     */ 
/* 335 */     if (this.dropTarget == null) {
/* 336 */       this.dropTarget = getFxScene().createDropTarget();
/*     */     }
/* 338 */     return this.dropTarget;
/*     */   }
/*     */ 
/*     */   private void endDnD() {
/* 342 */     assert (this.dropTarget != null);
/*     */ 
/* 344 */     this.dropTarget = null;
/* 345 */     if (this.swingDragSource != null)
/* 346 */       this.swingDragSource = null;
/*     */   }
/*     */ 
/*     */   private void storeMouseEvent(MouseEvent paramMouseEvent)
/*     */   {
/* 351 */     this.me = paramMouseEvent;
/*     */   }
/*     */ 
/*     */   private static void applyDragResult(TransferMode paramTransferMode, DropTargetDragEvent paramDropTargetDragEvent)
/*     */   {
/* 356 */     if (paramTransferMode == null)
/* 357 */       paramDropTargetDragEvent.rejectDrag();
/*     */     else
/* 359 */       paramDropTargetDragEvent.acceptDrag(transferModeToDropAction(paramTransferMode));
/*     */   }
/*     */ 
/*     */   private static void applyDropResult(TransferMode paramTransferMode, DropTargetDropEvent paramDropTargetDropEvent)
/*     */   {
/* 365 */     if (paramTransferMode == null)
/* 366 */       paramDropTargetDropEvent.rejectDrop();
/*     */     else
/* 368 */       paramDropTargetDropEvent.acceptDrop(transferModeToDropAction(paramTransferMode));
/*     */   }
/*     */ 
/*     */   static TransferMode dropActionToTransferMode(int paramInt)
/*     */   {
/* 373 */     switch (paramInt) {
/*     */     case 1:
/* 375 */       return TransferMode.COPY;
/*     */     case 2:
/* 377 */       return TransferMode.MOVE;
/*     */     case 1073741824:
/* 379 */       return TransferMode.LINK;
/*     */     case 0:
/* 381 */       return null;
/*     */     }
/* 383 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   static int transferModeToDropAction(TransferMode paramTransferMode)
/*     */   {
/* 388 */     switch (5.$SwitchMap$javafx$scene$input$TransferMode[paramTransferMode.ordinal()]) {
/*     */     case 1:
/* 390 */       return 1;
/*     */     case 2:
/* 392 */       return 2;
/*     */     case 3:
/* 394 */       return 1073741824;
/*     */     }
/* 396 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   static Set<TransferMode> dropActionsToTransferModes(int paramInt)
/*     */   {
/* 402 */     EnumSet localEnumSet = EnumSet.noneOf(TransferMode.class);
/* 403 */     if ((paramInt & 0x1) != 0) {
/* 404 */       localEnumSet.add(TransferMode.COPY);
/*     */     }
/* 406 */     if ((paramInt & 0x2) != 0) {
/* 407 */       localEnumSet.add(TransferMode.MOVE);
/*     */     }
/* 409 */     if ((paramInt & 0x40000000) != 0) {
/* 410 */       localEnumSet.add(TransferMode.LINK);
/*     */     }
/* 412 */     return Collections.unmodifiableSet(localEnumSet);
/*     */   }
/*     */ 
/*     */   static int transferModesToDropActions(Set<TransferMode> paramSet) {
/* 416 */     int i = 0;
/* 417 */     for (TransferMode localTransferMode : paramSet) {
/* 418 */       i |= transferModeToDropAction(localTransferMode);
/*     */     }
/* 420 */     return i;
/*     */   }
/*     */ 
/*     */   private static void checkSwingEventDispatchThread()
/*     */   {
/* 482 */     if (!SwingUtilities.isEventDispatchThread())
/* 483 */       throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   private final class DnDTransferable
/*     */     implements Transferable
/*     */   {
/*     */     private DnDTransferable()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Object getTransferData(DataFlavor paramDataFlavor)
/*     */       throws UnsupportedFlavorException, IOException
/*     */     {
/* 431 */       SwingDnD.access$1400();
/*     */ 
/* 433 */       if (!SwingDnD.this.hasFxScene()) {
/* 434 */         return null;
/*     */       }
/*     */ 
/* 437 */       String str = DataFlavorUtils.getFxMimeType(paramDataFlavor);
/*     */ 
/* 439 */       return DataFlavorUtils.adjustFxData(paramDataFlavor, SwingDnD.this.getDragSource().getData(str));
/*     */     }
/*     */ 
/*     */     public DataFlavor[] getTransferDataFlavors()
/*     */     {
/* 445 */       SwingDnD.access$1400();
/*     */ 
/* 447 */       if (!SwingDnD.this.hasFxScene()) {
/* 448 */         return null;
/*     */       }
/*     */ 
/* 451 */       String[] arrayOfString1 = SwingDnD.this.getDragSource().getMimeTypes();
/*     */ 
/* 453 */       ArrayList localArrayList = new ArrayList(arrayOfString1.length);
/*     */ 
/* 455 */       for (String str : arrayOfString1) {
/* 456 */         DataFlavor localDataFlavor = null;
/*     */         try {
/* 458 */           localDataFlavor = new DataFlavor(str);
/*     */         }
/*     */         catch (ClassNotFoundException localClassNotFoundException) {
/* 461 */           continue;
/*     */         }
/* 463 */         localArrayList.add(localDataFlavor);
/*     */       }
/* 465 */       return (DataFlavor[])localArrayList.toArray(new DataFlavor[0]);
/*     */     }
/*     */ 
/*     */     public boolean isDataFlavorSupported(DataFlavor paramDataFlavor)
/*     */     {
/* 470 */       SwingDnD.access$1400();
/*     */ 
/* 472 */       if (!SwingDnD.this.hasFxScene()) {
/* 473 */         return false;
/*     */       }
/*     */ 
/* 476 */       return SwingDnD.this.getDragSource().isMimeTypeAvailable(DataFlavorUtils.getFxMimeType(paramDataFlavor));
/*     */     }
/*     */   }
/*     */ 
/*     */   static abstract interface JFXPanelFacade
/*     */   {
/*     */     public abstract EmbeddedSceneInterface getScene();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swing.SwingDnD
 * JD-Core Version:    0.6.2
 */