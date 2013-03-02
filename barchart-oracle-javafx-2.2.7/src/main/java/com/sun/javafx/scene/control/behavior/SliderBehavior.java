/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.event.EventType;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.scene.control.Control;
/*     */ import javafx.scene.control.Slider;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public class SliderBehavior extends BehaviorBase<Slider>
/*     */ {
/*  63 */   protected static final List<KeyBinding> SLIDER_BINDINGS = new ArrayList();
/*     */ 
/*     */   protected void callAction(String paramString)
/*     */   {
/*  94 */     if ("Home".equals(paramString)) home();
/*  95 */     else if ("End".equals(paramString)) end();
/*  96 */     else if ("IncrementValue".equals(paramString)) incrementValue();
/*  97 */     else if ("DecrementValue".equals(paramString)) decrementValue(); else
/*  98 */       super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   public SliderBehavior(Slider paramSlider) {
/* 102 */     super(paramSlider);
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings() {
/* 106 */     return SLIDER_BINDINGS;
/*     */   }
/*     */ 
/*     */   public void trackPress(MouseEvent paramMouseEvent, double paramDouble)
/*     */   {
/* 124 */     Slider localSlider = (Slider)getControl();
/*     */ 
/* 126 */     if (!localSlider.isFocused()) localSlider.requestFocus();
/* 127 */     if (localSlider.getOrientation().equals(Orientation.HORIZONTAL))
/* 128 */       localSlider.adjustValue(paramDouble * (localSlider.getMax() - localSlider.getMin()) + localSlider.getMin());
/*     */     else
/* 130 */       localSlider.adjustValue((1.0D - paramDouble) * (localSlider.getMax() - localSlider.getMin()) + localSlider.getMin());
/*     */   }
/*     */ 
/*     */   public void trackRelease(MouseEvent paramMouseEvent, double paramDouble)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void thumbPressed(MouseEvent paramMouseEvent, double paramDouble)
/*     */   {
/* 145 */     Slider localSlider = (Slider)getControl();
/* 146 */     if (!localSlider.isFocused()) localSlider.requestFocus();
/* 147 */     localSlider.setValueChanging(true);
/*     */   }
/*     */ 
/*     */   public void thumbDragged(MouseEvent paramMouseEvent, double paramDouble)
/*     */   {
/* 155 */     Slider localSlider = (Slider)getControl();
/* 156 */     localSlider.setValue(Utils.clamp(localSlider.getMin(), paramDouble * (localSlider.getMax() - localSlider.getMin()) + localSlider.getMin(), localSlider.getMax()));
/*     */   }
/*     */ 
/*     */   private double snapValueToTicks(double paramDouble) {
/* 160 */     Slider localSlider = (Slider)getControl();
/* 161 */     double d1 = paramDouble;
/* 162 */     double d2 = 0.0D;
/*     */ 
/* 164 */     if (localSlider.getMinorTickCount() != 0)
/* 165 */       d2 = localSlider.getMajorTickUnit() / (Math.max(localSlider.getMinorTickCount(), 0) + 1);
/*     */     else {
/* 167 */       d2 = localSlider.getMajorTickUnit();
/*     */     }
/* 169 */     int i = (int)((d1 - localSlider.getMin()) / d2);
/* 170 */     double d3 = i * d2 + localSlider.getMin();
/* 171 */     double d4 = (i + 1) * d2 + localSlider.getMin();
/* 172 */     d1 = Utils.nearest(d3, d1, d4);
/* 173 */     return Utils.clamp(localSlider.getMin(), d1, localSlider.getMax());
/*     */   }
/*     */ 
/*     */   public void thumbReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 181 */     Slider localSlider = (Slider)getControl();
/* 182 */     localSlider.setValueChanging(false);
/*     */ 
/* 185 */     if (localSlider.isSnapToTicks())
/* 186 */       localSlider.setValue(snapValueToTicks(localSlider.getValue()));
/*     */   }
/*     */ 
/*     */   void home()
/*     */   {
/* 191 */     Slider localSlider = (Slider)getControl();
/* 192 */     localSlider.adjustValue(localSlider.getMin());
/*     */   }
/*     */ 
/*     */   void decrementValue() {
/* 196 */     Slider localSlider = (Slider)getControl();
/*     */ 
/* 199 */     if (localSlider.isSnapToTicks())
/* 200 */       localSlider.adjustValue(localSlider.getValue() - computeIncrement());
/*     */     else
/* 202 */       localSlider.decrement();
/*     */   }
/*     */ 
/*     */   void end()
/*     */   {
/* 208 */     Slider localSlider = (Slider)getControl();
/* 209 */     localSlider.adjustValue(localSlider.getMax());
/*     */   }
/*     */ 
/*     */   void incrementValue() {
/* 213 */     Slider localSlider = (Slider)getControl();
/*     */ 
/* 216 */     if (localSlider.isSnapToTicks())
/* 217 */       localSlider.adjustValue(localSlider.getValue() + computeIncrement());
/*     */     else
/* 219 */       localSlider.increment();
/*     */   }
/*     */ 
/*     */   double computeIncrement()
/*     */   {
/* 225 */     Slider localSlider = (Slider)getControl();
/* 226 */     double d = 0.0D;
/* 227 */     if (localSlider.getMinorTickCount() != 0)
/* 228 */       d = localSlider.getMajorTickUnit() / (Math.max(localSlider.getMinorTickCount(), 0) + 1);
/*     */     else {
/* 230 */       d = localSlider.getMajorTickUnit();
/*     */     }
/*     */ 
/* 233 */     if ((localSlider.getBlockIncrement() > 0.0D) && (localSlider.getBlockIncrement() < d)) {
/* 234 */       return d;
/*     */     }
/*     */ 
/* 237 */     return localSlider.getBlockIncrement();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  65 */     SLIDER_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext"));
/*  66 */     SLIDER_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").shift());
/*     */ 
/*  68 */     SLIDER_BINDINGS.add(new KeyBinding(KeyCode.F4, "TraverseDebug").alt().ctrl().shift());
/*     */ 
/*  70 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.LEFT, "DecrementValue"));
/*  71 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.KP_LEFT, "DecrementValue"));
/*  72 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.UP, "IncrementValue").vertical());
/*  73 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.KP_UP, "IncrementValue").vertical());
/*  74 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.RIGHT, "IncrementValue"));
/*  75 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.KP_RIGHT, "IncrementValue"));
/*  76 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.DOWN, "DecrementValue").vertical());
/*  77 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.KP_DOWN, "DecrementValue").vertical());
/*     */ 
/*  79 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.LEFT, "TraverseLeft").vertical());
/*  80 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.KP_LEFT, "TraverseLeft").vertical());
/*  81 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.UP, "TraverseUp"));
/*  82 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.KP_UP, "TraverseUp"));
/*  83 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.RIGHT, "TraverseRight").vertical());
/*  84 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.KP_RIGHT, "TraverseRight").vertical());
/*  85 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.DOWN, "TraverseDown"));
/*  86 */     SLIDER_BINDINGS.add(new SliderKeyBinding(KeyCode.KP_DOWN, "TraverseDown"));
/*     */ 
/*  88 */     SLIDER_BINDINGS.add(new KeyBinding(KeyCode.HOME, KeyEvent.KEY_RELEASED, "Home"));
/*  89 */     SLIDER_BINDINGS.add(new KeyBinding(KeyCode.END, KeyEvent.KEY_RELEASED, "End"));
/*     */   }
/*     */ 
/*     */   public static class SliderKeyBinding extends OrientedKeyBinding
/*     */   {
/*     */     public SliderKeyBinding(KeyCode paramKeyCode, String paramString)
/*     */     {
/* 242 */       super(paramString);
/*     */     }
/*     */ 
/*     */     public SliderKeyBinding(KeyCode paramKeyCode, EventType<KeyEvent> paramEventType, String paramString) {
/* 246 */       super(paramEventType, paramString);
/*     */     }
/*     */ 
/*     */     public boolean getVertical(Control paramControl) {
/* 250 */       return ((Slider)paramControl).getOrientation() == Orientation.VERTICAL;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.SliderBehavior
 * JD-Core Version:    0.6.2
 */