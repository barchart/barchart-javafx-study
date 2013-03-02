/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.javafx.cursor.CursorFrame;
/*     */ import com.sun.javafx.embed.AbstractEvents;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDragStartListenerInterface;
/*     */ import com.sun.javafx.embed.EmbeddedSceneDropTargetInterface;
/*     */ import com.sun.javafx.embed.EmbeddedSceneInterface;
/*     */ import com.sun.javafx.embed.HostInterface;
/*     */ import com.sun.javafx.scene.traversal.Direction;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.prism.NGNode;
/*     */ import com.sun.javafx.tk.TKSceneListener;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.prism.render.ToolkitInterface;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.IntBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javafx.application.Platform;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.input.Dragboard;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.input.ScrollEvent;
/*     */ 
/*     */ public class EmbeddedScene extends GlassScene
/*     */   implements EmbeddedSceneInterface
/*     */ {
/*     */   private AbstractPainter painter;
/*     */   private PaintRenderJob paintRenderJob;
/*  38 */   protected Lock sizeLock = new ReentrantLock();
/*     */   protected IntBuffer textureBits;
/*     */   protected HostInterface host;
/*  42 */   protected boolean needsReset = true;
/*     */   protected int width;
/*     */   protected int height;
/*     */   private final EmbeddedSceneDnD dndDelegate;
/*     */ 
/*     */   public EmbeddedScene(HostInterface paramHostInterface)
/*     */   {
/*  49 */     this(paramHostInterface, false);
/*     */   }
/*     */ 
/*     */   public EmbeddedScene(HostInterface paramHostInterface, boolean paramBoolean) {
/*  53 */     super(PrismSettings.verbose, paramBoolean);
/*  54 */     this.dndDelegate = new EmbeddedSceneDnD(this);
/*     */ 
/*  56 */     this.host = paramHostInterface;
/*  57 */     this.host.setEmbeddedScene(this);
/*     */ 
/*  59 */     PaintCollector localPaintCollector = PaintCollector.getInstance();
/*  60 */     this.painter = new EmbeddedPainter(this);
/*  61 */     this.paintRenderJob = new PaintRenderJob(this, localPaintCollector.getRendered(), (Runnable)this.painter);
/*     */   }
/*     */ 
/*     */   protected boolean isSynchronous() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   public void setRoot(PGNode paramPGNode) {
/*  69 */     super.setRoot(paramPGNode);
/*  70 */     this.painter.setRoot((NGNode)paramPGNode);
/*     */   }
/*     */ 
/*     */   public void markDirty()
/*     */   {
/*  75 */     super.markDirty();
/*  76 */     this.host.repaint();
/*     */   }
/*     */ 
/*     */   public Dragboard createDragboard()
/*     */   {
/*  81 */     return this.dndDelegate.createDragboard();
/*     */   }
/*     */ 
/*     */   public void enableInputMethodEvents(boolean paramBoolean)
/*     */   {
/*  86 */     if (this.verbose)
/*  87 */       System.err.println("EmbeddedScene.enableInputMethodEvents " + paramBoolean);
/*     */   }
/*     */ 
/*     */   void stageVisible(boolean paramBoolean)
/*     */   {
/*  93 */     if (!paramBoolean) {
/*  94 */       this.host.setEmbeddedScene(null);
/*  95 */       this.host = null;
/*     */     }
/*  97 */     super.stageVisible(paramBoolean);
/*     */   }
/*     */ 
/*     */   public void repaint()
/*     */   {
/* 104 */     Toolkit localToolkit = Toolkit.getToolkit();
/* 105 */     ToolkitInterface localToolkitInterface = (ToolkitInterface)localToolkit;
/* 106 */     localToolkitInterface.addRenderJob(this.paintRenderJob);
/*     */   }
/*     */ 
/*     */   public boolean traverseOut(Direction paramDirection)
/*     */   {
/* 111 */     if (paramDirection == Direction.NEXT)
/* 112 */       return this.host.traverseFocusOut(true);
/* 113 */     if (paramDirection == Direction.PREVIOUS) {
/* 114 */       return this.host.traverseFocusOut(false);
/*     */     }
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   public void setSize(final int paramInt1, final int paramInt2)
/*     */   {
/* 121 */     this.sizeLock.lock();
/*     */     try {
/* 123 */       this.width = paramInt1;
/* 124 */       this.height = paramInt2;
/* 125 */       this.needsReset = true;
/*     */     } finally {
/* 127 */       this.sizeLock.unlock();
/*     */     }
/*     */ 
/* 130 */     Platform.runLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 133 */         AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Void run() {
/* 136 */             if (EmbeddedScene.this.sceneListener != null) {
/* 137 */               EmbeddedScene.this.sceneListener.changedSize(EmbeddedScene.1.this.val$width, EmbeddedScene.1.this.val$height);
/*     */             }
/* 139 */             return null;
/*     */           }
/*     */         }
/*     */         , EmbeddedScene.this.getAccessControlContext());
/*     */       }
/*     */     });
/* 145 */     entireSceneNeedsRepaint();
/*     */   }
/*     */ 
/*     */   public boolean getPixels(IntBuffer paramIntBuffer, int paramInt1, int paramInt2)
/*     */   {
/* 150 */     this.sizeLock.lock();
/*     */     try
/*     */     {
/*     */       boolean bool;
/* 152 */       if ((this.textureBits == null) || (this.needsReset) || (this.width != paramInt1) || (this.height != paramInt2))
/*     */       {
/* 155 */         return false;
/*     */       }
/* 157 */       paramIntBuffer.rewind();
/* 158 */       this.textureBits.rewind();
/* 159 */       paramIntBuffer.put(this.textureBits);
/* 160 */       return true;
/*     */     } finally {
/* 162 */       this.sizeLock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseEvent(final int paramInt1, final int paramInt2, final boolean paramBoolean1, final boolean paramBoolean2, final boolean paramBoolean3, final int paramInt3, final int paramInt4, final int paramInt5, final int paramInt6, final int paramInt7, final boolean paramBoolean4, final boolean paramBoolean5, final boolean paramBoolean6, final boolean paramBoolean7, final int paramInt8, final boolean paramBoolean8)
/*     */   {
/* 173 */     Platform.runLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 176 */         AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Void run() {
/* 179 */             if (EmbeddedScene.this.sceneListener != null) {
/* 180 */               EventType localEventType = AbstractEvents.mouseIDToFXEventID(EmbeddedScene.2.this.val$type);
/*     */ 
/* 182 */               if (localEventType == ScrollEvent.SCROLL) {
/* 183 */                 EmbeddedScene.this.sceneListener.scrollEvent(ScrollEvent.SCROLL, 0.0D, -EmbeddedScene.2.this.val$wheelRotation, 0.0D, 0.0D, 40.0D, 40.0D, 0, 0, 0, 0, 0, EmbeddedScene.2.this.val$x, EmbeddedScene.2.this.val$y, EmbeddedScene.2.this.val$xAbs, EmbeddedScene.2.this.val$yAbs, EmbeddedScene.2.this.val$shift, EmbeddedScene.2.this.val$ctrl, EmbeddedScene.2.this.val$alt, EmbeddedScene.2.this.val$meta, false, false);
/*     */               }
/*     */               else
/*     */               {
/* 187 */                 MouseEvent localMouseEvent = MouseEvent.impl_mouseEvent(EmbeddedScene.2.this.val$x, EmbeddedScene.2.this.val$y, EmbeddedScene.2.this.val$xAbs, EmbeddedScene.2.this.val$yAbs, AbstractEvents.mouseButtonToFXMouseButton(EmbeddedScene.2.this.val$button), EmbeddedScene.2.this.val$clickCount, EmbeddedScene.2.this.val$shift, EmbeddedScene.2.this.val$ctrl, EmbeddedScene.2.this.val$alt, EmbeddedScene.2.this.val$meta, EmbeddedScene.2.this.val$popupTrigger, EmbeddedScene.2.this.val$primaryBtnDown, EmbeddedScene.2.this.val$middleBtnDown, EmbeddedScene.2.this.val$secondaryBtnDown, false, localEventType);
/*     */ 
/* 194 */                 EmbeddedScene.this.sceneListener.mouseEvent(localMouseEvent);
/*     */               }
/*     */             }
/* 197 */             return null;
/*     */           }
/*     */         }
/*     */         , EmbeddedScene.this.getAccessControlContext());
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void menuEvent(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final boolean paramBoolean)
/*     */   {
/* 207 */     Platform.runLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 210 */         AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Void run() {
/* 213 */             if (EmbeddedScene.this.sceneListener != null) {
/* 214 */               EmbeddedScene.this.sceneListener.menuEvent(EmbeddedScene.3.this.val$x, EmbeddedScene.3.this.val$y, EmbeddedScene.3.this.val$xAbs, EmbeddedScene.3.this.val$yAbs, EmbeddedScene.3.this.val$isKeyboardTrigger);
/*     */             }
/* 216 */             return null;
/*     */           }
/*     */         }
/*     */         , EmbeddedScene.this.getAccessControlContext());
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void keyEvent(final int paramInt1, final int paramInt2, final char[] paramArrayOfChar, final int paramInt3)
/*     */   {
/* 225 */     Platform.runLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 228 */         AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Void run() {
/* 231 */             if (EmbeddedScene.this.sceneListener != null) {
/* 232 */               String str1 = EmbeddedScene.4.this.val$type == 2 ? new String(EmbeddedScene.4.this.val$ch) : Character.toString((char)EmbeddedScene.4.this.val$key);
/*     */ 
/* 236 */               String str2 = new String(EmbeddedScene.4.this.val$ch);
/* 237 */               boolean bool1 = (EmbeddedScene.4.this.val$modifiers & 0x1) != 0;
/* 238 */               boolean bool2 = (EmbeddedScene.4.this.val$modifiers & 0x2) != 0;
/* 239 */               boolean bool3 = (EmbeddedScene.4.this.val$modifiers & 0x4) != 0;
/* 240 */               boolean bool4 = (EmbeddedScene.4.this.val$modifiers & 0x8) != 0;
/* 241 */               KeyEvent localKeyEvent = KeyEvent.impl_keyEvent(null, str1, str2, EmbeddedScene.4.this.val$key, bool1, bool2, bool3, bool4, AbstractEvents.keyIDToFXEventType(EmbeddedScene.4.this.val$type));
/*     */ 
/* 244 */               EmbeddedScene.this.sceneListener.keyEvent(localKeyEvent);
/*     */             }
/* 246 */             return null;
/*     */           }
/*     */         }
/*     */         , EmbeddedScene.this.getAccessControlContext());
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void setCursor(Object paramObject)
/*     */   {
/* 255 */     super.setCursor(paramObject);
/* 256 */     this.host.setCursor((CursorFrame)paramObject);
/*     */   }
/*     */ 
/*     */   public void setDragStartListener(EmbeddedSceneDragStartListenerInterface paramEmbeddedSceneDragStartListenerInterface)
/*     */   {
/* 261 */     this.dndDelegate.setDragStartListener(paramEmbeddedSceneDragStartListenerInterface);
/*     */   }
/*     */ 
/*     */   public EmbeddedSceneDropTargetInterface createDropTarget()
/*     */   {
/* 266 */     return this.dndDelegate.createDropTarget();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.EmbeddedScene
 * JD-Core Version:    0.6.2
 */