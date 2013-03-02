/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.scene.control.skin.ScrollPaneSkin;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.control.Control;
/*     */ import javafx.scene.control.ScrollPane;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public class ScrollPaneBehavior extends BehaviorBase<ScrollPane>
/*     */ {
/* 124 */   protected static final List<KeyBinding> SCROLLVIEW_BINDINGS = new ArrayList();
/*     */ 
/*     */   public ScrollPaneBehavior(ScrollPane paramScrollPane)
/*     */   {
/*  68 */     super(paramScrollPane);
/*     */   }
/*     */ 
/*     */   public void horizontalUnitIncrement()
/*     */   {
/*  78 */     ((ScrollPaneSkin)((ScrollPane)getControl()).getSkin()).hsbIncrement();
/*     */   }
/*     */   public void horizontalUnitDecrement() {
/*  81 */     ((ScrollPaneSkin)((ScrollPane)getControl()).getSkin()).hsbDecrement();
/*     */   }
/*     */   public void verticalUnitIncrement() {
/*  84 */     ((ScrollPaneSkin)((ScrollPane)getControl()).getSkin()).vsbIncrement();
/*     */   }
/*     */   void verticalUnitDecrement() {
/*  87 */     ((ScrollPaneSkin)((ScrollPane)getControl()).getSkin()).vsbDecrement();
/*     */   }
/*     */   void horizontalPageIncrement() {
/*  90 */     ((ScrollPaneSkin)((ScrollPane)getControl()).getSkin()).hsbPageIncrement();
/*     */   }
/*     */   void horizontalPageDecrement() {
/*  93 */     ((ScrollPaneSkin)((ScrollPane)getControl()).getSkin()).hsbPageDecrement();
/*     */   }
/*     */   void verticalPageIncrement() {
/*  96 */     ((ScrollPaneSkin)((ScrollPane)getControl()).getSkin()).vsbPageIncrement();
/*     */   }
/*     */   void verticalPageDecrement() {
/*  99 */     ((ScrollPaneSkin)((ScrollPane)getControl()).getSkin()).vsbPageDecrement();
/*     */   }
/*     */ 
/*     */   public void contentDragged(double paramDouble1, double paramDouble2)
/*     */   {
/* 104 */     ScrollPane localScrollPane = (ScrollPane)getControl();
/* 105 */     if (!localScrollPane.isPannable()) return;
/* 106 */     if (((paramDouble1 < 0.0D) && (localScrollPane.getHvalue() != 0.0D)) || ((paramDouble1 > 0.0D) && (localScrollPane.getHvalue() != localScrollPane.getHmax()))) {
/* 107 */       localScrollPane.setHvalue(localScrollPane.getHvalue() + paramDouble1);
/*     */     }
/* 109 */     if (((paramDouble2 < 0.0D) && (localScrollPane.getVvalue() != 0.0D)) || ((paramDouble2 > 0.0D) && (localScrollPane.getVvalue() != localScrollPane.getVmax())))
/* 110 */       localScrollPane.setVvalue(localScrollPane.getVvalue() + paramDouble2);
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/* 143 */     return SCROLLVIEW_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callAction(String paramString) {
/* 147 */     if ("HorizontalUnitDecrement".equals(paramString)) horizontalUnitDecrement();
/* 148 */     else if ("HorizontalUnitIncrement".equals(paramString)) horizontalUnitIncrement();
/* 149 */     else if ("VerticalUnitDecrement".equals(paramString)) verticalUnitDecrement();
/* 150 */     else if ("VerticalUnitIncrement".equals(paramString)) verticalUnitIncrement();
/* 151 */     else if ("VerticalPageDecrement".equals(paramString)) verticalPageDecrement();
/* 152 */     else if ("VerticalPageIncrement".equals(paramString)) verticalPageIncrement(); else
/* 153 */       super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   public void mouseClicked()
/*     */   {
/* 163 */     ((ScrollPane)getControl()).requestFocus();
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent) {
/* 167 */     super.mousePressed(paramMouseEvent);
/* 168 */     ((ScrollPane)getControl()).requestFocus();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 126 */     SCROLLVIEW_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext"));
/* 127 */     SCROLLVIEW_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").shift());
/*     */ 
/* 129 */     SCROLLVIEW_BINDINGS.add(new KeyBinding(KeyCode.F4, "TraverseDebug").alt().ctrl().shift());
/*     */ 
/* 131 */     SCROLLVIEW_BINDINGS.add(new ScrollViewKeyBinding(KeyCode.LEFT, "HorizontalUnitDecrement"));
/* 132 */     SCROLLVIEW_BINDINGS.add(new ScrollViewKeyBinding(KeyCode.RIGHT, "HorizontalUnitIncrement"));
/*     */ 
/* 134 */     SCROLLVIEW_BINDINGS.add(new ScrollViewKeyBinding(KeyCode.UP, "VerticalUnitDecrement"));
/* 135 */     SCROLLVIEW_BINDINGS.add(new ScrollViewKeyBinding(KeyCode.DOWN, "VerticalUnitIncrement"));
/*     */ 
/* 137 */     SCROLLVIEW_BINDINGS.add(new ScrollViewKeyBinding(KeyCode.PAGE_UP, "VerticalPageDecrement"));
/* 138 */     SCROLLVIEW_BINDINGS.add(new ScrollViewKeyBinding(KeyCode.PAGE_DOWN, "VerticalPageIncrement"));
/* 139 */     SCROLLVIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "VerticalPageIncrement"));
/*     */   }
/*     */ 
/*     */   public static class ScrollViewKeyBinding extends OrientedKeyBinding
/*     */   {
/*     */     public ScrollViewKeyBinding(KeyCode paramKeyCode, String paramString)
/*     */     {
/* 176 */       super(paramString);
/*     */     }
/*     */ 
/*     */     public ScrollViewKeyBinding(KeyCode paramKeyCode, EventType<KeyEvent> paramEventType, String paramString) {
/* 180 */       super(paramEventType, paramString);
/*     */     }
/*     */     public boolean getVertical(Control paramControl) {
/* 183 */       return true;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.ScrollPaneBehavior
 * JD-Core Version:    0.6.2
 */