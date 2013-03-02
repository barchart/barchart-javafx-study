/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.scene.control.skin.ColorPickerSkin;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.scene.control.ColorPicker;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.paint.Color;
/*     */ 
/*     */ public class ColorPickerBehavior extends ComboBoxBaseBehavior<Color>
/*     */ {
/*     */   protected static final String OPEN_ACTION = "Open";
/*     */   protected static final String CLOSE_ACTION = "Close";
/*  72 */   protected static final List<KeyBinding> COLOR_PICKER_BINDINGS = new ArrayList();
/*     */ 
/*     */   public ColorPickerBehavior(ColorPicker paramColorPicker)
/*     */   {
/*  52 */     super(paramColorPicker);
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/*  82 */     return COLOR_PICKER_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callAction(String paramString) {
/*  86 */     ColorPicker localColorPicker = (ColorPicker)getControl();
/*  87 */     if ("Open".equals(paramString))
/*  88 */       show();
/*  89 */     else if ("Close".equals(paramString))
/*  90 */       hide();
/*     */     else
/*  92 */       super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   private ColorPicker getColorPicker() {
/*  96 */     return (ColorPicker)getControl();
/*     */   }
/*     */ 
/*     */   public void onAutoHide()
/*     */   {
/* 118 */     this.wasComboBoxButtonClickedForAutoHide = this.mouseInsideButton;
/* 119 */     ColorPicker localColorPicker = (ColorPicker)getControl();
/* 120 */     ColorPickerSkin localColorPickerSkin = (ColorPickerSkin)localColorPicker.getSkin();
/* 121 */     localColorPickerSkin.syncWithAutoUpdate();
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent, boolean paramBoolean)
/*     */   {
/* 137 */     if (paramBoolean)
/* 138 */       super.mouseReleased(paramMouseEvent);
/*     */     else
/* 140 */       disarm();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  75 */     COLOR_PICKER_BINDINGS.add(new KeyBinding(KeyCode.ESCAPE, KeyEvent.KEY_PRESSED, "Close"));
/*  76 */     COLOR_PICKER_BINDINGS.add(new KeyBinding(KeyCode.SPACE, KeyEvent.KEY_PRESSED, "Open"));
/*  77 */     COLOR_PICKER_BINDINGS.add(new KeyBinding(KeyCode.ENTER, KeyEvent.KEY_PRESSED, "Open"));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.ColorPickerBehavior
 * JD-Core Version:    0.6.2
 */