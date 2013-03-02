/*     */ package javafx.embed.swing;
/*     */ 
/*     */ import com.sun.javafx.application.PlatformImpl;
/*     */ import com.sun.javafx.application.PlatformImpl.FinishListener;
/*     */ import com.sun.javafx.cursor.CursorFrame;
/*     */ import com.sun.javafx.embed.EmbeddedSceneInterface;
/*     */ import com.sun.javafx.embed.EmbeddedStageInterface;
/*     */ import com.sun.javafx.embed.HostInterface;
/*     */ import com.sun.javafx.stage.EmbeddedWindow;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.HierarchyEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.awt.im.InputContext;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.nio.IntBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import javafx.application.Platform;
/*     */ import javafx.scene.Scene;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingUtilities;
/*     */ import sun.awt.CausedFocusEvent;
/*     */ import sun.awt.CausedFocusEvent.Cause;
/*     */ 
/*     */ public class JFXPanel extends JComponent
/*     */ {
/*     */   private static PlatformImpl.FinishListener finishListener;
/* 118 */   private static boolean firstPanelShown = false;
/*     */   private HostContainer hostContainer;
/*     */   private volatile EmbeddedWindow stage;
/*     */   private volatile Scene scene;
/*     */   private final SwingDnD dnd;
/*     */   private EmbeddedStageInterface stagePeer;
/*     */   private EmbeddedSceneInterface scenePeer;
/* 131 */   private int pWidth = -1;
/* 132 */   private int pHeight = -1;
/*     */ 
/* 135 */   private volatile int pPreferredWidth = -1;
/* 136 */   private volatile int pPreferredHeight = -1;
/*     */ 
/* 140 */   private volatile int screenX = 0;
/* 141 */   private volatile int screenY = 0;
/*     */   private BufferedImage pixelsIm;
/* 145 */   private volatile float opacity = 1.0F;
/*     */ 
/* 149 */   private volatile int disableCount = 0;
/*     */ 
/* 151 */   private boolean isCapturingMouse = false;
/*     */ 
/*     */   private static void initFx()
/*     */   {
/* 155 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 157 */         System.setProperty("javafx.macosx.embedded", "true");
/* 158 */         return null;
/*     */       }
/*     */     });
/* 161 */     if (finishListener != null)
/*     */     {
/* 163 */       return;
/*     */     }
/*     */ 
/* 166 */     finishListener = new PlatformImpl.FinishListener() {
/*     */       public void idle(boolean paramAnonymousBoolean) {
/* 168 */         if (!JFXPanel.firstPanelShown) {
/* 169 */           return;
/*     */         }
/* 171 */         PlatformImpl.removeListener(JFXPanel.finishListener);
/* 172 */         JFXPanel.access$102(null);
/* 173 */         if (paramAnonymousBoolean)
/* 174 */           Platform.exit();
/*     */       }
/*     */ 
/*     */       public void exitCalled()
/*     */       {
/*     */       }
/*     */     };
/* 180 */     PlatformImpl.addListener(finishListener);
/*     */ 
/* 182 */     PlatformImpl.startup(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public JFXPanel()
/*     */   {
/* 199 */     initFx();
/*     */ 
/* 201 */     this.hostContainer = new HostContainer(null);
/*     */ 
/* 203 */     enableEvents(229437L);
/*     */ 
/* 212 */     setFocusable(true);
/* 213 */     setFocusTraversalKeysEnabled(false);
/*     */ 
/* 215 */     this.dnd = new SwingDnD(this, new SwingDnD.JFXPanelFacade()
/*     */     {
/*     */       public EmbeddedSceneInterface getScene()
/*     */       {
/* 219 */         return JFXPanel.this.isFxEnabled() ? JFXPanel.this.scenePeer : null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public Scene getScene()
/*     */   {
/* 230 */     return this.scene;
/*     */   }
/*     */ 
/*     */   public void setScene(final Scene paramScene)
/*     */   {
/* 244 */     if (Toolkit.getToolkit().isFxUserThread()) {
/* 245 */       setSceneImpl(paramScene);
/*     */     } else {
/* 247 */       final CountDownLatch localCountDownLatch = new CountDownLatch(1);
/* 248 */       Platform.runLater(new Runnable()
/*     */       {
/*     */         public void run() {
/* 251 */           JFXPanel.this.setSceneImpl(paramScene);
/* 252 */           localCountDownLatch.countDown();
/*     */         }
/*     */       });
/*     */       try {
/* 256 */         localCountDownLatch.await();
/*     */       } catch (InterruptedException localInterruptedException) {
/* 258 */         localInterruptedException.printStackTrace(System.err);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setSceneImpl(Scene paramScene)
/*     */   {
/* 267 */     if ((this.stage != null) && (paramScene == null)) {
/* 268 */       this.stage.hide();
/* 269 */       this.stage = null;
/*     */     }
/* 271 */     this.scene = paramScene;
/* 272 */     if ((this.stage == null) && (paramScene != null)) {
/* 273 */       this.stage = new EmbeddedWindow(this.hostContainer);
/*     */     }
/* 275 */     if (this.stage != null) {
/* 276 */       this.stage.setScene(paramScene);
/* 277 */       if (!this.stage.isShowing()) {
/* 278 */         this.stage.show();
/* 279 */         firstPanelShown = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void setOpaque(boolean paramBoolean)
/*     */   {
/* 296 */     if (!paramBoolean)
/* 297 */       super.setOpaque(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isOpaque()
/*     */   {
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */   private void sendMouseEventToFX(MouseEvent paramMouseEvent) {
/* 322 */     if ((this.scenePeer == null) || (!isFxEnabled())) {
/* 323 */       return;
/*     */     }
/*     */ 
/* 326 */     int i = paramMouseEvent.getModifiersEx();
/*     */ 
/* 329 */     boolean bool1 = (i & 0x400) != 0;
/* 330 */     boolean bool2 = (i & 0x800) != 0;
/* 331 */     boolean bool3 = (i & 0x1000) != 0;
/*     */ 
/* 334 */     if (paramMouseEvent.getID() == 506)
/*     */     {
/* 335 */       if (this.isCapturingMouse);
/*     */     }
/* 338 */     else if (paramMouseEvent.getID() == 501) {
/* 339 */       this.isCapturingMouse = true;
/* 340 */     } else if (paramMouseEvent.getID() == 502) {
/* 341 */       if (!this.isCapturingMouse) {
/* 342 */         return;
/*     */       }
/* 344 */       this.isCapturingMouse = ((bool1) || (bool2) || (bool3));
/*     */     }
/* 346 */     this.scenePeer.mouseEvent(SwingEvents.mouseIDToEmbedMouseType(paramMouseEvent.getID()), SwingEvents.mouseButtonToEmbedMouseButton(paramMouseEvent.getButton(), i), bool1, bool2, bool3, paramMouseEvent.getClickCount(), paramMouseEvent.getX(), paramMouseEvent.getY(), paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), (i & 0x40) != 0, (i & 0x80) != 0, (i & 0x200) != 0, (i & 0x100) != 0, SwingEvents.getWheelRotation(paramMouseEvent), paramMouseEvent.isPopupTrigger());
/*     */ 
/* 357 */     if (paramMouseEvent.isPopupTrigger())
/* 358 */       this.scenePeer.menuEvent(paramMouseEvent.getX(), paramMouseEvent.getY(), paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), false);
/*     */   }
/*     */ 
/*     */   protected void processMouseEvent(MouseEvent paramMouseEvent)
/*     */   {
/* 371 */     if ((paramMouseEvent.getID() == 501) && (paramMouseEvent.getButton() == 1))
/*     */     {
/* 373 */       if (!hasFocus()) {
/* 374 */         requestFocus();
/*     */       }
/*     */     }
/*     */ 
/* 378 */     sendMouseEventToFX(paramMouseEvent);
/* 379 */     super.processMouseEvent(paramMouseEvent);
/*     */   }
/*     */ 
/*     */   protected void processMouseMotionEvent(MouseEvent paramMouseEvent)
/*     */   {
/* 391 */     sendMouseEventToFX(paramMouseEvent);
/* 392 */     super.processMouseMotionEvent(paramMouseEvent);
/*     */   }
/*     */ 
/*     */   protected void processMouseWheelEvent(MouseWheelEvent paramMouseWheelEvent)
/*     */   {
/* 405 */     sendMouseEventToFX(paramMouseWheelEvent);
/* 406 */     super.processMouseWheelEvent(paramMouseWheelEvent);
/*     */   }
/*     */ 
/*     */   private void sendKeyEventToFX(KeyEvent paramKeyEvent) {
/* 410 */     if ((this.scenePeer == null) || (!isFxEnabled())) {
/* 411 */       return;
/*     */     }
/*     */ 
/* 414 */     char[] arrayOfChar = { paramKeyEvent.getKeyChar() == 65535 ? new char[0] : paramKeyEvent.getKeyChar() };
/*     */ 
/* 418 */     this.scenePeer.keyEvent(SwingEvents.keyIDToEmbedKeyType(paramKeyEvent.getID()), paramKeyEvent.getKeyCode(), arrayOfChar, SwingEvents.keyModifiersToEmbedKeyModifiers(paramKeyEvent.getModifiersEx()));
/*     */   }
/*     */ 
/*     */   protected void processKeyEvent(KeyEvent paramKeyEvent)
/*     */   {
/* 433 */     sendKeyEventToFX(paramKeyEvent);
/* 434 */     super.processKeyEvent(paramKeyEvent);
/*     */   }
/*     */ 
/*     */   private void sendResizeEventToFX() {
/* 438 */     if (this.stagePeer != null) {
/* 439 */       this.stagePeer.setSize(this.pWidth, this.pHeight);
/*     */     }
/* 441 */     if (this.scenePeer != null)
/* 442 */       this.scenePeer.setSize(this.pWidth, this.pHeight);
/*     */   }
/*     */ 
/*     */   protected void processComponentEvent(ComponentEvent paramComponentEvent)
/*     */   {
/* 457 */     switch (paramComponentEvent.getID())
/*     */     {
/*     */     case 101:
/* 461 */       this.pWidth = Math.max(0, getWidth());
/* 462 */       this.pHeight = Math.max(0, getHeight());
/* 463 */       resizePixels();
/* 464 */       sendResizeEventToFX();
/* 465 */       break;
/*     */     case 100:
/* 468 */       if (updateScreenLocation()) {
/* 469 */         sendMoveEventToFX();
/*     */       }
/*     */       break;
/*     */     }
/*     */ 
/* 474 */     super.processComponentEvent(paramComponentEvent);
/*     */   }
/*     */ 
/*     */   private boolean updateScreenLocation()
/*     */   {
/* 479 */     synchronized (getTreeLock()) {
/* 480 */       if (isShowing()) {
/* 481 */         Point localPoint = getLocationOnScreen();
/* 482 */         this.screenX = localPoint.x;
/* 483 */         this.screenY = localPoint.y;
/* 484 */         return true;
/*     */       }
/*     */     }
/* 487 */     return false;
/*     */   }
/*     */ 
/*     */   private void sendMoveEventToFX() {
/* 491 */     if (this.stagePeer == null) {
/* 492 */       return;
/*     */     }
/*     */ 
/* 495 */     this.stagePeer.setLocation(this.screenX, this.screenY);
/*     */   }
/*     */ 
/*     */   protected void processHierarchyBoundsEvent(HierarchyEvent paramHierarchyEvent)
/*     */   {
/* 509 */     if ((paramHierarchyEvent.getID() == 1401) && 
/* 510 */       (updateScreenLocation())) {
/* 511 */       sendMoveEventToFX();
/*     */     }
/*     */ 
/* 514 */     super.processHierarchyBoundsEvent(paramHierarchyEvent);
/*     */   }
/*     */ 
/*     */   protected void processHierarchyEvent(HierarchyEvent paramHierarchyEvent)
/*     */   {
/* 519 */     if (((paramHierarchyEvent.getChangeFlags() & 0x4) != 0L) && 
/* 520 */       (updateScreenLocation())) {
/* 521 */       sendMoveEventToFX();
/*     */     }
/*     */ 
/* 524 */     super.processHierarchyEvent(paramHierarchyEvent);
/*     */   }
/*     */ 
/*     */   private void sendFocusEventToFX(final FocusEvent paramFocusEvent) {
/* 528 */     if ((this.stage == null) || (this.stagePeer == null) || (!isFxEnabled())) {
/* 529 */       return;
/*     */     }
/*     */ 
/* 532 */     final boolean bool = paramFocusEvent.getID() == 1004;
/* 533 */     int i = ((Integer)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Integer run()
/*     */       {
/* 537 */         int i = bool ? 0 : 3;
/*     */ 
/* 540 */         if ((bool) && ((paramFocusEvent instanceof CausedFocusEvent))) {
/* 541 */           CausedFocusEvent localCausedFocusEvent = (CausedFocusEvent)paramFocusEvent;
/* 542 */           if (localCausedFocusEvent.getCause() == CausedFocusEvent.Cause.TRAVERSAL_FORWARD)
/* 543 */             i = 1;
/* 544 */           else if (localCausedFocusEvent.getCause() == CausedFocusEvent.Cause.TRAVERSAL_BACKWARD) {
/* 545 */             i = 2;
/*     */           }
/*     */         }
/* 548 */         return Integer.valueOf(i);
/*     */       }
/*     */     })).intValue();
/*     */ 
/* 552 */     this.stagePeer.setFocused(bool, i);
/*     */   }
/*     */ 
/*     */   protected void processFocusEvent(FocusEvent paramFocusEvent)
/*     */   {
/* 565 */     sendFocusEventToFX(paramFocusEvent);
/* 566 */     super.processFocusEvent(paramFocusEvent);
/*     */   }
/*     */ 
/*     */   private void resizePixels() {
/* 570 */     if ((this.pWidth <= 0) || (this.pHeight <= 0)) {
/* 571 */       this.pixelsIm = null;
/*     */     } else {
/* 573 */       BufferedImage localBufferedImage = this.pixelsIm;
/* 574 */       this.pixelsIm = new BufferedImage(this.pWidth, this.pHeight, 2);
/* 575 */       if (localBufferedImage != null) {
/* 576 */         Graphics localGraphics = this.pixelsIm.getGraphics();
/*     */         try {
/* 578 */           localGraphics.drawImage(localBufferedImage, 0, 0, null);
/*     */         } finally {
/* 580 */           localGraphics.dispose();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void paintComponent(Graphics paramGraphics)
/*     */   {
/* 597 */     if ((this.scenePeer == null) || (this.pixelsIm == null)) {
/* 598 */       return;
/*     */     }
/*     */ 
/* 601 */     DataBufferInt localDataBufferInt = (DataBufferInt)this.pixelsIm.getRaster().getDataBuffer();
/* 602 */     int[] arrayOfInt = localDataBufferInt.getData();
/* 603 */     IntBuffer localIntBuffer = IntBuffer.wrap(arrayOfInt);
/* 604 */     if (!this.scenePeer.getPixels(localIntBuffer, this.pWidth, this.pHeight));
/* 612 */     Graphics localGraphics = null;
/*     */     try {
/* 614 */       localGraphics = paramGraphics.create();
/* 615 */       if ((this.opacity < 1.0F) && ((localGraphics instanceof Graphics2D))) {
/* 616 */         Graphics2D localGraphics2D = (Graphics2D)localGraphics;
/* 617 */         AlphaComposite localAlphaComposite = AlphaComposite.getInstance(3, this.opacity);
/* 618 */         localGraphics2D.setComposite(localAlphaComposite);
/*     */       }
/* 620 */       localGraphics.drawImage(this.pixelsIm, 0, 0, null);
/*     */     } catch (Throwable localThrowable) {
/* 622 */       localThrowable.printStackTrace();
/*     */     } finally {
/* 624 */       if (localGraphics != null)
/* 625 */         localGraphics.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Dimension getPreferredSize()
/*     */   {
/* 640 */     if ((isPreferredSizeSet()) || (this.scenePeer == null)) {
/* 641 */       return super.getPreferredSize();
/*     */     }
/* 643 */     return new Dimension(this.pPreferredWidth, this.pPreferredHeight);
/*     */   }
/*     */ 
/*     */   private boolean isFxEnabled() {
/* 647 */     return this.disableCount == 0;
/*     */   }
/*     */ 
/*     */   private void setFxEnabled(boolean paramBoolean) {
/* 651 */     if (!paramBoolean) {
/* 652 */       this.disableCount += 1;
/*     */     } else {
/* 654 */       if (this.disableCount == 0)
/*     */       {
/* 656 */         return;
/*     */       }
/* 658 */       this.disableCount -= 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addNotify()
/*     */   {
/* 669 */     super.addNotify();
/*     */ 
/* 671 */     this.dnd.addNotify();
/*     */ 
/* 673 */     Platform.runLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 676 */         JFXPanel.this.resizePixels();
/* 677 */         if ((JFXPanel.this.stage != null) && (!JFXPanel.this.stage.isShowing())) {
/* 678 */           JFXPanel.this.stage.show();
/* 679 */           JFXPanel.access$002(true);
/* 680 */           JFXPanel.this.sendMoveEventToFX();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void removeNotify()
/*     */   {
/* 692 */     Platform.runLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 695 */         if ((JFXPanel.this.stage != null) && (JFXPanel.this.stage.isShowing())) {
/* 696 */           JFXPanel.this.stage.hide();
/*     */         }
/* 698 */         JFXPanel.this.pixelsIm = null;
/*     */       }
/*     */     });
/* 701 */     super.removeNotify();
/*     */ 
/* 703 */     this.dnd.removeNotify();
/*     */ 
/* 706 */     getInputContext().removeNotify(this);
/*     */   }
/*     */   private class HostContainer implements HostInterface {
/*     */     private HostContainer() {
/*     */     }
/*     */ 
/*     */     public void setEmbeddedStage(EmbeddedStageInterface paramEmbeddedStageInterface) {
/* 713 */       JFXPanel.this.stagePeer = paramEmbeddedStageInterface;
/* 714 */       if (JFXPanel.this.stagePeer == null) {
/* 715 */         return;
/*     */       }
/* 717 */       if ((JFXPanel.this.pWidth > 0) && (JFXPanel.this.pHeight > 0)) {
/* 718 */         JFXPanel.this.stagePeer.setSize(JFXPanel.this.pWidth, JFXPanel.this.pHeight);
/*     */       }
/* 720 */       if (JFXPanel.this.isFocusOwner()) {
/* 721 */         JFXPanel.this.stagePeer.setFocused(true, 0);
/*     */       }
/* 723 */       JFXPanel.this.sendMoveEventToFX();
/*     */     }
/*     */ 
/*     */     public void setEmbeddedScene(EmbeddedSceneInterface paramEmbeddedSceneInterface)
/*     */     {
/* 728 */       JFXPanel.this.scenePeer = paramEmbeddedSceneInterface;
/* 729 */       if (JFXPanel.this.scenePeer == null) {
/* 730 */         return;
/*     */       }
/* 732 */       if ((JFXPanel.this.pWidth > 0) && (JFXPanel.this.pHeight > 0)) {
/* 733 */         JFXPanel.this.scenePeer.setSize(JFXPanel.this.pWidth, JFXPanel.this.pHeight);
/*     */       }
/*     */ 
/* 737 */       SwingUtilities.invokeLater(new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 741 */           JFXPanel.this.scenePeer.setDragStartListener(JFXPanel.this.dnd.getDragStartListener());
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/*     */     public boolean requestFocus()
/*     */     {
/* 749 */       return JFXPanel.this.requestFocusInWindow();
/*     */     }
/*     */ 
/*     */     public boolean traverseFocusOut(boolean paramBoolean)
/*     */     {
/* 755 */       KeyboardFocusManager localKeyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 756 */       if (paramBoolean)
/* 757 */         localKeyboardFocusManager.focusNextComponent(JFXPanel.this);
/*     */       else {
/* 759 */         localKeyboardFocusManager.focusPreviousComponent(JFXPanel.this);
/*     */       }
/* 761 */       return true;
/*     */     }
/*     */ 
/*     */     public void setPreferredSize(int paramInt1, int paramInt2)
/*     */     {
/* 766 */       JFXPanel.this.pPreferredWidth = paramInt1;
/* 767 */       JFXPanel.this.pPreferredHeight = paramInt2;
/* 768 */       JFXPanel.this.revalidate();
/*     */     }
/*     */ 
/*     */     public void repaint()
/*     */     {
/* 783 */       JFXPanel.this.repaint();
/*     */     }
/*     */ 
/*     */     public void setEnabled(boolean paramBoolean)
/*     */     {
/* 788 */       JFXPanel.this.setFxEnabled(paramBoolean);
/*     */     }
/*     */ 
/*     */     public void setCursor(CursorFrame paramCursorFrame)
/*     */     {
/* 793 */       final Cursor localCursor = getPlatformCursor(paramCursorFrame);
/* 794 */       SwingUtilities.invokeLater(new Runnable()
/*     */       {
/*     */         public void run() {
/* 797 */           JFXPanel.this.setCursor(localCursor);
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/*     */     private Cursor getPlatformCursor(CursorFrame paramCursorFrame) {
/* 803 */       Cursor localCursor1 = (Cursor)paramCursorFrame.getPlatformCursor(Cursor.class);
/*     */ 
/* 805 */       if (localCursor1 != null)
/*     */       {
/* 807 */         return localCursor1;
/*     */       }
/*     */ 
/* 811 */       Cursor localCursor2 = SwingCursors.embedCursorToCursor(paramCursorFrame);
/*     */ 
/* 813 */       paramCursorFrame.setPlatforCursor(Cursor.class, localCursor2);
/*     */ 
/* 815 */       return localCursor2;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swing.JFXPanel
 * JD-Core Version:    0.6.2
 */