/*     */ package javafx.embed.swt;
/*     */ 
/*     */ import com.sun.javafx.application.PlatformImpl;
/*     */ import com.sun.javafx.cursor.CursorFrame;
/*     */ import com.sun.javafx.cursor.CursorType;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDragSourceInterface;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDragStartListenerInterface;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDropTargetInterface;
/*     */ import com.sun.javafx.embed.EmbeddedSceneInterface;
/*     */ import com.sun.javafx.embed.EmbeddedStageInterface;
/*     */ import com.sun.javafx.embed.HostInterface;
/*     */ import com.sun.javafx.stage.EmbeddedWindow;
/*     */ import java.nio.IntBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javafx.application.Platform;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.input.TransferMode;
/*     */ import org.eclipse.swt.SWT;
/*     */ import org.eclipse.swt.dnd.DragSource;
/*     */ import org.eclipse.swt.dnd.DragSourceEvent;
/*     */ import org.eclipse.swt.dnd.DragSourceListener;
/*     */ import org.eclipse.swt.dnd.DropTarget;
/*     */ import org.eclipse.swt.dnd.DropTargetEvent;
/*     */ import org.eclipse.swt.dnd.DropTargetListener;
/*     */ import org.eclipse.swt.dnd.FileTransfer;
/*     */ import org.eclipse.swt.dnd.HTMLTransfer;
/*     */ import org.eclipse.swt.dnd.ImageTransfer;
/*     */ import org.eclipse.swt.dnd.RTFTransfer;
/*     */ import org.eclipse.swt.dnd.TextTransfer;
/*     */ import org.eclipse.swt.dnd.Transfer;
/*     */ import org.eclipse.swt.dnd.TransferData;
/*     */ import org.eclipse.swt.dnd.URLTransfer;
/*     */ import org.eclipse.swt.events.ControlEvent;
/*     */ import org.eclipse.swt.events.ControlListener;
/*     */ import org.eclipse.swt.events.DisposeEvent;
/*     */ import org.eclipse.swt.events.DisposeListener;
/*     */ import org.eclipse.swt.events.FocusEvent;
/*     */ import org.eclipse.swt.events.FocusListener;
/*     */ import org.eclipse.swt.events.KeyEvent;
/*     */ import org.eclipse.swt.events.KeyListener;
/*     */ import org.eclipse.swt.events.MenuDetectEvent;
/*     */ import org.eclipse.swt.events.MenuDetectListener;
/*     */ import org.eclipse.swt.events.MouseEvent;
/*     */ import org.eclipse.swt.events.MouseListener;
/*     */ import org.eclipse.swt.events.MouseMoveListener;
/*     */ import org.eclipse.swt.events.MouseTrackListener;
/*     */ import org.eclipse.swt.events.MouseWheelListener;
/*     */ import org.eclipse.swt.events.PaintEvent;
/*     */ import org.eclipse.swt.events.PaintListener;
/*     */ import org.eclipse.swt.graphics.Cursor;
/*     */ import org.eclipse.swt.graphics.GC;
/*     */ import org.eclipse.swt.graphics.Image;
/*     */ import org.eclipse.swt.graphics.ImageData;
/*     */ import org.eclipse.swt.graphics.PaletteData;
/*     */ import org.eclipse.swt.graphics.Point;
/*     */ import org.eclipse.swt.graphics.Rectangle;
/*     */ import org.eclipse.swt.widgets.Canvas;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Display;
/*     */ 
/*     */ public class FXCanvas extends Canvas
/*     */ {
/*     */   private HostContainer hostContainer;
/*     */   private volatile EmbeddedWindow stage;
/*     */   private volatile Scene scene;
/*     */   private EmbeddedStageInterface stagePeer;
/*     */   private EmbeddedSceneInterface scenePeer;
/* 139 */   private int pWidth = 0;
/* 140 */   private int pHeight = 0;
/*     */ 
/* 142 */   private volatile int pPreferredWidth = -1;
/* 143 */   private volatile int pPreferredHeight = -1;
/*     */ 
/* 145 */   private IntBuffer pixelsBuf = null;
/*     */ 
/* 147 */   static Transfer[] StandardTransfers = { TextTransfer.getInstance(), RTFTransfer.getInstance(), HTMLTransfer.getInstance(), URLTransfer.getInstance(), ImageTransfer.getInstance(), FileTransfer.getInstance() };
/*     */ 
/* 155 */   static Transfer[] CustomTransfers = new Transfer[0];
/*     */   int lastWidth;
/*     */   int lastHeight;
/* 367 */   IntBuffer lastPixelsBuf = null;
/*     */ 
/*     */   static Transfer[] getAllTransfers()
/*     */   {
/* 158 */     Transfer[] arrayOfTransfer = new Transfer[StandardTransfers.length + CustomTransfers.length];
/* 159 */     System.arraycopy(StandardTransfers, 0, arrayOfTransfer, 0, StandardTransfers.length);
/* 160 */     System.arraycopy(CustomTransfers, 0, arrayOfTransfer, StandardTransfers.length, CustomTransfers.length);
/* 161 */     return arrayOfTransfer;
/*     */   }
/*     */ 
/*     */   static Transfer getCustomTransfer(String paramString) {
/* 165 */     for (int i = 0; i < CustomTransfers.length; i++) {
/* 166 */       if (((CustomTransfer)CustomTransfers[i]).getMime().equals(paramString)) {
/* 167 */         return CustomTransfers[i];
/*     */       }
/*     */     }
/* 170 */     CustomTransfer localCustomTransfer = new CustomTransfer(paramString, paramString);
/* 171 */     Transfer[] arrayOfTransfer = new Transfer[CustomTransfers.length + 1];
/* 172 */     System.arraycopy(CustomTransfers, 0, arrayOfTransfer, 0, CustomTransfers.length);
/* 173 */     arrayOfTransfer[CustomTransfers.length] = localCustomTransfer;
/* 174 */     CustomTransfers = arrayOfTransfer;
/* 175 */     return localCustomTransfer;
/*     */   }
/*     */ 
/*     */   public FXCanvas(Composite paramComposite, int paramInt)
/*     */   {
/* 182 */     super(paramComposite, paramInt | 0x40000);
/* 183 */     initFx();
/* 184 */     this.hostContainer = new HostContainer(null);
/* 185 */     registerEventListeners();
/*     */   }
/*     */ 
/*     */   private static void initFx() {
/* 189 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 191 */         System.setProperty("javafx.macosx.embedded", "true");
/* 192 */         System.setProperty("javafx.embed.isEventThread", "true");
/* 193 */         return null;
/*     */       }
/*     */     });
/* 197 */     PlatformImpl.startup(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/* 208 */     checkWidget();
/* 209 */     if ((paramInt1 == -1) && (paramInt2 == -1) && 
/* 210 */       (this.pPreferredWidth != -1) && (this.pPreferredHeight != -1)) {
/* 211 */       return new Point(this.pPreferredWidth, this.pPreferredHeight);
/*     */     }
/*     */ 
/* 214 */     return super.computeSize(paramInt1, paramInt2, paramBoolean);
/*     */   }
/*     */ 
/*     */   public Scene getScene()
/*     */   {
/* 223 */     checkWidget();
/* 224 */     return this.scene;
/*     */   }
/*     */ 
/*     */   public void setScene(Scene paramScene)
/*     */   {
/* 238 */     checkWidget();
/*     */ 
/* 240 */     if ((this.stage == null) && (paramScene != null)) {
/* 241 */       this.stage = new EmbeddedWindow(this.hostContainer);
/* 242 */       this.stage.show();
/*     */     }
/* 244 */     this.scene = paramScene;
/* 245 */     if (this.stage != null) {
/* 246 */       this.stage.setScene(paramScene);
/*     */     }
/* 248 */     if ((this.stage != null) && (paramScene == null)) {
/* 249 */       this.stage.hide();
/* 250 */       this.stage = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void registerEventListeners()
/*     */   {
/* 256 */     addDisposeListener(new DisposeListener()
/*     */     {
/*     */       public void widgetDisposed(DisposeEvent paramAnonymousDisposeEvent) {
/* 259 */         FXCanvas.this.widgetDisposed(paramAnonymousDisposeEvent);
/*     */       }
/*     */     });
/* 263 */     addPaintListener(new PaintListener()
/*     */     {
/*     */       public void paintControl(PaintEvent paramAnonymousPaintEvent) {
/* 266 */         FXCanvas.this.paintControl(paramAnonymousPaintEvent);
/*     */       }
/*     */     });
/* 270 */     addMouseListener(new MouseListener()
/*     */     {
/*     */       public void mouseDoubleClick(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*     */       }
/*     */ 
/*     */       public void mouseDown(MouseEvent paramAnonymousMouseEvent) {
/* 277 */         FXCanvas.this.sendMouseEventToFX(paramAnonymousMouseEvent, 0);
/*     */       }
/*     */ 
/*     */       public void mouseUp(MouseEvent paramAnonymousMouseEvent) {
/* 281 */         FXCanvas.this.sendMouseEventToFX(paramAnonymousMouseEvent, 1);
/*     */       }
/*     */     });
/* 285 */     addMouseMoveListener(new MouseMoveListener()
/*     */     {
/*     */       public void mouseMove(MouseEvent paramAnonymousMouseEvent) {
/* 288 */         if ((paramAnonymousMouseEvent.stateMask & SWT.BUTTON_MASK) != 0)
/* 289 */           FXCanvas.this.sendMouseEventToFX(paramAnonymousMouseEvent, 6);
/*     */         else
/* 291 */           FXCanvas.this.sendMouseEventToFX(paramAnonymousMouseEvent, 5);
/*     */       }
/*     */     });
/* 296 */     addMouseWheelListener(new MouseWheelListener()
/*     */     {
/*     */       public void mouseScrolled(MouseEvent paramAnonymousMouseEvent) {
/* 299 */         FXCanvas.this.sendMouseEventToFX(paramAnonymousMouseEvent, 7);
/*     */       }
/*     */     });
/* 303 */     addMouseTrackListener(new MouseTrackListener()
/*     */     {
/*     */       public void mouseEnter(MouseEvent paramAnonymousMouseEvent) {
/* 306 */         FXCanvas.this.sendMouseEventToFX(paramAnonymousMouseEvent, 3);
/*     */       }
/*     */ 
/*     */       public void mouseExit(MouseEvent paramAnonymousMouseEvent) {
/* 310 */         FXCanvas.this.sendMouseEventToFX(paramAnonymousMouseEvent, 4);
/*     */       }
/*     */ 
/*     */       public void mouseHover(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*     */       }
/*     */     });
/* 318 */     addControlListener(new ControlListener()
/*     */     {
/*     */       public void controlMoved(ControlEvent paramAnonymousControlEvent) {
/* 321 */         FXCanvas.this.sendMoveEventToFX();
/*     */       }
/*     */ 
/*     */       public void controlResized(ControlEvent paramAnonymousControlEvent) {
/* 325 */         FXCanvas.this.sendResizeEventToFX();
/*     */       }
/*     */     });
/* 329 */     addFocusListener(new FocusListener()
/*     */     {
/*     */       public void focusGained(FocusEvent paramAnonymousFocusEvent) {
/* 332 */         FXCanvas.this.sendFocusEventToFX(paramAnonymousFocusEvent, true);
/*     */       }
/*     */ 
/*     */       public void focusLost(FocusEvent paramAnonymousFocusEvent) {
/* 336 */         FXCanvas.this.sendFocusEventToFX(paramAnonymousFocusEvent, false);
/*     */       }
/*     */     });
/* 340 */     addKeyListener(new KeyListener()
/*     */     {
/*     */       public void keyPressed(KeyEvent paramAnonymousKeyEvent) {
/* 343 */         FXCanvas.this.sendKeyEventToFX(paramAnonymousKeyEvent, 1);
/*     */       }
/*     */ 
/*     */       public void keyReleased(KeyEvent paramAnonymousKeyEvent)
/*     */       {
/* 348 */         FXCanvas.this.sendKeyEventToFX(paramAnonymousKeyEvent, 2);
/*     */       }
/*     */     });
/* 352 */     addMenuDetectListener(new MenuDetectListener()
/*     */     {
/*     */       public void menuDetected(MenuDetectEvent paramAnonymousMenuDetectEvent) {
/* 355 */         FXCanvas.this.sendMenuEventToFX(paramAnonymousMenuDetectEvent);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void widgetDisposed(DisposeEvent paramDisposeEvent) {
/* 361 */     if (this.stage != null)
/* 362 */       this.stage.hide();
/*     */   }
/*     */ 
/*     */   private void paintControl(PaintEvent paramPaintEvent)
/*     */   {
/* 369 */     if ((this.scenePeer == null) || (this.pixelsBuf == null)) {
/* 370 */       return;
/*     */     }
/*     */ 
/* 374 */     IntBuffer localIntBuffer = this.pixelsBuf;
/* 375 */     int i = this.pWidth; int j = this.pHeight;
/* 376 */     if (this.scenePeer.getPixels(this.pixelsBuf, this.pWidth, this.pHeight)) {
/* 377 */       i = this.lastWidth = this.pWidth;
/* 378 */       j = this.lastHeight = this.pHeight;
/* 379 */       localIntBuffer = this.lastPixelsBuf = this.pixelsBuf;
/*     */     } else {
/* 381 */       if (this.lastPixelsBuf == null) return;
/* 382 */       i = this.lastWidth;
/* 383 */       j = this.lastHeight;
/* 384 */       localIntBuffer = this.lastPixelsBuf;
/*     */     }
/*     */ 
/* 388 */     ImageData localImageData = null;
/* 389 */     if ("win32".equals(SWT.getPlatform())) {
/* 390 */       localObject = new PaletteData(65280, 16711680, -16777216);
/* 391 */       int k = i * 4;
/* 392 */       byte[] arrayOfByte = new byte[k * j];
/* 393 */       int[] arrayOfInt = localIntBuffer.array();
/* 394 */       int m = 0; int n = 0;
/* 395 */       for (int i1 = 0; i1 < j; i1++) {
/* 396 */         for (int i2 = 0; i2 < i; i2++) {
/* 397 */           int i3 = arrayOfInt[(n++)];
/* 398 */           arrayOfByte[(m++)] = ((byte)(i3 & 0xFF));
/* 399 */           arrayOfByte[(m++)] = ((byte)(i3 >> 8 & 0xFF));
/* 400 */           arrayOfByte[(m++)] = ((byte)(i3 >> 16 & 0xFF));
/* 401 */           arrayOfByte[(m++)] = 0;
/*     */         }
/*     */       }
/* 404 */       localImageData = new ImageData(i, j, 32, (PaletteData)localObject, 4, arrayOfByte);
/*     */     } else {
/* 406 */       localObject = new PaletteData(16711680, 65280, 255);
/* 407 */       localImageData = new ImageData(i, j, 32, (PaletteData)localObject);
/* 408 */       localImageData.setPixels(0, 0, i * j, localIntBuffer.array(), 0);
/*     */     }
/*     */ 
/* 411 */     Object localObject = new Image(Display.getDefault(), localImageData);
/* 412 */     paramPaintEvent.gc.drawImage((Image)localObject, 0, 0);
/* 413 */     ((Image)localObject).dispose();
/*     */   }
/*     */ 
/*     */   private void sendMoveEventToFX() {
/* 417 */     if (this.stagePeer == null) {
/* 418 */       return;
/*     */     }
/* 420 */     Point localPoint = toDisplay(getLocation());
/* 421 */     this.stagePeer.setLocation(localPoint.x, localPoint.y);
/*     */   }
/*     */ 
/*     */   private void sendMouseEventToFX(MouseEvent paramMouseEvent, int paramInt) {
/* 425 */     if (this.scenePeer == null) {
/* 426 */       return;
/*     */     }
/*     */ 
/* 429 */     Point localPoint = toDisplay(paramMouseEvent.x, paramMouseEvent.y);
/* 430 */     boolean bool1 = (paramMouseEvent.stateMask & 0x80000) != 0;
/* 431 */     boolean bool2 = (paramMouseEvent.stateMask & 0x100000) != 0;
/* 432 */     boolean bool3 = (paramMouseEvent.stateMask & 0x200000) != 0;
/* 433 */     boolean bool4 = (paramMouseEvent.stateMask & 0x20000) != 0;
/* 434 */     boolean bool5 = (paramMouseEvent.stateMask & 0x40000) != 0;
/* 435 */     boolean bool6 = (paramMouseEvent.stateMask & 0x10000) != 0;
/* 436 */     boolean bool7 = (paramMouseEvent.stateMask & 0x400000) != 0;
/* 437 */     switch (paramInt) {
/*     */     case 0:
/* 439 */       bool1 |= paramMouseEvent.button == 1;
/* 440 */       bool2 |= paramMouseEvent.button == 2;
/* 441 */       bool3 |= paramMouseEvent.button == 3;
/* 442 */       break;
/*     */     case 1:
/* 444 */       bool1 &= paramMouseEvent.button != 1;
/* 445 */       bool2 &= paramMouseEvent.button != 2;
/* 446 */       bool3 &= paramMouseEvent.button != 3;
/*     */     }
/*     */ 
/* 449 */     this.scenePeer.mouseEvent(paramInt, SWTEvents.mouseButtonToEmbedMouseButton(paramMouseEvent.button, paramMouseEvent.stateMask), bool1, bool2, bool3, paramMouseEvent.count, paramMouseEvent.x, paramMouseEvent.y, localPoint.x, localPoint.y, bool4, bool5, bool6, bool7, SWTEvents.getWheelRotation(paramMouseEvent, paramInt), false);
/*     */   }
/*     */ 
/*     */   private void sendKeyEventToFX(KeyEvent paramKeyEvent, int paramInt)
/*     */   {
/* 461 */     if (this.scenePeer == null) {
/* 462 */       return;
/*     */     }
/* 464 */     int i = paramKeyEvent.stateMask;
/* 465 */     if (paramInt == 1) {
/* 466 */       if (paramKeyEvent.keyCode == 131072) i |= 131072;
/* 467 */       if (paramKeyEvent.keyCode == 262144) i |= 262144;
/* 468 */       if (paramKeyEvent.keyCode == 65536) i |= 65536;
/* 469 */       if (paramKeyEvent.keyCode == 4194304) i |= 4194304; 
/*     */     }
/* 471 */     else { if (paramKeyEvent.keyCode == 131072) i &= -131073;
/* 472 */       if (paramKeyEvent.keyCode == 262144) i &= -262145;
/* 473 */       if (paramKeyEvent.keyCode == 65536) i &= -65537;
/* 474 */       if (paramKeyEvent.keyCode == 4194304) i &= -4194305;
/*     */     }
/* 476 */     int j = SWTEvents.keyCodeToEmbedKeyCode(paramKeyEvent.keyCode);
/* 477 */     this.scenePeer.keyEvent(SWTEvents.keyIDToEmbedKeyType(paramInt), j, new char[0], SWTEvents.keyModifiersToEmbedKeyModifiers(i));
/*     */ 
/* 481 */     if ((paramKeyEvent.character != 0) && (paramInt == 1)) {
/* 482 */       char[] arrayOfChar = { paramKeyEvent.character };
/* 483 */       this.scenePeer.keyEvent(2, paramKeyEvent.keyCode, arrayOfChar, SWTEvents.keyModifiersToEmbedKeyModifiers(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void sendMenuEventToFX(MenuDetectEvent paramMenuDetectEvent)
/*     */   {
/* 491 */     if (this.scenePeer == null) {
/* 492 */       return;
/*     */     }
/* 494 */     Point localPoint = toControl(paramMenuDetectEvent.x, paramMenuDetectEvent.y);
/* 495 */     this.scenePeer.menuEvent(localPoint.x, localPoint.y, paramMenuDetectEvent.x, paramMenuDetectEvent.y, false);
/*     */   }
/*     */ 
/*     */   private void sendResizeEventToFX()
/*     */   {
/* 501 */     redraw();
/* 502 */     update();
/*     */ 
/* 504 */     this.pWidth = getClientArea().width;
/* 505 */     this.pHeight = getClientArea().height;
/*     */ 
/* 507 */     if ((this.pWidth <= 0) || (this.pHeight <= 0))
/* 508 */       this.pixelsBuf = (this.lastPixelsBuf = null);
/*     */     else {
/* 510 */       this.pixelsBuf = IntBuffer.allocate(this.pWidth * this.pHeight);
/*     */     }
/*     */ 
/* 513 */     if (this.scenePeer == null) {
/* 514 */       return;
/*     */     }
/*     */ 
/* 517 */     this.stagePeer.setSize(this.pWidth, this.pHeight);
/* 518 */     this.scenePeer.setSize(this.pWidth, this.pHeight);
/*     */   }
/*     */ 
/*     */   private void sendFocusEventToFX(FocusEvent paramFocusEvent, boolean paramBoolean) {
/* 522 */     if ((this.stage == null) || (this.stagePeer == null)) {
/* 523 */       return;
/*     */     }
/* 525 */     int i = paramBoolean ? 0 : 3;
/*     */ 
/* 528 */     this.stagePeer.setFocused(paramBoolean, i);
/*     */   }
/*     */ 
/*     */   private class HostContainer
/*     */     implements HostInterface
/*     */   {
/*     */     DropTarget dropTarget;
/* 823 */     Object lock = new Object();
/* 824 */     boolean queued = false;
/*     */ 
/*     */     private HostContainer()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void setEmbeddedStage(EmbeddedStageInterface paramEmbeddedStageInterface)
/*     */     {
/* 535 */       FXCanvas.this.stagePeer = paramEmbeddedStageInterface;
/* 536 */       if (FXCanvas.this.stagePeer == null) {
/* 537 */         return;
/*     */       }
/* 539 */       if ((FXCanvas.this.pWidth > 0) && (FXCanvas.this.pHeight > 0)) {
/* 540 */         FXCanvas.this.stagePeer.setSize(FXCanvas.this.pWidth, FXCanvas.this.pHeight);
/*     */       }
/* 542 */       if (FXCanvas.this.isFocusControl()) {
/* 543 */         FXCanvas.this.stagePeer.setFocused(true, 0);
/*     */       }
/* 545 */       FXCanvas.this.sendMoveEventToFX();
/* 546 */       FXCanvas.this.sendResizeEventToFX();
/*     */     }
/*     */ 
/*     */     TransferMode getTransferMode(int paramInt) {
/* 550 */       switch (paramInt) {
/*     */       case 1:
/* 552 */         return TransferMode.COPY;
/*     */       case 2:
/*     */       case 8:
/* 555 */         return TransferMode.MOVE;
/*     */       case 4:
/* 557 */         return TransferMode.LINK;
/*     */       case 3:
/*     */       case 5:
/*     */       case 6:
/* 559 */       case 7: } return null;
/*     */     }
/*     */ 
/*     */     Set<TransferMode> getTransferModes(int paramInt)
/*     */     {
/* 564 */       HashSet localHashSet = new HashSet();
/* 565 */       if ((paramInt & 0x1) != 0) localHashSet.add(TransferMode.COPY);
/* 566 */       if ((paramInt & 0x2) != 0) localHashSet.add(TransferMode.MOVE);
/* 567 */       if ((paramInt & 0x8) != 0) localHashSet.add(TransferMode.MOVE);
/* 568 */       if ((paramInt & 0x4) != 0) localHashSet.add(TransferMode.LINK);
/* 569 */       return localHashSet;
/*     */     }
/*     */ 
/*     */     private DragSource createDragSource(final EmbeddedSceneDragSourceInterface paramEmbeddedSceneDragSourceInterface, TransferMode paramTransferMode)
/*     */     {
/* 574 */       Transfer[] arrayOfTransfer = getTransferTypes(paramEmbeddedSceneDragSourceInterface.getMimeTypes());
/* 575 */       if (arrayOfTransfer.length == 0) return null;
/* 576 */       int i = getDragActions(paramEmbeddedSceneDragSourceInterface.getSupportedActions());
/* 577 */       final DragSource localDragSource = new DragSource(FXCanvas.this, i);
/* 578 */       localDragSource.setTransfer(arrayOfTransfer);
/* 579 */       localDragSource.addDragListener(new DragSourceListener() {
/*     */         public void dragFinished(DragSourceEvent paramAnonymousDragSourceEvent) {
/* 581 */           localDragSource.dispose();
/* 582 */           paramEmbeddedSceneDragSourceInterface.dragDropEnd(FXCanvas.HostContainer.this.getTransferMode(paramAnonymousDragSourceEvent.detail));
/*     */         }
/*     */         public void dragSetData(DragSourceEvent paramAnonymousDragSourceEvent) {
/* 585 */           Transfer[] arrayOfTransfer = localDragSource.getTransfer();
/* 586 */           for (int i = 0; i < arrayOfTransfer.length; i++) {
/* 587 */             if (arrayOfTransfer[i].isSupportedType(paramAnonymousDragSourceEvent.dataType)) {
/* 588 */               String str = FXCanvas.HostContainer.this.getMime(arrayOfTransfer[i]);
/* 589 */               if (str != null) {
/* 590 */                 paramAnonymousDragSourceEvent.doit = true;
/* 591 */                 paramAnonymousDragSourceEvent.data = paramEmbeddedSceneDragSourceInterface.getData(str);
/* 592 */                 return;
/*     */               }
/*     */             }
/* 595 */             paramAnonymousDragSourceEvent.doit = false;
/*     */           }
/*     */         }
/*     */ 
/*     */         public void dragStart(DragSourceEvent paramAnonymousDragSourceEvent)
/*     */         {
/*     */         }
/*     */       });
/* 601 */       return localDragSource;
/*     */     }
/*     */ 
/*     */     int getDragAction(TransferMode paramTransferMode) {
/* 605 */       if (paramTransferMode == null) return 0;
/* 606 */       switch (FXCanvas.13.$SwitchMap$javafx$scene$input$TransferMode[paramTransferMode.ordinal()]) { case 1:
/* 607 */         return 1;
/*     */       case 2:
/* 608 */         return 2;
/*     */       case 3:
/* 609 */         return 4;
/*     */       }
/* 611 */       throw new IllegalArgumentException("Invalid transfer mode");
/*     */     }
/*     */ 
/*     */     int getDragActions(Set<TransferMode> paramSet)
/*     */     {
/* 616 */       int i = 0;
/* 617 */       for (TransferMode localTransferMode : paramSet) {
/* 618 */         i |= getDragAction(localTransferMode);
/*     */       }
/* 620 */       return i;
/*     */     }
/*     */ 
/*     */     Transfer getTransferType(String paramString) {
/* 624 */       if (paramString.equals("text/plain")) return TextTransfer.getInstance();
/* 625 */       if (paramString.equals("text/rtf")) return RTFTransfer.getInstance();
/* 626 */       if (paramString.equals("text/html")) return HTMLTransfer.getInstance();
/* 627 */       if (paramString.equals("text/uri-list")) return URLTransfer.getInstance();
/* 628 */       if (paramString.equals("application/x-java-rawimage")) return ImageTransfer.getInstance();
/* 629 */       if ((paramString.equals("application/x-java-file-list")) || (paramString.equals("java.file-list"))) {
/* 630 */         return FileTransfer.getInstance();
/*     */       }
/* 632 */       return FXCanvas.getCustomTransfer(paramString);
/*     */     }
/*     */ 
/*     */     Transfer[] getTransferTypes(String[] paramArrayOfString) {
/* 636 */       int i = 0;
/* 637 */       Object localObject = new Transfer[paramArrayOfString.length];
/* 638 */       for (int j = 0; j < paramArrayOfString.length; j++) {
/* 639 */         Transfer localTransfer = getTransferType(paramArrayOfString[j]);
/* 640 */         if (localTransfer != null) localObject[(i++)] = localTransfer;
/*     */       }
/* 642 */       if (i != paramArrayOfString.length) {
/* 643 */         Transfer[] arrayOfTransfer = new Transfer[i];
/* 644 */         System.arraycopy(localObject, 0, arrayOfTransfer, 0, i);
/* 645 */         localObject = arrayOfTransfer;
/*     */       }
/* 647 */       return localObject;
/*     */     }
/*     */ 
/*     */     String getMime(Transfer paramTransfer) {
/* 651 */       if (paramTransfer.equals(TextTransfer.getInstance())) return "text/plain";
/* 652 */       if (paramTransfer.equals(RTFTransfer.getInstance())) return "text/rtf";
/* 653 */       if (paramTransfer.equals(HTMLTransfer.getInstance())) return "text/html";
/* 654 */       if (paramTransfer.equals(URLTransfer.getInstance())) return "text/uri-list";
/* 655 */       if (paramTransfer.equals(ImageTransfer.getInstance())) return "application/x-java-rawimage";
/* 656 */       if (paramTransfer.equals(FileTransfer.getInstance())) return "application/x-java-file-list";
/*     */ 
/* 659 */       if ((paramTransfer instanceof CustomTransfer)) return ((CustomTransfer)paramTransfer).getMime();
/* 660 */       return null;
/*     */     }
/*     */ 
/*     */     String[] getMimes(Transfer[] paramArrayOfTransfer, TransferData paramTransferData) {
/* 664 */       int i = 0;
/* 665 */       Object localObject = new String[paramArrayOfTransfer.length];
/* 666 */       for (int j = 0; j < paramArrayOfTransfer.length; j++) {
/* 667 */         if (paramArrayOfTransfer[j].isSupportedType(paramTransferData)) {
/* 668 */           localObject[(i++)] = getMime(paramArrayOfTransfer[j]);
/*     */         }
/*     */       }
/* 671 */       if (i != localObject.length) {
/* 672 */         String[] arrayOfString = new String[i];
/* 673 */         System.arraycopy(localObject, 0, arrayOfString, 0, i);
/* 674 */         localObject = arrayOfString;
/*     */       }
/* 676 */       return localObject;
/*     */     }
/*     */ 
/*     */     DropTarget createDropTarget(EmbeddedSceneInterface paramEmbeddedSceneInterface) {
/* 680 */       final DropTarget localDropTarget = new DropTarget(FXCanvas.this, 7);
/* 681 */       final EmbeddedSceneDropTargetInterface localEmbeddedSceneDropTargetInterface = paramEmbeddedSceneInterface.createDropTarget();
/* 682 */       localDropTarget.setTransfer(FXCanvas.getAllTransfers());
/* 683 */       localDropTarget.addDropListener(new DropTargetListener()
/*     */       {
/*     */         Object data;
/*     */         TransferData[] transferData;
/*     */         TransferData currentTransferData;
/*     */         boolean ignoreLeave;
/* 688 */         int detail = 0; int operations = 0;
/* 689 */         EmbeddedSceneDragSourceInterface fxDragSource = new EmbeddedSceneDragSourceInterface() {
/*     */           public Set<TransferMode> getSupportedActions() {
/* 691 */             return FXCanvas.HostContainer.this.getTransferModes(FXCanvas.HostContainer.2.this.operations);
/*     */           }
/*     */ 
/*     */           public Object getData(String paramAnonymous2String) {
/* 695 */             return FXCanvas.HostContainer.2.this.data;
/*     */           }
/*     */           public String[] getMimeTypes() {
/* 698 */             if (FXCanvas.HostContainer.2.this.currentTransferData == null) return new String[0];
/* 699 */             return FXCanvas.HostContainer.this.getMimes(FXCanvas.getAllTransfers(), FXCanvas.HostContainer.2.this.currentTransferData);
/*     */           }
/*     */           public boolean isMimeTypeAvailable(String paramAnonymous2String) {
/* 702 */             String[] arrayOfString = getMimeTypes();
/* 703 */             for (int i = 0; i < arrayOfString.length; i++) {
/* 704 */               if (arrayOfString[i].equals(paramAnonymous2String)) return true;
/*     */             }
/* 706 */             return false;
/*     */           }
/*     */           public void dragDropEnd(TransferMode paramAnonymous2TransferMode) {
/* 709 */             FXCanvas.HostContainer.2.this.data = null;
/* 710 */             FXCanvas.HostContainer.2.this.transferData = null;
/* 711 */             FXCanvas.HostContainer.2.this.currentTransferData = null;
/*     */           }
/* 689 */         };
/*     */ 
/*     */         public void dragEnter(DropTargetEvent paramAnonymousDropTargetEvent)
/*     */         {
/* 715 */           localDropTarget.setTransfer(FXCanvas.getAllTransfers());
/* 716 */           this.detail = paramAnonymousDropTargetEvent.detail;
/* 717 */           this.operations = paramAnonymousDropTargetEvent.operations;
/* 718 */           dragOver(paramAnonymousDropTargetEvent, true, this.detail);
/*     */         }
/*     */         public void dragLeave(DropTargetEvent paramAnonymousDropTargetEvent) {
/* 721 */           this.detail = (this.operations = 0);
/* 722 */           this.data = null;
/* 723 */           this.transferData = null;
/* 724 */           this.currentTransferData = null;
/* 725 */           FXCanvas.this.getDisplay().asyncExec(new Runnable() {
/*     */             public void run() {
/* 727 */               if (FXCanvas.HostContainer.2.this.ignoreLeave) return;
/* 728 */               FXCanvas.HostContainer.2.this.val$fxDropTarget.handleDragLeave();
/*     */             } } );
/*     */         }
/*     */ 
/*     */         public void dragOperationChanged(DropTargetEvent paramAnonymousDropTargetEvent) {
/* 733 */           this.detail = paramAnonymousDropTargetEvent.detail;
/* 734 */           this.operations = paramAnonymousDropTargetEvent.operations;
/* 735 */           dragOver(paramAnonymousDropTargetEvent, false, this.detail);
/*     */         }
/*     */         public void dragOver(DropTargetEvent paramAnonymousDropTargetEvent) {
/* 738 */           this.operations = paramAnonymousDropTargetEvent.operations;
/* 739 */           dragOver(paramAnonymousDropTargetEvent, false, this.detail);
/*     */         }
/*     */         public void dragOver(DropTargetEvent paramAnonymousDropTargetEvent, boolean paramAnonymousBoolean, int paramAnonymousInt) {
/* 742 */           this.transferData = paramAnonymousDropTargetEvent.dataTypes;
/* 743 */           this.currentTransferData = paramAnonymousDropTargetEvent.currentDataType;
/* 744 */           Point localPoint = FXCanvas.this.toControl(paramAnonymousDropTargetEvent.x, paramAnonymousDropTargetEvent.y);
/* 745 */           if (paramAnonymousInt == 0) paramAnonymousInt = 1;
/* 746 */           TransferMode localTransferMode2 = FXCanvas.HostContainer.this.getTransferMode(paramAnonymousInt);
/*     */           TransferMode localTransferMode1;
/* 747 */           if (paramAnonymousBoolean)
/* 748 */             localTransferMode1 = localEmbeddedSceneDropTargetInterface.handleDragEnter(localPoint.x, localPoint.y, paramAnonymousDropTargetEvent.x, paramAnonymousDropTargetEvent.y, localTransferMode2, this.fxDragSource);
/*     */           else {
/* 750 */             localTransferMode1 = localEmbeddedSceneDropTargetInterface.handleDragOver(localPoint.x, localPoint.y, paramAnonymousDropTargetEvent.x, paramAnonymousDropTargetEvent.y, localTransferMode2);
/*     */           }
/* 752 */           paramAnonymousDropTargetEvent.detail = FXCanvas.HostContainer.this.getDragAction(localTransferMode1);
/*     */         }
/*     */         public void drop(DropTargetEvent paramAnonymousDropTargetEvent) {
/* 755 */           this.detail = paramAnonymousDropTargetEvent.detail;
/* 756 */           this.operations = paramAnonymousDropTargetEvent.operations;
/* 757 */           this.data = paramAnonymousDropTargetEvent.data;
/* 758 */           this.transferData = paramAnonymousDropTargetEvent.dataTypes;
/* 759 */           this.currentTransferData = paramAnonymousDropTargetEvent.currentDataType;
/* 760 */           Point localPoint = FXCanvas.this.toControl(paramAnonymousDropTargetEvent.x, paramAnonymousDropTargetEvent.y);
/* 761 */           TransferMode localTransferMode1 = FXCanvas.HostContainer.this.getTransferMode(paramAnonymousDropTargetEvent.detail);
/* 762 */           TransferMode localTransferMode2 = localEmbeddedSceneDropTargetInterface.handleDragDrop(localPoint.x, localPoint.y, paramAnonymousDropTargetEvent.x, paramAnonymousDropTargetEvent.y, localTransferMode1);
/* 763 */           paramAnonymousDropTargetEvent.detail = FXCanvas.HostContainer.this.getDragAction(localTransferMode2);
/* 764 */           this.data = null;
/* 765 */           this.transferData = null;
/* 766 */           this.currentTransferData = null;
/*     */         }
/*     */         public void dropAccept(DropTargetEvent paramAnonymousDropTargetEvent) {
/* 769 */           this.ignoreLeave = true;
/*     */         }
/*     */       });
/* 772 */       return localDropTarget;
/*     */     }
/*     */ 
/*     */     public void setEmbeddedScene(EmbeddedSceneInterface paramEmbeddedSceneInterface)
/*     */     {
/* 778 */       FXCanvas.this.scenePeer = paramEmbeddedSceneInterface;
/* 779 */       if (FXCanvas.this.scenePeer == null) {
/* 780 */         return;
/*     */       }
/* 782 */       if ((FXCanvas.this.pWidth > 0) && (FXCanvas.this.pHeight > 0)) {
/* 783 */         FXCanvas.this.scenePeer.setSize(FXCanvas.this.pWidth, FXCanvas.this.pHeight);
/*     */       }
/* 785 */       FXCanvas.this.scenePeer.setDragStartListener(new EmbeddedSceneDragStartListenerInterface()
/*     */       {
/*     */         public void dragStarted(final EmbeddedSceneDragSourceInterface paramAnonymousEmbeddedSceneDragSourceInterface, final TransferMode paramAnonymousTransferMode) {
/* 788 */           Platform.runLater(new Runnable() {
/*     */             public void run() {
/* 790 */               DragSource localDragSource = FXCanvas.HostContainer.this.createDragSource(paramAnonymousEmbeddedSceneDragSourceInterface, paramAnonymousTransferMode);
/* 791 */               if (localDragSource == null) {
/* 792 */                 paramAnonymousEmbeddedSceneDragSourceInterface.dragDropEnd(null);
/*     */               } else {
/* 794 */                 if (FXCanvas.HostContainer.this.dropTarget != null) FXCanvas.HostContainer.this.dropTarget.setTransfer(FXCanvas.getAllTransfers());
/* 795 */                 FXCanvas.this.notifyListeners(29, null);
/*     */               }
/*     */             }
/*     */           });
/*     */         }
/*     */       });
/* 801 */       if (this.dropTarget != null) this.dropTarget.dispose();
/* 802 */       this.dropTarget = createDropTarget(paramEmbeddedSceneInterface);
/*     */     }
/*     */ 
/*     */     public boolean requestFocus()
/*     */     {
/* 807 */       Display.getDefault().asyncExec(new Runnable()
/*     */       {
/*     */         public void run() {
/* 810 */           if (FXCanvas.this.isDisposed()) return;
/* 811 */           FXCanvas.this.forceFocus();
/*     */         }
/*     */       });
/* 814 */       return true;
/*     */     }
/*     */ 
/*     */     public boolean traverseFocusOut(boolean paramBoolean)
/*     */     {
/* 820 */       return true;
/*     */     }
/*     */ 
/*     */     public void repaint()
/*     */     {
/* 826 */       synchronized (this.lock) {
/* 827 */         if (this.queued) return;
/* 828 */         this.queued = true;
/* 829 */         Display.getDefault().asyncExec(new Object()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 833 */               if (FXCanvas.this.isDisposed()) return;
/* 834 */               FXCanvas.this.redraw();
/* 835 */               FXCanvas.this.sendMoveEventToFX();
/*     */             } finally {
/* 837 */               synchronized (FXCanvas.HostContainer.this.lock) {
/* 838 */                 FXCanvas.HostContainer.this.queued = false;
/*     */               }
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */ 
/*     */     public void setPreferredSize(int paramInt1, int paramInt2)
/*     */     {
/* 848 */       FXCanvas.this.pPreferredWidth = paramInt1;
/* 849 */       FXCanvas.this.pPreferredHeight = paramInt2;
/*     */     }
/*     */ 
/*     */     public void setEnabled(boolean paramBoolean)
/*     */     {
/* 855 */       FXCanvas.this.setEnabled(paramBoolean);
/*     */     }
/*     */ 
/*     */     public void setCursor(CursorFrame paramCursorFrame)
/*     */     {
/* 860 */       FXCanvas.this.setCursor(getPlatformCursor(paramCursorFrame));
/*     */     }
/*     */ 
/*     */     private Cursor getPlatformCursor(CursorFrame paramCursorFrame)
/*     */     {
/* 870 */       if (paramCursorFrame.getCursorType() == CursorType.DEFAULT) {
/* 871 */         return null;
/*     */       }
/* 873 */       Cursor localCursor1 = (Cursor)paramCursorFrame.getPlatformCursor(Cursor.class);
/*     */ 
/* 875 */       if (localCursor1 != null)
/*     */       {
/* 877 */         return localCursor1;
/*     */       }
/*     */ 
/* 881 */       Cursor localCursor2 = SWTCursors.embedCursorToCursor(paramCursorFrame);
/*     */ 
/* 883 */       paramCursorFrame.setPlatforCursor(Cursor.class, localCursor2);
/*     */ 
/* 885 */       return localCursor2;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swt.FXCanvas
 * JD-Core Version:    0.6.2
 */