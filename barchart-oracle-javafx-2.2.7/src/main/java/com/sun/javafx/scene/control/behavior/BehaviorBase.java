/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.scene.traversal.Direction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Control;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public class BehaviorBase<C extends Control>
/*     */ {
/*  80 */   protected static final List<KeyBinding> TRAVERSAL_BINDINGS = new ArrayList();
/*     */   private C control;
/* 106 */   private List<KeyBinding> keyBindings = createKeyBindings();
/*     */ 
/* 119 */   private final EventHandler<KeyEvent> keyEventListener = new EventHandler() {
/*     */     public void handle(KeyEvent paramAnonymousKeyEvent) {
/* 121 */       BehaviorBase.this.callActionForEvent(paramAnonymousKeyEvent);
/*     */     }
/* 119 */   };
/*     */ 
/*     */   public BehaviorBase(C paramC)
/*     */   {
/* 114 */     this.control = paramC;
/*     */ 
/* 116 */     paramC.addEventHandler(KeyEvent.ANY, this.keyEventListener);
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/* 133 */     return TRAVERSAL_BINDINGS;
/*     */   }
/*     */ 
/*     */   public final C getControl()
/*     */   {
/* 140 */     return this.control;
/*     */   }
/*     */   protected void callActionForEvent(KeyEvent paramKeyEvent) {
/* 143 */     Object localObject = null;
/* 144 */     int i = 0;
/*     */ 
/* 146 */     int j = this.keyBindings.size();
/* 147 */     for (int k = 0; k < j; k++) {
/* 148 */       KeyBinding localKeyBinding = (KeyBinding)this.keyBindings.get(k);
/* 149 */       int m = localKeyBinding.getSpecificity(this.control, paramKeyEvent);
/* 150 */       if (m > i) {
/* 151 */         i = m;
/* 152 */         localObject = localKeyBinding;
/*     */       }
/*     */     }
/*     */ 
/* 156 */     if (localObject != null) {
/* 157 */       callAction(localObject.getAction());
/* 158 */       paramKeyEvent.consume();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void callAction(String paramString)
/*     */   {
/* 173 */     if ("TraverseUp".equals(paramString)) traverseUp();
/* 174 */     else if ("TraverseDown".equals(paramString)) traverseDown();
/* 175 */     else if ("TraverseLeft".equals(paramString)) traverseLeft();
/* 176 */     else if ("TraverseRight".equals(paramString)) traverseRight();
/* 177 */     else if ("TraverseNext".equals(paramString)) traverseNext();
/* 178 */     else if ("TraversePrevious".equals(paramString)) traversePrevious();
/*     */   }
/*     */ 
/*     */   public void traverse(Node paramNode, Direction paramDirection)
/*     */   {
/* 186 */     paramNode.impl_traverse(paramDirection);
/*     */   }
/*     */ 
/*     */   public void traverseUp()
/*     */   {
/* 194 */     traverse(this.control, Direction.UP);
/*     */   }
/*     */ 
/*     */   public void traverseDown()
/*     */   {
/* 202 */     traverse(this.control, Direction.DOWN);
/*     */   }
/*     */ 
/*     */   public void traverseLeft()
/*     */   {
/* 210 */     traverse(this.control, Direction.LEFT);
/*     */   }
/*     */ 
/*     */   public void traverseRight()
/*     */   {
/* 218 */     traverse(this.control, Direction.RIGHT);
/*     */   }
/*     */ 
/*     */   public void traverseNext()
/*     */   {
/* 226 */     traverse(this.control, Direction.NEXT);
/*     */   }
/*     */ 
/*     */   public void traversePrevious()
/*     */   {
/* 234 */     traverse(this.control, Direction.PREVIOUS);
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent paramMouseEvent)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseEntered(MouseEvent paramMouseEvent)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseExited(MouseEvent paramMouseEvent)
/*     */   {
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  82 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.UP, "TraverseUp"));
/*  83 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "TraverseDown"));
/*  84 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "TraverseLeft"));
/*  85 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "TraverseRight"));
/*  86 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext"));
/*  87 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").shift());
/*     */ 
/*  89 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.UP, "TraverseUp").shift().alt().ctrl());
/*  90 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "TraverseDown").shift().alt().ctrl());
/*  91 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "TraverseLeft").shift().alt().ctrl());
/*  92 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "TraverseRight").shift().alt().ctrl());
/*  93 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext").shift().alt().ctrl());
/*  94 */     TRAVERSAL_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").alt().ctrl());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.BehaviorBase
 * JD-Core Version:    0.6.2
 */