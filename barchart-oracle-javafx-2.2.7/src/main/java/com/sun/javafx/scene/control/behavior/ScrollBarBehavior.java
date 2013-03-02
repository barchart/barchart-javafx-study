/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.Logging;
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.animation.KeyFrame;
/*     */ import javafx.animation.KeyValue;
/*     */ import javafx.animation.Timeline;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventType;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.scene.control.Control;
/*     */ import javafx.scene.control.ScrollBar;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class ScrollBarBehavior extends BehaviorBase<ScrollBar>
/*     */ {
/* 109 */   protected static final List<KeyBinding> SCROLLBAR_BINDINGS = new ArrayList();
/*     */   Timeline timeline;
/*     */ 
/*     */   public ScrollBarBehavior(ScrollBar paramScrollBar)
/*     */   {
/*  75 */     super(paramScrollBar);
/*     */   }
/*     */ 
/*     */   void home()
/*     */   {
/*  85 */     ((ScrollBar)getControl()).setValue(((ScrollBar)getControl()).getMin());
/*     */   }
/*     */ 
/*     */   void decrementValue() {
/*  89 */     ((ScrollBar)getControl()).adjustValue(0.0D);
/*     */   }
/*     */ 
/*     */   void end() {
/*  93 */     ((ScrollBar)getControl()).setValue(((ScrollBar)getControl()).getMax());
/*     */   }
/*     */ 
/*     */   void incrementValue() {
/*  97 */     ((ScrollBar)getControl()).adjustValue(1.0D);
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/* 139 */     return SCROLLBAR_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callAction(String paramString) {
/* 143 */     if ("Home".equals(paramString)) home();
/* 144 */     else if ("End".equals(paramString)) end();
/* 145 */     else if ("IncrementValue".equals(paramString)) incrementValue();
/* 146 */     else if ("DecrementValue".equals(paramString)) decrementValue(); else
/* 147 */       super.callAction(paramString);
/* 148 */     super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   public void trackPress(MouseEvent paramMouseEvent, double paramDouble)
/*     */   {
/* 177 */     if (this.timeline != null) return;
/*     */ 
/* 181 */     ScrollBar localScrollBar = (ScrollBar)getControl();
/*     */ 
/* 183 */     final double d = paramDouble;
/* 184 */     if (!localScrollBar.isFocused()) localScrollBar.requestFocus();
/* 185 */     final boolean bool = d > (localScrollBar.getValue() - localScrollBar.getMin()) / (localScrollBar.getMax() - localScrollBar.getMin());
/* 186 */     this.timeline = new Timeline();
/* 187 */     this.timeline.setCycleCount(-1);
/*     */ 
/* 189 */     EventHandler local1 = new EventHandler()
/*     */     {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent)
/*     */       {
/* 193 */         int i = d > (bool.getValue() - bool.getMin()) / (bool.getMax() - bool.getMin()) ? 1 : 0;
/* 194 */         if (this.val$incrementing == i)
/*     */         {
/* 197 */           bool.adjustValue(d);
/*     */         }
/* 199 */         else if (ScrollBarBehavior.this.timeline != null)
/*     */         {
/* 201 */           ScrollBarBehavior.this.timeline.stop();
/* 202 */           ScrollBarBehavior.this.timeline = null;
/*     */         }
/*     */       }
/*     */     };
/* 207 */     KeyFrame localKeyFrame = new KeyFrame(Duration.millis(200.0D), local1, new KeyValue[0]);
/* 208 */     this.timeline.getKeyFrames().add(localKeyFrame);
/*     */ 
/* 210 */     this.timeline.play();
/* 211 */     local1.handle(null);
/*     */   }
/*     */ 
/*     */   public void trackRelease(MouseEvent paramMouseEvent, double paramDouble)
/*     */   {
/* 218 */     if (this.timeline != null) {
/* 219 */       this.timeline.stop();
/* 220 */       this.timeline = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void decButtonPressed(MouseEvent paramMouseEvent)
/*     */   {
/* 229 */     final ScrollBar localScrollBar = (ScrollBar)getControl();
/* 230 */     if (this.timeline != null) {
/* 231 */       Logging.getJavaFXLogger().warning("timeline is not null");
/* 232 */       this.timeline.stop();
/* 233 */       this.timeline = null;
/*     */     }
/* 235 */     this.timeline = new Timeline();
/* 236 */     this.timeline.setCycleCount(-1);
/*     */ 
/* 238 */     EventHandler local2 = new EventHandler()
/*     */     {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent)
/*     */       {
/* 242 */         if (localScrollBar.getValue() > localScrollBar.getMin()) {
/* 243 */           localScrollBar.decrement();
/*     */         }
/* 245 */         else if (ScrollBarBehavior.this.timeline != null) {
/* 246 */           ScrollBarBehavior.this.timeline.stop();
/* 247 */           ScrollBarBehavior.this.timeline = null;
/*     */         }
/*     */       }
/*     */     };
/* 252 */     KeyFrame localKeyFrame = new KeyFrame(Duration.millis(200.0D), local2, new KeyValue[0]);
/* 253 */     this.timeline.getKeyFrames().add(localKeyFrame);
/*     */ 
/* 255 */     this.timeline.play();
/* 256 */     local2.handle(null);
/*     */   }
/*     */ 
/*     */   public void decButtonReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 262 */     if (this.timeline != null) {
/* 263 */       this.timeline.stop();
/* 264 */       this.timeline = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void incButtonPressed(MouseEvent paramMouseEvent)
/*     */   {
/* 273 */     final ScrollBar localScrollBar = (ScrollBar)getControl();
/* 274 */     if (this.timeline != null) {
/* 275 */       Logging.getJavaFXLogger().warning("timeline is not null");
/* 276 */       this.timeline.stop();
/* 277 */       this.timeline = null;
/*     */     }
/* 279 */     this.timeline = new Timeline();
/* 280 */     this.timeline.setCycleCount(-1);
/*     */ 
/* 282 */     EventHandler local3 = new EventHandler()
/*     */     {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent)
/*     */       {
/* 286 */         if (localScrollBar.getValue() < localScrollBar.getMax()) {
/* 287 */           localScrollBar.increment();
/*     */         }
/* 289 */         else if (ScrollBarBehavior.this.timeline != null) {
/* 290 */           ScrollBarBehavior.this.timeline.stop();
/* 291 */           ScrollBarBehavior.this.timeline = null;
/*     */         }
/*     */       }
/*     */     };
/* 296 */     KeyFrame localKeyFrame = new KeyFrame(Duration.millis(200.0D), local3, new KeyValue[0]);
/* 297 */     this.timeline.getKeyFrames().add(localKeyFrame);
/*     */ 
/* 299 */     this.timeline.play();
/* 300 */     local3.handle(null);
/*     */   }
/*     */ 
/*     */   public void incButtonReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 306 */     if (this.timeline != null) {
/* 307 */       this.timeline.stop();
/* 308 */       this.timeline = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void thumbDragged(MouseEvent paramMouseEvent, double paramDouble)
/*     */   {
/* 322 */     ScrollBar localScrollBar = (ScrollBar)getControl();
/* 323 */     double d = paramDouble * (localScrollBar.getMax() - localScrollBar.getMin()) + localScrollBar.getMin();
/* 324 */     if (!Double.isNaN(d))
/* 325 */       localScrollBar.setValue(Utils.clamp(localScrollBar.getMin(), d, localScrollBar.getMax()));
/*     */   }
/*     */ 
/*     */   public void thumbReleased(MouseEvent paramMouseEvent, double paramDouble)
/*     */   {
/* 334 */     ((ScrollBar)getControl()).adjustValue(paramDouble);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 111 */     SCROLLBAR_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext"));
/* 112 */     SCROLLBAR_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").shift());
/*     */ 
/* 114 */     SCROLLBAR_BINDINGS.add(new KeyBinding(KeyCode.F4, "TraverseDebug").alt().ctrl().shift());
/*     */ 
/* 116 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.LEFT, "DecrementValue"));
/* 117 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.KP_LEFT, "DecrementValue"));
/* 118 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.UP, "DecrementValue").vertical());
/* 119 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.KP_UP, "DecrementValue").vertical());
/* 120 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.RIGHT, "IncrementValue"));
/* 121 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.KP_RIGHT, "IncrementValue"));
/* 122 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.DOWN, "IncrementValue").vertical());
/* 123 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.KP_DOWN, "IncrementValue").vertical());
/*     */ 
/* 125 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.LEFT, "TraverseLeft").vertical());
/* 126 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.KP_LEFT, "TraverseLeft").vertical());
/* 127 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.UP, "TraverseUp"));
/* 128 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.KP_UP, "TraverseUp"));
/* 129 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.RIGHT, "TraverseRight").vertical());
/* 130 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.KP_RIGHT, "TraverseRight").vertical());
/* 131 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.DOWN, "TraverseDown"));
/* 132 */     SCROLLBAR_BINDINGS.add(new ScrollBarKeyBinding(KeyCode.KP_DOWN, "TraverseDown"));
/*     */ 
/* 134 */     SCROLLBAR_BINDINGS.add(new KeyBinding(KeyCode.HOME, KeyEvent.KEY_RELEASED, "Home"));
/* 135 */     SCROLLBAR_BINDINGS.add(new KeyBinding(KeyCode.END, KeyEvent.KEY_RELEASED, "End"));
/*     */   }
/*     */ 
/*     */   public static class ScrollBarKeyBinding extends OrientedKeyBinding
/*     */   {
/*     */     public ScrollBarKeyBinding(KeyCode paramKeyCode, String paramString)
/*     */     {
/* 342 */       super(paramString);
/*     */     }
/*     */ 
/*     */     public ScrollBarKeyBinding(KeyCode paramKeyCode, EventType<KeyEvent> paramEventType, String paramString) {
/* 346 */       super(paramEventType, paramString);
/*     */     }
/*     */ 
/*     */     public boolean getVertical(Control paramControl) {
/* 350 */       return ((ScrollBar)paramControl).getOrientation() == Orientation.VERTICAL;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.ScrollBarBehavior
 * JD-Core Version:    0.6.2
 */